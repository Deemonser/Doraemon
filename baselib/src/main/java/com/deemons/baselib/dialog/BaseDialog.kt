package com.deemons.baselib.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.util.SparseArray
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.core.util.forEach

class BaseDialog : Dialog {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, @StyleRes themeResId: Int) : super(context, themeResId) {
    }

    class Builder @JvmOverloads constructor(val context: Context, val themeResId: Int = 0) {


        var mCancelable: Boolean = false
        var mOnDismissListener: DialogInterface.OnDismissListener? = null
        var mTextArray = SparseArray<CharSequence>()
        var mListenerArray = SparseArray<(BaseDialog, View) -> Unit>()
        var mLayoutView: View? = null
        var mWidth = LinearLayout.LayoutParams.WRAP_CONTENT

        var mGravity = Gravity.CENTER
        var mHeight = LinearLayout.LayoutParams.WRAP_CONTENT


        fun setText(ViewId: Int, text: CharSequence): Builder {
            mTextArray.put(ViewId, text)
            return this
        }

        fun setOnclickListener(viewId: Int, listener: (BaseDialog, View) -> Unit): Builder {
            mListenerArray.put(viewId, listener)
            return this
        }

        fun setContentView(layoutId: Int): Builder {
            mLayoutView = LayoutInflater.from(context).inflate(layoutId, null)
            return this
        }

        fun setContentView(layoutView: View): Builder {
            mLayoutView = layoutView
            return this
        }


        fun setOnDismissListener(onDismissListener: DialogInterface.OnDismissListener): Builder {
            mOnDismissListener = onDismissListener
            return this
        }

        fun fullWidth(): Builder {
            mWidth = ViewGroup.LayoutParams.MATCH_PARENT
            return this
        }

        fun fromBottom(): Builder {
            mGravity = Gravity.BOTTOM
            return this
        }


        fun setHeight(height: Int): Builder {
            mHeight = height
            return this
        }

        fun setWidth(width: Int): Builder {
            mWidth = width
            return this
        }

        fun setCancelable(cancelable: Boolean): Builder {
            mCancelable = cancelable
            return this
        }

        fun create(): BaseDialog {
            val dialog = if (themeResId == 0) {
                BaseDialog(context)
            } else {
                BaseDialog(context, themeResId)
            }

            mLayoutView?.apply {
                dialog.setContentView(this)
            } ?: throw IllegalAccessException("please setContentView")


            mTextArray.forEach { key, value ->
                mLayoutView?.findViewById<TextView>(key)?.text = value
            }

            mListenerArray.forEach { key, value ->
                mLayoutView?.findViewById<View>(key)?.setOnClickListener {
                    value(dialog, it)
                }
            }


            dialog.setCancelable(mCancelable)
            dialog.setCanceledOnTouchOutside(mCancelable)
            dialog.setOnDismissListener(mOnDismissListener)
            return dialog

        }

    }

}
