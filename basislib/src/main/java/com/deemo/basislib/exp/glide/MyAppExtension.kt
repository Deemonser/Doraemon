package com.deemo.basislib.exp.glide

import com.bumptech.glide.annotation.GlideExtension
import com.bumptech.glide.annotation.GlideOption
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.BaseRequestOptions

/**
 * author： deemo
 * date:    2019-07-19
 * desc:
 */
@GlideExtension
object MyAppExtension {

    @GlideOption
    @JvmStatic
    fun roundedCorners(options: BaseRequestOptions<*>, radius: Int): BaseRequestOptions<*> {
        return options.transform(RoundedCorners(radius))
    }
}
