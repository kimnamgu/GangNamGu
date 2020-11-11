/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합양식 manager
 * 설명:
 */
package nexti.ejms.format.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import java.io.FileNotFoundException;
import java.sql.Connection;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

import nexti.ejms.attr.model.AttrBean;
import nexti.ejms.attr.model.AttrManager;
import nexti.ejms.common.ConnectionManager;
import nexti.ejms.common.appInfo;
import nexti.ejms.commtreat.model.CommTreatManager;
import nexti.ejms.formatBook.model.FileBookBean;
import nexti.ejms.formatBook.model.FormatBookManager;
import nexti.ejms.formatFixed.model.FormatFixedBean;
import nexti.ejms.formatFixed.model.FormatFixedManager;
import nexti.ejms.formatLine.model.FormatLineBean;
import nexti.ejms.formatLine.model.FormatLineManager;
import nexti.ejms.util.FileManager;

public class FormatManager {
	
	private static Logger logger = Logger.getLogger(FormatManager.class);
	
	private static FormatManager instance = null;
	private static FormatDAO dao = null;
	
	private FormatManager() {
		
	}
	
	public static FormatManager instance() {
		
		if(instance == null)
			instance = new FormatManager();
		return instance;
	}

	private FormatDAO getFormatDAO() {
		
		if(dao == null)
			dao = new FormatDAO(); 
		return dao;
	}
	
