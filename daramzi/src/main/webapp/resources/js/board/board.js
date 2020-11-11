    
	//검색 버튼
	function check_search() {
        if ($("input[name=keyWord]", "form[name=search]").val() == "") {
            alert("검색어를 입력하세요.");
            $("input[name=keyWord]", "form[name=search]").focus();
            return;
        }
        
        $('form[name="search"]').submit();
    }
    
    //드롭리스트 검색
    function listCnt_search() {
    	$('form[name="search"]').submit();
    }
    
    //페이지 이동
    function pagemove(i) {
        $("input[name=page]", "form[name=search]").val(Number(i));
        $('form[name="search"]').submit();
    }
    
    //게시판 글 보기
    function read(boardIdx) {
        $("input[name=boardIdx]", "form[name=read]").val(boardIdx);
        $('form[name="read"]').submit();
        
    }
    
    //게시판 글 수정
    function mod_Content(boardIdx) {
    	$('form[name="modContent"]').attr("action","/board/boardUpdate");
    	$("input[name=boardIdx]", "form[name=modContent]").val(boardIdx);
    	$('form[name="modContent"]').submit();
    }
    
    //게시판 글삭제
    function delContent(boardIdx) {
    	$('form[name="modContent"]').attr("action","/board/boardDelete");
    	$("input[name=boardIdx]", "form[name=modContent]").val(boardIdx);
    	$('form[name="modContent"]').submit();
    }

    //글쓰기
    function go_write(loginId) {
    	if(loginId == null){
    		alert("글쓰기는 로그인 후 가능합니다.");
    		return 0;
    	}
    	
    	location.href="/board/boardWrite";
    }    
    
    //목록으로 돌아가기
    function list() {
    	$('form[name="search"]').submit();
    }
    
    //게시글추천
    function fn_recommend(boardIdx,recommendYN) {
        $.ajax({
	        type: "post",
	        url : "/board/boardRecommend",
	        data: {
	        	boardIdx : boardIdx,
	        	recommendYN : recommendYN
	        },
	        contentType : "application/x-www-form-urlencoded; charset=utf-8",

	        success:function (data) {//추천성공
	        	if(data.recommendYN == "Y"){
	        		alert("게시글을 추천하였습니다.");
	        		$("#"+ data.boardIdx + "_recommendN").css('background','');
	        		$("#"+ data.boardIdx + "_recommendY").css('background','red');
	        		
	        		$("#"+ data.boardIdx + "_recommendY").val('좋아요 ' + data.recommendYcnt);
	        		$("#"+ data.boardIdx + "_recommendN").val('싫어요 ' + data.recommendNcnt);
	        	}else{
	        		alert("게시글을 비추천하였습니다.");
	        		$("#"+ data.boardIdx + "_recommendY").css('background','');
	        		$("#"+ data.boardIdx + "_recommendN").css('background','red');
	        		
	        		$("#"+ data.boardIdx + "_recommendY").val('좋아요 ' + data.recommendYcnt);
	        		$("#"+ data.boardIdx + "_recommendN").val('싫어요 ' + data.recommendNcnt);
	        	}
            },

	        error: function (data) {//추천실패
	        	alert("기능 동작이 안됐습니다.관리자에게 문의 바랍니다.");
            }

     	});
    } 
    
    //게시글추천
    function fn_replyReg() {
    	// submit 등록. 실제로 submit type은 아니다.
		var form = $('#replyForm')[0];
		var formData = new FormData(form);
		
		//ajax 통신으로 multipart form을 전송한다.
		$.ajax({
			type : 'POST',
			enctype : 'multipart/form-data',
			processData : false,
			contentType : false,
			cache : false,
			timeout : 600000,
			url : '/board/replyReg',
			dataType : 'JSON',
			data : formData,
			success : function(result) {
				alert('답변이 등록되었습니다.');
				location.reload();
			}
		//전송실패에대한 핸들링은 고려하지 않음
		});
    } 
    
    //게시글추천
    function fn_replyReReg(upperIdx) {
    	
    	$("#upperReplyIdx").val(upperIdx);
    	// submit 등록. 실제로 submit type은 아니다.
		var form = $('#replyList')[0];
		var formData = new FormData(form);
		
		//ajax 통신으로 multipart form을 전송한다.
		$.ajax({
			type : 'POST',
			enctype : 'multipart/form-data',
			processData : false,
			contentType : false,
			cache : false,
			timeout : 600000,
			url : '/board/replyReg',
			dataType : 'JSON',
			data : formData,
			success : function(result) {
				alert('답변이 등록되었습니다.');
				location.reload();
			}
		//전송실패에대한 핸들링은 고려하지 않음
		});
    } 
    
    function fn_replyReShow(upperIdx) {
    	
        var str = '<TR>';
        str += '<td>';
    	str += '<textarea name="replyCont" id="replyCont" rows="2" cols="50" ></textarea>';
    	str += '<input type="button" value="답변등록" onClick="fn_replyReReg('+upperIdx+')">'
        str += '</td>';
        str += '</TR>';
        
       $("#" + upperIdx).after(str); 
    }

    //엑셀 다운로드
    function doExcelDownloadProcess(mappingValue){
        var f = document.excelDown;
        f.action = mappingValue;
        f.submit();
    }
    
    //이건 모하는 기능이야
    function blockmovef() {
        document.blockmovef.submit();
    }
    
    //이건 모하는 기능이야
    function blockMoveb() {
        console.log("##" + document.blockmoveb.nowBlock.value);
        document.blockmoveb.submit();
    }
    ///////////////////////////////////////////////////////////////////////////////////    
    /*
    var xmlHttp;
 
    var xmlDoc;
 
    var message;
 
    function contentprev(seq) {
 
        var url = "boardAjax.action?seq=" + seq; //미리보기 서블릿 호출
 
        xmlHttp = createXMLHttpRequest();
 
        xmlHttp.onreadystatechange = handleStateChange;
 
        xmlHttp.open("get", url, true);
 
        xmlHttp.send(null);
 
    }
 
    function handleStateChange() {
 
        if (xmlHttp.readyState == 4) {
            if (xmlHttp.status == 200) {
                xmlDoc = xmlHttp.responseText;
                document.getElementById("layer1").innerHTML = xmlDoc;
            }
 
        }
 
    }
 
    function showlayer(id) {
        if (document.all[id]) {
            document.all[id].style.visibility = "visible";
        } else if (document.layers[id]) {
            document.layers[id].style.visibility = "visible";
        }
 
    }
 
    function hidelayer(id) {
        if (document.all[id])
            document.all[id].style.visibility = "hidden";
 
        else if (document.layers[id])
            document.layers[id].style.visibility = "hidden";
 
    }
 
    function movetip() {
        layer1.style.marginTop = event.y + document.body.scrollTop + 10 +"px";
        
        layer1.style.marginLeft = event.x + document.body.scrollLeft + 10 +"px";
    }
 */
//    document.onmousemove = movetip;