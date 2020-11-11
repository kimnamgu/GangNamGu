/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 자료이관 manager
 * 설명:
 */
package nexti.ejms.dataTransfer.model;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;

public class DataTransferManager {
	
	private static Logger logger = Logger.getLogger(DataTransferManager.class);
	
	private static DataTransferManager instance = null;
	private static DataTransferDAO dao = null;
	
	private DataTransferManager() {}
	
	public static DataTransferManager instance() {
		if(instance == null) instance = new DataTransferManager();
		return instance;
	}

	private DataTransferDAO getDataTransferDAO() {		
		if(dao == null) dao = new DataTransferDAO(); 
		return dao;
	}
	
	/**
	 * 원본자료 부서목록
	 * @return
	 * @throws Exception
	 */
	public String getOrgDeptList(String selectDeptid, String selectOrggbn) throws Exception {
		String result = null;
		StringBuffer sb = new StringBuffer();

		sb.append("<option value=''>---------전체---------</option><br>");
		
		List deptList = getDataTransferDAO().getOrgDeptList(selectOrggbn);
		for ( int i = 0; deptList != null && i < deptList.size(); i++ ) {
			DataTransferBean dtBean = (DataTransferBean)deptList.get(i);
			String deptid = dtBean.getDeptid();
			String deptnm = dtBean.getDeptnm();
			
			if ( deptid.equals(selectDeptid) == true ) {
				sb.append("<option value='"); sb.append(deptid); sb.append("' selected "); 
			} else {
				sb.append("<option value='"); sb.append(deptid); sb.append("' ");
			}
			sb.append("title='"); sb.append(deptnm); sb.append(" ("); sb.append(deptid); sb.append(")"); sb.append("'>");
			sb.append(deptnm); sb.append(" ("); sb.append(deptid); sb.append(")");
			sb.append("</option><br>");
		}
		
		result = sb.toString();
		return result;
	}
	
	/**
	 * 원본자료 사용자목록
	 * @param deptid
	 * @param selectUserid
	 * @return
	 * @throws Exception
	 */
	public String getOrgUserList(String deptid, String orggbn, String selectUserid) throws Exception {
		String result = null;
		StringBuffer sb = new StringBuffer();
		
		sb.append("<option value=''>-------전체-------</option><br>");
		
		List deptList = getDataTransferDAO().getOrgUserList(deptid, orggbn);
		for ( int i = 0; deptList != null && i < deptList.size(); i++ ) {
			DataTransferBean dtBean = (DataTransferBean)deptList.get(i);
			String userid = dtBean.getUserid();
			String usernm = dtBean.getUsernm();
			
			if ( userid.equals(selectUserid) == true ) {
				sb.append("<option value='"); sb.append(userid); sb.append("' selected "); 
			} else {
				sb.append("<option value='"); sb.append(userid); sb.append("' ");
			}
			sb.append("title='"); sb.append(usernm); sb.append(" ("); sb.append(userid); sb.append(")"); sb.append("'>");
			sb.append(usernm); sb.append(" ("); sb.append(userid); sb.append(")");
			sb.append("</option><br>");
		}
		
		result = sb.toString();
		return result;
	}
	
	/**
	 * 대상자료 부서목록
	 * @return
	 * @throws Exception
	 */
	public String getTgtDeptList(String orggbn) throws Exception {
		String result = null;
		StringBuffer sb = new StringBuffer();
		
		sb.append("<option value=''>---------전체---------</option><br>");
		
		List deptList = getDataTransferDAO().getTgtDeptList(orggbn);
		for ( int i = 0; deptList != null && i < deptList.size(); i++ ) {
			DataTransferBean dtBean = (DataTransferBean)deptList.get(i);
			String deptid = dtBean.getDeptid();
			String deptnm = dtBean.getDeptnm();
			
			sb.append("<option value='"); sb.append(deptid); sb.append("' ");
			sb.append("title='"); sb.append(deptid); sb.append(" ("); sb.append(deptnm); sb.append(")"); sb.append("'>");
			sb.append(deptnm); sb.append(" ("); sb.append(deptid); sb.append(")");
			sb.append("</option><br>");
		}
		
		result = sb.toString();
		return result;
	}
	
