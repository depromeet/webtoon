package com.depromeet.webtoon.api.endpoint.account

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.depromeet.webtoon.api.config.JsonWebToken
import com.depromeet.webtoon.api.endpoint.dto.JwtDto
import com.depromeet.webtoon.api.endpoint.dto.convertToJwtDto
import com.depromeet.webtoon.core.domain.account.model.Account
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountAuthService(
    val accountRepository: AccountRepository,
    val jsonWebToken: JsonWebToken,
) {
    fun getJwtToken(deviceId: String):JwtDto {
        val account = createOrFindAccount(deviceId)
        return createJwtToken(account)
    }

    private fun createJwtToken(account: Account): JwtDto{
        val token = JWT.create()
            .withSubject(jsonWebToken.subject)
            .withExpiresAt(Date(System.currentTimeMillis() + jsonWebToken.expiredTime))
            .withClaim("deviceId", account.deviceId)
            .withClaim("nickname", account.nickname)
            .sign(Algorithm.HMAC512(jsonWebToken.secure))

        return convertToJwtDto(token)
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
