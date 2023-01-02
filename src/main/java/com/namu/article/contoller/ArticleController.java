package com.namu.article.contoller;

import com.google.gson.JsonObject;
import com.namu.article.domain.Article;
import com.namu.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @RequestMapping
    public String main(Model model){
        List<Article> list = articleService.getArticleList();
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
    public String insertArticle(Article article){
        System.out.println(article);
        articleService.insertArticle(article);
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
    public String updateArticle(Article article){
        System.out.println(article);
        articleService.updateArticle(article);
        return "redirect:/article/"+article.getA_seq();
    }

    @RequestMapping("/deleteArticle/{a_seq}")
    public String deleteArticle(@PathVariable int a_seq){
        articleService.deleteArticle(a_seq);
        return "redirect:/";
    }
}