	/**
	 * 대상자료 사용자목록
	 * @param deptid
	 * @return
	 * @throws Exception
	 */
	public String getTgtUserList(String deptid) throws Exception {
		String result = null;
		StringBuffer sb = new StringBuffer();
		
		sb.append("<option value=''>사용자를선택하세요.</option><br>");
		
		if ( deptid.equals("") == false ) {
			List deptList = getDataTransferDAO().getTgtUserList(deptid);
			for ( int i = 0; deptList != null && i < deptList.size(); i++ ) {
				DataTransferBean dtBean = (DataTransferBean)deptList.get(i);
				String userid = dtBean.getUserid();
				String usernm = dtBean.getUsernm();
				
				sb.append("<option value='"); sb.append(userid); sb.append("' ");
				sb.append("title='"); sb.append(usernm); sb.append(" ("); sb.append(userid); sb.append(")"); sb.append("'>");
				sb.append(usernm); sb.append(" ("); sb.append(userid); sb.append(")");
				sb.append("</option><br>");
			}
		}
		
		result = sb.toString();
		return result;
	}
	
	/**
	 * 대상자료 사용자목록(코드,값,코드,값...)
	 * @param deptid
	 * @return
	 * @throws Exception
	 */
	public String getTgtUserValueList(String deptid) throws Exception {
		String result = null;
		StringBuffer sb = new StringBuffer();
		
		List deptList = getDataTransferDAO().getTgtUserList(deptid);
		for ( int i = 0; deptList != null && i < deptList.size(); i++ ) {
			DataTransferBean dtBean = (DataTransferBean)deptList.get(i);
			String userid = dtBean.getUserid();
			String usernm = dtBean.getUsernm();
			
			sb.append(userid); sb.append(","); sb.append(usernm);
			
			if ( i + 1 < deptList.size() ) sb.append(",");
		}
		
		result = sb.toString();
		return result;
	}
	
