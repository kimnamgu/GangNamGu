/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합양식 dao
 * 설명:
 */
package nexti.ejms.format.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.util.Utils;
import nexti.ejms.util.commfunction;
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.common.ConnectionManager;
import nexti.ejms.common.appInfo;
import nexti.ejms.commtreat.model.CommTreatManager;
import nexti.ejms.dept.model.DeptManager;

public class FormatDAO {
	private static Logger logger = Logger.getLogger(FormatDAO.class);
	
	/**
	 * 입력부서 전체정보
	 * @param sysdocno
	 * @param deptid
	 * @param sch_deptInfo
	 * @return
	 * @throws Exception
	 */
	public List getInputDeptInfoAll(int sysdocno, String userid, String deptid, String[] sch_deptcd, String formkind) throws Exception {
		final int LEVEL = 6;
		List result = new ArrayList();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			if ( "01".equals(formkind) ) {
				formkind = "DATALINEFRM";
			} else if ( "02".equals(formkind) ) {
				formkind = "DATAFIXEDFRM";
			} else if ( "03".equals(formkind) ) {
				formkind = "DATABOOKFRM";
			}
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT DEPTCD1, TRIM(SUBSTR(DEPTNM1, ?)) DEPTNM1, DEPTCD2, TRIM(SUBSTR(DEPTNM2, ?)) DEPTNM2, DEPTCD3, TRIM(SUBSTR(DEPTNM3, ?)) DEPTNM3, \n");
			sql.append("       DEPTCD4, TRIM(SUBSTR(DEPTNM4, ?)) DEPTNM4, DEPTCD5, TRIM(SUBSTR(DEPTNM5, ?)) DEPTNM5, DEPTCD6, TRIM(SUBSTR(DEPTNM6, ?)) DEPTNM6, \n");
			sql.append("       D.CHRGUNITCD, NVL(C.CHRGUNITNM, '담당단위없음') CHRGUNITNM,    \n");
			sql.append("       INPUTUSRID, NVL(USER_NAME, '입력자:'||INPUTUSRID) INPUTUSRNM, \n");
			sql.append("       DEPT_RANK, LEV                                               \n");
			sql.append("FROM " + formkind + " D, USR U, CHRGUNIT C, DEPT DE,                \n");
			sql.append("     (SELECT LEVEL LEV, DEPT_ID                                     \n");
			sql.append("      FROM DEPT                                                     \n");
			sql.append("      START WITH DEPT_ID = ?                                        \n");
			sql.append("      CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID) Z                   \n");
			sql.append("WHERE D.INPUTUSRID = U.USER_ID(+)                                   \n");
			sql.append("AND D.TGTDEPTCD = C.DEPT_ID(+)                                      \n");
			sql.append("AND D.CHRGUNITCD = C.CHRGUNITCD(+)                                  \n");
			sql.append("AND D.TGTDEPTCD = DE.DEPT_ID(+)                                     \n");
			sql.append("AND D.TGTDEPTCD = Z.DEPT_ID(+)                                      \n");
			sql.append("AND SYSDOCNO = ?                                                    \n");
			sql.append("AND TGTDEPTCD IN (SELECT TGTDEPTCD                                  \n");
			sql.append("                  FROM (SELECT TGTDEPTCD, TGTDEPTNM, PREDEPTCD      \n");
			sql.append("                        FROM TGTDEPT                                \n");
			sql.append("                        WHERE SYSDOCNO = ?                          \n");
			sql.append("                        UNION ALL                                   \n");
			sql.append("                        SELECT 'M_' || COLDEPTCD, COLDEPTNM, NULL   \n");
			sql.append("                        FROM DOCMST                                 \n");
			sql.append("                        WHERE SYSDOCNO = ?)                         \n");
			sql.append("                  START WITH TGTDEPTCD = ?                          \n");
			sql.append("                  CONNECT BY PRIOR TGTDEPTCD = PREDEPTCD)           \n");
			sql.append("AND NVL(DEPTCD1, '0') LIKE NVL(?, '%')                              \n");
			sql.append("AND NVL(DEPTCD2, '0') LIKE NVL(?, '%')                              \n");
			sql.append("AND NVL(DEPTCD3, '0') LIKE NVL(?, '%')                              \n");
			sql.append("AND NVL(DEPTCD4, '0') LIKE NVL(?, '%')                              \n");
			sql.append("AND NVL(DEPTCD5, '0') LIKE NVL(?, '%')                              \n");
			sql.append("AND NVL(DEPTCD6, '0') LIKE NVL(?, '%')                              \n");
			sql.append("AND NVL(D.CHRGUNITCD, '0') LIKE NVL(?, '%')                         \n");
			sql.append("AND INPUTUSRID LIKE NVL(?, '%')                                     \n");
			sql.append("GROUP BY DEPTCD1, DEPTNM1, DEPTCD2, DEPTNM2, DEPTCD3, DEPTNM3,      \n");
			sql.append("         DEPTCD4, DEPTNM4, DEPTCD5, DEPTNM5, DEPTCD6, DEPTNM6,      \n");
			sql.append("         D.CHRGUNITCD, NVL(C.CHRGUNITNM, '담당단위없음'),             \n");
			sql.append("         INPUTUSRID, NVL(USER_NAME, '입력자:'||INPUTUSRID),          \n");
			sql.append("         DEPT_RANK, LEV                                             \n");
			sql.append("ORDER BY LEV, DEPT_RANK                                             \n");

			con = ConnectionManager.getConnection();
			
			int substring = DeptManager.instance().getDeptInfo(appInfo.getRootid()).getDept_fullname().length() + 1;
			int depth = CommTreatManager.instance().getTargetDeptLevel(con, sysdocno, deptid);
			String[][] deptInfo = getInputDeptInfo(con, sysdocno, deptid);
			
			String coldeptcd = ColldocManager.instance().getColldoc(sysdocno).getColdeptcd();
			if ( deptid.equals(coldeptcd) ) {
				deptid = "M_" + coldeptcd;
			}

			pstmt = con.prepareStatement(sql.toString());
			int idx = 0;
			pstmt.setInt(++idx, substring);
			pstmt.setInt(++idx, substring);
			pstmt.setInt(++idx, substring);
			pstmt.setInt(++idx, substring);
			pstmt.setInt(++idx, substring);
			pstmt.setInt(++idx, substring);
			pstmt.setString(++idx, appInfo.getRootid());
			pstmt.setInt(++idx, sysdocno);
			pstmt.setInt(++idx, sysdocno);
			pstmt.setInt(++idx, sysdocno);
			pstmt.setString(++idx, deptid);
			
			idx = 12;
			for ( int i = 0; i < LEVEL + 2; i++ ) {
				for ( int j = 0; j < i; j ++ ) {
					if ( j < depth ) {
						pstmt.setString(j + idx, Utils.nullToEmptyString(deptInfo[j][0]));
					} else if ( j >= depth && j < LEVEL + 2 ) {
						pstmt.setString(j + idx, Utils.nullToEmptyString(sch_deptcd[j]));
					}
				}
				for ( int j = i; j < LEVEL + 2; j++ ) {
					pstmt.setString(j + idx, "");
				}
				
				rs = pstmt.executeQuery();
				  
				ArrayList info1 = null;
				ArrayList info2 = null;
				String[][] info = null;
				while ( rs.next() ) {
					if ( info1 == null ) info1 = new ArrayList();
					if ( info2 == null ) info2 = new ArrayList();
					info1.add(Utils.nullToEmptyString(rs.getString(i * 2 + 1)));
					info2.add(Utils.nullToEmptyString(rs.getString(i * 2 + 2)));					
				}
				
				if ( info1 != null ) {
					info = new String[2][info1.size()];
					info[0] = (String[])info1.toArray(info[0]);
					info[1] = (String[])info2.toArray(info[1]);
				} else {
					info = new String[2][1];					
					info[0][0] = "";
					info[1][0] = "";
				}
				
				if ( result == null ) result = new ArrayList();
				result.add(info);
				
				try { rs.close(); } catch ( Exception e ) {}
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
	 * 입력부서 부서정보
	 * @param con
	 * @param sysdocno
	 * @param deptid
	 * @return
	 * @throws Exception
	 */
	public String[][] getInputDeptInfo(Connection con, int sysdocno, String deptid) throws Exception {
		final int LEVEL = 6;
		String[][] result = new String[LEVEL][2]; 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT LEVEL, SYSDOCNO, TGTDEPTNM,                                       \n");
			sql.append("       DECODE(PREDEPTCD, '0', SUBSTR(TGTDEPTCD, 3), TGTDEPTCD) TGTDEPTCD \n");
			sql.append("FROM (SELECT SYSDOCNO, TGTDEPTCD, DEPT_FULLNAME TGTDEPTNM, PREDEPTCD     \n");
			sql.append("      FROM TGTDEPT T, DEPT D                                             \n");
			sql.append("      WHERE T.TGTDEPTCD = D.DEPT_ID                                      \n");
			sql.append("      AND SYSDOCNO = ?                                                   \n");
			sql.append("      UNION ALL                                                          \n");
			sql.append("      SELECT SYSDOCNO, 'M_' || COLDEPTCD, DEPT_FULLNAME, '0'             \n");
			sql.append("      FROM DOCMST T, DEPT D                                              \n");
			sql.append("      WHERE T.COLDEPTCD = D.DEPT_ID                                      \n");
			sql.append("      AND SYSDOCNO = ? )                                                 \n");
			sql.append("START WITH TGTDEPTCD = ?                                                 \n");
			sql.append("CONNECT BY PRIOR PREDEPTCD = TGTDEPTCD                                   \n");
			sql.append("ORDER BY LEVEL DESC                                                      \n");

			if ( sysdocno <= 0 ) {
				deptid = "M_" + deptid;
			} else {
				String coldeptcd = ColldocManager.instance().getColldoc(sysdocno).getColdeptcd();
				if ( deptid.equals(coldeptcd) ) {
					deptid = "M_" + coldeptcd;
				}
			}
			
			pstmt = con.prepareStatement(sql.toString());
			int idx = 0;
			pstmt.setInt(++idx, sysdocno);
			pstmt.setInt(++idx, sysdocno);
			pstmt.setString(++idx, deptid);
			
			rs = pstmt.executeQuery();
			
			for ( int i = 0; i < LEVEL; i++ ) {
				if ( rs.next() ) {
					result[i][0] = Utils.nullToEmptyString(rs.getString("TGTDEPTCD"));
					result[i][1] = Utils.nullToEmptyString(rs.getString("TGTDEPTNM"));
				} else {
					result[i][0] = "";
					result[i][1] = "";
				}
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 사용했던양식 속성복사
	 * @param Connection con
	 * @param FormatBean fbean
	 * @param String user_id
	 * @throws Exception
	 */
	public void copyUsedFormat(Connection con, FormatBean fbean, String user_id) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			int sysdocno = fbean.getSysdocno();
			int formseq = fbean.getFormseq();
			int usedsysdocno = fbean.getUsedsysdocno();
			int usedformseq = fbean.getUsedformseq();
			String formkind = fbean.getFormkind();
			
			String query;
			
			//양식정보 복사
			query = "INSERT INTO " +
					"FORMMST(SYSDOCNO, FORMSEQ, FORMTITLE, FORMKIND, FORMCOMMENT, " +
					"        TBLCOLS, TBLROWS, ORD, CRTDT, CRTUSRID, UPTDT, UPTUSRID) " +
					"SELECT "+sysdocno+", "+formseq+", FORMTITLE, FORMKIND, FORMCOMMENT, " +
					"       TBLCOLS, TBLROWS, "+formseq+", " +
					"       TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), '" + user_id + "', " +
					"       TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), '" + user_id + "' " +
					"FROM FORMMST " +
					"WHERE SYSDOCNO = ? " +
					"  AND FORMSEQ = ? ";
			
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, usedsysdocno);
			pstmt.setInt(2, usedformseq);
			pstmt.executeUpdate();
			try {pstmt.close();} catch(Exception e) {}

			String[] columnName = {"FORMHTML", "FORMHEADERHTML", "FORMBODYHTML", "FORMTAILHTML"};
			String[] columnData = new String[4];
			for ( int i = 0; i < columnName.length; i++ ) {
				query = "SELECT " + columnName[i] + " FROM FORMMST WHERE SYSDOCNO = ? AND FORMSEQ = ?";
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1, usedsysdocno);
				pstmt.setInt(2, usedformseq);
				rs = pstmt.executeQuery();
				if ( rs.next() ) {
					columnData[i] = Utils.readClobData(rs, columnName[i]);
				}
				ConnectionManager.close(pstmt, rs);
			}
			
			for ( int i = 0; i < columnName.length; i++ ) {
				query = "UPDATE FORMMST SET " + columnName[i] + " = EMPTY_CLOB() WHERE SYSDOCNO = ? AND FORMSEQ = ?";
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1, sysdocno);
				pstmt.setInt(2, formseq);
				rs = pstmt.executeQuery();
				ConnectionManager.close(pstmt, rs);

				query = "SELECT " + columnName[i] + " FROM FORMMST WHERE SYSDOCNO = ? AND FORMSEQ = ? FOR UPDATE";
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1, sysdocno);
				pstmt.setInt(2, formseq);
				rs = pstmt.executeQuery();
				if ( rs.next() ) {
					Utils.writeClobData(rs, columnName[i], columnData[i]);
				}
				ConnectionManager.close(pstmt, rs);
			}
			ConnectionManager.close(pstmt, rs);
			
