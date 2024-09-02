package com.example.firstproject.controller.api;

import com.example.firstproject.models.dto.ReplyDto;
import com.example.firstproject.service.ReplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class ReplyApiController {
    @Autowired
    private ReplyService replyService;


    @GetMapping("/api/articles/{articleId}/replys")
    public ResponseEntity<List<ReplyDto>> replys(
            @PathVariable Long articleId
    ) {

        List<ReplyDto> replyDtoList = replyService.replys(articleId);

        return ResponseEntity.status(HttpStatus.OK).body(replyDtoList);
    }


    @PostMapping("/api/articles/{articleId}/replys")
    public ResponseEntity<ReplyDto> create(
            @PathVariable Long articleId,
            @RequestBody ReplyDto replyDto) {

        ReplyDto createdDto = replyService.create(articleId, replyDto);

        return ResponseEntity.status(HttpStatus.OK).body(createdDto);
    }


    @PatchMapping("/api/replys/{id}")
    public ResponseEntity<ReplyDto> update(
            @PathVariable Long id,
            @RequestBody ReplyDto replyDto
    ) {

        ReplyDto updatedDto = replyService.update(id, replyDto);

        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }


    @DeleteMapping("/api/replys/{id}")
    public ResponseEntity<ReplyDto> delete(@PathVariable Long id) {

        ReplyDto deletedDto = replyService.delete(id);


        return ResponseEntity.status(HttpStatus.OK).body(deletedDto);
    }
}
