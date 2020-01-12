package com.deemons.baselib.rxbus

import androidx.lifecycle.LifecycleOwner
import com.deemons.baselib.exp.rx.autoDisposable
import com.deemons.baselib.mvp.IPresenter
import io.reactivex.subjects.PublishSubject


class RxBus private constructor() {
    private val subject = PublishSubject.create<MessageBean<*>>().toSerialized()


    /**
     * 发送事件
     * @param `bean`
     */
    fun send(code: Int, obj: Any = 0) {
        subject.onNext(MessageBean(code, obj))
    }


    /**
     * 订阅
     *
     */
    fun <T> subscribe(presenter: IPresenter, code: Int, consumer: (T) -> Unit) {
        subject.filter { it.code == code }
            .map { it.obj as T }
            .autoDisposable(presenter.getLifecycle() ?: return)
            .subscribe(consumer, { it.printStackTrace() })
    }


    /**
     * 订阅
     *
     */
    fun <T> subscribe(owner: LifecycleOwner, code: Int, consumer: (T) -> Unit) {
        subject.filter { it.code == code }
            .map { it.obj as T }
            .autoDisposable(owner)
            .subscribe(consumer, { it.printStackTrace() })
    }


    companion object {
        val instance: RxBus by lazy { RxBus() }
    }
}
