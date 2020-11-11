package nexti.ejms.agent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

import nexti.ejms.agent.model.SidoLdapBean;
import nexti.ejms.agent.model.SidoLdapManager;
import nexti.ejms.common.ConnectionManager;
import nexti.ejms.common.appInfo;
import nexti.ejms.organ.model.OrganizeManager;

import org.apache.log4j.Logger;

public class TaskSidoLdapGetting extends TaskBase {
	private static TaskSidoLdapGetting _instance;
	public static TaskSidoLdapGetting getInstance() {
		if(_instance == null) { _instance = new TaskSidoLdapGetting(); }
		return _instance;
	}
	private static Logger logger = Logger.getLogger(TaskSidoLdapGetting.class);
	
	private Connection DBConn = null;
	private Connection DBSido = null;
	private boolean isSidoLdapConnect = false;
	private boolean isConnect = false;

	private void DBConnection() {
		if(!isConnect) {
			AgentUtil.Disconnection(DBConn);
			DBConn = AgentUtil.getConnection();
			if(DBConn==null) isConnect = false;
			else isConnect = true;
		}
		if(!isSidoLdapConnect) {
			try { if (DBSido != null) { DBSido.close(); } } catch (Exception ex) { }
			try {
				Class.forName(appInfo.getSidoldapDbtype());
				DBSido = DriverManager.getConnection(appInfo.getSidoldapDbip(), appInfo.getSidoldapDbuser(), appInfo.getSidoldapDbpass());
				isSidoLdapConnect = true;
			} catch (Exception e) {
				isSidoLdapConnect = false;
				setLastRunStat("SidoLdap database connection error:" + e);
				logger.error(getLastRunStat());				
			}
		}
	}
	
