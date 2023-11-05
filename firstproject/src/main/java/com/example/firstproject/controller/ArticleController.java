package com.example.firstproject.controller;
import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
@Slf4j
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;


    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        //System.out.println(form.toString());
        log.info(form.toString());

        Article article =form.toEntity();
        //System.out.println(article.toString());
        log.info(article.toString());

        Article saved = articleRepository.save(article);
        //System.out.println(saved.toString());
        log.info(saved.toString());

        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id);

        // 1: id로 데이터를 가져옴!
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 2: 가져온 데이터를 모델에 등록!
        model.addAttribute("article", articleEntity);

        // 3: 보여줄 페이지를 설정!
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        // 1: 모든 Article을 가져온다!
        List<Article> articleEntityList = articleRepository.findAll();

        // 2: 가져온 Article 묶음을 뷰로 전달!
        model.addAttribute("articleList", articleEntityList);
        // 3: 뷰 페이지를 설정!

        return "articles/index";
    }

}