package com.deemons.server.di.component

import com.deemons.baselib.di.component.AppComponent
import com.deemons.baselib.di.scope.ActivityScope
import com.deemons.server.mvp.launch.SplashActivity
import dagger.Component

/**
 * author： Deemo
 * date:    2019-07-05
 * desc:
 */
@ActivityScope
@Component(dependencies = [AppComponent::class])
interface ActivityComponent {
    fun inject(activity: SplashActivity)

}