package com.deemo.widget.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StyleRes

class BaseDialog  : Dialog {
    private lateinit var mAlert: AlertController

    constructor(context: Context) : super(context) {}

    constructor(context: Context, @StyleRes themeResId: Int) : super(context, themeResId) {
        mAlert = AlertController(this, window!!)
    }

    class Builder @JvmOverloads constructor(context: Context, themeResId: Int = 0) {

        private val P: AlertController.AlertParams by lazy { AlertController.AlertParams(context, themeResId) }

        fun setText(ViewId: Int, text: CharSequence): Builder {
            P.mTextArray.put(ViewId, text)
            return this
        }

        fun setOnclickListener(viewId: Int, listener: (BaseDialog, View) -> Unit): Builder {
            P.mListenerArray.put(viewId, listener)
            return this
        }

        fun setContentView(layoutId: Int): Builder {
            P.mLayoutView = LayoutInflater.from(P.mContext).inflate(layoutId, null)
            return this
        }

        fun setContentView(layoutView: View): Builder {
            P.mLayoutView = layoutView
            return this
        }

        fun setOnCancelListener(onCancelListener: DialogInterface.OnCancelListener): Builder {
            P.mOnCancelListener = onCancelListener
            return this
        }


        fun setOnDismissListener(onDismissListener: DialogInterface.OnDismissListener): Builder {
            P.mOnDismissListener = onDismissListener
            return this
        }

        fun fullWidth(): Builder {
            P.mWidth = ViewGroup.LayoutParams.MATCH_PARENT
            return this
        }

        fun fromBottom(): Builder {
            P.mGravity = Gravity.BOTTOM
            return this
        }

        fun defauleAnimtion(): Builder {
            return this
        }

        fun setAnimtion(style: Int): Builder {
            P.mAnimtion = style
            return this
        }

        fun setWidthAndHeight(width: Int, height: Int): Builder {
            P.mWidth = width
            P.mHeight = height
            return this
        }

        fun setWidth(width: Int): Builder {
            P.mWidth = width
            return this
        }

        fun setCancelable(cancelable: Boolean): Builder {
            P.mCancelable = cancelable
            return this
        }

        fun create(): BaseDialog {
            val dialog = BaseDialog(P.mContext, P.mThemeResId)
            P.apply(dialog.mAlert)
            dialog.setCancelable(P.mCancelable)
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true)
            }
            dialog.setOnCancelListener(P.mOnCancelListener)
            dialog.setOnDismissListener(P.mOnDismissListener)
            return dialog

        }

    }

    companion object {

        private val BaseDialog: BaseDialog? = null
    }
}
