package com.example.firstproject.dao.mapper;

import com.example.firstproject.models.vo.ArticleVO;

import java.util.List;

// JPA에 Repository와 비슷한 역할을 하는 인터페이스
public interface ArticleMapper {
    List<ArticleVO> getArticleList();  // VO는 value object
//  void insertArticle(ArticleVO articleVO);
}
