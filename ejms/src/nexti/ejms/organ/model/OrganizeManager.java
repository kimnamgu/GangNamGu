/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 조직관리 manager
 * 설명:
 */
package nexti.ejms.organ.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.agent.TaskUsrDeptGetting;
import nexti.ejms.agent.TaskWsUsrDeptGetting;
import nexti.ejms.attr.model.AttrBean;
import nexti.ejms.attr.model.AttrManager;
import nexti.ejms.common.ConnectionManager;
import nexti.ejms.common.appInfo;
import nexti.ejms.group.model.GroupManager;
import nexti.ejms.util.Utils;

public class OrganizeManager {
	private static Logger logger = Logger.getLogger(OrganizeManager.class);
	private static OrganizeManager instance = null;
	private static OrganizeDAO dao = null;
	
	private OrganizeManager() {
	}
	
	public static OrganizeManager instance() {
		if (instance==null) instance = new OrganizeManager(); 
		return instance;
	}
	
	private OrganizeDAO OrganizeDAO(){
		if (dao==null) dao = new OrganizeDAO(); 
		return dao;
	}
	
	public int updateOrgRank(Connection DBConn) throws Exception {
		PreparedStatement pstmt = null;
		StringBuffer sql = null;
		int result = 0;
		
		try {
			DBConn.commit();
			DBConn.setAutoCommit(false);
			
			sql = new StringBuffer();
			sql.append("UPDATE /*+ bypass_ujvc */ \n");
			sql.append("(SELECT D1.DEPT_RANK A, D2.NUM B \n");
			sql.append(" FROM DEPT D1, (SELECT ROWNUM NUM, DEPT_ID \n");
			sql.append("                FROM DEPT \n");
			sql.append("                START WITH DEPT_ID = '" + appInfo.getRootid() +"' \n");
			sql.append("                CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID \n");
			sql.append("                ORDER SIBLINGS BY DEPT_RANK) D2 \n");
			sql.append(" WHERE D1.DEPT_ID = D2.DEPT_ID) \n");
			sql.append("SET A = B \n");
			
			pstmt = DBConn.prepareStatement(sql.toString());
			result = pstmt.executeUpdate();
			
			if ( result > 0 ) {
				ConnectionManager.close(pstmt);
				sql = new StringBuffer();
				sql.append("UPDATE /*+ bypass_ujvc */ \n");
				sql.append("(SELECT U1.USR_RANK A, U2.NUM B \n");
				sql.append(" FROM USR U1, (SELECT ROWNUM NUM, USER_ID \n");
				sql.append("               FROM (SELECT USER_ID \n");
				sql.append("                     FROM USR U, DEPT D \n");
				sql.append("                     WHERE U.DEPT_ID(+) = D.DEPT_ID \n");
				sql.append("                     AND USER_ID IS NOT NULL \n");
				sql.append("                     ORDER BY DEPT_RANK, USR_RANK)) U2 \n");
				sql.append(" WHERE U1.USER_ID = U2.USER_ID)");
				sql.append("SET A = B \n");
				
				pstmt = DBConn.prepareStatement(sql.toString());
				result = pstmt.executeUpdate();
			}
			
			if ( result > 0 ) {
				DBConn.commit();
			} else {
				DBConn.rollback();
			}
		} catch ( Exception e ) {
			DBConn.rollback();
			logger.error("ERROR", e);
		} finally {
			ConnectionManager.close(pstmt);
			DBConn.setAutoCommit(true);
		}
		
		return result;
	}
	
	/**
	 * 관리자 여부 
	 * @throws Exception 
	 */
	public String isSysMgr(String user_id) throws Exception{
		String result = "000";
		
		result = OrganizeDAO().isSysMgr(user_id);
		
		if(result.equals("Y")){
			//관리자 이면 001값으로 셋팅
			result = "001";  //시스템관리자
		} 		
		return result;
	}
	
