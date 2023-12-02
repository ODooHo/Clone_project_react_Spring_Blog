package com.dooho.board.api.board.liky;

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
