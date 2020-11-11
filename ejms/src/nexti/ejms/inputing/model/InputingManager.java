/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 입력하기 manager
 * 설명:
 */
package nexti.ejms.inputing.model;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.commtreat.model.CommTreatManager;
import nexti.ejms.delivery.model.DeliveryManager;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.util.Utils;

public class InputingManager {
	
	private static Logger logger = Logger.getLogger(InputingManager.class);
	
	private static InputingManager instance = null;
	private static InputingDAO dao = null;
	
	public static InputingManager instance() {
		if (instance==null) instance = new InputingManager(); 
		return instance;
	}
	
	private InputingDAO getInputingDAO(){
		if (dao==null) dao = new InputingDAO(); 
		return dao;
	}
	
	private InputingManager() {
	}
	
	/**
	 * 제출부서 입력완료 여부 체크
	 * @param sysdocno 시스템문서번호
	 * @param tgtdeptcd 제출부서코드
	 * @return 제출완료sysdocno,tgtdeptcd,inputusrid(1명)
	 */
	public String[] isTgtdeptInputComplete(int sysdocno, String tgtdeptcd) throws Exception {
		
		Connection con = null;
		String[] result = null;
		
		try {
			con = ConnectionManager.getConnection(false);
			
			result = isTgtdeptInputComplete(con, sysdocno, tgtdeptcd);
			
			con.commit();
		} catch(Exception e) {
			logger.error("ERROR", e);
			try {
				con.rollback();
			} catch(Exception ex) {
				logger.error("ERROR", ex);
			}
			ConnectionManager.close(con);
			throw e;
		} finally {
			ConnectionManager.close(con);
		}
		
		
		return result;		
	}
	
	public String[] isTgtdeptInputComplete(Connection con, int sysdocno, String tgtdeptcd) throws Exception {
		return getInputingDAO().isTgtdeptInputComplete(con, sysdocno, tgtdeptcd);
	}

	/**
	 * 입력하기 목록
	 * 
	 * @param userid : 사용자ID
	 * @param start : 목록 시작 인덱스
	 * @param end : 목록 마지막 인덱스
	 * 
	 * @return List : 입력목록
	 * @throws Exception 
	 */
	public List inputingList(String userid, String deptcd, int gubun, String initentry, String isSysMgr, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, int start, int end) throws Exception {
		return getInputingDAO().inputingList(userid, deptcd, gubun, initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm, start, end);
	}
	
	/**
	 * 입력완료 목록
	 * 
	 * @param userid : 사용자ID
	 * @param start : 목록 시작 인덱스
	 * @param end : 목록 마지막 인덱스
	 * @param searchtext : 검색어
	 * @param selyear : 선택년
	 * 
	 * @return List : 입력완료목록
	 * @throws Exception 
	 */
	public List inputCompleteList(String userid, String deptcd, String initentry, String isSysMgr, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, int start, int end, String searchvalue, String selyear) throws Exception {
		return getInputingDAO().inputCompleteList(userid, deptcd, initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm, start, end, searchvalue, selyear);
	}
	
	/**
	 * 마지막 입력자인지 체크
	 * 03: 해당없음
	 * 04: 입력완료
	 * 05: 마감시한 초과
	 */
	public int doLastInputCompleteCheck(int sysdocno, String usrid, String deptcd) throws Exception {
		
		Connection con = null;
		int result = 0;
		
		try {
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			result = doLastInputCompleteCheck(con, sysdocno, usrid, deptcd);
			
			con.commit();
		} catch(Exception e) {
			logger.error("ERROR", e);
			try {
				con.rollback();
			} catch(Exception ex) {
				logger.error("ERROR", ex);
			}
			ConnectionManager.close(con);
			throw e;
		} finally {
			ConnectionManager.close(con);
		}
		
		return result;
	}
	
