<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글 읽기</title>
<link rel="stylesheet" type="text/css" href="/resources/css/board/board.css" />
<script language="javascript" type="text/javascript" src="/resources/js/board/board.js"></script>
</head>

<body>
	<table summary="전체 테이블 구성" style="margin:10px;width:100%;" border=1  cellpadding=5 cellspacing=0>
	    <tr> 
	        <td align=center bgcolor=#dddddd width=20%> 작성자</td>
	        <td bgcolor=#ffffe8 width=30%>${viewContent.id}</td>
	        <td align=center bgcolor=#dddddd width=15%> 작성일</td>
	        <td bgcolor=#ffffe8 width=50%>${viewContent.ins_dt }</td>
	    </tr>
	    <tr> 
	        <td align=center bgcolor=#dddddd> 제 목</td>
	        <td bgcolor=#ffffe8 colspan=3>${viewContent.title}</td>
	    </tr>
		<tr> 
		     <td colspan=4>
		     <br>
			     <c:forEach var="fileDtl" items="${resultlist}">
			     	<br><img alt="" src="/resources/uploadImage/${fileDtl.folderPath}/${fileDtl.fileRealName}" style="width: 190px; height:auto;" title="${fileDtl.fileDescription}">
			     </c:forEach>
		     <br>${viewContent.content }
		     <br>
		     </td>
		</tr>
		<tr>
		     <td colspan=4 align=right> 조회수  : ${viewContent.hitCnt}</td>
		</tr>
    </table>
    
    <div align="center" style="margin:10px;">
	      <c:choose>
	        <c:when test = "${viewContent.recommendYN eq 'Y'}">
	           	<button class="btn btn-success btn-sm" id="${viewContent.boardIdx}_recommendY" style="background-color:red" onClick="fn_recommend(${viewContent.boardIdx},'Y')">좋아요 ${viewContent.recommendYcnt}</button>
	           	<button class="btn btn-danger btn-sm" id="${viewContent.boardIdx}_recommendN" onClick="fn_recommend(${viewContent.boardIdx},'N')">싫어요  ${viewContent.recommendNcnt}</button>
	        </c:when>
	        <c:when test = "${viewContent.recommendYN eq 'N'}">
	           	<button class="btn btn-success btn-sm" id="${viewContent.boardIdx}_recommendY" onClick="fn_recommend(${viewContent.boardIdx},'Y')">좋아요 ${viewContent.recommendYcnt}</button>
	           	<button class="btn btn-danger btn-sm" id="${viewContent.boardIdx}_recommendN" style="background-color:red" onClick="fn_recommend(${viewContent.boardIdx},'N')">싫어요  ${viewContent.recommendNcnt}</button>
	        </c:when>
	        <c:otherwise>
	           	<button class="btn btn-success btn-sm" id="${viewContent.boardIdx}_recommendY" onClick="fn_recommend(${viewContent.boardIdx},'Y')">좋아요 ${viewContent.recommendYcnt}</button>
				<button class="btn btn-danger btn-sm" id="${viewContent.boardIdx}_recommendN" onClick="fn_recommend(${viewContent.boardIdx},'N')">싫어요  ${viewContent.recommendNcnt}</button>
	        </c:otherwise>
	     </c:choose>
    </div>
	<div align="center" style="margin:10px;">
		<button class="btn btn-primary btn-sm" onClick="list()">목 록</button>
		
		<!-- 로그인한 사람이고 작성자일경우 수정삭제가능-->
		<c:if test = "${viewContent.login_id eq viewContent.id}">
			<button class="btn btn-warning btn-sm" onClick="mod_Content(${viewContent.boardIdx})">수 정</button>
			<button class="btn btn-danger btn-sm" onClick="delContent(${viewContent.boardIdx})">삭 제 </button>
		</c:if>
	</div>
</body>

<!-- 답글 -->
<form name="replyForm" id="replyForm" method="post">
<div style="float:left;">
	<textarea name="replyCont" id="replyCont" rows="2" cols="50" class="is-invalid form-control"></textarea>
	<input type="hidden" name="boardIdx" value="${viewContent.boardIdx}"/>
</div>
<div style="float:left;">
	<input type="button" value="답변등록" class="btn btn-primary btn-lg" onClick="fn_replyReg()">
</div>
</form>

<form name="replyList" id="replyList" method="post">
	<input type="hidden" name="boardIdx" value="${viewContent.boardIdx}"/>
	<input type="hidden" id="upperReplyIdx" name="upperReplyIdx"/>

	<table border="1" summary="답변 테이블 구성"> 
		<c:forEach var="replyDtl" items="${replylist}">
			<tr id="${replyDtl.replyIdx}">
				<td>${replyDtl.replyCont}</td>
				<td><input type="button" value="답글 " onClick="fn_replyReShow(${replyDtl.replyIdx})"></td>
			</tr>
		</c:forEach>
	</table>
</form>

    <!-- 게시글 수정 -->
    <form name="modContent" method="post"  >
        <input type="hidden" name="boardIdx"/>
       	<input type="hidden" name="listNum" 	value="${search.listNum}" /> 
        <input type="hidden" name="keyField" 	value="${search.keyField }" /> 
        <input type="hidden" name="keyWord" 	value="${search.keyWord }" />
        <input type="hidden" name="page" 		value="${search.page}" /> 
        <input type="hidden" name="pageYN" 		value="N" />
    </form>
    
    <!-- 목록으로 -->
    <form action="/board/boardList" name="search" method="post"  >
     	<input type="hidden" name="listNum" 	value="${search.listNum}" /> 
        <input type="hidden" name="keyField" 	value="${search.keyField }" /> 
        <input type="hidden" name="keyWord" 	value="${search.keyWord }" />
        <input type="hidden" name="page" 		value="${search.page}" /> 
        <input type="hidden" name="pageYN" 		value="N" />
    </form>
    

</html>
