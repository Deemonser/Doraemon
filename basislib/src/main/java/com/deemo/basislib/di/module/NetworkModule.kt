package com.deemo.basislib.di.module

import android.os.Build
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.CacheDoubleStaticUtils
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.LogUtils
import com.deemo.basislib.BuildConfig
import com.deemo.basislib.constants.SPKey
import com.deemo.basislib.exp.rx.RxHelper
import com.deemo.basislib.net.api.Api
import com.deemo.basislib.net.interceptor.AddQueryParameterInterceptor
import com.deemo.basislib.net.interceptor.EncryptInterceptor
import com.deemo.basislib.net.interceptor.HeaderInterceptor
import com.deemo.basislib.net.model.UrlBean
import com.deemo.basislib.utils.InitUtils
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import okhttp3.ConnectionSpec
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import kotlin.collections.ArrayList

/**
 * author： Deemo
 * date:    2019-07-05
 * desc:    网络层
 */
@Module
class NetworkModule {

    private val CONNECT_TIMEOUT = 30L
    private val READ_TIMEOUT = 30L


    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()


    @Singleton
    @Provides
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()


    @Singleton
    @Provides
    fun provideExecutorService(): ExecutorService = Executors.newFixedThreadPool(10)


    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)


    /**
     * 添加请求头
     *
     * token:
     * mac： Mac 地址
     * version： 客户端版本
     * sourcedId： 客户端唯一号
     * terminal： 终端类型：android
     * timestamp：时间戳
     * phoneType：手机型号
     * os：手机操作系统
     *
     */
    @Singleton
    @Provides
    fun provideHeaderInterceptor(): HeaderInterceptor {
        val map = mutableMapOf(
            Pair("versionName", AppUtils.getAppVersionName()),
            Pair("versionCode", AppUtils.getAppVersionCode().toString()),
            Pair("sourceId", getUUID()),
            Pair("deviceType", "Android"),
            Pair("phoneType", "${DeviceUtils.getManufacturer()} ${DeviceUtils.getModel()}"),
            Pair("os", DeviceUtils.getSDKVersionCode().toString())
        )


        return HeaderInterceptor(map) { url, mutableMap ->
            mutableMap["timestamp"] = System.currentTimeMillis().toString()
            mutableMap["Authorization"] = token
        }
    }


    @Singleton
    @Provides
    fun provideOkHttpClient(
        builder: OkHttpClient.Builder,
        executorService: ExecutorService,
        headerInterceptor: HeaderInterceptor,
        encryptInterceptor: EncryptInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
        addQueryParameterInterceptor: AddQueryParameterInterceptor
    ): OkHttpClient {
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(headerInterceptor)
            .addInterceptor(addQueryParameterInterceptor)
            .addInterceptor(encryptInterceptor)
            .addInterceptor(loggingInterceptor)
            .retryOnConnectionFailure(true)
            .dispatcher(Dispatcher(executorService))
        setSSL(builder)



        return builder.build()
    }

    // 设置 ssl
    private fun setSSL(builder: OkHttpClient.Builder) {
        if (Build.VERSION.SDK_INT in 16..21) {
            try {
                val sc = SSLContext.getInstance("TLSv1.2")
                sc.init(null, null, null)
//                builder.sslSocketFactory(Tls12SocketFactory(sc.socketFactory))

                val cs = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .tlsVersions(TlsVersion.TLS_1_2)
                    .build()

                val specs = ArrayList<ConnectionSpec>()
                specs.add(cs)
                specs.add(ConnectionSpec.COMPATIBLE_TLS)
                specs.add(ConnectionSpec.CLEARTEXT)

                builder.connectionSpecs(specs)
            } catch (exc: Exception) {
                LogUtils.e("OkHttpTLSCompat", "Error while setting TLS 1.2", exc)
            }
        }
    }

    @Singleton
    @Provides
    fun provideScheduler(executorService: ExecutorService): Scheduler {
        val scheduler = Schedulers.from(executorService)
        RxHelper.scheduler = scheduler
        return scheduler
    }


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson, scheduler: Scheduler): Retrofit.Builder {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(scheduler))
    }


    @Singleton
    @Provides
    fun provideApi(retrofitBuilder: Retrofit.Builder, gson: Gson): Api {
        var baseUrl = Api.BASE_URL
        if (!InitUtils.isRelease) {
            CacheDoubleStaticUtils.getSerializable(SPKey.DEBUG_BASE_URL, null)?.let {
                val urlBean = it as UrlBean
                baseUrl = urlBean.hostValue
            }
        }
        currentBaseUrl = baseUrl
        return retrofitBuilder.baseUrl(baseUrl)
            .build().create(Api::class.java)
    }


    companion object {

        /**
         * 当前 baseUrl
         */
        var currentBaseUrl = ""
            private set(value) {
                field = value
            }

        /**
         * token
         */
        var token = ""
            set(value) {
                field = if (value.isNullOrBlank()) "" else "Basic $value"
            }

        /**
         *  获取 UUID
         */
        fun getUUID(): String {
            var uuid = CacheDoubleStaticUtils.getString(SPKey.UUID, "")
            if (uuid.isBlank()) {
                uuid = UUID.randomUUID().toString()
                CacheDoubleStaticUtils.put(SPKey.UUID, uuid)
            }
            return uuid
        }
    }

}