			//양식속성 복사
			if(formkind.equals("01") == true) {
				query = "INSERT INTO " +
						"ATTLINEFRM(SYSDOCNO, FORMSEQ, " +
						"           A, B, C, D, E, F, G, H, I, J, K, L, M, " +
						"           N, O, P, Q, R, S, T, U, V, W, X, Y, Z, " +
						"           AA, BB, CC, DD, EE, FF, GG, HH, II, JJ, KK, LL, MM, " +
						"           NN, OO, PP, QQ, RR, SS, TT, UU, VV, WW, XX, YY, ZZ) " +
						"SELECT "+sysdocno+", "+formseq+", " +
						"       A, B, C, D, E, F, G, H, I, J, K, L, M, " +
						"       N, O, P, Q, R, S, T, U, V, W, X, Y, Z, " +
						"       AA, BB, CC, DD, EE, FF, GG, HH, II, JJ, KK, LL, MM, " +
						"       NN, OO, PP, QQ, RR, SS, TT, UU, VV, WW, XX, YY, ZZ " +
						"FROM ATTLINEFRM " +
						"WHERE SYSDOCNO = ? " +
						"  AND FORMSEQ = ? ";
		
				pstmt = con.prepareStatement(query);
				
				pstmt.setInt(1, usedsysdocno);
				pstmt.setInt(2, usedformseq);
				pstmt.executeUpdate();
				try {pstmt.close();} catch(Exception e) {}
			} else if(formkind.equals("02") == true) {
				query = "INSERT INTO " +
						"ATTFIXEDFRM(SYSDOCNO, FORMSEQ, " +
						"           A, B, C, D, E, F, G, H, I, J, K, L, M, " +
						"           N, O, P, Q, R, S, T, U, V, W, X, Y, Z, " +
						"           AA, BB, CC, DD, EE, FF, GG, HH, II, JJ, KK, LL, MM, " +
						"           NN, OO, PP, QQ, RR, SS, TT, UU, VV, WW, XX, YY, ZZ) " +
						"SELECT "+sysdocno+", "+formseq+", " +
						"       A, B, C, D, E, F, G, H, I, J, K, L, M, " +
						"       N, O, P, Q, R, S, T, U, V, W, X, Y, Z, " +
						"       AA, BB, CC, DD, EE, FF, GG, HH, II, JJ, KK, LL, MM, " +
						"       NN, OO, PP, QQ, RR, SS, TT, UU, VV, WW, XX, YY, ZZ " +
						"FROM ATTFIXEDFRM " +
						"WHERE SYSDOCNO = ? " +
						"  AND FORMSEQ = ? ";
		
				pstmt = con.prepareStatement(query);
				
				pstmt.setInt(1, usedsysdocno);
				pstmt.setInt(2, usedformseq);
				pstmt.executeUpdate();
				try {pstmt.close();} catch(Exception e) {}
			} else if(formkind.equals("03") == true) {
				query = "INSERT INTO " +
						"ATTBOOKFRM(SYSDOCNO, FORMSEQ, SEQ, CATEGORYNM, ORD) " +
						"SELECT "+sysdocno+", "+formseq+", SEQ, CATEGORYNM, ORD " +
						"FROM ATTBOOKFRM " +
						"WHERE SYSDOCNO = ? " +
						"  AND FORMSEQ = ? ";
		
				pstmt = con.prepareStatement(query);
				
				pstmt.setInt(1, usedsysdocno);
				pstmt.setInt(2, usedformseq);
				pstmt.executeUpdate();
				try {pstmt.close();} catch(Exception e) {}
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
	}
	
