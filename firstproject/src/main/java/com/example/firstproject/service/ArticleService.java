package com.example.firstproject.service;

import com.example.firstproject.models.dto.ArticleForm;
import com.example.firstproject.models.entity.Article;
import com.example.firstproject.dao.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service    // 서비스 객체 생성
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm formDto) {
        Article article = formDto.toEntity();
        if (article != null && article.getId() != null) {
            return null;
        }

        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        // 1. DTO(ArticleForm)를 엔티티(Article)로 변환
        Article articleEntity = dto.toEntity();
        log.info("id: {}, article: {}", id, articleEntity.toString());

        // 2. 타깃 조회하기
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 타깃의 값을 체크해서 잘못된 요청 처리하기
        if (target == null || id != articleEntity.getId() ) {
            return null;
        }

        // 4. 타깃의 값을 실제 변경하기
        target.patch(articleEntity);
        Article updated = articleRepository.save(target);  // 엔티티를 DB에 저장(갱신)

        return updated;
    }

    public Article delete(Long id) {
        // 1. 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 2. 잘못된 요청 처리하기
        if (target == null) {
            return null;
        }

        // 3. 대상 삭제하기
        articleRepository.delete(target);
        return target;
    }

    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // 1. dtos를 엔티티 묶음으로 변환하기
        List<Article> articleList
                = dtos.stream().map(dto -> dto.toEntity()).collect(Collectors.toList());
//        List<Article> articleList2 = new ArrayList<>();
//        for (ArticleForm dto : dtos) {
//            articleList2.add(dto.toEntity());
//        }

        // 2. 엔티티 묶음을 DB 저장하기
        articleList.stream().forEach(article -> articleRepository.save(article));

        // 3. 강제 예외 발생시키기
        articleRepository.findById(-1L)
                .orElseThrow(() -> new IllegalArgumentException("결제 실패"));

        // 4. 결과 값 반환하기
        return articleList;
    }
}
