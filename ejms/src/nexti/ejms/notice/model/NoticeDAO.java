/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공지사항 dao
 * 설명:
 */
package nexti.ejms.notice.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.util.DateTime;
import nexti.ejms.util.FileBean;

public class NoticeDAO {
	
	private static Logger logger = Logger.getLogger(NoticeDAO.class);
		
	/**
	 * 공지사항 목록
	 * @throws Exception 
	 */
	public List noticeList(SearchBean search) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List noticeList = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT BUNHO, SEQ, TITLE, CONTENT, VISITNO, ");
			selectQuery.append("       CRTDT, CRTUSRNM ");
			selectQuery.append("FROM (SELECT ROWNUM BUNHO, SEQ,   TITLE, CONTENT, VISITNO, ");
			selectQuery.append("             CRTDT, CRTUSRNM ");					
			selectQuery.append("      FROM (SELECT SEQ, TITLE, CONTENT, VISITNO,  ");
			selectQuery.append("                   SUBSTR(CRTDT,1,10) CRTDT, CRTUSRNM ");
			selectQuery.append("            FROM NOTICE ");
			selectQuery.append("            WHERE TITLE LIKE ? ");
			selectQuery.append("              AND CONTENT LIKE ? ");
			selectQuery.append("              AND DELYN = '0' ");
			selectQuery.append("            ORDER BY CRTDT DESC) ");
			selectQuery.append("      ) ");
			selectQuery.append("WHERE BUNHO BETWEEN ? AND ? ");			
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			
			String gbn = search.getGubun(); //1:제목, 2:내용
			String sval = search.getSearchval().trim();
			int startidx = search.getStartidx();
			int endidx = search.getEndidx();
				
			//제목 조회조건 설정
			if("1".equals(gbn) && !"".equals(sval)){
				pstmt.setString(1, "%"+sval+"%");
			} else {
				pstmt.setString(1, "%");
			}
			
			//내용조회조건 설정
			if("2".equals(gbn) && !"".equals(sval)){
				pstmt.setString(2, "%"+sval+"%");
			} else {
				pstmt.setString(2, "%");
			}
			
			pstmt.setInt(3, startidx);
			pstmt.setInt(4, endidx);
									
			rs = pstmt.executeQuery();
			
			noticeList = new ArrayList();
			
