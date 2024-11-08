package com.cb.listener;

import com.cb.model.Article;
import com.cb.search.repository.es.ArticleSearchRepository;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import org.springframework.stereotype.Component;

@Component
public class ArticleEntityListener {

    private final ArticleSearchRepository articleSearchRepository;


    public ArticleEntityListener(ArticleSearchRepository articleSearchRepository) {
        this.articleSearchRepository = articleSearchRepository;
    }

    @PostPersist
    @PostUpdate
    public void onPostPersistOrUpdate(Article article) {
        articleSearchRepository.save(article); // Update or insert in Elasticsearch
    }

    @PostRemove
    public void onPostRemove(Article article) {
        articleSearchRepository.deleteById(article.getId()); // Remove from Elasticsearch
    }
}
