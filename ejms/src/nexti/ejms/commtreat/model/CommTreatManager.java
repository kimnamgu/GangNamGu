/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공통처리현황 manager
 * 설명:
 */
package nexti.ejms.commtreat.model;

import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.commapproval.model.commapprovalManager;
import nexti.ejms.common.ConnectionManager;
import nexti.ejms.ccd.model.CcdManager;
import nexti.ejms.util.Utils;

public class CommTreatManager {
	private static CommTreatManager instance = null;
	private static CommTreatDAO dao = null;
	private static Logger logger = Logger.getLogger(CommTreatManager.class);
	
	public static CommTreatManager instance() {
		if (instance==null) instance = new CommTreatManager(); 
		return instance;
	}
	
	private CommTreatDAO getCommTreatDAO(){
		if (dao==null) dao = new CommTreatDAO(); 
		return dao;
	}
	
	private CommTreatManager() {}
	
	/**
	 * 취합대상정보 가져오기
	 * @param sysdocno
	 * @param tgtdeptcd
	 * @param sch_deptid
	 * @param sch_submitsate
	 * @param sch_userid
	 * @param sch_inputstate
	 * @return
	 * @throws Exception
	 */
	public String targetDeptCheck(String type, String sessionId, int sysdocno, String tgtdeptcd, String sch_deptid, String sch_submitsate, String sch_userid, String sch_inputstate) throws Exception {
		StringBuffer result = null;
		boolean check = false;
		
		if ( "DEPT_EXIST".equals(type) ) {
			if (  !tgtdeptcd.equals(sch_deptid) ) {
				if ( getFormationGroup(sessionId, sysdocno, null, tgtdeptcd, sch_deptid, "", "", "") != null ) {
					check = true;
				}
			}
		} else if ( "USER_EXIST".equals(type) ) {
			if ( getFormationGroup(sessionId, sysdocno, null, tgtdeptcd, "", "", sch_userid, "") != null ) {
				check = true;
			}
		} else if ( "DEPT_DATA_EXIST".equals(type) ) {
			if ( getFormationGroup(sessionId, sysdocno, null, tgtdeptcd, sch_deptid, "02,03,04,05", "", "") != null ) {
				check = true;
			}
		} else if ( "USER_DATA_EXIST".equals(type) ) {
			if ( getFormationGroup(sessionId, sysdocno, null, tgtdeptcd, "", "", sch_userid, "02,03,04,05") != null ) {
				check = true;
			}
		}
		
		result = new StringBuffer();
		result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		result.append("<result value=\"" + check + "\"/>\n");
		
		return result.toString();
	}
	
	/**
	 * 취합대상레벨 가져오기
	 * @param con
	 * @param sysdocno
	 * @param deptid
	 * @return
	 * @throws Exception
	 */
	public int getTargetDeptLevel(Connection con, int sysdocno, String deptid) throws Exception {
		int result = 0;
		
		String tgtdeptcd = "";
		
		if ( sysdocno <= 0 ) {
			tgtdeptcd = "M_" + deptid;
		} else {
			tgtdeptcd = ColldocManager.instance().getColldoc(sysdocno).getColdeptcd();
		}
		
		List fgrp = getCommTreatDAO().getFormationGroup(con, "", sysdocno, null, tgtdeptcd, deptid, "", "", "", false);
		
		if ( fgrp != null ) {
			result = ((CommTreatBean)fgrp.get(0)).getLevel();
		}

		return result;
	}
	
	/**
	 * 취합대상레벨 가져오기
	 * @param sysdocno
	 * @param deptid
	 * @return
	 * @throws Exception
	 */
	public int getTargetDeptLevel(int sysdocno, String deptid) throws Exception {
		int result = 0;
		Connection con = null;
		
		try {
			con = ConnectionManager.getConnection();
			
			result = getTargetDeptLevel(con, sysdocno, deptid);
		} catch ( Exception e ) {
			ConnectionManager.close(con);
			throw e;
		} finally {
			ConnectionManager.close(con);
		}
		
		return result; 
	}
	
