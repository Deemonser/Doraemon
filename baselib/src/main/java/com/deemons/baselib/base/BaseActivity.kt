package com.deemons.baselib.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Lifecycle
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.deemons.baselib.R
import com.deemons.baselib.mvp.IView
import com.deemons.baselib.view.ToolBar
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

        if (isARouter()) ARouter.getInstance().inject(this)


        // 指定屏幕为竖直方向
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContentView(layoutId)

        initComponent()

        //toolbar
        statusBarColor = initToolBar()?.backgroundColor ?: 0

        //statusBar
        initStatusBar().init()

        initEventAndData()
    }

    /**
     *  是否开启 ARouter 注册
     */
    open fun isARouter() = true

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
     *  否则，默认情况下：
     *      主题背景色，左边白色返回箭头
     *
     */
    open fun initToolBar(): ToolBar? {
        val toolBar = findViewById<ConstraintLayout>(R.id.tool_bar_root_view)?.parent ?: return null
        return if (toolBar is ToolBar) toolBar.setLeftClick { onBackPressed() } else null
    }


    override fun getAppCompatActivity(): AppCompatActivity {
        return this
    }

    override fun getLifecycle(): Lifecycle{
        return super.getLifecycle()
    }

    override fun showMessage(msg: String) {
        if (msg.isBlank()) return
        ToastUtils.showLong(msg)
    }

    override fun showSuccessDialog(msg: CharSequence, during: Long, dismissListener: () -> Unit) {
//        TipDialog.create(this, msg).showTime(during, dismissListener)
    }

    override fun showErrorDialog(msg: CharSequence, during: Long, dismissListener: () -> Unit) {
//        TipDialog.create(this, msg, false).showTime(during, dismissListener)
    }


    //================= 以下需实现 ================

    abstract fun initComponent()

    abstract fun initEventAndData()
}