			while (rs.next()) {
				NoticeBean notice = new NoticeBean();
				
				notice.setBunho(rs.getInt("BUNHO"));
				notice.setSeq(rs.getInt("SEQ"));
				notice.setTitle(rs.getString("TITLE"));		
				notice.setContent(rs.getString("CONTENT"));
				notice.setVisitno(rs.getInt("VISITNO"));
				notice.setCrtdt(rs.getString("CRTDT"));
				notice.setCrtusrnm(rs.getString("CRTUSRNM"));
				
				//글쓴일자가 오늘이면 New표시 할것
				String currentdate = DateTime.getCurrentDate();
				if(currentdate.equals(rs.getString("CRTDT"))){
					notice.setNewfl("1"); //새글
				} else {
					notice.setNewfl("0");
				}
				
				List attachFile = fileList(notice.getSeq());
				if ( attachFile != null && attachFile.size() > 0 ) {
					FileBean fbean = (FileBean)attachFile.get(0);
					notice.setFileseq(fbean.getFileseq());
					notice.setFilenm(fbean.getFilenm());
					notice.setOriginfilenm(fbean.getOriginfilenm());
					notice.setFilesize(fbean.getFilesize());
					notice.setExt(fbean.getExt());
					notice.setOrd(fbean.getOrd());
				}
								
				noticeList.add(notice);				
			}							
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return noticeList;
	}
	
	/** 
	 * 공지사항 전체 갯수 가져오기	
	 * @throws Exception 
	 */
	public int noticeTotCnt(SearchBean search) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int totalCount = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) ");
			selectQuery.append("FROM NOTICE ");
			selectQuery.append("WHERE TITLE LIKE ? ");		
			selectQuery.append("  AND CONTENT LIKE ? ");
			selectQuery.append("  AND DELYN = '0' ");				

			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			
			String gbn = search.getGubun(); //1:제목, 2:내용
			String sval = search.getSearchval().trim();		
				
			//제목 조회조건 설정
			if("1".equals(gbn) && !"".equals(sval)){
				pstmt.setString(1, "%"+sval+"%");
			} else {
				pstmt.setString(1, "%");
			}
			
			//내용조회조건 설정
			if("2".equals(gbn) && !"".equals(sval)){
				pstmt.setString(2, "%"+sval+"%");
			} else {
				pstmt.setString(2, "%");
			}
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				totalCount = rs.getInt(1);
			}

		 } catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return totalCount;
	}
	
	/**
	 * 공지사항 상세보기
	 * @throws Exception 
	 */
	public NoticeBean noticeInfo(int seq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		NoticeBean notice = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT SEQ,   TITLE, CONTENT, VISITNO, DELYN, ");
			selectQuery.append("       CRTDT, CRTUSRNM ");
			selectQuery.append("FROM NOTICE ");
			selectQuery.append("WHERE SEQ = ? ");								
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, seq);			
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				notice = new NoticeBean();
				
				notice.setSeq(rs.getInt("SEQ"));				
				notice.setTitle(rs.getString("TITLE"));
				notice.setContent(rs.getString("CONTENT"));
				notice.setVisitno(rs.getInt("VISITNO"));
				notice.setCrtdt(rs.getString("CRTDT"));
				notice.setCrtusrnm(rs.getString("CRTUSRNM"));
			}			
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return notice;
	}	
	
	/** 
	 * 방문회수 증가	
	 * @throws Exception 
	 */
	public int addVisitNo (int seq) throws Exception{
		Connection conn = null;        
		PreparedStatement pstmt = null;
		int result =0;
		
		try {
			StringBuffer updateQuery = new StringBuffer();

			updateQuery.append("UPDATE NOTICE SET VISITNO=VISITNO+1 ");
			updateQuery.append("WHERE SEQ=? ");
		    
			conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(updateQuery.toString());		
		    
			pstmt.setInt(1, seq);			
			
			result = pstmt.executeUpdate();			
			
		 } catch (Exception e) {
			 logger.error("ERROR", e);
			 ConnectionManager.close(conn,pstmt);
			 throw e;
	     } finally {	       
	    	 ConnectionManager.close(conn,pstmt);   
	     }	     
	     return result;
	}
	
	/** 
	 * 공지사항 삭제	
	 * 삭제플래그만 체크
	 * @throws Exception 
	 */
	public int deleteNotice (int seq) throws Exception{
		Connection conn = null;        
		PreparedStatement pstmt = null;
		int result =0;
		
		try {
			StringBuffer updateQuery = new StringBuffer();

			updateQuery.append("UPDATE NOTICE SET DELYN='1' ");
			updateQuery.append("WHERE SEQ=? ");
		    
			conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(updateQuery.toString());		
		    
			pstmt.setInt(1, seq);			
			
			result = pstmt.executeUpdate();			
			
		 } catch (Exception e) {
			 logger.error("ERROR", e);
	    	 ConnectionManager.close(conn,pstmt); 
			 throw e;
	     } finally {	       
	    	 ConnectionManager.close(conn,pstmt);   
	     }	     
	     return result;
	}
	
	/** 
	 * 공지사항 수정	
	 * @throws Exception 
	 */
	public int  updateNotice (NoticeBean notice) throws Exception{
		Connection conn = null;        
		PreparedStatement pstmt = null;
		int result =0;
		
		try {
			StringBuffer updateQuery = new StringBuffer();

			updateQuery.append("UPDATE NOTICE SET TITLE = ?, CONTENT = ? ");
			updateQuery.append("WHERE SEQ = ? ");
		    
			conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(updateQuery.toString());		
		    
		    pstmt.setString(1, notice.getTitle());
		    pstmt.setString(2, notice.getContent());
			pstmt.setInt(3, notice.getSeq());			
			
			result = pstmt.executeUpdate();			
			
		 } catch (Exception e) {
			 logger.error("ERROR", e);
	    	 ConnectionManager.close(conn,pstmt); 
			 throw e;
	     } finally {	       
	    	 ConnectionManager.close(conn,pstmt);   
	     }	     
	     return result;
	}
	
	/** 
	 * 공지사항 저장
	 * @throws Exception 
	 */
	public int insertNotice (NoticeBean notice) throws Exception{
		Connection conn = null;        
		PreparedStatement pstmt = null;
		int result =0;
		
		try {
			StringBuffer insertQuery = new StringBuffer();

			insertQuery.append("INSERT INTO NOTICE ");
			insertQuery.append("VALUES(?, ?, ?, ?, '0',   TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), ?) ");			
		    
			conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(insertQuery.toString());		
		    
		    int maxseq = getMaxSeq();
			pstmt.setInt(1, maxseq);
			pstmt.setString(2, notice.getTitle());
			pstmt.setString(3, notice.getContent());
			pstmt.setInt(4, 0);						
			pstmt.setString(5, notice.getCrtusrnm());
			
			result = pstmt.executeUpdate();			
			
			if(result > 0){
				result = maxseq;
			} 
		 } catch (Exception e) {
			 logger.error("ERROR", e);
	    	 ConnectionManager.close(conn,pstmt); 
			 throw e;
	     } finally {	       
	    	 ConnectionManager.close(conn,pstmt);   
	     }	     
	     return result;
	}
	
	/** 
	 * 공지사항 SEQ MAX값 가져오기
	 * @throws Exception 
	 */
	public int getMaxSeq() throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int  maxseq = 0;		
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT NVL(MAX(SEQ),0)+1 ");
			selectQuery.append("FROM NOTICE ");
			
			conn = ConnectionManager.getConnection();
			pstmt =	conn.prepareStatement(selectQuery.toString());

			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				maxseq = rs.getInt(1);
			}

		 } catch (Exception e) {
			logger.error("ERROR", e);
	    	 ConnectionManager.close(conn,pstmt,rs); 
			throw e;
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
	     
		return maxseq;
	}	
	
	/** 
	 * 공지사항 첨부파일 추가
	 * @throws Exception 
	 */
	public int fileInsert (int seq, String user_id, FileBean bean) throws Exception  {
		Connection conn = null;       
		PreparedStatement pstmt = null;
		int result =0;
				
		try {								
		
		 	StringBuffer insertQuery = new StringBuffer();
	       	
	       	insertQuery.append("INSERT INTO NOTICEFILE ");
	       	insertQuery.append("      (SEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, ");
	       	insertQuery.append("       EXT) ");
	        insertQuery.append("VALUES(?, ?, ?, ?, ?,    ?)");
		    
	        conn = ConnectionManager.getConnection();
	       	pstmt = conn.prepareStatement(insertQuery.toString());	
					    
			pstmt.setInt(1, seq);
			pstmt.setInt(2, getMaxFileSeq(seq));
			pstmt.setString(3, bean.getFilenm());
			pstmt.setString(4, bean.getOriginfilenm());
			pstmt.setInt(5, bean.getFilesize());
			pstmt.setString(6, bean.getExt());					
			
			result = pstmt.executeUpdate();			
			
		 } catch (Exception e) {	
			 logger.error("ERROR", e);
	    	 ConnectionManager.close(conn,pstmt);	
			 throw e;
	     } finally {	    
	    	 ConnectionManager.close(conn,pstmt);	   
	     }	     
	     return result;
	}
	
	/** 
	 * 첨부파일 테이블에서 FILESEQ MAX값 가져오기
	 * @throws Exception 
	 */
	public int getMaxFileSeq(int seq) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int  maxSeq = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT NVL(MAX(FILESEQ),0)+1 ");
			selectQuery.append("FROM NOTICEFILE ");	
			selectQuery.append("WHERE SEQ = ? ");
			
			conn = ConnectionManager.getConnection();
			pstmt =	conn.prepareStatement(selectQuery.toString());
			
			pstmt.setInt(1, seq);
			
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
	
	/** 
	 * 첨부파일 삭제
	 * @throws Exception 
	 */
	public int fileDelete (int seq, int fileseq) throws Exception {
		Connection conn = null;        
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {	
			//첨부파일 삭제
			StringBuffer deleteQuery = new StringBuffer();	

			deleteQuery.append("DELETE FROM NOTICEFILE ");
			deleteQuery.append("WHERE SEQ = ? ");
			deleteQuery.append("  AND FILESEQ = ? ");
			
			conn = ConnectionManager.getConnection();
			
		    pstmt = conn.prepareStatement(deleteQuery.toString());	
		    pstmt.setInt(1, seq);	
		    pstmt.setInt(2, fileseq);
			
			result = pstmt.executeUpdate();				
			
		 } catch (Exception e) {			
			 logger.error("ERROR", e);
	    	 ConnectionManager.close(conn,pstmt);	
			 throw e;
	     } finally {		    	
	    	 ConnectionManager.close(conn,pstmt);	   
	     }
	     
	     return result;
	}
	
	/**
	 * 첨부파일 정보
	 * @throws Exception 
	 */
	public List fileList(int seq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List fileList = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT SEQ, FILESEQ, FILENM, ORIGINFILENM,  FILESIZE, ");
			selectQuery.append("       EXT  ");
			selectQuery.append("FROM NOTICEFILE ");
			selectQuery.append("WHERE SEQ = ? ");			
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
						
			pstmt.setInt(1, seq);
						
			rs = pstmt.executeQuery();					
			
			fileList = new ArrayList();
			
			while ( rs.next() ){
				FileBean fbean = new FileBean();					
				
				fbean.setSeq(rs.getInt("SEQ"));
				fbean.setFileseq(rs.getInt("FILESEQ"));
				fbean.setFilenm(rs.getString("FILENM"));
				fbean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				fbean.setFilesize(rs.getInt("FILESIZE"));	
				fbean.setExt(rs.getString("EXT"));
				
				fileList.add(fbean);
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
	 * 파일명 가져오기
	 * @throws Exception 
	 */
	public String fileNm(int seq, int fileseq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT FILENM ");
			selectQuery.append("FROM NOTICEFILE ");
			selectQuery.append("WHERE SEQ = ? ");
			selectQuery.append("  AND FILESEQ = ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, seq);		
			pstmt.setInt(2, fileseq);
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				result = rs.getString("FILENM");
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
}
