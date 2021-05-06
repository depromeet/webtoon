package com.depromeet.webtoon.core.domain.account.service

import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountService (
    val accountRepository: AccountRepository
) {

}
