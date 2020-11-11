/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 에이전트관련 메소드
 * 설명:
 */
package nexti.ejms.agent;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.util.Base64;
import nexti.ejms.util.crypto.SeedCipher;

public class AgentUtil {
	private static Logger logger = Logger.getLogger(AgentUtil.class);

	/**
	 * Agent시작로그
	 * @param p_id
	 * @param p_seq
	 * @return
	 */
	public static long AgentlogStart(String p_id, int p_seq) {
		Connection DBConn = null;		
		String sql = null;
		ResultSet rs=null;
		long lSeq = 0;
		PreparedStatement pstmt = null;
		
		try {
			DBConn = ConnectionManager.getConnection();
			
			sql = "SELECT NVL(MAX(LSEQ),0)+1 AS LSEQ FROM AGENT_LOG ";
			pstmt = DBConn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) { lSeq = rs.getLong("LSEQ"); }
			else { lSeq = -1; }
			pstmt.close();
			
			sql = "INSERT INTO AGENT_LOG (LSEQ,P_ID,P_SEQ,RUN_DT,RUN_RESULT) VALUES (?, ?, ?,TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS'), '1') ";
			pstmt = DBConn.prepareStatement(sql);
			pstmt.setLong(1 ,lSeq);
			pstmt.setString(2, p_id);
			pstmt.setInt(3, p_seq);
			pstmt.executeUpdate();
			pstmt.close();
			try { DBConn.commit(); } catch (Exception e) {}
		} catch (Exception e) {
			try { DBConn.rollback(); } catch (Exception ex) {}
			logger.error("error", e);
			ConnectionManager.close(DBConn,pstmt,rs);
			return -1;
		} finally {
	    	ConnectionManager.close(DBConn,pstmt,rs); 	   
		}
		return lSeq;
	}
	
	/**
	 * Agent종료로그
	 * @param num
	 */
	public static void AgentlogEnd(long num) {
		Connection DBConn = null;
		String sql = null;
		PreparedStatement pstmt = null;
		
		try {
			DBConn = ConnectionManager.getConnection();
			
			sql = "UPDATE AGENT_LOG SET RUN_RESULT = '0' WHERE LSEQ = ? ";
			pstmt = DBConn.prepareStatement(sql);
			pstmt.setLong(1, num);
			pstmt.executeUpdate();
			pstmt.close();
			DBConn.commit();
		} catch (Exception e) {
			logger.error("error", e);
			try {DBConn.rollback();}catch(Exception e2) {}
		} finally {
	    	ConnectionManager.close(DBConn,pstmt); 	   
		}
	}
	
	/**
	 * Agent에러로그
	 * @param lSeq
	 */
	public static void AgentlogError(long lSeq) {
		Connection DBConn = null;
		String sql = null;
		PreparedStatement pstmt = null;

		try {
			DBConn = ConnectionManager.getConnection();
			
			sql = "UPDATE AGENT_LOG SET RUN_RESULT = '2' WHERE LSEQ = ? ";
			pstmt = DBConn.prepareStatement(sql);
			pstmt.setLong(1 ,lSeq);
			pstmt.executeUpdate();
			pstmt.close();
			DBConn.commit();
		} catch (Exception e) {
			logger.error("error", e);
			try {DBConn.rollback();}catch(Exception e2) {}
		} finally {
	    	ConnectionManager.close(DBConn,pstmt); 	   
		}
	}	

	/**
	 * 외부파일실행
	 * 절대경로로 실행파일 호출
	 */
	public static String runexec(String cmd)
	{	
		Process process = null;
		InputStream in = null;
		String returnData = "";
	
		try {
			process = Runtime.getRuntime().exec(cmd);
			in = process.getInputStream(); // 혹은 getErrorStream
			int i;
			while ((i=in.read()) != -1) {
				returnData += (char)i;
			}
		} catch (Exception e) {
			logger.error("error", e);
		} finally {
			try { process.destroy(); } catch(Exception e) {}
			try { in.close(); } catch(Exception e) {}
		}
		return returnData;
	}
	
	public static void Disconnection(Connection conn)
	{
		try {
			ConnectionManager.close(conn);
		} catch (Exception e) {
			logger.error("EXCEPTION : "+e);
		}
	}
	
	public static Connection getConnection(Object obj)
	{
		Connection conn=null;
		try {
			conn = ConnectionManager.getConnection();
			if(conn==null) return null;
			conn.setAutoCommit(false);
		} catch (Exception e) {
			logger.error("EXCEPTION : "+e);
			conn = null;
		}
		return conn;
	}
	public static Connection getConnection()
	{
		Connection conn=null;
		try {
			conn = ConnectionManager.getConnection();
			if(conn==null) return null;
			conn.setAutoCommit(false);
		} catch (Exception e) {
			logger.error("EXCEPTION : "+e);
			conn = null;
		}
		return conn;
	}
	
	public static String encryptSeed(String sText)
	{
		String key = "cktpeowjdqhrltnf"; //key값은 16자리로 하여야 한다 (16byte)
        SeedCipher seed = new SeedCipher();
        String result = "";
        try{
        	result = Base64.encodeBytes(("00"+sText).getBytes(),Base64.DONT_BREAK_LINES);
        	byte[] encryptTextbyte = seed.encrypt(result, key.getBytes(), "UTF-8");
        	result = Base64.encodeBytes(encryptTextbyte,Base64.DONT_BREAK_LINES);
        }catch (Exception e) {
			//logger.error("EXCEPTION : ", e);
        }
        return result;
	}
	
	public static String decryptSeed(String sText)
	{
		String key = "cktpeowjdqhrltnf"; //key값은 16자리로 하여야 한다 (16byte)
        SeedCipher seed = new SeedCipher();
        String result = "";
        try{
        	byte[] encryptbytes = Base64.decode(sText);
        	result = seed.decryptAsString(encryptbytes, key.getBytes(), "UTF-8");
        	result = new String(Base64.decode(result)).substring(2);
        }catch (Exception e) {
			//logger.error("EXCEPTION : ", e);
        }
        return result;
	}	
}
