package com.example.firstproject.models.entity;

import com.example.firstproject.models.dto.ReplyDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column
    private String nickname;


    @Column
    private String body;


    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    public static Reply createReply(
            ReplyDto replyDto,
            Article article) {

        if(article.getId() != replyDto.getArticleId())
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못되었습니다.");
        if(replyDto.getId() != null)
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야 합니다.");


        return new Reply(
                null,
                replyDto.getNickname(),
                replyDto.getBody(),
                article);
    }

    public void patch(ReplyDto replyDto) {
        if(this.id != replyDto.getId())
            throw new IllegalArgumentException("댓글 수정 실패! 댓글의 id가 일치하지 않습니다.");

        if (replyDto.getNickname() != null) {
            this.nickname = replyDto.getNickname();
        }
        if (replyDto.getBody() != null) {
            this.body = replyDto.getBody();
        }
    }

}
