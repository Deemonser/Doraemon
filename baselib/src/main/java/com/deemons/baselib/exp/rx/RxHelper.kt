package com.deemons.baselib.exp.rx

import androidx.annotation.StringRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.LogUtils
import com.deemons.baselib.dialog.BaseDialog
import com.deemons.baselib.dialog.TipDialog
import com.deemons.baselib.mvp.IPresenter
import com.deemons.baselib.mvp.IView
import com.deemons.baselib.net.exception.ExceptionHandle
import com.deemons.baselib.utils.getString
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.CheckReturnValue
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
    lifecycle: Lifecycle,
    untilEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
): ObservableSubscribeProxy<T> =
    this.`as`(
        AutoDispose.autoDisposable(
            AndroidLifecycleScopeProvider.from(lifecycle, untilEvent)
        )
    )


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
 *  在订阅过后显示，结束时自动消失
 *  在 2s 后可取消弹窗，同时取消订阅
 */
fun <T> Observable<T>.bindLoadingDialog(
    view: IView?, @StringRes stringRes: Int = 0,
    cancelDelay: Long = 2000
): Observable<T> {
    val dialogCancelSubject = BehaviorSubject.create<Boolean>()
    var dialog: BaseDialog? = null
    view?.getAppCompatActivity()?.let { activity ->
        dialog =
            TipDialog.createLoading(activity, if (stringRes == 0) "" else stringRes.getString())
        dialog?.setOnCancelListener { dialogCancelSubject.onNext(true) }
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.show()

        Observable.timer(cancelDelay, TimeUnit.MILLISECONDS, RxHelper.scheduler)
            .autoDisposable(activity)
            .subscribe({
                dialog?.setCancelable(true)
                dialog?.setCanceledOnTouchOutside(true)
            }, { it.printStackTrace() })
    }

    return this.takeUntil(dialogCancelSubject)
        .doFinally { dialog?.dismiss() }
}

/**
 *  简化 Presenter 中 rx 调用
 */
fun <T> Observable<T>.simpleBind(view: IView?): Observable<T> {
    return this.simpleError(view)
        .io_main()
        .bindLoadingDialog(view)
}


/**
 *  Rx 中 Error 处理
 */
fun <T> Observable<T>.simpleError(view: IView?): Observable<T> {
    return this.doOnError {
        view?.showMessage(
            ExceptionHandle.handleException(it) ?: return@doOnError
        )
    }
}


/**
 * 错误处理
 */
fun <T> Observable<T>.sub(
    presenter: IPresenter,
    onError: ((Throwable) -> Unit)? = null,
    onNext: (T) -> Unit
) {
    this.autoDisposable(presenter.getLifecycle() ?: return)
        .subscribe({ onNext(it) }, {
            LogUtils.e(it)
            onError?.let { self -> self(it) }
        })
}

