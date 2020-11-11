/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 고정양식형 dao
 * 설명:
 */
package nexti.ejms.formatFixed.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import nexti.ejms.util.Utils;
import nexti.ejms.colldoc.model.ColldocBean;
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.common.ConnectionManager;
import nexti.ejms.common.appInfo;
import nexti.ejms.commtreat.model.CommTreatManager;
import nexti.ejms.dept.model.DeptManager;
import nexti.ejms.format.model.FormatBean;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.format.model.InputDeptSearchBoxBean;

public class FormatFixedDAO {
	
	private static Logger logger = Logger.getLogger(FormatFixedDAO.class);
	
	public List getListTempDeptData(Connection con, String type, int count) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List result = null;
		
		try {
			String sql =
				"SELECT DEPT_ID, DEPT_NAME " +
				"FROM DEPT " +
				"WHERE DEPT_NAME LIKE ? " +
				"  AND ROWNUM <= ? " +
				"ORDER BY TO_NUMBER(DEPT_RANK)";
		
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, "%" + type);
			pstmt.setInt(2, count);
	
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if(result == null) {
					result = new ArrayList();
				}
				
				String[] deptInfo = new String[2];
				
				deptInfo[0] = rs.getString("DEPT_ID");
				deptInfo[1] = rs.getString("DEPT_NAME");
				
