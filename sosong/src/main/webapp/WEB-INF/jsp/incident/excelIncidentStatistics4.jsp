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
<td rowspan=3>����</td>
<td rowspan=3>�Ѱ�</td>
<td colspan=7>���᳻��</td>
<td rowspan=3>�¼���</td>
<td rowspan=3>������</td>							
</tr>
<tr>
<td rowspan=2>�Ұ�</td>
<td colspan=5>�¼�����</td>
<td rowspan=2>�м�</td>	            						
</tr>
<tr>
<td>�Ұ�</td>
<td>�¼�</td>
<td>�Ϻν¼�</td>
<td>������</td>
<td>��Ÿ</td>	            							
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