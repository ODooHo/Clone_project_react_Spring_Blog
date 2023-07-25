package com.dooho.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikyDto {
    private Integer likeId;
    private Integer boardNumber;
    private String userEmail;
    private String likeUserProfile;
    private String likeUserNickname;
}
