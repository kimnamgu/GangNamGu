<%--
 * 작성일: 2010.05.31
 * 작성자: 대리 서동찬
 * 모듈명: 건수연계
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
	String userid = (String)session.getAttribute("SSO_USER");
	String _URL_ = "http://112.16.129.51:9400/ssocount/ejms3.jsp?userid=" + userid;

	java.net.URL ejmsURL = new java.net.URL(_URL_);
	java.net.HttpURLConnection ejmsHttpURLConn = (java.net.HttpURLConnection)ejmsURL.openConnection();
	int responseCode = 0;
	try {
		ejmsHttpURLConn.connect();
		responseCode = ejmsHttpURLConn.getResponseCode();
	} catch ( Exception e ) {
		System.out.println("현황표시오류 : " + e);
	} finally {
		try { ejmsHttpURLConn.disconnect(); } catch ( Exception e ) {}
	}
	if ( responseCode == java.net.HttpURLConnection.HTTP_OK ) {
		out.println("<iframe src='" + _URL_ + "' width='195' height='111' frameborder='0' marginwidth='0' marginheight='0' scrolling='no' title='취합현황'></iframe>");
	} else {
		out.println("<table width='195' height='111' border='0' cellpadding='0' cellspacing='1' bgcolor='#d6b892'>");
		out.println("	<tr>");
		out.println("		<td bgcolor='#FFFFFF'>");
		out.println("			<table width='100%' height='100%' border='0' cellspacing='0' cellpadding='0' align='center'>");
		out.println("				<tr>");
		out.println("					<td height='27' style='padding:5pt 0pt 0pt 10pt;font-size:9pt'><font color='#fb7e07'>▶</font>&nbsp;<b>"+nexti.ejms.common.appInfo.getAppName()+"</b></td>");
		out.println("				</tr>");
		out.println("				<tr>");
		out.println("					<td height='36'>");
		out.println("						<table width='130' height='25' border='0' cellspacing='0' cellpadding='0' align='center' bgcolor='#f4f4f4'>");
		out.println("							<tr>");
		out.println("								<td style='padding:2pt 0pt 0pt 10pt;font-size:9pt'><font color='#565656'>- 서비스 준비 중</font></td>");
		out.println("							</tr>");
		out.println("						</table>");
		out.println("					</td>");
		out.println("				</tr>");
		out.println("				<tr>");
		out.println("					<td bgcolor='#edead9' height='5'></td>");
		out.println("				</tr>");
		out.println("			</table>");
		out.println("		</td>");
		out.println("	</tr>");
		out.println("</table>");
	}
%>