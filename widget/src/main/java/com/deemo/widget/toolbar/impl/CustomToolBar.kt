package com.deemo.widget.toolbar.impl

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.deemo.widget.R
import com.deemo.widget.toolbar.BaseToolBar

/**
 * author： cl
 * date:   2019-07-17
 * desc: 通用的toolbar（左边图片，中间文字，右边图片）
 */
class CustomToolBar(builder: Builder) : BaseToolBar(builder) {

    override fun bindLayout(): Int {
        return R.layout.custom_tool_bar
    }

    override fun bindView() {

        setIcon(R.id.custom_tool_bar_left, mBuilder.mLeftIcon)
        setIcon(R.id.custom_tool_bar_right, mBuilder.mRightIcon)

        setText(R.id.custom_tool_bar_title, mBuilder.mTitle)
        setTextColor(R.id.custom_tool_bar_title, mBuilder.mTitleColor)

        mBuilder.rootView?.setBackgroundColor(mBuilder.mBackgroundColor)

        setClickListener(R.id.custom_tool_bar_left)
        setClickListener(R.id.custom_tool_bar_right)
    }

    /**
     * 控件添加点击事件
     * @param id 控件id
     */
    private fun setClickListener(id: Int) {
        mBuilder.mParent?.findViewById<View>(id)?.setOnClickListener { view ->
            val i = view.id
            if (i == R.id.custom_tool_bar_left) {
                mBuilder.mLeftListener?.let { it() }
            } else if (i == R.id.custom_tool_bar_right) {
                mBuilder.mRightListener?.let { it() }
            }
        }
    }

    class Builder : BaseToolBar.Builder {

        constructor(context: Context) : super(context, null)

        constructor(context: Context, parent: ViewGroup) : super(context, parent)

        override fun build(): BaseToolBar {
            return CustomToolBar(this)
        }
    }
}
