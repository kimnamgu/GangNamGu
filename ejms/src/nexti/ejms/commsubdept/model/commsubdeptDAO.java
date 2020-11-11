/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합대상지정 dao
 * 설명:
 */
package nexti.ejms.commsubdept.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.commsubdept.model.commsubdeptDAO;
import nexti.ejms.commsubdept.model.commsubdeptBean;
import nexti.ejms.commsubdept.model.commdeptuserBean;
import nexti.ejms.commtreat.model.CommTreatManager;
import nexti.ejms.commapproval.model.commapprovalBean;
import nexti.ejms.commapproval.model.commapprovalManager;
import nexti.ejms.common.ConnectionManager;
import nexti.ejms.common.appInfo;
import nexti.ejms.delivery.model.DeliveryManager;
import nexti.ejms.group.model.GroupBean;
import nexti.ejms.group.model.GroupManager;
import nexti.ejms.inputing.model.InputingManager;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.commfunction;

public class commsubdeptDAO {
	private static Logger logger = Logger.getLogger(commsubdeptDAO.class);
	
	/**
	 * 부서정보를 XML형태로 생성하여 리턴
	 * @return
	 * @throws Exception
	 */
	public String getTargetDeptFormationList(String searchType, String searchValue) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList deptList = null;
		
		StringBuffer selectQuery = new StringBuffer();
		
		String rootid = appInfo.getRootid();
		selectQuery.append("SELECT DEPT_ID, DEPT_NAME, UPPER_DEPT_ID, GRPGBN, MAIN_YN, LEVEL DEPT_LEVEL                      \n");
		selectQuery.append("FROM                                                                                             \n");
		selectQuery.append("(                                                                                                \n");
		selectQuery.append("SELECT D.DEPT_ID, D.DEPT_NAME, D.UPPER_DEPT_ID, '2' GRPGBN, D.MAIN_YN, D.DEPT_RANK, '' GRADE_ID  \n");
		selectQuery.append("FROM DEPT D				                                                                         \n");
		selectQuery.append("WHERE D.USE_YN = 'Y'                                                                             \n");
		selectQuery.append("AND D.DEPT_ID IN                                                                                 \n");
		selectQuery.append("    (SELECT DEPT_ID FROM USR WHERE USE_YN = 'Y'                  								 \n");
		selectQuery.append("     UNION                                                                                       \n");
		selectQuery.append("     SELECT UPPER_DEPT_ID FROM DEPT WHERE USE_YN = 'Y')                                          \n");
		selectQuery.append("UNION ALL                                                                                        \n");
		selectQuery.append("SELECT U.USER_ID, USER_NAME, U.DEPT_ID, '3', D.MAIN_YN, D.DEPT_RANK, U.GRADE_ID               	 \n");
		selectQuery.append("FROM USR U, DEPT D				                                                                 \n");
		selectQuery.append("WHERE U.DEPT_ID = D.DEPT_ID                                                                      \n");
		selectQuery.append("AND U.USE_YN = 'Y'                                                                               \n");
		selectQuery.append("AND D.USE_YN = 'Y'                                                                               \n");
		selectQuery.append("AND D.MAIN_YN = 'Y'                                                                              \n");
		selectQuery.append(") T                                                                                              \n");
		if ( searchType.equals("1") == true ) {
			selectQuery.append("WHERE DEPT_ID IN                                                                             \n");
			selectQuery.append("(                                                                                            \n");
			selectQuery.append("SELECT DEPT_ID FROM DEPT                                                                     \n");
			selectQuery.append("START WITH DEPT_ID IN (SELECT DEPT_ID FROM DEPT WHERE DEPT_NAME LIKE ?)                      \n");
			selectQuery.append("CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID                                                     \n");
			selectQuery.append("UNION ALL                                                                                    \n");
			selectQuery.append("SELECT DEPT_ID FROM DEPT                                                                     \n");
			selectQuery.append("START WITH DEPT_ID IN (SELECT DEPT_ID FROM DEPT WHERE DEPT_NAME LIKE ?)                      \n");
			selectQuery.append("CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID                                                     \n");
			selectQuery.append(")                                                                                            \n");
		} else if ( searchType.equals("2") == true ) {
			selectQuery.append("WHERE DEPT_ID IN                                                                             \n");
			selectQuery.append("(                                                                                            \n");
			selectQuery.append("SELECT DEPT_ID FROM DEPT                                                                     \n");
			selectQuery.append("START WITH DEPT_ID IN (SELECT DEPT_ID FROM USR WHERE USER_NAME LIKE ?)                       \n");
			selectQuery.append("CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID                                                     \n");
			selectQuery.append("UNION ALL                                                                                    \n");
			selectQuery.append("SELECT DEPT_ID FROM DEPT                                                                     \n");
			selectQuery.append("START WITH DEPT_ID IN (SELECT DEPT_ID FROM USR WHERE USER_NAME LIKE ?)                       \n");
			selectQuery.append("CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID                                                     \n");
			selectQuery.append("UNION ALL                                                                                    \n");
			selectQuery.append("SELECT USER_ID FROM USR WHERE USER_NAME LIKE ?                                               \n");
			selectQuery.append(")                                                                                            \n");
		}
		selectQuery.append("START WITH DEPT_ID = ?                                                                           \n");
		selectQuery.append("CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID                                                         \n");
		selectQuery.append("ORDER SIBLINGS BY TO_NUMBER(DEPT_RANK), GRADE_ID                                                 \n");
		try{
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if ( searchType.equals("1") == true ) {
				pstmt.setString(1, "%" + searchValue + "%");
				pstmt.setString(2, "%" + searchValue + "%");
				pstmt.setString(3, rootid);
			} else if ( searchType.equals("2") == true ) {
				pstmt.setString(1, "%" + searchValue + "%");
				pstmt.setString(2, "%" + searchValue + "%"); 
				pstmt.setString(3, "%" + searchValue + "%");
				pstmt.setString(4, rootid);
			} else {
				pstmt.setString(1, rootid);
			}
			rs = pstmt.executeQuery();
			
			deptList = new ArrayList();

			while(rs.next()){
				commsubdeptBean bean = new commsubdeptBean();
				
				bean.setCode(rs.getString("DEPT_ID"));
				bean.setName(rs.getString("DEPT_NAME"));
				bean.setUpperDeptId(rs.getString("UPPER_DEPT_ID"));
				bean.setGrpGbn(rs.getString("GRPGBN"));
				bean.setMainyn(rs.getString("MAIN_YN"));
				bean.setDeptLevel(rs.getInt("DEPT_LEVEL"));
				deptList.add(bean);
			}
		}catch(Exception e){
			logger.error("ERROR",e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		}finally{
			ConnectionManager.close(con, pstmt, rs);
		}
		
		String openState = "";
		if ( searchValue.trim().length() != 0 ) openState = "1";
		
		return getTargetDeptListXML(deptList, openState);
	}
	
	/**
	 * 부서정보를 XML형태로 생성하여 리턴
	 * @return
	 * @throws Exception
	 */
	public String getResearchDeptFormationList(String searchType, String searchValue) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList deptList = null;
		
		StringBuffer selectQuery = new StringBuffer();
		
		String rootid = appInfo.getRootid();

		selectQuery.append("SELECT DEPT_ID, DEPT_NAME, UPPER_DEPT_ID, GRPGBN, MAIN_YN, LEVEL DEPT_LEVEL                      \n");
		selectQuery.append("FROM                                                                                             \n");
		selectQuery.append("(                                                                                                \n");
		selectQuery.append("SELECT D.DEPT_ID, D.DEPT_NAME, D.UPPER_DEPT_ID, '2' GRPGBN, D.MAIN_YN, D.DEPT_RANK, '' GRADE_ID  \n");
		selectQuery.append("FROM DEPT D				                                                                         \n");
		selectQuery.append("WHERE D.USE_YN = 'Y'                                                                             \n");
		selectQuery.append("AND D.DEPT_ID IN                                                                                 \n");
		selectQuery.append("    (SELECT DEPT_ID FROM USR WHERE USE_YN = 'Y'                  								 \n");
		selectQuery.append("     UNION                                                                                       \n");
		selectQuery.append("     SELECT UPPER_DEPT_ID FROM DEPT WHERE USE_YN = 'Y')                                          \n");
		selectQuery.append("UNION ALL                                                                                        \n");
		selectQuery.append("SELECT U.USER_ID, USER_NAME, U.DEPT_ID, '3', D.MAIN_YN, D.DEPT_RANK, U.GRADE_ID                  \n");
		selectQuery.append("FROM USR U, DEPT D				                                                                 \n");
		selectQuery.append("WHERE U.DEPT_ID = D.DEPT_ID                                                                      \n");
		selectQuery.append("AND U.USE_YN = 'Y'                                                                               \n");
		selectQuery.append("AND D.USE_YN = 'Y'                                                                               \n");
		selectQuery.append("AND DE.MAIN_YN = 'Y'                                                                             \n");
		selectQuery.append(") T                                                                                              \n");
		if ( searchType.equals("1") == true ) {
			selectQuery.append("WHERE DEPT_ID IN                                                                             \n");
			selectQuery.append("(                                                                                            \n");
			selectQuery.append("SELECT DEPT_ID FROM DEPT                                                                     \n");
			selectQuery.append("START WITH DEPT_ID IN (SELECT DEPT_ID FROM DEPT WHERE DEPT_NAME LIKE ?)                      \n");
			selectQuery.append("CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID                                                     \n");
			selectQuery.append("UNION ALL                                                                                    \n");
			selectQuery.append("SELECT DEPT_ID FROM DEPT                                                                     \n");
			selectQuery.append("START WITH DEPT_ID IN (SELECT DEPT_ID FROM DEPT WHERE DEPT_NAME LIKE ?)                      \n");
			selectQuery.append("CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID                                                     \n");
			selectQuery.append(")                                                                                            \n");
		} else if ( searchType.equals("2") == true ) {
			selectQuery.append("WHERE DEPT_ID IN                                                                             \n");
			selectQuery.append("(                                                                                            \n");
			selectQuery.append("SELECT DEPT_ID FROM DEPT                                                                     \n");
			selectQuery.append("START WITH DEPT_ID IN (SELECT DEPT_ID FROM USR WHERE USER_NAME LIKE ?)                       \n");
			selectQuery.append("CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID                                                     \n");
			selectQuery.append("UNION ALL                                                                                    \n");
			selectQuery.append("SELECT DEPT_ID FROM DEPT                                                                     \n");
			selectQuery.append("START WITH DEPT_ID IN (SELECT DEPT_ID FROM USR WHERE USER_NAME LIKE ?)                       \n");
			selectQuery.append("CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID                                                     \n");
			selectQuery.append("UNION ALL                                                                                    \n");
			selectQuery.append("SELECT USER_ID FROM USR WHERE USER_NAME LIKE ?                                               \n");
			selectQuery.append(")                                                                                            \n");
		}
		selectQuery.append("START WITH DEPT_ID = ?                                                                           \n");
		selectQuery.append("CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID                                                         \n");
		selectQuery.append("ORDER SIBLINGS BY TO_NUMBER(DEPT_RANK), GRADE_ID                                                 \n");
		
		try{
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if ( searchType.equals("1") == true ) {
				pstmt.setString(1, "%" + searchValue + "%");
				pstmt.setString(2, "%" + searchValue + "%");
				pstmt.setString(3, rootid);
			} else if ( searchType.equals("2") == true ) {
				pstmt.setString(1, "%" + searchValue + "%");
				pstmt.setString(2, "%" + searchValue + "%"); 
				pstmt.setString(3, "%" + searchValue + "%");
				pstmt.setString(4, rootid);
			} else {
				pstmt.setString(1, rootid);
			}
			rs = pstmt.executeQuery();
			
			deptList = new ArrayList();

			while(rs.next()){
				commsubdeptBean bean = new commsubdeptBean();
				
				bean.setCode(rs.getString("DEPT_ID"));
				bean.setName(rs.getString("DEPT_NAME"));
				bean.setUpperDeptId(rs.getString("UPPER_DEPT_ID"));
				bean.setGrpGbn(rs.getString("GRPGBN"));
				bean.setMainyn(rs.getString("MAIN_YN"));
				bean.setDeptLevel(rs.getInt("DEPT_LEVEL"));
				deptList.add(bean);
			}
		}catch(Exception e){
			logger.error("ERROR",e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		}finally{
			ConnectionManager.close(con, pstmt, rs);
		}
		
		String openState = "";
		if ( searchValue.trim().length() != 0 ) openState = "1";
		
		return getTargetDeptListXML(deptList, openState);
	}
	
	/**
	 * 메뉴 목록에 대한 XML 파일을 생성한다.
	 * @param deptList
	 * @return
	 */
	private String getTargetDeptListXML(ArrayList deptList, String openState) {

		int preLevel = 0;
		int diffDepth = 0;

		StringBuffer deptXML = new StringBuffer();
		
		deptXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		deptXML.append("<tree id=\"0\">");
		
		for (int i = 0 ; i < deptList.size(); i++){
			commsubdeptBean bean = (commsubdeptBean)deptList.get(i);
			
			if ( openState.equals("1") ) openState = "open=\"1\"";

			// 이전 엘레멘트와 현재 엘레메트 간의 레벨차이
			diffDepth = bean.getDeptLevel() - preLevel;
			
			if (i == 0){	// 최초 엘레멘트
				deptXML.append("\n<item text=\"" + bean.getName() + "\" id=\"" + bean.getCode() + "\" open=\"1\" >");
				//<item> 엘레멘트 본문(사용자정의 데이터)
				deptXML.append("\n<userdata name=\"grpgbn\">" + bean.getGrpGbn() + "</userdata>");
				deptXML.append("\n<userdata name=\"mainyn\">" + bean.getMainyn() + "</userdata>");
				
				if(deptList.size() == 1) {
					deptXML.append("\n</item>");
				}
				
			} else {
				if (diffDepth == 0) {				// 이전 엘레멘트와 동일레벨일 경우
					
					deptXML.append("</item>");
					deptXML.append("\n<item text=\"" + bean.getName() + "\" id=\"" + bean.getCode() + "\" " + openState + " >");
					//<item> 엘레멘트 본문(사용자정의 데이터)
					deptXML.append("\n<userdata name=\"grpgbn\">" + bean.getGrpGbn() + "</userdata>");
					deptXML.append("\n<userdata name=\"mainyn\">" + bean.getMainyn() + "</userdata>");
					
				} else if (diffDepth > 0){			// 이전 엘레멘트보다 하위 레벨일 경우
					deptXML.append("\n<item text=\"" + bean.getName() + "\" id=\"" + bean.getCode() +"\" " + openState + " >");
					//<item> 엘레멘트 본문(사용자정의 데이터)
					deptXML.append("\n<userdata name=\"grpgbn\">" + bean.getGrpGbn() + "</userdata>");
					deptXML.append("\n<userdata name=\"mainyn\">" + bean.getMainyn() + "</userdata>");
				} else {							// 이전 엘레멘트보다 상위 레벨인 경우
					for (int j = 0 ; j < Math.abs(diffDepth) + 1 ; j++)
					deptXML.append("\n</item>");
					deptXML.append("\n<item text=\"" + bean.getName() + "\" id=\"" + bean.getCode() +"\" " + openState + " >");
					//<item> 엘레멘트 본문(사용자정의 데이터)
					deptXML.append("\n<userdata name=\"grpgbn\">" + bean.getGrpGbn() + "</userdata>");
					deptXML.append("\n<userdata name=\"mainyn\">" + bean.getMainyn() + "</userdata>");
				}
				
				if (i == (deptList.size() - 1)){
					if(bean.getDeptLevel() ==0){
						for (int j = 0 ; j <= bean.getDeptLevel() ; j++){
							deptXML.append("\n</item>");
						}
					}else{
						for (int j = 0 ; j < bean.getDeptLevel() ; j++){
							deptXML.append("\n</item>");
						}
					}
				}
			}
			
			preLevel = bean.getDeptLevel();
		}
		
		if(deptList.size() == 0) {
			deptXML.append("\n<item text=\"정보가 없습니다\"></item>");
		}
		
		// 최초 엘레멘트 open에 대해 엘레멘트 닫기
		deptXML.append("\n</tree>");
		return (deptXML.toString());
	}
	
