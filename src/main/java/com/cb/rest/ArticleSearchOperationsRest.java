package com.cb.rest;


import com.cb.model.Article;
import com.cb.search.repository.es.ArticleSearchOperations;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Article Search Operations Rest Controller", description = "Article Search Operations Rest Controller, to perform operations on Article entity in Elasticsearch")
@RestController
@RequestMapping("/api/articles/op")
public class ArticleSearchOperationsRest {

    private final ArticleSearchOperations articleSearchOperations;

    public ArticleSearchOperationsRest(ArticleSearchOperations articleSearchOperations) {
        this.articleSearchOperations = articleSearchOperations;
    }

    @PostMapping
    public Article indexArticle(@RequestBody Article article) {
        return articleSearchOperations.indexArticle(article);
    }

    @GetMapping("/{id}")
    public Article getArticleById(@PathVariable String id) {
        return articleSearchOperations.getArticleById(id);
    }

    @PutMapping
    public Article updateArticle(@RequestBody Article article) {
        return articleSearchOperations.updateArticle(article);
    }

    @DeleteMapping("/{id}")
    public String removeArticleFromIndex(@PathVariable String id) {
        return articleSearchOperations.removeArticleFromIndex(id);
    }

    @GetMapping("/search/criteria")
    public List<Article> searchArticlesCriteriaQuery(@RequestParam String q) {
        return articleSearchOperations.searchArticlesCriteriaQuery(q);
    }

    @GetMapping("/search/native")
    public List<Article> searchArticlesNativeQuery(@RequestParam String q) {
        return articleSearchOperations.searchArticlesNativeQuery(q);
    }
}
