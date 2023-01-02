package com.namu.article.repository;

import com.namu.article.domain.Article;
import com.namu.article.persistence.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleRepository {

    @Autowired
    ArticleMapper articleMapper;

    public void insertArticle(Article article){
        articleMapper.insertArticle(article);
    }

    public List<Article> getArticleList() {
        List<Article> list = articleMapper.getArticleList();
        return list;
    }

    public Article getArticle(int a_seq) {
        Article article = articleMapper.getArticle(a_seq);
        return article;
    }

    public void updateArticle(Article article) {
        articleMapper.updateArticle(article);
    }

    public void deleteArticle(int a_seq) {
        articleMapper.deleteArticle(a_seq);
    }
}