	/**
	 * 사용했던양식 파일복사
	 * @param Connection conn
	 * @param FormatBean fbean
	 * @param int fileseq
	 * @param String filenm
	 * @return
	 * @throws Exception
	 */
	public int copyUsedFormatFile(Connection conn, FormatBean fbean, int fileseq, String filenm) throws Exception {
		
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			int sysdocno = fbean.getSysdocno();
			int formseq = fbean.getFormseq();
			int usedsysdocno = fbean.getUsedsysdocno();
			int usedformseq = fbean.getUsedformseq();
			
			String query;
			
			query = "INSERT INTO " +
					"FILEBOOKFRM(SYSDOCNO, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD) " +
					"SELECT "+sysdocno+", "+formseq+", FILESEQ, '"+filenm+"', ORIGINFILENM, FILESIZE, EXT, ORD " +
					"FROM FILEBOOKFRM " +
					"WHERE SYSDOCNO = ? " +
					"  AND FORMSEQ = ? " +
					"  AND FILESEQ = ? ";

			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, usedsysdocno);
			pstmt.setInt(2, usedformseq);
			pstmt.setInt(3, fileseq);
			
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
	 * 공통양식 속성복사
	 * @param Connection conn
	 * @param FormatBean fbean
	 * @param String user_id
	 * @throws Exception
	 */
	public void copyCommFormat(Connection con, FormatBean fbean, String user_id) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			int sysdocno = fbean.getSysdocno();
			int formseq = fbean.getFormseq();
			int commformseq = fbean.getCommformseq();
			String formkind = fbean.getFormkind();
			
			String query;
			
