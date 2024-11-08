package com.cb.sync;

import com.cb.repository.postgres.ArticleRepository;
import com.cb.search.repository.es.ArticleSearchRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BatchSyncService {
    private final ArticleRepository articleRepository;
    private final ArticleSearchRepository articleSearchRepository;

    public BatchSyncService(ArticleRepository articleRepository, ArticleSearchRepository articleSearchRepository) {
        this.articleRepository = articleRepository;
        this.articleSearchRepository = articleSearchRepository;
    }

    @Scheduled(fixedRate = 300000) // Every 5 minutes
    public void syncArticlesToElasticsearch() {
        var articles = articleRepository.findAll();
        articles.forEach(articleSearchRepository::save);
    }
}