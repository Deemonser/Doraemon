package com.deemo.basislib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.deemo.basislib.exp.view.showTime
import com.deemo.basislib.mvp.IView
import com.deemo.widget.dialog.TipDialog

/**
 * author： Deemo
 * date:    2019-07-06
 * desc:
 */
abstract class BaseFragment : Fragment(), IView {

    abstract val layoutId: Int
    lateinit var mRootView: View


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        ARouter.getInstance().inject(this)

        mRootView = inflater.inflate(layoutId, container, false)
        return mRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initComponent()

        initEventAndData()

    }

    override fun getAppCompatActivity(): AppCompatActivity {
        return context as AppCompatActivity
    }

    override fun showMessage(msg: String) {
        ToastUtils.showLong(msg)
    }

    override fun showSuccessDialog(msg: String, during: Long, dismissListener: () -> Unit) {
        TipDialog.create(getAppCompatActivity(), msg).showTime(during, dismissListener)
    }

    override fun showErrorDialog(msg: String, during: Long, dismissListener: () -> Unit) {
        TipDialog.create(getAppCompatActivity(), msg, false).showTime(during, dismissListener)
    }


    //================= 以下需实现 ================

    abstract fun initComponent()

    abstract fun initEventAndData()
}