	/**
	 * 입력부서 전체정보
	 * @param sysdocno
	 * @param deptid
	 * @param sch_deptInfo
	 * @return
	 * @throws Exception
	 */
	public InputDeptSearchBoxBean getInputDeptInfoAll(int sysdocno, String userid, String deptid, InputDeptSearchBoxBean sch_bean, String formkind) throws Exception {
		InputDeptSearchBoxBean result = new InputDeptSearchBoxBean();
		final int LEVEL = 6;
		
		String[] sch_deptInfo = new String[LEVEL + 2]; 
		sch_deptInfo[0] = sch_bean.getSch_deptcd1();
		sch_deptInfo[1] = sch_bean.getSch_deptcd2();
		sch_deptInfo[2] = sch_bean.getSch_deptcd3();
		sch_deptInfo[3] = sch_bean.getSch_deptcd4();
		sch_deptInfo[4] = sch_bean.getSch_deptcd5();
		sch_deptInfo[5] = sch_bean.getSch_deptcd6();
		sch_deptInfo[6] = sch_bean.getSch_chrgunitcd();
		sch_deptInfo[7] = sch_bean.getSch_inputusrid();
		List list = getFormatDAO().getInputDeptInfoAll(sysdocno, userid, deptid, sch_deptInfo, formkind);
		
		String cd1 = "";	String nm1 = "";	String cd2 = "";	String nm2 = "";
		String cd3 = "";	String nm3 = "";	String cd4 = "";	String nm4 = "";
		String cd5 = "";	String nm5 = "";	String cd6 = "";	String nm6 = "";
		String cd7 = "";	String nm7 = "";	String cd8 = "";	String nm8 = "";
		Vector entries1 = null;	Vector entries2 = null;	Vector entries3 = null;
		Vector entries4 = null;	Vector entries5 = null;	Vector entries6 = null;
		Vector entries7 = null;	Vector entries8 = null;
		
		String[][] info = null;
		String selected = "";
		int depth = CommTreatManager.instance().getTargetDeptLevel(sysdocno, deptid);
		
		info = (String[][])list.get(0);
		for ( int i = 0; i < info[0].length; i++ ) {
			for ( int j = 0; entries1 != null && j < entries1.size(); j++ ) {
				if ( ((LabelValueBean)entries1.get(j)).getValue().indexOf(info[0][i] + "\"") != -1 ) {
					info[0][i] = "";
					break;
				}
			}
			if ( !cd1.equals(info[0][i]) && !"".equals(info[0][i]) ) {
				cd1 = info[0][i];
				nm1 = info[1][i];
				if ( entries1 == null ) {
					entries1 = new Vector();
					if ( depth < 1 ) {
						entries1.add(new LabelValueBean("1차취합부서 : 전체", ""));
					}
				}
				if ( cd1.equals(sch_bean.getSch_deptcd1()) ) selected = "selected";
				entries1.add(new LabelValueBean(nm1, cd1 + "\" " + selected + " title=\"" + nm1));
				selected = "";
			}
		}
		
		info = (String[][])list.get(1);
		for ( int i = 0; i < info[0].length; i++ ) {
			for ( int j = 0; entries2 != null && j < entries2.size(); j++ ) {
				if ( ((LabelValueBean)entries2.get(j)).getValue().indexOf(info[0][i] + "\"") != -1 ) {
					info[0][i] = "";
					break;
				}
			}
			if ( !cd2.equals(info[0][i]) && !"".equals(info[0][i]) ) {
				cd2 = info[0][i];
				nm2 = info[1][i];
				if ( entries2 == null ) {
					entries2 = new Vector();
					if ( depth < 2 ) {
						entries2.add(new LabelValueBean("2차취합부서 : 전체", ""));
					}
				}
				if ( cd2.equals(sch_bean.getSch_deptcd2()) ) selected = "selected";
				entries2.add(new LabelValueBean(nm2, cd2 + "\" " + selected + " title=\"" + nm2));
				selected = "";
			}
		}
		
		info = (String[][])list.get(2);
		for ( int i = 0; i < info[0].length; i++ ) {
			for ( int j = 0; entries3 != null && j < entries3.size(); j++ ) {
				if ( ((LabelValueBean)entries3.get(j)).getValue().indexOf(info[0][i] + "\"") != -1 ) {
					info[0][i] = "";
					break;
				}
			}
			if ( !cd3.equals(info[0][i]) && !"".equals(info[0][i]) ) {
				cd3 = info[0][i];
				nm3 = info[1][i];
				if ( entries3 == null ) {
					entries3 = new Vector();
					if ( depth < 3 ) {
						entries3.add(new LabelValueBean("3차취합부서 : 전체", ""));
					}
				}
				if ( cd3.equals(sch_bean.getSch_deptcd3()) ) selected = "selected";
				entries3.add(new LabelValueBean(nm3, cd3 + "\" " + selected + " title=\"" + nm3));
				selected = "";
			}
		}

		info = (String[][])list.get(3);
		for ( int i = 0; i < info[0].length; i++ ) {
			for ( int j = 0; entries4 != null && j < entries4.size(); j++ ) {
				if ( ((LabelValueBean)entries4.get(j)).getValue().indexOf(info[0][i] + "\"") != -1 ) {
					info[0][i] = "";
					break;
				}
			}
			if ( !cd4.equals(info[0][i]) && !"".equals(info[0][i]) ) {
				cd4 = info[0][i];
				nm4 = info[1][i];
				if ( entries4 == null ) {
					entries4 = new Vector();
					if ( depth < 4 ) {
						entries4.add(new LabelValueBean("4차취합부서 : 전체", ""));
					}
				}
				if ( cd4.equals(sch_bean.getSch_deptcd4()) ) selected = "selected";
				entries4.add(new LabelValueBean(nm4, cd4 + "\" " + selected + " title=\"" + nm4));
				selected = "";
			}
		}
		
		info = (String[][])list.get(4);
		for ( int i = 0; i < info[0].length; i++ ) {
			for ( int j = 0; entries5 != null && j < entries5.size(); j++ ) {
				if ( ((LabelValueBean)entries5.get(j)).getValue().indexOf(info[0][i] + "\"") != -1 ) {
					info[0][i] = "";
					break;
				}
			}
			if ( !cd5.equals(info[0][i]) && !"".equals(info[0][i]) ) {
				cd5 = info[0][i];
				nm5 = info[1][i];
				if ( entries5 == null ) {
					entries5 = new Vector();
					if ( depth < 5 ) {
						entries5.add(new LabelValueBean("5차취합부서 : 전체", ""));
					}
				}
				if ( cd5.equals(sch_bean.getSch_deptcd5()) ) selected = "selected";
				entries5.add(new LabelValueBean(nm5, cd5 + "\" " + selected + " title=\"" + nm5));
				selected = "";
			}
		}
		
		info = (String[][])list.get(5);
		for ( int i = 0; i < info[0].length; i++ ) {
			for ( int j = 0; entries6 != null && j < entries6.size(); j++ ) {
				if ( ((LabelValueBean)entries6.get(j)).getValue().indexOf(info[0][i] + "\"") != -1 ) {
					info[0][i] = "";
					break;
				}
			}
			if ( !cd6.equals(info[0][i]) && !"".equals(info[0][i]) ) {
				cd6 = info[0][i];
				nm6 = info[1][i];
				if ( entries6 == null ) {
					entries6 = new Vector();
					if ( depth < 6 ) {
						entries6.add(new LabelValueBean("6차취합부서 : 전체", ""));
					}
				}
				if ( cd6.equals(sch_bean.getSch_deptcd6()) ) selected = "selected";
				entries6.add(new LabelValueBean(nm6, cd6 + "\" " + selected + " title=\"" + nm6));
				selected = "";
			}
		}
			
		info = (String[][])list.get(6);
		for ( int i = 0; i < info[0].length; i++ ) {
			for ( int j = 0; entries7 != null && j < entries7.size(); j++ ) {
				if ( ((LabelValueBean)entries7.get(j)).getValue().indexOf(info[0][i] + "\"") != -1 ) {
					info[0][i] = "";
					break;
				}
			}
			if ( !cd7.equals(info[0][i]) && !"".equals(info[0][i]) ) {
				cd7 = info[0][i];
				nm7 = info[1][i];
				if ( entries7 == null ) {
					entries7 = new Vector();
					if ( depth < 7 ) {
						entries7.add(new LabelValueBean("입력담당단위명 : 전체", ""));
					}
				}
				if ( cd7.equals(sch_bean.getSch_chrgunitcd()) ) selected = "selected";
				entries7.add(new LabelValueBean(nm7, cd7 + "\" " + selected + " title=\"" + nm7));
				selected = "";
			}
		}
			
		info = (String[][])list.get(7);
		for ( int i = 0; i < info[0].length; i++ ) {
			for ( int j = 0; entries8 != null && j < entries8.size(); j++ ) {
				if ( ((LabelValueBean)entries8.get(j)).getValue().indexOf(info[0][i] + "\"") != -1 ) {
					info[0][i] = "";
					break;
				}
			}
			if ( !cd8.equals(info[0][i]) && !"".equals(info[0][i]) ) {
				cd8 = info[0][i];
				nm8 = info[1][i];
				if ( entries8 == null ) {
					entries8 = new Vector();
					if ( depth < 8 ) {
						entries8.add(new LabelValueBean("입력담당자명 : 전체", ""));
					}
				}
				if ( cd8.equals(sch_bean.getSch_inputusrid()) ) selected = "selected";
				entries8.add(new LabelValueBean(nm8, cd8 + "\" " + selected + " title=\"" + nm8));
				selected = "";
			}
		}
		
		result.setSch_deptcd1(sch_bean.getSch_deptcd1());
		result.setSch_deptcd2(sch_bean.getSch_deptcd2());
		result.setSch_deptcd3(sch_bean.getSch_deptcd3());
		result.setSch_deptcd4(sch_bean.getSch_deptcd4());
		result.setSch_deptcd5(sch_bean.getSch_deptcd5());
		result.setSch_deptcd6(sch_bean.getSch_deptcd6());
		result.setSch_chrgunitcd(sch_bean.getSch_chrgunitcd());
		result.setSch_inputusrid(sch_bean.getSch_inputusrid());
		result.setSch_deptcd1_collection(entries1);
		result.setSch_deptcd2_collection(entries2);
		result.setSch_deptcd3_collection(entries3);
		result.setSch_deptcd4_collection(entries4);
		result.setSch_deptcd5_collection(entries5);
		result.setSch_deptcd6_collection(entries6);
		result.setSch_chrgunitcd_collection(entries7);
		result.setSch_inputusrid_collection(entries8);
		
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
		return getFormatDAO().getInputDeptInfo(con, sysdocno, deptid);
	}
	
