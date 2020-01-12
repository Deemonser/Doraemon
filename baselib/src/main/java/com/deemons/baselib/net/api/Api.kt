package com.deemons.baselib.net.api

import com.deemons.baselib.net.model.BaseResponse
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


/**
 * author： Deemo
 * date:    2019-07-05
 * desc:    网络 API,
 *
 *
 *
 */
interface Api {

    companion object {
        //此处只能填写正式环境地址
        val BASE_URL = "http://111.229.132.170/"

        // 因依赖原因，故移动到此处
        val hostList = arrayListOf(
            Pair("Release ", BASE_URL),
            Pair("Test    ", "http://111.229.132.170/")
        )
    }



    /**
     * 微信支付
     */
    @FormUrlEncoded
    @POST("pay/wx/order")
    fun payWX(
        @Field("mchName") mchName: String,
        @Field("channel") channel: String,
        @Field("fee") fee: Long,
        @Field("feeType") feeType: String,
        @Field("type") type: String,
        @Field("tradeType") tradeType: String,
        @Field("attach") attach: String,
        @Field("description") description: String,
        @Field("deviceInfo") deviceInfo: String
    ): Observable<BaseResponse<String>>


}
