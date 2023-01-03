package com.namu.article.persistence;

import com.namu.article.domain.Article;
import com.namu.article.domain.Comment;
import com.namu.article.domain.Paging;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {
    public void insertArticle(Article article);

    public List<Article> getArticleList(Paging paging);

    public Article getArticle(int a_seq);

    public void updateArticle(Article article);

    public void deleteArticle(int a_seq);

    public int getTotalArticle();

    public List<Comment> getComments(int a_seq);

    public void deleteComments(int c_seq);

    public void insertComments(Comment comment);

    public void updateArticleCount(int a_seq);
}
