package com.dooho.board.api.auth

data class SignUpDto (
    val userEmail: String? = null,
    val userPassword: String? = null,
    val userPasswordCheck: String? = null,
    val userNickname: String? = null,
    val userPhoneNumber: String? = null,
    val userAddress: String? = null,
    val userAddressDetail: String? = null,
    val userProfile: String? = null
)