			//양식정보 복사
			query = "INSERT INTO " +
					"FORMMST(SYSDOCNO, FORMSEQ, FORMTITLE, FORMKIND, FORMCOMMENT, " +
					"        TBLCOLS, TBLROWS, ORD, CRTDT, CRTUSRID, UPTDT, UPTUSRID) " +
					"SELECT "+sysdocno+", "+formseq+", FORMTITLE, FORMKIND, FORMCOMMENT, " +
					"       TBLCOLS, TBLROWS, "+formseq+", " +
					"       TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), '" + user_id + "', " +
					"       TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), '" + user_id + "' " +
					"FROM COMMFORMMST " +
					"WHERE FORMSEQ = ? ";
			
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, commformseq);
			pstmt.executeUpdate();
			try {pstmt.close();} catch(Exception e) {}
			
			String[] columnName = {"FORMHTML", "FORMHEADERHTML", "FORMBODYHTML", "FORMTAILHTML"};
			String[] columnData = new String[4];
			for ( int i = 0; i < columnName.length; i++ ) {
				query = "SELECT " + columnName[i] + " FROM COMMFORMMST WHERE FORMSEQ = ?";
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1, commformseq);
				rs = pstmt.executeQuery();
				if ( rs.next() ) {
					columnData[i] = Utils.readClobData(rs, columnName[i]);
				}
				ConnectionManager.close(pstmt, rs);
			}
			
			for ( int i = 0; i < columnName.length; i++ ) {
				query = "UPDATE FORMMST SET " + columnName[i] + " = EMPTY_CLOB() WHERE SYSDOCNO = ? AND FORMSEQ = ?";
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1, sysdocno);
				pstmt.setInt(2, formseq);
				rs = pstmt.executeQuery();
				ConnectionManager.close(pstmt, rs);

				query = "SELECT " + columnName[i] + " FROM FORMMST WHERE SYSDOCNO = ? AND FORMSEQ = ? FOR UPDATE";
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1, sysdocno);
				pstmt.setInt(2, formseq);
				rs = pstmt.executeQuery();
				if ( rs.next() ) {
					Utils.writeClobData(rs, columnName[i], columnData[i]);
				}
				ConnectionManager.close(pstmt, rs);
			}
			ConnectionManager.close(pstmt, rs);
			
			//양식속성 복사
			if(formkind.equals("01") == true) {
				query = "INSERT INTO " +
						"ATTLINEFRM(SYSDOCNO, FORMSEQ, " +
						"           A, B, C, D, E, F, G, H, I, J, K, L, M, " +
						"           N, O, P, Q, R, S, T, U, V, W, X, Y, Z, " +
						"           AA, BB, CC, DD, EE, FF, GG, HH, II, JJ, KK, LL, MM, " +
						"           NN, OO, PP, QQ, RR, SS, TT, UU, VV, WW, XX, YY, ZZ) " +
						"SELECT "+sysdocno+", "+formseq+", " +
						"       A, B, C, D, E, F, G, H, I, J, K, L, M, " +
						"       N, O, P, Q, R, S, T, U, V, W, X, Y, Z, " +
						"       AA, BB, CC, DD, EE, FF, GG, HH, II, JJ, KK, LL, MM, " +
						"       NN, OO, PP, QQ, RR, SS, TT, UU, VV, WW, XX, YY, ZZ " +
						"FROM COMMATTLINEFRM " +
						"WHERE FORMSEQ = ? ";
		
				pstmt = con.prepareStatement(query);
				
				pstmt.setInt(1, commformseq);
				pstmt.executeUpdate();
				try {pstmt.close();} catch(Exception e) {}
			} else if(formkind.equals("02") == true) {
				query = "INSERT INTO " +
						"ATTFIXEDFRM(SYSDOCNO, FORMSEQ, " +
						"           A, B, C, D, E, F, G, H, I, J, K, L, M, " +
						"           N, O, P, Q, R, S, T, U, V, W, X, Y, Z, " +
						"           AA, BB, CC, DD, EE, FF, GG, HH, II, JJ, KK, LL, MM, " +
						"           NN, OO, PP, QQ, RR, SS, TT, UU, VV, WW, XX, YY, ZZ) " +
						"SELECT "+sysdocno+", "+formseq+", " +
						"       A, B, C, D, E, F, G, H, I, J, K, L, M, " +
						"       N, O, P, Q, R, S, T, U, V, W, X, Y, Z, " +
						"       AA, BB, CC, DD, EE, FF, GG, HH, II, JJ, KK, LL, MM, " +
						"       NN, OO, PP, QQ, RR, SS, TT, UU, VV, WW, XX, YY, ZZ " +
						"FROM COMMATTFIXEDFRM " +
						"WHERE FORMSEQ = ? ";
		
				pstmt = con.prepareStatement(query);
				
				pstmt.setInt(1, commformseq);
				pstmt.executeUpdate();
				try {pstmt.close();} catch(Exception e) {}
			} else if(formkind.equals("03") == true) {
				query = "INSERT INTO " +
						"ATTBOOKFRM(SYSDOCNO, FORMSEQ, SEQ, CATEGORYNM, ORD) " +
						"SELECT "+sysdocno+", "+formseq+", SEQ, CATEGORYNM, ORD " +
						"FROM COMMATTBOOKFRM " +
						"WHERE FORMSEQ = ? ";
		
				pstmt = con.prepareStatement(query);
				
				pstmt.setInt(1, commformseq);
				pstmt.executeUpdate();
				try {pstmt.close();} catch(Exception e) {}
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
	}
	
	/**
	 * 공통양식 파일복사
	 * @param Connection conn
	 * @param FormatBean fbean
	 * @param int fileseq
	 * @param String filenm
	 * @return
	 * @throws Exception
	 */
	public int copyCommFormatFile(Connection conn, FormatBean fbean, int fileseq, String filenm) throws Exception {
		
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			int sysdocno = fbean.getSysdocno();
			int formseq = fbean.getFormseq();
			int commformseq = fbean.getCommformseq();
			
			String query;
			
			query = "INSERT INTO " +
					"FILEBOOKFRM(SYSDOCNO, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD) " +
					"SELECT "+sysdocno+", "+formseq+", FILESEQ, '"+filenm+"', ORIGINFILENM, FILESIZE, EXT, FILESEQ " +
					"FROM COMMFILEBOOKFRM " +
					"WHERE FORMSEQ = ? " +
					"  AND FILESEQ = ? ";

			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, commformseq);
			pstmt.setInt(2, fileseq);
			
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
	 * 공통양식 목록 가져오기 
	 * @param FormatBean fbean
	 * @return List 공통양식목록(ArrayList)
	 * @throws Exception
	 */
	public List getListCommFormat(FormatBean fbean, int start, int end) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		
		List result = null;
		
		try {
			String sql =
				"SELECT (CNT-SEQ+1) BUNHO, FORMSEQ, FORMTITLE, FORMKIND, CCDNAME " +
				"FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* " +
				"      FROM (SELECT ROWNUM SEQ, A2.* " +
				"            FROM (SELECT A.FORMSEQ, A.FORMTITLE, A.FORMKIND, B.CCDNAME " +
				"                  FROM COMMFORMMST A, CCD B " +
				"                  WHERE A.FORMKIND = B.CCDSUBCD " +
				"                    AND B.CCDCD = '002' " +
				"                    AND A.DEPT_ID IN (SELECT DEPT_ID " +
				"                                      FROM DEPT D " +
				"                                      START WITH DEPT_ID = ? " +
				"                                      CONNECT BY PRIOR D.DEPT_ID = D.UPPER_DEPT_ID) " +
				"                    AND A.FORMTITLE LIKE ? " +
				"                  ORDER BY A.UPTDT DESC) A2) A1) " +
				"WHERE SEQ BETWEEN ? AND ? ";
			
			conn = ConnectionManager.getConnection();
        	
        	pstmt = conn.prepareStatement(sql);
        	
        	pstmt.setString(1, fbean.getSearchdept());
        	pstmt.setString(2, "%"+fbean.getSearchtitle()+"%");
        	pstmt.setInt(3, start);
        	pstmt.setInt(4, end);
        	
        	rs = pstmt.executeQuery();
			
        	result = new ArrayList();
			
			while(rs.next()) {
				FormatBean bean = new FormatBean();
				
				bean.setSeqno(rs.getInt("BUNHO"));
				bean.setCommformseq(rs.getInt("FORMSEQ"));
				bean.setFormtitle(rs.getString("FORMTITLE"));
				bean.setFormkind(rs.getString("FORMKIND"));
				bean.setFormkindname(rs.getString("CCDNAME"));
			
				result.add(bean);
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(conn, pstmt, rs);
			throw e;
	     } finally {	       
	    	 ConnectionManager.close(conn, pstmt, rs);
	     }
	
		return result;
	}
	
	/**
	 * 공통양식관리 목록 가져오기 
	 * @param FormatBean fbean
	 * @return List 공통양식목록(ArrayList)
	 * @throws Exception
	 */
	public List getListCommFormatMgr(FormatBean fbean, String user_id, int start, int end) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		
		List result = null;

		int subLen = commfunction.getDeptFullNameSubstringIndex(user_id);
		try {
			String sql =
				"SELECT BUNHO, FORMSEQ, FORMTITLE, FORMKIND, CCDNAME, DEPT_NAME, MAKEDATE " +
				"FROM (SELECT ROWNUM BUNHO, A2.* " +
				"      FROM (SELECT A.FORMSEQ, A.FORMTITLE, A.FORMKIND, B.CCDNAME, NVL(TRIM(SUBSTR(DEPT_FULLNAME, "+subLen+")), C.DEPT_NAME) AS DEPT_NAME, " +
				"            TO_CHAR(TO_DATE(A.UPTDT, 'YYYY-MM-DD HH24:MI:SS'), 'YYYY\"년 \"MM\"월 \"DD\"일') MAKEDATE " +
				"            FROM COMMFORMMST A, CCD B , DEPT C" + 
				"            WHERE A.DEPT_ID = C.DEPT_ID" +
				"              AND A.FORMKIND = B.CCDSUBCD  " +
				"              AND B.CCDCD = '002' " + 
				"              AND A.DEPT_ID IN (SELECT DEPT_ID " + 
				"                                FROM DEPT D " + 
				"                                START WITH DEPT_ID = ? " + 
				"                                CONNECT BY PRIOR D.DEPT_ID = D.UPPER_DEPT_ID) " + 
				"              AND A.FORMTITLE LIKE ? " +
				"              AND NVL(A.FORMCOMMENT, ' ') LIKE ? " + 
				"            ORDER BY A.FORMTITLE ASC, A.UPTDT DESC) A2) A1 " +
				"WHERE BUNHO BETWEEN ? AND ? ";
			
			conn = ConnectionManager.getConnection();
        	pstmt = conn.prepareStatement(sql);
        	
        	pstmt.setString(1, fbean.getSearchdept());
        	pstmt.setString(2, "%"+fbean.getSearchtitle()+"%");
        	pstmt.setString(3, "%"+fbean.getSearchcomment()+"%");
        	pstmt.setInt(4, start);
        	pstmt.setInt(5, end);
        	
        	rs = pstmt.executeQuery();
			
        	result = new ArrayList();
			
			while(rs.next()) {
				FormatBean bean = new FormatBean();
				
				bean.setSeqno(rs.getInt("BUNHO"));
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setFormtitle(rs.getString("FORMTITLE"));
				bean.setFormkind(rs.getString("FORMKIND"));
				bean.setFormkindname(rs.getString("CCDNAME"));
				bean.setDeptcd(rs.getString("DEPT_NAME"));
				bean.setUptdt(rs.getString("MAKEDATE"));
			
				result.add(bean);
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
	    	 ConnectionManager.close(conn, pstmt, rs);
			throw e;
	     } finally {	       
	    	 ConnectionManager.close(conn, pstmt, rs);
	     }
	
		return result;
	}
	
	/**
	 * 사용했던양식 목록 가져오기 
	 * @param FormatBean fbean
	 * @return List 공통양식목록(ArrayList)
	 * @throws Exception
	 */
	public List getListUsedFormat(FormatBean fbean, int start, int end) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		
		List result = null;
		
		try {

			String sql =
				"SELECT (CNT-SEQ+1) BUNHO, SYSDOCNO, FORMSEQ, FORMTITLE, FORMKIND, CCDNAME " +
				"FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* " +
				"      FROM (SELECT ROWNUM SEQ, A2.* " +
				"            FROM (SELECT A.SYSDOCNO, A.FORMSEQ, A.FORMTITLE, A.FORMKIND, B.CCDNAME " +
				"                  FROM FORMMST A, CCD B, DOCMST C, USR D " +
				"                  WHERE A.FORMKIND = B.CCDSUBCD " +
				"                    AND B.CCDCD = '002' " +
				"                    AND A.SYSDOCNO = C.SYSDOCNO " +
				"                    AND C.DOCSTATE > '01' " +
				"                    AND A.UPTUSRID = D.USER_ID " +
				"                    AND C.COLDEPTCD IN (SELECT DEPT_ID " +
				"                                        FROM DEPT " +
				"                                        START WITH DEPT_ID = ? " +
				"                                        CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID) " +
				"                    AND A.FORMTITLE LIKE ? " +
				"                    AND A.UPTDT LIKE ? " +
				"                    AND D.USER_NAME LIKE ? " +
				"                  ORDER BY A.UPTDT DESC) A2) A1) " +
				"WHERE SEQ BETWEEN ? AND ? ";
			
			conn = ConnectionManager.getConnection();
        	
        	pstmt = conn.prepareStatement(sql);
        	
        	pstmt.setString(1, fbean.getSearchdept());
        	pstmt.setString(2, "%"+fbean.getSearchtitle()+"%");
        	pstmt.setString(3, fbean.getSearchyear()+"%");
        	pstmt.setString(4, "%"+fbean.getSearchuser()+"%");
        	pstmt.setInt(5, start);
        	pstmt.setInt(6, end);
        	
        	rs = pstmt.executeQuery();
			
        	result = new ArrayList();
			
			while(rs.next()) {
				FormatBean bean = new FormatBean();
				
				bean.setSeqno(rs.getInt("BUNHO"));
				bean.setUsedsysdocno(rs.getInt("SYSDOCNO"));
				bean.setUsedformseq(rs.getInt("FORMSEQ"));
				bean.setFormtitle(rs.getString("FORMTITLE"));
				bean.setFormkind(rs.getString("FORMKIND"));
				bean.setFormkindname(rs.getString("CCDNAME"));
			
				result.add(bean);
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
	    	 ConnectionManager.close(conn, pstmt, rs);
			throw e;
	     } finally {	       
	    	 ConnectionManager.close(conn, pstmt, rs);
	     }
	
		return result;
	}
	
	/**
	 * 공통양식 개수 가져오기
	 * @param FormatBean fbean
	 * @return int 수행개수
	 * @throws Exception
	 */
	public int getCountCommFormat(FormatBean fbean) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
		
		int result = 0;
		
		try {
			String sql =
				"SELECT COUNT(*) " +
				"FROM COMMFORMMST " +
				"WHERE DEPT_ID IN (SELECT DEPT_ID " +
				"                  FROM DEPT D " +
				"                  START WITH DEPT_ID = ? " +
				"                  CONNECT BY PRIOR D.DEPT_ID = D.UPPER_DEPT_ID) " +
				"  AND FORMTITLE LIKE ? " +
				"  AND NVL(FORMCOMMENT, ' ') LIKE ? ";
			
			conn = ConnectionManager.getConnection();
        	
        	pstmt = conn.prepareStatement(sql);
        	
        	pstmt.setString(1, fbean.getSearchdept());
        	pstmt.setString(2, "%"+fbean.getSearchtitle()+"%");
        	pstmt.setString(3, "%"+fbean.getSearchcomment()+"%");
        	
        	rs = pstmt.executeQuery();
        	
        	if(rs.next()) {
        		result = rs.getInt(1);
        	}
		} catch(Exception e) {
			logger.error("ERROR",e);
	    	 ConnectionManager.close(conn, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(conn, pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 사용했던양식 개수 가져오기 
	 * @param FormatBean fbean
	 * @return int 수행개수
	 * @throws Exception
	 */
	public int getCountUsedFormat(FormatBean fbean) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
		
		int result = 0;
		
		try {
			String sql =
				"SELECT COUNT(*) " +
				"FROM FORMMST A, DOCMST B, USR C " +
				"WHERE A.SYSDOCNO = B.SYSDOCNO " +
				"  AND B.DOCSTATE > '01' " +
				"  AND A.UPTUSRID = C.USER_ID " +
				"  AND B.COLDEPTCD IN (SELECT DEPT_ID " +
				"                      FROM DEPT " +
				"                      START WITH DEPT_ID = ? " +
				"                      CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID) " +
				"  AND A.FORMTITLE LIKE ? " +
				"  AND A.UPTDT LIKE ? " +
				"  AND C.USER_NAME LIKE ? ";
			
			conn = ConnectionManager.getConnection();
        	
        	pstmt = conn.prepareStatement(sql);
        	
        	pstmt.setString(1, fbean.getSearchdept());
        	pstmt.setString(2, "%"+fbean.getSearchtitle()+"%");
        	pstmt.setString(3, fbean.getSearchyear()+"%");
        	pstmt.setString(4, "%"+fbean.getSearchuser()+"%");
        	
        	rs = pstmt.executeQuery();
        	
        	if(rs.next()) {
        		result = rs.getInt(1);
        	}
		} catch(Exception e) {
			logger.error("ERROR",e);
	    	 ConnectionManager.close(conn, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(conn, pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 양식형태명 가져오기
	 * 
	 * @param formkind : 양식종류코드
	 * 
	 * @return String : 양식형태명
	 * @throws Exception 
	 */
	public String getFormkindName(String formkind) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
		
        String result = null;
		
		try {
			String query =
				"SELECT CCDNAME " +
				"FROM CCD " +
				"WHERE CCDCD = '002' " +
				"  AND CCDSUBCD = ? ";
			
			conn = ConnectionManager.getConnection();
        	
        	pstmt = conn.prepareStatement(query);
        	
        	pstmt.setString(1, formkind);
        	
        	rs = pstmt.executeQuery();
        	
        	if(rs.next()) {
        		result = rs.getString(1);
        	}
		} catch(Exception e) {
			logger.error("ERROR",e);
	    	 ConnectionManager.close(conn, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(conn, pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 양식정보 가져오기
	 * @param sysdocno : 시스템문서번호 
	 * @param formseq : 양식일련번호
	 * @return FormatBean : 양식정보
	 * @throws Exception 
	 */
	public FormatBean getFormat(int sysdocno, int formseq) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
		
        FormatBean bean = null;
		
		try {
			String query;
			
			query = "SELECT  A.SYSDOCNO, A.FORMSEQ, A.FORMTITLE, A.FORMKIND, A.FORMCOMMENT, " +
					"        A.TBLCOLS, A.TBLROWS, A.ORD, A.CRTDT, A.CRTUSRID, A.UPTDT, A.UPTUSRID, B.CCDNAME " +
					"FROM FORMMST A, CCD B " +
					"WHERE A.FORMKIND = B.CCDSUBCD " +
					"  AND B.CCDCD = '002' " +
					"  AND A.SYSDOCNO = ? " +
					"  AND A.FORMSEQ = ? ";
			
			conn = ConnectionManager.getConnection();
        	
        	pstmt = conn.prepareStatement(query);
        	
        	pstmt.setInt(1, sysdocno);
        	pstmt.setInt(2, formseq);
        	
        	rs = pstmt.executeQuery();
        	
        	bean = new FormatBean();
        	
        	if(rs.next()) {
        		bean.setSysdocno(rs.getInt("SYSDOCNO"));				//시스템문서번호
        		bean.setFormseq(rs.getInt("FORMSEQ"));					//양식일련번호
        		bean.setFormtitle(rs.getString("FORMTITLE"));			//양식제목
        		bean.setFormkind(rs.getString("FORMKIND"));				//양식종류코드
        		bean.setFormkindname(rs.getString("CCDNAME"));			//양식종류이름
        		bean.setFormcomment(rs.getString("FORMCOMMENT"));		//양식개요
        		bean.setTblcols(rs.getInt("TBLCOLS"));					//열개수
        		bean.setTblrows(rs.getInt("TBLROWS"));					//행개수
        		bean.setOrd(rs.getInt("ORD"));							//정렬순서
        		bean.setCrtdt(rs.getString("CRTDT"));					//생성일자
        		bean.setCrtusrid(rs.getString("CRTUSRID"));				//생성자ID
        		bean.setUptdt(rs.getString("UPTDT"));					//수정일자
        		bean.setUptusrid(rs.getString("UPTUSRID"));				//수정자ID
        		
        		ConnectionManager.close(pstmt, rs);
    			String[] columnName = {"FORMHTML", "FORMHEADERHTML", "FORMBODYHTML", "FORMTAILHTML"};
    			String[] columnData = new String[4];
    			for ( int i = 0; i < columnName.length; i++ ) {
    				query = "SELECT " + columnName[i] + " FROM FORMMST WHERE SYSDOCNO = ? AND FORMSEQ = ?";
    				pstmt = conn.prepareStatement(query);
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
			logger.error("ERROR",e);
	    	 ConnectionManager.close(conn, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(conn, pstmt, rs);
		}
		
		return bean;
	}
	
	/**
	 * 공통양식정보 가져오기 
	 * @param formseq : 양식일련번호
	 * @return FormatBean : 양식정보
	 * @throws Exception 
	 */
	public FormatBean getCommFormat(int formseq) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
		
        FormatBean bean = null;
		
		try {
			String query;
			
			query = "SELECT  A.FORMSEQ, A.FORMTITLE, A.FORMKIND, A.FORMCOMMENT, A.DEPT_ID, " +
					"        A.TBLCOLS, A.TBLROWS, A.CRTDT, A.CRTUSRID, A.UPTDT, A.UPTUSRID, B.CCDNAME " +
					"FROM COMMFORMMST A, CCD B " +
					"WHERE A.FORMKIND = B.CCDSUBCD " +
					"  AND B.CCDCD = '002' " +
					"  AND A.FORMSEQ = ? ";
			
			conn = ConnectionManager.getConnection();
        	
        	pstmt = conn.prepareStatement(query);
        	
        	pstmt.setInt(1, formseq);
        	
        	rs = pstmt.executeQuery();
        	
        	bean = new FormatBean();
        	
        	if(rs.next()) {
        		bean.setFormseq(rs.getInt("FORMSEQ"));					//양식일련번호
        		bean.setFormtitle(rs.getString("FORMTITLE"));			//양식제목
        		bean.setFormkind(rs.getString("FORMKIND"));				//양식종류코드
        		bean.setFormkindname(rs.getString("CCDNAME"));			//양식종류이름
        		bean.setFormcomment(rs.getString("FORMCOMMENT"));		//양식개요
        		bean.setDeptcd(rs.getString("DEPT_ID"));				//관련부서
        		bean.setTblcols(rs.getInt("TBLCOLS"));					//열개수
        		bean.setTblrows(rs.getInt("TBLROWS"));					//행개수
        		bean.setCrtdt(rs.getString("CRTDT"));					//생성일자
        		bean.setCrtusrid(rs.getString("CRTUSRID"));				//생성자ID
        		bean.setUptdt(rs.getString("UPTDT"));					//수정일자
        		bean.setUptusrid(rs.getString("UPTUSRID"));				//수정자ID
        		
        		ConnectionManager.close(pstmt, rs);
    			String[] columnName = {"FORMHTML", "FORMHEADERHTML", "FORMBODYHTML", "FORMTAILHTML"};
    			String[] columnData = new String[4];
    			for ( int i = 0; i < columnName.length; i++ ) {
    				query = "SELECT " + columnName[i] + " FROM COMMFORMMST WHERE FORMSEQ = ?";
    				pstmt = conn.prepareStatement(query);
    				pstmt.setInt(1, formseq);
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
			logger.error("ERROR",e);
	    	 ConnectionManager.close(conn, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(conn, pstmt, rs);
		}
		
		return bean;
	}
	
	/**
	 * 사용했던양식정보 가져오기
	 * @param sysdocno : 시스템문서번호 
	 * @param formseq : 양식일련번호
	 * @return FormatBean : 양식정보
	 * @throws Exception 
	 */
	public FormatBean getUsedFormat(int sysdocno, int formseq) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
		
        FormatBean bean = null;
		
		try {
			String query;
			
			query = "SELECT  A.SYSDOCNO, A.FORMSEQ, A.FORMTITLE, A.FORMKIND, A.FORMCOMMENT, " +
					"        A.TBLCOLS, A.TBLROWS, A.ORD, A.CRTDT, A.CRTUSRID, A.UPTDT, A.UPTUSRID, B.CCDNAME " +
					"FROM FORMMST A, CCD B, DOCMST C " +
					"WHERE A.FORMKIND = B.CCDSUBCD " +
					"  AND B.CCDCD = '002' " +
					"  AND A.SYSDOCNO = C.SYSDOCNO " +
					"  AND C.DOCSTATE > '01' " +
					"  AND A.SYSDOCNO = ? " +
					"  AND A.FORMSEQ = ? ";
			
			conn = ConnectionManager.getConnection();
        	
        	pstmt = conn.prepareStatement(query);
        	
        	pstmt.setInt(1, sysdocno);
        	pstmt.setInt(2, formseq);
        	
        	rs = pstmt.executeQuery();
        	
        	bean = new FormatBean();
        	
        	if(rs.next()) {
        		bean.setSysdocno(rs.getInt("SYSDOCNO"));				//시스템문서번호
        		bean.setFormseq(rs.getInt("FORMSEQ"));					//양식일련번호
        		bean.setFormtitle(rs.getString("FORMTITLE"));			//양식제목
        		bean.setFormkind(rs.getString("FORMKIND"));				//양식종류코드
        		bean.setFormkindname(rs.getString("CCDNAME"));			//양식종류이름
        		bean.setFormcomment(rs.getString("FORMCOMMENT"));		//양식개요
        		bean.setTblcols(rs.getInt("TBLCOLS"));					//열개수
        		bean.setTblrows(rs.getInt("TBLROWS"));					//행개수
        		bean.setOrd(rs.getInt("ORD"));							//정렬순서
        		bean.setCrtdt(rs.getString("CRTDT"));					//생성일자
        		bean.setCrtusrid(rs.getString("CRTUSRID"));				//생성자ID
        		bean.setUptdt(rs.getString("UPTDT"));					//수정일자
        		bean.setUptusrid(rs.getString("UPTUSRID"));				//수정자ID
        		
        		ConnectionManager.close(pstmt, rs);
    			String[] columnName = {"FORMHTML", "FORMHEADERHTML", "FORMBODYHTML", "FORMTAILHTML"};
    			String[] columnData = new String[4];
    			for ( int i = 0; i < columnName.length; i++ ) {
    				query = "SELECT " + columnName[i] + " FROM FORMMST WHERE SYSDOCNO = ? AND FORMSEQ = ?";
    				pstmt = conn.prepareStatement(query);
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
			logger.error("ERROR",e);
	    	 ConnectionManager.close(conn, pstmt, rs);
			throw e;
		} finally {
			ConnectionManager.close(conn, pstmt, rs);
		}
		
		return bean;
	}
	
	/**
	 * 새로운양식 추가
	 * @param Connection con
	 * @param FormatBean bean
	 * @throws Exception 
	 */
	public void addFormat(Connection conn, FormatBean bean) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			/* Base64디코딩 + GZIP압축해제  시작 */
//			Connection c = null;
//			PreparedStatement p1 = null;
//			PreparedStatement p2 = null;
//			PreparedStatement p3 = null;
//			ResultSet r1 = null;
//			ResultSet r2 = null;
//			ResultSet r3 = null;
//			String q1 = null;
//			String q2 = null;
//			String q3 = null;
//			try {
//				String[] tableName = {"FORMMST", "FORMMST_TEMP", "COMMFORMMST", "COMMFORMMST_TEMP"}; //_TEMP : 원본
//				String[] updateColumnName = {"FORMHTML", "FORMHEADERHTML", "FORMBODYHTML", "FORMTAILHTML"};
//				
//				c = ConnectionManager.getConnection(false);
//				for ( int j = 0; j < tableName.length; j += 2 ) {			
//					if ( j == 0 ) {
//						q1 = "INSERT INTO " + tableName[j] + "(SYSDOCNO, FORMSEQ, FORMTITLE, FORMKIND, FORMCOMMENT, TBLCOLS, TBLROWS, ORD, CRTDT, CRTUSRID, UPTDT, UPTUSRID)" +
//								"SELECT SYSDOCNO, FORMSEQ, FORMTITLE, FORMKIND, FORMCOMMENT, TBLCOLS, TBLROWS, ORD, CRTDT, CRTUSRID, UPTDT, UPTUSRID FROM " + tableName[j + 1];
//						p1 = c.prepareStatement(q1);
//						p1.executeQuery();
//						
//						q1 = "SELECT SYSDOCNO, FORMSEQ, FORMHTML, FORMHEADERHTML, FORMBODYHTML, FORMTAILHTML FROM " + tableName[j + 1];
//					} else {
//						q1 = "INSERT INTO " + tableName[j] + "(FORMSEQ, FORMTITLE, FORMKIND, FORMCOMMENT, DEPT_ID, TBLCOLS, TBLROWS, CRTDT, CRTUSRID, UPTDT, UPTUSRID)" +
//						 		"SELECT FORMSEQ, FORMTITLE, FORMKIND, FORMCOMMENT, DEPT_ID, TBLCOLS, TBLROWS, CRTDT, CRTUSRID, UPTDT, UPTUSRID FROM " + tableName[j + 1];
//						p1 = c.prepareStatement(q1);
//						p1.executeQuery();
//					
//						q1 = "SELECT FORMSEQ, FORMHTML, FORMHEADERHTML, FORMBODYHTML, FORMTAILHTML FROM " + tableName[j + 1];
//					}
//					p1 = c.prepareStatement(q1);
//					r1 = p1.executeQuery();
//					while ( r1.next() ) {
//						int sysdocno = 0;
//						int formseq = 0;
//						if ( j == 0 ) {
//							sysdocno = r1.getInt("SYSDOCNO");
//							formseq = r1.getInt("FORMSEQ");
//						} else {
//							formseq = r1.getInt("FORMSEQ");
//						}
//						String[] html = {Utils.nullToEmptyString(nexti.ejms.util.Base64.decodeString(r1.getString(updateColumnName[0]))),
//										Utils.nullToEmptyString(nexti.ejms.util.Base64.decodeString(r1.getString(updateColumnName[1]))),
//										Utils.nullToEmptyString(nexti.ejms.util.Base64.decodeString(r1.getString(updateColumnName[2]))),
//										Utils.nullToEmptyString(nexti.ejms.util.Base64.decodeString(r1.getString(updateColumnName[3])))};
//						
//						for ( int i = 0; i < updateColumnName.length; i++ ) {
//							if ( j == 0 ) {
//								q2 = "UPDATE " + tableName[j] + " SET " + updateColumnName[i] + " = EMPTY_CLOB() WHERE SYSDOCNO = ? AND FORMSEQ = ?";
//								p2 = c.prepareStatement(q2);
//								p2.setInt(1, sysdocno);
//								p2.setInt(2, formseq);
//							} else {
//								q2 = "UPDATE " + tableName[j] + " SET " + updateColumnName[i] + " = EMPTY_CLOB() WHERE FORMSEQ = ?";
//								p2 = c.prepareStatement(q2);
//								p2.setInt(1, formseq);
//							}
//							r2 = p2.executeQuery();
//							
//							if ( j == 0 ) {
//								q3 = "SELECT " + updateColumnName[i] + " FROM " + tableName[j] + " WHERE SYSDOCNO = ? AND FORMSEQ = ? FOR UPDATE";
//								p3 = c.prepareStatement(q3);
//								p3.setInt(1, sysdocno);
//								p3.setInt(2, formseq);
//							} else {
//								q3 = "SELECT " + updateColumnName[i] + " FROM " + tableName[j] + " WHERE FORMSEQ = ? FOR UPDATE";
//								p3 = c.prepareStatement(q3);
//								p3.setInt(1, formseq);
//							}
//							r3 = p3.executeQuery();
//							if ( r3.next() ) {
//								Utils.writeClobData(r3, updateColumnName[i], html[i]);
//							}
//							
//							ConnectionManager.close(p2, r2);
//							ConnectionManager.close(p3, r3);
//						}
//					}
//					ConnectionManager.close(p1, r1);
//				}
//				c.commit();
//				System.out.println("양식변환 완료");
//			} catch (Exception e) {
//				try {c.rollback();} catch (Exception ex) {}
//				e.printStackTrace();
//			} finally {
//				ConnectionManager.close(c);
//				ConnectionManager.close(p1, r1);
//				ConnectionManager.close(p2, r2);
//				ConnectionManager.close(p3, r3);
//			}
			/* Base64디코딩 + GZIP압축해제  끝 */
			
			String query =
					"INSERT INTO " +
					"FORMMST(SYSDOCNO, FORMSEQ, FORMTITLE, FORMKIND, FORMCOMMENT, " +
					"        TBLCOLS, TBLROWS, ORD, CRTUSRID, UPTUSRID, " +
					"        CRTDT, UPTDT, " +
					"        FORMHTML, FORMHEADERHTML, FORMBODYHTML, FORMTAILHTML) " +
					"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
					"       TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), " +
					"       TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), " +
					"       EMPTY_CLOB(), EMPTY_CLOB(), EMPTY_CLOB(), EMPTY_CLOB()) ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, bean.getSysdocno());
			pstmt.setInt(2, bean.getFormseq());
			pstmt.setString(3, bean.getFormtitle());
			pstmt.setString(4, bean.getFormkind());
			pstmt.setString(5, bean.getFormcomment());
			pstmt.setInt(6, bean.getTblcols());
			pstmt.setInt(7, bean.getTblrows());
			pstmt.setInt(8, getNewFormatOrd(bean.getSysdocno()));
			pstmt.setString(9, bean.getCrtusrid());
			pstmt.setString(10, bean.getUptusrid());
			
			pstmt.executeUpdate();
			
			ConnectionManager.close(pstmt, rs);
			String[] columnName = {"FORMHTML", "FORMHEADERHTML", "FORMBODYHTML", "FORMTAILHTML"};
			String[] columnData = {bean.getFormhtml(), bean.getFormheaderhtml(), bean.getFormbodyhtml(), bean.getFormtailhtml()};
			for ( int i = 0; i < columnName.length; i++ ) {
				query = "SELECT " + columnName[i] + " FROM FORMMST WHERE SYSDOCNO = ? AND FORMSEQ = ? FOR UPDATE";
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, bean.getSysdocno());
				pstmt.setInt(2, bean.getFormseq());
				rs = pstmt.executeQuery();
				if ( rs.next() ) {
					Utils.writeClobData(rs, columnName[i], columnData[i]);
				}
				ConnectionManager.close(pstmt, rs);
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
	    	 ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
	}
	
	/**
	 * 양식 수정
	 * @param Connection con
	 * @param FormatBean bean
	 * @throws Exception 
	 */
	public void modifyFormat(Connection conn, FormatBean bean) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String query =
					"UPDATE FORMMST " +
					"SET FORMTITLE=?, FORMCOMMENT=?, TBLCOLS=?, TBLROWS=?, " +
					"    UPTUSRID=?, UPTDT=TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), " +
					"    FORMHTML=EMPTY_CLOB(), FORMHEADERHTML=EMPTY_CLOB(), " +
					"    FORMBODYHTML=EMPTY_CLOB(), FORMTAILHTML=EMPTY_CLOB() " +
					"WHERE SYSDOCNO = ? " +
					"  AND FORMSEQ = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, bean.getFormtitle());
			pstmt.setString(2, bean.getFormcomment());
			pstmt.setInt(3, bean.getTblcols());
			pstmt.setInt(4, bean.getTblrows());
			pstmt.setString(5, bean.getUptusrid());
			pstmt.setInt(6, bean.getSysdocno());
			pstmt.setInt(7, bean.getFormseq());
			
			pstmt.executeUpdate();
			
			ConnectionManager.close(pstmt, rs);
			String[] columnName = {"FORMHTML", "FORMHEADERHTML", "FORMBODYHTML", "FORMTAILHTML"};
			String[] columnData = {bean.getFormhtml(), bean.getFormheaderhtml(), bean.getFormbodyhtml(), bean.getFormtailhtml()};
			for ( int i = 0; i < columnName.length; i++ ) {
				query = "SELECT " + columnName[i] + " FROM FORMMST WHERE SYSDOCNO = ? AND FORMSEQ = ? FOR UPDATE";
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, bean.getSysdocno());
				pstmt.setInt(2, bean.getFormseq());
				rs = pstmt.executeQuery();
				if ( rs.next() ) {
					Utils.writeClobData(rs, columnName[i], columnData[i]);
				}
				ConnectionManager.close(pstmt, rs);
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
	    	 ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
	}
	
	/**
	 * 공통양식 수정
	 * @param Connection con
	 * @param FormatBean bean
	 * @throws Exception 
	 */
	public void modifyCommFormat(Connection conn, FormatBean bean) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String query =
					"UPDATE COMMFORMMST " +
					"SET FORMTITLE=?, FORMCOMMENT=?, TBLCOLS=?, TBLROWS=?, " +
					"    UPTUSRID=?, UPTDT=TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), " +
					"    FORMHTML=EMPTY_CLOB(), FORMHEADERHTML=EMPTY_CLOB(), " +
					"    FORMBODYHTML=EMPTY_CLOB(), FORMTAILHTML=EMPTY_CLOB() " +
					"WHERE FORMSEQ = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, bean.getFormtitle());
			pstmt.setString(2, bean.getFormcomment());
			pstmt.setInt(3, bean.getTblcols());
			pstmt.setInt(4, bean.getTblrows());
			pstmt.setString(5, bean.getUptusrid());
			pstmt.setInt(6, bean.getFormseq());
			
			pstmt.executeUpdate();
			
			ConnectionManager.close(pstmt, rs);
			String[] columnName = {"FORMHTML", "FORMHEADERHTML", "FORMBODYHTML", "FORMTAILHTML"};
			String[] columnData = {bean.getFormhtml(), bean.getFormheaderhtml(), bean.getFormbodyhtml(), bean.getFormtailhtml()};
			for ( int i = 0; i < columnName.length; i++ ) {
				query = "SELECT " + columnName[i] + " FROM COMMFORMMST WHERE FORMSEQ = ? FOR UPDATE";
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, bean.getFormseq());
				rs = pstmt.executeQuery();
				if ( rs.next() ) {
					Utils.writeClobData(rs, columnName[i], columnData[i]);
				}
				ConnectionManager.close(pstmt, rs);
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
	}
	
	/**
	 * 새로운공통양식 추가
	 * @param Connection con
	 * @param FormatBean bean
	 * @throws Exception 
	 */
	public void addCommFormat(Connection conn, FormatBean bean) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {			
			String query =
					"INSERT INTO " +
					"COMMFORMMST(FORMSEQ, FORMTITLE, FORMKIND, FORMCOMMENT, DEPT_ID, " +
					"            TBLCOLS, TBLROWS, CRTUSRID, UPTUSRID, " +
					"            CRTDT, UPTDT, " +
					"            FORMHTML, FORMHEADERHTML, FORMBODYHTML, FORMTAILHTML) " +
					"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, " +
					"       TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), " +
					"       TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), " +
					"       EMPTY_CLOB(), EMPTY_CLOB(), EMPTY_CLOB(), EMPTY_CLOB()) ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, bean.getFormseq());
			pstmt.setString(2, bean.getFormtitle());
			pstmt.setString(3, bean.getFormkind());
			pstmt.setString(4, bean.getFormcomment());
			pstmt.setString(5, bean.getDeptcd());
			pstmt.setInt(6, bean.getTblcols());
			pstmt.setInt(7, bean.getTblrows());
			pstmt.setString(8, bean.getCrtusrid());
			pstmt.setString(9, bean.getUptusrid());
			
			pstmt.executeUpdate();
			
			ConnectionManager.close(pstmt, rs);
			String[] columnName = {"FORMHTML", "FORMHEADERHTML", "FORMBODYHTML", "FORMTAILHTML"};
			String[] columnData = {bean.getFormhtml(), bean.getFormheaderhtml(), bean.getFormbodyhtml(), bean.getFormtailhtml()};
			for ( int i = 0; i < columnName.length; i++ ) {
				query = "SELECT " + columnName[i] + " FROM COMMFORMMST WHERE FORMSEQ = ? FOR UPDATE";
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, bean.getFormseq());
				rs = pstmt.executeQuery();
				if ( rs.next() ) {
					Utils.writeClobData(rs, columnName[i], columnData[i]);
				}
				ConnectionManager.close(pstmt, rs);
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
	}
	
	/**
	 * 새로운 양식일련번호 가져오기
	 * @param int sysdocno
	 * @return int 양식일련번호
	 * @throws Exception 
	 */
	public int getNewFormatseq(int sysdocno) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int formseq = 0;
		
		try {
			String query = "SELECT NVL(MAX(FORMSEQ), 0) + 1 " +
						   "FROM FORMMST " +
						   "WHERE SYSDOCNO = ? ";
			
			conn = ConnectionManager.getConnection();
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, sysdocno);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				formseq = rs.getInt(1);
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
	    	ConnectionManager.close(conn, pstmt, rs);
			throw e;
	    } finally {	       
	    	ConnectionManager.close(conn, pstmt, rs);
	    }
	    
	    return formseq;
	}
	
	/**
	 * 새로운 공통양식일련번호 가져오기
	 * @return int 공통양식일련번호
	 * @throws Exception 
	 */
	public int getNewCommFormatseq() throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int formseq = 0;
		
		try {
			String query = "SELECT NVL(MAX(FORMSEQ), 0) + 1 " +
						   "FROM COMMFORMMST ";
			
			conn = ConnectionManager.getConnection();
			
			pstmt = conn.prepareStatement(query);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				formseq = rs.getInt(1);
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
	    	ConnectionManager.close(conn, pstmt, rs);
			throw e;
	    } finally {	       
	    	ConnectionManager.close(conn, pstmt, rs);
	    }
	    
	    return formseq;
	}
	
	/**
	 * 새로운 양식정렬순서 가져오기
	 * @param int sysdocno
	 * @return int 양식정렬순서
	 * @throws Exception 
	 */
	public int getNewFormatOrd(int sysdocno) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int ord = 0;
		
		try {
			String query = "SELECT NVL(MAX(ORD), 0) + 1 " +
						   "FROM FORMMST " +
						   "WHERE SYSDOCNO = ? ";
			
			conn = ConnectionManager.getConnection();
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, sysdocno);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				ord = rs.getInt(1);
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
	    	ConnectionManager.close(conn, pstmt, rs);
			throw e;
	    } finally {	       
	    	ConnectionManager.close(conn, pstmt, rs);
	    }
	    
	    return ord;
	}
	
	/**
	 * 등록된 파일개수 가져오기
	 * @param int sysdocno
	 * @param int formseq
	 * @return int 파일개수
	 * @throws Exception 
	 */
	public int getFilecount(int sysdocno, int formseq) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int filecount = 0;
		
		try {
			String query = "SELECT NVL(COUNT(*), 0) " +
						   "FROM FILEBOOKFRM " +
						   "WHERE SYSDOCNO = ? " +
						   "  AND FORMSEQ = ? ";
			
			conn = ConnectionManager.getConnection();
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				filecount = rs.getInt(1);
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
	    	ConnectionManager.close(conn, pstmt, rs);
			throw e;
	    } finally {	       
	    	ConnectionManager.close(conn, pstmt, rs);
	    }
	    
	    return filecount;
	}
	
	/**
	 * 등록된 공통양식 파일개수 가져오기
	 * @param int formseq
	 * @return int 파일개수
	 * @throws Exception 
	 */
	public int getCommFilecount(int formseq) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int filecount = 0;
		
		try {
			String query = "SELECT NVL(COUNT(*), 0) " +
						   "FROM COMMFILEBOOKFRM " +
						   "WHERE FORMSEQ = ? ";
			
			conn = ConnectionManager.getConnection();
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, formseq);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				filecount = rs.getInt(1);
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
	    	ConnectionManager.close(conn, pstmt, rs);
			throw e;
	    } finally {	       
	    	ConnectionManager.close(conn, pstmt, rs);
	    }
	    
	    return filecount;
	}
	
	/**
	 * 등록된 사용했던양식 파일개수 가져오기
	 * @param int sysdocno
	 * @param int formseq
	 * @return int 파일개수
	 * @throws Exception 
	 */
	public int getUsedFilecount(int sysdocno, int formseq) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int filecount = 0;
		
		try {
			String query = "SELECT NVL(COUNT(*), 0) " +
						   "FROM FILEBOOKFRM A, DOCMST B " +
						   "WHERE A.SYSDOCNO = B.SYSDOCNO " +
						   "  AND B.DOCSTATE > '01' " +
						   "  AND A.SYSDOCNO = ? " +
						   "  AND A.FORMSEQ = ? ";
			
			conn = ConnectionManager.getConnection();
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				filecount = rs.getInt(1);
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
	    	ConnectionManager.close(conn, pstmt, rs);
			throw e;
	    } finally {	       
	    	ConnectionManager.close(conn, pstmt, rs);
	    }
	    
	    return filecount;
	}

	/**
	 * 사용했던양식목록 가져오기
	 * @param String deptcd
	 * @param String makedt
	 * @param String title
	 * @param String makeusr
	 * @param int start
	 * @param int end
	 * @return List 사용했던양식목록(ArrayList)
	 * @throws Exception 
	 */
	public List getListUsedFormat(String deptcd, String makedt, String title, String makeusr, int start, int end) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        List list = null;
		
		try {
			String query;
			
			query = "SELECT (CNT-SEQ+1) BUNHO, SYSDOCNO, FORMSEQ, FORMTITLE, FORMKIND, CCDNAME " +
					"FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* " + 
					"      FROM (SELECT ROWNUM SEQ, A2.* " +
					"            FROM (SELECT B.SYSDOCNO, B.FORMSEQ, B.FORMTITLE, B.FORMKIND, C.CCDNAME, " +
					"                  FROM DOCMST A, FORMMST B, CCD C " +
					"                  WHERE A.SYSDOCNO = B.SYSDOCNO " +
					"                    AND B.FORMKIND = C.CCDSUBCD " +
					"                    AND C.CCDCD = '001' " +
					"                    AND A.COLDEPTCD = ? " +
					"                    AND A.ENDDT LIKE ? " +
					"                    AND B.FORMTITLE LIKE ? " +
					"                    AND A.CHRGUSRNM LIKE ? " +
					"                  ORDER BY A.ENDDT DESC) A2) A1) " +
					"WHERE SEQ BETWEEN ? AND ? ";
			
			conn = ConnectionManager.getConnection();
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, deptcd);
			pstmt.setString(2, makedt+"%");
			pstmt.setString(3, "%"+title+"%");
			pstmt.setString(4, makeusr+"%");
			pstmt.setInt(5, start);
			pstmt.setInt(6, end);
			
			rs = pstmt.executeQuery();
			
			list = new ArrayList();
			
			while(rs.next()) {
				FormatBean bean = new FormatBean();
				
				bean.setSeqno(rs.getInt("BUNHO"));
				bean.setSysdocno(rs.getInt("SYSDOCNO"));
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setFormtitle(rs.getString("FORMTITLE"));
				bean.setFormkind(rs.getString("FORMKIND"));
				bean.setFormkindname(rs.getString("CCDNAME"));
				
				list.add(bean);
			}
		 } catch(Exception e) {
			 logger.error("ERROR",e);
		    ConnectionManager.close(conn, pstmt, rs);
			 throw e;
	     } finally {	       
	    	 ConnectionManager.close(conn, pstmt, rs);
	     }
	     
		return list;		
	}
	
	/**
	 * 취합양식 삭제하기
	 * @param Connection conn
	 * @param int sysdocno 
	 * @param int formseq
	 * @throws Exception 
	 */
	public void delFormat(Connection conn, int sysdocno, int formseq) throws Exception {
		
		PreparedStatement pstmt = null;

		try {
			String[] table = {"FORMMST",	//양식마스터
							  "ATTLINEFRM", "ATTFIXEDFRM", "ATTBOOKFRM",
							  "DATALINEFRM", "DATAFIXEDFRM", "DATABOOKFRM"};	//양식속성
			
			String query;
			
			for(int i = 0; i < table.length; i++) {
				query = "DELETE " +
						"FROM " + table[i] + " " +
						"WHERE SYSDOCNO = ? ";
				
				if(formseq != -1) {
					query += "AND FORMSEQ = " + formseq;
				}
				
				pstmt = conn.prepareStatement(query);
				
				pstmt.setInt(1, sysdocno);
				pstmt.executeUpdate();
				try {pstmt.close();} catch(Exception e) {}
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
	    	ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
		
		return;		
	}
	
	/**
	 * 공통양식 삭제하기
	 * @param Connection conn
	 * @param int formseq
	 * @throws Exception 
	 */
	public void delCommFormat(Connection conn, int formseq) throws Exception {
		
		PreparedStatement pstmt = null;

		try {			
			String[] table = {"COMMFORMMST",	//양식마스터
							  "COMMATTLINEFRM", "COMMATTFIXEDFRM", "COMMATTBOOKFRM"};	//양식속성
			
			String query;
			
			for(int i = 0; i < table.length; i++) {
				query = "DELETE " +
						"FROM " + table[i] + " " +
						"WHERE FORMSEQ = ? ";
				
				pstmt = conn.prepareStatement(query);
				
				pstmt.setInt(1, formseq);
				pstmt.executeUpdate();
				try {pstmt.close();} catch(Exception e) {}
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
		
		return;		
	}
	
	/**
	 * 배부하기 상세 - 취합양식 데이터 보기
	 * 양식 리스트 가져오기
	 * @throws Exception 
	 */
	public List viewFormList(int sysdocno) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List listform = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT ROWNUM, A.* " +
							   "FROM (SELECT A.SYSDOCNO, B.FORMSEQ, B.FORMTITLE, B.FORMKIND, " +
							   "			(SELECT CCDNAME FROM CCD WHERE CCDCD = '002' AND CCDSUBCD = B.FORMKIND ) FORMKINDNAME " +
							   "	  FROM DOCMST A, FORMMST B " +
							   "	  WHERE A.SYSDOCNO = B.SYSDOCNO " +
							   "	  AND A.SYSDOCNO = ? " +
							   "	  ORDER BY B.ORD, B.FORMSEQ) A ");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
									
			rs = pstmt.executeQuery();
			
			listform = new ArrayList();
			
			while(rs.next()) {
				FormatBean frmBean = new FormatBean();
				
				frmBean.setSeqno(rs.getInt("ROWNUM"));
				frmBean.setSysdocno(rs.getInt("SYSDOCNO"));
				frmBean.setFormseq(rs.getInt("FORMSEQ"));
				frmBean.setFormtitle(rs.getString("FORMTITLE"));
				frmBean.setFormkind(rs.getString("FORMKIND"));
				frmBean.setFormkindname(rs.getString("FORMKINDNAME"));
				
				listform.add(frmBean);
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return listform;
	}
}
