package kr.hs.entrydsm.exit.global.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object FileUtil {

    fun LocalDateTime.toFileDateFormat(): String =
        format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}