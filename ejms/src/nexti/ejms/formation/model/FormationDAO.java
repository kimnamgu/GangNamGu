/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 조직도 dao
 * 설명:
 */
package nexti.ejms.formation.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.util.Utils;
import nexti.ejms.util.commfunction;

import org.apache.log4j.Logger;

public class FormationDAO {
	private static Logger logger = Logger.getLogger(FormationDAO.class);
	
	/**
	 * 조직도 불러오기 : 부서+사용자
	 * @param orggbn
	 * @param orgid
	 * @param searchKey
	 * @param searchValue
	 * @return
	 * @throws Exception
	 */
	public List getFormationDeptUser(String orggbn, String orgid, String searchKey, String searchValue, String userid) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List result = null;
		
		boolean ORG_USE = false;
		if ( "부산6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 && "".equals(orggbn) && "".equals(searchValue) ) {
			ORG_USE = true;
		}
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT LEVEL, GRPGBN, UPPER_DEPT_ID, UPPER_DEPT_NAME, UPPER_DEPT_FULLNAME, DEPT_ID, DEPT_NAME, DEPT_FULLNAME, MAIN_YN      \n");
			sql.append("FROM (                                                                                                                     \n");
			sql.append("      SELECT *                                                                                                             \n");
			sql.append("      FROM (                                                                                                               \n");
			
			if ( ORG_USE ) {
				sql.append("SELECT 2 GRPGBN, DECODE(D1.DEPT_DEPTH, 2, 'V00' || D1.ORGGBN || '0', D1.UPPER_DEPT_ID) UPPER_DEPT_ID, D2.DEPT_NAME UPPER_DEPT_NAME, \n");
			} else {
			sql.append("            SELECT 2 GRPGBN, D1.UPPER_DEPT_ID, D2.DEPT_NAME UPPER_DEPT_NAME,                                               \n");
			}
			
			sql.append("                   NVL(TRIM(SUBSTR(D2.DEPT_FULLNAME, ?)), D2.DEPT_FULLNAME) UPPER_DEPT_FULLNAME,                           \n");
			sql.append("                   D1.DEPT_ID, D1.DEPT_NAME,                                                                               \n");
			sql.append("                   NVL(TRIM(SUBSTR(D1.DEPT_FULLNAME, ?)), D1.DEPT_FULLNAME) DEPT_FULLNAME,                                 \n");
			sql.append("                   D1.MAIN_YN, D1.ORGGBN, D1.DEPT_RANK, 0 USR_RANK                                                         \n");
			sql.append("            FROM DEPT D1, DEPT D2                                                                                          \n");
			sql.append("            WHERE D1.UPPER_DEPT_ID = D2.DEPT_ID(+)                                                                         \n");
			sql.append("            AND D1.USE_YN = 'Y'                                                                                            \n");
			
			if ( ORG_USE ) {
			    sql.append("            AND D1.DEPT_ID NOT LIKE 'V%'                                                                               \n");
				sql.append("UNION ALL \n");          
				sql.append("SELECT 2 GRPGBN, UPPER_DEPT_ID, NULL, NULL, DEPT_ID, DEPT_NAME, DEPT_FULLNAME, MAIN_YN, ORGGBN, DEPT_RANK, 0 USR_RANK \n");
				sql.append("FROM DEPT WHERE DEPT_ID LIKE 'V%' \n");
			}
			
			sql.append("            UNION ALL                                                                                                      \n");
			sql.append("            SELECT 3 GRPGBN, U.DEPT_ID UPPER_DEPT_ID, U.DEPT_NAME UPPER_DEPT_NAME,                                         \n");
			sql.append("                   NVL(TRIM(SUBSTR(U.DEPT_FULLNAME, ?)), U.DEPT_FULLNAME) UPPER_DEPT_FULLNAME,                             \n");
			sql.append("                   USER_ID DEPT_ID, USER_NAME DEPT_NAME,                                                                   \n");
			sql.append("                   NVL(TRIM(SUBSTR(U.DEPT_FULLNAME, ?)), U.DEPT_FULLNAME) || ' ' || USER_NAME DEPT_FULLNAME,               \n");
			sql.append("                   MAIN_YN, ORGGBN, DEPT_RANK, USR_RANK                                                                    \n");
			sql.append("            FROM USR U, DEPT D                                                                                             \n");
			sql.append("            WHERE U.DEPT_ID = D.DEPT_ID                                                                                    \n");
			sql.append("            AND U.USE_YN = 'Y' AND D.USE_YN = 'Y'                                                                          \n");			
			sql.append("           ) A                                                                                                             \n");
			
