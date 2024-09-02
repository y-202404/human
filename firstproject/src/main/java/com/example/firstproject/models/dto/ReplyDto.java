package com.example.firstproject.models.dto;

import com.example.firstproject.models.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
//@Setter
@ToString
public class ReplyDto {
    private Long id;            // 댓글 번호
    private Long articleId;     // 댓글이 달린 게시글(부모) 번호
    private String nickname;    // 댓글 작성자 닉네임
    private String body;        // 댓글 본문

    public static ReplyDto createReplyDto(Reply reply) {
        return new ReplyDto(
                reply.getId(),    // 댓글 실제 번호
                reply.getArticle().getId(),   // 댓글 엔티티의 부모 게시글 id
                reply.getNickname(),  // 댓글 작성자 닉네임
                reply.getBody()       // 댓글 본문
        );
    }
}
