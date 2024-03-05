package com.dooho.board.api.board;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.time.LocalDate;
import java.util.List;

@EnableElasticsearchRepositories(basePackageClasses = BoardSearchRepository.class)
public interface BoardSearchRepository extends ElasticsearchRepository<BoardDocument,Integer> {

    List<BoardDocument> findByTitleContains(String boardTitle);
    void saveAllDocuments();

}
