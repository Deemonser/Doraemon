package com.deemo.basislib.exp.view

import android.app.Dialog
import android.content.ContextWrapper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.deemo.basislib.exp.rx._main
import com.deemo.basislib.exp.rx.autoDisposable
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * author： Deemo
 * date:    2019-07-05
 * desc:    View 相关扩展
 */

/**
 * 防抖点击
 */
fun View.setOnClick(during: Long = 1000, listener: (view: View) -> Unit) {
    var lastClickTime = 0L
    this.setOnClickListener {
        if (System.currentTimeMillis() > lastClickTime + during) {
            lastClickTime = System.currentTimeMillis()
            listener(it)
        }
    }
}


/**
 *  定时显示 Dialog
 */
fun Dialog.showTime(during: Long = 2000, dismissListener: (() -> Unit)? = null) {
    val activity: AppCompatActivity = when {
        context is AppCompatActivity -> context as AppCompatActivity
        context is ContextWrapper && (context as ContextWrapper).baseContext is AppCompatActivity -> (context as ContextWrapper).baseContext as AppCompatActivity
        else -> return
    }


    Observable.timer(during, TimeUnit.MILLISECONDS)
        .doOnSubscribe { show() }
        ._main()
        .doFinally {
            if (this.isShowing) {
                dismiss()
            }
        }
        .autoDisposable(activity)
        .subscribe({
            if (this.isShowing) {
                dismiss()
            }
            dismissListener?.let { it() }
        }, { it.printStackTrace() })

}

/**
 * 安全显示 Dialog
 */
fun Dialog.safeShow(dismissListener: (() -> Unit)? = null) {
    this.showTime(Long.MAX_VALUE, dismissListener)
}