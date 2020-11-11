<%@ page contentType="text/html;charset=euc-kr" %>
<jsp:directive.page import="java.io.PrintWriter"/>
<jsp:directive.page import="java.sql.Connection"/>
<jsp:directive.page import="java.sql.DriverManager"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.PreparedStatement"/>
<jsp:directive.page import="nexti.ejms.common.appInfo"/>
<jsp:directive.page import="javax.naming.Context"/>
<jsp:directive.page import="javax.sql.DataSource"/>
<jsp:directive.page import="java.util.Properties"/>
<jsp:directive.page import="javax.naming.InitialContext;"/>

<%
	String WAS = null;
	Connection con = null;
	Context initContext = null;
	Context envContext = null;
	boolean autuCommit;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
		
		try {
			String was_name = null;
			DataSource ds = null;
			
			was_name = "WEBLOGIC";
			if ( WAS == null || WAS.equals(was_name) ) {
				try {
					Properties prop = new Properties();
					prop.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
					envContext = new InitialContext(prop);
					ds = (DataSource)envContext.lookup("ejmsDs");
					WAS = was_name;
				} catch ( Exception e ) {
					WAS = null;
				}
			}
			con = ds.getConnection();
			if ( con != null ) {
				String sql = "select '1' from dual";		
				pstmt = con.prepareStatement(sql.toString());
				rs = pstmt.executeQuery();
				con.commit();
		    	//con.setAutoCommit(autuCommit);
		    }
	} catch ( Exception e ) { 
			try	{
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch ( ClassNotFoundException cnfe )	{
				cnfe.printStackTrace();
			} finally {
				con.close();
				out.println("error");
				//e.printStackTrace();
			}
		} finally {
			try { con.close(); } catch ( Exception e ) {}
			try { envContext.close(); } catch ( Exception e ) {}
			try { initContext.close(); } catch ( Exception e ) {}
		}
			
		

		
%>