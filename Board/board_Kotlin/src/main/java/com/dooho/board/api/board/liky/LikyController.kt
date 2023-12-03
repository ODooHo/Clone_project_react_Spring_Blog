package com.dooho.board.api.board.liky

import com.dooho.board.api.ResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/board")
class LikyController @Autowired constructor(private val likyService: LikyService) {
    @PostMapping("/{boardNumber}/liky/add")
    fun addLiky(@RequestBody requestBody: LikyDto?): ResponseDto<*> {
        return likyService.addLiky(requestBody)
    }

    @GetMapping("/{boardNumber}/liky/get")
    fun getLiky(@PathVariable boardNumber: Int?): ResponseDto<List<LikyEntity?>?> {
        return likyService.getLiky(boardNumber)
    }

    @GetMapping("/{boardNumber}/liky/get/count")
    fun getLikyCount(@PathVariable boardNumber: Int?): ResponseDto<Int?> {
        return likyService.getLikyCount(boardNumber)
    }

    @GetMapping("/{boardNumber}/liky/delete/{likeUserNickname}")
    fun deleteLiky(
        @PathVariable boardNumber: Int?,
        @PathVariable likeUserNickname: String?
    ): ResponseDto<*> {
        return likyService.deleteLiky(boardNumber, likeUserNickname)
    }
}