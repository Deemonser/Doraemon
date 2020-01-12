package com.deemons.baselib.dialog

import android.content.Context
import android.util.SparseArray
import android.view.View
import android.widget.TextView
import java.lang.ref.WeakReference

class DialogViewHelper(private val mContext: Context, val contentView: View) {
    private val viewArray = SparseArray<WeakReference<View>>()

    fun setText(viewId: Int, charSequence: CharSequence?) {
        val tv = getView<TextView>(viewId)
        charSequence?.let { tv.text = charSequence }
    }

    private fun <T : View> getView(viewId: Int): T {
        val viewWeakReference = viewArray.get(viewId)
        if (viewWeakReference != null) {
            return viewWeakReference.get()?.findViewById<View>(viewId) as T
        } else {
            viewArray.put(viewId, WeakReference(contentView.findViewById(viewId)))
            return contentView.findViewById<View>(viewId) as T
        }
    }

    fun setOnclickListener(
        viewId: Int,
        listener: (BaseDialog, View) -> Unit,
        mBaseDialog: BaseDialog
    ) {
        val view = getView<View>(viewId)
        view.setOnClickListener {
            listener(mBaseDialog, it)
        }
    }
}
