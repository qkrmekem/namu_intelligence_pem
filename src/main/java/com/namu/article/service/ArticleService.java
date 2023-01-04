package com.namu.article.service;

import com.google.gson.JsonObject;
import com.namu.article.domain.Article;
import com.namu.article.domain.Comment;
import com.namu.article.domain.Paging;
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

    public void insertArticle(Article article, MultipartFile multipartFile) throws Exception {

        int ext = multipartFile.getOriginalFilename().indexOf(".");
        //업로드 된 파일이 있는지 확인
        if (ext>0){
            //파일 저장 경로
            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
            //파일 랜덤 이름 부여
            UUID uuid = UUID.randomUUID();

            String fileName = uuid + "_" + multipartFile.getOriginalFilename();

            String extension = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));


            File saveFile = new File(projectPath, fileName);

            multipartFile.transferTo(saveFile);

            article.setA_filename(fileName);
            //static 밑에 있는 파일들은 static 아래의 경로만 적으면 접근 가능
            article.setA_filepath("/files/"+fileName);
        }

        articleRepository.insertArticle(article);
    }

    public List<Article> getArticleList(Paging paging) {
        List<Article> list = articleRepository.getArticleList(paging);
        return list;
    }

    public Article getArticle(int a_seq) {
        Article article =  articleRepository.getArticle(a_seq);
        return article;
    }

    public void updateArticle(Article article, MultipartFile multipartFile) throws Exception  {
        int ext = multipartFile.getOriginalFilename().indexOf(".");
        //업로드 된 파일이 있는지 확인
        if (ext>0){
            //파일 저장 경로
            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
            //파일 랜덤 이름 부여
            UUID uuid = UUID.randomUUID();

            String fileName = uuid + "_" + multipartFile.getOriginalFilename();

            String extension = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));


            File saveFile = new File(projectPath, fileName);

            multipartFile.transferTo(saveFile);

            article.setA_filename(fileName);
            //static 밑에 있는 파일들은 static 아래의 경로만 적으면 접근 가능
            article.setA_filepath("/files/"+fileName);
        }
        articleRepository.updateArticle(article);
    }

    public void deleteArticle(int a_seq) {
        articleRepository.deleteArticle(a_seq);
    }

    public int getTotalArticle() {
        int totalArticle = articleRepository.getTotalArticle();
        return totalArticle;
    }

    public List<Comment> getComments(int a_seq) {
        List<Comment> list = articleRepository.getComments(a_seq);
        return list;
    }

    public void deleteComments(int c_seq) {
        articleRepository.deleteComments(c_seq);
    }

    public void insertComments(Comment comment) {
        articleRepository.insertComments(comment);
    }

    public void updateArticleCount(int a_seq) {
        articleRepository.updateArticleCount(a_seq);
    }

    public void deleteFile(int a_seq) {
        articleRepository.deleteFile(a_seq);
    }
}
