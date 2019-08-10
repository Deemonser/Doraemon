package com.deemons.doraemon.di.helper

import com.deemo.basislib.di.helper.BaseComponentHelper
import com.deemons.doraemon.di.component.ActivityComponent
import com.deemons.doraemon.di.component.DaggerActivityComponent
import com.deemons.doraemon.di.component.DaggerFragmentComponent
import com.deemons.doraemon.di.component.FragmentComponent


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