				result.add(deptInfo);
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt,rs);
			throw e;
		} finally {
			try {rs.close();} catch(Exception e) {}
			try {pstmt.close();} catch(Exception e) {}
		}
		
		return result;
	}
	
	/**
	 * 양식 미리보기 임시데이터 저장
	 * @param Connection conn
	 * @param FormatBean ffbean
	 * @param String deptnm
	 * @return int 수행개수
	 * @throws Exception 
	 */
	public int addFormatFixedPreviewTempData(Connection con, FormatBean fbean, String deptnm) throws Exception {
		
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			String query =
				"INSERT INTO " +
				"TGTDEPT(SYSDOCNO, TGTDEPTCD, TGTDEPTNM, SUBMITSTATE) " +
				"VALUES(?, ?, ?, ?)";
		
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, fbean.getSysdocno());
			pstmt.setString(2, fbean.getDeptcd());
			pstmt.setString(3, deptnm);
			pstmt.setString(4, "05");
	
			result = pstmt.executeUpdate();			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
		
		return result;
	}
	
	/**
	 * 양식 미리보기 임시데이터 삭제
	 * @param Connection conn
	 * @param int sysdocno
	 * @return int 수행개수
	 * @throws Exception 
	 */
	public int delFormatFixedPreveiwTempData(Connection con, int sysdocno) throws Exception {
		
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {			
			String query =
				"DELETE " +
				"FROM TGTDEPT " +
				"WHERE SYSDOCNO = ?";
		
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, sysdocno);
	
			result = pstmt.executeUpdate();	
		} catch(Exception e) {
			logger.error("ERROR", e);

			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
		
		return result;
	}
	
	/**
	 * 양식 입력속성 수정하기
	 * @param Connection conn
	 * @param FormatFixedBean bean
	 * @return int 수행개수
	 * @throws Exception 
	 */
	public int modifyFormatFixed(Connection con, FormatFixedBean bean) throws Exception {
		
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {			
			StringBuffer colValue = new StringBuffer();
			String[] cellatt = bean.getCellatt(); 
			String col = "";
			
			for(int i = 0; i < bean.getTblcols(); i++) {
				if(i < 26) {
					col = (char)('A' + i) + "";
				}
				else {
					col = (char)('A' + i - 26) + "" + (char)('A' + i - 26);
				}
				
				colValue.append(col + "='" + cellatt[i] + "'");
				
				if(i + 1  < bean.getTblcols()) {
					colValue.append(", ");
				}
			}
			
			String query =
					"UPDATE ATTFIXEDFRM " +
					"SET " + colValue.toString() + " " +
					"WHERE SYSDOCNO = ? " +
					"  AND FORMSEQ = ? ";
			
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, bean.getSysdocno());
			pstmt.setInt(2, bean.getFormseq());

			result = pstmt.executeUpdate();
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
		
		return result;
	}
	
	/**
	 * 공통양식 입력속성 수정하기
	 * @param Connection conn
	 * @param FormatFixedBean bean
	 * @return int 수행개수
	 * @throws Exception 
	 */
	public int modifyCommFormatFixed(Connection con, FormatFixedBean bean) throws Exception {
		
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {			
			StringBuffer colValue = new StringBuffer();
			String[] cellatt = bean.getCellatt(); 
			String col = "";
			
			for(int i = 0; i < bean.getTblcols(); i++) {
				if(i < 26) {
					col = (char)('A' + i) + "";
				}
				else {
					col = (char)('A' + i - 26) + "" + (char)('A' + i - 26);
				}
				
				colValue.append(col + "='" + cellatt[i] + "'");
				
				if(i + 1  < bean.getTblcols()) {
					colValue.append(", ");
				}
			}
			
			String query =
					"UPDATE COMMATTFIXEDFRM " +
					"SET " + colValue.toString() + " " +
					"WHERE FORMSEQ = ? ";
			
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, bean.getFormseq());

			result = pstmt.executeUpdate();
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
		
		return result;
	}
	
	/**
	 * 고정양식형 양식속성 가져오기
	 * @param int sysdocno 
	 * @param int formseq
	 * @return String[] 양식정보
	 * @throws Exception
	 */
	public String[] getFormatFixedAtt(int sysdocno, int formseq) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String[] result = null;
		
		try {			
			String query =
				"SELECT A, B, C, D, E, F, G, H, I, J, K, L, M, " +
				"       N, O, P, Q, R, S, T, U, V, W, X, Y, Z, " +
				"       AA, BB, CC, DD, EE, FF, GG, HH, II, JJ, KK, LL, MM, " +
				"       NN, OO, PP, QQ, RR, SS, TT, UU, VV, WW, XX, YY, ZZ " +
				"FROM ATTFIXEDFRM " +
				"WHERE SYSDOCNO = ? " +
				"  AND FORMSEQ = ? ";
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
			
			rs = pstmt.executeQuery();

			if(rs.next() == true) {
				ArrayList arr = new ArrayList();
				
				for(int i = 1; i <= 52; i++) {
					if(rs.getString(i) != null) {
						arr.add(new String(rs.getString(i)));
					} else {
						break;
					}
				}
				
				result = new String[arr.size()];
				result = (String[])arr.toArray(result);
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(con, pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 고정양식형 공통양식속성 가져오기
	 * @param int formseq
	 * @return String[] 공통양식정보
	 * @throws Exception
	 */
	public String[] getCommFormatFixedAtt(int formseq) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String[] result = null;
		
		try {			
			String query =
				"SELECT A, B, C, D, E, F, G, H, I, J, K, L, M, " +
				"       N, O, P, Q, R, S, T, U, V, W, X, Y, Z, " +
				"       AA, BB, CC, DD, EE, FF, GG, HH, II, JJ, KK, LL, MM, " +
				"       NN, OO, PP, QQ, RR, SS, TT, UU, VV, WW, XX, YY, ZZ " +
				"FROM COMMATTFIXEDFRM " +
				"WHERE FORMSEQ = ? ";
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, formseq);
			
			rs = pstmt.executeQuery();

			if(rs.next() == true) {
				ArrayList arr = new ArrayList();
				
				for(int i = 1; i <= 52; i++) {
					if(rs.getString(i) != null) {
						arr.add(new String(rs.getString(i)));
					} else {
						break;
					}
				}
				
				result = new String[arr.size()];
				result = (String[])arr.toArray(result);
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(con, pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 양식 입력속성 저장하기
	 * @param Connection conn
	 * @param FormatFixedBean bean
	 * @return int 수행개수
	 * @throws Exception 
	 */
	public int addFormatFixed(Connection con, FormatFixedBean bean) throws Exception {

		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {			
			StringBuffer colName = new StringBuffer();
			StringBuffer colValue = new StringBuffer();
			String[] cellatt = bean.getCellatt(); 
			String col = "";
			
			for(int i = 0; i < bean.getTblcols(); i++) {
				if(i < 26) {
					col = (char)('A' + i) + "";
				}
				else {
					col = (char)('A' + i - 26) + "" + (char)('A' + i - 26);
				}
				
				colName.append(col);
				colValue.append("'" + cellatt[i] + "'");
				
				if(i + 1  < bean.getTblcols()) {
					colName.append(", ");
					colValue.append(", ");
				}
			}
			
			String query =
					"INSERT INTO ATTFIXEDFRM(SYSDOCNO, FORMSEQ, " + colName + ") " +
					"VALUES(?, ?, " + colValue + ")" ;
			
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, bean.getSysdocno());
			pstmt.setInt(2, bean.getFormseq());

			result = pstmt.executeUpdate();
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close( pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
		
		return result;
	}
	
	/**
	 * 공통양식 입력속성 저장하기
	 * @param Connection conn
	 * @param FormatFixedBean bean
	 * @return int 수행개수
	 * @throws Exception 
	 */
	public int addCommFormatFixed(Connection con, FormatFixedBean bean) throws Exception {

		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {			
			StringBuffer colName = new StringBuffer();
			StringBuffer colValue = new StringBuffer();
			String[] cellatt = bean.getCellatt(); 
			String col = "";
			
			for(int i = 0; i < bean.getTblcols(); i++) {
				if(i < 26) {
					col = (char)('A' + i) + "";
				}
				else {
					col = (char)('A' + i - 26) + "" + (char)('A' + i - 26);
				}
				
				colName.append(col);
				colValue.append("'" + cellatt[i] + "'");
				
				if(i + 1  < bean.getTblcols()) {
					colName.append(", ");
					colValue.append(", ");
				}
			}
			
			String query =
					"INSERT INTO COMMATTFIXEDFRM(FORMSEQ, " + colName + ") " +
					"VALUES(?, " + colValue + ")" ;
			
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, bean.getFormseq());

			result = pstmt.executeUpdate();
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
		
		return result;
	}
	
	/**
	 * 공통 양식자료 보기 - 양식폼 보여주기 데이터 가져오기
	 * 양식폼 가져오기
	 * 
	 * @param sysdocno : 시스템문서번호
	 * @param formseq : 양식일련번호
	 * 
	 * @return FormatFixedBean
	 * @throws Exception 
	 */
	public FormatFixedBean getFormatFormView(int sysdocno, int formseq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		FormatFixedBean bean = new FormatFixedBean();
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("\n SELECT FORMCOMMENT, TBLCOLS, TBLROWS				");
		selectQuery.append("\n   FROM FORMMST 									");
		selectQuery.append("\n  WHERE SYSDOCNO = ? 								");
		selectQuery.append("\n    AND FORMKIND = '02'							");
		selectQuery.append("\n    AND FORMSEQ = ?    							");
		
		try {

			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
									
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				bean.setFormcomment(rs.getString("FORMCOMMENT"));
				bean.setTblcols(rs.getInt("TBLCOLS"));
				bean.setTblrows(rs.getInt("TBLROWS"));
				
				ConnectionManager.close(pstmt, rs);
    			String[] columnName = {"FORMHTML", "FORMHEADERHTML", "FORMBODYHTML", "FORMTAILHTML"};
    			String[] columnData = new String[4];
    			for ( int i = 0; i < columnName.length; i++ ) {
    				String sql = "SELECT " + columnName[i] + " FROM FORMMST WHERE SYSDOCNO = ? AND FORMSEQ = ?";
    				pstmt = con.prepareStatement(sql);
    				pstmt.setInt(1, sysdocno);
    				pstmt.setInt(2, formseq);
    				rs = pstmt.executeQuery();
    				if ( rs.next() ) {
    					columnData[i] = Utils.readClobData(rs, columnName[i]);
    				}
    				ConnectionManager.close(pstmt, rs);
    			}
    			
        		bean.setFormhtml(columnData[0]);
        		bean.setFormheaderhtml(columnData[1]);
        		bean.setFormbodyhtml(columnData[2]);
        		bean.setFormtailhtml(columnData[3]);
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return bean;
	}
	
	/**
	 * 양식마스터 - 고정양식형 데이터 가져오기(특정 필드 데이터만)
	 * 
	 * @param sysdocno : 시스템문서번호
	 * @param formseq : 양식일련번호
	 * @param colname : 가져올 필드
	 * @param usenvl : NVL 사용여부
	 * 
	 * @return Object : FORMMST 테이블 각 필드 데이터
	 * @throws Exception 
	 */
	public Object getFormatFixedView(int sysdocno, int formseq, String colname, boolean usenvl) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Object obj = new Object();
		
		StringBuffer selectQuery = new StringBuffer();
		

		selectQuery.append("\n SELECT " + colname);
		selectQuery.append("\n   FROM FORMMST 									");
		selectQuery.append("\n  WHERE SYSDOCNO = ? 								");
		selectQuery.append("\n    AND FORMKIND = '02'							");
		selectQuery.append("\n    AND FORMSEQ = ?								");
		
		try {

			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
									
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(colname.toUpperCase().equals("FORMHTML")
						|| colname.toUpperCase().equals("FORMHEADERHTML")
						|| colname.toUpperCase().equals("FORMBODYHTML")
						|| colname.toUpperCase().equals("FORMTAILHTML")) {
					
					obj = (Object)Utils.readClobData(rs, colname);
				}
				else {
					obj = rs.getObject(colname);
				}
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return (usenvl && obj == null) ? "" : obj;
	}
	
	/**
	 * 공통 양식자료 보기 - 양식폼내용에 따른 데이터 보여주기 가져오기
	 * 양식폼 가져오기
	 * 
	 * @param sysdocno : 시스템문서번호
	 * @param formseq : 양식일련번호
	 * 
	 * @return List
	 * @throws Exception 
	 */
	public List getFormDataList(int sysdocno, int formseq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List datalist = null;
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("SELECT SYSDOCNO \n");
		selectQuery.append("     , A.SYSDOCNO \n");
		selectQuery.append("     , B.FORMTITLE \n");
		selectQuery.append("     , B.FORMKIND \n");
		selectQuery.append("     , (SELECT CCDNAME FROM CCD WHERE CCDCD = '002' AND CCDSUBCD = B.FORMKIND ) FORMKINDNAME \n");
		selectQuery.append("  FROM DOCMST A, FORMMST B \n");
		selectQuery.append(" WHERE A.SYSDOCNO = B.SYSDOCNO \n");
		selectQuery.append("   AND A.SYSDOCNO = ? \n");
		selectQuery.append(" ORDER BY B.FORMKIND ");
		
		try {

			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
									
			rs = pstmt.executeQuery();
			
			datalist = new ArrayList();
			
			while(rs.next()) {
				FormatFixedBean frmBean = new FormatFixedBean();
				
				frmBean.setSeqno(rs.getInt("SEQ"));
				frmBean.setSysdocno(rs.getInt("SYSDOCNO"));
				frmBean.setFormtitle(rs.getString("FORMTITLE"));
				frmBean.setFormkind(rs.getString("FORMKIND"));
				frmBean.setFormkindname(rs.getString("FORMKINDNAME"));
				
				datalist.add(frmBean);
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return datalist;
	}
	
	public String getAttFixedFrmData(Connection con, int sysdocno, int formseq, String colname) throws Exception {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String result = "";
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("\n SELECT " + colname + " " + colname);
		selectQuery.append("\n   FROM ATTFIXEDFRM 		");
		selectQuery.append("\n  WHERE SYSDOCNO = ? 		");
		selectQuery.append("\n    AND FORMSEQ = ?		");
		
		try {			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
									
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getString(colname);
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close( pstmt, rs);
			throw e;
		} finally {
			try {rs.close();} catch(Exception e) {}
			try {pstmt.close();} catch(Exception e) {}
		}	
		
		return result;
	}
	
	/**
	 * 고정양식형 속성 가져오기
	 * 
	 * @param sysdocno : 시스템문서번호
	 * @param formseq : 양식일련번호
	 * @param colname : 가져올 칼럼명
	 * 
	 * @return String
	 * @throws Exception 
	 */
	public String getAttFixedFrmData(int sysdocno, int formseq, String colname) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String result = "";
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("\n SELECT " + colname + " " + colname);
		selectQuery.append("\n   FROM ATTFIXEDFRM 		");
		selectQuery.append("\n  WHERE SYSDOCNO = ? 		");
		selectQuery.append("\n    AND FORMSEQ = ?		");
		
		try {

			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
									
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getString(colname);
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return result;
	}
	
	/**
	 * 고정양식형 데이터 가져오기
	 * 
	 * @param sysdocno : 시스템문서번호
	 * @param formseq : 양식일련번호
	 * @param deptcd : 부서코드
	 * @param usrid : 사용자ID
	 * @param chrgunitcd : 담당단위코드
	 * @param colname : 가져올 칼럼명
	 * 
	 * @return String : 필드데이터
	 * @throws Exception 
	 */
	public String getDataFixedFrm(int sysdocno, int formseq, int rowseq, InputDeptSearchBoxBean idsbbean, String colname, boolean isIncludeNotSubmitData) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String result = "";
		
		StringBuffer sql = new StringBuffer();
		
		if ( idsbbean.getSch_inputusrid().equals("") == false ) {
			sql.append("SELECT " + colname + " " + colname + " \n");
		} else {
			sql.append("SELECT SUM(" + colname + ") " + colname + " \n");
		}
		sql.append("FROM DATAFIXEDFRM DF, TGTDEPT T, DEPT D   \n");
		sql.append("WHERE DF.SYSDOCNO = T.SYSDOCNO            \n");
		sql.append("AND DF.TGTDEPTCD = T.TGTDEPTCD            \n");
		sql.append("AND DF.TGTDEPTCD = D.DEPT_ID(+)           \n");
		sql.append("AND DF.SYSDOCNO = ?                       \n");
		sql.append("AND FORMSEQ = ?                           \n");		
		sql.append("AND NVL(DEPTCD1, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD2, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD3, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD4, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD5, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD6, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(CHRGUNITCD, '0') LIKE NVL(?, '%') \n");
		sql.append("AND INPUTUSRID LIKE NVL(?, '%')           \n");
		sql.append("AND ROWSEQ = " + rowseq + "               \n");
		if ( !isIncludeNotSubmitData ) {
			sql.append("AND T.SUBMITSTATE = '05'              \n");
		}
		sql.append("ORDER BY TO_NUMBER(NVL(DECODE(DF.TGTDEPTCD, '0', 0, DEPT_RANK), '0')), DEPT_NAME, ROWSEQ, INPUTUSRID \n");
		
		try {
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(sql.toString());
			int idx = 0;
			pstmt.setInt(++idx, sysdocno);
			pstmt.setInt(++idx, formseq);
			pstmt.setString(++idx, idsbbean.getSch_deptcd1());
			pstmt.setString(++idx, idsbbean.getSch_deptcd2());
			pstmt.setString(++idx, idsbbean.getSch_deptcd3());
			pstmt.setString(++idx, idsbbean.getSch_deptcd4());
			pstmt.setString(++idx, idsbbean.getSch_deptcd5());
			pstmt.setString(++idx, idsbbean.getSch_deptcd6());
			pstmt.setString(++idx, idsbbean.getSch_chrgunitcd());
			pstmt.setString(++idx, idsbbean.getSch_inputusrid());
									
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getString(colname);
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}
		
		if(result != null && result.trim().toLowerCase().equals("null") == true) {
			result = null;
		}
		return result;
	}
	
	/**
	 * 고정양식형 문자열 데이터 가져오기
	 * 
	 * @param sysdocno : 시스템문서번호
	 * @param formseq : 양식일련번호
	 * @param deptcd : 부서코드
	 * @param usrid : 사용자ID
	 * @param chrgunitcd : 담당단위코드
	 * @param colname : 가져올 칼럼명
	 * 
	 * @return String : 필드데이터
	 * @throws Exception 
	 */
	public String getStringDataFixedFrm(int sysdocno, int formseq, int rowseq, InputDeptSearchBoxBean idsbbean, String colname, boolean isIncludeNotSubmitData) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String result = "";
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT " + colname + " " + colname + "    \n");
		sql.append("FROM DATAFIXEDFRM DF, TGTDEPT T, DEPT D   \n");
		sql.append("WHERE DF.SYSDOCNO = T.SYSDOCNO            \n");
		sql.append("AND DF.TGTDEPTCD = T.TGTDEPTCD            \n");
		sql.append("AND DF.TGTDEPTCD = D.DEPT_ID(+)           \n");
		sql.append("AND DF.SYSDOCNO = ?                       \n");
		sql.append("AND FORMSEQ = ?                           \n");			
		sql.append("AND NVL(DEPTCD1, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD2, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD3, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD4, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD5, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD6, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(CHRGUNITCD, '0') LIKE NVL(?, '%') \n");
		sql.append("AND INPUTUSRID LIKE NVL(?, '%')           \n");
		if ( rowseq != 0 ) {
			sql.append("AND ROWSEQ = " + rowseq + "           \n");
		}
		if ( !isIncludeNotSubmitData ) {
			sql.append("AND T.SUBMITSTATE = '05'              \n");
		}
		sql.append("ORDER BY TO_NUMBER(NVL(DECODE(T.TGTDEPTCD, '0', 0, DEPT_RANK), '0')), DEPT_NAME, ROWSEQ, INPUTUSRID \n");
		
		try {
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(sql.toString());
			int idx = 0;
			pstmt.setInt(++idx, sysdocno);
			pstmt.setInt(++idx, formseq);
			pstmt.setString(++idx, idsbbean.getSch_deptcd1());
			pstmt.setString(++idx, idsbbean.getSch_deptcd2());
			pstmt.setString(++idx, idsbbean.getSch_deptcd3());
			pstmt.setString(++idx, idsbbean.getSch_deptcd4());
			pstmt.setString(++idx, idsbbean.getSch_deptcd5());
			pstmt.setString(++idx, idsbbean.getSch_deptcd6());
			pstmt.setString(++idx, idsbbean.getSch_chrgunitcd());
			pstmt.setString(++idx, idsbbean.getSch_inputusrid());
									
			rs = pstmt.executeQuery();
			
			if ( idsbbean.getSch_inputusrid().equals("") == true ) {
				StringBuffer sb = new StringBuffer();
				while(rs.next()) {
					String rsStr = Utils.nullToEmptyString(rs.getString(colname));
					if(sb.toString().trim().equals("") == true) {
						sb.append(rsStr);
					} else {
						sb.append("\r\n" + rsStr);
					}
				}
				result = sb.toString();
			} else {
				if(rs.next()) {
					result = Utils.nullToEmptyString(rs.getString(colname));
				}
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return result;
	}
	
	/** 
	 * 입력하기, 입력완료 - 고정양식형 데이터 삭제
	 * 
	 * @param conn : Connection 객체
	 * @param sysdocno : 시스템문서번호
	 * @param formseq : 양식일련번호
	 * @param deptcd : 부서코드
	 * @param usrid : 사용자ID
	 * 
	 * @return int : 결과코드
	 * @throws Exception 
	 */
	public int deleteFormatFixedData(Connection con, int sysdocno, int formseq, String deptcd, String usrid) throws Exception {
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		StringBuffer deleteQuery = new StringBuffer();
		deleteQuery.append("\n DELETE                 ");
		deleteQuery.append("\n   FROM DATAFIXEDFRM    ");
		deleteQuery.append("\n  WHERE SYSDOCNO   = ?  ");
		deleteQuery.append("\n    AND FORMSEQ    = ?  ");
		deleteQuery.append("\n    AND TGTDEPTCD  = ?  ");
		deleteQuery.append("\n    AND INPUTUSRID = ?  ");
		
		try{
			pstmt = con.prepareStatement(deleteQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
			pstmt.setString(3, deptcd);
			pstmt.setString(4, usrid);
				
			result = pstmt.executeUpdate();
		} catch(Exception e){
			logger.error("ERROR", e);
			ConnectionManager.close( pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
		
		return result;
	}
	
	/** 
	 * 입력하기, 입력완료 - 고정양식형 데이터 내용삭제
	 * 
	 * @param conn : Connection 객체
	 * @param sysdocno : 시스템문서번호
	 * @param formseq : 양식일련번호
	 * @param deptcd : 부서코드
	 * @param usrid : 사용자ID
	 * 
	 * @return int : 결과코드
	 * @throws Exception 
	 */
	public int deleteAllFormatFixedData(int sysdocno, int formseq, String deptcd, String usrid) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;

		StringBuffer deleteQuery = new StringBuffer();
		deleteQuery.append("\n DELETE                 	");
		deleteQuery.append("\n   FROM DATAFIXEDFRM     	");
		deleteQuery.append("\n  WHERE SYSDOCNO   = ?  	");
		deleteQuery.append("\n    AND FORMSEQ    = ?  	");
		deleteQuery.append("\n    AND TGTDEPTCD  = ?  	");
		deleteQuery.append("\n    AND INPUTUSRID = ?  	");
		
		try{
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(deleteQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
			pstmt.setString(3, deptcd);
			pstmt.setString(4, usrid);
				
			result = pstmt.executeUpdate();
		} catch(Exception e){
			logger.error("ERROR", e);
			ConnectionManager.close(con, pstmt);
			throw e;
		}finally {
			ConnectionManager.close(con,pstmt);
		}
		return result;
	}
	
	/** 
	 * 입력하기, 입력완료 - 고정양식형 데이터 저장
	 * 
	 * @param conn : Connection 객체
	 * @param insertQuery : 입력쿼리문
	 * 
	 * @return int : 결과코드
	 * @throws Exception 
	 */
	public int insertFormatFixedData(Connection con, StringBuffer insertQuery) throws Exception {
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		try{
			pstmt = con.prepareStatement(insertQuery.toString());
				
			result = pstmt.executeUpdate();
		} catch(Exception e){
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
		
		return result;
	}
	
	/**
	 * 공통 양식자료 보기 - 양식폼내용에 따른 데이터 보여주기 가져오기
	 * 양식폼 가져오기
	 * @throws Exception 
	 */
	public List getFormFixedDataList(FormatFixedBean schBean) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List datalist = null;
		int sysdocno = schBean.getSysdocno();
		int formseq = schBean.getFormseq();
		InputDeptSearchBoxBean idsbbean = new InputDeptSearchBoxBean();
		BeanUtils.copyProperties(idsbbean, schBean);
		boolean totalState = schBean.isTotalState();
		boolean totalShowStringState = schBean.isTotalShowStringState();
		boolean subtotalState = schBean.isSubtotalState();
		boolean subtotalShowStringState = schBean.isSubtotalShowStringState();
		boolean isIncludeNotSubmitData = schBean.isIncludeNotSubmitData();
		
		StringBuffer sql = new StringBuffer();
		String cellname;
		String pretgtdept = "";
		int k = 0;
		
		/*컬럼수및 행수 가져오기*/
		int cols = getFormatFormView(sysdocno, formseq).getTblcols();
		int rows = getFormatFormView(sysdocno, formseq).getTblrows();
		
		String lbody = getFormatFormView(sysdocno, formseq).getFormbodyhtml();
		String lrow = "";
		String lstart = "";
		String lend = "";
		
		/*소계추가하기*/ 
		if (rows > 1 && subtotalState == true) { 
			int startIdx = 0;
			int startIdx1 = 0;
			int startIdx2 = 0;
			int rowcount = 0;
			
			startIdx = lbody.indexOf("</tr>", 0);
			lrow = lbody.substring(0,(startIdx+5));
			
			startIdx = lrow.indexOf("$");	
			lstart = lrow.substring(0,startIdx);
			lend = lrow.substring(startIdx,lrow.length());
			
			for(int i=0; i< startIdx; i++){
				startIdx1 = lstart.indexOf("<td",rowcount);
				rowcount = startIdx1  + 3;
			    startIdx2 = lstart.indexOf(">", startIdx1);
			    startIdx1 = lstart.indexOf("</", startIdx2);
			    if(startIdx1 != -1){
			    	lstart = lstart.substring(0,(startIdx2+1)) + lstart.substring(startIdx1,lstart.length());
			    }
	
				startIdx1 = lstart.indexOf("rowspan=\"",0);
				if(startIdx1 == -1){
					break;
				}
				startIdx2 = lstart.indexOf("\"",(startIdx1+9));
				lstart = lstart.substring(0,startIdx1) + lstart.substring((startIdx2+1),lstart.length());
			}
			lrow = lstart + lend;
			
			//열머리글수 가져와서 [소계]란 추가
			String formhtml = getFormatFixedView(sysdocno, formseq, "FORMHTML", true).toString();
			FormatFixedManager ffmgr = FormatFixedManager.instance();
			int colHeadCount = ffmgr.FormatFixedHtmlSeparator(formhtml).getColHeadCount();
			int findIndex = lrow.indexOf("$");
			String colHeadAtt = "bgcolor=\"rgb(240,240,255)\"";
			lrow = lrow.substring(0, lrow.indexOf(">") + 1) +
					"<td colspan='" + colHeadCount + "' " + colHeadAtt + ">소계</td>" +
					lrow.substring(lrow.lastIndexOf("</td>", findIndex) + "</td>".length());
		}
		
		lbody = lrow + lbody;
			
		/*sql생성*/
		for(int i = 0; i < cols; i++) {
			if(i < 26) {
				cellname = (char)('A' + i) + "";
			} else {
				cellname = (char)('A' + i - 26) + "" + (char)('A' + i - 26);
			}
			
			if(i == 0){
				sql.append("SELECT NVL(NVL(Y.DEPT_FULLNAME, Z.TGTDEPTNM), '총계') AS TGTDEPTNM, X.*, TO_NUMBER(NVL(Y.DEPT_RANK, '0')) AS DEPT_RANK \n");
				sql.append("FROM (SELECT NVL(TGTDEPTCD,'0') AS TGTDEPTCD, ROWSEQ, \n");
				sql.append("             SUM(" + cellname + ") AS " + cellname + " \n");
			}else{
				sql.append("             , SUM(" + cellname + ") AS " + cellname + " \n");
			}
		}

		for(int i = 0; i < cols; i++) {
			if(i < 26) {
				cellname = (char)('A' + i) + "";
			} else {
				cellname = (char)('A' + i - 26) + "" + (char)('A' + i - 26);
			}
			if(i == 0){
				sql.append("      FROM (SELECT B.TGTDEPTCD, NVL(ROWSEQ,0) AS ROWSEQ, \n");
				sql.append("                   SUM(NVL(DECODE(SUBSTR(C."+ cellname+ ",0,2),'02','', DECODE(SUBSTR(C."+ cellname+ ",0,2),'04','', A." + cellname + ")), '')) AS " + cellname + " \n");
			}else{
				sql.append("                   , SUM(NVL(DECODE(SUBSTR(C."+ cellname+ ",0,2),'02','', DECODE(SUBSTR(C."+ cellname+ ",0,2),'04','', A." + cellname + ")), '')) AS " + cellname + " \n");
			}
		}
		
		sql.append("            FROM DATAFIXEDFRM A, TGTDEPT B, ATTFIXEDFRM C                                         \n");
		sql.append("            WHERE A.SYSDOCNO = B.SYSDOCNO                                                         \n");
		sql.append("            AND A.TGTDEPTCD = B.TGTDEPTCD                                                         \n");
		sql.append("            AND A.SYSDOCNO = C.SYSDOCNO                                                           \n");
		sql.append("            AND A.FORMSEQ = C.FORMSEQ                                                             \n");
		sql.append("            AND A.SYSDOCNO = ?                                                                    \n");
		sql.append("            AND A.FORMSEQ = ?                                                                     \n");
		sql.append("            AND NVL(DEPTCD1, '0') LIKE NVL(?, '%')                                                \n");
		sql.append("            AND NVL(DEPTCD2, '0') LIKE NVL(?, '%')                                                \n");
		sql.append("            AND NVL(DEPTCD3, '0') LIKE NVL(?, '%')                                                \n");
		sql.append("            AND NVL(DEPTCD4, '0') LIKE NVL(?, '%')                                                \n");
		sql.append("            AND NVL(DEPTCD5, '0') LIKE NVL(?, '%')                                                \n");
		sql.append("            AND NVL(DEPTCD6, '0') LIKE NVL(?, '%')                                                \n");
		sql.append("            AND NVL(A.CHRGUNITCD, '0') LIKE NVL(?, '%')                                           \n");
		sql.append("            AND INPUTUSRID LIKE NVL(?, '%')                                                       \n");
		
		if ( !isIncludeNotSubmitData ) {
			sql.append("            AND B.SUBMITSTATE = '05'                                                          \n");
		}
		
		sql.append("            GROUP BY B.TGTDEPTCD, ROLLUP(ROWSEQ))                                                 \n");
		sql.append("      GROUP BY ROLLUP(TGTDEPTCD), ROWSEQ ) X, DEPT Y, TGTDEPT Z                                   \n");
		sql.append("WHERE X.TGTDEPTCD = Y.DEPT_ID(+)                                                                  \n");
		sql.append("AND X.TGTDEPTCD = Z.TGTDEPTCD(+)                                                                  \n");
		sql.append("AND Z.SYSDOCNO(+) = ?                                                                             \n");
		sql.append("ORDER BY TO_NUMBER(NVL(DECODE(X.TGTDEPTCD,'0', 0, Y.DEPT_RANK), 99999)), NVL(Y.DEPT_FULLNAME, Z.TGTDEPTNM), ROWSEQ \n");
		
		try {
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(sql.toString());
			int idx = 0;
			pstmt.setInt(++idx, sysdocno);
			pstmt.setInt(++idx, formseq);
			pstmt.setString(++idx, schBean.getSch_deptcd1());
			pstmt.setString(++idx, schBean.getSch_deptcd2());
			pstmt.setString(++idx, schBean.getSch_deptcd3());
			pstmt.setString(++idx, schBean.getSch_deptcd4());
			pstmt.setString(++idx, schBean.getSch_deptcd5());
			pstmt.setString(++idx, schBean.getSch_deptcd6());
			pstmt.setString(++idx, schBean.getSch_chrgunitcd());
			pstmt.setString(++idx, schBean.getSch_inputusrid());
			pstmt.setInt(++idx, sysdocno);
			rs = pstmt.executeQuery();
			
			datalist = new ArrayList();
			String formbodyhtml= lbody;
			
			/*소계추가하기*/
			int rowss = rows;
			if (rows > 1 && subtotalState == true) {
				rowss = rows+1;
			}
							
			while(rs.next()) {
				/*총계추가하기*/
				if ( "0".equals(rs.getString("DEPT_RANK")) == true && totalState == false ) {
					continue;
				}
				/*소계추가하기*/
				if ( (rs.getInt("ROWSEQ") == 0 && subtotalState == false) || (rs.getInt("ROWSEQ") == 0 && rows == 1) ) {
					continue;
				}
				
				int strIdx = 0;
				k++;
				String firstStr = "";
				String lastStr = "";
	
				FormatFixedBean Bean = new FormatFixedBean();
				
				//부서추가
				if(!pretgtdept.equals(rs.getString("TGTDEPTNM"))){
					strIdx = formbodyhtml.indexOf(">", 0);
					firstStr = formbodyhtml.substring(0, strIdx + 1);
					lastStr = formbodyhtml.substring(strIdx + 1, formbodyhtml.length());
					String colHeadAtt_ext = "bgcolor=\"rgb(225,225,255)\"";
					
					int substring = DeptManager.instance().getDeptInfo(appInfo.getRootid()).getDept_fullname().length() + 1;
					String tgtdeptnm = rs.getString("TGTDEPTNM");
					if ( tgtdeptnm.length() > substring ) tgtdeptnm = tgtdeptnm.substring(substring);
					formbodyhtml = firstStr + "<td rowspan='"+(rowss) +"' align='center' " + colHeadAtt_ext + ">" + tgtdeptnm + "</td>" + lastStr;
				}
				
				for(int i=0; i<cols; i++){
					if(i < 26) {
						cellname = (char)('A' + i) + "";
					} else {
						cellname = (char)('A' + i - 26) + "" + (char)('A' + i - 26);
					}
					if(rs.getString(cellname)!= null){
						String replaceStr = rs.getString(cellname);
						String strValue = null;
						DecimalFormat df = new DecimalFormat("###,###.##########");
						if(replaceStr.length() > 1 && replaceStr.substring(0, 1).equals(".") == true) {
							strValue = "0" + replaceStr;
						} else if(replaceStr.length() > 2 && replaceStr.substring(0, 2).equals("-.") == true) {
							strValue = "-0" + replaceStr.substring(1);
						} else {
							strValue = replaceStr;
						}
						if ( strValue == null || strValue.trim().length() == 0 ) strValue = "0";
						formbodyhtml = formbodyhtml.replaceFirst("\\$"+cellname, df.format(Double.parseDouble(strValue)));
					} else{
						String celldata = "";
						String[] gubun = getAttFixedFrmData(sysdocno, formseq, cellname).split("[|]");
						
						int rowseq = rs.getInt("ROWSEQ");
						String deptrank = rs.getString("DEPT_RANK");
						String tgtdeptcd = rs.getString("TGTDEPTCD");
						
						InputDeptSearchBoxBean idsbbean2 = new InputDeptSearchBoxBean();
						BeanUtils.copyProperties(idsbbean2, idsbbean);
						String[][] deptInfo = FormatManager.instance().getInputDeptInfo(sysdocno, tgtdeptcd);
						int depth = CommTreatManager.instance().getTargetDeptLevel(sysdocno, tgtdeptcd);
						int idx2 = 0;
						if ( depth < 1 ) {
							ColldocBean colldocBean = ColldocManager.instance().getColldoc(sysdocno);
							if ( colldocBean != null ) {
								deptInfo = FormatManager.instance().getInputDeptInfo(sysdocno, colldocBean.getColdeptcd());
								depth = CommTreatManager.instance().getTargetDeptLevel(sysdocno, colldocBean.getColdeptcd());
							}
						}
						if ( depth > idx2 ) idsbbean2.setSch_deptcd1(deptInfo[idx2++][0]);
						if ( depth > idx2 ) idsbbean2.setSch_deptcd2(deptInfo[idx2++][0]);
						if ( depth > idx2 ) idsbbean2.setSch_deptcd3(deptInfo[idx2++][0]);
						if ( depth > idx2 ) idsbbean2.setSch_deptcd4(deptInfo[idx2++][0]);
						if ( depth > idx2 ) idsbbean2.setSch_deptcd5(deptInfo[idx2++][0]);
						if ( depth > idx2 ) idsbbean2.setSch_deptcd6(deptInfo[idx2++][0]);
						
						if(("02".equals(gubun[0]) && Integer.parseInt(gubun[1], 10) == 1) || "04".equals(gubun[0])) {
							if ( "0".equals(deptrank) == true ) {					//총계일때
								if ( totalShowStringState == true ) {				//총계표시할때
									if ( rowseq == 0 ) {							//총계-소계일때
										if ( subtotalShowStringState == true ) {	//총계-소계표시할때
											celldata = getStringDataFixedFrm(sysdocno, formseq, 0, idsbbean, cellname, isIncludeNotSubmitData);
										}
									} else {										//총계-소계아닐때
										celldata = getStringDataFixedFrm(sysdocno, formseq, rowseq, idsbbean, cellname, isIncludeNotSubmitData);
									}
								}
							} else if ( rowseq == 0 ) {								//소계일때
								if ( subtotalShowStringState == true ) {			//소계표시할때									
									celldata = getStringDataFixedFrm(sysdocno, formseq, 0, idsbbean2, cellname, isIncludeNotSubmitData);
								}
							} else {												//총계소계아닐때
								celldata = getStringDataFixedFrm(sysdocno, formseq, rowseq, idsbbean2, cellname, isIncludeNotSubmitData);
							}
							celldata = Utils.convertHtmlBrNbsp(celldata);
							if(celldata == null || celldata.trim().equals("") == true) {
								celldata = "-";
							}
						} else if("02".equals(gubun[0]) && Integer.parseInt(gubun[1], 10) > 1) {
							if ( "0".equals(deptrank) == true ) {					//총계일때
								if ( totalShowStringState == true ) {				//총계표시할때
									if ( rowseq == 0 ) {							//총계-소계일때
										if ( subtotalShowStringState == true ) {	//총계-소계표시할때
											celldata = getStringDataFixedFrm(sysdocno, formseq, 0, idsbbean, cellname, isIncludeNotSubmitData);
										}
									} else {										//총계-소계아닐때
										celldata = getStringDataFixedFrm(sysdocno, formseq, rowseq, idsbbean, cellname, isIncludeNotSubmitData);
									}
								}
							} else if ( rowseq == 0 ) {								//소계일때
								if ( subtotalShowStringState == true ) {			//소계표시할때
									celldata = getStringDataFixedFrm(sysdocno, formseq, 0, idsbbean2, cellname, isIncludeNotSubmitData);
								}
							} else {												//총계소계아닐때
								celldata = getStringDataFixedFrm(sysdocno, formseq, rowseq, idsbbean2, cellname, isIncludeNotSubmitData);
							}
							celldata = Utils.convertHtmlBrNbsp(celldata);
							if(celldata == null || celldata.trim().equals("") == true) {
								celldata = "-";
							}
							celldata = "<div align='left' style='padding-left:10; padding-right:10'>" + celldata + "</div>";
						}

						//formbodyhtml = formbodyhtml.replaceFirst("\\$"+cellname, celldata);
						StringBuffer sb = new StringBuffer(formbodyhtml);
						String cell = "$"+cellname;
						int index = formbodyhtml.indexOf(cell);
						sb.replace(index, index + cell.length(), celldata);
						formbodyhtml = sb.toString();
					}
				}
				
				if(k == (rowss)){
					Bean.setFormbodyhtml(formbodyhtml);
					datalist.add(Bean);
					formbodyhtml = lbody;
					k = 0;
				}

				pretgtdept = rs.getString("TGTDEPTNM");				
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return datalist;
	}	
}
