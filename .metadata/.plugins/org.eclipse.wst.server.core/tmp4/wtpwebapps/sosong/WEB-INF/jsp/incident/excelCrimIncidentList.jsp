<%@ page language="java" contentType = "application/vnd.ms-excel;charset=euc-kr" pageEncoding="EUC-KR"%>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-Disposition", "attachment; filename=excelCrimIncidentList.xls");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
</head>
<body>
<table border=1>
<tr>
	<td></td>
	<td>��Ǹ�</td>
	<td>���μ�</td>
	<td>���ð�����</td>
	<td>��ȣ��</td>
	<td>������</td>
	<td>���</td>
</tr>
<c:forEach var="row" items="${list }" varStatus="status">
<tr>
<td>${row.RNUM }</td>
<td>${row.INCDNT_NAME }</td>
<td>${row.DEPT_NAME }</td>
<td>${row.PERFORM_PERSON }</td>
<td>${row.CHRG_LAWYER }</td>
<td><fmt:parseDate value="${row.SUE_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM.dd"/></td>
<td>${row.LAST_RSLT_CONT }</td>
</tr>
</c:forEach>
</table>
</body>
</html>