package com.depromeet.webtoon.api.endpoint.account

import com.depromeet.webtoon.core.domain.account.dto.AccountResponse
import com.depromeet.webtoon.core.domain.account.dto.convertToAccountResponse
import com.depromeet.webtoon.core.domain.account.model.Account
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountImportService(
    val accountRepository: AccountRepository,
    val nicknameGenerator: AccountNicknameGenerator,
    val profileGenerator: AccountProfileGenerator
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

    private fun createAccount(loginToken: String): Account {
        var newAccount = Account(authToken = loginToken)
        newAccount = nicknameGenerator.generateNickname(newAccount)
        newAccount = profileGenerator.generateProfileImage(newAccount)
        return newAccount
    }
}