	/** 
	 * 관리자 여부 저장
	 * @throws Exception 
	 */
	public int updateMgr(String gbn, String[] userid) throws Exception {
		int result = 0;
		
		for( int i=0; i<userid.length ;i++){
		
			if("1".equals(gbn)){
				result += OrganizeDAO().insertMgr(userid[i].trim());
			} else {
				result += OrganizeDAO().deleteMgr(userid[i].trim());
			}
		}
		
		return result;
	}

	/**
	 * 관리자 정보 가져오기(1명만 불러옴, 없을 경우 일반 사용자)
	 * @throws Exception 
	 */
	public String getManager() throws Exception {
		return OrganizeDAO().getManager();
	}
	
	/**
	 * 관리자 정보가져오기
	 * @throws Exception 
	 */
	public List managerList(String user_gbn, String rep_dept, String user_id) throws Exception {
		List mgrlist = null;
		
		mgrlist = OrganizeDAO().managerList(user_gbn, rep_dept, user_id);
		
		return mgrlist;
	}
	
	/**
	 * 관리자 정보가져오기
	 * @throws Exception 
	 */
	public int existManagerList(String user) throws Exception {
		int result = 0;
		
		result = OrganizeDAO().existManagerList(user);
		
		return result;
	}

	/**
	 * SQL문 실행(delete, insert용)
	 */
	public int executeUpdate(Connection DBConn, String sql) throws Exception {
		int result = 0;
		PreparedStatement pstmt = null;
		try {
			pstmt = DBConn.prepareStatement(sql);
			result = pstmt.executeUpdate();
			try { pstmt.close(); } catch (Exception e) { }
		} finally {
			try { if (pstmt != null) { pstmt.close(); } } catch (Exception ex) { }
		}
		return result;
	}

	/**
	 * 부서step3: DEPT_TEMP_SSO와 DEPT를 비교한다.
	 * return 폐지부서가 존재하면 true, 아니면 false
	 */
	public int chkDelDeptTemp(Connection DBConn) throws Exception {
		String sql = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int ret = 0;
		
		try {
			sql = "SELECT COUNT(*)CNT \n" +
				  "FROM (SELECT DEPT_ID FROM DEPT \n" +
				  "      WHERE NVL(CON_YN, 'Y') = 'Y' \n" +
				  "	     MINUS \n" +
				  "      SELECT DEPT_ID FROM DEPT_TEMP_SSO) \n";
			pstmt = DBConn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ret = rs.getInt("CNT");
			}
		} finally {
			try { if (rs != null) { rs.close(); } } catch (Exception ex) { }
			try { if (pstmt != null) { pstmt.close(); } } catch (Exception ex) { }
		}
		
