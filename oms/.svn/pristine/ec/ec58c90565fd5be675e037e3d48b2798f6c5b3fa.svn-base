<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
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
			
		});
		
		function fn_openBoardList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/officework/officeworkRegList.do' />");
			comSubmit.submit();
		}
		
		function fn_openBoardUpdate(){
			var id = "${map.ID}";
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/officework/openBoardUpdate.do' />");
			comSubmit.addParam("ID", id);
			comSubmit.submit();
		}
				
	</script>
</head>
<body>
	<table class="board_view">
		<colgroup>
			<col width="15%"/>
			<col width="35%"/>
			<col width="15%"/>
			<col width="35%"/>
		</colgroup>
		<caption>게시글 상세</caption>
		<tbody>
			<tr>
				<th scope="row">글 번호</th>
				<td>${map.ID }</td>
				<th scope="row">OW_NAME</th>
				<td>${map.OW_NAME }</td>
			</tr>
			<tr>
				<th scope="row">OW_DEPT</th>
				<td>${map.OW_DEPT }</td>
				<th scope="row">OW_TYPE1</th>
				<td>${map.OW_TYPE1 }</td>
			</tr>
			<tr>
				<th scope="row">OW_NAME</th>
				<td colspan="3">${map.OW_NAME }</td>
			</tr>
			<tr>
				<td colspan="4">${map.OW_CONTENTS }</td>
			</tr>			
		</tbody>
	</table>
	<br/>
	
	<a href="#this" class="btn" id="list">목록으로</a>
	<a href="#this" class="btn" id="update">수정하기</a>	
	<%@ include file="/WEB-INF/include/include-body.jspf" %>	
</body>
</html>