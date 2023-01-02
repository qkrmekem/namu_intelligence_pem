package com.namu.article.domain;

import lombok.Data;

@Data
public class Paging {

    private int total_page_count;
    private int page_per_article;
    private int total_article;
    private int page;
    private int start_page;
    private int end_page;
    private int start_article;
    private int end_article;


    public Paging(int page, int total_article) {
        super();
        this.page_per_article = 10;
        this.page = page;
        this.total_article = total_article;
        this.total_page_count = total_article/page_per_article;
        if(total_article%page_per_article != 0) {
            total_page_count++;
        }
        //5개 미만일때
        if(total_page_count<5) {
            end_page = total_page_count;
            start_page=1;
            //페이지가 최대페이지 -1일때
        }else if(page>=total_page_count-1) {
            end_page = total_page_count;
            start_page = total_page_count-4;
            //페이지가 5개 이상인데 현재 페이지가 2보다 작거나 같을 때
        }else if(page<=2) {
            end_page = 5;
            start_page = 1;
            //페이지가 5개 이상이고 현재 페이지가 3이상이고 최대페이지 -2미만일 때
        }else {
            start_page = page-2;
            end_page = page+2;
        }
        this.start_article = (page-1)*page_per_article;
        this.end_article = page*page_per_article > total_article? total_article : page*page_per_article;
    }
}
