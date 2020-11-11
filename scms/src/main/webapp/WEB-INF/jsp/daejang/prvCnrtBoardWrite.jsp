<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>수의계약 관리시스템</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript" src="/scms/se2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript">
		var gfv_count = 1;
		var tmp2 = "";
		var i = 1;
		$(document).ready(function(){
						
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_prvCnrtBoardList();
			});
			
			$("#write").on("click", function(e){ //등록하기 버튼							
				e.preventDefault();				
				
				if( $('#USER_RIGHT').val() == 'A')
				{
					fn_insertPrvCnrtBoard();
				}
				else{
					alert('권한이 없습니다.');
				}
				
			});
						
			$('#close1').click(function() {
		        $('#pop').hide();
		    });
			
			$('#close2').click(function() {
		        $('#pop').hide();
		    });
			
			$('#open').click(function() {
		        
				if($("#USER_NAME").val().length < 1){					
					alert('찾으시려는 이름을 입력하세요');
					$("#USER_NAME").focus();
					return;
				}
				
				$('#pop').show();
		        fn_jikwonlist();
		    });
			
			$('#clear').click(function() {
				
				$('#SAUP_PERSON_ID').val("");			
		    });
			
			$("#addFile").on("click", function(e){ //파일 추가 버튼
				e.preventDefault();
				fn_addFile();
			});
			
			$("a[name='delete']").on("click", function(e){ //삭제 버튼
				e.preventDefault();
				fn_deleteFile($(this));
			});
			
			$("#SAUP_BUDGET_AMT").bind('keyup keydown',function(){
		        inputNumberFormat(this);
		    });
			
			
			
			$(document).keydown(function(e){
				if(e.target.nodeName != "INPUT" && e.target.nodeName != "TEXTAREA"){
			        if(e.keyCode == 8){		        
			        	return false;
			        }
			    }
				
				if(e.target.readOnly){ // readonly일 경우 true
		            if(e.keyCode == 8){
		                return false;
		           }
				}
			});
			
			
		});
		
		
		
		
		//입력한 문자열 전달
	    function inputNumberFormat(obj) {
	        obj.value = comma(uncomma(obj.value));
	    }
	       
	    //콤마찍기
	    function comma(str) {
	        str = String(str);
	        return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
	    }
	 
	    //콤마풀기
	    function uncomma(str) {
	        str = String(str);
	        return str.replace(/[^\d]+/g, '');
	    }
	 
	    //숫자만 리턴(저장할때)
	    //alert(cf_getNumberOnly('1,2./3g')); -> 123 return
	    function cf_getNumberOnly (str) {
	        var len      = str.length;
	        var sReturn  = "";
	 
	        for (var i=0; i<len; i++){
	            if ( (str.charAt(i) >= "0") && (str.charAt(i) <= "9") ){
	                sReturn += str.charAt(i);
	            }
	        }
	        return sReturn;
	    }
		
		
		
		
		function fn_prvCnrtBoardList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtBoardList.do' />");		
			comSubmit.submit();
		}
	
		
		
		function fn_insertPrvCnrtBoard(){
			var comSubmit = new ComSubmit("form1");
			var tmp = "";				
			oEditors.getById["CONTENTS"].exec("UPDATE_CONTENTS_FIELD", []);
			
			if($('#TITLE').val() == "")
			{
				alert('제목을  입력하세요!!');
				$('#TITLE').focus();
				return false();
			}
			
								
			if($('#CONTENT').val() == "")
			{
				alert('내용을  입력하세요!!');
				$('#CONTENT').focus();
				return false();
			}
			
						
			if(confirm('데이터를 등록하시겠습니까?'))
			{							
				comSubmit.setUrl("<c:url value='/daejang/insertPrvCnrtBoard.do' />");
				comSubmit.addParam("BOARD_ID", "${param.BOARD_ID}");
				comSubmit.submit();
			}
		}
		
		
		function fn_addFile(){
			var str = "<p><input type='file' name='file_"+(gfv_count++)+"'> <a href='#this' class='btn' name='delete'>삭제</a></p>";
			$("#fileDiv").append(str);
			$("a[name='delete']").on("click", function(e){ //삭제 버튼
				e.preventDefault();
				fn_deleteFile($(this));
			});
		}
		
		function fn_deleteFile(obj){
			obj.parent().remove();
		}		
		
		
		function fn_amtCheck(){
			
			if(uncomma($('#SAUP_BUDGET_AMT').val()) <  3300000 || uncomma($('#SAUP_BUDGET_AMT').val()) > 22000000 )
			{				
				alert('수의계약 관리시스템 금액기준을 벗어났습니다.!!\n한번 확인후 진행하시길 바랍니다.[330만원 ~ 2,200만원(예외있음)]');
				$('#SAUP_BUDGET_AMT').focus();
				$('#SAUP_BUDGET_AMT').select();
				//return false();
			}			
		}
	</script>
	
	
