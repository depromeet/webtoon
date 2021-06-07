package com.depromeet.webtoon.core.domain.account

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldNotBe

class AccountTest: FunSpec ({

    context("Account 도메인 "){
        test("generateNickname 정상동작확인"){
            val account = accountFixture(nickname = "")
            account.generateNickname()
            account.nickname.shouldNotBe("")
            println(account.nickname)
        }
    }
})
