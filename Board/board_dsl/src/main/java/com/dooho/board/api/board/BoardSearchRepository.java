package com.dooho.board.api.board;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface BoardSearchRepository extends ElasticsearchRepository<BoardEntity,Integer> {
    List<BoardEntity> findByTitleContains(String boardTitle);

    List<BoardEntity> findByUser_UserEmail(String userEmail);

    List<BoardEntity> findTop3ByLikesCountOrderByBoardWriteDateDesc();

}