	/**
	 * 원본자료 자료목록
	 * @param deptid
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public List getOrgDataList(String orggbn, String deptid, String userid) throws Exception {
		return getDataTransferDAO().getOrgDataList(orggbn, deptid, userid);
	}
	
	/**
	 * 자료이관처리
	 * @param dtBean
	 * @return
	 * @throws Exception
	 */
	public int dataTransferProc(DataTransferBean dtBean) throws Exception {
		int result = 0;
		
		final String[] GUBUN = {"COLLECT", "RESEARCH", "REQUEST", "RESEARCHGROUP"};
		final String[] TABLE = {"DOCMST", "RCHMST", "REQFORMMST", "RCHGRPMST"};
		final String[][] COLUMN = {
				{"SYSDOCNO", "COLDEPTCD", "COLDEPTNM", "CHRGUSRCD", "CHRGUSRNM", "CRTDT", "CRTUSRID", "UPTDT", "UPTUSRID", "CHRGUNITCD", "CHRGUNITNM"},
				{"RCHNO",    "COLDEPTCD", "COLDEPTNM", "CHRGUSRID", "CHRGUSRNM", "CRTDT", "CRTUSRID", "UPTDT", "UPTUSRID", "COLDEPTTEL"},
				{"REQFORMNO","COLDEPTCD", "COLDEPTNM", "CHRGUSRID", "CHRGUSRNM", "CRTDT", "CRTUSRID", "UPTDT", "UPTUSRID", "COLDEPTTEL"},
				{"RCHGRPNO",    "COLDEPTCD", "COLDEPTNM", "CHRGUSRID", "CHRGUSRNM", "CRTDT", "CRTUSRID", "UPTDT", "UPTUSRID", "COLDEPTTEL"}};
		
		Connection conn = null;
		
		try {
			conn = ConnectionManager.getConnection(false);
			
			String[] gbn = dtBean.getGbn();
			String[] gbnid = dtBean.getGbnid();
			String[] tgtuserid = dtBean.getTgtuserid();
			
			for ( int i = 0; i < gbn.length; i++ ) {
				String userid = tgtuserid[i];
				if ( userid.equals("") == true ) continue;
				
				String[] OLDVALUE = null;
				DataTransferBean selectBean = null;
				DataTransferBean updateBean = new DataTransferBean();
				DataTransferBean insertBean = new DataTransferBean();
				int uCnt = 0;
				int iCnt = 0;
				int no = Integer.parseInt("0" + gbnid[i]);
				String gbntype = gbn[i];
				
				//자료종류 판단
				int gbnIdx = -1;
				for ( int j = 0; j < GUBUN.length; j++ ) {
					if ( gbntype.equals(GUBUN[j]) == true ) gbnIdx = j;
				}
				if ( gbnIdx == -1 ) continue;
				
				//원본자료 가져오기, 이관처리
				updateBean.setUserid(userid);
				updateBean.setNo(no);
				if ( gbntype.equals(GUBUN[0]) == true ) {
					selectBean = getDataTransferDAO().selectCollectTable(conn, no);	
					if ( selectBean != null ) {						
						uCnt = getDataTransferDAO().updateCollectTable(conn, updateBean);
						String[] temp = {Integer.toString(selectBean.getNo()),
										 selectBean.getDeptid(), selectBean.getDeptnm(), selectBean.getUserid(),
										 selectBean.getUsernm(), selectBean.getCrtdt(), selectBean.getCrtusrid(),
										 selectBean.getUptdt(), selectBean.getUptusrid(),
										 Integer.toString(selectBean.getChrgunitid()), selectBean.getChrgunitnm()};
						OLDVALUE = (String[])temp.clone();
					}
				} else if ( gbntype.equals(GUBUN[1]) == true ) {
					selectBean = getDataTransferDAO().selectResearchTable(conn, no);	
					if ( selectBean != null ) {
						uCnt = getDataTransferDAO().updateResearchTable(conn, updateBean);
						String[] temp = {Integer.toString(selectBean.getNo()),
										 selectBean.getDeptid(), selectBean.getDeptnm(), selectBean.getUserid(),
										 selectBean.getUsernm(), selectBean.getCrtdt(), selectBean.getCrtusrid(),
										 selectBean.getUptdt(), selectBean.getUptusrid(), selectBean.getDepttel()};
						OLDVALUE = (String[])temp.clone();
					}
				} else if ( gbntype.equals(GUBUN[2]) == true ) {
					selectBean = getDataTransferDAO().selectRequestTable(conn, no);	
					if ( selectBean != null ) {
						uCnt = getDataTransferDAO().updateRequestTable(conn, updateBean);
						String[] temp = {Integer.toString(selectBean.getNo()),
										 selectBean.getDeptid(), selectBean.getDeptnm(), selectBean.getUserid(),
										 selectBean.getUsernm(), selectBean.getCrtdt(), selectBean.getCrtusrid(),
										 selectBean.getUptdt(), selectBean.getUptusrid(), selectBean.getDepttel()};
						OLDVALUE = (String[])temp.clone();
					}
				} else if ( gbntype.equals(GUBUN[3]) == true ) {
					selectBean = getDataTransferDAO().selectResearchGroupTable(conn, no);	
					if ( selectBean != null ) {
						uCnt = getDataTransferDAO().updateResearchGroupTable(conn, updateBean);
						String[] temp = {Integer.toString(selectBean.getNo()),
										 selectBean.getDeptid(), selectBean.getDeptnm(), selectBean.getUserid(),
										 selectBean.getUsernm(), selectBean.getCrtdt(), selectBean.getCrtusrid(),
										 selectBean.getUptdt(), selectBean.getUptusrid(), selectBean.getDepttel()};
						OLDVALUE = (String[])temp.clone();
					}
				}
				
				//이관이력기록
				if ( uCnt > 0 ) {
					int histtoryno = getDataTransferDAO().getNextHistoryNo(conn);
					for ( int j = 0; uCnt > 0 && j < COLUMN[gbnIdx].length; j++ ) {
						insertBean.setHistoryno(histtoryno);
						insertBean.setSeq(getDataTransferDAO().getNextSeq(conn, histtoryno));
						insertBean.setTablename(TABLE[gbnIdx]);
						insertBean.setColumnname(COLUMN[gbnIdx][j]);
						insertBean.setOldvalue(OLDVALUE[j]);
						insertBean.setCause(dtBean.getCause());
						insertBean.setCrtusrid(userid);
						
						iCnt += getDataTransferDAO().insertHistory(conn, insertBean);
					}
				}
				
				if ( iCnt == COLUMN[gbnIdx].length ) {
					result = iCnt;
				} else {
					result = 0;
					break;
				}
			}
			
			//이력기록이 이관시 변경데이터수와 동일할 때 적용
			if ( result > 0 ) {
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (Exception e) {
			try {conn.rollback();} catch (Exception ex) {logger.error("error", ex);}
			logger.error("error", e);
		} finally {
			ConnectionManager.close(conn);
		}
		
		return result;
	}
}