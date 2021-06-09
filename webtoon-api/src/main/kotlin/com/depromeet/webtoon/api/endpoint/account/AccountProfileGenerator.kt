package com.depromeet.webtoon.api.endpoint.account

import com.depromeet.webtoon.core.domain.account.model.Account
import com.depromeet.webtoon.core.domain.account.repository.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountProfileGenerator(
    val accountRepository: AccountRepository
) {
    fun generateProfileImage(newAccount: Account): Account {
        val profile = profileList[newAccount.id!!.toInt() % 6]
        newAccount.profileImage = profile
        return accountRepository.save(newAccount)
    }

    companion object{
        val profileList = listOf(
            "https://iili.io/BQbI9f.png",
            "https://iili.io/BQbnFn.png",
            "https://iili.io/BQboas.png",
            "https://iili.io/BQbx8G.png",
            "https://iili.io/BQbTu4.png",
            "https://iili.io/BQbuwl.png",
        )
    }
}
