/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 제본자료형 dao
 * 설명:
 */
package nexti.ejms.formatBook.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.dept.model.DeptManager;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.format.model.InputDeptSearchBoxBean;
import nexti.ejms.util.FileBean;

public class FormatBookDAO {
	
	private static Logger logger = Logger.getLogger(FormatBookDAO.class);
	
	/**
	 * 양식파일 체크(
	 * @param int sysdocno
	 * @param int formseq(생략시 0)
	 * @return List 없는파일목록(없을시 null)
	 * @throws Exception
	 */
	public List getExistBookFile(int sysdocno, int formseq) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List result = null;
		
		try {
			String searchFormseq = "";
			if(formseq > 0) {
				searchFormseq = "AND A.FORMSEQ = " + formseq;
			}

			String query =
				"SELECT A.SYSDOCNO, A.FORMSEQ, A.FILESEQ, A.FILENM, A.ORIGINFILENM, B.FORMTITLE " +
				"FROM FILEBOOKFRM A, FORMMST B " +
				"WHERE A.SYSDOCNO = B.SYSDOCNO " +
				"  AND A.FORMSEQ = B.FORMSEQ " +
				"  AND A.SYSDOCNO = ? " + searchFormseq + " " +
				"ORDER BY A.FORMSEQ ASC, A.ORD ASC, A.FILESEQ ASC ";

			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, sysdocno);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if(result == null) {
					result = new ArrayList();
				}
				
				DataBookBean bean = new DataBookBean();
				
				bean.setSysdocno(rs.getInt("SYSDOCNO"));
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setFileseq(rs.getInt("FILESEQ"));
				bean.setFilenm(rs.getString("FILENM"));
				bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				bean.setFormtitle(rs.getString("FORMTITLE"));
				
