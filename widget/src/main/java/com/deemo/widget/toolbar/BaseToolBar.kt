package com.deemo.widget.toolbar

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.IdRes

/**
 * author： cl
 * date:   2019-07-17
 * desc: 基础的toolbar，具体的继承该类实现个性化的定制
 */
abstract class BaseToolBar(protected var mBuilder: Builder) {

    init {
        initView()
    }

    private fun initView() {
        //防止继承Activity获取不到相应的根视图
        if (mBuilder.mParent == null) {
            //获取Activity的根视图，这个是AppCompatActivity才有的
            mBuilder.mParent = (mBuilder.mContext as Activity).findViewById(android.R.id.content)
        }
        if (mBuilder.mParent == null) {
            return
        }
        //创建导航视图
        val mNavigationView = LayoutInflater.from(mBuilder.mContext).inflate(bindLayout(), mBuilder.mParent, false)
        //添加到父控件
        mBuilder.setRootView(mNavigationView)
        mBuilder.mParent!!.addView(mNavigationView)

        bindView()
    }

    /**
     * 自定义的的toolbar布局
     */
    protected abstract fun bindLayout(): Int

    /**
     * 设置toolbar布局中的文本，图片，点击事件
     */
    protected abstract fun bindView()

    /**
     * 给图片控件赋值
     * @param id
     * @param resId
     */
    protected fun setIcon(id: Int, resId: Int) {
        val iv = mBuilder.mParent!!.findViewById<ImageView>(id)
        if (resId != 0) {
            iv.visibility = View.VISIBLE
            iv.setImageResource(resId)
        }
    }

    /**
     * 给文本控件赋值
     * @param id
     * @param text
     */
    protected fun setText(@IdRes id: Int, text: String) {
        val tv = mBuilder.mParent!!.findViewById<TextView>(id)
        if (!TextUtils.isEmpty(text)) {
            tv.visibility = View.VISIBLE
            tv.text = text
        }
    }

    /**
     * 设置文本颜色
     */
    protected fun setTextColor(@IdRes id: Int, @ColorInt colorInt: Int) {
        if (colorInt == 0) return
        val tv = mBuilder.mParent?.findViewById<TextView>(id) ?: return
        tv.setTextColor(colorInt)

    }

    abstract class Builder(var mContext: Context, var mParent: ViewGroup?) {
        var rootView: View? = null

        var mTitle: String = ""

        var mLeftIcon: Int = 0

        var mRightIcon: Int = 0

        var mBackgroundColor: Int = 0

        var mTitleColor: Int = 0

        var mLeftListener: (() -> Unit)? = null

        var mRightListener: (() -> Unit)? = null

        fun setTitle(title: String): Builder {
            mTitle = title
            return this
        }

        fun setTitleColor(@ColorInt color: Int): Builder {
            mTitleColor = color
            return this
        }

        fun setRootView(view: View): Builder {
            rootView = view
            return this
        }

        fun setLeftIcon(leftIcon: Int, leftListener: (() -> Unit)? = null): Builder {
            mLeftIcon = leftIcon
            mLeftListener = leftListener
            return this
        }

        fun setRightIcon(rightIcon: Int, rightListener: (() -> Unit)? = null): Builder {
            mRightIcon = rightIcon
            mRightListener = rightListener
            return this
        }

        fun setBackgroundColor(@ColorInt color: Int): Builder {
            mBackgroundColor = color
            return this
        }


        /**
         * 将Builder的值通过构造方法传给BaseToolBar
         * 在BaseToolBar中获取参数的值
         * @return
         */
        abstract fun build(): BaseToolBar
    }

}
