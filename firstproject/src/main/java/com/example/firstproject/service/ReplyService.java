package com.example.firstproject.service;

import com.example.firstproject.models.dto.ReplyDto;
import com.example.firstproject.models.entity.Article;
import com.example.firstproject.models.entity.Reply;
import com.example.firstproject.dao.repository.ArticleRepository;
import com.example.firstproject.dao.repository.ReplyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReplyService {
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private ArticleRepository articleRepository;

    // 1. 댓글 리스트 조회
    public List<ReplyDto> replys(Long articleId) {


        return replyRepository.findByArticleId(articleId)
                .stream()
                .map(Reply -> ReplyDto.createReplyDto(Reply))
                .collect(Collectors.toList())
                ;
    }


    @Transactional
    public ReplyDto create(Long articleId, ReplyDto replyDto) {

        Article article = articleRepository.findById(articleId).orElseThrow(
                () -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다.")
        );


        Reply reply = Reply.createReply(replyDto, article);


        Reply created = replyRepository.save(reply);


        return ReplyDto.createReplyDto(created);
    }


    @Transactional
    public ReplyDto update(Long id, ReplyDto ReplyDto) {

        Reply target = replyRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다.")
        );


        target.patch(ReplyDto);


        Reply updated = replyRepository.save(target);


        return ReplyDto.createReplyDto(updated);
    }


    @Transactional
    public ReplyDto delete(Long id) {

        Reply target = replyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! " +
                        "대상이 없습니다."));


        replyRepository.delete(target);


        return ReplyDto.createReplyDto(target);
    }
}
