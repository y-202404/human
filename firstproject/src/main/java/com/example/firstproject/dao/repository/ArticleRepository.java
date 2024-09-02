package com.example.firstproject.dao.repository;

import com.example.firstproject.models.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    @Override
    ArrayList<Article> findAll();   // Iterable을 ArrayList로 변경
}
