package com.deemons.doraemon.di.component

import com.deemo.basislib.di.component.AppComponent
import com.deemo.basislib.di.scope.FragmentScope
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