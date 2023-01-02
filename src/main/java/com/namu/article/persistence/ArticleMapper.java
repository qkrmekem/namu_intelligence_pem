package com.namu.article.persistence;

import com.namu.article.domain.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {
    public void insertArticle(Article article);

    public List<Article> getArticleList();

    public Article getArticle(int a_seq);

    public void updateArticle(Article article);

    public void deleteArticle(int a_seq);
}
