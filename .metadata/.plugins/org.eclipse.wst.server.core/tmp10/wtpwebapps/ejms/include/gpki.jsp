<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: GPKI ���������� �߰�����
 * ����:
--%>
<%@ page language="java" pageEncoding="EUC-KR"%>
<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
%>

<%@ include file="/gpkisecureweb/gpkisecureweb.jsp" %>

<% String challenge =((GPKIHttpServletResponse)gpkiresponse).getChallenge(); %>

<script language='javascript' src='/gpkisecureweb/var.js'></script>
<script language='javascript' src='/gpkisecureweb/GPKIFunc.js'></script>
<script language='javascript' src='/gpkisecureweb/object.js'></script>
<script>
function gpkiLogin(form, type, url) {
	var oldAction = form.action;
	try {
    form.challenge.value = "<%=challenge%>"
    form.type.value = type;
    form.action = url;
		Login(form);
	} catch (exception) {
		alert("GPKI ������⿡ ������ �߻��Ͽ����ϴ�.\n\n[������]-[���α׷� �߰�/����]���� \"GPKISecureWeb\" ���� ��\n�������� �ٽ� �����Ͽ� ����� �缳ġ �Ͻðų�\n�Ϲݷα����� �Ͻñ� �ٶ��ϴ�");
	} finally {
		form.action = oldAction;
	}
}
</script>