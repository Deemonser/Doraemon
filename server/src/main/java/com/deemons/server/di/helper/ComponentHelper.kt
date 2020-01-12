package com.deemons.server.di.helper

import com.deemons.baselib.di.helper.BaseComponentHelper
import com.deemons.server.di.component.ActivityComponent
import com.deemons.server.di.component.DaggerActivityComponent
import com.deemons.server.di.component.DaggerFragmentComponent
import com.deemons.server.di.component.FragmentComponent


/**
 * author： Deemo
 * date:    2019-07-05
 * desc:
 */
object ComponentHelper {

    val activityComponent: ActivityComponent by lazy {
        DaggerActivityComponent.builder()
            .appComponent(BaseComponentHelper.appComponent)
            .build()
    }

    val fragmentComponent: FragmentComponent by lazy {
        DaggerFragmentComponent.builder()
            .appComponent(BaseComponentHelper.appComponent)
                .build()
    }

}