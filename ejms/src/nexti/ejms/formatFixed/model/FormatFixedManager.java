/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 고정양식형 manager
 * 설명:
 */
package nexti.ejms.formatFixed.model;

import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.util.Utils;
import nexti.ejms.util.math.EvaluateException;
import nexti.ejms.util.math.Expression;
import nexti.ejms.util.math.ParseException;
import nexti.ejms.attr.model.AttrBean;
import nexti.ejms.attr.model.AttrManager;
import nexti.ejms.format.model.FormatBean;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.format.model.InputDeptSearchBoxBean;
import nexti.ejms.formatLine.model.FormatLineDAO;
import nexti.ejms.formatLine.model.FormatLineManager;

public class FormatFixedManager {
	
	private static FormatFixedManager instance = null;
	private static FormatFixedDAO dao = null;
	private static Logger logger = Logger.getLogger(FormatFixedManager.class);
	
	private FormatFixedManager() {
		
	}
	
	public static FormatFixedManager instance() {
		
		if(instance == null)
			instance = new FormatFixedManager();
		return instance;
	}

	private FormatFixedDAO getFormatFixedDAO() {
		
		if(dao == null)
			dao = new FormatFixedDAO(); 
		return dao;
	}
	
	/**
	 * 고정양식형 양식속성 가져오기
	 * @param int sysdocno 
	 * @param int formseq
	 * @return String[] 양식정보
	 * @throws Exception
	 */
	public String[] getFormatFixedAtt(int sysdocno, int formseq) throws Exception {
		return getFormatFixedDAO().getFormatFixedAtt(sysdocno, formseq);
	}
	
	/**
	 * 양식 미리보기
	 * @param FormatFixedBean ffbean
	 * @param FormatBean fbean
	 * @param String depttype
	 * @return int 수행개수
	 * @throws Exception 
	 */
	public int formatFixedPreview(FormatFixedBean ffbean, FormatBean fbean, String depttype) throws Exception {
		
		Connection conn = null;
		
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			FormatManager fmgr = FormatManager.instance();
			
			formatFixedPreviewEnd(fbean.getSysdocno(), fbean.getFormseq());
			
			result = getFormatFixedDAO().addFormatFixed(conn, ffbean);

			fmgr.addFormat(conn, fbean);
			
			addFormatFixedPreviewTempData(conn, fbean, ffbean.getCellatt(), depttype, 3);
			
			conn.commit();
		} catch(Exception e) {
			conn.rollback();
			ConnectionManager.close(conn);
			throw e;
		} finally {
			ConnectionManager.close(conn);
		}
		
