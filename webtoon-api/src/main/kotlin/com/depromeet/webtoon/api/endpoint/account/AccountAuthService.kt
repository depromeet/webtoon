package com.depromeet.webtoon.api.endpoint.account

import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.core.domain.account.AccountLoginResult
import com.depromeet.webtoon.core.domain.account.convertToAccountLoginResult
import com.depromeet.webtoon.core.domain.account.model.Account
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountAuthService(
    val accountRepository: AccountRepository,
) {
    fun loginAccount(loginToken: String):ApiResponse<AccountLoginResult> {

        // todo nickname 랜덤 생성구현
        return ApiResponse.ok(createOrFindAccount(loginToken).convertToAccountLoginResult())
    }

    private fun createOrFindAccount(loginToken: String): Account {
        var optionalAccount = accountRepository.findByDeviceId(loginToken)
        if(optionalAccount == null){
            optionalAccount = createAccount(loginToken)
        }
        return optionalAccount
    }

    private fun createAccount(deviceId: String): Account {
        val newAccount = Account(deviceId = deviceId)
        return accountRepository.save(newAccount)
    }

}
