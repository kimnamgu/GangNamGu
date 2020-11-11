<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>자료실</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript" src="/oms/smarteditor/js/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript">
		var gfv_count = 1;
		
		//전역변수선언
		var editor_object = [];

		$(document).ready(function(){
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_openBoardList();
			});
			
			$("#write").on("click", function(e){ //작성하기 버튼
				e.preventDefault();
				editor_object.getById["CONTENTS"].exec("UPDATE_CONTENTS_FIELD", []);
				alert($("#CONTENTS").val());
				fn_insertBoard();
			});
			
			$("#addFile").on("click", function(e){ //파일 추가 버튼
				e.preventDefault();
				fn_addFile();
			});
			
			$("a[name='delete']").on("click", function(e){ //삭제 버튼
				e.preventDefault();
				fn_deleteFile($(this));
			});
			
			
			
			nhn.husky.EZCreator.createInIFrame({
				oAppRef: editor_object,
				elPlaceHolder: "CONTENTS",
				sSkinURI: "/oms/smarteditor/SmartEditor2Skin.html",	
				htParams : {
					// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
					bUseToolbar : true,				
					// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
					bUseVerticalResizer : true,		
					// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
					bUseModeChanger : true,	
				}
			});
		});
				
		function fn_openBoardList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/board/openBoardList.do' />");
			comSubmit.submit();
		}
		
		function fn_insertBoard(){
			var comSubmit = new ComSubmit("form1");
			comSubmit.setUrl("<c:url value='/board/insertBoard.do' />");
			comSubmit.addParam("BDID", "${param.BDID}");
			comSubmit.submit();
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
	</script>
</head>
<body>
<form id="form1" name="form1" enctype="multipart/form-data">
<input type="hidden" id="INS_ID" name="INS_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="INS_DEPT" name="INS_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="BOARD_ID" name="BOARD_ID" value="${param.BDID}">

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/oms/images/popup_title.gif" align="absmiddle">
        <c:set var="bdId" value="${param.BDID}" />
		<c:choose>
	    <c:when test="${bdId eq '1'}">
                   자료실
    	</c:when>
	    <c:when test="${bdId eq '2'}">
                  공지사항
    	</c:when>
    	<c:otherwise>
	    </c:otherwise>
		</c:choose>
        </td>
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

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="title"><img src="/oms/images/title_ico.gif" align="absmiddle">자료실  등록</td>
				</tr>
			</table>
			</td>				
		</tr>
		</table>
			
        <br>
        <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">
	      	   <tr>
	            <td width="120" class="tbl_field">작성자</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                ${sessionScope.userinfo.userName}	                
	            </td>
	          </tr>		         
			  <tr>
	            <td width="120" class="tbl_field">제목</td>
	            <td colspan="3" width="607" class="tbl_list_left">	              
	                <input type="text" id="TITLE" name="TITLE" class="input" style="width:557px;">                
	            </td>								
	          </tr>			  
			  <tr>
	            <td width="120" class="tbl_field">내용</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <textarea rows="10" cols="76" id="CONTENTS" name="CONTENTS" class="input" style="width:800px;height:412px;" rows="10" cols="100"></textarea>	                
	            </td>
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">첨부파일</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <div id="fileDiv">
						<p>
							<input type="file" id="file" name="file_0">
							<a href="#this" class="btn" id="delete" name="delete">삭제</a>
						</p>
					</div>
					<a href="#this" class="btn" id="addFile">파일 추가</a>            
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
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
                  <tr>
                    <td><img src="/oms/images/btn_type0_head.gif"></td>
                    <td background="/oms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="write"><font color="white">등록</font></a></td>
                    <td><img src="/oms/images/btn_type0_end.gif"></td>
                  </tr>
                </table>                
                </td>				
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemoTempSave21" style="filter:none;">
                  <tr>
                    <td><img src="/oms/images/btn_type1_head.gif"></td>
                    <td background="/oms/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="list">목록</a></td>
                    <td><img src="/oms/images/btn_type1_end.gif"></td>
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
</body>
</html>