use sakila;

use sys;

select * from sys_config;

select * from article;

create table t_comment(
	c_seq int auto_increment primary key,
    a_seq int,
    c_detail text not null,
    c_dtime datetime not null
);

alter table t_comment add constraint t_comment_a_seq_fk foreign key(a_seq)
references article(a_seq) on delete cascade;

select * from article order by a_seq desc;

insert into t_comment(a_seq, c_detail, c_dtime) values(81, '댓글 테스트', now());
insert into t_comment(a_seq, c_detail, c_dtime) values(81, '두 번째 댓글 테스트', now());
insert into t_comment(a_seq, c_detail, c_dtime) values(81, '세 번째 댓글 테스트', now());

select * from t_comment;
