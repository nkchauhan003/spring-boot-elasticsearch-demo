package com.cb.rest;

import com.cb.model.Article;
import com.cb.search.repository.es.ArticleSearchRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Article Search Repository Rest Controller", description = "Article Search Repository Rest Controller, to perform operations on Article entity in Elasticsearch")
@RestController
@RequestMapping("/api/articles/repo")
public class ArticleSearchRepositoryRest {

    private final ArticleSearchRepository articleSearchRepository;

    public ArticleSearchRepositoryRest(ArticleSearchRepository articleSearchRepository) {
        this.articleSearchRepository = articleSearchRepository;
    }

    @PostMapping
    public Article indexArticle(@RequestBody Article article) {
        return articleSearchRepository.save(article);
    }

    @GetMapping("/{id}")
    public Article getArticleById(@PathVariable String id) {
        return articleSearchRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Article not found with id: " + id));
    }

    @PutMapping
    public Article updateArticle(@RequestBody Article article) {
        return articleSearchRepository.save(article);
    }

    @DeleteMapping("/{id}")
    public void removeArticleFromIndex(@PathVariable String id) {
        articleSearchRepository.deleteById(id);
    }

    @GetMapping("/search/string")
    public List<Article> searchArticlesStringQuery(@RequestParam String q) {
        return articleSearchRepository.searchArticlesStringQuery(q, PageRequest.of(0, 10, Sort.Direction.ASC, "title")).getContent();
    }

}
