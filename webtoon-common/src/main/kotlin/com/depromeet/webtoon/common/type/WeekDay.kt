package com.depromeet.webtoon.common.type

enum class WeekDay(val str: String) {
    MON("mon"),
    TUE("tue"),
    WED("wed"),
    THU("thu"),
    FRI("fri"),
    SAT("sat"),
    SUN("sun"),
    NONE("none");

    companion object {
        fun parse(str: String): WeekDay {
            return values().firstOrNull { str == it.str }
                ?: throw IllegalArgumentException("입력 문자열과 매칭되는 요일을 찾지 못했습니다. str : $str")
        }
    }
}
