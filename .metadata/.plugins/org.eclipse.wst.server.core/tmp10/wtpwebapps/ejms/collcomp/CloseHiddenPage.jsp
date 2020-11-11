<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합완료 백그라운드 프로세스
 * 설명:
--%>
<%@ page contentType="text/html;charset=euc-kr" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
%>
<%
int sysdocno = 0;

if(request.getAttribute("sysdocno") != null) {
	sysdocno = Integer.parseInt(request.getAttribute("sysdocno").toString());
}
%>
<script language="javascript">
	parent.location.href="/collcompList.do?sysdocno=<%=sysdocno%>";
</script>
