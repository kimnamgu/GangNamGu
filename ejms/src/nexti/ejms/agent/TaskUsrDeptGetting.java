/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 시스템에이전트 사용자,부서연계 프로세스
 * 설명:
 */
package nexti.ejms.agent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.common.appInfo;
import nexti.ejms.organ.model.OrganizeManager;
import nexti.ejms.util.Utils;

public class TaskUsrDeptGetting extends TaskBase {
	private static TaskUsrDeptGetting _instance;
	public static TaskUsrDeptGetting getInstance() {
		if(_instance == null) { _instance = new TaskUsrDeptGetting(); }
		return _instance;
	}
	private static Logger logger = Logger.getLogger(TaskUsrDeptGetting.class);
	
	private Connection DBConn = null;
	private Connection DBUsrDept = null;
	private boolean isUsrDeptConnect = false;
	private boolean isConnect = false;

	private void DBConnection() {
		if(!isConnect) {
			AgentUtil.Disconnection(DBConn);
			DBConn = AgentUtil.getConnection();
			if(DBConn==null) isConnect = false;
			else isConnect = true;
		}
		if(!isUsrDeptConnect) {
			try { if (DBUsrDept != null) { DBUsrDept.close(); } } catch (Exception ex) { }
			try {
				Class.forName(appInfo.getUsrdeptDbtype());
				DBUsrDept = DriverManager.getConnection(appInfo.getUsrdeptDbip(), appInfo.getUsrdeptDbuser(), appInfo.getUsrdeptDbpass());
				isUsrDeptConnect = true;
			} catch (Exception e) {
				isUsrDeptConnect = false;
				setLastRunStat("UsrDept database connection error:" + e);
				logger.error(getLastRunStat());				
			}
		}
	}
	
