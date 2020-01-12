package com.deemons.baselib.dialog

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import com.deemons.baselib.R
import com.deemons.baselib.utils.ShapeUtils
import com.deemons.baselib.utils.mm2px

/**
 * author： deemo
 * date:    2019-07-12
 * desc:    提醒的 Dialog
 */
object TipDialog {


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