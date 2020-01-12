package com.deemons.baselib.mvp

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle


/**
 * author： Deemo
 * date:    2019-07-05
 * desc:
 */
interface IView  {

    fun getAppCompatActivity():AppCompatActivity

    fun getLifecycle():Lifecycle

    fun showMessage(msg: String)

    fun showSuccessDialog(msg: CharSequence, during: Long = 1500, dismissListener: () -> Unit)

    fun showErrorDialog(msg: CharSequence, during: Long = 1500, dismissListener: () -> Unit)
}
