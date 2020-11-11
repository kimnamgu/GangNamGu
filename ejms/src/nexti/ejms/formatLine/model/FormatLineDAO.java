/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���߰��� dao
 * ����:
 */
package nexti.ejms.formatLine.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.attr.model.AttrBean;
import nexti.ejms.format.model.FormatBean;
import nexti.ejms.format.model.InputDeptSearchBoxBean;
import nexti.ejms.util.Utils;
import nexti.ejms.dept.model.DeptManager;

public class FormatLineDAO {
	
	private static Logger logger = Logger.getLogger(FormatLineDAO.class);
	
	/**
	 * ��� �̸����� �ӽõ����� ����
	 * @param Connection conn
	 * @param FormatBean flbean
	 * @param String deptnm
	 * @param int count
	 * @return int ���ళ��
	 * @throws Exception 
	 */
	public int addFormatLinePreviewTempData(Connection con, FormatBean fbean, String deptnm, int count) throws Exception {
		
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
			ConnectionManager.close( pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
		
		return result;
	}
	
	/**
	 * ��� �̸����� �ӽõ����� ����
	 * @param Connection conn
	 * @param int sysdocno
	 * @return int ���ళ��
	 * @throws Exception 
	 */
	public int delFormatLinePreveiwTempData(Connection con, int sysdocno) throws Exception {
		
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
			ConnectionManager.close( pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
		
		return result;
	}
	
	/**
	 * ���߰��� ��ļӼ� ��������
	 * @param int sysdocno 
	 * @param int formseq
	 * @return String[] �������
	 * @throws Exception
	 */
	public String[] getFormatLineAtt(int sysdocno, int formseq) throws Exception {
		
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
				"FROM ATTLINEFRM " +
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
	 * ���߰��� �����ļӼ� ��������
	 * @param int formseq
	 * @return String[] ����������
	 * @throws Exception
	 */
	public String[] getCommFormatLineAtt(int formseq) throws Exception {
		
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
				"FROM COMMATTLINEFRM " +
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
	 * �Ӽ���� ����Ʈ ��������
	 * @return
	 * @throws Exception
	 */	
	public List getListAttlist() throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		
		List result = null;
		AttrBean albean = null;
		
		try {
			String sql1 =
				"SELECT LISTNM, LISTCD " +
				"FROM ATTLISTMST ";
			
			con = ConnectionManager.getConnection();
			
			pstmt1 = con.prepareStatement(sql1);

			rs1 = pstmt1.executeQuery();
			
			result = new ArrayList();
			
			while(rs1.next()) {
				albean = new AttrBean();
				
				albean.setListnm(rs1.getString("LISTNM"));
				albean.setListcd(rs1.getString("LISTCD"));
				
				String sql2 = 
					"SELECT LISTDTLNM " +
					"FROM ATTLISTDTL " +
					"WHERE LISTCD = ? " +
					"ORDER BY SEQ ASC ";
			
				pstmt2 = con.prepareStatement(sql2);
				
				pstmt2.setString(1, rs1.getString("LISTCD"));
				
				rs2 = pstmt2.executeQuery();
				
				ArrayList listdtl = new ArrayList();
				
				while(rs2.next()) {
					String detail = new String(rs2.getString("LISTDTLNM"));
					
					listdtl.add(detail);
				}
				
				albean.setListdtlList(listdtl);

				result.add(albean);
				
				try { rs2.close(); } catch (Exception e) {}
				try {pstmt2.close();} catch(Exception e) {}
			}			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt2);
			ConnectionManager.close(con, pstmt1, rs1);
			throw e;
		} finally {
			try {pstmt2.close();} catch(Exception e) {}
			ConnectionManager.close(con, pstmt1, rs1);
		}
		
		return result;
	}
	
	/**
	 * ��� �Է¼Ӽ� �����ϱ�
	 * @param Connection conn
	 * @param FormatLineBean bean
	 * @return int ���ళ��
	 * @throws Exception 
	 */
	public int addFormatLine(Connection con, FormatLineBean bean) throws Exception {
		
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
					"INSERT INTO ATTLINEFRM(SYSDOCNO, FORMSEQ, " + colName + ") " +
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
	 * ��� �Է¼Ӽ� �����ϱ�
	 * @param Connection conn
	 * @param FormatLineBean bean
	 * @return int ���ళ��
	 * @throws Exception 
	 */
	public int modifyFormatLine(Connection con, FormatLineBean bean) throws Exception {
		
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
					"UPDATE ATTLINEFRM " +
					"SET " + colValue.toString() + " " +
					"WHERE SYSDOCNO = ? " +
					"  AND FORMSEQ = ? ";
			
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
	 * ������ �Է¼Ӽ� �����ϱ�
	 * @param Connection conn
	 * @param FormatLineBean bean
	 * @return int ���ళ��
	 * @throws Exception 
	 */
	public int modifyCommFormatLine(Connection con, FormatLineBean bean) throws Exception {
		
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
					"UPDATE COMMATTLINEFRM " +
					"SET " + colValue.toString() + " " +
					"WHERE FORMSEQ = ? ";
			
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, bean.getFormseq());

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
	 * ������ �Է¼Ӽ� �����ϱ�
	 * @param Connection conn
	 * @param FormatLineBean bean
	 * @return int ���ళ��
	 * @throws Exception 
	 */
	public int addCommFormatLine(Connection con, FormatLineBean bean) throws Exception {
		
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
					"INSERT INTO COMMATTLINEFRM(FORMSEQ, " + colName + ") " +
					"VALUES(?, " + colValue + ")" ;
			
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, bean.getFormseq());

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
	 * ���� ����ڷ� ���� - ����� �����ֱ� ������ ��������
	 * ����� ��������
	 * @throws Exception 
	 */
	public FormatLineBean getFormatFormView(int sysdocno, int formseq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		FormatLineBean bean = new FormatLineBean();
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("\n SELECT FORMCOMMENT, TBLCOLS, TBLROWS				");
		selectQuery.append("\n   FROM FORMMST 									");
		selectQuery.append("\n  WHERE SYSDOCNO = ? 								");
		selectQuery.append("\n    AND FORMKIND = '01'							");
		selectQuery.append("\n    AND FORMSEQ = ?								");
		
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
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return bean;
	}
	
	/**
	 * ��ĸ����� - ���߰��� ������ ��������(Ư�� �ʵ� �����͸�)
	 * 
	 * @param sysdocno : �ý��۹�����ȣ
	 * @param formseq : ����Ϸù�ȣ
	 * @param colname : ������ �ʵ�
	 * @param usenvl : NVL ��뿩��
	 * 
	 * @return Object : FORMMST ���̺� �� �ʵ� ������
	 * @throws Exception 
	 */
	public Object getFormatLineView(int sysdocno, int formseq, String colname, boolean usenvl) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Object obj = new Object();
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("\n SELECT " + colname);
		selectQuery.append("\n   FROM FORMMST 									");
		selectQuery.append("\n  WHERE SYSDOCNO = ? 								");
		selectQuery.append("\n    AND FORMKIND = '01'							");
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
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	

		return (usenvl && obj == null) ? "" : obj;
	}
	
	/**
	 * ���� ����ڷ� ���� - ��������뿡 ���� ������ �����ֱ� ��������
	 * ����� ��������
	 * @throws Exception 
	 */
	public List getFormDataList(int sysdocno, int formseq, InputDeptSearchBoxBean idsbbean, boolean isTotalState, boolean isTotalShowStringState, boolean isIncludeNotSubmitData) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List datalist = null;
		
		StringBuffer tsSql = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		
		int rownum = 0;
		int cols = getFormatFormView(sysdocno, formseq).getTblcols();
		String lbody = getFormatFormView(sysdocno, formseq).getFormbodyhtml();
		
		
		//tsSql �κ��� �Ѱ� ����, �ڹٷ� ó������ �ʰ� SQL�� ���ڸ� �����Ͽ� ����ϵ��� �۾�, �����۵��� ������ �׽�Ʈ����
		String cellname = "";
		for ( int i = 0; i < cols; i++ ) {			
			if ( i < 26 ) {
				cellname = (char)('A' + i) + "";
			} else {
				cellname = (char)('A' + i - 26) + "" + (char)('A' + i - 26);
			}
			
			if ( i == 0 ) {
				sql.append("SELECT NVL(" + cellname + ", ' ') AS " + cellname + " ");
				if ( isTotalState ) {
					tsSql.append("SELECT TO_CHAR(SUM(CASE WHEN (NVL(LENGTH(TRIM(" + cellname + ")), 0) - NVL(LENGTH(TRANSLATE(TRIM(" + cellname + "), '.-1234567890' || TRIM(" + cellname + "), '.-1234567890')), 0) = 0) \n");
					tsSql.append("                        THEN CASE WHEN (NVL(LENGTH(TRIM(" + cellname + ")), 0) - NVL(LENGTH(TRANSLATE(TRIM(" + cellname + "), '.-1234567890' || TRIM(" + cellname + "), '.-')), 0) != 0) \n");
					tsSql.append("                                  THEN CASE WHEN (INSTR(TRIM(" + cellname + "), '-') < 2) \n");
					tsSql.append("                                            THEN CASE WHEN (INSTR(TRIM(" + cellname + "), '-', INSTR(TRIM(" + cellname + "), '-') + 1) < 2) \n");
					tsSql.append("                                                      THEN CASE WHEN (INSTR(TRIM(" + cellname + "), '.', INSTR(TRIM(" + cellname + "), '.') + 1) < 2) \n");
					tsSql.append("                                                                THEN TRIM(" + cellname + ") \n");
					tsSql.append("                                                                ELSE '' END \n");
					tsSql.append("                                                      ELSE '' END \n");
					tsSql.append("                                            ELSE '' END \n");
					tsSql.append("                                  ELSE '' END \n");
					tsSql.append("                        ELSE '' END)) AS " + cellname + " \n");
				}
			} else {
				sql.append(", NVL(" + cellname + ", ' ') AS " + cellname + " ");
				if ( isTotalState ) {
					tsSql.append("       , TO_CHAR(SUM(CASE WHEN (NVL(LENGTH(TRIM(" + cellname + ")), 0) - NVL(LENGTH(TRANSLATE(TRIM(" + cellname + "), '.-1234567890' || TRIM(" + cellname + "), '.-1234567890')), 0) = 0) \n");
					tsSql.append("                          THEN CASE WHEN (NVL(LENGTH(TRIM(" + cellname + ")), 0) - NVL(LENGTH(TRANSLATE(TRIM(" + cellname + "), '.-1234567890' || TRIM(" + cellname + "), '.-')), 0) != 0) \n");
					tsSql.append("                                    THEN CASE WHEN (INSTR(TRIM(" + cellname + "), '-') < 2) \n");
					tsSql.append("                                              THEN CASE WHEN (INSTR(TRIM(" + cellname + "), '-', INSTR(TRIM(" + cellname + "), '-') + 1) < 2) \n");
					tsSql.append("                                                        THEN CASE WHEN (INSTR(TRIM(" + cellname + "), '.', INSTR(TRIM(" + cellname + "), '.') + 1) < 2) \n");
					tsSql.append("                                                                  THEN TRIM(" + cellname + ") \n");
					tsSql.append("                                                                  ELSE '' END \n");
					tsSql.append("                                                        ELSE '' END \n");
					tsSql.append("                                              ELSE '' END \n");
					tsSql.append("                                    ELSE '' END \n");
					tsSql.append("                          ELSE '' END)) AS " + cellname + " \n");
				}
			}
		}
		
		int tsSql_idx1 = sql.length();
		
		sql.append("\n");
		sql.append("FROM DATALINEFRM A, TGTDEPT B, DEPT C     \n");
		sql.append("WHERE A.SYSDOCNO = B.SYSDOCNO             \n"); 
		sql.append("AND A.TGTDEPTCD = B.TGTDEPTCD             \n");
		sql.append("AND A.TGTDEPTCD = C.DEPT_ID(+)            \n");
		sql.append("AND A.SYSDOCNO = ?                        \n");
		sql.append("AND A.FORMSEQ = ?                         \n");		
		sql.append("AND NVL(DEPTCD1, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD2, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD3, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD4, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD5, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD6, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(CHRGUNITCD, '0') LIKE NVL(?, '%') \n");
		sql.append("AND INPUTUSRID LIKE NVL(?, '%')           \n");
		
		if( !isIncludeNotSubmitData ) {
			sql.append("AND B.SUBMITSTATE = '05'              \n");
		}
		
		int tsSql_idx2 = sql.length();
		
		sql.append("ORDER BY DEPT_RANK, ROWSEQ                \n");
		
		if ( isTotalState ) {
			tsSql.append(sql.substring(tsSql_idx1, tsSql_idx2));
			tsSql.append("UNION ALL \n");
			tsSql.append("SELECT * FROM ( \n");
			
			sql.append(") \n");
			sql.insert(0, tsSql.toString());
		}
		
		try {
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(sql.toString());
			int idx = 0;
			
			if ( isTotalState ) {
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
				
				rownum = -1;
			}
			
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
			
			datalist = new ArrayList();
			
			while(rs.next()) {
				int strIdx = 0;
				String firstStr = "";
				String lastStr = "";
				String formbodyhtml = lbody;
				FormatLineBean Bean = new FormatLineBean();
				
				strIdx = formbodyhtml.indexOf(">", 0);
				firstStr = formbodyhtml.substring(0, strIdx + 1);
				lastStr = formbodyhtml.substring(strIdx + 1, formbodyhtml.length());
				
				String colHeadAtt_ext = "bgcolor=\"rgb(225,225,255)\"";
				if ( rownum++ == -1 ) {
					formbodyhtml = firstStr + "<td align='center' style='border:1 solid gray' " + colHeadAtt_ext + ">�Ѱ�</td>" + lastStr;
				} else {
					formbodyhtml = firstStr + "<td align='center' style='border:1 solid gray' " + colHeadAtt_ext + ">" + rownum + "</td>" + lastStr;
				}
				
				for ( int i=0 ; i < cols; i++ ) {
					if ( i < 26 ) {
						cellname = (char)('A' + i) + "";
					} else {
						cellname = (char)('A' + i - 26) + "" + (char)('A' + i - 26);
					}
					
					String replaceStr = "";
					String cellatt = getAttLineFrmData(sysdocno, formseq, cellname);
					String[] gubun = cellatt.split("[|]");
					
					//�� �Ӽ��� ó��(gubun[0] : �Ӽ� ����, gubun[1] : �Ӽ� �Ķ��Ÿ)
					if ( "01".equals(gubun[0]) || "03".equals(gubun[0]) ) {	//�Ӽ��� ������ Į��(gubun[0]�� ���), ���� �Ӽ��� ǥ��
						replaceStr = Utils.nullToEmptyString(rs.getString(cellname));
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
						replaceStr = df.format(Double.parseDouble(strValue));
					} else if ( rownum != 0 && "02".equals(gubun[0]) ) {	//�Ӽ��� ������ Į��(gubun[1]�� ���� ���� ����)
						if ( Integer.parseInt(gubun[1], 10) == 1 ) {
							replaceStr = Utils.nullToEmptyString(rs.getString(cellname));
						} else {
							replaceStr = "<div align='left' style='padding-left:10; padding-right:10'>" + Utils.convertHtmlBrNbsp(Utils.nullToEmptyString(rs.getString(cellname))) + "</div>";
						}
					} else if ( rownum != 0 && "04".equals(gubun[0]) ) {	//�Ӽ��� ����� Į��(gubun[1]�� ���� ATTLISTDTL�� ATTLISTMST���� �����;��� �ڵ��)
						replaceStr = Utils.nullToEmptyString(rs.getString(cellname));
					}
					
					//formbodyhtml = formbodyhtml.replaceFirst("\\$"+cellname, replaceStr);
					StringBuffer sb = new StringBuffer(formbodyhtml);
					String cell = "$"+cellname;
					int index = formbodyhtml.indexOf(cell);
					sb.replace(index, index + cell.length(), replaceStr);
					formbodyhtml = sb.toString();
				}
				Bean.setFormbodyhtml(formbodyhtml);
				datalist.add(Bean);
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
	
	/**
	 * ���߰��� �Ӽ� ��������
	 * 
	 * @param sysdocno : �ý��۹�����ȣ
	 * @param formseq : ����Ϸù�ȣ
	 * @param colname : ������ Į����
	 * 
	 * @return String
	 * @throws Exception 
	 */
	public String getAttLineFrmData(int sysdocno, int formseq, String colname) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String result = "";
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("\n SELECT " + colname + " " + colname);
		selectQuery.append("\n   FROM ATTLINEFRM 		");
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
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return result;
	}
	
	public String getAttLineFrmData(Connection con, int sysdocno, int formseq, String colname) throws Exception {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String result = "";
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("\n SELECT " + colname + " " + colname);
		selectQuery.append("\n   FROM ATTLINEFRM 		");
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
			ConnectionManager.close(pstmt,rs);
			throw e;
		} finally {
			try {rs.close();} catch(Exception e) {}
			try {pstmt.close();} catch(Exception e) {}
		}	
		
		return result;
	}
	
	/**
	 * ���߰��� ������ ��������
	 * 
	 * @param sysdocno : �ý��۹�����ȣ
	 * @param formseq : ����Ϸù�ȣ
	 * @param deptcd : �μ��ڵ�
	 * @param usrid : �����ID
	 * @param chrgunitcd : �������ڵ�
	 * @param colname : ������ Į����
	 * 
	 * @return String : �ʵ嵥����
	 * @throws Exception 
	 */
	public String getDataLineFrm(int sysdocno, int formseq, int rowseq, String deptcd, String usrid, int chrgunitcd, String colname) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int bindpos = 0;
		String result = "";
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("\n SELECT NVL(" + colname + ", ' ') " + colname);
		selectQuery.append("\n   FROM DATALINEFRM 			");
		selectQuery.append("\n  WHERE SYSDOCNO   = ? 		");
		selectQuery.append("\n    AND FORMSEQ    = ?		");
		selectQuery.append("\n    AND TGTDEPTCD  LIKE ?		");
		selectQuery.append("\n    AND INPUTUSRID LIKE ?		");
		if(!deptcd.equals("%")) {
			selectQuery.append("\n    AND ROWSEQ     = ?		");
		}
		if(chrgunitcd != 0) {
			selectQuery.append("\n    AND CHRGUNITCD     = ?	");
		}
		
		try {

			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(++bindpos, sysdocno);
			pstmt.setInt(++bindpos, formseq);
			pstmt.setString(++bindpos, deptcd);
			pstmt.setString(++bindpos, usrid);
			if(!deptcd.equals("%")) {
				pstmt.setInt(++bindpos, rowseq);
			}				
			if(chrgunitcd != 0) {
				pstmt.setInt(++bindpos, chrgunitcd);
			}
									
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getString(colname);
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
	 * �Է��ϱ�, �Է¿Ϸ� - ���߰��� ������ ����
	 * 
	 * @param conn : Connection ��ü
	 * @param sysdocno : �ý��۹�����ȣ
	 * @param formseq : ����Ϸù�ȣ
	 * @param deptcd : �μ��ڵ�
	 * @param usrid : �����ID
	 * 
	 * @return int : ����ڵ�
	 * @throws Exception 
	 */
	public int deleteFormatLineData(Connection con, int sysdocno, int formseq, String deptcd, String usrid) throws Exception {
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		StringBuffer deleteQuery = new StringBuffer();
		deleteQuery.append("\n DELETE                 ");
		deleteQuery.append("\n   FROM DATALINEFRM     ");
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
			ConnectionManager.close(pstmt);
			throw e;
		}finally {
			try {pstmt.close();} catch(Exception e) {}
		}
		
		return result;
	}
	
	/** 
	 * �Է��ϱ�, �Է¿Ϸ� - �Է»��¸� �ӽ����� ���·� ����
	 * 
	 * @param conn : Connection ��ü
	 * @param sysdocno : �ý��۹�����ȣ
	 * @param deptcd : �μ��ڵ�
	 * @param usrid : �����ID
	 * 
	 * @return int : ����ڵ�
	 * @throws Exception 
	 */
	public int updateInputState(Connection con, int sysdocno, String deptcd, String usrid) throws Exception {
		int result = 0;
		
		PreparedStatement pstmt = null;

		StringBuffer updateQuery = new StringBuffer();
		updateQuery.append("\n UPDATE INPUTUSR                                               ");
		updateQuery.append("\n    SET INPUTSTATE = '02'                                      ");
		updateQuery.append("\n      , CHRGUNITCD = ?                                         ");
		updateQuery.append("\n      , CHRGUNITNM = ?                                         ");
		updateQuery.append("\n      , UPTDT      = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') ");
		updateQuery.append("\n      , UPTUSRID   = ?                                         ");
		updateQuery.append("\n  WHERE SYSDOCNO   = ?                                         ");
		updateQuery.append("\n    AND TGTDEPT    = ?                                         ");
		updateQuery.append("\n    AND INPUTUSRID = ?                                         ");
		updateQuery.append("\n    AND INPUTSTATE NOT IN ('03', '04', '05')                   ");
		
		try{
			pstmt = con.prepareStatement(updateQuery.toString());
			
			pstmt.setInt(1, DeptManager.instance().getChrgunitcd(usrid));
			pstmt.setString(2, DeptManager.instance().getChrgunitnm(deptcd, DeptManager.instance().getChrgunitcd(usrid)));
			pstmt.setString(3, usrid);
			pstmt.setInt(4, sysdocno);
			pstmt.setString(5, deptcd);
			pstmt.setString(6, usrid);
				
			result = pstmt.executeUpdate();
		} catch(Exception e){
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		}finally {
			try {pstmt.close();} catch(Exception e) {}
		}
		
		return result;
	}
	
	/**
	 * @param Connection conn
	 * @param sysdocno
	 * @param formseq
	 * @param deptcd
	 * @param usrid
	 * @return
	 * @throws Exception
	 */ 
	public int getMaxSeq(Connection con, int sysdocno, int formseq, String deptcd, String usrid) throws Exception {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int result = 0;
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery = new StringBuffer();
		selectQuery.append("\n SELECT MAX(NVL(ROWSEQ,0)) AS MAXSEQ 	");
		selectQuery.append("\n   FROM DATALINEFRM       			");
		selectQuery.append("\n  WHERE SYSDOCNO 		= ?     		");
		selectQuery.append("\n    AND FORMSEQ  		= ?     		");
		selectQuery.append("\n    AND TGTDEPTCD  	= ?    			");
		selectQuery.append("\n    AND INPUTUSRID  	= ?    			");
		
		try {			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
			pstmt.setString(3, deptcd);
			pstmt.setString(4, usrid);
									
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt("MAXSEQ");
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
	 * �Է��ϱ�, �Է¿Ϸ� - ���߰��� ������ ����
	 * 
	 * @param conn : Connection ��ü
	 * @param insertQuery : �Է�������
	 * 
	 * @return int : ����ڵ�
	 * @throws Exception 
	 */
	public int insertFormatLineData(Connection con, StringBuffer insertQuery) throws Exception {
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		try{
			pstmt = con.prepareStatement(insertQuery.toString());
				
			result = pstmt.executeUpdate();
		} catch(Exception e){
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		}finally {
			try {pstmt.close();} catch(Exception e) {}
		}
		
		return result;
	}
	
	/** 
	 * �Է��ϱ�, �Է¿Ϸ� - ���߰��� ������ ����
	 * 
	 * @param conn : Connection ��ü
	 * @param sysdocno : �ý��۹�����ȣ
	 * @param formseq : ����Ϸù�ȣ
	 * @param deptcd : �μ��ڵ�
	 * @param usrid : �����ID
	 * @param rowseq : �Է��ٹ�ȣ
	 * 
	 * @return int : ����ڵ�
	 * @throws Exception 
	 */
	public int deleteFormatLineData(int sysdocno, int formseq, String deptcd, String usrid, int rowseq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;

		StringBuffer deleteQuery = new StringBuffer();
		deleteQuery.append("\n DELETE                 	");
		deleteQuery.append("\n   FROM DATALINEFRM     	");
		deleteQuery.append("\n  WHERE SYSDOCNO   = ?  	");
		deleteQuery.append("\n    AND FORMSEQ    = ?  	");
		deleteQuery.append("\n    AND TGTDEPTCD  = ?  	");
		deleteQuery.append("\n    AND INPUTUSRID = ?  	");
		deleteQuery.append("\n    AND ROWSEQ 	 = ?	");
		
		try{
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(deleteQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
			pstmt.setString(3, deptcd);
			pstmt.setString(4, usrid);
			pstmt.setInt(5, rowseq);
				
			result = pstmt.executeUpdate();
		} catch(Exception e){
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt);
			throw e;
		}finally {
			ConnectionManager.close(con,pstmt);
		}
		return result;
	}
	
	/** 
	 * �Է��ϱ�, �Է¿Ϸ� - ���߰��� ������ �������
	 * 
	 * @param conn : Connection ��ü
	 * @param sysdocno : �ý��۹�����ȣ
	 * @param formseq : ����Ϸù�ȣ
	 * @param deptcd : �μ��ڵ�
	 * @param usrid : �����ID
	 * 
	 * @return int : ����ڵ�
	 * @throws Exception 
	 */
	public int deleteAllFormatLineData(int sysdocno, int formseq, String deptcd, String usrid) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;

		StringBuffer deleteQuery = new StringBuffer();
		deleteQuery.append("\n DELETE                 	");
		deleteQuery.append("\n   FROM DATALINEFRM     	");
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
			ConnectionManager.close(con,pstmt);
			throw e;
		}finally {
			ConnectionManager.close(con,pstmt);
		}
		return result;
	}
	
	/**
	 * ���� ����ڷ� ���� - ���߰��� �Ӽ� �� ��� ������ ��������
	 * 
	 * @param listcd : ATTLISTMST, ATTLISTDTL �ڵ��̸�
	 * 
	 * @return List
	 * @throws Exception 
	 */
	public List getAttListData(String listcd) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List listResult = null;
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("\n SELECT B.LISTDTLNM 					");
		selectQuery.append("\n   FROM ATTLISTMST A, ATTLISTDTL B	");
		selectQuery.append("\n  WHERE A.LISTCD = B.LISTCD			");
		selectQuery.append("\n    AND A.LISTCD = ?					");
		selectQuery.append("\n  ORDER BY B.SEQ ");
		
		try {

			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, listcd);

			rs = pstmt.executeQuery();
			
			listResult = new ArrayList();
			
			while(rs.next()) {
				listResult.add(rs.getString("LISTDTLNM"));
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return listResult;
	}
	
	/**
	 * ���߰��� ������ ���� ��������
	 * 
	 * @param sysdocno : �ý��۹�����ȣ
	 * @param formseq : ����Ϸù�ȣ
	 * @param deptcd : �μ��ڵ�
	 * @param usrid : �����ID
	 * @param chrgunitcd : �������ڵ�
	 * 
	 * @return int : ������ ����
	 * @throws Exception 
	 */
	public int getFormatLineDataCnt(int sysdocno, int formseq, InputDeptSearchBoxBean idsbbean) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int count = 0;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT COUNT(*) CNT   	\n");
		sql.append("FROM DATALINEFRM    	\n");
		sql.append("WHERE SYSDOCNO = ? \n");
		sql.append("AND FORMSEQ = ? \n");		
		sql.append("AND NVL(DEPTCD1, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD2, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD3, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD4, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD5, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD6, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(CHRGUNITCD, '0') LIKE NVL(?, '%') \n");
		sql.append("AND INPUTUSRID LIKE NVL(?, '%')           \n");
		
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
				count = rs.getInt("CNT");
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return count;
	}
			
	/**
	 * �ش� ���� ���� ���� Ȯ�� - ���߰���
	 * 
	 * @param sysdocno : �ý��� ������ȣ
	 * @param formseq : ����Ϸù�ȣ
	 * @param deptcd : �μ��ڵ�
	 * 
	 * @return String : ����/���� ���� ����
	 * @throws Exception 
	 */
	public int IsDocSanctgt(int sysdocno, int formseq, String deptcd) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int issanc = 0;
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("\n SELECT COUNT(*) CNT             ");
		selectQuery.append("\n   FROM SANCTGT A, FORMMST B     ");
		selectQuery.append("\n  WHERE A.SYSDOCNO  = B.SYSDOCNO ");
		selectQuery.append("\n    AND A.SYSDOCNO  = ?          ");
		selectQuery.append("\n    AND A.TGTDEPTCD = ?          ");
		selectQuery.append("\n    AND B.FORMSEQ   = ?          ");
		selectQuery.append("\n    AND B.FORMKIND  = '01'       ");
		selectQuery.append("\n    AND A.SANCYN    = '1'        ");
		
		try {

			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setString(2, deptcd);
			pstmt.setInt(3, formseq);
									
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				issanc = rs.getInt("CNT");
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return issanc;
	}
	
	/**
	 * ������� �������� ������ - ���߰���
	 * 
	 * @param sysdocno : �ý��� ������ȣ
	 * @param formseq : ����Ϸù�ȣ
	 * @param deptcd : �μ��ڵ�
	 * 
	 * @return String : �������� ���� 
	 * @throws Exception 
	 */
	public String getAuthFormSubmitstate(int sysdocno, int formseq, String deptcd) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String submitstate = "";
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("\n SELECT A.SUBMITSTATE               ");
		selectQuery.append("\n   FROM TGTDEPT A, FORMMST B     ");
		selectQuery.append("\n  WHERE A.SYSDOCNO  = B.SYSDOCNO ");
		selectQuery.append("\n    AND A.SYSDOCNO  = ?          ");
		selectQuery.append("\n    AND A.TGTDEPTCD = ?          ");
		selectQuery.append("\n    AND B.FORMSEQ   = ?          ");
		selectQuery.append("\n    AND B.FORMKIND  = '01'       ");
		
		try {

			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setString(2, deptcd);
			pstmt.setInt(3, formseq);
									
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				submitstate = rs.getString("SUBMITSTATE");
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return submitstate;
	}	
	
	/**
	 * ���� ����ڷ� ���� - ��������뿡 ���� ������ �����ֱ�(��������)
	 * ����� ��������
	 * @throws Exception 
	 */
	public List getFormDataList1(int sysdocno, int formseq, InputDeptSearchBoxBean idsbbean, int strDataIdx, int endDataIdx) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List datalist = null;
		
		StringBuffer sql = new StringBuffer();
		String cellname = "";
		String cellatt = "";
		String replaceStr = "";
		String[] gubun = new String[2];
		List attList = null;
		
		int cols = getFormatFormView(sysdocno, formseq).getTblcols();
		 
		String lbody = getFormatFormView(sysdocno, formseq).getFormbodyhtml();
		
		for(int i = 0; i < cols; i++) {
			if ( i < 26 ) {
				cellname = (char)('A' + i) + "";
			} else {
				cellname = (char)('A' + i - 26) + "" + (char)('A' + i - 26);
			}
			if ( i == 0 ) {
				sql.append("SELECT ROWNUM, A.* \n");
				sql.append("FROM (SELECT COUNT(*)OVER() TCNT, ROWSEQ, NVL(" + cellname + ", ' ') AS " + cellname + " ");
			} else {
				sql.append(", NVL(" + cellname + ", ' ') AS " + cellname + " ");
			}
		}
		sql.append("\n");
		sql.append("FROM DATALINEFRM A, TGTDEPT B, DEPT C     \n");
		sql.append("WHERE A.SYSDOCNO = B.SYSDOCNO             \n"); 
		sql.append("AND A.TGTDEPTCD = B.TGTDEPTCD             \n");
		sql.append("AND A.TGTDEPTCD = C.DEPT_ID(+)            \n");
		sql.append("AND A.SYSDOCNO = ?                        \n");
		sql.append("AND A.FORMSEQ = ?                         \n");		
		sql.append("AND NVL(DEPTCD1, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD2, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD3, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD4, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD5, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD6, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(CHRGUNITCD, '0') LIKE NVL(?, '%') \n");
		sql.append("AND INPUTUSRID LIKE NVL(?, '%')           \n");
		sql.append("ORDER BY DEPT_RANK, ROWSEQ) A             \n");
		
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
			
			datalist = new ArrayList();

			while(rs.next()) {
				if ( strDataIdx-- > 0 ) {
					continue;
				} else {
					if ( endDataIdx-- == 0 ) break;
				}
				
				int strIdx = 0;
				int bodylen =0;
				String firstStr = "";
				String lastStr = "";
				String formbodyhtml = lbody;
				FormatLineBean Bean = new FormatLineBean();
				
				strIdx = formbodyhtml.indexOf(">", 0);
				firstStr = formbodyhtml.substring(0, strIdx + 1);
				lastStr = formbodyhtml.substring(strIdx + 1, formbodyhtml.length());
				String colHeadAtt_ext = "bgcolor=\"rgb(225,225,255)\"";
				formbodyhtml = firstStr + "<td align='center' style='border:1 solid gray' " + colHeadAtt_ext + "><input type=hidden name=rowseq  value=\""+ rs.getInt("ROWSEQ")+"\">" + rs.getInt("ROWNUM") + "</td>" + lastStr;
				
				bodylen = formbodyhtml.length();
				String delRowImg = "<td align='left'>&nbsp<img src='/images/common/btn_s_save.gif' style='CURSOR:hand' onclick='checkModify(document.forms[0],\""+rs.getInt("ROWNUM")+"\");' alt='����'>&nbsp;<img src='/images/common/btn_s_cancel.gif' style='CURSOR:hand' onclick='showModifyLayer(modifyLayer" + rs.getInt("ROWNUM") + ", document.forms[0],\""+rs.getInt("ROWNUM")+"\");' alt='���'></td>";
				delRowImg = "";
				firstStr = formbodyhtml.substring(0, formbodyhtml.lastIndexOf("</tr>"));
				lastStr = formbodyhtml.substring(formbodyhtml.lastIndexOf("</tr>"), bodylen);
				formbodyhtml = firstStr + Utils.replace(lastStr, lastStr, delRowImg + "</tr>");
				for(int i=0; i<cols; i++){
					if(i < 26) {
						cellname = (char)('A' + i) + "";
					} else {
						cellname = (char)('A' + i - 26) + "" + (char)('A' + i - 26);
					}
					cellatt = getAttLineFrmData(sysdocno, formseq, cellname);
					gubun = cellatt.split("[|]");
					
					//�� �Ӽ��� ó��(gubun[0] : �Ӽ� ����, gubun[1] : �Ӽ� �Ķ��Ÿ)
					if("01".equals(gubun[0])) {	//�Ӽ��� ������ Į��(gubun[0]�� ���)
						replaceStr = rs.getString(cellname);
						String strValue = null;
						DecimalFormat df = new DecimalFormat("#.##########");
						if(replaceStr.length() > 1 && replaceStr.substring(0, 1).equals(".") == true) {
							strValue = "0" + replaceStr;
						} else if(replaceStr.length() > 2 && replaceStr.substring(0, 2).equals("-.") == true) {
							strValue = "-0" + replaceStr.substring(1);
						} else {
							strValue = replaceStr;
						}
						if ( strValue == null || strValue.trim().length() == 0 ) {
							strValue = "";
						} else {
							strValue = df.format(Double.parseDouble(strValue));
						}
						replaceStr = "<input type='text' style='width:95%; ime-mode:disabled' align='center' name='" + cellname + "' size='15' value=\"" + strValue +"\" onkeydown=key_entertotab() onkeypress=\"inputFilterKey('[0-9.-]');\" onchange=\"instantCalculation('autocal');\">";
					} else if("02".equals(gubun[0])) {	//�Ӽ��� ������ Į��(gubun[1]�� ���� ���� ����)
						if(Integer.parseInt(gubun[1]) > 1) {	//gubun[1]�� ���� 1���� ũ�� TEXTAREA�� ǥ��
							replaceStr = "<textarea style='width:95%; ime-mode:active' align='center' name='" + cellname + "' cols='20' rows='" + Integer.parseInt(gubun[1]) + "'>" + rs.getString(cellname) +"</textarea>";
						} else {	//gubun[1]�� ���� 1���� ������ �Ϲ� �ؽ�Ʈ�ڽ��� ǥ��
							replaceStr = "<input type='text' style='width:95%; ime-mode:active' align='center' name='" + cellname + "' size='15' value=\"" + rs.getString(cellname).replaceAll("\"", "&quot;") +"\" onkeydown=key_entertotab()>";
						}
					} else if("03".equals(gubun[0])) {	//�Ӽ��� ������ Į��(�ʱ� �ε�ÿ��� ȭ�鿡 �ڵ�����̶�� ���ڸ� ǥ��)
						//���İ������κ�
						replaceStr = gubun[1];
						replaceStr = "<input type='hidden' name='" + cellname + "' value=\"autocal\"><input type=\"text\" style=\"text-align:center;border:0;width:100%;\" disabled=\"true\" name=\"autocal\" cellname=\"" + cellname + "\" title=\"�ڵ����:" + replaceStr + "\">";
//						if(rs.getString(cellname).equals("") == false) {
//							replaceStr = gubun[1];
//							replaceStr = "<input type='hidden' name='" + cellname + "' value=\"autocal\"><input type=\"text\" style=\"text-align:center;border:0;width:100%;\" disabled=\"true\" name=\"autocal\" cellname=\"" + cellname + "\" title=\"�ڵ����:" + replaceStr + "\">";
//						} else {
//							replaceStr = rs.getString(cellname);
//							replaceStr += "<input type='hidden' name='" + cellname + "' value=\"autocal\">";
//						}
					} else if("04".equals(gubun[0])) {	//�Ӽ��� ����� Į��(gubun[1]�� ���� ATTLISTDTL�� ATTLISTMST���� �����;��� �ڵ��)
						attList = new ArrayList();
						attList = this.getAttListData(gubun[1]);
						replaceStr = "<select name='" + cellname + "' style='width:95%' align='center' onkeydown=key_entertotab()>";
						replaceStr += "<option value=\"\">==����==</option>";
						for(int k = 0; k < attList.size(); k++) {
							replaceStr += "<option value=\"" + attList.get(k) + "\"";
							if(attList.get(k).equals(rs.getString(cellname))) {
								replaceStr += " selected";
							}
							replaceStr += ">" + attList.get(k) + "</option>";
						}
						replaceStr += "</select>";
					}
					
					//formbodyhtml = formbodyhtml.replaceFirst("\\$"+cellname, replaceStr);
					StringBuffer sb = new StringBuffer(formbodyhtml);
					String cell = "$"+cellname;
					int index = formbodyhtml.indexOf(cell);
					sb.replace(index, index + cell.length(), replaceStr);
					formbodyhtml = sb.toString();
				}
				
				//����,��� ��ư�� formtatilhtml�� ��Ƽ� �ѱ�
				delRowImg = 
					"<div align='right' style='padding-top:5; padding-right:10'>" +
					"<table border='0'>" +
					"	<tr>" + 
					"		<td align='left'>&nbsp<input type='text' onfocus='this.blur();modifybutton"+rs.getInt("ROWNUM")+".click()' style='width:0'><img src='/images/common/btn_s_save.gif' name='modifybutton"+rs.getInt("ROWNUM")+"' onclick='checkModify(document.forms[0],\""+rs.getInt("ROWNUM")+"\")' style='CURSOR:hand' alt='����'>&nbsp;<img src='/images/common/btn_s_cancel.gif' style='CURSOR:hand' onclick='showModifyLayer(modifyLayer" + rs.getInt("ROWNUM") + ", document.forms[0],\""+rs.getInt("ROWNUM")+"\");' alt='���'></td>" +
					"	</tr>" +
					"</table>" +
					"</div>";
				Bean.setFormtailhtml(delRowImg);
				
				Bean.setFormheaderhtml(getFormatFormView(sysdocno, formseq).getFormheaderhtml());
				Bean.setFormbodyhtml(formbodyhtml);
				Bean.setSeqno(rs.getInt("ROWNUM"));
				Bean.setDatacnt(rs.getInt("TCNT"));
				datalist.add(Bean);
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
	
	/**
	 * ���� ����ڷ� ���� - ��������뿡 ���� ������ ���� �����ֱ�(���̾ ���� ����)
	 * ����� ��������
	 * @throws Exception 
	 */
	public List getFormDataList2(int sysdocno, int formseq, InputDeptSearchBoxBean idsbbean, int strDataIdx, int endDataIdx) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List datalist = null;
		
		StringBuffer sql = new StringBuffer();
		String cellname = "";
		String cellatt = "";
		String replaceStr = "";
		String[] gubun = new String[2];
		
		
		int cols = getFormatFormView(sysdocno, formseq).getTblcols();
		 
		String lbody = getFormatFormView(sysdocno, formseq).getFormbodyhtml();
		
		for(int i = 0; i < cols; i++) {
			if ( i < 26 ) {
				cellname = (char)('A' + i) + "";
			} else {
				cellname = (char)('A' + i - 26) + "" + (char)('A' + i - 26);
			}
			if ( i == 0 ) {
				sql.append("SELECT ROWNUM, A.* \n");
				sql.append("FROM (SELECT COUNT(*)OVER() TCNT, ROWSEQ, NVL(" + cellname + ", ' ') AS " + cellname + " ");
			} else {
				sql.append(", NVL(" + cellname + ", ' ') AS " + cellname + " ");
			}
		}
		sql.append("\n");
		sql.append("FROM DATALINEFRM A, TGTDEPT B, DEPT C     \n");
		sql.append("WHERE A.SYSDOCNO = B.SYSDOCNO             \n"); 
		sql.append("AND A.TGTDEPTCD = B.TGTDEPTCD             \n");
		sql.append("AND A.TGTDEPTCD = C.DEPT_ID(+)            \n");
		sql.append("AND A.SYSDOCNO = ?                        \n");
		sql.append("AND A.FORMSEQ = ?                         \n");		
		sql.append("AND NVL(DEPTCD1, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD2, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD3, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD4, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD5, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(DEPTCD6, '0') LIKE NVL(?, '%')    \n");
		sql.append("AND NVL(CHRGUNITCD, '0') LIKE NVL(?, '%') \n");
		sql.append("AND INPUTUSRID LIKE NVL(?, '%')           \n");
		sql.append("ORDER BY DEPT_RANK, ROWSEQ) A             \n");
		
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
			
			datalist = new ArrayList();
			
			while(rs.next()) {
				if ( strDataIdx-- > 0 ) {
					continue;
				} else {
					if ( endDataIdx-- == 0 ) break;
				}
				
				int strIdx = 0;
				int bodylen =0;
				String firstStr = "";
				String lastStr = "";
				String formbodyhtml = lbody;
				FormatLineBean Bean = new FormatLineBean();
				
				strIdx = formbodyhtml.indexOf(">", 0);
				firstStr = formbodyhtml.substring(0, strIdx + 1);
				lastStr = formbodyhtml.substring(strIdx + 1, formbodyhtml.length());
				String colHeadAtt_ext = "bgcolor=\"rgb(225,225,255)\"";
				formbodyhtml = firstStr + "<td align='center' style='border:1 solid gray' " + colHeadAtt_ext + "><input type=hidden name=rowseq  value=\""+ rs.getInt("ROWSEQ")+"\">" + rs.getInt("ROWNUM") + "</td>" + lastStr;
				
				bodylen = formbodyhtml.length();
				String delRowImg = "<td align='left' width='110'>&nbsp<img src='/images/common/btn_icon_edit.gif' alt='����' style='CURSOR:hand' onclick='showModifyLayer(modifyLayer" + rs.getInt("ROWNUM") + ", document.forms[0],\""+rs.getInt("ROWNUM")+"\");'>&nbsp<img src='/images/common/btn_icon_del.gif' alt='����' style='CURSOR:hand' onclick='checkDelete(document.forms[0],\""+rs.getInt("ROWNUM")+"\")'></td>";
				
				firstStr = formbodyhtml.substring(0, formbodyhtml.lastIndexOf("</tr>"));
				lastStr = formbodyhtml.substring(formbodyhtml.lastIndexOf("</tr>"), bodylen);
				formbodyhtml = firstStr + Utils.replace(lastStr, lastStr, delRowImg + "</tr>");
				for(int i=0; i<cols; i++){
					if(i < 26) {
						cellname = (char)('A' + i) + "";
					} else {
						cellname = (char)('A' + i - 26) + "" + (char)('A' + i - 26);
					}
					cellatt = getAttLineFrmData(sysdocno, formseq, cellname);
					gubun = cellatt.split("[|]");
					
					//�� �Ӽ��� ó��(gubun[0] : �Ӽ� ����, gubun[1] : �Ӽ� �Ķ��Ÿ)
					if("01".equals(gubun[0]) || "03".equals(gubun[0])) {	//�Ӽ��� ������ Į��(gubun[0]�� ���), ���ĵ� ����
						replaceStr = rs.getString(cellname);
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
						replaceStr = df.format(Double.parseDouble(strValue));
					} else if("02".equals(gubun[0])) {	//�Ӽ��� ������ Į��(gubun[1]�� ���� ���� ����)
						if(Integer.parseInt(gubun[1], 10) == 1) {
							replaceStr = rs.getString(cellname);
						} else {
							replaceStr = "<div align='left' style='padding-left:10; padding-right:10'>" + Utils.convertHtmlBrNbsp(rs.getString(cellname)) + "</div>";
						}
					} else if("04".equals(gubun[0])) {	//�Ӽ��� ����� Į��(gubun[1]�� ���� ATTLISTDTL�� ATTLISTMST���� �����;��� �ڵ��)
						replaceStr = rs.getString(cellname);
					}
					
					//formbodyhtml = formbodyhtml.replaceFirst("\\$"+cellname, replaceStr);
					StringBuffer sb = new StringBuffer(formbodyhtml);
					String cell = "$"+cellname;
					int index = formbodyhtml.indexOf(cell);
					sb.replace(index, index + cell.length(), replaceStr);
					formbodyhtml = sb.toString();
				}
				
				String formheaderhtml = getFormatFormView(sysdocno, formseq).getFormheaderhtml();
				int findIndex1 = formheaderhtml.indexOf("width=\"") + "width=\"".length();
				int findIndex2 = formheaderhtml.indexOf("\"", findIndex1);
				int width = Integer.parseInt(formheaderhtml.substring(findIndex1, findIndex2), 10) + 110;
				formheaderhtml = formheaderhtml.substring(0, findIndex1) + width + formheaderhtml.substring(findIndex2);
				
				Bean.setFormheaderhtml(formheaderhtml);
				Bean.setFormbodyhtml(formbodyhtml);
				Bean.setSeqno(rs.getInt("ROWNUM"));
				Bean.setDatacnt(rs.getInt("TCNT"));
				datalist.add(Bean);
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
