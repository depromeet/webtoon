package com.depromeet.webtoon.common.type

import java.time.DayOfWeek

enum class WeekDay(val str: String, val dayOfWeek: DayOfWeek) {
    MON("mon", DayOfWeek.MONDAY),
    TUE("tue", DayOfWeek.TUESDAY),
    WED("wed", DayOfWeek.WEDNESDAY),
    THU("thu", DayOfWeek.THURSDAY),
    FRI("fri", DayOfWeek.FRIDAY),
    SAT("sat", DayOfWeek.SATURDAY),
    SUN("sun", DayOfWeek.SUNDAY),
    ;

    companion object {
        fun parse(str: String): WeekDay {
            return values().firstOrNull { str == it.str }
                ?: throw IllegalArgumentException("입력 문자열과 매칭되는 요일을 찾지 못했습니다. str : $str")
        }

        fun findByDayOfWeek(dayOfWeek: DayOfWeek): WeekDay {
            return values().firstOrNull { dayOfWeek == it.dayOfWeek }
                ?: throw IllegalArgumentException("매칭되는 요일을 찾지 못했습니다. dayOfWeek : $dayOfWeek")
        }
    }
}