		return ret;
	}

	/**
	 * 부서step3: DEPT_TEMP_SSO와 DEPT를 비교한다.
	 * 폐지부서삭제 (DEPT_TEMP)
	 */
	public int deleteDelDept(Connection DBConn) throws Exception {
		String sql = null;
		PreparedStatement pstmt = null, pstmt2 = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			sql = "SELECT DEPT_ID \n" +
				  "FROM (SELECT DEPT_ID FROM DEPT \n" +
				  "      WHERE NVL(CON_YN, 'Y') = 'Y' \n" +
				  "	     MINUS \n" +
				  "      SELECT DEPT_ID FROM DEPT_TEMP_SSO) \n";
			pstmt = DBConn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			pstmt2 = DBConn.prepareStatement("UPDATE DEPT SET USE_YN = 'N', CON_YN = 'Y' WHERE DEPT_ID = ?");
			while (rs.next()) {
				//폐지부서는 지운다.
				pstmt2.clearParameters();
				pstmt2.setString(1, rs.getString("DEPT_ID"));
				result += pstmt2.executeUpdate();
			}
			try { rs.close(); } catch (Exception e) { }
			try { pstmt.close(); } catch (Exception e) { }
			try { pstmt2.close(); } catch (Exception e) { }			
		} finally {
			try { if (rs != null) { rs.close(); } } catch (Exception ex) { }
			try { if (pstmt != null) { pstmt.close(); } } catch (Exception ex) { }
			try { if (pstmt2 != null) { pstmt2.close(); } } catch (Exception ex) { }
		}
		
		return result;
	}		
	
	/**
	 * 부서step7: 배포목록디테일(GRPLISTDTL) 기존부서는 명칭만 변경")
	 */
	public void updateGrpList(Connection DBConn) throws Exception {
		String sql = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//기존 부서의 명칭을 최신정보로 변경
			sql = "SELECT DEPT_ID, DEPT_NAME FROM DEPT";
			pstmt = DBConn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				GroupManager.instance().modifyDetailNameFormCode(rs.getString("DEPT_ID"), rs.getString("DEPT_NAME"));
			}
			ConnectionManager.close(pstmt, rs);
			
			//정렬순서가 부서,사용자 순위에 맞게 업데이트 시작 : 임시로직
			try {
				DBConn.commit();
				DBConn.setAutoCommit(false);
				//삭제된 부서, 사용자 삭제
				executeUpdate(DBConn, "DELETE GRPLISTDTL G \n" +
									  "WHERE (CODEGBN = '1' AND (SELECT COUNT(*) FROM USR WHERE USE_YN = 'Y' AND USER_ID = G.CODE) < 1) \n" +
									  "OR    (CODEGBN = '0' AND (SELECT COUNT(*) FROM DEPT WHERE USE_YN = 'Y' AND DEPT_ID = G.CODE) < 1) \n");
				//중복으로 등록된 부서, 사용자 삭제
				executeUpdate(DBConn, "DELETE GRPLISTDTL\n" +
									  "WHERE (GRPLISTCD, SEQ)\n" +
									  "      IN (SELECT GRPLISTCD, SEQ\n" +
									  "          FROM (SELECT GRPLISTCD, SEQ,\n" +
									  "                       ROW_NUMBER() OVER(PARTITION BY GRPLISTCD, CODE, CODEGBN ORDER BY SEQ) CNT\n" +
									  "                FROM GRPLISTDTL)\n" +
									  "          WHERE CNT > 1)\n");
				//부서, 사용자 서열순 정렬을 위하여 일련번호 업데이트	
				executeUpdate(DBConn, "CREATE TABLE ORGRANK(NUM NUMBER, CODE VARCHAR(20)) \n");
	
				executeUpdate(DBConn, "INSERT INTO ORGRANK \n" +
									  "SELECT ROWNUM NUM, DEPT_ID \n" +
									  "FROM (SELECT DEPT_ID, UPPER_DEPT_ID, DEPT_RANK FROM DEPT \n" +
									  "      UNION ALL \n" +
									  "      SELECT USER_ID, DEPT_ID, USR_RANK FROM USR) \n" +
									  "CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID \n" +
									  "START WITH DEPT_ID = '" + appInfo.getRootid() + "' \n" +
									  "ORDER SIBLINGS BY DEPT_RANK \n");
	
				executeUpdate(DBConn, "UPDATE GRPLISTDTL SET SEQ = SEQ + 99999 \n");
	
				executeUpdate(DBConn, "UPDATE GRPLISTDTL G SET SEQ = NVL((SELECT NUM FROM ORGRANK WHERE CODE = G.CODE), SEQ) \n");
	
				executeUpdate(DBConn, "TRUNCATE TABLE ORGRANK \n");
	
				executeUpdate(DBConn, "INSERT INTO ORGRANK \n" +
				                      "SELECT ROWNUM + 99999, CODE \n" +
				                      "FROM (SELECT DISTINCT CODE, CODEGBN \n" +
				                      "      FROM GRPLISTDTL \n" +
				                      "      WHERE SEQ > 99999 \n" +
				                      "      ORDER BY CODEGBN, CODE) \n");
	
				executeUpdate(DBConn, "UPDATE GRPLISTDTL G SET SEQ = NVL((SELECT NUM FROM ORGRANK WHERE CODE = G.CODE), SEQ) \n");
				
				executeUpdate(DBConn, "DROP TABLE ORGRANK \n");
			} catch ( Exception e ) {
				DBConn.rollback();
				logger.error("ERROR", e);
			} finally {
				DBConn.commit();
				DBConn.setAutoCommit(true);
			}
			//정렬순서가 부서,사용자 순위에 맞게 업데이트 끝 : 임시로직
		} finally {
			ConnectionManager.close(pstmt, rs);
		}
		
	}
	
	/**
	 * 부서step8: 속성목록디테일(ATTLISTDTL)테이블에 LISTCD='00001'조건으로 삭제후 대상부서인것만 추가
	 */
	public void updateAttList(Connection DBConn) throws Exception {
		String sql = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String listcd = "00001";	//부서속성 고정값
			AttrManager.instance().deleteAttrMst(listcd);
			AttrBean attrBean = new AttrBean();
			attrBean.setListcd(listcd);
			attrBean.setListnm("전체부서");
			attrBean.setCrtusrid("AGENT");
			attrBean.setCrtusrgbn("0");
			AttrManager.instance().insertAttrMst(attrBean);
			sql = "SELECT ROWNUM NUM, DEPT_ID, DEPT_NAME " +
				  "FROM (SELECT DEPT_ID, DEPT_NAME " +
		      	  "      FROM DEPT A " +
		      	  "      WHERE USE_YN = 'Y' " +
		      	  "      AND MAIN_YN = 'Y' " +
		          "      ORDER BY TO_NUMBER(DEPT_RANK) )";
			pstmt = DBConn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				attrBean = new AttrBean();
				attrBean.setListcd(listcd);
				attrBean.setSeq(rs.getInt("NUM"));
				attrBean.setListdtlnm(rs.getString("DEPT_NAME"));
				attrBean.setAttr_desc("");
				AttrManager.instance().insertAttrDtl(attrBean);
			}
			try { pstmt.close(); } catch (Exception e) { }
		} finally {
			try { if (rs != null) { rs.close(); } } catch (Exception ex) { }
			try { if (pstmt != null) { pstmt.close(); } } catch (Exception ex) { }
		}
		
	}
	
	/**
	 * 사용자정보 연계
	 * 3. USR_TEMP_SSO에  USR를 비교한다.
	 * 4. USR을 모두 삭제한다.
	 * 5. USR_TEMP_SSO를 USR에 복사한다. 
	 * 6. USR_TEMP_SSO를 USR에 비교하여 수정한다(commit)
	 * return 추가/삭제된 사용자수
	 */
	public void updateUserInfo(Connection DBConn, TaskUsrDeptGetting tudg) throws Exception {
		String sql = null;
		PreparedStatement pstmt = null, pstmt2 = null;
		ResultSet rs = null;
		int userCount = 0;
		
		try { 			
			/*******************************************************************************/
			logger.debug(">> 사용자step3: USR에 삭제사용자를 체크한다.");
			/*******************************************************************************/			
			sql = "SELECT USER_ID \n" +
				  "FROM (SELECT USER_ID FROM USR \n" +
				  "      WHERE NVL(CON_YN, 'Y') = 'Y' \n" +
				  "	     MINUS \n" +
				  "      SELECT USER_ID FROM USR_TEMP_SSO) \n";
			pstmt = DBConn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			pstmt2 = DBConn.prepareStatement(
					"UPDATE USR SET USE_YN = 'N', CON_YN = 'Y' WHERE USER_ID = ?");
			while (rs.next()) {
				//삭제된 사용자는 지운다.
				pstmt2.clearParameters();
				pstmt2.setString(1, rs.getString("USER_ID"));
				userCount += pstmt2.executeUpdate();
			}
			
			ConnectionManager.close(pstmt2);
			ConnectionManager.close(pstmt, rs);
			
			tudg.setLastRunStat("Delete User Count : " + userCount);
			logger.info(tudg.getLastRunStat());
			
			/*******************************************************************************/
			logger.debug(">> 사용자step4: USR_TEMP_SSO를 USR에 복사한다.");
			/*******************************************************************************/
			pstmt = DBConn.prepareStatement(
					"SELECT A.* " +
					"FROM USR_TEMP_SSO A, USR B " +
					"WHERE A.USER_ID = B.USER_ID " +
					"AND NVL(B.CON_YN, 'Y') = 'Y'");
			pstmt2 = DBConn.prepareStatement(
					"UPDATE USR " +
					"SET USER_SN = ?, USER_NAME = ?, DEPT_ID = ?, DEPT_NAME = ?, DEPT_FULLNAME = ?, " +
					"    CLASS_ID = ?, CLASS_NAME = ?, POSITION_ID = ?, POSITION_NAME = ?, GRADE_ID = ?, " +
					"    GRADE_NAME = ?, USR_RANK = ?, EMAIL = ?, TEL = ?, MOBILE = ?, " +
					"    USE_YN = ?, CON_YN = ?, CRTDT = ?, CRTUSRID = ?, UPTDT = ?, UPTUSRID = ? " +
					"WHERE USER_ID = ?");
			
			rs = pstmt.executeQuery();
			userCount = 0;
			
			while ( rs.next() ) {
				int idx = 0;
				pstmt2.setString(++idx, rs.getString("USER_SN"));
				pstmt2.setString(++idx, rs.getString("USER_NAME"));
				pstmt2.setString(++idx, rs.getString("DEPT_ID"));
				pstmt2.setString(++idx, rs.getString("DEPT_NAME"));
				pstmt2.setString(++idx, rs.getString("DEPT_FULLNAME"));
				pstmt2.setString(++idx, rs.getString("CLASS_ID"));
				pstmt2.setString(++idx, rs.getString("CLASS_NAME"));
				pstmt2.setString(++idx, rs.getString("POSITION_ID"));
				pstmt2.setString(++idx, rs.getString("POSITION_NAME"));
				pstmt2.setString(++idx, rs.getString("GRADE_ID"));
				pstmt2.setString(++idx, rs.getString("GRADE_NAME"));
				pstmt2.setInt(++idx, "".equals(Utils.nullToEmptyString(rs.getString("GRADE_ID"))) ? 99999 : Integer.parseInt(rs.getString("GRADE_ID")) );
				pstmt2.setString(++idx, rs.getString("EMAIL"));
				pstmt2.setString(++idx, rs.getString("TEL"));
				pstmt2.setString(++idx, rs.getString("MOBILE"));
				pstmt2.setString(++idx, rs.getString("USE_YN"));
				pstmt2.setString(++idx, rs.getString("CON_YN"));
				pstmt2.setString(++idx, rs.getString("REG_DAY"));
				pstmt2.setString(++idx, "AGENT");
				pstmt2.setString(++idx, rs.getString("UPDATE_DAY"));
				pstmt2.setString(++idx, "AGENT");
				pstmt2.setString(++idx, rs.getString("USER_ID"));
				userCount += pstmt2.executeUpdate();
				pstmt2.clearParameters();
			}
			
			ConnectionManager.close(pstmt2);
			ConnectionManager.close(pstmt, rs);
			
			tudg.setLastRunStat("Update User Count : " + userCount);
			logger.info(tudg.getLastRunStat());
			
			String userStat = "'" + appInfo.getUsrdeptUserstat().replaceAll(",", "','") + "'";
			String regularity = "'" + appInfo.getUsrdeptRegularity().replaceAll(",", "','") + "'";

			pstmt = DBConn.prepareStatement(
					"INSERT INTO USR \n" +
					"SELECT USER_ID, USER_SN, USER_NAME, DEPT_ID, DEPT_NAME, \n" +
					"       DEPT_FULLNAME, CLASS_ID, CLASS_NAME, POSITION_ID, POSITION_NAME, \n" +
					"       GRADE_ID, GRADE_NAME, TO_NUMBER(GRADE_ID), EMAIL, TEL, \n" +
					"       MOBILE, 'Y', 'Y', '1', 'N', NULL, \n" +
					"       NULL, NULL, NULL, NULL, NULL, NULL, \n" +
					"       REG_DAY, 'AGENT', UPDATE_DAY, 'AGENT' \n" +
					"FROM USR_TEMP_SSO \n" +
					"WHERE USER_ID NOT IN (SELECT USER_ID FROM USR) \n" +
					"AND USER_STAT IN (" + userStat + ") \n" +
					"AND REGULARITY IN (" + regularity + ") \n");
			userCount = pstmt.executeUpdate();
			
			ConnectionManager.close(pstmt);
			
			tudg.setLastRunStat("Insert User Count : " + userCount);
			logger.info(tudg.getLastRunStat());

			/*******************************************************************************/
			logger.debug(">> 사용자step5: 주민번호를 통해 성별과 나이대를 저장한다.");
			/*******************************************************************************/
			sql = "SELECT * FROM USR_TEMP_SSO ";
			pstmt = DBConn.prepareStatement(sql);
//			pstmt2 = DBConn.prepareStatement(
//					"UPDATE USR UE " +
//					"SET SEX = (SELECT CASE LENGTH(USER_SN) WHEN 13 " +
//					"                       THEN CASE WHEN SUBSTR(USER_SN, 7, 1) IN (2, 4) " +
//					"                                 THEN 'F' " +
//					"                                 ELSE 'M' END " +
//					"	                     ELSE 'M' END " +
//					"	         FROM USR U " +
//					"	         WHERE U.USER_ID = UE.USER_ID), " +
//					"	  AGE = (SELECT CASE LENGTH(USER_SN) WHEN 13 " +
//					"                       THEN TRUNC(TO_NUMBER(TO_CHAR(SYSDATE, 'YYYY')) - TO_NUMBER('19'||SUBSTR(USER_SN, 1, 2)) + 1, -1) " +
//					"	                     ELSE 20 END " +
//					"	         FROM USR U " +
//					"	         WHERE U.USER_ID = UE.USER_ID) " +
//					"WHERE USER_ID = NVL((SELECT USER_ID FROM USR WHERE USER_ID = ? AND NVL(CON_YN, 'Y') = 'Y'), ?) ");
			pstmt2 = DBConn.prepareStatement(
					"UPDATE USR UE " +
					"SET SEX = 'M', " +
					"	  AGE = '20' " +
					"WHERE USER_ID = NVL((SELECT USER_ID FROM USR WHERE USER_ID = ? AND NVL(CON_YN, 'Y') = 'Y'), ?) ");
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				pstmt2.setString(1, rs.getString("USER_ID"));
				pstmt2.setString(2, rs.getString("USER_ID"));
				pstmt2.executeUpdate();
				pstmt2.clearParameters();
			}
			try { rs.close(); } catch (Exception e) { }
			try { pstmt.close(); } catch (Exception e) { }			
			
						
		} finally {
			try { if (rs != null) { rs.close(); } } catch (Exception ex) { }
			try { if (pstmt != null) { pstmt.close(); } } catch (Exception ex) { }
			try { if (pstmt2 != null) { pstmt2.close(); } } catch (Exception ex) { }
		}
	}
	
	/**
	 * 사용자정보 연계
	 * 3. USR_TEMP_SSO에  USR를 비교한다.
	 * 4. USR을 모두 삭제한다.
	 * 5. USR_TEMP_SSO를 USR에 복사한다. 
	 * 6. USR_TEMP_SSO를 USR에 비교하여 수정한다(commit)
	 * return 추가/삭제된 사용자수
	 */
	public void updateWsUserInfo(Connection DBConn, TaskWsUsrDeptGetting tudg) throws Exception {
		String sql = null;
		PreparedStatement pstmt = null, pstmt2 = null;
		ResultSet rs = null;
		int userCount = 0;
		
		try { 			
			/*******************************************************************************/
			logger.debug(">> 사용자step3: USR에 삭제사용자를 체크한다.");
			/*******************************************************************************/			
			sql = "SELECT USER_ID \n" +
				  "FROM (SELECT USER_ID FROM USR \n" +
				  "      WHERE NVL(CON_YN, 'Y') = 'Y' \n" +
				  "	     MINUS \n" +
				  "      SELECT USER_ID FROM USR_TEMP_SSO) \n";
			pstmt = DBConn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			pstmt2 = DBConn.prepareStatement(
					"UPDATE USR SET USE_YN = 'N', CON_YN = 'Y' WHERE USER_ID = ?");
			while (rs.next()) {
				//삭제된 사용자는 지운다.
				pstmt2.clearParameters();
				pstmt2.setString(1, rs.getString("USER_ID"));
				userCount += pstmt2.executeUpdate();
			}
			
			ConnectionManager.close(pstmt2);
			ConnectionManager.close(pstmt, rs);
			
			tudg.setLastRunStat("Delete User Count : " + userCount);
			logger.info(tudg.getLastRunStat());
			
			/*******************************************************************************/
			logger.debug(">> 사용자step4: USR_TEMP_SSO를 USR에 복사한다.");
			/*******************************************************************************/
			pstmt = DBConn.prepareStatement(
					"SELECT A.* " +
					"FROM USR_TEMP_SSO A, USR B " +
					"WHERE A.USER_ID = B.USER_ID " +
					"AND NVL(B.CON_YN, 'Y') = 'Y'");
			pstmt2 = DBConn.prepareStatement(
					"UPDATE USR " +
					"SET USER_SN = ?, USER_NAME = ?, DEPT_ID = ?, DEPT_NAME = ?, DEPT_FULLNAME = ?, " +
					"    CLASS_ID = ?, CLASS_NAME = ?, POSITION_ID = ?, POSITION_NAME = ?, GRADE_ID = ?, " +
					"    GRADE_NAME = ?, USR_RANK = ?, EMAIL = ?, TEL = ?, MOBILE = ?, " +
					"    USE_YN = ?, CON_YN = ?, CRTDT = ?, CRTUSRID = ?, UPTDT = ?, UPTUSRID = ? " +
					"WHERE USER_ID = ?");
			
			rs = pstmt.executeQuery();
			userCount = 0;
			
			while ( rs.next() ) {
				int idx = 0;
				pstmt2.setString(++idx, rs.getString("USER_SN"));
				pstmt2.setString(++idx, rs.getString("USER_NAME"));
				pstmt2.setString(++idx, rs.getString("DEPT_ID"));
				pstmt2.setString(++idx, rs.getString("DEPT_NAME"));
				pstmt2.setString(++idx, rs.getString("DEPT_FULLNAME"));
				pstmt2.setString(++idx, rs.getString("CLASS_ID"));
				pstmt2.setString(++idx, rs.getString("CLASS_NAME"));
				pstmt2.setString(++idx, rs.getString("POSITION_ID"));
				pstmt2.setString(++idx, rs.getString("POSITION_NAME"));
				pstmt2.setString(++idx, rs.getString("GRADE_ID"));
				pstmt2.setString(++idx, rs.getString("GRADE_NAME"));
				pstmt2.setInt(++idx, "".equals(Utils.nullToEmptyString(rs.getString("GRADE_ID"))) ? 99999 : Integer.parseInt(rs.getString("GRADE_ID")) );
				pstmt2.setString(++idx, rs.getString("EMAIL"));
				pstmt2.setString(++idx, rs.getString("TEL"));
				pstmt2.setString(++idx, rs.getString("MOBILE"));
				pstmt2.setString(++idx, rs.getString("USE_YN"));
				pstmt2.setString(++idx, rs.getString("CON_YN"));
				pstmt2.setString(++idx, rs.getString("REG_DAY"));
				pstmt2.setString(++idx, "AGENT");
				pstmt2.setString(++idx, rs.getString("UPDATE_DAY"));
				pstmt2.setString(++idx, "AGENT");
				pstmt2.setString(++idx, rs.getString("USER_ID"));
				userCount += pstmt2.executeUpdate();
				pstmt2.clearParameters();
			}
			
			ConnectionManager.close(pstmt2);
			ConnectionManager.close(pstmt, rs);
			
			tudg.setLastRunStat("Update User Count : " + userCount);
			logger.info(tudg.getLastRunStat());
			
			String userStat = "'" + appInfo.getUsrdeptUserstat().replaceAll(",", "','") + "'";
			String regularity = "'" + appInfo.getUsrdeptRegularity().replaceAll(",", "','") + "'";

			pstmt = DBConn.prepareStatement(
					"INSERT INTO USR \n" +
					"SELECT USER_ID, USER_SN, USER_NAME, DEPT_ID, DEPT_NAME, \n" +
					"       DEPT_FULLNAME, CLASS_ID, CLASS_NAME, POSITION_ID, POSITION_NAME, \n" +
					"       GRADE_ID, GRADE_NAME, TO_NUMBER(GRADE_ID), EMAIL, TEL, \n" +
					"       MOBILE, 'Y', 'Y', '1', 'N', NULL, \n" +
					"       NULL, NULL, NULL, NULL, NULL, NULL, \n" +
					"       REG_DAY, 'AGENT', UPDATE_DAY, 'AGENT' \n" +
					"FROM USR_TEMP_SSO \n" +
					"WHERE USER_ID NOT IN (SELECT USER_ID FROM USR) \n" +
					"AND USER_STAT IN (" + userStat + ") \n" +
					"AND REGULARITY IN (" + regularity + ") \n");
			userCount = pstmt.executeUpdate();
			
			ConnectionManager.close(pstmt);
			
			tudg.setLastRunStat("Insert User Count : " + userCount);
			logger.info(tudg.getLastRunStat());

			/*******************************************************************************/
			logger.debug(">> 사용자step5: 주민번호를 통해 성별과 나이대를 저장한다.");
			/*******************************************************************************/
			sql = "SELECT * FROM USR_TEMP_SSO ";
			pstmt = DBConn.prepareStatement(sql);
			pstmt2 = DBConn.prepareStatement(
					"UPDATE USR UE " +
					"SET SEX = (SELECT CASE LENGTH(USER_SN) WHEN 13 " +
					"                       THEN CASE WHEN SUBSTR(USER_SN, 7, 1) IN (2, 4) " +
					"                                 THEN 'F' " +
					"                                 ELSE 'M' END " +
					"	                     ELSE 'M' END " +
					"	         FROM USR U " +
					"	         WHERE U.USER_ID = UE.USER_ID), " +
					"	  AGE = (SELECT CASE LENGTH(USER_SN) WHEN 13 " +
					"                       THEN TRUNC(TO_NUMBER(TO_CHAR(SYSDATE, 'YYYY')) - TO_NUMBER('19'||SUBSTR(USER_SN, 1, 2)) + 1, -1) " +
					"	                     ELSE 20 END " +
					"	         FROM USR U " +
					"	         WHERE U.USER_ID = UE.USER_ID) " +
					"WHERE USER_ID = NVL((SELECT USER_ID FROM USR WHERE USER_ID = ? AND NVL(CON_YN, 'Y') = 'Y'), ?) ");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				pstmt2.setString(1, rs.getString("USER_ID"));
				pstmt2.setString(2, rs.getString("USER_ID"));
				pstmt2.executeUpdate();
				pstmt2.clearParameters();
			}
			try { rs.close(); } catch (Exception e) { }
			try { pstmt.close(); } catch (Exception e) { }			
			
						
		} finally {
			try { if (rs != null) { rs.close(); } } catch (Exception ex) { }
			try { if (pstmt != null) { pstmt.close(); } } catch (Exception ex) { }
			try { if (pstmt2 != null) { pstmt2.close(); } } catch (Exception ex) { }
		}
	}
}
