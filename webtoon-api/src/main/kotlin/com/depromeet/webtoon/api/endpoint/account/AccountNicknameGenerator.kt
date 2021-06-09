package com.depromeet.webtoon.api.endpoint.account

import com.depromeet.webtoon.core.domain.account.model.Account
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service

@Service
class AccountNicknameGenerator (
    val resourceLoader: ResourceLoader,
    val accountRepository: AccountRepository
    )
{
    fun generateNickname(newAccount: Account): Account{
        val createdAccount = accountRepository.save(newAccount)

        val nounList = getNicknameNounList()
        val randomNoun = generateRandom(nounList)

        val prefixList = getNicknamePrefixList()
        val randomPrefix = generateRandom(prefixList)

        createdAccount.nickname += "$randomPrefix $randomNoun#${createdAccount.id}"
        return accountRepository.save(createdAccount)
    }

    private fun getNicknamePrefixList(): List<String> {
        val nicknamePrefixes = resourceLoader.getResource("classpath:nickname/nicknameprefix.txt")
            .inputStream.bufferedReader().readText()
         return nicknamePrefixes.split(",")
    }

    private fun getNicknameNounList(): List<String> {
        val nicknameNouns = resourceLoader.getResource("classpath:nickname/nicknamenoun.txt")
            .inputStream.bufferedReader().readText()
        return nicknameNouns.split(",")
    }

    private fun generateRandom(list: List<String>): String {
        return list[(Math.random() * list.size).toInt()]
    }
}