	/**
	 * 배포목록을 XML형태로 생성하여 리턴
	 * @return
	 * @throws Exception
	 */
	public String getDistributeList(String user_id, String delImg, String onclickHandler) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList deptList = null;
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("SELECT GRPLISTCD, GRPLISTNM, '1' AS GRPGBN, 0 AS DEPT_LEVEL, CRTUSRGBN, ORD  \n");
		selectQuery.append("FROM GROUPMST                                                                \n");
		selectQuery.append("WHERE CRTUSRGBN LIKE '0'                                                     \n");
		selectQuery.append("UNION ALL                                                                    \n");
		selectQuery.append("SELECT GRPLISTCD, GRPLISTNM, '1' AS GRPGBN, 0 AS DEPT_LEVEL, CRTUSRGBN, ORD  \n");
		selectQuery.append("FROM GROUPMST                                                                \n");
		selectQuery.append("WHERE CRTUSRID LIKE ?                                                        \n");
		selectQuery.append("AND CRTUSRGBN LIKE '1'                                                       \n");
		selectQuery.append("ORDER BY CRTUSRGBN, ORD                                                      \n");
		
		try{
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			pstmt.setString(1, user_id);
			rs = pstmt.executeQuery();
			
			deptList = new ArrayList();

			while(rs.next()){
				commsubdeptBean bean = new commsubdeptBean();
				
				bean.setCode(rs.getString("GRPLISTCD"));
				bean.setName(rs.getString("GRPLISTNM"));
				bean.setGrpGbn(rs.getString("GRPGBN"));				
				bean.setDeptLevel(rs.getInt("DEPT_LEVEL"));
				bean.setCrtusrgbn(rs.getString("CRTUSRGBN"));
				deptList.add(bean);
			}
		}catch(Exception e){
			logger.error("ERROR",e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		}finally{
			ConnectionManager.close(con, pstmt, rs);
		}
		
		return getDistributeListXML(deptList, delImg, onclickHandler);
	}
	
	/**
	 * 메뉴 목록에 대한 XML 파일을 생성한다.
	 * @param deptList
	 * @return
	 */
	private String getDistributeListXML(ArrayList deptList, String delImg, String onclickHandler) {

		int preLevel = 0;
		int diffDepth = 0;

		StringBuffer deptXML = new StringBuffer();
		
		deptXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		deptXML.append("<tree id=\"0\">");
		
		for (int i = 0 ; i < deptList.size(); i++){
			commsubdeptBean bean = (commsubdeptBean)deptList.get(i);

			// 이전 엘레멘트와 현재 엘레메트 간의 레벨차이
			diffDepth = bean.getDeptLevel() - preLevel;
			
			if ( bean.getCrtusrgbn().equals("0") ) bean.setName(bean.getName() + " (공통)");
			else bean.setName(bean.getName() + " &lt;img src='" + delImg + "' alt='삭제' align='absmiddle' onclick=" + onclickHandler + "('delete','" + bean.getCode() + "')&gt;");
			
			if (i == 0){	// 최초 엘레멘트
				deptXML.append("\n<item text=\"" + bean.getName() + "\" id=\"" + bean.getCode() + "\" open=\"1\" >");
				//<item> 엘레멘트 본문(사용자정의 데이터)
				deptXML.append("\n<userdata name=\"grpgbn\">" + bean.getGrpGbn() + "</userdata>");
				deptXML.append("\n<userdata name=\"mainyn\">" + bean.getMainyn() + "</userdata>");
				
				if(deptList.size() == 1) {
					deptXML.append("\n</item>");
				}
				
			} else {
				if (diffDepth == 0) {				// 이전 엘레멘트와 동일레벨일 경우
					
					deptXML.append("</item>");
					deptXML.append("\n<item text=\"" + bean.getName() + "\" id=\"" + bean.getCode() + "\" >");
					//<item> 엘레멘트 본문(사용자정의 데이터)
					deptXML.append("\n<userdata name=\"grpgbn\">" + bean.getGrpGbn() + "</userdata>");
					deptXML.append("\n<userdata name=\"mainyn\">" + bean.getMainyn() + "</userdata>");
					
				} else if (diffDepth > 0){			// 이전 엘레멘트보다 하위 레벨일 경우
					deptXML.append("\n<item text=\"" + bean.getName() + "\" id=\"" + bean.getCode() +"\" >");
					//<item> 엘레멘트 본문(사용자정의 데이터)
					deptXML.append("\n<userdata name=\"grpgbn\">" + bean.getGrpGbn() + "</userdata>");
					deptXML.append("\n<userdata name=\"mainyn\">" + bean.getMainyn() + "</userdata>");
				} else {							// 이전 엘레멘트보다 상위 레벨인 경우
					for (int j = 0 ; j < Math.abs(diffDepth) + 1 ; j++)
					deptXML.append("\n</item>");
					deptXML.append("\n<item text=\"" + bean.getName() + "\" id=\"" + bean.getCode() +"\" >");
					//<item> 엘레멘트 본문(사용자정의 데이터)
					deptXML.append("\n<userdata name=\"grpgbn\">" + bean.getGrpGbn() + "</userdata>");
					deptXML.append("\n<userdata name=\"mainyn\">" + bean.getMainyn() + "</userdata>");
				}
				
				if (i == (deptList.size() - 1)){
					if(bean.getDeptLevel() ==0){
						for (int j = 0 ; j <= bean.getDeptLevel() ; j++){
							deptXML.append("\n</item>");
						}
					}else{
						for (int j = 0 ; j < bean.getDeptLevel() ; j++){
							deptXML.append("\n</item>");
						}
					}
				}
			}
			
			preLevel = bean.getDeptLevel();
		}
		
		if(deptList.size() == 0) {
			deptXML.append("\n<item text=\"정보가 없습니다\"></item>");
		}
		
		// 최초 엘레멘트 open에 대해 엘레멘트 닫기
		deptXML.append("\n</tree>");
		return (deptXML.toString());
	}
	
	/**
	 * 부서정보를 XML형태로 생성하여 리턴
	 * @return
	 * @throws Exception
	 */
	public String getFormationList() throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList deptList = null;
		
		StringBuffer selectQuery = new StringBuffer();
		
		String rootid = appInfo.getRootid();		
		selectQuery.append("SELECT DEPT_ID, DEPT_NAME, UPPER_DEPT_ID, GRPGBN, MAIN_YN, LEVEL DEPT_LEVEL             \n");
		selectQuery.append("FROM                                                                                    \n");
		selectQuery.append("(                                                                                       \n");
		selectQuery.append("SELECT DEPT_ID, DEPT_NAME, UPPER_DEPT_ID, '2' GRPGBN, MAIN_YN, DEPT_RANK, '' GRADE_ID 	\n");
		selectQuery.append("FROM DEPT					                                                            \n");
		selectQuery.append("WHERE USE_YN = 'Y'                                                                      \n");
		selectQuery.append(") T                                                                                     \n");
		selectQuery.append("START WITH DEPT_ID = ?                                                                  \n");
		selectQuery.append("CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID                                                \n");
		selectQuery.append("ORDER SIBLINGS BY TO_NUMBER(DEPT_RANK), GRADE_ID                                        \n");
		
		try{
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			pstmt.setString(1, rootid);
			rs = pstmt.executeQuery();
			
			deptList = new ArrayList();
			
			while(rs.next()){
				commsubdeptBean bean = new commsubdeptBean();
				
				bean.setCode(rs.getString("DEPT_ID"));
				bean.setName(rs.getString("DEPT_NAME"));
				bean.setUpperDeptId(rs.getString("UPPER_DEPT_ID"));
				bean.setGrpGbn(rs.getString("GRPGBN"));
				bean.setMainyn(rs.getString("MAIN_YN"));
				bean.setDeptLevel(rs.getInt("DEPT_LEVEL"));
				deptList.add(bean);
			}
		}catch(Exception e){
			logger.error("ERROR",e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		}finally{
			ConnectionManager.close(con, pstmt, rs);
		}
		
		return getDeptListXML(deptList);
	}
	
	/**
	 * 메뉴 목록에 대한 XML 파일을 생성한다.
	 * @param deptList
	 * @return
	 */
	private String getDeptListXML(ArrayList deptList) {

		int preLevel = 0;
		int diffDepth = 0;

		StringBuffer deptXML = new StringBuffer();
		
		deptXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		deptXML.append("<tree id=\"0\">");
		
		for (int i = 0 ; i < deptList.size(); i++){
			commsubdeptBean bean = (commsubdeptBean)deptList.get(i);

			// 이전 엘레멘트와 현재 엘레메트 간의 레벨차이
			diffDepth = bean.getDeptLevel() - preLevel;
			
			if (i == 0){	// 최초 엘레멘트
				deptXML.append("\n<item text=\"" + bean.getName() + "\" id=\"" + bean.getCode() + "\" open=\"1\" >");
				//<item> 엘레멘트 본문(사용자정의 데이터)
				deptXML.append("\n<userdata name=\"grpgbn\">" + bean.getGrpGbn() + "</userdata>");
				deptXML.append("\n<userdata name=\"mainyn\">" + bean.getMainyn() + "</userdata>");
				
				if(deptList.size() == 1) {
					deptXML.append("\n</item>");
				}
				
			} else {
				if (diffDepth == 0) {				// 이전 엘레멘트와 동일레벨일 경우
					
					deptXML.append("</item>");
					deptXML.append("\n<item text=\"" + bean.getName() + "\" id=\"" + bean.getCode() + "\" >");
					//<item> 엘레멘트 본문(사용자정의 데이터)
					deptXML.append("\n<userdata name=\"grpgbn\">" + bean.getGrpGbn() + "</userdata>");
					deptXML.append("\n<userdata name=\"mainyn\">" + bean.getMainyn() + "</userdata>");
					
				} else if (diffDepth > 0){			// 이전 엘레멘트보다 하위 레벨일 경우
					deptXML.append("\n<item text=\"" + bean.getName() + "\" id=\"" + bean.getCode() +"\" >");
					//<item> 엘레멘트 본문(사용자정의 데이터)
					deptXML.append("\n<userdata name=\"grpgbn\">" + bean.getGrpGbn() + "</userdata>");
					deptXML.append("\n<userdata name=\"mainyn\">" + bean.getMainyn() + "</userdata>");
				} else {							// 이전 엘레멘트보다 상위 레벨인 경우
					for (int j = 0 ; j < Math.abs(diffDepth) + 1 ; j++)
					deptXML.append("\n</item>");
					deptXML.append("\n<item text=\"" + bean.getName() + "\" id=\"" + bean.getCode() +"\" >");
					//<item> 엘레멘트 본문(사용자정의 데이터)
					deptXML.append("\n<userdata name=\"grpgbn\">" + bean.getGrpGbn() + "</userdata>");
					deptXML.append("\n<userdata name=\"mainyn\">" + bean.getMainyn() + "</userdata>");
				}
				
				if (i == (deptList.size() - 1)){
					if(bean.getDeptLevel() ==0){
						for (int j = 0 ; j <= bean.getDeptLevel() ; j++){
							deptXML.append("\n</item>");
						}
					}else{
						for (int j = 0 ; j < bean.getDeptLevel() ; j++){
							deptXML.append("\n</item>");
						}
					}
				}
			}
			
			preLevel = bean.getDeptLevel();
		}
		
		if(deptList.size() == 0) {
			deptXML.append("\n<item text=\"정보가 없습니다\"></item>");
		}
		
		// 최초 엘레멘트 open에 대해 엘레멘트 닫기
		deptXML.append("\n</tree>");
		return (deptXML.toString());
	}
	
	public List getCommsubdeptList(int sysdocno, String predeptcd, String sessionId, String userid) throws Exception {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList deptView = new ArrayList();
		int bindPos = 0;
		
		StringBuffer sql = new StringBuffer();
		
		if(sysdocno == 0){
			sql.append("SELECT SESSIONID, GRPCD, GRPNM, GRPGBN,                                                              \n");
			sql.append("       DECODE(GRPGBN, '1', GRPNM, '2', NVL(TRIM(SUBSTR(DEPT_FULLNAME, ?)), GRPNM)) DISPLAYNAME       \n");
			sql.append("FROM TGTLIST_TEMP T, DEPT D                                                                          \n");
			sql.append("WHERE T.GRPCD = D.DEPT_ID(+)                                                                         \n");
			sql.append("AND SESSIONID LIKE ?                                                                                    \n");
			sql.append("AND PREDEPTCD = ?												                                     \n");
			sql.append("AND GRPCD NOT IN (SELECT TGTDEPTCD								                                     \n");
			sql.append("				  FROM TGTDEPT_TEMP								                                     \n");
			sql.append("				  WHERE SESSIONID LIKE ?							                                     \n");
			sql.append("				  AND PREDEPTCD = ?							                                         \n");			
			sql.append("				  AND SUBMITSTATE > '01' 						                                     \n");
			sql.append("				  AND TGTDEPTCD IN (SELECT TGTDEPT                                                   \n");
			sql.append("				                    FROM INPUTUSR_TEMP                                               \n");
			sql.append("				                    WHERE SESSIONID LIKE ?))                                            \n");
			sql.append("UNION ALL														                                     \n");
			sql.append("SELECT SESSIONID, INPUTUSRID, INPUTUSRNM, '3',                                                       \n");
			sql.append("       NVL(TRIM(SUBSTR(DEPT_FULLNAME, ?)), INPUTUSRNM) || ' ' || INPUTUSRNM DISPLAYNAME              \n");
			sql.append("FROM INPUTUSR_TEMP I, USR D                                                                          \n");
			sql.append("WHERE I.INPUTUSRID = D.USER_ID(+)                                                                    \n");
			sql.append("AND SESSIONID LIKE ?                                                                                    \n");
			sql.append("AND TGTDEPT IN (SELECT TGTDEPTCD								                                     \n");
			sql.append("				FROM TGTDEPT_TEMP								                                     \n");
			sql.append("				WHERE SESSIONID LIKE ?								                                     \n");
			sql.append("				AND PREDEPTCD = ?							                                         \n");
			sql.append("				AND SUBMITSTATE > '01')							                                     \n");
			sql.append("AND INPUTUSRID NOT IN (SELECT CODE								                                     \n");
			sql.append("					   FROM GRPLISTDTL							                                     \n");
			sql.append("					   WHERE CODEGBN = '1'						                                     \n");
			sql.append("					   AND GRPLISTCD IN (SELECT GRPCD			                                     \n");
			sql.append("										 FROM TGTLIST_TEMP		                                     \n");
			sql.append("										 WHERE GRPGBN = '1'		                                     \n");
			sql.append("										 AND SESSIONID LIKE ?	                                         \n");
			sql.append("										 AND PREDEPTCD = ?))	                                     \n");
		}else{			
			sql.append("SELECT SYSDOCNO, GRPCD, GRPNM, GRPGBN,                                                               \n");
			sql.append("       DECODE(GRPGBN, '1', GRPNM, '2', NVL(TRIM(SUBSTR(DEPT_FULLNAME, ?)), GRPNM)) DISPLAYNAME       \n");
			sql.append("FROM TGTLIST T, DEPT D                                                                               \n");
			sql.append("WHERE T.GRPCD = D.DEPT_ID(+)                                                                         \n");
			sql.append("AND SYSDOCNO = ?                                                                                     \n");
			sql.append("AND PREDEPTCD = ?												                                     \n");
			sql.append("AND GRPCD NOT IN (SELECT TGTDEPTCD								                                     \n");
			sql.append("				  FROM TGTDEPT									                                     \n");
			sql.append("				  WHERE SYSDOCNO = ?							                                     \n");
			sql.append("				  AND PREDEPTCD = ?							                                         \n");
			sql.append("				  AND SUBMITSTATE > '01' 						                                     \n");
			sql.append("				  AND TGTDEPTCD IN (SELECT TGTDEPT                                                   \n");
			sql.append("				                    FROM INPUTUSR                                                    \n");
			sql.append("				                    WHERE SYSDOCNO = ?))                                             \n");
			sql.append("UNION ALL														                                     \n");
			sql.append("SELECT SYSDOCNO, INPUTUSRID, INPUTUSRNM, '3',                                                        \n");
			sql.append("       NVL(TRIM(SUBSTR(DEPT_FULLNAME, ?)), INPUTUSRNM) || ' ' || INPUTUSRNM DISPLAYNAME              \n");
			sql.append("FROM INPUTUSR I, USR D                                                                               \n");
			sql.append("WHERE I.INPUTUSRID = D.USER_ID(+)                                                                    \n");
			sql.append("AND SYSDOCNO = ?                                                                                     \n");
			sql.append("AND TGTDEPT IN (SELECT TGTDEPTCD								                                     \n");
			sql.append("				FROM TGTDEPT									                                     \n");
			sql.append("				WHERE SYSDOCNO = ?								                                     \n");
			sql.append("				AND PREDEPTCD = ?							                                         \n");
			sql.append("				AND SUBMITSTATE > '01')							                                     \n");
			sql.append("AND INPUTUSRID NOT IN (SELECT CODE								                                     \n");
			sql.append("					   FROM GRPLISTDTL							                                     \n");
			sql.append("					   WHERE CODEGBN = '1'						                                     \n");
			sql.append("					   AND GRPLISTCD IN (SELECT GRPCD			                                     \n");
			sql.append("										 FROM TGTLIST			                                     \n");
			sql.append("										 WHERE GRPGBN = '1'		                                     \n");
			sql.append("										 AND SYSDOCNO = ?		                                     \n");
			sql.append("										 AND PREDEPTCD = ?))                                         \n");
		}
		
		try{			
			int substring = commfunction.getDeptFullNameSubstringIndex(userid);
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			
			String rootTgtdeptcd = CommTreatManager.instance().getPreDeptcd(con, sysdocno, predeptcd, false);
			
			if(sysdocno ==0 ){
				pstmt.setInt(++bindPos, substring);
				pstmt.setString(++bindPos, sessionId);
				pstmt.setString(++bindPos, rootTgtdeptcd);
				pstmt.setString(++bindPos, sessionId);
				pstmt.setString(++bindPos, rootTgtdeptcd);
				pstmt.setString(++bindPos, sessionId);
				pstmt.setInt(++bindPos, substring);
				pstmt.setString(++bindPos, sessionId);
				pstmt.setString(++bindPos, sessionId);
				pstmt.setString(++bindPos, rootTgtdeptcd);
				pstmt.setString(++bindPos, sessionId);
				pstmt.setString(++bindPos, rootTgtdeptcd);
			}else{
				pstmt.setInt(++bindPos, substring);
				pstmt.setInt(++bindPos, sysdocno);
				pstmt.setString(++bindPos, rootTgtdeptcd);
				pstmt.setInt(++bindPos, sysdocno);
				pstmt.setString(++bindPos, rootTgtdeptcd);
				pstmt.setInt(++bindPos, sysdocno);
				pstmt.setInt(++bindPos, substring);
				pstmt.setInt(++bindPos, sysdocno);
				pstmt.setInt(++bindPos, sysdocno);
				pstmt.setString(++bindPos, rootTgtdeptcd);
				pstmt.setInt(++bindPos, sysdocno);
				pstmt.setString(++bindPos, rootTgtdeptcd);
			}
			
			rs = pstmt.executeQuery();
			
			commsubdeptBean bean = null;
			while(rs.next()){
				bean = new commsubdeptBean();
				
				bean.setCode(rs.getString("GRPCD"));
				bean.setName(rs.getString("GRPNM"));
				bean.setDisplayName(rs.getString("DISPLAYNAME"));
				bean.setGrpGbn(rs.getString("GRPGBN"));

				deptView.add(bean);
				
				bean = null;				
			}
			
		}finally{
			ConnectionManager.close(con, pstmt, rs);
		}
		return deptView;
	}
	