			if ( "1".equals(searchKey) && !"".equals(Utils.nullToEmptyString(searchValue)) ) {
			sql.append("         , (                                                                                                               \n");
			sql.append("            SELECT DISTINCT *                                                                                              \n");
			sql.append("            FROM (                                                                                                         \n");
			sql.append("                  SELECT DEPT_ID DID FROM DEPT                                                                             \n");
			sql.append("                  START WITH DEPT_ID IN (SELECT DEPT_ID FROM DEPT WHERE DEPT_NAME LIKE '%" + searchValue + "%')            \n");
			sql.append("                  CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID                                                                 \n");
			sql.append("                  UNION ALL                                                                                                \n");
			sql.append("                  SELECT DEPT_ID FROM DEPT                                                                                 \n");
			sql.append("                  START WITH DEPT_ID IN (SELECT DEPT_ID FROM DEPT WHERE DEPT_NAME LIKE '%" + searchValue + "%')            \n");
			sql.append("                  CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID                                                                 \n");
			sql.append("                 )                                                                                                         \n");
			
			if ( ORG_USE ) {
				sql.append("UNION ALL SELECT DEPT_ID DID FROM DEPT WHERE DEPT_ID LIKE 'V%' \n");
			}
			
			sql.append("           ) B                                                                                                             \n");
			sql.append("WHERE A.DEPT_ID = B.DID                                                                                                    \n");
			} else if ( "2".equals(searchKey) && !"".equals(Utils.nullToEmptyString(searchValue)) ) {
			sql.append("         , (                                                                                                               \n");
			sql.append("            SELECT DISTINCT *                                                                                              \n");
			sql.append("            FROM (                                                                                                         \n");
			sql.append("                  SELECT DEPT_ID DID FROM DEPT                                                                             \n");
			sql.append("                  START WITH DEPT_ID IN (SELECT DEPT_ID FROM USR WHERE USER_NAME LIKE '%" + searchValue + "%')             \n");
			sql.append("                  CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID                                                                 \n");
			sql.append("                  UNION ALL                                                                                                \n");
			sql.append("                  SELECT DEPT_ID FROM DEPT                                                                                 \n");
			sql.append("                  START WITH DEPT_ID IN (SELECT DEPT_ID FROM USR WHERE USER_NAME LIKE '%" + searchValue + "%')             \n");
			sql.append("                  CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID                                                                 \n");
			sql.append("                  UNION ALL                                                                                                \n");
			sql.append("                  SELECT USER_ID FROM USR WHERE USER_NAME LIKE '%" + searchValue + "%'                                     \n");
			sql.append("                 )                                                                                                         \n");
			
			if ( ORG_USE ) {
				sql.append("UNION ALL SELECT DEPT_ID DID FROM DEPT WHERE DEPT_ID LIKE 'V%' \n");
			}
			
			sql.append("           ) B                                                                                                             \n");
			sql.append("WHERE A.DEPT_ID = B.DID                                                                                                    \n");
			}
			
			sql.append(")                                                                                                                          \n");
			sql.append("START WITH DEPT_ID = ?                                                                                                     \n");
			sql.append("CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID                                                                                   \n");
			sql.append("AND LEVEL < 3                                                                                                              \n");
			sql.append("AND ORGGBN LIKE NVL(?, '%')                                                                                                \n");
			sql.append("ORDER SIBLINGS BY GRPGBN, DEPT_RANK, USR_RANK                                                                              \n");
			
			int substring = commfunction.getDeptFullNameSubstringIndex(userid);
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			int idx = 0;
			pstmt.setInt(++idx, substring);
			pstmt.setInt(++idx, substring);
			pstmt.setInt(++idx, substring);
			pstmt.setInt(++idx, substring);
			pstmt.setString(++idx, orgid);
			pstmt.setString(++idx, orggbn);
			rs = pstmt.executeQuery();
			
