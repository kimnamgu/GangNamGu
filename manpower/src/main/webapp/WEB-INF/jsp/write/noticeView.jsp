<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf"%>


<script type="text/javascript">
	var user_right = "${sessionScope.userinfo.userright}";

	$(document).ready(
		function() {
		
			//정보입력
			$("#update").on("click", function(e){ //등록하기 버튼		
				e.preventDefault();				
				fn_update();				
			});
			
		});
	
	
	//정보 입력
	function fn_update(){
		var comSubmit = new ComSubmit("form1");
		
		if($('#TITLE').val() == "")
		{
			alert('제목을 입력하세요!!');
			$('#TITLE').focus();
			return false;
		}
		
		if($('#CONTENT').val() == "")
		{
			alert('내용을 입력하세요!!');
			$('#CONTENT').focus();
			return false;
		}
		
		
		if(confirm('데이터를 수정하시겠습니까?'))
		{					
			comSubmit.setUrl("<c:url value='/write/updateNotice.do' />");									
			comSubmit.submit();
			alert('등록완료 되었습니다.!!');
		}
	}
			
			
	function trim(str) {
	    return str.replace(/(^\s*)|(\s*$)/gi, '');
	}	
</script>


<style>
  table {
    border: 1px solid #444444;
     border-collapse: collapse;
  }
  th, td {
    border: 1px solid #444444;
    padding : 10px;
  }
</style>
</head>

<body>

<div style="background-color:#170B3B;color:white; margin-bottom:10px; width: 100%;">
	<a href="#this" class="btn" id="logout">
		<img src="/manpower/images/popup_title.gif" align="absmiddle">
	</a>공지사항 등록
	
	<div style="padding-right:10px;padding-top:7px;float:right;">
		<a href="/manpower/manage/noticeManage.do" class="btn btn-success" id="statistics"><i class="fa fa-reply"></i> 목록보기</a>
	</div>
</div>


<form id="form1" name="form1" enctype="multipart/form-data">
	<input type="hidden" id="UPD_ID" name="UPD_ID" value="${sessionScope.userinfo.userId}">
	<input type="hidden" id="ID" name="ID" value="${map.ID}">
	
		<!--리스트 시작 -->
		<table style="margin:20px;">
			<tr>
				<td>제목</td>
				<td><input type="text" class="form-control" id="TITLE" name="TITLE" value="${map.TITLE}"></td>
			</tr>
			
			<tr>
				<td><font color='RED'>내용</font></td>
				<td><input type="text" class="form-control" id="CONTENT" name="CONTENT" value="${map.CONTENT}"></td>
			</tr>
			
			<tr align="right" >
				<td colspan="2">
					<a href="#this" class="btn btn-info" id="update">수정</a>
				</td>
			</tr>
		</table>
	</form>

</body>
</html>