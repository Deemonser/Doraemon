package com.deemo.basislib.arouter

import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback

/**
 * author： deemo
 * date:    2019-08-08
 * desc:
 */
open class SimpleNavigationCallback : NavigationCallback {
    override fun onLost(postcard: Postcard?) {

    }

    override fun onFound(postcard: Postcard?) {
    }

    override fun onInterrupt(postcard: Postcard?) {
    }

    override fun onArrival(postcard: Postcard?) {
    }

}