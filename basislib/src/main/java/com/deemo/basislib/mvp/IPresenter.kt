package com.deemo.basislib.mvp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import org.jetbrains.annotations.NotNull


/**
 * author： Deemo
 * date:    2019-07-05
 * desc:
 */
interface IPresenter : LifecycleObserver {

    fun attachView(view: IView)

    fun getLifecycle(): Lifecycle

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(@NotNull owner: LifecycleOwner)

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(@NotNull owner: LifecycleOwner)

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onLifecycleChanged(
        @NotNull owner: LifecycleOwner,
        @NotNull event: Lifecycle.Event
    )

}