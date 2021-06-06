package com.depromeet.webtoon.api.endpoint.account

import com.depromeet.webtoon.core.domain.account.dto.AccountResponse
import com.depromeet.webtoon.core.domain.account.dto.convertToAccountResponse
import com.depromeet.webtoon.core.domain.account.model.Account
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountAuthService(
    val accountRepository: AccountRepository,
) {
    fun loginAccount(loginToken: String) :AccountResponse {

        return createOrFindAccount(loginToken).convertToAccountResponse()
    }

    private fun createOrFindAccount(loginToken: String): Account {
        var optionalAccount = accountRepository.findByAuthToken(loginToken)
        if(optionalAccount == null){
            optionalAccount = createAccount(loginToken)
        }
        return optionalAccount
    }

    private fun createAccount(deviceId: String): Account {
        val newAccount = Account(authToken = deviceId)
        return accountRepository.save(newAccount)
    }

}
