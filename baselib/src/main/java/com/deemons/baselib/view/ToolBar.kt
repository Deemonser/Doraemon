package com.deemons.baselib.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.deemons.baselib.R
import kotlinx.android.synthetic.main.custom_tool_bar.view.*

/**
 * author： deemo
 * date:    2019-09-10
 * desc:    ToolBar
 */
class ToolBar : ConstraintLayout {

    companion object {
        const val STYLE_BLUE = 100
        const val STYLE_BLUE_QUESTION = 110
        const val STYLE_TRANSPARENT = 200
    }

    var style: Int = STYLE_BLUE
    var title: String = ""
    var backgroundColor: Int? = null

    constructor(context: Context) : super(context) {
        initView(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.custom_tool_bar, this, true)
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ToolBar)
            title = typedArray.getString(R.styleable.ToolBar_title) ?: ""
            style = typedArray.getInt(R.styleable.ToolBar_toolbar_style, STYLE_BLUE)
            typedArray.recycle()
        }

        custom_tool_bar_title.text
        setToolBarStyle(style)
        setTitleText(title)
    }

    override fun setBackgroundColor(color: Int) {
        backgroundColor = color
        if (color == 0) return
        super.setBackgroundColor(color)
    }

    /**
     *  设置样式
     */
    fun setToolBarStyle(style: Int): ToolBar {
//        when (style) {
//            STYLE_BLUE -> {
//                custom_tool_bar_left.visibility = View.VISIBLE
//                setBackgroundColor(R.color.main_color.getColor(context))
//                setLeftImage(R.mipmap.icon_back_white)
//                setTitleColor(Color.WHITE)
//            }
//            STYLE_BLUE_QUESTION -> {
//                custom_tool_bar_left.visibility = View.VISIBLE
//                custom_tool_bar_right.visibility = View.VISIBLE
//                setBackgroundColor(R.color.main_color.getColor(context))
//                setLeftImage(R.mipmap.icon_back_white)
//                setTitleColor(Color.WHITE)
//                setRightImage(R.mipmap.icon_help)
//            }
//            STYLE_TRANSPARENT -> {
//                custom_tool_bar_left.visibility = View.VISIBLE
//                setBackgroundColor(0)
//                background = null
//                setTitleColor(Color.BLACK)
//                setLeftImage(R.mipmap.icon_back_blue)
//            }
//        }
        return this
    }


    /**
     * 设置左边 Image
     */
    fun setLeftImage(imageIdRes: Int): ToolBar {
        custom_tool_bar_left.setImageResource(imageIdRes)
        return this
    }

    /**
     *  设置左边点击
     */
    fun setLeftClick(clickListener: (View) -> Unit): ToolBar {
        custom_tool_bar_left.setOnClickListener { clickListener(it) }
        return this
    }

    /**
     * 设置右边 Image
     */
    fun setRightImage(imageIdRes: Int): ToolBar {
        custom_tool_bar_right.setImageResource(imageIdRes)
        return this
    }

    /**
     *  设置右边点击
     */
    fun setRightClick(clickListener: (View) -> Unit): ToolBar {
        custom_tool_bar_right.setOnClickListener { clickListener(it) }
        return this
    }


    /**
     *  设置 Title
     */
    fun setTitleText(title: CharSequence): ToolBar {
        custom_tool_bar_title.text = title
        return this
    }

    /**
     *  设置 Title 颜色
     */
    fun setTitleColor(color: Int): ToolBar {
        custom_tool_bar_title.setTextColor(color)
        return this
    }

    /**
     *  设置左边 View 可见
     */
    fun setLeftVisibility(visibility: Int): ToolBar {
        custom_tool_bar_left.visibility = visibility
        return this
    }

    /**
     *  设置右边 View 可见
     */
    fun setRightVisibility(visibility: Int): ToolBar {
        custom_tool_bar_right.visibility = visibility
        return this
    }


}
