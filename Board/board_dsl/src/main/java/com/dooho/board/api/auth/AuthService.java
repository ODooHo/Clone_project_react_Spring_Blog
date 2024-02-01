package com.dooho.board.api.auth;

import com.dooho.board.api.ResponseDto;
import com.dooho.board.api.auth.dto.RefreshResponseDto;
import com.dooho.board.api.auth.dto.SignInDto;
import com.dooho.board.api.auth.dto.SignInResponseDto;
import com.dooho.board.api.auth.dto.SignUpDto;
import com.dooho.board.api.user.UserEntity;
import com.dooho.board.api.user.UserRepository;
import com.dooho.board.global.security.TokenProvider;
import org.springframework.security.authentication.BadCredentialsException;
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

    public ResponseDto<String> signUp(SignUpDto dto) {



        //이메일 중복 확인
        if (userRepository.existsById(dto.userEmail())) {
            throw new IllegalArgumentException("Already Exists email");
        }
        if (userRepository.existsByUserNickname(dto.userNickname())) {
            throw new IllegalArgumentException("Already Exists Nickname");
        }

        // 비밀번호가 다르면 failed response
        if (!dto.userPassword().equals(dto.userPasswordCheck())) {
            throw new IllegalArgumentException("Different Password");
        }

        // userEntity 생성,
        UserEntity userEntity = new UserEntity(dto);
        //비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(dto.userPassword());
        userEntity.setUserPassword(encodedPassword);

        //repository(db)에 저장
        userRepository.save(userEntity);
        return ResponseDto.setSuccess("Success", "SignUp Success");
    }

    @Transactional(readOnly = true)
    public ResponseDto<SignInResponseDto> signIn(SignInDto dto) {
        UserEntity userEntity = null;

        userEntity = userRepository.findById(dto.userEmail()).orElse(null);
        //잘못된 이메일
        if (userEntity == null){
            throw new BadCredentialsException("Wrong email or password");
        }


//            if (!passwordEncoder.matches(userPassword,userEntity.getUserPassword()))
//                return ResponseDto.setFailed("Sign in Failed");
        userEntity.setUserPassword("");

        String token = tokenProvider.createAccessToken(dto.userEmail());
        Integer exprTime = 1800000;

        String refreshToken = tokenProvider.createRefreshToken(dto.userEmail());
        Integer refreshExprTime = 360000000;

        SignInResponseDto signInResponseDto = new SignInResponseDto(token, exprTime, refreshToken, refreshExprTime, userEntity);
        return ResponseDto.setSuccess("Sign in Success", signInResponseDto);
    }

    @Transactional(readOnly = true)
    public ResponseDto<RefreshResponseDto> getAccess(String refreshToken) {
        String accessToken = tokenProvider.createAccessTokenFromRefreshToken(refreshToken);

        Integer exprTime = 1800000;

        RefreshResponseDto refreshResponseDto = new RefreshResponseDto(accessToken, exprTime);

        return ResponseDto.setSuccess("Success", refreshResponseDto);
    }
}
