package com.deemo.widget.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.Utils
import me.jessyan.autosize.utils.AutoSizeUtils

/**
 * author： deemo
 * date:    2019-07-15
 * desc:    资源工具
 */
object ResHelper {

    fun getContext(): Context {
        return ActivityUtils.getTopActivity() ?: Utils.getApp()
    }
}

/**
 * mm 转 px
 */
fun Float.mm2px(): Int = AutoSizeUtils.mm2px(ResHelper.getContext(), this)

/**
 * 获取字符串
 */
fun Int.getString(): String = ResHelper.getContext().resources.getString(this)

/**
 * 获取字符串
 */
fun Int.getString(vararg formatArgs: Any): String = ResHelper.getContext().resources.getString(this, formatArgs)

/**
 * 获取 Int 数组
 */
fun Int.getIntArr(): IntArray = ResHelper.getContext().resources.getIntArray(this)

/**
 *  获取 String 数组
 */
fun Int.getStringArr(): Array<String> = ResHelper.getContext().resources.getStringArray(this)

/**
 * 获取颜色
 */
fun Int.getColor(): Int = ContextCompat.getColor(ResHelper.getContext(), this)

/**
 * 获取颜色
 */
fun String.parseColor(): Int = Color.parseColor(this)

/**
 * 获取 drawable
 */
fun Int.getDrawable(): Drawable? = ContextCompat.getDrawable(ResHelper.getContext(), this)
