package com.cb.search.repository.es;


import com.cb.model.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArticleSearchOperations {

    private final ElasticsearchOperations elasticsearchOperations;

    public ArticleSearchOperations(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public Article indexArticle(Article article) {
        return elasticsearchOperations.save(article); // Index article in Elasticsearch
    }

    public Article getArticleById(String articleId) {
        return elasticsearchOperations.get(articleId, Article.class); // Get article from Elasticsearch
    }

    public Article updateArticle(Article article) {
        return elasticsearchOperations.save(article); // Update article in Elasticsearch
    }

    public String removeArticleFromIndex(String articleId) {
        return elasticsearchOperations.delete(articleId, Article.class); // Delete article from Elasticsearch
    }

    public List<Article> searchArticlesCriteriaQuery(String q, Pageable pageable) {
        var criteria = new Criteria("title").fuzzy(q).or(new Criteria("content").fuzzy(q));
        var query = new CriteriaQuery(criteria);
        query.setPageable(pageable);
        return elasticsearchOperations.search(query, Article.class).getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .toList(); // Search articles using CriteriaQuery
    }

    public List<Article> searchArticlesNativeQuery(String q) {
        var query = NativeQuery.builder()
                .withQuery(n -> n
                        .multiMatch(m -> m
                                .fields("title", "content")
                                .query(q)
                        )
                )
                .build();
        return elasticsearchOperations.search(query, Article.class).getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .toList(); // Search articles using StringQuery
    }
}
