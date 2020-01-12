package com.deemons.baselib.rxbus

/**
 * author： deemo
 * date:    2019-11-20
 * desc:
 */
data class MessageBean<T> constructor(val code: Int, val obj: T)

const val BUS_RATE = 30_00