			while ( rs.next() ) {
				FormationBean fbean = new FormationBean();
				fbean.setLevel(Utils.nullToEmptyString(rs.getString("LEVEL")));
				fbean.setGrpgbn(Utils.nullToEmptyString(rs.getString("GRPGBN")));
				fbean.setMain_yn(Utils.nullToEmptyString(rs.getString("MAIN_YN")));
				fbean.setUpper_dept_id(Utils.nullToEmptyString(rs.getString("UPPER_DEPT_ID")));
				fbean.setUpper_dept_name(Utils.nullToEmptyString(rs.getString("UPPER_DEPT_NAME")));
				fbean.setUpper_dept_fullname(Utils.nullToEmptyString(rs.getString("UPPER_DEPT_FULLNAME")));
				fbean.setDept_id(Utils.nullToEmptyString(rs.getString("DEPT_ID")));
				fbean.setDept_name(Utils.nullToEmptyString(rs.getString("DEPT_NAME")));
				fbean.setDept_fullname(Utils.nullToEmptyString(rs.getString("DEPT_FULLNAME")));
				
				if ( result == null ) result = new ArrayList();
				result.add(fbean);
			}			
		} catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(con, pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 조직도 불러오기 : 부서
	 * @param orggbn
	 * @param orgid
	 * @return
	 * @throws Exception
	 */
	public List getFormationDept(String orggbn, String orgid, String userid) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List result = null;
		
		boolean ORG_USE = false;
		if ( "부산6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 && "".equals(orggbn) ) {
			ORG_USE = true;
		}
		
		try {			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT LEVEL, GRPGBN, UPPER_DEPT_ID, UPPER_DEPT_NAME, UPPER_DEPT_FULLNAME, DEPT_ID, DEPT_NAME, DEPT_FULLNAME, MAIN_YN FROM           \n");
			sql.append("(                                                                                                                                    \n");
			
			if ( ORG_USE ) {
				sql.append("SELECT 2 GRPGBN, DECODE(D1.DEPT_DEPTH, 2, 'V00' || D1.ORGGBN || '0', D1.UPPER_DEPT_ID) UPPER_DEPT_ID, D2.DEPT_NAME UPPER_DEPT_NAME, \n");
			} else {
			sql.append("SELECT 2 GRPGBN, D1.UPPER_DEPT_ID, D2.DEPT_NAME UPPER_DEPT_NAME,                                                                     \n");
			}
			
			sql.append("       NVL(TRIM(SUBSTR(D2.DEPT_FULLNAME, ?)), D2.DEPT_FULLNAME) UPPER_DEPT_FULLNAME,                                                 \n");
			sql.append("       D1.DEPT_ID, D1.DEPT_NAME,                                                                                                     \n");
			sql.append("       NVL(TRIM(SUBSTR(D1.DEPT_FULLNAME, ?)), D1.DEPT_FULLNAME) DEPT_FULLNAME,                                                       \n");
			sql.append("       D1.MAIN_YN, D1.ORGGBN, D1.DEPT_RANK, 0 USR_RANK                                                                               \n");
			sql.append("FROM DEPT D1, DEPT D2                                                                                                                \n");
			sql.append("WHERE D1.UPPER_DEPT_ID = D2.DEPT_ID(+)                                                                                               \n");
			sql.append("AND D1.USE_YN = 'Y'                                                                                                                  \n");
			
			if ( ORG_USE ) {
			    sql.append("AND D1.DEPT_ID NOT LIKE 'V%' \n");
				sql.append("UNION ALL \n");          
				sql.append("SELECT 2 GRPGBN, UPPER_DEPT_ID, NULL, NULL, DEPT_ID, DEPT_NAME, DEPT_FULLNAME, MAIN_YN, ORGGBN, DEPT_RANK, 0 USR_RANK \n");
				sql.append("FROM DEPT WHERE DEPT_ID LIKE 'V%' \n");
			}
			
			sql.append(")                                                                                                                                    \n");
			sql.append("START WITH DEPT_ID = ?                                                                                                               \n");
			sql.append("CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID                                                                                             \n");
			sql.append("AND LEVEL < 3                                                                                                                        \n");
			sql.append("AND ORGGBN LIKE NVL(?, '%')                                                                                                          \n");
			sql.append("ORDER SIBLINGS BY GRPGBN, DEPT_RANK, USR_RANK                                                                                        \n");
			
			int substring = commfunction.getDeptFullNameSubstringIndex(userid);
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			int idx = 0;
			pstmt.setInt(++idx, substring);
			pstmt.setInt(++idx, substring);
			pstmt.setString(++idx, orgid);
			pstmt.setString(++idx, orggbn);
			rs = pstmt.executeQuery();
			
			while ( rs.next() ) {
				FormationBean fbean = new FormationBean();
				fbean.setLevel(Utils.nullToEmptyString(rs.getString("LEVEL")));
				fbean.setGrpgbn(Utils.nullToEmptyString(rs.getString("GRPGBN")));
				fbean.setMain_yn(Utils.nullToEmptyString(rs.getString("MAIN_YN")));
				fbean.setUpper_dept_id(Utils.nullToEmptyString(rs.getString("UPPER_DEPT_ID")));
				fbean.setUpper_dept_name(Utils.nullToEmptyString(rs.getString("UPPER_DEPT_NAME")));
				fbean.setUpper_dept_fullname(Utils.nullToEmptyString(rs.getString("UPPER_DEPT_FULLNAME")));
				fbean.setDept_id(Utils.nullToEmptyString(rs.getString("DEPT_ID")));
				fbean.setDept_name(Utils.nullToEmptyString(rs.getString("DEPT_NAME")));
				fbean.setDept_fullname(Utils.nullToEmptyString(rs.getString("DEPT_FULLNAME")));
				
				if ( result == null ) result = new ArrayList();
				result.add(fbean);
			}
		} catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(con, pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 조직도 불러오기 : 그룹
	 * @param orggbn
	 * @param userid
	 * @param delImg
	 * @param onclickHandler
	 * @return
	 * @throws Exception
	 */
	public List getFormationGroup(String orggbn, String userid, String delImg, String onclickHandler) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT 1 GRPGBN, GRPLISTCD DEPT_ID, GRPLISTNM DEPT_NAME, CRTUSRGBN, ORD \n");
			sql.append("FROM GROUPMST                                                           \n");
			sql.append("WHERE CRTUSRGBN LIKE '0'                                                \n");
			sql.append("UNION ALL                                                               \n");
			sql.append("SELECT 1, GRPLISTCD, GRPLISTNM, CRTUSRGBN, ORD                          \n");
			sql.append("FROM GROUPMST                                                           \n");
			sql.append("WHERE CRTUSRID LIKE ?                                                   \n");
			sql.append("AND CRTUSRGBN LIKE '1'                                                  \n");
			sql.append("ORDER BY CRTUSRGBN, ORD                                                 \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			int idx = 0;
			pstmt.setString(++idx, userid);
			rs = pstmt.executeQuery();
			
			while ( rs.next() ) {
				FormationBean fbean = new FormationBean();
				fbean.setGrpgbn(Utils.nullToEmptyString(rs.getString("GRPGBN")));
				fbean.setDept_id(Utils.nullToEmptyString(rs.getString("DEPT_ID")));
				fbean.setDept_name(Utils.nullToEmptyString(rs.getString("DEPT_NAME")));
				
				String group_name = Utils.nullToEmptyString(rs.getString("DEPT_NAME"));
				if ( "0".equals(rs.getString("CRTUSRGBN")) ) {
					group_name = group_name + " (공통)";
				} else {
					group_name = group_name + " &lt;img src='" + delImg + "' alt='삭제' align='absmiddle' onclick=" +
								onclickHandler + "('delete','" + fbean.getDept_id() + "')&gt;";
				}
				fbean.setUpper_dept_name(group_name);
				
				if ( result == null ) result = new ArrayList();
				result.add(fbean);
			}			
		} catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(con, pstmt, rs);
		}
		
		return result;
	}
	
	public List getFormationDeptExclude005(String orggbn, String orgid, String userid) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List result = null;
		
		boolean ORG_USE = false;
		if ( "부산6260000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 && "".equals(orggbn) ) {
			ORG_USE = true;
		}
		
		try {			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT LEVEL, GRPGBN, UPPER_DEPT_ID, UPPER_DEPT_NAME, UPPER_DEPT_FULLNAME, DEPT_ID, DEPT_NAME, DEPT_FULLNAME, MAIN_YN FROM           \n");
			sql.append("(                                                                                                                                    \n");
			
			if ( ORG_USE ) {
				sql.append("SELECT 2 GRPGBN, DECODE(D1.DEPT_DEPTH, 2, 'V00' || D1.ORGGBN || '0', D1.UPPER_DEPT_ID) UPPER_DEPT_ID, D2.DEPT_NAME UPPER_DEPT_NAME, \n");
			} else {
			sql.append("SELECT 2 GRPGBN, D1.UPPER_DEPT_ID, D2.DEPT_NAME UPPER_DEPT_NAME,                                                                     \n");
			}
			
			sql.append("       NVL(TRIM(SUBSTR(D2.DEPT_FULLNAME, ?)), D2.DEPT_FULLNAME) UPPER_DEPT_FULLNAME,                                                 \n");
			sql.append("       D1.DEPT_ID, D1.DEPT_NAME,                                                                                                     \n");
			sql.append("       NVL(TRIM(SUBSTR(D1.DEPT_FULLNAME, ?)), D1.DEPT_FULLNAME) DEPT_FULLNAME,                                                       \n");
			sql.append("       D1.MAIN_YN, D1.ORGGBN, D1.DEPT_RANK, 0 USR_RANK                                                                               \n");
			sql.append("FROM DEPT D1, DEPT D2                                                                                                                \n");
			sql.append("WHERE D1.UPPER_DEPT_ID = D2.DEPT_ID(+)                                                                                               \n");
			sql.append("AND D1.USE_YN = 'Y'                                                                                                                  \n");
			
			if ( ORG_USE ) {
				sql.append("UNION ALL \n");          
				sql.append("SELECT 2 GRPGBN, UPPER_DEPT_ID, NULL, NULL, DEPT_ID, DEPT_NAME, DEPT_FULLNAME, MAIN_YN, ORGGBN, DEPT_RANK, 0 USR_RANK \n");
				sql.append("FROM DEPT WHERE DEPT_ID LIKE 'V%' \n");
			}
			
			sql.append(")                                                                                                                                    \n");
			sql.append("START WITH DEPT_ID = ?                                                                                                               \n");
			sql.append("CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID                                                                                             \n");
			sql.append("AND LEVEL < 3                                                                                                                        \n");
			sql.append("AND ORGGBN LIKE NVL(?, '%')                                                                                                          \n");
			sql.append("ORDER SIBLINGS BY GRPGBN, DEPT_RANK, USR_RANK                                                                                        \n");
			
			int substring = commfunction.getDeptFullNameSubstringIndex(userid);
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			int idx = 0;
			pstmt.setInt(++idx, substring);
			pstmt.setInt(++idx, substring);
			pstmt.setString(++idx, orgid);
			pstmt.setString(++idx, orggbn);
			rs = pstmt.executeQuery();
			
			while ( rs.next() ) {
				FormationBean fbean = new FormationBean();
				fbean.setLevel(Utils.nullToEmptyString(rs.getString("LEVEL")));
				fbean.setGrpgbn(Utils.nullToEmptyString(rs.getString("GRPGBN")));
				fbean.setMain_yn(Utils.nullToEmptyString(rs.getString("MAIN_YN")));
				fbean.setUpper_dept_id(Utils.nullToEmptyString(rs.getString("UPPER_DEPT_ID")));
				fbean.setUpper_dept_name(Utils.nullToEmptyString(rs.getString("UPPER_DEPT_NAME")));
				fbean.setUpper_dept_fullname(Utils.nullToEmptyString(rs.getString("UPPER_DEPT_FULLNAME")));
				fbean.setDept_id(Utils.nullToEmptyString(rs.getString("DEPT_ID")));
				fbean.setDept_name(Utils.nullToEmptyString(rs.getString("DEPT_NAME")));
				fbean.setDept_fullname(Utils.nullToEmptyString(rs.getString("DEPT_FULLNAME")));
				
				if ( result == null ) result = new ArrayList();
				result.add(fbean);
			}			
		} catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(con, pstmt, rs);
		}
		
		return result;
	}
}