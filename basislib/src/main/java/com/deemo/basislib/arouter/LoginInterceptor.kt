package com.deemo.basislib.arouter

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor

/**
 * author： Deemo
 * date:    2019-07-07
 * desc:    登录拦截器
 */
@Interceptor(priority = 8, name = "登录拦截器")
class LoginInterceptor : IInterceptor {
    override fun process(postcard: Postcard, callback: InterceptorCallback) {
//        LogUtils.d("ARouter LoginInterceptor needLogin")
//
//        if (postcard.extra != ARouterExtras.notNeedLogin) {
//            callback.onContinue(postcard)
//        }
    }

    override fun init(context: Context?) {

    }
}