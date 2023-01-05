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

    // 페이지 정보를 넘겨 받지 않았을 때 띄울 메인페이지
    @RequestMapping
    public String main(Model model){
        String search=null;
        int total_article = articleService.getTotalArticle(search);
        Paging paging = new Paging(1,total_article);
        model.addAttribute(paging);
        List<Article> list = articleService.getArticleList(paging);
        model.addAttribute("articleList", list);
        return "articleList";
    }

    // 페이지 정보를 넘겨 받았을 때 띄울 메인페이지
    @RequestMapping("/{page}")
    public String main(Model model, @PathVariable int page,@RequestParam String search){
        System.out.println("search : "+search);
        int total_article = articleService.getTotalArticle(search);
        System.out.println("total_article : " + total_article);
        Paging paging = new Paging(page,total_article);
        paging.setSearch(search);
        model.addAttribute(paging);
        List<Article> list = articleService.getArticleList(paging);
        System.out.println("list : " + list.size());
        model.addAttribute("articleList", list);
        return "articleList";
    }

    //게시판 글 작성 페이지를 띄우기 위한 메서드
    @RequestMapping("articleForm")
    public String articleForm(){
        return "articleForm";
    }

    //게시판 글 작성 중 취소 버튼을 눌렀을 때 메인 페이지로 이동
    @RequestMapping("resetInsert")
    public String resetInsert(){
        return "redirect:/";
    }

    //썸머노트에서 이미지 드롭다운이나 복사 붙여넣기 이벤트 발생 시에 작동하는 콜백 함수
    @ResponseBody
    @RequestMapping("/uploadSummernoteImageFile")
    public JsonObject uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {

        JsonObject jsonObject = articleService.uploadSummernoteImageFile(multipartFile);

        return jsonObject;
    }

    //글쓰기 작성 완료 버튼 누를 때 작동하는 메서드
    @RequestMapping("/insertArticle")
    public String insertArticle(Article article, MultipartFile multipartFile) throws Exception{
        articleService.insertArticle(article, multipartFile);
        return "redirect:/";
    }

    //게시판 글 상세보기 페이지로 이동하는 메서드
    @RequestMapping("/article/{a_seq}")
    public String article(@PathVariable int a_seq, Model model){
        articleService.updateArticleCount(a_seq);
        Article article = articleService.getArticle(a_seq);
        model.addAttribute("article", article);
        return "article";
    }

    //게시글 수정 페이지로 이동하는 메서드
    @RequestMapping("/updateArticleForm/{a_seq}")
    public String updateArticleForm(@PathVariable int a_seq, Model model){
        Article article = articleService.getArticle(a_seq);
        model.addAttribute(article);
        return "articleUpdateForm";
    }

    // 게시글 수정 완료 시에 DB에 적용시키고 해당 게시글로 다시 이동
    @RequestMapping("/updateArticle")
    public String updateArticle(Article article, MultipartFile multipartFile) throws Exception{
        articleService.updateArticle(article, multipartFile);
        return "redirect:/article/"+article.getA_seq();
    }

    // 게시글 삭제 메서드
    @RequestMapping("/deleteArticle/{a_seq}")
    public String deleteArticle(@PathVariable int a_seq){
        articleService.deleteArticle(a_seq);
        return "redirect:/";
    }

    //파일 다운로드 할 때 작동하는 메서드
    @GetMapping("/download/{filename}")
    public void download(HttpServletResponse response, @PathVariable String filename) throws IOException {
        String path = "C:/Users/10/Desktop/article/src/main/resources/static/files/"+filename;

        byte[] fileByte = FileUtils.readFileToByteArray(new File(path));

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(filename, "UTF-8")+"\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        response.getOutputStream().write(fileByte);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    // 댓글 버튼 클릭 시에 댓글 목록을 가져오는 비동기 통신 메서드
    @RequestMapping("/getComments")
    @ResponseBody
    public  List<Comment> getComments(@RequestParam int a_seq, Model model){
        List<Comment> list = articleService.getComments(a_seq);
        return list;
    }

    //댓글 삭제 시에 작동하는 메서드
    @RequestMapping("/deleteComments")
    @ResponseBody
    public List<Comment> deleteComments(@RequestParam int c_seq, @RequestParam int a_seq, Model model){
        articleService.deleteComments(c_seq);
        List<Comment> list = articleService.getComments(a_seq);
        return list;
    }

    //댓글 작성 시에 작동하는 메서드
    @RequestMapping("/insertComments")
    @ResponseBody
    public List<Comment> insertComments(Comment comment){
        articleService.insertComments(comment);
        List<Comment> list = articleService.getComments(comment.getA_seq());
        return list;
    }

    //업로드 된 파일 삭제 메서드(비동기)
    @RequestMapping("/deleteFile")
    @ResponseBody
    public void deleteFile(@RequestParam int a_seq){
        System.out.println("deleteFile 실행 "+a_seq);
        articleService.deleteFile(a_seq);
    }
}