	/**
	 * 취합대상 정보
	 * @param sessionId
	 * @param sysdocno
	 * @param tgtdeptcd
	 * @param sch_deptid
	 * @param sch_submitsate
	 * @param sch_userid
	 * @param sch_inputstate
	 * @return
	 * @throws Exception
	 */
	public List getFormationGroup(String sessionId, int sysdocno, String userid, String tgtdeptcd, String sch_deptid, String sch_submitsate, String sch_userid, String sch_inputstate) throws Exception {		
		List result = null;
		Connection con = null;
		
		try {
			con = ConnectionManager.getConnection();
			
			result = getFormationGroup(con, sessionId, sysdocno, userid, tgtdeptcd, sch_deptid, sch_submitsate, sch_userid, sch_inputstate);
		} catch(Exception e) {
			ConnectionManager.close(con);
			throw e;
		} finally {
			ConnectionManager.close(con);
		}
		
		return result;
	}
	
	public List getFormationGroup(Connection con, String sessionId, int sysdocno, String userid, String tgtdeptcd, String sch_deptid, String sch_submitsate, String sch_userid, String sch_inputstate) throws Exception {
		return getCommTreatDAO().getFormationGroup(con, sessionId, sysdocno, userid, tgtdeptcd, sch_deptid, sch_submitsate, sch_userid, sch_inputstate, false);
	}
	
	/**
	 * 이전대상부서 가져오기
	 * @param con
	 * @param sysdocno
	 * @param deptid
	 * @return
	 * @throws Exception
	 */
	public String getPreDeptcd(int sysdocno, String deptid, boolean isDeliveried) throws Exception {
		String result = null;
		Connection con = null;
		
		try {
			con = ConnectionManager.getConnection();
			
			result = getPreDeptcd(con, sysdocno, deptid, isDeliveried);
		} catch ( Exception e ) {
			ConnectionManager.close(con);
			throw e;
		} finally {
			ConnectionManager.close(con);
		}
		
		return result; 
	}
	
	/**
	 * 이전대상부서 가져오기
	 * @param con
	 * @param sysdocno
	 * @param deptid
	 * @return
	 * @throws Exception
	 */
	public String getPreDeptcd(Connection con, int sysdocno, String deptid, boolean isDeliveried) throws Exception {
		return getCommTreatDAO().getPreDeptcd(con, sysdocno, deptid, isDeliveried);
	}
	
	/** 
	 * 문서상태 변경
	 * @param sysdocno : 시스템 문서번호
	 * @param usrid : 사용자ID
	 * @param docstate : 문서상태(01 : 작성중, 02 : 검토대기, 03 : 승인대기, 04 : 취합진행, 05 : 마감대기, 06 : 마감완료)
	 * @return int : 처리결과
	 * @throws Exception : SQL관련 예외
	 */
	public int updateDocState(int sysdocno, String usrid, String docstate) throws Exception {
		
		int result = 0;
		
		result = getCommTreatDAO().updateDocState(sysdocno, usrid, docstate);
		
		return result;
	}
	
	/** 
	 * 제출상태 변경
	 * @param sysdocno : 시스템 문서번호
	 * @param deptcd : 부서코드
	 * @param usrid : 사용자ID
	 * @param submitstate : 제출상태(01 : 배부대기, 02 : 입력진행, 03 : 검토대기, 04 : 승인대기, 05 : 제출완료, 06 : 반송
	 * @return int : 처리결과
	 * @throws Exception : SQL관련 예외
	 */
	public int updateSubmitState(int sysdocno, String deptcd, String usrid, String submitstate) throws Exception {
		
		int result = 0;
		
		result = getCommTreatDAO().updateSubmitState(sysdocno, deptcd, usrid, submitstate);
		
		return result;
	}
	
	//제출부서 결재여부
	public int IsDocSanctgt(int sysdocno, String deptcd, String userid) throws Exception {
		return getCommTreatDAO().IsDocSanctgt(sysdocno, deptcd, userid);
	}
	
	/**
	 * 배부하기 상세 - 처리현황 보기
	 * 
	 * @param sysdocno : 시스템문서번호
	 * @param deptcd : 부서코드
	 * 
	 * @return CommTreatBean : 처리현황 데이터를 담고 있는 Bean
	 * @throws Exception 
	 */
	public CommTreatBean viewCommTreatStatus(int sysdocno, String deptcd) throws Exception {
		CommTreatBean result = new CommTreatBean();
		
		result.setAppntusrnm(getCommTreatDAO().getInputUsrnm(sysdocno, deptcd));
		result.setSancusrnm1(getCommTreatDAO().getApprovalUsrnm("1", sysdocno, deptcd));
		result.setSancusrnm2(getCommTreatDAO().getApprovalUsrnm("2", sysdocno, deptcd));
		
		return result;
	}
	