		return result;
	}
	
	/**
	 * 공통양식 미리보기
	 * @param FormatFixedBean ffbean
	 * @param FormatBean fbean
	 * @param String depttype
	 * @return int 수행개수
	 * @throws Exception 
	 */
	public int commFormatFixedPreview(FormatBean fbean, String depttype) throws Exception {
		
		Connection conn = null;
		
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			FormatManager fmgr = FormatManager.instance();
			
			int sysdocno = fbean.getSysdocno();
			int formseq = fbean.getFormseq();
			
			formatFixedPreviewEnd(sysdocno, formseq);
			
			fmgr.formatGetComm(fbean, fbean.getUptusrid(), null, null, false);
			
			String[] cellatt = getFormatFixedDAO().getFormatFixedAtt(sysdocno, formseq);
			
			fbean.setTblcols(getFormatFormView(sysdocno, formseq, "부서").getTblcols());
			fbean.setTblrows(getFormatFormView(sysdocno, formseq, "부서").getTblrows());
			
			addFormatFixedPreviewTempData(conn, fbean, cellatt, depttype, 3);
			
			conn.commit();
		} catch(Exception e) {
			conn.rollback();
			ConnectionManager.close(conn);
			throw e;
		} finally {
			ConnectionManager.close(conn);
		}
		
		return result;
	}
	
	/**
	 * 사용했던양식 미리보기
	 * @param FormatFixedBean ffbean
	 * @param FormatBean fbean
	 * @param String depttype
	 * @return int 수행개수
	 * @throws Exception 
	 */
	public int usedFormatFixedPreview(FormatBean fbean, String depttype) throws Exception {
		
		Connection conn = null;
		
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			FormatManager fmgr = FormatManager.instance();
			
			int sysdocno = fbean.getSysdocno();
			int formseq = fbean.getFormseq();
			
			formatFixedPreviewEnd(sysdocno, formseq);
			
			fmgr.formatGetUsed(fbean, fbean.getUptusrid(), null, false);
			
			String[] cellatt = getFormatFixedDAO().getFormatFixedAtt(sysdocno, formseq);
			
			fbean.setTblcols(getFormatFormView(sysdocno, formseq, "부서").getTblcols());
			fbean.setTblrows(getFormatFormView(sysdocno, formseq, "부서").getTblrows());
			
			addFormatFixedPreviewTempData(conn, fbean, cellatt, depttype, 3);
			
			conn.commit();
		} catch(Exception e) {
			conn.rollback();
			ConnectionManager.close(conn);
			throw e;
		} finally {
			ConnectionManager.close(conn);
		}
		
		return result;
	}
	
	/**
	 * 양식 미리보기 임시데이터 저장
	 * @param Connection conn
	 * @param FormatBean fbean
	 * @param String[] cellatt
	 * @param String depttype
	 * @param int count
	 * @return int 수행개수
	 * @throws Exception 
	 */
	public int addFormatFixedPreviewTempData(Connection conn, FormatBean fbean, String[] cellatt, String depttype, int count) throws Exception {
		
		int result = 0;
		
		int sysdocno = fbean.getSysdocno();
		int formseq = fbean.getFormseq();
		int tblcols = fbean.getTblcols();
		int tblrows = fbean.getTblrows();
		String user_id = fbean.getUptusrid();
		
		String colname;
		String coldata = "";
		List colList = new ArrayList();
		
		for(int i = 0; i < fbean.getTblcols(); i++) {
			if(i < 26) {
				colname = (char)('A' + i) + "";
			}
			else {
				colname = (char)('A' + i - 26) + "" + (char)('A' + i - 26);
			}

			String[] att = cellatt[i].split("[|]");
			if(att[0].equals("01") == true) {
				coldata = "10";
			} else if(att[0].equals("02") == true) {
				coldata = "가 ";
			} else if(att[0].equals("03") == true) {
				coldata = "autocal";
			} else if(att[0].equals("04") == true) {	
				List attlist = AttrManager.instance().getFormatAttList("%", "%");
				
				if(attlist == null || attlist.size() == 0) {
					coldata = "가 ";
				} else {
					for(int j = 0; j < attlist.size(); j++) {
						AttrBean attbean = (AttrBean)attlist.get(j);
						
						if(attbean.getListcd().equals(att[1].toString()) == true) {
							coldata = ((AttrBean)attbean.getListdtlList().get(0)).getListdtlnm();
							break;
						} else if(j + 1 == attlist.size()){
							coldata = "가 ";
						}
					}		
				}
			}		
			
			colList.add(colname + ":" + coldata);
		}

		//임시데이터 입력
		List colListSet = new ArrayList();
		for(int i = 0; i < tblrows; i++) {
			colListSet.add(colList);
		}
		
		//부서형태에 따라 임시부서명 가져오기
		List deptList = getFormatFixedDAO().getListTempDeptData(conn, depttype, count);
		if ( deptList == null || deptList.size() < count ) {
			deptList = getFormatFixedDAO().getListTempDeptData(conn, "과", count);
		}
		if ( deptList == null || deptList.size() < count ) {
			deptList = getFormatFixedDAO().getListTempDeptData(conn, "", count);
		}	

		for(int datacnt = 0; datacnt < count; datacnt++) {
			String[] tempDept = (String[])deptList.get(datacnt);
			
			result += insertFormatFixedData(conn, sysdocno, formseq, colListSet, tblcols, tempDept[0], user_id, 0, "update");
			
			fbean.setDeptcd(tempDept[0]);
			getFormatFixedDAO().addFormatFixedPreviewTempData(conn, fbean, tempDept[1]);
		}
		
		return result;
	}
	
	/**
	 * 양식 미리보기
	 * @param int sysdocno
	 * @param int formseq
	 * @return int 수행개수
	 * @throws Exception 
	 */
	public int formatFixedPreviewEnd(int sysdocno, int formseq) throws Exception {
		
		Connection conn = null;
		
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			FormatManager fmgr = FormatManager.instance();
			
			getFormatFixedDAO().delFormatFixedPreveiwTempData(conn, sysdocno);
			
			fmgr.delFormat(conn, sysdocno, formseq, null);
			
			conn.commit();
		} catch(Exception e) {
			conn.rollback();
			ConnectionManager.close(conn);
			throw e;
		} finally {
			try {
				conn.setAutoCommit(true);
			} finally {
				ConnectionManager.close(conn);
			}
		}
		
		return result;
	}
	
	/**
	 * 양식 입력속성 수정하기
	 * @param FormatFixedBean ffbean
	 * @param FormatBean fbean
	 * @return int 수행개수
	 * @throws Exception 
	 */
	public int modifyFormatFixed(FormatFixedBean flbean, FormatBean fbean) throws Exception {
		
		Connection conn = null;
		
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			FormatManager fmgr = FormatManager.instance();
			
			result = getFormatFixedDAO().modifyFormatFixed(conn, flbean);

			fmgr.modifyFormat(conn, fbean);
			
			conn.commit();
		} catch(Exception e) {
			conn.rollback();
			ConnectionManager.close(conn);
			throw e;
		} finally {
			try {
				conn.setAutoCommit(true);
			} finally {
				ConnectionManager.close(conn);
			}
		}
		
		return result;
	}
	
	/**
	 * 공통양식 입력속성 수정하기
	 * @param FormatFixedBean flbean
	 * @param FormatBean fbean
	 * @return int 수행개수
	 * @throws Exception 
	 */
	public int modifyCommFormatFixed(FormatFixedBean flbean, FormatBean fbean) throws Exception {
		
		Connection conn = null;
		
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			FormatManager fmgr = FormatManager.instance();
			
			result = getFormatFixedDAO().modifyCommFormatFixed(conn, flbean);

			fmgr.modifyCommFormat(conn, fbean);
			
			conn.commit();
		} catch(Exception e) {
			conn.rollback();
			ConnectionManager.close(conn);
			throw e;
		} finally {
			try {
				conn.setAutoCommit(true);
			} finally {
				ConnectionManager.close(conn);
			}
		}
		
		return result;
	}
	
	/**
	 * 양식 입력속성 저장하기
	 * @param FormatFixedBean ffbean
	 * @param FormatBean fbean
	 * @return int 수행개수
	 * @throws Exception 
	 */
	public int addFormatFixed(FormatFixedBean ffbean, FormatBean fbean) throws Exception {
		
		Connection conn = null;
		
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			FormatManager fmgr = FormatManager.instance();
			
			int formseq = ffbean.getFormseq();
			
			//새로만들기(formseq=0)일 때 새로운 공통양식일련번호 가져오기
			if(formseq == 0) {
				formseq = fmgr.getNewFormatseq(ffbean.getSysdocno());
				
				ffbean.setFormseq(formseq);
				fbean.setFormseq(formseq);
			}

			result = getFormatFixedDAO().addFormatFixed(conn, ffbean);

			fmgr.addFormat(conn, fbean);
			
			conn.commit();
		} catch(Exception e) {
			conn.rollback();
			ConnectionManager.close(conn);
			throw e;
		} finally {
			try {
				conn.setAutoCommit(true);
			} finally {
				ConnectionManager.close(conn);
			}
		}
		
		return result;
	}
	
	/**
	 * 공통양식 입력속성 저장하기
	 * @param FormatFixedBean ffbean
	 * @param FormatBean fbean
	 * @return int 수행개수
	 * @throws Exception 
	 */
	public int addCommFormatFixed(FormatFixedBean ffbean, FormatBean fbean) throws Exception {
		
		Connection conn = null;
		
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			FormatManager fmgr = FormatManager.instance();
			
			int formseq = ffbean.getFormseq();
			
			//새로만들기(formseq=0)일 때 새로운 공통양식일련번호 가져오기
			if(formseq == 0) {
				formseq = fmgr.getNewCommFormatseq();
				
				ffbean.setFormseq(formseq);
				fbean.setFormseq(formseq);
			}
			
			result = getFormatFixedDAO().addCommFormatFixed(conn, ffbean);

			fmgr.addCommFormat(conn, fbean);
			
			conn.commit();
		} catch(Exception e) {
			conn.rollback();
			ConnectionManager.close(conn);
			throw e;
		} finally {
			try {
				conn.setAutoCommit(true);
			} finally {
				ConnectionManager.close(conn);
			}
		}
		
		return result;
	}
	
	/**
	 * 양식폼 내용 가져오기
	 * 
	 * @param sysdocno : 시스템문서번호
	 * @param formseq : 양식일련번호
	 * 
	 * @return FormatFixedBean : 고정양식형 구조를 가지고 있는 Bean
	 * @throws Exception 
	 */
	public FormatFixedBean getFormatFormView(int sysdocno, int formseq, String deptnm) throws Exception{
		FormatFixedBean result = new FormatFixedBean();
		int cutlen = 0;
		String colname = "";
		
		//양식개요, header, body, tail 정보 및 테이블 row, col 수 가져오기
		String formcomment = getFormatFixedDAO().getFormatFixedView(sysdocno, formseq, "FORMCOMMENT", true).toString();
		String formhtml = getFormatFixedDAO().getFormatFixedView(sysdocno, formseq, "FORMHTML", true).toString();
		String formheaderhtml = getFormatFixedDAO().getFormatFixedView(sysdocno, formseq, "FORMHEADERHTML", true).toString();
		String formbodyhtml = getFormatFixedDAO().getFormatFixedView(sysdocno, formseq, "FORMBODYHTML", true).toString();
		String formtailhtml = getFormatFixedDAO().getFormatFixedView(sysdocno, formseq, "FORMTAILHTML", true).toString();
		int tblcols = Integer.parseInt(getFormatFixedDAO().getFormatFixedView(sysdocno, formseq, "TBLCOLS", false).toString());
		int tblrows = Integer.parseInt(getFormatFixedDAO().getFormatFixedView(sysdocno, formseq, "TBLROWS", false).toString());
		
		int strIdx = formbodyhtml.indexOf(">", 0);
		String firstStr = formbodyhtml.substring(0, strIdx + 1);
		String lastStr = formbodyhtml.substring(strIdx + 1, formbodyhtml.length());
		String colHeadAtt_ext = "bgcolor=\"rgb(225,225,255)\"";
		formbodyhtml = firstStr + "<td rowspan='"+tblrows +"' align='center' " + colHeadAtt_ext + ">" + deptnm + "</td>" + lastStr;
		
		//첫번째 FOR문은 행단위로 반복
		for(int i = 0; i < tblrows; i++) {
			//두번째  FOR문은 칼럼단위로 반복하면서 $A, $B, $C 등으로 된 부분 제거
			for(int j = 0; j < tblcols; j++) {
				if(j >= 26) {
					cutlen = formbodyhtml.indexOf("$" + (char)('A' + j - 26) + "" + (char)('A' + j - 26));
				} else {
					cutlen = formbodyhtml.indexOf("$" + (char)('A' + j));
				}
				
				if(j >= 26) {
					colname = formbodyhtml.substring(cutlen + 1, cutlen + 3);
				} else {
					colname = formbodyhtml.substring(cutlen + 1, cutlen + 2);
				}
				
				//변환된 값을 formbodyhtml에 해당 위치에 치환
				formbodyhtml = formbodyhtml.replaceFirst("\\$" + colname, "&nbsp;");
			}
		}
		
		//각 결과를 Bean에 저장
		result.setFormcomment(formcomment);
		result.setFormhtml(formhtml);
		result.setFormheaderhtml(formheaderhtml);
		result.setFormbodyhtml(formbodyhtml);
		result.setFormtailhtml(formtailhtml);
		result.setTblcols(tblcols);
		result.setTblrows(tblrows);
		
		return result;
	}
	
	/**
	 * 양식폼 내용에 대한 데이터 리스트 가져오기
	 * @throws Exception 
	 */
	public List getFormDataList(int sysdocno, int formseq) throws Exception {
		return getFormatFixedDAO().getFormDataList(sysdocno, formseq);
	}
	
	/**
	 * 행추가형 구조 및 속성 및 데이터 보기
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param formseq : 양식일련번호
	 * @param deptcd : 부서코드
	 * @param usrid : 사용자ID
	 * @param chrgunitcd : 담당단위코드
	 * @param chgatt : true - 속성값 치환, false - 값만 치환
	 * 
	 * @return FormatFixedBean
	 * @throws Exception 
	 */
	public FormatFixedBean getFormatFixedDataView(int sysdocno, int formseq, InputDeptSearchBoxBean idsbbean, String deptnm, boolean chgatt, boolean isIncludeNotSubmitData) throws Exception {
		FormatFixedBean result = new FormatFixedBean();
		int cutlen = 0;
		String cellname = "";
		String colattdata = "";
		String coldata = "";
		String replaceStr = "";
		
		//양식개요, header, body, tail 정보 및 테이블 row, col 수 가져오기
		String formcomment = getFormatFixedDAO().getFormatFixedView(sysdocno, formseq, "FORMCOMMENT", true).toString();
		String formheaderhtml = getFormatFixedDAO().getFormatFixedView(sysdocno, formseq, "FORMHEADERHTML", true).toString();
		String formbodyhtml = getFormatFixedDAO().getFormatFixedView(sysdocno, formseq, "FORMBODYHTML", true).toString();
		String formtailhtml = getFormatFixedDAO().getFormatFixedView(sysdocno, formseq, "FORMTAILHTML", true).toString();
		int tblcols = Integer.parseInt(getFormatFixedDAO().getFormatFixedView(sysdocno, formseq, "TBLCOLS", false).toString());
		int tblrows = Integer.parseInt(getFormatFixedDAO().getFormatFixedView(sysdocno, formseq, "TBLROWS", false).toString());
		
		result.setSysdocno(sysdocno);
		result.setFormseq(formseq);
		result.setFormcomment(formcomment);
		result.setFormheaderhtml(formheaderhtml);
		result.setFormtailhtml(formtailhtml);
		result.setTblcols(tblcols);
		result.setTblrows(tblrows);
		
		int strIdx = formbodyhtml.indexOf(">", 0);
		String firstStr = formbodyhtml.substring(0, strIdx + 1);
		String lastStr = formbodyhtml.substring(strIdx + 1, formbodyhtml.length());
		String colHeadAtt_ext = "bgcolor=\"rgb(225,225,255)\"";
		formbodyhtml = firstStr + "<td rowspan='"+tblrows +"' align='center' " + colHeadAtt_ext + ">" + deptnm + "</td>" + lastStr;
		
		//첫번째 FOR문은 행단위로 반복
		for(int i = 0; i < tblrows; i++) {
			//각 속성값 바디 내 해당 위치에 넣는 프로세스
			for(int j = 0; j < tblcols; j++) {
				if(j >= 26) {
					cutlen = formbodyhtml.indexOf("$" + (char)('A' + j - 26) + "" + (char)('A' + j - 26));
				} else {
					cutlen = formbodyhtml.indexOf("$" + (char)('A' + j));
				}
				
				if(j >= 26) {
					cellname = formbodyhtml.substring(cutlen + 1, cutlen + 3);
				} else {
					cellname = formbodyhtml.substring(cutlen + 1, cutlen + 2);
				}

				colattdata = getFormatFixedDAO().getAttFixedFrmData(sysdocno, formseq, cellname);
				String[] gubun = colattdata.split("[|]");
				if ( "04".equals(gubun[0]) ) { 
					List attList = FormatLineManager.instance().getAttListData(gubun[1]);
					if ( attList == null || attList.size() == 0 ) colattdata = "02|1";
				}
				
				if("02".equals(gubun[0]) || "04".equals(gubun[0])) {
					coldata = getFormatFixedDAO().getStringDataFixedFrm(sysdocno, formseq, (i + 1), idsbbean, cellname, isIncludeNotSubmitData);
				} else {
					coldata = getFormatFixedDAO().getDataFixedFrm(sysdocno, formseq, (i + 1), idsbbean, cellname, isIncludeNotSubmitData);
				}
				
				if(coldata == null || coldata.equals("")){
					coldata = "";
				}
				
				if(chgatt) {	//true이면 각 칼럼 속성과 데이터를 치환					
					replaceStr = replaceAttributeData(cellname, colattdata, coldata);
				} else {	//false이면 데이터만 치환
					if("01".equals(gubun[0]) || "03".equals(gubun[0])) {	//속성이 숫자인 칼럼(gubun[0]만 사용)
						String strValue = null;
						DecimalFormat df = new DecimalFormat("###,###.##########");
						if(coldata.length() > 1 && coldata.substring(0, 1).equals(".") == true) {
							strValue = "0" + coldata;
						} else if(coldata.length() > 2 && coldata.substring(0, 2).equals("-.") == true) {
							strValue = "-0" + coldata.substring(1);
						} else {
							strValue = coldata;
						}
						if ( strValue == null || strValue.trim().length() == 0 ) strValue = "0";
						replaceStr = df.format(Double.parseDouble(strValue));
					} else if(("02".equals(gubun[0]) && Integer.parseInt(gubun[1], 10) == 1) || "04".equals(gubun[0])) {
						replaceStr = Utils.convertHtmlBrNbsp(coldata);
					} else if("02".equals(gubun[0]) && Integer.parseInt(gubun[1], 10) > 1) {
						replaceStr = "<div align='left' style='padding-left:10; padding-right:10'>" + Utils.convertHtmlBrNbsp(coldata) + "</div>";
					} else {
						replaceStr = coldata;
					}
				}
				
				//변환된 값을 formbodyhtml에 해당 위치에 치환
				//formbodyhtml = formbodyhtml.replaceFirst("\\$"+cellname, replaceStr);
				StringBuffer sb = new StringBuffer(formbodyhtml);
				String cell = "$"+cellname;
				int idx = formbodyhtml.indexOf(cell);
				sb.replace(idx, idx + cell.length(), replaceStr);
				formbodyhtml = sb.toString();
			}
		}
		
		result.setFormbodyhtml(formbodyhtml);
		
		return result;
	}
	
	/**
	 * 고정양식형 - 각 칼럼의 속성 및 데이터 변환
	 * 
	 * @param colname : 칼럼명
	 * @param colattdata : 칼럼속성
	 * @param coldata : 칼럼데이터
	 * 
	 * @return String : 치환 결과
	 * @throws Exception 
	 */
	public String replaceAttributeData(String colname, String colattdata, String coldata) throws Exception {
		String replaceStr = "";
		List attList = null;
		String[] gubun = new String[2];
		
		gubun = colattdata.split("[|]");
		
		//각 속성별 처리(gubun[0] : 속성 구분, gubun[1] : 속성 프로퍼티)
		if("01".equals(gubun[0])) {	//속성이 숫자인 칼럼(gubun[0]만 사용)
			replaceStr = coldata;
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
			replaceStr = "<input type='text' style='width:95%; ime-mode:disabled' align='center' name='" + colname + "' size='15' value=\"" + strValue +"\" onkeydown=key_entertotab() onkeypress=\"inputFilterKey('[0-9.-]');\" onchange=\"setChanged();instantCalculation('autocal');\">";
		} else if("02".equals(gubun[0])) {	//속성이 문자인 칼럼(gubun[1]의 값은 줄의 길이)
			if(Integer.parseInt(gubun[1]) > 1) {	//gubun[1]의 값이 1보다 크면 TEXTAREA로 표시
				replaceStr = "<textarea style='width:95%; ime-mode:active' name='" + colname + "' rows='" + Integer.parseInt(gubun[1]) + "' onchange=\"setChanged();\">" + coldata +"</textarea>";
			} else {	//gubun[1]의 값이 1보다 작으면 일반 텍스트박스로 표시
				replaceStr = "<input type='text' name='" + colname + "' style='width:95%; ime-mode:active' value=\"" + coldata.replaceAll("\"", "&quot;") +"\" onkeydown=key_entertotab() onchange=\"setChanged();\">";
			}
		} else if("03".equals(gubun[0])) {	//속성이 수식인 칼럼(초기 로드시에는 화면에 자동계산이라는 글자만 표시)
			//수식계산수정부분
			replaceStr = gubun[1];
			replaceStr = "<input type='hidden' name='" + colname + "' value=\"autocal\"><input type=\"text\" style=\"text-align:center;border:0;width:100%;\" disabled=\"true\" name=\"autocal\" cellname=\"" + colname + "\" title=\"자동계산:" + replaceStr + "\">";
		} else if("04".equals(gubun[0])) {	//속성이 목록인 칼럼(gubun[1]의 값은 ATTLISTDTL과 ATTLISTMST에서 가져와야할 코드명)
			attList = new ArrayList();
			attList = FormatLineManager.instance().getAttListData(gubun[1]);
			replaceStr = "<select name='" + colname + "' style='width:95%' onkeydown=key_entertotab() onchange=\"setChanged();\">";
			replaceStr += "<option value=\"\">==선택==</option>";
			for(int k = 0; k < attList.size(); k++) {
				replaceStr += "<option value=\"" + attList.get(k) + "\"";
				if(attList.get(k).equals(coldata)) {
					replaceStr += " selected";
				}
				replaceStr += ">" + attList.get(k) + "</option>";
			}
			replaceStr += "</select>";
		}
		
		return replaceStr;
	}
	
	/** 
	 * 입력하기, 입력완료 - 고정양식형 데이터 저장
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param formseq : 양식일련번호
	 * @param rowList : 입력할 데이터 리스트
	 * @param tblcols : 칼럼수
	 * @param deptcd : 부서코드
	 * @param usrid : 사용자ID
	 * @param chrgunitcd : 담당단위코드	
	 * 
	 * @return int : 결과코드
	 * @throws Exception 
	 */
	public int insertFormatFixedData(int sysdocno, int formseq, List colList, int tblcols, String deptcd, String usrid, int chrgunitcd, String mode) throws Exception {
		Connection conn = null;

		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
			
			result = insertFormatFixedData(conn, sysdocno, formseq, colList, tblcols, deptcd, usrid, chrgunitcd, mode);
			 
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

		return result;
	}
	
	/** 
	 * 입력하기, 입력완료 - 고정양식형 데이터 저장
	 * @param Connection conn
	 * @param sysdocno : 시스템 문서번호
	 * @param formseq : 양식일련번호
	 * @param rowList : 입력할 데이터 리스트
	 * @param tblcols : 칼럼수
	 * @param deptcd : 부서코드
	 * @param usrid : 사용자ID
	 * @param chrgunitcd : 담당단위코드	
	 * 
	 * @return int : 결과코드
	 * @throws Exception 
	 */
	public int insertFormatFixedData(Connection conn, int sysdocno, int formseq, List rowList, int tblcols, String deptcd, String usrid, int chrgunitcd, String mode) throws Exception {

		int result = 0;
		String colname = "";
		String resultCal = "";
		List tmpList = null;
		StringBuffer insertQuery = null;
		DecimalFormat decimailFormat = new DecimalFormat("#.##########");
			
		result = getFormatFixedDAO().deleteFormatFixedData(conn, sysdocno, formseq, deptcd, usrid);
		
		if(mode.equals("insert")){
			//입력상태를 임시저장 상태로 변경한다.		
			result = new FormatLineDAO().updateInputState(conn, sysdocno, deptcd, usrid);
		}
		
		FormatManager fmgr = FormatManager.instance();
		String[][] deptInfo = fmgr.getInputDeptInfo(conn, sysdocno, deptcd);
		
		for(int i = 0; i < rowList.size(); i++) {
			insertQuery = new StringBuffer();
			insertQuery.append("\n INSERT INTO DATAFIXEDFRM(SYSDOCNO, FORMSEQ, TGTDEPTCD, INPUTUSRID, CHRGUNITCD, ROWSEQ,");
			insertQuery.append("\n                          DEPTCD1, DEPTNM1, DEPTCD2, DEPTNM2, DEPTCD3, DEPTNM3,        ");
			insertQuery.append("\n                          DEPTCD4, DEPTNM4, DEPTCD5, DEPTNM5, DEPTCD6, DEPTNM6         ");
			
			for(int j = 0; j < tblcols; j++) {
				if(j < 26) {
					colname = (char)('A' + j) + "";
				} else {
					colname = (char)('A' + j - 26) + "" + (char)('A' + j - 26);
				}
				
				insertQuery.append(", " + colname);
			}
			
			insertQuery.append(") \n VALUES(" + sysdocno + ", " + formseq + ", '" + deptcd + "', '" + usrid + "', " + chrgunitcd + ", " + (i + 1));
			
			for ( int j = 0; j < deptInfo.length; j++) {
				insertQuery.append(", '" + deptInfo[j][0] + "', '" + deptInfo[j][1] + "'");
			}
			
			tmpList = new ArrayList();
			tmpList = (List)rowList.get(i);
			
			for(int k = 0; k < tmpList.size(); k++) {
				if(k < 26) {
					colname = (char)('A' + k) + "";
				} else {
					colname = (char)('A' + k - 26) + "" + (char)('A' + k - 26);
				}
				
				if(colname.matches("[A-Z]++")) {
					if((colname + ":" + "autocal").equals(tmpList.get(k))) {
						try {
							resultCal = decimailFormat.format(this.calExpression(conn, tmpList, sysdocno, formseq, colname));
						} catch (Exception e) {
							try {
								conn.rollback();
							} catch (Exception ex) {
								logger.error("ERROR",ex);
							}
	
							throw new Exception((i + 1) + ":" + e.getMessage());
						}
						
						insertQuery.append(", '" + resultCal + "'");
					} else {
						String value = null;
						if(k < 26) {
							value = tmpList.get(k).toString().substring(2, tmpList.get(k).toString().length());
						} else {
							value = tmpList.get(k).toString().substring(3, tmpList.get(k).toString().length());
						}	
						String[] gubun = getFormatFixedDAO().getAttFixedFrmData(sysdocno, formseq, colname).split("[|]");
						
						if("01".equals(gubun[0])) {			
							if(value.trim().equals("") == true) {
								value = "0";
							} else {
								if(value.indexOf(".") != -1) {
									value = Double.toString(Double.parseDouble(value));
								} else {
									value = Integer.toString(Integer.parseInt(value, 10));
								}
							}
						} else if("02".equals(gubun[0])) {
							if(value != null) {
								value = value.replaceAll("'", "''");
							} else {
								value = "";
							}
						}
						
						insertQuery.append(", '" + value + "'");
					}
				}
			}
			
			insertQuery.append(")");
			
			result = getFormatFixedDAO().insertFormatFixedData(conn, insertQuery);
		}				
		
		return result;
	}
	
	public int deleteAllFormatFixedData(int sysdocno, int formseq, String deptcd, String usrid) throws Exception {
		return getFormatFixedDAO().deleteAllFormatFixedData(sysdocno, formseq, deptcd, usrid);
	}
	
	/**
	 * 수식 계산 처리 함수 - 행추가형
	 * @param Connection conn
	 * @param sysdocno : 시스템 문서번호
	 * @param formseq : 양식일련번호
	 * @param deptcd : 부서코드
	 * 
	 * @return String : 수식 처리 결과 
	 */
	public double calExpression(Connection conn, List tmpList, int sysdocno, int formseq, String colname) throws Exception {
		double result = 0.0;
		String colattdata = "";
		String[] gubun = new String[2];
		String[] colvalue = new String[2];
		
		try {
			colattdata = getFormatFixedDAO().getAttFixedFrmData(conn, sysdocno, formseq, colname);
			
			gubun = colattdata.split("[|]");
			
			Expression expr = new Expression(gubun[1]);
			Hashtable hash = new Hashtable();
			
			try {
				for(int j = 0; j < tmpList.size(); j++) {
					String[] arrValue = tmpList.get(j).toString().split(":");
					if(arrValue.length == 1) {
						colvalue[0] = arrValue[0];
						colvalue[1] = "0"; 
					} else {
						colvalue = (String[])arrValue.clone();
					}
					
					for(int i = 0; i < gubun[1].length(); i++) {
						if(i + 1 < gubun[1].length() && colvalue[0].equals(gubun[1].charAt(i) + "" + gubun[1].charAt(i + 1))) {
							hash.put(colvalue[0], colvalue[1]);
							i++;
						} else if(colvalue[0].equals(gubun[1].charAt(i) + "")) {
							hash.put(colvalue[0], colvalue[1]);
						}
					}
				}
			} catch (Exception e) {
				throw new EvaluateException("NaN");
			}
			
			try {
				result = expr.evaluate(hash);
			} catch (EvaluateException e) {
				throw new EvaluateException("NaN");
			}

			if(Double.isInfinite(result)) {
				throw new Exception("Infinity");
			}
			
		} catch (ParseException e) {
			logger.error("ERROR",e);
		}
		
		return result;
	}
	
	/**
	 * 양식폼 내용에 대한 데이터 리스트 가져오기
	 * @throws Exception 
	 */
	public List getFormFixedDataList(FormatFixedBean schBean) throws Exception {
		return getFormatFixedDAO().getFormFixedDataList(schBean);
	}
	
	/**
	 * 양식HTML을 헤더,바디,테일로 구분(필요없는 정보 삭제)
	 * @param String formhtml
	 * @return FormatFixedBean 양식HTML
	 */
	public FormatFixedBean FormatFixedHtmlSeparator(String original_formhtml) throws Exception {
		
		FormatFixedBean ffbean  =  new FormatFixedBean();
		
		//attTABLE, attTR, attTD에 테이블 재구성시 필요한 속성 정의
		String	attTABLE= "id=\"tbl\" border=\"1\" bordercolor=\"gray\" style=\"border-collapse:collapse\"";
		String	attTR	= "align=\"center\"";
		String	attTD	= "";
		String 	sTABLE 	= "<table";						//시작태그는 >없이 지정
		String 	eTABLE	= "</table>";
		String 	sTR		= "<tr";						//시작태그는 >없이 지정
		String 	eTR		= "</tr>";
		String 	sTD		= "<td";						//시작태그는 >없이 지정
		String 	eTD		= "</td>";
		String	attTagRowspan = "rowspan=";
		String	attTagColspan = "colspan=";
		String	attTagWidth = "width=";
		
		String rowHeadAtt = "bgcolor=\"rgb(210,210,255)\"";
		String colHeadAtt = "bgcolor=\"rgb(240,240,255)\""; 
		String colHeadAtt_ext = "bgcolor=\"rgb(225,225,255)\"";
		int addTableWidth = 100;	//연번 컬럼 넓이
		
		//FormatLineBean에 담겨져 반환되는 변수
		StringBuffer html = new StringBuffer();
		StringBuffer formhtml = new StringBuffer();
		StringBuffer formheaderhtml = new StringBuffer();
		StringBuffer formbodyhtml = new StringBuffer();
		StringBuffer formbodyhtml_ext = new StringBuffer();
		StringBuffer formtailhtml = new StringBuffer();
		int tblcols = 0;
		int tblrows = 0;
		int tblwidth = 0;
		int colHeadCount = 0;
		int rowHeadCount = 0;
		
		//임시 사용변수
		StringBuffer tmpStr = new StringBuffer();
		StringBuffer emptyformbodyhtml = new StringBuffer();	//속성($A,$B...)지정없는 HTML
		int startIndex = 0;
		int endIndex = 0;
		int trStartIndex = 0;
		int trEndIndex = 0;
		int tdStartIndex = 0;
		int tdEndIndex = 0;
		int findIndex1 = 0;
		int findIndex2 = 0;
		int width = 0;
		int colspan = 0;
		int rowspan = 0;
		
		original_formhtml = original_formhtml.replaceAll("<TABLE", "<table");
		original_formhtml = original_formhtml.replaceAll("</TABLE", "</table");
		original_formhtml = original_formhtml.replaceAll("<TR", "<tr");
		original_formhtml = original_formhtml.replaceAll("</TR", "</tr");
		original_formhtml = original_formhtml.replaceAll("<TD", "<td");
		original_formhtml = original_formhtml.replaceAll("</TD", "</td");
		original_formhtml = original_formhtml.replaceAll("ROWSPAN=", "rowspan=");
		original_formhtml = original_formhtml.replaceAll("rowSpan=", "rowspan=");
		original_formhtml = original_formhtml.replaceAll("COLSPAN=", "colspan=");
		original_formhtml = original_formhtml.replaceAll("colSpan=", "colspan=");
		original_formhtml = original_formhtml.replaceAll("<P", "<p");
		original_formhtml = original_formhtml.replaceAll("</P", "</p");
		original_formhtml = original_formhtml.replaceAll("<BR", "<br");
		
		html.append(original_formhtml.toString());
		
		//원본html에 table 태그 유무 체크
		startIndex = html.indexOf(sTABLE, 0);
		endIndex = html.indexOf(eTABLE, startIndex);
		if(startIndex != -1 && endIndex != -1) {
			//테이블 태그 이외의 태그 삭제
			html.delete(endIndex + eTABLE.length(), html.length());
			html.delete(0, startIndex);
		} else {
			return null;
		}
		
		//table width 가져오기
		startIndex = html.indexOf(sTABLE, 0);
		endIndex = html.indexOf(">", startIndex);
		tmpStr.append(html.substring(startIndex, endIndex + 1));
		
		String tmp = htmlSeparatorFindAttValue(tmpStr.toString(), attTagWidth);
		if(tmp != null && tmp.equals("") == false) {
			tblwidth = Integer.parseInt(tmp, 10);
		}
		
		//formheaderhtml, formbodyhtml 생성
		//table 태그 생성
		formheaderhtml.append(sTABLE + " " + attTABLE + " " + attTagWidth + "\"" + tblwidth + "\">\n");
		
		boolean colHeadComp = false;
		boolean rowHeadComp = false;
		int totalTdCount = 0;
		startIndex += sTABLE.length();
		endIndex = html.indexOf(eTABLE, startIndex);
		for(trStartIndex = startIndex; trStartIndex < endIndex; ) {
			//table에 tr 태그 유무 체크
			trStartIndex = html.indexOf(sTR, trStartIndex);
			trEndIndex = html.indexOf(eTR, trStartIndex);
			if(trStartIndex != -1 && trStartIndex < trEndIndex
					&& trEndIndex != -1 && trEndIndex < endIndex) {
				//tr 태그 생성
				if(rowHeadComp == false) {
					formheaderhtml.append("\t" + sTR + " " + attTR + " " + rowHeadAtt + ">\n");
				} else {
					formbodyhtml.append("\t" + sTR + " " + attTR + ">\n");
					emptyformbodyhtml.append("\t" + sTR + " " + attTR + ">\n");
				}
				
				int tdCount = 0;
				trStartIndex += sTR.length();
				for(tdStartIndex = trStartIndex; tdStartIndex < trEndIndex; ) {
					//tr에 td 태그 유무 체크
					tdStartIndex = html.indexOf(sTD, tdStartIndex);
					tdEndIndex = html.indexOf(eTD, tdStartIndex);
					if(tdStartIndex != -1 && tdStartIndex < tdEndIndex 
							&& tdEndIndex != -1 && tdEndIndex < trEndIndex) {
						tdEndIndex = html.indexOf(">", tdStartIndex);
						tmpStr.delete(0, tmpStr.capacity());
						tmpStr.append(html.substring(tdStartIndex, tdEndIndex + 1));
						
						tmp = htmlSeparatorFindAttValue(tmpStr.toString(), attTagColspan);
						if(tmp != null && tmp.equals("") == false) {
							colspan = Integer.parseInt(tmp, 10);
						} else {
							colspan = -1;
						}
						tmp = htmlSeparatorFindAttValue(tmpStr.toString(), attTagRowspan);
						if(tmp != null && tmp.equals("") == false) {
							rowspan = Integer.parseInt(tmp, 10);
						} else {
							rowspan = -1;
						}
						tmp = htmlSeparatorFindAttValue(tmpStr.toString(), attTagWidth);
						if(tmp != null && tmp.equals("") == false) {
							width = Integer.parseInt(tmp, 10);
						} else {
							width = -1;
						}
						
						tmpStr.delete(0, tmpStr.capacity());
						if(colspan > 1) {
							tmpStr.append(attTagColspan + "\"" + colspan + "\" ");
							tdCount += colspan - 1;
						}
						if(rowspan > 1) {
							tmpStr.append(attTagRowspan + "\"" + rowspan + "\" ");
						}
						if(width != -1) {
							tmpStr.append(attTagWidth + "\"" + width + "\" ");
						}
						//table td 태그 생성
						if(rowHeadComp == false) {
							formheaderhtml.append("\t\t" + sTD + " " + attTD + " " + tmpStr.toString() + ">");
						} else {
							formbodyhtml.append("\t\t" + sTD + " " + attTD + " " + tmpStr.toString() + ">");
							emptyformbodyhtml.append("\t\t" + sTD + " " + attTD + " " + tmpStr.toString() + ">");
						}
						
						//td에 셀 값만 가져오기
						tdStartIndex = tdEndIndex + 1;
						tdEndIndex = html.indexOf(eTD, tdStartIndex);
						tmpStr.delete(0, tmpStr.capacity());
						tmpStr.append(html.substring(tdStartIndex, tdEndIndex).replaceAll("&nbsp;|\r|\n", "").trim());
						for(findIndex1 = 0; findIndex1 < tmpStr.length(); ) {
							findIndex1 = tmpStr.indexOf("<", findIndex1);
							findIndex2 = tmpStr.indexOf(">", findIndex1);
							if(findIndex1 != -1 && findIndex2 != -1) {
								if(tmpStr.indexOf("<p", findIndex1) == findIndex1) {
									tmpStr.delete(findIndex1, findIndex2 + 1);
								} else if(tmpStr.indexOf("</p", findIndex1) == findIndex1) {
									if(findIndex2 + 1 < tmpStr.length() - 1) {
										tmpStr.replace(findIndex1, findIndex2 + 1, "<br>");
										findIndex1 += "<br>".length();
									} else {
										tmpStr.delete(findIndex1, findIndex2 + 1);
									}
								} else if(tmpStr.indexOf("<br", findIndex1) == findIndex1) {
									tmpStr.replace(findIndex1, findIndex2 + 1, "<br>");
									findIndex1 += "<br>".length();
								} else {
									tmpStr.delete(findIndex1, findIndex2 + 1);
								}
							} else {
								break;
							}
						}	//<TD></TD> 사이에서 데이터만 추출
						String attValue = tmpStr.toString().trim();
						tmpStr.delete(0, tmpStr.capacity());
						
						//속성셀 생성
						if(attValue.length() == 0
								|| (attValue.length() == 1 && attValue.getBytes()[0] == -95 && attValue.getBytes()[1] == -95)) {
							if(rowHeadComp == false) {
								if(colspan < 1) {
									colspan = 1;
								}
									
								if(rowHeadCount > 0 ) {
									rowHeadComp = true;
									colHeadComp = true;
									colHeadCount = tdCount;
									findIndex1 = formheaderhtml.lastIndexOf(sTR);
									formbodyhtml.append("\t" + formheaderhtml.substring(findIndex1));
									emptyformbodyhtml.append("\t" + formheaderhtml.substring(findIndex1));
									
									findIndex2 = formbodyhtml.indexOf(rowHeadAtt);
									formbodyhtml.delete(findIndex2, findIndex2 + rowHeadAtt.length());
									emptyformbodyhtml.delete(findIndex2, findIndex2 + rowHeadAtt.length());

									findIndex1 = formheaderhtml.lastIndexOf(">", findIndex1) + 1;
									formheaderhtml.delete(findIndex1, formheaderhtml.length());
									
									for(int i = 0; i < formbodyhtml.length();) {
										if((i = formbodyhtml.indexOf(sTD, i)) != -1) {
											if(formbodyhtml.indexOf(sTD, i + 1) == -1) {
												break;
											}
										}
										i = formbodyhtml.indexOf(">", i);
										formbodyhtml.insert(i, colHeadAtt);
										emptyformbodyhtml.insert(i, colHeadAtt);
										i += colHeadAtt.length() + 1;
									}
								} else {
									return null;
								}
							}
								
							//A~Z~AA~ZZ 52글자(26*2) : 입력란 셀이름 입력
							String cellName = null;
							int i = tdCount - colHeadCount;
							if(i < 0) {
								tdCount -= tdCount - colHeadCount;
								i = tdCount - colHeadCount;
							}

							if(i < 26) {
								cellName = "$" + (char)('A' + i);
							} else {
								cellName = "$" + (char)('A' + i - 26) + (char)('A' + i - 26);
							}
							
							formbodyhtml.append(cellName + eTD + "\n");
							emptyformbodyhtml.append("&nbsp;" + eTD + "\n");
						} else {
							if(rowHeadComp == false) {
								formheaderhtml.append(attValue + eTD + "\n");
							} else if(rowHeadComp == true && colHeadComp == true) {
								formbodyhtml.insert(formbodyhtml.length() - 1, " " + colHeadAtt);
								emptyformbodyhtml.insert(emptyformbodyhtml.length() - 1, " " + colHeadAtt);
								formbodyhtml.append(attValue + eTD + "\n");
								emptyformbodyhtml.append(attValue + eTD + "\n");
							} else if(rowHeadComp == true && colHeadComp == true && tdCount + 1 > colHeadCount) {
								return null;
							}
						}

						tdStartIndex = tdEndIndex + 1;
						tdCount++;
					} else {
						break;
					}				
				}
				
				if(rowHeadComp == false) {
					rowHeadCount++;
					formheaderhtml.append("\t" + eTR + "\n");
				} else {
					formbodyhtml.append("\t" + eTR + "\n");
					emptyformbodyhtml.append("\t" + eTR + "\n");
					
					//각 행의 입력란 수가 같은지 체크
					if(totalTdCount == 0) {
						totalTdCount = colHeadCount + tdCount;
					} else if(totalTdCount != colHeadCount + tdCount) {
						return null;
					}	
				}

				if(tblcols < tdCount) {
					tblcols = tdCount;
					if(tblcols > 52) {
						ffbean.setTblcols(53);
						return ffbean;
					}
				}
				
				tblrows++;
			} else {
				break;
			}
			trStartIndex = trEndIndex + 1;
		}
		tblcols = tblcols - colHeadCount;
		tblrows = tblrows - rowHeadCount;	//행추가형은 1
		
		//formtailhtml 생성
		//table 태그 닫기
		formtailhtml.append(eTABLE);

		//formhtml 생성
		formhtml.append(formheaderhtml.toString());
		formhtml.append(emptyformbodyhtml.toString());
		formhtml.append(formtailhtml.toString());
		
		//연번 컬럼 삽입(formbodyhtml에는 연번 컬럼을 추가하지 않고 formbodyhtml_ext에 추가)
		findIndex1 = formheaderhtml.indexOf(sTD);
		formheaderhtml.insert(findIndex1, sTD + " " + attTD + " " + attTagRowspan + "\"" + rowHeadCount + "\" " + attTagWidth + "\"" + addTableWidth + "\">부서" + eTD + "\n\t\t");
		
		//formheaderhtml의 width를 연번 컬럼 넓이에 맞게 조절
		findIndex1 = formheaderhtml.indexOf(attTagWidth) + attTagWidth.length() + 1;
		findIndex2 = formheaderhtml.indexOf("\"", findIndex1);
		formheaderhtml.replace(findIndex1, findIndex2, Integer.toString(tblwidth + addTableWidth, 10));
		
		//formbodyhtml_ext 생성
		formbodyhtml_ext.append(formbodyhtml.toString());
		findIndex1 = formbodyhtml_ext.indexOf(sTD);
		formbodyhtml_ext.insert(findIndex1, sTD + " " + attTD + " " + colHeadAtt_ext + " " + attTagRowspan + "\"" + tblrows + "\">&nbsp;" + eTD + "\n\t\t");
		
		String cellname = "";
		for(int i = 0; i < tblcols; i++) {
			if(i < 26) {
				cellname = "$" + (char)('A' + i);
			} else {
				cellname = "$" + (char)('A' + i - 26) + (char)('A' + i - 26);
			}
			
			if(tblcols - 1 < 26) {
				findIndex1 = formbodyhtml_ext.indexOf("$" + (char)('A' + tblcols - 1));
			} else {
				findIndex1 = formbodyhtml_ext.indexOf("$" + (char)('A' + tblcols - 1 - 26) + (char)('A' + tblcols - 1 - 26));
			}

			while((findIndex1 = formbodyhtml_ext.indexOf(cellname + "", findIndex1 + 1)) != -1) {
				if(cellname.substring(1).equals(formbodyhtml_ext.substring(findIndex1 + 2, findIndex1 + 3))) {
					continue;
				}
				formbodyhtml_ext.delete(findIndex1,  findIndex1 + cellname.length());
				formbodyhtml_ext.insert(findIndex1, "&nbsp;");
			}
		}

		ffbean.setFormhtml(formhtml.toString());
		ffbean.setFormheaderhtml(formheaderhtml.toString());
		ffbean.setFormbodyhtml(formbodyhtml.toString());
		ffbean.setFormbodyhtml_ext(formbodyhtml_ext.toString());
		ffbean.setFormtailhtml(formtailhtml.toString());
		ffbean.setTblcols(tblcols);
		ffbean.setTblrows(tblrows);
		ffbean.setTblwidth(tblwidth);
		ffbean.setColHeadCount(colHeadCount);
		ffbean.setRowHeadCount(rowHeadCount);
		
		return ffbean;
	}
	
	/**
	 * 속성값 찾기
	 * @param String html
	 * @param String attStr
	 * @return String 속성값
	 */
	public String htmlSeparatorFindAttValue(String html, String attStr) {
		
		int startIndex = 0;
		int endIndex = 0;
		int findIndex1 = 0;
		int findIndex2 = 0;
		String result = null;
		
		startIndex = 0;
		endIndex = html.length();
		for(findIndex1 = startIndex; findIndex1 < endIndex; findIndex1++) {
			findIndex1 = html.indexOf(attStr, findIndex1);
			if(findIndex1 != -1) {
				int symbolLength = 0;
				findIndex1 += attStr.length();
				if(html.charAt(findIndex1) == '"') {
					findIndex2 = html.indexOf("\"", findIndex1 + 1);
					symbolLength = 1;
				} else if(html.charAt(findIndex1) == '\'') {
					findIndex2 = html.indexOf("'", findIndex1 + 1);
					symbolLength = 1;
				} else {
					findIndex2 = html.indexOf(" ", findIndex1 + 1);			
					if(findIndex2 == -1) {						
						findIndex2 = html.indexOf(">", findIndex1 + 1);
					}
				}
				try {
					result = html.substring(findIndex1 + symbolLength, findIndex2).trim();
					break;
				} catch(Exception e) {
					result = null;
				}
			} else {
				result = null;
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * formbodyhtml에 입력속성지정란 생성
	 * @param String formbodyhtml
	 * @param int tblcols
	 * @param int sysdocno
	 * @param int formseq
	 * @return String formbodyhtml
	 * @throws Exception 
	 */
	public String makeAttCell(String formbodyhtml, int tblcols, int sysdocno, int formseq) throws Exception {
		
		//A~Z~AA~ZZ 52글자(26*2)
		int cellindex = -1;
		int cellcount = 0;
		String cellname = "";
		StringBuffer select = null;
		StringBuffer html = new StringBuffer(formbodyhtml);
		
		String[] attValue = null;
		if(sysdocno != 0 && formseq != 0) {
			attValue = getFormatFixedDAO().getFormatFixedAtt(sysdocno, formseq);
		} else if(sysdocno == 0 && formseq != 0) {
			attValue = getFormatFixedDAO().getCommFormatFixedAtt(formseq);
		}
		
		StringBuffer option = new StringBuffer("<option value=\"01|\">숫자</option>");
		
		while(true) {
			if(cellcount < 26) {
				cellname = "$" + (char)('A' + cellcount);
			}
			else {
				cellname = "$" + (char)('A' + cellcount - 26) + (char)('A' + cellcount - 26);
			}
			
			if((cellindex = html.indexOf(cellname, cellindex + 1)) == -1) {
				break;
			}
			
			if(attValue != null && attValue.length > cellcount) {
				String[] optionSet = attValue[cellcount].split("[|]");
				
				option = new StringBuffer();
				option.append("<option value=\"" + attValue[cellcount] + "\">");
				
				if(optionSet[0].equals("01") == true) {
					option.append("숫자</option>");
				} else if(optionSet[0].equals("02") == true) {
					if(optionSet[1].equals("1")) {
						option.append("문자:단문(1줄)</option>");
					} else {
						option.append("문자:장문</option>");
					}
				} else if(optionSet[0].equals("03") == true) {
					option.append("수식:" + optionSet[1] + "</option>");
				} else if(optionSet[0].equals("04") == true) {	
					List attlist = AttrManager.instance().getFormatAttList("%", "%");
					
					if(attlist == null || attlist.size() == 0) {
						option.append("목록:" + optionSet[1] + "</option>");
					} else {
						for(int i = 0; i < attlist.size(); i++) {
							AttrBean bean = (AttrBean)attlist.get(i);
							
							if(bean.getListcd().equals(optionSet[1].toString()) == true) {
								option.append("목록:" + bean.getListnm() + "</option>");
								break;
							} else if(i + 1 == attlist.size()){
								option.append("목록:" + optionSet[1] + "</option>");
							}
						}		
					}
				}
			}
			
			select = new StringBuffer( 
					"<select name=\"cellatt\" style=\"width:100%\" onmousedown=\"eventCellAtt=this; showMainMenu(eventCellAtt, mainmenu)\"" +
					"ondblclick=\"this.onmousedown()\">" + option.toString() + "</select>");
			
			html.replace(cellindex, cellindex + cellname.length(), cellname.substring(1) + select);
			
			cellcount++;
		}
		
		if(cellcount == tblcols) {
			return html.toString();
		} else {
			return null;
		}
	}
}