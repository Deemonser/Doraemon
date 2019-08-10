package com.deemo.basislib.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.deemo.basislib.R
import com.deemo.basislib.exp.view.showTime
import com.deemo.basislib.mvp.IView
import com.deemo.widget.dialog.TipDialog
import com.deemo.widget.toolbar.BaseToolBar
import com.deemo.widget.toolbar.impl.CustomToolBar
import com.deemo.widget.utils.getColor
import com.gyf.barlibrary.ImmersionBar

/**
 * author： Deemo
 * date:    2019-07-05
 * desc:
 */
abstract class BaseActivity : AppCompatActivity(), IView {

    abstract val layoutId: Int

    private var statusBarColor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ARouter.getInstance().inject(this)


        setContentView(layoutId)

        // 指定屏幕为竖直方向
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        initComponent()

        //toolbar
        val toolBar = initToolBar()
        statusBarColor = toolBar?.mBackgroundColor ?: 0
        toolBar?.build()

        //statusBar
        initStatusBar().init()

        initEventAndData()
    }

    /**
     *  初始化状态栏
     *
     *  状态栏文字文字默认白色
     *  ImmersionBar使用文档：https://github.com/gyf-dev/ImmersionBar
     *
     *  默认情况下：
     *  1.页面不延伸到状态栏，如需要，则 fitsSystemWindows(false)
     *  2.状态栏颜色默认透明，如果有 toolbar，而且其有颜色，则设置其颜色
     *
     */
    open fun initStatusBar(): ImmersionBar {
        val immersionBar = ImmersionBar.with(this)
            .fitsSystemWindows(true)
            .statusBarDarkFont(false, 0.2f)
        if (statusBarColor != 0) immersionBar.statusBarColorInt(statusBarColor)
        return immersionBar
    }

    /**
     *  初始化 ToolBar
     *  如果返回值为 null，则不使用默认的 ToolBar
     *  默认情况下：
     *  左边白色返回箭头
     *  背景主色
     */
    open fun initToolBar(): BaseToolBar.Builder? {
        return CustomToolBar.Builder(this)
            .setBackgroundColor(R.color.main_color.getColor())
            .setLeftIcon(R.mipmap.icon_back_white) { onBackPressed() }
    }


    override fun onDestroy() {
        super.onDestroy()
        ImmersionBar.with(this).destroy()
    }

    override fun getAppCompatActivity(): AppCompatActivity {
        return this
    }

    override fun showMessage(msg: String) {
        if (msg.isBlank()) return
        ToastUtils.showLong(msg)
    }

    override fun showSuccessDialog(msg: String, during: Long, dismissListener: () -> Unit) {
        TipDialog.create(this, msg).showTime(during, dismissListener)
    }

    override fun showErrorDialog(msg: String, during: Long, dismissListener: () -> Unit) {
        TipDialog.create(this, msg, false).showTime(during, dismissListener)
    }


    //================= 以下需实现 ================

    abstract fun initComponent()

    abstract fun initEventAndData()
}