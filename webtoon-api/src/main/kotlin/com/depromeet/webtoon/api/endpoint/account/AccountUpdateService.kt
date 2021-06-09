package com.depromeet.webtoon.api.endpoint.account

import com.depromeet.webtoon.core.domain.account.dto.NicknameUpdateResponse
import com.depromeet.webtoon.core.domain.account.dto.converToNicknameUpdateRespose
import com.depromeet.webtoon.core.domain.account.model.Account
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import com.depromeet.webtoon.core.exceptions.ApiValidationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AccountUpdateService(
    val accountRepository: AccountRepository
) {
    fun updateNickname(newNickname: String): NicknameUpdateResponse {
        if(accountRepository.findByNickname(newNickname) != null){
            throw ApiValidationException("이미 존재하는 닉네임 입니다.")
        }
        return changeNickname(newNickname).converToNicknameUpdateRespose()
    }

    private fun changeNickname(newNickname: String): Account {
        val account = getAccount()
        account.nickname = newNickname
        return accountRepository.save(account)
    }


    private fun getAccount(): Account {
        val authentication = SecurityContextHolder.getContext().authentication
        return accountRepository.findById(authentication.name.toLong()).orElseThrow { ApiValidationException("서버오류") }
    }
}
