package com.deemo.basislib.net.api

import com.deemo.basislib.net.model.BaseResponse
import com.deemo.basislib.net.model.UserBean
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * author： Deemo
 * date:    2019-07-05
 * desc:    网络 API
 */
interface Api {

    companion object {
        //此次只能填写正式环境地址
        val BASE_URL = "https://sit.app.in/"
    }


    /**
     * 版本更新
     */
    @POST("/system/getVersion")
    fun getVersion(): Observable<BaseResponse<String>>


    /**
     * 登陆
     */
    @FormUrlEncoded
    @POST("userBase/userLogin")
    fun login(
        @Field("fbEmail") fbEmail: String,
        @Field("ggEmail") ggEmail: String,
        @Field("headImg") avatarUrl: String,
        @Field("mobile") mobile: String,
        @Field("verificationCode") verificationCode: String
    ): Observable<BaseResponse<UserBean>>

}