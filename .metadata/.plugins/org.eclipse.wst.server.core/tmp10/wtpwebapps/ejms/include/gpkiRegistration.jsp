<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���������� GPKI ������ ���
 * ����:
--%>
<%@ page contentType="text/html;charset=euc-kr"%>
<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
%>

<body onload="gpkiLogin(document.gpkiReg, 'registration', '/certOK.do')">
<form name="gpkiReg" method="post">
	<input type="hidden" name="challenge">
	<input type="hidden" name="type">
</form>
<%@ include file="/include/gpki.jsp" %>
</body>