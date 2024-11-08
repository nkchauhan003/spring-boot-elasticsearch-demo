package com.cb.rest;

import com.cb.model.Article;
import com.cb.repository.postgres.ArticleRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Article Postgres Rest Controller", description = "Article Postgres Rest Controller, to perform CRUD operations on Article entity")
@RestController
@RequestMapping("/api/postgres/articles")
public class ArticlePostgresRest {

    private final ArticleRepository articleRepository;

    public ArticlePostgresRest(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @PostMapping
    public Article createArticle(@RequestBody Article article) {
        return articleRepository.save(article);
    }

    @GetMapping("/{id}")
    public Article getArticleById(@PathVariable String id) {
        return articleRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Article not found with id: " + id));
    }

    @PutMapping("/{id}")
    public Article updateArticle(@PathVariable String id, @RequestBody Article articleDetails) {
        var article = articleRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Article not found with id: " + id));
        article.setTitle(articleDetails.getTitle());
        article.setContent(articleDetails.getContent());
        return articleRepository.save(article);
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable String id) {
        articleRepository.deleteById(id);
    }

    @GetMapping
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }
}