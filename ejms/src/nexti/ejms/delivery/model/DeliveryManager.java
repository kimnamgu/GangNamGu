/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배부하기 manager
 * 설명:
 */
package nexti.ejms.delivery.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;

public class DeliveryManager {
	
	private static Logger logger = Logger.getLogger(DeliveryManager.class);
	private static DeliveryManager instance = null;
	private static DeliveryDAO dao = null;
	
	public static DeliveryManager instance() {
		if (instance==null) instance = new DeliveryManager(); 
		return instance;
	}
	
	private DeliveryDAO getDeliveryDAO(){
		if (dao==null) dao = new DeliveryDAO(); 
		return dao;
	}
	
	private DeliveryManager() {
	}
	
	/**
	 * 배부하기 목록
	 * 
	 * @param deptcd : 부서코드
	 * @param start : 리스트 시작지점
	 * @param end : 리스트 종료지점
	 * 
	 * @return List : 배부 목록 리스트
	 * @throws Exception 
	 */
	public List deliveryList(String deptcd, String isSysMgr, String sch_deptcd, int start, int end) throws Exception{		
		return getDeliveryDAO().deliveryList(deptcd, isSysMgr, sch_deptcd, start, end);
	}
	
	/**
	 * 배부하기 상세 - 반송 팝업창 데이터 보기
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * 
	 * @return DeliveryRetDocViewBean : 반송 팝업창 데이터를 담고 있는 Bean
	 * @throws Exception 
	 */
	public DeliveryRetDocViewBean viewReturnDoc(int sysdocno) throws Exception {
		return getDeliveryDAO().viewReturnDoc(sysdocno);
	}
	
	/** 
	 * 배부하기 상세 - 반송 처리
	 * 
	 * @param RetDocBean : 반송 사유/제출 상태를 담고 있는 Bean
	 * @param usrid : 사용자ID
	 * 
	 * @return int : 처리결과
	 * @throws Exception 
	 */
	public int deliveryReturnDoc(DeliveryRetDocBean RetDocBean, String usrid) throws Exception {
		Connection conn = null;
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
			
			result = getDeliveryDAO().deliveryReturnDoc(conn, RetDocBean, usrid);
			
			if(this.IsLastDeliveryDept(conn, RetDocBean.getSysdocno(), RetDocBean.getTgtdeptcd())) {
				result = updateDocState(conn, RetDocBean.getSysdocno(), "05", usrid);
			}
			
			try { conn.commit(); } catch (Exception e) {}
		} catch (SQLException e) {
			try { conn.rollback(); } catch (Exception ex) {}
		} finally {
			try { conn.setAutoCommit(true); } catch (Exception ex) {}
			ConnectionManager.close(conn);
		}
		