	public String getGrpDetailList(String grpId, String userid) throws Exception {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList detailList = new ArrayList();
		String detailListXML = "";
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT GRPLISTCD, SEQ, CODE, NAME, CODEGBN + 2 AS GRPGBN,                                               \n");
		sql.append("       CASE CODEGBN WHEN '0' THEN (SELECT NVL(TRIM(SUBSTR(DEPT_FULLNAME, ?)), DEPT_NAME)                \n");
		sql.append("                                   FROM DEPT WHERE DEPT_ID = G.CODE)                                    \n");
		sql.append("                    WHEN '1' THEN (SELECT NVL(TRIM(SUBSTR(DEPT_FULLNAME, ?)), DEPT_NAME) || ' ' || NAME \n");
		sql.append("                                   FROM USR WHERE USER_ID = G.CODE)                                     \n");
		sql.append("                    ELSE NAME END DISPLAYNAME                                                           \n");
		sql.append("FROM GRPLISTDTL G                                                                                       \n");
		sql.append("WHERE GRPLISTCD = ?                                                                                     \n");
		sql.append("ORDER BY SEQ                                                                                            \n");
		
		try{
			int substring = commfunction.getDeptFullNameSubstringIndex(userid);
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			
			int bindPos = 0;
			pstmt.setInt(++bindPos, substring);
			pstmt.setInt(++bindPos, substring);
			pstmt.setString(++bindPos, grpId);
			rs = pstmt.executeQuery();
			
			commsubdeptBean bean = null;
			while(rs.next()){
				bean = new commsubdeptBean();
				
				bean.setCode(rs.getString("CODE"));
				bean.setName(rs.getString("NAME"));
				bean.setGrpGbn(rs.getString("GRPGBN"));
				bean.setDisplayName(rs.getString("DISPLAYNAME"));
				detailList.add(bean);
				
				bean = null;				
			}
			
			detailListXML = getDeptViewXML(detailList);
		}finally{
			ConnectionManager.close(con, pstmt, rs);
		}
		return detailListXML;
	}
	
	public String getDeptViewXML(List deptView) {

		StringBuffer deptViewXML = new StringBuffer();

		if (deptView.size() > 0){
			
			commsubdeptBean bean = null;
			for (int i = 0 ; i < deptView.size() ; i++){
				bean = (commsubdeptBean)deptView.get(i);
				deptViewXML.append("\n	<data id=\"").append(bean.getCode()).append("\">");				
				deptViewXML.append("\n		<userdata id=\"deptId\">").append(bean.getCode()).append("</userdata>");
				deptViewXML.append("\n		<userdata id=\"deptName\">").append(bean.getName()).append("</userdata>");
				deptViewXML.append("\n		<userdata id=\"deptFullName\">").append(bean.getDisplayName()).append("</userdata>");
				deptViewXML.append("\n		<userdata id=\"grpGbn\">").append(bean.getGrpGbn()).append("</userdata>");
				deptViewXML.append("\n	</data>");
				bean = null;
			}			
		}
		return (deptViewXML.toString());
	}
	
