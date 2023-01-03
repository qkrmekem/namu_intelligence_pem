package com.namu.article.contoller;

import com.google.gson.JsonObject;
import com.namu.article.domain.Article;
import com.namu.article.domain.Comment;
import com.namu.article.domain.Paging;
import com.namu.article.service.ArticleService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
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
        articleService.insertArticle(article, multipartFile);
        return "redirect:/";
    }

    @RequestMapping("/article/{a_seq}")
    public String article(@PathVariable int a_seq, Model model){
        articleService.updateArticleCount(a_seq);
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

    @RequestMapping("/deleteComments")
    @ResponseBody
    public List<Comment> deleteComments(@RequestParam int c_seq, @RequestParam int a_seq, Model model){
        articleService.deleteComments(c_seq);
        List<Comment> list = articleService.getComments(a_seq);
        return list;
    }

    @RequestMapping("/insertComments")
    @ResponseBody
    public List<Comment> insertComments(Comment comment){
        articleService.insertComments(comment);
        List<Comment> list = articleService.getComments(comment.getA_seq());
        return list;
    }
}