<style>

input:read-only { 
	background-color:blue;
	}
</style>	
</head>

<body>
<form id="form1" name="form1" enctype="multipart/form-data">
<input type="hidden" id="INS_ID" name="INS_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="INS_DEPT" name="INS_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="WRITE_ID" name="WRITE_ID" value="${map.INS_ID}">
<input type="hidden" id="LOGIN_ID" name="LOGIN_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="BOARD_GB" name="BOARD_GB" value="7"> 

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/scms/images/popup_title.png" align="absmiddle">공지사항 등록</td>
      </tr>
      <tr>
        <td class="margin_title"></td>
      </tr>
    </table>
    <!--페이지 타이틀 끝 -->
    </td>
  </tr>
  
  <tr>
    <td class="pupup_frame" style="padding-right:12px">

    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="15">
                                
 
        <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">	      	 	         	       
	          <tr>
	            <td width="120" class="tbl_field">제목</td>
	            <td width="710" colspan="3" class="tbl_list_left"><input type="text" id="TITLE" name="TITLE" class="input" style="width:400;"></td>									
	          </tr>	         	         	          
	          <tr>
	            <td width="120" class="tbl_field">내용</td>
	            <td width="710" colspan="3" class="tbl_list_left">
	             <textarea rows="10" cols="100" id="CONTENTS" name="CONTENTS" class="input" style="width:700px; height:400px; display:none;"></textarea>
	            </td>
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">첨부파일</td>
	            <td colspan="3" width="710" class="tbl_list_left">
	                <div id="fileDiv">
						<p>
							<input type="file" id="file" name="file_0">
							<a href="#this" class="btn" id="delete" name="delete">삭제</a> (<b>10M이하</b>)
						</p>
					</div>
					<a href="#this" class="btn" id="addFile">파일 추가</a> (<b>10M이하</b>)           
	            </td>
	          </tr>	        	       
	      </table>
	      	      
        <!-- -------------- 버튼 시작 --------------  -->
        <table width="100%" height="40" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td></td>
            <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
               <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
                  <tr>
                    <td><img src="/scms/images/btn_type0_head.gif"></td>
                    <td background="/scms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="write"><font color="white">등록</font></a></td>
                    <td><img src="/scms/images/btn_type0_end.gif"></td>
                  </tr>
                </table>
                </td>                               	
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemoTempSave21" style="filter:none;">
                  <tr>
                    <td><img src="/scms/images/btn_type1_head.gif"></td>
                    <td background="/scms/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="list">목록</a></td>
                    <td><img src="/scms/images/btn_type1_end.gif"></td>
                  </tr>
                </table>                
                </td>                               
              </tr>
            </table>
            </td>
          </tr>
          <tr>
            <td class="margin_btn" colspan="2"></td>
          </tr>
        </table>
        <!-- -------------- 버튼 끝 --------------  -->        
        </td>
      </tr>
    </table>
    </td>    
  </tr>
</table>
</form>
<%@ include file="/WEB-INF/include/include-body.jspf" %>

<script type="text/javascript">
var oEditors = [];

var sLang = "ko_KR";	// 언어 (ko_KR/ en_US/ ja_JP/ zh_CN/ zh_TW), default = ko_KR

// 추가 글꼴 목록
//var aAdditionalFontSet = [["MS UI Gothic", "MS UI Gothic"], ["Comic Sans MS", "Comic Sans MS"],["TEST","TEST"]];

nhn.husky.EZCreator.createInIFrame({
	oAppRef: oEditors,
	elPlaceHolder: "CONTENTS",
	sSkinURI: "/scms/se2/SmartEditor2Skin.html",	
	htParams : {
		bUseToolbar : true,				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
		bUseVerticalResizer : true,		// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
		bUseModeChanger : true,			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
		//bSkipXssFilter : true,		// client-side xss filter 무시 여부 (true:사용하지 않음 / 그외:사용)
		//aAdditionalFontList : aAdditionalFontSet,		// 추가 글꼴 목록
		fOnBeforeUnload : function(){
			//alert("완료!");
		},
		I18N_LOCALE : sLang
	}, //boolean
	fOnAppLoad : function(){
		//예제 코드
		//oEditors.getById["SAUP_PUSH_CONTENT"].exec("PASTE_HTML", ["로딩이 완료된 후에 본문에 삽입되는 text입니다."]);
	},
	fCreator: "createSEditor2"
});

</script>

</body>
</html>
