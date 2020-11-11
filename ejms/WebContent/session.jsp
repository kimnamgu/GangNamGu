<%@ page contentType="text/html;charset=euc-kr"%>
<br><br>== Session List==<br>
<%
java.util.Enumeration enu_session = session.getAttributeNames();
while ( enu_session.hasMoreElements() ) {
	String key = enu_session.nextElement().toString();
	String value = session.getAttribute(key).toString();
	out.println(key + " : " + value + "<br>");
}
%>
<br><br>== Cookie List ==<br>
<%
Cookie[] cookArr = request.getCookies();
for ( int i = 0; cookArr != null && i < cookArr.length; i++ ) {
	out.println(cookArr[i].getName() + " : " + cookArr[i].getValue() + "<br>");
}
%>