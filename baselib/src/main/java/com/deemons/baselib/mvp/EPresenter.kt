package com.deemons.baselib.mvp

import com.deemons.baselib.base.BasePresenter
import javax.inject.Inject

/**
 * author： Deemo
 * date:    2019-07-05
 * desc:
 */
class EPresenter @Inject constructor() : BasePresenter<EContract.View>(), EContract.Presenter