$(document).ready(function() {

    var fontList = ['맑은 고딕','굴림','돋움','바탕','궁서','NotoSansKR','Arial','Courier New','Verdana','Tahoma','Times New Roamn'];
    $('#summernote').summernote({
        lang: 'ko-KR',
        height: 400,
        fontNames: fontList,
        disableResizeEditor: true,
        fontNamesIgnoreCheck: fontList,
        fontSizes: ['8','9','10','11','12','14','18','24','36'],
        toolbar: [
            ['font', ['fontname','fontsize','fontsizeunit']],
            ['fontstyle', ['bold','italic','underline','strikethrough','forecolor','backcolor','superscript','subscript','clear']],
            ['style', ['style']],
            ['paragraph', ['paragraph','height','ul','ol']],
            ['insert', ['table','hr','link','picture','video']],
            ['codeview'],
        ],
        callbacks: {	//여기 부분이 이미지를 첨부하는 부분
          					onImageUpload : function(files) {
          					    console.log(files);
          						uploadSummernoteImageFile(files[0],this);
          					},
          					onPaste: function (e) {
          						var clipboardData = e.originalEvent.clipboardData;
          						if (clipboardData && clipboardData.items && clipboardData.items.length) {
          							var item = clipboardData.items[0];
          							if (item.kind === 'file' && item.type.indexOf('image/') !== -1) {
          								e.preventDefault();
          							}
          						}
          					}
          				}
    });
      // $('#summernote').summernote('fontName', '맑은 고딕');
      // $('#summernote').summernote('fontSize', 11);
      // $('#summernote').summernote('fontSizeUnit', 'pt');



  	/**
  	* 이미지 파일 업로드
  	*/
  	function uploadSummernoteImageFile(file, editor) {
  		data = new FormData();
  		data.append("file", file);
  		$.ajax({
  			data : data,
  			type : "POST",
  			url : "/uploadSummernoteImageFile",
  			contentType : false,
  			processData : false,
  			success : function(data) {
              	//항상 업로드된 파일의 url이 있어야 한다.
  				$(editor).summernote('insertImage', data.url);
  			}
  		});
  	}

      $("div.note-editable").on('drop',function(e){
               for(i=0; i< e.originalEvent.dataTransfer.files.length; i++){
                  uploadSummernoteImageFile(e.originalEvent.dataTransfer.files[i],$("#summernote")[0]);
               }
              e.preventDefault();
         })

});