		return result;
	}
	
	/** 
	 * 배부처리시 문서상태 변경
	 * 
	 * @param conn : Connection 객체
	 * @param sysdocno : 시스템 문서번호
	 * @param docstate : 변경할 문서상태
	 * @param usrid : 사용자ID
	 * 
	 * @return int : 처리결과 
	 * @throws Exception : SQL 관련 예외
	 */
	public int updateDocState(Connection conn, int sysdocno, String docstate, String usrid) throws Exception {
		return getDeliveryDAO().updateDocState(conn, sysdocno, docstate, usrid);
	}
	
	/** 
	 * 공통함수 - 마지막 제출부서인지 아닌지 여부 체크
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param deptcd : 부서코드
	 * 
	 * @return true : 마지막 부서 O, false : 마지막 부서 X	
	 * @throws Exception 
	 */
	public boolean IsLastDeliveryDept(Connection conn, int sysdocno, String deptcd) throws Exception {
		return getDeliveryDAO().IsLastDeliveryDept(conn, sysdocno, deptcd);
	}
	
	public boolean IsLastDeliveryDept(int sysdocno, String deptcd) throws Exception {
		Connection conn = null;
		boolean result = false;
		
		try {
			conn = ConnectionManager.getConnection(false);
			
			IsLastDeliveryDept(conn, sysdocno, deptcd);
			
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
	 * 배부하기 상세 - 배부
	 * 
	 * @param submitstate : 변경할 제출부서 상태
	 * @param appusrnm : 입력담당지정자 설명
	 * @param usrid : 사용자ID
	 * @param sysdocno : 시스템 문서번호
	 * @param deptcd : 부서코드
	 * 
	 * @return int : 처리결과
	 * @throws Exception 
	 */
	public int deliveryProcess(String submitstate, String appusrnm, String usrid, int sysdocno, String deptcd) throws Exception {
		 return getDeliveryDAO().deliveryProcess(submitstate, appusrnm, usrid, sysdocno, deptcd);
	}
	
	public int deliveryProcess(Connection conn, String submitstate, String appusrnm, String usrid, int sysdocno, String sessionId, String deptcd) throws Exception {
		return getDeliveryDAO().deliveryProcess(conn, submitstate, appusrnm, usrid, sysdocno, sessionId, deptcd);
	}
	
	/** 
	 * 배부하기 상세 - 마감시한 초과 체크, 입력담당자 지정 및 승인/검토자 지정 여부 확인
	 * 
	 * @param sysdocno : 시스템문서번호
	 * @param deptcd : 부서코드
	 * 
	 * @return String : OVERTIME, NONINPUTUSR, NONAPPROVAL, OK
	 * @throws Exception 
	 */
	public String IsAssignCharge(int sysdocno, String deptcd) throws Exception {
		String retCode = "OK";

		if(!getDeliveryDAO().IsAssignEndDt(sysdocno)) {
			return "OVERTIME";
		}
		
		if(!getDeliveryDAO().IsAssignInputUsrcharge(sysdocno, deptcd)) {
			return "NONINPUTUSR";
		}

		if(!getDeliveryDAO().IsAssignApprovalUsr(sysdocno, deptcd)) {
			return "NONAPPROVAL";
		}
		
		return retCode;
	}
	
	/**
	 * 배부완료 목록
	 * 
	 * @param deptcd : 부서코드
	 * @param start : 리스트 시작지점
	 * @param end : 리스트 종료지점
	 * 
	 * @return List : 배부완료 목록 리스트
	 * @throws Exception 
	 */
	public List deliveryCompList(String deptcd, String isSysMgr, String sch_deptcd, int start, int end) throws Exception {
		return getDeliveryDAO().deliveryCompList(deptcd, isSysMgr, sch_deptcd, start, end);
	}
	
	/**
	 * 배부하기 상세 - 마감시한/마감알림말 가져오기
	 * 
	 * @param sysdocno : 시스템문서번호
	 * 
	 * @return DeliveryDetailBean : 마감시한/마감알림말을 담고 있는 Bean
	 * @throws Exception 
	 */
	public DeliveryDetailBean viewDeliveryDetail(int sysdocno) throws Exception {
		return getDeliveryDAO().viewDeliveryDetail(sysdocno);
	}
	
	/** 
	 * 배부하기 목록 갯수 가져오기
	 * 
	 * @param deptcd : 부서코드
	 * 
	 * @return int : 배부하기 목록 총갯수
	 * @throws Exception 
	 */
	public int deliCnt(String deptcd, String isSysMgr, String sch_deptcd) throws Exception{
		return getDeliveryDAO().deliCnt(deptcd, isSysMgr, sch_deptcd);
	}

	/** 
	 * 배부하기 목록 총갯수 가져오기
	 * 
	 * @param deptcd : 부서코드
	 * 
	 * @return int : 배부하기 목록 총갯수
	 * @throws Exception 
	 */
	public int deliTotCnt(String deptcd) throws Exception{
		return getDeliveryDAO().deliTotCnt(deptcd);
	}
	

	/** 
	 * 배부완료 목록 총갯수 가져오기
	 * 
	 * @param deptcd : 부서코드
	 * 
	 * @return int : 배부완료 목록 총갯수 
	 * @throws Exception 
	 */
	public int deliCompCnt(String deptcd, String isSysMgr, String sch_deptcd) throws Exception {
		return getDeliveryDAO().deliCompCnt(deptcd, isSysMgr, sch_deptcd);
	}
	
	/** 
	 * 배부완료 목록 총갯수 가져오기
	 * 
	 * @param deptcd : 부서코드
	 * 
	 * @return int : 배부완료 목록 총갯수 
	 * @throws Exception 
	 */
	public int deliCompTotCnt(String deptcd) throws Exception {
		return getDeliveryDAO().deliCompTotCnt(deptcd);
	}
	
	/**
	 * 배부하기 - 관리자인경우 검색 조건에 해당하는 값 가져오기 
	 */
	public String getSearchDelivery(String sch_deptcd) throws Exception{
		return getDeliveryDAO().getSearchDelivery(sch_deptcd);
	}

	/**
	 * 배부완료 - 관리자인경우 검색 조건에 해당하는 값 가져오기 
	 */
	public String getSearchDeliveryComp(String sch_deptcd) throws Exception{
		return getDeliveryDAO().getSearchDeliveryComp(sch_deptcd);
	}
}
