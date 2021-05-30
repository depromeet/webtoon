package com.depromeet.webtoon.api.endpoint.account

import com.depromeet.webtoon.api.endpoint.dto.ApiResponse
import com.depromeet.webtoon.core.domain.account.model.Account
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountAuthService(
    val accountRepository: AccountRepository,
) {
    fun enrollAccount(deviceId: String):ApiResponse<String> {
        createOrFindAccount(deviceId)
        return ApiResponse.ok("ready")
    }

    private fun createOrFindAccount(deviceId: String): Account {
        var optionalAccount = accountRepository.findByDeviceId(deviceId)
        if(optionalAccount == null){
            optionalAccount = createAccount(deviceId)
        }
        return optionalAccount
    }

    private fun createAccount(deviceId: String): Account {
        val newAccount = Account(deviceId = deviceId)
        return accountRepository.save(newAccount)
    }

}
