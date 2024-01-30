package com.dooho.board.api.board.liky;

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
    private Integer boardId;
    private String userEmail;
    private String likeUserProfile;
    private String likeUserNickname;

    public LikyEntity(LikyDto dto) {
        this.likeId = dto.getLikeId();
        this.boardId = dto.getBoardId();
        this.userEmail = dto.getUserEmail();
        this.likeUserProfile = dto.getLikeUserProfile();
        this.likeUserNickname = dto.getLikeUserNickname();
    }
}
