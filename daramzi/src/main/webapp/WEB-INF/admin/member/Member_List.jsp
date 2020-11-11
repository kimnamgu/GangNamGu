<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판 목록</title>
<link rel="stylesheet" type="text/css" href="$/resources/css/board/board.css" />
<script language="javascript" type="text/javascript" src="/resources/js/board/board.js"></script>
</head>
 
<body>
<div id="layer1">게시물 본문 미리 보기</div>
    <form:form action="/board/boardList" name="search" ModelAttribute="search" method="post">
        <table width=610 >
        		<tr>
	            <!-- 검색 part -->
	            <td align=Right>
		            <select name="keyField" size="1">
		                <option value="name" 	<c:if test="${search.keyField eq 'name'}"> 		selected</c:if>> 이름 </option>
		            </select>
		            <input type="text" size="16" name="keyWord" value="${search.keyWord}">
		            <input type="button" value="검색" onClick="check_search()">
	            </td>
	            <tr>
	            <!-- 리스트 선택 -->
	            <td align=Right>
	            <select name="listNum" onchange="listCnt_search()" >
	            	<option value="2"   <c:if test="${search.listNum eq '2'  }"> selected</c:if>> 2개씩보기  </option>
	            	<option value="20"  <c:if test="${search.listNum eq '20' }"> selected</c:if>> 20개씩보기  </option>
					<option value="50"  <c:if test="${search.listNum eq '50' }"> selected</c:if>> 50개씩보기  </option>
					<option value="100" <c:if test="${search.listNum eq '100'}"> selected</c:if>> 100개씩보기</option>
				</select>
				</td>
				<tr>
	            <!-- 정보 part -->
	            <td align=left>▶게시물수 : ${page.totalCount }개 </td><tr>
	            <td align=right valign=top>
	            
	            <!-- 페이지 이동시 값 유지를 위한 hidden 값 -->
				<input type="hidden" name="listNumOrg" 	value="${search.listNum}" /> 
		        <input type="hidden" name="keyFieldOrg" value="${search.keyField }" /> 
		        <input type="hidden" name="keyWordOrg" 	value="${search.keyWord }" />
		        <input type="hidden" name="page" 		value="${search.page}" /> 
		        <input type="hidden" name="pageYN" 		value="N" />
        	</tr>                
        </table>
    </form:form>   

     <!-- 글쓰기 -->
    <div align=Right>
    	<input type="button" value="글쓰기" onClick="go_write(${authInfo.name })">
    </div>
 
    <table class="bbs" width="610" border="2" bgcolor="D8D8D8">
        <colgroup>
            <col width="50" />
            <col width="300" />
            <col width="50" />
            <col width="50" />
            <col width="50" />
        </colgroup>
        <thead>
            <tr>
                <th>번 호</th>
                <th>작성자</th>
                <th>작성일</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <c:choose>
                    <c:when test="${empty resultlist }">
                    	<td colspan="5" align="center">
                    		등록된 글이 없습니다.
                    	</td>
                    </c:when>
                    <c:otherwise>
                        <c:set var="doneLoop" value="false" />
                        <c:forEach begin="0" end="${resultlist.size()-1}" var="i">
                                <c:if test="${not doneLoop }">
                                    <tr>
                                        <td align=center>${resultlist[i].idx }</td>
                                        <td align="center"><a href="mailto:${resultlist[i].email}">${resultlist[i].name }</a>
                                        <td align=center>${resultlist[i].ins_dt }</td>
                                    </tr>
                                    <c:if test="${i+1 == page.totalCount} }">
                                        <c:set var="doneLoop" value="true" />
                                    </c:if>
                                </c:if>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
        </tbody>
        <tfoot></tfoot>
    </table>
        
	<div id="paging">
		<!-- 1~10까지 있는 페이지의 페이징 -->
		<c:if test="${page.prev}">
		    <a href="javascript:pagemove(${page.beginPage+1})">prev</a>
		</c:if>
		<c:forEach begin="${page.beginPage}" end="${page.endPage}" step="1" var="index">
		    <c:choose>
		        <c:when test="${page.page==index}">
		            ${index}
		        </c:when>
		        <c:otherwise>
		            <a href="javascript:pagemove(${index})"> ${index}</a>
		        </c:otherwise>
		    </c:choose>
		</c:forEach>
		<c:if test="${page.next}">
		    <a href="javascript:pagemove(${page.endPage+1})">next</a>
		</c:if>
	</div>
	
   
    <!-- 게시글 상세 -->
    <form action="/board/boardView" name="read" method="post"  >
        <input type="hidden" name="seq"/>
		<input type="hidden" name="listNum" 	value="${search.listNum}" /> 
        <input type="hidden" name="keyField" 	value="${search.keyField }" /> 
        <input type="hidden" name="keyWord" 	value="${search.keyWord }" />
        <input type="hidden" name="page" 		value="${search.page}" /> 
        <input type="hidden" name="pageYN" 		value="N" />
    </form>


  <%-- 
        <form name="pagemove" method="POST" action="boardList.action" >
            <input type="hidden" name="nowBlock" value="${page.nowBlock}" /> 
            <input type="hidden" name="nowPage" value="${page.nowBlock*page.pagePerBlock}" /> 
            <input type="hidden"name="keyField" value="${keyField }" /> 
            <input type="hidden"name="keyWord" value="${keyWord }" />
        </form>
    
    <form name="blockmovef" method="get" action="boardList.action">
        <input type="hidden" name="nowBlock" value="${page.nowBlock+1 }" /> 
        <input type="hidden" name="nowPage" value="${(page.nowBlock+1)*page.pagePerBlock}" />
        <input type="hidden" name="keyField" value="${keyField }" />
        <input type="hidden" name="keyWord" value="${keyWord }" />
    </form>
    
    <form name="blockmoveb" method="POST" action="boardList.action">
        <input type="hidden" name="nowBlock" value="${page.nowBlock-1 }" /> 
        <input type="hidden" name="nowPage" value="${(page.nowBlock-1)*page.pagePerBlock}" />
        <input type="hidden" name="keyField" value="${keyField }" />
        <input type="hidden" name="keyWord" value="${keyWord }" />
    </form>
    
    <form name="list" method="GET">
        <input type="hidden" name="reload" value="true">
    </form> --%>
 
</body>
</html>

