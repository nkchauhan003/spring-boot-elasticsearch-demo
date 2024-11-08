package com.cb.search.repository.es;

import com.cb.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleSearchRepository extends ElasticsearchRepository<Article, String> {
    @org.springframework.data.elasticsearch.annotations.Query("""
            {
              "multi_match": {
                "query": "?0",
                "fields": ["title", "content"]
              }
            }
            """)
    Page<Article> searchArticlesStringQuery(String q, Pageable pageable);
}
