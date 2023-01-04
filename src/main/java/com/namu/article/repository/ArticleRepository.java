package com.namu.article.repository;

import com.namu.article.domain.Article;
import com.namu.article.domain.Comment;
import com.namu.article.domain.Paging;
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

    public List<Article> getArticleList(Paging paging) {
        List<Article> list = articleMapper.getArticleList(paging);
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

    public int getTotalArticle() {
        int totalArticle = articleMapper.getTotalArticle();
        return totalArticle;
    }

    public List<Comment> getComments(int a_seq) {
        List<Comment> list = articleMapper.getComments(a_seq);
        return list;
    }

    public void deleteComments(int c_seq) {
        articleMapper.deleteComments(c_seq);
    }

    public void insertComments(Comment comment) {
        articleMapper.insertComments(comment);
    }

    public void updateArticleCount(int a_seq) {
        articleMapper.updateArticleCount(a_seq);
    }

    public void deleteFile(int a_seq) {
        articleMapper.deleteFile(a_seq);
    }
}