	/**
	 * 마지막 입력자인지 체크
	 * 03: 해당없음
	 * 04: 입력완료
	 * 05: 마감시한 초과
	 */
	public int doLastInputCompleteCheck(Connection conn, int sysdocno, String usrid, String deptcd) throws Exception {
		int result = 0;

		//마지막 입력자가 아니면 리턴
		if (getInputingDAO().IsLastInputUsr(conn, sysdocno, usrid, deptcd) == true) {
			/*
			//검토자가 존재하면
			if(getInputingDAO().checkExistApprovalUsr(sysdocno, deptcd, "1")) {
				result = getInputingDAO().updateSubmitState(conn, sysdocno, usrid, deptcd, "03");
				result = getInputingDAO().updateSubmitDT(conn, sysdocno, usrid, deptcd, "1");
			}
			//승인자가 존재하면
			else if(getInputingDAO().checkExistApprovalUsr(sysdocno, deptcd, "2")) {
				result = getInputingDAO().updateSubmitState(conn, sysdocno, usrid, deptcd, "04");
				result = getInputingDAO().updateSubmitDT(conn, sysdocno, usrid, deptcd, "2");
			} else {
				result = getInputingDAO().updateSubmitState(conn, sysdocno, usrid, deptcd, "05");
			}
			//결재자가(승인자) 없고, 마지막 제출부서이면
			if (getInputingDAO().checkExistApprovalUsr(sysdocno, deptcd, "2")!=true 
				&& DeliveryManager.instance().IsLastDeliveryDept(sysdocno, deptcd)) {
				result = getInputingDAO().updateDocState(conn, sysdocno, usrid, "05");
			}
			*/
			
			result = getInputingDAO().updateSubmitState(conn, sysdocno, usrid, deptcd, "05");

			CommTreatManager ctmgr = CommTreatManager.instance();
			String[][] deptInfo = FormatManager.instance().getInputDeptInfo(conn, sysdocno, deptcd);
			for ( int i = deptInfo.length - 1; i + 1 > 0; i-- ) {
				if ( "".equals(Utils.nullToEmptyString(deptInfo[i][0])) ) continue;
				
				int tcnt = 0;
				int scnt = 0;
				
				List fg = ctmgr.getFormationGroup(conn, "", sysdocno, null, deptInfo[i][0], "", "", "", "");
				if ( fg != null ) {
					tcnt = fg.size();
				}

				//fg = ctmgr.getFormationGroup(conn, "", sysdocno, null, deptInfo[i][0], "05,06", "", "", "03,04,05");
				fg = ctmgr.getFormationGroup(conn, "", sysdocno, null, deptInfo[i][0], "", "", "", "01,02");
				if ( fg != null ) {
					scnt = fg.size();
				}
				
				if ( tcnt > 0 && scnt < 1 ) {
					result += getInputingDAO().updateSubmitState(conn, sysdocno, usrid, deptInfo[i][0], "05");
				}
			}
			
			//결재자가(승인자) 없고, 마지막 제출부서이면
			if (DeliveryManager.instance().IsLastDeliveryDept(conn, sysdocno, deptcd)) {
				result = getInputingDAO().updateDocState(conn, sysdocno, usrid, "05");
			}

		}

		return result;
	}
	
	/**
	 * 입력완료처리 프로세스
	 * 03: 해당없음
	 * 04: 입력완료
	 * 05: 마감시한 초과
	 */
	private int inputingProcess(Connection conn, int sysdocno, String usrid, String deptcd, String uptusrid, String state) throws Exception {
		int result = 0;
		
		//입력상태 변경
		result = getInputingDAO().updateInputState(conn, sysdocno, usrid, deptcd, uptusrid, state);
		
		if ( result > 0 ) {
			result += doLastInputCompleteCheck(conn, sysdocno, usrid, deptcd);
		}
		
		return result;
	}
	
	/** 
	 * 입력하기 상세 - 취합문서해당없음 처리
	 * 
	 * @param usrid : 사용자ID
	 * @param sysdocno : 시스템 문서번호
	 * @param deptcd : 부서코드	
	 * 
	 * @return int : 처리결과
	 * @throws Exception 
	 */
	public int inputingNotApplicable(int sysdocno, String usrid, String deptcd) throws Exception {
		Connection conn = null;
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
			
			//취합문서 해당없음 처리하기 전 입력된 데이터는 삭제
			getInputingDAO().deleteInputData(conn, "DATALINEFRM", sysdocno, deptcd, usrid);
			getInputingDAO().deleteInputData(conn, "DATAFIXEDFRM", sysdocno, deptcd, usrid);
			getInputingDAO().deleteInputData(conn, "DATABOOKFRM", sysdocno, deptcd, usrid);

			//처리프로세스 (취합문서해당없음)
			result = inputingProcess(conn, sysdocno, usrid, deptcd, usrid, "03");
			
			conn.commit();
		} catch (Exception e) {
			try { conn.rollback(); } catch (Exception ex) {}
			ConnectionManager.close(conn);
			throw e;
		} finally {
			ConnectionManager.close(conn);
		}
		
