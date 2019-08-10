package com.deemo.widget.dialog

import android.content.Context
import android.content.DialogInterface
import android.util.SparseArray
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import com.deemo.widget.R

class AlertController(private val mBaseDialog: BaseDialog, private val mWindow: Window) {

    class AlertParams(var mContext: Context, themeResId: Int) {

        var mThemeResId = R.style.dialog
        var mCancelable: Boolean = false
        var mOnCancelListener: DialogInterface.OnCancelListener? = null
        var mOnDismissListener: DialogInterface.OnDismissListener? = null
        var mTextArray = SparseArray<CharSequence>()
        var mListenerArray = SparseArray<(BaseDialog, View) -> Unit>()
        var mLayoutView: View? = null
        private var mViewHelper: DialogViewHelper? = null
        var mWidth = LinearLayout.LayoutParams.WRAP_CONTENT
        var mAnimtion = 0
        var mGravity = Gravity.CENTER
        var mHeight = LinearLayout.LayoutParams.WRAP_CONTENT

        init {
            this.mThemeResId = themeResId
        }

        @Throws(IllegalAccessException::class)
        fun apply(mAlert: AlertController) {

            if (mLayoutView != null) {
                mViewHelper = DialogViewHelper(mContext, mLayoutView!!)
            }
            if (mViewHelper == null) {
                throw IllegalAccessException("please setContentView")
            }
            mAlert.mBaseDialog.setContentView(mViewHelper!!.contentView)

            val textSize = mTextArray.size()
            val lisSize = mListenerArray.size()
            for (i in 0 until textSize) {
                mViewHelper!!.setText(mTextArray.keyAt(i), mTextArray.valueAt(i))
            }
            for (i in 0 until lisSize) {
                mViewHelper!!.setOnclickListener(mListenerArray.keyAt(i), mListenerArray.valueAt(i),mAlert.mBaseDialog)
            }

            val window = mAlert.mWindow

            val attributes = window.attributes
            attributes.width = mWidth
            attributes.height = mHeight
            window.attributes = attributes
            window.setGravity(mGravity)
        }
    }

}
