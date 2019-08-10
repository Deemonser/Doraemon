package com.deemo.basislib.arouter

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.SerializationService
import com.deemo.basislib.di.helper.BaseComponentHelper
import com.google.gson.Gson
import java.lang.reflect.Type
import javax.inject.Inject

/**
 * author： Deemo
 * date:    2019-07-07
 * desc:
 */
@Route(path = "/gfloan/json")
class JsonServiceImpl : SerializationService {

    @Inject
    lateinit var mGson: Gson

    override fun init(context: Context) {
        BaseComponentHelper.appComponent.inject(this)
    }

    override fun object2Json(instance: Any): String {
        return mGson.toJson(instance)
    }

    override fun <T : Any?> json2Object(input: String, clazz: Class<T>): T {
        return mGson.fromJson(input, clazz)
    }

    override fun <T : Any?> parseObject(input: String, clazz: Type): T {
        return mGson.fromJson(input, clazz)
    }
}