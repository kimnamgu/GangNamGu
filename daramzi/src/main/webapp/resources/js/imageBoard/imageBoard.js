	// 전체선택해제
	$(document).ready(function(){
	    //체크박스 전체 선탣&해제
	    $('#ck_all').click(function(){
	         if($("#ck_all").prop("checked")){
	            $("input[type=checkbox]").prop("checked",true); 
	        }else{
	            $("input[type=checkbox]").prop("checked",false); 
	        }
	    });
	});

	//선택파일삭제 ajax 로 변경
	function go_chkDel() {
		  var checkedValue = [];
		  $("input[name='idxArr']:checked").each(function(index, item){
			  checkedValue.push($(item).val());   
			  });
		  
        $.ajax({
	        type: "post",
	        url : "/imageboard/fileChk",
	        data: {
	        	sendStyle : 'del',
	        	idxArr	: checkedValue
	        },

	        success:function (data) {//사진삭제 - 성공시
	        	
	        	var objectValues = data;
	        	for (var key in objectValues){
	        		$("#" + objectValues[key]).empty();
	        	}
	        	alert("사진을 삭제하였습니다.");
            },

	        error: function (data) {//사진삭제 - 실패시
	        	alert("사진을 삭제하지 못했습니다.관리자에게 문의 바랍니다.");
            }

     	});
    }
	
    //사진삭제
    function go_fileDel(fileIdx) {
        $.ajax({
	        type: "post",
	        url : "/imageboard/fileDel",
	        data: {
	        	fileIdx : fileIdx
	        },

	        success:function (data) {//사진삭제 - 성공시
	        	alert("사진을 삭제하였습니다.");
	        	$("#" + fileIdx).empty();
            },

	        error: function (data) {//사진삭제 - 실패시
	        	alert("사진을 삭제하지 못했습니다.관리자에게 문의 바랍니다.");
            }

     	});
    }
    
    //파일추천
    function fn_recommend(fileIdx,recommendYN) {
        $.ajax({
	        type: "post",
	        url : "/imageboard/fileRecommend",
	        data: {
	        	fileIdx : fileIdx,
	        	recommendYN : recommendYN
	        },
	        contentType : "application/x-www-form-urlencoded; charset=utf-8",

	        success:function (data) {//추천성공
	        	if(data.recommendYN == "Y"){
	        		alert("사진을 추천하였습니다.");
	        		$("#"+ data.fileIdx + "_recommendN").css('background','');
	        		$("#"+ data.fileIdx + "_recommendY").css('background','red');
	        		
	        		$("#"+ data.fileIdx + "_recommendY").html('좋아요 ' + data.recommendYcnt);
	        		$("#"+ data.fileIdx + "_recommendN").html('싫어요 ' + data.recommendNcnt);
	        	}else{
	        		alert("사진을 비추천하였습니다.");
	        		$("#"+ data.fileIdx + "_recommendY").css('background','');
	        		$("#"+ data.fileIdx + "_recommendN").css('background','red');
	        		
	        		$("#"+ data.fileIdx + "_recommendY").html('좋아요 ' + data.recommendYcnt);
	        		$("#"+ data.fileIdx + "_recommendN").html('싫어요 ' + data.recommendNcnt);
	        	}
	        	
            },

	        error: function (data) {//추천실패
	        	alert("기능 동작이 안됐습니다.관리자에게 문의 바랍니다.");
            }

     	});
    }
    
	//선택파일다운로드
	function go_chkDown() {

		var agent = navigator.userAgent.toLowerCase(); 

		if ($('input[name="idxArr"]:checked').length > 2 && agent.indexOf("chrome") == -1) {
			alert("두개이상의 파일 다운로드는 크롬에서만 가능합니다.");
			return false;
		}
		
		var iFrameCnt = 0;
        $('input[name="idxArr"]:checked').each(function(index, item){ //img 태그중 ImageList 명으로 시작하는 요소를 가져옴
            var url = "/imageboard/fileDown?fileIdx="+ $(item).val(); //다운로드 받는 경로 와 변수
            fnCreateIframe(iFrameCnt); // 보이지 않는 iframe 생성, name는 숫자로
            $("iframe[name=" + iFrameCnt + "]").attr("src", url);
            iFrameCnt++;
            //fnSleep(1000); //각 파일별 시간 텀을 준다
        });
        
    }
	
	
    function fnSleep(delay){
        var start = new Date().getTime();
        while (start + delay > new Date().getTime());
    }
	
    function fnCreateIframe(name){
        var frm = $('<iframe name="' + name + '" style="display: none;"></iframe>');
        frm.appendTo("body");
    }

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
    
    //드롭리스트 검색
    function viewStyle_search() {
    	$('form[name="search"]').submit();
    }
    
    //페이지 이동
    function pagemove(i) {
        $("input[name=page]", "form[name=search]").val(Number(i));
        $('form[name="search"]').submit();
    }
    
    //게시판 글 보기
    function read(seq) {
        $("input[name=seq]", "form[name=read]").val(seq);
        $('form[name="read"]').submit();
        
    }
    
    //게시판 글 수정
    function modContent(seq) {
    	$('form[name="modContent"]').attr("action","/board/boardUpdate");
    	$("input[name=seq]", "form[name=modContent]").val(seq);
    	$('form[name="modContent"]').submit();
    }
    
    //게시판 글삭제
    function delContent(seq) {
    	$('form[name="modContent"]').attr("action","/board/boardDelete");
    	$("input[name=seq]", "form[name=modContent]").val(seq);
    	$('form[name="modContent"]').submit();
    }

    //사진업로드 화면
    function go_upload(loginId) {
    	//개발편의를 위해 우선 막음
    	if(loginId == null){
    		alert("사진업로드는 로그인 후 가능합니다.");
    		return 0;
    	}
    	
    	location.href="/imageboard/fileUploadForm";
    }
    
    //파일관리 화면
    function go_myFile(loginId) {
    	//개발편의를 위해 우선 막음
    	if(loginId == null){
    		alert("파일관리는 로그인 후 가능합니다.");
    		return 0;
    	}
    	location.href="/imageboard/myFileMng";
    } 
    

    //그림 클릭시 미리보기
    var imgCommonPreview = new Image(); 
    function viewPic(filepath) { 
    	if(filepath == "") {
    		alert('등록된 이미지가 없습니다.'); 
    		return;
		}
    	imgCommonPreview.src = filepath;
		setTimeout("createPreviewWin(imgCommonPreview)", 100); 
	} 
    
    function createPreviewWin(imgCommonPreview) { 
    	if(!imgCommonPreview.complete) {
    		setTimeout("createPreviewWin(imgCommonPreview)", 100); 
    		return;
    		} var scrollsize = 17; 
    		var swidth = screen.width - 10;
    		var sheight = screen.height - 90;
    		var wsize = imgCommonPreview.width;
    		var hsize = imgCommonPreview.height;

    		if(wsize < 50) wsize = 50; // 가로 최소 크기 
    		if(hsize < 50) hsize = 50; // 세로 최소 크기 
    		if(wsize > swidth) wsize = swidth; // 가로 최대 크기
    		if(hsize > sheight) hsize = sheight; // 세로 최대 크기 
    		
    		// 세로가 최대크기를 초과한경우 세로스크롤바 자리 확보 
    		if((wsize < swidth-scrollsize) && hsize >= sheight) wsize += scrollsize; // 가로가 최대크기를 초과한경우 가로스크롤바 자리 확보 
    		if((hsize < sheight-scrollsize) && wsize >= swidth) hsize += scrollsize; // IE 6,7 전용 : 가로세로 크기가 보통일때 세로 스크롤바 자리 확보 
    		if((wsize < swidth-scrollsize) && hsize < sheight
    				&& (navigator.userAgent.indexOf("MSIE 6.0") > -1
					|| navigator.userAgent.indexOf("MSIE 7.0") > -1))wsize += scrollsize; // 듀얼 모니터에서 팝업 가운데 정렬하기 
    		var mtWidth = document.body.clientWidth; // 현재 브라우저가 있는 모니터의 화면 폭 사이즈 
    		var mtHeight = document.body.clientHeight; // 현재 브라우저가 있는 모니터의 화면 높이 사이즈
    		var scX = window.screenLeft; // 현재 브라우저의 x 좌표(모니터 두 대를 합한 총 위치 기준) 
    		var scY = window.screenTop; // 현재 브라우저의 y 좌표(모니터 두 대를 합한 총 위치 기준) 
    		var popX = scX + (mtWidth - wsize) / 2 - 50; // 팝업 창을 띄울 x 위치 지정(모니터 두 대를 합한 총 위치 기준) 
    		var popY = scY + (mtHeight - hsize) / 2 - 50; // 팝업 창을 띄울 y 위치 지정(모니터 두 대를 합한 총 위치 기준) 
    		
    		// window.open('주소', '이름(공란가능)', '속성'); 
    		imageWin = window.open("", "", 
    				"top=" + 100
    				+ ",left="+ popX
    				+ ",width=" + wsize
    				+ ",height="+ hsize
    				+",scrollbars=yes,resizable=yes,status=no");
    		imageWin.document.write("<html><title>Preview</title><body style='margin:0;cursor:pointer;' title='Close' onclick='window.close()'>");
    		imageWin.document.write("<img src='" + imgCommonPreview.src + "'>");
    		imageWin.document.write("</body></html>"); 
	} 

																																																																																																																																							// title='Close'
																																																																																																																																							// onclick='window.close()'>");imageWin.document.write("<img
																																																																																																																																							// src='"
																																																																																																																																							// +
																																																																																																																																							// imgCommonPreview.src
																																																																																																																																							// +
																																																																																																																																							// "'>");imageWin.document.write("</body></html>");
																																																																																																																																							// }

 

 