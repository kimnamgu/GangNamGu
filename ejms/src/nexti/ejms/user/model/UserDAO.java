/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��������� dao
 * ����:
 */
package nexti.ejms.user.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.util.Utils;
import nexti.ejms.util.commfunction;

public class UserDAO {
	
	private static Logger logger = Logger.getLogger(UserDAO.class);

	/**
	 * ��ǥ�μ� �ڵ�(��û�� ��õ������, �������� ��õ������ �ٷ� �� STEP �Ʒ� ���)
	 */
	public String selectRepDept(String userId, String orggbn) throws Exception {		
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		String rep_orggbn = null;
		
		if(orggbn.equals("001")) rep_orggbn = "1";
		else rep_orggbn = "2";
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append(" SELECT DEPT_ID FROM DEPT   												    			\n"); 
			selectQuery.append(" WHERE DEPT_DEPTH = '"+rep_orggbn+"'                                            			\n");
			selectQuery.append(" START WITH DEPT_ID IN (SELECT DEPT_ID FROM USR WHERE UPPER(USER_ID) = UPPER('"+userId+"'))	\n");                    
			selectQuery.append(" CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID                                   				\n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				result = rs.getString("DEPT_ID");
			}			
			
		} catch(Exception e) {
			logger.error("ERROR : result" + result, e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		return result;
	}
	
	/** 
	 * �ֱٷα������� ����
	 * @throws Exception 
	 */
	public int updateLoginfo(String userid) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result =0;
		
		try {
			StringBuffer updateQuery = new StringBuffer();
			
			/*[USR_EXT] ���̺� ����
			updateQuery.append("UPDATE USR_EXT SET LSTLOGINDT = TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS') ");
			updateQuery.append("WHERE USER_ID = ? ");
		    */
			
			updateQuery.append(" UPDATE USR SET LSTLOGINDT = TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS') 	\n");
			updateQuery.append(" WHERE USER_ID = ? 														\n");
			
			conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(updateQuery.toString());		
		    
			pstmt.setString(1, userid);		

			result = pstmt.executeUpdate();		
			
		 } catch (Exception e) {
			 logger.error("ERROR", e);
	     } finally {	       
	    	 ConnectionManager.close(conn,pstmt);   
	     }	     
	     return result;
	}
	
	/** 
	 * �������� �α��ν� ����,���ɴ� ����
	 * @throws Exception 
	 */
	public int updateResearchLoginfo(String userid, String sex, int age) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result =0;
		
