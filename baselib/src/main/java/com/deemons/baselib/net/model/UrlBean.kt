package com.deemons.baselib.net.model

import java.io.Serializable

/**
 * author： deemo
 * date:    2019-08-03
 * desc:
 */
data class UrlBean(var hostValue: String, val hostList: ArrayList<Pair<String, String>>):Serializable