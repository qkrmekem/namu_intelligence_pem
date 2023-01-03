//댓글 가져오기 기능
$(document).ready(function() {
    //댓글 버튼 누르면 활성화
    $("#btn_comments").on("click", function(){
        var a_seq = $("#article_seq").val()
        console.log(a_seq);
        $.ajax({
            url : "/getComments",
            type : "post",
            data : {"a_seq":a_seq},
            dataType : "json",
            success : resultGetComments,
            error : function(e){console.log(e);}
        });
    })

    function resultGetComments(data){
        var html = "<div class='panel panel-default comment-box'>";
        html += "<div class='panel-heading'>Comments</div>";
        for(i=0; i<data.length; i++){
            console.log(data[i].c_detail);
            html+= "<div class='panel-body'>"+data[i].c_detail;
            html+= "<button id='btn_comment_delete' class='to-left' onclick='deleteComments("+data[i].c_seq+","+data[i].a_seq+")'>×</button>";
            html+= "</div>";
        }
        html+="</div>";
        $("#result_comments").html(html);
    }

    $("#btn_comment_insert").on("click",function(){
        var comment = $("#comment_form").serialize();
        $.ajax({
            url : "/insertComments",
            type : "post",
            data : comment,
            dataType : "json",
            success : reload,
            error : function(e){console.log(e);}
        });
    })
    function reload(data){
        $("#input_comment").val("");
        var html = "<div class='panel panel-default comment-box'>";
        html += "<div class='panel-heading'>Comments</div>";
        for(i=0; i<data.length; i++){
            console.log(data[i].c_detail);
            html+= "<div class='panel-body'>"+data[i].c_detail;
            html+= "<button id='btn_comment_delete' class='to-left' onclick='deleteComments("+data[i].c_seq+","+data[i].a_seq+")'>×</button>";
            html+= "</div>";
        }
        html+="</div>";
        $("#result_comments").html(html);
    }


});