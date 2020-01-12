package com.deemons.baselib.utils


import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.TemporalAccessor

/**
 * author： Deemo
 * date:    2019-07-05
 * desc:    此类是对 Time 的扩展
 */
object TimeUtils {


    /**
     *  友好显示时间
     */
    fun convert(long: Long): String {
        val duration = Duration.ofMillis(System.currentTimeMillis() - long)
        return when (duration.toMinutes()) {
            in 0..1 -> "1 minute ago"
            in 1..60 -> "${duration.toMinutes()} minutes ago"
            in 60..60 * 24 -> "${duration.toHours()} hours ago"
            in 60 * 24..60 * 24 * 4 -> "${duration.toDays()} days ago"
            else -> {
                val time =
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(long), Clock.systemUTC().zone)
                time.toString((if (LocalDate.now().year == time.year) "MM/dd HH:mm" else "yy/MM/dd HH:mm"))
            }
        }
    }

}

@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class TimeFormat


/**
 * 将 时间字符串 转为 UTC 时间
 */
fun String.toLocalDateTime(@TimeFormat format: String): LocalDateTime {
    return LocalDateTime.parse(this, DateTimeFormatter.ofPattern(format))
}


/**
 *  LocalDateTime 转为 UTC 时间
 */
fun LocalDateTime.toUTC(): ZonedDateTime {
    return ZonedDateTime.of(this, Clock.systemUTC().zone)
}


/**
 * 将 时间字符串 转为 LocalDate
 */
fun String.toLocalDate(@TimeFormat format: String): LocalDate {
    return LocalDate.parse(this, DateTimeFormatter.ofPattern(format))
}


/**
 *  时间格式化
 */
fun TemporalAccessor.toString(pattern: String, zoneId: ZoneId = ZoneId.systemDefault()): String {
    return DateTimeFormatter.ofPattern(pattern).withZone(zoneId).format(this)
}