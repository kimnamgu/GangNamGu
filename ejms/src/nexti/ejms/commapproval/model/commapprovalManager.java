/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 결재선지정,입력담당자지정 manager
 * 설명:
 */
package nexti.ejms.commapproval.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nexti.ejms.commapproval.model.commapprovalDAO;

public class commapprovalManager {

	private static commapprovalManager instance = null;
	private static commapprovalDAO dao = null;
	
	public static commapprovalManager instance() {
		if (instance==null) instance = new commapprovalManager(); 
		return instance;
	}
	
	/**
	 * 프로그램 관리 DAO 객체를 받아온다.
	 * @return CommsubdeptDAO - 프로그램 관리 DAO 객체
	 */
	private commapprovalDAO getCommApprovalDAO(){
		if (dao==null) dao = new commapprovalDAO(); 
		return dao;
	}
	
	/**
	 * 설문기타대상 목록 XML
	 * @return
	 * @throws Exception
	 */
	public String getGradeListXml() throws Exception{
		return getCommApprovalDAO().getGradeListXml();
	}
	
	/**
	 * 설문지정 목록 XML
	 * @return
	 * @throws Exception
	 */
	public String getResearchGrpListXml(String range) throws Exception{
		return getCommApprovalDAO().getResearchGrpListXml(range);
	}
	
	/**
	 * 메뉴목록을 XML String으로 가져오기
	 * @param form
	 * @return ArrayList - 프로그램 정보목록
	 * @throws SQLException
	 */
	public String getUserListXml(String deptId) throws Exception{
		return getCommApprovalDAO().getUserListXml(deptId);
	}

	public String selectUserXml(String deptId) throws Exception {
		
		return getCommApprovalDAO().selectUserXml(deptId);
	}
	
	public boolean designateInsert( int sysdocno, ArrayList userList, String sessionId, String deptId, String usrid, String type) throws Exception {
		
		
		return getCommApprovalDAO().designateInsert(sysdocno, userList, sessionId, deptId, usrid, type);		
	}
	
	public int inputusrInsert( int sysdocno, ArrayList userList, String userId, String deptId) throws Exception {
		
		return getCommApprovalDAO().inputusrInsert(sysdocno, userList, userId, deptId);
	}
	
	public int insertOtherTarget( int rchno, String sessionId, ArrayList userList) throws Exception {
		
		return getCommApprovalDAO().insertOtherTarget(rchno, sessionId, userList);
	}
	
	public int insertGrpOtherTarget( int rchgrpno, String sessionId, ArrayList userList) throws Exception {
		
		return getCommApprovalDAO().insertGrpOtherTarget(rchgrpno, sessionId, userList);
	}
	
	public int insertGrpChoice( int rchgrpno, String sessionId, String idList) throws Exception {
		
		return getCommApprovalDAO().insertGrpChoice(rchgrpno, sessionId, idList);
	}
	
	public int inputusrInsert(Connection conn, int sysdocno, String sessionId, ArrayList userList, String userId, String deptId) throws Exception {
		
		return getCommApprovalDAO().inputusrInsert(conn, sysdocno, sessionId, userList, userId, deptId);
	}	
	
	public String getDesigNateView(int sysdocno, String sessionId, String deptcd, String type) throws Exception {
		
		return getCommApprovalDAO().getDesigNateView(sysdocno, sessionId, deptcd, type);
	}
	
	public String getInputUsrView(int sysdocno, String deptcd) throws Exception {
		
		return getCommApprovalDAO().getInputUsrView(sysdocno, deptcd);
	}
	
	public String getGradeListView(int rchno, String sessionId) throws Exception {
		return getCommApprovalDAO().getGradeListView(rchno, sessionId);
	}
	
	public String getGradeListViewGrp(int rchgrpno, String sessionId) throws Exception {
		return getCommApprovalDAO().getGradeListViewGrp(rchgrpno, sessionId);
	}
	
	public String getResearchGrpView(int rchgrpno, String sessionId) throws Exception {
		List rchGrpList = getCommApprovalDAO().getResearchGrpView(rchgrpno, sessionId);
		
		return getCommApprovalDAO().getGradeListXML(rchGrpList);
	}
	
	public List getResearchGrpList(int rchgrpno, String sessionId) throws Exception {		
		return getCommApprovalDAO().getResearchGrpView(rchgrpno, sessionId);
	}
	
	/**
	 * 취합부서 결재선 승인/검토 데이터 가져오기
	 * gubun: 검토(1),  승인(2)
	 * sysdocno : 시스템문서번호
	 * @throws Exception 
	 */
	public List getColApprInfo(String gubun, int sysdocno) throws Exception {
		List result = null;
		
		result = getCommApprovalDAO().getColApprInfo(gubun, sysdocno);
		
		return result;
	}
	
	/**
	 * 제출부서 결재선 승인/검토 데이터 가져오기	 
	 * gubun: 검토(1),  승인(2)
	 * sysdocno : 시스템문서번호
	 * deptcd : 부서코드
	 * @throws Exception 
	 */
	public List getTgtApprInfo(String gubun, int sysdocno, String deptcd) throws Exception {
		List result = null;
		
		result = getCommApprovalDAO().getTgtApprInfo(gubun, sysdocno, deptcd);
		
		return result;
	}
	
	/**
	 * [취합부서 용] 기안일시 가져오기 - 결재진행
	 * gubun : 승인(1), 검토(2)
	 * @throws Exception 
	 */
	public String getColSubmitDt(int sysdocno, String gubun, String sancuserid) throws Exception {
		String result = "";
		
		result = getCommApprovalDAO().getColSubmitDt(sysdocno, gubun, sancuserid);
		
		return result;
	}
	
	/**
	 * [제출부서 용] 기안일시 가져오기 - 결재진행
	 * gubun : 승인(1), 검토(2)
	 * @throws Exception 
	 */
	public String getTgtSubmitDt(int sysdocno, String deptcd, String gubun, String sancuserid) throws Exception {
		String result = "";
		
		result = getCommApprovalDAO().getTgtSubmitDt(sysdocno, deptcd, gubun, sancuserid);
		
		return result;
	}
	
	/**
	 * [제출부서 용] 문서상태 및 결재 상태 가져오기
	 * state : 승인(1), 검토(2)
	 * @throws Exception 
	 */
	public String getState(int sysdocno, String deptcd, String type) throws Exception {
		String result = "";
		
		result = getCommApprovalDAO().getState(sysdocno, deptcd, type);
		
		return result;
	}
	
	public String getReqformView(int reqformno, int reqseq, String deptcd) throws Exception {
		String result = "";
		
		result = getCommApprovalDAO().getReqformView(reqformno, reqseq, deptcd);
		
		return result;
	}
	
	public int commreqformInsert(int reqformno, int reqseq, ArrayList userList, String deptId, String usrid) throws Exception {
		int result = 0;
		
		result = getCommApprovalDAO().commreqformInsert(reqformno, reqseq, userList, deptId, usrid);
		
		return result;
	}
	
}
