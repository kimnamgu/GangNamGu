/**
 * 작성일: 2010.08.26
 * 작성자: 대리 서동찬
 * 모듈명: 메세지 연계 dao(Active Post)
 * 설명: 
 */
package nexti.ejms.messanger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.util.Utils;

public class MessangerRelayDAO {
	private static Logger logger = Logger.getLogger(MessangerRelayDAO.class);
	
	//배부대기상태:전체취합대상부서담당자
	public MessangerRelayBean get_COLLECT_START(MessangerRelayBean mrBean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MessangerRelayBean result = null;
		
		try {
			StringBuffer sql = new StringBuffer();			
			sql.append("SELECT DOCTITLE, SUMMARY, U1.USER_SN SND_USER, U2.USER_SN RCV_USER \n");
			sql.append("FROM DOCMST D, TGTDEPT T, USR U1, USR U2 \n");
			sql.append("WHERE D.SYSDOCNO = ? \n");
			sql.append("AND D.SYSDOCNO = T.SYSDOCNO AND D.CHRGUSRCD = U1.USER_ID AND T.TGTDEPTCD = U2.DEPT_ID \n");
			sql.append("AND D.DOCSTATE = '04' AND T.SUBMITSTATE = '01' AND U2.USE_YN = 'Y' \n");
			sql.append("AND NVL(U2.GRADE_ID, '016') >= '016' \n");
			sql.append("AND NVL(U2.DELIVERY_YN, 'N') = (SELECT DECODE(COUNT(*), 0, 'N', 'Y') \n");
			sql.append("                                FROM DOCMST DD, TGTDEPT TT, USR UU \n");
			sql.append("                                WHERE DD.SYSDOCNO = D.SYSDOCNO AND TT.TGTDEPTCD = T.TGTDEPTCD \n");
			sql.append("                                AND DD.SYSDOCNO = TT.SYSDOCNO AND TT.TGTDEPTCD = UU.DEPT_ID \n");
			sql.append("                                AND UU.DELIVERY_YN = 'Y') \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			int idx = 0;
			pstmt.setInt(++idx, mrBean.getSysdocno());
			rs = pstmt.executeQuery();
			
			while ( rs.next() ) {
				if ( result == null ) {
					result = new MessangerRelayBean();
					result.setDoc_name_title(Utils.nullToEmptyString(rs.getString("DOCTITLE")));
					result.setDoc_desc_content(Utils.nullToEmptyString(rs.getString("SUMMARY")));
					result.setSnd_user_usersn(Utils.nullToEmptyString(rs.getString("SND_USER")));
					result.setList(new ArrayList());
				}
				result.getList().add(new String(Utils.nullToEmptyString(rs.getString("RCV_USER"))));
			}
		} catch ( Exception e ) {
			logger.error("ERROR", e);
		} finally {
			ConnectionManager.close(con, pstmt, rs);
		}
		
		return result;
	}
	
	//입력대기상태:전체취합대상부서입력담당자
	public MessangerRelayBean get_COLLECT_START_DELIVERY(MessangerRelayBean mrBean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MessangerRelayBean result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT DOCTITLE, SUMMARY, U1.USER_SN SND_USER, U2.USER_SN RCV_USER \n");
			sql.append("FROM DOCMST D, TGTDEPT T, USR U1, USR U2, INPUTUSR I \n");
			sql.append("WHERE D.SYSDOCNO = ? \n");
			sql.append("AND D.SYSDOCNO = T.SYSDOCNO AND D.CHRGUSRCD = U1.USER_ID AND T.TGTDEPTCD = U2.DEPT_ID \n");
			sql.append("AND T.SYSDOCNO = I.SYSDOCNO AND T.TGTDEPTCD = I.TGTDEPT AND I.INPUTUSRID = U2.USER_ID \n");
			sql.append("AND D.DOCSTATE = '04' AND T.SUBMITSTATE = '02' AND I.INPUTSTATE = '01' AND U2.USE_YN = 'Y' \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			int idx = 0;
			pstmt.setInt(++idx, mrBean.getSysdocno());
			rs = pstmt.executeQuery();
			
			while ( rs.next() ) {
				if ( result == null ) {
					result = new MessangerRelayBean();
					result.setDoc_name_title(Utils.nullToEmptyString(rs.getString("DOCTITLE")));
					result.setDoc_desc_content(Utils.nullToEmptyString(rs.getString("SUMMARY")));
					result.setSnd_user_usersn(Utils.nullToEmptyString(rs.getString("SND_USER")));
					result.setList(new ArrayList());
				}
				result.getList().add(new String(Utils.nullToEmptyString(rs.getString("RCV_USER"))));
			}
		} catch ( Exception e ) {
			logger.error("ERROR", e);
		} finally {
			ConnectionManager.close(con, pstmt, rs);
		}
		
		return result;
	}
	
	//입력대기상태:취합대상부서입력담당자
	public MessangerRelayBean get_DELIVERY(MessangerRelayBean mrBean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MessangerRelayBean result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT DOCTITLE, SUMMARY, U1.USER_SN SND_USER, U2.USER_SN RCV_USER \n");
			sql.append("FROM DOCMST D, TGTDEPT T, USR U1, USR U2, INPUTUSR I \n");
			sql.append("WHERE D.SYSDOCNO = ? AND T.TGTDEPTCD = ? \n");
			sql.append("AND D.SYSDOCNO = T.SYSDOCNO AND D.CHRGUSRCD = U1.USER_ID AND T.TGTDEPTCD = U2.DEPT_ID \n");
			sql.append("AND T.SYSDOCNO = I.SYSDOCNO AND T.TGTDEPTCD = I.TGTDEPT AND I.INPUTUSRID = U2.USER_ID \n");
			sql.append("AND D.DOCSTATE = '04' AND T.SUBMITSTATE = '02' AND I.INPUTSTATE = '01' AND U2.USE_YN = 'Y' \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			int idx = 0;
			pstmt.setInt(++idx, mrBean.getSysdocno());
			pstmt.setString(++idx, mrBean.getDeptcode());
			rs = pstmt.executeQuery();
			
			while ( rs.next() ) {
				if ( result == null ) {
					result = new MessangerRelayBean();
					result.setDoc_name_title(Utils.nullToEmptyString(rs.getString("DOCTITLE")));
					result.setDoc_desc_content(Utils.nullToEmptyString(rs.getString("SUMMARY")));
					result.setSnd_user_usersn(Utils.nullToEmptyString(rs.getString("SND_USER")));
					result.setList(new ArrayList());
				}
				result.getList().add(new String(Utils.nullToEmptyString(rs.getString("RCV_USER"))));
			}
		} catch ( Exception e ) {
			logger.error("ERROR", e);
		} finally {
			ConnectionManager.close(con, pstmt, rs);
		}
		
		return result;
	}
	
	//취합완료(공개,취합/입력자에게공개):전체취합대상부서취합,입력담당자
	public MessangerRelayBean get_COLLECT_COMP(MessangerRelayBean mrBean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MessangerRelayBean result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT DOCTITLE, SUMMARY, U1.USER_SN SND_USER, U2.USER_SN RCV_USER \n");
			sql.append("FROM DOCMST D, TGTDEPT T, USR U1, USR U2, INPUTUSR I \n");
			sql.append("WHERE D.SYSDOCNO = ? \n");
			sql.append("AND D.SYSDOCNO = T.SYSDOCNO AND D.CHRGUSRCD = U1.USER_ID AND T.TGTDEPTCD = U2.DEPT_ID \n");
			sql.append("AND T.SYSDOCNO = I.SYSDOCNO AND T.TGTDEPTCD = I.TGTDEPT AND I.INPUTUSRID = U2.USER_ID \n");
			sql.append("AND D.DOCSTATE = '06' AND OPENDT IN ('1900-01-01', '1900-01-04') AND U2.USE_YN = 'Y' \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			int idx = 0;
			pstmt.setInt(++idx, mrBean.getSysdocno());
			rs = pstmt.executeQuery();
			
			while ( rs.next() ) {
				if ( result == null ) {
					result = new MessangerRelayBean();
					result.setDoc_name_title(Utils.nullToEmptyString(rs.getString("DOCTITLE")));
					result.setDoc_desc_content(Utils.nullToEmptyString(rs.getString("SUMMARY")));
					result.setSnd_user_usersn(Utils.nullToEmptyString(rs.getString("SND_USER")));
					result.setList(new ArrayList());
				}
				result.getList().add(new String(Utils.nullToEmptyString(rs.getString("RCV_USER"))));
			}
		} catch ( Exception e ) {
			logger.error("ERROR", e);
		} finally {
			ConnectionManager.close(con, pstmt, rs);
		}
		
		return result;
	}
	
	//배부대기상태:취합대상부서담당자
	public List getDeliveryWaitUserList(int sysdocno, String deptcode) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT U.USER_SN \n");
			sql.append("FROM DOCMST D, TGTDEPT T, USR U \n");
			sql.append("WHERE D.SYSDOCNO = ? AND T.TGTDEPTCD = ? \n");
			sql.append("AND D.SYSDOCNO = T.SYSDOCNO AND T.TGTDEPTCD = U.DEPT_ID \n");
			sql.append("AND T.SUBMITSTATE = '01' AND U.USE_YN = 'Y' \n");
			sql.append("AND NVL(U.GRADE_ID, '016') >= '016' \n");
			sql.append("AND NVL(U.DELIVERY_YN, 'N') = (SELECT DECODE(COUNT(*), 0, 'N', 'Y') \n");
			sql.append("                                FROM DOCMST DD, TGTDEPT TT, USR UU \n");
			sql.append("                                WHERE DD.SYSDOCNO = D.SYSDOCNO AND TT.TGTDEPTCD = T.TGTDEPTCD \n");
			sql.append("                                AND DD.SYSDOCNO = TT.SYSDOCNO AND TT.TGTDEPTCD = UU.DEPT_ID \n");
			sql.append("                                AND UU.DELIVERY_YN = 'Y') \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			int idx = 0;
			pstmt.setInt(++idx, sysdocno);
			pstmt.setString(++idx, deptcode);
			rs = pstmt.executeQuery();
			
			while ( rs.next() ) {
				if ( result == null ) result = new ArrayList();
				result.add(new String(Utils.nullToEmptyString(rs.getString("USER_SN"))));
			}
		} catch ( Exception e ) {
			logger.error("ERROR", e);
		} finally {
			ConnectionManager.close(con, pstmt, rs);
		}
		
		return result;
	}
}