	public void run() {
		if(!getIsRun()) return;
		setLastRunDateTime();
		DBConnection();
		if (!isUsrDeptConnect) {
			AgentUtil.AgentlogError(getLSeq());
			return;
		}
		if (!isConnect) {
			AgentUtil.AgentlogError(getLSeq());
			return;
		}
		
		PreparedStatement upstmt=null, pstmt = null, pstmt2 = null;;
		ResultSet urs=null,rs = null;
		try {
			runInfoLinkQuery(DBConn, "BEFORE");		//정보연계 전 쿼리
			
			/**
			 * 부서정보 연계
			 * 1. DEPT_TEMP_SSO를 모두 삭제한다
			 * 2. SSOV_DEPT를 DEPT_TEMP_SSO로 복사한다.
			 * 3. DEPT에 DEPT_TEMP_SSO와 DEPT을 비교한다.
			 *   - 삭제부서: USE_YN = N, CON_YN = Y
			 *   - 신규부서: 추가, 대상부서(하위부서가 없으면 MAIN_YN에 'Y' 아니면 'N') 
			 *             USE_YN, CON_YN은 모두 'Y'로 설정, 비고(부서명)으로 적용, 생성정보 적용
			 * 4. DEPT를 모두 삭제한다.
			 * 5. DEPT_TEMP_SSO를 DEPT에 복사한다.
			 * 6. DEPT 하위부서가 없으면 MAIN_YN에 'Y' 아니면 'N' 처리
			 */
			/*******************************************************************************/
			logger.debug(">> 부서step1: DEPT_TEMP_SSO를 모두 삭제한다");
			/*******************************************************************************/
			OrganizeManager.instance().executeUpdate(DBConn, "DELETE FROM DEPT_TEMP_SSO");

			/*******************************************************************************/
			logger.debug(">> 부서step2: SSOV_DEPT를 DEPT_TEMP_SSO로 복사한다. (DEPT_RANK가 NULL인것은 제외)");
			/*******************************************************************************/
			createDeptTemp(DBUsrDept, DBConn);
						
			/*******************************************************************************/
			logger.debug(">> 부서step3: DEPT_TEMP_SSO와 DEPT를 비교한다.");
			/*******************************************************************************/
			int deptCount = OrganizeManager.instance().chkDelDeptTemp(DBConn);
			if (deptCount > 0) {
				deptCount = OrganizeManager.instance().deleteDelDept(DBConn);
			}
			setLastRunStat("Delete Dept Count : " + deptCount);
			logger.info(getLastRunStat());

			/*******************************************************************************/
			logger.debug(">> 부서step4: DEPT를 모두 삭제한다.");
			/*******************************************************************************/
			OrganizeManager.instance().executeUpdate(DBConn, "DELETE FROM DEPT \n" +
															 "WHERE NVL(CON_YN, 'Y') = 'Y' \n");
			
			/*******************************************************************************/
			logger.debug(">> 부서step5: DEPT_TEMP_SSO를 DEPT에 복사한다.");
			/*******************************************************************************/
			deptCount = OrganizeManager.instance().executeUpdate(DBConn,
					"INSERT INTO DEPT \n" +
	                "SELECT A.DEPT_ID, A.DEPT_NAME, A.DEPT_FULLNAME, A.UPPER_DEPT_ID, A.TOP_DEPT_ID, \n" +
	                "       LEVEL, A.DEPT_RANK, A.DEPT_TEL, A.DEPT_FAX, '001', 'Y', \n" +
	                "       A.USE_YN, A.CON_YN, A.REG_DAY, 'AGENT', A.UPDATE_DAY, 'AGENT' \n" +
	                "FROM DEPT_TEMP_SSO A, DEPT B \n" +
	                "WHERE A.DEPT_ID = B.DEPT_ID(+) \n" +
	                "AND NVL(B.CON_YN, 'Y') = 'Y' \n" +
	                "CONNECT BY PRIOR A.DEPT_ID = A.UPPER_DEPT_ID \n" +
	                "START WITH A.DEPT_ID = '" + appInfo.getRootid() + "' \n");
			
			setLastRunStat("Insert & Update Dept Count : " + deptCount);
			logger.info(getLastRunStat());
			
			/*******************************************************************************/
			logger.debug(">> 부서step6: DEPT 하위부서가 없으면 MAIN_YN에 'Y' 아니면 'N' 처리");
			/*******************************************************************************/
			//OrganizeManager.instance().executeUpdate(DBConn, "DELETE FROM DEPT_TEMP_SSO");

			//각 부서별 하위 조직(부서,사용자)을 체크하여 존재할 경우 대상부서여부 끔
			//if ( !appInfo.isMultipleDelivery() ) {
			//	OrganizeManager.instance().executeUpdate(DBConn,
			//			"UPDATE DEPT \n" +
			//			"SET MAIN_YN = 'N' \n" +
			//			"WHERE DEPT_ID IN (SELECT UPPER_DEPT_ID FROM DEPT WHERE NVL(USE_YN, 'Y') = 'Y') \n");
			//}
			
			/**
			 * 사용자정보 연계
			 * 1. USR_TEMP_SSO를 모두 삭제한다.
			 * 2. SSOV_USER를 USR_TEMP_SSO에 복사한다.
			 * 3. USR을 모두 삭제한다.
			 * 4. USR_TEMP_SSO를 USR에 복사한다. 
			 * 5. USR_TEMP_SSO와 USR을 비교한다.
			 *   - 삭제사용자: 해당 사용자를 삭제한다.
			 *   - 신규사용자: 추가, 비번(1), 관리(0), 비고(사용자명)으로 적용, 생성정보 적용
			 *   - 기존사용자: 기존부서와 다를경우 담당단위코드만 null로 처리, 수정정보 적용
			 * 6. 배포목록디테일(GRPLISTDTL) 테이블에 기존부서는 명칭및 정렬순서 적용
			 * 7. 속성목록디테일(ATTLISTDTL)테이블에 LISTCD='00001'조건으로 삭제후 대상부서인것만 추가
			 */
			/*******************************************************************************/
			logger.debug(">> 사용자step1: USR_TEMP_SSO를 모두 삭제한다.");
			/*******************************************************************************/
			OrganizeManager.instance().executeUpdate(DBConn, "DELETE FROM USR_TEMP_SSO");
			
			/*******************************************************************************/
			logger.debug(">> 사용자step2: SSOV_USER를 USR_TEMP_SSO에 복사한다.");
			/*******************************************************************************/
			createUserTemp(DBUsrDept, DBConn);
			
			// step3 ~ step5
			OrganizeManager.instance().updateUserInfo(DBConn, this);
			
			//부서, 사용자 RANK 일괄변경
			if ( OrganizeManager.instance().updateOrgRank(DBConn) > 0 ) {
				/*******************************************************************************/
				logger.debug(">> 사용자step6: 배포목록디테일(GRPLISTDTL) 기존부서는 명칭및 정렬순서 적용");
				/*******************************************************************************/
				try { OrganizeManager.instance().updateGrpList(DBConn); } catch ( Exception e ) {}
				
				/*******************************************************************************/
				logger.debug(">> 사용자step7: 속성목록디테일(ATTLISTDTL)테이블에 LISTCD='00001'조건으로 삭제후 대상부서인것만 추가");
				/*******************************************************************************/
				try { OrganizeManager.instance().updateAttList(DBConn); } catch ( Exception e ) {}
			}
			
			DBConn.commit();
			setLastRunStat("RUNNING COMPLETE!!!");
			logger.info(getLastRunStat());
			
			runInfoLinkQuery(DBConn, "AFTER");		//정보연계 후 쿼리
		} catch (Exception e) {
			try { DBConn.rollback(); } catch (Exception ex) { }
			AgentUtil.AgentlogError(getLSeq());
			setLastRunStat("error: "+ e);
			logger.error(getLastRunStat(), e);
		} finally {
			try { if (rs != null) { rs.close(); } } catch (Exception ex) { }
			try { if (pstmt != null) { pstmt.close(); } } catch (Exception ex) { }
			try { if (pstmt2 != null) { pstmt2.close(); } } catch (Exception ex) { }
			try { if (DBConn != null) { DBConn.close(); } } catch (Exception ex) { }
			try { if (urs != null) { urs.close(); } } catch (Exception ex) { }
			try { if (upstmt != null) { upstmt.close(); } } catch (Exception ex) { }
			try { if (DBUsrDept != null) { DBUsrDept.close(); } } catch (Exception ex) { }
			isConnect = false;
			isUsrDeptConnect = false;
		}
	}
	
