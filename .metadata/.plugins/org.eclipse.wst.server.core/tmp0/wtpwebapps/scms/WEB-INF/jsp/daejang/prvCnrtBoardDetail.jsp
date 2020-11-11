<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
pageContext.setAttribute("cr", "\r"); //Space
pageContext.setAttribute("cn", "\n"); //Enter
pageContext.setAttribute("crcn", "\r\n"); //Space, Enter
pageContext.setAttribute("br", "<br/>"); //br 태그
%>
<html>
<head>
<title>수의계약 관리시스템</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var tmp2 = "";
		var i = 1;
		$(document).ready(function(){
			
			
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_prvCnrtBoardList();
			});
			
			$("#update").on("click", function(e){ //등록하기 버튼							
				e.preventDefault();				
				/*if( $('#USER_RIGHT').val() == 'A' || $('#WRITE_ID').val() == $('#LOGIN_ID').val())
				{*/
					fn_prvCnrtBoardUpdate();
				/*}
				else{
					alert('권한이 없습니다.');
				}*/
			});
			
			
			$("#del").on("click", function(e){ //삭제 버튼							
				e.preventDefault();
				if( $('#USER_RIGHT').val() == 'A' || $('#WRITE_ID').val() == $('#LOGIN_ID').val())
				{
					fn_deletePrvCnrtBoard();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
			
		
			$("a[name='file']").on("click", function(e){ //파일 이름
				e.preventDefault();
				fn_downloadFile($(this));
			});
			
		});
		
		function fn_prvCnrtBoardList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtBoardList.do' />");
			//comSubmit.addParam("bdId", "${param.BDID}");
			comSubmit.submit();
		}
		
	
		
		function fn_prvCnrtBoardUpdate(){		
			var comSubmit = new ComSubmit("form1");			
			
			comSubmit.addParam("ID", "${map.ID}");
			comSubmit.addParam("PARENT_ID", "${map.ID}");
			comSubmit.addParam("BOARD_GB", "7"); //첨부파일 구분
			comSubmit.addParam("BOARD_ID", "1"); //게시판 구분			
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtBoardUpdate.do' />");
			comSubmit.submit();
		}
		
				
		function fn_deletePrvCnrtBoard(){		
			var comSubmit = new ComSubmit("form1");
			
			if(confirm('데이터를 삭제하시겠습니까?'))
			{	
				comSubmit.addParam("ID","${map.ID}");
				comSubmit.setUrl("<c:url value='/daejang/deletePrvCnrtBoard.do' />");
				comSubmit.submit();
			}
		}
		
		function trim(str) {
		    return str.replace(/(^\s*)|(\s*$)/gi, '');
		}	

		
		
		function fn_downloadFile(obj){
			var idx = obj.parent().find("#FID").val();
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/common/downloadFile.do' />");
			comSubmit.addParam("ID", idx);
			comSubmit.submit();
		}	
	</script>
	
	
<style>

input:read-only { 
	background-color:blue;
	}
</style>	
</head>

<body>
<form name="form1" id="form1">
<input type="hidden" id="DEL_ID" name="DEL_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="DEL_DEPT" name="DEL_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="WRITE_ID" name="WRITE_ID" value="${map.INS_ID}">
<input type="hidden" id="LOGIN_ID" name="LOGIN_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="COMP_ADDR" name="COMP_ADDR" value="">
<input type="hidden" id="COMP_ADDR_ROAD" name="COMP_ADDR_ROAD" value="">
<input type="hidden" id="COMP_CAPITAL_DETAIL" name="COMP_CAPITAL_DETAIL" value="">
<c:set var="u_rt" value="${sessionScope.userinfo.userright}"/>
<c:set var="w_id" value="${map.INS_ID}"/> 
<c:set var="l_id" value="${sessionScope.userinfo.userId}"/>

<table border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/scms/images/popup_title.png" align="absmiddle">공지사항</td>
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
	            <td width="130" class="tbl_field">글번호</td>
	            <td width="607" colspan="3" class="tbl_list_left">${map.ID}</td>									
	          </tr>
	          <tr>
	            <td width="130" class="tbl_field">조회수</td>
	            <td width="607" colspan="3" class="tbl_list_left">${map.HIT_CNT}</td>									
	          </tr>
	          
	          <tr>
	            <td width="130" class="tbl_field">작성자</td>
	            <td width="607" colspan="3" class="tbl_list_left">${map.USER_NAME}</td>									
	          </tr>	          
	          <tr>
	            <td width="130" class="tbl_field">작성시간</td>
	            <td width="607" colspan="3" class="tbl_list_left">${map.INS_DATE}</td>									
	          </tr>	          
	          <tr>
	            <td width="130" class="tbl_field">제목</td>
	            <td width="607" colspan="3" class="tbl_list_left">${map.TITLE}</td>									
	          </tr>
	          <tr>
	            <td width="130" class="tbl_field">내용</td>
	            <td width="607" colspan="3" class="tbl_list_left">${fn:replace(map.CONTENTS, crcn, br)}</td>									
	          </tr>	        
	         
	          <tr>
	            <td width="130" class="tbl_field">첨부파일</td>
	            <td colspan="3" width="607" class="tbl_list_left">
					<c:forEach var="row" items="${list }">
						<p>
							<input type="hidden" id="FID" value="${row.ID }">
							<a href="#this" name="file">${row.ORIGINAL_FILE_NAME }</a> 
							(${row.FILE_SIZE }kb)
						</p>
					</c:forEach>
				</td>
	      </table>
	      
	     
	      
        <!-- -------------- 버튼 시작 --------------  -->
        <table width="100%" height="40" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td></td>
            <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>   
               <c:choose>
           	   <c:when test="${u_rt eq 'A'}">           
               <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
                  <tr>
                    <td><img src="/scms/images/btn_type0_head.gif"></td>
                    <td background="/scms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="update"><font color="white">수정</font></a></td>
                    <td><img src="/scms/images/btn_type0_end.gif"></td>
                  </tr>
                </table>
                </td>                
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
                  <tr>
                    <td><img src="/scms/images/btn_type0_head.gif"></td>
                    <td background="/scms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="del"><font color="white">삭제</font></a></td>
                    <td><img src="/scms/images/btn_type0_end.gif"></td>
                  </tr>
                </table>
                </td>
                </c:when>
                </c:choose>                                                   
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

</body>
</html>
