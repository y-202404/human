package com.example.firstproject.controller;

import com.example.firstproject.dao.mapper.ArticleMapper;
import com.example.firstproject.models.dto.ArticleForm;
import com.example.firstproject.models.dto.ReplyDto;
import com.example.firstproject.dao.repository.ArticleRepository;
import com.example.firstproject.models.vo.ArticleVO;
import com.example.firstproject.service.ReplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.firstproject.models.entity.Article;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private ArticleMapper articleMapper;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {

        log.info(form.toString()); // 로깅을 위한 코드

        Article article = form.toEntity();
        log.info(article.toString()); // 로깅을 위한 코드

        Article saved = articleRepository.save(article);
        log.info(saved.toString());

        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id);

        Article articleEntity = articleRepository.findById(id).orElse(null);

        List<ReplyDto> commentDtoList = replyService.replys(id);


        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtoList", commentDtoList);


        return "articles/show";
    }


    @GetMapping("/articles")
    public String index(Model model) {
        List<ArticleVO> articleVOList = articleMapper.getArticleList();
        List<Article> articleList = new ArrayList<>();
        for(ArticleVO articleVO : articleVOList) {
            articleList.add(articleVO.toEntity());
        }


        model.addAttribute("articleList", articleList);


        return "articles/index"; // templates/articles/index.mustache로 이동
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {

        Article articleEntity = articleRepository.findById(id).orElse(null);


        model.addAttribute("article", articleEntity);


        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        log.info(form.toString());


        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());


        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        if(target != null) {
            articleRepository.save(articleEntity);
        }


        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")    // 삭제 페이지로 이동
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다.");


        Article articleEntity = articleRepository.findById(id).orElse(null);
        log.info(articleEntity.toString());


        if (articleEntity != null) {

            articleRepository.delete(articleEntity);
            rttr.addFlashAttribute("msg", "삭제되었습니다.!!!");
        }


        return "redirect:/articles";
    }
}