	/**
	 * 입력완료 담당자 목록을 셋팅한다.
	 * 
	 * @param sysdocno : 시스템문서번호
	 * @param deptcd : 부서코드
	 * 
	 * @return List : 입력완료 담당자 목록 리스트
	 * @throws Exception 
	 */
	public List setInputUsr(int sysdocno, String deptcd) throws Exception{
		List result = new ArrayList();		
		List inusrList = getCommTreatDAO().inputUsr(sysdocno, deptcd);
		
		String chrgunitnm = "";
		String temp = "";
		for(int i=0;i<inusrList.size();i++){
			String[] usrinfo = (String[])inusrList.get(i);					
			usrinfo[0] = Utils.nullToEmptyString(usrinfo[0]);
			
			if ( "".equals(Utils.nullToEmptyString(usrinfo[0])) ) {
				usrinfo[0] = "담당단위없음";
			}
			
			if(chrgunitnm.equals(usrinfo[0])){
				temp = temp + ", " + usrinfo[1];
			} else {
				if(i>0){
					InputUsrBean usr = new InputUsrBean();	
					usr.setChrusrnm(temp);
					result.add(usr);
					temp = "";
				}
				temp = usrinfo[0] + " : " + usrinfo[1];				
			}
			
			chrgunitnm = usrinfo[0];
			
			//마지막이면 바로 넣는다.
			if(i==inusrList.size()-1){
				InputUsrBean usr = new InputUsrBean();	
				usr.setChrusrnm(temp);
				result.add(usr);
			}
		}
		
		return result;
	}
	
	/**
	 * 진행중인 취합문서 상세 - 처리현황 보기
	 * 
	 * @param sysdocno : 시스템문서번호
	 * @param deptcd : 부서코드
	 * @param user_id : 사용자ID
	 * 
	 * @return CommTreatBean : 처리현황 데이터를 담고 있는 Bean
	 * @throws Exception 
	 */
	public CommTreatBean viewCommTreatStatus1(int sysdocno, String deptcd, String user_id) throws Exception {
		CommTreatBean result = new CommTreatBean();
		
		result.setSancusrnm1(getCommTreatDAO().getApprovalUsrnm1("1", sysdocno, deptcd, user_id));
		result.setSancusrnm2(getCommTreatDAO().getApprovalUsrnm1("2", sysdocno, deptcd, user_id));
		
		CommTreatBean ctbean = getTgtdeptCnt(sysdocno, deptcd);
		
		result.setTcnt(ctbean.getTcnt());
		result.setScnt(ctbean.getScnt());
		result.setFcnt(ctbean.getFcnt());
		
		return result;
	}
	
	/**
	 * 제출현황건수
	 * @param sysdocno
	 * @param deptcd
	 * @return
	 * @throws Exception
	 */
	public CommTreatBean getTgtdeptCnt(int sysdocno, String deptcd) throws Exception {
		CommTreatBean result = null;
		Connection con = null;
		
		try {
			con = ConnectionManager.getConnection();
			
			result = getTgtdeptCnt(con, sysdocno, deptcd);
						
		} catch ( Exception e ) {
			logger.error("ERROR", e);
		} finally {
			ConnectionManager.close(con);
		}
		
		return result;
	}
	
	/**
	 * 제출현황건수
	 * @param sysdocno
	 * @param deptcd
	 * @return
	 * @throws Exception
	 */
	public CommTreatBean getTgtdeptCnt(Connection con, int sysdocno, String deptcd) throws Exception {
		CommTreatBean result = new CommTreatBean();
		
		int tcnt = 0;
		int scnt = 0;
		int fcnt = 0;
		
		List fg = getCommTreatDAO().getFormationGroup(con, "", sysdocno, null, deptcd, "", "", "", "", true);
		if ( fg != null ) {
			tcnt = fg.size();
		}
		
		fg = getCommTreatDAO().getFormationGroup(con, "", sysdocno, null, deptcd, "", "05,06", "", "", true);
		if ( fg != null ) {
			scnt = fg.size();
		}
		
		fcnt = tcnt - scnt;
		
		result.setTcnt(Integer.toString(tcnt));
		result.setScnt(Integer.toString(scnt));
		result.setFcnt(Integer.toString(fcnt));
		
		return result;
	}
	
	/**
	 * 배부일시 데이터 가져오기
	 */
	public String getTreatStatusDeliveryDT(int sysdocno) throws Exception {
		return getCommTreatDAO().getTreatStatusDeliveryDT(sysdocno);
	}
	
