package com.dooho.board.api.board.liky

import jakarta.persistence.*



@Entity(name = "Liky")
@Table(name = "Liky")
data class LikyEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val likeId: Int? = null,
    val boardNumber: Int? = null,
    val userEmail: String? = null,
    val likeUserProfile: String? = null,
    val likeUserNickname: String? = null
) {
    constructor(dto: LikyDto?) : this(
        dto?.likeId,
        dto?.boardNumber,
        dto?.userEmail,
        dto?.likeUserProfile,
        dto?.likeUserNickname
    )
}