	/**
	 * 입력부서 부서정보
	 * @param sysdocno
	 * @param deptid
	 * @return
	 * @throws Exception
	 */
	public String[][] getInputDeptInfo(int sysdocno, String deptid) throws Exception {
		String[][] result = null;
		Connection con = null;
		
		try {
			con = ConnectionManager.getConnection();
			
			result = getInputDeptInfo(con, sysdocno, deptid);
		} catch ( Exception e ) {
			ConnectionManager.close(con);
			throw e;
		} finally {
			ConnectionManager.close(con);
		}
		
		return result;
	}
	
	/**
	 * 엑셀파일 내용 저장
	 * @param sysdocno
	 * @param formseq
	 * @param formkind
	 * @param xlsData
	 * @param dept_code
	 * @param user_id
	 * @param chrgunitcd 
	 * @return
	 * @throws Exception
	 */
	public boolean xlsUpload(int sysdocno, int formseq, String formkind, List xlsData,
			String dept_code, String user_id, int chrgunitcd) throws Exception {
		boolean result = false;
		
		FormatLineManager flmgr = FormatLineManager.instance();
		FormatFixedManager ffmgr = FormatFixedManager.instance();
		
		if ( formkind.equals("01") == true ) {
			FormatBean fbean = getFormat(sysdocno, formseq);
			FormatLineBean flbean = flmgr.FormatLineHtmlSeparator(fbean.getFormhtml());
			
			int rowHeadCnt = flbean.getRowHeadCount();
			int colCnt = flbean.getTblcols();
			
			int retCode = 0;
			String colname = "";
			List colList = null;
			
			for(int i = rowHeadCnt; i < xlsData.size(); i++) {
				colList = new ArrayList();
				
				for(int j = 0; j < colCnt; j++) {
					if ( j < 26 ) colname = (char)('A' + j) + "";
					else colname = (char)('A' + j - 26) + "" + (char)('A' + j - 26);
					
					String cellData = "";
					List rowData = (List)xlsData.get(i);
					if ( rowData.size() > j ) {
						cellData = (String)rowData.get(j);
					}
					
					String[] cellatt = flmgr.getFormatLineAtt(sysdocno, formseq);
					String[] att = cellatt[j].split("[|]");
					
					if ( "04".equals(att[0]) ) { 
						List attList = FormatLineManager.instance().getAttListData(att[1]);
						if ( attList == null || attList.size() == 0 ) att = "02|1".split("[|]");
					}
					
					if(att[0].equals("01") == true) {	//숫자일때
						try { Double.parseDouble(cellData); } catch (Exception e) { cellData = ""; }
					} else if(att[0].equals("02") == true) {	//문자(단문)일때
						if ( Integer.parseInt(att[1]) < 2) cellData = cellData.replaceAll("\r|\n", "");
					} else if(att[0].equals("03") == true) {	//수식일때
						cellData = "autocal";
					} else if(att[0].equals("04") == true) {	//목록일때
						List dtlList = AttrManager.instance().attrDtlList(att[1]);
						if(dtlList == null || dtlList.size() == 0) {
							cellData = "";
						} else {
							for(int k = 0; k < dtlList.size(); k++) {
								if ( cellData.equals(((AttrBean)dtlList.get(k)).getListdtlnm()) == true ) break;
								else if( k + 1 == dtlList.size() ) cellData = "";
							}		
						}
					}	
					colList.add(colname + ":" + cellData.trim());
				}

				retCode += flmgr.insertFormatLineData(sysdocno, formseq, colList, colCnt, dept_code, user_id, chrgunitcd, "insert");
			}
			
			if ( retCode > 0 ) {
				result = true;
			}
 
		} else if ( formkind.equals("02") == true ) {
			FormatBean fbean = getFormat(sysdocno, formseq);
			FormatFixedBean ffbean = ffmgr.FormatFixedHtmlSeparator(fbean.getFormhtml());
			
			int rowHeadCnt = ffbean.getRowHeadCount();
			int rowCnt = ffbean.getTblrows();
			int colCnt = ffbean.getTblcols();
			
			String colname = "";
			List colList = null;
			List rowList = new ArrayList();
			
			for(int i = rowHeadCnt; i < rowCnt + rowHeadCnt; i++) {
				colList = new ArrayList();

				for(int j = 0; j < colCnt; j++) {
					if ( j < 26 ) colname = (char)('A' + j) + "";
					else colname = (char)('A' + j - 26) + "" + (char)('A' + j - 26);
					
					String cellData = "";
					List rowData = null;
					if ( xlsData.size() > i ) {
						rowData = (List)xlsData.get(i);
						int dataStartIndex = rowData.size() - colCnt + j;
						if ( rowData.size() > dataStartIndex ) {
							cellData = (String)rowData.get(dataStartIndex);
						}
					}

					String[] cellatt = ffmgr.getFormatFixedAtt(sysdocno, formseq);
					String[] att = cellatt[j].split("[|]");
					
					if ( "04".equals(att[0]) ) { 
						List attList = FormatLineManager.instance().getAttListData(att[1]);
						if ( attList == null || attList.size() == 0 ) att = "02|1".split("[|]");
					}
					
					if(att[0].equals("01") == true) {	//숫자일때
						try { Double.parseDouble(cellData); } catch (Exception e) { cellData = ""; }
					} else if(att[0].equals("02") == true) {	//문자(단문)일때
						if ( Integer.parseInt(att[1]) < 2) cellData = cellData.replaceAll("\r|\n", "");
					} else if(att[0].equals("03") == true) {	//수식일때
						cellData = "autocal";
					} else if(att[0].equals("04") == true) {	//목록일때
						List dtlList = AttrManager.instance().attrDtlList(att[1]);
						if(dtlList == null || dtlList.size() == 0) {
							cellData = "";
						} else {
							for(int k = 0; k < dtlList.size(); k++) {
								if ( cellData.equals(((AttrBean)dtlList.get(k)).getListdtlnm()) == true ) break;
								else if( k + 1 == dtlList.size() ) cellData = "";
							}		
						}
					}	
					colList.add(colname + ":" + cellData.trim());
				}
				rowList.add(colList);
			}
			
			int retCode = 0;		
			retCode = ffmgr.insertFormatFixedData(sysdocno, formseq, rowList, colCnt, dept_code, user_id, chrgunitcd, "insert");
			if ( retCode > 0 ) {
				result = true;
			}
		}
		
		return result;
	}
	