	/**
	 * 배부완료 상세 - 처리현황 데이터 가져오기
	 * 
	 * @param sysdocno : 시스템문서번호
	 * @param deptcd : 부서코드
	 * 
	 * @return CommTreatBean
	 * @throws Exception 
	 */
	public CommTreatBean viewDeliCompTreatStatus(int sysdocno, String deptcd) throws Exception {
		CcdManager ccdMgr = CcdManager.instance();
		commapprovalManager commappMgr = commapprovalManager.instance();
		
		CommTreatBean result = new CommTreatBean();
		
		String submitstate  = "";	 //제출부서 진행상태
		String deliverydt 	= "";    //배부일시
		List sanclist1  	= null;  //검토자 목록
		List sanclist2  	= null;  //승인자 목록
		List inputuser1 	= null;  //입력담당자 목록
		List inputuser2 	= null;  //미입력담당자 목록
		
		//제출부서 진행상태  가져오기
		submitstate = ccdMgr.getCcdName("004", getCommTreatDAO().getTgtdeptState(sysdocno, deptcd));
		
		//배부일시 가져오기
		deliverydt = getTreatStatusDeliveryDT(sysdocno);
		
		//입력완료 담당자 데이터 가져오기
		inputuser1 = setInputUsr(sysdocno, deptcd);
		
		//미입력 담당자 데이터 가져오기
		inputuser2 = getCommTreatDAO().inputUsrX(sysdocno, deptcd);
		
		//검토자 데이터 가져오기
		sanclist1 = commappMgr.getTgtApprInfo("1", sysdocno, deptcd);
		
		//승인자 데이터 가져오기
		sanclist2 = commappMgr.getTgtApprInfo("2", sysdocno, deptcd);
		
		CommTreatBean ctbean = getTgtdeptCnt(sysdocno, deptcd);
		
		result.setTcnt(ctbean.getTcnt());
		result.setScnt(ctbean.getScnt());
		result.setFcnt(ctbean.getFcnt());
		
		result.setSubmitstate(submitstate);
		result.setDeliverydt(deliverydt);
		result.setInputuser1(inputuser1);
		result.setInputuser2(inputuser2);
		result.setSancList1(sanclist1);
		result.setSancList2(sanclist2);
		
		return result;
	}
	
	/**
	 * 입력하기 상세 - 처리현황 데이터 가져오기
	 * 
	 * @param sysdocno : 시스템문서번호
	 * @param usrid : 사용자ID
	 * @param deptcd : 부서코드
	 * 
	 * @return CommTreatBean
	 * @throws Exception 
	 */
	public CommTreatBean viewInputTreatStatus(int sysdocno, String usrid, String deptcd) throws Exception {
		CcdManager ccdMgr = CcdManager.instance();
		commapprovalManager commappMgr = commapprovalManager.instance();
		
		CommTreatBean result = null;
		
		String submitstate  = "";	 //제출부서 진행상태
		List sanclist1  	= null;  //검토자 목록
		List sanclist2  	= null;  //승인자 목록
		List inputuser1 	= null;  //입력담당자 목록
		List inputuser2 	= null;  //미입력담당자 목록
		
		//제출부서 진행상태  가져오기
		submitstate = ccdMgr.getCcdName("004", getCommTreatDAO().getTgtdeptState(sysdocno, deptcd));
		
		//입력담당자명 및 담당단위 가져오기
		result = getCommTreatDAO().getInputChrgunitName(sysdocno, usrid, deptcd);
		
		if (result == null) {
			return null;
		}
		
		//입력완료 담당자 데이터 가져오기
		inputuser1 = setInputUsr(sysdocno, deptcd);
		
		//미입력 담당자 데이터 가져오기
		inputuser2 = getCommTreatDAO().inputUsrX(sysdocno, deptcd);
		
		//검토자 데이터 가져오기
		sanclist1 = commappMgr.getTgtApprInfo("1", sysdocno, deptcd);
		
		//승인자 데이터 가져오기
		sanclist2 = commappMgr.getTgtApprInfo("2", sysdocno, deptcd);
		
		result.setSubmitstate(submitstate);
		result.setInputuser1(inputuser1);
		result.setInputuser2(inputuser2);
		result.setSancList1(sanclist1);
		result.setSancList2(sanclist2);
		
		return result;
	}
	
