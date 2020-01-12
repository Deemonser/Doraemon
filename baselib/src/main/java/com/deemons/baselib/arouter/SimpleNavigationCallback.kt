package com.deemons.baselib.arouter

import android.app.Activity
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



/**
 * 跳转成功后，关闭自身页面
 */
fun Postcard.withFinishNavigation(activity: Activity) {
    this.navigation(activity, object : SimpleNavigationCallback() {
        override fun onArrival(postcard: Postcard?) {
            super.onArrival(postcard)
            activity.finish()
        }
    })
}