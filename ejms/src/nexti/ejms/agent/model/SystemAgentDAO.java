/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 시스템에이전트 dao
 * 설명:
 */
package nexti.ejms.agent.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;

public class SystemAgentDAO {
	private static Logger logger = Logger.getLogger(SystemAgentDAO.class);

	/**
	 * agent runtime 리스트 
	 */
	public List agentRuntimeList(String p_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List arList = null;

		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT A.P_ID, A.P_SEQ, A.P_TYPE, B2.CCDNAME P_TYPENM, ");
			selectQuery.append("	A.P_T1, A.P_T2, A.P_T3, A.P_T4, A.P_T5, A.P_T6, ");
			selectQuery.append("	B1.CCDNAME P_USE  ");
			selectQuery.append("FROM AGENT_DTL A, CCD B1, CCD B2 ");
			selectQuery.append("WHERE B1.CCDCD='019' ");
			selectQuery.append("  AND A.P_USE=B1.CCDSUBCD ");
			selectQuery.append("  AND B2.CCDCD='022' ");
			selectQuery.append("  AND A.P_TYPE=B2.CCDSUBCD ");
			selectQuery.append("  AND A.P_ID = ? ");
			selectQuery.append("ORDER BY A.P_SEQ ");
			
			con = ConnectionManager.getConnection();
			pstmt =	con.prepareStatement(
						selectQuery.toString(),
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
			pstmt.setString(1, p_id);
			
			rs = pstmt.executeQuery();
	
			arList = new ArrayList();
			
			while (rs.next()) {
				SystemAgentRuntimeBean arBean = new SystemAgentRuntimeBean();
				
				arBean.setP_id(rs.getString("P_ID"));
				arBean.setP_seq(rs.getInt("P_SEQ"));
				arBean.setP_type(rs.getString("P_TYPE"));
				arBean.setP_typenm(rs.getString("P_TYPENM"));
				arBean.setP_t1(rs.getString("P_T1"));
				arBean.setP_t2(rs.getString("P_T2"));
				arBean.setP_t3(rs.getString("P_T3"));
				arBean.setP_t4(rs.getString("P_T4"));
				arBean.setP_t5(rs.getString("P_T5"));
				arBean.setP_t6(rs.getString("P_T6"));
				arBean.setP_use(rs.getString("P_USE"));
				//arBean.setDb_pass(rs.getString("DB_PASS"));
				arList.add(arBean);				
			}	
	
		} catch(SQLException e) {
			logger.error("ERROR", e);
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}
		
		return arList;
	}

	/** agent runtime등록	*/
	public int insertSystemAgentRuntime (SystemAgentRuntimeBean bean) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("INSERT INTO AGENT_DTL ");
			insertQuery.append("	(P_ID,P_SEQ,P_TYPE, ");
			insertQuery.append("	P_T1,P_T2,P_T3,P_T4,P_T5,P_T6, ");
			insertQuery.append("	P_USE,MADE_MAN,MADE_DT) ");
			insertQuery.append("VALUES (?, ?, ?, ");
			insertQuery.append("	?, ?, ?, ?, ?, ?, ");
			insertQuery.append("	?, ?, to_char(sysdate, 'yyyy-mm-dd hh24:mi:ss') )");
	    	
