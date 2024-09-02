package com.example.firstproject.dao.repository;

import com.example.firstproject.models.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query(value = "SELECT * FROM reply WHERE article_id = :articleId", nativeQuery = true)
    List<Reply> findByArticleId(@Param(value = "articleId") Long articleId);
    
    List<Reply> findByNickname(@Param(value = "nickname") String nickname);
}
