/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 결재하기 manager
 * 설명:
 */
package nexti.ejms.approval.model;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.commtreat.model.CommTreatManager;

public class ApprovalManager {
	
	private static Logger logger = Logger.getLogger(ApprovalManager.class);
	
	private static ApprovalManager instance = null;
	private static ApprovalDAO dao = null;
	
	public static ApprovalManager instance() {
		if (instance==null) instance = new ApprovalManager(); 
		return instance;
	}
	
	private ApprovalDAO getApprovalDAO(){
		if (dao==null) dao = new ApprovalDAO(); 
		return dao;
	}
	
	private ApprovalManager() {
	}
	
	/**
	 * 취합부서 결재완료 여부 체크
	 * gubun(1) : 검토, gubun(2) : 승인
	 * @param sysdocno 시스템문서번호
	 * @param gubun 구분
	 * @return 결재완료결재완료sysdocno,sancusrid(1명)
	 */
	public String[] isColSancComplete(int sysdocno, String gubun) throws Exception {
		String[] result = null;
		
		result = getApprovalDAO().isColSancComplete(sysdocno, gubun);
		
		return result;
	}
	
	/**
	 * 제출부서 결재완료 여부 체크
	 * gubun(1) : 검토, gubun(2) : 승인
	 * @param sysdocno 시스템문서번호
	 * @param tgtdeptcd 제출부서코드
	 * @param gubun 구분
	 * @return 결재완료결재완료sysdocno,tgtdeptcd,sancusrid(1명)
	 */
	public String[] isTgtSancComplete(int sysdocno, String tgtdeptcd, String gubun) throws Exception {
		String[] result = null;
		
		result = getApprovalDAO().isTgtSancComplete(sysdocno, tgtdeptcd, gubun);
		
		return result;
	}
	
	/**
	 * 결재하기 목록
	 * @throws Exception 
	 */
	public List approvalList(SearchBean search, String deptcd) throws Exception{
		List result = null;
		
		result = getApprovalDAO().appList(search, deptcd);
		
		return result;		
	}
	
	/**
	 * 결재하기 목록 갯수 가져오기	
	 * @throws Exception 
	 */
	public int appTotCnt(SearchBean search, String deptcd) throws Exception{
		int result = 0;
		
		result = getApprovalDAO().appTotCnt(search, deptcd);
		
		return result;
	}
	
	/**
	 * 결재완료 목록
	 * @throws Exception 
	 */
	public List appCompList(SearchBean search, String deptcd) throws Exception{
		List result = null;
		
		result = getApprovalDAO().appCompList(search, deptcd);
		
		return result;		
	}
	
	/**
	 * 결재완료 목록 갯수 가져오기	
	 * @throws Exception 
	 */
	public int appCompTotCnt(SearchBean search, String deptcd) throws Exception{
		int result = 0;
		
		result = getApprovalDAO().appCompTotCnt(search, deptcd);
		
		return result;
	}
	
	/**
	 * 결재하기
	 * gbn: 취합부서(1), 제출부서(2)
	 * @throws Exception 
	 */
	public int doSanc(String gbn, int sysdocno, String deptcd, String sancusrid) throws Exception{
		int result = 0;
		String status = "";
		if("1".equals(gbn)){
			//취합부서 결재
			status = CommTreatManager.instance().getDocState(sysdocno);
			
			if("02".equals(status)){
				//검토
				result = getApprovalDAO().doColSanc(sysdocno, "1", deptcd, sancusrid);
			} else if("03".equals(status)) {
				//승인
				result = getApprovalDAO().doColSanc(sysdocno, "2", deptcd, sancusrid);
			}
		} else {
			//제출부서 결재
			status = CommTreatManager.instance().getTgtdeptState(sysdocno, deptcd);
			
			if("03".equals(status)){
				//검토
				result = getApprovalDAO().doTgtSanc(sysdocno, "1", deptcd, sancusrid);
			} else if("04".equals(status)){
				//승인
				result = getApprovalDAO().doTgtSanc(sysdocno, "2", deptcd, sancusrid);
			}
		}
		
		return result;
	}
	
	/** 
	 * 취합부서 결재상태 체크(마지막 결제자 인지 체크하여 처리)
	 * gubun : 검토(1), 승인(2)	
	 * @throws Exception 
	 */
	public int doLastColSancCheck(int sysdocno, String gubun, String deptcd, String sancusrid) throws Exception  {
		
		int result =0;
		Connection conn = null;
		
		try {
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
			
			result = getApprovalDAO().doLastColSancCheck(conn, sysdocno, gubun, deptcd, sancusrid);
			
			conn.commit();
		} catch(Exception e) {
			try {
				conn.rollback();
			} catch(Exception ex) {
				logger.error("ERROR", ex);
			}
			ConnectionManager.close(conn);
			throw e;
		} finally {
			ConnectionManager.close(conn);
		}
		
		return result;
	}
	
	/** 
	 * 제출부서 결재상태 체크(마지막 결제자 인지 체크하여 처리)
	 * gubun : 검토(1), 승인(2)	
	 * @throws Exception 
	 */
	public int doLastTgtSancCheck(int sysdocno, String gubun, String deptcd, String sancusrid) throws Exception  {
		
		int result =0;
		Connection conn = null;
		
		try {
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
			
			result = getApprovalDAO().doLastTgtSancCheck(conn, sysdocno, gubun, deptcd, sancusrid);
			
			conn.commit();
		} catch(Exception e) {
			try {
				conn.rollback();
			} catch(Exception ex) {
				logger.error("ERROR", ex);
			}
			ConnectionManager.close(conn);
			throw e;
		} finally {
			ConnectionManager.close(conn);
		}
		
		return result;
	}
}
