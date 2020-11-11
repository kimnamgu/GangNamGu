/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 결재선지정,입력담당자지정 dao
 * 설명:
 */
package nexti.ejms.commapproval.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.util.Utils;
import nexti.ejms.commapproval.model.commapprovalDAO;
import nexti.ejms.commapproval.model.commapprovalBean;
import nexti.ejms.common.ConnectionManager;

public class commapprovalDAO {
	private static Logger logger = Logger.getLogger(commapprovalDAO.class);

	public String getGradeListXml() throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList gradeList = null;
		
		StringBuffer selectQuery = new StringBuffer();

		selectQuery.append("SELECT DISTINCT GRADE_ID, GRADE_NAME \n");
		selectQuery.append("FROM USR \n");
		selectQuery.append("WHERE GRADE_ID IS NOT NULL \n");
		selectQuery.append("AND GRADE_NAME IS NOT NULL \n");
		
		try{
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());

			rs = pstmt.executeQuery();
			
			gradeList = new ArrayList();

			while(rs.next()){
				commapprovalBean bean = new commapprovalBean();
				
				bean.setGrade_id(rs.getString("GRADE_ID"));
				bean.setGrade_name(rs.getString("GRADE_NAME"));
				bean.setDeptLevel(1);
				
				gradeList.add(bean);
			}
		}catch(Exception e){
			logger.error("ERROR",e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		}finally{
			ConnectionManager.close(con, pstmt, rs);
		}
		
		return getGradeList(gradeList);
	}
	
	public String getResearchGrpListXml(String range) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList gradeList = null;
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("SELECT RCHNO, TITLE \n");
		selectQuery.append("FROM RCHMST \n");
		selectQuery.append("WHERE GROUPYN = 'Y' \n");
		selectQuery.append("AND RANGE LIKE ? \n");
		
		try{
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, range + "%");
			
			rs = pstmt.executeQuery();
			
			gradeList = new ArrayList();
			
			while(rs.next()){
				commapprovalBean bean = new commapprovalBean();
				
				bean.setTgtcode(rs.getString("RCHNO"));
				bean.setTgtname(rs.getString("TITLE"));
				bean.setDeptLevel(1);
				
				gradeList.add(bean);
			}
		}catch(Exception e){
			logger.error("ERROR",e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		}finally{
			ConnectionManager.close(con, pstmt, rs);
		}
		
		return getResearchGrpList(gradeList);
	}
	
	/**
	 * tree XML 파일을 생성한다.
	 * @param deptList
	 * @return
	 */
	private String getGradeList(ArrayList gradeList) {
		
		int preLevel = 0;
		int diffDepth = 0;

		StringBuffer userXML = new StringBuffer();
		
		userXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		userXML.append("<tree id=\"0\">");
		
		for (int i = 0 ; i < gradeList.size(); i++){
			commapprovalBean bean = (commapprovalBean)gradeList.get(i);

			// 이전 엘레멘트와 현재 엘레메트 간의 레벨차이
			diffDepth = bean.getDeptLevel() - preLevel;
			
			if (i == 0){	// 최초 엘레멘트
				userXML.append("\n<item text=\"" + bean.getGrade_name()+"("+bean.getGrade_id()+")" + "\" id=\"" + bean.getGrade_id() + "\" open=\"1\" >");
				
				if(gradeList.size() == 1) {
					userXML.append("\n</item>");
				}

			} else {
				if (diffDepth == 0) {				// 이전 엘레멘트와 동일레벨일 경우
					userXML.append("</item>");
					userXML.append("\n<item text=\"" + bean.getGrade_name()+"("+bean.getGrade_id()+")" + "\" id=\"" + bean.getGrade_id() + "\" >");										
				} else if (diffDepth > 0){			// 이전 엘레멘트보다 하위 레벨일 경우
					userXML.append("\n<item text=\"" + bean.getGrade_name()+"("+bean.getGrade_id()+")" + "\" id=\"" + bean.getGrade_id() + "\" >");
				} else {							// 이전 엘레멘트보다 상위 레벨인 경우
					for (int j = 0 ; j < Math.abs(diffDepth) + 1 ; j++)
					userXML.append("\n</item>");
					userXML.append("\n<item text=\"" + bean.getGrade_name()+"("+bean.getGrade_id()+")" + "\" id=\"" + bean.getGrade_id() + "\" >");
				}
				
				if (i == (gradeList.size() - 1)){
					for (int j = 0 ; j < bean.getDeptLevel() ; j++){
						userXML.append("\n</item>");
					}
				}
			}
			
			preLevel = bean.getDeptLevel();
		}
			
		// 최초 엘레멘트 open에 대해 엘레멘트 닫기
		userXML.append("\n</tree>");
		return (userXML.toString());
	}
	
	/**
	 * tree XML 파일을 생성한다.
	 * @param deptList
	 * @return
	 */
	private String getResearchGrpList(ArrayList gradeList) {
		
		int preLevel = 0;
		int diffDepth = 0;
		
		StringBuffer userXML = new StringBuffer();
		
		userXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		userXML.append("<tree id=\"0\">");
		
		for (int i = 0 ; i < gradeList.size(); i++){
			commapprovalBean bean = (commapprovalBean)gradeList.get(i);
			
			// 이전 엘레멘트와 현재 엘레메트 간의 레벨차이
			diffDepth = bean.getDeptLevel() - preLevel;
			
			if (i == 0){	// 최초 엘레멘트
				userXML.append("\n<item text=\"" + bean.getTgtname() + "\" id=\"" + bean.getTgtcode() + "\" open=\"1\" >");
				
				if(gradeList.size() == 1) {
					userXML.append("\n</item>");
				}
				
			} else {
				if (diffDepth == 0) {				// 이전 엘레멘트와 동일레벨일 경우
					userXML.append("</item>");
					userXML.append("\n<item text=\"" + bean.getTgtname() + "\" id=\"" + bean.getTgtcode() + "\" >");										
				} else if (diffDepth > 0){			// 이전 엘레멘트보다 하위 레벨일 경우
					userXML.append("\n<item text=\"" + bean.getTgtname() + "\" id=\"" + bean.getTgtcode() + "\" >");
				} else {							// 이전 엘레멘트보다 상위 레벨인 경우
					for (int j = 0 ; j < Math.abs(diffDepth) + 1 ; j++)
						userXML.append("\n</item>");
					userXML.append("\n<item text=\"" + bean.getTgtname() + "\" id=\"" + bean.getTgtcode() + "\" >");
				}
				
				if (i == (gradeList.size() - 1)){
					for (int j = 0 ; j < bean.getDeptLevel() ; j++){
						userXML.append("\n</item>");
					}
				}
			}
			
			preLevel = bean.getDeptLevel();
		}
		
		// 최초 엘레멘트 open에 대해 엘레멘트 닫기
		userXML.append("\n</tree>");
		return (userXML.toString());
	}

	/**
	 * 부서정보를 XML형태로 생성하여 리턴
	 * @return
	 * @throws Exception
	 */
	public String getUserListXml(String deptId) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList userList = null;
		int bindPos = 0;
		
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT T1.DEPT_ID, T1.DEPT_NAME, T1.UPPER_DEPT_ID, T2.USER_ID, T2.USER_NAME, \n");
		sql.append("       T2.CLASS_NAME, T2.CHRGUNITCD, T3.CHRGUNITNM, 1 DEPT_LEVEL \n");
		sql.append("FROM DEPT T1, \n");
		sql.append("     (SELECT USER_ID, USER_NAME, DEPT_ID, USR_RANK, CLASS_ID, \n");
		sql.append("             CLASS_NAME, CHRGUNITCD, GRADE_ID, USE_YN \n");
		sql.append("      FROM USR)T2, CHRGUNIT T3 \n");
		sql.append("WHERE T1.DEPT_ID = T2.DEPT_ID \n");
		sql.append("AND T2.DEPT_ID = T3.DEPT_ID(+) \n");
		sql.append("AND T2.CHRGUNITCD = T3.CHRGUNITCD(+) \n");
		sql.append("AND T2.USE_YN = 'Y' \n");
		sql.append("AND T1.DEPT_ID = ? \n");
		sql.append("ORDER BY T2.USR_RANK, T2.GRADE_ID, T2.CLASS_ID, T2.USER_NAME \n");
		
		try{
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setString(++bindPos, deptId);
			rs = pstmt.executeQuery();
			
			userList = new ArrayList();

			while(rs.next()){
				commapprovalBean bean = new commapprovalBean();
				
				bean.setDeptId(rs.getString("DEPT_ID"));
				bean.setDeptName(rs.getString("DEPT_NAME"));
				bean.setUpperDeptId(rs.getString("UPPER_DEPT_ID"));
				bean.setUserId(rs.getString("USER_ID"));
				bean.setUserName(rs.getString("USER_NAME"));
				bean.setClassName(Utils.nullToEmptyString(rs.getString("CLASS_NAME")));
				bean.setChrgunitcd(rs.getString("CHRGUNITCD"));
				bean.setChrgunitnm(rs.getString("CHRGUNITNM"));
				bean.setDeptLevel(rs.getInt("DEPT_LEVEL"));
				
				userList.add(bean);
			}
		}catch(Exception e){
			logger.error("ERROR",e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		}finally{
			ConnectionManager.close(con, pstmt, rs);
		}
		
		return getUserList(userList);
	}

	/**
	 * 부서별 사용자 정보를 XML형태로 생성하여 리턴
	 * @return
	 * @throws Exception
	 */
	public String selectUserXml(String deptId) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		commapprovalBean bean = new commapprovalBean();
		int bindPos = 0;
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("\n SELECT T1.DEPT_ID,                                           ");
		selectQuery.append("\n        T1.DEPT_NAME,                                         ");
		selectQuery.append("\n        T1.UPPER_DEPT_ID,                                    	");
		selectQuery.append("\n        T2.USER_ID,                                       	");
		selectQuery.append("\n        T2.USER_NAME,                                         ");
		selectQuery.append("\n		  T2.CLASS_NAME,										");	
		selectQuery.append("\n        LEVEL AS DEPT_LEVEL,                                  ");                                           
		selectQuery.append("\n   FROM DEPT T1, USR T2  										");
		selectQuery.append("\n  WHERE T1.DEPT_ID = T2.DEPT_ID(+)                            ");
		selectQuery.append("\n    AND T1.DEPT_ID = ?                               			");

		
		try{
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(++bindPos, deptId);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				
				bean.setDeptId(rs.getString("DEPT_ID"));
				bean.setDeptName(rs.getString("DEPT_NAME"));
				bean.setUpperDeptId(rs.getString("UPPER_DEPT_ID"));
				bean.setUserId(Utils.nullToEmptyString(rs.getString("USER_ID")));
				bean.setUserName(Utils.nullToEmptyString(rs.getString("USER_NAME")));
				bean.setDeptLevel(rs.getInt("DEPT_LEVEL"));				
			}
		}catch(Exception e){
			logger.error("ERROR",e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		}finally{
			ConnectionManager.close(con, pstmt, rs);
		}
		
		return getUser(bean);
	}
	
	/**
	 * XML 파일을 생성한다.
	 * @param deptList
	 * @return
	 */
	private String getUser(commapprovalBean bean) {
			
			StringBuffer deptXML = new StringBuffer();
			deptXML.append("\n<userdata name=\"deptId\">" + bean.getDeptId() + "</userdata>");
			deptXML.append("\n<userdata name=\"deptName\">" + bean.getDeptName() + "</userdata>");
			deptXML.append("\n<userdata name=\"upperDeptId\">" + bean.getUpperDeptId() + "</userdata>");
			deptXML.append("\n<userdata name=\"userId\">" + bean.getUserId() + "</userdata>");
			deptXML.append("\n<userdata name=\"userName\">" + bean.getUserName() + "</userdata>");
		
			// 최초 엘레멘트 open에 대해 엘레멘트 닫기
			return (deptXML.toString());
	}
	
	/**
	 * tree XML 파일을 생성한다.
	 * @param deptList
	 * @return
	 */
	private String getUserList(ArrayList userList) {
		
		int preLevel = 0;
		int diffDepth = 0;

		StringBuffer userXML = new StringBuffer();
		
		userXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		userXML.append("<tree id=\"0\">");
		
		for (int i = 0 ; i < userList.size(); i++){
			commapprovalBean bean = (commapprovalBean)userList.get(i);

			// 이전 엘레멘트와 현재 엘레메트 간의 레벨차이
			diffDepth = bean.getDeptLevel() - preLevel;
			
			if (i == 0){	// 최초 엘레멘트
				userXML.append("\n<item text=\"" + bean.getUserName()+"("+bean.getClassName()+")" + "\" id=\"" + bean.getUserId() + "\" open=\"1\" >");
//				 <item> 엘레멘트 본문(사용자정의 데이터)
				userXML.append("\n<userdata name=\"className\">" + bean.getClassName() + "</userdata>");
				userXML.append("\n<userdata name=\"deptName\">" + bean.getDeptName() + "</userdata>");
				userXML.append("\n<userdata name=\"chrgunitNm\">" + bean.getChrgunitnm() + "</userdata>");
				
				if(userList.size() == 1) {
					userXML.append("\n</item>");
				}

			} else {
				if (diffDepth == 0) {				// 이전 엘레멘트와 동일레벨일 경우
					
					userXML.append("</item>");
					userXML.append("\n<item text=\"" + bean.getUserName() +"("+bean.getClassName()+")" + "\" id=\"" + bean.getUserId()+ "\" >");				
//					 <item> 엘레멘트 본문(사용자정의 데이터)
					userXML.append("\n<userdata name=\"className\">" + bean.getClassName() + "</userdata>");
					userXML.append("\n<userdata name=\"deptName\">" + bean.getDeptName() + "</userdata>");
					userXML.append("\n<userdata name=\"chrgunitNm\">" + bean.getChrgunitnm() + "</userdata>");
										
				} else if (diffDepth > 0){			// 이전 엘레멘트보다 하위 레벨일 경우
					userXML.append("\n<item text=\"" + bean.getUserName() + "("+bean.getClassName()+")" + "\" id=\"" + bean.getUserId() +"\" >");
//					 <item> 엘레멘트 본문(사용자정의 데이터)
					userXML.append("\n<userdata name=\"className\">" + bean.getClassName() + "</userdata>");
					userXML.append("\n<userdata name=\"deptName\">" + bean.getDeptName() + "</userdata>");
					userXML.append("\n<userdata name=\"chrgunitNm\">" + bean.getChrgunitnm() + "</userdata>");
				} else {							// 이전 엘레멘트보다 상위 레벨인 경우
					for (int j = 0 ; j < Math.abs(diffDepth) + 1 ; j++)
					userXML.append("\n</item>");
					userXML.append("\n<item text=\"" + bean.getUserName()+ "("+bean.getClassName()+")"  + "\" id=\"" + bean.getUserId() +"\" >");
//					 <item> 엘레멘트 본문(사용자정의 데이터)
					userXML.append("\n<userdata name=\"className\">" + bean.getClassName() + "</userdata>");
					userXML.append("\n<userdata name=\"deptName\">" + bean.getDeptName() + "</userdata>");
					userXML.append("\n<userdata name=\"chrgunitNm\">" + bean.getChrgunitnm() + "</userdata>");
				}
				
				if (i == (userList.size() - 1)){
					for (int j = 0 ; j < bean.getDeptLevel() ; j++){
						userXML.append("\n</item>");
					}
				}
			}
			
			preLevel = bean.getDeptLevel();
		}
			
		// 최초 엘레멘트 open에 대해 엘레멘트 닫기
		userXML.append("\n</tree>");
		return (userXML.toString());
	}
	
	/**
	 * 결재선 지정
	 * @param sysdocno
	 * @param userList
	 * @param sessionId
	 * @param deptId
	 * @param usrid
	 * @param type 1 : 취합부서, 1이아닌값 : 제출부서
	 * @return true : 성공, false : 실패
	 * @throws Exception
	 */
	public boolean designateInsert( int sysdocno, ArrayList userList, String sessionId, String deptId, String usrid, String type ) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int bindPos = 0;
		boolean result = false;
		
		int maxSeq = 0;
		
		StringBuffer insertQuery = new StringBuffer();
		StringBuffer deleteQuery = new StringBuffer();	
		StringBuffer selectQuery = new StringBuffer();

		if(type.equals("1")){
			if(sysdocno ==0){
				deleteQuery.append("DELETE FROM SANCCOL_TEMP 	");
				deleteQuery.append("WHERE SESSIONID LIKE ? 			");	
				deleteQuery.append("  AND SANCYN = '0' 			");	
			}else{
				deleteQuery.append("DELETE FROM SANCCOL 		");
				deleteQuery.append("WHERE SYSDOCNO = ? 			");	
				deleteQuery.append("  AND SANCYN = '0' 			");	
			}
		}else{
			deleteQuery.append("DELETE FROM SANCTGT 			");
			deleteQuery.append("WHERE SYSDOCNO = ? 				");	
			deleteQuery.append("  AND SANCYN = '0' 				");	
			deleteQuery.append("  AND TGTDEPTCD = ? 			");
		}
		
		
		if(type.equals("1")){
			if(sysdocno == 0){
				selectQuery.append("SELECT NVL(MAX(SEQ),0) 		");
				selectQuery.append("  FROM SANCCOL_TEMP 		");
				selectQuery.append(" WHERE SESSIONID LIKE ?			");
			}else{
				selectQuery.append("SELECT NVL(MAX(SEQ),0) 		");
				selectQuery.append("  FROM SANCCOL 				");
				selectQuery.append(" WHERE SYSDOCNO = ? 		");
			}
		}else{
				selectQuery.append("SELECT NVL(MAX(SEQ),0) 		");
				selectQuery.append("  FROM SANCTGT 				");
				selectQuery.append(" WHERE SYSDOCNO = ? 		");
				selectQuery.append("   AND TGTDEPTCD = ? 		");
		}
		
		if(type.equals("1")){
			if(sysdocno == 0){
				insertQuery.append("\n INSERT INTO SANCCOL_TEMP                    						");
				insertQuery.append("\n             (SESSIONID, SEQ,               						");
				insertQuery.append("\n              GUBUN, SANCUSRID, SANCUSRNM,    					");
				insertQuery.append("\n              SANCYN, CRTDT, CRTUSRID 							");
				insertQuery.append("\n             )                                					");
				insertQuery.append("\n      VALUES (?, ?,             									");
				insertQuery.append("\n              ?, ?, ?, 											");
				insertQuery.append("\n              '0', TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), ? 	");
				insertQuery.append("\n             )                                					");
			}else{
				insertQuery.append("\n INSERT INTO SANCCOL                    							");
				insertQuery.append("\n             (SYSDOCNO, SEQ,               						");
				insertQuery.append("\n              GUBUN, SANCUSRID, SANCUSRNM,    					");
				insertQuery.append("\n              SANCYN, CRTDT, CRTUSRID 							");
				insertQuery.append("\n             )                                					");
				insertQuery.append("\n      VALUES (?, ?,             									");
				insertQuery.append("\n              ?, ?, ?, 											");
				insertQuery.append("\n              '0', TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), ? 	");
				insertQuery.append("\n             )                                					");
			}
		}else{
			insertQuery.append("\n INSERT INTO SANCTGT                    							");
			insertQuery.append("\n             (SYSDOCNO, TGTDEPTCD, INPUTUSRID, SEQ,      			");
			insertQuery.append("\n              GUBUN, SANCUSRID, SANCUSRNM,    					");
			insertQuery.append("\n              SANCYN, CRTDT, CRTUSRID 							");
			insertQuery.append("\n             )                                					");
			insertQuery.append("\n      VALUES (?, ?, ?,         									");
			insertQuery.append("\n              ?, ?, ?, 											");
			insertQuery.append("\n              '0', TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), ? 	");
			insertQuery.append("\n             )                                					");
		}
		
		
		try{
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			/*취합결재선 지정 삭제처리*/
			pstmt = con.prepareStatement(deleteQuery.toString());	
		    
			if(type.equals("1")){
				if(sysdocno ==0){
					pstmt.setString(++bindPos, sessionId);	
			    }else{
					pstmt.setInt(++bindPos , sysdocno);
			    }
			}else{
				pstmt.setInt(++bindPos , sysdocno);
				pstmt.setString(++bindPos, deptId);
			}
		    
			
			pstmt.executeUpdate();
			bindPos = 0;
			
			if (pstmt != null){
				try {pstmt.close();} catch(SQLException ignored){}
			}
			
			/*취합결제선 max값가져오기*/
			pstmt =	con.prepareStatement(selectQuery.toString());
			
			if(type.equals("1")){
				if(sysdocno ==0){
					pstmt.setString(++bindPos, sessionId);
				}else{
					pstmt.setInt(++bindPos, sysdocno);
				}
			}else{
				pstmt.setInt(++bindPos, sysdocno);
				pstmt.setString(++bindPos, deptId);
			}

			rs = pstmt.executeQuery();
			bindPos = 0;
			
			if ( rs.next() ){
				maxSeq = rs.getInt(1);
			}
			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}
			
			/*취합결제선 지정 등록*/
			if (userList.size() > 0){
				pstmt = con.prepareStatement(insertQuery.toString());
				
				if(type.equals("1")){
					if(sysdocno ==0){
						commapprovalBean user = null;
						for (int i = 0 ; i < userList.size() ; i++){
							user = (commapprovalBean)userList.get(i);
							
							pstmt.setString(++bindPos, sessionId);
							pstmt.setInt(++bindPos, ++maxSeq);
							pstmt.setString(++bindPos, user.getGubun());
							pstmt.setString(++bindPos, user.getUserId());
							pstmt.setString(++bindPos, user.getUserName());
							pstmt.setString(++bindPos, usrid);
							
							pstmt.addBatch();
							
							bindPos = 0;
						}
					}else{
						commapprovalBean user = null;
						for (int i = 0 ; i < userList.size() ; i++){
							user = (commapprovalBean)userList.get(i);
							
							pstmt.setInt(++bindPos, sysdocno);
							pstmt.setInt(++bindPos, ++maxSeq);
							pstmt.setString(++bindPos, user.getGubun());
							pstmt.setString(++bindPos, user.getUserId());
							pstmt.setString(++bindPos, user.getUserName());
							pstmt.setString(++bindPos, usrid);
							
							pstmt.addBatch();
							
							bindPos = 0;
						}
					}
				}else{
					commapprovalBean user = null;
					for (int i = 0 ; i < userList.size() ; i++){
						user = (commapprovalBean)userList.get(i);
						
						pstmt.setInt(++bindPos, sysdocno);
						pstmt.setString(++bindPos, deptId);
						pstmt.setString(++bindPos, usrid);
						pstmt.setInt(++bindPos, ++maxSeq);
						pstmt.setString(++bindPos, user.getGubun());
						pstmt.setString(++bindPos, user.getUserId());
						pstmt.setString(++bindPos, user.getUserName());
						pstmt.setString(++bindPos, usrid);
						
						pstmt.addBatch();
						
						bindPos = 0;
					}
				}
				
				pstmt.executeBatch();
			}
			con.commit();
			result = true;
		} catch(Exception e){
			try {con.rollback();} catch(Exception ex) {}
			result = false;
		} finally {
			try {con.setAutoCommit(true);} catch(Exception ex) {}
			ConnectionManager.close(con, pstmt, rs);
		}
		return result;
	}
	
	/** (max+1)코드 값 가져오기	
	 * @throws Exception */
	public int getMaxDesignate(int sysdocno, String sessionId, String deptId, String type) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int maxSeq = 0;
		int bindPos = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			if(type.equals("1")){
				if(sysdocno == 0){
					selectQuery.append("SELECT NVL(MAX(SEQ),0) 		");
					selectQuery.append("  FROM SANCCOL_TEMP 		");
					selectQuery.append(" WHERE USER_ID = ?			");
				}else{
					selectQuery.append("SELECT NVL(MAX(SEQ),0) 		");
					selectQuery.append("  FROM SANCCOL 				");
					selectQuery.append(" WHERE SYSDOCNO = ? 		");
				}
			}else{
					selectQuery.append("SELECT NVL(MAX(SEQ),0) 		");
					selectQuery.append("  FROM SANCTGT 				");
					selectQuery.append(" WHERE SYSDOCNO = ? 		");
					selectQuery.append("   AND TGTDEPTCD = ? 		");
			}
			
			
			conn = ConnectionManager.getConnection();
			pstmt =	conn.prepareStatement(selectQuery.toString());
			
			if(type.equals("1")){
				if(sysdocno ==0){
					pstmt.setString(++bindPos, sessionId);
				}else{
					pstmt.setInt(++bindPos, sysdocno);
				}
			}else{
				pstmt.setInt(++bindPos, sysdocno);
				pstmt.setString(++bindPos, deptId);
			}

			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				maxSeq = rs.getInt(1);
			}

		 } catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn, pstmt, rs);
			throw e;
	     } finally {	       
				ConnectionManager.close(conn,pstmt,rs);
	     }
		return maxSeq;
	}
	
	public int inputusrInsert( int sysdocno, ArrayList userList, String userId, String deptId) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		int bindPos = 0;
		int cnt = 0;
		//int result = 0;
		int[] ret = null;
		
		StringBuffer deleteQuery = new StringBuffer();	
		deleteQuery.append("\n DELETE FROM INPUTUSR 	");
		deleteQuery.append("\n  WHERE SYSDOCNO = ? 		");	
		deleteQuery.append("\n    AND TGTDEPT = ?       ");
		deleteQuery.append("\n    AND INPUTSTATE = '01' ");
		
		StringBuffer insertQuery = new StringBuffer();
		insertQuery.append("\n INSERT INTO INPUTUSR                    						");
		insertQuery.append("\n             (SYSDOCNO, TGTDEPT,               				");
		insertQuery.append("\n              INPUTUSRID, INPUTUSRNM, INPUTSTATE, 			");
		insertQuery.append("\n              CRTDT, CRTUSRID									");
		insertQuery.append("\n             )                                				");
		insertQuery.append("\n      VALUES (?, ?,             								");
		insertQuery.append("\n              ?, ?, '01',										");
		insertQuery.append("\n 	            TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') , ?	");
		insertQuery.append("\n             )                                				");
		
		try{
			con = ConnectionManager.getConnection();
			
			con.commit();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(deleteQuery.toString());	
		    
			pstmt.setInt(++bindPos , sysdocno);
			pstmt.setString(++bindPos, deptId);
			
			//result = pstmt.executeUpdate();
			if(pstmt.executeUpdate()>0){
				cnt++;
			}
			bindPos = 0;
			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}
				
			if (userList.size() > 0){
				pstmt = con.prepareStatement(insertQuery.toString());
				
				commapprovalBean user = null;
				for (int i = 0 ; i < userList.size() ; i++){
					user = (commapprovalBean)userList.get(i);
					
					pstmt.setInt(++bindPos, sysdocno);
					pstmt.setString(++bindPos, deptId);
					pstmt.setString(++bindPos, user.getUserId());
					pstmt.setString(++bindPos, user.getUserName());
					pstmt.setString(++bindPos, userId);
	
					pstmt.addBatch();
					
					bindPos = 0;
				}
				
				ret = pstmt.executeBatch();
				cnt = ret.length;
			}
			con.commit();
		} catch(Exception e){
			con.rollback();
			ConnectionManager.close(con, pstmt);
			throw e;
		} finally {
			con.setAutoCommit(true);
			ConnectionManager.close(con, pstmt);
		}
		return cnt;
	}
	
	public int insertOtherTarget( int rchno, String sessionId, ArrayList userList) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		int bindPos = 0;
		int cnt = 0;
		int[] ret = null;
		
		StringBuffer deleteQuery = new StringBuffer();
		if ( rchno == 0 ) {
			deleteQuery.append("\n DELETE RCHOTHERTARGET_TEMP ");
			deleteQuery.append("\n WHERE SESSIONID LIKE ? ");
		} else {		
			deleteQuery.append("\n DELETE RCHOTHERTARGET ");
			deleteQuery.append("\n WHERE RCHNO = ? ");
		}
		
		StringBuffer insertQuery = new StringBuffer();
		if ( rchno == 0 ) {
			insertQuery.append("\n INSERT INTO RCHOTHERTARGET_TEMP(SESSIONID, TGTCODE, TGTNAME, TGTGBN) ");
			insertQuery.append("\n VALUES(?, ?, ?, ?) ");
		} else {	
			insertQuery.append("\n INSERT INTO RCHOTHERTARGET(RCHNO, TGTCODE, TGTNAME, TGTGBN) ");
			insertQuery.append("\n VALUES(?, ?, ?, ?) ");
		}
		
		try{
			con = ConnectionManager.getConnection();
			
			con.commit();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(deleteQuery.toString());	
			
			if ( rchno == 0 ) {
				pstmt.setString(++bindPos, sessionId);
			} else {
				pstmt.setInt(++bindPos , rchno);
			}
			
			if(pstmt.executeUpdate()>0){
				cnt++;
			}
			bindPos = 0;
			
			ConnectionManager.close(pstmt);
			
			if (userList.size() > 0){
				pstmt = con.prepareStatement(insertQuery.toString());
				
				commapprovalBean user = null;
				for (int i = 0 ; i < userList.size() ; i++){
					user = (commapprovalBean)userList.get(i);
					
					if ( rchno == 0 ) {
						pstmt.setString(++bindPos, sessionId);
					} else {
						pstmt.setInt(++bindPos, rchno);
					}
					pstmt.setString(++bindPos, user.getTgtcode());
					pstmt.setString(++bindPos, user.getTgtname());
					pstmt.setString(++bindPos, user.getTgtgbn());
					
					pstmt.addBatch();
					
					bindPos = 0;
				}
				
				ret = pstmt.executeBatch();
				cnt = ret.length;
			}
			con.commit();
		} catch(Exception e){
			con.rollback();
			ConnectionManager.close(con, pstmt);
			throw e;
		} finally {
			con.setAutoCommit(true);
			ConnectionManager.close(con, pstmt);
		}
		return cnt;
	}
	
	public int insertGrpOtherTarget( int rchgrpno, String sessionId, ArrayList userList) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		int bindPos = 0;
		int cnt = 0;
		int[] ret = null;
		
		StringBuffer deleteQuery = new StringBuffer();
		if ( rchgrpno == 0 ) {
			deleteQuery.append("\n DELETE RCHGRPOTHERTARGET_TEMP ");
			deleteQuery.append("\n WHERE SESSIONID LIKE ? ");
		} else {		
			deleteQuery.append("\n DELETE RCHGRPOTHERTARGET ");
			deleteQuery.append("\n WHERE RCHGRPNO = ? ");
		}
		
		StringBuffer insertQuery = new StringBuffer();
		if ( rchgrpno == 0 ) {
			insertQuery.append("\n INSERT INTO RCHGRPOTHERTARGET_TEMP(SESSIONID, TGTCODE, TGTNAME, TGTGBN) ");
			insertQuery.append("\n VALUES(?, ?, ?, ?) ");
		} else {	
			insertQuery.append("\n INSERT INTO RCHGRPOTHERTARGET(RCHGRPNO, TGTCODE, TGTNAME, TGTGBN) ");
			insertQuery.append("\n VALUES(?, ?, ?, ?) ");
		}
		
		try{
			con = ConnectionManager.getConnection();
			
			con.commit();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(deleteQuery.toString());	
			
			if ( rchgrpno == 0 ) {
				pstmt.setString(++bindPos, sessionId);
			} else {
				pstmt.setInt(++bindPos , rchgrpno);
			}
			
			if(pstmt.executeUpdate()>0){
				cnt++;
			}
			bindPos = 0;
			
			ConnectionManager.close(pstmt);
			
			if (userList.size() > 0){
				pstmt = con.prepareStatement(insertQuery.toString());
				
				commapprovalBean user = null;
				for (int i = 0 ; i < userList.size() ; i++){
					user = (commapprovalBean)userList.get(i);
					
					if ( rchgrpno == 0 ) {
						pstmt.setString(++bindPos, sessionId);
					} else {
						pstmt.setInt(++bindPos, rchgrpno);
					}
					pstmt.setString(++bindPos, user.getTgtcode());
					pstmt.setString(++bindPos, user.getTgtname());
					pstmt.setString(++bindPos, user.getTgtgbn());
					
					pstmt.addBatch();
					
					bindPos = 0;
				}
				
				ret = pstmt.executeBatch();
				cnt = ret.length;
			}
			con.commit();
		} catch(Exception e){
			con.rollback();
			ConnectionManager.close(con, pstmt);
			throw e;
		} finally {
			con.setAutoCommit(true);
			ConnectionManager.close(con, pstmt);
		}
		return cnt;
	}
	
	public int insertGrpChoice( int rchgrpno, String sessionId, String idList) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		StringBuffer sql = new StringBuffer();
		if ( rchgrpno == 0 ) {
			sql.append("\n UPDATE RCHGRPMST_TEMP SET RCHNOLIST = ? WHERE SESSIONID LIKE ? ");
		} else {
			sql.append("\n UPDATE RCHGRPMST SET RCHNOLIST = ? WHERE RCHGRPNO = ? ");
		}
		
		try{
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(sql.toString());	
			
			pstmt.setString(1, idList);
			if ( rchgrpno == 0 ) {
				pstmt.setString(2, sessionId);
			} else {
				pstmt.setInt(2, rchgrpno);
			}
			
			result = pstmt.executeUpdate();
		} finally {
			ConnectionManager.close(con, pstmt);
		}
		return result;
	}
	
	public int inputusrInsert(Connection con, int sysdocno, String sessionId, ArrayList userList, String userId, String deptId) throws Exception {
		
		PreparedStatement pstmt = null;
		int bindPos = 0;
		int cnt = 0;
		
		StringBuffer insertQuery = new StringBuffer();
		
		if ( sysdocno == 0 ) {
			insertQuery.append("\n INSERT INTO INPUTUSR_TEMP(SESSIONID, TGTDEPT, INPUTUSRID, INPUTUSRNM, INPUTSTATE,	");
			insertQuery.append("\n                      CRTDT, CRTUSRID)												");
			insertQuery.append("\n SELECT ?, ?, ?, ?, '01', TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), ?				");
			insertQuery.append("\n FROM DUAL																			");
			insertQuery.append("\n WHERE NOT EXISTS (SELECT * FROM INPUTUSR_TEMP										");
			insertQuery.append("\n                  WHERE SESSIONID LIKE ? AND TGTDEPT = ? AND INPUTUSRID = ?)				");
		} else {
			insertQuery.append("\n INSERT INTO INPUTUSR(SYSDOCNO, TGTDEPT, INPUTUSRID, INPUTUSRNM, INPUTSTATE, ");
			insertQuery.append("\n                      CRTDT, CRTUSRID)									   ");
			insertQuery.append("\n SELECT ?, ?, ?, ?, '01', TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), ?       ");
			insertQuery.append("\n FROM DUAL                                                                   ");
			insertQuery.append("\n WHERE NOT EXISTS (SELECT * FROM INPUTUSR                                    ");
			insertQuery.append("\n                  WHERE SYSDOCNO = ? AND TGTDEPT = ? AND INPUTUSRID = ?)     ");
		}
		
		try{
			if (userList.size() > 0){				
				commapprovalBean user = null;
				for (int i = 0 ; i < userList.size() ; i++){
					user = (commapprovalBean)userList.get(i);
					
					pstmt = con.prepareStatement(insertQuery.toString());
					
					if ( sysdocno == 0 ) {
						pstmt.setString(++bindPos, sessionId);
						pstmt.setString(++bindPos, deptId);
						pstmt.setString(++bindPos, user.getUserId());
						pstmt.setString(++bindPos, user.getUserName());
						pstmt.setString(++bindPos, userId);
						pstmt.setString(++bindPos, sessionId);
						pstmt.setString(++bindPos, deptId);
						pstmt.setString(++bindPos, user.getUserId());
					} else {
						pstmt.setInt(++bindPos, sysdocno);
						pstmt.setString(++bindPos, deptId);
						pstmt.setString(++bindPos, user.getUserId());
						pstmt.setString(++bindPos, user.getUserName());
						pstmt.setString(++bindPos, userId);
						pstmt.setInt(++bindPos, sysdocno);
						pstmt.setString(++bindPos, deptId);
						pstmt.setString(++bindPos, user.getUserId());
					}
					
					cnt += pstmt.executeUpdate();
					
					bindPos = 0;
					ConnectionManager.close(pstmt);
				}
			}
		} catch(Exception e){
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			ConnectionManager.close(pstmt);
		}
		return cnt;
	}
	
	public String getDesigNateView(int sysdocno, String sessionId, String deptcd, String type) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList userList = new ArrayList();
		String userListXML = "";
		int bindPos = 0;
		
		StringBuffer selectQuery = new StringBuffer();
		
		if(type.equals("1")){
			if(sysdocno == 0){
				selectQuery.append("\n SELECT T1.SESSIONID, T1.SEQ, T1.SANCUSRID,  		");
				selectQuery.append("\n        T1.SANCUSRNM, T2.DEPT_ID, T2.DEPT_NAME,	");
				selectQuery.append("\n        T1.GUBUN, T1.SANCYN, T2.CLASS_NAME		");		
				selectQuery.append("\n   FROM SANCCOL_TEMP T1,			       			");
				selectQuery.append("\n        ( SELECT A.USER_ID,        				");                                	
				selectQuery.append("\n                 A.USER_NAME,						");
				selectQuery.append("\n                 A.DEPT_ID,        				");                                   
				selectQuery.append("\n                 A.DEPT_NAME,						");
				selectQuery.append("\n        		   A.CLASS_NAME,					");
				selectQuery.append("\n        		   A.CHRGUNITCD,     				"); 		
				selectQuery.append("\n        		   A.GRADE_ID						");
				selectQuery.append("\n            FROM USR A							"); 
				selectQuery.append("\n         )T2										"); 
				selectQuery.append("\n  WHERE T1.SANCUSRID = T2.USER_ID(+)              ");
				selectQuery.append("\n    AND T1.SESSIONID LIKE ?                    		");
				selectQuery.append("\n    AND T2.DEPT_ID(+) = ?							");
				selectQuery.append("\n  ORDER BY T2.GRADE_ID 							");
			}else{
				selectQuery.append("\n SELECT T1.SYSDOCNO, T1.SEQ, T1.SANCUSRID,  		");
				selectQuery.append("\n        T1.SANCUSRNM, T2.DEPT_ID, T2.DEPT_NAME,	");
				selectQuery.append("\n        T1.GUBUN, T1.SANCYN, T2.CLASS_NAME		");				
				selectQuery.append("\n   FROM SANCCOL T1, 		        				");
				selectQuery.append("\n        ( SELECT A.USER_ID,        				");                                	
				selectQuery.append("\n                 A.USER_NAME,						");
				selectQuery.append("\n                 A.DEPT_ID,        				");                                   
				selectQuery.append("\n                 A.DEPT_NAME,						");
				selectQuery.append("\n        		   A.CLASS_NAME,					");
				selectQuery.append("\n        		   A.CHRGUNITCD,     				"); 		
				selectQuery.append("\n        		   A.GRADE_ID						");
				selectQuery.append("\n            FROM USR A							"); 
				selectQuery.append("\n         )T2										"); 			
				selectQuery.append("\n  WHERE T1.SANCUSRID = T2.USER_ID(+)              ");				
				selectQuery.append("\n    AND T1.SYSDOCNO = ?                    		");
				selectQuery.append("\n    AND T2.DEPT_ID(+) = ?							");
				selectQuery.append("\n  ORDER BY T2.GRADE_ID 							");
			}
		}else{
			selectQuery.append("\n SELECT T1.SYSDOCNO, T1.SEQ, T1.SANCUSRID,  		");
			selectQuery.append("\n        T1.SANCUSRNM, T2.DEPT_ID, T2.DEPT_NAME,	");
			selectQuery.append("\n        T1.GUBUN, T1.SANCYN, T2.CLASS_NAME 		");				
			selectQuery.append("\n   FROM SANCTGT T1, 		        				");
			selectQuery.append("\n        ( SELECT A.USER_ID,        				");                                	
			selectQuery.append("\n                 A.USER_NAME,						");
			selectQuery.append("\n                 A.DEPT_ID,        				");                                   
			selectQuery.append("\n                 A.DEPT_NAME,						");
			selectQuery.append("\n        		   A.CLASS_NAME,					");
			selectQuery.append("\n        		   A.CHRGUNITCD,     				"); 		
			selectQuery.append("\n        		   A.GRADE_ID						");
			selectQuery.append("\n            FROM USR A							"); 
			selectQuery.append("\n         )T2										"); 				
			selectQuery.append("\n  WHERE T1.SANCUSRID = T2.USER_ID(+)              ");			
			selectQuery.append("\n    AND T1.SYSDOCNO = ?                    		");
			selectQuery.append("\n    AND T1.TGTDEPTCD(+) = ?							");
			selectQuery.append("\n  ORDER BY T2.GRADE_ID 							");
		}

		
		try{
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if(sysdocno == 0){
				pstmt.setString(++bindPos, sessionId);
				pstmt.setString(++bindPos, deptcd);
			}else{
				pstmt.setInt(++bindPos, sysdocno);
				pstmt.setString(++bindPos, deptcd);
			}

			rs = pstmt.executeQuery();
			
			commapprovalBean bean = null;
			while(rs.next()){
				bean = new commapprovalBean();
				
				bean.setUserId(rs.getString("SANCUSRID"));
				bean.setUserName(rs.getString("SANCUSRNM"));
				bean.setDeptId(rs.getString("DEPT_ID"));
				bean.setDeptName(Utils.nullToEmptyString(rs.getString("DEPT_NAME")));
				bean.setGubun(rs.getString("GUBUN"));
				bean.setClassName(Utils.nullToEmptyString(rs.getString("CLASS_NAME")));
				bean.setSancYn(rs.getString("SANCYN"));

				userList.add(bean);
				
				bean = null;				
			}
			
			userListXML = getUserListXML(userList);
		}finally{
			ConnectionManager.close(con, pstmt, rs);
		}
		return userListXML;
	}
	
	public String getGradeListView(int rchno, String sessionId) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList userList = new ArrayList();
		String userListXML = "";
		int bindPos = 0;
		
		StringBuffer selectQuery = new StringBuffer();
		
		if ( rchno == 0 ) {
			selectQuery.append("SELECT TGTCODE, TGTNAME \n");
			selectQuery.append("FROM RCHOTHERTARGET_TEMP \n");
			selectQuery.append("WHERE TGTGBN = '1' \n");
			selectQuery.append("AND SESSIONID LIKE ? \n");
			selectQuery.append("ORDER BY TGTCODE, TGTNAME \n");
		} else {
			selectQuery.append("SELECT TGTCODE, TGTNAME \n");
			selectQuery.append("FROM RCHOTHERTARGET \n");
			selectQuery.append("WHERE TGTGBN = '1' \n");
			selectQuery.append("AND RCHNO = ? \n");
			selectQuery.append("ORDER BY TGTCODE, TGTNAME \n");
		}
		
		try{
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if ( rchno == 0 ) {
				pstmt.setString(++bindPos, sessionId);
			} else {
				pstmt.setInt(++bindPos, rchno);
			}

			rs = pstmt.executeQuery();
			
			commapprovalBean bean = null;
			while(rs.next()){
				bean = new commapprovalBean();
				
				bean.setTgtcode(rs.getString("TGTCODE"));
				bean.setTgtname(rs.getString("TGTNAME"));
				userList.add(bean);
				
				bean = null;				
			}
			
			userListXML = getGradeListXML(userList);
		}finally{
			ConnectionManager.close(con, pstmt, rs);
		}
		return userListXML;
	}
	
	public String getGradeListViewGrp(int rchgrpno, String sessionId) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList userList = new ArrayList();
		String userListXML = "";
		int bindPos = 0;
		
		StringBuffer selectQuery = new StringBuffer();
		
		if ( rchgrpno == 0 ) {
			selectQuery.append("SELECT TGTCODE, TGTNAME \n");
			selectQuery.append("FROM RCHGRPOTHERTARGET_TEMP \n");
			selectQuery.append("WHERE TGTGBN = '1' \n");
			selectQuery.append("AND SESSIONID LIKE ? \n");
			selectQuery.append("ORDER BY TGTCODE, TGTNAME \n");
		} else {
			selectQuery.append("SELECT TGTCODE, TGTNAME \n");
			selectQuery.append("FROM RCHGRPOTHERTARGET \n");
			selectQuery.append("WHERE TGTGBN = '1' \n");
			selectQuery.append("AND RCHGRPNO = ? \n");
			selectQuery.append("ORDER BY TGTCODE, TGTNAME \n");
		}
		
		try{
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if ( rchgrpno == 0 ) {
				pstmt.setString(++bindPos, sessionId);
			} else {
				pstmt.setInt(++bindPos, rchgrpno);
			}
			
			rs = pstmt.executeQuery();
			
			commapprovalBean bean = null;
			while(rs.next()){
				bean = new commapprovalBean();
				
				bean.setTgtcode(rs.getString("TGTCODE"));
				bean.setTgtname(rs.getString("TGTNAME"));
				userList.add(bean);
				
				bean = null;				
			}
			
			userListXML = getGradeListXML(userList);
		}finally{
			ConnectionManager.close(con, pstmt, rs);
		}
		return userListXML;
	}
	
	public List getResearchGrpView(int rchgrpno, String sessionId) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList userList = new ArrayList();
		
		StringBuffer selectQuery1 = new StringBuffer();
		StringBuffer selectQuery2 = new StringBuffer();
		
		if ( rchgrpno == 0 ) {
			selectQuery1.append("SELECT RCHNOLIST \n");
			selectQuery1.append("FROM RCHGRPMST_TEMP  \n");
			selectQuery1.append("WHERE SESSIONID LIKE ? \n");
		} else {
			selectQuery1.append("SELECT RCHNOLIST \n");
			selectQuery1.append("FROM RCHGRPMST  \n");
			selectQuery1.append("WHERE RCHGRPNO = ? \n");
		}
		selectQuery2.append("SELECT RCHNO, TITLE \n");
		selectQuery2.append("FROM RCHMST \n");
		selectQuery2.append("WHERE RCHNO = ? \n");
		
		try{
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery1.toString());
			
			if ( rchgrpno == 0 ) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, rchgrpno);
			}
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ) {
				String[] nolist = Utils.nullToEmptyString(rs.getString(1)).split(",");
				
				for ( int i = 0; i < nolist.length; i++ ) {
					ConnectionManager.close(pstmt, rs);
					pstmt = con.prepareStatement(selectQuery2.toString());
					
					pstmt.setString(1, nolist[i]);
					
					rs = pstmt.executeQuery();
					
					commapprovalBean bean = null;
					while(rs.next()){
						bean = new commapprovalBean();
						
						bean.setTgtcode(rs.getString("RCHNO"));
						bean.setTgtname(rs.getString("TITLE"));
						userList.add(bean);
						
						bean = null;				
					}
					
				}
			}
		} finally {
			ConnectionManager.close(con, pstmt, rs);
		}
		return userList;
	}
	
	public String getInputUsrView(int sysdocno, String deptcd) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList userList = new ArrayList();
		String userListXML = "";
		int bindPos = 0;
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("\n SELECT T1.SYSDOCNO, T1.TGTDEPT, T1.INPUTUSRID,  	");
		selectQuery.append("\n        T1.INPUTUSRNM, T2.DEPT_ID, T2.DEPT_NAME,	");
		selectQuery.append("\n        T2.CLASS_NAME, T1.INPUTSTATE, 			");
		selectQuery.append("\n		  T1.CHRGUNITNM								");
		selectQuery.append("\n   FROM INPUTUSR T1, 			       				");
		selectQuery.append("\n        ( SELECT A.USER_ID,        				");                                	
		selectQuery.append("\n                 A.USER_NAME,						");
		selectQuery.append("\n                 A.DEPT_ID,        				");                                   
		selectQuery.append("\n                 A.DEPT_NAME,						");
		selectQuery.append("\n        		   A.CLASS_NAME,					");
		selectQuery.append("\n        		   A.CHRGUNITCD,     				"); 		
		selectQuery.append("\n        		   A.GRADE_ID						");
		selectQuery.append("\n            FROM USR A							"); 
		selectQuery.append("\n         )T2										"); 	
		selectQuery.append("\n  WHERE T1.INPUTUSRID = T2.USER_ID(+)            	");
		selectQuery.append("\n    AND T1.SYSDOCNO = ?                    		");
		selectQuery.append("\n    AND T1.TGTDEPT = ?							");
		selectQuery.append("\n  ORDER BY T2.GRADE_ID 							");
		
		try{
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(++bindPos, sysdocno);
			pstmt.setString(++bindPos, deptcd);
			rs = pstmt.executeQuery();
			
			commapprovalBean bean = null;
			while(rs.next()){
				bean = new commapprovalBean();
				
				bean.setUserId(rs.getString("INPUTUSRID"));
				bean.setUserName(rs.getString("INPUTUSRNM"));
				bean.setDeptId(rs.getString("DEPT_ID"));
				bean.setDeptName(Utils.nullToEmptyString(rs.getString("DEPT_NAME")));
				bean.setClassName(Utils.nullToEmptyString(rs.getString("CLASS_NAME")));
				bean.setGubun("1");
				bean.setSancYn(rs.getString("INPUTSTATE"));
				bean.setChrgunitnm(rs.getString("CHRGUNITNM"));

				userList.add(bean);
				
				bean = null;				
			}
			
			userListXML = getUserListXML(userList);
		}finally{
			ConnectionManager.close(con, pstmt, rs);
		}
		return userListXML;
	}
	
	private String getUserListXML(ArrayList userList) throws Exception {
		
		StringBuffer userListXML = new StringBuffer();

		if (userList.size() > 0){
			
			commapprovalBean bean = null;
			for (int i = 0 ; i < userList.size() ; i++){
				bean = (commapprovalBean)userList.get(i);
				userListXML.append("\n	<data id=\"").append(bean.getUserId()).append("\">");				
				userListXML.append("\n		<userdata id=\"userId\">").append(bean.getUserId()).append("</userdata>");
				userListXML.append("\n		<userdata id=\"userName\">").append(bean.getUserName()).append("</userdata>");
				userListXML.append("\n		<userdata id=\"deptId\">").append(bean.getDeptId()).append("</userdata>");
				userListXML.append("\n		<userdata id=\"deptName\">").append(bean.getDeptName()).append("</userdata>");
				userListXML.append("\n		<userdata id=\"className\">").append(bean.getClassName()).append("</userdata>");
				userListXML.append("\n		<userdata id=\"gubun\">").append(bean.getGubun()).append("</userdata>");
				userListXML.append("\n		<userdata id=\"sancYn\">").append(bean.getSancYn()).append("</userdata>");	
				userListXML.append("\n	</data>");
				bean = null;
			}			
		}
		return (userListXML.toString());
	}
	
	public String getGradeListXML(List userList) throws Exception {
		
		StringBuffer userListXML = new StringBuffer();
		
		if (userList.size() > 0){
			
			commapprovalBean bean = null;
			for (int i = 0 ; i < userList.size() ; i++){
				bean = (commapprovalBean)userList.get(i);
				userListXML.append("\n	<data id=\"").append(bean.getTgtcode()).append("\">");				
				userListXML.append("\n		<userdata id=\"tgtcode\">").append(bean.getTgtcode()).append("</userdata>");
				userListXML.append("\n		<userdata id=\"tgtname\">").append(bean.getTgtname()).append("</userdata>");	
				userListXML.append("\n	</data>");
				bean = null;
			}			
		}
		return (userListXML.toString());
	}
	

	/**
	 * 취합부서 결재선 승인/검토 데이터 가져오기
	 * gubun: 검토(1),  승인(2)
	 * sysdocno : 시스템문서번호
	 * @throws Exception 
	 */
	public List getColApprInfo(String gubun, int sysdocno) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List apprInfoList = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT GUBUN, SANCUSRID, SANCUSRNM, SANCYN, SANCDT ");
			selectQuery.append("FROM SANCCOL ");
			selectQuery.append("WHERE SYSDOCNO = ? ");			
			selectQuery.append("   AND GUBUN = ? ");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);			
			pstmt.setString(2, gubun);
									
			rs = pstmt.executeQuery();
			 
			apprInfoList = new ArrayList();
			
			while(rs.next()) {
				commapprovalBean bean = new commapprovalBean();
				
				bean.setGubun(rs.getString("GUBUN"));
				bean.setUserId(rs.getString("SANCUSRID"));
				bean.setUserName(rs.getString("SANCUSRNM"));
				bean.setSancYn(rs.getString("SANCYN"));
				bean.setSancdt(rs.getString("SANCDT"));
				
				apprInfoList.add(bean);
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return apprInfoList;
	}
	
	/**
	 * 제출부서 결재선 승인/검토 데이터 가져오기
	 * gubun: 검토(1),  승인(2)
	 * sysdocno : 시스템문서번호
	 * deptcd : 부서코드
	 * @throws Exception 
	 */
	public List getTgtApprInfo(String gubun, int sysdocno, String deptcd) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List apprInfoList = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT GUBUN, SANCUSRID, SANCUSRNM, SANCYN, SANCDT, SUBMITDT ");
			selectQuery.append("FROM SANCTGT ");
			selectQuery.append(" WHERE SYSDOCNO = ? ");	
			selectQuery.append("   AND TGTDEPTCD = ? ");
			selectQuery.append("   AND GUBUN = ? ");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);	
			pstmt.setString(2, deptcd);
			pstmt.setString(3, gubun);
									
			rs = pstmt.executeQuery();
			 
			apprInfoList = new ArrayList();
			
			while(rs.next()) {
				commapprovalBean bean = new commapprovalBean();
				
				bean.setGubun(rs.getString("GUBUN"));
				bean.setUserId(rs.getString("SANCUSRID"));
				bean.setUserName(rs.getString("SANCUSRNM"));
				bean.setSancYn(rs.getString("SANCYN"));
				bean.setSancdt(rs.getString("SANCDT"));
				bean.setSubmitdt(rs.getString("SUBMITDT"));
				
				apprInfoList.add(bean);
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return apprInfoList;
	}
	
	/**
	 * [취합부서 용] 기안일시 가져오기 - 결재진행
	 * gubun : 승인(1), 검토(2)
	 * @throws Exception 
	 */
	public String getColSubmitDt(int sysdocno, String gubun, String sancuserid) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			//결재하지 않은 목록중 일자가 가장 작은 것
			selectQuery.append("SELECT SUBMITDT ");
			selectQuery.append("FROM SANCCOL ");
			selectQuery.append("WHERE SYSDOCNO = ? ");
			selectQuery.append("  AND GUBUN = ? ");
			selectQuery.append("  AND SANCUSRID = ? ");			
			selectQuery.append("  AND SANCYN = '0' ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			pstmt.setInt(1, sysdocno);		
			pstmt.setString(2, gubun);
			pstmt.setString(3, sancuserid);
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				result = rs.getString("SUBMITDT");
			}			
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return result;
	}
	
	/**
	 * [제출부서 용] 기안일시 가져오기 - 결재진행
	 * gubun : 승인(1), 검토(2)
	 * @throws Exception 
	 */
	public String getTgtSubmitDt(int sysdocno, String deptcd, String gubun, String sancuserid) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			//결재하지 않은 목록중 일자가 가장 작은 것
			selectQuery.append("SELECT SUBMITDT ");
			selectQuery.append("FROM SANCTGT ");
			selectQuery.append("WHERE SYSDOCNO = ? ");
			selectQuery.append("  AND TGTDEPTCD = ? ");
			selectQuery.append("  AND GUBUN = ? ");
			selectQuery.append("  AND SANCUSRID = ? ");			
			selectQuery.append("  AND SANCYN = '0' ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			pstmt.setInt(1, sysdocno);		
			pstmt.setString(2, deptcd);
			pstmt.setString(3, gubun);
			pstmt.setString(4, sancuserid);
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				result = rs.getString("SUBMITDT");
			}			
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return result;
	}
	
	/**
	 * 문서상태 및 결재 상태 가져오기
	 * @param sysdocno
	 * @param deptcd
	 * @param type
	 * state : 승인(1), 검토(2)
	 * @throws Exception 
	 */
	public String getState(int sysdocno, String deptcd, String type) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
		
		StringBuffer selectQuery = new StringBuffer();
		
		if(type.equals("1")){
			//취합부서결재인경우 
			selectQuery.append("\n SELECT DOCSTATE AS STATE	");
			selectQuery.append("\n   FROM DOCMST 			");
			selectQuery.append("\n  WHERE SYSDOCNO = ? 		");
		}else{
			//제출부서결재인경우 
			selectQuery.append("\n SELECT SUBMITSTATE AS STATE 	");
			selectQuery.append("\n   FROM TGTDEPT 				");
			selectQuery.append("\n  WHERE SYSDOCNO = ? 			");
			selectQuery.append("\n    AND TGTDEPTCD = ? 		");
		}
		
		try {
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if(type.equals("1")){
				pstmt.setInt(1, sysdocno);		
			}else{
				pstmt.setInt(1, sysdocno);		
				pstmt.setString(2, deptcd);
			}
			
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				result = rs.getString("STATE");
			}			
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return result;
	}	

	/**
	 * 
	 * @param reqformno
	 * @param reqseq
	 * @param deptcd
	 * @return
	 * @throws Exception
	 */
	public String getReqformView(int reqformno, int reqseq, String deptcd) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList userList = new ArrayList();
		String userListXML = "";
		int bindPos = 0;
		
		StringBuffer selectQuery = new StringBuffer();
		selectQuery.append("\n SELECT T1.REQSEQ, T1.SEQ, T1.SANCUSRID,  		");
		selectQuery.append("\n        T1.SANCUSRNM, T2.DEPT_ID, T2.DEPT_NAME,	");
		selectQuery.append("\n        T1.GUBUN, T1.SANCYN, T2.CLASS_NAME		");				
		selectQuery.append("\n   FROM REQSANC T1, 		        				");
		selectQuery.append("\n        ( SELECT A.USER_ID,        				");                                	
		selectQuery.append("\n                 A.USER_NAME,						");
		selectQuery.append("\n                 A.DEPT_ID,        				");                                   
		selectQuery.append("\n                 A.DEPT_NAME,						");
		selectQuery.append("\n        		   A.CLASS_NAME,					");
		selectQuery.append("\n        		   A.CHRGUNITCD,     				"); 		
		selectQuery.append("\n        		   A.GRADE_ID						");
		selectQuery.append("\n            FROM USR A							"); 
		selectQuery.append("\n         )T2										"); 			
		selectQuery.append("\n  WHERE T1.SANCUSRID = T2.USER_ID               	");	
		selectQuery.append("\n    AND T1.REQFORMNO = ?    						");			
		selectQuery.append("\n    AND T1.REQSEQ = ?                    			");
		selectQuery.append("\n    AND T2.DEPT_ID(+) = ?							");
		selectQuery.append("\n  ORDER BY T2.GRADE_ID 							");
	
		try{
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			pstmt.setInt(++bindPos, reqformno);
			pstmt.setInt(++bindPos, reqseq);
			pstmt.setString(++bindPos, deptcd);

			rs = pstmt.executeQuery();
			
			commapprovalBean bean = null;
			while(rs.next()){
				bean = new commapprovalBean();
				
				bean.setUserId(rs.getString("SANCUSRID"));
				bean.setUserName(rs.getString("SANCUSRNM"));
				bean.setDeptId(rs.getString("DEPT_ID"));
				bean.setDeptName(Utils.nullToEmptyString(rs.getString("DEPT_NAME")));
				bean.setGubun(rs.getString("GUBUN"));
				bean.setClassName(Utils.nullToEmptyString(rs.getString("CLASS_NAME")));
				bean.setSancYn(rs.getString("SANCYN"));

				userList.add(bean);
				
				bean = null;				
			}
			
			userListXML = getUserListXML(userList);
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		}finally{
			ConnectionManager.close(con, pstmt, rs);
		}
		return userListXML;
	}	
	
	public int commreqformInsert( int reqformno, int reqseq, ArrayList userList, String deptId, String usrid ) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int bindPos = 0;
		int cnt = 0;
		//int result = 0;
		int[] ret = null;
		
		int maxSeq = 0;
		
		StringBuffer insertQuery = new StringBuffer();
		StringBuffer deleteQuery = new StringBuffer();	
		StringBuffer selectQuery = new StringBuffer();

		deleteQuery.append("\n DELETE FROM REQSANC		");
		deleteQuery.append("\n  WHERE REQFORMNO = ?		");	
		deleteQuery.append("\n    AND REQSEQ = ?		");
		deleteQuery.append("\n    AND SANCYN = '0'		");	

		selectQuery.append("\n SELECT NVL(MAX(SEQ),0) 	");
		selectQuery.append("\n   FROM REQSANC 			");
		selectQuery.append("\n  WHERE REQFORMNO = ?		");	
		selectQuery.append("\n    AND REQSEQ = ?		");
		
		insertQuery.append("\n INSERT INTO REQSANC                    							");
		insertQuery.append("\n             (REQFORMNO, REQSEQ, SEQ,               				");
		insertQuery.append("\n              GUBUN, SANCUSRID, SANCUSRNM,    					");
		insertQuery.append("\n              SANCYN, CRTDT, CRTUSRID 							");
		insertQuery.append("\n             )                                					");
		insertQuery.append("\n      VALUES (?, ?, ?,             								");
		insertQuery.append("\n              ?, ?, ?, 											");
		insertQuery.append("\n              '0', TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), ? 	");
		insertQuery.append("\n             )                                					");
		
		try{
			con = ConnectionManager.getConnection();
			
			con.commit();
			con.setAutoCommit(false);
			
			/*취합결재선 지정 삭제처리*/
			pstmt = con.prepareStatement(deleteQuery.toString());	
		    
	    	pstmt.setInt(++bindPos, reqformno);
			pstmt.setInt(++bindPos , reqseq);
		
			if(pstmt.executeUpdate()>0){
				cnt++;
			}
			bindPos = 0;
			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}
			
			/*취합결제선 max값가져오기*/
			pstmt =	con.prepareStatement(selectQuery.toString());
			
			pstmt.setInt(++bindPos, reqformno);
			pstmt.setInt(++bindPos, reqseq);

			rs = pstmt.executeQuery();
			bindPos = 0;
			
			if ( rs.next() ){
				maxSeq = rs.getInt(1);
			}
			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}
			
			/*취합결제선 지정 등록*/
			if (userList.size() > 0){
				pstmt = con.prepareStatement(insertQuery.toString());
				
				commapprovalBean user = null;
				for (int i = 0 ; i < userList.size() ; i++){
					user = (commapprovalBean)userList.get(i);
					
					pstmt.setInt(++bindPos, reqformno);
					pstmt.setInt(++bindPos, reqseq);
					pstmt.setInt(++bindPos, ++maxSeq);
					pstmt.setString(++bindPos, user.getGubun());
					pstmt.setString(++bindPos, user.getUserId());
					pstmt.setString(++bindPos, user.getUserName());
					pstmt.setString(++bindPos, usrid);
					
					pstmt.addBatch();
					
					bindPos = 0;
				}

				ret = pstmt.executeBatch();
				cnt = ret.length;
			}
			con.commit();
		} catch(Exception e){
			con.rollback();
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			con.setAutoCommit(true);
			ConnectionManager.close(con, pstmt, rs);
		}
		return cnt;
	}	
}
