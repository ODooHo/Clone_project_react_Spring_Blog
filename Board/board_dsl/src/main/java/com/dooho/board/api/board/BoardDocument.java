package com.dooho.board.api.board;

import com.dooho.board.api.user.dto.UserDto;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "board")
public class BoardDocument {
    @Id
    @Field(type = FieldType.Keyword)
    private Integer id;
    @Field(type = FieldType.Text)
    private String title;
    @Field(type = FieldType.Text)
    private String content;
    @Field(type = FieldType.Text)
    private String image;
    @Field(type = FieldType.Text)
    private String video;
    @Field(type = FieldType.Text)
    private String file;
    @Field(type = FieldType.Date, format = {DateFormat.basic_date})
    private LocalDate boardWriteDate;
    @Field(type = FieldType.Text)
    private int clickCount;
    @Field(type = FieldType.Text)
    private int likesCount;
    @Field(type = FieldType.Text)
    private int commentsCount;



    public static BoardEntity toEntity(BoardDocument boardDocument){
        return BoardEntity.of(
                boardDocument.getId(),
                boardDocument.getTitle(),
                boardDocument.getContent(),
                boardDocument.getImage(),
                boardDocument.getVideo(),
                boardDocument.getFile(),
                boardDocument.getBoardWriteDate(),
                boardDocument.getClickCount(),
                boardDocument.getLikesCount(),
                boardDocument.getCommentsCount(),
                null
        );
    }
}

