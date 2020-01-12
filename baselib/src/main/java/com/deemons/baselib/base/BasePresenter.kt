package com.deemons.baselib.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.deemons.baselib.mvp.IPresenter
import com.deemons.baselib.mvp.IView


/**
 * author： Deemo
 * date:    2019-07-05
 * desc:
 */
open class BasePresenter<V : IView> : IPresenter {

    protected var mView: V? = null


    @Suppress("UNCHECKED_CAST")
    override fun attachView(view: IView) {
        mView = view as V
    }

    override fun getLifecycle(): Lifecycle? {
        return mView?.getLifecycle()
    }

    override fun onDestroy() {
        mView = null
    }

    override fun onLifecycleChanged(owner: LifecycleOwner, event: Lifecycle.Event) {

    }
}