				result.add(bean);
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
	 * 공통양식파일 체크(
	 * @param int formseq
	 * @return List 없는파일목록(없을시 null)
	 * @throws Exception
	 */
	public List getExistCommBookFile(int formseq) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List result = null;
		
		try {
			String query =
				"SELECT A.FORMSEQ, A.FILESEQ, A.FILENM, A.ORIGINFILENM, B.FORMTITLE " +
				"FROM COMMFILEBOOKFRM A, COMMFORMMST B " +
				"WHERE A.FORMSEQ = B.FORMSEQ " +
				"  AND A.FORMSEQ = ? " +
				"ORDER BY A.FILESEQ ASC ";

			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, formseq);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if(result == null) {
					result = new ArrayList();
				}
				
				DataBookBean bean = new DataBookBean();
				
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setFileseq(rs.getInt("FILESEQ"));
				bean.setFilenm(rs.getString("FILENM"));
				bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				bean.setFormtitle(rs.getString("FORMTITLE"));
				
				result.add(bean);
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
	 * 사용했던 양식파일 체크(
	 * @param int sysdocno
	 * @param int formseq
	 * @return List 없는파일목록(없을시 null)
	 * @throws Exception
	 */
	public List getExistUsedBookFile(int sysdocno, int formseq) throws Exception {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List result = null;
		
		try {
			String query =
				"SELECT A.SYSDOCNO, A.FORMSEQ, A.FILESEQ, A.FILENM, A.ORIGINFILENM, B.FORMTITLE " +
				"FROM FILEBOOKFRM A, FORMMST B, DOCMST C " +
				"WHERE A.SYSDOCNO = B.SYSDOCNO " +
				"  AND B.SYSDOCNO = C.SYSDOCNO " +
				"  AND A.FORMSEQ = B.FORMSEQ " +
				"  AND C.DOCSTATE > '01' " +
				"  AND A.SYSDOCNO = ? " +
				"  AND A.FORMSEQ = ? " +
				"ORDER BY A.FORMSEQ ASC, A.ORD ASC, A.FILESEQ ASC ";
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if(result == null) {
					result = new ArrayList();
				}
				
				DataBookBean bean = new DataBookBean();
				
				bean.setSysdocno(rs.getInt("SYSDOCNO"));
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setFileseq(rs.getInt("FILESEQ"));
				bean.setFilenm(rs.getString("FILENM"));
				bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				bean.setFormtitle(rs.getString("FORMTITLE"));
				
				result.add(bean);
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
	 * 카테고리 목록 가져오기
	 * @param int sysdocno
	 * @param int formseq
	 * @return String[] 카테고리목록
	 * @throws Exception 
	 */
	public String[] getListCategory(int sysdocno, int formseq) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String[] result = null;
		
		try {
			String query =
					"SELECT CATEGORYNM " +
					"FROM ATTBOOKFRM " +
					"WHERE SYSDOCNO = ? " +
					"  AND FORMSEQ = ? " +
					"ORDER BY ORD ";
			
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
			
			rs = pstmt.executeQuery();
			
			ArrayList arr = new ArrayList();
			
			while(rs.next()) {
				arr.add(rs.getString(1));
			}
			
			if(arr.size() != 0) {
				result = new String[arr.size()];
				result = (String[])arr.toArray(result);
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
	 * 공통양식 카테고리 목록 가져오기
	 * @param int formseq
	 * @return String[] 카테고리목록
	 * @throws Exception 
	 */
	public String[] getListCommCategory(int formseq) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String[] result = null;
		
		try {
			String query =
					"SELECT CATEGORYNM " +
					"FROM COMMATTBOOKFRM " +
					"WHERE FORMSEQ = ? " +
					"ORDER BY ORD ";
			
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, formseq);
			
			rs = pstmt.executeQuery();
			
			ArrayList arr = new ArrayList();
			
			while(rs.next()) {
				arr.add(rs.getString(1));
			}
			
			if(arr.size() != 0) {
				result = new String[arr.size()];
				result = (String[])arr.toArray(result);
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
	 * 사용했던양식 카테고리 목록 가져오기
	 * @param int sysdocno
	 * @param int formseq
	 * @return String[] 카테고리목록
	 * @throws Exception 
	 */
	public String[] getListUsedCategory(int sysdocno, int formseq) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String[] result = null;
		
		try {
			String query =
					"SELECT A.CATEGORYNM " +
					"FROM ATTBOOKFRM A, DOCMST B " +
					"WHERE A.SYSDOCNO = B.SYSDOCNO " +
					"  AND B.DOCSTATE > '01' " +
					"  AND A.SYSDOCNO = ? " +
					"  AND A.FORMSEQ = ? " +
					"ORDER BY A.ORD ";
			
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
			
			rs = pstmt.executeQuery();
			
			ArrayList arr = new ArrayList();
			
			while(rs.next()) {
				arr.add(rs.getString(1));
			}
			
			if(arr.size() != 0) {
				result = new String[arr.size()];
				result = (String[])arr.toArray(result);
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
	 * 제본자료형속성 카테고리 추가
	 * @param Connection conn
	 * @param int sysdocno
	 * @param int formseq
	 * @param String category
	 * @throws Exception 
	 */
	public int addFormatBook(Connection conn, int sysdocno, int formseq, String category) throws Exception {
		
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {			
			String query =
					"INSERT INTO ATTBOOKFRM(SYSDOCNO, FORMSEQ, SEQ, CATEGORYNM, ORD) " +
					"VALUES(?, ?, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
			pstmt.setInt(3, getNewCategorySeq(conn, sysdocno, formseq));
			pstmt.setString(4, category);
			pstmt.setInt(5, getNewCategoryOrd(conn, sysdocno, formseq));
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
	 * 공통제본자료형속성 카테고리 추가
	 * @param Connection conn
	 * @param int formseq
	 * @param String category
	 * @throws Exception 
	 */
	public int addCommFormatBook(Connection conn, int formseq, String category) throws Exception {
		
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {			
			String query =
					"INSERT INTO COMMATTBOOKFRM(FORMSEQ, SEQ, CATEGORYNM, ORD) " +
					"VALUES(?, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, formseq);
			pstmt.setInt(2, getNewCommCategorySeq(conn, formseq));
			pstmt.setString(3, category);
			pstmt.setInt(4, getNewCommCategoryOrd(conn, formseq));
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
	 * 제본자료형속성 카테고리 삭제
	 * @param Connection conn
	 * @param int sysdocno
	 * @param int formseq
	 * @param String category
	 * @throws Exception 
	 */
	public int delFormatBook(Connection conn, int sysdocno, int formseq) throws Exception {
		
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {			
			String query =
					"DELETE " +
					"FROM ATTBOOKFRM " +
					"WHERE SYSDOCNO = ? " +
					"  AND FORMSEQ = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);

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
	 * 공통제본자료형속성 카테고리 삭제
	 * @param Connection conn
	 * @param int formseq
	 * @param String category
	 * @throws Exception 
	 */
	public int delCommFormatBook(Connection conn, int formseq) throws Exception {
		
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {			
			String query =
					"DELETE " +
					"FROM COMMATTBOOKFRM " +
					"WHERE FORMSEQ = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, formseq);

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
	 * 파일명 가져오기
	 * @param Connection conn
	 * @param FileBookBean fbbean
	 * @return String 파일명
	 * @throws Exception 
	 */
	public String getFilename(Connection conn, FileBookBean fbbean) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String filenm = "";
		
		try {
			String query = "SELECT FILENM " +
						   "FROM FILEBOOKFRM " +
						   "WHERE SYSDOCNO = ? " +
						   "  AND FORMSEQ = ? " +
						   "  AND FILESEQ = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, fbbean.getSysdocno());
			pstmt.setInt(2, fbbean.getFormseq());
			pstmt.setInt(3, fbbean.getFileseq());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				filenm = rs.getString(1);
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt,rs);
			throw e;
	    } finally {	       
	    	try {rs.close();} catch(Exception e) {}
	    	try {pstmt.close();} catch(Exception e) {}
	    }
	    
	    return filenm;
	}
	
	/**
	 * 공통파일명 가져오기
	 * @param Connection conn
	 * @param FileBookBean fbbean
	 * @return String 파일명
	 * @throws Exception 
	 */
	public String getCommFilename(Connection conn, FileBookBean fbbean) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String filenm = "";
		
		try {
			String query = "SELECT FILENM " +
						   "FROM COMMFILEBOOKFRM " +
						   "WHERE FORMSEQ = ? " +
						   "  AND FILESEQ = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, fbbean.getFormseq());
			pstmt.setInt(2, fbbean.getFileseq());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				filenm = rs.getString(1);
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt,rs);
			throw e;
	    } finally {	       
	    	try {rs.close();} catch(Exception e) {}
	    	try {pstmt.close();} catch(Exception e) {}
	    }
	    
	    return filenm;
	}
	
	/**
	 * 취합문서 전체양식 파일명리스트 가져오기
	 * @param Connection conn
	 * @param int sysdocno
	 * @return List 파일명(ArrayList)
	 * @throws Exception 
	 */
	public List getListAllFilename(Connection conn, int sysdocno) throws Exception {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List fileList = null;
		
		try {
			String query = "SELECT SYSDOCNO, FORMSEQ, FILESEQ, FILENM " +
						   "FROM FILEBOOKFRM " +
						   "WHERE SYSDOCNO = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, sysdocno);
			
			rs = pstmt.executeQuery();
			
			fileList = new ArrayList();
			
			while(rs.next()) {
				FileBookBean bean = new FileBookBean();
				
				bean.setSysdocno(rs.getInt("SYSDOCNO"));
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setFileseq(rs.getInt("FILESEQ"));
				bean.setFilenm(rs.getString("FILENM"));
				
				fileList.add(bean);
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt,rs);
			throw e;
	    } finally {	       
	    	try {rs.close();} catch(Exception e) {}
	    	try {pstmt.close();} catch(Exception e) {}
	    }
	    
	    return fileList;
	}
	
	/**
	 * 사용했던양식 파일명리스트 가져오기
	 * @param Connection conn
	 * @param int usedsysdocno
	 * @param int usedformseq
	 * @return List 파일명(ArrayList)
	 * @throws Exception 
	 */
	public List getListUsedFilename(Connection conn, int usedsysdocno, int usedformseq) throws Exception {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List fileList = null;
		
		try {
			String query = "SELECT SYSDOCNO, FORMSEQ, FILESEQ, FILENM " +
						   "FROM FILEBOOKFRM " +
						   "WHERE SYSDOCNO = ? " +
						   "  AND FORMSEQ = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, usedsysdocno);
			pstmt.setInt(2, usedformseq);
			
			rs = pstmt.executeQuery();
			
			fileList = new ArrayList();
			
			while(rs.next()) {
				FileBookBean bean = new FileBookBean();
				
				bean.setSysdocno(rs.getInt("SYSDOCNO"));
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setFileseq(rs.getInt("FILESEQ"));
				bean.setFilenm(rs.getString("FILENM"));
				
				fileList.add(bean);
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt,rs);
			throw e;
	    } finally {	  
	    	try {rs.close();} catch(Exception e) {}
	    	try {pstmt.close();} catch(Exception e) {}
	    }
	    
	    return fileList;
	}
	
	/**
	 * 공통양식 파일명리스트 가져오기
	 * @param Connection conn
	 * @param int commformseq
	 * @return List 파일명(ArrayList)
	 * @throws Exception 
	 */
	public List getListCommFilename(Connection conn, int commformseq) throws Exception {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List fileList = null;
		
		try {
			String query = "SELECT FORMSEQ, FILESEQ, FILENM " +
						   "FROM COMMFILEBOOKFRM " +
						   "WHERE FORMSEQ = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, commformseq);
			
			rs = pstmt.executeQuery();
			
			fileList = new ArrayList();
			
			while(rs.next()) {
				FileBookBean bean = new FileBookBean();
				
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setFileseq(rs.getInt("FILESEQ"));
				bean.setFilenm(rs.getString("FILENM"));
				
				fileList.add(bean);
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt,rs);
			throw e;
	    } finally {	       
	    	try {rs.close();} catch(Exception e) {}
	    	try {pstmt.close();} catch(Exception e) {}
	    }
	    
	    return fileList;
	}
	
	/**
	 * 양식파일명리스트 가져오기
	 * @param Connection conn
	 * @param int sysdocno
	 * @param int formseq
	 * @return List 파일명(ArrayList)
	 * @throws Exception 
	 */
	public List getListFilename(Connection conn, int sysdocno, int formseq) throws Exception {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List filenm = null;
		
		try {
			String query = "SELECT FILENM " +
						   "FROM FILEBOOKFRM " +
						   "WHERE SYSDOCNO = ? " +
						   "  AND FORMSEQ = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
			
			rs = pstmt.executeQuery();
			
			filenm = new ArrayList();
			
			while(rs.next()) {
				filenm.add(rs.getString(1));
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt,rs);
			throw e;
	    } finally {
    		try {rs.close();} catch(Exception e) {}
    		try {pstmt.close();} catch(Exception e) {}
	    }
	    
	    return filenm;
	}
	
	/**
	 * 최종자료파일명리스트 가져오기
	 * @param Connection conn
	 * @param int sysdocno
	 * @param int formseq
	 * @return List 파일명(ArrayList)
	 * @throws Exception 
	 */
	public List getListCompFilename(Connection conn, int sysdocno, int formseq) throws Exception {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List filenm = null;
		
		try {
			String query = "SELECT FILENM " +
						   "FROM DATABOOKFRMCMPT " +
						   "WHERE SYSDOCNO = ? " +
						   "  AND FORMSEQ = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
			
			rs = pstmt.executeQuery();
			
			filenm = new ArrayList();
			
			while(rs.next()) {
				filenm.add(rs.getString(1));
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt,rs);
			throw e;
	    } finally {
    		try {rs.close();} catch(Exception e) {}
    		try {pstmt.close();} catch(Exception e) {}
	    }
	    
	    return filenm;
	}
	
	/**
	 * 새로운 카테고리일련번호 가져오기
	 * @param Connection conn
	 * @param int sysdocno
	 * @param int formseq
	 * @return int 카테고리일련번호
	 * @throws Exception 
	 */
	public int getNewCategorySeq(Connection conn, int sysdocno, int formseq) throws Exception {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int seq = 0;
		
		try {
			String query = "SELECT NVL(MAX(SEQ), 0) + 1 " +
						   "FROM ATTBOOKFRM " +
						   "WHERE SYSDOCNO = ? " +
						   "  AND FORMSEQ = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				seq = rs.getInt(1);
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt,rs);
			throw e;
	    } finally {	       
	    	try {rs.close();} catch(Exception e) {}
	    	try {pstmt.close();} catch(Exception e) {}
	    }
	    
	    return seq;
	}
	
	/**
	 * 새로운 공통양식 카테고리일련번호 가져오기
	 * @param Connection conn
	 * @param int formseq
	 * @return int 카테고리일련번호
	 * @throws Exception 
	 */
	public int getNewCommCategorySeq(Connection conn, int formseq) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int seq = 0;
		
		try {
			String query = "SELECT NVL(MAX(SEQ), 0) + 1 " +
						   "FROM COMMATTBOOKFRM " +
						   "WHERE FORMSEQ = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, formseq);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				seq = rs.getInt(1);
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt,rs);
			throw e;
	    } finally {	       
	    	try {rs.close();} catch(Exception e) {}
	    	try {pstmt.close();} catch(Exception e) {}
	    }
	    
	    return seq;
	}
	
	/**
	 * 새로운 카테고리정렬순서 가져오기
	 * @param Connection conn
	 * @param int sysdocno
	 * @param int formseq
	 * @return int 카테고리정렬순서
	 * @throws Exception 
	 */
	public int getNewCategoryOrd(Connection conn, int sysdocno, int formseq) throws Exception {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int ord = 0;
		
		try {
			String query = "SELECT NVL(MAX(ORD), 0) + 1 " +
						   "FROM ATTBOOKFRM " +
						   "WHERE SYSDOCNO = ? " +
						   "  AND FORMSEQ = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				ord = rs.getInt(1);
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt,rs);
			throw e;
	    } finally {	       
	    	try {rs.close();} catch(Exception e) {}
	    	try {pstmt.close();} catch(Exception e) {}
	    }
	    
	    return ord;
	}
	
	/**
	 * 새로운 공통양식 카테고리정렬순서 가져오기
	 * @param Connection conn
	 * @param int formseq
	 * @return int 카테고리정렬순서
	 * @throws Exception 
	 */
	public int getNewCommCategoryOrd(Connection conn, int formseq) throws Exception {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int ord = 0;
		
		try {
			String query = "SELECT NVL(MAX(ORD), 0) + 1 " +
						   "FROM COMMATTBOOKFRM " +
						   "WHERE FORMSEQ = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, formseq);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				ord = rs.getInt(1);
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt,rs);
			throw e;
	    } finally {	       
	    	try {rs.close();} catch(Exception e) {}
	    	try {pstmt.close();} catch(Exception e) {}
	    }
	    
	    return ord;
	}
	
	/**
	 * 양식첨부파일 추가
	 * @param Connection conn
	 * @param FileBookBean fbbean
	 * @throws Exception 
	 */
	public void addFileBook(Connection conn, FileBookBean fbbean) throws Exception {

		PreparedStatement pstmt = null;
		
		try {
			String query = 
					"INSERT INTO FILEBOOKFRM(SYSDOCNO, FORMSEQ, FILESEQ, FILENM, " +
					"                        ORIGINFILENM, FILESIZE, EXT, ORD) " +
					"VALUES(?, ?, ?, ?, ?, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, fbbean.getSysdocno());
			pstmt.setInt(2, fbbean.getFormseq());
			pstmt.setInt(3, fbbean.getFileseq());
			pstmt.setString(4, fbbean.getFilenm());
			pstmt.setString(5, fbbean.getOriginfilenm());
			pstmt.setInt(6, fbbean.getFilesize());
			pstmt.setString(7, fbbean.getExt());
			pstmt.setInt(8, fbbean.getOrd());
			
			pstmt.executeUpdate();
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
	}
	
	/**
	 * 공통양식첨부파일 추가
	 * @param Connection conn
	 * @param FileBookBean fbbean
	 * @throws Exception 
	 */
	public void addCommFileBook(Connection conn, FileBookBean fbbean) throws Exception {

		PreparedStatement pstmt = null;
		
		try {
			String query = 
					"INSERT INTO COMMFILEBOOKFRM(FORMSEQ, FILESEQ, FILENM, " +
					"                        ORIGINFILENM, FILESIZE, EXT) " +
					"VALUES(?, ?, ?, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, fbbean.getFormseq());
			pstmt.setInt(2, fbbean.getFileseq());
			pstmt.setString(3, fbbean.getFilenm());
			pstmt.setString(4, fbbean.getOriginfilenm());
			pstmt.setInt(5, fbbean.getFilesize());
			pstmt.setString(6, fbbean.getExt());
			
			pstmt.executeUpdate();
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
	}
	
	/**
	 * 양석첨부파일 삭제
	 * @param Connection conn
	 * @param FileBookBean fbbean
	 * @throws Exception 
	 */
	public void delFileBook(Connection conn, FileBookBean fbbean) throws Exception {

		PreparedStatement pstmt = null;
		
		try {
			String query = 
					"DELETE " +
					"FROM FILEBOOKFRM " +
					"WHERE SYSDOCNO = ? " +
					"  AND FORMSEQ = ? " +
					"  AND FILESEQ = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, fbbean.getSysdocno());
			pstmt.setInt(2, fbbean.getFormseq());
			pstmt.setInt(3, fbbean.getFileseq());
			
			pstmt.executeUpdate();
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
	}
	
	/**
	 * 공통양식첨부파일 삭제
	 * @param Connection conn
	 * @param FileBookBean fbbean
	 * @throws Exception 
	 */
	public void delCommFileBook(Connection conn, FileBookBean fbbean) throws Exception {

		PreparedStatement pstmt = null;
		
		try {
			String query = 
					"DELETE " +
					"FROM COMMFILEBOOKFRM " +
					"WHERE FORMSEQ = ? " +
					"  AND FILESEQ = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, fbbean.getFormseq());
			pstmt.setInt(2, fbbean.getFileseq());
			
			pstmt.executeUpdate();
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
	}
	
	/**
	 * 양식첨부파일 전체삭제
	 * @param Connection conn
	 * @param int sysdocno
	 * @param int formseq
	 * @throws Exception 
	 */
	public void delAllFileBook(Connection conn, int sysdocno, int formseq) throws Exception {
		
		PreparedStatement pstmt = null;
		
		try {
			String query = 
					"DELETE " +
					"FROM FILEBOOKFRM " +
					"WHERE SYSDOCNO = ? " +
					"  AND FORMSEQ = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
			
			pstmt.executeUpdate();
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
	}
	
	/**
	 * 최종자료첨부파일 전체삭제
	 * @param Connection conn
	 * @param int sysdocno
	 * @param int formseq
	 * @throws Exception 
	 */
	public void delAllFileCompBook(Connection conn, int sysdocno, int formseq) throws Exception {
		
		PreparedStatement pstmt = null;
		
		try {
			String query = 
					"DELETE " +
					"FROM DATABOOKFRMCMPT " +
					"WHERE SYSDOCNO = ? " +
					"  AND FORMSEQ = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
			
			pstmt.executeUpdate();
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
	}
	
	/**
	 * 공통양식첨부파일 전체삭제
	 * @param Connection conn
	 * @param int formseq
	 * @throws Exception 
	 */
	public void delAllCommFileBook(Connection conn, int formseq) throws Exception {
		
		PreparedStatement pstmt = null;
		
		try {
			String query = 
					"DELETE " +
					"FROM COMMFILEBOOKFRM " +
					"WHERE FORMSEQ = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, formseq);
			
			pstmt.executeUpdate();
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
	}
	
	/**
	 * 새로운 파일일련번호 가져오기
	 * @param Connection conn
	 * @param int sysdocno
	 * @param int formseq
	 * @return int 파일일련번호
	 * @throws Exception 
	 */
	public int getNewFileSeq(Connection conn, int sysdocno, int formseq) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int fileseq = 0;
		
		try {
			String query = "SELECT NVL(MAX(FILESEQ), 0) + 1 " +
						   "FROM FILEBOOKFRM " +
						   "WHERE SYSDOCNO = ? " +
						   "  AND FORMSEQ = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				fileseq = rs.getInt(1);
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt,rs);
			throw e;
	    } finally {	       
	    	try {rs.close();} catch(Exception e) {}
	    	try {pstmt.close();} catch(Exception e) {}
	    }
	    
	    return fileseq;
	}
	
	/**
	 * 새로운 공통파일일련번호 가져오기
	 * @param Connection conn
	 * @param int formseq
	 * @return int 파일일련번호
	 * @throws Exception 
	 */
	public int getNewCommFileSeq(Connection conn, int formseq) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int fileseq = 0;
		
		try {
			String query = "SELECT NVL(MAX(FILESEQ), 0) + 1 " +
						   "FROM COMMFILEBOOKFRM " +
						   "WHERE FORMSEQ = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, formseq);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				fileseq = rs.getInt(1);
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt,rs);
			throw e;
	    } finally {	       
	    	try {rs.close();} catch(Exception e) {}
	    	try {pstmt.close();} catch(Exception e) {}
	    }
	    
	    return fileseq;
	}
	
	/**
	 * 새로운 파일정렬순서 가져오기
	 * @param Connection conn
	 * @param int sysdocno
	 * @param int formseq
	 * @return int 파일정렬순서
	 * @throws Exception 
	 */
	public int getNewFileOrd(Connection conn, int sysdocno, int formseq) throws Exception {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int ord = 0;
		
		try {
			String query = "SELECT NVL(MAX(ORD), 0) + 1 " +
						   "FROM FILEBOOKFRM " +
						   "WHERE SYSDOCNO = ? " +
						   "  AND FORMSEQ = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				ord = rs.getInt(1);
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt,rs);
			throw e;
	    } finally {	       
	    	try {rs.close();} catch(Exception e) {}
	    	try {pstmt.close();} catch(Exception e) {}
	    }
	    
	    return ord;
	}
	
	/**
	 * 양식첨부파일 목록가져오기(작성)
	 * @param int sysdocno
	 * @param int formseq
	 * @return List 양식첨부파일목록(ArrayList)
	 * @throws Exception 
	 */
	public List getListFileBook(int sysdocno, int formseq) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List list = null;
		
		try {
			String query = "SELECT SYSDOCNO, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE " +
						   "FROM FILEBOOKFRM " +
						   "WHERE SYSDOCNO = ? " +
						   "  AND FORMSEQ = ? " +
						   "ORDER BY ORD ASC ";
			
			conn = ConnectionManager.getConnection();
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
			
			rs = pstmt.executeQuery();
			
			list = new ArrayList();
			
			while(rs.next()) {
				FileBookBean bean = new FileBookBean();
				
				bean.setSysdocno(rs.getInt("SYSDOCNO"));
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setFileseq(rs.getInt("FILESEQ"));
				bean.setFilenm(rs.getString("FILENM"));
				bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				bean.setFilesize(rs.getInt("FILESIZE"));
				
				list.add(bean);
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(conn,pstmt,rs);
		}
		
		return list;
	}
	
	/**
	 * 공통양식첨부파일 목록가져오기(작성)
	 * @param int formseq
	 * @return List 공통양식첨부파일목록(ArrayList)
	 * @throws Exception 
	 */
	public List getListCommFileBook(int formseq) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List list = null;
		
		try {
			String query = "SELECT FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT " +
						   "FROM COMMFILEBOOKFRM " +
						   "WHERE FORMSEQ = ? " +
						   "ORDER BY FILESEQ ASC ";
			
			conn = ConnectionManager.getConnection();
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, formseq);
			
			rs = pstmt.executeQuery();
			
			list = new ArrayList();
			
			while(rs.next()) {
				FileBookBean bean = new FileBookBean();
				
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setFileseq(rs.getInt("FILESEQ"));
				bean.setFilenm(rs.getString("FILENM"));
				bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				bean.setFilesize(rs.getInt("FILESIZE"));
				bean.setExt(rs.getString("EXT"));
				
				list.add(bean);
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(conn,pstmt,rs);
		}
		
		return list;
	}
	
	/**
	 * 사용했던양식첨부파일 목록가져오기(작성)
	 * @param int sysdocno
	 * @param int formseq
	 * @return List 양식첨부파일목록(ArrayList)
	 * @throws Exception 
	 */
	public List getListUsedFileBook(int sysdocno, int formseq) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List list = null;
		
		try {
			String query = "SELECT A.SYSDOCNO, A.FORMSEQ, A.FILESEQ, A.FILENM, A.ORIGINFILENM, A.FILESIZE, A.EXT " +
						   "FROM FILEBOOKFRM A, DOCMST B " +
						   "WHERE A.SYSDOCNO = B.SYSDOCNO " +
						   "  AND B.DOCSTATE > '01' " +
						   "  AND A.SYSDOCNO = ? " +
						   "  AND A.FORMSEQ = ? " +
						   "ORDER BY A.ORD ASC ";
			
			conn = ConnectionManager.getConnection();
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
			
			rs = pstmt.executeQuery();
			
			list = new ArrayList();
			
			while(rs.next()) {
				FileBookBean bean = new FileBookBean();
				
				bean.setSysdocno(rs.getInt("SYSDOCNO"));
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setFileseq(rs.getInt("FILESEQ"));
				bean.setFilenm(rs.getString("FILENM"));
				bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				bean.setFilesize(rs.getInt("FILESIZE"));
				bean.setExt(rs.getString("EXT"));
				
				list.add(bean);
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(conn,pstmt,rs);
		}
		
		return list;
	}
	
	/**
	 * 공통 양식자료 보기 - 양식폼 보여주기 데이터 가져오기
	 * 양식폼 가져오기
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param formseq : 양식일련번호
	 * 
	 * @return FormatBookBean 
	 * @throws Exception 
	 */
	public FormatBookBean getFormatFormView(int sysdocno, int formseq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		FormatBookBean Bean = new FormatBookBean();
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("\n SELECT SYSDOCNO, FORMSEQ, FORMCOMMENT	");
		selectQuery.append("\n   FROM FORMMST 							");
		selectQuery.append("\n  WHERE SYSDOCNO = ?						");
		selectQuery.append("\n    AND FORMKIND = '03'					");
		selectQuery.append("\n    AND FORMSEQ  = ?						");
		
		try {

			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
									
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Bean.setSysdocno(rs.getInt("SYSDOCNO"));
				Bean.setFormseq(rs.getInt("FORMSEQ"));
				Bean.setFormcomment(rs.getString("FORMCOMMENT"));
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return Bean;
	}
	
	/**
	 * 공통 양식자료 보기 - 제본형 샘플파일 가져오기
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param formseq : 양식일련번호
	 * 
	 * @return List
	 * @throws Exception 
	 */
	public List getFileBookFrm(int sysdocno, int formseq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List fileList = null;
		
		FileBookBean Bean = null;
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("\n SELECT SYSDOCNO, FORMSEQ, FILESEQ, FILENM ");
		selectQuery.append("\n      , ORIGINFILENM, FILESIZE, EXT, ORD   ");
		selectQuery.append("\n   FROM FILEBOOKFRM 			");
		selectQuery.append("\n  WHERE SYSDOCNO = ?			");
		selectQuery.append("\n    AND FORMSEQ  = ?			");
		selectQuery.append("\n  ORDER BY ORD				");
		
		try {

			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
									
			rs = pstmt.executeQuery();
			
			fileList = new ArrayList();
			
			while(rs.next()) {
				Bean = new FileBookBean();
				
				Bean.setSysdocno(rs.getInt("SYSDOCNO"));
				Bean.setFormseq(rs.getInt("FORMSEQ"));
				Bean.setFileseq(rs.getInt("FILESEQ"));
				Bean.setFilenm(rs.getString("FILENM"));
				Bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				Bean.setFilesize(rs.getInt("FILESIZE"));
				Bean.setExt(rs.getString("EXT"));
				Bean.setOrd(rs.getInt("ORD"));
				
				fileList.add(Bean);
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return fileList;
	}
	
	/** 
	 * 제본형 입력 첨부파일 추가
	 * 
	 * @param dataBean : 입력할 데이터
	 * @param fileBean : 입력할 파일 데이터
	 * 
	 * @return int
	 * @throws Exception 
	 */
	public int InsertDataBookFrm(DataBookBean dataBean, FileBean fileBean, String mode) throws Exception {
		Connection conn = null;       
		PreparedStatement pstmt = null;
		int result = 0;
				
		try {
			conn = ConnectionManager.getConnection(false);
			
			FormatManager fmgr = FormatManager.instance();
			String[][] deptInfo = fmgr.getInputDeptInfo(conn, dataBean.getSysdocno(), dataBean.getTgtdeptcd());
			
		 	StringBuffer insertQuery = new StringBuffer();
		 	StringBuffer updateQuery = new StringBuffer();
	       	
	       	insertQuery.append("\n INSERT INTO DATABOOKFRM ");
	       	insertQuery.append("\n (SYSDOCNO, FORMSEQ, TGTDEPTCD, INPUTUSRID, FILESEQ, CHRGUNITCD,                   ");
	       	insertQuery.append("\n  DEPTCD1, DEPTNM1, DEPTCD2, DEPTNM2, DEPTCD3, DEPTNM3,                            ");
			insertQuery.append("\n  DEPTCD4, DEPTNM4, DEPTCD5, DEPTNM5, DEPTCD6, DEPTNM6,                            ");
	       	insertQuery.append("\n  CATEGORYNM, FORMTITLE, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD)                 ");
	        insertQuery.append("\n VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
	        
			updateQuery.append("\n UPDATE INPUTUSR                                               ");
			updateQuery.append("\n    SET INPUTSTATE = '02'                                      ");
			updateQuery.append("\n      , CHRGUNITCD = ?                                         ");
			updateQuery.append("\n      , CHRGUNITNM = ?                                         ");
			updateQuery.append("\n      , UPTDT      = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') ");
			updateQuery.append("\n      , UPTUSRID   = ?                                         ");
			updateQuery.append("\n  WHERE SYSDOCNO   = ?                                         ");
			updateQuery.append("\n    AND TGTDEPT    = ?                                         ");
			updateQuery.append("\n    AND INPUTUSRID = ?  										 ");
		         
	       	pstmt = conn.prepareStatement(insertQuery.toString());	
			
	       	int idx = 0;
			pstmt.setInt(++idx, dataBean.getSysdocno());
			pstmt.setInt(++idx, dataBean.getFormseq());
			pstmt.setString(++idx, dataBean.getTgtdeptcd());
			pstmt.setString(++idx, dataBean.getInputusrid());
			pstmt.setInt(++idx, getMaxFileSeq(dataBean.getSysdocno(), dataBean.getFormseq(), dataBean.getTgtdeptcd(), dataBean.getInputusrid()) + 1);
			pstmt.setInt(++idx, dataBean.getChrgunitcd());
			
			for ( int j = 0; j < deptInfo.length; j++) {
				pstmt.setString(++idx, deptInfo[j][0]);
				pstmt.setString(++idx, deptInfo[j][1]);
			}
			
			pstmt.setString(++idx, dataBean.getCategorynm());
			pstmt.setString(++idx, dataBean.getFormtitle());
			pstmt.setString(++idx, fileBean.getFilenm());
			pstmt.setString(++idx, fileBean.getOriginfilenm());
			pstmt.setInt(++idx, fileBean.getFilesize());
			pstmt.setString(++idx, fileBean.getExt());
			pstmt.setInt(++idx, getMaxOrder(dataBean.getSysdocno(), dataBean.getFormseq(), dataBean.getTgtdeptcd(), dataBean.getInputusrid()) + 1);
			
			result = pstmt.executeUpdate();
			
			try {pstmt.close();} catch(Exception e) {}
			
			if(mode.equals("insert")){
				if(result > 0){
					pstmt = conn.prepareStatement(updateQuery.toString());
					pstmt.setInt(1, DeptManager.instance().getChrgunitcd(dataBean.getInputusrid()));
					pstmt.setString(2, DeptManager.instance().getChrgunitnm(dataBean.getTgtdeptcd(), DeptManager.instance().getChrgunitcd(dataBean.getInputusrid())));
					pstmt.setString(3, dataBean.getInputusrid());
					pstmt.setInt(4, dataBean.getSysdocno());
					pstmt.setString(5, dataBean.getTgtdeptcd());
					pstmt.setString(6, dataBean.getInputusrid());
						
					result = pstmt.executeUpdate();
				}
			}
			
			conn.commit();
		 } catch (Exception e) {
			 try {
				 conn.rollback();
			 } catch(Exception ex) {
				 logger.error("ERROR", ex);
//				 throw ex;
			 }
			 logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt);
			 throw e;
	     } finally {
	    	 try {
	    		 conn.setAutoCommit(true);
	    	 } catch(Exception ex) {
	    		 logger.error("ERROR", ex);
//	    		 throw ex;
			 }
	    	 ConnectionManager.close(conn,pstmt);	   
	     }	     
	     return result;
	}
	
	/** 
	 * 제본형 입력 첨부파일 삭제
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param formseq : 양식일련번호
	 * @param deptcd : 부서코드
	 * @param usrid : 사용자ID
	 * @param fileseq : 파일일련번호
	 * 
	 * @return int
	 * @throws Exception 
	 */
	public int DeleteDataBookFrm(int sysdocno, int formseq, String deptcd, String usrid, int fileseq) throws Exception {
		Connection conn = null;       
		PreparedStatement pstmt = null;
		int result = 0;
				
		try {								
		
		 	StringBuffer deleteQuery = new StringBuffer();
	       	
		 	deleteQuery.append("\n DELETE FROM DATABOOKFRM 	");
	       	deleteQuery.append("\n  WHERE SYSDOCNO   = ?	");
	       	deleteQuery.append("\n    AND FORMSEQ    = ? 	");
	       	deleteQuery.append("\n    AND TGTDEPTCD  = ?	");
	       	deleteQuery.append("\n    AND INPUTUSRID = ?	");
	       	deleteQuery.append("\n    AND FILESEQ    = ?	");
		    
	        conn = ConnectionManager.getConnection();
	        
	        conn.setAutoCommit(false);
	        
	       	pstmt = conn.prepareStatement(deleteQuery.toString());
	       	pstmt.setInt(1, sysdocno);
	       	pstmt.setInt(2, formseq);
	       	pstmt.setString(3, deptcd);
	       	pstmt.setString(4, usrid);
	       	pstmt.setInt(5, fileseq);
			
			result = pstmt.executeUpdate();
			
			conn.commit();
		 } catch (Exception e) {	
			try {
				conn.rollback();
			} catch(Exception ex) {
				logger.error("ERROR", ex);
				throw ex;
			}
			 logger.error("ERROR", e);
			 ConnectionManager.close(conn,pstmt);	   
			 throw e;
	     } finally {
	    	 try {
				conn.setAutoCommit(true);
	    	 } catch(Exception ex) {
				logger.error("ERROR", ex);
//				throw ex;
			 }
	    	 ConnectionManager.close(conn,pstmt);	   
	     }	     
	     return result;
	}
	
	/**
	 * 제본형 첨부파일 보기 - 제본형 첨부파일 가져오기
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param formseq : 양식일련번호
	 * @param deptcd : 부서코드
	 * @param usrid : 사용자ID
	 * @param chrgunitcd : 담당단위코드
	 * 
	 * @return List
	 * @throws Exception 
	 */
	public List getDataBookFrm(int sysdocno, int formseq, InputDeptSearchBoxBean idsbbean) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List dataList = null;
		
		DataBookBean Bean = null;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT SYSDOCNO, FORMSEQ, FILESEQ, CATEGORYNM,   \n");
		sql.append("       FORMTITLE, FILENM, ORIGINFILENM, FILESIZE \n");
		sql.append("FROM DATABOOKFRM D, DEPT C                       \n");		
		sql.append("WHERE D.TGTDEPTCD = C.DEPT_ID(+)                 \n");
		sql.append("AND SYSDOCNO = ?                                 \n");
		sql.append("AND FORMSEQ = ?                                  \n");		
		sql.append("AND NVL(DEPTCD1, '0') LIKE NVL(?, '%')           \n");
		sql.append("AND NVL(DEPTCD2, '0') LIKE NVL(?, '%')           \n");
		sql.append("AND NVL(DEPTCD3, '0') LIKE NVL(?, '%')           \n");
		sql.append("AND NVL(DEPTCD4, '0') LIKE NVL(?, '%')           \n");
		sql.append("AND NVL(DEPTCD5, '0') LIKE NVL(?, '%')           \n");
		sql.append("AND NVL(DEPTCD6, '0') LIKE NVL(?, '%')           \n");
		sql.append("AND NVL(CHRGUNITCD, '0') LIKE NVL(?, '%')        \n");
		sql.append("AND INPUTUSRID LIKE NVL(?, '%')                  \n");
		sql.append("ORDER BY ORD, DEPT_RANK                          \n");
		
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
			
			dataList = new ArrayList();
			
			while(rs.next()) {
				Bean = new DataBookBean();
				
				Bean.setSysdocno(rs.getInt("SYSDOCNO"));
				Bean.setFormseq(rs.getInt("FORMSEQ"));
				Bean.setFileseq(rs.getInt("FILESEQ"));
				Bean.setCategorynm(rs.getString("CATEGORYNM"));
				Bean.setFormtitle(rs.getString("FORMTITLE"));
				Bean.setFilenm(rs.getString("FILENM"));
				Bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				Bean.setFilesize(rs.getInt("FILESIZE"));
				
				dataList.add(Bean);
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return dataList;
	}
	
	/**
	 * 파일 시퀀스 번호 가져오기
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param formseq : 양식일련번호
	 * @param deptcd : 부서코드
	 * @param usrid : 사용자ID
	 * 
	 * @return int 
	 * @throws Exception 
	 */
	public int getMaxFileSeq(int sysdocno, int formseq, String deptcd, String usrid) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int fileseq = 0;
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("\n SELECT NVL(MAX(FILESEQ), 1) FILESEQ	");
		selectQuery.append("\n   FROM DATABOOKFRM					");
		selectQuery.append("\n  WHERE SYSDOCNO   = ?				");
		selectQuery.append("\n    AND FORMSEQ    = ?				");
		selectQuery.append("\n    AND TGTDEPTCD  = ?				");
		selectQuery.append("\n    AND INPUTUSRID = ?				");
		
		try {

			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
			pstmt.setString(3, deptcd);
			pstmt.setString(4, usrid);
									
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				fileseq = rs.getInt("FILESEQ");
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return fileseq;
	}
	
	/**
	 * 파일 정렬순서 가져오기
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param formseq : 양식일련번호
	 * @param deptcd : 부서코드
	 * @param usrid : 사용자ID
	 * 
	 * @return int 
	 * @throws Exception 
	 */
	public int getMaxOrder(int sysdocno, int formseq, String deptcd, String usrid) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int order = 0;
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("\n SELECT NVL(MAX(ORD), 1) ORD	");
		selectQuery.append("\n   FROM DATABOOKFRM			");
		selectQuery.append("\n  WHERE SYSDOCNO   = ?		");
		selectQuery.append("\n    AND FORMSEQ    = ?		");
		selectQuery.append("\n    AND TGTDEPTCD  = ?		");
		selectQuery.append("\n    AND INPUTUSRID = ?		");
		
		try {

			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
			pstmt.setString(3, deptcd);
			pstmt.setString(4, usrid);
									
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				order = rs.getInt("ORD");
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return order;
	}
	
	/**
	 * 양식첨부파일 목록가져오기(작성)
	 * @param int sysdocno
	 * @param int formseq
	 * @return List 양식첨부파일목록(ArrayList)
	 * @throws Exception 
	 */
	public List getFormDataList(int sysdocno, int formseq, InputDeptSearchBoxBean idsbbean, String rdb_sort, boolean isIncludeNotSubmitData) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List dataList = null;
		
		DataBookBean Bean = null;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT A.SYSDOCNO, A.FORMSEQ, A.TGTDEPTCD, A.INPUTUSRID, A.FILESEQ, \n");
		sql.append("       A.CATEGORYNM, A.FORMTITLE, A.FILENM,      \n");
		sql.append("       A.ORIGINFILENM, A.FILESIZE, A.EXT, A.ORD, \n");
		sql.append("       B.USER_NAME, C.TGTDEPTNM                  \n");
		sql.append("FROM DATABOOKFRM A, USR B, TGTDEPT C, DEPT D     \n");
		sql.append("WHERE A.INPUTUSRID = B.USER_ID(+)                \n");
		sql.append("AND A.SYSDOCNO = C.SYSDOCNO                      \n");
		sql.append("AND A.TGTDEPTCD = C.TGTDEPTCD                    \n");
		sql.append("AND C.TGTDEPTCD = D.DEPT_ID(+)                   \n");
		sql.append("AND A.SYSDOCNO = ?                               \n");
		sql.append("AND A.FORMSEQ = ?                                \n");
		sql.append("AND NVL(DEPTCD1, '0') LIKE NVL(?, '%')           \n");
		sql.append("AND NVL(DEPTCD2, '0') LIKE NVL(?, '%')           \n");
		sql.append("AND NVL(DEPTCD3, '0') LIKE NVL(?, '%')           \n");
		sql.append("AND NVL(DEPTCD4, '0') LIKE NVL(?, '%')           \n");
		sql.append("AND NVL(DEPTCD5, '0') LIKE NVL(?, '%')           \n");
		sql.append("AND NVL(DEPTCD6, '0') LIKE NVL(?, '%')           \n");
		sql.append("AND NVL(A.CHRGUNITCD, '0') LIKE NVL(?, '%')      \n");
		sql.append("AND INPUTUSRID LIKE NVL(?, '%')                  \n");
		
		if ( !isIncludeNotSubmitData ) {
			sql.append("AND C.SUBMITSTATE  = '05'                    \n");
		}
		
		if ( rdb_sort.equals("1") ) {
			sql.append("ORDER BY A.ORD, NVL(D.DEPT_RANK, 99999), NVL(B.USR_RANK, 99999), A.CATEGORYNM    \n");
		} else if ( rdb_sort.equals("2") ) {
			sql.append("ORDER BY A.CATEGORYNM, NVL(D.DEPT_RANK, 99999), NVL(B.USR_RANK, 99999), A.ORD    \n");
		} else {
			sql.append("ORDER BY NVL(D.DEPT_RANK, 99999), NVL(B.USR_RANK, 99999), A.CATEGORYNM, A.ORD    \n");
		}
		
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
			
			dataList = new ArrayList();
			
			while(rs.next()) {
				Bean = new DataBookBean();
				
				Bean.setSysdocno(rs.getInt("SYSDOCNO"));
				Bean.setFormseq(rs.getInt("FORMSEQ"));
				Bean.setTgtdeptcd(rs.getString("TGTDEPTCD"));
				Bean.setInputusrid(rs.getString("INPUTUSRID"));
				Bean.setFileseq(rs.getInt("FILESEQ"));
				Bean.setCategorynm(rs.getString("CATEGORYNM"));
				Bean.setFormtitle(rs.getString("FORMTITLE"));
				Bean.setFilenm(rs.getString("FILENM"));
				Bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				Bean.setFilesize(rs.getInt("FILESIZE"));
				Bean.setExt(rs.getString("EXT"));
				Bean.setOrd(rs.getString("ORD"));
				Bean.setTgtdeptnm(rs.getString("TGTDEPTNM"));
				Bean.setInputusrnm(rs.getString("USER_NAME"));
				
				dataList.add(Bean);
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return dataList;
	}
	
	public int DataBookOrdUpdate(String ord_gubun, List dataList) throws Exception {

		Connection con = null;
		PreparedStatement pstmt = null;
		int bindPos = 0;
		int cnt = 0;
		int[] ret = null;
		
		StringBuffer updateQuery = new StringBuffer();
		
		updateQuery.append("UPDATE DATABOOKFRM \n");
		if ( ord_gubun.equals("0") ) {
			updateQuery.append("SET ORD = (SELECT NUM                                         \n");
			updateQuery.append("           FROM (SELECT ROWNUM NUM, TGTDEPTCD, INPUTUSRID     \n");
			updateQuery.append("                 FROM (SELECT DISTINCT TGTDEPTCD, INPUTUSRID, \n");
			updateQuery.append("                              DEPT_RANK, USR_RANK             \n");
			updateQuery.append("                       FROM DATABOOKFRM B, DEPT D, USR U      \n");
			updateQuery.append("                       WHERE B.TGTDEPTCD = D.DEPT_ID(+)       \n");
			updateQuery.append("                       AND B.INPUTUSRID = U.USER_ID(+)        \n");
			updateQuery.append("                       AND SYSDOCNO = ?                       \n");
			updateQuery.append("                       AND FORMSEQ = ?                        \n");
			updateQuery.append("                       ORDER BY DEPT_RANK, USR_RANK))         \n");
			updateQuery.append("           WHERE TGTDEPTCD = ?                                \n");
			updateQuery.append("           AND INPUTUSRID = ?)                                \n");
		} else {
			updateQuery.append("SET ORD = ? \n");
		}
		updateQuery.append("WHERE SYSDOCNO = ? \n");
		updateQuery.append("AND FORMSEQ = ? \n");
		updateQuery.append("AND TGTDEPTCD = ? \n");
		updateQuery.append("AND INPUTUSRID = ? \n");
		updateQuery.append("AND FILESEQ = ? \n");
		
		try{
			con = ConnectionManager.getConnection();
			
			con.commit();
			con.setAutoCommit(false);
			
			/*제출부서 그룹 및 부서목록LIST등록*/
			if (dataList.size() > 0){
				pstmt = con.prepareStatement(updateQuery.toString());

				DataBookBean Bean = null;
				for (int i = 0 ; i < dataList.size() ; i++){
					Bean = (DataBookBean)dataList.get(i);

					if ( ord_gubun.equals("0") ) {
						pstmt.setInt(++bindPos, Bean.getSysdocno());
						pstmt.setInt(++bindPos, Bean.getFormseq());
						pstmt.setString(++bindPos, Bean.getTgtdeptcd());
						pstmt.setString(++bindPos, Bean.getInputusrid());
					} else if ( ord_gubun.equals("1") ) {
						pstmt.setString(++bindPos, "1");
					} else {
						pstmt.setString(++bindPos, Bean.getOrd());
					}
					pstmt.setInt(++bindPos, Bean.getSysdocno());
					pstmt.setInt(++bindPos, Bean.getFormseq());
					pstmt.setString(++bindPos, Bean.getTgtdeptcd());
					pstmt.setString(++bindPos, Bean.getInputusrid());
					pstmt.setInt(++bindPos, Bean.getFileseq());
					
					pstmt.addBatch();
					
					bindPos = 0;
				}
				
				ret = pstmt.executeBatch();
				cnt += ret.length;
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
	
	/** 
	 * 제본형 입력 최종자료 첨부파일 추가
	 * 
	 * @param dataBean : 입력할 데이터
	 * @param fileBean : 입력할 파일 데이터
	 * 
	 * @return int
	 * @throws Exception 
	 */
	public int DataBookCompFrm(DataBookBean dataBean, FileBean fileBean) throws Exception {
		Connection conn = null;       
		PreparedStatement pstmt = null;
		int result = 0;
				
		try {								
		
		 	StringBuffer insertQuery = new StringBuffer();
	       	
	       	insertQuery.append("\n INSERT INTO DATABOOKFRMCMPT ");
	       	insertQuery.append("\n (SYSDOCNO, FORMSEQ, FILENM, ORIGINFILENM, FILESIZE, EXT) ");
	        insertQuery.append("\n VALUES(?, ?, ?, ?, ?, ?) ");
		    
	        conn = ConnectionManager.getConnection();
	        
	        conn.setAutoCommit(false);
	        
	       	pstmt = conn.prepareStatement(insertQuery.toString());	
					    
			pstmt.setInt(1, dataBean.getSysdocno());
			pstmt.setInt(2, dataBean.getFormseq());
			pstmt.setString(3, fileBean.getFilenm());
			pstmt.setString(4, fileBean.getOriginfilenm());
			pstmt.setInt(5, fileBean.getFilesize());
			pstmt.setString(6, fileBean.getExt());
			
			result = pstmt.executeUpdate();
			
			conn.commit();
		 } catch (Exception e) {
			 try {
				 conn.rollback();
			 } catch(Exception ex) {
				 logger.error("ERROR", ex);
//				 throw ex;
			 }
			 logger.error("ERROR", e);
	    	 ConnectionManager.close(conn,pstmt);
			 throw e;
	     } finally {
	    	 try {
	    		 conn.setAutoCommit(true);
	    	 } catch(Exception ex) {
	    		 logger.error("ERROR", ex);
//	    		 throw ex;
			 }
	    	 ConnectionManager.close(conn,pstmt);	   
	     }	     
	     return result;
	}
		
	/**
	 * 최종제본자료  - 양식폼 보여주기 데이터 가져오기
	 * 양식폼 가져오기
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param formseq : 양식일련번호
	 * 
	 * @return FormatBookBean 
	 * @throws Exception 
	 */
	public DataBookBean getDataBookCompView(int sysdocno, int formseq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		DataBookBean Bean = new DataBookBean();
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("\n SELECT SYSDOCNO, FORMSEQ, FILENM, 	");
		selectQuery.append("\n		  ORIGINFILENM, FILESIZE, EXT	");
		selectQuery.append("\n   FROM DATABOOKFRMCMPT				");
		selectQuery.append("\n  WHERE SYSDOCNO = ?					");
		selectQuery.append("\n    AND FORMSEQ  = ?					");
		
		try {

			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
									
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Bean.setFilenm(rs.getString("FILENM"));
				Bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				Bean.setFilesize(rs.getInt("FILESIZE"));
				Bean.setExt(rs.getString("EXT"));
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}		
		return Bean;
	}
	
	/**
	 * 최종제본자료 양식첨부파일 삭제
	 * @param int sysdocno
	 * @param int formseq
	 * @throws Exception 
	 */
	public int DataBookCompDelete(int sysdocno, int formseq) throws Exception {
		Connection conn = null;       
		PreparedStatement pstmt = null;
		int result = 0;
				
		try {								
		
		 	StringBuffer deleteQuery = new StringBuffer();
	       	
		 	deleteQuery.append("\n DELETE FROM DATABOOKFRMCMPT 	");
	       	deleteQuery.append("\n  WHERE SYSDOCNO   = ?	");
	       	deleteQuery.append("\n    AND FORMSEQ    = ? 	");
		    
	        conn = ConnectionManager.getConnection();
	        
	        conn.setAutoCommit(false);
	        
	       	pstmt = conn.prepareStatement(deleteQuery.toString());
	       	pstmt.setInt(1, sysdocno);
	       	pstmt.setInt(2, formseq);
			
			result = pstmt.executeUpdate();
			
			conn.commit();
		 } catch (Exception e) {	
			try {
				conn.rollback();
			} catch(Exception ex) {
				logger.error("ERROR", ex);
//				throw ex;
			}
			 logger.error("ERROR", e);
	    	 ConnectionManager.close(conn,pstmt);
			 throw e;
	     } finally {
	    	 try {
				conn.setAutoCommit(true);
	    	 } catch(Exception ex) {
				logger.error("ERROR", ex);
//				throw ex;
			 }
	    	 ConnectionManager.close(conn,pstmt);	   
	     }	     
	     return result;
	}
	
	/**
	 * 확장자 체크  가져오기
	 * @param int sysdocno
	 * @param int formseq
	 * @param int fileseq
	 * @return String 파일명
	 * @throws Exception 
	 */
	public int getExtchk(int sysdocno, int formseq) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int extchk = 0;
		
		try {
			String query = "SELECT SUM(DECODE(LOWER(A.EXT), 'hwp', 0, 'txt', 0, 1)) " +
						   "  FROM DATABOOKFRM A, TGTDEPT B" +
						   " WHERE A.SYSDOCNO = B.SYSDOCNO" +
						   "   AND A.TGTDEPTCD = B.TGTDEPTCD" +
						   "   AND B.SUBMITSTATE = '05'" +
						   "   AND A.SYSDOCNO = ? " +
						   "   AND A.FORMSEQ = ? ";
			
			conn = ConnectionManager.getConnection();
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, sysdocno);
			pstmt.setInt(2, formseq);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				extchk = rs.getInt(1);
			}
		} catch(Exception e) {
			logger.error("ERROR",e);
	    	ConnectionManager.close(conn, pstmt, rs);
			throw e;
	    } finally {	       
	    	ConnectionManager.close(conn, pstmt, rs);
	    }
	    
	    return extchk;
	}	
}
