package com.namu.article.service;

import com.google.gson.JsonObject;
import com.namu.article.domain.Article;
import com.namu.article.repository.ArticleRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    public JsonObject uploadSummernoteImageFile(MultipartFile multipartFile) {
        JsonObject jsonObject = new JsonObject();

        String fileRoot = "C:\\summernote_image\\";	//저장될 외부 파일 경로
        String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자

        String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명

        File targetFile = new File(fileRoot + savedFileName);

        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장
            jsonObject.addProperty("url", "/summernoteImage/"+savedFileName);
            jsonObject.addProperty("responseCode", "success");

        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile);	//저장된 파일 삭제
            jsonObject.addProperty("responseCode", "error");
            e.printStackTrace();
        }

        return jsonObject;
    }

    public void insertArticle(Article article) {
        articleRepository.insertArticle(article);
    }

    public List<Article> getArticleList() {
        List<Article> list = articleRepository.getArticleList();
        return list;
    }

    public Article getArticle(int a_seq) {
        Article article =  articleRepository.getArticle(a_seq);
        return article;
    }

    public void updateArticle(Article article) {
        articleRepository.updateArticle(article);
    }

    public void deleteArticle(int a_seq) {
        articleRepository.deleteArticle(a_seq);
    }
}