	/**
	 * 입력완료 상세 - 처리현황 데이터 가져오기
	 * 
	 * @param sysdocno : 시스템문서번호
	 * @param usrid : 사용자ID
	 * @param deptcd : 부서코드
	 * 
	 * @return CommTreatBean
	 * @throws Exception 
	 */
	public CommTreatBean viewInputCompTreatStatus(int sysdocno, String usrid, String deptcd) throws Exception {
		commapprovalManager commappMgr = commapprovalManager.instance();
		
		CommTreatBean result = null;
		
		List sanclist1  	= null;  //검토자 목록
		List sanclist2  	= null;  //승인자 목록
		List inputuser1 	= null;  //입력담당자 목록
		List inputuser2 	= null;  //미입력담당자 목록
		
		//진행상태/입력완료일시/입력완료 사유 가져오기
		result = getCommTreatDAO().getInputCompTreatData(sysdocno, deptcd, usrid);
		
		//입력완료 담당자 데이터 가져오기
		inputuser1 = setInputUsr(sysdocno, deptcd);
		
		//미입력 담당자 데이터 가져오기
		inputuser2 = getCommTreatDAO().inputUsrX(sysdocno, deptcd);
		
		//검토자 데이터 가져오기
		sanclist1 = commappMgr.getTgtApprInfo("1", sysdocno, deptcd);
		
		//승인자 데이터 가져오기
		sanclist2 = commappMgr.getTgtApprInfo("2", sysdocno, deptcd);
		
		result.setInputuser1(inputuser1);
		result.setInputuser2(inputuser2);
		result.setSancList1(sanclist1);
		result.setSancList2(sanclist2);
		
		return result;
	}
	
