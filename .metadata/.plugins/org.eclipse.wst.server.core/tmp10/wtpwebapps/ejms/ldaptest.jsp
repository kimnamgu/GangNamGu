<%@ page contentType="text/html;charset=euc-kr" %>
<jsp:directive.page import="java.io.PrintWriter"/>
<jsp:directive.page import="java.sql.Connection"/>
<jsp:directive.page import="java.sql.DriverManager"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.PreparedStatement"/>
<jsp:directive.page import="nexti.ejms.common.appInfo"/>
<%
boolean onlySelect			= true;
String result						= "NO UPDATE";
String logdate					= "00000000000000";
String logseq						= "1";

Connection con 					= null;
PreparedStatement pstmt	= null;
ResultSet rs						= null;
String dbType			= appInfo.getSidoldapDbtype();
String dbIp				= appInfo.getSidoldapDbip();
String dbUser			= appInfo.getSidoldapDbuser();
String dbPass			= appInfo.getSidoldapDbpass();
String sysgbn			= appInfo.getSidoldapSysgbn();
PrintWriter pw		= response.getWriter();

try {
	Class.forName(dbType);
	con = DriverManager.getConnection(dbIp, dbUser, dbPass);
	
	StringBuffer sql = new StringBuffer();
	if ( !onlySelect ) {
		sql.append(" UPDATE CMMANLDLASTWRK \n");
		sql.append(" SET LOG_DATE = '" + logdate + "', LOG_SEQ = " + logseq + " \n");
		sql.append(" WHERE SYS_GBN = '" + sysgbn + "' \n");
		
		pstmt = con.prepareStatement(sql.toString());
	}

	if ( onlySelect || pstmt.executeUpdate() > 0 ) {
		try { pstmt.close(); } catch ( Exception e ) {}
		
		sql.delete(0, sql.capacity());
		sql.append("SELECT * FROM CMMANLDLASTWRK WHERE SYS_GBN = '" + sysgbn + "' \n");

		pstmt =	con.prepareStatement(sql.toString());
		rs = pstmt.executeQuery();
		
		if ( rs.next() ) {
			result = sysgbn + " : " + logdate + " : " + logseq + "<br>";
			result += rs.getString("SYS_GBN") + " : " + rs.getString("LOG_DATE") + " : " + rs.getString("LOG_SEQ") + " : " + rs.getString("SYS_NAME");
		}
	}
} catch ( Exception e ) {
		e.printStackTrace();
} finally {
	try { rs.close(); } catch ( Exception e ) {}
	try { pstmt.close(); } catch ( Exception e ) {}
	try { con.close(); } catch ( Exception e ) {}
	pw.write(result);
	pw.flush();
	pw.close();
}
%>