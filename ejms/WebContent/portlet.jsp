<%@ page language="java" pageEncoding="EUC-KR"%>
<%
	String _SSO_ORG_ID_ = (String)session.getAttribute("SSO_ORG_ID");
	String _URL_ = "http://111.21.1.70:9400/ssocount/ejms5.jsp?userid=" + _SSO_ORG_ID_;

	java.net.URL ejmsURL = new java.net.URL(_URL_);
	java.net.HttpURLConnection ejmsHttpURLConn = (java.net.HttpURLConnection)ejmsURL.openConnection();
	int responseCode = 0;
	try {
		ejmsHttpURLConn.connect();
		responseCode = ejmsHttpURLConn.getResponseCode();
	} catch ( Exception e ) {
		System.out.println("��Ȳǥ�ÿ��� : " + e);
	} finally {
		try { ejmsHttpURLConn.disconnect(); } catch ( Exception e ) {}
	}
	if ( responseCode == java.net.HttpURLConnection.HTTP_OK ) {
		out.println("<iframe src='" + _URL_ + "' width='170' height='110' frameborder='0' marginwidth='0' marginheight='0' scrolling='no' title='������Ȳ'></iframe>");
	} else {
		out.println("<table width='170' height='70' border='0' cellpadding='0' cellspacing='1' bgcolor='#d6b892'>");
		out.println("	<tr>");
		out.println("		<td bgcolor='#FFFFFF'>");
		out.println("			<table width='100%' border='0' cellspacing='0' cellpadding='0' align='center'>");
		out.println("				<tr>");
		out.println("					<td height='27' style='padding:5pt 0pt 0pt 10pt;font-size:9pt'><font color='#fb7e07'>��</font>&nbsp;<b>"+nexti.ejms.common.appInfo.getAppName()+"</b></td>");
		out.println("				</tr>");
		out.println("				<tr>");
		out.println("					<td height='36'>");
		out.println("						<table width='130' height='25' border='0' cellspacing='0' cellpadding='0' align='center' bgcolor='#f4f4f4'>");
		out.println("							<tr>");
		out.println("								<td style='padding:2pt 0pt 0pt 10pt;font-size:9pt'><font color='#565656'>- ���� �غ� ��</font></td>");
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