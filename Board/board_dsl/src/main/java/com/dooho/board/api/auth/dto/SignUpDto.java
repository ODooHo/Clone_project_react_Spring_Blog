package com.dooho.board.api.auth.dto;

public record SignUpDto (
     String userEmail,
     String userPassword,
     String userPasswordCheck,
     String userNickname,
     String userPhoneNumber,
     String userAddress,
     String userAddressDetail,
     String userProfile
){}
