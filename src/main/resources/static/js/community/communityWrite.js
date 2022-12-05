//첫번째 셀렉트 박스의 리스트들 의 모임
const $postFilterLi = $(".post_filter li");
//다이어리 필터
const $diaryFilter = $(".diary_filter_1h");
//다이어리 필터안 리스트들의 모임
const $diaryFilterLi = $(".diary_filter_1h li");
//교환 필터
const $exchangeFilter = $(".exchange_filter_1h");
//교환 필터안 리스트들의 모임
const $exchangeFilterLi = $(".exchange_filter_1h li");

let text ="";

// editor js에 작성된 글들 저장하는 끔찍한 방법
$(".Button-bqxlp0-0.fFBpBV").click(function(){
    for(let i = 0; i<$(".cdx-block").length;i++){
        text += "<p>" + $(".cdx-block").eq(i).html()+"</p>";
    }
    console.log(text);
    $(".cdx-block").html(text);
})


$postFilterLi.click(function () {
    var text = $(this).text();
    $(this).parent().find(".post_filter_input").val(text);
    $(this).parent().parent().removeClass('active');
    $(this).parent().prev().text(text);

    if(text=='일기'){
        $diaryFilter.show()
    }else{
        $diaryFilter.hide()
        $exchangeFilter.hide();
        $(".diary_filter_1h").find(".label").text("공개")
        $(".diary_filter_1h").find(".diary_filter_input").val("공개")
    }
})


$diaryFilterLi.click(function(){
    var text = $(this).text();
    $(this).parent().find(".diary_filter_input").val(text);
    $(this).parent().parent().removeClass('active');
    $(this).parent().prev().text(text);
    if(text=='비공개'){
        $exchangeFilter.show();
    }else{
        $exchangeFilter.hide();
    }
})


$exchangeFilterLi.click(function () {
    var text = $(this).text();
    $(this).parent().find(".exchange_filter_input").val(text);
    $(this).parent().parent().removeClass('active');
    $(this).parent().prev().text(text);

})

checkMedia()
$(window).resize(function(){
    if(window.innerWidth<650){
      $(".registerBtn_1h").hide()
        $(".until650px").show()
    } else{
        $(".registerBtn_1h").show()
        $(".until650px").hide()
    }
})

function checkMedia(){

    if(window.innerWidth<650){
        $(".registerBtn_1h").hide()
        $(".until650px").show()
    } else{
        $(".registerBtn_1h").show()
        $(".until650px").hide()
    }
}


/*썸머노트*/
// // 서머노트에 text 쓰기
// $('#summernote').summernote('insertText', 써머노트에 쓸 텍스트);

$(document).ready(function() {
    function sendFile(file){
        var data = new FormData();
        data.append("file",file);
        $.ajax({
            url: "/host/summernote/",
            type: "POST",
            enctype: 'multipart/form-data',
            data: data,
            cache: false,
            contentType : false,
            processData : false,
            success: function(image){
                $('#summernote').summernote('insertImage',image);
            },
            error: function(e){console.log(e);}
        });
    }

    // summernote
    $('#summernote').summernote({
        height :500,
        minHeight:null,
        maxHeight:1000,
        toolbar: [
            ['fontname', ['fontname']],
            ['fontsize', ['fontsize']],
            ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
            ['color', ['forecolor','color']],
            ['table', ['table']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['height', ['height']],
            ['insert',['picture']],
        ],
        fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋움체','바탕체'],
        fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72'],
        focus:true,
        lang : "ko-KR",
        callbacks: {
            onImageUpload : function(files){
                sendFile(files[0]);
            }
        }
    });

    /*$('#summernote').summernote('insertText', $('input[name=groupContent]').val());*/
    $(".note-editable").html($('input[name=groupContent]').val());

    $('#summernote').on('summernote.change', function(we, contents, $editable) {
        $('input[name=groupContent]').attr('value', $(".note-editable").html());
    });
}); //ready