	public void run() {
		if(!getIsRun()) return;
		setLastRunDateTime();
		DBConnection();
		
		if (!isSidoLdapConnect) {
			AgentUtil.AgentlogError(getLSeq());
			return;
		}
		if (!isConnect) {
			AgentUtil.AgentlogError(getLSeq());
			return;
		}

		List sidoLdapList = null;
		int changeSidoLDAP1 = 0;
		int changeSidoLDAP2 = 0;
		int sidoUpdate = 0;
		
		try {
			runInfoLinkQuery(DBConn, "BEFORE");		//정보연계 전 쿼리
			
			/**
			 * 1. 시도LDAP 동기화 조직정보테이블의 동기화 데이터 가져오기.
			 *    1) 동기화최종정보테이블에서 최종으로 반영한 내용의 일자와 순번을 가져온다.
			 *    2) LOG_SEQ 최대길이 체크
			 *    3) 동기화정보의 최종정보이후의 변경내용에 대한 일시 순서 사용자구분 명령형식을 가져온다.
			 * 2. 변경된 LDAP동기화정보를 사용자정보와 부서정보 테이블에 갱신 - [관]LDAP사용자정보(연계용), [관]부서정보임시(연계용)   
			 * 	  1) 사용자구분 0:사용자 1:조직 -- INSERT, UPDATE
			 * 2. 변경된 LDAP동기화정보를 사용자정보와 부서정보 테이블에 갱신- [관]사용자정보, [관]부서정보)    
			 * 	  1) 사용자구분 0:사용자 1:조직 -- INSERT, UPDATE
			 * 4. LDAP동기화최종정보 테이블에 최종 변경사항반영 - 일시 및 순서 업데이트
			 * 	  1) 조직정보 저장
			 */
			
			OrganizeManager.instance().executeUpdate(DBConn,
					"INSERT INTO USR_TEMP_LDAP(RESINUMBER) \n" +
					"SELECT USER_SN FROM USR WHERE USER_SN IS NOT NULL \n" +
					"MINUS SELECT RESINUMBER FROM USR_TEMP_LDAP \n");
			
			OrganizeManager.instance().executeUpdate(DBConn,
					"INSERT INTO DEPT_TEMP_LDAP(OUCODE) \n" +
					"SELECT DEPT_ID FROM DEPT \n" +
					"MINUS SELECT OUCODE FROM DEPT_TEMP_LDAP \n");
			
			DBConn.commit();

			SidoLdapManager manager = SidoLdapManager.instance();
			
			logger.debug("step~1: sidoLdapList");
			//LDA동기화최종정보 테이블에서 변경 이력을 체크하여 변경된 정보만 가져옴
			// [1]
			sidoLdapList = manager.getSidoLdapList(DBSido);

			if ( sidoLdapList.size() > 0 ) {
				setLastRunStat("전체변경정보 : 사용자/부서(" + sidoLdapList.size() + "), 가져오는중...");
				logger.info(getLastRunStat());
				
				logger.debug("step~2: changeSidoLDAP1");
				//변경된 LDAP동기화정보를 사용자정보와 부서정보 테이블에 갱신 - [관]LDAP사용자정보(연계용), [관]부서정보임시(연계용)
				// [2]
				changeSidoLDAP1 = manager.changeSidoLDAP1(sidoLdapList);
				logger.info("정보변경진행 "+changeSidoLDAP1);
				//사용자정보와 부서정보 테이블 -> 사용자정보와 부서정보 테이블 갱신 - [관]사용자정보, [관]부서정보)    
				if ( changeSidoLDAP1 > 0 ) {
					// [3]
					changeSidoLDAP2 = manager.changeSidoLDAP2();
				}
				logger.info("정보변경진행 a");
				//LDAP동기화최종정보 테이블에 동기화 이력 갱신
				// [4]
				sidoUpdate = manager.sidoUpdate(DBSido, (SidoLdapBean)sidoLdapList.get(sidoLdapList.size()-1));
				logger.info("정보변경진행 b");
				if ( changeSidoLDAP2 > 0 && sidoUpdate > 0 ) {
					setLastRunStat("COMPLETE(이력O/정보O UPDATE:" + sidoLdapList.size() + "[" + changeSidoLDAP1 + "])");
				} else if ( changeSidoLDAP2 > 0 && sidoUpdate == 0 ) {
					setLastRunStat("COMPLETE(이력X/정보O UPDATE:" + sidoLdapList.size() + "[" + changeSidoLDAP1 + "])");
				} else if ( changeSidoLDAP2 == 0 && sidoUpdate > 0 ) {
					setLastRunStat("COMPLETE(이력O/정보X UPDATE:" + sidoLdapList.size() + "[" + changeSidoLDAP1 + "])");
				} else if ( changeSidoLDAP2 == 0 && sidoUpdate == 0 ) {
					setLastRunStat("COMPLETE(이력X/정보X UPDATE:" + sidoLdapList.size() + "[" + changeSidoLDAP1 + "])");
				} else {
					setLastRunStat("COMPLETE(LDAP NO DATA:" + sidoLdapList.size() + "[" + changeSidoLDAP1 + "])");
				}
			} else {
				setLastRunStat("COMPLETE(LDAP NO DATA)");	//ldap 정보 없음
			}
			logger.info(getLastRunStat());
			//부서, 사용자 RANK 일괄변경
			if ( OrganizeManager.instance().updateOrgRank(DBConn) > 0 ) {
				//배포목록디테일(GRPLISTDTL) 기존부서는 명칭만 변경
				try { OrganizeManager.instance().updateGrpList(DBConn); } catch ( Exception e ) {}
				logger.info("랭크변경 1");
				//속성목록디테일(ATTLISTDTL)테이블에 LISTCD='00001'조건으로 삭제후 대상부서인것만 추가
				try { OrganizeManager.instance().updateAttList(DBConn); } catch ( Exception e ) {}
				logger.info("랭크변경 2");
			}
			
			DBConn.commit();
			DBSido.commit();
			logger.info(getLastRunStat());
			logger.info("후쿼리완");
			runInfoLinkQuery(DBConn, "AFTER");		//정보연계 후 쿼리
		} catch (Exception e) {
			try { DBConn.rollback(); } catch (Exception ex) { }
			AgentUtil.AgentlogError(getLSeq());
			setLastRunStat("error: "+ e);
			logger.error(getLastRunStat(), e);
		} finally {
			try { if (DBConn != null) { DBConn.close(); } } catch (Exception ex) { }
			try { if (DBSido != null) { DBSido.close(); } } catch (Exception ex) { }
			isConnect = false;
			isSidoLdapConnect = false;
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
}