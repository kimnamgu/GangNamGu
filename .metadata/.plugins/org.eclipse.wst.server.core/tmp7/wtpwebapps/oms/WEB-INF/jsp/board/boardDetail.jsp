<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
pageContext.setAttribute("crcn", "\r\n"); //Space, Enter
pageContext.setAttribute("br", "<br/>"); //br 태그
%>
<html>
<head>
<title>위탁사무등록</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
$(document).ready(function(){
	$("#list").on("click", function(e){ //목록으로 버튼
		e.preventDefault();
		fn_openBoardList();
	});
	
	$("#update").on("click", function(e){ //수정하기 버튼		
		e.preventDefault();
		fn_openBoardUpdate();				
	});
	
	$("#del").on("click", function(e){ //삭제하기 버튼
		e.preventDefault();
		fn_BoardDelete();
	});
	
	$("a[name='file']").on("click", function(e){ //파일 이름
		e.preventDefault();
		fn_downloadFile($(this));
	});
});

function fn_openBoardList(){
	var comSubmit = new ComSubmit();
	comSubmit.setUrl("<c:url value='/board/openBoardList.do' />");
	comSubmit.addParam("bdId", "${param.BDID}");
	comSubmit.submit();
}

function fn_openBoardUpdate(){
	var idx = "${map.ID}";
	var comSubmit = new ComSubmit();
	var user_right =  $("#USER_RIGHT").val();
	
	if(user_right == 'A'){
				
		comSubmit.setUrl("<c:url value='/board/openBoardUpdate.do' />");
		comSubmit.addParam("ID", idx);
		comSubmit.addParam("BDID", "${param.BDID}");
		comSubmit.submit();		
	}
	else{
		alert('권한이 없습니다.');
	}
	
}

function fn_BoardDelete(){
	var idx = "${map.ID}";
	var comSubmit = new ComSubmit("form1");
	var user_right =  $("#USER_RIGHT").val();
	
	if(user_right == 'A'){
		
		if(confirm('데이터를 삭제하시겠습니까?'))
		{
			comSubmit.setUrl("<c:url value='/board/deleteBoard.do' />");
			comSubmit.addParam("ID", idx);
			comSubmit.addParam("BDID", "${param.BDID}");
			comSubmit.submit();
		}
	}
	else{
		alert('권한이 없습니다.');
	}
	
}

function fn_downloadFile(obj){
	var idx = obj.parent().find("#FID").val();
	var comSubmit = new ComSubmit();
	comSubmit.setUrl("<c:url value='/common/downloadFile.do' />");
	comSubmit.addParam("ID", idx);
	comSubmit.submit();
}
</script>
</head>
<body>
<form id="form1" name="form1">
<input type="hidden" id="DEL_ID" name="DEL_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="DEL_DEPT" name="DEL_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/oms/images/popup_title.gif" align="absmiddle">
	    <c:set var="bdId" value="${map.BOARD_ID}" />
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
            <td class="margin_btn" colspan="2"></td>
          </tr>
        </table>
        
        <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">	      	  
	          <tr>
	            <td width="120" class="tbl_field">글번호</td>
	            <td width="243" class="tbl_list_left">
	                ${param.RID} [${map.ID}]                 
	            </td>
				<td width="120" class="tbl_field">조회수</td>
	            <td width="244" class="tbl_list_left">
	                ${map.HIT_CNT}
	            </td>							
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">작성자</td>
	            <td width="243" class="tbl_list_left">
	                ${map.USER_NAME}                 
	            </td>
				<td width="120" class="tbl_field">작성시간</td>
	            <td width="244" class="tbl_list_left">
	                ${map.INS_DATE}
	            </td>							
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">제목 <font class="font_star"></font></td>
	            <td colspan="3" width="607" class="tbl_list_left">${map.TITLE}</td>
	          </tr>			 
			  <tr>
	            <td width="120" class="tbl_field">내 용</td>
	            <td colspan="3" width="607" class="tbl_list_left">${fn:replace(map.CONTENTS, crcn, br)}</td>
	          </tr>				  
			  <tr>
	            <td width="120" class="tbl_field">첨부파일</td>
	            <td colspan="3" width="607" class="tbl_list_left">
					<c:forEach var="row" items="${list }">
						<p>
							<input type="hidden" id="FID" value="${row.ID }">
							<a href="#this" name="file">${row.ORIGINAL_FILE_NAME }</a> 
							(${row.FILE_SIZE }kb)
						</p>
					</c:forEach>
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
                    <td><img src="/oms/images/btn_type0_head.gif"></td>
                    <td background="/oms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="update"><font color="white">수정</font></a></td>
                    <td><img src="/oms/images/btn_type0_end.gif"></td>
                  </tr>
                </table>
                </td>
				
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
                  <tr>
                    <td><img src="/oms/images/btn_type0_head.gif"></td>
                    <td background="/oms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="del"><font color="white">삭제</font></a></td>
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