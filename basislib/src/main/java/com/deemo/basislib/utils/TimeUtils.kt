package com.deemo.basislib.utils


import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * author： Deemo
 * date:    2019-07-05
 * desc:    此类是对 Time 的扩展
 */
object TimeUtils {


    @Retention(RetentionPolicy.SOURCE)
    private annotation class TimeFormat


    /**
     * 将 时间字符串 转为 DataTime
     * 如果字符串格式是标准格式，可以直接使用 [DateTime.parse] 或者  [DateTime]构造
     * @param time   时间字符串
     * @param format 格式 [TimeFormat]
     * @return DateTime
     */
    fun parse(time: String, @TimeFormat format: String): LocalDateTime {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern(format))
    }

    /**
     *  友好显示时间
     */
    fun convert(long: Long): String {
//        val duration = Duration(long, System.currentTimeMillis())
//        return when (duration.standardMinutes.toInt()) {
//            in 0..1 -> "1分钟前"
//            in 1..60 -> "${duration.standardMinutes.toInt()}分钟前"
//            in 60..60 * 24 -> "${duration.standardHours.toInt()}小时前"
//            in 60 * 24..60 * 24 * 4 -> "${duration.standardDays.toInt()}天前"
//            else -> {
//                val time = DateTime(long)
//                time.toString((if (DateTime.now().year == time.year) "MM/dd HH:mm" else "yy/MM/dd HH:mm"))
//            }
//        }
        return ""
    }

}
