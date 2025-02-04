package com.depromeet.webtoon.core.domain.account.repository

import com.depromeet.webtoon.core.domain.account.model.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account, Long> {

    fun findByAuthToken(authToken: String): Account?
    fun findByNickname(newNickname: String): Account?
}