			conn = ConnectionManager.getConnection();
			pstmt =	conn.prepareStatement(
					insertQuery.toString(),
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
			
			pstmt.setString(1, bean.getP_id());
			pstmt.setInt(2, getMaxSeq(bean.getP_id()));
			pstmt.setString(3, bean.getP_type())
			;
			pstmt.setString(4, bean.getP_t1());
			pstmt.setString(5, bean.getP_t2());
			pstmt.setString(6, bean.getP_t3());
			pstmt.setString(7, bean.getP_t4());
			pstmt.setString(8, bean.getP_t5());
			pstmt.setString(9, bean.getP_t6());
			
			pstmt.setString(10, bean.getP_use());
			//pstmt.setString(11, bean.getDb_pass());
			pstmt.setString(11, bean.getMade_man());
			
			result=pstmt.executeUpdate();
			
		 } catch (SQLException e) {
				logger.error("ERROR", e);
	     } finally {	       
				ConnectionManager.close(conn,pstmt);
	     }
		return result;
	}

	/** runtime max seq 가져오기	*/
	public int getMaxSeq(String p_id) {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int  maxSeq = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT NVL(MAX(P_SEQ),0)+1 MAX_SEQ FROM AGENT_DTL WHERE P_ID=? ");
		
			conn = ConnectionManager.getConnection();
			pstmt =	conn.prepareStatement(
						selectQuery.toString(),
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
			
			pstmt.setString(1,p_id);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				maxSeq = rs.getInt(1);
			}

		 } catch (SQLException e) {
				logger.error("ERROR", e);
	     } finally {	       
				ConnectionManager.close(conn,pstmt,rs);
	     }
		return maxSeq;
	}

	/** agent runtime 삭제	*/
	public int deleteSystemAgentRuntime (String p_id, int p_seq) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("DELETE FROM AGENT_DTL WHERE  P_ID=? AND  P_SEQ=? ");
	    	
			conn = ConnectionManager.getConnection();
			pstmt =	conn.prepareStatement(
					insertQuery.toString(),
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
			
			pstmt.setString(1, p_id);
			pstmt.setInt(2, p_seq);

			result=pstmt.executeUpdate();
			
		 } catch (SQLException e) {
				logger.error("ERROR", e);
	     } finally {	       
				ConnectionManager.close(conn,pstmt);
	     }
		return result;
	}

	/** agent runtime 수정	*/
	public int modifySystemAgentRuntime(SystemAgentRuntimeBean bean) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			StringBuffer updateQuery = new StringBuffer();
			updateQuery.append("UPDATE AGENT_DTL SET ");
			updateQuery.append("	P_TYPE=?, ");
			updateQuery.append("	P_T1=?, P_T2=?, P_T3=?, ");
			updateQuery.append("	P_T4=?, P_T5=?, P_T6=?, ");
			updateQuery.append("	P_USE=?, MADE_MAN=?, MADE_DT=to_char(sysdate, 'yyyy-mm-dd hh24:mi:ss') ");
			updateQuery.append("WHERE P_ID=? AND P_SEQ=? ");

			conn = ConnectionManager.getConnection();
			pstmt =	conn.prepareStatement(
					updateQuery.toString(),
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
			
			pstmt.setString(1, bean.getP_type());
			
			pstmt.setString(2, bean.getP_t1());
			pstmt.setString(3, bean.getP_t2());
			pstmt.setString(4, bean.getP_t3());

			pstmt.setString(5, bean.getP_t4());
			pstmt.setString(6, bean.getP_t5());
			pstmt.setString(7, bean.getP_t6());
			
			pstmt.setString(8, bean.getP_use());			
			//pstmt.setString(9, bean.getDb_pass());
			pstmt.setString(9, bean.getMade_man());
			
			pstmt.setString(10, bean.getP_id());
			pstmt.setInt(11, bean.getP_seq());
			
			result = pstmt.executeUpdate();
					
		 } catch (SQLException e) {
				logger.error("ERROR", e);
	     } finally {	       
				ConnectionManager.close(conn,pstmt);
	     }
		return result;
	}

	/**
	 * agent runtime detail 상세정보 
	 */
	public SystemAgentRuntimeBean agentRuntimeDtlInfo(String p_id, int p_seq) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SystemAgentRuntimeBean arBean = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT P_ID, P_SEQ, P_TYPE, ");
			selectQuery.append("	P_T1, P_T2, P_T3, P_T4, P_T5, P_T6, ");
			selectQuery.append("	P_USE ");
			selectQuery.append("FROM AGENT_DTL ");
			selectQuery.append("WHERE P_ID = ? AND P_SEQ=? ");
			selectQuery.append("ORDER BY P_SEQ ");
			
			con = ConnectionManager.getConnection();
			pstmt =	con.prepareStatement(
						selectQuery.toString(),
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
			pstmt.setString(1, p_id);
			pstmt.setInt(2, p_seq);
			
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				arBean = new SystemAgentRuntimeBean();
				
				arBean.setP_id(rs.getString("P_ID"));
				arBean.setP_seq(rs.getInt("P_SEQ"));
				arBean.setP_type(rs.getString("P_TYPE"));
				arBean.setP_t1(rs.getString("P_T1"));
				arBean.setP_t2(rs.getString("P_T2"));
				arBean.setP_t3(rs.getString("P_T3"));
				arBean.setP_t4(rs.getString("P_T4"));
				arBean.setP_t5(rs.getString("P_T5"));
				arBean.setP_t6(rs.getString("P_T6"));
				arBean.setP_use(rs.getString("P_USE"));
				//arBean.setDb_pass(rs.getString("DB_PASS"));
			}	
	
		} catch(SQLException e) {
			logger.error("ERROR", e);
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}
		
		return arBean;
	}
}
