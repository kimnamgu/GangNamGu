<%--
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����Ʈ����
 * ����:
--%>
<%@ page contentType="text/html;charset=euc-kr" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
%>
<% if ( nexti.ejms.common.appInfo.isGpkilogin() == false ){ %>
<logic:forward name="login"/>
<!--
	<script>
		var url = "<%=nexti.ejms.common.appInfo.getWeb_address()%>";
		if ( String(document.referrer).indexOf(url.substring(0, url.length - 2)) != -1 ) {
			window.open("about:blank", "_self").close();
		}
	</script>
-->
<% } else { %>
<script>document.location.href="/gpkisecureweb/install.html"</script>
<% } %>