/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 외부망 dao
 * 설명:
 */
package nexti.ejms.outside.model;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import nexti.ejms.commapproval.model.commapprovalBean;
import nexti.ejms.common.ConnectionManager;
import nexti.ejms.common.appInfo;
import nexti.ejms.research.form.ResearchForm;
import nexti.ejms.research.model.RchSearchBean;
import nexti.ejms.research.model.ResearchAnsBean;
import nexti.ejms.research.model.ResearchBean;
import nexti.ejms.research.model.ResearchSubBean;
import nexti.ejms.research.model.ResearchExamBean;
import nexti.ejms.sinchung.form.SinchungForm;
import nexti.ejms.sinchung.model.ArticleBean;
import nexti.ejms.sinchung.model.FrmBean;
import nexti.ejms.sinchung.model.ReqMstBean;
import nexti.ejms.sinchung.model.ReqSubBean;
import nexti.ejms.sinchung.model.SampleBean;
import nexti.ejms.sinchung.model.SearchBean;
import nexti.ejms.util.Base64;
import nexti.ejms.util.FileBean;
import nexti.ejms.util.FileManager;
import nexti.ejms.util.Utils;

public class OutsideDAO {
	
	private static Logger logger = Logger.getLogger(OutsideDAO.class);

	/**
	 * 설문조사 답변 건수 확인
	 * 양식 수정여부를 확인하기 위해서
	 */
	public int rchMstCnt(int rchno) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(RCHNO) ");
			selectQuery.append("FROM OUT_RCHANS ");
			selectQuery.append("WHERE RCHNO = ? ");
			//logger.info(selectQuery);
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, rchno);
								
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				result = rs.getInt(1);			
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
	 * 설문조사 답변삭제
	 * @param conn
	 * @param subList
	 * @param rchno
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public int rchAnsDel(List ansList) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int[] ret = null; 
        int result = 0;

		StringBuffer deleteQuery = new StringBuffer();
		
		deleteQuery.append("\n DELETE FROM OUT_RCHANS  WHERE RCHNO = ? AND ANSUSRSEQ = ?  ");
		
		try {
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			ResearchAnsBean ansBean = null;
			
			pstmt = con.prepareStatement(deleteQuery.toString());
			for(int j=0; j<ansList.size();j++){
				ansBean = (ResearchAnsBean)ansList.get(j);
	
				pstmt.setInt(1, ansBean.getRchno());
				pstmt.setInt(2, ansBean.getAnsusrseq());
				
				pstmt.addBatch();
			}
			ret = pstmt.executeBatch();
			result = result + ret.length;		
				
			con.commit();
		}catch(SQLException e){
			con.rollback();
			logger.error("ERROR",e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
	    }finally {	     
//	    	con.setAutoCommit(true);
			ConnectionManager.close(con,pstmt,rs);
	    }	     
		return result;
	}
	
	/**
	 * 설문조사 삭제
	 * @param sessionid
	 * @throws Exception
	 */
	public int ResearchDlete(int rchno, ServletContext sContext) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		int result= 0;
		
		try {
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
			
			String[] table = {"OUT_RCHMST", "OUT_RCHSUB", "OUT_RCHEXAM", "OUT_RCHEXAM" };
			
			for(int i = 0; i < table.length; i++) {
				
				String sql = 
					"DELETE " +
					"FROM " + table[i] + " " +
					"WHERE RCHNO LIKE ?";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, rchno);
				result += pstmt.executeUpdate();
				try {pstmt.close();} catch(Exception e) {}
			
			}
			
			//문항 보기 첨부파일 삭제
			result += delRchSubExamAllFile(conn, "", rchno, sContext);
			
			conn.commit();
		} catch(Exception e) {
			try {conn.rollback();} catch (Exception ex) {};
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt);
			throw e;
		} finally {
			ConnectionManager.close(conn, pstmt);
		}
		
		return result;
	}
	
	/**
	 * 설문조사그룹 삭제
	 * @param sessionid
	 * @throws Exception
	 */
	public int ResearchGrpDlete(int rchno) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		int result= 0;
		
		try {
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
			
			String[] table = {"OUT_RCHGRPMST"};
			
			for(int i = 0; i < table.length; i++) {
				
				String sql = 
					"DELETE " +
					"FROM " + table[i] + " " +
					"WHERE RCHGRPNO LIKE ?";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, rchno);
				result += pstmt.executeUpdate();
				try {pstmt.close();} catch(Exception e) {}
			
			}
			
			conn.commit();
		} catch(Exception e) {
			try {conn.rollback();} catch (Exception ex) {};
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt);
			throw e;
		} finally {
			ConnectionManager.close(conn, pstmt);
		}
		
		return result;
	}
	
	/**
	 * 설문조사 결과가져오기
	 * @return
	 * @throws Exception
	 */
	public String getRchResult() throws Exception {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList ansList = new ArrayList();
		String ansXML = "";
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("\n SELECT T1.RCHNO,						");
		selectQuery.append("\n        T1.ANSUSRSEQ,					");
		selectQuery.append("\n        T1.FORMSEQ,					");                                         
		selectQuery.append("\n        T1.ANSCONT,					");
		selectQuery.append("\n		  T1.OTHER,						");
		selectQuery.append("\n        T1.CRTDT,						");
		selectQuery.append("\n        T1.CRTUSRID,					");
		selectQuery.append("\n        T1.CRTUSRNM					");
		selectQuery.append("\n   FROM OUT_RCHANS T1					");
		selectQuery.append("\n  WHERE NVL(TRANSMITYN, ' ') <> 'Y'	");
		selectQuery.append("\n  ORDER BY RCHNO, ANSUSRSEQ			");
		//selectQuery.append("\n  WHERE T1.RCHNO IN ( "+ rchno + ")	");

		try{
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			rs = pstmt.executeQuery();
			
			ResearchAnsBean bean = null;
			while(rs.next()){
				bean = new ResearchAnsBean();
				
				bean.setRchno(rs.getInt("RCHNO"));
				bean.setAnsusrseq(rs.getInt("ANSUSRSEQ"));
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setAnscont(rs.getString("ANSCONT"));	
				bean.setOther(rs.getString("OTHER"));
				bean.setCrtdt(rs.getString("CRTDT"));
				bean.setCrtusrid(rs.getString("CRTUSRID"));
				bean.setCrtusrnm(rs.getString("CRTUSRNM"));
				
				rchResultTransmit(con, bean.getRchno(), bean.getAnsusrseq(), bean.getFormseq());
				ansList.add(bean);
				
				bean = null;				
			}
			
			ansXML = getAnsXML(ansList);
		}catch (Exception e) {
			ConnectionManager.close(con,pstmt,rs);
		}finally{
			ConnectionManager.close(con, pstmt, rs);
		}
		return ansXML;
	}
	
	/**
	 * 답변XML
	 * @param ansList
	 * @return
	 */
	private String getAnsXML(ArrayList ansList) {

		StringBuffer ansXML = new StringBuffer();
		
		if (ansList.size() > 0){
			
			ResearchAnsBean bean = null;
			for (int i = 0 ; i < ansList.size() ; i++){
				bean = (ResearchAnsBean)ansList.get(i);

				ansXML.append("\n <ansInfo id=\"").append(Base64.encodeString(i)).append("\">");
				ansXML.append("\n <rchno>").append(Base64.encodeString(bean.getRchno())).append("</rchno>");
				ansXML.append("\n <ansusrseq>").append(Base64.encodeString(bean.getAnsusrseq())).append("</ansusrseq>");
				ansXML.append("\n <formseq>").append(Base64.encodeString(bean.getFormseq())).append("</formseq>");
				ansXML.append("\n <anscont>").append(Base64.encodeString(bean.getAnscont())).append("</anscont>");
				ansXML.append("\n <other>").append(Base64.encodeString(bean.getOther())).append("</other>");
				ansXML.append("\n <crtdt>").append(Base64.encodeString(bean.getCrtdt())).append("</crtdt>");
				ansXML.append("\n <crtusrid>").append(Base64.encodeString(bean.getCrtusrid())).append("</crtusrid>");
				ansXML.append("\n <crtusrnm>").append(Base64.encodeString(bean.getCrtusrnm())).append("</crtusrnm>");
				ansXML.append("\n </ansInfo>");

				bean = null;
			}			
		}
		return (ansXML.toString());
	}
	
	/**
	 * 외부망 설문조사결과 전송완료 처리
	 * @throws Exception
	 */
	public void rchResultTransmitComplete() throws Exception {
		Connection conn = null;        
		PreparedStatement pstmt = null;
				
		try{
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE OUT_RCHANS SET TRANSMITYN = 'Y' \n");
			sql.append("WHERE TRANSMITYN IN ('N', 'Z') \n");
			
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.executeUpdate();
		} catch (Exception e) {		
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt);
			throw e;
		} finally {	       
	    	 ConnectionManager.close(conn, pstmt);   
		}
	}
	
	/**
	 * 외부망 설문조사결과 전송 처리
	 * @throws Exception
	 */
	public void rchResultTransmit(Connection conn, int rchno, int ansusrseq, int formseq) throws Exception {
		PreparedStatement pstmt = null;
				
		try{
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE OUT_RCHANS SET TRANSMITYN = DECODE(TRANSMITYN, 'N', 'Z', 'Z', 'Y', 'N') \n");
			sql.append("WHERE RCHNO = ? AND ANSUSRSEQ = ? AND FORMSEQ = ? \n");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, rchno);
			pstmt.setInt(2, ansusrseq);
			pstmt.setInt(3, formseq);
			pstmt.executeUpdate();
		} catch (Exception e) {		
			 logger.error("ERROR", e);
			 ConnectionManager.close(pstmt);
			 throw e;
		} finally {	       
	    	 ConnectionManager.close(pstmt);   
		}
	}
	
	/**
	 * 외부망 신청서결과 전송완료 처리
	 * @throws Exception
	 */
	public void reqResultTransmitComplete() throws Exception {
		Connection conn = null;        
		PreparedStatement pstmt = null;
				
		try{
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE OUT_REQMST SET TRANSMITYN = 'Y' \n");
			sql.append("WHERE TRANSMITYN IN ('N', 'Z') \n");
			
			conn = ConnectionManager.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.executeUpdate();
		} catch (Exception e) {		
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt);
			throw e;
		} finally {	       
	    	 ConnectionManager.close(conn, pstmt);   
		}
	}
	
	/**
	 * 외부망 신청서결과 전송 처리
	 * @throws Exception
	 */
	public void reqResultTransmit(Connection conn, int reqformno) throws Exception {
		PreparedStatement pstmt = null;
				
		try{
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE OUT_REQMST SET TRANSMITYN = DECODE(TRANSMITYN, 'N', 'Z', 'Z', 'Y', 'N') \n");
			sql.append("WHERE REQFORMNO = ? \n");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, reqformno);			
			pstmt.executeUpdate();
		} catch (Exception e) {		
			 logger.error("ERROR", e);
			 ConnectionManager.close(pstmt);
			 throw e;
		} finally {	       
	    	 ConnectionManager.close(pstmt);   
		}
	}
	
	/**
	 * 외부망 설문조사 리스트
	 * @return
	 * @throws Exception
	 */
	public String getRchResultList(String uid, String resident, String committee) throws Exception {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList list = new ArrayList();
		String result = "";
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("SELECT 'research_result' TYPE, RCHNO, RANGE, TITLE, COLDEPTNM, CHRGUSRNM, COLDEPTTEL, STRDT, ENDDT, NVL(UPTDT, CRTDT) CRTDT, OPENTYPE, EXHIBIT, \n");
		selectQuery.append("       CASE WHEN TO_CHAR(SYSDATE, 'YYYY-MM-DD') >= STRDT AND TO_CHAR(SYSDATE, 'YYYY-MM-DD') <= NVL(ENDDT, TO_CHAR(SYSDATE, 'YYYY-MM-DD')) THEN 'y' ELSE 'n' END PROC \n");
		selectQuery.append("FROM OUT_RCHMST \n");
		selectQuery.append("WHERE GROUPYN = 'N' \n");
		selectQuery.append("UNION ALL \n");
		selectQuery.append("SELECT 'research_group_result' TYPE, RCHGRPNO, RANGE, TITLE, COLDEPTNM, CHRGUSRNM, COLDEPTTEL, STRDT, ENDDT, NVL(UPTDT, CRTDT) CRTDT, OPENTYPE, EXHIBIT, \n");
		selectQuery.append("       CASE WHEN TO_CHAR(SYSDATE, 'YYYY-MM-DD') >= STRDT AND TO_CHAR(SYSDATE, 'YYYY-MM-DD') <= NVL(ENDDT, TO_CHAR(SYSDATE, 'YYYY-MM-DD')) THEN 'y' ELSE 'n' END PROC \n");
		selectQuery.append("FROM OUT_RCHGRPMST \n");
		selectQuery.append("WHERE TRIM(RCHNOLIST) IS NOT NULL \n");
		selectQuery.append("ORDER BY ENDDT DESC, STRDT DESC, CRTDT \n");

		try{
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			rs = pstmt.executeQuery();
			
			ResearchBean bean = null;
			while(rs.next()){
				bean = new ResearchBean();
				
				bean.setGroupyn(Utils.nullToEmptyString(rs.getString("TYPE")));
				bean.setRchno(rs.getInt("RCHNO"));
				bean.setRange(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 1));
				bean.setTitle(Utils.nullToEmptyString(rs.getString("TITLE")));
				bean.setColdeptnm(Utils.nullToEmptyString(rs.getString("COLDEPTNM")));
				bean.setChrgusrnm(Utils.nullToEmptyString(rs.getString("CHRGUSRNM")));
				bean.setColdepttel(Utils.nullToEmptyString(rs.getString("COLDEPTTEL")));
				bean.setStrdt(Utils.nullToEmptyString(rs.getString("STRDT")));
				bean.setEnddt(Utils.nullToEmptyString(rs.getString("ENDDT")));
				bean.setCrtdt(Utils.nullToEmptyString(rs.getString("CRTDT")));
				bean.setStatus(Utils.nullToEmptyString(rs.getString("PROC")));
				bean.setOpentype(Utils.nullToEmptyString(rs.getString("OPENTYPE")));
				bean.setExhibit(Utils.nullToEmptyString(rs.getString("EXHIBIT")));
				
				list.add(bean);
				
				bean = null;				
			}
			
			result = getListXml(list, uid, resident, committee);
		}catch(Exception e){
			ConnectionManager.close(con,pstmt,rs);
		}finally{
			ConnectionManager.close(con, pstmt, rs);
		}
		return result;
	}
	
	/**
	 * 외부망 설문조사 리스트
	 * @return
	 * @throws Exception
	 */
	public String getRchList(String syear, String uid, String resident, String committee) throws Exception {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList list = new ArrayList();
		String result = "";
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("SELECT 'research' TYPE, RCHNO, RANGE, TITLE, COLDEPTNM, CHRGUSRNM, DECODE(EXHIBIT,'1','y','n') EXHIBIT, DECODE(OPENTYPE,'1','y','n') OPENTYPE, COLDEPTTEL, STRDT, ENDDT, NVL(UPTDT, CRTDT) CRTDT, \n");
		selectQuery.append("       CASE WHEN TO_CHAR(SYSDATE, 'YYYY-MM-DD') >= STRDT AND TO_CHAR(SYSDATE, 'YYYY-MM-DD') <= NVL(ENDDT, TO_CHAR(SYSDATE, 'YYYY-MM-DD')) THEN 'y' ELSE 'n' END PROC \n");
		selectQuery.append("FROM OUT_RCHMST \n");
		selectQuery.append("WHERE GROUPYN = 'N' \n");
		//selectQuery.append("AND TO_CHAR(SYSDATE, 'YYYY-MM-DD') BETWEEN STRDT AND NVL(ENDDT, TO_CHAR(SYSDATE, 'YYYY-MM-DD')) \n");
		selectQuery.append("AND STRDT LIKE '" + syear + "%' \n");
		selectQuery.append("UNION ALL \n");
		selectQuery.append("SELECT 'research_group' TYPE, RCHGRPNO, RANGE, TITLE, COLDEPTNM, CHRGUSRNM, DECODE(EXHIBIT,'1','y','n') EXHIBIT, DECODE(OPENTYPE,'1','y','n') OPENTYPE, COLDEPTTEL, STRDT, ENDDT, NVL(UPTDT, CRTDT) CRTDT, \n");
		selectQuery.append("       CASE WHEN TO_CHAR(SYSDATE, 'YYYY-MM-DD') >= STRDT AND TO_CHAR(SYSDATE, 'YYYY-MM-DD') <= NVL(ENDDT, TO_CHAR(SYSDATE, 'YYYY-MM-DD')) THEN 'y' ELSE 'n' END PROC \n");
		selectQuery.append("FROM OUT_RCHGRPMST \n");
		selectQuery.append("WHERE TRIM(RCHNOLIST) IS NOT NULL \n");
		//selectQuery.append("AND TO_CHAR(SYSDATE, 'YYYY-MM-DD') BETWEEN STRDT AND NVL(ENDDT, TO_CHAR(SYSDATE, 'YYYY-MM-DD')) \n");
		selectQuery.append("AND STRDT LIKE '" + syear + "%' \n");
		selectQuery.append("ORDER BY ENDDT DESC, STRDT DESC, CRTDT \n");

		try{
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			rs = pstmt.executeQuery();
			
			ResearchBean bean = null;
			while(rs.next()){
				bean = new ResearchBean();
				
				bean.setGroupyn(Utils.nullToEmptyString(rs.getString("TYPE")));
				bean.setRchno(rs.getInt("RCHNO"));
				bean.setRange(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 1));
				bean.setTitle(Utils.nullToEmptyString(rs.getString("TITLE")));
				bean.setColdeptnm(Utils.nullToEmptyString(rs.getString("COLDEPTNM")));
				bean.setChrgusrnm(Utils.nullToEmptyString(rs.getString("CHRGUSRNM")));
				bean.setExhibit(Utils.nullToEmptyString(rs.getString("EXHIBIT")));
				bean.setOpentype(Utils.nullToEmptyString(rs.getString("OPENTYPE")));
				bean.setColdepttel(Utils.nullToEmptyString(rs.getString("COLDEPTTEL")));
				bean.setStrdt(Utils.nullToEmptyString(rs.getString("STRDT")));
				bean.setEnddt(Utils.nullToEmptyString(rs.getString("ENDDT")));
				bean.setCrtdt(Utils.nullToEmptyString(rs.getString("CRTDT")));
				bean.setStatus(Utils.nullToEmptyString(rs.getString("PROC")));
				
				list.add(bean);
				
				bean = null;				
			}
			
			result = getListXml(list, uid, resident, committee);
		}catch (Exception e){
			ConnectionManager.close(con,pstmt,rs);
		}finally{
			ConnectionManager.close(con, pstmt, rs);
		}
		return result;
	}
	
	/**
	 * 외부망 신청서 리스트
	 * @return
	 * @throws Exception
	 */
	public String getReqList(String syear, String uid, String resident, String committee) throws Exception {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList list = new ArrayList();
		String result = "";
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("SELECT 'request' TYPE, REQFORMNO, RANGE, TITLE, COLDEPTNM, CHRGUSRNM, COLDEPTTEL, STRDT, ENDDT, NVL(UPTDT, CRTDT) CRTDT, \n");
		selectQuery.append("       CASE WHEN TO_CHAR(SYSDATE, 'YYYY-MM-DD') >= STRDT AND TO_CHAR(SYSDATE, 'YYYY-MM-DD') <= NVL(ENDDT, TO_CHAR(SYSDATE, 'YYYY-MM-DD')) THEN 'y' ELSE 'n' END PROC \n");
		selectQuery.append("FROM OUT_REQFORMMST \n");
		//selectQuery.append("WHERE TO_CHAR(SYSDATE, 'YYYY-MM-DD') BETWEEN STRDT AND NVL(ENDDT, TO_CHAR(SYSDATE, 'YYYY-MM-DD')) \n");
		selectQuery.append("WHERE STRDT LIKE '"+ syear +"%' \n");
		selectQuery.append("ORDER BY ENDDT DESC, STRDT DESC, CRTDT \n");

		try{
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			rs = pstmt.executeQuery();
			
			ResearchBean bean = null;
			while(rs.next()){
				bean = new ResearchBean();				
				bean.setGroupyn(Utils.nullToEmptyString(rs.getString("TYPE")));
				bean.setRchno(rs.getInt("REQFORMNO"));
				bean.setRange(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 1));
				bean.setTitle(Utils.nullToEmptyString(rs.getString("TITLE")));
				bean.setColdeptnm(Utils.nullToEmptyString(rs.getString("COLDEPTNM")));
				bean.setChrgusrnm(Utils.nullToEmptyString(rs.getString("CHRGUSRNM")));
				bean.setColdepttel(Utils.nullToEmptyString(rs.getString("COLDEPTTEL")));
				bean.setStrdt(Utils.nullToEmptyString(rs.getString("STRDT")));
				bean.setEnddt(Utils.nullToEmptyString(rs.getString("ENDDT")));
				bean.setCrtdt(Utils.nullToEmptyString(rs.getString("CRTDT")));
				bean.setStatus(Utils.nullToEmptyString(rs.getString("PROC")));
				
				list.add(bean);
				
				bean = null;				
			}
			
			result = getListXml(list, uid, resident, committee);
		}catch (Exception e){
			ConnectionManager.close(con,pstmt,rs);
		}finally{
			ConnectionManager.close(con, pstmt, rs);
		}
		return result;
	}
	
	/**
	 * 리스트 xml
	 * @param ansList
	 * @return
	 */
	private String getListXml(ArrayList list, String uid, String resident, String committee) {

		StringBuffer resultXML = new StringBuffer();
		resultXML.append("\n<!--");
		resultXML.append("\n팝업 옵션 : width=700,height=680,location=no,menubar=no,resizable=yes,scrollbars=yes,");
		resultXML.append("\n          status=yes,titlebar=no,toolbar=no,channelmode=no,directories=no,fullscreen=no");
		resultXML.append("\n설문조사 결과목록 : " + appInfo.getOutsideweburl() + "outsideRchResultList.do?uid=사용자ID&resident=강남구거주여부(y or n)&committee=자치위원여부(y or n)");
		resultXML.append("\n설문조사 목록     : " + appInfo.getOutsideweburl() + "outsideRchList.do?uid=사용자ID&resident=강남구거주여부(y or n)&committee=자치위원여부(y or n)");
		resultXML.append("\n신청서 목록       : " + appInfo.getOutsideweburl() + "outsideReqList.do?uid=사용자ID&resident=강남구거주여부(y or n)&committee=자치위원여부(y or n)");
		resultXML.append("\n<seq>        : 일련번호");
		resultXML.append("\n<type>       : 양식종류(research_result:설문결과, research_group_result:그룹용설문결과, research:설문조사, research_group:그룹용설문지, request:신청서)");
		resultXML.append("\n<number>     : 양식번호");
		resultXML.append("\n<title>      : 양식제목");
		resultXML.append("\n<deptname>   : 작성부서");
		resultXML.append("\n<username>   : 작성자");
		resultXML.append("\n<depttel>    : 작성자전화");
		resultXML.append("\n<startdate>  : 시작일자");
		resultXML.append("\n<enddate>    : 종료일자");
		resultXML.append("\n<createdate> : 작성일자");
		resultXML.append("\n<progress>   : 진행여부(y or n)");
		resultXML.append("\n<link>       : 설문조사/신청서 링크");
		resultXML.append("\n-->");
		
		if (list.size() > 0){
			
			ResearchBean bean = null;
			for (int i = 0 ; i < list.size() ; i++){
				bean = (ResearchBean)list.get(i);
				
				String link = appInfo.getOutsideweburl();
				
				if ( "research".equals(bean.getGroupyn()) ) {
					link += "outsideRchView.do?rchno=" + bean.getRchno();
				} else if ( "research_group".equals(bean.getGroupyn()) ) {
					link += "outsideRchGrpView.do?rchgrpno=" + bean.getRchno() + "&mode=research";
				} else if ( "research_result".equals(bean.getGroupyn()) ) {
					link += "researchResult.do?rchno=" + bean.getRchno() + "&range=" + bean.getRange();
				} else if ( "research_group_result".equals(bean.getGroupyn()) ) {
					link += "researchGrpPreview.do?rchgrpno=" + bean.getRchno() + "&range=" + bean.getRange() + "&mode=result";
				} else if ( "request".equals(bean.getGroupyn()) ) {
					link += "outsideReqView.do?reqformno=" + bean.getRchno();
				}
				link += "&uid=" + uid + "&resident=" + resident + "&committee=" + committee;
				link = link.replaceAll("&", "&amp;");

				resultXML.append("\n\t<listInfo>");
				resultXML.append("\n\t\t<seq>").append(i + 1).append("</seq>");
				resultXML.append("\n\t\t<type>").append(bean.getGroupyn()).append("</type>");
				resultXML.append("\n\t\t<number>").append(bean.getRchno()).append("</number>");
				resultXML.append("\n\t\t<title>").append(bean.getTitle()).append("</title>");
				resultXML.append("\n\t\t<deptname>").append(bean.getColdeptnm()).append("</deptname>");
				resultXML.append("\n\t\t<username>").append(bean.getChrgusrnm()).append("</username>");
				resultXML.append("\n\t\t<depttel>").append(bean.getColdepttel()).append("</depttel>");
				resultXML.append("\n\t\t<startdate>").append(bean.getStrdt()).append("</startdate>");
				resultXML.append("\n\t\t<enddate>").append(bean.getEnddt()).append("</enddate>");
				resultXML.append("\n\t\t<createdate>").append(bean.getCrtdt()).append("</createdate>");
				resultXML.append("\n\t\t<progress>").append(bean.getStatus()).append("</progress>");
				resultXML.append("\n\t\t<public>").append(bean.getExhibit()).append("</public>");
				resultXML.append("\n\t\t<opentype>").append(bean.getOpentype()).append("</opentype>");
				resultXML.append("\n\t\t<link>").append(link).append("</link>");
				resultXML.append("\n\t</listInfo>");

				bean = null;
			}			
		}
		return (resultXML.toString());
	}
	
	/**
	 * 설문항목 저장
	 * @param Bean
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public int rchAllSave(ResearchBean Bean, ServletContext sContext) throws Exception {
		//logger.info("out rchAllSave start");
		Connection con = null;
        int result = 0;
        
		try {
			con = ConnectionManager.getConnection();
			
			con.commit();
			con.setAutoCommit(false);
			
			//임시테이블에 값이 있거나, 정규테이블에 값이 있을때는 업데이트 한다.
			if(checkCnt(Bean.getRchno()) ){						
				//마스터 저장
				result = rchUptMst(con, Bean, Bean.getRchno());							
			} else {
				//마스터 저장 (TEMP)
				result = rchInsMst(con, Bean);
			}
			
			//문항 보기 첨부파일 삭제
			result += delRchSubExamAllFile(con, "", Bean.getRchno(), sContext);
			
			//문항저장(보기포함)
			result += rchInsSub(con, Bean.getListrch(), Bean.getSessionId(), Bean.getRchno());
			
			//문항첨부파일 저장
			for ( int i = 0; Bean.getSubFileList() != null && i < Bean.getSubFileList().size(); i++ ) {
				ResearchSubBean rsBean = (ResearchSubBean)Bean.getSubFileList().get(i);
				FileBean fBean = new FileBean();
				fBean.setFileseq(rsBean.getFileseq());
				fBean.setFilenm(rsBean.getFilenm());
				fBean.setOriginfilenm(rsBean.getOriginfilenm());
				fBean.setFilesize(rsBean.getFilesize());
				fBean.setExt(rsBean.getExt());
				fBean.setOrd(rsBean.getOrd());
				result += addRchSubFile(con, "", rsBean.getRchno(), rsBean.getFormseq(), fBean);
			}
			
			//보기첨부파일 저장
			for ( int i = 0; Bean.getExamFileList() != null && i < Bean.getExamFileList().size(); i++ ) {
				ResearchExamBean reBean = (ResearchExamBean)Bean.getExamFileList().get(i);
				FileBean fBean = new FileBean();
				fBean.setFileseq(reBean.getFileseq());
				fBean.setFilenm(reBean.getFilenm());
				fBean.setOriginfilenm(reBean.getOriginfilenm());
				fBean.setFilesize(reBean.getFilesize());
				fBean.setExt(reBean.getExt());
				fBean.setOrd(reBean.getOrd());
				result += addRchExamFile(con, "", reBean.getRchno(), reBean.getFormseq(), reBean.getExamseq(), fBean);
			}
			
			con.commit();
		 } catch (Exception e) {
			con.rollback();
			logger.error("ERROR",e);
			ConnectionManager.close(con);
			throw e;
	     } finally {
//	    	 con.setAutoCommit(true);
	    	 ConnectionManager.close(con);
	     }
		//logger.info("out rchAllSave end");
		return result;
	}
	
	/**
	 * 설문항목 저장
	 * @param Bean
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public int rchGrpAllSave(ResearchBean Bean) throws Exception {
		Connection con = null;
        int result = 0;
        
		try {
			con = ConnectionManager.getConnection();
			
			con.commit();
			con.setAutoCommit(false);
			
			//임시테이블에 값이 있거나, 정규테이블에 값이 있을때는 업데이트 한다.
			if(checkCntGrp(Bean.getRchgrpno()) ){						
				//마스터 저장
				result = rchGrpUptMst(con, Bean, Bean.getRchgrpno());							
			} else {
				//마스터 저장 (TEMP)
				result = rchGrpInsMst(con, Bean);
			}
			
			con.commit();
		 } catch (Exception e) {
			con.rollback();
			logger.error("ERROR",e);
			ConnectionManager.close(con);
			throw e;
	     } finally {
//	    	 con.setAutoCommit(true);
	    	 ConnectionManager.close(con);
	     }
		return result;
	}
	
	/**
	 * 설문조사항목이 있는지 확인
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public boolean checkCnt(int rchno) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) FROM OUT_RCHMST WHERE RCHNO = ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, rchno);			
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				if(rs.getInt(1) > 0){
					result = true;
				}
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
	 * 설문조사그룹항목이 있는지 확인
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public boolean checkCntGrp(int rchgrpno) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) FROM OUT_RCHGRPMST WHERE RCHGRPNO = ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, rchgrpno);			
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				if(rs.getInt(1) > 0){
					result = true;
				}
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
	 * 설문 마감 	
	 */
	public int rchState(int rchno, String userid, int grpDuplicheck, int grpLimitcount) throws Exception {
		Connection con = null;        
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
				
		try{
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("\n SELECT COUNT(RCHNO) ");
			selectQuery.append("\n   FROM OUT_RCHMST  ");
			selectQuery.append("\n  WHERE RCHNO = ? ");
			if ( grpDuplicheck == -1 && grpLimitcount == -1 ) {
				selectQuery.append("\n    AND TO_CHAR(SYSDATE,'YYYY-MM-DD')BETWEEN STRDT AND ENDDT  ");
			}
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			pstmt.setInt(1, rchno);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				result = rs.getInt(1);
			}
			ConnectionManager.close(pstmt, rs);
			if ( result == 0 ){
			    selectQuery.delete(0, selectQuery.capacity());
			    selectQuery.append("\n SELECT COUNT(RCHNO) ");
	            selectQuery.append("\n   FROM OUT_RCHMST  ");
	            selectQuery.append("\n  WHERE RCHNO = ? ");
	            if ( grpDuplicheck == -1 && grpLimitcount == -1 ) {
	                selectQuery.append("\n    AND STRDT > TO_CHAR(SYSDATE,'YYYY-MM-DD')");
	            }
	            
//	            con = ConnectionManager.getConnection();
	            pstmt = con.prepareStatement(selectQuery.toString());
	            
	            pstmt.setInt(1, rchno);
	            
	            rs = pstmt.executeQuery();
	            
	            if ( rs.next() ){
	                if ( rs.getInt(1) > 0 ){
	                    result = -1;
	                }
	            }
			}
			ConnectionManager.close(pstmt, rs);
			if ( result > 0 ) {
				selectQuery.delete(0, selectQuery.capacity());
				if ( grpLimitcount == -1 ) {
					selectQuery.append("\n SELECT DECODE(LIMITCOUNT, 0, COUNT(DISTINCT ANSUSRSEQ) + 1, LIMITCOUNT) - COUNT(DISTINCT ANSUSRSEQ) ");
				} else {
					selectQuery.append("\n SELECT DECODE(" + grpLimitcount + ", 0, COUNT(DISTINCT ANSUSRSEQ) + 1, " + grpLimitcount + ") - COUNT(DISTINCT ANSUSRSEQ) ");
				}
				selectQuery.append("\n FROM OUT_RCHMST RM, OUT_RCHANS RA ");
				selectQuery.append("\n WHERE RM.RCHNO = RA.RCHNO(+)  ");
				selectQuery.append("\n AND RM.RCHNO = ? ");
				selectQuery.append("\n GROUP BY LIMITCOUNT ");
				
//				ConnectionManager.close(pstmt, rs);
				pstmt = con.prepareStatement(selectQuery.toString());
				
				pstmt.setInt(1, rchno);
				
				rs = pstmt.executeQuery();
				
				if ( rs.next() ) {
					result = rs.getInt(1);
					if ( result < 0 ) result = 0;
				}
			}
			ConnectionManager.close(pstmt, rs);
			if ( result > 0 ) {
				selectQuery.delete(0, selectQuery.capacity());				
				selectQuery.append("\n SELECT COUNT(R1.RCHNO)");
				selectQuery.append("\n FROM OUT_RCHANS R1, OUT_RCHMST R2");
				selectQuery.append("\n WHERE R1.RCHNO = R2.RCHNO");
				if ( grpDuplicheck == -1 ) {
					selectQuery.append("\n AND R2.DUPLICHECK = '2'");
				} else {
					selectQuery.append("\n AND '" + grpDuplicheck + "' = '2'");
				}
				selectQuery.append("\n AND R1.RCHNO = ? AND R1.CRTUSRID = ?");
				
//				ConnectionManager.close(pstmt, rs);
				pstmt = con.prepareStatement(selectQuery.toString());
				
				pstmt.setInt(1, rchno);
				pstmt.setString(2, userid);
				
				rs = pstmt.executeQuery();
				
				if ( rs.next() ){
					result = rs.getInt(1);
					
					if ( result > 0 ) {
						result = -1;
					} else {
						result = 1;
					}
				}
			}
			
		} catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {	       
	    	 ConnectionManager.close(con,pstmt, rs);   
		}
		return result;
	}
	
	/**
	 * 설문 마감 	
	 */
	public int rchGrpState(int rchgrpno) throws Exception {
		Connection con = null;        
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try{
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("\n SELECT COUNT(RCHGRPNO) ");
			selectQuery.append("\n   FROM OUT_RCHGRPMST  ");
			selectQuery.append("\n  WHERE RCHGRPNO = ? ");
			selectQuery.append("\n    AND TO_CHAR(SYSDATE,'YYYY-MM-DD')BETWEEN STRDT AND ENDDT  ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			pstmt.setInt(1, rchgrpno);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {	       
			ConnectionManager.close(con,pstmt, rs);   
		}
		return result;
	}
	
	/**
	 * 설문조사 항목 목록
	 * @param rchno
	 * @return
	 * @throws Exception
	 */
	public List getRchSubList(int rchno, String sessionId, String mode) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List rchSubList = null;
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("\n SELECT A.FORMSEQ, FORMGRP, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE, REQUIRE, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD, NVL(EX_FRSQ, 0) AS EX_FRSQ, NVL(EX_EXSQ, 0) AS EX_EXSQ ");
		selectQuery.append("\n   FROM OUT_RCHSUB A, OUT_RCHSUBFILE B ");
		selectQuery.append("\n  WHERE A.RCHNO = B.RCHNO(+) ");
		selectQuery.append("\n    AND A.FORMSEQ = B.FORMSEQ(+) ");
		selectQuery.append("\n    AND A.RCHNO = ? 			");
		selectQuery.append("\n  ORDER BY FORMSEQ 			");
		
		try {
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			
			pstmt.setInt(1, rchno);
						
			rs = pstmt.executeQuery();
			
			rchSubList = new ArrayList();
			
			while (rs.next()) {
				ResearchSubBean Bean = new ResearchSubBean();
				int formseq = rs.getInt("FORMSEQ");
				
				Bean.setFormseq(rs.getInt("FORMSEQ"));
				Bean.setFormgrp(rs.getString("FORMGRP"));
				Bean.setFormtitle(rs.getString("FORMTITLE"));
				Bean.setFormtype(rs.getString("FORMTYPE"));
				Bean.setSecurity(rs.getString("SECURITY"));
				Bean.setExamtype(rs.getString("EXAMTYPE"));
				Bean.setRequire(rs.getString("REQUIRE"));
				Bean.setFileseq(rs.getInt("FILESEQ"));
				Bean.setFilenm(rs.getString("FILENM"));
				Bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				Bean.setFilesize(rs.getInt("FILESIZE"));
				Bean.setExt(rs.getString("EXT"));				
				Bean.setOrd(rs.getInt("ORD"));
		        Bean.setEx_frsq(rs.getInt("EX_FRSQ"));
		        Bean.setEx_exsq(rs.getString("EX_EXSQ"));
				Bean.setExamList(rchExamList(rchno, sessionId, formseq, mode));

				rchSubList.add(Bean);		
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return rchSubList;
	}
	
	/**
	 * 설문조사 보기 목록
	 * @param rchno
	 * @param sessionId
	 * @param formseq
	 * @return
	 * @throws Exception
	 */
	public List rchExamList(int rchno, String sessionId, int formseq, String mode) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List rchExamList = null;	
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("\n SELECT T1.RCHNO, T1.FORMSEQ, T1.EXAMSEQ, T1.EXAMCONT, ");
		selectQuery.append("\n 	      (SELECT COUNT(DISTINCT ANSUSRSEQ) FROM RCHANS WHERE T1.RCHNO = RCHNO AND T1.FORMSEQ = FORMSEQ AND ANSCONT LIKE '%'||T1.EXAMSEQ||'%' ) CNT, ");
		selectQuery.append("\n        FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD ");
		selectQuery.append("\n   FROM OUT_RCHEXAM T1, OUT_RCHEXAMFILE T2 ");
		selectQuery.append("\n  WHERE T1.RCHNO = T2.RCHNO(+) ");
		selectQuery.append("\n    AND T1.FORMSEQ = T2.FORMSEQ(+) ");
		selectQuery.append("\n    AND T1.EXAMSEQ = T2.EXAMSEQ(+) ");
		selectQuery.append("\n    AND T1.RCHNO = ? 	");
		selectQuery.append("\n    AND T1.FORMSEQ = ? ");
		selectQuery.append("\n ORDER BY EXAMSEQ			");

		
		try {

			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if(rchno == 0){
				pstmt.setString(1, sessionId);
				pstmt.setInt(2, formseq);
			}else{
				pstmt.setInt(1, rchno);
				pstmt.setInt(2, formseq);
			}

			
			rs = pstmt.executeQuery();
			
			rchExamList = new ArrayList();
			int cnt = 0;
			while (rs.next()) {
				ResearchExamBean bean = new ResearchExamBean();
						
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setExamseq(rs.getInt("EXAMSEQ"));
				bean.setExamcont(rs.getString("EXAMCONT"));
				bean.setExamcnt(rs.getInt("CNT"));
				bean.setFileseq(rs.getInt("FILESEQ"));
				bean.setFilenm(rs.getString("FILENM"));
				bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				bean.setFilesize(rs.getInt("FILESIZE"));
				bean.setExt(rs.getString("EXT"));				
				bean.setOrd(rs.getInt("ORD"));
				
				rchExamList.add(bean);
				cnt = cnt + 1;
			}						
			
			if("".equals(mode)){
				//빈보기를 5개까지 채운다.
				for(int i=0;i<5-cnt;i++){
					ResearchExamBean bean = new ResearchExamBean();
					rchExamList.add(bean);
				}
			}
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return rchExamList;
	}
	
	/**
	 * 신청 신청내역 저장
	 */
	public int insertReqData(ReqMstBean mbean, String sessi) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;		
		int result = 0;					
		//logger.info(" insertReqData start");
		try{
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			//마스터 저장
			StringBuffer insertQuery1 = new StringBuffer();
			insertQuery1.append("INSERT INTO OUT_REQMST(REQFORMNO, REQSEQ, PRESENTNM, PRESENTID, PRESENTSN, PRESENTBD, ");
			insertQuery1.append("                   POSITION,  DUTY,   ZIP,       ADDR1,     ADDR2, ");
			insertQuery1.append("                   EMAIL,     TEL,    CEL,       FAX,       STATE, ");
			insertQuery1.append("                   CRTDT,     CRTUSRID) ");
			insertQuery1.append("VALUES(?,?,?,?,?,?,  ?,?,?,?,?,   ?,?,?,?,?,  TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'),?)");
			//logger.info(insertQuery1);
			int reqformno = mbean.getReqformno();
			int reqseq = getMaxReqseq(mbean.getReqformno());
			
			pstmt = con.prepareStatement(insertQuery1.toString());		
			pstmt.setInt(1, reqformno);
			pstmt.setInt(2, reqseq);
			pstmt.setString(3, mbean.getPresentnm());
			pstmt.setString(4, mbean.getPresentid());
			pstmt.setString(5, mbean.getPresentsn());
			pstmt.setString(6, mbean.getPresentbd());
			pstmt.setString(7, mbean.getPosition());
			pstmt.setString(8, mbean.getDuty());
			
			//주소입력 처리
			String addr = mbean.getAddr1();
			String addr_temp = "";
			String zip_temp = "";
			if(addr != null && addr.length()>9){
				zip_temp = addr.substring(1,6);
				addr_temp = addr.substring(9);
			}
			pstmt.setString(9, zip_temp);
			pstmt.setString(10, addr_temp);
			pstmt.setString(11, mbean.getAddr2());
			pstmt.setString(12, mbean.getEmail());
			pstmt.setString(13, mbean.getTel());
			pstmt.setString(14, mbean.getCel());
			pstmt.setString(15, mbean.getFax());
			pstmt.setString(16, "02");    //신청중	
			pstmt.setString(17, mbean.getCrtusrid());
			
			result = pstmt.executeUpdate();
			ConnectionManager.close(pstmt);
			
			//추가문항 저장
			if(mbean.getAnscontList() != null){
				
				StringBuffer insertQuery2 = new StringBuffer();
				insertQuery2.append("INSERT INTO OUT_REQSUB(REQFORMNO, REQSEQ, FORMSEQ, ANSCONT, OTHER) ");
				insertQuery2.append("VALUES(?,?,?,?,?)");
				//logger.info(insertQuery2);
				pstmt = con.prepareStatement(insertQuery2.toString());
				
				for(int i=0;i<mbean.getAnscontList().size();i++){
					ReqSubBean sbean = (ReqSubBean)mbean.getAnscontList().get(i);
					
					pstmt.clearParameters();
					pstmt.setInt(1, reqformno);
					pstmt.setInt(2, reqseq);
					pstmt.setInt(3, sbean.getFormseq());
					pstmt.setString(4, sbean.getAnscont());
					pstmt.setString(5, sbean.getOther());
					
					result += pstmt.executeUpdate();	
				}		
				ConnectionManager.close(pstmt);
			}
			
			con.commit();
		} catch (Exception e) {
			try{
				con.rollback();
			} catch(Exception ex){
				logger.error("ERROR",ex);
//				throw ex;
			}
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt);
			 throw e;
	     } finally {	
//	    	 try{ 
//		 		con.setAutoCommit(true);
//	    	 } catch (Exception e){
//				logger.error("ERROR",e);
////				throw e;
//	    	 }
			ConnectionManager.close(con,pstmt);	   
	     }	
		//logger.info(" insertReqData end");
		return result;
	}
	
	/**
	 * 신청 신청내역 수정
	 */
	public int updateReqData(ReqMstBean mbean) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;		
		int result = 0;					
				
		try{
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			//마스터 수정
			StringBuffer updateQuery1 = new StringBuffer();
			updateQuery1.append("UPDATE OUT_REQMST SET PRESENTNM = ?, PRESENTID = ?, PRESENTSN = ?, PRESENTBD=?, POSITION = ?,  DUTY = ?, ");
			updateQuery1.append("                  ZIP = ?,       ADDR1 = ?,     ADDR2 = ?,     EMAIL = ?,     TEL = ?, ");
			updateQuery1.append("                  CEL = ?,       FAX = ?,       UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), UPTUSRID = ? ");		
			updateQuery1.append("WHERE REQFORMNO = ? ");
			updateQuery1.append("  AND REQSEQ = ? ");			
			
			int reqformno = mbean.getReqformno();
			int reqseq = mbean.getReqseq();
			
			pstmt = con.prepareStatement(updateQuery1.toString());		
			
			pstmt.setString(1, mbean.getPresentnm());
			pstmt.setString(2, mbean.getPresentid());
			pstmt.setString(3, mbean.getPresentsn());
			pstmt.setString(4, mbean.getPresentbd());
			pstmt.setString(5, mbean.getPosition());
			pstmt.setString(6, mbean.getDuty());
			
			//주소입력 처리
			String addr = mbean.getAddr1();
			String addr_temp = "";
			String zip_temp = "";
			if(addr != null && addr.length()>9){
				zip_temp = addr.substring(1,6);
				addr_temp = addr.substring(9);
			}
			pstmt.setString(7, zip_temp);
			pstmt.setString(8, addr_temp);
			pstmt.setString(9, mbean.getAddr2());
			pstmt.setString(10, mbean.getEmail());
			pstmt.setString(11, mbean.getTel());
			pstmt.setString(12, mbean.getCel());
			pstmt.setString(13, mbean.getFax());					
			pstmt.setString(14, mbean.getUptusrid());			
			pstmt.setInt(15, reqformno);
			pstmt.setInt(16, reqseq);
						
			result = pstmt.executeUpdate();		
			ConnectionManager.close(pstmt);
			
			//추가문항 수정
			if(mbean.getAnscontList() != null){
				
				StringBuffer updateQuery2 = new StringBuffer();
				updateQuery2.append("UPDATE OUT_REQSUB SET ANSCONT = ?, OTHER = ? ");
				updateQuery2.append("WHERE REQFORMNO = ? ");
				updateQuery2.append("  AND REQSEQ = ? ");
				updateQuery2.append("  AND FORMSEQ = ? ");				
				
				pstmt = con.prepareStatement(updateQuery2.toString());
				
				for(int i=0;i<mbean.getAnscontList().size();i++){
					ReqSubBean sbean = (ReqSubBean)mbean.getAnscontList().get(i);
					
					pstmt.clearParameters();					
					pstmt.setString(1, sbean.getAnscont());
					pstmt.setString(2, sbean.getOther());
					pstmt.setInt(3, sbean.getReqformno());
					pstmt.setInt(4, sbean.getReqseq());
					pstmt.setInt(5, sbean.getFormseq());
					
					result += pstmt.executeUpdate();	
				}		
				ConnectionManager.close(pstmt);
			}	
			
			con.commit();
		} catch (Exception e) {
			try{
				con.rollback();
			} catch(Exception ex){
				logger.error("ERROR",ex);
//				throw ex;
			}
			ConnectionManager.close(con,pstmt);
			 logger.error("ERROR", e);
			 throw e;
	     } finally {	
//	    	 try{ 
//		 		con.setAutoCommit(true);
//	    	 } catch (Exception e){
//				logger.error("ERROR",e);
////				throw e;
//	    	 }
			ConnectionManager.close(con,pstmt);	   
	     }	
		return result;
	}
	
	/**
	 * 신청결과 삭제
	 * @param conn
	 * @param subList
	 * @param rchno
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public int reqAnsDel(List ansList, ServletContext sContext) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		int[] ret = null; 
        int result = 0;

		StringBuffer deleteQuery = new StringBuffer();
		StringBuffer deleteQuery1 = new StringBuffer();
		
		deleteQuery.append("\n DELETE FROM OUT_REQMST  WHERE REQFORMNO = ? AND REQSEQ = ?  ");
		deleteQuery1.append("\n DELETE FROM OUT_REQSUB  WHERE REQFORMNO = ? AND REQSEQ = ?  ");
		
		try {
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			ReqMstBean ansBean = null;
			
			pstmt = con.prepareStatement(deleteQuery.toString());
			for(int j=0; j<ansList.size();j++){
				ansBean = (ReqMstBean)ansList.get(j);
	
				pstmt.setInt(1, ansBean.getReqformno());
				pstmt.setInt(2, ansBean.getReqseq());
				
				pstmt.addBatch();
			}
			ret = pstmt.executeBatch();
			result = result + ret.length;	
			
			ansBean = null;
			ConnectionManager.close(pstmt);
			
			pstmt = con.prepareStatement(deleteQuery1.toString());
			for(int j=0; j<ansList.size();j++){
				ansBean = (ReqMstBean)ansList.get(j);
	
				pstmt.setInt(1, ansBean.getReqformno());
				pstmt.setInt(2, ansBean.getReqseq());
				
				pstmt.addBatch();
			}
			ret = pstmt.executeBatch();
			result = result + ret.length;

			for(int j=0; j<ansList.size();j++){
				delReqMstFile(con, ansBean.getReqformno(), ansBean.getReqseq(), sContext);
			}
							
			con.commit();
		}catch(SQLException e){
			con.rollback();
			logger.error("ERROR",e);
			ConnectionManager.close(con,pstmt);
			throw e;
	    }finally {	     
//	    	con.setAutoCommit(true);
			ConnectionManager.close(con,pstmt);
	    }	     
		return result;
	}
	
	/**
	 * 신청서 양식 관련 모든 테이블 삭제	
	 */
	public int RequestDel(int reqformno, ServletContext sContext) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;		
		int result = 0;	
						
		try{
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			//마스터 삭제(양식)
			StringBuffer delteQuery1 = new StringBuffer();
			delteQuery1.append("DELETE FROM OUT_REQFORMMST WHERE REQFORMNO = ? ");
			
			pstmt = con.prepareStatement(delteQuery1.toString());
			pstmt.setInt(1, reqformno);			
			
			result = pstmt.executeUpdate();
			ConnectionManager.close(pstmt);
			
			if(result == 0){return 0;}   //적용된 건이 없으면 오류 처리..
			
			//문항 삭제(양식)
			result += deleteArticleAll(con, reqformno, "");				
			//보기 삭제(양식)
			result += deleteSampleAll(con, reqformno, "");
			//문항보기 첨부파일 삭제
			result += delReqFormSubExamAllFile(con, "", reqformno, sContext);
			
			//신청내역삭제
			String[] dtable = {"OUT_REQMST", "OUT_REQSUB"};			
			
			for(int i=0;i<dtable.length;i++){
				StringBuffer delteQuery2 = new StringBuffer();
				delteQuery2.append("DELETE FROM "+dtable[i]+" WHERE REQFORMNO = ? ");
				
				pstmt = con.prepareStatement(delteQuery2.toString());				
				pstmt.setInt(1, reqformno);			
							
				result += pstmt.executeUpdate();
				ConnectionManager.close(pstmt);
			}
			
			result += delReqMstAllFile(con, reqformno, sContext);
			
			con.commit();
		} catch (Exception e) {
			try{
				con.rollback();
			} catch(Exception ex){
				logger.error("ERROR",ex);
//				throw ex;
			}
			ConnectionManager.close(con,pstmt);
			 logger.error("ERROR", e);
			 throw e;
	     } finally {	
//	    	 try{ 
//		 		con.setAutoCommit(true);
//	    	 } catch (Exception e){
//				logger.error("ERROR",e);
////				throw e;
//	    	 }
			ConnectionManager.close(con,pstmt);	   
	     }	
		return result;
	}
	
	/**
	 * 신청서 신청내용 가져오기
	 * @return
	 * @throws Exception
	 */
	public String getReqResult(ServletContext sContext) throws Exception {
		//logger.info("신청서 신청내용 가져오기(getReqResult)");
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList ansList = new ArrayList();
		String ansXML = "";
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("\n SELECT T1.REQFORMNO,					");
		selectQuery.append("\n        T1.REQSEQ,					");
		selectQuery.append("\n        T1.PRESENTNM,					");                                         
		selectQuery.append("\n        T1.PRESENTID,					");
		selectQuery.append("\n		  T1.PRESENTSN,					");
		selectQuery.append("\n		  T1.PRESENTBD,					");
		selectQuery.append("\n        T1.POSITION,					");
		selectQuery.append("\n        T1.DUTY,						");
		selectQuery.append("\n		  T1.ZIP,						");
		selectQuery.append("\n        T1.ADDR1,						");
		selectQuery.append("\n        T1.ADDR2,						");
		selectQuery.append("\n		  T1.EMAIL,						");
		selectQuery.append("\n        T1.TEL,						");
		selectQuery.append("\n        T1.CEL,						");
		selectQuery.append("\n		  T1.FAX,						");
		selectQuery.append("\n        T1.STATE,						");		
		selectQuery.append("\n 		  T1.CRTDT,						");
		selectQuery.append("\n 		  T1.CRTUSRID					");
		selectQuery.append("\n   FROM OUT_REQMST T1					");
		selectQuery.append("\n  WHERE NVL(TRANSMITYN, ' ') <> 'Y'	");
		selectQuery.append("\n  ORDER BY T1.REQFORMNO, T1.REQSEQ	");
		//selectQuery.append("\n  WHERE T1.REQFORMNO IN ( "+ reqformno + ")	");
		//logger.info("1 : "+selectQuery.toString());
		try{
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			//logger.info("2 : "+selectQuery.toString());
			rs = pstmt.executeQuery();
			
			ReqMstBean bean = null;
			while(rs.next()){
				bean = new ReqMstBean();
				
				bean.setReqformno(rs.getInt("REQFORMNO"));
				bean.setReqseq(rs.getInt("REQSEQ"));
				bean.setPresentnm(rs.getString("PRESENTNM"));
				bean.setPresentid(rs.getString("PRESENTID"));
				bean.setPresentsn(rs.getString("PRESENTSN"));
				bean.setPresentbd(rs.getString("PRESENTBD"));
				bean.setPosition(rs.getString("POSITION"));
				bean.setDuty(rs.getString("DUTY"));
				bean.setZip(rs.getString("ZIP"));
				bean.setAddr1(rs.getString("ADDR1"));
				bean.setAddr2(rs.getString("ADDR2"));
				bean.setEmail(rs.getString("EMAIL"));
				bean.setTel(rs.getString("TEL"));
				bean.setCel(rs.getString("CEL"));
				bean.setFax(rs.getString("FAX"));
				bean.setState(rs.getString("STATE"));
				bean.setCrtdt(rs.getString("CRTDT"));
				bean.setCrtusrid(rs.getString("CRTUSRID"));
				
				bean.setAnscontList(getReqSubList(rs.getInt("REQFORMNO"), rs.getInt("REQSEQ")));
				
				ReqMstBean rmBean = getReqMstFile(con, bean.getReqformno(), bean.getReqseq());
				if ( rmBean != null ) {
					bean.setFileseq(rmBean.getFileseq());
					bean.setFilenm(rmBean.getFilenm());
					bean.setOriginfilenm(rmBean.getOriginfilenm());
					bean.setFilesize(rmBean.getFilesize());
					bean.setExt(rmBean.getExt());
					bean.setOrd(rmBean.getOrd());
					
					String fileData = "";
					try {
						String file = sContext.getRealPath(rmBean.getFilenm());
						byte buffer[] = new byte[4096];
						BufferedInputStream bis = null;
						ByteArrayOutputStream baos = null;
						try {
							bis = new BufferedInputStream(new FileInputStream(file));
							baos = new ByteArrayOutputStream();
							int i;
							while ( (i = bis.read(buffer, 0, buffer.length) ) != -1 ) {
								baos.write(buffer, 0, i);
							}
							
							fileData = new String(Base64.encodeBytes(baos.toByteArray()).getBytes("UTF-8"));
						} catch ( Exception e) {
							e.printStackTrace();
						} finally {
							try { bis.close(); } catch ( Exception e ) {}
							try { baos.close(); } catch ( Exception e ) {}
						}
					} catch ( Exception e ) {
						e.printStackTrace();
					}
					bean.setFileToBase64Encoding(Utils.nullToEmptyString(fileData));
				}
				
				reqResultTransmit(con, bean.getReqformno());
				ansList.add(bean);
				
				bean = null;				
			}
			
			ansXML = getReqAnsXML(ansList);
		}catch (Exception e) {
			ConnectionManager.close(con, pstmt, rs);
			throw e;
		}finally{
			ConnectionManager.close(con, pstmt, rs);
		}
		return ansXML;
	}
	
	/**
	 * 신청서항목 가져오기
	 * @param reqformno
	 * @param reqseq
	 * @return
	 * @throws Exception
	 */
	public List getReqSubList(int reqformno, int reqseq) throws Exception {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList subList = new ArrayList();
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("\n SELECT T1.REQFORMNO,					");
		selectQuery.append("\n        T1.REQSEQ,					");
		selectQuery.append("\n        T1.FORMSEQ,					");                                         
		selectQuery.append("\n        T1.ANSCONT,					");
		selectQuery.append("\n		  T1.OTHER						");
		selectQuery.append("\n   FROM OUT_REQSUB T1					");
		selectQuery.append("\n  WHERE T1.REQFORMNO = ?				");
		selectQuery.append("\n    AND T1.REQSEQ = ? 				");	

		try{
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			pstmt.setInt(1, reqformno);
			pstmt.setInt(2, reqseq);
			
			rs = pstmt.executeQuery();
			
			ReqSubBean bean = null;
			while(rs.next()){
				bean = new ReqSubBean();
				
				bean.setReqformno(rs.getInt("REQFORMNO"));
				bean.setReqseq(rs.getInt("REQSEQ"));
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setAnscont(rs.getString("ANSCONT"));
				bean.setOther(rs.getString("OTHER"));
				
				subList.add(bean);
				
				bean = null;				
			}
		}finally{
			ConnectionManager.close(con, pstmt, rs);
		}
		return subList;
	}
	
	/**
	 * 신청결과XML
	 * @param ansList
	 * @return
	 */
	private String getReqAnsXML(ArrayList ansList) {

		StringBuffer ansXML = new StringBuffer();
		
		if (ansList.size() > 0){
			
			ReqMstBean bean = null;
			for (int i = 0 ; i < ansList.size() ; i++){
				bean = (ReqMstBean)ansList.get(i);

				ansXML.append("\n <ansInfo id=\"").append(Base64.encodeString(i)).append("\">");
				ansXML.append("\n <reqformno>").append(Base64.encodeString(bean.getReqformno())).append("</reqformno>");
				ansXML.append("\n <reqseq>").append(Base64.encodeString(bean.getReqseq())).append("</reqseq>");
				ansXML.append("\n <presentnm>").append(Base64.encodeString(bean.getPresentnm())).append("</presentnm>");
				ansXML.append("\n <presentid>").append(Base64.encodeString(bean.getPresentid())).append("</presentid>");
				ansXML.append("\n <presentsn>").append(Base64.encodeString(bean.getPresentsn())).append("</presentsn>");
				ansXML.append("\n <presentbd>").append(Base64.encodeString(bean.getPresentbd())).append("</presentbd>");
				ansXML.append("\n <position>").append(Base64.encodeString(bean.getPosition())).append("</position>");
				ansXML.append("\n <duty>").append(Base64.encodeString(bean.getDuty())).append("</duty>");
				ansXML.append("\n <zip>").append(Base64.encodeString(bean.getZip())).append("</zip>");
				ansXML.append("\n <addr1>").append(Base64.encodeString(bean.getAddr1())).append("</addr1>");
				ansXML.append("\n <addr2>").append(Base64.encodeString(bean.getAddr2())).append("</addr2>");	
				ansXML.append("\n <email>").append(Base64.encodeString(bean.getEmail())).append("</email>");
				ansXML.append("\n <tel>").append(Base64.encodeString(bean.getTel())).append("</tel>");	
				ansXML.append("\n <cel>").append(Base64.encodeString(bean.getCel())).append("</cel>");
				ansXML.append("\n <fax>").append(Base64.encodeString(bean.getFax())).append("</fax>");	
				ansXML.append("\n <state>").append(Base64.encodeString(bean.getState())).append("</state>");
				ansXML.append("\n <crtdt>").append(Base64.encodeString(bean.getCrtdt())).append("</crtdt>");
				ansXML.append("\n <crtusrid>").append(Base64.encodeString(bean.getCrtusrid())).append("</crtusrid>");
				
				ReqSubBean subbean = null;
				for(int j=0; j<bean.getAnscontList().size(); j++){
					subbean = (ReqSubBean)bean.getAnscontList().get(j);
					ansXML.append("\n <ansSub id=\"").append(Base64.encodeString(j)).append("\">");
					ansXML.append("\n <formseq>").append(Base64.encodeString(subbean.getFormseq())).append("</formseq>");
					ansXML.append("\n <anscont>").append(Base64.encodeString(subbean.getAnscont())).append("</anscont>");
					ansXML.append("\n <other>").append(Base64.encodeString(subbean.getOther())).append("</other>");
					ansXML.append("\n </ansSub>");
					
					subbean = null;
				}
				
				ansXML.append("\n <fileseq>").append(Base64.encodeString(bean.getFileseq())).append("</fileseq>");
				ansXML.append("\n <filenm>").append(Base64.encodeString(bean.getFilenm())).append("</filenm>");
				ansXML.append("\n <originfilenm>").append(Base64.encodeString(bean.getOriginfilenm())).append("</originfilenm>");
				ansXML.append("\n <filesize>").append(Base64.encodeString(bean.getFilesize())).append("</filesize>");
				ansXML.append("\n <ext>").append(Base64.encodeString(bean.getExt())).append("</ext>");
				ansXML.append("\n <ord>").append(Base64.encodeString(bean.getOrd())).append("</ord>");
				ansXML.append("\n <filetobase64encoding>").append(bean.getFileToBase64Encoding()).append("</filetobase64encoding>");
				
				ansXML.append("\n </ansInfo>");

				bean = null;
			}			
		}
		return (ansXML.toString());
	}
	
	/**
	 * 신청서 전체 저장(TEMP)
	 */
	public int reqAllSave(FrmBean fbean, ServletContext sContext) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;		
		int result = 0;	
				
		try{
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			//임시테이블에 값이 있으면 업데이트 한다.
			if(existCheck(fbean.getReqformno())){								
				//마스터 저장
				result = updateMst(con, fbean);							
			} else {
				//마스터 저장 (TEMP)
				result = insertMst(con, fbean);
			}
			
			//문항 삭제
			result += deleteArticleAll(con, fbean.getReqformno(), fbean.getSessi());				
			//보기 삭제
			result += deleteSampleAll(con, fbean.getReqformno(), fbean.getSessi());
			//문항보기 첨부파일 삭제
			result += delReqFormSubExamAllFile(con, "", fbean.getReqformno(), sContext);
			
			//문항저장(보기포함)
			result += insertArticleAll(con, fbean.getArticleList());
			
			//문항첨부파일 저장
			for ( int i = 0; fbean.getSubFileList() != null && i < fbean.getSubFileList().size(); i++ ) {
				ArticleBean aBean = (ArticleBean)fbean.getSubFileList().get(i);
				FileBean fBean = new FileBean();
				fBean.setFileseq(aBean.getFileseq());
				fBean.setFilenm(aBean.getFilenm());
				fBean.setOriginfilenm(aBean.getOriginfilenm());
				fBean.setFilesize(aBean.getFilesize());
				fBean.setExt(aBean.getExt());
				fBean.setOrd(aBean.getOrd());
				result += addReqFormSubFile(con, "", aBean.getReqformno(), aBean.getFormseq(), fBean);
			}
			
			//보기첨부파일 저장
			for ( int i = 0; fbean.getExamFileList() != null && i < fbean.getExamFileList().size(); i++ ) {
				SampleBean sBean = (SampleBean)fbean.getExamFileList().get(i);
				FileBean fBean = new FileBean();
				fBean.setFileseq(sBean.getFileseq());
				fBean.setFilenm(sBean.getFilenm());
				fBean.setOriginfilenm(sBean.getOriginfilenm());
				fBean.setFilesize(sBean.getFilesize());
				fBean.setExt(sBean.getExt());
				fBean.setOrd(sBean.getOrd());
				result += addReqFormExamFile(con, "", sBean.getReqformno(), sBean.getFormseq(), sBean.getExamseq(), fBean);
			}
			
			con.commit();
		} catch (Exception e) {
			try{
				con.rollback();
			} catch(Exception ex){
				logger.error("ERROR",ex);
//				throw ex;
			}
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt);
			 throw e;
	     } finally {	
//	    	 try{ 
//		 		con.setAutoCommit(true);
//	    	 } catch (Exception e){
//				logger.error("ERROR",e);
////				throw e;
//	    	 }
			ConnectionManager.close(con,pstmt);	   
	     }	
		return result;
	}
	
	/**
	 * 임시 마스터에 값이 있는지 확인(Temp)
	 */
	public boolean existCheck(int reqformno) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) FROM OUT_REQFORMMST WHERE REQFORMNO = ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, reqformno);			
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				if(rs.getInt(1) > 0){
					result = true;
				}
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
	 * 마스터 UPDATE
	 * gbn : TEMP(REQFORMMST), NULL(REQFORMMST)
	 */
	private int updateMst(Connection con, FrmBean fbean) throws Exception{
		PreparedStatement pstmt = null;
		int result = 0;		
		String tb = "";
		String kfield = "";
		
		tb = "OUT_REQFORMMST";
		kfield = "REQFORMNO";	
		
		StringBuffer updateQuery = new StringBuffer();
       	
	 	updateQuery.append("UPDATE "+tb+" SET TITLE = ?,     STRDT = ?,     ENDDT = ?,    COLDEPTCD = ?, COLDEPTNM = ?, COLDEPTTEL = ?, ");
	 	updateQuery.append("	   CHRGUSRID = ?, CHRGUSRNM = ?, SUMMARY = ?,  RANGE = ?, IMGPREVIEW = ?, DUPLICHECK = ?, LIMITCOUNT = ?, ");
	 	updateQuery.append("       SANCNEED = ?, BASICTYPE = ?, HEADCONT = ?,  TAILCONT = ?, UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), UPTUSRID = ? ");	 	
	 	updateQuery.append(" WHERE "+kfield+" = ? ");
	 	//logger.info(updateQuery);
	 	pstmt = con.prepareStatement(updateQuery.toString());						
		pstmt.setString(1, fbean.getTitle());
		pstmt.setString(2, fbean.getStrdt());
		pstmt.setString(3, fbean.getEnddt());
		pstmt.setString(4, fbean.getColdeptcd());
		pstmt.setString(5, fbean.getColdeptnm());
		pstmt.setString(6, fbean.getColtel());
		pstmt.setString(7, fbean.getChrgusrid());
		pstmt.setString(8, fbean.getChrgusrnm());
		pstmt.setString(9, fbean.getSummary());
		pstmt.setString(10, ( "1".equals(fbean.getRange()) ) ? fbean.getRange() : fbean.getRangedetail());
		pstmt.setString(11, fbean.getImgpreview());
		pstmt.setString(12, fbean.getDuplicheck());
		pstmt.setInt(13, fbean.getLimitcount());	
		pstmt.setString(14, fbean.getSancneed());
		pstmt.setString(15, fbean.getBasictype());
		pstmt.setString(16, fbean.getHeadcont());
		pstmt.setString(17, fbean.getTailcont());
		pstmt.setString(18, fbean.getUptusrid());
		pstmt.setInt(19, fbean.getReqformno());		
		
		result = pstmt.executeUpdate();
		ConnectionManager.close(pstmt);
		
		return result;
	}
	
	/**
	 * 문항 저장
	 * gbn : 1(REQFORMMST), 2(REQFORMMST)
	 */
	private int insertArticleAll(Connection con, List articeList) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		String tb = "";
		String kfield = "";
		
		tb = "OUT_REQFORMSUB";
		kfield = "REQFORMNO";
			
		
		if(articeList != null){
			for(int i=0;i<articeList.size();i++){
				ArticleBean abean = (ArticleBean)articeList.get(i);
				
				StringBuffer insertQuery = new StringBuffer();
				insertQuery.append("INSERT INTO "+tb+" ("+kfield+", FORMSEQ, FORMTITLE, REQUIRE, FORMTYPE, ");
				insertQuery.append("                    SECURITY, HELPEXP, EXAMTYPE, EX_FRSQ, EX_EXSQ) ");
				insertQuery.append("VALUES(?,?,?,?,?,  ?,?,?, NVL(?, 0), NVL(?, 0) )");
				
				pstmt = con.prepareStatement(insertQuery.toString());		
				
				pstmt.setInt(1, abean.getReqformno());
				pstmt.setInt(2, abean.getFormseq());
				pstmt.setString(3, abean.getFormtitle());
				pstmt.setString(4, abean.getRequire());
				pstmt.setString(5, abean.getFormtype());
				pstmt.setString(6, abean.getSecurity());
				pstmt.setString(7, abean.getHelpexp());
				pstmt.setString(8, abean.getExamtype());
				pstmt.setInt(9, abean.getEx_frsq());
				pstmt.setString(10, abean.getEx_exsq());
				
				result += pstmt.executeUpdate();				
				ConnectionManager.close(pstmt);
				
				//보기 저장
				result += insertSampleAll(con, abean.getSampleList(), abean.getFormseq());
			}
		}
		
		return result;
	}
	
	/**
	 * 보기 저장
	 * gbn : TEMP(REQFORMMST), NULL(REQFORMMST)
	 */
	private int insertSampleAll(Connection con, List sampleList, int formseq) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		String tb = "";
		String kfield = "";
		
		tb = "OUT_REQFORMEXAM";
		kfield = "REQFORMNO";		
		
		if(sampleList != null){
			for(int i=0;i<sampleList.size();i++){
				SampleBean sbean = (SampleBean)sampleList.get(i);
				
				if ( "".equals(sbean.getExamcont().trim()) ) continue;
				
				StringBuffer insertQuery = new StringBuffer();
				insertQuery.append("INSERT INTO "+tb+" ("+kfield+", FORMSEQ, EXAMSEQ, EXAMCONT) ");
				insertQuery.append("VALUES(?,?,?,?)");
				
				pstmt = con.prepareStatement(insertQuery.toString());	
				
				pstmt.setInt(1, sbean.getReqformno());
				pstmt.setInt(2, formseq);
				pstmt.setInt(3, i+1);
				pstmt.setString(4, sbean.getExamcont());			
				
				result += pstmt.executeUpdate();
				ConnectionManager.close(pstmt);
			}
		}
		
		return result;
	}
	
////////////////////////////////////////////////////////////
////////////////////// 설문조사 DAO 시작 //////////////////////
////////////////////////////////////////////////////////////	
	/**
	 * 설문문항첨부파일 가져오기
	 * @param conn
	 * @param sessionId
	 * @param rchno
	 * @param formseq
	 * @return
	 * @throws Exception
	 */
	public List getRchSubFile(Connection conn, String sessionId, int rchno) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( rchno == 0 ) {
				sql.append("SELECT SESSIONID, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM OUT_RCHSUBFILE \n");
				sql.append("WHERE SESSIONID LIKE ? \n");
			} else {
				sql.append("SELECT RCHNO, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM OUT_RCHSUBFILE \n");
				sql.append("WHERE RCHNO=? \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( rchno == 0 ) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, rchno);
			}
			
			rs = pstmt.executeQuery();
			
			while ( rs.next() == true ) {
				ResearchSubBean rchSubBean = new ResearchSubBean();
				
				rchSubBean.setSessionId(sessionId);
				rchSubBean.setRchno(rchno);
				rchSubBean.setFormseq(rs.getInt("FORMSEQ"));
				rchSubBean.setFileseq(rs.getInt("FILESEQ"));
				rchSubBean.setFilenm(rs.getString("FILENM"));
				rchSubBean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				rchSubBean.setFilesize(rs.getInt("FILESIZE"));
				rchSubBean.setExt(rs.getString("EXT"));				
				rchSubBean.setOrd(rs.getInt("ORD"));				
				
				if ( result == null ) {
					result = new ArrayList();
				}
				result.add(rchSubBean);
			}
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt, rs);
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt, rs);
		}
		
		return result;
	}

	/**
	 * 설문문항보기첨부파일 가져오기
	 * @param conn
	 * @param sessionId
	 * @param rchno
	 * @param formseq
	 * @param examseq
	 * @return
	 * @throws Exception
	 */
	public List getRchExamFile(Connection conn, String sessionId, int rchno, int formseq) throws Exception {
	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( rchno == 0 ) {
				sql.append("SELECT SESSIONID, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM OUT_RCHEXAMFILE \n");
				sql.append("WHERE SESSIONID LIKE ? \n");
			} else {
				sql.append("SELECT RCHNO, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM OUT_RCHEXAMFILE \n");
				sql.append("WHERE RCHNO=? \n");
			}
			if ( formseq != 0 ) {
				sql.append("AND FORMSEQ=? \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( rchno == 0 ) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, rchno);
			}
			if ( formseq != 0 ) {
				pstmt.setInt(2, formseq);
			}
			
			rs = pstmt.executeQuery();
			
			while ( rs.next() == true ) {
				ResearchExamBean rchExamBean = new ResearchExamBean();
				
				rchExamBean.setSessionId(sessionId);
				rchExamBean.setRchno(rchno);
				rchExamBean.setFormseq(rs.getInt("FORMSEQ"));
				rchExamBean.setExamseq(rs.getInt("EXAMSEQ"));
				rchExamBean.setFileseq(rs.getInt("FILESEQ"));
				rchExamBean.setFilenm(rs.getString("FILENM"));
				rchExamBean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				rchExamBean.setFilesize(rs.getInt("FILESIZE"));
				rchExamBean.setExt(rs.getString("EXT"));				
				rchExamBean.setOrd(rs.getInt("ORD"));
				
				if ( result == null ) {
					result = new ArrayList();
				}
				result.add(rchExamBean);
			}
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt, rs);
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 보기파일 삭제 후 순서정렬(1,3,5->1,2,3)
	 * @param conn
	 * @param sessionId
	 * @param rchno
	 * @param formseq
	 * @param examseq
	 * @return
	 * @throws Exception
	 */
	public int setOrderRchExamFile(Connection conn, String sessionId, int rchno, int formseq) throws Exception {
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( rchno == 0 ) {
				sql.append("UPDATE OUT_RCHEXAMFILE R \n");
				sql.append("SET R.EXAMSEQ = (SELECT NEWEXAMSEQ \n");
				sql.append("                 FROM (SELECT ROWNUM NEWEXAMSEQ, EXAMSEQ \n");
				sql.append("                       FROM (SELECT SESSIONID, FORMSEQ, EXAMSEQ \n");
				sql.append("                             FROM OUT_RCHEXAMFILE \n");
				sql.append("                             WHERE SESSIONID LIKE ? \n");
				sql.append("                             AND FORMSEQ = ? \n");
				sql.append("                             ORDER BY EXAMSEQ)) \n");
				sql.append("                 WHERE EXAMSEQ = R.EXAMSEQ) \n");
				sql.append("WHERE SESSIONID LIKE ? \n");
				sql.append("AND FORMSEQ = ? \n");
				sql.append("AND EXAMSEQ NOT IN (SELECT EXAMSEQ \n");
				sql.append("                    FROM OUT_RCHEXAM \n");
				sql.append("                    WHERE SESSIONID LIKE ? \n"); 
				sql.append("                    AND FORMSEQ = ?) \n");
			} else {
				sql.append("UPDATE OUT_RCHEXAMFILE R \n");
				sql.append("SET R.EXAMSEQ = (SELECT NEWEXAMSEQ \n");
				sql.append("                 FROM (SELECT ROWNUM NEWEXAMSEQ, EXAMSEQ \n");
				sql.append("                       FROM (SELECT RCHNO, FORMSEQ, EXAMSEQ \n");
				sql.append("                             FROM OUT_RCHEXAMFILE \n");
				sql.append("                             WHERE RCHNO = ? \n");
				sql.append("                             AND FORMSEQ = ? \n");
				sql.append("                             ORDER BY EXAMSEQ)) \n");
				sql.append("                 WHERE EXAMSEQ = R.EXAMSEQ) \n");
				sql.append("WHERE RCHNO = ? \n");
				sql.append("AND FORMSEQ = ? \n");
				sql.append("AND EXAMSEQ NOT IN (SELECT EXAMSEQ \n");
				sql.append("                    FROM OUT_RCHEXAM \n");
				sql.append("                    WHERE RCHNO = ? \n"); 
				sql.append("                    AND FORMSEQ = ?) \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( rchno == 0 ) {
				pstmt.setString(1, sessionId);
				pstmt.setInt(2, formseq);
				pstmt.setString(3, sessionId);
				pstmt.setInt(4, formseq);
				pstmt.setString(5, sessionId);
				pstmt.setInt(6, formseq);
			} else {
				pstmt.setInt(1, rchno);
				pstmt.setInt(2, formseq);
				pstmt.setInt(3, rchno);
				pstmt.setInt(4, formseq);
				pstmt.setInt(5, rchno);
				pstmt.setInt(6, formseq);
			}
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt);
		}
		
		return result;
	}
	
	/**
	 * 사용하지않는 설문문항보기첨부파일 가져오기
	 * @param conn
	 * @param sessionId
	 * @param rchno
	 * @param formseq
	 * @param examseq
	 * @return
	 * @throws Exception
	 */
	public List getRchExamUnusedFile(Connection conn, String sessionId, int rchno, int formseq) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( rchno == 0 ) {
				sql.append("SELECT SESSIONID, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM OUT_RCHEXAMFILE R \n");
				sql.append("WHERE SESSIONID LIKE ? \n");
				sql.append("AND FORMSEQ=? \n");
				sql.append("AND EXAMSEQ NOT IN (SELECT EXAMSEQ \n");
				sql.append("                    FROM  OUT_RCHEXAM \n");
				sql.append("                    WHERE SESSIONID LIKE R.SESSIONID \n");
				sql.append("                    AND FORMSEQ=R.FORMSEQ) \n");
			} else {
				sql.append("SELECT RCHNO, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM OUT_RCHEXAMFILE R \n");
				sql.append("WHERE RCHNO=? \n");
				sql.append("AND FORMSEQ=? \n");
				sql.append("AND EXAMSEQ NOT IN (SELECT EXAMSEQ \n");
				sql.append("                    FROM  OUT_RCHEXAM \n");
				sql.append("                    WHERE RCHNO=R.RCHNO \n");
				sql.append("                    AND FORMSEQ=R.FORMSEQ) \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( rchno == 0 ) {
				pstmt.setString(1, sessionId);
				pstmt.setInt(2, formseq);
			} else {
				pstmt.setInt(1, rchno);
				pstmt.setInt(2, formseq);
			}
			
			rs = pstmt.executeQuery();
			
			while ( rs.next() == true ) {
				ResearchExamBean rchExamBean = new ResearchExamBean();
				
				rchExamBean.setSessionId(sessionId);
				rchExamBean.setRchno(rchno);
				rchExamBean.setFormseq(rs.getInt("FORMSEQ"));
				rchExamBean.setExamseq(rs.getInt("EXAMSEQ"));
				rchExamBean.setFileseq(rs.getInt("FILESEQ"));
				rchExamBean.setFilenm(rs.getString("FILENM"));
				rchExamBean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				rchExamBean.setFilesize(rs.getInt("FILESIZE"));
				rchExamBean.setExt(rs.getString("EXT"));				
				rchExamBean.setOrd(rs.getInt("ORD"));
				
				if ( result == null ) {
					result = new ArrayList();
				}
				result.add(rchExamBean);
			}
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt, rs);
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 설문문항첨부파일 가져오기
	 * @param conn
	 * @param sessionId
	 * @param rchno
	 * @param formseq
	 * @return
	 * @throws Exception
	 */
	public ResearchSubBean getRchSubFile(Connection conn, String sessionId, int rchno, int formseq) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResearchSubBean result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( rchno == 0 ) {
				sql.append("SELECT SESSIONID, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM OUT_RCHSUBFILE \n");
				sql.append("WHERE SESSIONID LIKE ? \n");
				sql.append("AND FORMSEQ=? \n");
			} else {
				sql.append("SELECT RCHNO, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM OUT_RCHSUBFILE \n");
				sql.append("WHERE RCHNO=? \n");
				sql.append("AND FORMSEQ=? \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( rchno == 0 ) {
				pstmt.setString(1, sessionId);
				pstmt.setInt(2, formseq);
			} else {
				pstmt.setInt(1, rchno);
				pstmt.setInt(2, formseq);
			}
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() == true ) {
				result = new ResearchSubBean();
				
				result.setSessionId(sessionId);
				result.setRchno(rchno);
				result.setFormseq(rs.getInt("FORMSEQ"));
				result.setFileseq(rs.getInt("FILESEQ"));
				result.setFilenm(rs.getString("FILENM"));
				result.setOriginfilenm(rs.getString("ORIGINFILENM"));
				result.setFilesize(rs.getInt("FILESIZE"));
				result.setExt(rs.getString("EXT"));				
				result.setOrd(rs.getInt("ORD"));
			}
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt, rs);
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 설문문항보기첨부파일 가져오기
	 * @param conn
	 * @param sessionId
	 * @param rchno
	 * @param formseq
	 * @param examseq
	 * @return
	 * @throws Exception
	 */
	public ResearchExamBean getRchExamFile(Connection conn, String sessionId, int rchno, int formseq, int examseq) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResearchExamBean result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( rchno == 0 ) {
				sql.append("SELECT SESSIONID, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM OUT_RCHEXAMFILE \n");
				sql.append("WHERE SESSIONID LIKE ? \n");
				sql.append("AND FORMSEQ=? \n");
				sql.append("AND EXAMSEQ=? \n");
			} else {
				sql.append("SELECT RCHNO, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM OUT_RCHEXAMFILE \n");
				sql.append("WHERE RCHNO=? \n");
				sql.append("AND FORMSEQ=? \n");
				sql.append("AND EXAMSEQ=? \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( rchno == 0 ) {
				pstmt.setString(1, sessionId);
				pstmt.setInt(2, formseq);
				pstmt.setInt(3, examseq);
			} else {
				pstmt.setInt(1, rchno);
				pstmt.setInt(2, formseq);
				pstmt.setInt(3, examseq);
			}
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() == true ) {
				result = new ResearchExamBean();
				
				result.setSessionId(sessionId);
				result.setRchno(rchno);
				result.setFormseq(rs.getInt("FORMSEQ"));
				result.setExamseq(rs.getInt("EXAMSEQ"));
				result.setFileseq(rs.getInt("FILESEQ"));
				result.setFilenm(rs.getString("FILENM"));
				result.setOriginfilenm(rs.getString("ORIGINFILENM"));
				result.setFilesize(rs.getInt("FILESIZE"));
				result.setExt(rs.getString("EXT"));				
				result.setOrd(rs.getInt("ORD"));
			}
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt, rs);
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 설문문항첨부파일 추가
	 * @param conn
	 * @param sessionId
	 * @param rchno
	 * @param formseq
	 * @param fileBean
	 * @return
	 * @throws Exception
	 */
	public int addRchSubFile(Connection conn, String sessionId, int rchno, int formseq, FileBean fileBean) throws Exception {
		//logger.info("addRchSubFile");
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( rchno == 0 ) {
				sql.append("INSERT INTO \n");
				sql.append("OUT_RCHSUBFILE(SESSIONID, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD) \n");
				sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?) \n");
			} else {				
				sql.append("INSERT INTO \n");
				sql.append("OUT_RCHSUBFILE(RCHNO, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD) \n");
				sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?) \n");
			}
			//logger.info(sql);
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( rchno == 0 ) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, rchno);
			}
			pstmt.setInt(2, formseq);
			pstmt.setInt(3, fileBean.getFileseq());
			pstmt.setString(4, fileBean.getFilenm());
			pstmt.setString(5, fileBean.getOriginfilenm());
			pstmt.setInt(6, fileBean.getFilesize());
			pstmt.setString(7, fileBean.getExt());
			pstmt.setInt(8, fileBean.getFileseq());
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt);
		}
		
		return result;
	}
	
	/**
	 * 설문문항보기첨부파일 추가
	 * @param conn
	 * @param sessionId
	 * @param rchno
	 * @param formseq
	 * @param examseq
	 * @param fileBean
	 * @return
	 * @throws Exception
	 */
	public int addRchExamFile(Connection conn, String sessionId, int rchno, int formseq, int examseq, FileBean fileBean) throws Exception {
		//logger.info("addRchExamFile start");
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( rchno == 0 ) {
				sql.append("INSERT INTO \n");
				sql.append("OUT_RCHEXAMFILE(SESSIONID, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD) \n");
				sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) \n");
			} else {
				sql.append("INSERT INTO \n");
				sql.append("OUT_RCHEXAMFILE(RCHNO, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD) \n");
				sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) \n");
			}
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( rchno == 0 ) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, rchno);
			}
			pstmt.setInt(2, formseq);
			pstmt.setInt(3, examseq);
			pstmt.setInt(4, fileBean.getFileseq());
			pstmt.setString(5, fileBean.getFilenm());
			pstmt.setString(6, fileBean.getOriginfilenm());
			pstmt.setInt(7, fileBean.getFilesize());
			pstmt.setString(8, fileBean.getExt());
			pstmt.setInt(9, fileBean.getFileseq());
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error("addRchExamFile ERROR");
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt);
		}
		//logger.info("addRchExamFile end");
		return result;
	}
	
	/**
	 * 설문문항첨부파일 삭제
	 * @param conn
	 * @param sessionId
	 * @param rchno
	 * @param formseq
	 * @param fileseq
	 * @return
	 * @throws Exception
	 */
	public int delRchSubFile(Connection conn, String sessionId, int rchno, int formseq, int fileseq) throws Exception {

		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( rchno == 0 ) {
				sql.append("DELETE FROM OUT_RCHSUBFILE \n");
				sql.append("WHERE SESSIONID LIKE ? AND FORMSEQ=? AND FILESEQ=? \n");
			} else {
				sql.append("DELETE FROM OUT_RCHSUBFILE \n");
				sql.append("WHERE RCHNO=? AND FORMSEQ=? AND FILESEQ=? \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( rchno == 0 ) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, rchno);
			}
			pstmt.setInt(2, formseq);
			pstmt.setInt(3, fileseq);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt);
		}
		
		return result;
	}
	
	/**
	 * 설문문항보기첨부파일 삭제
	 * @param conn
	 * @param sessionId
	 * @param rchno
	 * @param formseq
	 * @param examseq
	 * @param fileseq
	 * @return
	 * @throws Exception
	 */
	public int delRchExamFile(Connection conn, String sessionId, int rchno, int formseq, int examseq, int fileseq) throws Exception {
		
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( rchno == 0 ) {
				sql.append("DELETE FROM OUT_RCHEXAMFILE \n");
				sql.append("WHERE SESSIONID LIKE ? AND FORMSEQ=? AND EXAMSEQ=? AND FILESEQ=? \n");
			} else {
				sql.append("DELETE FROM OUT_RCHEXAMFILE \n");
				sql.append("WHERE RCHNO=? AND FORMSEQ=? AND EXAMSEQ=? AND FILESEQ=? \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( rchno == 0 ) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, rchno);
			}
			pstmt.setInt(2, formseq);
			pstmt.setInt(3, examseq);
			pstmt.setInt(4, fileseq);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt);
		}
		
		return result;
	}	
	
	/**
	 * 내설문목록
	 * @param usrid
	 * @param sch_title
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List getResearchMyList(String usrid, String deptcd, String sch_title, int start, int end) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List reserchList = null;
		int bindPos = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("\n SELECT (CNT-SEQ+1) BUNHO, SEQ, RCHNO, TITLE, RANGE, STRDT, ENDDT, TDAY		");
			selectQuery.append("\n   FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* 									");
			selectQuery.append("\n           FROM (SELECT ROWNUM SEQ, A2.* 										");
			selectQuery.append("\n		             FROM (SELECT A.RCHNO, A.TITLE, A.RANGE, REPLACE(SUBSTR(STRDT,6),'-','/') STRDT,  " +
							   "								  REPLACE(SUBSTR(ENDDT,6),'-','/') ENDDT, TRUNC(TO_DATE(ENDDT,'YYYY-MM-DD')-SYSDATE)TDAY ");
			selectQuery.append("\n                           FROM OUT_RCHMST A 										");
			selectQuery.append("\n                          WHERE A.CHRGUSRID = ? 										");
			selectQuery.append("\n                            AND A.COLDEPTCD = ? 										");
			if(!"".equals(sch_title)||sch_title != null){
				selectQuery.append("\n                        AND A.TITLE LIKE  ?										");
			}

			selectQuery.append("\n                          ORDER BY A.CRTDT DESC) A2) A1) 									");
			selectQuery.append("\n  WHERE SEQ BETWEEN ? AND ? 													");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(++bindPos, usrid);
			pstmt.setString(++bindPos, deptcd);
			
			if(!sch_title.equals("")||sch_title!= null){
				pstmt.setString(++bindPos, "%"+ sch_title +"%");
			}
			pstmt.setInt(++bindPos, start);
			pstmt.setInt(++bindPos, end);
									
			rs = pstmt.executeQuery();
			
			reserchList = new ArrayList();
			
			while (rs.next()) {
				ResearchBean Bean = new ResearchBean();
				
				Bean.setSeqno(rs.getInt("BUNHO"));
				Bean.setRchno(rs.getInt("RCHNO"));
				Bean.setTitle(rs.getString("TITLE"));
				Bean.setRange(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 1));
				Bean.setRangedetail(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 2));
				Bean.setStrdt(rs.getString("STRDT"));
				Bean.setEnddt(rs.getString("ENDDT"));
				Bean.setTday(rs.getInt("TDAY"));
				
				reserchList.add(Bean);
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return reserchList;
	}
	
	/**
	 * 내설문목록 건
	 * @param usrid
	 * @param sch_title
	 * @return
	 * @throws Exception
	 */
	public int getResearchMyTotCnt(String usrid, String deptcd, String sch_title, String mode) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int totalCount = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("\n SELECT COUNT(CHRGUSRID) 		");
			selectQuery.append("\n   FROM OUT_RCHMST 				");
			selectQuery.append("\n  WHERE CHRGUSRID = ? 		");
			selectQuery.append("\n    AND COLDEPTCD = ? 		");
			
			if("main".equals(mode)){
				selectQuery.append("\n  AND TO_CHAR(SYSDATE,'YYYY-MM-DD')BETWEEN STRDT AND ENDDT ");
			}
			
			if(!sch_title.equals("")||sch_title!=null){
				selectQuery.append("\n  AND TITLE LIKE  ?		");
			}

			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setString(1, usrid);
			pstmt.setString(2, deptcd);
			if(!sch_title.equals("")||sch_title!=null){
				pstmt.setString(3, "%"+ sch_title +"%");
			}			
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				totalCount = rs.getInt(1);
			}

		 } catch (SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return totalCount;
	}
	
	/*
	 * 설문 답변자 목록 가져오기
	 */
	public List getRchAnsusrList(int rchno) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        List result = null;

		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("\n SELECT RCHNO, ANSUSRSEQ, CRTUSRID, NVL(CRTUSRNM, '익명' || ANSUSRSEQ) CRTUSRNM ");
			selectQuery.append("\n FROM OUT_RCHANS ");
			selectQuery.append("\n WHERE RCHNO = ? ");
			selectQuery.append("\n GROUP BY RCHNO, ANSUSRSEQ, CRTUSRID, CRTUSRNM ");
			
			con = ConnectionManager.getConnection();
			pstmt =	con.prepareStatement(selectQuery.toString());
			
			pstmt.setInt(1, rchno);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				if (result == null) {
					result = new ArrayList();
				}
				
				ResearchBean rbean = new ResearchBean();
				rbean.setRchno(rs.getInt("RCHNO"));
				rbean.setAnsusrseq(rs.getInt("ANSUSRSEQ"));
				rbean.setCrtusrid(rs.getString("CRTUSRID"));
				rbean.setCrtusrnm(rs.getString("CRTUSRNM"));
				
				result.add(rbean);
			}			

		 } catch (SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(con,pstmt,rs);
	     }
		return result;
	}
	
	/**
	 * 설문미리보기
	 * @param usrid
	 * @param sch_title
	 * @return
	 * @throws Exception
	 */
	public ResearchBean getRchMst(int rchno, String sessionId) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		ResearchBean Bean = null;
		
		StringBuffer selectQuery = new StringBuffer();
		
		if(rchno == 0){
			selectQuery.append("\n SELECT SESSIONID, TITLE, STRDT, SUBSTR(A.STRDT,1,4)||'년 '||SUBSTR(A.STRDT,6,2)||'월 '||SUBSTR(A.STRDT,9,2)||'일' STRYMD, ");
			selectQuery.append("\n        ENDDT, SUBSTR(A.ENDDT,1,4)||'년 '||SUBSTR(A.ENDDT,6,2)||'월 '||SUBSTR(A.ENDDT,9,2)||'일' ENDYMD, ");
			selectQuery.append("\n        COLDEPTCD, COLDEPTNM, COLDEPTTEL, CHRGUSRID, CHRGUSRNM, SUMMARY, EXHIBIT, OPENTYPE, RANGE, IMGPREVIEW, DUPLICHECK, LIMITCOUNT, ");
			selectQuery.append("\n        GROUPYN, TGTDEPTNM, HEADCONT, TAILCONT, ANSCOUNT, (SELECT DEPT_TEL FROM DEPT WHERE DEPT_ID = A.COLDEPTCD) DEPT_TEL ");
			selectQuery.append("\n   FROM OUT_RCHMST A				");
			selectQuery.append("\n  WHERE SESSIONID LIKE ? 		");
		}else{
			selectQuery.append("\n SELECT RCHNO, TITLE, STRDT, SUBSTR(A.STRDT,1,4)||'년 '||SUBSTR(A.STRDT,6,2)||'월 '||SUBSTR(A.STRDT,9,2)||'일' STRYMD, ");
			selectQuery.append("\n        ENDDT, SUBSTR(A.ENDDT,1,4)||'년 '||SUBSTR(A.ENDDT,6,2)||'월 '||SUBSTR(A.ENDDT,9,2)||'일' ENDYMD, ");
			selectQuery.append("\n        COLDEPTCD, COLDEPTNM, COLDEPTTEL, CHRGUSRID, CHRGUSRNM, SUMMARY, EXHIBIT, OPENTYPE, RANGE, IMGPREVIEW, DUPLICHECK, LIMITCOUNT, ");
			selectQuery.append("\n        GROUPYN, TGTDEPTNM, HEADCONT, TAILCONT, ANSCOUNT, (SELECT DEPT_TEL FROM DEPT WHERE DEPT_ID = A.COLDEPTCD) DEPT_TEL ");
			selectQuery.append("\n   FROM OUT_RCHMST A				");
			selectQuery.append("\n  WHERE RCHNO = ? 		");
		}
		try {
			
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			
			if(rchno == 0){
				pstmt.setString(1, sessionId);
			}else{
				pstmt.setInt(1, rchno);
			}

			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Bean = new ResearchBean();
				if(rchno == 0){
					Bean.setRchno(rchno);
					Bean.setSessionId(rs.getString("SESSIONID"));
				}else{
					Bean.setRchno(rs.getInt("RCHNO"));
					Bean.setSessionId(sessionId);
				}
					
				Bean.setTitle(rs.getString("TITLE"));
				Bean.setStrdt(rs.getString("STRDT"));
				Bean.setStrymd(rs.getString("STRYMD"));
				Bean.setEnddt(rs.getString("ENDDT"));
				Bean.setEndymd(rs.getString("ENDYMD"));
				Bean.setColdeptcd(rs.getString("COLDEPTCD"));
				Bean.setColdeptnm(rs.getString("COLDEPTNM"));
				Bean.setColdepttel(rs.getString("COLDEPTTEL"));
				Bean.setChrgusrid(rs.getString("CHRGUSRID"));
				Bean.setChrgusrnm(rs.getString("CHRGUSRNM"));
				Bean.setSummary(rs.getString("SUMMARY"));	
				Bean.setExhibit(rs.getString("EXHIBIT"));
				Bean.setOpentype(rs.getString("OPENTYPE"));
				Bean.setRange(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 1));
				Bean.setRangedetail(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 2));
				Bean.setImgpreview(rs.getString("IMGPREVIEW"));
				Bean.setDuplicheck(rs.getString("DUPLICHECK"));
				Bean.setLimitcount(rs.getInt("LIMITCOUNT"));
				Bean.setGroupyn(rs.getString("GROUPYN"));
				Bean.setTgtdeptnm(rs.getString("TGTDEPTNM"));
				Bean.setHeadcont(rs.getString("HEADCONT"));
				Bean.setTailcont(rs.getString("TAILCONT"));
				Bean.setTelno(rs.getString("DEPT_TEL"));
				Bean.setAnscount(rs.getInt("ANSCOUNT"));
			}			

		 } catch (SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return Bean;
	}
	
	/**
	 * 설문조사그룹 마스터 가져오기
	 * @param usrid
	 * @param sch_title
	 * @return
	 * @throws Exception
	 */
	public ResearchBean getRchGrpMst(int rchgrpno, String sessionId) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		ResearchBean Bean = null;
		
		StringBuffer selectQuery = new StringBuffer();
		
		if(rchgrpno == 0){
			selectQuery.append("\n SELECT SESSIONID, TITLE, STRDT, SUBSTR(A.STRDT,1,4)||'년 '||SUBSTR(A.STRDT,6,2)||'월 '||SUBSTR(A.STRDT,9,2)||'일' STRYMD, ");
			selectQuery.append("\n        ENDDT, SUBSTR(A.ENDDT,1,4)||'년 '||SUBSTR(A.ENDDT,6,2)||'월 '||SUBSTR(A.ENDDT,9,2)||'일' ENDYMD, ");
			selectQuery.append("\n        COLDEPTCD, COLDEPTNM, COLDEPTTEL, CHRGUSRID, CHRGUSRNM, SUMMARY, EXHIBIT, OPENTYPE, RANGE, IMGPREVIEW, ");
			selectQuery.append("\n        DUPLICHECK, LIMITCOUNT, GROUPYN, RCHNOLIST, TGTDEPTNM, HEADCONT, TAILCONT, ANSCOUNT, ");
			selectQuery.append("\n        (SELECT DEPT_TEL FROM DEPT WHERE DEPT_ID = A.COLDEPTCD) DEPT_TEL ");
			selectQuery.append("\n   FROM OUT_RCHGRPMST_TEMP A				");
			selectQuery.append("\n  WHERE SESSIONID LIKE ? 		");
		}else{
			selectQuery.append("\n SELECT RCHGRPNO, TITLE, STRDT, SUBSTR(A.STRDT,1,4)||'년 '||SUBSTR(A.STRDT,6,2)||'월 '||SUBSTR(A.STRDT,9,2)||'일' STRYMD, ");
			selectQuery.append("\n        ENDDT, SUBSTR(A.ENDDT,1,4)||'년 '||SUBSTR(A.ENDDT,6,2)||'월 '||SUBSTR(A.ENDDT,9,2)||'일' ENDYMD, ");
			selectQuery.append("\n        COLDEPTCD, COLDEPTNM, COLDEPTTEL, CHRGUSRID, CHRGUSRNM, SUMMARY, EXHIBIT, OPENTYPE, RANGE, IMGPREVIEW, ");
			selectQuery.append("\n        DUPLICHECK, LIMITCOUNT, GROUPYN, RCHNOLIST, TGTDEPTNM, HEADCONT, TAILCONT, ANSCOUNT, ");
			selectQuery.append("\n        (SELECT DEPT_TEL FROM DEPT WHERE DEPT_ID = A.COLDEPTCD) DEPT_TEL ");
			selectQuery.append("\n   FROM OUT_RCHGRPMST A				");
			selectQuery.append("\n  WHERE RCHGRPNO = ? 		");
		}
		try {
			
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			
			if(rchgrpno == 0){
				pstmt.setString(1, sessionId);
			}else{
				pstmt.setInt(1, rchgrpno);
			}

			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Bean = new ResearchBean();
				if(rchgrpno == 0){
					Bean.setRchgrpno(rchgrpno);
					Bean.setSessionId(rs.getString("SESSIONID"));
				}else{
					Bean.setRchgrpno(rs.getInt("RCHGRPNO"));
					Bean.setSessionId(sessionId);
				}
					
				Bean.setTitle(rs.getString("TITLE"));
				Bean.setStrdt(rs.getString("STRDT"));
				Bean.setStrymd(rs.getString("STRYMD"));
				Bean.setEnddt(rs.getString("ENDDT"));
				Bean.setEndymd(rs.getString("ENDYMD"));
				Bean.setColdeptcd(rs.getString("COLDEPTCD"));
				Bean.setColdeptnm(rs.getString("COLDEPTNM"));
				Bean.setColdepttel(rs.getString("COLDEPTTEL"));
				Bean.setChrgusrid(rs.getString("CHRGUSRID"));
				Bean.setChrgusrnm(rs.getString("CHRGUSRNM"));
				Bean.setSummary(rs.getString("SUMMARY"));	
				Bean.setExhibit(rs.getString("EXHIBIT"));
				Bean.setOpentype(rs.getString("OPENTYPE"));
				Bean.setRange(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 1));
				Bean.setRangedetail(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 2));
				Bean.setImgpreview(rs.getString("IMGPREVIEW"));
				Bean.setDuplicheck(rs.getString("DUPLICHECK"));
				Bean.setLimitcount(rs.getInt("LIMITCOUNT"));
				Bean.setGroupyn(rs.getString("GROUPYN"));
				Bean.setRchnolist(rs.getString("RCHNOLIST"));
				Bean.setTgtdeptnm(rs.getString("TGTDEPTNM"));
				Bean.setHeadcont(rs.getString("HEADCONT"));
				Bean.setTailcont(rs.getString("TAILCONT"));
				Bean.setTelno(rs.getString("DEPT_TEL"));
				Bean.setAnscount(rs.getInt("ANSCOUNT"));
			}			

		 } catch (SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return Bean;
	}
	
	/**
	 * 
	 * @param rchno
	 * @return
	 * @throws Exception
	 */
	public List getRchSubList(int rchno, String sessionId, int examcount, String mode) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List rchSubList = null;
		StringBuffer selectQuery = new StringBuffer();
		if(rchno ==0){
			selectQuery.append("\n SELECT A.FORMSEQ, FORMGRP, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE, REQUIRE, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD, NVL(EX_FRSQ, 0) AS EX_FRSQ, NVL(EX_EXSQ, 0) AS EX_EXSQ ");
			selectQuery.append("\n   FROM OUT_RCHSUB A, OUT_RCHSUBFILE B ");
			selectQuery.append("\n  WHERE A.SESSIONID = B.SESSIONID(+) ");
			selectQuery.append("\n    AND A.FORMSEQ = B.FORMSEQ(+) ");
			selectQuery.append("\n    AND A.SESSIONID LIKE ? 		");
			selectQuery.append("\n  ORDER BY FORMSEQ 			");			
		}else{
			selectQuery.append("\n SELECT A.FORMSEQ, FORMGRP, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE, REQUIRE, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD, NVL(EX_FRSQ, 0) AS EX_FRSQ, NVL(EX_EXSQ, 0) AS EX_EXSQ ");
			selectQuery.append("\n   FROM OUT_RCHSUB A, OUT_RCHSUBFILE B ");
			selectQuery.append("\n  WHERE A.RCHNO = B.RCHNO(+) ");
			selectQuery.append("\n    AND A.FORMSEQ = B.FORMSEQ(+) ");
			selectQuery.append("\n    AND A.RCHNO = ? 			");
			selectQuery.append("\n  ORDER BY FORMSEQ 			");	
		}
		try {
		
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if(rchno ==0 ){
				pstmt.setString(1, sessionId);
			}else{
				pstmt.setInt(1, rchno);
			}
			
									
			rs = pstmt.executeQuery();
			
			rchSubList = new ArrayList();
			
			while (rs.next()) {
				ResearchSubBean Bean = new ResearchSubBean();
				int formseq = rs.getInt("FORMSEQ");
				
				Bean.setFormseq(rs.getInt("FORMSEQ"));
				Bean.setFormgrp(rs.getString("FORMGRP"));
				Bean.setFormtitle(rs.getString("FORMTITLE"));
				Bean.setFormtype(rs.getString("FORMTYPE"));
				Bean.setSecurity(rs.getString("SECURITY"));
				Bean.setExamtype(rs.getString("EXAMTYPE"));
				Bean.setRequire(rs.getString("REQUIRE"));
				Bean.setFileseq(rs.getInt("FILESEQ"));
				Bean.setFilenm(rs.getString("FILENM"));
				Bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				Bean.setFilesize(rs.getInt("FILESIZE"));
				Bean.setExt(rs.getString("EXT"));				
				Bean.setOrd(rs.getInt("ORD"));
				Bean.setEx_frsq(rs.getInt("EX_FRSQ"));
			    Bean.setEx_exsq(rs.getString("EX_EXSQ"));
				Bean.setExamList(rchExamList(rchno, sessionId, formseq, examcount, mode));

				rchSubList.add(Bean);		
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return rchSubList;
	}
	
	/**
	 * 
	 * @param rchno
	 * @return
	 * @throws Exception
	 */
	public List getResultSubList(int rchno, String range, RchSearchBean schbean, String sessionId) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List rchSubList = null;
		StringBuffer selectQuery = new StringBuffer();
		
		String[] sch_exam = schbean.getSch_exam(); 
		int cnt=0;

		selectQuery.append("\n SELECT A.FORMSEQ, FORMGRP, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE, REQUIRE, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD, NVL(EX_FRSQ, 0) AS EX_FRSQ, NVL(EX_EXSQ, 0) AS EX_EXSQ ");
		selectQuery.append("\n   FROM OUT_RCHSUB A, OUT_RCHSUBFILE B ");
		selectQuery.append("\n  WHERE A.RCHNO = B.RCHNO(+) ");
		selectQuery.append("\n    AND A.FORMSEQ = B.FORMSEQ(+) ");
		selectQuery.append("\n    AND A.RCHNO = ? 			");
		selectQuery.append("\n  ORDER BY FORMSEQ 			");	

		try {
		
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());

			pstmt.setInt(1, rchno);
						
			rs = pstmt.executeQuery();
			
			rchSubList = new ArrayList();
			
			while (rs.next()) {
				ResearchSubBean Bean = new ResearchSubBean();
				int formseq = rs.getInt("FORMSEQ");
				
				Bean.setFormseq(rs.getInt("FORMSEQ"));
				Bean.setFormgrp(rs.getString("FORMGRP"));
				Bean.setFormtitle(rs.getString("FORMTITLE"));
				Bean.setFormtype(rs.getString("FORMTYPE"));
				Bean.setSecurity(rs.getString("SECURITY"));
				Bean.setRequire(rs.getString("REQUIRE"));
				Bean.setExamtype(rs.getString("EXAMTYPE"));
				if("01".equals(rs.getString("FORMTYPE")) ||"02".equals(rs.getString("FORMTYPE"))){
					if(sch_exam != null){
						for(int i=0; i<sch_exam.length; i++){
							if(cnt == i){
								Bean.setSch_exam(sch_exam[i]);
								break;
							}
						}
					}else{
						Bean.setSch_exam("%");
					}
					cnt++;
					Bean.setExamList(getResultExamList(rchno, range, schbean, formseq));
					if("Y".equals(rs.getString("EXAMTYPE"))){
						Bean.setOtherList(rchOtherList(rchno, range, schbean, formseq));
					}
				}else{
					Bean.setExamList(rchAnsList(rchno, range, schbean, formseq));
				}
				Bean.setFileseq(rs.getInt("FILESEQ"));
				Bean.setFilenm(rs.getString("FILENM"));
				Bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				Bean.setFilesize(rs.getInt("FILESIZE"));
				Bean.setExt(rs.getString("EXT"));				
				Bean.setOrd(rs.getInt("ORD"));
				Bean.setEx_frsq(rs.getInt("EX_FRSQ"));
			    Bean.setEx_exsq(rs.getString("EX_EXSQ"));
				
				rchSubList.add(Bean);
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return rchSubList;
	}	
	
	/**
	 * 단답,복수형 답변결과
	 * @param rchno
	 * @param range
	 * @param schbean
	 * @param formseq
	 * @return
	 * @throws Exception
	 */
	public List getResultExamList(int rchno, String range, RchSearchBean schbean, int formseq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List rchExamList = null;	
		String[] schexam = null;
		String inexamans = "";
		if(schbean.getSch_exam()!=null){
			
			schexam = schbean.getSch_exam();
			
			for(int i=0; i<schexam.length; i++){
				if("".equals(inexamans)){
					if(!"%".equals(schexam[i])){
						inexamans = schexam[i];
					}
				}else{
					if(!"%".equals(schexam[i])){
						inexamans = inexamans + "," + schexam[i];
					}
				}
			}
		}
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("SELECT RA.RCHNO, RA.FORMSEQ, RE.EXAMSEQ, RE.EXAMCONT, ");
		if(inexamans.equals("") == true) {
			selectQuery.append("       SUM(CASE WHEN INSTR(',' || REPLACE(RA.ANSCONT, ',', ',,') || ',', ',' || RE.EXAMSEQ || ',') > 0 THEN 1 ELSE 0 END) CNT ");
		} else {
			String[] selectExam = inexamans.split("[,]");
			String prevFormNumber = "";
			
			selectQuery.append("       SUM(CASE WHEN INSTR(',' || REPLACE(RA.ANSCONT, ',', ',,') || ',', ',' || RE.EXAMSEQ || ',') > 0 THEN ");
			for(int i = 0; i < selectExam.length; i++) {
				int div = 0;
				if ( Integer.parseInt("0" + prevFormNumber) > 9 ) div = 1;
				int selectExamLength = selectExam[i].length();
				String formNumber = selectExam[i].substring(0, selectExamLength / 2 + div);
				String examNumber = selectExam[i].substring(selectExamLength / 2 + div);
				prevFormNumber = formNumber;
				
				selectQuery.append("       CASE WHEN RA.ANSUSRSEQ IN (SELECT ANSUSRSEQ ");
				selectQuery.append("                                 FROM OUT_RCHANS ");
				selectQuery.append("                                 WHERE RCHNO = RA.RCHNO ");
				selectQuery.append("                                 AND FORMSEQ = " + formNumber + " ");
				if(examNumber.equals("0") == true) {
					selectQuery.append("                                 AND OTHER IS NOT NULL) THEN ");
				} else {
					selectQuery.append("                                 AND INSTR(',' || REPLACE(ANSCONT, ',', ',,') || ',', '," + examNumber + ",') > 0) THEN ");
				}
			}
			selectQuery.append("1 ");
			for(int i = 0; i < selectExam.length; i++) {
				selectQuery.append("END ");
			}
			selectQuery.append("ELSE 0 END) CNT ");
		}
		
		if(range.equals("2") == true) {	//외부망
			selectQuery.append("FROM OUT_RCHANS RA, OUT_RCHEXAM RE ");
			selectQuery.append("WHERE RA.RCHNO = RE.RCHNO ");
			selectQuery.append("AND RA.FORMSEQ = RE.FORMSEQ "); 
			selectQuery.append("AND RA.RCHNO = ? ");
			selectQuery.append("AND RA.FORMSEQ = ? ");
		} else {	//내부망
			/*[USR_EXT] 테이블 삭제
			selectQuery.append("FROM OUT_RCHANS RA, OUT_RCHEXAM RE, USR U, USR_EXT UE ");
			selectQuery.append("WHERE RA.RCHNO = RE.RCHNO ");
			selectQuery.append("AND RA.FORMSEQ = RE.FORMSEQ ");
			if("%".equals(schbean.getSch_deptcd()) && "%".equals(schbean.getSch_sex()) && "".equals(schbean.getSch_age())){
				selectQuery.append("AND RA.CRTUSRID = U.USER_ID(+) ");
				selectQuery.append("AND RA.CRTUSRID = UE.USER_ID(+) ");
			} else {
				selectQuery.append("AND RA.CRTUSRID = U.USER_ID ");
				selectQuery.append("AND RA.CRTUSRID = UE.USER_ID ");
			}
			selectQuery.append("AND RA.RCHNO = ? ");
			selectQuery.append("AND RA.FORMSEQ = ? ");
			selectQuery.append("AND NVL(U.DEPT_ID, ' ') LIKE ? ");
			selectQuery.append("AND NVL(UE.SEX, 'M') LIKE ? ");
			if(schbean.getSch_age().equals("") == false) {
				selectQuery.append("AND NVL(UE.AGE, 20) = ? ");
			}
			*/
			selectQuery.append("FROM OUT_RCHANS RA, OUT_RCHEXAM RE, USR U ");
			selectQuery.append("WHERE RA.RCHNO = RE.RCHNO ");
			selectQuery.append("AND RA.FORMSEQ = RE.FORMSEQ ");
			if("%".equals(schbean.getSch_deptcd()) && "%".equals(schbean.getSch_sex()) && "".equals(schbean.getSch_age())){
				selectQuery.append("AND RA.CRTUSRID = U.USER_ID(+) ");
			} else {
				selectQuery.append("AND RA.CRTUSRID = U.USER_ID ");
			}
			selectQuery.append("AND RA.RCHNO = ? ");
			selectQuery.append("AND RA.FORMSEQ = ? ");
			selectQuery.append("AND NVL(U.DEPT_ID, ' ') LIKE ? ");
			selectQuery.append("AND NVL(U.SEX, 'M') LIKE ? ");
			if(schbean.getSch_age().equals("") == false) {
				selectQuery.append("AND NVL(U.AGE, 20) = ? ");
			}
		}
		
		if(schbean.getSch_ansusrseq() > 0) {
			selectQuery.append("AND RA.ANSUSRSEQ = " + schbean.getSch_ansusrseq() + "  ");
		}
		
		selectQuery.append("GROUP BY RA.RCHNO, RA.FORMSEQ, RE.EXAMSEQ, RE.EXAMCONT ");
		selectQuery.append("ORDER BY RA.RCHNO, RA.FORMSEQ, RE.EXAMSEQ ");
		
		try {
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if(range.equals("2") == true) {	//외부망
				pstmt.setInt(1, rchno);
				pstmt.setInt(2, formseq);
			} else {
				pstmt.setInt(1, rchno);
				pstmt.setInt(2, formseq);
				
				pstmt.setString(3, schbean.getSch_deptcd());
				pstmt.setString(4, schbean.getSch_sex());
				
				if(schbean.getSch_age().equals("") == false){
					pstmt.setInt(5, Integer.parseInt(schbean.getSch_age().toString()));
				}	
			}		

			rs = pstmt.executeQuery();
			
			rchExamList = new ArrayList();
			while (rs.next()) {
				ResearchExamBean bean = new ResearchExamBean();
						
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setExamseq(rs.getInt("EXAMSEQ"));
				bean.setExamcont(rs.getString("EXAMCONT"));
				bean.setExamcnt(rs.getInt("CNT"));
				
				ResearchExamBean rchExamBean = getRchExamFile(con, "", rchno, bean.getFormseq(), bean.getExamseq());
				if ( rchExamBean != null ) {
					bean.setFileseq(rchExamBean.getFileseq());
					bean.setFilenm(rchExamBean.getFilenm());
					bean.setOriginfilenm(rchExamBean.getOriginfilenm());
					bean.setFilesize(rchExamBean.getFilesize());
					bean.setExt(rchExamBean.getExt());				
					bean.setOrd(rchExamBean.getOrd());
				}
				
				rchExamList.add(bean);
			}	
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return rchExamList;
	}		
	
	/**
	 * 단답,복수형 기타 답변결과
	 * @param rchno
	 * @param range
	 * @param schbean
	 * @param formseq
	 * @return
	 * @throws Exception
	 */
	public List rchOtherList(int rchno, String range, RchSearchBean schbean, int formseq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List rchOtherList = null;	
		String[] schexam = null;		
		String inexamans = "";
		if(schbean.getSch_exam()!=null){
			
			schexam = schbean.getSch_exam();
			
			for(int i=0; i<schexam.length; i++){
				if("".equals(inexamans)){
					if(!"%".equals(schexam[i])){
						inexamans = schexam[i];
					}
				}else{
					if(!"%".equals(schexam[i])){
						inexamans = inexamans + "," + schexam[i];
					}
				}
			}
		}
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("SELECT DISTINCT RA.RCHNO, RA.ANSUSRSEQ, RA.FORMSEQ, RA.OTHER ");
		
		if(range.equals("2") == true) {	//외부망
			selectQuery.append("FROM OUT_RCHANS RA, OUT_RCHEXAM RE ");
			selectQuery.append("WHERE RA.RCHNO = RE.RCHNO ");
			selectQuery.append("AND RA.FORMSEQ = RE.FORMSEQ "); 
			selectQuery.append("AND RA.RCHNO = ? ");
			selectQuery.append("AND RA.FORMSEQ = ? ");
			selectQuery.append("AND RA.OTHER IS NOT NULL ");
		} else {	//내부망			
			selectQuery.append("FROM OUT_RCHANS RA, OUT_RCHEXAM RE, USR U ");
			selectQuery.append("WHERE RA.RCHNO = RE.RCHNO ");
			selectQuery.append("AND RA.FORMSEQ = RE.FORMSEQ ");
			selectQuery.append("AND RA.OTHER IS NOT NULL ");
			if("%".equals(schbean.getSch_deptcd()) && "%".equals(schbean.getSch_sex()) && "".equals(schbean.getSch_age())){
				selectQuery.append("AND RA.CRTUSRID = U.USER_ID(+) ");
			} else {
				selectQuery.append("AND RA.CRTUSRID = U.USER_ID ");
			}
			selectQuery.append("AND RA.RCHNO = ? ");
			selectQuery.append("AND RA.FORMSEQ = ? ");
			selectQuery.append("AND NVL(U.DEPT_ID, ' ') LIKE ? ");
			selectQuery.append("AND NVL(U.SEX, 'M') LIKE ? ");
			if(schbean.getSch_age().equals("") == false) {
				selectQuery.append("AND NVL(UE.AGE, 20) = ? ");
			}
		}
		if(inexamans.equals("") == false) {
			String[] selectExam = inexamans.split("[,]");
			String prevFormNumber = "";

			for(int i = 0; i < selectExam.length; i++) {
				int div = 0;
				if ( Integer.parseInt("0" + prevFormNumber) > 9 ) div = 1;
				int selectExamLength = selectExam[i].length();
				String formNumber = selectExam[i].substring(0, selectExamLength / 2 + div);
				String examNumber = selectExam[i].substring(selectExamLength / 2 + div);
				prevFormNumber = formNumber;
				
				selectQuery.append("AND RA.ANSUSRSEQ IN (SELECT ANSUSRSEQ ");
				selectQuery.append("                    FROM OUT_RCHANS ");
				selectQuery.append("                    WHERE RCHNO = RA.RCHNO ");
				selectQuery.append("                    AND FORMSEQ = " + formNumber + " ");
				if(examNumber.equals("0") == true) {
					selectQuery.append("                    AND OTHER IS NOT NULL) ");
				} else {
					selectQuery.append("                    AND INSTR(',' || REPLACE(ANSCONT, ',', ',,') || ',', '," + examNumber + ",') > 0) ");
				}
			}
		}

		if(schbean.getSch_ansusrseq() > 0) {
			selectQuery.append("AND RA.ANSUSRSEQ = " + schbean.getSch_ansusrseq() + "  ");
		}
		
		selectQuery.append("ORDER BY RA.RCHNO, RA.FORMSEQ, RA.OTHER ");
		
		try {
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if(range.equals("2") == true) {	//외부망
				pstmt.setInt(1, rchno);
				pstmt.setInt(2, formseq);
			} else {
				pstmt.setInt(1, rchno);
				pstmt.setInt(2, formseq);
				
				pstmt.setString(3, schbean.getSch_deptcd());
				pstmt.setString(4, schbean.getSch_sex());
				
				if(schbean.getSch_age().equals("") == false){
					pstmt.setInt(5, Integer.parseInt(schbean.getSch_age().toString()));
				}	
			}		

			rs = pstmt.executeQuery();
			
			rchOtherList = new ArrayList();
			while(rs.next()) {
				ResearchAnsBean bean = new ResearchAnsBean();
						
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setOther(rs.getString("OTHER"));
				
				rchOtherList.add(bean);
			}						
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return rchOtherList;
	}	
	
	/**
	 * 단문,장문형 답변결과
	 * @param rchno
	 * @param range
	 * @param schbean
	 * @param formseq
	 * @return
	 * @throws Exception
	 */
	public List rchAnsList(int rchno, String range, RchSearchBean schbean, int formseq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List rchAnsList = null;	
		String[] schexam = null;		
		String inexamans = "";
		if(schbean.getSch_exam()!=null){
			
			schexam = schbean.getSch_exam();
			
			for(int i=0; i<schexam.length; i++){
				if("".equals(inexamans)){
					if(!"%".equals(schexam[i])){
						inexamans = schexam[i];
					}
				}else{
					if(!"%".equals(schexam[i])){
						inexamans = inexamans + "," + schexam[i];
					}
				}
			}
		}
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("SELECT DISTINCT RA.RCHNO, RA.ANSUSRSEQ, RA.FORMSEQ, RA.ANSCONT ");
		
		if(range.equals("2") == true) {	//외부망
			selectQuery.append("FROM OUT_RCHANS RA, OUT_RCHEXAM RE ");
			selectQuery.append("WHERE RA.RCHNO = RE.RCHNO(+) ");
			selectQuery.append("AND RA.FORMSEQ = RE.FORMSEQ(+) "); 
			selectQuery.append("AND RA.RCHNO = ? ");
			selectQuery.append("AND RA.FORMSEQ = ? ");
		} else {	//내부망			
			selectQuery.append("FROM OUT_RCHANS RA, OUT_RCHEXAM RE, USR U ");
			selectQuery.append("WHERE RA.RCHNO = RE.RCHNO(+) ");
			selectQuery.append("AND RA.FORMSEQ = RE.FORMSEQ(+) ");
			if("%".equals(schbean.getSch_deptcd()) && "%".equals(schbean.getSch_sex()) && "".equals(schbean.getSch_age())){
				selectQuery.append("AND RA.CRTUSRID = U.USER_ID(+) ");
			} else {
				selectQuery.append("AND RA.CRTUSRID = U.USER_ID ");
			}
			selectQuery.append("AND RA.RCHNO = ? ");
			selectQuery.append("AND RA.FORMSEQ = ? ");
			selectQuery.append("AND NVL(U.DEPT_ID, ' ') LIKE ? ");
			selectQuery.append("AND NVL(U.SEX, 'M') LIKE ? ");
			if(schbean.getSch_age().equals("") == false) {
				selectQuery.append("AND NVL(UE.AGE, 20) = ? ");
			}
		}
		if(inexamans.equals("") == false) {
			String[] selectExam = inexamans.split("[,]");
			String prevFormNumber = "";

			for(int i = 0; i < selectExam.length; i++) {
				int div = 0;
				if ( Integer.parseInt("0" + prevFormNumber) > 9 ) div = 1;
				int selectExamLength = selectExam[i].length();
				String formNumber = selectExam[i].substring(0, selectExamLength / 2 + div);
				String examNumber = selectExam[i].substring(selectExamLength / 2 + div);
				prevFormNumber = formNumber;
				
				selectQuery.append("AND RA.ANSUSRSEQ IN (SELECT ANSUSRSEQ ");
				selectQuery.append("                    FROM OUT_RCHANS ");
				selectQuery.append("                    WHERE RCHNO = RA.RCHNO ");
				selectQuery.append("                    AND FORMSEQ = " + formNumber + " ");
				if(examNumber.equals("0") == true) {
					selectQuery.append("                    AND OTHER IS NOT NULL) ");
				} else {
					selectQuery.append("                    AND INSTR(',' || REPLACE(ANSCONT, ',', ',,') || ',', '," + examNumber + ",') > 0) ");
				}
			}
		}

		if(schbean.getSch_ansusrseq() > 0) {
			selectQuery.append("AND RA.ANSUSRSEQ = " + schbean.getSch_ansusrseq() + "  ");
		}
		
		selectQuery.append("ORDER BY RA.RCHNO, RA.FORMSEQ, RA.ANSCONT ");
		
		try {
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if(range.equals("2") == true) {	//외부망
				pstmt.setInt(1, rchno);
				pstmt.setInt(2, formseq);
			} else {
				pstmt.setInt(1, rchno);
				pstmt.setInt(2, formseq);
				
				pstmt.setString(3, schbean.getSch_deptcd());
				pstmt.setString(4, schbean.getSch_sex());
				
				if(schbean.getSch_age().equals("") == false){
					pstmt.setInt(5, Integer.parseInt(schbean.getSch_age().toString()));
				}	
			}		

			rs = pstmt.executeQuery();
			
			rchAnsList = new ArrayList();
			while (rs.next()) {
				ResearchAnsBean bean = new ResearchAnsBean();
						
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setAnscont(rs.getString("ANSCONT"));
				
				rchAnsList.add(bean);
			}						
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return rchAnsList;
	}
	
	/**
	 * 
	 * @param rchno
	 * @param sessionId
	 * @param formseq
	 * @return
	 * @throws Exception
	 */
	public List rchExamList(int rchno, String sessionId, int formseq, int examcount, String mode) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List rchExamList = null;	
		
		StringBuffer selectQuery = new StringBuffer();
		
		if(rchno ==0){
			selectQuery.append("\n SELECT T1.SESSIONID, T1.FORMSEQ, T1.EXAMSEQ, EXAMCONT,  0 CNT, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD ");
			selectQuery.append("\n   FROM OUT_RCHEXAM T1, OUT_RCHEXAMFILE T2 ");
			selectQuery.append("\n  WHERE T1.SESSIONID = T2.SESSIONID(+) ");
			selectQuery.append("\n    AND T1.FORMSEQ = T2.FORMSEQ(+) ");
			selectQuery.append("\n    AND T1.EXAMSEQ = T2.EXAMSEQ(+) ");
			selectQuery.append("\n    AND T1.SESSIONID LIKE ? 	");
			selectQuery.append("\n    AND T1.FORMSEQ = ? 	");
			selectQuery.append("\n ORDER BY EXAMSEQ			");
			
		}else{
			selectQuery.append("\n SELECT T1.RCHNO, T1.FORMSEQ, T1.EXAMSEQ, T1.EXAMCONT, ");
			selectQuery.append("\n 	      (SELECT COUNT(DISTINCT ANSUSRSEQ) FROM OUT_RCHANS WHERE T1.RCHNO = RCHNO AND T1.FORMSEQ = FORMSEQ AND ANSCONT LIKE '%'||T1.EXAMSEQ||'%' ) CNT, ");
			selectQuery.append("\n        FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD ");
			selectQuery.append("\n   FROM OUT_RCHEXAM T1, OUT_RCHEXAMFILE T2 ");
			selectQuery.append("\n  WHERE T1.RCHNO = T2.RCHNO(+) ");
			selectQuery.append("\n    AND T1.FORMSEQ = T2.FORMSEQ(+) ");
			selectQuery.append("\n    AND T1.EXAMSEQ = T2.EXAMSEQ(+) ");
			selectQuery.append("\n    AND T1.RCHNO = ? 	");
			selectQuery.append("\n    AND T1.FORMSEQ = ? ");
			selectQuery.append("\n ORDER BY EXAMSEQ			");
		}
		
		try {

			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if(rchno == 0){
				pstmt.setString(1, sessionId);
				pstmt.setInt(2, formseq);
			}else{
				pstmt.setInt(1, rchno);
				pstmt.setInt(2, formseq);
			}

			
			rs = pstmt.executeQuery();
			
			rchExamList = new ArrayList();
			int cnt = 0;
			while (rs.next()) {
				ResearchExamBean bean = new ResearchExamBean();
						
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setExamseq(rs.getInt("EXAMSEQ"));
				bean.setExamcont(rs.getString("EXAMCONT"));
				bean.setExamcnt(rs.getInt("CNT"));
				bean.setFileseq(rs.getInt("FILESEQ"));
				bean.setFilenm(rs.getString("FILENM"));
				bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				bean.setFilesize(rs.getInt("FILESIZE"));
				bean.setExt(rs.getString("EXT"));				
				bean.setOrd(rs.getInt("ORD"));
				
				rchExamList.add(bean);
				cnt = cnt + 1;
			}						
			
			if("".equals(mode)){
				//비어있는 보기를 examcount개까지 채운다.
				for(int i=0;i<examcount-cnt;i++){
					ResearchExamBean bean = new ResearchExamBean();
					rchExamList.add(bean);
				}
			}
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return rchExamList;
	}	
	
	
	/**
	 * 
	 * @param rchno
	 * @param sessionId
	 * @param formseq
	 * @return
	 * @throws Exception
	 */
	public int rchAnsCnt(int rchno, int formseq, int examseq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;	
		
		StringBuffer selectQuery = new StringBuffer();
		
		selectQuery.append("\n SELECT COUNT(DISTINCT ANSUSRSEQ) ");
		selectQuery.append("\n   FROM OUT_RCHANS		");
		selectQuery.append("\n  WHERE RCHNO = ? 	");
		selectQuery.append("\n    AND FORMSEQ = ? 	");
		selectQuery.append("\n    AND ANSCONT LIKE '%'||?||'%' ");

		
		try {

			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
			
			pstmt.setInt(1, rchno);
			pstmt.setInt(2, formseq);
			pstmt.setInt(3, examseq);

			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				result = rs.getInt(1);
			}	
			
		} catch(SQLException e) {
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
	 * @param Bean
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public int rchAllSave(ResearchBean Bean, ResearchForm rchForm, ServletContext context, String saveDir) throws Exception {
		Connection con = null;
        int result = 0;
        
		try {
			con = ConnectionManager.getConnection(false);
			
			//임시테이블에 값이 있거나, 정규테이블에 값이 있을때는 업데이트 한다.
			if(checkCnt(Bean.getSessionId())|| Bean.getRchno()> 0 ){
				//마스터 저장
				result = rchUptMst(con, Bean, 0);							
				//문항저장(보기포함)
				result += rchInsSub(con, Bean.getListrch(), Bean.getSessionId(), Bean.getRchno());

				//파일 업로드
				File saveFolder = new File(context.getRealPath(saveDir));
				if(!saveFolder.exists()) saveFolder.mkdirs();
				
				String[] formattitleFileYN = Bean.getFormtitleFileYN();
				String[] examcontFileYN = Bean.getExamcontFileYN();
				String[] formtitle = Bean.getFormtitle();
				String[] examcont = Bean.getExamcont();
				int examcount = Bean.getExamcount();
				
				for ( int i = 0; formtitle != null && i < formtitle.length; i++ ) {
					ResearchSubBean rchSubBean = getRchSubFile(con, Bean.getSessionId(), Bean.getRchno(), i+1);
					
					if ( rchSubBean != null &&
							(formattitleFileYN[i] == null || 
									(Bean.getFormtitleFile()[i] != null && Bean.getFormtitleFile()[i].getFileName().equals("") == false)) ) {
						delRchSubFile(con, Bean.getSessionId(), Bean.getRchno(), i+1, 1);
						
						String delFile = rchSubBean.getFilenm();
						if ( delFile != null && delFile.trim().equals("") == false) {
							FileManager.doFileDelete(context.getRealPath(delFile));
						}
					}
					
					if ( Bean.getFormtitleFile()[i] != null && Bean.getFormtitleFile()[i].getFileName().equals("") == false ) {
						FileBean subFileBean = FileManager.doFileUpload(Bean.getFormtitleFile()[i], context, saveDir);
						
						if( subFileBean != null ) {
							int addResult = 0;
							subFileBean.setFileseq(1);
							addResult = addRchSubFile(con, Bean.getSessionId(), Bean.getRchno(), i+1, subFileBean);
							if ( addResult == 0 ) {
								throw new Exception("첨부실패:DB업데이트");
							}
						} else {
							throw new Exception("첨부실패:파일업로드");
						}
					}
					
					int prevExamcount = 0;
					while ( prevExamcount < rchForm.getExamcontFile().length
							&& rchForm.getExamcontFile()[prevExamcount] != null ) {
						prevExamcount++;
					}
					prevExamcount = prevExamcount / Bean.getFormcount();
					
					if ( examcount >= prevExamcount ) {
						for ( int j = 0; examcont != null && j < prevExamcount; j++ ) {
							ResearchExamBean rchExamBean = getRchExamFile(con, Bean.getSessionId(), Bean.getRchno(), i+1, j+1);
	
							if ( rchExamBean != null &&
									(examcont[i*prevExamcount+j].trim().equals("") == true || examcontFileYN[i*prevExamcount+j] == null || 
											(Bean.getExamcontFile()[i*prevExamcount+j] != null && Bean.getExamcontFile()[i*prevExamcount+j].getFileName().equals("") == false)) ) {
								delRchExamFile(con, Bean.getSessionId(), Bean.getRchno(), i+1, j+1, 1);
								
								String delFile = rchExamBean.getFilenm();
								if ( delFile != null && delFile.trim().equals("") == false) {
									FileManager.doFileDelete(context.getRealPath(delFile));
								}
							}
	
							if ( Bean.getExamcontFile()[i*prevExamcount+j] != null && Bean.getExamcontFile()[i*prevExamcount+j].getFileName().equals("") == false ) {
								FileBean examFileBean = FileManager.doFileUpload(Bean.getExamcontFile()[i*prevExamcount+j], context, saveDir);
								
								if(examFileBean != null) {
									int addResult = 0;
									examFileBean.setFileseq(1);
									addResult = addRchExamFile(con, Bean.getSessionId(), Bean.getRchno(), i+1, j+1, examFileBean);
									if ( addResult == 0 ) {
										throw new Exception("첨부실패:DB업데이트");
									}
								} else {
									throw new Exception("첨부실패:파일업로드");
								}
							}
						}
					
						setOrderRchExamFile(con, Bean.getSessionId(), Bean.getRchno(), i+1);
					}
					
					List rchExamUnusedList = getRchExamUnusedFile(con, Bean.getSessionId(), Bean.getRchno(),  i+1);
					
					for ( int k = 0; rchExamUnusedList != null && k < rchExamUnusedList.size(); k++ ) {
						ResearchExamBean rchExamBean = (ResearchExamBean)rchExamUnusedList.get(k);
						
						if ( rchExamBean != null ) {
							delRchExamFile(con, Bean.getSessionId(), Bean.getRchno(), i+1, rchExamBean.getExamseq(), rchExamBean.getFileseq());
							
							String delFile = rchExamBean.getFilenm();
							if ( delFile != null && delFile.trim().equals("") == false) {
								FileManager.doFileDelete(context.getRealPath(delFile));
							}
						}
					}
				}
				
			} else {
				//마스터 저장 (TEMP)
				result = rchInsMst(con, Bean);
			}							
			con.commit();
		 } catch (Exception e) {
			con.rollback();
			logger.error("ERROR",e);
			ConnectionManager.close(con);
			throw e;
	     } finally {
//	    	 con.setAutoCommit(true);
	    	 ConnectionManager.close(con);
	     }
		return result;
	}	

	
	/**
	 * 
	 * @param sessi
	 * @return
	 * @throws Exception
	 */
	public int rchInsComp(String sessionId, ResearchBean Bean, ServletContext context, String saveDir) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		int rchno = 0;	
		//logger.info("out rchInsComp start");
		try{				
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);

			if(Bean.getMdrchno()==0){
				//신청양식 번호가져오기
				StringBuffer selectQuery1 = new StringBuffer();
				selectQuery1.append("SELECT RCHSEQ.NEXTVAL FROM DUAL ");
	
				pstmt = con.prepareStatement(selectQuery1.toString());
				
				rs = pstmt.executeQuery();
				
				if(rs.next()){
					rchno = rs.getInt(1);
				}
				if (pstmt != null){
					try {
						rs.close();
						pstmt.close();
						pstmt = null;
					} catch(SQLException ignored){ }
				}	
	
				//양식마스터 복사
				StringBuffer insertQuery2 = new StringBuffer();
				insertQuery2.append("\n INSERT INTO OUT_RCHMST ");
				insertQuery2.append("\n SELECT " + rchno + ", TITLE, STRDT, ENDDT, COLDEPTCD, ");
				insertQuery2.append("\n        COLDEPTNM, COLDEPTTEL, CHRGUSRID, CHRGUSRNM, SUMMARY, EXHIBIT, OPENTYPE, RANGE, IMGPREVIEW, DUPLICHECK, LIMITCOUNT, ");
				insertQuery2.append("\n        GROUPYN, TGTDEPTNM, HEADCONT, TAILCONT, ANSCOUNT, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), CRTUSRID, '', '' ");
				insertQuery2.append("\n   FROM OUT_RCHMST ");
				insertQuery2.append("\n  WHERE SESSIONID LIKE ? "); 
				//logger.info(insertQuery2);
				pstmt = con.prepareStatement(insertQuery2.toString());
				pstmt.setString(1, sessionId);
				
				pstmt.executeUpdate();			
				if (pstmt != null){
					try {
						pstmt.close();
						pstmt = null;
					} catch(SQLException ignored){ }
				}
				
//				양식 문항 복사
				StringBuffer selectQuery3 = new StringBuffer();
				selectQuery3.append("\n INSERT INTO OUT_RCHSUB ");
				selectQuery3.append("\n SELECT " + rchno + ", FORMSEQ, FORMGRP, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE, REQUIRE, NVL(EX_FRSQ, 0) AS EX_FRSQ, NVL(EX_EXSQ, 0) AS EX_EXSQ ");
				selectQuery3.append("\n   FROM OUT_RCHSUB ");
				selectQuery3.append("\n  WHERE SESSIONID LIKE ? "); 
				//logger.info(selectQuery3);
				pstmt = con.prepareStatement(selectQuery3.toString());
				pstmt.setString(1, sessionId);
				
				pstmt.executeUpdate();			
				if (pstmt != null){
					try {
						pstmt.close();
						pstmt = null;
					} catch(SQLException ignored){ }
				}	
				
				//양식 보기 복사
				StringBuffer selectQuery4 = new StringBuffer();
				selectQuery4.append("\n INSERT INTO OUT_RCHEXAM ");
				selectQuery4.append("\n SELECT " + rchno + ", FORMSEQ, EXAMSEQ, EXAMCONT ");
				selectQuery4.append("\n   FROM OUT_RCHEXAM ");
				selectQuery4.append("\n  WHERE SESSIONID LIKE ? "); 
				//logger.info(selectQuery4);
				pstmt = con.prepareStatement(selectQuery4.toString());
				pstmt.setString(1, sessionId);
				
				pstmt.executeUpdate();			
				if (pstmt != null){
					try {
						pstmt.close();
						pstmt = null;
					} catch(SQLException ignored){ }
				}							
			} else {
				rchUptMst(con, Bean, Bean.getMdrchno());
				
				rchInsSub(con, Bean.getListrch(), Bean.getSessionId(), Bean.getMdrchno());
				
				StringBuffer delSubQuery = new StringBuffer();
				delSubQuery.append("\n DELETE FROM OUT_RCHDEPT 	");
				delSubQuery.append("\n  WHERE RCHNO = ? 	");
				
				pstmt = con.prepareStatement(delSubQuery.toString());
				pstmt.setInt(1, Bean.getMdrchno());
				
				pstmt.executeUpdate();			
				if (pstmt != null){
					try {
						pstmt.close();
						pstmt = null;
					} catch(SQLException ignored){ }
				}
				
				StringBuffer delSubQuery1 = new StringBuffer();
				delSubQuery1.append("\n DELETE FROM OUT_RCHDEPTLIST 	");
				delSubQuery1.append("\n  WHERE RCHNO = ? 	");
				
				pstmt = con.prepareStatement(delSubQuery1.toString());
				pstmt.setInt(1, Bean.getMdrchno());
				
				pstmt.executeUpdate();			
				if (pstmt != null){
					try {
						pstmt.close();
						pstmt = null;
					} catch(SQLException ignored){ }
				}
				
				rchno = Bean.getMdrchno();
			}
			
			//원본첨부파일 삭제
			delRchSubExamAllFile(con, "", rchno, context);
			
			//첨부파일 복사
			copyRchSubExamFile(con, sessionId, rchno, context, saveDir, "SAVE");
			
			//대상부서지정 복사
			StringBuffer selectQuery5 = new StringBuffer();
			selectQuery5.append("\n INSERT INTO OUT_RCHDEPT ");
			selectQuery5.append("\n SELECT '" + rchno + "', TGTDEPTCD, TGTDEPTNM ");
			selectQuery5.append("\n   FROM OUT_RCHDEPT  ");
			selectQuery5.append("\n  WHERE SESSIONID LIKE ? "); 
			pstmt = con.prepareStatement(selectQuery5.toString());
			pstmt.setString(1, sessionId);
			
			pstmt.executeUpdate();			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}	
			
			//대상부서리스트지정 복사
			StringBuffer selectQuery6 = new StringBuffer();
			selectQuery6.append("\n INSERT INTO OUT_RCHDEPTLIST ");
			selectQuery6.append("\n SELECT '" + rchno + "', SEQ, GRPCD, GRPNM, GRPGBN ");
			selectQuery6.append("\n   FROM OUT_RCHDEPTLIST  ");
			selectQuery6.append("\n  WHERE SESSIONID LIKE ? "); 
			pstmt = con.prepareStatement(selectQuery6.toString());
			pstmt.setString(1, sessionId);
			
			pstmt.executeUpdate();			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}

			con.commit();
		} catch (Exception e) {
			rchno = -1;
			con.rollback();
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
	     } finally {	
//	    	 con.setAutoCommit(true);
	    	 ConnectionManager.close(con,pstmt,rs);	   
	     }	
		//logger.info("out rchInsComp end");
		return rchno;
	}
	
	/**
	 * 설문조사 저장
	 * @param conn
	 * @param Bean
	 * @param rchno
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public int rchInsMst(Connection conn, ResearchBean Bean) throws Exception {
		//logger.info("rchInsMst");
		PreparedStatement pstmt = null;
        int result = 0;
        int bindPos = 0;
        int[] ret = null;
        
		String summary = null;
		String headcont = null;
		String tailcont = null;
		
		if(Bean.getSummary() != null) {
			summary = Bean.getSummary().replaceAll("'", "''");
			summary = new String(summary.getBytes("x-windows-949"), "x-windows-949");
		} else {
			summary = "";
		}
		
		if(Bean.getHeadcont() != null) {
			headcont = Bean.getHeadcont().replaceAll("'", "''");
			headcont = new String(headcont.getBytes("x-windows-949"), "x-windows-949");
		} else {
			headcont = "";
		}
		
		if(Bean.getTailcont() != null) {
			tailcont = Bean.getTailcont().replaceAll("'", "''");
			tailcont = new String(tailcont.getBytes("x-windows-949"), "x-windows-949");
		} else {
			tailcont = "";
		}
		
		String insMstQuery = "";

		insMstQuery = "INSERT INTO OUT_RCHMST" +
		"(		RCHNO,	TITLE,		STRDT,		ENDDT,		COLDEPTCD, " +
        "       COLDEPTNM,	COLDEPTTEL, CHRGUSRID,	CHRGUSRNM,	SUMMARY,	EXHIBIT,	OPENTYPE, " +
        "       RANGE,		IMGPREVIEW, DUPLICHECK,	LIMITCOUNT,	GROUPYN,	TGTDEPTNM,	HEADCONT, " +
        "       TAILCONT,	ANSCOUNT, 	CRTDT,		CRTUSRID) " +
        "VALUES(?,			?,			?,			?,			?, " +
        "       ?,			?,			?,			?,			'" + summary + "', ?,	?, " +
        "       ?,			?,			?,			?,			?,			?,			'"+ headcont+ "', " +
        "		'" + tailcont + "',	0,	TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), ?) ";	
			//logger.info(insMstQuery);
		pstmt = conn.prepareStatement(insMstQuery);
		
		pstmt.setInt(++bindPos, Bean.getRchno());
		pstmt.setString(++bindPos, Bean.getTitle());		//제목
		pstmt.setString(++bindPos, Bean.getStrdt());		//시작일
		pstmt.setString(++bindPos, Bean.getEnddt());		//종료일
		pstmt.setString(++bindPos, Bean.getColdeptcd());	//담당부서코드
		pstmt.setString(++bindPos, Bean.getColdeptnm());	//담당부서명
		pstmt.setString(++bindPos, Bean.getColdepttel());   //담당부서전화
		pstmt.setString(++bindPos, Bean.getChrgusrid());	//담당자ID
		pstmt.setString(++bindPos, Bean.getChrgusrnm());	//담당자명
		pstmt.setString(++bindPos, Bean.getExhibit());		//결과공개여부
		pstmt.setString(++bindPos, Bean.getOpentype());		//조사방법
		pstmt.setString(++bindPos, ( "1".equals(Bean.getRange()) ) ? Bean.getRange() : Bean.getRangedetail());		//범위
		pstmt.setString(++bindPos, Bean.getImgpreview());	//첨부그림미리보기
		pstmt.setString(++bindPos, Bean.getDuplicheck());	//중복답변체크
		pstmt.setInt(++bindPos, Bean.getLimitcount());	//목표응답수
		pstmt.setString(++bindPos, Bean.getGroupyn());	//설문그룹여부
		pstmt.setString(++bindPos, Bean.getTgtdeptnm());	//취합담당단위명
		pstmt.setString(++bindPos, Bean.getChrgusrid());	//생성자ID

		result = pstmt.executeUpdate();
		
		if (pstmt != null){
			try {
				pstmt.close();
				pstmt = null;
			} catch(SQLException ignored){ }
		}
			
		StringBuffer insSubQuery = new StringBuffer();
			
		insSubQuery.append("\n INSERT INTO OUT_RCHSUB(RCHNO, FORMSEQ, FORMGRP, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE, REQUIRE, EX_FRSQ, EX_EXSQ) ");
		insSubQuery.append("\n VALUES (?, ?, '', '', '1', 'N', 'N', 'Y', 0, 0) ");	
		//logger.info(insSubQuery);
		pstmt = conn.prepareStatement(insSubQuery.toString());
		
		for(int i=0;i<Bean.getFormcount();i++){

			pstmt.setInt(1, Bean.getRchno());
			pstmt.setInt(2, i+1);
			
			pstmt.addBatch();		
		}	   
		
		ret = pstmt.executeBatch();
		result = result + ret.length;	
		
		if (pstmt != null){
			try {
				pstmt.close();
				pstmt = null;
			} catch(SQLException ignored){ }
		}
		
		return result;
	}
	
	/**
	 * 설문조사그룹 저장
	 * @param conn
	 * @param Bean
	 * @param rchno
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public int rchGrpInsMst(Connection conn, ResearchBean Bean) throws Exception {
		PreparedStatement pstmt = null;
        int result = 0;
        int bindPos = 0;
        
		String summary = null;
		String headcont = null;
		String tailcont = null;
		
		if(Bean.getSummary() != null) {
			summary = Bean.getSummary().replaceAll("'", "''");
		} else {
			summary = "";
		}
		
		if(Bean.getHeadcont() != null) {
			headcont = Bean.getHeadcont().replaceAll("'", "''");
		} else {
			headcont = "";
		}
		
		if(Bean.getTailcont() != null) {
			tailcont = Bean.getTailcont().replaceAll("'", "''");
		} else {
			tailcont = "";
		}
		
		String insMstQuery = "";

		insMstQuery = "INSERT INTO OUT_RCHGRPMST" +
		"(		RCHGRPNO,	TITLE,		STRDT,		ENDDT,		COLDEPTCD, " +
        "       COLDEPTNM,	COLDEPTTEL, CHRGUSRID,	CHRGUSRNM,	SUMMARY,	EXHIBIT,	OPENTYPE, " +
        "       RANGE,		IMGPREVIEW, DUPLICHECK,	LIMITCOUNT,	GROUPYN,	RCHNOLIST,	TGTDEPTNM,	HEADCONT, " +
        "       TAILCONT,	ANSCOUNT, 	CRTDT,		CRTUSRID) " +
        "VALUES(?,			?,			?,			?,			?, " +
        "       ?,			?,			?,			?,			'" + summary + "', ?,	?, " +
        "       ?,			?,			?,			?,			?,			?,			?,			'"+ headcont+ "', " +
        "		'" + tailcont + "',	0,	TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), ?) ";	
			
		pstmt = conn.prepareStatement(insMstQuery);
		
		pstmt.setInt(++bindPos, Bean.getRchgrpno());
		pstmt.setString(++bindPos, Bean.getTitle());		//제목
		pstmt.setString(++bindPos, Bean.getStrdt());		//시작일
		pstmt.setString(++bindPos, Bean.getEnddt());		//종료일
		pstmt.setString(++bindPos, Bean.getColdeptcd());	//담당부서코드
		pstmt.setString(++bindPos, Bean.getColdeptnm());	//담당부서명
		pstmt.setString(++bindPos, Bean.getColdepttel());   //담당부서전화
		pstmt.setString(++bindPos, Bean.getChrgusrid());	//담당자ID
		pstmt.setString(++bindPos, Bean.getChrgusrnm());	//담당자명
		pstmt.setString(++bindPos, Bean.getExhibit());		//결과공개여부
		pstmt.setString(++bindPos, Bean.getOpentype());		//조사방법
		pstmt.setString(++bindPos, ( "1".equals(Bean.getRange()) ) ? Bean.getRange() : Bean.getRangedetail());		//범위
		pstmt.setString(++bindPos, Bean.getImgpreview());	//첨부그림미리보기
		pstmt.setString(++bindPos, Bean.getDuplicheck());	//중복답변체크
		pstmt.setInt(++bindPos, Bean.getLimitcount());		//목표응답수
		pstmt.setString(++bindPos, Bean.getGroupyn());		//설문그룹여부
		pstmt.setString(++bindPos, Bean.getRchnolist());		//설문조사목록
		pstmt.setString(++bindPos, Bean.getTgtdeptnm());	//취합담당단위명
		pstmt.setString(++bindPos, Bean.getChrgusrid());	//생성자ID

		result = pstmt.executeUpdate();
		
		if (pstmt != null){
			try {
				pstmt.close();
				pstmt = null;
			} catch(SQLException ignored){ }
		}

		return result;
	}
	
	/**
	 * 
	 * @param conn
	 * @param subList
	 * @param formcount
	 * @param rchno
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public int rchInsSub(Connection conn, List subList, String sessionId, int rchno) throws Exception {
		//logger.info("out rchInsSub start");
		
		PreparedStatement pstmt = null;
        int[] ret = null; 
        int result = 0;

		StringBuffer delSubQuery = new StringBuffer();
		String insSubQuery = "";
		
		if(rchno ==0 ){
			delSubQuery.append("\n DELETE FROM OUT_RCHSUB 	");
			delSubQuery.append("\n  WHERE SESSIONID LIKE ? 	");

			insSubQuery = "INSERT INTO OUT_RCHSUB" +
			"(		 SESSIONID,	FORMSEQ,	FORMGRP,	FORMTITLE,		" +
			"		 FORMTYPE, 	SECURITY, 	EXAMTYPE,  REQUIRE	, EX_FRSQ, EX_EXSQ ) " +
	        "VALUES( ?,			?,			?,			?,			" +
	        "		 ?, 		?,			?,			NVL(?, 'N'),	NVL(?, 0),	NVL(?, 0) ) ";
		}else{
			delSubQuery.append("\n DELETE FROM OUT_RCHSUB 	");
			delSubQuery.append("\n  WHERE RCHNO = ? 	");

			insSubQuery = "INSERT INTO OUT_RCHSUB" +
			"(		 RCHNO,		FORMSEQ,	FORMGRP,	FORMTITLE,		" +
			"		 FORMTYPE, 	SECURITY,   EXAMTYPE, 	REQUIRE, EX_FRSQ, EX_EXSQ	 ) " +
	        "VALUES( ?,			?,			?,			?,			" +
	        "		 ?, 		?,			?,			NVL(?, 'N'),	NVL(?, 0),	NVL(?, 0)  ) ";	
		}
		//logger.info(insSubQuery);
		try {		
			
			pstmt = conn.prepareStatement(delSubQuery.toString());
			
			if(rchno == 0){
				pstmt.setString(1, sessionId);
			}else{
				pstmt.setInt(1, rchno);
			}

			result = pstmt.executeUpdate();

			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}
			
			pstmt = conn.prepareStatement(insSubQuery);	
			
			if(subList != null){
				ResearchSubBean subBean = null;
				//logger.info("subList.size("+subList.size()+") "+rchno);
				for(int i=0; i<subList.size(); i++){
					subBean = (ResearchSubBean)subList.get(i);
					if(rchno==0){
						pstmt.setString(1, sessionId);
					}else{
						pstmt.setInt(1, rchno);
					}	
					pstmt.setInt(2, subBean.getFormseq());
					pstmt.setString(3, new String(subBean.getFormgrp().getBytes("x-windows-949"), "x-windows-949"));
					pstmt.setString(4, new String(subBean.getFormtitle().getBytes("x-windows-949"), "x-windows-949"));
					pstmt.setString(5, subBean.getFormtype());
					pstmt.setString(6, subBean.getSecurity());
					pstmt.setString(7, subBean.getExamtype());
					pstmt.setString(8, subBean.getRequire());	//2017.2.9 필수응답 추가
					pstmt.setInt(9, subBean.getEx_frsq());			//2018.2.28 동적 문항 추가(연계된 문항에 보기와 연계)
					pstmt.setString(10, subBean.getEx_exsq());		//2018.2.28 동적 문항 추가(연계된 문항에 보기와 연계)
					//logger.info("pstmt["+subBean.getFormseq()+"]["+subBean.getFormgrp()+"]["+subBean.getFormtype()+"]["+subBean.getSecurity()+"]["+subBean.getExamtype()+"]["+subBean.getRequire()+"]["+subBean.getEx_frsq()+"]["+subBean.getEx_exsq()+"]");
					pstmt.addBatch();
					pstmt.clearParameters();
				}	
			}

			ret = pstmt.executeBatch();
			result = result + ret.length;
			//logger.info("ret.length("+ret.length+")");
			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}
			
			if(subList != null){
				ResearchSubBean subBean = null;
				for(int i=0; i<subList.size(); i++){
					subBean = (ResearchSubBean)subList.get(i);
					//logger.info("exam :"+(i+1));
					result += rchInsExam(conn, subBean.getExamList(), subBean.getFormseq(), sessionId, rchno, i);
				}
			}

		} catch(Exception e) {
			logger.error("rchInsSub ERROR");
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
		//logger.info("out rchInsSub end");     
		return result;
	}	
	
	/**
	 * 항목추가 (빈항목 저장)
	 */
	public int insAddSub(int rchno, String sessionId) throws Exception {
		//logger.info("out insAddSub");
		Connection con = null;        
		PreparedStatement pstmt = null;
		int result = 0;
		StringBuffer insertQuery = new StringBuffer();

		if(rchno == 0){
			insertQuery.append("INSERT INTO OUT_RCHSUB (SESSIONID, FORMSEQ, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE, REQUIRE, EX_FRSQ, EX_EXSQ ) ");
			insertQuery.append("VALUES(?, ?, '', '1', 'N', 'N', 'Y', 0, 0) ");		
		} else {
			insertQuery.append("INSERT INTO OUT_RCHSUB (RCHNO, FORMSEQ, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE, REQUIRE, EX_FRSQ, EX_EXSQ ) ");
			insertQuery.append("VALUES(?, ?, '', '1', 'N', 'N', 'Y', 0, 0) ");
		}			
		//logger.info(insertQuery);
		try{			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());
			
			if(rchno == 0){
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, rchno);
			}
			
			pstmt.setInt(2, getMaxSubSeq(rchno, sessionId));
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			 result = -1;
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt);
			 throw e;
	     } finally {	       
	    	 ConnectionManager.close(con,pstmt);   
	     }
		return result;
	}
	
	/**
	 * 
	 * @param rchno
	 * @param sessionId
	 * @param formcount
	 * @return
	 * @throws Exception
	 */
	public int insMakeSub(int rchno, String sessionId, int formcount) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		int precnt = getCntSubSeq(rchno, sessionId);
		int maxseq = getMaxSubSeq(rchno, sessionId);
		int result = 0;
		int[] ret = null;
		
		StringBuffer insertQuery = new StringBuffer();
		StringBuffer selectQuery = new StringBuffer();	
		//삭제 처리
		StringBuffer delteQuery = new StringBuffer();	
		
				
		if(rchno == 0){
			insertQuery.append("INSERT INTO OUT_RCHSUB(SESSIONID, FORMSEQ, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE, REQUIRE, EX_FRSQ, EX_EXSQ ) ");
			insertQuery.append("VALUES(?, ?, '', '1', 'N', 'N', 'Y', 0, 0) ");	
			
			delteQuery.append("DELETE FROM OUT_RCHSUB WHERE SESSIONID LIKE ? AND FORMSEQ = ? ");
			
			selectQuery.append("SELECT NVL(MAX(FORMSEQ),0) FROM OUT_RCHSUB WHERE SESSIONID LIKE ? ");
		} else {
			insertQuery.append("INSERT INTO OUT_RCHSUB(RCHNO, FORMSEQ, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE, REQUIRE, EX_FRSQ, EX_EXSQ ) ");
			insertQuery.append("VALUES(?, ?, '', '1', 'N', 'N', 'Y', 0, 0) ");
			
			delteQuery.append("DELETE FROM OUT_RCHSUB WHERE RCHNO = ? AND FORMSEQ = ? ");
			
			selectQuery.append("SELECT NVL(MAX(FORMSEQ),0) FROM OUT_RCHSUB WHERE RCHNO = ? ");
		}		
		//logger.info(insertQuery);
		try{	
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			if(formcount == precnt){
				return 0;  
			} else if(formcount > precnt) {
				int cnt = formcount - precnt;
				
				pstmt = con.prepareStatement(insertQuery.toString());
				for(int i=0;i<cnt;i++){
					
					if(rchno == 0){
						pstmt.setString(1, sessionId);
					} else {
						pstmt.setInt(1, rchno);
					}
					
					pstmt.setInt(2, maxseq+i);
					pstmt.addBatch();	
				}
				ret = pstmt.executeBatch();
				result = result + ret.length;	
				
				if (pstmt != null){
					try {
						pstmt.close();
						pstmt = null;
					} catch(SQLException ignored){ }
				}
			} else if(formcount < precnt){
				//항목 차이 수량 만큼 삭제
				int cnt = precnt - formcount;				
				
				for(int i=0;i<cnt;i++){
										
					pstmt = con.prepareStatement(selectQuery.toString());
					
					if(rchno == 0){
						pstmt.setString(1, sessionId);	
					} else {
						pstmt.setInt(1, rchno);
					}
								
					rs = pstmt.executeQuery();	
					
					int formseq = 0;
					if ( rs.next() ){
						formseq = rs.getInt(1);
					}	
					
					if (pstmt != null){
						try {
							rs.close();
							pstmt.close();
							pstmt = null;
						} catch(SQLException ignored){ }
					}
										
					pstmt = con.prepareStatement(delteQuery.toString());
					
					if(rchno == 0){
						pstmt.setString(1, sessionId);	
					} else {
						pstmt.setInt(1, rchno);
					}					
					pstmt.setInt(2, formseq);
								
					result += pstmt.executeUpdate();
					
					if (pstmt != null){
						try {
							pstmt.close();
							pstmt = null;
						} catch(SQLException ignored){ }
					}
					
					//보기 삭제
					result += rchDelExam(con, rchno, sessionId, formseq);
				}
			}			
			
			con.commit();
		} catch (Exception e) {
			try{
				result = -1;
				con.rollback();
			} catch(Exception ex){
				logger.error("ERROR",ex);
//				throw ex;
			}
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt,rs);
			 throw e;
	     } finally {	
//	    	 try{ 
//		 		con.setAutoCommit(true);
//	    	 } catch (Exception e){
//				logger.error("ERROR",e);
////				throw e;
//	    	 }
			ConnectionManager.close(con,pstmt,rs);	   
	     }	
		return result;
	}	
	
	/**
	 * 
	 * @param conn
	 * @param subList
	 * @param rchno
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public int rchInsExam(Connection conn, List examList, int formseq, String sessionId, int rchno, int cnt) throws Exception {
		//logger.info("out rchInsExam start examList["+examList+"] formseq["+formseq+"] rchno["+rchno+"] cnt["+cnt+"]");
		PreparedStatement pstmt = null;
        int result = 0;
        int[] ret = null;

		StringBuffer delExamQuery = new StringBuffer();
		String insExamQuery = "";
		
		if(rchno ==0 ){
			delExamQuery.append("\n DELETE FROM OUT_RCHEXAM ");
			delExamQuery.append("\n  WHERE SESSIONID LIKE ? 	");
			
			insExamQuery = "INSERT INTO OUT_RCHEXAM" +
			"(		SESSIONID,		FORMSEQ,	EXAMSEQ,	EXAMCONT	 ) " +
	        "VALUES(?,			?,			?,			?				) ";
		}else{
			delExamQuery.append("\n DELETE FROM OUT_RCHEXAM ");
			delExamQuery.append("\n  WHERE RCHNO = ? 	");
			
			insExamQuery = "INSERT INTO OUT_RCHEXAM" +
			"(		RCHNO,		FORMSEQ,	EXAMSEQ,	EXAMCONT	 ) " +
	        "VALUES(?,			?,			?,			?			) ";			
		}
		//logger.info(insExamQuery);
		if(cnt == 0){
			pstmt = conn.prepareStatement(delExamQuery.toString());
			
			if(rchno == 0){
				pstmt.setString(1, sessionId);
			}else{
				pstmt.setInt(1, rchno);
			}
	
			result = pstmt.executeUpdate();
						
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}
		}
		
			ResearchExamBean examBean = null;
			try{				
			if(examList != null){
				pstmt = conn.prepareStatement(insExamQuery);
				for(int j=0; j<examList.size();j++){
					examBean = (ResearchExamBean)examList.get(j);
					
					if ( "".equals(examBean.getExamcont().trim()) ) continue;
					
					if(rchno == 0){
						pstmt.setString(1, sessionId);
					}else{
						pstmt.setInt(1, rchno);
					}
					pstmt.setInt(2, formseq);
					pstmt.setInt(3, examBean.getExamseq() );
					pstmt.setString(4, new String(examBean.getExamcont().getBytes("x-windows-949"), "x-windows-949"));
					
					pstmt.addBatch();
					pstmt.clearParameters();
				}
			
				ret = pstmt.executeBatch();
				result = result +ret.length;	
				//logger.info("examList ret.length("+ret.length+")");
			}
		
		}catch (Exception e) {
			logger.error("ERROR", e);
			 throw e;
	     } finally {
	    	 if (pstmt != null){
					try {
						pstmt.close();
						pstmt = null;
					} catch(SQLException ignored){ }
				}
			}
		
		//logger.info("out rchInsExam end");
		return result;
	}	
	
	
	/**
	 * 설문조사 수정
	 * @param conn
	 * @param Bean
	 * @param subList
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public int rchUptMst(Connection conn, ResearchBean Bean, int mdrchno) throws Exception {
		PreparedStatement pstmt = null;
        int result = 0;

		try {
			String summary = null;
			String headcont = null;
			String tailcont = null;
			String query = null;
			
			if(Bean.getSummary() != null) {
				summary = Bean.getSummary().replaceAll("'", "''");
			} else {
				summary = "";
			}
			
			if(Bean.getHeadcont() != null) {
				headcont = Bean.getHeadcont().replaceAll("'", "''");
			} else {
				headcont = "";
			}
			
			if(Bean.getTailcont() != null) {
				tailcont = Bean.getTailcont().replaceAll("'", "''");
			} else {
				tailcont = "";
			}		
			
			if(mdrchno==0){
				query =	"UPDATE OUT_RCHMST" +
				"   SET	TITLE = ?,		STRDT = ?,		ENDDT = ?,		COLDEPTCD = ?,  COLDEPTNM = ?,	COLDEPTTEL = ?, " +
				"	    CHRGUSRID = ?,	CHRGUSRNM = ?,	SUMMARY = '" + summary + "',	EXHIBIT = ?,	OPENTYPE = ?, 	RANGE = ?,	IMGPREVIEW = ?,	DUPLICHECK = ?,	LIMITCOUNT = ?, " +
				"		GROUPYN = ?,	TGTDEPTNM = ?,	HEADCONT = '"+ headcont+ "',	TAILCONT = '" + tailcont + "',	UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS')," +
		        "		UPTUSRID = ?    " +
		        " WHERE SESSIONID LIKE ? ";
			}else{
				query =	"UPDATE OUT_RCHMST" +
				"   SET	TITLE = ?,		STRDT = ?,		ENDDT = ?,		COLDEPTCD = ?,  COLDEPTNM = ?,	COLDEPTTEL = ?, " +
				"	    CHRGUSRID = ?,	CHRGUSRNM = ?,	SUMMARY = '" + summary + "',	EXHIBIT = ?,	OPENTYPE = ?, 	RANGE = ?,	IMGPREVIEW = ?,	DUPLICHECK = ?,	LIMITCOUNT = ?, " +
				"		GROUPYN = ?,	TGTDEPTNM = ?,	HEADCONT = '"+ headcont+ "',	TAILCONT = '" + tailcont + "',	UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS')," +
		        "		UPTUSRID = ?    " +
		        " WHERE RCHNO =  ?  ";
			}

			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, Bean.getTitle());		//제목
			pstmt.setString(2, Bean.getStrdt());		//시작일
			pstmt.setString(3, Bean.getEnddt());		//종료일
			pstmt.setString(4, Bean.getColdeptcd());	//담당부서코드
			pstmt.setString(5, Bean.getColdeptnm());	//담당부서명
			pstmt.setString(6, Bean.getColdepttel());   //담당부서전화
			pstmt.setString(7, Bean.getChrgusrid());	//담당자ID
			pstmt.setString(8, Bean.getChrgusrnm());	//담당자명
			pstmt.setString(9, Bean.getExhibit());		//결과공개
			pstmt.setString(10, Bean.getOpentype());	//조사방법
			pstmt.setString(11, ( "1".equals(Bean.getRange()) ) ? Bean.getRange() : Bean.getRangedetail());		//범위
			pstmt.setString(12, Bean.getImgpreview());	//첨부그림미리보기
			pstmt.setString(13, Bean.getDuplicheck());	//중복답변체크
			pstmt.setInt(14, Bean.getLimitcount());	//목표응답수
			pstmt.setString(15, Bean.getGroupyn());	//설문그룹여부
			pstmt.setString(16, Bean.getTgtdeptnm());	//취합담당단위명
			pstmt.setString(17, Bean.getChrgusrid());	//생성자ID
			
			if(mdrchno ==0){
				pstmt.setString(18, Bean.getSessionId());
			}else{
				pstmt.setInt(18, mdrchno);
			}

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
	 * 설문조사그룹 수정
	 * @param conn
	 * @param Bean
	 * @param subList
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public int rchGrpUptMst(Connection conn, ResearchBean Bean, int mdrchgrpno) throws Exception {
		PreparedStatement pstmt = null;
        int result = 0;

		try {
			String summary = null;
			String headcont = null;
			String tailcont = null;
			String query = null;
			
			if(Bean.getSummary() != null) {
				summary = Bean.getSummary().replaceAll("'", "''");
			} else {
				summary = "";
			}
			
			if(Bean.getHeadcont() != null) {
				headcont = Bean.getHeadcont().replaceAll("'", "''");
			} else {
				headcont = "";
			}
			
			if(Bean.getTailcont() != null) {
				tailcont = Bean.getTailcont().replaceAll("'", "''");
			} else {
				tailcont = "";
			}		
			
			if(mdrchgrpno==0){
				query =	"UPDATE OUT_RCHGRPMST" +
				"   SET	TITLE = ?,		STRDT = ?,		ENDDT = ?,		COLDEPTCD = ?,  COLDEPTNM = ?,	COLDEPTTEL = ?, " +
				"	    CHRGUSRID = ?,	CHRGUSRNM = ?,	SUMMARY = '" + summary + "',	EXHIBIT = ?,	OPENTYPE = ?, 	RANGE = ?,	IMGPREVIEW = ?,	DUPLICHECK = ?,	LIMITCOUNT = ?, " +
				"		GROUPYN = ?,	RCHNOLIST = ?,	TGTDEPTNM = ?,	HEADCONT = '"+ headcont+ "',	TAILCONT = '" + tailcont + "',	UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS')," +
		        "		UPTUSRID = ?    " +
		        " WHERE SESSIONID LIKE ? ";
			}else{
				query =	"UPDATE OUT_RCHGRPMST" +
				"   SET	TITLE = ?,		STRDT = ?,		ENDDT = ?,		COLDEPTCD = ?,  COLDEPTNM = ?,	COLDEPTTEL = ?, " +
				"	    CHRGUSRID = ?,	CHRGUSRNM = ?,	SUMMARY = '" + summary + "',	EXHIBIT = ?,	OPENTYPE = ?, 	RANGE = ?,	IMGPREVIEW = ?,	DUPLICHECK = ?,	LIMITCOUNT = ?, " +
				"		GROUPYN = ?,	RCHNOLIST = ?,	TGTDEPTNM = ?,	HEADCONT = '"+ headcont+ "',	TAILCONT = '" + tailcont + "',	UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS')," +
		        "		UPTUSRID = ?    " +
		        " WHERE RCHGRPNO =  ?  ";
			}

			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, Bean.getTitle());		//제목
			pstmt.setString(2, Bean.getStrdt());		//시작일
			pstmt.setString(3, Bean.getEnddt());		//종료일
			pstmt.setString(4, Bean.getColdeptcd());	//담당부서코드
			pstmt.setString(5, Bean.getColdeptnm());	//담당부서명
			pstmt.setString(6, Bean.getColdepttel());   //담당부서전화
			pstmt.setString(7, Bean.getChrgusrid());	//담당자ID
			pstmt.setString(8, Bean.getChrgusrnm());	//담당자명
			pstmt.setString(9, Bean.getExhibit());		//결과공개
			pstmt.setString(10, Bean.getOpentype());	//조사방법
			pstmt.setString(11, ( "1".equals(Bean.getRange()) ) ? Bean.getRange() : Bean.getRangedetail());		//범위
			pstmt.setString(12, Bean.getImgpreview());	//첨부그림미리보기
			pstmt.setString(13, Bean.getDuplicheck());	//중복답변체크
			pstmt.setInt(14, Bean.getLimitcount());		//목표응답수
			pstmt.setString(15, Bean.getGroupyn());		//설문그룹여부
			pstmt.setString(16, Bean.getRchnolist());	//설문조사목록
			pstmt.setString(17, Bean.getTgtdeptnm());	//취합담당단위명
			pstmt.setString(18, Bean.getChrgusrid());	//생성자ID
			
			if(mdrchgrpno ==0){
				pstmt.setString(19, Bean.getSessionId());
			}else{
				pstmt.setInt(19, mdrchgrpno);
			}

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
	 * 설문조사 보기 첨부파일 삭제
	 * @param conn
	 * @param rchExamBean
	 * @param context
	 * @throws Exception
	 */
	public int delRchExamAllFile(Connection conn, String sessionId, int rchno, int formseq, ServletContext context) throws Exception {
		
		int result = 0;

		ResearchSubBean rchSubBean = getRchSubFile(conn, sessionId, rchno, formseq);
		
		if ( rchSubBean != null ) {
			delRchSubFile(conn, sessionId, rchno, formseq, rchSubBean.getFileseq());
			
			String delFile = rchSubBean.getFilenm();
			if ( delFile != null && delFile.trim().equals("") == false) {
				FileManager.doFileDelete(context.getRealPath(delFile));
			}
		}
		
		List rchExamList = getRchExamFile(conn, sessionId, rchno, formseq);
		
		for ( int i = 0; rchExamList != null && i < rchExamList.size(); i++ ) {
			ResearchExamBean rchExamBean = (ResearchExamBean)rchExamList.get(i);
			
			if ( rchExamBean != null ) {
				delRchExamFile(conn, sessionId, rchno, formseq, rchExamBean.getExamseq(), rchExamBean.getFileseq());
				
				String delFile = rchExamBean.getFilenm();
				if ( delFile != null && delFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delFile));
				}
			}
		}

		return result;
	}

	/**
	 * 설문조사 전체 첨부파일 삭제
	 * @param conn
	 * @param rchExamBean
	 * @param context
	 * @throws Exception
	 */
	public int delRchSubExamAllFile(Connection conn, String sessionId, int rchno, ServletContext context) throws Exception {
		//logger.info("out delRchSubExamAllFile start");
		int result = 0;
		
		List rchSubList = getRchSubFile(conn, sessionId, rchno);
		for ( int i = 0; rchSubList != null && i < rchSubList.size(); i++ ) {
			ResearchSubBean rchSubBean = (ResearchSubBean)rchSubList.get(i);
		
			if ( rchSubBean != null ) {
				delRchSubFile(conn, sessionId, rchno, rchSubBean.getFormseq(), rchSubBean.getFileseq());
				
				String delFile = rchSubBean.getFilenm();
				if ( delFile != null && delFile.trim().equals("") == false) {
					File f = new File(context.getRealPath(delFile));
					if (f.isFile()) {
						FileManager.doFileDelete(context.getRealPath(delFile));
					}
				}
			}
		}
		
		List rchExamList = getRchExamFile(conn, sessionId, rchno, 0);
		for ( int i = 0; rchExamList != null && i < rchExamList.size(); i++ ) {
			ResearchExamBean rchExamBean = (ResearchExamBean)rchExamList.get(i);
			
			if ( rchExamBean != null ) {
				delRchExamFile(conn, sessionId, rchno, rchExamBean.getFormseq(), rchExamBean.getExamseq(), rchExamBean.getFileseq());
				
				String delFile = rchExamBean.getFilenm();
				if ( delFile != null && delFile.trim().equals("") == false) {
					File f = new File(context.getRealPath(delFile));
					if (f.isFile()) {
						FileManager.doFileDelete(context.getRealPath(delFile));
					}
				}
			}
		}
		//logger.info("out delRchSubExamAllFile end : "+result );
		return result;
	}
	
	/**
	 * 
	 * @param reqformno
	 * @param sessi
	 * @param formseq
	 * @return
	 * @throws Exception
	 */
	public int rchDelSub(int rchno, String sessionId, int formseq, ServletContext context) throws Exception{
		Connection con = null;        
		PreparedStatement pstmt = null;
		int result =0;
		StringBuffer deleteQuery = new StringBuffer();
		
		if(rchno == 0){
			deleteQuery.append("DELETE FROM OUT_RCHSUB WHERE SESSIONID LIKE ? AND FORMSEQ = ? ");
		} else {
			deleteQuery.append("DELETE FROM OUT_RCHSUB WHERE RCHNO = ? AND FORMSEQ = ? ");		
		}
		
		try {
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
		    
			pstmt = con.prepareStatement(deleteQuery.toString());		
		    
		    if(rchno == 0){
		    	pstmt.setString(1, sessionId);	
		    } else {
		    	pstmt.setInt(1, rchno);	
		    }				
		    
		    pstmt.setInt(2, formseq);
			
			result = pstmt.executeUpdate();			
			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}	
			
			//보기내용 삭제
			result += rchDelExam(con, rchno, sessionId, formseq);
			
			delRchExamAllFile(con, sessionId, rchno, formseq, context);
			
			con.commit();
		} catch (Exception e) {
			try{
				result = -1;
				con.rollback();
			} catch(Exception ex){
				logger.error("ERROR",ex);
//				throw ex;
			}
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt);
			 throw e;
	     } finally {	
//	    	 try{ 
//		 		con.setAutoCommit(true);
//	    	 } catch (Exception e){
//				logger.error("ERROR",e);
////				throw e;
//	    	 }
			ConnectionManager.close(con,pstmt);	   
	     }	
		return result;
	}
	
	/**
	 * 
	 * @param con
	 * @param rchno
	 * @param sessionId
	 * @param formseq
	 * @return
	 * @throws Exception
	 */
	private int rchDelExam(Connection con, int rchno, String sessionId, int formseq) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		
		StringBuffer delteQuery = new StringBuffer();		
		if(rchno == 0){	
			delteQuery.append("DELETE FROM OUT_RCHEXAM WHERE SESSIONID LIKE ? AND FORMSEQ = ? ");
		} else {
			delteQuery.append("DELETE FROM OUT_RCHEXAM WHERE RCHNO = ? AND FORMSEQ = ? ");
		}	
	 
		try{
			pstmt = con.prepareStatement(delteQuery.toString());
			
			if(rchno == 0){	
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, rchno);
			}	
			
			pstmt.setInt(2, formseq);
			
			result = pstmt.executeUpdate();			
		
		}catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			try {pstmt.close();} catch(Exception e) {}
		}
		return result;
	}	
	
	/**
	 * 
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public boolean checkCnt(String sessionId) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) FROM OUT_RCHMST WHERE SESSIONID LIKE ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, sessionId);			
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				if(rs.getInt(1) > 0){
					result = true;
				}
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
	 * @param rchno
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public int getMaxSubSeq(int rchno, String sessionId) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;

		
		StringBuffer selectQuery = new StringBuffer();

		if(rchno == 0){
			selectQuery.append("SELECT NVL(MAX(FORMSEQ),0)+1 FROM OUT_RCHSUB WHERE SESSIONID LIKE ? ");
		} else {
			
			selectQuery.append("SELECT NVL(MAX(FORMSEQ),0)+1 FROM OUT_RCHSUB WHERE RCHNO = ? ");
		}	
		
		try {
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if(rchno == 0){
				pstmt.setString(1, sessionId);	
			} else {
				pstmt.setInt(1, rchno);
			}
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				result = rs.getInt(1);
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
	 * @param rchno
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	private int getCntSubSeq(int rchno, String sessionId) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		StringBuffer selectQuery = new StringBuffer();
		
		if(rchno == 0){
			selectQuery.append("SELECT COUNT(*) FROM OUT_RCHSUB WHERE SESSIONID LIKE ? ");
		} else {
			selectQuery.append("SELECT COUNT(*) FROM OUT_RCHSUB WHERE RCHNO = ? ");
		}	
		
		try {
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if(rchno == 0){
				pstmt.setString(1, sessionId);	
			} else {
				pstmt.setInt(1, rchno);
			}
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				result = rs.getInt(1);
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
	 * 공유 목록
	 * @param CommCollDocSearchBean searchBean
	 * @param int start
	 * @param int end
	 * @return List 취합문서목록(ArrayList)
	 * @throws Exception 
	 */
	public List getUsedRchList(ResearchBean schBean, int start, int end) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List list = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT (CNT-SEQ+1) BUNHO, X.* " +
							   "FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* " +
							   "      FROM (SELECT ROWNUM SEQ, A2.*  " +
							   "            FROM (SELECT T1.RCHNO, T1.TITLE, REPLACE(SUBSTR(T1.STRDT,6,5),'-','/') STRDT,  REPLACE(SUBSTR(T1.ENDDT,6,5),'-','/') ENDDT, T2.CCDNAME AS RANGE " +
							   "                  FROM OUT_RCHMST  T1, CCD T2 " +
							   "                  WHERE T1.RANGE = T2.CCDSUBCD " +
							   "                    AND T2.CCDCD = '013' " +
							   "   				 	AND T1.OPENTYPE = '1' \n");
			//부서 조건
			if (Utils.isNotNull(schBean.getSch_deptcd())) {
			selectQuery.append("                    AND T1.COLDEPTCD IN (SELECT T1.DEPT_ID \n" +
							   "                                          FROM DEPT T1 \n" +
							   "                                         START WITH T1.DEPT_ID = '"+schBean.getSch_deptcd()+"' \n" +
							   "                                       CONNECT BY PRIOR T1.DEPT_ID = T1.UPPER_DEPT_ID) \n");
			}
			//제목 조건
			if (Utils.isNotNull(schBean.getSch_title())) {
			selectQuery.append("                    AND T1.TITLE LIKE '%"+schBean.getSch_title()+"%' \n");
			}
			//년도 조건
			if (Utils.isNotNull(schBean.getSelyear())) {
			selectQuery.append("                    AND TO_CHAR(TO_DATE(T1.ENDDT,'YYYY-MM-DD HH24:MI:SS'), 'YYYY') = '"+schBean.getSelyear()+"' \n");
			}
			//모든조건이 없을경우
			selectQuery.append("				  ORDER BY T1.CRTDT DESC) A2) A1) X \n" +
							   "WHERE SEQ BETWEEN ? AND ? \n");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
									
			rs = pstmt.executeQuery();
			
			list = new ArrayList();
			
			while (rs.next()) {
				ResearchBean Bean = new ResearchBean();
				
				Bean.setSeqno(rs.getInt("BUNHO"));
				Bean.setRchno(rs.getInt("RCHNO"));
				Bean.setTitle(rs.getString("TITLE"));		
				Bean.setStrdt(rs.getString("STRDT"));
				Bean.setEnddt(rs.getString("ENDDT"));
				Bean.setRange(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 1));
				Bean.setRangedetail(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 2));
				
				list.add(Bean);
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return list;
	}
	
	/**
	 * 공유 목록 갯수 가져오기
	 * @param String deptcd
	 * @return int 취합문서개수
	 * @throws Exception 
	 */
	public int getUsedRchTotCnt(ResearchBean Bean) throws Exception {
		Connection con = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int totalCount = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*)CNT \n" +
							   "FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* \n" +
							   "      FROM (SELECT ROWNUM SEQ, A2.*  \n" +
							   "            FROM (SELECT RCHNO, TITLE, STRDT, ENDDT, RANGE \n" +
							   "                    FROM OUT_RCHMST  \n" +
							   "                   WHERE OPENTYPE = '1' \n");
			//부서 조건
			if (Utils.isNotNull(Bean.getSch_deptcd())) {
			selectQuery.append("                     AND COLDEPTCD IN (SELECT T1.DEPT_ID \n" +
							   "                                           FROM DEPT T1 \n" +
							   "                                          START WITH T1.DEPT_ID = '"+Bean.getSch_deptcd()+"' \n" +
							   "                                        CONNECT BY PRIOR T1.DEPT_ID = T1.UPPER_DEPT_ID) \n");
			}
			//제목 조건
			if (Utils.isNotNull(Bean.getSch_title())) {
			selectQuery.append("                     AND TITLE LIKE '%"+Bean.getSch_title()+"%' \n");
			}
			//년도 조건
			if (Utils.isNotNull(Bean.getSelyear())) {
			selectQuery.append("                     AND TO_CHAR(TO_DATE(ENDDT,'YYYY-MM-DD HH24:MI:SS'), 'YYYY') = '"+Bean.getSelyear()+"' \n");
			}			
			selectQuery.append("				   ORDER BY CRTDT DESC) A2) A1) X \n" +
							   " ");
			con = ConnectionManager.getConnection();
			
			pstmt =	con.prepareStatement(selectQuery.toString());
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				totalCount = rs.getInt(1);
			}

		 } catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(con,pstmt,rs);
	     }
		return totalCount;
	}		
	
	/**
	 * 공유 목록
	 * @param CommCollDocSearchBean searchBean
	 * @param int start
	 * @param int end
	 * @return List 취합문서목록(ArrayList)
	 * @throws Exception 
	 */
	public List getRchParticiList(String usrid, String deptcd, String schtitle, int start, int end) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List list = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT (CNT-SEQ+1) BUNHO, X.* " +
							   "FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* " +
							   "      FROM (SELECT ROWNUM SEQ, A2.*  " +
							   "             FROM (SELECT DISTINCT T1.RCHNO, T1.TITLE, REPLACE(SUBSTR(T1.STRDT,6,5),'-','/') STRDT,  REPLACE(SUBSTR(T1.ENDDT,6,5),'-','/') ENDDT, T1.CRTDT  " +
							   "                     FROM OUT_RCHMST T1, RCHDEPT T2, RCHOTHERTARGET T3" +
							   "                    WHERE T1.RCHNO = T2.RCHNO(+)" +
							   "                      AND T1.RCHNO = T3.RCHNO(+)" +
							   "					  AND T1.OPENTYPE = '1'" +
							   "					  AND T1.RANGE = '1'" +
							   "					  AND T1.GROUPYN = 'N'" +
							   "					  AND T3.TGTGBN(+) = '1' \n");
			selectQuery.append("                      AND TO_CHAR(SYSDATE,'YYYY-MM-DD')BETWEEN T1.STRDT AND T1.ENDDT \n");
			selectQuery.append("                      AND (CASE NVL(T2.TGTCODE, '0') WHEN '0' THEN 1 WHEN '"+deptcd+"' THEN 1 ELSE 0 END = 1 \n");
			selectQuery.append("                           OR CASE NVL(T2.TGTCODE, '0') WHEN '0' THEN 1 WHEN '"+usrid+"' THEN 1 ELSE 0 END = 1) \n");
			selectQuery.append("                      AND CASE NVL(T3.TGTCODE, '0') WHEN '0' THEN 1 WHEN (SELECT GRADE_ID FROM USR WHERE USER_ID = '"+usrid+"') THEN 1 ELSE 0 END = 1 \n");
			selectQuery.append("                      AND T1.TITLE LIKE '%"+Utils.nullToEmptyString(schtitle)+"%' \n");		
			selectQuery.append("                    MINUS " +
							   "                   SELECT T1.RCHNO, T1.TITLE, REPLACE(SUBSTR(T1.STRDT,6,5),'-','/') STRDT,  REPLACE(SUBSTR(T1.ENDDT,6,5),'-','/') ENDDT, T1.CRTDT " +
							   "                     FROM OUT_RCHMST T1, OUT_RCHANS T2 " +
							   "                    WHERE T1.RCHNO = T2.RCHNO" +
							   "                      AND T2.CRTUSRID = '"+usrid+"' \n");                 
			selectQuery.append("				    ORDER BY CRTDT DESC ) A2) A1) X  " +
							   "WHERE SEQ BETWEEN ? AND ? \n");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
									
			rs = pstmt.executeQuery();
			
			list = new ArrayList();
			
			while (rs.next()) {
				ResearchBean Bean = new ResearchBean();
				
				Bean.setSeqno(rs.getInt("BUNHO"));
				Bean.setRchno(rs.getInt("RCHNO"));
				Bean.setTitle(rs.getString("TITLE"));		
				Bean.setStrdt(rs.getString("STRDT"));
				Bean.setEnddt(rs.getString("ENDDT"));
				
				list.add(Bean);
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return list;
	}
	
	/**
	 * 공유 목록 갯수 가져오기
	 * @param String deptcd
	 * @return int 취합문서개수
	 * @throws Exception 
	 */
	public int getRchParticiTotCnt(String usrid, String deptcd, String schtitle) throws Exception {
		Connection con = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int totalCount = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*)CNT \n" +
							   "FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* \n" +
							   "      FROM (SELECT ROWNUM SEQ, A2.*  \n" +
							   "             FROM (SELECT DISTINCT T1.RCHNO, T1.TITLE, SUBSTR(T1.STRDT,6,5) STRDT,  SUBSTR(T1.ENDDT,6,5) ENDDT, T1.CRTDT, '0'  " +
							   "                     FROM OUT_RCHMST T1, RCHDEPT T2, RCHOTHERTARGET T3" +
							   "                    WHERE T1.RCHNO = T2.RCHNO(+)" +
							   "                      AND T1.RCHNO = T3.RCHNO(+)" +
							   "					  AND T1.OPENTYPE = '1' " +
							   "                      AND T1.RANGE = '1'" +
							   "                      AND T1.GROUPYN = 'N'" +
							   "					  AND T3.TGTGBN(+) = '1' \n");
			selectQuery.append("\n                    AND TO_CHAR(SYSDATE,'YYYY-MM-DD')BETWEEN T1.STRDT AND T1.ENDDT ");
			selectQuery.append("                      AND (CASE NVL(T2.TGTCODE, '0') WHEN '0' THEN 1 WHEN '"+deptcd+"' THEN 1 ELSE 0 END = 1 \n");
			selectQuery.append("                           OR CASE NVL(T2.TGTCODE, '0') WHEN '0' THEN 1 WHEN '"+usrid+"' THEN 1 ELSE 0 END = 1) \n");
			selectQuery.append("                      AND CASE NVL(T3.TGTCODE, '0') WHEN '0' THEN 1 WHEN (SELECT GRADE_ID FROM USR WHERE USER_ID = '"+usrid+"') THEN 1 ELSE 0 END = 1 \n");
			selectQuery.append("                      AND T1.TITLE LIKE '%"+Utils.nullToEmptyString(schtitle)+"%' \n");
			/*selectQuery.append("                      UNION" +
							   "                     SELECT T1.RCHNO, T1.TITLE, SUBSTR(T1.STRDT,6,5) STRDT,  SUBSTR(T1.ENDDT,6,5) ENDDT, T1.CRTDT, '1'" +
							   "                       FROM OUT_RCHMST T1, OUT_RCHANS T2 " +
							   "                      WHERE T1.RCHNO = T2.RCHNO" +
							   "                        AND T2.CRTUSRID = '"+Bean.getChrgusrid()+"' \n");
			//제목 조건
			if (Utils.isNotNull(Bean.getSch_title())) {
			selectQuery.append("                        AND T1.TITLE LIKE '%"+Bean.getSch_title()+"%' \n");
			} */                   
			selectQuery.append("				   ) A2" +
					           "       ORDER BY CRTDT DESC) A1) X \n");
			
			con = ConnectionManager.getConnection();
			
			pstmt =	con.prepareStatement(selectQuery.toString());
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				totalCount = rs.getInt(1);
			}

		 } catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(con,pstmt,rs);
	     }
		return totalCount;
	}	
	
	/**
	 * 
	 * @param conn
	 * @param subList
	 * @param rchno
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public int rchAnsSave(List ansList, int rchno, String usrid, String usrnm) throws Exception {
		//logger.info("out rchAnsSave start ["+rchno+"]");
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int[] ret = null; 
        int result = 0;
        int maxseq = 0;

		StringBuffer selectQuery1 = new StringBuffer();
		StringBuffer selectQuery2 = new StringBuffer();
		StringBuffer deleteQuery = new StringBuffer();
		StringBuffer updateQuery = new StringBuffer();
		String insertQuery = "";
		
		selectQuery1.append("\n SELECT DISTINCT ANSUSRSEQ FROM OUT_RCHANS WHERE RCHNO = ? AND CRTUSRID = ?");
		//selectQuery2.append("\n SELECT NVL(MAX(ANSUSRSEQ),0) FROM OUT_RCHANS WHERE RCHNO = ? ");
		selectQuery2.append("\n SELECT ANSUSRSEQ.NEXTVAL FROM DUAL ");
		
		deleteQuery.append("\n DELETE FROM OUT_RCHANS WHERE RCHNO = ? AND CRTUSRID = ?");
		
		insertQuery = "INSERT INTO OUT_RCHANS " +
					  "(RCHNO,	ANSUSRSEQ,	FORMSEQ,	ANSCONT,	OTHER, " +
					  " CRTDT,	CRTUSRID,	CRTUSRNM) " +
			          "VALUES(?, ?, ?, ?, ?, " +
			          "       TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), '"+usrid+"', '"+ usrnm +"') ";		
		
		updateQuery.append("\n UPDATE OUT_RCHMST SET ANSCOUNT = (SELECT COUNT(DISTINCT ANSUSRSEQ) FROM OUT_RCHANS WHERE RCHNO = ?) WHERE RCHNO = ? "); 
		try {
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			//답변기록이 있는지 확인
			pstmt = con.prepareStatement(selectQuery1.toString());
			pstmt.setInt(1, rchno);
			pstmt.setString(2, usrid);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next()) { //중복답변 가능하도록 처리(&& rs.getInt(1) > 1 제거시 중복답변 안됨)
				maxseq = rs.getInt(1);
				
				ConnectionManager.close(pstmt, rs);
				pstmt = con.prepareStatement(deleteQuery.toString());
				
				pstmt.setInt(1, rchno);
				pstmt.setString(2, usrid);
		
				pstmt.executeUpdate();
			} else {
				ConnectionManager.close(pstmt, rs);
				
				pstmt = con.prepareStatement(selectQuery2.toString());
				//pstmt.setInt(1, rchno);
		
				rs = pstmt.executeQuery();
				if ( rs.next() ){
					//maxseq = rs.getInt(1) + 1;
					maxseq = rs.getInt(1);
				}
			}
			//logger.info("maxseq["+maxseq+"]");
			ConnectionManager.close(pstmt, rs);
			
			ResearchAnsBean ansBean = null;
			pstmt = con.prepareStatement(insertQuery);
			for(int j=0; j<ansList.size();j++){
				ansBean = (ResearchAnsBean)ansList.get(j);
	
				pstmt.setInt(1, rchno);
				pstmt.setInt(2, maxseq);
				pstmt.setInt(3, ansBean.getFormseq());
				pstmt.setString(4, ansBean.getAnscont());
				pstmt.setString(5, ansBean.getOther());
				
				pstmt.addBatch();

			}
			ret = pstmt.executeBatch();
			result = result + ret.length;	
			ConnectionManager.close(pstmt);
			
			
			pstmt = con.prepareStatement(updateQuery.toString());
			
			pstmt.setInt(1, rchno);
			pstmt.setInt(2, rchno);
			
			result += pstmt.executeUpdate();	
			ConnectionManager.close(pstmt);
			
			con.commit();
		}catch(SQLException e){
			con.rollback();
			logger.error("ERROR",e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
	    }finally {	     
	    	con.setAutoCommit(true);
			ConnectionManager.close(con,pstmt,rs);
	    }
		//logger.info("out rchAnsSave end");
		return result;
	}	
	
	/**
	 * 
	 * @param sessi
	 * @return
	 * @throws Exception
	 */
	public int rchChoice(int rchno, String sessionId, String usrid, String usrnm, String deptcd, String deptnm, String coltel, ServletContext context, String saveDir) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;	
		int result = 0;
		//logger.info("out rchChoice start");
		try{				
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);	
			
			//양식마스터 복사
			StringBuffer insertQuery2 = new StringBuffer();
			insertQuery2.append("\n INSERT INTO OUT_RCHMST ");
			insertQuery2.append("\n SELECT '" + sessionId + "', TITLE, STRDT, ENDDT, '"+ deptcd +"', ");
			insertQuery2.append("\n        '" + deptnm + "', '" + coltel + "', '" + usrid + "', '" + usrnm + "', SUMMARY, EXHIBIT, OPENTYPE, RANGE, IMGPREVIEW, DUPLICHECK, ");
			insertQuery2.append("\n        LIMITCOUNT, GROUPYN, RCHNOLIST, TGTDEPTNM, HEADCONT, TAILCONT, 0, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), '" +usrid + "', '', '' ");
			insertQuery2.append("\n   FROM OUT_RCHMST ");
			insertQuery2.append("\n  WHERE RCHNO = ? "); 
			//logger.info(insertQuery2);
			pstmt = con.prepareStatement(insertQuery2.toString());
			pstmt.setInt(1, rchno);
			
			result = pstmt.executeUpdate();			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}	
			
			//양식 문항 복사
			StringBuffer selectQuery3 = new StringBuffer();
			selectQuery3.append("\n INSERT INTO OUT_RCHSUB ");
			selectQuery3.append("\n SELECT '" + sessionId + "', FORMSEQ, FORMGRP, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE, REQUIRE, EX_FRSQ, EX_EXSQ ");
			selectQuery3.append("\n   FROM OUT_RCHSUB ");
			selectQuery3.append("\n  WHERE RCHNO = ? "); 
			//logger.info(selectQuery3);
			pstmt = con.prepareStatement(selectQuery3.toString());
			pstmt.setInt(1, rchno);
			
			result = result + pstmt.executeUpdate();			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}	
			
			//양식 보기 복사
			StringBuffer selectQuery4 = new StringBuffer();
			selectQuery4.append("\n INSERT INTO OUT_RCHEXAM ");
			selectQuery4.append("\n SELECT '" + sessionId + "', FORMSEQ, EXAMSEQ, EXAMCONT ");
			selectQuery4.append("\n   FROM OUT_RCHEXAM ");
			selectQuery4.append("\n  WHERE RCHNO = ? "); 
			//logger.info(selectQuery4);
			pstmt = con.prepareStatement(selectQuery4.toString());
			pstmt.setInt(1, rchno);
			
			result = result + pstmt.executeUpdate();			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}	
			
			//대상부서지정 복사
			StringBuffer selectQuery5 = new StringBuffer();
			selectQuery5.append("\n INSERT INTO OUT_RCHDEPT ");
			selectQuery5.append("\n SELECT '" + sessionId + "', TGTDEPTCD, TGTDEPTNM ");
			selectQuery5.append("\n   FROM OUT_RCHDEPT ");
			selectQuery5.append("\n  WHERE RCHNO = ? "); 
			pstmt = con.prepareStatement(selectQuery5.toString());
			pstmt.setInt(1, rchno);
			
			result = result + pstmt.executeUpdate();			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}	
			
			//대상부서리스트지정 복사
			StringBuffer selectQuery6 = new StringBuffer();
			selectQuery6.append("\n INSERT INTO OUT_RCHDEPTLIST ");
			selectQuery6.append("\n SELECT '" + sessionId + "', SEQ, GRPCD, GRPNM, GRPGBN ");
			selectQuery6.append("\n   FROM OUT_RCHDEPTLIST ");
			selectQuery6.append("\n  WHERE RCHNO = ? "); 
			pstmt = con.prepareStatement(selectQuery6.toString());
			pstmt.setInt(1, rchno);
			
			result = result + pstmt.executeUpdate();			
			if (pstmt != null){
				try {
					pstmt.close();
					pstmt = null;
				} catch(SQLException ignored){ }
			}
			
			//첨부파일 복사
			result += copyRchSubExamFile(con, sessionId, rchno, context, saveDir, "VIEW");
			
			con.commit();
		} catch (Exception e) {
			con.rollback();
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt);
			throw e;
	     } finally {	
//	    	 con.setAutoCommit(true);
	    	 ConnectionManager.close(con,pstmt);	   
	     }	
		//logger.info("out rchChoice end");
		return result;
	}
	
	/**
	 * 설문조사 첨부파일 복사
	 * @param conn
	 * @param sessionId
	 * @param rchno
	 * @param context
	 * @param saveDir
	 * @param mode	( VIEW : 설문보기, SAVE : 설문저장 ) 
	 * @return
	 * @throws Exception
	 */
	public int copyRchSubExamFile(Connection conn, String sessionId, int rchno, ServletContext context, String saveDir, String mode) throws Exception {
		int result = 0;
		
		List rchSubFile = null;
		if ( mode.equals("VIEW") ) {
			rchSubFile = getRchSubFile(conn, "", rchno);
		} else if ( mode.equals("SAVE") ) {
			rchSubFile = getRchSubFile(conn, sessionId, 0);
		}
		for ( int i = 0; rchSubFile != null && i < rchSubFile.size(); i++ ) {
			ResearchSubBean rchSubBean = (ResearchSubBean)rchSubFile.get(i);
			
			String newFilenm = "";
			try {
				newFilenm = FileManager.doFileCopy(context.getRealPath(rchSubBean.getFilenm()));
			} catch (FileNotFoundException e) {
				continue;
			}
			
			if( newFilenm.equals("") == false ) {
				FileBean subFileBean = new FileBean();
				subFileBean.setFileseq(rchSubBean.getFileseq());
				subFileBean.setFilenm(saveDir + newFilenm);
				subFileBean.setOriginfilenm(rchSubBean.getOriginfilenm());
				subFileBean.setFilesize(rchSubBean.getFilesize());
				subFileBean.setExt(rchSubBean.getExt());
				
				int addResult = 0;
				if ( mode.equals("VIEW") ) {
					addResult = addRchSubFile(conn, sessionId, 0, rchSubBean.getFormseq(), subFileBean);
				} else if ( mode.equals("SAVE") ) {
					addResult = addRchSubFile(conn, "", rchno, rchSubBean.getFormseq(), subFileBean);
				}
				if ( addResult == 0 ) {
					throw new Exception("첨부실패(저장):DB업데이트");
				}
				result += addResult;
			} else {
				throw new Exception("첨부실패(저장):파일업로드");
			}
		}
		 
		List rchExamFile = null;
		if ( mode.equals("VIEW") ) {
			rchExamFile = getRchExamFile(conn, "", rchno, 0);
		} else if ( mode.equals("SAVE") ) {
			rchExamFile = getRchExamFile(conn, sessionId, 0, 0);
		}
		for ( int i = 0; rchExamFile != null && i < rchExamFile.size(); i++ ) {
			ResearchExamBean rchExamBean = (ResearchExamBean)rchExamFile.get(i);
			
			String newFilenm = "";
			try {
				newFilenm = FileManager.doFileCopy(context.getRealPath(rchExamBean.getFilenm()));
			} catch (FileNotFoundException e) {
				continue;
			}
			
			if( newFilenm.equals("") == false ) {
				FileBean subFileBean = new FileBean();
				subFileBean.setFileseq(rchExamBean.getFileseq());
				subFileBean.setFilenm(saveDir + newFilenm);
				subFileBean.setOriginfilenm(rchExamBean.getOriginfilenm());
				subFileBean.setFilesize(rchExamBean.getFilesize());
				subFileBean.setExt(rchExamBean.getExt());
				
				int addResult = 0;
				if ( mode.equals("VIEW") ) {
					addResult = addRchExamFile(conn, sessionId, 0, rchExamBean.getFormseq(), rchExamBean.getExamseq(), subFileBean);
				} else if ( mode.equals("SAVE") ) {
					addResult = addRchExamFile(conn, "", rchno, rchExamBean.getFormseq(), rchExamBean.getExamseq(), subFileBean);
				}
				if ( addResult == 0 ) {
					throw new Exception("첨부실패(저장):DB업데이트");
				}
				result += addResult;
			} else {
				throw new Exception("첨부실패(저장):파일업로드");
			}
		}
		
		return result;
	}
	
	/**
	 * 설문조사 임시테이블 삭제
	 * @param sessionid
	 * @throws Exception
	 */
	public void delResearchTempData(Connection conn, String sessionid) throws Exception {

		PreparedStatement pstmt = null;
		
		try {			
			String[] table = {"OUT_RCHMST", "OUT_RCHSUB", "OUT_RCHEXAM",
							  "OUT_RCHDEPT", "OUT_RCHDEPTLIST"};
			
			for(int i = 0; i < table.length; i++) {
				
				String sql = 
					"DELETE " +
					"FROM " + table[i] + " " +
					"WHERE SESSIONID LIKE ?";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, sessionid);
				pstmt.executeUpdate();
				ConnectionManager.close(pstmt);
			
			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {
			ConnectionManager.close(pstmt);
		}
	}
	
	/**
	 * 설문조사 임시테이블 삭제
	 * @param sessionid
	 * @throws Exception
	 */
	public int ResearchDlete(Connection conn, int rchno) throws Exception {
		
		PreparedStatement pstmt = null;
		
		int result= 0;
		
		try {
			conn = ConnectionManager.getConnection();
			
			String[] table = {"OUT_RCHMST", "OUT_RCHSUB", "OUT_RCHEXAM", "OUT_RCHANS",
							  "OUT_RCHDEPT", "OUT_RCHDEPTLIST",};
			
			for(int i = 0; i < table.length; i++) {
				
				String sql = 
					"DELETE " +
					"FROM " + table[i] + " " +
					"WHERE RCHNO LIKE ?";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, rchno);
				result += pstmt.executeUpdate();
				ConnectionManager.close(pstmt);

			}
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt);
			throw e;
		} finally {
			ConnectionManager.close(conn,pstmt);
		}
		
		return result;
	}
	
	/**
	 * 설문 마감 	
	 */
	public int rchClose(int rchno, String userid) throws Exception {
		Connection con = null;        
		PreparedStatement pstmt = null;
		int result = 0;
				
		try{
			//설문을 마감할 경우 하루전 날짜로 셋팅한다.
			StringBuffer updateQuery = new StringBuffer();
			
			updateQuery.append("UPDATE OUT_RCHMST SET ENDDT = TO_CHAR(SYSDATE - 1, 'YYYY-MM-DD') ");
			updateQuery.append("WHERE RCHNO = ? ");
			updateQuery.append("  AND CHRGUSRID = ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(updateQuery.toString());
			
			pstmt.setInt(1, rchno);
			pstmt.setString(2, userid);
				
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {		
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt);
			 throw e;
		} finally {	       
	    	 ConnectionManager.close(con,pstmt);   
		}
		return result;
	}
	
	/**
	 * 
	 * @param usrid
	 * @param deptcd
	 * @param schtitle
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List getRchOutsideList() throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List list = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append(" SELECT T1.RCHNO  " +
							   "   FROM OUT_RCHMST T1 " +
							   "  WHERE T1.OPENTYPE = '1'" +
							   "	AND T1.RANGE LIKE '2%' \n");
			selectQuery.append("    AND TO_CHAR(SYSDATE,'YYYY-MM-DD')BETWEEN T1.STRDT AND T1.ENDDT \n");	
			selectQuery.append("  ORDER BY CRTDT DESC \n");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
									
			rs = pstmt.executeQuery();
			
			list = new ArrayList();
			
			while (rs.next()) {
				ResearchAnsBean Bean = new ResearchAnsBean();
				Bean.setRchno(rs.getInt("RCHNO"));
				
				list.add(Bean);
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}		
		return list;
	}
	
	/**
	 * 문항당 보기개수 가져오기
	 * @param rchno
	 * @return
	 * @throws Exception
	 */
	public int getRchSubExamcount(int rchno, String sessionId) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			if ( rchno == 0 ) {
				selectQuery.append("SELECT MAX(COUNT(EXAMSEQ)) \n");
				selectQuery.append("FROM OUT_RCHEXAM \n");
				selectQuery.append("WHERE SESSIONID LIKE ? \n");
				selectQuery.append("GROUP BY FORMSEQ \n");
			} else {
				selectQuery.append("SELECT MAX(COUNT(EXAMSEQ)) \n");
				selectQuery.append("FROM OUT_RCHEXAM \n");
				selectQuery.append("WHERE RCHNO = ? \n");
				selectQuery.append("GROUP BY FORMSEQ \n");
			}
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if ( rchno == 0) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, rchno);
			}
									
			rs = pstmt.executeQuery();
						
			if ( rs.next() ) {
				result = rs.getInt(1);
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
///////////////////////////////////////////////////////////
/////////////////////// 설문조사 DAO 끝 //////////////////////
///////////////////////////////////////////////////////////

	
/////////////////////////////////////////////////////////////
/////////////////////// 신청서 DAO 시작 ////////////////////////
/////////////////////////////////////////////////////////////
	/**
	 * 신청문항첨부파일 가져오기
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param formseq
	 * @return
	 * @throws Exception
	 */
	public List getReqFormSubFile(Connection conn, String sessionId, int reqformno) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( reqformno == 0 ) {
				sql.append("SELECT SESSIONID, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM OUT_REQFORMSUBFILE \n");
				sql.append("WHERE SESSIONID LIKE ? \n");
			} else {
				sql.append("SELECT REQFORMNO, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM OUT_REQFORMSUBFILE \n");
				sql.append("WHERE REQFORMNO=? \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( reqformno == 0 ) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, reqformno);
			}
			
			rs = pstmt.executeQuery();
			
			while ( rs.next() == true ) {
				ArticleBean atcBean = new ArticleBean();
				
				atcBean.setSessi(sessionId);
				atcBean.setReqformno(reqformno);
				atcBean.setFormseq(rs.getInt("FORMSEQ"));
				atcBean.setFileseq(rs.getInt("FILESEQ"));
				atcBean.setFilenm(rs.getString("FILENM"));
				atcBean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				atcBean.setFilesize(rs.getInt("FILESIZE"));
				atcBean.setExt(rs.getString("EXT"));				
				atcBean.setOrd(rs.getInt("ORD"));				
				
				if ( result == null ) {
					result = new ArrayList();
				}
				result.add(atcBean);
			}
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt, rs);
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt, rs);
		}
		
		return result;
	}

	/**
	 * 신청문항보기첨부파일 가져오기
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param formseq
	 * @param examseq
	 * @return
	 * @throws Exception
	 */
	public List getReqFormExamFile(Connection conn, String sessionId, int reqformno, int formseq) throws Exception {
	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( reqformno == 0 ) {
				sql.append("SELECT SESSIONID, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM OUT_REQFORMEXAMFILE \n");
				sql.append("WHERE SESSIONID LIKE ? \n");
			} else {
				sql.append("SELECT REQFORMNO, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM OUT_REQFORMEXAMFILE \n");
				sql.append("WHERE REQFORMNO=? \n");
			}
			if ( formseq != 0 ) {
				sql.append("AND FORMSEQ=? \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( reqformno == 0 ) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, reqformno);
			}
			if ( formseq != 0 ) {
				pstmt.setInt(2, formseq);
			}
			
			rs = pstmt.executeQuery();
			
			while ( rs.next() == true ) {
				SampleBean spBean = new SampleBean();
				
				spBean.setSessi(sessionId);
				spBean.setReqformno(reqformno);
				spBean.setFormseq(rs.getInt("FORMSEQ"));
				spBean.setExamseq(rs.getInt("EXAMSEQ"));
				spBean.setFileseq(rs.getInt("FILESEQ"));
				spBean.setFilenm(rs.getString("FILENM"));
				spBean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				spBean.setFilesize(rs.getInt("FILESIZE"));
				spBean.setExt(rs.getString("EXT"));				
				spBean.setOrd(rs.getInt("ORD"));
				
				if ( result == null ) {
					result = new ArrayList();
				}
				result.add(spBean);
			}
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt, rs);
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 신청내역첨부파일 가져오기
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param reqseq
	 * @param formseq
	 * @return
	 * @throws Exception
	 */
	public List getReqMstFile(Connection conn, int reqformno) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT REQFORMNO, REQSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
			sql.append("FROM OUT_REQMSTFILE \n");
			sql.append("WHERE REQFORMNO=? \n");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, reqformno);
			
			rs = pstmt.executeQuery();
			
			while ( rs.next() == true ) {
				ReqMstBean bean = new ReqMstBean();

				bean.setReqformno(reqformno);
				bean.setReqseq(rs.getInt("REQSEQ"));
				bean.setFileseq(rs.getInt("FILESEQ"));
				bean.setFilenm(rs.getString("FILENM"));
				bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				bean.setFilesize(rs.getInt("FILESIZE"));
				bean.setExt(rs.getString("EXT"));				
				bean.setOrd(rs.getInt("ORD"));				
				
				if ( result == null ) {
					result = new ArrayList();
				}
				result.add(bean);
			}
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt, rs);
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 보기파일 삭제 후 순서정렬(1,3,5->1,2,3)
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param formseq
	 * @param examseq
	 * @return
	 * @throws Exception
	 */
	public int setOrderReqFormExamFile(Connection conn, String sessionId, int reqformno, int formseq) throws Exception {
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( reqformno == 0 ) {
				sql.append("UPDATE OUT_REQFORMEXAMFILE R \n");
				sql.append("SET R.EXAMSEQ = (SELECT NEWEXAMSEQ \n");
				sql.append("                 FROM (SELECT ROWNUM NEWEXAMSEQ, EXAMSEQ \n");
				sql.append("                       FROM (SELECT SESSIONID, FORMSEQ, EXAMSEQ \n");
				sql.append("                             FROM OUT_REQFORMEXAMFILE \n");
				sql.append("                             WHERE SESSIONID LIKE ? \n");
				sql.append("                             AND FORMSEQ = ? \n");
				sql.append("                             ORDER BY EXAMSEQ)) \n");
				sql.append("                 WHERE EXAMSEQ = R.EXAMSEQ) \n");
				sql.append("WHERE SESSIONID LIKE ? \n");
				sql.append("AND FORMSEQ = ? \n");
				sql.append("AND EXAMSEQ NOT IN (SELECT EXAMSEQ \n");
				sql.append("                    FROM OUT_REQFORMEXAM \n");
				sql.append("                    WHERE SESSIONID LIKE ? \n"); 
				sql.append("                    AND FORMSEQ = ?) \n");
			} else {
				sql.append("UPDATE OUT_REQFORMEXAMFILE R \n");
				sql.append("SET R.EXAMSEQ = (SELECT NEWEXAMSEQ \n");
				sql.append("                 FROM (SELECT ROWNUM NEWEXAMSEQ, EXAMSEQ \n");
				sql.append("                       FROM (SELECT REQFORMNO, FORMSEQ, EXAMSEQ \n");
				sql.append("                             FROM OUT_REQFORMEXAMFILE \n");
				sql.append("                             WHERE REQFORMNO = ? \n");
				sql.append("                             AND FORMSEQ = ? \n");
				sql.append("                             ORDER BY EXAMSEQ)) \n");
				sql.append("                 WHERE EXAMSEQ = R.EXAMSEQ) \n");
				sql.append("WHERE REQFORMNO = ? \n");
				sql.append("AND FORMSEQ = ? \n");
				sql.append("AND EXAMSEQ NOT IN (SELECT EXAMSEQ \n");
				sql.append("                    FROM OUT_REQFORMEXAM \n");
				sql.append("                    WHERE REQFORMNO = ? \n"); 
				sql.append("                    AND FORMSEQ = ?) \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( reqformno == 0 ) {
				pstmt.setString(1, sessionId);
				pstmt.setInt(2, formseq);
				pstmt.setString(3, sessionId);
				pstmt.setInt(4, formseq);
				pstmt.setString(5, sessionId);
				pstmt.setInt(6, formseq);
			} else {
				pstmt.setInt(1, reqformno);
				pstmt.setInt(2, formseq);
				pstmt.setInt(3, reqformno);
				pstmt.setInt(4, formseq);
				pstmt.setInt(5, reqformno);
				pstmt.setInt(6, formseq);
			}
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt);
		}
		
		return result;
	}
	
	/**
	 * 사용하지않는 신청문항보기첨부파일 가져오기
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param formseq
	 * @param examseq
	 * @return
	 * @throws Exception
	 */
	public List getReqFormExamUnusedFile(Connection conn, String sessionId, int reqformno, int formseq) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( reqformno == 0 ) {
				sql.append("SELECT SESSIONID, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM OUT_REQFORMEXAMFILE R \n");
				sql.append("WHERE SESSIONID LIKE ? \n");
				sql.append("AND FORMSEQ=? \n");
				sql.append("AND EXAMSEQ NOT IN (SELECT EXAMSEQ \n");
				sql.append("                    FROM  OUT_REQFORMEXAM \n");
				sql.append("                    WHERE SESSIONID LIKE R.SESSIONID \n");
				sql.append("                    AND FORMSEQ=R.FORMSEQ) \n");
			} else {
				sql.append("SELECT REQFORMNO, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM OUT_REQFORMEXAMFILE R \n");
				sql.append("WHERE REQFORMNO=? \n");
				sql.append("AND FORMSEQ=? \n");
				sql.append("AND EXAMSEQ NOT IN (SELECT EXAMSEQ \n");
				sql.append("                    FROM  OUT_REQFORMEXAM \n");
				sql.append("                    WHERE REQFORMNO=R.REQFORMNO \n");
				sql.append("                    AND FORMSEQ=R.FORMSEQ) \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( reqformno == 0 ) {
				pstmt.setString(1, sessionId);
				pstmt.setInt(2, formseq);
			} else {
				pstmt.setInt(1, reqformno);
				pstmt.setInt(2, formseq);
			}
			
			rs = pstmt.executeQuery();
			
			while ( rs.next() == true ) {
				SampleBean spBean = new SampleBean();
				
				spBean.setSessi(sessionId);
				spBean.setReqformno(reqformno);
				spBean.setFormseq(rs.getInt("FORMSEQ"));
				spBean.setExamseq(rs.getInt("EXAMSEQ"));
				spBean.setFileseq(rs.getInt("FILESEQ"));
				spBean.setFilenm(rs.getString("FILENM"));
				spBean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				spBean.setFilesize(rs.getInt("FILESIZE"));
				spBean.setExt(rs.getString("EXT"));				
				spBean.setOrd(rs.getInt("ORD"));
				
				if ( result == null ) {
					result = new ArrayList();
				}
				result.add(spBean);
			}
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt, rs);
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 신청문항첨부파일 가져오기
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param formseq
	 * @return
	 * @throws Exception
	 */
	public ArticleBean getReqFormSubFile(Connection conn, String sessionId, int reqformno, int formseq) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArticleBean result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( reqformno == 0 ) {
				sql.append("SELECT SESSIONID, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM OUT_REQFORMSUBFILE \n");
				sql.append("WHERE SESSIONID LIKE ? \n");
				sql.append("AND FORMSEQ=? \n");
			} else {
				sql.append("SELECT REQFORMNO, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM OUT_REQFORMSUBFILE \n");
				sql.append("WHERE REQFORMNO=? \n");
				sql.append("AND FORMSEQ=? \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( reqformno == 0 ) {
				pstmt.setString(1, sessionId);
				pstmt.setInt(2, formseq);
			} else {
				pstmt.setInt(1, reqformno);
				pstmt.setInt(2, formseq);
			}
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() == true ) {
				result = new ArticleBean();
				
				result.setSessi(sessionId);
				result.setReqformno(reqformno);
				result.setFormseq(rs.getInt("FORMSEQ"));
				result.setFileseq(rs.getInt("FILESEQ"));
				result.setFilenm(rs.getString("FILENM"));
				result.setOriginfilenm(rs.getString("ORIGINFILENM"));
				result.setFilesize(rs.getInt("FILESIZE"));
				result.setExt(rs.getString("EXT"));				
				result.setOrd(rs.getInt("ORD"));
			}
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt, rs);
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 신청문항보기첨부파일 가져오기
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param formseq
	 * @param examseq
	 * @return
	 * @throws Exception
	 */
	public SampleBean getReqFormExamFile(Connection conn, String sessionId, int reqformno, int formseq, int examseq) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SampleBean result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( reqformno == 0 ) {
				sql.append("SELECT SESSIONID, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM OUT_REQFORMEXAMFILE \n");
				sql.append("WHERE SESSIONID LIKE ? \n");
				sql.append("AND FORMSEQ=? \n");
				sql.append("AND EXAMSEQ=? \n");
			} else {
				sql.append("SELECT REQFORMNO, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
				sql.append("FROM OUT_REQFORMEXAMFILE \n");
				sql.append("WHERE REQFORMNO=? \n");
				sql.append("AND FORMSEQ=? \n");
				sql.append("AND EXAMSEQ=? \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( reqformno == 0 ) {
				pstmt.setString(1, sessionId);
				pstmt.setInt(2, formseq);
				pstmt.setInt(3, examseq);
			} else {
				pstmt.setInt(1, reqformno);
				pstmt.setInt(2, formseq);
				pstmt.setInt(3, examseq);
			}
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() == true ) {
				result = new SampleBean();
				
				result.setSessi(sessionId);
				result.setReqformno(reqformno);
				result.setFormseq(rs.getInt("FORMSEQ"));
				result.setExamseq(rs.getInt("EXAMSEQ"));
				result.setFileseq(rs.getInt("FILESEQ"));
				result.setFilenm(rs.getString("FILENM"));
				result.setOriginfilenm(rs.getString("ORIGINFILENM"));
				result.setFilesize(rs.getInt("FILESIZE"));
				result.setExt(rs.getString("EXT"));				
				result.setOrd(rs.getInt("ORD"));
			}
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt, rs);
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 신청문항첨부파일 가져오기
	 * @param conn
	 * @param reqformno
	 * @param reqseq
	 * @return
	 * @throws Exception
	 */
	public ReqMstBean getReqMstFile(Connection conn, int reqformno, int reqseq) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ReqMstBean result = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT REQFORMNO, REQSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
			sql.append("FROM OUT_REQMSTFILE \n");
			sql.append("WHERE REQFORMNO=? \n");
			sql.append("AND REQSEQ=? \n");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, reqformno);
			pstmt.setInt(2, reqseq);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() == true ) {
				result = new ReqMstBean();
				
				result.setReqformno(reqformno);
				result.setReqseq(rs.getInt("REQSEQ"));
				result.setFileseq(rs.getInt("FILESEQ"));
				result.setFilenm(rs.getString("FILENM"));
				result.setOriginfilenm(rs.getString("ORIGINFILENM"));
				result.setFilesize(rs.getInt("FILESIZE"));
				result.setExt(rs.getString("EXT"));				
				result.setOrd(rs.getInt("ORD"));
			}
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt, rs);
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 신청문항첨부파일 추가
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param formseq
	 * @param fileBean
	 * @return
	 * @throws Exception
	 */
	public int addReqFormSubFile(Connection conn, String sessionId, int reqformno, int formseq, FileBean fileBean) throws Exception {

		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( reqformno == 0 ) {
				sql.append("INSERT INTO \n");
				sql.append("OUT_REQFORMSUBFILE(SESSIONID, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD) \n");
				sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?) \n");
			} else {				
				sql.append("INSERT INTO \n");
				sql.append("OUT_REQFORMSUBFILE(REQFORMNO, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD) \n");
				sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?) \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( reqformno == 0 ) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, reqformno);
			}
			pstmt.setInt(2, formseq);
			pstmt.setInt(3, fileBean.getFileseq());
			pstmt.setString(4, fileBean.getFilenm());
			pstmt.setString(5, fileBean.getOriginfilenm());
			pstmt.setInt(6, fileBean.getFilesize());
			pstmt.setString(7, fileBean.getExt());
			pstmt.setInt(8, fileBean.getFileseq());
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt);
		}
		
		return result;
	}
	
	/**
	 * 신청문항보기첨부파일 추가
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param formseq
	 * @param examseq
	 * @param fileBean
	 * @return
	 * @throws Exception
	 */
	public int addReqFormExamFile(Connection conn, String sessionId, int reqformno, int formseq, int examseq, FileBean fileBean) throws Exception {
		
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( reqformno == 0 ) {
				sql.append("INSERT INTO \n");
				sql.append("OUT_REQFORMEXAMFILE(SESSIONID, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD) \n");
				sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) \n");
			} else {
				sql.append("INSERT INTO \n");
				sql.append("OUT_REQFORMEXAMFILE(REQFORMNO, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD) \n");
				sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) \n");
			}
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( reqformno == 0 ) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, reqformno);
			}
			pstmt.setInt(2, formseq);
			pstmt.setInt(3, examseq);
			pstmt.setInt(4, fileBean.getFileseq());
			pstmt.setString(5, fileBean.getFilenm());
			pstmt.setString(6, fileBean.getOriginfilenm());
			pstmt.setInt(7, fileBean.getFilesize());
			pstmt.setString(8, fileBean.getExt());
			pstmt.setInt(9, fileBean.getFileseq());
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt);
		}
		
		return result;
	}
	
	/**
	 * 신청내역첨부파일 추가
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param formseq
	 * @param fileBean
	 * @return
	 * @throws Exception
	 */
	public int addReqAnsFile(Connection conn, int reqformno, int reqseq, FileBean fileBean) throws Exception {

		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
							
			sql.append("INSERT INTO \n");
			sql.append("OUT_REQMSTFILE(REQFORMNO, REQSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD) \n");
			sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?) \n");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, reqformno);
			pstmt.setInt(2, reqseq);
			pstmt.setInt(3, fileBean.getFileseq());
			pstmt.setString(4, fileBean.getFilenm());
			pstmt.setString(5, fileBean.getOriginfilenm());
			pstmt.setInt(6, fileBean.getFilesize());
			pstmt.setString(7, fileBean.getExt());
			pstmt.setInt(8, fileBean.getFileseq());
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt);
		}
		
		return result;
	}
	
	/**
	 * 신청문항첨부파일 삭제
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param formseq
	 * @param fileseq
	 * @return
	 * @throws Exception
	 */
	public int delReqFormSubFile(Connection conn, String sessionId, int reqformno, int formseq, int fileseq) throws Exception {

		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( reqformno == 0 ) {
				sql.append("DELETE FROM OUT_REQFORMSUBFILE \n");
				sql.append("WHERE SESSIONID LIKE ? AND FORMSEQ=? AND FILESEQ=? \n");
			} else {
				sql.append("DELETE FROM OUT_REQFORMSUBFILE \n");
				sql.append("WHERE REQFORMNO=? AND FORMSEQ=? AND FILESEQ=? \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( reqformno == 0 ) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, reqformno);
			}
			pstmt.setInt(2, formseq);
			pstmt.setInt(3, fileseq);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt);
		}
		
		return result;
	}
	
	/**
	 * 신청문항보기첨부파일 삭제
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param formseq
	 * @param examseq
	 * @param fileseq
	 * @return
	 * @throws Exception
	 */
	public int delReqFormExamFile(Connection conn, String sessionId, int reqformno, int formseq, int examseq, int fileseq) throws Exception {
		
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			if ( reqformno == 0 ) {
				sql.append("DELETE FROM OUT_REQFORMEXAMFILE \n");
				sql.append("WHERE SESSIONID LIKE ? AND FORMSEQ=? AND EXAMSEQ=? AND FILESEQ=? \n");
			} else {
				sql.append("DELETE FROM OUT_REQFORMEXAMFILE \n");
				sql.append("WHERE REQFORMNO=? AND FORMSEQ=? AND EXAMSEQ=? AND FILESEQ=? \n");
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if ( reqformno == 0 ) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, reqformno);
			}
			pstmt.setInt(2, formseq);
			pstmt.setInt(3, examseq);
			pstmt.setInt(4, fileseq);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt);
		}
		
		return result;
	}
	
	/**
	 * 신청내역첨부파일 삭제
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param formseq
	 * @param fileseq
	 * @return
	 * @throws Exception
	 */
	public int delReqMstFile(Connection conn, int reqformno, int reqseq, int fileseq) throws Exception {

		PreparedStatement pstmt = null;
		
		int result = 0;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			sql.append("DELETE FROM OUT_REQMSTFILE \n");
			sql.append("WHERE REQFORMNO=? AND REQSEQ=? AND FILESEQ=? \n");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, reqformno);
			pstmt.setInt(2, reqseq);
			pstmt.setInt(3, fileseq);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error("ERROR",e);
			ConnectionManager.close(pstmt);
			throw e;
		} finally {	       
			ConnectionManager.close(pstmt);
		}
		
		return result;
	}
	
	/**
	 * 메인화면에 보여줄 목록 가져오기
	 */
	public List mainShowList() throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List reqList = null;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT BUNHO, REQFORMNO, TITLE, STRDT, ENDDT, CRTDT ");			
			selectQuery.append("FROM (SELECT ROWNUM BUNHO, REQFORMNO, TITLE, ");
			selectQuery.append("             REPLACE(SUBSTR(STRDT,6),'-','/') STRDT, REPLACE(SUBSTR(ENDDT,6),'-','/') ENDDT, CRTDT ");
			selectQuery.append("	  FROM (SELECT REQFORMNO, TITLE, STRDT, ENDDT, CRTDT ");
			selectQuery.append("            FROM OUT_REQFORMMST ");
			selectQuery.append("            WHERE COLDEPTCD IN (SELECT DEPT_ID FROM DEPT ");
			selectQuery.append("								CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID ");
			selectQuery.append("								START WITH DEPT_ID = ? ) ");
			selectQuery.append("              AND STRDT <= TO_CHAR(SYSDATE,'YYYY-MM-DD') ");
			selectQuery.append("              AND (ENDDT >= TO_CHAR(SYSDATE,'YYYY-MM-DD') OR ENDDT IS NULL) ");
			selectQuery.append("              AND RANGE = '1' ");
			selectQuery.append("            ORDER BY CRTDT DESC) ");
			selectQuery.append("      ) ");
			selectQuery.append("WHERE BUNHO BETWEEN 1 AND 5 ");
			
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
						
			pstmt.setString(1, appInfo.getRootid());
			rs = pstmt.executeQuery();
			
			reqList = new ArrayList();
			
			while (rs.next()) {
				FrmBean bean = new FrmBean();						
				
				bean.setReqformno(rs.getInt("REQFORMNO"));
				bean.setTitle(rs.getString("TITLE"));
				bean.setStrdt(rs.getString("STRDT"));
				bean.setEnddt(rs.getString("ENDDT"));
				reqList.add(bean);				
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return reqList;
	}
	
	/**
	 * 신청이 진행중인 건이 있는지 확인한다.
	 * gbn(1): 결재전, gbn(2):결재후
	 * 양식 수정여부를 확인하기 위해서
	 */
	public int reqMstCnt(int reqformno, String gbn) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(REQFORMNO) ");
			selectQuery.append("FROM OUT_REQMST ");
			selectQuery.append("WHERE REQFORMNO = ? ");
			
			if("1".equals(gbn)){
				selectQuery.append("  AND STATE = '01' ");
			} else {
				selectQuery.append("  AND STATE <> '01' ");
			}			
			//logger.info(selectQuery);
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, reqformno);
								
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				result = rs.getInt(1);			
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
	 * 신청 진행중(접수중)인 신청서 건수
	 */
	public int jupsuCnt(String userid) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(REQFORMNO) ");
			selectQuery.append("FROM OUT_REQFORMMST ");
			selectQuery.append("WHERE STRDT <= TO_CHAR(SYSDATE,'YYYY-MM-DD') ");
			selectQuery.append("  AND (ENDDT >= TO_CHAR(SYSDATE,'YYYY-MM-DD') OR ENDDT IS NULL) ");
			selectQuery.append("  AND CHRGUSRID = ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, userid);
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				result = rs.getInt(1);			
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
	 * 미처리된 접수 건수
	 */
	public int notProcCnt(String userid) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(A.REQFORMNO) ");
			selectQuery.append("FROM OUT_REQMST A, OUT_REQFORMMST B ");
			selectQuery.append("WHERE A.REQFORMNO = B.REQFORMNO ");
			selectQuery.append("  AND B.STRDT <= TO_CHAR(SYSDATE,'YYYY-MM-DD') ");
			selectQuery.append("  AND (B.ENDDT >= TO_CHAR(SYSDATE,'YYYY-MM-DD') OR B.ENDDT IS NULL) ");
			selectQuery.append("  AND A.STATE = '02' ");
			selectQuery.append("  AND B.CHRGUSRID = ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());			
			pstmt.setString(1, userid);
			
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				result = rs.getInt(1);			
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
	 * 신청하기 목록 가져오기
	 */
	public List doSinchungList(SearchBean search) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List doList = null;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT BUNHO, REQFORMNO, TITLE, STRDT, ENDDT ");
			selectQuery.append("FROM (SELECT ROWNUM BUNHO, REQFORMNO, TITLE, REPLACE(SUBSTR(STRDT,6),'-','/') STRDT, ");
			selectQuery.append("             REPLACE(SUBSTR(ENDDT,6),'-','/') ENDDT ");
			selectQuery.append("      FROM (SELECT REQFORMNO, TITLE,  STRDT, ENDDT ");
			selectQuery.append("            FROM OUT_REQFORMMST ");
			selectQuery.append("            WHERE COLDEPTCD IN (SELECT DEPT_ID FROM DEPT ");
			selectQuery.append("								CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID ");
			selectQuery.append("								START WITH DEPT_ID = ? ) "); 
			selectQuery.append("            AND STRDT <= TO_CHAR(SYSDATE,'YYYY-MM-DD') ");			
			selectQuery.append("            AND (ENDDT >= TO_CHAR(SYSDATE,'YYYY-MM-DD') OR ENDDT IS NULL) ");
			selectQuery.append("            AND TITLE LIKE '%"+search.getTitle()+"%' ");
			selectQuery.append("            AND RANGE = '1' ");
			selectQuery.append("            ORDER BY ENDDT DESC, CRTDT DESC) ");
			selectQuery.append("      ) ");
			selectQuery.append("WHERE BUNHO BETWEEN ? AND ? ");
			
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
						
			pstmt.setString(1, search.getDeptid());
			pstmt.setInt(2, search.getStart_idx());
			pstmt.setInt(3, search.getEnd_idx());
			
			rs = pstmt.executeQuery();
			
			doList = new ArrayList();
			
			while (rs.next()) {
				FrmBean bean = new FrmBean();
						
				bean.setBunho(rs.getInt("BUNHO"));
				bean.setReqformno(rs.getInt("REQFORMNO"));
				bean.setTitle(rs.getString("TITLE"));
				bean.setStrdt(rs.getString("STRDT"));
				bean.setEnddt(rs.getString("ENDDT"));
			
				doList.add(bean);				
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return doList;
	}
	
	/**
	 * 신청하기 전체 건수 가져오기
	 */
	public int doSinchungTotCnt(SearchBean search) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalCount = 0;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT COUNT(*) ");
			selectQuery.append("FROM OUT_REQFORMMST ");
			selectQuery.append("WHERE COLDEPTCD IN (SELECT DEPT_ID FROM DEPT ");
			selectQuery.append("					CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID ");
			selectQuery.append("					START WITH DEPT_ID = ? ) "); 
			selectQuery.append("AND STRDT <= TO_CHAR(SYSDATE,'YYYY-MM-DD') ");			
			selectQuery.append("AND (ENDDT >= TO_CHAR(SYSDATE,'YYYY-MM-DD') OR ENDDT IS NULL) ");				
			selectQuery.append("AND TITLE LIKE '%"+search.getTitle()+"%' ");
			selectQuery.append("AND RANGE = '1' ");
			
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, search.getDeptid());
			
			rs = pstmt.executeQuery();			
			
			if ( rs.next() ){
				totalCount = rs.getInt(1);
			}			
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return totalCount;
	}
	
	/**
	 * 내신청함 목록 가져오기
	 */
	public List mySinchungList(SearchBean search) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List myList = null;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT BUNHO, REQFORMNO, REQSEQ, TITLE, CRTDT, CCDNAME ");
			selectQuery.append("FROM (SELECT ROWNUM BUNHO, REQFORMNO, REQSEQ, TITLE, CRTDT, CCDNAME ");			
			selectQuery.append("      FROM (SELECT A.REQFORMNO, A.REQSEQ, B.TITLE, REPLACE(SUBSTR(A.CRTDT,6,11),'-','/') CRTDT, C.CCDNAME ");
			selectQuery.append("            FROM OUT_REQMST A, OUT_REQFORMMST B, (SELECT CCDSUBCD, CCDNAME FROM CCD WHERE CCDCD = '015') C ");
			selectQuery.append("            WHERE A.REQFORMNO = B.REQFORMNO ");
			selectQuery.append("              AND A.STATE = C.CCDSUBCD ");
			selectQuery.append("              AND A.CRTUSRID = ? ");
			selectQuery.append("              AND B.TITLE LIKE '%"+search.getTitle()+"%' ");
			
			//신청중인것만 검색
			if("0".equals(search.getGbn())){
				selectQuery.append("          AND A.STATE = '02' ");
			}
			
			selectQuery.append("            ORDER BY A.CRTDT DESC) ");
			selectQuery.append("      ) ");
			selectQuery.append("WHERE BUNHO BETWEEN ? AND ? ");
			
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
						
			pstmt.setString(1, search.getUserid());
			pstmt.setInt(2, search.getStart_idx());
			pstmt.setInt(3, search.getEnd_idx());
			
			rs = pstmt.executeQuery();
			
			myList = new ArrayList();
			
			while (rs.next()) {
				ReqMstBean bean = new ReqMstBean();
						
				bean.setBunho(rs.getInt("BUNHO"));
				bean.setReqformno(rs.getInt("REQFORMNO"));
				bean.setReqseq(rs.getInt("REQSEQ"));
				bean.setTitle(rs.getString("TITLE"));
				bean.setCrtdt(rs.getString("CRTDT"));
				bean.setStatenm(rs.getString("CCDNAME"));
						
				myList.add(bean);				
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return myList;
	}
	
	/**
	 * 내신청함 전체 건수 가져오기
	 */
	public int mySinchungTotCnt(SearchBean search) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalCount = 0;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT COUNT(A.REQFORMNO) ");
			selectQuery.append("FROM OUT_REQMST A, OUT_REQFORMMST B ");
			selectQuery.append("WHERE A.REQFORMNO = B.REQFORMNO ");		
			selectQuery.append("  AND A.CRTUSRID = ? ");
			selectQuery.append("  AND B.TITLE LIKE '%"+search.getTitle()+"%' ");
			
			//신청중인것만 검색
			if("0".equals(search.getGbn())){
				selectQuery.append("AND A.STATE = '02' ");
			}		
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, search.getUserid());
			
			rs = pstmt.executeQuery();			
			
			if ( rs.next() ){
				totalCount = rs.getInt(1);
			}			
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return totalCount;
	}
	
	/**
	 * 접수내역 목록 가져오기
	 */
	public List reqDataList(SearchBean search) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List dataList = null;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT BUNHO, REQFORMNO, REQSEQ, PRESENTNM, CRTDT, CCDNAME ");
			selectQuery.append("FROM (SELECT ROWNUM BUNHO, REQFORMNO, REQSEQ, PRESENTNM, REPLACE(CRTDT,'-','/') CRTDT, CCDNAME ");		
			selectQuery.append("      FROM (SELECT A.REQFORMNO, A.REQSEQ, A.PRESENTNM, SUBSTR(A.CRTDT,6,11) CRTDT, B.CCDNAME ");
			selectQuery.append("			FROM OUT_REQMST  A, (SELECT CCDSUBCD, CCDNAME FROM CCD WHERE CCDCD = '015') B ");
			selectQuery.append("			WHERE A.STATE = B.CCDSUBCD ");
			selectQuery.append("			  AND A.STATE <> '01' ");
			selectQuery.append("              AND A.PRESENTNM LIKE '%"+search.getPresentnm()+"%' ");
			
			if ("0".equals(search.getProcFL())){
				selectQuery.append("          AND A.STATE = '02' ");   //신청중
 			}
			selectQuery.append("              AND A.REQFORMNO = ? ");
			selectQuery.append("            ORDER BY A.REQSEQ DESC) ");
			selectQuery.append("      ) ");
			selectQuery.append("WHERE BUNHO BETWEEN ? AND ? ");
			
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
						
			pstmt.setInt(1, search.getReqformno());
			pstmt.setInt(2, search.getStart_idx());
			pstmt.setInt(3, search.getEnd_idx());
			
			rs = pstmt.executeQuery();
			
			dataList = new ArrayList();
			
			while (rs.next()) {
				ReqMstBean bean = new ReqMstBean();
				
				int reqformno = rs.getInt("REQFORMNO");
				int reqseq = rs.getInt("REQSEQ");
				
				bean.setBunho(rs.getInt("BUNHO"));
				bean.setReqformno(reqformno);
				bean.setReqseq(reqseq);
				bean.setPresentnm(rs.getString("PRESENTNM"));
				bean.setCrtdt(rs.getString("CRTDT"));
				bean.setLastsanc(lastSancName(reqformno, reqseq));
				bean.setStatenm(rs.getString("CCDNAME"));
			
				dataList.add(bean);				
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return dataList;
	}
	
	/**
	 * 접수내역 목록 전체 건수 가져오기
	 */
	public int reqDataTotCnt(SearchBean search) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalCount = 0;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT COUNT(REQSEQ) ");
			selectQuery.append("FROM OUT_REQMST ");
			selectQuery.append("WHERE STATE <> '01' ");
			selectQuery.append("  AND PRESENTNM LIKE '%"+search.getPresentnm()+"%' ");
			
			if ("0".equals(search.getProcFL())){
				selectQuery.append("AND STATE = '02' ");   //신청중
 			}		
			selectQuery.append("  AND REQFORMNO = ? ");
			
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, search.getReqformno());
			
			rs = pstmt.executeQuery();			
			
			if ( rs.next() ){
				totalCount = rs.getInt(1);
			}			
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return totalCount;
	}
	
	/**
	 * 신청 내역 가져오기
	 */
	public ReqMstBean reqDataInfo(int reqformno, int reqseq) throws Exception {
		Connection conn = null;
	    ResultSet rs = null;
		PreparedStatement pstmt = null;
		ReqMstBean bean = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT A.PRESENTNM, A.PRESENTID, A.PRESENTSN, ");
			selectQuery.append("       A.POSITION,  A.DUTY,      A.ZIP,       A.ADDR1, A.ADDR2, ");
			selectQuery.append("       A.EMAIL,     A.TEL,       A.CEL,       A.FAX,   B.CCDNAME, ");
			selectQuery.append("       A.STATE,     A.CRTDT,     A.CRTUSRID,  A.UPTDT, A.UPTUSRID, ");
			selectQuery.append("       C.FILESEQ, C.FILENM, C.ORIGINFILENM, C.FILESIZE, C.EXT, C.ORD ");
			selectQuery.append("FROM OUT_REQMST A, (SELECT CCDSUBCD, CCDNAME FROM CCD WHERE CCDCD = '015') B, OUT_REQMSTFILE C ");
			selectQuery.append("WHERE A.STATE = B.CCDSUBCD ");
			selectQuery.append("  AND A.REQFORMNO = C.REQFORMNO(+) ");
			selectQuery.append("  AND A.REQSEQ = C.REQSEQ(+) ");
			selectQuery.append("  AND A.REQFORMNO = ? ");
			selectQuery.append("  AND A.REQSEQ = ? ");
			
			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, reqformno);
			pstmt.setInt(2, reqseq);
	
			rs = pstmt.executeQuery();
			
			if(rs.next()) {				
				
				bean = new ReqMstBean();
				
				bean.setReqformno(reqformno);
				bean.setReqseq(reqseq);
				bean.setPresentnm(rs.getString("PRESENTNM"));
				bean.setPresentid(rs.getString("PRESENTID"));
				bean.setPresentsn(rs.getString("PRESENTSN"));
				bean.setPosition(rs.getString("POSITION"));
				bean.setDuty(rs.getString("DUTY"));
				bean.setZip(rs.getString("ZIP"));
				bean.setAddr1(rs.getString("ADDR1"));
				bean.setAddr2(rs.getString("ADDR2"));
				bean.setEmail(rs.getString("EMAIL"));
				bean.setTel(rs.getString("TEL"));
				bean.setCel(rs.getString("CEL"));
				bean.setFax(rs.getString("FAX"));
				bean.setStatenm(rs.getString("CCDNAME"));
				bean.setState(rs.getString("STATE"));
				bean.setCrtdt(rs.getString("CRTDT"));
				bean.setCrtusrid(rs.getString("CRTUSRID"));
				bean.setUptdt(rs.getString("UPTDT"));
				bean.setUptusrid(rs.getString("UPTUSRID"));
				bean.setFileseq(rs.getInt("FILESEQ"));
				bean.setFilenm(rs.getString("FILENM"));
				bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				bean.setFilesize(rs.getInt("FILESIZE"));
				bean.setExt(rs.getString("EXT"));				
				bean.setOrd(rs.getInt("ORD"));
				bean.setLastsanc(lastSancName(reqformno,reqseq));
				
				bean.setAnscontList(reqDataSubList(reqformno, reqseq));
			}			
	
		 } catch (SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return bean;
	}
	
	/**
	 * 신청내역 추가항목 가져오기
	 */
	public List reqDataSubList(int reqformno, int reqseq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List dataSubList = null;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT REQFORMNO, REQSEQ, FORMSEQ, ANSCONT, OTHER ");
			selectQuery.append("FROM OUT_REQSUB ");
			selectQuery.append("WHERE REQFORMNO = ? ");
			selectQuery.append("  AND REQSEQ = ? ");
			
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());						
			pstmt.setInt(1, reqformno);
			pstmt.setInt(2, reqseq);
			
			rs = pstmt.executeQuery();
			
			dataSubList = new ArrayList();
			
			while (rs.next()) {				
				
				ReqSubBean bean = new ReqSubBean();
				
				bean.setReqformno(rs.getInt("REQFORMNO"));
				bean.setReqseq(rs.getInt("REQSEQ"));
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setAnscont(rs.getString("ANSCONT"));
				bean.setOther(rs.getString("OTHER"));
				
				dataSubList.add(bean);				
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return dataSubList;
	}

	/**
	 * 신청서 관리목록 가져오기
	 */
	public List reqFormList(SearchBean search) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List reqList = null;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT BUNHO, REQFORMNO, TITLE, STRDT, ENDDT, TDAY, RANGE, STATE, CRTDT ");
			selectQuery.append("FROM (SELECT ROWNUM BUNHO, REQFORMNO, TITLE, REPLACE(SUBSTR(STRDT,6),'-','/') STRDT, ");
			selectQuery.append("             REPLACE(SUBSTR(ENDDT,6),'-','/') ENDDT, TRUNC(TO_DATE(ENDDT,'YYYY-MM-DD')-SYSDATE) TDAY, RANGE, STATE, CRTDT ");
			selectQuery.append("      FROM (SELECT A.REQFORMNO, MAX(A.TITLE) TITLE, MAX(A.STRDT) STRDT, MAX(A.ENDDT) ENDDT, MAX(A.RANGE) RANGE, ");
			selectQuery.append("       		       SUM(DECODE(B.STATE,'02',1,0)) STATE," +
					           "                   A.CRTDT ");
			selectQuery.append("            FROM OUT_REQFORMMST A, OUT_REQMST B	");
			selectQuery.append("            WHERE A.REQFORMNO = B.REQFORMNO(+) ");
			selectQuery.append("              AND A.TITLE LIKE '%"+search.getTitle()+"%' ");
			selectQuery.append("              AND A.COLDEPTCD = '"+search.getDeptid()+"'");
			selectQuery.append("              AND A.CHRGUSRID = '"+search.getUserid()+"'");
					
			if ("0".equals(search.getProcFL())){
				selectQuery.append("          AND B.STATE = '02' ");   //신청중
 			}
			
			selectQuery.append("            GROUP BY A.REQFORMNO, A.CRTDT) "); 
			selectQuery.append("      ) ");
			selectQuery.append("WHERE BUNHO BETWEEN ? AND ? ");
			selectQuery.append("ORDER BY ENDDT DESC, CRTDT DESC ");
			
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
						
			pstmt.setInt(1, search.getStart_idx());
			pstmt.setInt(2, search.getEnd_idx());
			
			rs = pstmt.executeQuery();
			
			reqList = new ArrayList();
			
			while (rs.next()) {
				FrmBean bean = new FrmBean();
						
				bean.setBunho(rs.getInt("BUNHO"));
				bean.setReqformno(rs.getInt("REQFORMNO"));
				bean.setTitle(rs.getString("TITLE"));
				bean.setStrdt(rs.getString("STRDT"));
				bean.setEnddt(rs.getString("ENDDT"));
				bean.setTday(rs.getInt("TDAY"));
				bean.setRange(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 1));
				bean.setRangedetail(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 2));
				bean.setNotproc(rs.getInt("STATE"));
				reqList.add(bean);				
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return reqList;
	}
	
	/**
	 * 신청서 관리 전체 건수 가져오기
	 */
	public int reqFormTotCnt(SearchBean search) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalCount = 0;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT COUNT(*) ");
			selectQuery.append("FROM (SELECT A.REQFORMNO ");
			selectQuery.append("      FROM OUT_REQFORMMST	A, OUT_REQMST B	");
			selectQuery.append("      WHERE A.REQFORMNO = B.REQFORMNO(+) ");
			selectQuery.append("        AND TITLE LIKE '%"+search.getTitle()+"%' ");
			selectQuery.append("        AND COLDEPTCD = '"+search.getDeptid()+"'");
			selectQuery.append("        AND CHRGUSRID = '"+search.getUserid()+"'");
			
			if ("0".equals(search.getProcFL())){
				selectQuery.append("    AND B.STATE = '02' ");   //신청중
			}
			
			selectQuery.append("      GROUP BY A.REQFORMNO) "); 		
			
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
					
			rs = pstmt.executeQuery();			
			
			if ( rs.next() ){
				totalCount = rs.getInt(1);
			}			
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return totalCount;
	}
	
	/**
	 * 기존양식 목록 가져오기
	 */
	public List getUsedList(SearchBean search) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List usedList = null;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT BUNHO, REQFORMNO, TITLE, STRDT, ENDDT, RANGE ");
			selectQuery.append("FROM (SELECT ROWNUM BUNHO, REQFORMNO, TITLE, REPLACE(SUBSTR(STRDT,6),'-','/') STRDT, ");
			selectQuery.append("             REPLACE(SUBSTR(ENDDT,6),'-','/') ENDDT, RANGE ");
			selectQuery.append("      FROM (SELECT REQFORMNO, TITLE,  STRDT, ENDDT, RANGE ");
			selectQuery.append("            FROM OUT_REQFORMMST ");
			selectQuery.append("            WHERE COLDEPTCD IN (SELECT DEPT_ID FROM DEPT ");
			selectQuery.append("								CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID ");
			selectQuery.append("								START WITH DEPT_ID = ?) "); 			
			selectQuery.append("            AND TITLE LIKE '%"+search.getTitle()+"%' ");
			selectQuery.append("            AND CRTDT LIKE '"+search.getSyear().substring(0, 4)+"%'");
			selectQuery.append("            ORDER BY ENDDT DESC) ");
			selectQuery.append("      ) ");
			selectQuery.append("WHERE BUNHO BETWEEN ? AND ? ");
			
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
						
			pstmt.setString(1, search.getDeptid());
			pstmt.setInt(2, search.getStart_idx());
			pstmt.setInt(3, search.getEnd_idx());
			
			rs = pstmt.executeQuery();
			
			usedList = new ArrayList();
			
			while (rs.next()) {
				FrmBean bean = new FrmBean();
						
				bean.setBunho(rs.getInt("BUNHO"));
				bean.setReqformno(rs.getInt("REQFORMNO"));
				bean.setTitle(rs.getString("TITLE"));
				bean.setStrdt(rs.getString("STRDT"));
				bean.setEnddt(rs.getString("ENDDT"));
				bean.setRange(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 1));
				bean.setRangedetail(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 2));
				
				usedList.add(bean);				
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return usedList;
	}
	
	/**
	 * 기존양식 가져오기 전체 건수 가져오기
	 */
	public int getUsedTotCnt(SearchBean search) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalCount = 0;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT COUNT(*) ");
			selectQuery.append("FROM OUT_REQFORMMST ");
			selectQuery.append("WHERE COLDEPTCD IN (SELECT DEPT_ID FROM DEPT ");
			selectQuery.append("					CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID ");
			selectQuery.append("					START WITH DEPT_ID = ? ) "); 
			//selectQuery.append("AND STRDT <= TO_CHAR(SYSDATE,'YYYY-MM-DD') ");			
			//selectQuery.append("AND (ENDDT >= TO_CHAR(SYSDATE,'YYYY-MM-DD') OR ENDDT IS NULL) ");				
			selectQuery.append("AND TITLE LIKE '%"+search.getTitle()+"%' ");
			selectQuery.append("AND CRTDT LIKE '"+search.getSyear().substring(0, 4)+"%'");
			
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, search.getDeptid());
			
			rs = pstmt.executeQuery();			
			
			if ( rs.next() ){
				totalCount = rs.getInt(1);
			}			
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return totalCount;
	}
	
	/**
	 * 마스터 정보 가져오기
	 */
	public FrmBean reqFormInfo(int reqformno) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		FrmBean bean = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT REQFORMNO, TITLE,     STRDT,     ENDDT,    COLDEPTCD, ");
			selectQuery.append("       COLDEPTNM, COLDEPTTEL, CHRGUSRID, CHRGUSRNM, SUMMARY,  RANGE, ");
			selectQuery.append("       IMGPREVIEW, DUPLICHECK, LIMITCOUNT, SANCNEED,  BASICTYPE, HEADCONT,  TAILCONT, CRTDT, "); 
			selectQuery.append("       CRTUSRID,  UPTDT,     UPTUSRID,  SIGN(MONTHS_BETWEEN(TO_DATE(ENDDT), SYSDATE)) CLOSEFL, ");
			selectQuery.append("       (SELECT COUNT(1) FROM OUT_REQFORMSUB  WHERE REQFORMNO = A.REQFORMNO) AS ACNT  ");
			selectQuery.append("FROM OUT_REQFORMMST A ");
			selectQuery.append("WHERE REQFORMNO = ? ");

			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, reqformno);

			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				bean = new FrmBean();
				
				bean.setReqformno(rs.getInt("REQFORMNO"));
				bean.setTitle(rs.getString("TITLE"));
				bean.setStrdt(rs.getString("STRDT"));
				bean.setEnddt(rs.getString("ENDDT"));
				bean.setColdeptcd(rs.getString("COLDEPTCD"));
				bean.setColdeptnm(rs.getString("COLDEPTNM"));
				bean.setColtel(rs.getString("COLDEPTTEL"));
				bean.setChrgusrid(rs.getString("CHRGUSRID"));
				bean.setChrgusrnm(rs.getString("CHRGUSRNM"));
				bean.setSummary(rs.getString("SUMMARY"));
				bean.setRange(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 1));
				bean.setRangedetail(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 2));
				bean.setImgpreview(rs.getString("IMGPREVIEW"));
				bean.setDuplicheck(rs.getString("DUPLICHECK"));
				bean.setLimitcount(rs.getInt("LIMITCOUNT"));
				bean.setSancneed(rs.getString("SANCNEED"));
				bean.setBasictype(rs.getString("BASICTYPE"));
				bean.setHeadcont(rs.getString("HEADCONT"));
				bean.setTailcont(rs.getString("TAILCONT"));
				bean.setCrtdt(rs.getString("CRTDT"));
				bean.setCrtusrid(rs.getString("CRTUSRID"));
				bean.setUptdt(rs.getString("UPTDT"));
				bean.setUptusrid(rs.getString("UPTUSRID"));
				bean.setAcnt(rs.getInt("ACNT"));
				
				int temp = rs.getInt("CLOSEFL");
				if(temp >= 0){
					bean.setClosefl("N");
				} else {
					bean.setClosefl("Y");
				}						
			}			

		 } catch (SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return bean;
	}
	
	/**
	 * 마스터(TEMP) 정보 가져오기
	 */
	public FrmBean reqFormInfo_temp(String sessi) throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		FrmBean bean = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT TITLE,     STRDT,     ENDDT,    COLDEPTCD, COLDEPTNM, COLDEPTTEL, ");
			selectQuery.append("       CHRGUSRID, CHRGUSRNM, SUMMARY,  RANGE,	IMGPREVIEW,	DUPLICHECK, ");
			selectQuery.append("       LIMITCOUNT, BASICTYPE, HEADCONT,  TAILCONT, CRTDT,     CRTUSRID, ");
			selectQuery.append("       SANCNEED,  UPTDT,     UPTUSRID ");
			selectQuery.append("FROM OUT_REQFORMMST ");
			selectQuery.append("WHERE SESSIONID LIKE ? ");

			conn = ConnectionManager.getConnection();
			
			pstmt =	conn.prepareStatement(selectQuery.toString());
			pstmt.setString(1, sessi);

			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				bean = new FrmBean();				
				
				bean.setTitle(rs.getString("TITLE"));
				bean.setStrdt(rs.getString("STRDT"));
				bean.setEnddt(rs.getString("ENDDT"));
				bean.setColdeptcd(rs.getString("COLDEPTCD"));
				bean.setColdeptnm(rs.getString("COLDEPTNM"));
				bean.setColtel(rs.getString("COLDEPTTEL"));
				bean.setChrgusrid(rs.getString("CHRGUSRID"));
				bean.setChrgusrnm(rs.getString("CHRGUSRNM"));
				bean.setSummary(rs.getString("SUMMARY"));
				bean.setRange(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 1));
				bean.setRangedetail(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 2));
				bean.setImgpreview(rs.getString("IMGPREVIEW"));
				bean.setDuplicheck(rs.getString("DUPLICHECK"));
				bean.setLimitcount(rs.getInt("LIMITCOUNT"));
				bean.setSancneed(rs.getString("SANCNEED"));
				bean.setBasictype(rs.getString("BASICTYPE"));
				bean.setHeadcont(rs.getString("HEADCONT"));
				bean.setTailcont(rs.getString("TAILCONT"));
				bean.setCrtdt(rs.getString("CRTDT"));
				bean.setCrtusrid(rs.getString("CRTUSRID"));
				bean.setUptdt(rs.getString("UPTDT"));
				bean.setUptusrid(rs.getString("UPTUSRID"));
			}			

		 } catch (SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(conn,pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(conn,pstmt,rs);
	     }
		return bean;
	}
	
	/**
	 * 문항목록 가져오기
	 */
	public List reqFormSubList(int reqformno) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List reqSubList = null;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT A.REQFORMNO, A.FORMSEQ, FORMTITLE, REQUIRE, FORMTYPE, ");
			selectQuery.append("	   SECURITY, HELPEXP, EXAMTYPE, ");
			selectQuery.append("	   FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD, NVL(EX_FRSQ, 0) AS EX_FRSQ, NVL(EX_EXSQ, 0) AS EX_EXSQ ");
			selectQuery.append("FROM OUT_REQFORMSUB A, OUT_REQFORMSUBFILE B ");
			selectQuery.append("WHERE A.REQFORMNO = B.REQFORMNO(+) ");
			selectQuery.append("  AND A.FORMSEQ = B.FORMSEQ(+) ");
			selectQuery.append("  AND A.REQFORMNO = ? 		");
			selectQuery.append("ORDER BY FORMSEQ 			");	
			//logger.info(selectQuery);
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());						
			pstmt.setInt(1, reqformno);
			
			rs = pstmt.executeQuery();
			
			reqSubList = new ArrayList();
			
			while (rs.next()) {
				int formno = rs.getInt("REQFORMNO");
				int formseq = rs.getInt("FORMSEQ");
				
				ArticleBean bean = new ArticleBean();
				
				bean.setReqformno(formno);
				bean.setFormseq(formseq);
				bean.setFormtitle(rs.getString("FORMTITLE"));
				bean.setRequire(rs.getString("REQUIRE"));
				bean.setFormtype(rs.getString("FORMTYPE"));
				bean.setSecurity(rs.getString("SECURITY"));
				bean.setHelpexp(rs.getString("HELPEXP"));
				bean.setExamtype(rs.getString("EXAMTYPE"));
				bean.setFileseq(rs.getInt("FILESEQ"));
				bean.setFilenm(rs.getString("FILENM"));
				bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				bean.setFilesize(rs.getInt("FILESIZE"));
				bean.setExt(rs.getString("EXT"));				
				bean.setOrd(rs.getInt("ORD"));
				bean.setEx_frsq(rs.getInt("EX_FRSQ"));
				bean.setEx_exsq(rs.getString("EX_EXSQ"));
				
				bean.setSampleList(sampleList(formno, formseq));
				
				reqSubList.add(bean);				
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return reqSubList;
	}
	
	/**
	 * 문항목록(TEMP) 가져오기
	 */
	public List reqFormSubList_temp(String sessi, int examcount) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List reqSubList = null;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT A.FORMSEQ, FORMTITLE, REQUIRE, FORMTYPE, ");
			selectQuery.append("	   SECURITY, HELPEXP, EXAMTYPE, ");
			selectQuery.append("	   FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD, NVL(EX_FRSQ, 0) AS EX_FRSQ, NVL(EX_EXSQ, 0) AS EX_EXSQ ");
			selectQuery.append("FROM OUT_REQFORMSUB A, OUT_REQFORMSUBFILE B ");
			selectQuery.append("WHERE A.SESSIONID = B.SESSIONID(+) ");
			selectQuery.append("  AND A.FORMSEQ = B.FORMSEQ(+) ");
			selectQuery.append("  AND A.SESSIONID LIKE ? 		");
			selectQuery.append("ORDER BY FORMSEQ 			");	
			
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());						
			pstmt.setString(1, sessi);
			
			rs = pstmt.executeQuery();
			
			reqSubList = new ArrayList();
			
			while (rs.next()) {				
				int formseq = rs.getInt("FORMSEQ");
				
				ArticleBean bean = new ArticleBean();				
				
				bean.setFormseq(formseq);
				bean.setFormtitle(rs.getString("FORMTITLE"));
				bean.setRequire(rs.getString("REQUIRE"));
				bean.setFormtype(rs.getString("FORMTYPE"));
				bean.setSecurity(rs.getString("SECURITY"));
				bean.setHelpexp(rs.getString("HELPEXP"));
				bean.setExamtype(rs.getString("EXAMTYPE"));
				bean.setFileseq(rs.getInt("FILESEQ"));
				bean.setFilenm(rs.getString("FILENM"));
				bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				bean.setFilesize(rs.getInt("FILESIZE"));
				bean.setExt(rs.getString("EXT"));				
				bean.setOrd(rs.getInt("ORD"));
				bean.setEx_frsq(rs.getInt("EX_FRSQ"));
				bean.setEx_exsq(rs.getString("EX_EXSQ"));
				
				bean.setSampleList(sampleList_temp(sessi, formseq, examcount));
				
				reqSubList.add(bean);				
			}							
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return reqSubList;
	}
	
	/**
	 * 문항별 보기목록 가져오기
	 */
	public List sampleList(int reqformno, int formseq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List sampleList = null;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT A.REQFORMNO, A.FORMSEQ, A.EXAMSEQ, EXAMCONT, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD ");
			selectQuery.append("FROM OUT_REQFORMEXAM A, OUT_REQFORMEXAMFILE B ");
			selectQuery.append("WHERE A.REQFORMNO = B.REQFORMNO(+) ");
			selectQuery.append("  AND A.FORMSEQ = B.FORMSEQ(+) ");
			selectQuery.append("  AND A.EXAMSEQ = B.EXAMSEQ(+) ");
			selectQuery.append("  AND A.REQFORMNO = ? ");
			selectQuery.append("  AND A.FORMSEQ = ? ");
			selectQuery.append("ORDER BY A.EXAMSEQ ");
			
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());						
			pstmt.setInt(1, reqformno);
			pstmt.setInt(2, formseq);
			
			rs = pstmt.executeQuery();
			
			sampleList = new ArrayList();
			int cnt = 0;
			while (rs.next()) {
				SampleBean bean = new SampleBean();
						
				bean.setReqformno(rs.getInt("REQFORMNO"));
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setExamseq(rs.getInt("EXAMSEQ"));
				bean.setExamcont(rs.getString("EXAMCONT"));
				bean.setFileseq(rs.getInt("FILESEQ"));
				bean.setFilenm(rs.getString("FILENM"));
				bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				bean.setFilesize(rs.getInt("FILESIZE"));
				bean.setExt(rs.getString("EXT"));				
				bean.setOrd(rs.getInt("ORD"));
				
				sampleList.add(bean);
				cnt = cnt + 1;
			}						
			
			//비어있는 보기를 examcount개까지 채운다.
			int examcount = getReqSubExamcount(reqformno, "");
			for(int i=0;i<examcount-cnt;i++){
				SampleBean bean = new SampleBean();
				sampleList.add(bean);
			}
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return sampleList;
	}
	
	/**
	 * 문항별 보기목록(TEMP) 가져오기
	 */
	public List sampleList_temp(String sessi, int formseq, int examcount) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List sampleList = null;	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("SELECT A.FORMSEQ, A.EXAMSEQ, EXAMCONT, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD ");		
			selectQuery.append("FROM OUT_REQFORMEXAM A, OUT_REQFORMEXAMFILE B ");
			selectQuery.append("WHERE A.SESSIONID = B.SESSIONID(+) ");
			selectQuery.append("  AND A.FORMSEQ = B.FORMSEQ(+) ");
			selectQuery.append("  AND A.EXAMSEQ = B.EXAMSEQ(+) ");
			selectQuery.append("  AND A.SESSIONID LIKE ? ");
			selectQuery.append("  AND A.FORMSEQ = ? ");
			selectQuery.append("ORDER BY A.EXAMSEQ ");
			
			con = ConnectionManager.getConnection();			
			pstmt = con.prepareStatement(selectQuery.toString());						
			pstmt.setString(1, sessi);
			pstmt.setInt(2, formseq);
			
			rs = pstmt.executeQuery();
			
			sampleList = new ArrayList();
			int cnt = 0;
			while (rs.next()) {
				SampleBean bean = new SampleBean();						
				
				bean.setFormseq(rs.getInt("FORMSEQ"));
				bean.setExamseq(rs.getInt("EXAMSEQ"));
				bean.setExamcont(rs.getString("EXAMCONT"));
				bean.setFileseq(rs.getInt("FILESEQ"));
				bean.setFilenm(rs.getString("FILENM"));
				bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
				bean.setFilesize(rs.getInt("FILESIZE"));
				bean.setExt(rs.getString("EXT"));				
				bean.setOrd(rs.getInt("ORD"));
				
				sampleList.add(bean);
				cnt = cnt + 1;
			}						
			
			//비어있는 보기를 examcount개까지 채운다.
			for(int i=0;i<examcount-cnt;i++){
				SampleBean bean = new SampleBean();
				sampleList.add(bean);
			}
			
		} catch(SQLException e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return sampleList;
	}
	
	/**
	 * 임시테이블로 양식 데이터 복사
	 */
	public int copyToTemp(int reqformno, String sessi, ServletContext context, String saveDir) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;		
		int result = 0;			
				
		try{
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
            //양식마스터 복사
			StringBuffer insertQuery1 = new StringBuffer();
			insertQuery1.append("INSERT INTO OUT_REQFORMMST ");
			insertQuery1.append("SELECT '" + sessi + "', TITLE, STRDT, ENDDT, COLDEPTCD, ");
			insertQuery1.append("       COLDEPTNM, COLDEPTTEL, CHRGUSRID, CHRGUSRNM, SUMMARY, RANGE, ");
			insertQuery1.append("       IMGPREVIEW, DUPLICHECK, LIMITCOUNT, SANCNEED, BASICTYPE, HEADCONT, ");
			insertQuery1.append("       TAILCONT, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), CRTUSRID, '', '' ");
			insertQuery1.append("FROM OUT_REQFORMMST ");
			insertQuery1.append("WHERE REQFORMNO = ? "); 
			
			pstmt = con.prepareStatement(insertQuery1.toString());
			pstmt.setInt(1, reqformno);
			
			result = pstmt.executeUpdate();			
			ConnectionManager.close(pstmt);
			
			//양식 문항 복사
			StringBuffer insertQuery2 = new StringBuffer();
			insertQuery2.append("INSERT INTO OUT_REQFORMSUB ");
			insertQuery2.append("SELECT '" + sessi + "', FORMSEQ, FORMTITLE, REQUIRE, FORMTYPE, SECURITY, HELPEXP, EXAMTYPE , NVL(EX_FRSQ, 0) AS EX_FRSQ, NVL(EX_EXSQ, 0) AS EX_EXSQ");
			insertQuery2.append("FROM OUT_REQFORMSUB ");
			insertQuery2.append("WHERE REQFORMNO = ? "); 
			
			pstmt = con.prepareStatement(insertQuery2.toString());
			pstmt.setInt(1, reqformno);
			
			result += pstmt.executeUpdate();			
			ConnectionManager.close(pstmt);
			
			//양식 보기 복사
			StringBuffer insertQuery3 = new StringBuffer();
			insertQuery3.append("INSERT INTO OUT_REQFORMEXAM ");
			insertQuery3.append("SELECT '" + sessi + "', FORMSEQ, EXAMSEQ, EXAMCONT ");
			insertQuery3.append("FROM OUT_REQFORMEXAM ");
			insertQuery3.append("WHERE REQFORMNO = ? "); 
			
			pstmt = con.prepareStatement(insertQuery3.toString());
			pstmt.setInt(1, reqformno);
			
			result += pstmt.executeUpdate();			
			ConnectionManager.close(pstmt);
			
			//첨부파일 복사
			result += copyReqFormSubExamFile(con, sessi, reqformno, context, saveDir, "VIEW");
			
			con.commit();
		} catch (Exception e) {
			try{
				result = 0;
				con.rollback();
			} catch(Exception ex){
				logger.error("ERROR",ex);
//				throw ex;
			}
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt);
			 throw e;
	     } finally {	
//	    	 try{ 
//		 		con.setAutoCommit(true);
//	    	 } catch (Exception e){
//				logger.error("ERROR",e);
////				throw e;
//	    	 }
			ConnectionManager.close(con,pstmt);	   
	     }	
		return result;
	}
		
	/**
	 * 전체 저장(TEMP)
	 */
	public int saveAll(FrmBean fbean, SinchungForm sForm, ServletContext context, String saveDir) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;		
		int result = 0;	
				
		try{
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			//임시테이블에 값이 있으면 업데이트 한다.
			if(existCheck_temp(fbean.getSessi())){
								
				//마스터 저장
				result = updateMst(con, fbean, "TEMP");				
				//문항 삭제
				result += deleteArticleAll(con, 0, fbean.getSessi());				
				//보기 삭제
				result += deleteSampleAll(con, 0, fbean.getSessi());				
				//문항저장(보기포함)
				result += insertArticleAll(con, fbean.getArticleList(), "TEMP");
				
				//파일 업로드
				File saveFolder = new File(context.getRealPath(saveDir));
				if(!saveFolder.exists()) saveFolder.mkdirs();
				
				String[] formattitleFileYN = fbean.getFormtitleFileYN();
				String[] examcontFileYN = fbean.getExamcontFileYN();
				String[] formtitle = fbean.getFormtitle();
				String[] examcont = fbean.getExamcont();
				int examcount = fbean.getExamcount();
				
				fbean.setReqformno(0);	//0일때 임시테이블이 대상이 됨
				
				for ( int i = 0; formtitle != null && i < formtitle.length; i++ ) {
					ArticleBean atcBean = getReqFormSubFile(con, fbean.getSessi(), fbean.getReqformno(), i+1);
					
					if ( atcBean != null &&
							(formattitleFileYN[i] == null || 
									(fbean.getFormtitleFile()[i] != null && fbean.getFormtitleFile()[i].getFileName().equals("") == false)) ) {
						delReqFormSubFile(con, fbean.getSessi(), fbean.getReqformno(), i+1, 1);
						
						String delFile = atcBean.getFilenm();
						if ( delFile != null && delFile.trim().equals("") == false) {
							FileManager.doFileDelete(context.getRealPath(delFile));
						}
					}
					
					if ( fbean.getFormtitleFile()[i] != null && fbean.getFormtitleFile()[i].getFileName().equals("") == false ) {
						FileBean atcFileBean = FileManager.doFileUpload(fbean.getFormtitleFile()[i], context, saveDir);
						
						if( atcFileBean != null ) {
							int addResult = 0;
							atcFileBean.setFileseq(1);
							addResult = addReqFormSubFile(con, fbean.getSessi(), fbean.getReqformno(), i+1, atcFileBean);
							if ( addResult == 0 ) {
								throw new Exception("첨부실패:DB업데이트");
							}
						} else {
							throw new Exception("첨부실패:파일업로드");
						}
					} 
					
					int prevExamcount = 0;
					while ( prevExamcount < sForm.getExamcontFile().length
							&& sForm.getExamcontFile()[prevExamcount] != null ) {
						prevExamcount++;
					}
					prevExamcount = prevExamcount / fbean.getAcnt();
					
					if ( examcount >= prevExamcount ) {
					
						for ( int j = 0; examcont != null && j < prevExamcount; j++ ) {
							SampleBean spBean = getReqFormExamFile(con, fbean.getSessi(), fbean.getReqformno(), i+1, j+1);
	
							if ( spBean != null &&
									(examcont[i*prevExamcount+j].trim().equals("") == true || examcontFileYN[i*prevExamcount+j] == null || 
											(fbean.getExamcontFile()[i*prevExamcount+j] != null && fbean.getExamcontFile()[i*prevExamcount+j].getFileName().equals("") == false)) ) {
								delReqFormExamFile(con, fbean.getSessi(), fbean.getReqformno(), i+1, j+1, 1);
								
								String delFile = spBean.getFilenm();
								if ( delFile != null && delFile.trim().equals("") == false) {
									FileManager.doFileDelete(context.getRealPath(delFile));
								}
							}
	
							if ( fbean.getExamcontFile()[i*prevExamcount+j] != null && fbean.getExamcontFile()[i*prevExamcount+j].getFileName().equals("") == false ) {
								FileBean spFileBean = FileManager.doFileUpload(fbean.getExamcontFile()[i*prevExamcount+j], context, saveDir);
								
								if(spFileBean != null) {
									int addResult = 0;
									spFileBean.setFileseq(1);
									addResult = addReqFormExamFile(con, fbean.getSessi(), fbean.getReqformno(), i+1, j+1, spFileBean);
									if ( addResult == 0 ) {
										throw new Exception("첨부실패:DB업데이트");
									}
								} else {
									throw new Exception("첨부실패:파일업로드");
								}
							}
						}

						setOrderReqFormExamFile(con, fbean.getSessi(), fbean.getReqformno(), i+1);
					}
					
					List spUnusedList = getReqFormExamUnusedFile(con, fbean.getSessi(), fbean.getReqformno(), i+1);
					
					for ( int k = 0; spUnusedList != null && k < spUnusedList.size(); k++ ) {
						SampleBean spBean = (SampleBean)spUnusedList.get(k);
						
						if ( spBean != null ) {
							delReqFormExamFile(con, fbean.getSessi(), fbean.getReqformno(), i+1, spBean.getExamseq(), spBean.getFileseq());
							
							String delFile = spBean.getFilenm();
							if ( delFile != null && delFile.trim().equals("") == false) {
								FileManager.doFileDelete(context.getRealPath(delFile));
							}
						}
					}
				}
				
			} else {
				//마스터 저장 (TEMP)
				result = insertMst(con, fbean);
			}		
			
			con.commit();
		} catch (Exception e) {
			try{
				con.rollback();
			} catch(Exception ex){
				logger.error("ERROR",ex);
//				throw ex;
			}
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt);
			 throw e;
	     } finally {	
//	    	 try{ 
//		 		con.setAutoCommit(true);
//	    	 } catch (Exception e){
//				logger.error("ERROR",e);
////				throw e;
//	    	 }
			ConnectionManager.close(con,pstmt);	   
	     }	
		return result;
	}
	
	/**
	 * 항목 만들기
	 */
	public int makeArticle(int reqformno, String sessi, int acnt) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		int tablecnt = getCntFormseq(reqformno, sessi);
		int maxseq = getMaxFormseq(reqformno, sessi);
		int result = 0;
		String tb = "";
		String kfield = "";		
		
		if(reqformno == 0){
			tb = "OUT_REQFORMSUB";
			kfield = "SESSIONID";			
		} else {
			tb = "OUT_REQFORMSUB";
			kfield = "REQFORMNO";
		}		
		
		try{			
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			if(acnt == tablecnt){
				return 0;  //항목수가 변화가 없으면 Return
			} else if(acnt > tablecnt) {
				//항목 차이 수량 만큼 추가
				int gap = acnt - tablecnt;
				
				for(int i=0;i<gap;i++){
					StringBuffer insertQuery = new StringBuffer();
					
					insertQuery.append("INSERT INTO "+tb+"("+kfield+", FORMSEQ, FORMTITLE, REQUIRE, FORMTYPE, ");
					insertQuery.append("                   SECURITY, HELPEXP, EXAMTYPE) ");
					insertQuery.append("VALUES(?, ?, '', 'N', '3',   'N', '', 'N') ");
					
					pstmt = con.prepareStatement(insertQuery.toString());
					
					if(reqformno == 0){
						pstmt.setString(1, sessi);
					} else {
						pstmt.setInt(1, reqformno);
					}
					
					pstmt.setInt(2, maxseq+i);
					
					result = pstmt.executeUpdate();					
					ConnectionManager.close(pstmt);
				}
			} else if(acnt < tablecnt){
				//항목 차이 수량 만큼 삭제
				int gap = tablecnt - acnt;				
				
				for(int i=0;i<gap;i++){
					//제일 끝에 있는 항목을 가져와서 삭제한다.
					StringBuffer selectQuery = new StringBuffer();					
					selectQuery.append("SELECT NVL(MAX(FORMSEQ),0) FROM "+tb+" WHERE "+kfield+" = ? ");
										
					pstmt = con.prepareStatement(selectQuery.toString());
					
					if(reqformno == 0){
						pstmt.setString(1, sessi);	
					} else {
						pstmt.setInt(1, reqformno);
					}
								
					rs = pstmt.executeQuery();	
					
					int formseq = 0;
					if ( rs.next() ){
						formseq = rs.getInt(1);
					}						
					ConnectionManager.close(pstmt, rs);
					
					//삭제 처리
					StringBuffer delteQuery = new StringBuffer();	
					delteQuery.append("DELETE FROM "+tb+" WHERE "+kfield+" = ? AND FORMSEQ = ? ");
										
					pstmt = con.prepareStatement(delteQuery.toString());
					
					if(reqformno == 0){
						pstmt.setString(1, sessi);	
					} else {
						pstmt.setInt(1, reqformno);
					}					
					pstmt.setInt(2, formseq);
								
					result += pstmt.executeUpdate();
					ConnectionManager.close(pstmt);
					
					//보기 삭제
					result += deleteSampleAll(con, reqformno, sessi);
				}
			}			
			
			con.commit();
		} catch (Exception e) {
			try{
				result = -1;
				con.rollback();
			} catch(Exception ex){
				logger.error("ERROR",ex);
//				throw ex;
			}
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt);
			 throw e;
	     } finally {	
//	    	 try{ 
//		 		con.setAutoCommit(true);
//	    	 } catch (Exception e){
//				logger.error("ERROR",e);
////				throw e;
//	    	 }
			ConnectionManager.close(con,pstmt);	   
	     }	
		return result;
	}
	
	/**
	 * 완료하기
	 * 임시테이블에서 정규테이블로 복사한다.
	 */
	public int saveComp(String sessi, ServletContext context, String saveDir) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		int reqformno = 0;		
				
		try{				
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			//신청양식 번호가져오기
			StringBuffer selectQuery1 = new StringBuffer();
			selectQuery1.append("SELECT REQFORMSEQ.NEXTVAL FROM DUAL ");
			
			pstmt = con.prepareStatement(selectQuery1.toString());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				reqformno = rs.getInt(1);
			}
			
			ConnectionManager.close(pstmt, rs);
			
			//양식마스터 복사
			StringBuffer insertQuery1 = new StringBuffer();
			insertQuery1.append("INSERT INTO OUT_REQFORMMST ");
			insertQuery1.append("SELECT " + reqformno + ", TITLE, STRDT, ENDDT, COLDEPTCD, ");
			insertQuery1.append("       COLDEPTNM, COLDEPTTEL, CHRGUSRID, CHRGUSRNM, SUMMARY, RANGE, ");
			insertQuery1.append("       IMGPREVIEW, DUPLICHECK, LIMITCOUNT, SANCNEED, BASICTYPE, HEADCONT, ");
			insertQuery1.append("       TAILCONT, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), CRTUSRID, '', '' ");
			insertQuery1.append("FROM OUT_REQFORMMST ");
			insertQuery1.append("WHERE SESSIONID LIKE ? "); 
			
			pstmt = con.prepareStatement(insertQuery1.toString());
			pstmt.setString(1, sessi);
			
			pstmt.executeUpdate();			
			ConnectionManager.close(pstmt);
			
			//양식 문항 복사
			StringBuffer insertQuery2 = new StringBuffer();
			insertQuery2.append("INSERT INTO OUT_REQFORMSUB ");
			insertQuery2.append("SELECT " + reqformno + ", FORMSEQ, FORMTITLE, REQUIRE, FORMTYPE, SECURITY, HELPEXP, EXAMTYPE, NVL(EX_FRSQ, 0) AS EX_FRSQ, NVL(EX_EXSQ, 0) AS EX_EXSQ ");
			insertQuery2.append("FROM OUT_REQFORMSUB ");
			insertQuery2.append("WHERE SESSIONID LIKE ? "); 
			
			pstmt = con.prepareStatement(insertQuery2.toString());
			pstmt.setString(1, sessi);
			
			pstmt.executeUpdate();			
			ConnectionManager.close(pstmt);
			
			//양식 보기 복사
			StringBuffer insertQuery3 = new StringBuffer();
			insertQuery3.append("INSERT INTO OUT_REQFORMEXAM ");
			insertQuery3.append("SELECT " + reqformno + ", FORMSEQ, EXAMSEQ, EXAMCONT ");
			insertQuery3.append("FROM OUT_REQFORMEXAM ");
			insertQuery3.append("WHERE SESSIONID LIKE ? "); 
			
			pstmt = con.prepareStatement(insertQuery3.toString());
			pstmt.setString(1, sessi);
			
			pstmt.executeUpdate();			
			ConnectionManager.close(pstmt);
			
			//원본첨부파일 삭제
			delReqFormSubExamAllFile(con, "", reqformno, context);
			
			//첨부파일 복사
			copyReqFormSubExamFile(con, sessi, reqformno, context, saveDir, "SAVE");
			
			con.commit();
		} catch (Exception e) {
			try{
				reqformno = -1;
				con.rollback();
			} catch(Exception ex){
				logger.error("ERROR",ex);
//				throw ex;
			}
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt);
			 throw e;
	     } finally {	
//	    	 try{ 
//		 		con.setAutoCommit(true);
//	    	 } catch (Exception e){
//				logger.error("ERROR",e);
////				throw e;
//	    	 }
			ConnectionManager.close(con,pstmt);	   
	     }	
		return reqformno;
	}
	
	/**
	 * 신청서 첨부파일 복사
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param context
	 * @param saveDir
	 * @param mode	( VIEW : 신청서보기, SAVE : 신청서저장 ) 
	 * @return
	 * @throws Exception
	 */
	public int copyReqFormSubExamFile(Connection conn, String sessionId, int reqformno, ServletContext context, String saveDir, String mode) throws Exception {
		int result = 0;
		
		List atcFile = null;
		if ( mode.equals("VIEW") ) {
			atcFile = getReqFormSubFile(conn, "", reqformno);
		} else if ( mode.equals("SAVE") ) {
			atcFile = getReqFormSubFile(conn, sessionId, 0);
		}
		for ( int i = 0; atcFile != null && i < atcFile.size(); i++ ) {
			ArticleBean atcBean = (ArticleBean)atcFile.get(i);
			
			String newFilenm = "";
			try {
				newFilenm = FileManager.doFileCopy(context.getRealPath(atcBean.getFilenm()));
			} catch (FileNotFoundException e) {
				continue;
			}
			
			if( newFilenm.equals("") == false ) {
				FileBean atcFileBean = new FileBean();
				atcFileBean.setFileseq(atcBean.getFileseq());
				atcFileBean.setFilenm(saveDir + newFilenm);
				atcFileBean.setOriginfilenm(atcBean.getOriginfilenm());
				atcFileBean.setFilesize(atcBean.getFilesize());
				atcFileBean.setExt(atcBean.getExt());
				
				int addResult = 0;
				if ( mode.equals("VIEW") ) {
					addResult = addReqFormSubFile(conn, sessionId, 0, atcBean.getFormseq(), atcFileBean);
				} else if ( mode.equals("SAVE") ) {
					addResult = addReqFormSubFile(conn, "", reqformno, atcBean.getFormseq(), atcFileBean);
				}
				if ( addResult == 0 ) {
					throw new Exception("첨부실패(저장):DB업데이트");
				}
				result += addResult;
			} else {
				throw new Exception("첨부실패(저장):파일업로드");
			}
		}
		 
		List spFile = null;
		if ( mode.equals("VIEW") ) {
			spFile = getReqFormExamFile(conn, "", reqformno, 0);
		} else if ( mode.equals("SAVE") ) {
			spFile = getReqFormExamFile(conn, sessionId, 0, 0);
		}
		for ( int i = 0; spFile != null && i < spFile.size(); i++ ) {
			SampleBean spBean = (SampleBean)spFile.get(i);
			
			String newFilenm = "";
			try {
				newFilenm = FileManager.doFileCopy(context.getRealPath(spBean.getFilenm()));
			} catch (FileNotFoundException e) {
				continue;
			}
			
			if( newFilenm.equals("") == false ) {
				FileBean spFileBean = new FileBean();
				spFileBean.setFileseq(spBean.getFileseq());
				spFileBean.setFilenm(saveDir + newFilenm);
				spFileBean.setOriginfilenm(spBean.getOriginfilenm());
				spFileBean.setFilesize(spBean.getFilesize());
				spFileBean.setExt(spBean.getExt());
				
				int addResult = 0;
				if ( mode.equals("VIEW") ) {
					addResult = addReqFormExamFile(conn, sessionId, 0, spBean.getFormseq(), spBean.getExamseq(), spFileBean);
				} else if ( mode.equals("SAVE") ) {
					addResult = addReqFormExamFile(conn, "", reqformno, spBean.getFormseq(), spBean.getExamseq(), spFileBean);
				}
				if ( addResult == 0 ) {
					throw new Exception("첨부실패(저장):DB업데이트");
				}
				result += addResult;
			} else {
				throw new Exception("첨부실패(저장):파일업로드");
			}
		}
		
		return result;
	}
	
	/**
	 * 저장하기(양식 수정)
	 * 정규테이블에서 임시테이블로 복사한다.
	 */
	public int updateComp(int reqformno, String sessi, ServletContext context, String saveDir) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;			
						
		try{				
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
						
			//마스터 삭제(양식)
			StringBuffer delteQuery1 = new StringBuffer();
			delteQuery1.append("DELETE FROM OUT_REQFORMMST WHERE REQFORMNO = ? ");
			
			pstmt = con.prepareStatement(delteQuery1.toString());
			pstmt.setInt(1, reqformno);
			
			pstmt.executeUpdate();						
			ConnectionManager.close(pstmt);	
			
			//문항 삭제(양식)
			deleteArticleAll(con, reqformno, "");				
			//보기 삭제(양식)
			deleteSampleAll(con, reqformno, "");
			
			//원본첨부파일 삭제
			delReqFormSubExamAllFile(con, "", reqformno, context);
			
			//첨부파일 복사
			copyReqFormSubExamFile(con, sessi, reqformno, context, saveDir, "SAVE");
			
			//양식마스터 복사
			StringBuffer insertQuery1 = new StringBuffer();
			insertQuery1.append("INSERT INTO OUT_REQFORMMST ");
			insertQuery1.append("SELECT " + reqformno + ", TITLE, STRDT, ENDDT, COLDEPTCD, ");
			insertQuery1.append("       COLDEPTNM, COLDEPTTEL, CHRGUSRID, CHRGUSRNM, SUMMARY, RANGE, ");
			insertQuery1.append("       IMGPREVIEW, DUPLICHECK, LIMITCOUNT, SANCNEED, BASICTYPE, HEADCONT, ");
			insertQuery1.append("       TAILCONT, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), CRTUSRID, '', '' ");
			insertQuery1.append("FROM OUT_REQFORMMST ");
			insertQuery1.append("WHERE SESSIONID LIKE ? "); 
			
			pstmt = con.prepareStatement(insertQuery1.toString());
			pstmt.setString(1, sessi);
			
			pstmt.executeUpdate();			
			ConnectionManager.close(pstmt);
			
			//양식 문항 복사
			StringBuffer insertQuery2 = new StringBuffer();
			insertQuery2.append("INSERT INTO OUT_REQFORMSUB ");
			insertQuery2.append("SELECT " + reqformno + ", FORMSEQ, FORMTITLE, REQUIRE, FORMTYPE, SECURITY, HELPEXP, EXAMTYPE, NVL(EX_FRSQ, 0) AS EX_FRSQ, NVL(EX_EXSQ, 0) AS EX_EXSQ ");
			insertQuery2.append("FROM OUT_REQFORMSUB ");
			insertQuery2.append("WHERE SESSIONID LIKE ? "); 
			
			pstmt = con.prepareStatement(insertQuery2.toString());
			pstmt.setString(1, sessi);
			
			pstmt.executeUpdate();			
			ConnectionManager.close(pstmt);
			
			//양식 보기 복사
			StringBuffer insertQuery3 = new StringBuffer();
			insertQuery3.append("INSERT INTO OUT_REQFORMEXAM ");
			insertQuery3.append("SELECT " + reqformno + ", FORMSEQ, EXAMSEQ, EXAMCONT ");
			insertQuery3.append("FROM OUT_REQFORMEXAM ");
			insertQuery3.append("WHERE SESSIONID LIKE ? "); 
			
			pstmt = con.prepareStatement(insertQuery3.toString());
			pstmt.setString(1, sessi);
			
			pstmt.executeUpdate();			
			ConnectionManager.close(pstmt);			
			
			con.commit();
		} catch (Exception e) {
			try{
				reqformno = -1;
				con.rollback();
			} catch(Exception ex){
				logger.error("ERROR",ex);
//				throw ex;
			}
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt);
			 throw e;
	     } finally {	
//	    	 try{ 
//		 		con.setAutoCommit(true);
//	    	 } catch (Exception e){
//				logger.error("ERROR",e);
////				throw e;
//	    	 }
			ConnectionManager.close(con,pstmt);	   
	     }	
		return reqformno;
	}
	
	/**
	 * 기존양식 가져오기에서 선택
	 * 임시 테이블로 복사
	 */
	public int selectUsed(int reqformno, String sessi, ServletContext context, String saveDir) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
				
		try{				
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
						
			//양식마스터 복사
			StringBuffer insertQuery1 = new StringBuffer();
			insertQuery1.append("INSERT INTO OUT_REQFORMMST ");
			insertQuery1.append("SELECT '" + sessi + "', TITLE, STRDT, ENDDT, '', ");
			insertQuery1.append("       '', '', '', '', SUMMARY, RANGE, IMGPREVIEW, DUPLICHECK, LIMITCOUNT, ");
			insertQuery1.append("       SANCNEED, BASICTYPE, HEADCONT, TAILCONT, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), CRTUSRID, '', '' ");
			insertQuery1.append("FROM OUT_REQFORMMST ");
			insertQuery1.append("WHERE REQFORMNO = ? "); 
			
			pstmt = con.prepareStatement(insertQuery1.toString());
			pstmt.setInt(1, reqformno);
			
			pstmt.executeUpdate();			
			ConnectionManager.close(pstmt);
			
			//양식 문항 복사
			StringBuffer insertQuery2 = new StringBuffer();
			insertQuery2.append("INSERT INTO OUT_REQFORMSUB ");
			insertQuery2.append("SELECT '" + sessi + "', FORMSEQ, FORMTITLE, REQUIRE, FORMTYPE, SECURITY, HELPEXP, EXAMTYPE, NVL(EX_FRSQ, 0) AS EX_FRSQ, NVL(EX_EXSQ, 0) AS EX_EXSQ ");
			insertQuery2.append("FROM OUT_REQFORMSUB ");
			insertQuery2.append("WHERE REQFORMNO = ? "); 
			
			pstmt = con.prepareStatement(insertQuery2.toString());
			pstmt.setInt(1, reqformno);
			
			pstmt.executeUpdate();			
			ConnectionManager.close(pstmt);
			
			//양식 보기 복사
			StringBuffer insertQuery3 = new StringBuffer();
			insertQuery3.append("INSERT INTO OUT_REQFORMEXAM ");
			insertQuery3.append("SELECT '" + sessi + "', FORMSEQ, EXAMSEQ, EXAMCONT ");
			insertQuery3.append("FROM OUT_REQFORMEXAM ");
			insertQuery3.append("WHERE REQFORMNO = ? "); 
			
			pstmt = con.prepareStatement(insertQuery3.toString());
			pstmt.setInt(1, reqformno);
			
			pstmt.executeUpdate();			
			ConnectionManager.close(pstmt);
			
			//첨부파일 복사
			copyReqFormSubExamFile(con, sessi, reqformno, context, saveDir, "VIEW");
			
			con.commit();
		} catch (Exception e) {
			try{				
				con.rollback();
			} catch(Exception ex){
				logger.error("ERROR",ex);
//				throw ex;
			}
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt);
			 throw e;
	     } finally {	
//	    	 try{ 
//		 		con.setAutoCommit(true);
//	    	 } catch (Exception e){
//				logger.error("ERROR",e);
////				throw e;
//	    	 }
			ConnectionManager.close(con,pstmt);	   
	     }	
		return reqformno;
	}
	
	/**
	 * 항목추가 (빈항목 저장)
	 */
	public int insertArticle(int reqformno, String sessi) throws Exception {
		Connection con = null;        
		PreparedStatement pstmt = null;
		int result = 0;
		String tb = "";
		String kfield = "";		
		
		if(reqformno == 0){
			tb = "OUT_REQFORMSUB";
			kfield = "SESSIONID";			
		} else {
			tb = "OUT_REQFORMSUB";
			kfield = "REQFORMNO";
		}			
		
		try{
			StringBuffer insertQuery = new StringBuffer();
			
			insertQuery.append("INSERT INTO "+tb+"("+kfield+", FORMSEQ, FORMTITLE, REQUIRE, FORMTYPE, ");
			insertQuery.append("                   SECURITY, HELPEXP, EXAMTYPE, NVL(EX_FRSQ, 0) AS EX_FRSQ, NVL(EX_EXSQ, 0) AS EX_EXSQ ) ");
			insertQuery.append("VALUES(?, ?, '', 'N', '3',   'N', '', 'N', 0, 0) ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());
			
			if(reqformno == 0){
				pstmt.setString(1, sessi);
			} else {
				pstmt.setInt(1, reqformno);
			}
			
			pstmt.setInt(2, getMaxFormseq(reqformno, sessi));
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			 result = -1;
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt);
			 throw e;
	     } finally {	       
	    	 ConnectionManager.close(con,pstmt);   
	     }
		return result;
	}

	/**
	 * 특정 문항 삭제
	 */
	public int deleteArticle (int reqformno, String sessi, int formseq, ServletContext context) throws Exception{
		Connection con = null;        
		PreparedStatement pstmt = null;
		int result =0;
		String tb = "";
		String kfield = "";	
		
		if(reqformno == 0){
			tb = "OUT_REQFORMSUB";
			kfield = "SESSIONID";
		
		} else {
			tb = "OUT_REQFORMSUB";
			kfield = "REQFORMNO";			
		}
		
		try {
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			StringBuffer deleteQuery = new StringBuffer();
	
			deleteQuery.append("DELETE FROM "+tb+" WHERE "+kfield+" = ? AND FORMSEQ = ? ");
					    
			pstmt = con.prepareStatement(deleteQuery.toString());		
		    
		    if(reqformno == 0){
		    	pstmt.setString(1, sessi);	
		    } else {
		    	pstmt.setInt(1, reqformno);	
		    }				
		    
		    pstmt.setInt(2, formseq);
			
			result = pstmt.executeUpdate();			
			ConnectionManager.close(pstmt);
			
			//보기내용 삭제
			result += deleteSample(con, reqformno, sessi, formseq);
			
			delReqFormExamAllFile(con, sessi, reqformno, formseq, context);
			
			con.commit();
		} catch (Exception e) {
			try{
				result = -1;
				con.rollback();
			} catch(Exception ex){
				logger.error("ERROR",ex);
//				throw ex;
			}
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt);
			 throw e;
	     } finally {	
//	    	 try{ 
//		 		con.setAutoCommit(true);
//	    	 } catch (Exception e){
//				logger.error("ERROR",e);
////				throw e;
//	    	 }
			ConnectionManager.close(con,pstmt);	   
	     }	
		return result;
	}

	/**
	 * 신청서 전체 첨부파일 삭제
	 * @param conn
	 * @param spBean
	 * @param context
	 * @throws Exception
	 */
	public int delReqFormSubExamAllFile(Connection conn, String sessionId, int reqformno, ServletContext context) throws Exception {
		
		int result = 0;
		
		List actList = getReqFormSubFile(conn, sessionId, reqformno);
		
		for ( int i = 0; actList != null && i < actList.size(); i++ ) {
			ArticleBean atcBean = (ArticleBean)actList.get(i);
		
			if ( atcBean != null ) {
				delReqFormSubFile(conn, sessionId, reqformno, atcBean.getFormseq(), atcBean.getFileseq());
				
				String delFile = atcBean.getFilenm();
				if ( delFile != null && delFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delFile));
				}
			}
		}
		
		List spList = getReqFormExamFile(conn, sessionId, reqformno, 0);
		
		for ( int i = 0; spList != null && i < spList.size(); i++ ) {
			SampleBean spBean = (SampleBean)spList.get(i);
			
			if ( spBean != null ) {
				delReqFormExamFile(conn, sessionId, reqformno, spBean.getFormseq(), spBean.getExamseq(), spBean.getFileseq());
				
				String delFile = spBean.getFilenm();
				if ( delFile != null && delFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delFile));
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 신청서 보기 첨부파일 삭제
	 * @param conn
	 * @param spBean
	 * @param context
	 * @throws Exception
	 */
	public int delReqFormExamAllFile(Connection conn, String sessionId, int reqformno, int formseq, ServletContext context) throws Exception {
		
		int result = 0;

		ArticleBean atcBean = getReqFormSubFile(conn, sessionId, reqformno, formseq);
		
		if ( atcBean != null ) {
			delReqFormSubFile(conn, sessionId, reqformno, formseq, atcBean.getFileseq());
			
			String delFile = atcBean.getFilenm();
			if ( delFile != null && delFile.trim().equals("") == false) {
				FileManager.doFileDelete(context.getRealPath(delFile));
			}
		}
		
		List spList = getReqFormExamFile(conn, sessionId, reqformno, formseq);
		
		for ( int i = 0; spList != null && i < spList.size(); i++ ) {
			SampleBean spBean = (SampleBean)spList.get(i);
			
			if ( spBean != null ) {
				delReqFormExamFile(conn, sessionId, reqformno, formseq, spBean.getExamseq(), spBean.getFileseq());
				
				String delFile = spBean.getFilenm();
				if ( delFile != null && delFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delFile));
				}
			}
		}

		return result;
	}
	
	/**
	 * 신청서 보기 첨부파일 전체삭제
	 * @param conn
	 * @param spBean
	 * @param context
	 * @throws Exception
	 */
	public int delReqMstAllFile(Connection conn, int reqformno, ServletContext context) throws Exception {
		
		int result = 0;

		List ansList = getReqMstFile(conn, reqformno);
		
		for ( int i = 0; ansList != null && i < ansList.size(); i++ ) {
			ReqMstBean ansBean = (ReqMstBean)ansList.get(i);
			
			if ( ansBean != null ) {
				result += delReqMstFile(conn, reqformno, ansBean.getReqseq(), ansBean.getFileseq());
				
				String delFile = ansBean.getFilenm();
				if ( delFile != null && delFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delFile));
				}
			}
		}

		return result;
	}
	
	/**
	 * 신청서 보기 첨부파일 삭제
	 * @param conn
	 * @param spBean
	 * @param context
	 * @throws Exception
	 */
	public int delReqMstFile(Connection conn, int reqformno, int reqseq, ServletContext context) throws Exception {
		
		int result = 0;
		
		ReqMstBean ansBean = getReqMstFile(conn, reqformno, reqseq);
		
		if ( ansBean != null ) {
			delReqMstFile(conn, reqformno, reqseq, ansBean.getFileseq());
			
			String delFile = ansBean.getFilenm();
			if ( delFile != null && delFile.trim().equals("") == false) {
				FileManager.doFileDelete(context.getRealPath(delFile));
			}
		}
		
		return result;
	}

	/**
	 * 임시 테이블(양식정보) 삭제	
	 */
	public int deleteTempAll(String sessi, ServletContext context) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;		
		int result = 0;	
						
		try{
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			//마스터 삭제
			StringBuffer delteQuery = new StringBuffer();
			delteQuery.append("DELETE FROM OUT_REQFORMMST WHERE SESSIONID LIKE ? ");
			
			pstmt = con.prepareStatement(delteQuery.toString());
			pstmt.setString(1, sessi);			
			
			result = pstmt.executeUpdate();				
			ConnectionManager.close(pstmt);
			
			//문항 삭제
			result += deleteArticleAll(con, 0, sessi);				
			//보기 삭제
			result += deleteSampleAll(con, 0, sessi);			
			
			//첨부파일 삭제
			List reqSubList = getReqFormSubFile(con, sessi, 0);
			
			for ( int i = 0; reqSubList != null && i < reqSubList.size(); i++ ) {
				ArticleBean atcBean = (ArticleBean)reqSubList.get(i);
				delReqFormSubFile(con, sessi, 0, atcBean.getFormseq(), atcBean.getFileseq());
				
				String delSubFile = atcBean.getFilenm();
				if ( delSubFile != null && delSubFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delSubFile));
				}
			}
			
			List reqExamList = getReqFormExamFile(con, sessi, 0, 0);
			
			for ( int j = 0; reqExamList != null && j < reqExamList.size(); j++ ) {
				SampleBean spBean = (SampleBean)reqExamList.get(j);
				delReqFormExamFile(con, sessi, 0, spBean.getFormseq(), spBean.getExamseq(), spBean.getFileseq());
				
				String delExamFile = spBean.getFilenm();
				if ( delExamFile != null && delExamFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delExamFile));
				}
			}
			
			con.commit();
		} catch (Exception e) {
			try{
				con.rollback();
			} catch(Exception ex){
				logger.error("ERROR",ex);
//				throw ex;
			}
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt);
			 throw e;
	     } finally {	
//	    	 try{ 
//		 		con.setAutoCommit(true);
//	    	 } catch (Exception e){
//				logger.error("ERROR",e);
////				throw e;
//	    	 }
			ConnectionManager.close(con,pstmt);	   
	     }	
		return result;
	}
	
	/**
	 * 신청서 양식 관련 모든 테이블 삭제	
	 */
	public int deleteAll(int reqformno, String userid, ServletContext context) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;		
		int result = 0;	
						
		try{
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			//마스터 삭제(양식)
			StringBuffer delteQuery1 = new StringBuffer();
			delteQuery1.append("DELETE FROM OUT_REQFORMMST WHERE REQFORMNO = ? AND CHRGUSRID = ? ");
			
			pstmt = con.prepareStatement(delteQuery1.toString());
			pstmt.setInt(1, reqformno);			
			pstmt.setString(2, userid);
			
			result = pstmt.executeUpdate();						
			ConnectionManager.close(pstmt);			
			if(result == 0){return 0;}   //적용된 건이 없으면 오류 처리..
			
			//문항 삭제(양식)
			result += deleteArticleAll(con, reqformno, "");				
			//보기 삭제(양식)
			result += deleteSampleAll(con, reqformno, "");
			
			//첨부파일 삭제
			result += delReqFormSubExamAllFile(con, "", reqformno, context);
			
			//신청내역삭제
			String[] dtable = {"OUT_REQMST","OUT_REQSUB", "OUT_REQSANC"};			
			
			for(int i=0;i<dtable.length;i++){
				StringBuffer delteQuery2 = new StringBuffer();
				delteQuery2.append("DELETE FROM "+dtable[i]+" WHERE REQFORMNO = ? ");
				
				pstmt = con.prepareStatement(delteQuery2.toString());				
				pstmt.setInt(1, reqformno);			
							
				result += pstmt.executeUpdate();
				ConnectionManager.close(pstmt);
			}
			
			//신청내역첨부파일 삭제
			result += delReqMstAllFile(con, reqformno, context);
			
			con.commit();
		} catch (Exception e) {
			try{
				con.rollback();
			} catch(Exception ex){
				logger.error("ERROR",ex);
//				throw ex;
			}
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt);
			 throw e;
	     } finally {	
//	    	 try{ 
//		 		con.setAutoCommit(true);
//	    	 } catch (Exception e){
//				logger.error("ERROR",e);
////				throw e;
//	    	 }
			ConnectionManager.close(con,pstmt);	   
	     }	
		return result;
	}
	
	/**
	 * 신청취소
	 */
	public int cancelSinchung(int reqformno, int reqseq, String userid, ServletContext context) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;		
		int result = 0;	
						
		try{
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			//마스터삭제(입력정보)
			StringBuffer delteQuery1 = new StringBuffer();
			delteQuery1.append("DELETE FROM OUT_REQMST ");
			delteQuery1.append("WHERE REQFORMNO = ? ");
			delteQuery1.append("  AND REQSEQ = ? ");
			delteQuery1.append("  AND CRTUSRID = ? ");
			
			pstmt = con.prepareStatement(delteQuery1.toString());
			pstmt.setInt(1, reqformno);	
			pstmt.setInt(2, reqseq);
			pstmt.setString(3, userid);
			
			result = pstmt.executeUpdate();						
			ConnectionManager.close(pstmt);			
			if(result == 0){return 0;}   //적용된 건이 없으면 오류 처리..
			
			//추가항목 내용 삭제
			StringBuffer delteQuery2 = new StringBuffer();
			delteQuery2.append("DELETE FROM OUT_REQSUB ");
			delteQuery2.append("WHERE REQFORMNO = ? ");
			delteQuery2.append("  AND REQSEQ = ? ");		
			
			pstmt = con.prepareStatement(delteQuery2.toString());
			pstmt.setInt(1, reqformno);	
			pstmt.setInt(2, reqseq);	
			
			result += pstmt.executeUpdate();						
			ConnectionManager.close(pstmt);	
			
			//결재내용 삭제
			StringBuffer delteQuery3 = new StringBuffer();
			delteQuery3.append("DELETE FROM OUT_REQSANC ");
			delteQuery3.append("WHERE REQFORMNO = ? ");
			delteQuery3.append("  AND REQSEQ = ? ");		
			
			pstmt = con.prepareStatement(delteQuery3.toString());
			pstmt.setInt(1, reqformno);	
			pstmt.setInt(2, reqseq);	
			
			result += pstmt.executeUpdate();						
			ConnectionManager.close(pstmt);	
			
			//첨부파일삭제
			result += delReqMstFile(con, reqformno, reqseq, context);
			
			con.commit();
		} catch (Exception e) {
			try{
				result = 0;
				con.rollback();
			} catch(Exception ex){
				logger.error("ERROR",ex);
//				throw ex;
			}
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt);
			 throw e;
	     } finally {	
//	    	 try{ 
//		 		con.setAutoCommit(true);
//	    	 } catch (Exception e){
//				logger.error("ERROR",e);
////				throw e;
//	    	 }
			ConnectionManager.close(con,pstmt);	   
	     }	
		return result;
	}
	
	/**
	 * 신청취소
	 */
	public int cancelSanc(int reqformno, int reqseq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;		
		int result = 0;	
						
		try{			
			//결재내용 삭제
			StringBuffer sql = new StringBuffer();
			sql.append("DELETE FROM OUT_REQSANC ");
			sql.append("WHERE REQFORMNO = ? ");
			sql.append("  AND REQSEQ = ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, reqformno);
			pstmt.setInt(2, reqseq);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt);
			throw e;
	    } finally {
			ConnectionManager.close(con,pstmt);	   
	    }	
	    
		return result;
	}

	/**
	 * 임시 마스터에 값이 있는지 확인(Temp)
	 */
	public boolean existCheck_temp(String sessi) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) FROM OUT_REQFORMMST WHERE SESSIONID LIKE ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, sessi);			
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				if(rs.getInt(1) > 0){
					result = true;
				}
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
	 * 신청 신청내역 저장
	 */
	public int insertReqData(ReqMstBean mbean, String sessi, ServletContext context, String saveDir) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;		
		int result = 0;					
		int cnt = 0;
		try{
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			//마스터 저장
			StringBuffer insertQuery1 = new StringBuffer();
			insertQuery1.append("INSERT INTO OUT_REQMST(REQFORMNO, REQSEQ, PRESENTNM, PRESENTID, PRESENTSN, PRESENTBD, ");
			insertQuery1.append("                   POSITION,  DUTY,   ZIP,       ADDR1,     ADDR2, ");
			insertQuery1.append("                   EMAIL,     TEL,    CEL,       FAX,       STATE, ");
			insertQuery1.append("                   CRTDT,     CRTUSRID) ");
			insertQuery1.append("VALUES(?,?,?,?,?,?,  ?,?,?,?,?,   ?,?,?,?,?,  TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'),?)");
			int reqformno = mbean.getReqformno();
			int reqseq = getMaxReqseq(mbean.getReqformno());
			
			pstmt = con.prepareStatement(insertQuery1.toString());		
			pstmt.setInt(1, reqformno);
			pstmt.setInt(2, reqseq);
			pstmt.setString(3, mbean.getPresentnm());
			pstmt.setString(4, mbean.getPresentid());
			pstmt.setString(5, mbean.getPresentsn());
			pstmt.setString(6, mbean.getPresentbd());
			pstmt.setString(7, mbean.getPosition());
			pstmt.setString(8, mbean.getDuty());
			/*logger.info(insertQuery1.toString());
			logger.info("1 : "+ reqformno);
			logger.info("2 : "+ reqseq);
			logger.info("3 : "+ mbean.getPresentnm());
			logger.info("4 : "+ mbean.getPresentid());
			logger.info("5 : "+ mbean.getPresentsn());
			logger.info("6 : "+ mbean.getPresentbd());
			logger.info("7 : "+ mbean.getPosition());
			logger.info("8 : "+ mbean.getDuty());*/
			//주소입력 처리
			String addr = mbean.getAddr1();
			String addr_temp = "";
			String zip_temp = "";
			if(addr != null && addr.length()>9){
				zip_temp = addr.substring(1,6);
				addr_temp = addr.substring(9);
			}
			pstmt.setString(9, zip_temp);
			pstmt.setString(10, addr_temp);
			pstmt.setString(11, mbean.getAddr2());
			pstmt.setString(12, mbean.getEmail());
			pstmt.setString(13, mbean.getTel());
			pstmt.setString(14, mbean.getCel());
			pstmt.setString(15, mbean.getFax());
			pstmt.setString(16, "02");    //신청중
			
			pstmt.setString(17, mbean.getCrtusrid());
			
			cnt = pstmt.executeUpdate();		
			ConnectionManager.close(pstmt);			
			
			//추가문항 저장
			if(mbean.getAnscontList() != null){
				
				StringBuffer insertQuery2 = new StringBuffer();
				insertQuery2.append("INSERT INTO OUT_REQSUB(REQFORMNO, REQSEQ, FORMSEQ, ANSCONT, OTHER) ");
				insertQuery2.append("VALUES(?,?,?,?,?)");
									
				pstmt = con.prepareStatement(insertQuery2.toString());
				//logger.info(insertQuery2.toString());
				for(int i=0;i<mbean.getAnscontList().size();i++){
					ReqSubBean sbean = (ReqSubBean)mbean.getAnscontList().get(i);
					
					pstmt.clearParameters();
					pstmt.setInt(1, reqformno);
					pstmt.setInt(2, reqseq);
					pstmt.setInt(3, sbean.getFormseq());
					pstmt.setString(4, sbean.getAnscont());
					pstmt.setString(5, sbean.getOther());
					/*logger.info("1 : "+ reqformno);
					logger.info("2 : "+ reqseq);
					logger.info("3 : "+ sbean.getFormseq());
					logger.info("4 : "+ sbean.getAnscont());
					logger.info("5 : "+ sbean.getOther());*/
					cnt += pstmt.executeUpdate();	
				}		
				ConnectionManager.close(pstmt);	
			}

			mbean.setReqseq(reqseq);
			//신청내역 첨부파일 추가
			cnt += addReqMstFile(con, mbean, context, saveDir);
			
			if ( cnt > 0 ) {
		    	 result = reqseq;
		    }
			
			con.commit();
		} catch (Exception e) {
			try{
				con.rollback();
			} catch(Exception ex){
				logger.error("ERROR",ex);
//				throw ex;
			}
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt);
			 throw e;
	     } finally {	
//	    	 try{ 
//		 		con.setAutoCommit(true);
//	    	 } catch (Exception e){
//				logger.error("ERROR",e);
////				throw e;
//	    	 }
			ConnectionManager.close(con,pstmt);	   
	     }	
	     
	     return result;
	}
	
	/**
	 * 신청 신청내역 수정
	 */
	public int updateReqData(ReqMstBean mbean, ServletContext context, String saveDir) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;		
		int result = 0;					
				
		try{
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			//마스터 수정
			StringBuffer updateQuery1 = new StringBuffer();
			updateQuery1.append("UPDATE OUT_REQMST SET PRESENTNM = ?, PRESENTID = ?, PRESENTSN = ?, PRESENTBD = ?, POSITION = ?,  DUTY = ?, ");
			updateQuery1.append("                  ZIP = ?,       ADDR1 = ?,     ADDR2 = ?,     EMAIL = ?,     TEL = ?, ");
			updateQuery1.append("                  CEL = ?,       FAX = ?,       UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), UPTUSRID = ? ");		
			updateQuery1.append("WHERE REQFORMNO = ? ");
			updateQuery1.append("  AND REQSEQ = ? ");			
			
			int reqformno = mbean.getReqformno();
			int reqseq = mbean.getReqseq();
			
			pstmt = con.prepareStatement(updateQuery1.toString());		
			
			pstmt.setString(1, mbean.getPresentnm());
			pstmt.setString(2, mbean.getPresentid());
			pstmt.setString(3, mbean.getPresentsn());
			pstmt.setString(4, mbean.getPresentbd());
			pstmt.setString(5, mbean.getPosition());
			pstmt.setString(6, mbean.getDuty());
			/*logger.info(updateQuery1.toString());
			logger.info("1 : "+ mbean.getPresentnm());
			logger.info("2 : "+ mbean.getPresentid());
			logger.info("3 : "+ mbean.getPresentsn());
			logger.info("4 : "+ mbean.getPresentbd());
			logger.info("5 : "+ mbean.getPosition());
			logger.info("6 : "+ mbean.getDuty());*/
			//주소입력 처리
			String addr = mbean.getAddr1();
			String addr_temp = "";
			String zip_temp = "";
			if(addr != null && addr.length()>9){
				zip_temp = addr.substring(1,6);
				addr_temp = addr.substring(9);
			}
			pstmt.setString(7, zip_temp);
			pstmt.setString(8, addr_temp);
			pstmt.setString(9, mbean.getAddr2());
			pstmt.setString(10, mbean.getEmail());
			pstmt.setString(11, mbean.getTel());
			pstmt.setString(12, mbean.getCel());
			pstmt.setString(13, mbean.getFax());					
			pstmt.setString(14, mbean.getUptusrid());			
			pstmt.setInt(15, reqformno);
			pstmt.setInt(16, reqseq);
						
			result = pstmt.executeUpdate();		
			ConnectionManager.close(pstmt);		
			
			//추가문항 수정
			if(mbean.getAnscontList() != null){
				
				StringBuffer updateQuery2 = new StringBuffer();
				updateQuery2.append("UPDATE OUT_REQSUB SET ANSCONT = ?, OTHER = ? ");
				updateQuery2.append("WHERE REQFORMNO = ? ");
				updateQuery2.append("  AND REQSEQ = ? ");
				updateQuery2.append("  AND FORMSEQ = ? ");				
				
				pstmt = con.prepareStatement(updateQuery2.toString());
				//logger.info(updateQuery2.toString());
				for(int i=0;i<mbean.getAnscontList().size();i++){
					ReqSubBean sbean = (ReqSubBean)mbean.getAnscontList().get(i);
					
					pstmt.clearParameters();					
					pstmt.setString(1, sbean.getAnscont());
					pstmt.setString(2, sbean.getOther());
					pstmt.setInt(3, sbean.getReqformno());
					pstmt.setInt(4, sbean.getReqseq());
					pstmt.setInt(5, sbean.getFormseq());
					/*logger.info("1 : "+ sbean.getAnscont());
					logger.info("2 : "+ sbean.getOther());
					logger.info("3 : "+ sbean.getReqformno());
					logger.info("4 : "+ sbean.getReqseq());
					logger.info("5 : "+ sbean.getFormseq());*/
					result += pstmt.executeUpdate();	
				}		
				ConnectionManager.close(pstmt);	
			}
			
			if ( mbean.getAttachFileYN().equals("N") == true ||
					(mbean.getAttachFile() != null && mbean.getAttachFile().getFileName().trim().equals("") == false) ) {
				//신청내역 첨부파일 삭제
				result += delReqMstFile(con, reqformno, reqseq, context);
			}
			if (  mbean.getAttachFileYN().equals("N") == false &&
					mbean.getAttachFile() != null && mbean.getAttachFile().getFileName().trim().equals("") == false ) {
				//신청내역 첨부파일 추가
				result += addReqMstFile(con, mbean, context, saveDir);
			}
			
			con.commit();
		} catch (Exception e) {
			try{
				con.rollback();
			} catch(Exception ex){
				logger.error("ERROR",ex);
//				throw ex;
			}
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt);
			 throw e;
	     } finally {	
//	    	 try{ 
//		 		con.setAutoCommit(true);
//	    	 } catch (Exception e){
//				logger.error("ERROR",e);
////				throw e;
//	    	 }
			ConnectionManager.close(con,pstmt);	   
	     }	
		return result;
	}
	
	/**
	 * 신청내역 첨부파일 추가
	 * @param con
	 * @param rmBean
	 * @param context
	 * @param saveDir
	 * @return
	 */
	public int addReqMstFile(Connection con, ReqMstBean rmBean, ServletContext context, String saveDir) throws Exception {

		int result = 0;
		
		File saveFolder = new File(context.getRealPath(saveDir));
		if(!saveFolder.exists()) saveFolder.mkdirs();
		
		ReqMstBean bean = getReqMstFile(con, rmBean.getReqformno(), rmBean.getReqseq());

		if ( bean != null && (rmBean.getAttachFile() != null && rmBean.getAttachFile().getFileName().equals("") == false) ) {
			delReqMstFile(con, bean.getReqformno(), bean.getReqseq(), bean.getFileseq());
			
			String delFile = bean.getFilenm();
			if ( delFile != null && delFile.trim().equals("") == false) {
				FileManager.doFileDelete(context.getRealPath(delFile));
			}
		}

		if ( rmBean.getAttachFile() != null && rmBean.getAttachFile().getFileName().equals("") == false ) {
			FileBean fileBean = FileManager.doFileUpload(rmBean.getAttachFile(), context, saveDir);
			
			if(fileBean != null) {
				int addResult = 0;
				fileBean.setFileseq(1);
				addResult = addReqAnsFile(con, rmBean.getReqformno(), rmBean.getReqseq(), fileBean);
				if ( addResult == 0 ) {
					throw new Exception("첨부실패:DB업데이트");
				}
				result = addResult;
			} else {
				throw new Exception("첨부실패:파일업로드");
			}
		}
		
		return result;
	}
	
	/**
	 * 접수처리
	 * gbn: 일괄접수처리토글(all), 접수완료(1), 접수보류(2), 미결재(3), 신청중(4)
	 */
	public int procJupsu(String gbn, int reqformno, int reqseq) throws Exception {
		Connection con = null;        
		PreparedStatement pstmt = null;
		int result = 0;
				
		try{
			StringBuffer updateQuery = new StringBuffer();
			
			updateQuery.append("UPDATE OUT_REQMST SET STATE = ? ");
			updateQuery.append("WHERE REQFORMNO = ? ");
			updateQuery.append("  AND REQSEQ = ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(updateQuery.toString());
			
			if("1".equals(gbn)){
				pstmt.setString(1, "03");
			} else if("2".equals(gbn)){
				pstmt.setString(1, "04");
			} else if("3".equals(gbn)){
				pstmt.setString(1, "01");
			} else {
				pstmt.setString(1, "02");
			}
			
			pstmt.setInt(2, reqformno);
			pstmt.setInt(3, reqseq);				
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {		
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt);
			 throw e;
	     } finally {	       
	    	 ConnectionManager.close(con,pstmt);   
	     }
		return result;
	}
	
	/**
	 * 일괄접수처리 토글 : 전체접수완료가 아니면 전체접수완료처리, 전체접수완료처리면 전체보류처리
	 */
	public int procTotalJupsu(int reqformno) throws Exception {
		Connection con = null;        
		PreparedStatement pstmt = null;
		int result = 0;
				
		try{
			StringBuffer updateQuery = new StringBuffer();
			
			updateQuery.append("UPDATE OUT_REQMST \n");
			updateQuery.append("SET STATE = (SELECT DECODE(SUM(DECODE(STATE, '03', 1, 0)), COUNT(*), '04', '03') \n");
			updateQuery.append("             FROM OUT_REQMST \n");
			updateQuery.append("             WHERE REQFORMNO = ?) \n");
			updateQuery.append("WHERE REQFORMNO = ? \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(updateQuery.toString());
			
			pstmt.setInt(1, reqformno);
			pstmt.setInt(2, reqformno);				
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {		
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt);
			 throw e;
	     } finally {	       
	    	 ConnectionManager.close(con,pstmt);   
	     }
		return result;
	}
	
	/**
	 * 결재선 승인/검토 데이터 가져오기
	 * gubun: 검토(1),  승인(2)
	 */
	public List approvalInfo(String gubun, int reqformno, int reqseq) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List apprInfoList = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT GUBUN, SANCUSRID, SANCUSRNM, SANCYN, SANCDT, SUBMITDT ");
			selectQuery.append("FROM OUT_REQSANC ");
			selectQuery.append(" WHERE REQFORMNO = ? ");	
			selectQuery.append("   AND REQSEQ = ? ");
			selectQuery.append("   AND GUBUN = ? ");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, reqformno);	
			pstmt.setInt(2, reqseq);
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
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return apprInfoList;
	}
	
	/**
	 * 신청서 결재
	 */
	public int doSanc (int reqformno, int reqseq, String userid) throws Exception{
		Connection con = null;        
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result =0;
				
		try {
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			StringBuffer updateQuery1 = new StringBuffer();
	
			updateQuery1.append("UPDATE OUT_REQSANC SET SANCYN = '1', SANCDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), ");
			updateQuery1.append("                   SUBMITDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS')	");
			updateQuery1.append("WHERE REQFORMNO = ? ");
			updateQuery1.append("  AND REQSEQ = ? ");
			updateQuery1.append("  AND SANCUSRID = ? ");
			
			pstmt = con.prepareStatement(updateQuery1.toString());		
		    pstmt.setInt(1, reqformno);		      
		    pstmt.setInt(2, reqseq);
		    pstmt.setString(3, userid);
			
			result = pstmt.executeUpdate();	
			ConnectionManager.close(pstmt);
			if(result == 0){return 0;}   //적용된 건이 없으면 오류 처리..
			
			//마지막 결재자인지 확인후 마스터 상태변경
			StringBuffer selectQuery1 = new StringBuffer();
			
			selectQuery1.append("SELECT COUNT(REQFORMNO) ");
			selectQuery1.append("FROM OUT_REQSANC ");
			selectQuery1.append("WHERE REQFORMNO = ? ");
			selectQuery1.append("  AND REQSEQ = ? ");
			selectQuery1.append("  AND SANCUSRID <> ? ");
			selectQuery1.append("  AND SANCYN = '0' ");
			
			pstmt = con.prepareStatement(selectQuery1.toString());	
			pstmt.setInt(1, reqformno);
			pstmt.setInt(2, reqseq);
			pstmt.setString(3, userid);
			
			rs = pstmt.executeQuery();
			
			int notSanc = 0;
			if(rs.next()){
				notSanc = rs.getInt(1);
			}
			ConnectionManager.close(pstmt, rs);
			
			//결재자 본인을 제외하고 미결재 건이 없으면 마스터의 상태를 변경한다.
			if(notSanc == 0){
				StringBuffer updateQuery2 = new StringBuffer();
				
				updateQuery2.append("UPDATE OUT_REQMST SET STATE = '02' ");				
				updateQuery2.append("WHERE REQFORMNO = ? ");
				updateQuery2.append("  AND REQSEQ = ? ");			
				
				pstmt = con.prepareStatement(updateQuery2.toString());		
			    pstmt.setInt(1, reqformno);		      
			    pstmt.setInt(2, reqseq);			 
				
				result = pstmt.executeUpdate();			
				ConnectionManager.close(pstmt);
			}
			
			con.commit();
		} catch (Exception e) {
			try{	
				con.rollback();
			} catch(Exception ex){
				logger.error("ERROR",ex);
//				throw ex;
			}
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt);
			 throw e;
	     } finally {	
//	    	 try{ 
//		 		con.setAutoCommit(true);
//	    	 } catch (Exception e){
//				logger.error("ERROR",e);
////				throw e;
//	    	 }
			ConnectionManager.close(con,pstmt);	   
	     }	
		return result;
	}
	
	/**
	 * 결재된 건이 있는지 확인
	 */
	public boolean existSanc(int reqformno, int reqseq) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) ");
			selectQuery.append("FROM OUT_REQSANC ");
			selectQuery.append("WHERE REQFORMNO = ? ");
			selectQuery.append("  AND REQSEQ = ? ");
			selectQuery.append("  AND SANCYN = '1' ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, reqformno);	
			pstmt.setInt(2, reqseq);
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				if(rs.getInt(1) > 0){
					result = true;
				}
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
	 * 신청서 마감 	
	 */
	public int docClose(int reqformno, String userid) throws Exception {
		Connection con = null;        
		PreparedStatement pstmt = null;
		int result = 0;
				
		try{
			//신청서를 마감할 경우 하루전 날짜로 셋팅한다.
			StringBuffer updateQuery = new StringBuffer();
			
			updateQuery.append("UPDATE OUT_REQFORMMST SET ENDDT = TO_CHAR(SYSDATE - 1, 'YYYY-MM-DD') ");
			updateQuery.append("WHERE REQFORMNO = ? ");
			updateQuery.append("  AND CHRGUSRID = ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(updateQuery.toString());
			
			pstmt.setInt(1, reqformno);
			pstmt.setString(2, userid);
				
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {		
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt);
			 throw e;
		} finally {	       
	    	 ConnectionManager.close(con,pstmt);   
		}
		return result;
	}
	
	/**
	 * 신청마스터에서 신청 일련번호 가져오기 (REQSEQ)
	 */
	public int getMaxReqseq(int reqformno) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int maxseq = 0;
				
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT NVL(MAX(REQSEQ),0)+1 FROM OUT_REQMST WHERE REQFORMNO = ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			pstmt.setInt(1, reqformno);
								
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				maxseq = rs.getInt(1);
			}		
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return maxseq;
	}
	
	/**
	 * 최종결재자 성명 가져오기
	 */
	private String lastSancName(int reqformno, int reqseq) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
				
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT SANCUSRNM \n");
			selectQuery.append("FROM OUT_REQSANC \n");
			selectQuery.append("WHERE REQFORMNO = ? \n");
			selectQuery.append("AND REQSEQ = ? \n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, reqformno);
			pstmt.setInt(2, reqseq);
			
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				result = rs.getString("SANCUSRNM");
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
	 * 마스터 INSERT(TEMP)
	 */
	private int insertMst(Connection con, FrmBean fbean) throws Exception{
		PreparedStatement pstmt = null;	
		int result = 0;	
		
		StringBuffer insertQuery1 = new StringBuffer();
		
		insertQuery1.append("INSERT INTO OUT_REQFORMMST(REQFORMNO, TITLE,     STRDT,     ENDDT,    COLDEPTCD, ");
		insertQuery1.append("                            COLDEPTNM, COLDEPTTEL, CHRGUSRID, CHRGUSRNM, SUMMARY,  RANGE, ");
		insertQuery1.append("                            IMGPREVIEW, DUPLICHECK, LIMITCOUNT, SANCNEED,  BASICTYPE, HEADCONT, ");
		insertQuery1.append("                            TAILCONT, CRTDT, CRTUSRID) ");
		insertQuery1.append("VALUES(?,?,?,?,?,   ?,?,?,?,?,?,   ?,?,?,?,?,?,   ?,TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'),?) ");
		
		pstmt = con.prepareStatement(insertQuery1.toString());
		pstmt.setInt(1, fbean.getReqformno());
		pstmt.setString(2, fbean.getTitle());
		pstmt.setString(3, fbean.getStrdt());
		pstmt.setString(4, fbean.getEnddt());
		pstmt.setString(5, fbean.getColdeptcd());
		pstmt.setString(6, fbean.getColdeptnm());
		pstmt.setString(7, fbean.getColtel());
		pstmt.setString(8, fbean.getChrgusrid());
		pstmt.setString(9, fbean.getChrgusrnm());
		pstmt.setString(10, fbean.getSummary());
		pstmt.setString(11, ( "1".equals(fbean.getRange()) ) ? fbean.getRange() : fbean.getRangedetail());
		pstmt.setString(12, fbean.getImgpreview());
		pstmt.setString(13, fbean.getDuplicheck());
		pstmt.setInt(14, fbean.getLimitcount());
		pstmt.setString(15, fbean.getSancneed());
		pstmt.setString(16, fbean.getBasictype());
		pstmt.setString(17, fbean.getHeadcont());
		pstmt.setString(18, fbean.getTailcont());
		pstmt.setString(19, fbean.getCrtusrid());
		
		result = pstmt.executeUpdate();		
		ConnectionManager.close(pstmt);
		
		for(int i=0;i<fbean.getAcnt();i++){
			StringBuffer insertQuery2 = new StringBuffer();
			
			insertQuery2.append("INSERT INTO OUT_REQFORMSUB(REQFORMNO, FORMSEQ, FORMTITLE, REQUIRE, FORMTYPE, ");
			insertQuery2.append("                            SECURITY, HELPEXP, EXAMTYPE, NVL(EX_FRSQ, 0) AS EX_FRSQ, NVL(EX_EXSQ, 0) AS EX_EXSQ) ");
			insertQuery2.append("VALUES(?, ?, '', 'N', '3',   'N', '', 'N', 0, 0) ");
			pstmt = con.prepareStatement(insertQuery2.toString());
			pstmt.setInt(1, fbean.getReqformno());
			pstmt.setInt(2, i+1);
			
			result += pstmt.executeUpdate();			
			ConnectionManager.close(pstmt);
		}
		
		return result;
	}
	
	/**
	 * 문항 저장
	 * gbn : 1(REQFORMMST), 2(REQFORMMST)
	 */
	private int insertArticleAll(Connection con, List articeList , String gbn) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		String tb = "";
		String kfield = "";
		
		if("TEMP".equals(gbn)){
			tb = "OUT_REQFORMSUB";
			kfield = "SESSIONID";
		} else {
			tb = "OUT_REQFORMSUB";
			kfield = "REQFORMNO";
		}	
		
		if(articeList != null){
			for(int i=0;i<articeList.size();i++){
				ArticleBean abean = (ArticleBean)articeList.get(i);
				
				StringBuffer insertQuery = new StringBuffer();
				insertQuery.append("INSERT INTO "+tb+" ("+kfield+", FORMSEQ, FORMTITLE, REQUIRE, FORMTYPE, ");
				insertQuery.append("                    SECURITY, HELPEXP, EXAMTYPE) ");
				insertQuery.append("VALUES(?,?,?,?,?,  ?,?,?)");
				
				pstmt = con.prepareStatement(insertQuery.toString());		
				
				if("TEMP".equals(gbn)){
					pstmt.setString(1, abean.getSessi());
				} else {
					pstmt.setInt(1, abean.getReqformno());
				}
				
				if (abean.getFormtype().equals("1") || abean.getFormtype().equals("2")) {
					abean.setRequire("N");
				}
				
				pstmt.setInt(2, abean.getFormseq());
				pstmt.setString(3, abean.getFormtitle());
				pstmt.setString(4, abean.getRequire());
				pstmt.setString(5, abean.getFormtype());
				pstmt.setString(6, abean.getSecurity());
				pstmt.setString(7, abean.getHelpexp());
				pstmt.setString(8, abean.getExamtype());
				
				result += pstmt.executeUpdate();				
				ConnectionManager.close(pstmt);
				
				//보기 저장
				result += insertSampleAll(con, abean.getSampleList(), abean.getFormseq(), gbn);
			}
		}
		
		return result;
	}

	/**
	 * 보기 저장
	 * gbn : TEMP(REQFORMMST), NULL(REQFORMMST)
	 */
	private int insertSampleAll(Connection con, List sampleList, int formseq, String gbn) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		String tb = "";
		String kfield = "";
		
		if("TEMP".equals(gbn)){
			tb = "OUT_REQFORMEXAM";
			kfield = "SESSIONID";
		} else {
			tb = "OUT_REQFORMEXAM";
			kfield = "REQFORMNO";
		}		
		
		if(sampleList != null){
			for(int i=0;i<sampleList.size();i++){
				SampleBean sbean = (SampleBean)sampleList.get(i);
				
				if ( "".equals(sbean.getExamcont().trim()) ) continue;
				
				StringBuffer insertQuery = new StringBuffer();
				insertQuery.append("INSERT INTO "+tb+" ("+kfield+", FORMSEQ, EXAMSEQ, EXAMCONT) ");
				insertQuery.append("VALUES(?,?,?,?)");
				
				pstmt = con.prepareStatement(insertQuery.toString());	
				
				if("TEMP".equals(gbn)){
					pstmt.setString(1, sbean.getSessi());
				} else {
					pstmt.setInt(1, sbean.getReqformno());
				}
				
				pstmt.setInt(2, formseq);
				pstmt.setInt(3, i+1);
				pstmt.setString(4, sbean.getExamcont());			
				
				result += pstmt.executeUpdate();				
				ConnectionManager.close(pstmt);
			}
		}
		
		return result;
	}
	
	/**
	 * 마스터 UPDATE
	 * gbn : TEMP(REQFORMMST), NULL(REQFORMMST)
	 */
	private int updateMst(Connection con, FrmBean fbean, String gbn) throws Exception{
		PreparedStatement pstmt = null;
		int result = 0;		
		String tb = "";
		String kfield = "";
		
		if("TEMP".equals(gbn)){
			tb = "OUT_REQFORMMST";
			kfield = "SESSIONID";
		} else {
			tb = "OUT_REQFORMMST";
			kfield = "REQFORMNO";
		}	
		
		StringBuffer updateQuery = new StringBuffer();
       	
	 	updateQuery.append("UPDATE "+tb+" SET TITLE = ?,     STRDT = ?,     ENDDT = ?,    COLDEPTCD = ?, COLDEPTNM = ?, COLDEPTTEL = ?, ");
	 	updateQuery.append("				  CHRGUSRID = ?, CHRGUSRNM = ?, SUMMARY = ?,  RANGE = ?, IMGPREVIEW = ?, DUPLICHECK = ?, LIMITCOUNT = ?, ");
	 	updateQuery.append("                  SANCNEED = ?, BASICTYPE = ?, HEADCONT = ?,  TAILCONT = ?, UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), UPTUSRID = ? ");	 	
	 	updateQuery.append("WHERE "+kfield+" = ? ");
	 	
	 	pstmt = con.prepareStatement(updateQuery.toString());						
		pstmt.setString(1, fbean.getTitle());
		pstmt.setString(2, fbean.getStrdt());
		pstmt.setString(3, fbean.getEnddt());
		pstmt.setString(4, fbean.getColdeptcd());
		pstmt.setString(5, fbean.getColdeptnm());
		pstmt.setString(6, fbean.getColtel());
		pstmt.setString(7, fbean.getChrgusrid());
		pstmt.setString(8, fbean.getChrgusrnm());
		pstmt.setString(9, fbean.getSummary());
		pstmt.setString(10, ( "1".equals(fbean.getRange()) ) ? fbean.getRange() : fbean.getRangedetail());
		pstmt.setString(11, fbean.getImgpreview());
		pstmt.setString(12, fbean.getDuplicheck());
		pstmt.setInt(13, fbean.getLimitcount());
		pstmt.setString(14, fbean.getSancneed());
		pstmt.setString(15, fbean.getBasictype());
		pstmt.setString(16, fbean.getHeadcont());
		pstmt.setString(17, fbean.getTailcont());
		pstmt.setString(18, fbean.getUptusrid());
		
		if("TEMP".equals(gbn)){
			pstmt.setString(19, fbean.getSessi());
		} else {
			pstmt.setInt(19, fbean.getReqformno());
		}	
		
		result = pstmt.executeUpdate();			
		ConnectionManager.close(pstmt);
		
		return result;
	}
	
	/**
	 * 전체 문항삭제
	 */
	private int deleteArticleAll(Connection con, int reqformno, String sessi) throws Exception {
		PreparedStatement pstmt = null;		
		int result = 0;	
		String tb = "";
		String kfield = "";
		
		if(reqformno == 0){
			tb = "OUT_REQFORMSUB";
			kfield = "SESSIONID";
		} else {
			tb = "OUT_REQFORMSUB";
			kfield = "REQFORMNO";
		}	
		
		StringBuffer delteQuery = new StringBuffer();
		delteQuery.append("DELETE FROM "+tb+" WHERE "+kfield+" LIKE ? ");
	 
		pstmt = con.prepareStatement(delteQuery.toString());
		
		if(reqformno == 0){
			pstmt.setString(1, sessi);
		} else {
			pstmt.setInt(1, reqformno);
		}	
		
		result = pstmt.executeUpdate();	
		ConnectionManager.close(pstmt);
		
		return result;
	}
	
	/**
	 * 전체 보기 삭제
	 */
	private int deleteSampleAll(Connection con, int reqformno, String sessi) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		String tb = "";
		String kfield = "";
		
		if(reqformno == 0){	
			tb = "OUT_REQFORMEXAM";
			kfield = "SESSIONID";
		} else {
			tb = "OUT_REQFORMEXAM";
			kfield = "REQFORMNO";
		}	
		
		StringBuffer delteQuery = new StringBuffer();
		delteQuery.append("DELETE FROM "+tb+" WHERE "+kfield+" LIKE ? ");
	 
		pstmt = con.prepareStatement(delteQuery.toString());
		
		if(reqformno == 0){	
			pstmt.setString(1, sessi);
		} else {
			pstmt.setInt(1, reqformno);
		}	
		
		result = pstmt.executeUpdate();			
		ConnectionManager.close(pstmt);
		
		return result;
	}
	
	/**
	 * 특정 항목의 보기삭제
	 */
	private int deleteSample(Connection con, int reqformno, String sessi, int formseq) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		String tb = "";
		String kfield = "";
		
		if(reqformno == 0){	
			tb = "OUT_REQFORMEXAM";
			kfield = "SESSIONID";
		} else {
			tb = "OUT_REQFORMEXAM";
			kfield = "REQFORMNO";
		}	
		
		StringBuffer delteQuery = new StringBuffer();
		delteQuery.append("DELETE FROM "+tb+" WHERE "+kfield+" = ? AND FORMSEQ = ? ");
	 
		pstmt = con.prepareStatement(delteQuery.toString());
		
		if(reqformno == 0){	
			pstmt.setString(1, sessi);
		} else {
			pstmt.setInt(1, reqformno);
		}	
		
		pstmt.setInt(2, formseq);
		
		result = pstmt.executeUpdate();			
		ConnectionManager.close(pstmt);
		
		return result;
	}
	
	/**
	 * 항목 테이블(REQFORMSUB 또는 REQFORMSUB) 의 FORMSEQ MAX값 가져오기	
	 */
	public int getMaxFormseq(int reqformno, String sessi) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int maxseq = 0;
		String tb = "";
		String kfield = "";
		
		if(reqformno == 0){
			tb = "OUT_REQFORMSUB";
			kfield = "SESSIONID";
		} else {
			tb = "OUT_REQFORMSUB";
			kfield = "REQFORMNO";
		}	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT NVL(MAX(FORMSEQ),0)+1 FROM "+tb+" WHERE "+kfield+" = ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if(reqformno == 0){
				pstmt.setString(1, sessi);	
			} else {
				pstmt.setInt(1, reqformno);
			}
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				maxseq = rs.getInt(1);
			}		
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return maxseq;
	}
	
	/**
	 * 항목 테이블(REQFORMSUB 또는 REQFORMSUB) 의 FORMSEQ 갯수 가져오기	 
	 */
	private int getCntFormseq(int reqformno, String sessi) throws Exception{
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		String tb = "";
		String kfield = "";
		
		if(reqformno == 0){
			tb = "OUT_REQFORMSUB";
			kfield = "SESSIONID";
		} else {
			tb = "OUT_REQFORMSUB";
			kfield = "REQFORMNO";
		}	
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) FROM "+tb+" WHERE "+kfield+" = ? ");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if(reqformno == 0){
				pstmt.setString(1, sessi);	
			} else {
				pstmt.setInt(1, reqformno);
			}
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				count = rs.getInt(1);
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
	 * 신청서 기간상태 체크하기
	 * @param reqformno
	 * @return
	 * @throws Exception
	 */
	public int reqState(int reqformno, String userid) throws Exception {
		Connection con = null;        
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
				
		try{
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("\n SELECT COUNT(REQFORMNO) ");
			selectQuery.append("\n   FROM OUT_REQFORMMST  ");
			selectQuery.append("\n  WHERE REQFORMNO = ? ");
			selectQuery.append("\n    AND STRDT <= TO_CHAR(SYSDATE,'YYYY-MM-DD') ");			
			selectQuery.append("\n    AND (ENDDT >= TO_CHAR(SYSDATE,'YYYY-MM-DD') OR ENDDT IS NULL) ");
			if ( "강남3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){ 
//			    selectQuery.append("\n    AND TO_CHAR(SYSDATE,'HH24MISS') > NVL(STRTM,'000000') ");
			}
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			
			pstmt.setInt(1, reqformno);
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				result = rs.getInt(1);
			}
			ConnectionManager.close(pstmt, rs);
			if ( result == 0 ) {
			    selectQuery.delete(0, selectQuery.capacity());
			    selectQuery.append("\n SELECT COUNT(REQFORMNO) ");
	            selectQuery.append("\n   FROM OUT_REQFORMMST  ");
	            selectQuery.append("\n  WHERE REQFORMNO = ? ");
	            if ( "강남3220000".indexOf(nexti.ejms.common.appInfo.getRootid()) != -1 ){
	                selectQuery.append("\n    AND STRDT > TO_CHAR(SYSDATE,'YYYY-MM-DD') ");
//	                selectQuery.append("\n         OR TO_CHAR(SYSDATE,'HH24MISS') < NVL(STRTM,'000000')) ");
	            }else{
	                selectQuery.append("\n    AND STRDT > TO_CHAR(SYSDATE,'YYYY-MM-DD') ");
	            }
	            
//	            con = ConnectionManager.getConnection();
	            pstmt = con.prepareStatement(selectQuery.toString());
	            
	            pstmt.setInt(1, reqformno);
	            
	            rs = pstmt.executeQuery();
	            
	            if ( rs.next() ){
	                if ( rs.getInt(1) > 0 ){
	                    result = -1;
	                }
	            }
			}
			ConnectionManager.close(pstmt, rs);
			if ( result > 0 ) {
				selectQuery.delete(0, selectQuery.capacity());
				selectQuery.append("\n SELECT DECODE(LIMITCOUNT, 0, COUNT(REQSEQ) + 1, LIMITCOUNT) - COUNT(REQSEQ) ");
				selectQuery.append("\n FROM OUT_REQFORMMST RM, OUT_REQMST RA ");
				selectQuery.append("\n WHERE RM.REQFORMNO = RA.REQFORMNO(+)  ");
				selectQuery.append("\n AND RM.REQFORMNO = ? ");
				selectQuery.append("\n GROUP BY LIMITCOUNT ");
				
//				ConnectionManager.close(pstmt, rs);
				pstmt = con.prepareStatement(selectQuery.toString());
				
				pstmt.setInt(1, reqformno);
				
				rs = pstmt.executeQuery();
				
				if ( rs.next() ) {
					result = rs.getInt(1);
					if ( result < 0 ) result = 0;
				}
			}
			ConnectionManager.close(pstmt, rs);
			if ( result > 0 ) {			
				selectQuery.delete(0, selectQuery.capacity());
				selectQuery.append("\n SELECT COUNT(R1.REQFORMNO)");
				selectQuery.append("\n FROM OUT_REQMST R1, OUT_REQFORMMST R2");
				selectQuery.append("\n WHERE R1.REQFORMNO = R2.REQFORMNO");
				selectQuery.append("\n AND R2.DUPLICHECK = '2'");
				selectQuery.append("\n AND R1.REQFORMNO = ? AND R1.PRESENTID = ?");
				
//				ConnectionManager.close(pstmt, rs);
				pstmt = con.prepareStatement(selectQuery.toString());
				
				pstmt.setInt(1, reqformno);
				pstmt.setString(2, userid);
				
				rs = pstmt.executeQuery();
				
				if ( rs.next() ){
					result = rs.getInt(1);
					
					if ( result > 0 ) {
						result = -1;
					} else {
						result = 1;
					}
				}
			}			
		} catch (Exception e) {		
			 logger.error("ERROR", e);
			 ConnectionManager.close(con,pstmt, rs);
			 throw e;
		} finally {	       
	    	 ConnectionManager.close(con,pstmt, rs);
		}
		return result;
	}	
	
	/**
	 * 
	 * @param usrid
	 * @param deptcd
	 * @param schtitle
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List getReqOutsideList() throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List list = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append(" SELECT T1.REQFORMNO  " +
							   "   FROM OUT_REQFORMMST T1 " +
							   "  WHERE T1.RANGE LIKE '2%' \n");
			selectQuery.append("    AND T1.STRDT <= TO_CHAR(SYSDATE,'YYYY-MM-DD') ");			
			selectQuery.append("    AND (T1.ENDDT >= TO_CHAR(SYSDATE,'YYYY-MM-DD') OR T1.ENDDT IS NULL)  \n");	
			selectQuery.append("  ORDER BY CRTDT DESC \n");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
									
			rs = pstmt.executeQuery();
			
			list = new ArrayList();
			
			while (rs.next()) {
				ReqMstBean Bean = new ReqMstBean();
				Bean.setReqformno(rs.getInt("REQFORMNO"));
				
				list.add(Bean);
			}
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}		
		return list;
	}
	
	/**
	 * 문항당 보기개수 가져오기
	 * @param reqformno
	 * @return
	 * @throws Exception
	 */
	public int getReqSubExamcount(int reqformno, String sessionId) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			if ( reqformno == 0 ) {
				selectQuery.append("SELECT MAX(COUNT(EXAMSEQ)) \n");
				selectQuery.append("FROM OUT_REQFORMEXAM \n");
				selectQuery.append("WHERE SESSIONID LIKE ? \n");
				selectQuery.append("GROUP BY FORMSEQ \n");
			} else {
				selectQuery.append("SELECT MAX(COUNT(EXAMSEQ)) \n");
				selectQuery.append("FROM OUT_REQFORMEXAM \n");
				selectQuery.append("WHERE REQFORMNO = ? \n");
				selectQuery.append("GROUP BY FORMSEQ \n");
			}
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if ( reqformno == 0) {
				pstmt.setString(1, sessionId);
			} else {
				pstmt.setInt(1, reqformno);
			}
									
			rs = pstmt.executeQuery();
						
			if ( rs.next() ) {
				result = rs.getInt(1);
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
	 * 결재진행중인 건수 체크
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public int apprProcCount(String userid) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			
			selectQuery.append("SELECT COUNT(*) \n");
			selectQuery.append("FROM OUT_REQSANC \n"); 
			selectQuery.append("WHERE SANCYN = 0 \n");
			selectQuery.append("AND REQFORMNO IN (SELECT DISTINCT REQFORMNO \n");
			selectQuery.append("                  FROM OUT_REQMST \n");
			selectQuery.append("                  WHERE PRESENTID = ?) \n");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, userid);
									
			rs = pstmt.executeQuery();
			 
			if ( rs.next() == true ) {
				result = rs.getInt(1);
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
///////////////////////////////////////////////////////////
/////////////////////// 신청서 DAO 끝 ////////////////////////
///////////////////////////////////////////////////////////	
}