	/**
	 * 사용했던양식 가져오기
	 * @param FormatBean fbean
	 * @param String user_id
	 * @param String filedir
	 * @param boolean force(첨부파일이 없을때 무시하고 진행:true)
	 * @throws Exception
	 */
	public void formatGetUsed(FormatBean fbean, String user_id, String filedir, boolean force) throws Exception {
		
		Connection conn = null;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			copyUsedFormat(conn, fbean, user_id);
			
			if(fbean.getFormkind().equals("03") == true)
				copyUsedFormatFile(conn, fbean, filedir, force);
			
			conn.commit();
		} catch(Exception e) {
			try {
				conn.rollback();
			} catch(Exception ex) {
				logger.error("ERROR", ex);
			}
			ConnectionManager.close(conn);
			throw e;
		} finally {
			ConnectionManager.close(conn);
		}
	}
	
	/**
	 * 사용했던양식 속성복사
	 * @param Connection conn
	 * @param FormatBean fbean
	 * @param String user_id
	 * @throws Exception
	 */
	public void copyUsedFormat(Connection conn, FormatBean fbean, String user_id) throws Exception {
		
		getFormatDAO().copyUsedFormat(conn, fbean, user_id);
		
		return;
	}
	
	/**
	 * 사용했던양식 파일복사
	 * @param Connection conn
	 * @param FormatBean fbean
	 * @param String filedir
	 * @param boolean force
	 * @return int 수행개수
	 * @throws Exception
	 */
	public int copyUsedFormatFile(Connection conn, FormatBean fbean, String filedir, boolean force) throws Exception {
		
		int result = 0;
		
		Calendar cal = Calendar.getInstance();
		
		FormatBookManager fbmgr = FormatBookManager.instance();
		
		String srcFile, descFile, descDir;
		
		List filenameList = fbmgr.getListUsedFilename(conn, fbean.getUsedsysdocno(), fbean.getUsedformseq());
		
		for(int i = 0; i < filenameList.size(); i++) {
			
			FileBookBean fbbean = (FileBookBean)filenameList.get(i);

			int fileseq = fbbean.getFileseq();
			String filenm = fbbean.getFilenm();
			
			srcFile = filedir + filenm;
			
			descDir = appInfo.getBookFrmSampleDir() + cal.get(Calendar.YEAR) + "/";
			
			try {
				descFile = FileManager.doFileCopy(srcFile, filedir + descDir);
				descFile = descDir + descFile;	
				result += getFormatDAO().copyUsedFormatFile(conn, fbean, fileseq, descFile);
			} catch(FileNotFoundException e) {
				if(force == false) {
					logger.error("ERROR", e);
					throw e;
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 공통양식 가져오기
	 * @param FormatBean fbean
	 * @param String formatFileDir
	 * @param String saveDir
	 * @param String user_id
	 * @param boolean force(첨부파일이 없을때 무시하고 진행:true)
	 * @throws Exception
	 */
	public void formatGetComm(FormatBean fbean, String user_id, String formatFiledir, String saveDir, boolean force) throws Exception {
		
		Connection conn = null;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			copyCommFormat(conn, fbean, user_id);
			
			if(fbean.getFormkind().equals("03") == true)
				copyCommFormatFile(conn, fbean, formatFiledir, saveDir, force);
			
			conn.commit();
		} catch(Exception e) {
			try {
				conn.rollback();
			} catch(Exception ex) {
				logger.error("ERROR", ex);
			}
			ConnectionManager.close(conn);
			throw e;
		} finally {
			ConnectionManager.close(conn);
		}
	}
	
	/**
	 * 공통양식 속성복사
	 * @param Connection conn
	 * @param FormatBean fbean
	 * @param String user_id
	 * @throws Exception
	 */
	public void copyCommFormat(Connection conn, FormatBean fbean, String user_id) throws Exception {
		
		getFormatDAO().copyCommFormat(conn, fbean, user_id);
		
		return;
	}
	
	/**
	 * 공통양식 파일복사
	 * @param Connection conn
	 * @param FormatBean fbean
	 * @param String formatFileDir
	 * @param String saveDir
	 * @param boolean force
	 * @return int 수행개수
	 * @throws Exception
	 */
	public int copyCommFormatFile(Connection conn, FormatBean fbean, String formatFileDir, String saveDir, boolean force) throws Exception {
		
		int result = 0;
		
		FormatBookManager fbmgr = FormatBookManager.instance();
		
		String srcFile, descFile, descDir;
		
		List filenameList = fbmgr.getListCommFilename(conn, fbean.getCommformseq());
		
		for(int i = 0; i < filenameList.size(); i++) {
			
			FileBookBean fbbean = (FileBookBean)filenameList.get(i);

			int fileseq = fbbean.getFileseq();
			String filenm = fbbean.getFilenm();
			
			srcFile = formatFileDir + filenm;
			
			descDir = formatFileDir + saveDir;
			
			try {
				descFile = FileManager.doFileCopy(srcFile, descDir);
				descFile = saveDir + descFile;
				result += getFormatDAO().copyCommFormatFile(conn, fbean, fileseq, descFile);
			} catch(FileNotFoundException e) {
				if(force == false) {
					logger.error("ERROR", e);
					throw e;
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 공통양식 목록 가져오기
	 * @param FormatBean fbean
	 * @param int start
	 * @param int end
	 * @return List 공통양식목록(ArrayList)
	 * @throws Exception
	 */
	public List getListCommFormat(FormatBean fbean, int start, int end) throws Exception {
		
		List result = null;
		
		result = getFormatDAO().getListCommFormat(fbean, start, end);
	
		return result;
	}
	
	/**
	 * 공통양식관리 목록 가져오기
	 * @param FormatBean fbean
	 * @param int start
	 * @param int end
	 * @return List 공통양식관리목록(ArrayList)
	 * @throws Exception
	 */
	public List getListCommFormatMgr(FormatBean fbean, String user_id, int start, int end) throws Exception {
		
		List result = null;
		
		result = getFormatDAO().getListCommFormatMgr(fbean, user_id, start, end);
	
		return result;
	}
	
	/**
	 * 사용했던양식 목록 가져오기
	 * @param FormatBean fbean
	 * @param int start
	 * @param int end
	 * @return List 사용했던양식목록(ArrayList)
	 * @throws Exception
	 */
	public List getListUsedFormat(FormatBean fbean, int start, int end) throws Exception {
		
		List result = null;
		
		result = getFormatDAO().getListUsedFormat(fbean, start, end);
	
		return result;
	}
	
	/**
	 * 공통양식 개수 가져오기
	 * @param FormatBean fbean
	 * @return int 수행개수
	 * @throws Exception
	 */
	public int getCountCommFormat(FormatBean fbean) throws Exception {
		
		int result = 0;
		
		result = getFormatDAO().getCountCommFormat(fbean);
		
		return result;
	}
	
	/**
	 * 사용했던양식 개수 가져오기
	 * @param FormatBean fbean
	 * @return int 수행개수
	 * @throws Exception
	 */
	public int getCountUsedFormat(FormatBean fbean) throws Exception {
		
		int result = 0;
		
		result = getFormatDAO().getCountUsedFormat(fbean);
		
		return result;
	}
	
	/**
	 * 양식정보 가져오기
	 * @param int sysdocno
	 * @param int formseq
	 * @return String 양식정보
	 * @throws Exception 
	 */
	public FormatBean getFormat(int sysdocno, int formseq) throws Exception {
		
		FormatBean result = null;
		
		result = getFormatDAO().getFormat(sysdocno, formseq);
		
		return result;
	}
	
	/**
	 * 공통양식정보 가져오기
	 * @param int formseq
	 * @return String 양식정보
	 * @throws Exception 
	 */
	public FormatBean getCommFormat(int formseq) throws Exception {
		
		FormatBean result = null;
		
		result = getFormatDAO().getCommFormat(formseq);
		
		return result;
	}
	
	/**
	 * 사용했던양식정보 가져오기
	 * @param int sysdocno
	 * @param int formseq
	 * @return String 양식정보
	 * @throws Exception 
	 */
	public FormatBean getUsedFormat(int sysdocno, int formseq) throws Exception {
		
		FormatBean result = null;
		
		result = getFormatDAO().getUsedFormat(sysdocno, formseq);
		
		return result;
	}
	
	/**
	 * 새로운양식 추가
	 * @param FormatBean bean
	 * @throws Exception 
	 */
	public void addFormat(FormatBean bean) throws Exception {
		
		Connection conn = null;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			addFormat(conn, bean);
			
			conn.commit();
		} catch(Exception e) {
			conn.rollback();
			ConnectionManager.close(conn);
			throw e;
		} finally {
			try {
				conn.setAutoCommit(true);
			}catch(Exception e){
				
			}
			ConnectionManager.close(conn);
		}
	}	
	
	/**
	 * 새로운양식 추가
	 * @param Connection conn
	 * @param FormatBean bean
	 * @throws Exception 
	 */
	public void addFormat(Connection conn, FormatBean bean) throws Exception {
		
		getFormatDAO().addFormat(conn, bean);
	}
	
	/**
	 * 양식 수정
	 * @param Connection conn
	 * @param FormatBean bean
	 * @throws Exception 
	 */
	public void modifyFormat(Connection conn, FormatBean bean) throws Exception {
		
		getFormatDAO().modifyFormat(conn, bean);
	}
	
	/**
	 * 공통양식 수정
	 * @param Connection conn
	 * @param FormatBean bean
	 * @throws Exception 
	 */
	public void modifyCommFormat(Connection conn, FormatBean bean) throws Exception {
		
		getFormatDAO().modifyCommFormat(conn, bean);
	}
	
	/**
	 * 새로운공통양식 추가
	 * @param Connection conn
	 * @param FormatBean bean
	 * @throws Exception 
	 */
	public void addCommFormat(Connection conn, FormatBean bean) throws Exception {
		
		getFormatDAO().addCommFormat(conn, bean);
	}
	
	/**
	 * 양식형태명 가져오기
	 * @param String formkind
	 * @return String 양식형태명
	 * @throws Exception 
	 */
	public String getFormkindName(String formkind) throws Exception {
		
		String result = "";
		
		result = getFormatDAO().getFormkindName(formkind);
		
		return result;
	}
	
	/**
	 * 새로운 양식일련번호 가져오기
	 * @param int sysdocno
	 * @return int 양식일련번호
	 * @throws Exception 
	 */
	public int getNewFormatseq(int sysdocno) throws Exception {
		
		int result = 0;
		
		result = getFormatDAO().getNewFormatseq(sysdocno);
		
		return result;	
	}
	
	/**
	 * 새로운 공통양식일련번호 가져오기
	 * @return int 공통양식일련번호
	 * @throws Exception 
	 */
	public int getNewCommFormatseq() throws Exception {
		
		int result = 0;
		
		result = getFormatDAO().getNewCommFormatseq();
		
		return result;	
	}
	
	/**
	 * 새로운 양식정렬순서 가져오기
	 * @param int sysdocno
	 * @return int 파일정렬순서
	 * @throws Exception 
	 */
	public int getNewFormatOrd(int sysdocno) throws Exception {
		
		int result = 0;
		
		result = getFormatDAO().getNewFormatOrd(sysdocno);
		
		return result;	
	}
	
	/**
	 * 등록된 파일개수 가져오기
	 * @param int sysdocno
	 * @param int formseq
	 * @return int 파일개수
	 * @throws Exception 
	 */
	public int getFilecount(int sysdocno, int formseq) throws Exception {
		
		int result = 0;
		
		result = getFormatDAO().getFilecount(sysdocno, formseq);
		
		return result;	
	}
	
	/**
	 * 등록된 공통양식 파일개수 가져오기
	 * @param int formseq
	 * @return int 파일개수
	 * @throws Exception 
	 */
	public int getCommFilecount(int formseq) throws Exception {
		
		int result = 0;
		
		result = getFormatDAO().getCommFilecount(formseq);
		
		return result;	
	}
	
	/**
	 * 등록된 사용했던양식 파일개수 가져오기
	 * @param int sysdocno
	 * @param int formseq
	 * @return int 파일개수
	 * @throws Exception 
	 */
	public int getUsedFilecount(int sysdocno, int formseq) throws Exception {
		
		int result = 0;
		
		result = getFormatDAO().getUsedFilecount(sysdocno, formseq);
		
		return result;	
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
		
		List result = null;
		
		result = getFormatDAO().getListUsedFormat(deptcd, makedt, title, makeusr, start, end);
		
		return result;
	}
	
	/**
	 * 선택한양식 삭제
	 * @param int sysdocno
	 * @param int formseq
	 * @param ServletContext context
	 * @throws Exception 
	 */
	public void delFormat(int sysdocno, int formseq, ServletContext context) throws Exception {
		
		Connection conn = null;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			delFormat(conn, sysdocno, formseq, context);
			
			conn.commit();
		} catch(Exception e) {
			try {
				conn.rollback();
			} catch(Exception ex) {
				logger.error("ERROR", ex);
			}
			ConnectionManager.close(conn);
			throw e;
		} finally {
			ConnectionManager.close(conn);
		}	
	}
	
	/**
	 * 선택한양식 삭제
	 * @param Connection conn
	 * @param int sysdocno
	 * @param int formseq
	 * @param ServletContext context
	 * @throws Exception 
	 */
	public void delFormat(Connection conn, int sysdocno, int formseq, ServletContext context) throws Exception {
			
		getFormatDAO().delFormat(conn, sysdocno, formseq);
		
		if(context != null) {
			//선택한 취합양식파일 삭제
			FormatBookManager fbmgr = FormatBookManager.instance();
			fbmgr.delAllFileBook(conn, sysdocno, formseq, context);
			fbmgr.delAllFileCompBook(conn, sysdocno, formseq, context);
		}
	}
	
	/**
	 * 선택한 공통양식 삭제
	 * @param int formseq
	 * @param ServletContext context
	 * @throws Exception 
	 */
	public void delCommFormat(int formseq, ServletContext context) throws Exception {
		
		Connection conn = null;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			delCommFormat(conn, formseq, context);
			
			conn.commit();
		} catch(Exception e) {
			try {
				conn.rollback();
			} catch(Exception ex) {
				logger.error("ERROR", ex);
			}
			ConnectionManager.close(conn);
			throw e;
		} finally {
			ConnectionManager.close(conn);
		}	
	}
	
	/**
	 * 선택한 공통양식 삭제
	 * @param Connection conn
	 * @param int formseq
	 * @param ServletContext context
	 * @throws Exception 
	 */
	public void delCommFormat(Connection conn, int formseq, ServletContext context) throws Exception {
			
		getFormatDAO().delCommFormat(conn, formseq);
		
		if(context != null) {
			//선택한 취합양식파일 삭제
			FormatBookManager fbmgr = FormatBookManager.instance();
			fbmgr.delAllCommFileBook(conn, formseq, context);
		}
	}

	/**
	 * 양식 리스트 가져오기
	 * @throws Exception 
	 */
	public List viewFormList(int sysdocno) throws Exception {
		return getFormatDAO().viewFormList(sysdocno);
	}
}