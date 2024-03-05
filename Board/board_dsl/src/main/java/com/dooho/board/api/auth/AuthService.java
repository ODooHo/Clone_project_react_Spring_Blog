package com.dooho.board.api.auth;

import com.dooho.board.api.auth.dto.RefreshResponseDto;
import com.dooho.board.api.auth.dto.SignInDto;
import com.dooho.board.api.auth.dto.SignInResponseDto;
import com.dooho.board.api.auth.dto.SignUpDto;
import com.dooho.board.api.exception.BoardApplicationException;
import com.dooho.board.api.exception.ErrorCode;
import com.dooho.board.api.user.UserEntity;
import com.dooho.board.api.user.UserRepository;
import com.dooho.board.global.security.TokenProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AuthService {
    private final UserRepository userRepository;

    private final TokenProvider tokenProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    public void signUp(SignUpDto dto) {
        //이메일 중복 확인
        if (userRepository.existsById(dto.userEmail())) {
            throw new BoardApplicationException(ErrorCode.DUPLICATED_USER_EMAIL);
        }
        if (userRepository.existsByUserNickname(dto.userNickname())) {
            throw new BoardApplicationException(ErrorCode.DUPLICATED_USER_NICKNAME);
        }

        // 비밀번호가 다르면 failed response
        if (!dto.userPassword().equals(dto.userPasswordCheck())) {
            throw new BoardApplicationException(ErrorCode.INVALID_PASSWORD_CHECK);
        }

        // userEntity 생성,
        UserEntity userEntity = new UserEntity(dto);
        //비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(dto.userPassword());
        userEntity.setUserPassword(encodedPassword);

        //repository(db)에 저장
        userRepository.save(userEntity);
    }

    @Transactional(readOnly = true)
    public SignInResponseDto signIn(SignInDto dto) {
        UserEntity userEntity = null;

        userEntity = userRepository.findById(dto.userEmail()).orElseThrow(
                () -> new BoardApplicationException(ErrorCode.USER_NOT_FOUND,String.format("userEmail is %s",dto.userEmail()))
        );


//        if (!passwordEncoder.matches(dto.userPassword(),userEntity.getUserPassword())) {
//            throw new BoardApplicationException(ErrorCode.INVALID_PASSWORD);
//        }

        userEntity.setUserPassword("");

        String token = tokenProvider.createAccessToken(dto.userEmail());
        Integer exprTime = 1800000;

        String refreshToken = tokenProvider.createRefreshToken(dto.userEmail());
        Integer refreshExprTime = 360000000;

        return SignInResponseDto.of(token, exprTime, refreshToken, refreshExprTime, userEntity);
    }

    @Transactional(readOnly = true)
    public RefreshResponseDto getAccess(String refreshToken) {
        String accessToken = tokenProvider.createAccessTokenFromRefreshToken(refreshToken);

        Integer exprTime = 1800000;

        return RefreshResponseDto.of(accessToken, exprTime);
    }
}
