<%@ page language="java" contentType = "application/vnd.ms-excel;charset=euc-kr" pageEncoding="EUC-KR"%>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-Disposition", "attachment; filename=excelAllIncidentList.xls");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
</head>
<body>
<table border=1>
<tr>
<td>��ȣ</td>
<td>��Ǹ�</td>
<td>����</td>
<td>�ǰ�</td>
<td>�Ұ��μ�</td>
<td>�Ұ�</td>
<td>���п���</td>
<td>�������</td>
<td>Ư�̻���</td>
</tr>
<c:forEach var="row" items="${list }" varStatus="status">
<tr>
<td>${row.RNUM }</td>
<td>${row.INCDNT_NAME }</td>
<td>${row.CMPLN_NAME }</td>
<td>${row.DFNDNT_NAME }</td>
<td>${row.DEPT_NAME }</td>
<td><fmt:formatNumber value="${row.LAWSUIT_VALUE}" pattern="###,###,###" /></td>
<td>
<c:choose>
   <c:when test="${row.WIN_LOSE_GB == '1'}">
       �¼�  
   </c:when>
   <c:when test="${row.WIN_LOSE_GB == '2'}">
       �м�
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