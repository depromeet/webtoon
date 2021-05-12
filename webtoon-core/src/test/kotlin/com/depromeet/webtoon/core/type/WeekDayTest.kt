package com.depromeet.webtoon.core.type

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain

internal class WeekDayTest : FunSpec({
    context("parse 테스트") {
        test("문자열 기반 WeekDay 생성") {
            forAll(
                row("mon", WeekDay.MON),
                row("tue", WeekDay.TUE),
                row("wed", WeekDay.WED),
                row("thu", WeekDay.THU),
                row("fri", WeekDay.FRI),
                row("sat", WeekDay.SAT),
                row("sun", WeekDay.SUN)
            ) { weekDayStr, weekDay ->
                WeekDay.parse(weekDayStr) shouldBe weekDay
            }
        }

        test("파싱이 안되면 오류!") {
            val except = shouldThrow<IllegalArgumentException> {
                WeekDay.parse("na")
            }

            except.message shouldContain "매칭되는 요일을 찾지 못했습니다."
        }
    }
})
