package com.deemo.basislib.exp.rx

import androidx.annotation.StringRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.LogUtils
import com.deemo.basislib.exp.view.showTime
import com.deemo.basislib.mvp.IPresenter
import com.deemo.basislib.mvp.IView
import com.deemo.basislib.net.exception.ExceptionHandle
import com.deemo.basislib.net.model.BaseResponse
import com.deemo.widget.dialog.BaseDialog
import com.deemo.widget.dialog.TipDialog
import com.deemo.widget.utils.getString
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.functions.Consumer
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit


/**
 * author： Deemo
 * date:    2019-07-05
 * desc:   rx 相关帮助类
 */
object RxHelper {

    var scheduler: Scheduler? = null
        get() = field ?: throw RuntimeException("RxHelper.scheduler need set first!")

}

@CheckReturnValue
inline fun <T> Observable<T>.autoDisposable(
    presenter: IPresenter,
    untilEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
): ObservableSubscribeProxy<T> =
    this.`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(presenter.getLifecycle(), untilEvent)))


@CheckReturnValue
inline fun <T> Observable<T>.autoDisposable(
    owner: LifecycleOwner,
    untilEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
): ObservableSubscribeProxy<T> =
    this.autoDisposable(AndroidLifecycleScopeProvider.from(owner, untilEvent))


fun <T> Observable<T>.io_main(): Observable<T> {
    return io_()._main()
}


fun <T> Observable<T>.io_(): Observable<T> {
    return this.subscribeOn(RxHelper.scheduler)
}


fun <T> Observable<T>.main_(): Observable<T> {
    return this.subscribeOn(AndroidSchedulers.mainThread())
}


fun <T> Observable<T>._io(): Observable<T> {
    return this.observeOn(RxHelper.scheduler)
}


fun <T> Observable<T>._main(): Observable<T> {
    return this.observeOn(AndroidSchedulers.mainThread())
}


fun <T> Single<T>.io_main(): Single<T> {
    return this.subscribeOn(RxHelper.scheduler)
        .observeOn(AndroidSchedulers.mainThread())
}


/**
 *  绑定 loading dialog
 *  在订阅过后 0.2s 显示，结束时自动消失
 *  在 2s 后可取消弹窗，同时取消订阅
 */
fun <T> Observable<T>.bindLoadingDialog(
    view: IView?, @StringRes stringRes: Int = 0,
    showDelay: Long = 200,
    cancelDelay: Long = 2000
): Observable<T> {
    val dialogCancelSubject = BehaviorSubject.create<Boolean>()
    var dialog: BaseDialog? = null
    var isFinish = false
    view?.getAppCompatActivity()?.let { activity ->
        Observable.timer(showDelay, TimeUnit.MILLISECONDS)
            .filter { !isFinish }
            .io_main()
            .autoDisposable(activity)
            .subscribe({
                dialog = TipDialog.createLoading(activity, if (stringRes == 0) "" else stringRes.getString())
                dialog?.setOnCancelListener { dialogCancelSubject.onNext(true) }
                dialog?.setCancelable(false)
                dialog?.setCanceledOnTouchOutside(false)
                dialog?.showTime(Long.MAX_VALUE) {}

                Observable.timer(cancelDelay, TimeUnit.MILLISECONDS, RxHelper.scheduler)
                    .autoDisposable(activity)
                    .subscribe({
                        dialog?.setCancelable(true)
                        dialog?.setCanceledOnTouchOutside(true)
                    }, { it.printStackTrace() })
            }, { it.printStackTrace() })
    }

    return this.doFinally {
        isFinish = true
        dialog?.dismiss()
    }.takeUntil(dialogCancelSubject)
}

/**
 *  简化 Presenter 中 rx 调用
 */
fun <T> Observable<T>.simpleBind(view: IView?): Observable<T> {
    return this
        .doOnError { view?.showMessage(ExceptionHandle.handleException(it) ?: return@doOnError) }
        .io_main()
        .bindLoadingDialog(view)
}

/**
 * 处理简单结果
 */
fun <T> Observable<BaseResponse<T>>.doSimpleResult(view: IView?): Observable<BaseResponse<T>> {
    return this.doOnNext {
        if (it.isNotSuccess) {
            LogUtils.e(it.msg)
            if (it.isShowErrorMsg) view?.showMessage(it.msg)
        }
    }
}

/**
 * 错误处理
 */
fun <T> Observable<T>.sub(presenter: IPresenter, onError: ((Throwable) -> Unit)? = null, onNext: (T) -> Unit) {
    this.autoDisposable(presenter)
        .subscribe(Consumer { onNext(it) }, Consumer {
            LogUtils.e(it)
            onError?.let { self -> self(it) }
        })
}

