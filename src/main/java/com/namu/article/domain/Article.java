package com.namu.article.domain;

import lombok.Data;

@Data
public class Article {

    private int a_seq;
    private String a_title;
    private String a_detail;
    private int a_count;
    private String a_dtime;

    private String a_filename;

    private String a_filepath;

    public void Article(){
        this.a_count=0;
    }
}
