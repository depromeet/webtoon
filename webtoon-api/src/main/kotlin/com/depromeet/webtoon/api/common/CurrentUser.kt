package com.depromeet.webtoon.api.common

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class CurrentUser(username: String?, password: String?, authorities: List<GrantedAuthority>, nickname: String) : User(username, password, authorities) {
}
