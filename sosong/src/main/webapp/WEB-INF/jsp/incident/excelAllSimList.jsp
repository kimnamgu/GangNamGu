<%@ page language="java" contentType = "application/vnd.ms-excel;charset=euc-kr" pageEncoding="EUC-KR"%>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-Disposition", "attachment; filename=excelAllSimList.xls");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
</head>
<body>
<table border=1>
<tr>
<td>번호</td>
<td>사건번호</td>
<td>사건명</td>
<td>소송주관부서</td>
<td>접수일자</td>
<td>변호사명</td>
<td>소송결과</td>
<td>판결일자</td>
<td>비고</td>
</tr>
<c:forEach var="row" items="${list }" varStatus="status">
<tr>
<td>${row.RNUM }</td>
<td>${row.ICDNT_NO }</td>
<td>${row.INCDNT_NAME }</td>
<td>${row.DEPT_NAME }</td>
<td><fmt:parseDate value="${row.ACCEPT_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/></td>
<td>${row.CHRG_LAWYER }</td>
<td>
<c:set var="cont_gb1" value="${row.JUDGE_CONT_GB1}" />
<c:set var="cont" value="${row.JUDGE_CONTENT}" />
<c:choose>							
<c:when test="${cont_gb1 eq NULL}">
${cont}
</c:when>
<c:otherwise>
${cont_gb1}
</c:otherwise>
</c:choose>
</td>
<td><fmt:parseDate value="${row.JUDGE_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/></td>
<td>
<c:choose>
   <c:when test="${row.INCDNT_GB == '1'}">
       행정  
   </c:when>
   <c:when test="${row.INCDNT_GB == '2'}">
       민사
   </c:when>
   <c:when test="${row.INCDNT_GB == '3'}">
       국가
   </c:when>    
</c:choose>

<c:choose>
   <c:when test="${row.ICDNT_TRIAL_NO == '11'}">
   1심  
   </c:when>
   <c:when test="${row.ICDNT_TRIAL_NO == '21'}">
   2심
   </c:when>
   <c:when test="${row.ICDNT_TRIAL_NO == '31'}">
   3심
   </c:when>    
</c:choose>
</td>
<td>${row.LAST_RSLT_CONT }</td>
<td>${row.NOTE }</td>
</tr>
</c:forEach>
</table>
</body>
</html>