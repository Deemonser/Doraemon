package com.deemo.widget.dialog

import android.graphics.Color
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.deemo.widget.R
import com.deemo.widget.utils.ShapeUtils
import com.deemo.widget.utils.mm2px

/**
 * author： deemo
 * date:    2019-07-12
 * desc:    提醒的 Dialog
 */
object TipDialog {

    fun create(activity: AppCompatActivity, msg: CharSequence, isSuccess: Boolean = true): BaseDialog {
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_tip, null, false)
        view.findViewById<ImageView>(R.id.tip_icon).setImageResource(
            if (isSuccess) R.mipmap.icon_success else R.mipmap.icon_fail
        )
        val set = ConstraintSet()
        set.clone(view as ConstraintLayout)
        set.setVerticalBias(R.id.tip_icon, if (isSuccess) 0.16f else 0.4f)
        set.applyTo(view)

        val baseDialog = BaseDialog.Builder(activity)
            .setContentView(view)
            .setText(R.id.tip_text, msg)
            .create()

        baseDialog.window?.let {
            val params = it.attributes
            params.width = 819f.mm2px()
            params.height = 897f.mm2px()
            it.attributes = params
            it.setDimAmount(0.2f)

            it.setBackgroundDrawable(
                ShapeUtils()
                    .solid(Color.WHITE)
                    .corner(15f.mm2px().toFloat())
                    .create()
            )
        }

        return baseDialog
    }


    fun createLoading(activity: AppCompatActivity, msg: CharSequence = ""): BaseDialog {
        val baseDialog = BaseDialog.Builder(activity)
            .setContentView(R.layout.dialog_tip_loading)
            .setText(R.id.dialog_loading_text, msg)
            .create()

        baseDialog.window?.let {
            val params = it.attributes
            params.width = 300f.mm2px()
            params.height = 300f.mm2px()
            it.attributes = params
            it.setDimAmount(0.2f)

            it.setBackgroundDrawable(
                ShapeUtils()
                    .solid(Color.WHITE)
                    .corner(15f.mm2px().toFloat())
                    .create()
            )
        }

        baseDialog.setCancelable(true)
        baseDialog.setCanceledOnTouchOutside(false)

        return baseDialog
    }
}