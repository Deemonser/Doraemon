package com.deemo.basislib.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.deemo.basislib.mvp.IPresenter
import com.deemo.basislib.mvp.IView


/**
 * author： Deemo
 * date:    2019-07-05
 * desc:
 */
open class BasePresenter<V : IView> : IPresenter {

    protected var mView: V? = null

    private var lifecycle: Lifecycle? = null

    @Suppress("UNCHECKED_CAST")
    override fun attachView(view: IView) {
        mView = view as V
        lifecycle = mView?.getLifecycle()
    }


    override fun onCreate(owner: LifecycleOwner) {
        lifecycle = owner.lifecycle
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycle!!
    }

    override fun onDestroy(owner: LifecycleOwner) {
        mView = null
        lifecycle = null
    }

    override fun onLifecycleChanged(owner: LifecycleOwner, event: Lifecycle.Event) {

    }


}