package com.dooho.board.api.user

import com.dooho.board.api.auth.SignUpDto
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "User")
data class UserEntity(
    @Id
    var userEmail: String? = null,
    var userPassword: String? = null,
    var userNickname: String? = null,
    var userPhoneNumber: String? = null,
    var userAddress: String? = null,
    var userProfile: String? = null
) {
    constructor(dto: SignUpDto?) : this(
        dto?.userEmail,
        dto?.userPassword,
        dto?.userNickname,
        dto?.userPhoneNumber,
        dto?.userAddress,
        dto?.userProfile
    )


    // equals, hashCode, toString 메서드는 자동으로 생성됨
}