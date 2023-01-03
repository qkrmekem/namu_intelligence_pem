$(document).ready(function() {
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
        html += "<div class='panel-heading'>Panel Heading</div>";
        for(i=0; i<data.length; i++){
            console.log(data[i].c_detail);
            html+= "<div class='panel-body'>"+data[i].c_detail+"</div>";
        }
        html+="</div>";
        $("#result_comments").html(html);
    }


});