	public int commsubdeptInsert(int sysdocno, String docState, ArrayList deptList, String sessionId, String userid, String deptid, String grpCd, String subCd ) throws Exception {

		Connection con = null;
		PreparedStatement pstmt = null;
		int bindPos = 0;
		int cnt = 0;
		int[] ret = null;
		
		int maxSeq = 0;
		
		StringBuffer delete1Query = new StringBuffer();
		StringBuffer delete2Query = new StringBuffer();
		StringBuffer delete3Query = new StringBuffer();
		StringBuffer insert1Query = new StringBuffer();
		StringBuffer insert2Query = new StringBuffer();
		StringBuffer insert3Query = new StringBuffer();
		StringBuffer delete4Query = new StringBuffer();
		StringBuffer delete5Query = new StringBuffer();
		StringBuffer delete6Query = new StringBuffer();
		StringBuffer update1Query = new StringBuffer();
		StringBuffer update2Query = new StringBuffer();
		StringBuffer update3Query = new StringBuffer();
		StringBuffer update4Query = new StringBuffer();

		if ( sysdocno == 0 ) {
			delete1Query.append("\n	DELETE FROM INPUTUSR_TEMP                                ");
			delete1Query.append("\n	WHERE SESSIONID LIKE ?		                                 ");
			delete1Query.append("\n AND (SESSIONID, TGTDEPT) IN (SELECT SESSIONID, TGTDEPTCD ");
			delete1Query.append("\n                              FROM TGTDEPT_TEMP           ");
			delete1Query.append("\n                              WHERE SESSIONID LIKE ?         ");
			delete1Query.append("\n                              AND PREDEPTCD = ?)          ");
			
			delete2Query.append("\n	DELETE FROM TGTLIST_TEMP  ");
			delete2Query.append("\n	WHERE SESSIONID LIKE ?		  ");
			delete2Query.append("\n	AND PREDEPTCD = ?		  ");
			
			delete3Query.append("\n DELETE FROM TGTDEPT_TEMP  ");
			delete3Query.append("\n	WHERE SESSIONID LIKE ?		  ");
			delete3Query.append("\n	AND PREDEPTCD = ?         ");
			
			update1Query.append("\n UPDATE TGTDEPT_TEMP T                                             ");
			update1Query.append("\n SET SUBMITSTATE = '01', INUSRSENDDT = '', APPNTUSRNM = ''         ");
			update1Query.append("\n WHERE SESSIONID LIKE ?                                               ");
			update1Query.append("\n AND TGTDEPTCD NOT IN (SELECT DEPT_ID                              ");
			update1Query.append("\n                       FROM DEPT                                   ");
			update1Query.append("\n                       START WITH DEPT_ID IN (SELECT TGTDEPT       ");
			update1Query.append("\n                                              FROM INPUTUSR_TEMP   ");
			update1Query.append("\n                                              WHERE SESSIONID LIKE ?) ");
			update1Query.append("\n                       CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID)   ");

			update2Query.append("\n UPDATE TGTDEPT_TEMP                                                                            ");
			update2Query.append("\n SET SUBMITSTATE = '02', INUSRSENDDT = TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS'), APPNTUSRNM = ? ");
			update2Query.append("\n WHERE SESSIONID LIKE ?                                                                            ");
			update2Query.append("\n AND SUBMITSTATE = '01'                                                                         ");
			update2Query.append("\n AND TGTDEPTCD IN (SELECT DEPT_ID                                                               ");
			update2Query.append("\n                   FROM DEPT                                                                    ");
			update2Query.append("\n                   START WITH DEPT_ID IN (SELECT TGTDEPT                                        ");
			update2Query.append("\n                                          FROM INPUTUSR_TEMP                                    ");
			update2Query.append("\n                                          WHERE SESSIONID LIKE ?)                                  ");
            update2Query.append("\n                   CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID)                                    ");

            update3Query.append("\n UPDATE TGTDEPT_TEMP                                             ");
            update3Query.append("\n SET SUBMITSTATE = '02', INUSRSENDDT = '', APPNTUSRNM = ''  		");
            update3Query.append("\n WHERE SESSIONID LIKE ?                                             ");
            update3Query.append("\n AND SUBMITSTATE = '01'                                          ");
            update3Query.append("\n AND TGTDEPTCD IN ( SELECT PREDEPTCD                             ");
            update3Query.append("\n                    FROM TGTDEPT                                 ");
            update3Query.append("\n                    WHERE SYSDOCNO = ?)                    	    ");
            
            delete4Query.append("\n	DELETE INPUTUSR_TEMP                                         ");
            delete4Query.append("\n	WHERE SESSIONID LIKE ?                                          ");
            delete4Query.append("\n	AND INPUTSTATE <= 1                                          ");
            delete4Query.append("\n AND TGTDEPT IN (SELECT DISTINCT DEPT_ID                      ");
            delete4Query.append("\n                   FROM DEPT                                  ");
            delete4Query.append("\n                   WHERE LEVEL > 1                            ");
            delete4Query.append("\n                   START WITH DEPT_ID IN (SELECT TGTDEPTCD    ");
            delete4Query.append("\n                                          FROM TGTDEPT_TEMP   ");
            delete4Query.append("\n                                          WHERE SESSIONID LIKE ? ");
            delete4Query.append("\n                                          AND PREDEPTCD = ?)  ");
            delete4Query.append("\n                   CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID)  ");
			
			delete5Query.append("\n DELETE TGTLIST_TEMP                                       ");
			delete5Query.append("\n WHERE SESSIONID LIKE ?                                      ");
			delete5Query.append("\n AND PREDEPTCD = ?                                        ");
			delete5Query.append("\n AND GRPGBN = 2                                           ");
			delete5Query.append("\n AND GRPCD IN (SELECT DISTINCT DEPT_ID                    ");
			delete5Query.append("\n               FROM DEPT                                  ");
			delete5Query.append("\n               WHERE LEVEL > 1                            ");
			delete5Query.append("\n               AND DEPT_ID IN (SELECT TGTDEPTCD           ");
			delete5Query.append("\n                               FROM TGTDEPT_TEMP          ");
			delete5Query.append("\n                               WHERE SESSIONID LIKE ?        ");
			delete5Query.append("\n                               AND PREDEPTCD = ?          ");
			delete5Query.append("\n                               AND (SUBMITSTATE <= 1 OR SUBMITSTATE = '06')) ");
			delete5Query.append("\n               START WITH DEPT_ID IN (SELECT GRPCD        ");
			delete5Query.append("\n                                      FROM TGTLIST_TEMP   ");
			delete5Query.append("\n                                      WHERE SESSIONID LIKE ? ");
			delete5Query.append("\n                                      AND PREDEPTCD = ?   ");
			delete5Query.append("\n                                      AND GRPGBN = 2)     ");
			delete5Query.append("\n               CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID)  ");
			
			delete6Query.append("\n DELETE TGTDEPT_TEMP                                          ");
			delete6Query.append("\n WHERE SESSIONID LIKE ?                                          ");
			delete6Query.append("\n AND PREDEPTCD = ?                                            ");
			delete6Query.append("\n AND TGTDEPTCD IN (SELECT DISTINCT DEPT_ID                    ");
			delete6Query.append("\n                   FROM DEPT                                  ");
			delete6Query.append("\n                   WHERE LEVEL > 1                            ");
			delete6Query.append("\n                   START WITH DEPT_ID IN (SELECT TGTDEPTCD    ");
			delete6Query.append("\n                                          FROM TGTDEPT_TEMP   ");
			delete6Query.append("\n                                          WHERE SESSIONID LIKE ? ");
			delete6Query.append("\n                                          AND PREDEPTCD = ?)  ");
			delete6Query.append("\n                   CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID)  ");
		} else {			
			if ( Integer.parseInt(docState) >= 4 ) {				
				delete1Query.append("\n	DELETE FROM INPUTUSR                                   ");
				delete1Query.append("\n	WHERE SYSDOCNO = ?                                     ");
				delete1Query.append("\n	AND INPUTSTATE <= 1                                    ");
				delete1Query.append("\n AND (SYSDOCNO, TGTDEPT) IN (SELECT SYSDOCNO, TGTDEPTCD ");
				delete1Query.append("\n                             FROM TGTDEPT               ");
				delete1Query.append("\n                             WHERE SYSDOCNO = ?         ");
				delete1Query.append("\n                             AND PREDEPTCD = ?          ");
				delete1Query.append("\n                             AND (SUBMITSTATE <= 2 OR SUBMITSTATE = '06')) ");
				
				delete2Query.append("\n	DELETE FROM TGTLIST		                             ");
				delete2Query.append("\n	WHERE SYSDOCNO = ?		                             ");	
				delete2Query.append("\n	AND PREDEPTCD = ?		                             ");
				delete2Query.append("\n AND (GRPGBN = '1' OR (                               ");
				delete2Query.append("\n     (SYSDOCNO, GRPCD) IN (SELECT SYSDOCNO, TGTDEPTCD ");
				delete2Query.append("\n                           FROM TGTDEPT               ");
				delete2Query.append("\n                           WHERE SYSDOCNO = ?         ");
				delete2Query.append("\n                           AND PREDEPTCD = ?          ");
				delete2Query.append("\n                           AND (SUBMITSTATE <= 1 OR SUBMITSTATE = '06')))) ");
				
				delete3Query.append("\n	DELETE FROM TGTDEPT	  ");
				delete3Query.append("\n	WHERE SYSDOCNO = ?	  ");
				delete3Query.append("\n	AND PREDEPTCD = ?     ");
				delete3Query.append("\n	AND (SUBMITSTATE <= 1 OR SUBMITSTATE = '06') ");
			} else {								
				delete1Query.append("\n	DELETE FROM INPUTUSR                                   ");
				delete1Query.append("\n	WHERE SYSDOCNO = ?                                     ");
				delete1Query.append("\n AND (SYSDOCNO, TGTDEPT) IN (SELECT SYSDOCNO, TGTDEPTCD ");
				delete1Query.append("\n                             FROM TGTDEPT               ");
				delete1Query.append("\n                             WHERE SYSDOCNO = ?         ");
				delete1Query.append("\n                             AND PREDEPTCD = ?)         ");
				
				delete2Query.append("\n	DELETE FROM TGTLIST		                              ");
				delete2Query.append("\n	WHERE SYSDOCNO = ?		                              ");	
				delete2Query.append("\n	AND PREDEPTCD = ?		                              ");
				delete2Query.append("\n AND (GRPGBN = '1' OR (                                ");
				delete2Query.append("\n      (SYSDOCNO, GRPCD) IN (SELECT SYSDOCNO, TGTDEPTCD ");
				delete2Query.append("\n                           FROM TGTDEPT                ");
				delete2Query.append("\n                           WHERE SYSDOCNO = ?          ");
				delete2Query.append("\n                           AND PREDEPTCD = ?)))        ");
				
				delete3Query.append("\n DELETE FROM TGTDEPT   ");
				delete3Query.append("\n	WHERE SYSDOCNO = ?    ");
				delete3Query.append("\n	AND PREDEPTCD = ?     ");
			}
			
			update1Query.append("\n UPDATE TGTDEPT T                                                 ");
			update1Query.append("\n SET SUBMITSTATE = '01', INUSRSENDDT = '', APPNTUSRNM = ''        ");
			update1Query.append("\n WHERE SYSDOCNO = ?                                               ");
			update1Query.append("\n AND SUBMITSTATE != '06'                                          ");			
			update1Query.append("\n AND TGTDEPTCD NOT IN (SELECT DEPT_ID                             ");
			update1Query.append("\n                       FROM DEPT                                  ");
			update1Query.append("\n                       START WITH DEPT_ID IN (SELECT TGTDEPT      ");
			update1Query.append("\n                                              FROM INPUTUSR       ");
			update1Query.append("\n                                              WHERE SYSDOCNO = ?) ");
			update1Query.append("\n                       CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID)  ");
			
			update2Query.append("\n UPDATE TGTDEPT                                                                                 ");
			update2Query.append("\n SET SUBMITSTATE = '02', INUSRSENDDT = TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS'), APPNTUSRNM = ? ");
			update2Query.append("\n WHERE SYSDOCNO = ?                                                                             ");
			update2Query.append("\n AND SUBMITSTATE = '01'                                                                         ");
			update2Query.append("\n AND TGTDEPTCD IN (SELECT DEPT_ID                                                               ");
			update2Query.append("\n                   FROM DEPT                                                                    ");
			update2Query.append("\n                   START WITH DEPT_ID IN (SELECT TGTDEPT                                        ");
			update2Query.append("\n                                          FROM INPUTUSR                                         ");
			update2Query.append("\n                                          WHERE SYSDOCNO = ?)                                   ");
            update2Query.append("\n                   CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID)                                    ");

            update3Query.append("\n UPDATE TGTDEPT                         		                    ");
            update3Query.append("\n SET SUBMITSTATE = '02', INUSRSENDDT = '', APPNTUSRNM = ''  		");
            update3Query.append("\n WHERE SYSDOCNO = ?                                              ");
            update3Query.append("\n AND SUBMITSTATE = '01'                                          ");
            update3Query.append("\n AND TGTDEPTCD IN ( SELECT PREDEPTCD                             ");
            update3Query.append("\n                    FROM TGTDEPT                                 ");
            update3Query.append("\n                    WHERE SYSDOCNO = ?)                    	    ");
            
            delete4Query.append("\n	DELETE INPUTUSR                                              ");
            delete4Query.append("\n	WHERE SYSDOCNO = ?                                           ");
            delete4Query.append("\n	AND INPUTSTATE <= 1                                          ");
            delete4Query.append("\n AND TGTDEPT IN (SELECT DISTINCT DEPT_ID                      ");
            delete4Query.append("\n                 FROM DEPT                                    ");
            delete4Query.append("\n                 WHERE LEVEL > 1                              ");
            delete4Query.append("\n                 START WITH DEPT_ID IN (SELECT TGTDEPTCD      ");
            delete4Query.append("\n                                        FROM TGTDEPT          ");
            delete4Query.append("\n                                        WHERE SYSDOCNO = ?    ");
            delete4Query.append("\n                                        AND PREDEPTCD = ?     ");
            delete4Query.append("\n                                        AND (SUBMITSTATE <= 1 OR SUBMITSTATE = '06')) ");
            delete4Query.append("\n                 CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID)    ");

			delete5Query.append("\n DELETE TGTLIST                                          ");
			delete5Query.append("\n WHERE SYSDOCNO = ?                                      ");
			delete5Query.append("\n AND PREDEPTCD = ?                                       ");
			delete5Query.append("\n AND GRPGBN = 2                                          ");
			delete5Query.append("\n AND GRPCD IN (SELECT DISTINCT DEPT_ID                   ");
			delete5Query.append("\n               FROM DEPT                                 ");
			delete5Query.append("\n               WHERE LEVEL > 1                           ");
			delete5Query.append("\n               AND DEPT_ID IN (SELECT TGTDEPTCD          ");
			delete5Query.append("\n                               FROM TGTDEPT              ");
			delete5Query.append("\n                               WHERE SYSDOCNO = ?        ");
			delete5Query.append("\n                               AND PREDEPTCD = ?         ");
			delete5Query.append("\n                               AND (SUBMITSTATE <= 1 OR SUBMITSTATE = '06')) ");
			delete5Query.append("\n               START WITH DEPT_ID IN (SELECT GRPCD       ");
			delete5Query.append("\n                                      FROM TGTLIST       ");
			delete5Query.append("\n                                      WHERE SYSDOCNO = ? ");
			delete5Query.append("\n                                      AND PREDEPTCD = ?  ");
			delete5Query.append("\n                                      AND GRPGBN = 2)    ");
			delete5Query.append("\n               CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID) ");
			
			delete6Query.append("\n DELETE TGTDEPT                                                 ");
			delete6Query.append("\n WHERE SYSDOCNO = ?                                             ");
			delete6Query.append("\n AND PREDEPTCD = ?                                              ");
			delete6Query.append("\n AND (SUBMITSTATE <= 1 OR SUBMITSTATE = '06')                   ");
			delete6Query.append("\n AND TGTDEPTCD IN (SELECT DISTINCT DEPT_ID                      ");
			delete6Query.append("\n                   FROM DEPT                                    ");
			delete6Query.append("\n                   WHERE LEVEL > 1                              ");
			delete6Query.append("\n                   START WITH DEPT_ID IN (SELECT TGTDEPTCD      ");
			delete6Query.append("\n                                          FROM TGTDEPT          ");
			delete6Query.append("\n                                          WHERE SYSDOCNO = ?    ");
			delete6Query.append("\n                                          AND PREDEPTCD = ?     ");
			delete6Query.append("\n                                          AND (SUBMITSTATE <= 1 OR SUBMITSTATE = '06')) ");
			delete6Query.append("\n                   CONNECT BY PRIOR UPPER_DEPT_ID = DEPT_ID)    ");
		}
		
		if ( sysdocno == 0 ) {
			insert1Query.append("\n	INSERT INTO TGTLIST_TEMP(SESSIONID, SEQ, GRPCD, GRPNM, GRPGBN, PREDEPTCD)                         ");
			insert1Query.append("\n	SELECT ?, ?, ?, ?, ?, ? FROM DUAL                                                                 ");
			insert1Query.append("\n	WHERE NOT EXISTS (SELECT * FROM TGTLIST_TEMP WHERE SESSIONID LIKE ? AND GRPCD = ? AND PREDEPTCD = ?) ");
			
			if ( "".equals(grpCd) == false || "".equals(subCd) == false ) {
				insert2Query.append("\n	INSERT INTO TGTDEPT_TEMP(SESSIONID, TGTDEPTCD, TGTDEPTNM, SUBMITSTATE, MODIFYYN, PREDEPTCD, CRTDT, CRTUSRID)   ");			
				insert2Query.append("\n	(																								               "); 
				if ( !grpCd.equals("") ) {
					insert2Query.append("\n	SELECT DISTINCT ? AS SESSIONID, CODE AS TGTDEPTCD, NAME AS TGTDEPTNM, '01' AS SUBMITSTATE, '1', ?,         ");
					insert2Query.append("\n		   TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS'), ?											               ");
					insert2Query.append("\n	FROM GRPLISTDTL																			   	  	           ");
					insert2Query.append("\n	WHERE GRPLISTCD IN (" + grpCd + ")														   	               ");
					insert2Query.append("\n	AND CODEGBN = '0'																		   	               ");
					insert2Query.append("\n	UNION																					   	 	           ");
					insert2Query.append("\n	SELECT DISTINCT ? AS SESSIONID, DEPT_ID AS TGTDEPTCD, DEPT_NAME AS TGTDEPTNM, '01' AS SUBMITSTATE, '1', ?, ");
					insert2Query.append("\n		   TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS'), ?											               ");
					insert2Query.append("\n	FROM USR																					               ");
					insert2Query.append("\n	WHERE USER_ID IN (SELECT CODE																               ");
					insert2Query.append("\n					  FROM GRPLISTDTL															               ");
					insert2Query.append("\n					  WHERE GRPLISTCD IN (" + grpCd + ")										               ");
					insert2Query.append("\n					  AND CODEGBN = '1')														               ");
				}
				if ( !grpCd.equals("") && !subCd.equals("") ) {
					insert2Query.append("\n	UNION																						               ");
				}
				if ( !subCd.equals("") ) {
					insert2Query.append("\n	SELECT ? AS SESSIONID, DEPT_ID AS TGTDEPTCD, DEPT_NAME AS TGTDEPTNM, '01' AS SUBMITSTATE, '1', ?,          ");
					insert2Query.append("\n		   TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS'), ?											               ");			
					insert2Query.append("\n	FROM DEPT																					               ");
					insert2Query.append("\n	WHERE DEPT_ID IN (" + subCd + ")															               ");
					insert2Query.append("\n	UNION																						               ");
					insert2Query.append("\n	SELECT ? AS SESSIONID, DEPT_ID AS TGTDEPTCD, DEPT_NAME AS TGTDEPTNM, '01' AS SUBMITSTATE, '1', ?,          ");
					insert2Query.append("\n		   TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS'), ?											               ");			
					insert2Query.append("\n	FROM USR																					               ");
					insert2Query.append("\n	WHERE USER_ID IN (" + subCd + ")															               ");
				}
				insert2Query.append("\n	)																								               ");
				
				insert3Query.append("\n	INSERT INTO TGTLIST_TEMP(SESSIONID, SEQ, GRPCD, GRPNM, GRPGBN, PREDEPTCD) ");
				insert3Query.append("\n	SELECT ? AS SESSIONID, ?, DEPT_ID AS GRPCD, DEPT_NAME AS GRPNM, ?, ?      ");
				insert3Query.append("\n	FROM USR														          ");
				insert3Query.append("\n	WHERE USER_ID LIKE ?											          ");
				insert3Query.append("\n	AND (?, DEPT_ID) NOT IN (SELECT SESSIONID, GRPCD				          ");
				insert3Query.append("\n									FROM TGTLIST_TEMP				          ");
				insert3Query.append("\n									WHERE GRPGBN = '2'				          ");
				insert3Query.append("\n									AND SESSIONID LIKE ?				          ");
				insert3Query.append("\n									AND PREDEPTCD = ?)				          ");
			}
		} else {
			insert1Query.append("\n	INSERT INTO TGTLIST(SYSDOCNO, SEQ, GRPCD, GRPNM, GRPGBN, PREDEPTCD)                         ");
			insert1Query.append("\n	SELECT ?, ?, ?, ?, ?, ? FROM DUAL                                                           ");
			insert1Query.append("\n	WHERE NOT EXISTS (SELECT * FROM TGTLIST WHERE SYSDOCNO = ? AND GRPCD = ? AND PREDEPTCD = ?) ");
			
			if ( "".equals(grpCd) == false || "".equals(subCd) == false ) {
					insert2Query.append("\n	INSERT INTO TGTDEPT(SYSDOCNO, TGTDEPTCD, TGTDEPTNM, SUBMITSTATE, MODIFYYN, PREDEPTCD, CRTDT, CRTUSRID)        ");			
					insert2Query.append("\n	(																								              "); 	
				if ( !grpCd.equals("") ) {
					if ( Integer.parseInt(docState, 10) >= 4 ) {
						insert2Query.append("\n	SELECT DISTINCT ? AS SYSDOCNO, CODE AS TGTDEPTCD, NAME AS TGTDEPTNM, '01' AS SUBMITSTATE, '1', ?,         ");
						insert2Query.append("\n		   TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS'), ?											              ");
						insert2Query.append("\n	FROM GRPLISTDTL																				              ");
						insert2Query.append("\n	WHERE GRPLISTCD IN (" + grpCd + ")															              ");
						insert2Query.append("\n	AND CODEGBN = '0'																			              ");
						insert2Query.append("\n	AND CODE NOT IN (SELECT TGTDEPTCD															              ");
						insert2Query.append("\n					 FROM TGTDEPT			   												 	              ");
						insert2Query.append("\n					 WHERE SUBMITSTATE > 1 								                                      ");
						insert2Query.append("\n					 AND SYSDOCNO = ?															              ");
						insert2Query.append("\n					 AND PREDEPTCD = ?)															              ");
						insert2Query.append("\n	UNION																						              ");
						insert2Query.append("\n	SELECT DISTINCT ? AS SYSDOCNO, DEPT_ID AS TGTDEPTCD, DEPT_NAME AS TGTDEPTNM, '01' AS SUBMITSTATE, '1', ?, ");
						insert2Query.append("\n		   TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS'), ?											              ");
						insert2Query.append("\n	FROM USR																					              ");
						insert2Query.append("\n	WHERE USER_ID IN (SELECT CODE																              ");
						insert2Query.append("\n					  FROM GRPLISTDTL															              ");
						insert2Query.append("\n					  WHERE GRPLISTCD IN (" + grpCd + ")										              ");
						insert2Query.append("\n					  AND CODEGBN = '1')														              ");
						insert2Query.append("\n	AND DEPT_ID NOT IN (SELECT TGTDEPTCD														              ");
						insert2Query.append("\n						FROM TGTDEPT			   											 	              ");
						insert2Query.append("\n						WHERE SUBMITSTATE > 1 								                                  ");
						insert2Query.append("\n						AND SYSDOCNO = ?														              ");
						insert2Query.append("\n					    AND PREDEPTCD = ?)															          ");
					} else {
						insert2Query.append("\n	SELECT DISTINCT ? AS SYSDOCNO, CODE AS TGTDEPTCD, NAME AS TGTDEPTNM, '01' AS SUBMITSTATE, '1', ?,         ");
						insert2Query.append("\n		   TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS'), ?											              ");
						insert2Query.append("\n	FROM GRPLISTDTL																				              ");
						insert2Query.append("\n	WHERE GRPLISTCD IN (" + grpCd + ")															              ");
						insert2Query.append("\n	AND CODEGBN = '0'																			              ");
						insert2Query.append("\n	UNION																						              ");
						insert2Query.append("\n	SELECT DISTINCT ? AS SYSDOCNO, DEPT_ID AS TGTDEPTCD, DEPT_NAME AS TGTDEPTNM, '01' AS SUBMITSTATE, '1', ?, ");
						insert2Query.append("\n		   TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS'), ?											              ");
						insert2Query.append("\n	FROM USR																					              ");
						insert2Query.append("\n	WHERE USER_ID IN (SELECT CODE																              ");
						insert2Query.append("\n					  FROM GRPLISTDTL															              ");
						insert2Query.append("\n					  WHERE GRPLISTCD IN (" + grpCd + ")										              ");
						insert2Query.append("\n					  AND CODEGBN = '1')														              ");
					}
				}
				if ( !grpCd.equals("") && !subCd.equals("") ) {
					insert2Query.append("\n	UNION																							         "); 
				}
				if ( !subCd.equals("") ) {
					if ( Integer.parseInt(docState, 10) >= 4 ) {
						insert2Query.append("\n	SELECT ? AS SYSDOCNO, DEPT_ID AS TGTDEPTCD, DEPT_NAME AS TGTDEPTNM, '01' AS SUBMITSTATE, '1', ?,     ");
						insert2Query.append("\n		   TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS'), ?											         ");			
						insert2Query.append("\n	FROM DEPT																					         ");
						insert2Query.append("\n	WHERE DEPT_ID IN (" + subCd + ")															         ");
						insert2Query.append("\n	AND DEPT_ID NOT IN (SELECT TGTDEPTCD														         ");
						insert2Query.append("\n						FROM TGTDEPT															         ");
						insert2Query.append("\n						WHERE SUBMITSTATE > 1								                             ");
						insert2Query.append("\n						AND SYSDOCNO = ?														         ");
						insert2Query.append("\n					    AND PREDEPTCD = ?)															     ");
						insert2Query.append("\n	UNION																						         ");
						insert2Query.append("\n	SELECT ? AS SYSDOCNO, DEPT_ID AS TGTDEPTCD, DEPT_NAME AS TGTDEPTNM, '01' AS SUBMITSTATE, '1', ?,     ");
						insert2Query.append("\n		   TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS'), ?											         ");			
						insert2Query.append("\n	FROM USR																					         ");
						insert2Query.append("\n	WHERE USER_ID IN (" + subCd + ")															         ");
						insert2Query.append("\n	AND DEPT_ID NOT IN (SELECT TGTDEPTCD														         ");
						insert2Query.append("\n						FROM TGTDEPT															         ");
						insert2Query.append("\n						WHERE SUBMITSTATE > 1								                             ");
						insert2Query.append("\n						AND SYSDOCNO = ?														         ");
						insert2Query.append("\n					    AND PREDEPTCD = ?)															     ");
					} else {
						insert2Query.append("\n	SELECT ? AS SYSDOCNO, DEPT_ID AS TGTDEPTCD, DEPT_NAME AS TGTDEPTNM, '01' AS SUBMITSTATE, '1', ?,     ");
						insert2Query.append("\n		   TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS'), ?											         ");			
						insert2Query.append("\n	FROM DEPT																					         ");
						insert2Query.append("\n	WHERE DEPT_ID IN (" + subCd + ")															         ");
						insert2Query.append("\n	UNION																						         ");
						insert2Query.append("\n	SELECT ? AS SYSDOCNO, DEPT_ID AS TGTDEPTCD, DEPT_NAME AS TGTDEPTNM, '01' AS SUBMITSTATE, '1', ?,     ");
						insert2Query.append("\n		   TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS'), ?											         ");			
						insert2Query.append("\n	FROM USR																					         ");
						insert2Query.append("\n	WHERE USER_ID IN (" + subCd + ")															         ");
					}
				}
					insert2Query.append("\n	)																								         ");	
			}
			
			insert3Query.append("\n	INSERT INTO TGTLIST(SYSDOCNO, SEQ, GRPCD, GRPNM, GRPGBN, PREDEPTCD) ");
			insert3Query.append("\n	SELECT ? AS SYSDOCNO, ?, DEPT_ID AS GRPCD, DEPT_NAME AS GRPNM, ?, ? ");
			insert3Query.append("\n	FROM USR														    ");
			insert3Query.append("\n	WHERE USER_ID LIKE ?											    ");
			insert3Query.append("\n	AND (?, DEPT_ID) NOT IN (SELECT SYSDOCNO, GRPCD					    ");
			insert3Query.append("\n						     FROM TGTLIST					            ");
			insert3Query.append("\n							 WHERE GRPGBN = '2'				            ");
			insert3Query.append("\n							 AND SYSDOCNO = ?				            ");
			insert3Query.append("\n							 AND PREDEPTCD = ?)				            ");
		}
		
		update4Query.append("\n	UPDATE DOCMST																	  ");
		update4Query.append("\n	SET TGTDEPTNM = ?, UPTDT = TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS'), UPTUSRID = ? ");
		update4Query.append("\n	WHERE SYSDOCNO = ?																  ");
		
		try{
			con = ConnectionManager.getConnection(false);
			
			String rootTgtdeptcd = CommTreatManager.instance().getPreDeptcd(con, sysdocno, deptid, false);
			
			/*입력담당자삭제*/
			pstmt = con.prepareStatement(delete1Query.toString());	
		    
		    if ( sysdocno == 0 ) {
				pstmt.setString(++bindPos, sessionId);
				pstmt.setString(++bindPos, sessionId);
				pstmt.setString(++bindPos , rootTgtdeptcd);
		    } else {
				pstmt.setInt(++bindPos , sysdocno);
				pstmt.setInt(++bindPos , sysdocno);
				pstmt.setString(++bindPos , rootTgtdeptcd);
		    }
			
			if ( pstmt.executeUpdate() >= 0 ) {
				cnt++;
			}
			
			bindPos = 0;
			ConnectionManager.close(pstmt);
			
			/*기존등록된 제출부서 그룹 및 부서목록LIST삭제*/
			pstmt = con.prepareStatement(delete2Query.toString());	
		    
		    if ( sysdocno == 0 ) {
				pstmt.setString(++bindPos, sessionId);
				pstmt.setString(++bindPos , rootTgtdeptcd);
		    } else {
				pstmt.setInt(++bindPos , sysdocno);
				pstmt.setString(++bindPos , rootTgtdeptcd);
				pstmt.setInt(++bindPos , sysdocno);
				pstmt.setString(++bindPos , rootTgtdeptcd);
		    }
			
			if ( pstmt.executeUpdate() >= 0 ) {
				cnt++;
			}
			
			bindPos = 0;
			ConnectionManager.close(pstmt);
			
			/*기존등록된 제출부서 목록LIST삭제*/
			pstmt = con.prepareStatement(delete3Query.toString());	
		    
		    if ( sysdocno == 0 ) {
				pstmt.setString(++bindPos, sessionId);
				pstmt.setString(++bindPos , rootTgtdeptcd);
		    } else {
				pstmt.setInt(++bindPos , sysdocno);
				pstmt.setString(++bindPos , rootTgtdeptcd);
		    }
			
			if ( pstmt.executeUpdate() >= 0 ) {
				cnt++;
			}
			
			bindPos = 0;
			ConnectionManager.close(pstmt);

			/*제출부서 그룹 및 부서목록LIST등록*/
			if ( deptList.size() > 0 ) {				
				maxSeq = getMaxSEQ(sysdocno);
				
				if ( sysdocno == 0 ) {
					commsubdeptBean dept = null;
					pstmt = con.prepareStatement(insert1Query.toString());
					for ( int i = 0; i < deptList.size(); i++ ) {
						dept = (commsubdeptBean)deptList.get(i);
						if ( dept.getGrpGbn().equals("3") == true ) continue;
						
						pstmt.setString(++bindPos, sessionId);
						pstmt.setInt(++bindPos, ++maxSeq);
						pstmt.setString(++bindPos, dept.getCode());
						pstmt.setString(++bindPos, dept.getName());
						pstmt.setString(++bindPos, dept.getGrpGbn());
						pstmt.setString(++bindPos , rootTgtdeptcd);
						pstmt.setString(++bindPos, sessionId);
						pstmt.setString(++bindPos, dept.getCode());
						pstmt.setString(++bindPos , rootTgtdeptcd);
						
						pstmt.addBatch();
						bindPos = 0;
					}
					
					ret = pstmt.executeBatch();
					cnt += ret.length;
					
					ConnectionManager.close(pstmt);
				} else {
					commsubdeptBean dept = null;
					for ( int i = 0; i < deptList.size(); i++ ) {
						dept = (commsubdeptBean)deptList.get(i);
						if ( dept.getGrpGbn().equals("3") == true ) continue;
						
						pstmt = con.prepareStatement(insert1Query.toString());
						pstmt.setInt(++bindPos, sysdocno);
						pstmt.setInt(++bindPos, ++maxSeq);
						pstmt.setString(++bindPos, dept.getCode());
						pstmt.setString(++bindPos, dept.getName());
						pstmt.setString(++bindPos, dept.getGrpGbn());
						pstmt.setString(++bindPos , rootTgtdeptcd);
						pstmt.setInt(++bindPos, sysdocno);
						pstmt.setString(++bindPos, dept.getCode());
						pstmt.setString(++bindPos , rootTgtdeptcd);
						
						cnt += pstmt.executeUpdate();
						
						bindPos = 0;
						ConnectionManager.close(pstmt);
					}
				}
				
				if ( "".equals(grpCd) == false || "".equals(subCd) == false ) {
					/*제출부서 목록LIST등록*/
					pstmt = con.prepareStatement(insert2Query.toString());
					
					if ( sysdocno == 0 ) {
						if ( !grpCd.equals("") ) {
							pstmt.setString(++bindPos, sessionId);
							pstmt.setString(++bindPos, rootTgtdeptcd);
							pstmt.setString(++bindPos, userid);
							pstmt.setString(++bindPos, sessionId);
							pstmt.setString(++bindPos, rootTgtdeptcd);
							pstmt.setString(++bindPos, userid);
						}
						if ( !subCd.equals("") ) {
							pstmt.setString(++bindPos, sessionId);
							pstmt.setString(++bindPos, rootTgtdeptcd);
							pstmt.setString(++bindPos, userid);
							pstmt.setString(++bindPos, sessionId);
							pstmt.setString(++bindPos, rootTgtdeptcd);
							pstmt.setString(++bindPos, userid);
						}
					} else {
						if ( !grpCd.equals("") ) {
							if ( Integer.parseInt(docState, 10) >= 4 ) {
								pstmt.setInt(++bindPos, sysdocno);
								pstmt.setString(++bindPos, rootTgtdeptcd);
								pstmt.setString(++bindPos, userid);
								pstmt.setInt(++bindPos, sysdocno);
								pstmt.setString(++bindPos, rootTgtdeptcd);
								pstmt.setInt(++bindPos, sysdocno);
								pstmt.setString(++bindPos, rootTgtdeptcd);
								pstmt.setString(++bindPos, userid);
								pstmt.setInt(++bindPos, sysdocno);
								pstmt.setString(++bindPos, rootTgtdeptcd);
							} else {
								pstmt.setInt(++bindPos, sysdocno);
								pstmt.setString(++bindPos, rootTgtdeptcd);
								pstmt.setString(++bindPos, userid);
								pstmt.setInt(++bindPos, sysdocno);
								pstmt.setString(++bindPos, rootTgtdeptcd);
								pstmt.setString(++bindPos, userid);
							}
						}
						if ( !subCd.equals("") ) {
							if ( Integer.parseInt(docState, 10) >= 4 ) {
								pstmt.setInt(++bindPos, sysdocno);
								pstmt.setString(++bindPos, rootTgtdeptcd);
								pstmt.setString(++bindPos, userid);
								pstmt.setInt(++bindPos, sysdocno);
								pstmt.setString(++bindPos, rootTgtdeptcd);
								pstmt.setInt(++bindPos, sysdocno);
								pstmt.setString(++bindPos, rootTgtdeptcd);
								pstmt.setString(++bindPos, userid);
								pstmt.setInt(++bindPos, sysdocno);
								pstmt.setString(++bindPos, rootTgtdeptcd);
							} else {
								pstmt.setInt(++bindPos, sysdocno);
								pstmt.setString(++bindPos, rootTgtdeptcd);
								pstmt.setString(++bindPos, userid);
								pstmt.setInt(++bindPos, sysdocno);
								pstmt.setString(++bindPos, rootTgtdeptcd);
								pstmt.setString(++bindPos, userid);
							}
						}
					}
					
					cnt += pstmt.executeUpdate();
					
					bindPos = 0;
					ConnectionManager.close(pstmt);
				}
				
				/*제출부서 그룹 및 부서목록LIST등록*/
				if ( sysdocno == 0 ) {
					commsubdeptBean dept = null;
					
					for ( int i = 0; i < deptList.size(); i++ ) {
						dept = (commsubdeptBean)deptList.get(i);
						if ( dept.getGrpGbn().equals("1") == true ) {
							List grpList = GroupManager.instance().getGrpDtlList(Integer.parseInt("0" + dept.getCode()), "1", userid);
							for ( int j = 0; j < grpList.size(); j++ ) {
								GroupBean grpBean = (GroupBean)grpList.get(j);
								
								pstmt = con.prepareStatement(insert3Query.toString());
								pstmt.setString(++bindPos, sessionId);
								pstmt.setInt(++bindPos, ++maxSeq);
								pstmt.setString(++bindPos, "2");
								pstmt.setString(++bindPos, rootTgtdeptcd);
								pstmt.setString(++bindPos, grpBean.getCode());
								pstmt.setString(++bindPos, sessionId);
								pstmt.setString(++bindPos, sessionId);
								pstmt.setString(++bindPos, rootTgtdeptcd);
								cnt += pstmt.executeUpdate();
								
								bindPos = 0;
								ConnectionManager.close(pstmt);
								
								ArrayList userList = new ArrayList();
								commapprovalBean bean = new commapprovalBean();
								bean.setUserId(grpBean.getCode());
								bean.setUserName(grpBean.getName());						
								userList.add(bean);
								
								String appusrnm = UserManager.instance().getUserInfo(userid).getUser_name();
								String deptcd = UserManager.instance().getUserInfo(grpBean.getCode()).getDept_id();
								cnt += commapprovalManager.instance().inputusrInsert(con, 0, sessionId, userList, userid, deptcd);	//입력담당자 지정
								cnt += DeliveryManager.instance().deliveryProcess(con, "02", appusrnm, userid, 0, sessionId, deptcd);	//배부처리
								
								InputingManager inputmgr = InputingManager.instance();
								String[] inputInfo = inputmgr.isTgtdeptInputComplete(con, sysdocno, deptcd);
								if(inputInfo != null && inputInfo[0].equals("-1") == false) {	//제출부서입력완료 완료
									cnt += inputmgr.doLastInputCompleteCheck(con, Integer.parseInt(inputInfo[0], 10), inputInfo[2], inputInfo[1]);
								}
							}
						} else if ( dept.getGrpGbn().equals("3") == true ) {
							pstmt = con.prepareStatement(insert3Query.toString());
							pstmt.setString(++bindPos, sessionId);
							pstmt.setInt(++bindPos, ++maxSeq);
							pstmt.setString(++bindPos, "2");
							pstmt.setString(++bindPos, rootTgtdeptcd);
							pstmt.setString(++bindPos, dept.getCode());
							pstmt.setString(++bindPos, sessionId);
							pstmt.setString(++bindPos, sessionId);
							pstmt.setString(++bindPos, rootTgtdeptcd);
							cnt += pstmt.executeUpdate();
							
							bindPos = 0;
							ConnectionManager.close(pstmt);
							
							ArrayList userList = new ArrayList();
							commapprovalBean bean = new commapprovalBean();
							bean.setUserId(dept.getCode());
							bean.setUserName(dept.getName());						
							userList.add(bean);
							
							String appusrnm = UserManager.instance().getUserInfo(userid).getUser_name();
							String deptcd = UserManager.instance().getUserInfo(dept.getCode()).getDept_id();
							cnt += commapprovalManager.instance().inputusrInsert(con, 0, sessionId, userList, userid, deptcd);	//입력담당자 지정
							cnt += DeliveryManager.instance().deliveryProcess(con, "02", appusrnm, userid, 0, sessionId, deptcd);	//배부처리
							
							InputingManager inputmgr = InputingManager.instance();
							String[] inputInfo = inputmgr.isTgtdeptInputComplete(con, sysdocno, deptcd);
							if(inputInfo != null && inputInfo[0].equals("-1") == false) {	//제출부서입력완료 완료
								cnt += inputmgr.doLastInputCompleteCheck(con, Integer.parseInt(inputInfo[0], 10), inputInfo[2], inputInfo[1]);
							}
						}
					}
				} else {
					commsubdeptBean dept = null;
					
					for ( int i = 0; i < deptList.size(); i++ ) {
						dept = (commsubdeptBean)deptList.get(i);
						if ( dept.getGrpGbn().equals("1") == true ) {
							List grpList = GroupManager.instance().getGrpDtlList(Integer.parseInt("0" + dept.getCode()), "1", userid);
							for ( int j = 0; j < grpList.size(); j++ ) {
								GroupBean grpBean = (GroupBean)grpList.get(j);
								
								pstmt = con.prepareStatement(insert3Query.toString());
								pstmt.setInt(++bindPos, sysdocno);
								pstmt.setInt(++bindPos, ++maxSeq);
								pstmt.setString(++bindPos, "2");
								pstmt.setString(++bindPos, rootTgtdeptcd);
								pstmt.setString(++bindPos, grpBean.getCode());
								pstmt.setInt(++bindPos, sysdocno);
								pstmt.setInt(++bindPos, sysdocno);
								pstmt.setString(++bindPos, rootTgtdeptcd);
								cnt += pstmt.executeUpdate();
								
								bindPos = 0;
								ConnectionManager.close(pstmt);
								
								ArrayList userList = new ArrayList();
								commapprovalBean bean = new commapprovalBean();
								bean.setUserId(grpBean.getCode());
								bean.setUserName(grpBean.getName());						
								userList.add(bean);
								
								String appusrnm = UserManager.instance().getUserInfo(userid).getUser_name();
								String deptcd = UserManager.instance().getUserInfo(grpBean.getCode()).getDept_id();
								cnt += commapprovalManager.instance().inputusrInsert(con, sysdocno, "", userList, userid, deptcd);	//입력담당자 지정
								cnt += DeliveryManager.instance().deliveryProcess(con, "02", appusrnm, userid, sysdocno, "", deptcd);	//배부처리
								
								InputingManager inputmgr = InputingManager.instance();
								String[] inputInfo = inputmgr.isTgtdeptInputComplete(con, sysdocno, deptcd);
								if(inputInfo != null && inputInfo[0].equals("-1") == false) {	//제출부서입력완료 완료
									cnt += inputmgr.doLastInputCompleteCheck(con, Integer.parseInt(inputInfo[0], 10), inputInfo[2], inputInfo[1]);
								}
							}
						} else if ( dept.getGrpGbn().equals("3") == true ) {
							pstmt = con.prepareStatement(insert3Query.toString());
							pstmt.setInt(++bindPos, sysdocno);
							pstmt.setInt(++bindPos, ++maxSeq);
							pstmt.setString(++bindPos, "2");
							pstmt.setString(++bindPos, rootTgtdeptcd);
							pstmt.setString(++bindPos, dept.getCode());
							pstmt.setInt(++bindPos, sysdocno);
							pstmt.setInt(++bindPos, sysdocno);
							pstmt.setString(++bindPos, rootTgtdeptcd);
							cnt += pstmt.executeUpdate();
							
							bindPos = 0;
							ConnectionManager.close(pstmt);
							
							ArrayList userList = new ArrayList();
							commapprovalBean bean = new commapprovalBean();
							bean.setUserId(dept.getCode());
							bean.setUserName(dept.getName());						
							userList.add(bean);
							
							String appusrnm = UserManager.instance().getUserInfo(userid).getUser_name();
							String deptcd = UserManager.instance().getUserInfo(dept.getCode()).getDept_id();
							cnt += commapprovalManager.instance().inputusrInsert(con, sysdocno, "", userList, userid, deptcd);	//입력담당자 지정
							cnt += DeliveryManager.instance().deliveryProcess(con, "02", appusrnm, userid, sysdocno, "", deptcd);	//배부처리
							
							InputingManager inputmgr = InputingManager.instance();
							String[] inputInfo = inputmgr.isTgtdeptInputComplete(con, sysdocno, deptcd);
							if(inputInfo != null && inputInfo[0].equals("-1") == false) {	//제출부서입력완료 완료
								cnt += inputmgr.doLastInputCompleteCheck(con, Integer.parseInt(inputInfo[0], 10), inputInfo[2], inputInfo[1]);
							}
						}
					}
				}
			}
			
			/*특정부서가 취합대상인 경우 해당부서의 상위부서의 사용자는 대상에서 삭제*/
			pstmt = con.prepareStatement(delete4Query.toString());
			if ( sysdocno == 0 ) {
				pstmt.setString(++bindPos, sessionId);
				pstmt.setString(++bindPos, sessionId);
				pstmt.setString(++bindPos , rootTgtdeptcd);
		    } else {
				pstmt.setInt(++bindPos , sysdocno);
				pstmt.setInt(++bindPos , sysdocno);
				pstmt.setString(++bindPos , rootTgtdeptcd);
		    }
			
			//국단위 취합이 가능한 경우 사용하면 안됨
			//if ( pstmt.executeUpdate() >= 0 ) {
			//	cnt++;
			//}
			
			bindPos = 0;
			ConnectionManager.close(pstmt);
			
			/*특정부서가 취합대상인 경우 해당부서의 상위부서는 대상에서 삭제1*/
			pstmt = con.prepareStatement(delete5Query.toString());
			if ( sysdocno == 0 ) {
				pstmt.setString(++bindPos, sessionId);
				pstmt.setString(++bindPos , rootTgtdeptcd);
				pstmt.setString(++bindPos, sessionId);
				pstmt.setString(++bindPos , rootTgtdeptcd);
				pstmt.setString(++bindPos, sessionId);
				pstmt.setString(++bindPos , rootTgtdeptcd);
		    } else {
				pstmt.setInt(++bindPos , sysdocno);
				pstmt.setString(++bindPos , rootTgtdeptcd);
				pstmt.setInt(++bindPos , sysdocno);
				pstmt.setString(++bindPos , rootTgtdeptcd);
				pstmt.setInt(++bindPos , sysdocno);
				pstmt.setString(++bindPos , rootTgtdeptcd);
		    }
			
			//국단위 취합이 가능한 경우 사용하면 안됨
			//if ( pstmt.executeUpdate() >= 0 ) {
			//	cnt++;
			//}
			
			bindPos = 0;
			ConnectionManager.close(pstmt);
			
			/*특정부서가 취합대상인 경우 해당부서의 상위부서는 대상에서 삭제2*/
			pstmt = con.prepareStatement(delete6Query.toString());
			if ( sysdocno == 0 ) {
				pstmt.setString(++bindPos, sessionId);
				pstmt.setString(++bindPos , rootTgtdeptcd);
				pstmt.setString(++bindPos, sessionId);
				pstmt.setString(++bindPos , rootTgtdeptcd);
		    } else {
				pstmt.setInt(++bindPos , sysdocno);
				pstmt.setString(++bindPos , rootTgtdeptcd);
				pstmt.setInt(++bindPos , sysdocno);
				pstmt.setString(++bindPos , rootTgtdeptcd);
		    }
			
			//국단위 취합이 가능한 경우 사용하면 안됨
			//if ( pstmt.executeUpdate() >= 0 ) {
			//	cnt++;
			//}
			
			bindPos = 0;
			ConnectionManager.close(pstmt);
			
			/*입력담당자 없는 부서를 배부대기 상태로 변경*/
			pstmt = con.prepareStatement(update1Query.toString());
			if ( sysdocno == 0 ) {
				pstmt.setString(++bindPos, sessionId);
				pstmt.setString(++bindPos, sessionId);
		    } else {
				pstmt.setInt(++bindPos , sysdocno);
				pstmt.setInt(++bindPos , sysdocno);
		    }
			
			if ( pstmt.executeUpdate() >= 0 ) {
				cnt++;
			}
			
			bindPos = 0;
			ConnectionManager.close(pstmt);

			
			/*입력담당자가 있으나 배부대기인 부서 배부완료로 변경*/
			String appusrnm = UserManager.instance().getUserInfo(userid).getUser_name();
			pstmt = con.prepareStatement(update2Query.toString());
			if ( sysdocno == 0 ) {
				pstmt.setString(++bindPos, appusrnm);
				pstmt.setString(++bindPos, sessionId);
				pstmt.setString(++bindPos, sessionId);
		    } else {
				pstmt.setString(++bindPos, appusrnm);
				pstmt.setInt(++bindPos , sysdocno);
				pstmt.setInt(++bindPos , sysdocno);
		    }
			
			if ( pstmt.executeUpdate() >= 0 ) {
				cnt++;
			}

			bindPos = 0;
			ConnectionManager.close(pstmt);
			
			/*하위부서는 있으나 입력담당자가 없는 경우도 배부완료로 변경*/
			pstmt = con.prepareStatement(update3Query.toString());
			if ( sysdocno == 0 ) {
				pstmt.setString(++bindPos, sessionId);
				pstmt.setInt(++bindPos , sysdocno);
		    } else {;
				pstmt.setInt(++bindPos , sysdocno);
				pstmt.setInt(++bindPos , sysdocno);
		    }
			
			if ( pstmt.executeUpdate() >= 0 ) {
				cnt++;
			}
			
			bindPos = 0;
			ConnectionManager.close(pstmt);
			
			/*문서MASTER의 제출부서명 등록*/
			if ( sysdocno != 0 && CommTreatManager.instance().getTargetDeptLevel(con, sysdocno, rootTgtdeptcd) == 1 ) {
				pstmt = con.prepareStatement(update4Query.toString());
				String tgtdeptnm = "";
				commsubdeptBean dept = null;
				for ( int i = 0; i < deptList.size(); i++ ) {
					dept = (commsubdeptBean)deptList.get(i);
					
					if ( i == 0 ) {
						tgtdeptnm = tgtdeptnm + dept.getName() ;
					} else {
						tgtdeptnm = tgtdeptnm + "," + dept.getName() ;
					}
				}
				
				pstmt.setString(++bindPos, tgtdeptnm);
				pstmt.setString(++bindPos, userid);
				pstmt.setInt(++bindPos, sysdocno);
				
				if(pstmt.executeUpdate()>0){
					cnt++;
				}
				
				bindPos = 0;
				ConnectionManager.close(pstmt);
			}
			
			if ( Integer.parseInt(docState) >= 4 ) {
				DeliveryManager delivMgr = DeliveryManager.instance();
				if ( delivMgr.IsLastDeliveryDept(con, sysdocno, deptid) ) {
					delivMgr.updateDocState(con, sysdocno, "05", userid);
				} else {
					delivMgr.updateDocState(con, sysdocno, "04", userid);
				}
			}
			
			con.commit();
		} catch(Exception e){
			con.rollback();
			ConnectionManager.close(con,pstmt);
			throw e;
		} finally {
			con.setAutoCommit(true);
			ConnectionManager.close(con, pstmt);
		}
		return cnt;
	}
	
