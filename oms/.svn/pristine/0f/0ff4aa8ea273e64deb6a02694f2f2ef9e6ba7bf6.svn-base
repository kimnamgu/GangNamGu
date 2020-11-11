<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>자료실</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
	var gfv_count = '${fn:length(list)+1}';
	$(document).ready(function(){
		$("#list").on("click", function(e){ //목록으로 버튼
			e.preventDefault();
			fn_openBoardList();
		});
		
		$("#update").on("click", function(e){ //저장하기 버튼
			e.preventDefault();
			fn_updateBoard();
		});
		
		$("#delete").on("click", function(e){ //삭제하기 버튼
			e.preventDefault();
			fn_deleteBoard();
		});
		
		$("#addFile").on("click", function(e){ //파일 추가 버튼
			e.preventDefault();
			fn_addFile();
		});
		
		$("a[name^='delete']").on("click", function(e){ //삭제 버튼
			e.preventDefault();
			fn_deleteFile($(this));
		});
	});
	
	function fn_openBoardList(){
		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/board/openBoardList.do' />");
		comSubmit.addParam("bdId", "${param.BDID}");
		comSubmit.submit();
	}
	
	function fn_updateBoard(){
		var comSubmit = new ComSubmit("form1");
		
		if($('#TITLE').val() == "")
		{
			alert('제목을 입력하세요.');
			$('#TITLE').focus();
			return false();
		}
		
		if($('#CONTENTS').val() == "")
		{
			alert('내용을  입력하세요.');
			$('#CONTENTS').focus();
			return false();
		}
		
		
		if(confirm('데이터를 수정하시겠습니까?'))
		{
			comSubmit.setUrl("<c:url value='/board/updateBoard.do' />");
			comSubmit.addParam("bdId", "${param.BDID}");
			comSubmit.submit();
		}
	}
	
	function fn_deleteBoard(){
		var comSubmit = new ComSubmit();
		
		if(confirm('데이터를 삭제하시겠습니까?'))
		{
			comSubmit.setUrl("<c:url value='/board/deleteBoard.do' />");
			comSubmit.addParam("IDX", $("#IDX").val());
			comSubmit.addParam("bdId", "${param.BDID}");
			comSubmit.submit();
		}		
	}
	
	function fn_addFile(){
		var str = "<p>" +
				"<input type='file' id='file_"+(gfv_count)+"' name='file_"+(gfv_count)+"'>"+
				"<a href='#this' class='btn' id='delete_"+(gfv_count)+"' name='delete_"+(gfv_count)+"'>삭제</a>" +
			"</p>";
		$("#fileDiv").append(str);
		$("#delete_"+(gfv_count++)).on("click", function(e){ //삭제 버튼
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
<input type="hidden" id="MOD_ID" name="MOD_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="MOD_DEPT" name="MOD_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="BOARD_ID" name="BOARD_ID" value="${param.BDID}">
<input type="hidden" id="ID" name="ID" value="${map.ID}">
<input type="hidden" id="BD_LST_ID" name="BD_LST_ID" value="${map.ID}">
<input type="hidden" id="BDID" name="BDID" value="${param.BDID}">

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/oms/images/popup_title.gif" align="absmiddle">자료실</td>
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
					<td class="title"><img src="/oms/images/title_ico.gif" align="absmiddle">자료실 내용수정</td>
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
	                ${sessionScope.userinfo.userName}	 [${map.ID}]               
	            </td>
	          </tr>		         
			  <tr>
	            <td width="120" class="tbl_field">제목</td>
	            <td colspan="3" width="607" class="tbl_list_left">	              
	                <input type="text" id="TITLE" name="TITLE" value="${map.TITLE}" class="input" style="width:557;">                
	            </td>								
	          </tr>			  
			  <tr>
	            <td width="120" class="tbl_field">내용</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <textarea rows="10" cols="76" id="CONTENTS" name="CONTENTS" class="input" style="width:557;">${map.CONTENTS}</textarea>	                
	            </td>
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">첨부파일</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	               <div id="fileDiv">				
						<c:forEach var="row" items="${list }" varStatus="var">
							<p>
								<input type="hidden" id="ID" name="ID_${var.index }" value="${row.ID }">
								<a href="#this" id="name_${var.index }" name="name_${var.index }">${row.ORIGINAL_FILE_NAME }</a>
								<input type="file" id="file_${var.index }" name="file_${var.index }"> 
								(${row.FILE_SIZE }kb)
								<a href="#this" class="btn" id="delete_${var.index }" name="delete_${var.index }">삭제</a>
							</p>
						</c:forEach>
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
                    <td background="/oms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="update"><font color="white">수정처리</font></a></td>
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