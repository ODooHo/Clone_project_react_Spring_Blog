package com.dooho.board.api.board

import jakarta.persistence.*

import java.time.LocalDate

 //GeneratedValue(strategy = 전략) 기본 키를 자동으로 생성해주는 어노테이션, IDENTITY = AUTOINCREMENT
//SEQUENCE = 오라클, Postgre 시퀀스를 지원, TABLE = 키 생성 전용 테이블 만들고 이름, 값을 만들어서 시퀀스를 흉내내는 것
@Entity(name = "Board")
@Table(name = "Board")
data class BoardEntity(
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     val boardNumber: Int? = null,
     var boardTitle: String? = null,
     var boardContent: String? = null,
     var boardImage: String? = null,
     var boardVideo: String? = null,
     var boardFile: String? = null,
     val boardWriterEmail: String? = null,
     var boardWriterProfile: String? = null,
     var boardWriterNickname: String? = null,
     var boardWriteDate: LocalDate? = null,
     var boardClickCount: Int? = 0,
     var boardLikeCount: Int? = 0,
     var boardCommentCount: Int? = 0
) {
    constructor(dto: BoardDto?) : this(
        dto?.boardNumber,
        dto?.boardTitle,
        dto?.boardContent,
        dto?.boardImage,
        dto?.boardVideo,
        dto?.boardFile,
        dto?.boardWriterEmail,
        dto?.boardWriterProfile,
        dto?.boardWriterNickname,
        dto?.boardWriteDate,
        dto?.boardClickCount,
        dto?.boardLikeCount,
        dto?.boardCommentCount
    )
}