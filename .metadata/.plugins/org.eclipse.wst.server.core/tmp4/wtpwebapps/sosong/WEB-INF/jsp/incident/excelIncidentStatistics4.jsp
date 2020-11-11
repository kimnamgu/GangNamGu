<%@ page language="java" contentType = "application/vnd.ms-excel;charset=euc-kr" pageEncoding="EUC-KR"%>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-Disposition", "attachment; filename=excelIncidentStatistics4.xls");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
</head>
<body>
<table border=1>
<tr>
<td rowspan=3>구분</td>
<td rowspan=3>총계</td>
<td colspan=7>종결내역</td>
<td rowspan=3>승소율</td>
<td rowspan=3>진행중</td>							
</tr>
<tr>
<td rowspan=2>소계</td>
<td colspan=5>승소유형</td>
<td rowspan=2>패소</td>	            						
</tr>
<tr>
<td>소계</td>
<td>승소</td>
<td>일부승소</td>
<td>소취하</td>
<td>기타</td>	            							
</tr>
<c:forEach var="row" items="${list }" varStatus="status">
<tr>
<td>${row.FY }</td>
<td><fmt:formatNumber value="${row.MAXST}" pattern="###,###,###" /></td>
<td><fmt:formatNumber value="${row.MAXSD}" pattern="###,###,###" /></td>
<td><fmt:formatNumber value="${row.MAXSW}" pattern="###,###,###" /></td>
<td><fmt:formatNumber value="${row.WIN1}" pattern="###,###,###" /></td>
<td><fmt:formatNumber value="${row.WIN2}" pattern="###,###,###" /></td>
<td><fmt:formatNumber value="${row.WIN3}" pattern="###,###,###" /></td>
<td><fmt:formatNumber value="${row.WIN4}" pattern="###,###,###" /></td>
<td><fmt:formatNumber value="${row.LOSE}" pattern="###,###,###" /></td>
<td>${row.WINP }</td>
<td><fmt:formatNumber value="${row.ING}" pattern="###,###,###" /></td>
</tr>
</c:forEach>
</table>
</body>
</html>