	/** 
	 * 입력하기 상세(처리현황) - 입력담당자 담당단위 변경 (INPUTUSR, USR_EXT TABLE)
	 * 
	 * @param chrgunitcd : 변경할 담당단위코드
	 * @param usrid : 사용자ID
	 * @param sysdocno : 시스템문서번호
	 * @param deptcd : 부서코드
	 * 
	 * @return int : 실행결과	
	 * @throws Exception 
	 */
	public int changeChrgUnit(int chrgunitcd, String usrid, int sysdocno, String deptcd) throws Exception {
		Connection conn = null;
		String chrgunitnm = "";
		int result = 0;
		
		chrgunitnm = getCommTreatDAO().getChrgunitName(deptcd, chrgunitcd);
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			result += getCommTreatDAO().changeInputusrChrgunit(conn, chrgunitcd, chrgunitnm, usrid, sysdocno, deptcd);
			result += getCommTreatDAO().changeUsrextChrgunit(conn, chrgunitcd, usrid);
			result += getCommTreatDAO().changeDataLineFrmChrgunit(conn, chrgunitcd, usrid, sysdocno, deptcd);
			result += getCommTreatDAO().changeDataFixedFrmChrgunit(conn, chrgunitcd, usrid, sysdocno, deptcd);
			result += getCommTreatDAO().changeDataBookFrmChrgunit(conn, chrgunitcd, usrid, sysdocno, deptcd);
			
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				e = ex;
			}
			logger.error("ERROR",e);
			ConnectionManager.close(conn);
			throw e;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (Exception ex) {
				logger.error("ERROR", ex);
				ConnectionManager.close(conn);
				throw ex;
			}
			ConnectionManager.close(conn);
		}
		
		return result;
	}
	
	/**
	 * 문서상태 가져오기
	 * 
	 * @param sysdocno : 시스템문서번호
	 * 
	 * @return CommTreatBean 문서상태
	 * @throws Exception 
	 */
	public CommTreatBean getDocStates(int sysdocno, String deptcd) throws Exception{
		CommTreatBean result = new CommTreatBean();
		
		CommTreatBean ctbean = getTgtdeptCnt(sysdocno, deptcd);
		
		result.setTcnt(ctbean.getTcnt());
		result.setScnt(ctbean.getScnt());
		result.setFcnt(ctbean.getFcnt());
		
		result.setDocstate(getCommTreatDAO().getDocState(sysdocno).getDocstate());
		result.setDocstatenm(getCommTreatDAO().getDocState(sysdocno).getDocstatenm());
		result.setDeliverydt(getCommTreatDAO().getDocState(sysdocno).getDeliverydt());
		result.setEnddt(getCommTreatDAO().getDocState(sysdocno).getEnddt());
		
		return result;	 
	}

	/**
	 * 문서상태 가져오기
	 * 
	 * @param sysdocno : 시스템문서번호
	 * 
	 * @return CommTreatBean 문서상태
	 * @throws Exception 
	 */
	public String getDocState(int sysdocno) throws Exception{
		return getCommTreatDAO().getDocState(sysdocno).getDocstate();
	}
	
	/**
	 * 제출대상목록 가져오기
	 * 
	 * @param sysdocno : 시스템문서번호
	 * 
	 * @param gbn : 제출구분 - 대상전체(all),완료(comp),미제출(nocomp)
	 * 
	 * @return List : 제출대상목록(CollprocDeptStatusBean)
	 * @throws Exception 
	 */
	public List getTgtdeptList(String deptcd, int sysdocno, String user_id, String gbn) throws Exception {
		return getFormationGroup("", sysdocno, user_id, deptcd , "", gbn, "", "");
	}	

	/** 
	 * 제출부서 수정가능여부 변경	
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param tgtdeptcd : 부서코드
	 * @param modifyyn : 수정가능여부
	 * 
	 * @return 없음
	 * @throws Exception 
	 */
	public void updateTgtdept(int sysdocno, String tgtdeptcd, String modifyyn) throws Exception {
		getCommTreatDAO().updateTgtdept(sysdocno, tgtdeptcd, modifyyn);
	}	

	/**
	 * 문서양식 수정권한 가져오기
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param deptcd : 부서코드
	 * 
	 * @return String : 수정권한 여부 
	 * @throws Exception 
	 */
	public String getTgtModifyYn(int sysdocno, String deptcd) throws Exception {
		return getCommTreatDAO().getTgtModifyYn(sysdocno, deptcd);
	}

	/**
	 * 제출상태 가져오기
	 */
	public String getTgtdeptState(int sysdocno, String deptcd) throws Exception {
		return getCommTreatDAO().getTgtdeptState(sysdocno, deptcd);
	}
	
	/**
	 * 결제상세 - 처리현황 보기
	 * 
	 * @param gbn : 배부문서(1), 제출문서(2)
	 * @param sysdocno : 시스템문서번호
	 * @param deptcd : 부서코드
	 * @param usrid : 사용자ID
	 * 
	 * @return CommTreatBean
	 * @throws Exception 
	 */
	public CommTreatBean appTreatView(String gbn, int sysdocno, String deptcd, String usrid) throws Exception {
		commapprovalManager commappMgr = commapprovalManager.instance();
		CcdManager ccdMgr = CcdManager.instance();
		CommTreatBean result = new CommTreatBean();
		
		String state    = "";    //문서진행 상태
		String submitdt = "";    //기안일시
		List sanclist1  = null;  //검토자 목록
		List sanclist2  = null;  //승인자 목록
		List inputuser1 = null;  //입력담당자 목록
		List inputuser2 = null;  //미입력담당자 목록			
		String gbntemp  = "";		
		
		if("1".equals(gbn)){
			//배부문서
			if("02".equals(state)) {gbntemp = "1";} else if("03".equals(state)) {gbntemp = "2";}  
			//gbntemp: 검토(1), 승인(2)
			submitdt  = commappMgr.getColSubmitDt(sysdocno, gbntemp, usrid);			
			state = ccdMgr.getCcdName("003", getDocState(sysdocno));
			sanclist1 = commappMgr.getColApprInfo("1", sysdocno);
			sanclist2 = commappMgr.getColApprInfo("2", sysdocno); 
		} else {
			//제출문서
			if("03".equals(state)) {gbntemp = "1";} else if("04".equals(state)) {gbntemp = "2";}  
			//gbntemp: 검토(1), 승인(2)
			submitdt  = commappMgr.getTgtSubmitDt(sysdocno, deptcd, gbntemp, usrid);
			state = ccdMgr.getCcdName("004", getTgtdeptState(sysdocno, deptcd));
			sanclist1 = commappMgr.getTgtApprInfo("1", sysdocno, deptcd); 
			sanclist2 = commappMgr.getTgtApprInfo("2", sysdocno, deptcd);	
			inputuser1 = setInputUsr(sysdocno, deptcd);
			inputuser2 = getCommTreatDAO().inputUsrX(sysdocno, deptcd);			
		}
				
		result.setSubmitstate(state);
		result.setSubmitdt(submitdt);
		result.setSancList1(sanclist1);
		result.setSancList2(sanclist2);
		result.setInputuser1(inputuser1);
		result.setInputuser2(inputuser2);
			
		return result;
	}	
}
