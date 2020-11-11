<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: JSP페이지 테일
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
		<iframe id="actionFrame" name="actionFrame" width="0" height="0" title="actionFrame"></iframe>
	</body>
</html>