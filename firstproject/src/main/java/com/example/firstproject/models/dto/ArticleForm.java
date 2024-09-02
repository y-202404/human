package com.example.firstproject.models.dto;

import com.example.firstproject.models.entity.Article;
import lombok.*;

@AllArgsConstructor
//@NoArgsConstructor
@Getter
//@Setter
@ToString
public class ArticleForm {
    private Long id;        // id를 받을 필드(속성)
    private String title;   // 제목을 받을 필드(속성)
    private String content; // 내용을 받을 필드(속성)

    public Article toEntity() {
        return new Article(id, title, content);
    }
}