		try {
			StringBuffer updateQuery = new StringBuffer();

			/*[USR_EXT] ���̺� ����
			updateQuery.append("UPDATE USR_EXT ");
			updateQuery.append("SET LSTLOGINDT=TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS'), SEX=?, AGE=? ");
			updateQuery.append("WHERE USER_ID = ? ");
			*/
			
			updateQuery.append("UPDATE USR ");
			updateQuery.append("SET LSTLOGINDT=TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS'), SEX=?, AGE=? ");
			updateQuery.append("WHERE USER_ID = ? ");
		    
			conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(updateQuery.toString());		
		    
		    pstmt.setString(1, sex);
		    pstmt.setInt(2, age);
			pstmt.setString(3, userid);		
			
			result = pstmt.executeUpdate();			
			
		 } catch (Exception e) {
			 logger.error("ERROR", e);
			 ConnectionManager.close(conn,pstmt);
			 throw e;
	     } finally {	       
	    	 ConnectionManager.close(conn,pstmt);   
	     }	     
	     return result;
	}
	
	/**
	 * �����ID-�н����� ��Ī 	 
	 * return user_id
	 * @throws Exception 
	 */
	public String matchIdPwd(String user_id, String password) throws Exception {		
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			/*[USR_EXT] ���̺� ����
			selectQuery.append("SELECT A.USER_ID ");
			selectQuery.append("FROM USR A, USR_EXT B ");
			selectQuery.append("WHERE A.USER_ID = B.USER_ID ");
			selectQuery.append("  AND UPPER(A.USER_ID) = UPPER(NVL(?, '')) ");
			selectQuery.append("  AND UPPER(B.PASSWD) = UPPER(NVL(?, '')) ");	
			selectQuery.append("  AND A.USE_YN = '1' ");
			*/

			selectQuery.append(" SELECT USER_ID 							\n");
			selectQuery.append(" FROM USR 									\n");
			selectQuery.append(" WHERE UPPER(USER_ID) = UPPER(NVL(?, '')) 	\n");
			selectQuery.append(" AND UPPER(PASSWD) = UPPER(NVL(?, '')) 		\n");
			selectQuery.append(" AND USE_YN = 'Y' 							\n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, user_id);
			pstmt.setString(2, password);
			
			System.out.println("user_id  확인 : " + user_id);
			System.out.println("password  확인 : " + password);
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				result = rs.getString(1);
			}			
			
		} catch(Exception e) {
			logger.error("ERROR : USER_ID" + user_id, e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return result;
	}

	/**
	 * �ֹι�ȣ ��Ī 	 
	 * return user_id
	 * @throws Exception 
	 */
	public String matchUserSn(String user_sn) throws Exception {		
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();

			/*[USR_EXT] ���̺� ����
			selectQuery.append("SELECT A.USER_ID ");
			selectQuery.append("FROM USR A, USR_EXT B ");
			selectQuery.append("WHERE A.USER_ID = B.USER_ID ");
			selectQuery.append("  AND UPPER(A.USER_SN) = UPPER(NVL(?, '')) ");
			selectQuery.append("  AND A.USE_YN = '1' ");
			*/

			selectQuery.append(" SELECT USER_ID 							\n");
			selectQuery.append(" FROM USR 									\n");
			selectQuery.append(" WHERE UPPER(USER_SN) = UPPER(NVL(?, '')) 	\n");
			selectQuery.append(" AND USE_YN = 'Y' 							\n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, user_sn);
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				result = rs.getString(1);
			}			
			
		} catch(Exception e) {
			logger.error("ERROR : USER_SN" + user_sn, e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return result;
	}
	/**
	 * ����� ����
	 * @throws Exception 
	 */
	public String getDeptName(String userId) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String dept_name = "";
		
		try {
			int subLen = commfunction.getDeptFullNameSubstringIndex(userId);
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append(" SELECT NVL(TRIM(SUBSTR(DEPT_FULLNAME, "+subLen+")),DEPT_NAME) AS DEPT_NAME      			 \n");
			selectQuery.append(" FROM USR A 																			     \n");
			selectQuery.append(" WHERE UPPER(USER_ID) = UPPER(?)                                                    		 \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, userId);			
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				dept_name = rs.getString("DEPT_NAME");
			}			
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return dept_name;
	}	
	
	/**
	 * ����� ����
	 * @throws Exception 
	 */
	public UserBean getUserInfo(String userId) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserBean user = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT USER_ID, USER_SN, USER_NAME, A.DEPT_ID, A.DEPT_NAME, \n");
			selectQuery.append("       A.DEPT_FULLNAME, CLASS_ID, CLASS_NAME, POSITION_ID, POSITION_NAME, \n");
			selectQuery.append("       GRADE_ID, GRADE_NAME, NVL(USR_RANK, 99999) USR_RANK, EMAIL, TEL, \n");
			selectQuery.append("       MOBILE, NVL(A.USE_YN, 'Y') USE_YN, NVL(A.CON_YN, 'Y') CON_YN, PASSWD, NVL(MGRYN, 'N') MGRYN, \n");
			selectQuery.append("       CHRGUNITCD, GPKI_ID, SEX, AGE, LSTLOGINDT, \n");
			selectQuery.append("       A.CRTDT, A.CRTUSRID, A.UPTDT, A.UPTUSRID, DEPT_FAX, \n");
			selectQuery.append("       NVL(TRIM(EA_ID), USER_SN) EA_ID, \n");
			selectQuery.append("       DECODE(DELIVERY_CNT, 0, 'Y', NVL(DELIVERY_YN, 'N')) DELIVERY_YN \n");
			selectQuery.append("FROM USR A, DEPT B, \n");
			selectQuery.append("     (SELECT DEPT_ID, SUM(DECODE(DELIVERY_YN, 'Y', 1, 0)) DELIVERY_CNT \n");
			selectQuery.append("      FROM USR GROUP BY DEPT_ID) C \n");
			selectQuery.append("WHERE A.DEPT_ID = B.DEPT_ID AND A.DEPT_ID = C.DEPT_ID(+) \n");
			selectQuery.append("AND UPPER(USER_ID) = UPPER(?) \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, userId);			
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				user = new UserBean();
				user.setUser_id(Utils.nullToEmptyString(rs.getString("USER_ID")));
				user.setUser_sn(Utils.nullToEmptyString(rs.getString("USER_SN")));
				user.setUser_name(Utils.nullToEmptyString(rs.getString("USER_NAME")));
				user.setDept_id(Utils.nullToEmptyString(rs.getString("DEPT_ID")));
				user.setDept_name(Utils.nullToEmptyString(rs.getString("DEPT_NAME")));
				user.setDept_fullname(Utils.nullToEmptyString(rs.getString("DEPT_FULLNAME")));
				user.setClass_id(Utils.nullToEmptyString(rs.getString("CLASS_ID")));
				user.setClass_name(Utils.nullToEmptyString(rs.getString("CLASS_NAME")));
				user.setPosition_id(Utils.nullToEmptyString(rs.getString("POSITION_ID")));
				user.setPosition_name(Utils.nullToEmptyString(rs.getString("POSITION_NAME")));
				user.setGrade_id(Utils.nullToEmptyString(rs.getString("GRADE_ID")));
				user.setGrade_name(Utils.nullToEmptyString(rs.getString("GRADE_NAME")));
				user.setUsr_rank(rs.getInt("USR_RANK"));
				user.setEmail(Utils.nullToEmptyString(rs.getString("EMAIL")));
				user.setTel(Utils.nullToEmptyString(rs.getString("TEL")));
				user.setMobile(Utils.nullToEmptyString(rs.getString("MOBILE")));
				user.setUse_yn(Utils.nullToEmptyString(rs.getString("USE_YN")));
				user.setCon_yn(Utils.nullToEmptyString(rs.getString("CON_YN")));
				user.setPasswd(Utils.nullToEmptyString(rs.getString("PASSWD")));
				user.setMgryn(Utils.nullToEmptyString(rs.getString("MGRYN")));
				user.setDelivery_yn(Utils.nullToEmptyString(rs.getString("DELIVERY_YN")));
				user.setChrgunitcd(Utils.nullToEmptyString(rs.getString("CHRGUNITCD")));
				user.setEa_id(Utils.nullToEmptyString(rs.getString("EA_ID")));
				user.setGpki_id(Utils.nullToEmptyString(rs.getString("GPKI_ID")));
				user.setSex(Utils.nullToEmptyString(rs.getString("SEX")));
				user.setAge(rs.getInt("AGE"));
				user.setLstlogindt(Utils.nullToEmptyString(rs.getString("LSTLOGINDT")));
				user.setCrtdt(Utils.nullToEmptyString(rs.getString("CRTDT")));
				user.setCrtusrid(Utils.nullToEmptyString(rs.getString("CRTUSRID")));
				user.setUptdt(Utils.nullToEmptyString(rs.getString("UPTDT")));
				user.setUptusrid(Utils.nullToEmptyString(rs.getString("UPTUSRID")));
				user.setDept_fax(Utils.nullToEmptyString(rs.getString("DEPT_FAX")));
				
				user.setChrgunitnm(Utils.nullToEmptyString(getChrgNm(user.getDept_id(), user.getChrgunitcd())));
			}			
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return user;
	}	

	/**
	 * ����� ���� üũ
	 * @throws Exception 
	 */
	public boolean existedUser(String userId) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean ret = false;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) CNT ");
			selectQuery.append("FROM USR ");
			selectQuery.append("WHERE UPPER(USER_ID) = UPPER(?) ");					
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, userId);			
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				if (rs.getInt("CNT")>0) ret = true;
			}			
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return ret;
	}

	/**
	 * ����� ���� üũ : �ֹι�ȣ
	 * @throws Exception 
	 */
	public boolean existedUserSN(String usersn) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean ret = false;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) CNT ");
			selectQuery.append("FROM USR ");
			selectQuery.append("WHERE UPPER(USER_SN) = UPPER(?) ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, usersn);			
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				if (rs.getInt("CNT")>0) ret = true;
			}			
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return ret;
	}
		
	/**
	 * ������ ��Ī ��������
	 * @throws Exception 
	 */
	public String getChrgNm(String depcd, String chrcd) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT CHRGUNITNM ");
			selectQuery.append("FROM CHRGUNIT ");
			selectQuery.append("WHERE DEPT_ID = ? ");
			selectQuery.append("  AND CHRGUNITCD = ?");
		
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, depcd);
			pstmt.setString(2, chrcd);	
						
			rs = pstmt.executeQuery();	
			
			if ( rs.next() ){
				result = rs.getString("CHRGUNITNM");
			}					
	
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return result;
	}
		
	/**
	 * ����� �������ڵ� ����
	 * @param String user_id
	 * @param int chrg_code
	 * @return int ���ళ��
	 * @throws Exception 
	 */
	public int updateChrgunitcd(String user_id, int chrg_code) throws Exception{
		Connection conn = null;        
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			/*[USR_EXT] ���̺� ����
			String sql =
				"UPDATE USR_EXT " +
				"SET CHRGUNITCD = ? " +
				"WHERE USER_ID = ? ";
			*/	
			
			StringBuffer updateQuery = new StringBuffer();
			updateQuery.append(" UPDATE USR 			\n");
			updateQuery.append(" SET CHRGUNITCD = ? 	\n");
			updateQuery.append(" WHERE USER_ID = ? 		\n");
			
			conn = ConnectionManager.getConnection();
			
		    pstmt = conn.prepareStatement(updateQuery.toString());		
		    
		    pstmt.setInt(1, chrg_code);
		    pstmt.setString(2, user_id);		
			
			result = pstmt.executeUpdate();			
		 } catch (Exception e) {
			 logger.error("ERROR", e);
			 ConnectionManager.close(conn,pstmt);
			 throw e;
	     } finally {	       
	    	 ConnectionManager.close(conn,pstmt);   
	     }	 
	     
	     return result;
	}
	
	/**
	 * �α����� ����� �������ڵ� ��������(USR ���̺�)
	 * 
	 * @param usrid : �����ID
	 * 
	 * @return int : �������ڵ� 
	 * @throws Exception 
	 */
	public int getUsrChrgunitcd(String usrid) throws Exception{
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int chrgunitcd = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();

			/*[USR_EXT] ���̺� ����
			selectQuery.append("\n SELECT CHRGUNITCD  ");
			selectQuery.append("\n   FROM USR_EXT     ");
			selectQuery.append("\n  WHERE USER_ID = ? ");
			*/

			selectQuery.append("\n SELECT CHRGUNITCD  ");
			selectQuery.append("\n   FROM USR	      ");
			selectQuery.append("\n  WHERE USER_ID = ? ");
			
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setString(1, usrid);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				chrgunitcd = rs.getInt("CHRGUNITCD");
			}

		 } catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return chrgunitcd;
	}
	

	/** 
	 * �������� ����
	 * @throws Exception 
	 */
	public void insertLoginLog(String userid, String sessi, String method, String ip, String dept_id, String rep_dept) throws Exception{
		Connection conn = null;        
		PreparedStatement pstmt = null;
		
		try {
			StringBuffer insertQuery = new StringBuffer();

			insertQuery.append("INSERT INTO LOGINLOG(USER_ID, SEQ, LOGINTIME, SESSI, LOGIN_METHOD, LOGIN_IP, DEPT_ID, REP_DEPT) ");
			insertQuery.append("VALUES(?, LOGINSEQ.NEXTVAL, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), ?, ?, ?, ?, ?) ");			
		    
			conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(insertQuery.toString());		
		     
			pstmt.setString(1, userid);
			pstmt.setString(2, sessi);
			pstmt.setString(3, method);
			pstmt.setString(4, ip);		
			pstmt.setString(5, dept_id);		
			pstmt.setString(6, rep_dept);						
			
			pstmt.executeUpdate();
		 } catch (Exception e) {
			 logger.error("ERROR", e);
	     } finally {	       
	    	 ConnectionManager.close(conn,pstmt);   
	     }	     
	     return;
	}

	/** 
	 * 
	 */
	public UserBean getUserInfoForRegno(String user_sn) throws Exception{ 
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserBean result = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append(" SELECT DEPT_ID 		 \n");
			selectQuery.append(" FROM USR			     \n");
			selectQuery.append(" WHERE USER_SN LIKE ?    \n");
			
			con = ConnectionManager.getConnection();			
		    pstmt = con.prepareStatement(selectQuery.toString());		   
		    pstmt.setString(1, "%"+user_sn+"%");

			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = new UserBean();
				result.setDept_id(Utils.nullToEmptyString(rs.getString("DEPT_ID")));
			}
			
		} catch (SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}

		return result;
	}
	
	/** 
	 * �ű� ���̵� ���� ��ȣ : �ڵ����
	 */
	public String getNextIdSeq(Connection con, String resiNumberLen) throws Exception{ 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String gender_mf = null;

		if ( resiNumberLen.equals("1") || resiNumberLen.equals("3") ) {
			gender_mf = "'1','3'";
		} else if( resiNumberLen.equals("2") || resiNumberLen.equals("4") ) {
			gender_mf = "'2','4'";
		} else {
			gender_mf = "'1','3'";
		}
		String result = "";
		
		try {
			StringBuffer selectQuery = new StringBuffer();
//			selectQuery.append(" SELECT NVL(TO_CHAR(LPAD(TO_CHAR(MAX(NVL(TRANSLATE(SUBSTR(USER_ID,2,8),'0123456789'||SUBSTR(USER_ID,2,8),'0123456789'),0))+1),7,0)),0) \n");
//			selectQuery.append(" FROM USR                                                                     \n");
//			selectQuery.append(" WHERE NVL(SUBSTR(USER_SN, 7,1), 1) IN ("+gender_mf+")                        \n");
			selectQuery.append(" SELECT NVL(TO_CHAR(LPAD(TO_CHAR(MAX(NVL(SUBSTR(USER_ID,2,8),0))+1),7,0)),0)  \n");
			selectQuery.append(" FROM USR                                                                     \n");

						
		    pstmt = con.prepareStatement(selectQuery.toString());
			rs = pstmt.executeQuery();

			if (rs.next()) {		
				result = rs.getString(1);
				if(result.equals("0")) result = "0000001";
			}

		} catch ( SQLException e ) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(pstmt,rs);
		}

		return result;
	}
	
	/** 
	 * �ű� ���̵� ���� ��ȣ : �������
	 */
	public String getNextId(String gbn) throws Exception { 
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT '" + gbn + "' || LPAD(nvl(MAX(TRANSLATE(SUBSTR(USER_ID, 2), '0123456789'||SUBSTR(USER_ID, 2), '0123456789')), 0) + 1 , 7, 0) \n");
			sql.append("FROM USR \n");
			sql.append("WHERE USER_ID LIKE '" + gbn + "%' \n");
					
			con = ConnectionManager.getConnection();
		    pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			if ( rs.next() ) {		
				result = rs.getString(1);
			}

		} catch ( SQLException e ) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con, pstmt,rs);
		}

		return result;
	}
}