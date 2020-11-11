<%@ page language="java" contentType = "application/vnd.ms-excel;charset=euc-kr" pageEncoding="EUC-KR"%>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-Disposition", "attachment; filename=excelIncidentStatistics2.xls");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
</head>
<body>
<table border=1>
<tr>
<td>구분</td>
<td>계</td>
<td>착수금</td>
<td>승소사례금</td>	            							
</tr>
<c:forEach var="row" items="${list }" varStatus="status">
<tr>
<td>${row.CHRG_LAWYER }</td>
<td><fmt:formatNumber value="${row.SUMN}" pattern="###,###,###" /></td>
<td><fmt:formatNumber value="${row.NUM1}" pattern="###,###,###" /></td>
<td><fmt:formatNumber value="${row.NUM2}" pattern="###,###,###" /></td>
</tr>
</c:forEach>
</table>
</body>
</html>