<%@ page language="java" contentType = "application/vnd.ms-excel;charset=euc-kr" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-Disposition", "attachment; filename=excelTrustList.xls");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
</head>
<body>
<table border=1>
<tr>
<td>번호</td>
<td>부서명</td>
<td>위탁사무명</td>
<td>위탁유형</td>
<td>위탁유형2</td>
<td>예산액</td>
<td>수탁기관명</td>
<td>산정방법</td>
<td>최초위탁일</td>
<td>현위탁기관</td>
</tr>
<c:forEach var="row" items="${list }" varStatus="status">
<tr><td>${row.RNUM }</td>
<td>${row.OW_DEPT_NM }</td>
<td>${row.OW_NAME }</td>
<td>
<c:choose>
    <c:when test="${row.OW_TYPE1 == '01'}">
         민간위탁
	</c:when>
    <c:when test="${row.OW_TYPE1 == '02'}">
         용역
    </c:when>
    <c:when test="${row.OW_TYPE1 == '03'}">
         대행·공공위탁
    </c:when>
    <c:when test="${row.OW_TYPE1 == '04'}">
         연구용역
    </c:when>
</c:choose>
</td>
<td>
<c:choose>
    <c:when test="${row.OW_TYPE2 == '01'}">
         사무  
	</c:when>
    <c:when test="${row.OW_TYPE2 == '02'}">
         시설
    </c:when>    
</c:choose>
</td>
<td>${row.BG_TOTAL_COST }</td>
<td>${row.CS_INST_NAME }</td>
<td>
<c:choose>
    <c:when test="${row.CS_CHOICE_METHOD == '01'}">
         공개모집  
	</c:when>
    <c:when test="${row.CS_CHOICE_METHOD == '02'}">
         수의계약
    </c:when>
    <c:when test="${row.CS_CHOICE_METHOD == '03'}">
         쟤계약
    </c:when>
    <c:when test="${row.CS_CHOICE_METHOD == '04'}">
         기타
    </c:when>
</c:choose>
</td>
<td>${row.FIRST_TRUST_DATE }</td>
<td>${row.CUR_TRUST_STDATE }~${row.CUR_TRUST_EDDATE }</td>
</tr>
</c:forEach>
</table>
</body>
</html>