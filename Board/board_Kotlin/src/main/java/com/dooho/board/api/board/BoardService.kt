package com.dooho.board.api.board

import com.dooho.board.api.ResponseDto
import com.dooho.board.api.ResponseDto.Companion.setFailed
import com.dooho.board.api.ResponseDto.Companion.setSuccess
import com.dooho.board.api.file.FileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Service
class BoardService @Autowired constructor(
    private val boardRepository: BoardRepository,
    private val fileService: FileService
) {
    fun getBoard(boardNumber: Int?): ResponseDto<BoardEntity?> {
        var board: BoardEntity? = null
        board = try {
            boardRepository.findByBoardNumber(boardNumber)
        } catch (e: Exception) {
            return setFailed("Database Error!")
        }
        return setSuccess("Success", board)
    }


    fun register(
        boardTitle: String?,
        boardContent: String?,
        boardWriterEmail: String?,
        boardWriterProfile: String?,
        boardWriterNickname: String?,
        boardWriteDate: String?,
        boardImage: MultipartFile?,
        boardVideo: MultipartFile?,
        boardFile: MultipartFile?
    ): ResponseDto<BoardEntity?> {
        val formatter = DateTimeFormatter.ISO_INSTANT
        val zonedDateTime = ZonedDateTime.parse(boardWriteDate, formatter.withZone(ZoneId.of("UTC")))
        val localDate = zonedDateTime.toLocalDate()
        val boardEntity = BoardEntity(
            boardTitle = boardTitle,
            boardContent = boardContent,
            boardWriterEmail = boardWriterEmail,
            boardWriterProfile = boardWriterProfile,
            boardWriterNickname = boardWriterNickname,
            boardWriteDate = localDate
        )

        if (boardRepository.existsByBoardTitle(boardTitle)) {
            return setFailed("Same Title already exists!")
        }

        boardRepository.save(boardEntity)

        try {
            fileService.uploadFile(boardImage, boardVideo, boardFile, boardEntity)
            boardRepository.save(boardEntity)
        } catch (e: Exception) {
            return setFailed("Database Error!")
        }

        return setSuccess("Register Success!", boardEntity)
    }
    fun getTop3() : ResponseDto<List<BoardEntity?>?>{
        var boardList: List<BoardEntity?>? = ArrayList()
        var date = LocalDate.now().minusDays(365)
        try{
            boardList = boardRepository.findTop3ByBoardWriteDateAfterOrderByBoardLikeCountDesc(date)
        }catch (e : Exception){
            e.printStackTrace()
            return setFailed("DataBase Error")
        }
        return setSuccess("Success",boardList)
    }

/**    코틀린에선, 위 메소드를 아래와 같이 프로퍼티 형태로 적용 가능
 * val top3: ResponseDto<List<BoardEntity?>?>
        get() {
            var boardList: List<BoardEntity?>? = ArrayList()
            val date = LocalDate.now().minusDays(365)
            boardList = try {
                boardRepository.findTop3ByBoardWriteDateAfterOrderByBoardLikeCountDesc(date)
            } catch (e: Exception) {
                e.printStackTrace()
                return setFailed<List<BoardEntity?>?>("DataBase Error")
            }
            return setSuccess("Success", boardList)
        }
**/

    fun getList(): ResponseDto<List<BoardEntity?>?>{
        var boardList : List<BoardEntity?>? = ArrayList()
        try{
            boardList = boardRepository.findByOrderByBoardWriteDateDescBoardNumberDesc()
        }catch (e : Exception){
            e.printStackTrace()
            return setFailed("DataBase Error")
        }
        return setSuccess("Success",boardList)
    }

    fun deleteBoard(boardNumber: Int?): ResponseDto<String?> {
        try {
            println("boardNumber = $boardNumber")
            boardRepository.deleteBoardEntityByBoardNumber(boardNumber)
        } catch (e: Exception) {
            e.printStackTrace()
            return setFailed("DataBase Error!")
        }
        return setSuccess("Success", "Success")
    }


    fun editBoard(boardNumber: Int?, dto: PatchBoardDto?): ResponseDto<PatchBoardResponseDto?> {
        var board: BoardEntity? = null
        val boardTitle = dto?.boardTitle
        val boardContent = dto?.boardContent
        val boardWriteDate = dto?.boardWriteDate
        try {
            board = boardRepository.findByBoardNumber(boardNumber)
            board?.boardTitle = boardTitle
            board?.boardContent = boardContent
            board?.boardWriteDate =boardWriteDate
            boardRepository.save(board)
        } catch (e: Exception) {
            e.printStackTrace()
            return setFailed("DataBase Error!")
        }
        val patchBoardResponseDto = PatchBoardResponseDto(board)
        return setSuccess("Success!", patchBoardResponseDto)
    }

    fun increaseView(boardNumber: Int?): ResponseDto<String?> {
        var board : BoardEntity? = boardRepository.findByBoardNumber(boardNumber)
        var boardClick : Int? = board?.boardClickCount
        try{
            board?.boardClickCount = boardClick?.plus(1)
            boardRepository.save(board)
        }catch(e : Exception){
            e.printStackTrace()
            return setFailed("DataBase Error!")
        }
        return setSuccess("Success", "")
    }

}