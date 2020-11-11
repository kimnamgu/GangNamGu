<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>게시판 목록</title>
<script language="javascript" type="text/javascript" src="/resources/js/board/board.js"></script>

</head>
 
<body>

    <form:form action="/board/boardList" name="search" ModelAttribute="search" method="post">
    	<div align="right" style="margin:2px 2px 20px 2px;">
	        <table>
	       		<tr>
					<!-- 검색 part -->
		            <td align=Right>
						<div class="rs-select2--light rs-select2--md">
				            <select class="form-control" style="width:100px;" name="keyField">
				                <option value="id" 	<c:if test="${search.keyField eq 'id'}"> 		selected</c:if>> 아이디 </option>
				                <option value="title" 	<c:if test="${search.keyField eq 'title'}"> 	selected</c:if>> 제목 </option>
				                <option value="content" <c:if test="${search.keyField eq 'content'}"> 	selected</c:if>> 내용 </option>
				            </select>
			            </div>
			            <div class="header-wrap" style="float: right;">
				            <input name="keyWord" class="au-input au-input--xl" style="min-width:100px!important;width:200px;height:40px;" type="text" value="${search.keyWord}">
				            <button class="au-btn--submit" style="width:40px;height:40px;" onClick="check_search()">
			                  <i class="zmdi zmdi-search"></i>&nbsp;
			                </button>
		                </div>
		            </td>
	            </tr>
	            <tr>
	            <!-- 리스트 선택 -->
		            <td align=Right>
			            <select name="listNum" class="form-control" style="width:150px;" onchange="listCnt_search()" >
			            	<option value="2"   <c:if test="${search.listNum eq '2'  }"> selected</c:if>> 2개씩보기  </option>
			            	<option value="20"  <c:if test="${search.listNum eq '20' }"> selected</c:if>> 20개씩보기  </option>
							<option value="50"  <c:if test="${search.listNum eq '50' }"> selected</c:if>> 50개씩보기  </option>
							<option value="100" <c:if test="${search.listNum eq '100'}"> selected</c:if>> 100개씩보기</option>
						</select>
					</td>
	
	       		</tr>                
	        </table>
        </div>
		<!-- 페이지 이동시 값 유지를 위한 hidden 값 -->
		<input type="hidden" name="listNumOrg" 	value="${search.listNum}" /> 
        <input type="hidden" name="keyFieldOrg" value="${search.keyField }" /> 
        <input type="hidden" name="keyWordOrg" 	value="${search.keyWord }" />
        <input type="hidden" name="page" 		value="${search.page}" /> 
        <input type="hidden" name="pageYN" 		value="Y" />
        
    </form:form>   


	<div align=left style="float: left; width: 50%;">게시물수 : ${page.totalCount}개 </div>
     <!-- 글쓰기 -->
    <div align=Right style="float: right; width: 50%;margin-bottom: 10px;">
		<button class="btn btn-success btn-sm" onClick="go_write(${authInfo.id })">
			<i class="fa fa-pencil-square-o"></i>
			글쓰기
		</button>
		<button class="btn btn-success btn-sm" onclick="doExcelDownloadProcess('/board/downloadExcelFile')">Excel</button>
    </div>
    
    <!-- 엑셀 다운로드 폼-->
	<form id="excelDown" name="excelDown" method="post" enctype="multipart/form-data">
        <input type="hidden" name="keyField" 	value="${search.keyField }" /> 
        <input type="hidden" name="keyWord" 	value="${search.keyWord }" />
        <input type="hidden" name="pageYN" 		value="N" />
	</form>
    
    <table class="table table-borderless table-striped table-earning" id="table">
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
                <th>제 목</th>
                <th>작성자</th>
                <th>작성일</th>
                <th>조 회</th>
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
                                        <td align=center>${resultlist[i].boardIdx }</td>
                                        <td>
                                            <!-- depth --> 
<%--                                             <c:if test="${resultlist[i].depth != 0 }">
                                                <c:forEach begin="0" end="${resultlist[i].depth}">
                                                </c:forEach>
                                            </c:if> --%>
                                            <a href="javascript:read(${resultlist[i].boardIdx })" 
                                            	onmouseover="contentprev('${resultlist[i].boardIdx}');showlayer('layer1');" 
                                                onmouseout="hidelayer('layer1');">${resultlist[i].title }</a>
                                        </td>
                                        <td align=center>${resultlist[i].id }</td>
                                        <td align=center>${resultlist[i].ins_dt }</td>
                                        <td align=center>${resultlist[i].hitCnt }</td>
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
        <input type="hidden" name="boardIdx"/>
		<input type="hidden" name="listNum" 	value="${search.listNum}" /> 
        <input type="hidden" name="keyField" 	value="${search.keyField }" /> 
        <input type="hidden" name="keyWord" 	value="${search.keyWord }" />
        <input type="hidden" name="page" 		value="${search.page}" /> 
        <input type="hidden" name="pageYN" 		value="Y" />
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

