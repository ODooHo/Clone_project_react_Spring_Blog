package com.dooho.board.entity;

import com.dooho.board.dto.board.LikyDto;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Liky")
@Table(name = "Liky")
public class LikyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer likeId;
    private Integer boardNumber;
    private String userEmail;
    private String likeUserProfile;
    private String likeUserNickname;

    public LikyEntity(LikyDto dto) {
        this.likeId = dto.getLikeId();
        this.boardNumber = dto.getBoardNumber();
        this.userEmail = dto.getUserEmail();
        this.likeUserProfile = dto.getLikeUserProfile();
        this.likeUserNickname = dto.getLikeUserNickname();
    }
}
