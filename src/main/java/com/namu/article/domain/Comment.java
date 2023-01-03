package com.namu.article.domain;

import lombok.Data;

@Data
public class Comment {
    private int c_seq;
    private int a_seq;
    private String c_detail;
    private String c_dtime;
}
