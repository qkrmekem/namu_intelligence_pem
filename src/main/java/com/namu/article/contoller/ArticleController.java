package com.namu.article.contoller;

import com.google.gson.JsonObject;
import com.namu.article.domain.Article;
import com.namu.article.domain.Comment;
import com.namu.article.domain.Paging;
import com.namu.article.service.ArticleService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @RequestMapping
    public String main(Model model){
        int total_article = articleService.getTotalArticle();
        Paging paging = new Paging(1,total_article);
        model.addAttribute(paging);
        List<Article> list = articleService.getArticleList(paging);
        model.addAttribute("articleList", list);
        return "articleList";
    }

    @RequestMapping("/{page}")
    public String main(Model model, @PathVariable int page){
        int total_article = articleService.getTotalArticle();
        Paging paging = new Paging(page,total_article);
        model.addAttribute(paging);
        System.out.println(paging);
        List<Article> list = articleService.getArticleList(paging);
        model.addAttribute("articleList", list);
        return "articleList";
    }

    @RequestMapping("articleForm")
    public String articleForm(){
        return "articleForm";
    }

    @RequestMapping("resetInsert")
    public String resetInsert(){
        return "redirect:/";
    }

    @ResponseBody
    @RequestMapping("/uploadSummernoteImageFile")
    public JsonObject uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {

        JsonObject jsonObject = articleService.uploadSummernoteImageFile(multipartFile);

        return jsonObject;
    }

    @RequestMapping("/insertArticle")
    public String insertArticle(Article article, MultipartFile multipartFile) throws Exception{
        System.out.println("멀티파트파일"+multipartFile);
        articleService.insertArticle(article, multipartFile);
        System.out.println("입력완료!");
        return "redirect:/";
    }

    @RequestMapping("/article/{a_seq}")
    public String article(@PathVariable int a_seq, Model model){
        Article article = articleService.getArticle(a_seq);
        model.addAttribute("article", article);
        return "article";
    }

    @RequestMapping("/updateArticleForm/{a_seq}")
    public String updateArticleForm(@PathVariable int a_seq, Model model){
        Article article = articleService.getArticle(a_seq);
        model.addAttribute(article);
        return "articleUpdateForm";
    }

    @RequestMapping("/updateArticle")
    public String updateArticle(Article article, MultipartFile multipartFile) throws Exception{
        System.out.println(article);
        articleService.updateArticle(article, multipartFile);
        return "redirect:/article/"+article.getA_seq();
    }

    @RequestMapping("/deleteArticle/{a_seq}")
    public String deleteArticle(@PathVariable int a_seq){
        articleService.deleteArticle(a_seq);
        return "redirect:/";
    }
//
//    ResourceLoader resourceLoader;
//
//    @Autowired
//    public void FileController (ResourceLoader resourceLoader) {
//        this.resourceLoader = resourceLoader;
//    }


    @GetMapping("/download/{filename}")
    public void download(HttpServletResponse response, @PathVariable String filename) throws IOException {
        System.out.println("여기까지 옴");
//        String path = "C:/Users/superpil/OneDrive/바탕 화면/file/tistory.PNG";
        String path = "C:/Users/10/Desktop/article/src/main/resources/static/files/"+filename;

        byte[] fileByte = FileUtils.readFileToByteArray(new File(path));

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(filename, "UTF-8")+"\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        response.getOutputStream().write(fileByte);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    @RequestMapping("/getComments")
    @ResponseBody
    public  List<Comment> getComments(@RequestParam int a_seq, Model model){
        List<Comment> list = articleService.getComments(a_seq);
        return list;
    }

}
