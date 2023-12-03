package com.dooho.board.api.auth

import com.dooho.board.api.ResponseDto
import com.dooho.board.api.ResponseDto.Companion.setFailed
import com.dooho.board.api.ResponseDto.Companion.setSuccess
import com.dooho.board.api.user.UserEntity
import com.dooho.board.api.user.UserRepository
import com.dooho.board.global.security.TokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService @Autowired constructor(
    private val userRepository: UserRepository,
    private val tokenProvider: TokenProvider
) {
    private val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()
    fun signUp(dto: SignUpDto?): ResponseDto<String?> {
        val userEmail = dto?.userEmail
        val userPassword = dto?.userPassword
        val userPasswordCheck = dto?.userPasswordCheck
        val userNickname = dto?.userNickname

        //이메일 중복 확인
        try {
            if (userRepository.existsById(userEmail)) {
                return setFailed("Email already exist!")
            }
            if (userRepository.existsByUserNickname(userNickname)) {
                return setFailed("Nickname already exist!")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return setFailed("DataBase Error!")
        }

        // 비밀번호가 다르면 failed response
        if (userPassword != userPasswordCheck) {
            return setFailed("password does not matched!")
        }

        // userEntity 생성,
        val userEntity = UserEntity(dto)
        //비밀번호 암호화
        val encodedPassword = passwordEncoder.encode(userPassword)
        userEntity.userPassword = encodedPassword

        //repository(db)에 저장
        try {
            userRepository.save(userEntity)
        } catch (e: Exception) {
            e.printStackTrace()
            return setFailed("DataBase Error!")
        }
        return setSuccess("Sign Up Success!", "Success")
    }

    fun signIn(dto: SignInDto?): ResponseDto<SignInResponseDto?> {
        val userEmail = dto?.userEmail
        val userPassword = dto?.userPassword
        var userEntity: UserEntity? = null
        try {
            userEntity = userRepository.findByUserEmail(userEmail)
            //잘못된 이메일
            if (userEntity == null) return setFailed("Sign in Failed")
            //잘못된 패스워드
            if (!passwordEncoder.matches(
                    userPassword,
                    userEntity.userPassword
                )
            ) return setFailed("Sign in Failed")
        } catch (e: Exception) {
            e.printStackTrace()
            return setFailed("DataBase Error!")
        }
        userEntity!!.userPassword = ""
        val token = tokenProvider.createAccessToken(userEmail)
        val exprTime = 1800000
        val refreshToken = tokenProvider.createRefreshToken(userEmail)
        val refreshExprTime = 360000000
        val signInResponseDto = SignInResponseDto(token, exprTime, refreshToken, refreshExprTime, userEntity)
        return setSuccess("Sign in Success", signInResponseDto)
    }

    fun getAccess(refreshToken: String?): ResponseDto<RefreshResponseDto?> {
        return try {
            val accessToken = tokenProvider.createAccessTokenFromRefreshToken(refreshToken)
            val exprTime = 1800000
            val refreshResponseDto = RefreshResponseDto(accessToken, exprTime)
            setSuccess("Success", refreshResponseDto)
        } catch (e: Exception) {
            e.printStackTrace()
            setFailed("DataBase Error!")
        }
    }
}