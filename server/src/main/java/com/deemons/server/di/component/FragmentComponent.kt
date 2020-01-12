package com.deemons.server.di.component

import com.deemons.baselib.di.component.AppComponent
import com.deemons.baselib.di.scope.FragmentScope
import dagger.Component

/**
 * author： Deemo
 * date:    2019-07-05
 * desc:
 */
@FragmentScope
@Component(dependencies = [AppComponent::class])
interface FragmentComponent {

}