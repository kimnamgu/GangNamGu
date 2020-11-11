<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글 수정</title>
<script language="javascript" type="text/javascript" src="/resources/js/board/boardWrite.js"></script>
</head>
<body>
 
<form name="modComp" id="modComp" method="post" action="/board/boardUpdateComp" >
    <input type="hidden" name="boardIdx" 		value="${modContent.boardIdx }"/>
    
    <!-- 검색조건 유지를 위한 값 -->
    <input type="hidden" name="listNum" 	value="${search.listNum}" /> 
    <input type="hidden" name="keyField" 	value="${search.keyField }" /> 
    <input type="hidden" name="keyWord" 	value="${search.keyWord }" />
    <input type="hidden" name="page" 		value="${search.page}" /> 
    <input type="hidden" name="pageYN" 		value="N" />   
           
 	<div class="row form-group">
         <div class="col col-md-3">
             <label for="id" class=" form-control-label">작성자</label>
         </div>
         <div class="col-12 col-md-9">
         
             <input type="text" id="id" name="id" size="10" value="${authInfo.id}" maxlength="8" placeholder="Disabled" readonly="true" class="form-control">
        </div>
    </div>
    <div class="row form-group">
        <div class="col col-md-3">
            <label for="title" class=" form-control-label">제 목</label>
        </div>
        <div class="col-12 col-md-9">
            <input type="text" id="title" name="title" placeholder="제목" value="${modContent.title}" class="form-control">
            <small class="form-text text-muted">재밌는 제목을 지어주세요 ~ ^^</small>
        </div>
    </div>

    <div class="row form-group">
        <div class="col col-md-3">
            <label for="content" class=" form-control-label">내용</label>
        </div>
        <div class="col-12 col-md-9">
            <textarea name="content" id="content" rows="9" placeholder="내용을 입력하세요..." class="form-control">${modContent.content}</textarea>
        </div>
    </div>
    <div class="row form-group">
        <div class="col col-md-3">
            <label for="filename" class=" form-control-label">첨부파일</label>
        </div>
        <div class="col-12 col-md-9">
            <input type="file" name="file" id="filename" multiple="multiple" class="form-control-file" accept=".jpg,.jpeg,.png,.gif,.bmp">
        </div>
    </div>
	<div align="center">
		<!-- <button name="submitBtn" class="btn btn-success btn-sm" onclick="reg_content()">등록</button>  
		<button onclick="history.back()" class="btn btn-danger btn-sm">뒤로</button> -->
		
		<input type="button" class="btn btn-success btn-sm"	value="수정 완료" onclick="check_modify()">  
		<input type="reset"	 class="btn btn-warning btn-sm"	value="다시 수정"> 
		<input type="button" class="btn btn-danger btn-sm"	value="뒤로" onclick="history.back()">
	</div>
	
	<c:forEach var="fileDtl" items="${resultlist}">
		<div id="${fileDtl.fileIdx}" >
					<br><img alt=""	src="/resources/uploadImage/${fileDtl.folderPath}/${fileDtl.fileRealName}" style="width: 190px; height: auto;" title="${fileDtl.fileDescription}">
					<a href="javascript:void(0);" onclick="go_fileDel(${fileDtl.fileIdx});">삭제</a>
		</div>
	</c:forEach>

	
 </form> 
 <%-- 
    <table summary="글수정 전체 테이블">
        <form:form name="modComp" id="modComp" ModelAttribute="modComp" method="post" action="/board/boardUpdateComp" >
        <input type="hidden" name="boardIdx" 		value="${modContent.boardIdx }"/>
        
        <!-- 검색조건 유지를 위한 값 -->
        <input type="hidden" name="listNum" 	value="${search.listNum}" /> 
        <input type="hidden" name="keyField" 	value="${search.keyField }" /> 
        <input type="hidden" name="keyWord" 	value="${search.keyWord }" />
        <input type="hidden" name="page" 		value="${search.page}" /> 
        <input type="hidden" name="pageYN" 		value="N" />   
           
        <colgroup>
            <col width="20%">
            <col width="80%">
        </colgroup>
       
        <table summary="테이블 구성" >
        <caption>글 수정하기 [${modContent.boardIdx }번] 게시물</caption>    
            <tr>
                <td>작성자</td>
                <td><input type=text id="name" name=name size=10 maxlength=8 value="${modContent.id }"></td>
            </tr>
            <tr>
                 <td>제 목</td>
                 <td><input type=text id="title" name=title value="${modContent.title }"></td>
            </tr>
            <tr>
                 <td>내 용</td>
                 <td><textarea id="content" name=content rows ="10" cols="100">${modContent.content }</textarea></td>
            </tr>
            <tr>
                 <td colspan=2><hr size=1></td>
            </tr>
            <tr>
			<td colspan="3">
				<div align="center">
					<input type="button" 	value="수정 완료" onclick="check_modify()">  
					<input type="reset"		value="다시 수정"> 
					<input type="button" 	value="뒤로" onclick="history.back()">
				</div>
			</td>
            </tr>
			<c:forEach var="fileDtl" items="${resultlist}">
				<tr id="${fileDtl.fileIdx}" >
					<td colspan=2>
							<br><img alt=""	src="/resources/uploadImage/${fileDtl.folderPath}/${fileDtl.fileRealName}" style="width: 190px; height: auto;" title="${fileDtl.fileDescription}">
							<a href="javascript:void(0);" onclick="go_fileDel(${fileDtl.fileIdx});">삭제</a>
					</td>
				</tr>
			</c:forEach>
			</table>
        

    </form:form> 
</table>
  --%>
</body>
</html>