	private void runInfoLinkQuery(Connection DBConn, String type) {
		PreparedStatement pstmt = null;
		
		try {
			if ( appInfo.isInfoLinkQuery() != true ) return;
			
			DBConn.commit();
			
			String[] query = null;
			if ( "BEFORE".equals(type) ) {
				query = appInfo.getInfoLinkQuery_BeforeQuery();
			} else if ( "AFTER".equals(type) ) {
				query = appInfo.getInfoLinkQuery_AfterQuery();
			} 
			
			for ( int i = 0; query != null && i < query.length; i++ ) {
				pstmt = DBConn.prepareStatement(query[i]);
				pstmt.executeUpdate();
			}
			
			DBConn.commit();
			logger.info("InfoLinkQuery" + ":" + type + " RUNNING COMPLETE!!!");
		} catch ( Exception e ) {
			try { DBConn.rollback(); } catch (Exception ex) {}
			logger.error("ERROR : runInfoLinkQuery()" + ":" + type, e);
		} finally {
			ConnectionManager.close(pstmt);
		}
	}

	/**
	 * 새올부서(SSOV_DEPT)를 DEPT_TEMP_SSO로 복사
	 * @param DBConn
	 * @throws Exception
	 */
	private void createDeptTemp(Connection DBUsrDept, Connection DBConn) throws Exception {
		String sql = null;
		PreparedStatement upstmt=null, pstmt = null;
		ResultSet urs=null;
		
		try {	
			//USE_YN 컬럼이 있는지 파악
			boolean existUSEYN = true;
			try {
				sql = "SELECT USE_YN FROM " + appInfo.getUsrdeptDeptTable() + " WHERE ROWNUM = 0";
				upstmt = DBUsrDept.prepareStatement(sql);
				upstmt.executeQuery();
			} catch ( Exception e ) {
				existUSEYN = false;
			} finally {
				try { upstmt.close(); } catch (Exception e) {}
			}
			
			sql = "SELECT * \n" +
				  "FROM " + appInfo.getUsrdeptDeptTable() + " \n" +
				  "WHERE DEPT_ID NOT IN (SELECT DEPT_ID \n" +
				  "                      FROM " + appInfo.getUsrdeptDeptTable() + " \n" +
				  "                      GROUP BY DEPT_ID \n" +
				  "                      HAVING COUNT(DEPT_ID) > 1) \n" +
				  "AND (UPPER_DEPT_ID IN (SELECT DEPT_ID FROM " + appInfo.getUsrdeptDeptTable() + ") \n" +
				  "     OR DEPT_ID = '" + appInfo.getRootid() + "') \n";
			upstmt = DBUsrDept.prepareStatement(sql);
			urs = upstmt.executeQuery();
			
			sql = "INSERT INTO DEPT_TEMP_SSO (DEPT_ID, BASE_SYS, DEPT_NAME, DEPT_FULLNAME, UPPER_DEPT_ID, \n" +
			      "       TOP_DEPT_ID, ORG_DIV_ID, DEPT_ORDER, DEPT_RANK, DEPT_TEL, \n" +
			      "       DEPT_FAX, DEPT_SE,  APPLY_DAY,  REG_DAY, UPDATE_DAY, \n" +
			      "		  USE_YN, CON_YN) \n" +
			      "VALUES (?,?,?,?,?, ?,?,?,?,?, \n" +
			      "        ?,?,?,?,?, ?,?) \n";
			pstmt = DBConn.prepareStatement(sql);
			int pIndex=0;
			while(urs.next()) {
				pstmt.clearParameters();
				pIndex=1;
				pstmt.setString(pIndex++, urs.getString("DEPT_ID"));
				pstmt.setString(pIndex++, urs.getString("BASE_SYS"));
				pstmt.setString(pIndex++, urs.getString("DEPT_NAME"));
				pstmt.setString(pIndex++, urs.getString("DEPT_FULLNAME"));
				pstmt.setString(pIndex++, urs.getString("UPPER_DEPT_ID"));
				pstmt.setString(pIndex++, urs.getString("TOP_DEPT_ID"));
				pstmt.setString(pIndex++, urs.getString("ORG_DIV_ID"));
				pstmt.setString(pIndex++, urs.getString("DEPT_ORDER"));	
				String deptrank = Utils.nullToEmptyString(urs.getString("DEPT_RANK")).trim();
				if ( deptrank.trim().equals("") ) {
					pstmt.setString(pIndex++, "99999");
				} else {
					pstmt.setString(pIndex++, deptrank);
				}
				pstmt.setString(pIndex++, urs.getString("DEPT_TEL"));
				pstmt.setString(pIndex++, urs.getString("DEPT_FAX"));
				pstmt.setString(pIndex++, urs.getString("DEPT_SE"));
				pstmt.setString(pIndex++, urs.getString("APPLY_DAY"));
				pstmt.setString(pIndex++, urs.getString("REG_DAY"));
				pstmt.setString(pIndex++, urs.getString("UPDATE_DAY"));				
				if ( existUSEYN == true ) {
					if ( urs.getString("USE_YN").equals("0") || urs.getString("USE_YN").equals("N") ) {
						pstmt.setString(pIndex++, "N");
					} else {
						pstmt.setString(pIndex++, "Y");
					}
				} else {
					pstmt.setString(pIndex++, "Y");
				}
				pstmt.setString(pIndex++, "Y");
				pstmt.executeUpdate();
			}
		} finally {
			try { urs.close(); } catch (Exception e) { }
			try { upstmt.close(); } catch (Exception e) { }
			try { pstmt.close(); } catch (Exception e) { }
		}
	}

