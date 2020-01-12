package com.deemons.baselib.exp.other

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 * author： deemo
 * date:    2019-10-22
 * desc:    RecycleView 帮助类,
 *          用于处理加载更多及刷新的页面
 *          如果仅仅是列表展示，请直接使用原生，不使用此类。
 */
class RecycleViewHelper<B> constructor(
    val adapter: BaseQuickAdapter<B, *>,
    val refreshLayout: SmartRefreshLayout? = null,
    val startPage: Int = 0,
    val pageSize: Int = 10
) {

    private var currentPage = startPage

    fun init(
        context: Context, rv: RecyclerView, emptyView: View? = null,
        isAutoRefresh: Boolean = false,
        getDataCallback: (currentPage: Int, pageSize: Int) -> Unit
    ) {
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = adapter

        //刷新
        refreshLayout?.setRefreshHeader(MaterialHeader(context))
        refreshLayout?.setOnRefreshListener {
            currentPage = startPage
            getDataCallback(currentPage++, pageSize)
        }

        //加载更多
        adapter.setEnableLoadMore(true)
        adapter.setPreLoadNumber(2)
        adapter.setOnLoadMoreListener({
            getDataCallback(currentPage++, pageSize)
        }, rv)

        //空页面
        emptyView?.let {
            adapter.emptyView = it
            adapter.isUseEmpty(false)
        }

        // 是否立刻加载
        if (isAutoRefresh) {
            refreshLayout?.autoRefresh()
        }
    }


    fun setData(list: List<B>, hasNextPage: Boolean) {
        if (currentPage == startPage + 1) {
            //刷新数据
            refreshLayout?.finishRefresh()
            adapter.isUseEmpty(true)
            adapter.setNewData(list)
        } else if (list.isNotEmpty()) {
            //加载更多
            adapter.addData(list)
        }

        if (!hasNextPage) {
            //加载更多结束
            adapter.loadMoreEnd()
        } else {
            //此次加载完成
            adapter.loadMoreComplete()
        }
    }


    fun OnError() {
        refreshLayout?.finishRefresh()
    }

}