package com.depromeet.webtoon.core.domain.account.model

import org.springframework.core.io.ClassPathResource
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.InputStream
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id

@Entity
@EntityListeners(AuditingEntityListener::class)
class Account(
    id: Long? = null,
    authToken: String = "",
    nickname: String = "",
    profileImage: String = "",
    createdAt: LocalDateTime? = null,
    modifiedAt: LocalDateTime? = null,
    ) {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Long? = id

    var authToken: String = authToken

    var nickname: String = nickname

    var profileImage: String = profileImage

    @CreatedDate
    var createdAt: LocalDateTime? = createdAt
        private set

    @LastModifiedDate
    var modifiedAt: LocalDateTime? = modifiedAt
        private set


    fun generateNickname() {
        val prefixList = getPrefixList()
        val nounList = getNounList()
        val randomPrefix = generateRandom(prefixList)
        val randomNoun = generateRandom(nounList)
        this.nickname = "$randomPrefix $randomNoun"
    }

    private fun generateRandom(nounList: List<String>): String {
        return nounList[(Math.random() * nounList.size).toInt()]
    }

    private fun getPrefixList(): List<String> {
        val prefix = ClassPathResource(nicknamePrefixPath)
        val prefixStream: InputStream = prefix.file.inputStream()
        val prefixSource = prefixStream.bufferedReader().use { it.readText() }
        return prefixSource.split(",")
    }

    private fun getNounList(): List<String> {
        val noun = ClassPathResource(nicknamePath)
        val nounStream: InputStream = noun.file.inputStream()
        val nounSource = nounStream.bufferedReader().use { it.readText() }
        return nounSource.split(",")
    }

    companion object {
        val nicknamePath = "nickname/nicknamenoun.txt"
        val nicknamePrefixPath = "nickname/nicknameprefix.txt"
    }
}