		return result;
	}
	
	/** 
	 * 입력하기 상세 - 입력완료 처리
	 * 
	 * @param usrid : 사용자ID
	 * @param sysdocno : 시스템 문서번호
	 * @param deptcd : 부서코드
	 * 
	 * @return int : 처리결과
	 * @throws Exception 
	 */
	public int inputingComplete(int sysdocno, String usrid, String deptcd) throws Exception {
		Connection conn = null;
		int result = 0; 
		
		try {
			conn = ConnectionManager.getConnection(false);

			//처리프로세스 (입력완료)
			result = inputingProcess(conn, sysdocno, usrid, deptcd, usrid, "04");
			
			conn.commit();
		} catch (Exception e) {
			try { conn.rollback(); } catch (Exception ex) {}
			ConnectionManager.close(conn);
			throw e;
		} finally {
			ConnectionManager.close(conn);
		}
		
		return result;
	}
	
	/** 
	 * 마감시한처리(agent) - 마감시한 초과 처리
	 * 
	 * @param usrid : 사용자ID
	 * @param sysdocno : 시스템 문서번호
	 * @param deptcd : 부서코드
	 * @param uptusrid : 수정자	
	 * 
	 * @return 없음
	 */
	public void inputingDeadlineProc(Connection conn, int sysdocno, String usrid, String deptcd, String uptusrid) throws Exception {
		//처리프로세스 (입력완료)
		inputingProcess(conn, sysdocno, usrid, deptcd, uptusrid, "05");	
	}

	/** 
	 * 입력하기 목록 갯수 가져오기	
	 * @throws Exception 
	 */
	public int inputingCnt(String userid, String deptcd, int gubun, String initentry, String isSysMgr, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm) throws Exception {
		return getInputingDAO().inputingCnt(userid, deptcd, gubun, initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm);
	}
	
	/** 
	 * 입력하기 목록 갯수 가져오기	
	 * @throws Exception 
	 */
	public int inputingTotCnt(String userid, String deptcd, int gubun) throws Exception {
		return getInputingDAO().inputingTotCnt(userid, deptcd, gubun);
	}

	/** 
	 * 입력완료 목록 갯수 가져오기	
	 * 
	 * @param userid : 사용자ID
	 * @param searchvalue : 검색어
	 * @param selyear : 선택년도
	 * 
	 * @return int : 목록 카운트
	 * @throws Exception 
	 */
	public int inputCompleteCnt(String userid, String deptcd, String initentry, String isSysMgr, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, String searchvalue, String selyear) throws Exception {
		return getInputingDAO().inputCompleteCnt(userid, deptcd, initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm, searchvalue, selyear);
	}
	
	/** 
	 * 입력완료 목록 갯수 가져오기	
	 * 
	 * @param userid : 사용자ID
	 * @param searchvalue : 검색어
	 * @param selyear : 선택년도
	 * 
	 * @return int : 목록 카운트
	 * @throws Exception 
	 */
	public int inputCompleteTotCnt(String userid, String deptcd, String searchvalue, String selyear) throws Exception {
		return getInputingDAO().inputCompleteTotCnt(userid, deptcd, searchvalue, selyear);
	}
	
	/** 
	 * 입력하기 - 입력담당자 담당단위 지정 여부 확인
	 * 
	 * @param usrid : 사용자ID
	 * 
	 * @return true, false	
	 * @throws Exception 
	 */
	public String IsAssignInputUsrcharge(String usrid) throws Exception {
		String retCode = "";
		
		if(getInputingDAO().IsAssignInputUsrcharge(usrid)) {
			retCode = "ASSIGN";
		} else {
			retCode = "NOTASSIGN";
		}
		
		return retCode;
	}
	
	/** 
	 * 입력하기 상세 - 입력데이터 존재 여부 체크
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param formseq : 양식일련번호
	 * @param deptcd : 부서코드
	 * @param usrid : 사용자ID
	 * 
	 * @return true : 데이터 존재, false : 데이터 미존재	
	 * @throws Exception 
	 */
	public String IsExistDataFrm(int sysdocno, String deptcd, String usrid) throws Exception {
		String retCode = "";
		
		boolean blnDataLine = getInputingDAO().IsExistDataLineFrm(sysdocno, deptcd, usrid);
		boolean blnDataFixed = getInputingDAO().IsExistDataFixedFrm(sysdocno, deptcd, usrid);
		boolean blnDataBook = getInputingDAO().IsExistDataBookFrm(sysdocno, deptcd, usrid);
		
		if(blnDataLine || blnDataFixed || blnDataBook) {
			retCode = "EXIST";
		} else {
			retCode = "NOTEXIST";
		}
		
		return retCode;
	}
	
	/** 
	 * 결재진행중인 건수 가져오기-(입력현황-메인화면)
	 * 
	 * @param usrid : 사용자ID
	 * 
	 * @return int : 결재진행중인 건수
	 * @throws Exception 
	 */
	public int procCount(String userid, String deptcd) throws Exception {
		return getInputingDAO().procCount(userid,deptcd);
	}
	

	/**
	 * 입력하기 - 관리자인경우 검색 조건에 해당하는 값 가져오기 
	 */
	public String getSearchInputing(int gubun, String sch_deptcd, String sch_userid) throws Exception{
		return getInputingDAO().getSearchInputing(gubun, sch_deptcd, sch_userid);
	}

	/**
	 * 입력완료 - 관리자인경우 검색 조건에 해당하는 값 가져오기 
	 */
	public String getSearchInputComplete(String gubun, String sch_deptcd, String sch_userid) throws Exception{
		return getInputingDAO().getSearchInputComplete(gubun, sch_deptcd, sch_userid);
	}
}
