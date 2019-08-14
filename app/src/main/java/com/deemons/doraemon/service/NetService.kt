package com.deemons.doraemon.service

import com.deemo.basislib.net.api.Api
import com.deemo.basislib.net.model.BaseResponse
import io.reactivex.Observable
import javax.inject.Inject

/**
 * author： deemo
 * date:    2019-08-14
 * desc:
 */
class NetService @Inject constructor(val api: Api) {

    fun getVersion(): Observable<BaseResponse<String>> {
        return api.getVersion()
    }

}