	/** (max+1)코드 값 가져오기	
	 * @throws Exception */
	public int getMaxSEQ(int sysdocno) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int  maxSeq = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			if(sysdocno == 0){
				selectQuery.append("SELECT NVL(MAX(SEQ),0) 		");
				selectQuery.append("  FROM TGTLIST_TEMP 		");
			}else{
				selectQuery.append("SELECT NVL(MAX(SEQ),0) 		");
				selectQuery.append("  FROM TGTLIST 				");	
			}
			
			conn = ConnectionManager.getConnection();
			pstmt =	conn.prepareStatement(selectQuery.toString());
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				maxSeq = rs.getInt(1);
			}

		 } catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return maxSeq;
	}
	
	public String selectDeptXml(String deptId) throws Exception {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		commsubdeptBean bean = new commsubdeptBean();
		int bindPos = 0;
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("\n SELECT T1.DEPT_ID,                                           ");
		selectQuery.append("\n        T1.DEPT_NAME,                                         ");
		selectQuery.append("\n        T1.UPPER_DEPT_ID,                                    	");
		selectQuery.append("\n        T2.USER_ID,                                       	");
		selectQuery.append("\n        T2.USER_NAME,                                         ");
		selectQuery.append("\n        LEVEL AS DEPT_LEVEL,                                  ");                                           
		selectQuery.append("\n   FROM DEPT T1, USR T2  										");
		selectQuery.append("\n  WHERE T1.DEPT_ID = T2.DEPT_ID(+)                            ");
		selectQuery.append("\n    AND T1.DEPT_ID = :DEPT_ID                               	");

		
		try{
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(++bindPos, deptId);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				
				bean.setCode(rs.getString("DEPT_ID"));
				bean.setName(rs.getString("DEPT_NAME"));
				bean.setUpperDeptId(rs.getString("UPPER_DEPT_ID"));
				bean.setDeptLevel(rs.getInt("DEPT_LEVEL"));				
			}
		}catch(Exception e){
			logger.error("ERROR",e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		}finally{
			ConnectionManager.close(con, pstmt, rs);
		}
		
		return getDept(bean);
	}
	
	/**
	 * 메뉴 목록에 대한 XML 파일을 생성한다.
	 * @param deptList
	 * @return
	 */
	private String getDept(commsubdeptBean bean) {

			StringBuffer deptXML = new StringBuffer();
			deptXML.append("\n<userdata name=\"deptId\">" + bean.getCode() + "</userdata>");
			deptXML.append("\n<userdata name=\"deptName\">" + bean.getName() + "</userdata>");
			deptXML.append("\n<userdata name=\"upperDeptId\">" + bean.getUpperDeptId() + "</userdata>");
		
			// 최초 엘레멘트 open에 대해 엘레멘트 닫기
			return (deptXML.toString());
	}
	
	/** 
	 * 해당프로세서별 체크사항
	 * 
	 * @param sysdocno : 시스템문서번호
	 * @param deptcd : 부서코드
	 * @param usrid : 사용자아이디
	 * @param type : 프로세서체크구분
	 * 
	 * @return String : 프로세서체크
	 * @throws Exception 
	 */
	public String getProcessChk(int sysdocno, String deptcd, String usrid, String gbn, String type) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		String result = "";
		
		StringBuffer selectQuery = new StringBuffer();
		
		if(type.equals("1")){
			selectQuery.append("\n	SELECT DOCSTATE AS PROCESSCHK		");
			selectQuery.append("\n	  FROM DOCMST A 					");
			selectQuery.append("\n 	 WHERE A.SYSDOCNO   = ? 			");	
		}else if(type.equals("2")){
			selectQuery.append("\n	SELECT SUBMITSTATE AS PROCESSCHK	");
			selectQuery.append("\n	  FROM TGTDEPT A, INPUTUSR B 		");
			selectQuery.append("\n 	 WHERE A.SYSDOCNO  = B.SYSDOCNO 	");
			selectQuery.append("\n     AND A.TGTDEPTCD = B.TGTDEPT 		");
			selectQuery.append("\n     AND A.SYSDOCNO = ? 				");
			selectQuery.append("\n     AND B.INPUTUSRID = ? 			");
		}else if(type.equals("3")){
			selectQuery.append("\n	SELECT SUBMITSTATE AS PROCESSCHK	");
			selectQuery.append("\n	  FROM TGTDEPT A             		");
			selectQuery.append("\n 	 WHERE A.SYSDOCNO = ? 				");
			selectQuery.append("\n     AND A.TGTDEPTCD = ? 		    	");
		}else{
			if("1".equals(gbn)){
				selectQuery.append("\n SELECT '1' AS PROCESSCHK 			");
				selectQuery.append("\n   FROM DOCMST A, SANCCOL B			");
				selectQuery.append("\n  WHERE A.SYSDOCNO = B.SYSDOCNO 		");
				selectQuery.append("\n    AND A.SYSDOCNO =  ? 				");
				selectQuery.append("\n    AND B.SANCUSRID = ? 				");
			}else{
				selectQuery.append("\n SELECT '2' AS PROCESSCHK 			");
				selectQuery.append("\n   FROM TGTDEPT A, SANCTGT B			");
				selectQuery.append("\n  WHERE A.SYSDOCNO = B.SYSDOCNO 		");
				selectQuery.append("\n    AND A.TGTDEPTCD = B.TGTDEPTCD 	");
				selectQuery.append("\n    AND A.SYSDOCNO =  ? 				");
				selectQuery.append("\n    AND B.SANCUSRID = ? 				");	
			}
		}
		
		try {
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			
			if(type.equals("1")){
				pstmt.setInt(1, sysdocno);
			}else if(type.equals("2")){
				pstmt.setInt(1, sysdocno);
				pstmt.setString(2, usrid);
			}else if(type.equals("3")){
				pstmt.setInt(1, sysdocno);
				pstmt.setString(2, deptcd);
			}else{
				if("1".equals(gbn)){
					pstmt.setInt(1, sysdocno);
					pstmt.setString(2, usrid);
				}else{
					pstmt.setInt(1, sysdocno);
					pstmt.setString(2, usrid);	
				}
							
			}

			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				result = rs.getString(1);
			}

		 } catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return result;
	}
	
	public String getUserList(String deptCode, String userName) throws Exception {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList userList = new ArrayList();
		String userListXML = "";
		int bindPos = 0;
		
		StringBuffer selectQuery = new StringBuffer();
		selectQuery.append(" SELECT USER_ID, USER_NAME, DEPT_ID, DEPT_NAME, POSITION_NAME  	\n");
		selectQuery.append(" FROM USR                                                    	\n");
		selectQuery.append(" WHERE 1=1		                         						\n");
		
		if (!"".equals(deptCode))
			selectQuery.append("    AND DEPT_ID = ?                                   		\n");
		
		if (!"".equals(userName))
			selectQuery.append("    AND USER_NAME LIKE '%' || ? || '%'                  	\n");
		
		selectQuery.append(" ORDER BY GRADE_ID                 								\n");
		
		try{
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if (!"".equals(deptCode))
				pstmt.setString(++bindPos, deptCode);
				
			if (!"".equals(userName))
				pstmt.setString(++bindPos, userName);
			
			rs = pstmt.executeQuery();
			
			commdeptuserBean bean = null;
			while(rs.next()){
				bean = new commdeptuserBean();
				
				bean.setUserId(rs.getString("USER_ID"));
				bean.setUserName(rs.getString("USER_NAME"));
				bean.setDeptId(rs.getString("DEPT_ID"));
				bean.setDeptName(rs.getString("DEPT_NAME"));

				userList.add(bean);
				
				bean = null;				
			}
			
			userListXML = getUserListXML(userList);
		}finally{
			ConnectionManager.close(con, pstmt, rs);
		}
		return userListXML;
	}	
	
	private String getUserListXML(ArrayList userList) {

		StringBuffer userListXML = new StringBuffer();

		if (userList.size() > 0){
			
			commdeptuserBean bean = null;
			for (int i = 0 ; i < userList.size() ; i++){
				bean = (commdeptuserBean)userList.get(i);
				userListXML.append("\n	<data id=\"").append(bean.getUserId()).append("\">");				
				userListXML.append("\n		<userdata id=\"userId\">").append(bean.getUserId()).append("</userdata>");
				userListXML.append("\n		<userdata id=\"userName\">").append(bean.getUserName()).append("</userdata>");
				userListXML.append("\n		<userdata id=\"deptCode\">").append(bean.getDeptId()).append("</userdata>");
				userListXML.append("\n		<userdata id=\"deptName\">").append(bean.getDeptName()).append("</userdata>");
				userListXML.append("\n	</data>");
				bean = null;
			}			
		}
		return (userListXML.toString());
	}	
	
	public String getCommrchdeptView(int rchno, String sessionId, String userid) throws Exception {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList deptView = new ArrayList();
		String deptViewXML = "";
		int bindPos = 0;
		
		StringBuffer selectQuery = new StringBuffer();
		
		if(rchno == 0){
			selectQuery.append("\n SELECT T1.SESSIONID,				");
			selectQuery.append("\n        T1.SEQ,					");
			selectQuery.append("\n        T1.GRPCD,					");                                         
			selectQuery.append("\n        T1.GRPNM,					");
			selectQuery.append("\n		  T1.GRPGBN					");
			selectQuery.append("\n   FROM RCHDEPTLIST_TEMP T1		");
			selectQuery.append("\n  WHERE T1.SESSIONID LIKE ?			");
		}else{
			selectQuery.append("\n SELECT T1.RCHNO,				    ");
			selectQuery.append("\n        T1.SEQ,					");
			selectQuery.append("\n        T1.GRPCD,					");                                         
			selectQuery.append("\n        T1.GRPNM,					");
			selectQuery.append("\n		  T1.GRPGBN					");
			selectQuery.append("\n   FROM RCHDEPTLIST T1			");
			selectQuery.append("\n  WHERE T1.RCHNO = ?			    ");
		}
		
		try{
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if(rchno ==0 ){
				pstmt.setString(++bindPos, sessionId);
			}else{
				pstmt.setInt(++bindPos, rchno);
			}
			
			rs = pstmt.executeQuery();
			
			commsubdeptBean bean = null;
			while(rs.next()){
				bean = new commsubdeptBean();
				
				bean.setCode(rs.getString("GRPCD"));
				bean.setName(rs.getString("GRPNM"));
				bean.setGrpGbn(rs.getString("GRPGBN"));

				deptView.add(bean);
				
				bean = null;				
			}
			
			deptViewXML = getDeptViewXML(deptView);
		}finally{
			ConnectionManager.close(con, pstmt, rs);
		}
		return deptViewXML;
	}	
	
	public int commrchdeptInsert( int rchno, ArrayList deptList, String sessionId, String usrid, String grpCd, String subCd ) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		int bindPos = 0;
		int cnt = 0;
		int[] ret = null;
		
		int maxSeq = 0;
		
		StringBuffer insert1Query = new StringBuffer();
		StringBuffer insert2Query = new StringBuffer();
		StringBuffer delete1Query = new StringBuffer();
		StringBuffer delete2Query = new StringBuffer();

		if ( rchno == 0 ) {
			delete1Query.append("\n	DELETE FROM RCHDEPTLIST_TEMP	");
			delete1Query.append("\n	WHERE SESSIONID LIKE ?				");
			
			delete2Query.append("\n DELETE FROM RCHDEPT_TEMP		");
			delete2Query.append("\n	WHERE SESSIONID LIKE ?				");
		} else {
			delete1Query.append("\n	DELETE FROM RCHDEPTLIST			");
			delete1Query.append("\n	WHERE SYSDOCNO = ?				");	
			
			delete2Query.append("\n DELETE FROM TGTDEPT				");
			delete2Query.append("\n	WHERE SYSDOCNO = ?				");
		}
		
		if ( rchno == 0 ) {
			insert1Query.append("\n	INSERT INTO RCHDEPTLIST_TEMP(SESSIONID, SEQ, GRPCD, GRPNM, GRPGBN)								");
			insert1Query.append("\n	VALUES (?, ?, ?, ?, ?)																			");
		
			if ( "".equals(grpCd) == false || "".equals(subCd) == false ) {
				insert2Query.append("\n	INSERT INTO RCHDEPT_TEMP(SESSIONID, TGTCODE, TGTNAME, TGTGBN)								");
				insert2Query.append("\n	(																							"); 
				if ( !grpCd.equals("") ) {
					insert2Query.append("\n	SELECT DISTINCT ? AS SESSIONID, CODE AS TGTCODE, NAME AS TGTNAME, '1' AS TGTGBN 		");
					insert2Query.append("\n	FROM GRPLISTDTL																			");
					insert2Query.append("\n	WHERE GRPLISTCD IN (" + grpCd + ")														");
					insert2Query.append("\n	AND CODEGBN = '0'																		");
					insert2Query.append("\n	UNION																					");
					//설문조사대상으로 사용자 지정시 소속 부서는 등록할 필요없음(취합대상지정과 다름)
					//insert2Query.append("\n	SELECT DISTINCT ? AS SESSIONID, DEPT_ID AS TGTCODE, DEPT_NAME AS TGTNAME, '1' AS TGTGBN	");
					insert2Query.append("\n	SELECT DISTINCT ? AS SESSIONID, USER_ID AS TGTCODE, USER_NAME AS TGTNAME, '2' AS TGTGBN	");
					insert2Query.append("\n	FROM USR																				");
					insert2Query.append("\n	WHERE USER_ID IN (SELECT CODE															");
					insert2Query.append("\n					  FROM GRPLISTDTL														");
					insert2Query.append("\n					  WHERE GRPLISTCD IN (" + grpCd + ")									");
					insert2Query.append("\n					  AND CODEGBN = '1')													");
				}
				if ( !grpCd.equals("") && !subCd.equals("") ) {
					insert2Query.append("\n	UNION																					");
				}
				if ( !subCd.equals("") ) {
					insert2Query.append("\n	SELECT ? AS SESSIONID, DEPT_ID AS TGTCODE, DEPT_NAME AS TGTNAME, '1' AS TGTGBN			");
					insert2Query.append("\n	FROM DEPT																				");
					insert2Query.append("\n	WHERE DEPT_ID IN (" + subCd + ")														");
					insert2Query.append("\n	UNION																					");
					insert2Query.append("\n	SELECT ? AS SESSIONID, USER_ID AS TGTCODE, USER_NAME AS TGTNAME, '2' AS TGTGBN		 	");
					insert2Query.append("\n	FROM USR																				");
					insert2Query.append("\n	WHERE USER_ID IN (" + subCd + ")														");
				}
				insert2Query.append("\n	)																							");
			}
		} else {
			insert1Query.append("\n	INSERT INTO RCHDEPTLIST(RCHNO, SEQ, GRPCD, GRPNM, GRPGBN)										");
			insert1Query.append("\n	VALUES (?, ?, ?, ?, ?)																			");
			
			if ( "".equals(grpCd) == false || "".equals(subCd) == false ) {
					insert2Query.append("\n	INSERT INTO RCHDEPT(RCHNO, TGTCODE, TGTNAME, TGTGBN)									");
					insert2Query.append("\n	(																						"); 	
				if ( !grpCd.equals("") ) {
					insert2Query.append("\n	SELECT DISTINCT ? AS RCHNO, CODE AS TGTCODE, NAME AS TGTNAME, '1' AS TGTGBN 			");
					insert2Query.append("\n	FROM GRPLISTDTL																			");
					insert2Query.append("\n	WHERE GRPLISTCD IN (" + grpCd + ")														");
					insert2Query.append("\n	AND CODEGBN = '0'																		");
					insert2Query.append("\n	UNION																					");
					//설문조사대상으로 사용자 지정시 소속 부서는 등록할 필요없음(취합대상지정과 다름)
					//insert2Query.append("\n	SELECT DISTINCT ? AS RCHNO, DEPT_ID AS TGTCODE, DEPT_NAME AS TGTNAME, '1' AS TGTGBN	");
					insert2Query.append("\n	SELECT DISTINCT ? AS RCHNO, USER_ID AS TGTCODE, USER_NAME AS TGTNAME, '2' AS TGTGBN		");
					insert2Query.append("\n	FROM USR																				");
					insert2Query.append("\n	WHERE USER_ID IN (SELECT CODE															");
					insert2Query.append("\n					  FROM GRPLISTDTL														");
					insert2Query.append("\n					  WHERE GRPLISTCD IN (" + grpCd + ")									");
					insert2Query.append("\n					  AND CODEGBN = '1')													");
				}
				if ( !grpCd.equals("") && !subCd.equals("") ) {
					insert2Query.append("\n	UNION																					"); 
				}
				if ( !subCd.equals("") ) {
					insert2Query.append("\n	SELECT ? AS RCHNO, DEPT_ID AS TGTCODE, DEPT_NAME AS TGTNAME, '1' AS TGTGBN				");
					insert2Query.append("\n	FROM DEPT																				");
					insert2Query.append("\n	WHERE DEPT_ID IN (" + subCd + ")														");
					insert2Query.append("\n	UNION																					");
					insert2Query.append("\n	SELECT ? AS RCHNO, DEPT_ID AS TGTCODE, DEPT_NAME AS TGTNAME, '2' AS TGTGBN			 	");
					insert2Query.append("\n	FROM USR																				");
					insert2Query.append("\n	WHERE USER_ID IN (" + subCd + ")														");
				}
					insert2Query.append("\n	)																						");	
			}
		}
		
		try{
			con = ConnectionManager.getConnection();
			
			con.commit();
			con.setAutoCommit(false);
			
			/*기존등록된 제출부서 그룹 및 부서목록LIST삭제*/
			pstmt = con.prepareStatement(delete1Query.toString());	
		    
		    if ( rchno == 0 ) {
				pstmt.setString(++bindPos, sessionId);	
		    } else {
				pstmt.setInt(++bindPos , rchno);
		    }
			
			if ( pstmt.executeUpdate() >= 0 ) {
				cnt++;
			}
			
			bindPos = 0;
			ConnectionManager.close(pstmt);
			
			/*기존등록된 제출부서 목록LIST삭제*/
			pstmt = con.prepareStatement(delete2Query.toString());	
		    
		    if ( rchno == 0 ) {
				pstmt.setString(++bindPos, sessionId);	
		    } else {
				pstmt.setInt(++bindPos , rchno);
		    }
			
			if ( pstmt.executeUpdate() >= 0 ) {
				cnt++;
			}
			
			bindPos = 0;
			ConnectionManager.close(pstmt);
			
			/*제출부서 그룹 및 부서목록LIST등록*/
			if ( deptList.size() > 0 ) {				
				maxSeq = getMaxSEQ(rchno);
				
				if ( rchno == 0 ) {
					commsubdeptBean dept = null;
					pstmt = con.prepareStatement(insert1Query.toString());
					for ( int i = 0; i < deptList.size(); i++ ) {
						dept = (commsubdeptBean)deptList.get(i);
						//if ( dept.getGrpGbn().equals("3") == true ) continue;
						
						pstmt.setString(++bindPos, sessionId);
						pstmt.setInt(++bindPos, ++maxSeq);
						pstmt.setString(++bindPos, dept.getCode());
						pstmt.setString(++bindPos, dept.getName());
						pstmt.setString(++bindPos, dept.getGrpGbn());
						
						pstmt.addBatch();
						bindPos = 0;
					}
					
					ret = pstmt.executeBatch();
					cnt += ret.length;
					
					ConnectionManager.close(pstmt);
				} else {
					commsubdeptBean dept = null;
					for ( int i = 0; i < deptList.size(); i++ ) {
						dept = (commsubdeptBean)deptList.get(i);
						//if ( dept.getGrpGbn().equals("3") == true ) continue;
						
						pstmt = con.prepareStatement(insert1Query.toString());
						pstmt.setInt(++bindPos, rchno);
						pstmt.setInt(++bindPos, ++maxSeq);
						pstmt.setString(++bindPos, dept.getCode());
						pstmt.setString(++bindPos, dept.getName());
						pstmt.setString(++bindPos, dept.getGrpGbn());
						cnt += pstmt.executeUpdate();
						
						bindPos = 0;
						ConnectionManager.close(pstmt);
					}
				}
				
				if ( "".equals(grpCd) == false || "".equals(subCd) == false ) {
					/*제출부서 목록LIST등록*/
					pstmt = con.prepareStatement(insert2Query.toString());
					
					if ( rchno == 0 ) {
						if ( !grpCd.equals("") ) {
							pstmt.setString(++bindPos, sessionId);
							pstmt.setString(++bindPos, sessionId);
						}
						if ( !subCd.equals("") ) {
							pstmt.setString(++bindPos, sessionId);
							pstmt.setString(++bindPos, sessionId);
						}
					} else {
						if ( !grpCd.equals("") ) {
							pstmt.setInt(++bindPos, rchno);
							pstmt.setInt(++bindPos, rchno);
						}
						if ( !subCd.equals("") ) {
							pstmt.setInt(++bindPos, rchno);
							pstmt.setInt(++bindPos, rchno);
						}
					}
					
					cnt += pstmt.executeUpdate();
					
					bindPos = 0;
					ConnectionManager.close(pstmt);
				}
			}
			
			con.commit();
		} catch(Exception e){
			con.rollback();
			ConnectionManager.close(con,pstmt);
			throw e;
		} finally {
			con.setAutoCommit(true);
			ConnectionManager.close(con, pstmt);
		}
		return cnt;
	}
	
	public int commrchGrpdeptInsert( int rchgrpno, ArrayList deptList, String sessionId, String usrid, String grpCd, String subCd ) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		int bindPos = 0;
		int cnt = 0;
		int[] ret = null;
		
		int maxSeq = 0;
		
		StringBuffer insert1Query = new StringBuffer();
		StringBuffer insert2Query = new StringBuffer();
		StringBuffer delete1Query = new StringBuffer();
		StringBuffer delete2Query = new StringBuffer();
		
		if ( rchgrpno == 0 ) {
			delete1Query.append("\n	DELETE FROM RCHGRPDEPTLIST_TEMP	");
			delete1Query.append("\n	WHERE SESSIONID LIKE ?				");
			
			delete2Query.append("\n DELETE FROM RCHGRPDEPT_TEMP		");
			delete2Query.append("\n	WHERE SESSIONID LIKE ?				");
		} else {
			delete1Query.append("\n	DELETE FROM RCHGRPDEPTLIST			");
			delete1Query.append("\n	WHERE SYSDOCNO = ?				");	
			
			delete2Query.append("\n DELETE FROM TGTDEPT				");
			delete2Query.append("\n	WHERE SYSDOCNO = ?				");
		}
		
		if ( rchgrpno == 0 ) {
			insert1Query.append("\n	INSERT INTO RCHGRPDEPTLIST_TEMP(SESSIONID, SEQ, GRPCD, GRPNM, GRPGBN)								");
			insert1Query.append("\n	VALUES (?, ?, ?, ?, ?)																			");
			
			if ( "".equals(grpCd) == false || "".equals(subCd) == false ) {
				insert2Query.append("\n	INSERT INTO RCHGRPDEPT_TEMP(SESSIONID, TGTCODE, TGTNAME, TGTGBN)								");
				insert2Query.append("\n	(																							"); 
				if ( !grpCd.equals("") ) {
					insert2Query.append("\n	SELECT DISTINCT ? AS SESSIONID, CODE AS TGTCODE, NAME AS TGTNAME, '1' AS TGTGBN 		");
					insert2Query.append("\n	FROM GRPLISTDTL																			");
					insert2Query.append("\n	WHERE GRPLISTCD IN (" + grpCd + ")														");
					insert2Query.append("\n	AND CODEGBN = '0'																		");
					insert2Query.append("\n	UNION																					");
					//설문조사대상으로 사용자 지정시 소속 부서는 등록할 필요없음(취합대상지정과 다름)
					//insert2Query.append("\n	SELECT DISTINCT ? AS SESSIONID, DEPT_ID AS TGTCODE, DEPT_NAME AS TGTNAME, '1' AS TGTGBN	");
					insert2Query.append("\n	SELECT DISTINCT ? AS SESSIONID, USER_ID AS TGTCODE, USER_NAME AS TGTNAME, '2' AS TGTGBN	");
					insert2Query.append("\n	FROM USR																				");
					insert2Query.append("\n	WHERE USER_ID IN (SELECT CODE															");
					insert2Query.append("\n					  FROM GRPLISTDTL														");
					insert2Query.append("\n					  WHERE GRPLISTCD IN (" + grpCd + ")									");
					insert2Query.append("\n					  AND CODEGBN = '1')													");
				}
				if ( !grpCd.equals("") && !subCd.equals("") ) {
					insert2Query.append("\n	UNION																					");
				}
				if ( !subCd.equals("") ) {
					insert2Query.append("\n	SELECT ? AS SESSIONID, DEPT_ID AS TGTCODE, DEPT_NAME AS TGTNAME, '1' AS TGTGBN			");
					insert2Query.append("\n	FROM DEPT																				");
					insert2Query.append("\n	WHERE DEPT_ID IN (" + subCd + ")														");
					insert2Query.append("\n	UNION																					");
					insert2Query.append("\n	SELECT ? AS SESSIONID, USER_ID AS TGTCODE, USER_NAME AS TGTNAME, '2' AS TGTGBN		 	");
					insert2Query.append("\n	FROM USR																				");
					insert2Query.append("\n	WHERE USER_ID IN (" + subCd + ")														");
				}
				insert2Query.append("\n	)																							");
			}
		} else {
			insert1Query.append("\n	INSERT INTO RCHGRPDEPTLIST(RCHNO, SEQ, GRPCD, GRPNM, GRPGBN)										");
			insert1Query.append("\n	VALUES (?, ?, ?, ?, ?)																			");
			
			if ( "".equals(grpCd) == false || "".equals(subCd) == false ) {
				insert2Query.append("\n	INSERT INTO RCHGRPDEPT(RCHNO, TGTCODE, TGTNAME, TGTGBN)									");
				insert2Query.append("\n	(																						"); 	
				if ( !grpCd.equals("") ) {
					insert2Query.append("\n	SELECT DISTINCT ? AS RCHNO, CODE AS TGTCODE, NAME AS TGTNAME, '1' AS TGTGBN 			");
					insert2Query.append("\n	FROM GRPLISTDTL																			");
					insert2Query.append("\n	WHERE GRPLISTCD IN (" + grpCd + ")														");
					insert2Query.append("\n	AND CODEGBN = '0'																		");
					insert2Query.append("\n	UNION																					");
					//설문조사대상으로 사용자 지정시 소속 부서는 등록할 필요없음(취합대상지정과 다름)
					//insert2Query.append("\n	SELECT DISTINCT ? AS RCHNO, DEPT_ID AS TGTCODE, DEPT_NAME AS TGTNAME, '1' AS TGTGBN	");
					insert2Query.append("\n	SELECT DISTINCT ? AS RCHNO, USER_ID AS TGTCODE, USER_NAME AS TGTNAME, '2' AS TGTGBN		");
					insert2Query.append("\n	FROM USR																				");
					insert2Query.append("\n	WHERE USER_ID IN (SELECT CODE															");
					insert2Query.append("\n					  FROM GRPLISTDTL														");
					insert2Query.append("\n					  WHERE GRPLISTCD IN (" + grpCd + ")									");
					insert2Query.append("\n					  AND CODEGBN = '1')													");
				}
				if ( !grpCd.equals("") && !subCd.equals("") ) {
					insert2Query.append("\n	UNION																					"); 
				}
				if ( !subCd.equals("") ) {
					insert2Query.append("\n	SELECT ? AS RCHNO, DEPT_ID AS TGTCODE, DEPT_NAME AS TGTNAME, '1' AS TGTGBN				");
					insert2Query.append("\n	FROM DEPT																				");
					insert2Query.append("\n	WHERE DEPT_ID IN (" + subCd + ")														");
					insert2Query.append("\n	UNION																					");
					insert2Query.append("\n	SELECT ? AS RCHNO, DEPT_ID AS TGTCODE, DEPT_NAME AS TGTNAME, '2' AS TGTGBN			 	");
					insert2Query.append("\n	FROM USR																				");
					insert2Query.append("\n	WHERE USER_ID IN (" + subCd + ")														");
				}
				insert2Query.append("\n	)																						");	
			}
		}
		
		try{
			con = ConnectionManager.getConnection();
			
			con.commit();
			con.setAutoCommit(false);
			
			/*기존등록된 제출부서 그룹 및 부서목록LIST삭제*/
			pstmt = con.prepareStatement(delete1Query.toString());	
			
			if ( rchgrpno == 0 ) {
				pstmt.setString(++bindPos, sessionId);	
			} else {
				pstmt.setInt(++bindPos , rchgrpno);
			}
			
			if ( pstmt.executeUpdate() >= 0 ) {
				cnt++;
			}
			
			bindPos = 0;
			ConnectionManager.close(pstmt);
			
			/*기존등록된 제출부서 목록LIST삭제*/
			pstmt = con.prepareStatement(delete2Query.toString());	
			
			if ( rchgrpno == 0 ) {
				pstmt.setString(++bindPos, sessionId);	
			} else {
				pstmt.setInt(++bindPos , rchgrpno);
			}
			
			if ( pstmt.executeUpdate() >= 0 ) {
				cnt++;
			}
			
			bindPos = 0;
			ConnectionManager.close(pstmt);
			
			/*제출부서 그룹 및 부서목록LIST등록*/
			if ( deptList.size() > 0 ) {				
				maxSeq = getMaxSEQ(rchgrpno);
				
				if ( rchgrpno == 0 ) {
					commsubdeptBean dept = null;
					pstmt = con.prepareStatement(insert1Query.toString());
					for ( int i = 0; i < deptList.size(); i++ ) {
						dept = (commsubdeptBean)deptList.get(i);
						//if ( dept.getGrpGbn().equals("3") == true ) continue;
						
						pstmt.setString(++bindPos, sessionId);
						pstmt.setInt(++bindPos, ++maxSeq);
						pstmt.setString(++bindPos, dept.getCode());
						pstmt.setString(++bindPos, dept.getName());
						pstmt.setString(++bindPos, dept.getGrpGbn());
						
						pstmt.addBatch();
						bindPos = 0;
					}
					
					ret = pstmt.executeBatch();
					cnt += ret.length;
					
					ConnectionManager.close(pstmt);
				} else {
					commsubdeptBean dept = null;
					for ( int i = 0; i < deptList.size(); i++ ) {
						dept = (commsubdeptBean)deptList.get(i);
						//if ( dept.getGrpGbn().equals("3") == true ) continue;
						
						pstmt = con.prepareStatement(insert1Query.toString());
						pstmt.setInt(++bindPos, rchgrpno);
						pstmt.setInt(++bindPos, ++maxSeq);
						pstmt.setString(++bindPos, dept.getCode());
						pstmt.setString(++bindPos, dept.getName());
						pstmt.setString(++bindPos, dept.getGrpGbn());
						cnt += pstmt.executeUpdate();
						
						bindPos = 0;
						ConnectionManager.close(pstmt);
					}
				}
				
				if ( "".equals(grpCd) == false || "".equals(subCd) == false ) {
					/*제출부서 목록LIST등록*/
					pstmt = con.prepareStatement(insert2Query.toString());
					
					if ( rchgrpno == 0 ) {
						if ( !grpCd.equals("") ) {
							pstmt.setString(++bindPos, sessionId);
							pstmt.setString(++bindPos, sessionId);
						}
						if ( !subCd.equals("") ) {
							pstmt.setString(++bindPos, sessionId);
							pstmt.setString(++bindPos, sessionId);
						}
					} else {
						if ( !grpCd.equals("") ) {
							pstmt.setInt(++bindPos, rchgrpno);
							pstmt.setInt(++bindPos, rchgrpno);
						}
						if ( !subCd.equals("") ) {
							pstmt.setInt(++bindPos, rchgrpno);
							pstmt.setInt(++bindPos, rchgrpno);
						}
					}
					
					cnt += pstmt.executeUpdate();
					
					bindPos = 0;
					ConnectionManager.close(pstmt);
				}
			}
			
			con.commit();
		} catch(Exception e){
			con.rollback();
			ConnectionManager.close(con,pstmt);
			throw e;
		} finally {
			con.setAutoCommit(true);
			ConnectionManager.close(con, pstmt);
		}
		return cnt;
	}
	
	/** 
	 * 
	 * @param rchno
	 * @return
	 * @throws Exception
	 */
	public int getRchMaxSEQ(int rchno) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int  maxSeq = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			if(rchno == 0){
				selectQuery.append("SELECT NVL(MAX(SEQ),0) 		");
				selectQuery.append("  FROM RCHDEPTLIST_TEMP 		");
			}else{
				selectQuery.append("SELECT NVL(MAX(SEQ),0) 		");
				selectQuery.append("  FROM RCHDEPTLIST 				");	
			}
			
			conn = ConnectionManager.getConnection();
			pstmt =	conn.prepareStatement(selectQuery.toString());
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				maxSeq = rs.getInt(1);
			}

		 } catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return maxSeq;
	}

	public List commrchdeptList(int rchno, String sessionId, String userid) throws Exception {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList deptView = new ArrayList();
		int bindPos = 0;
		
		StringBuffer selectQuery = new StringBuffer();
		
		if(rchno == 0){
			selectQuery.append(" SELECT SESSIONID, GRPCD, GRPNM, GRPGBN,                                                   		 \n");
			selectQuery.append("        DECODE(GRPGBN, '1', GRPNM, '2', NVL(TRIM(SUBSTR(DEPT_FULLNAME, ?)), GRPNM)) DISPLAYNAME  \n");
			selectQuery.append(" FROM RCHDEPTLIST_TEMP T, DEPT D                                                                 \n");
			selectQuery.append(" WHERE T.GRPCD = D.DEPT_ID(+)                                                                    \n");
			selectQuery.append(" AND GRPGBN IN(1,2)                                                                              \n");
			selectQuery.append(" AND SESSIONID LIKE ?                                                                               \n");
			selectQuery.append(" UNION ALL                                                                                       \n");
			selectQuery.append(" SELECT SESSIONID, GRPCD, GRPNM, '3',                                                            \n");
			selectQuery.append("        NVL(TRIM(SUBSTR(DEPT_FULLNAME, ?)), GRPNM) || ' ' || GRPNM GRPNM                         \n");
			selectQuery.append(" FROM RCHDEPTLIST_TEMP I, USR D                                                                  \n");
			selectQuery.append(" WHERE I.GRPCD = D.USER_ID(+)                                                                    \n");
			selectQuery.append(" AND GRPGBN = 3                                                                                  \n");
			selectQuery.append(" AND SESSIONID LIKE ?                                                                               \n");
		}else{			
			selectQuery.append(" SELECT RCHNO, GRPCD, GRPNM, GRPGBN,                                                   			 \n");
			selectQuery.append("        DECODE(GRPGBN, '1', GRPNM, '2', NVL(TRIM(SUBSTR(DEPT_FULLNAME, ?)), GRPNM)) DISPLAYNAME  \n");
			selectQuery.append(" FROM RCHDEPTLIST_TEMP T, DEPT D                                                           		 \n");
			selectQuery.append(" WHERE T.GRPCD = D.DEPT_ID(+)                                                                    \n");
			selectQuery.append(" AND GRPGBN IN(1,2)                                                                              \n");
			selectQuery.append(" AND RCHNO = ?                                                                         		     \n");
			selectQuery.append(" UNION ALL                                                                                       \n");
			selectQuery.append(" SELECT RCHNO, GRPCD, GRPNM, '3',                                                      		     \n");
			selectQuery.append("        NVL(TRIM(SUBSTR(DEPT_FULLNAME, ?)), GRPNM) || ' ' || GRPNM GRPNM                         \n");
			selectQuery.append(" FROM RCHDEPTLIST_TEMP I, USR D                                                                  \n");
			selectQuery.append(" WHERE I.GRPCD = D.USER_ID(+)                                                                    \n");
			selectQuery.append(" AND GRPGBN = 3                                                                                  \n");
			selectQuery.append(" AND RCHNO = ?                                                                         		     \n");
		}
		
		try{
			int substring = commfunction.getDeptFullNameSubstringIndex(userid);
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if(rchno ==0 ){
				pstmt.setInt(++bindPos, substring);
				pstmt.setString(++bindPos, sessionId);
				pstmt.setInt(++bindPos, substring);
				pstmt.setString(++bindPos, sessionId);
			}else{
				pstmt.setInt(++bindPos, substring);
				pstmt.setString(++bindPos, sessionId);
				pstmt.setInt(++bindPos, substring);
				pstmt.setString(++bindPos, sessionId);
			}
			
			rs = pstmt.executeQuery();
			
			commsubdeptBean bean = null;
			while(rs.next()){
				bean = new commsubdeptBean();
				
				bean.setCode(rs.getString("GRPCD"));
				bean.setName(rs.getString("GRPNM"));
				bean.setDisplayName(rs.getString("DISPLAYNAME"));
				bean.setGrpGbn(rs.getString("GRPGBN"));

				deptView.add(bean);
				
				bean = null;				
			}
			
		}finally{
			ConnectionManager.close(con, pstmt, rs);
		}
		return deptView;
	}
	
	public List commrchGrpdeptList(int rchgrpno, String sessionId, String userid) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList deptView = new ArrayList();
		int bindPos = 0;
		
		StringBuffer selectQuery = new StringBuffer();
		
		if(rchgrpno == 0){
			selectQuery.append(" SELECT SESSIONID, GRPCD, GRPNM, GRPGBN,                                                   		 \n");
			selectQuery.append("        DECODE(GRPGBN, '1', GRPNM, '2', NVL(TRIM(SUBSTR(DEPT_FULLNAME, ?)), GRPNM)) DISPLAYNAME  \n");
			selectQuery.append(" FROM RCHGRPDEPTLIST_TEMP T, DEPT D                                                                 \n");
			selectQuery.append(" WHERE T.GRPCD = D.DEPT_ID(+)                                                                    \n");
			selectQuery.append(" AND GRPGBN IN(1,2)                                                                              \n");
			selectQuery.append(" AND SESSIONID LIKE ?                                                                               \n");
			selectQuery.append(" UNION ALL                                                                                       \n");
			selectQuery.append(" SELECT SESSIONID, GRPCD, GRPNM, '3',                                                            \n");
			selectQuery.append("        NVL(TRIM(SUBSTR(DEPT_FULLNAME, ?)), GRPNM) || ' ' || GRPNM GRPNM                         \n");
			selectQuery.append(" FROM RCHGRPDEPTLIST_TEMP I, USR D                                                                  \n");
			selectQuery.append(" WHERE I.GRPCD = D.USER_ID(+)                                                                    \n");
			selectQuery.append(" AND GRPGBN = 3                                                                                  \n");
			selectQuery.append(" AND SESSIONID LIKE ?                                                                               \n");
		}else{			
			selectQuery.append(" SELECT RCHGRPNO, GRPCD, GRPNM, GRPGBN,                                                   			 \n");
			selectQuery.append("        DECODE(GRPGBN, '1', GRPNM, '2', NVL(TRIM(SUBSTR(DEPT_FULLNAME, ?)), GRPNM)) DISPLAYNAME  \n");
			selectQuery.append(" FROM RCHGRPDEPTLIST_TEMP T, DEPT D                                                           		 \n");
			selectQuery.append(" WHERE T.GRPCD = D.DEPT_ID(+)                                                                    \n");
			selectQuery.append(" AND GRPGBN IN(1,2)                                                                              \n");
			selectQuery.append(" AND RCHGRPNO = ?                                                                         		     \n");
			selectQuery.append(" UNION ALL                                                                                       \n");
			selectQuery.append(" SELECT RCHGRPNO, GRPCD, GRPNM, '3',                                                      		     \n");
			selectQuery.append("        NVL(TRIM(SUBSTR(DEPT_FULLNAME, ?)), GRPNM) || ' ' || GRPNM GRPNM                         \n");
			selectQuery.append(" FROM RCHGRPDEPTLIST_TEMP I, USR D                                                                  \n");
			selectQuery.append(" WHERE I.GRPCD = D.USER_ID(+)                                                                    \n");
			selectQuery.append(" AND GRPGBN = 3                                                                                  \n");
			selectQuery.append(" AND RCHGRPNO = ?                                                                         		     \n");
		}
		
		try{
			int substring = commfunction.getDeptFullNameSubstringIndex(userid);
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if(rchgrpno ==0 ){
				pstmt.setInt(++bindPos, substring);
				pstmt.setString(++bindPos, sessionId);
				pstmt.setInt(++bindPos, substring);
				pstmt.setString(++bindPos, sessionId);
			}else{
				pstmt.setInt(++bindPos, substring);
				pstmt.setString(++bindPos, sessionId);
				pstmt.setInt(++bindPos, substring);
				pstmt.setString(++bindPos, sessionId);
			}
			
			rs = pstmt.executeQuery();
			
			commsubdeptBean bean = null;
			while(rs.next()){
				bean = new commsubdeptBean();
				
				bean.setCode(rs.getString("GRPCD"));
				bean.setName(rs.getString("GRPNM"));
				bean.setDisplayName(rs.getString("DISPLAYNAME"));
				bean.setGrpGbn(rs.getString("GRPGBN"));
				
				deptView.add(bean);
				
				bean = null;				
			}
			
		}finally{
			ConnectionManager.close(con, pstmt, rs);
		}
		return deptView;
	}
}