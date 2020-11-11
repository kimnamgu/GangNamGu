<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: GPKI 인증페이지 추가파일
 * 설명:
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
		alert("GPKI 인증모듈에 오류가 발생하였습니다.\n\n[제어판]-[프로그램 추가/삭제]에서 \"GPKISecureWeb\" 삭제 후\n브라우져를 다시 실행하여 모듈을 재설치 하시거나\n일반로그인을 하시기 바랍니다");
	} finally {
		form.action = oldAction;
	}
}
</script>