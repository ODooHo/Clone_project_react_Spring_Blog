package com.dooho.board.api.user.dto;

import com.dooho.board.api.user.UserEntity;

public record UserDto(
        String userEmail,
        String userPassword,
        String userNickname,
        String userPhoneNumber,
        String userAddress,
        String userProfile
) {
    public static UserDto of(String userEmail, String userPassword, String userNickname, String userPhoneNumber, String userAddress, String userProfile) {
        return new UserDto(userEmail, userPassword, userNickname, userPhoneNumber, userAddress, userProfile);
    }

    public static UserDto from(UserEntity user){
        return new UserDto(
                user.getUserEmail(),
                user.getUserPassword(),
                user.getUserNickname(),
                user.getUserPhoneNumber(),
                user.getUserAddress(),
                user.getUserProfile()
                );
    }

    public UserEntity toEntity(){
        return UserEntity.of(
                userEmail,
                userPassword,
                userNickname,
                userPhoneNumber,
                userAddress,
                userProfile
        );
    }

}