	/**
	 * 새올사용자(SSOV_DEPT)를 USR_TEMP_SSO로 복사
	 * @param DBConn
	 * @throws Exception
	 */
	private void createUserTemp(Connection DBUsrDept, Connection DBConn) throws Exception {
		String sql = null;
		PreparedStatement upstmt=null, pstmt = null;
		ResultSet urs=null;
		
		try {
			//USE_YN 컬럼이 있는지 파악
			boolean existUSEYN = true;
			try {
				sql = "SELECT USE_YN FROM " + appInfo.getUsrdeptUserTable() + " WHERE ROWNUM = 0";
				upstmt = DBUsrDept.prepareStatement(sql);
				upstmt.executeQuery();
			} catch ( Exception e ) {
				existUSEYN = false;
			} finally {
				try { upstmt.close(); } catch (Exception e) { }
			}
			
			sql = "SELECT \n" +
//db암호화 적용시
				  " FC_CMM_DEC( USER_SN, 'ICA', '1', 'T' ) USER_SN \n" +
//db암호화 미적용시
//				  " USER_SN \n" +				  
		    	  " ,USER_ID,USER_NAME,USER_STAT,USER_STAT_NAME,REGULARITY,ORG_ID,ORG_NAME,DEPT_ID,DEPT_NAME,DEPT_FULLNAME \n" +
	    	      " ,CLASS_ID,CLASS_NAME,POSITION_ID,POSITION_NAME,GRADE_ID,GRADE_NAME,EMAIL,TEL,MOBILE,JOIN_DAY,RETIRE_DAY \n" +
	    	      " ,ADD_INFO1,ADD_INFO2,ADD_INFO3,ADD_INFO4,ADD_INFO5,ADD_INFO6,ADD_INFO7,REG_DAY,UPDATE_DAY,USE_YN \n" +
				  " FROM " + appInfo.getUsrdeptUserTable() + " \n" +
				  " WHERE USER_ID NOT IN (SELECT USER_ID \n" +
				  "                      FROM " + appInfo.getUsrdeptUserTable() + " \n" +
				  "                      GROUP BY USER_ID \n" +
				  "                      HAVING COUNT(USER_ID) > 1) \n";
//			logger.info("ntis query : " + sql);
			upstmt = DBUsrDept.prepareStatement(sql);
			urs = upstmt.executeQuery();
			
			sql = "INSERT INTO USR_TEMP_SSO (USER_ID, USER_NAME, USER_SN, USER_STAT, USER_STAT_NAME, \n" +
				  "                      	 REGULARITY, ORG_ID, ORG_NAME, DEPT_ID, DEPT_NAME, \n" +
				  "                      	 DEPT_FULLNAME, CLASS_ID, CLASS_NAME, POSITION_ID, POSITION_NAME, \n" +
				  "                      	 GRADE_ID, GRADE_NAME, EMAIL, TEL, MOBILE, \n" +
				  "                      	 JOIN_DAY, RETIRE_DAY, ADD_INFO1, ADD_INFO2, ADD_INFO3, \n" +
				  "                      	 ADD_INFO4, ADD_INFO5, ADD_INFO6, ADD_INFO7, BASE_SYS, \n" +
				  "                      	 REG_DAY, UPDATE_DAY, USE_YN, CON_YN, RDUTY_NAME) \n" +
			      "VALUES (?,?,?,?,?, ?,?,?,?,?, \n" +
			      "        ?,?,?,?,?, ?,?,?,?,?, \n" +
			      "        ?,?,?,?,?, ?,?,?,?,?, \n" +
			      "        ?,?,?,?,?) \n";
			pstmt = DBConn.prepareStatement(sql);
			int pIndex=0;
			
			while(urs.next()) {
				pstmt.clearParameters();
				pIndex=1;
				pstmt.setString(pIndex++, urs.getString("USER_ID"));
				pstmt.setString(pIndex++, urs.getString("USER_NAME"));
				//pstmt.setString(pIndex++, "");
				pstmt.setString(pIndex++, Utils.nullToEmptyString(urs.getString("USER_SN")));
				//강원도원주시,인천시,부산시청(현재 ldap) USER_SN 에 USER_ID값 사용
				//pstmt.setString(pIndex++, urs.getString("USER_ID"));
				//부산동구는 아래 주석제거하고 컴파일해야함
				//pstmt.setString(pIndex++, com.api.crypt.dec(Utils.nullToEmptyString(urs.getString("USER_SN"))));
				pstmt.setString(pIndex++, urs.getString("USER_STAT"));
				pstmt.setString(pIndex++, urs.getString("USER_STAT_NAME"));
				pstmt.setString(pIndex++, urs.getString("REGULARITY"));
				pstmt.setString(pIndex++, urs.getString("ORG_ID"));
				pstmt.setString(pIndex++, urs.getString("ORG_NAME"));
				pstmt.setString(pIndex++, urs.getString("DEPT_ID"));
				pstmt.setString(pIndex++, urs.getString("DEPT_NAME"));
				pstmt.setString(pIndex++, urs.getString("DEPT_FULLNAME"));
				pstmt.setString(pIndex++, urs.getString("CLASS_ID"));
				pstmt.setString(pIndex++, urs.getString("CLASS_NAME"));
				pstmt.setString(pIndex++, urs.getString("POSITION_ID"));
				pstmt.setString(pIndex++, urs.getString("POSITION_NAME"));
				pstmt.setString(pIndex++, Utils.nullToEmptyString(urs.getString("GRADE_ID")).trim());
				pstmt.setString(pIndex++, urs.getString("GRADE_NAME"));
				pstmt.setString(pIndex++, urs.getString("EMAIL"));
				pstmt.setString(pIndex++, urs.getString("TEL"));
				pstmt.setString(pIndex++, urs.getString("MOBILE"));
				pstmt.setString(pIndex++, urs.getString("JOIN_DAY"));
				pstmt.setString(pIndex++, urs.getString("RETIRE_DAY"));
				pstmt.setString(pIndex++, urs.getString("ADD_INFO1"));
				pstmt.setString(pIndex++, urs.getString("ADD_INFO2"));
				pstmt.setString(pIndex++, urs.getString("ADD_INFO3"));
				pstmt.setString(pIndex++, urs.getString("ADD_INFO4"));
				pstmt.setString(pIndex++, urs.getString("ADD_INFO5"));
				pstmt.setString(pIndex++, urs.getString("ADD_INFO6"));
				pstmt.setString(pIndex++, urs.getString("ADD_INFO7"));
				pstmt.setString(pIndex++, "");	//BASE_SYS
				pstmt.setString(pIndex++, urs.getString("REG_DAY"));
				pstmt.setString(pIndex++, urs.getString("UPDATE_DAY"));
				if ( existUSEYN == true ) {					
					if ( urs.getString("USE_YN").equals("0") || urs.getString("USE_YN").equals("N") ) {
						pstmt.setString(pIndex++, "N");
					} else {
						pstmt.setString(pIndex++, "Y");
					}
				} else {
					pstmt.setString(pIndex++, "Y");
				}
				if ( appInfo.getUsrdeptUserstat().indexOf(Utils.nullToEmptyString(urs.getString("USER_STAT"))) == -1
						|| appInfo.getUsrdeptRegularity().indexOf(Utils.nullToEmptyString(urs.getString("REGULARITY"))) == -1 ) {
					pstmt.setString(pIndex - 1, "N");
				}
				pstmt.setString(pIndex++, "Y");
				pstmt.setString(pIndex++, "");	//RDUTY_NAME
				pstmt.executeUpdate();
			}
		} finally {
			try { urs.close(); } catch (Exception e) { }
			try { upstmt.close(); } catch (Exception e) { }
			try { pstmt.close(); } catch (Exception e) { }
		}
	}
}
