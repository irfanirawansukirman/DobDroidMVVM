package ro.dobrescuandrei.demonewlibs.model.utils

import java.util.*

const val ONE_SECOND = 1000L
const val TIMEZONE = "Europe/Bucharest"

typealias Timestamp = Long
fun now() : Timestamp = nowAsCalendar().timeInMillis/ ONE_SECOND
fun nowAsCalendar() : Calendar = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE), Locale.ENGLISH)
fun Timestamp.isNil() = this==0L

fun Date.timestamp() : Timestamp = time