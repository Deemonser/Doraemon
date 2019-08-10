package com.deemo.basislib.mvp

import com.deemo.basislib.base.BasePresenter
import javax.inject.Inject

/**
 * author： Deemo
 * date:    2019-07-05
 * desc:
 */
class EPresenter @Inject constructor() : BasePresenter<EContract.View>(),EContract.Presenter