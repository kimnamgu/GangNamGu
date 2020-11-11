/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 데이터베이스 커넥션매니저
 * 설명:
 */
package nexti.ejms.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class ConnectionManager {
	private static Logger logger = Logger.getLogger(ConnectionManager.class);
//	private static Calendar cal = Calendar.getInstance();
	public static String WAS = null;
	private static int cnt =0;
	public static int addCnt() {
		return ++cnt;
	}
	public static int subCnt() {
		return --cnt;
	}

	/**
	 * Connection 객체를 반환하는 메써드.
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		return getConnection(true);
	}
	
	/**
	 * Connection 객체를 반환하는 메써드.
	 * @param autuCommit(true / false)
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection(boolean autuCommit) throws SQLException {		
		Connection con = null;
		Context initContext = null;
		Context envContext = null;
		
		try {
			String was_name = null;
			DataSource ds = null;
			
			was_name = "WEBLOGIC";
			if ( WAS == null || WAS.equals(was_name) ) {
				try {
					Properties prop = new Properties();
					prop.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
					envContext = new InitialContext(prop);
					//ds = (DataSource)envContext.lookup("ejmsDs");
					ds = (DataSource)envContext.lookup("jdbc/e_db");
					WAS = was_name;
				} catch ( Exception e ) {
					WAS = null;
				}
			}
	//		System.out.println(was_name+": was "+WAS+" , ds "+ds);
			
			was_name = "TOMCAT";
			//if ( WAS == null || WAS.equals(was_name) ) {
				try {
					initContext = new InitialContext();
				    envContext = (Context)initContext.lookup("java:/comp/env");			
				  //ds = (DataSource)envContext.lookup("ejmsDs");
					ds = (DataSource)envContext.lookup("jdbc/e_db");
					WAS = was_name;
				} catch ( Exception e ) {
					WAS = null;
				}
		//	}
	//		System.out.println(was_name+": was "+WAS+" , ds "+ds);
			
			
			
			was_name = "JEUS";
			if ( WAS == null || WAS.equals(was_name) ) {
				try {
					initContext = new InitialContext();
					//ds = (DataSource)envContext.lookup("ejmsDs");
					ds = (DataSource)envContext.lookup("jdbc/e_db");
					
					WAS = was_name;
				} catch ( Exception e ) {
					WAS = null;
				}
			}
	//		System.out.println(was_name+": was "+WAS+" , ds "+ds);
			
			con = ds.getConnection();
			if ( con != null ) {
				con.commit();
		    	con.setAutoCommit(autuCommit);
		    }
		} catch ( Exception e ) { 
			try	{
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch ( ClassNotFoundException cnfe )	{
				cnfe.printStackTrace();
			} finally {
				close(con);
				e.printStackTrace();
			}
			return null;
		} finally {
			try { envContext.close(); } catch ( Exception e ) {}
			try { initContext.close(); } catch ( Exception e ) {}
		}
		if(con != null){
//			cal.setTimeInMillis(System.currentTimeMillis());
//			System.out.println("conCheck : opened "+addCnt()+" "+cal.getTime());
//			logger.info("conCheck : opened "+addCnt()+" "+cal.getTime());
		}
		return con;
	}
	
	public static void close(Connection con, PreparedStatement pstmt, ResultSet rs) {
		if ( rs != null ) {
			try {
				rs.close();
				rs = null;
			} catch ( Exception e ) {
				if(rs != null){
					System.out.println("DOUBLE_CATCH RS");
					try{
						rs.close();
						rs = null;
					}catch(Exception ee){}
				}
			}
		}
		close(con, pstmt);		
	}
	
	public static void close(Connection con, PreparedStatement pstmt) {
		if ( pstmt != null ) {
			try {
				pstmt.close();
				pstmt = null;
			} catch ( Exception e ) {
				if(pstmt != null){
					System.out.println("DOUBLE_CATCH PSTMT");
					try{
						pstmt.close();
						pstmt = null;
					}catch(Exception ee){}
				}
			}
		}
		close(con);
	}
	
	public static void close(Connection con) {
		if ( con != null ) {
			try {
				con.close();
				con = null;
			} catch ( Exception e ) {
				if(con != null){
					System.out.println("DOUBLE_CATCH CON");
					try{
						con.close();
						con = null;
					}catch(Exception ee){}
				}
			}
//			cal.setTimeInMillis(System.currentTimeMillis());
			//+" "+cal.getTime()
//			System.out.println("conCheck : closed : "+subCnt()+" "+cal.getTime());
//			logger.info("conCheck : closed "+subCnt()+" "+cal.getTime());
		}
	}
	
	public static void close(PreparedStatement pstmt, ResultSet rs) {
		if ( rs != null ) {
			try {
				rs.close();
				rs = null;
			} catch ( Exception e ) {
				if(rs != null){
					System.out.println("DOUBLE_CATCH RS");
					try{
						rs.close();
						rs = null;
					}catch(Exception ee){}
				}
			}
		}
		close(pstmt);		
	}
	
	public static void close(PreparedStatement pstmt) {
		if ( pstmt != null ) {
			try {
				pstmt.close();
				pstmt = null;
			} catch ( Exception e ) {
				System.out.println("DOUBLE_CATCH PSTMT");
				if(pstmt != null){
					try{
						pstmt.close();
						pstmt = null;
					}catch(Exception ee){}
				}
			}
		}
	}
}