/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 행추가형 manager
 * 설명:
 */
package nexti.ejms.formatLine.model;

import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.attr.model.AttrBean;
import nexti.ejms.attr.model.AttrManager;
import nexti.ejms.format.model.FormatManager;
import nexti.ejms.format.model.FormatBean;
import nexti.ejms.format.model.InputDeptSearchBoxBean;
import nexti.ejms.util.Utils;
import nexti.ejms.util.math.EvaluateException;
import nexti.ejms.util.math.Expression;
import nexti.ejms.util.math.ParseException;

public class FormatLineManager {
	
	private static FormatLineManager instance = null;
	private static FormatLineDAO dao = null;
	private static Logger logger = Logger.getLogger(FormatLineManager.class);
	
	private FormatLineManager() {
		
	}
	
	public static FormatLineManager instance() {
		
		if(instance == null)
			instance = new FormatLineManager();
		return instance;
	}

	private FormatLineDAO getFormatLineDAO() {
		
		if(dao == null)
			dao = new FormatLineDAO(); 
		return dao;
	}
	
	/**
	 * 행추가형형 양식속성 가져오기
	 * @param int sysdocno 
	 * @param int formseq
	 * @return String[] 양식정보
	 * @throws Exception
	 */
	public String[] getFormatLineAtt(int sysdocno, int formseq) throws Exception {
		return getFormatLineDAO().getFormatLineAtt(sysdocno, formseq);
	}
	
	/**
	 * 양식 미리보기
	 * @param FormatLineBean flbean
	 * @param FormatBean fbean
	 * @return int 수행개수
	 * @throws Exception 
	 */
	public int formatLinePreview(FormatLineBean flbean, FormatBean fbean) throws Exception {
		
		Connection conn = null;
		
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			FormatManager fmgr = FormatManager.instance();
			
			formatLinePreviewEnd(fbean.getSysdocno(), fbean.getFormseq());
			
			result = getFormatLineDAO().addFormatLine(conn, flbean);

			fmgr.addFormat(conn, fbean);
			
			addFormatLinePreviewTempData(conn, fbean, flbean.getCellatt(), 10);
			
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
	 * @param FormatLineBean flbean
	 * @param FormatBean fbean
	 * @return int 수행개수
	 * @throws Exception 
	 */
	public int commFormatLinePreview(FormatBean fbean) throws Exception {
		
		Connection conn = null;
		
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			FormatManager fmgr = FormatManager.instance();
			
			int sysdocno = fbean.getSysdocno();
			int formseq = fbean.getFormseq();
			
			formatLinePreviewEnd(sysdocno, formseq);
			
			fmgr.formatGetComm(fbean, fbean.getUptusrid(), null, null, false);
			
			String[] cellatt = getFormatLineDAO().getFormatLineAtt(sysdocno, formseq);
			
			fbean.setTblcols(getFormatFormView(sysdocno, formseq).getTblcols());
			
			addFormatLinePreviewTempData(conn, fbean, cellatt, 10);
			
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
	 * @param FormatLineBean flbean
	 * @param FormatBean fbean
	 * @return int 수행개수
	 * @throws Exception 
	 */
	public int usedFormatLinePreview(FormatBean fbean) throws Exception {
		
		Connection conn = null;
		
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			FormatManager fmgr = FormatManager.instance();
			
			int sysdocno = fbean.getSysdocno();
			int formseq = fbean.getFormseq();
			
			formatLinePreviewEnd(sysdocno, formseq);
			
			fmgr.formatGetUsed(fbean, fbean.getUptusrid(), null, false);
			
			String[] cellatt = getFormatLineDAO().getFormatLineAtt(sysdocno, formseq);
			
			fbean.setTblcols(getFormatFormView(sysdocno, formseq).getTblcols());
			
			addFormatLinePreviewTempData(conn, fbean, cellatt, 10);
			
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
	 * @param int count
	 * @return int 수행개수
	 * @throws Exception 
	 */
	public int addFormatLinePreviewTempData(Connection conn, FormatBean fbean, String[] cellatt, int count) throws Exception {
		
		int result = 0;
		
		int sysdocno = fbean.getSysdocno();
		int formseq = fbean.getFormseq();
		int tblcols = fbean.getTblcols();
		String user_id = fbean.getUptusrid();
		String dept_code = fbean.getDeptcd();
		
		String colname;
		String coldata = "";
		List colList = new ArrayList();
		
		for(int i = 0; i < fbean.getTblcols(); i++) {
			if(i < 26) {
				colname = (char)('A' + i) + "";
			} else {
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
		for(int datacnt = 0; datacnt < count; datacnt++) {
			result += insertFormatLineData(conn, sysdocno, formseq, colList, tblcols, dept_code, user_id, 1, "");
		}
		
		getFormatLineDAO().addFormatLinePreviewTempData(conn, fbean, "", 10);
		
		return result;
	}
	
	/**
	 * 양식 미리보기
	 * @param int sysdocno
	 * @param int formseq
	 * @return int 수행개수
	 * @throws Exception 
	 */
	public int formatLinePreviewEnd(int sysdocno, int formseq) throws Exception {
		
		Connection conn = null;
		
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			FormatManager fmgr = FormatManager.instance();
			
			getFormatLineDAO().delFormatLinePreveiwTempData(conn, sysdocno);
			
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
	 * 양식 입력속성 저장하기
	 * @param FormatLineBean flbean
	 * @param FormatBean fbean
	 * @return int 수행개수
	 * @throws Exception 
	 */
	public int addFormatLine(FormatLineBean flbean, FormatBean fbean) throws Exception {
		
		Connection conn = null;
		
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			FormatManager fmgr = FormatManager.instance();
			
			int formseq = flbean.getFormseq();
			
			//새로만들기(formseq=0)일 때 새로운 공통양식일련번호 가져오기
			if(formseq == 0) {
				formseq = fmgr.getNewFormatseq(flbean.getSysdocno());
				
				flbean.setFormseq(formseq);
				fbean.setFormseq(formseq);
			}
			
			result = getFormatLineDAO().addFormatLine(conn, flbean);

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
	 * 양식 입력속성 수정하기
	 * @param FormatLineBean flbean
	 * @param FormatBean fbean
	 * @return int 수행개수
	 * @throws Exception 
	 */
	public int modifyFormatLine(FormatLineBean flbean, FormatBean fbean) throws Exception {
		
		Connection conn = null;
		
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			FormatManager fmgr = FormatManager.instance();
			
			result = getFormatLineDAO().modifyFormatLine(conn, flbean);

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
	 * @param FormatLineBean flbean
	 * @param FormatBean fbean
	 * @return int 수행개수
	 * @throws Exception 
	 */
	public int modifyCommFormatLine(FormatLineBean flbean, FormatBean fbean) throws Exception {
		
		Connection conn = null;
		
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			FormatManager fmgr = FormatManager.instance();
			
			result = getFormatLineDAO().modifyCommFormatLine(conn, flbean);

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
	 * 공통양식 입력속성 저장하기
	 * @param FormatLineBean flbean
	 * @param FormatBean fbean
	 * @return int 수행개수
	 * @throws Exception 
	 */
	public int addCommFormatLine(FormatLineBean flbean, FormatBean fbean) throws Exception {
		
		Connection conn = null;
		
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);
			
			FormatManager fmgr = FormatManager.instance();
			
			int formseq = flbean.getFormseq();
			
			//새로만들기(formseq=0)일 때 새로운 공통양식일련번호 가져오기
			if(formseq == 0) {
				formseq = fmgr.getNewCommFormatseq();
				
				flbean.setFormseq(formseq);
				fbean.setFormseq(formseq);
			}
			
			result = getFormatLineDAO().addCommFormatLine(conn, flbean);

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
	 * @param deptcd : 부서코드
	 * @param usrid : 사용자ID
	 * 
	 * @return FormatLineBean : 행추가형 구조를 가지고 있는 Bean
	 * @throws Exception 
	 */
	public FormatLineBean getFormatFormView(int sysdocno, int formseq) throws Exception{
		FormatLineBean result = new FormatLineBean();
		String colname = "";
		int cutlen = 0;
		//int headerlen = 0;
		int bodylen = 0 ;
		String beforetd = "";
		String aftertd = "";
		
		//양식개요, header, body, tail 정보 및 테이블 row, col 수 가져오기
		String formcomment = getFormatLineDAO().getFormatLineView(sysdocno, formseq, "FORMCOMMENT", true).toString();
		String formheaderhtml = getFormatLineDAO().getFormatLineView(sysdocno, formseq, "FORMHEADERHTML", true).toString();
		String formbodyhtml = getFormatLineDAO().getFormatLineView(sysdocno, formseq, "FORMBODYHTML", true).toString();
		String formtailhtml = getFormatLineDAO().getFormatLineView(sysdocno, formseq, "FORMTAILHTML", true).toString();
		int tblcols = Integer.parseInt(getFormatLineDAO().getFormatLineView(sysdocno, formseq, "TBLCOLS", false).toString());
		int tblrows = Integer.parseInt(getFormatLineDAO().getFormatLineView(sysdocno, formseq, "TBLROWS", false).toString());
		
		//바디 부분 제일 첫번째에 연번 데이터 칼럼 추가
		bodylen = formbodyhtml.length();
		aftertd = formbodyhtml.substring(formbodyhtml.indexOf("<td"), bodylen);
		beforetd = formbodyhtml.substring(0, formbodyhtml.indexOf("<td"));
		String colHeadAtt_ext = "bgcolor=\"rgb(225,225,255)\"";
		formbodyhtml = Utils.replace(formbodyhtml, formbodyhtml, beforetd + "<td style='border:1 solid gray' " + colHeadAtt_ext + ">&nbsp;</td>") + aftertd;
		
		//양식 구조 안의 $A, $B 등 제거
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
		
		//각 결과를 Bean에 저장
		result.setFormcomment(formcomment);
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
	public List getFormDataList(int sysdocno, int formseq, InputDeptSearchBoxBean idsbbean, boolean isTotalState, boolean isTotalShowStringState, boolean isIncludeNotSubmitData) throws Exception {
		return getFormatLineDAO().getFormDataList(sysdocno, formseq, idsbbean, isTotalState, isTotalShowStringState, isIncludeNotSubmitData);
	}
	
	/**
	 * 양식폼 내용에 대한  데이터 수정 보여주기 가져오기(데이터수정->레이어)
	 * @throws Exception 
	 */
	public List getFormDataList1(int sysdocno, int formseq, InputDeptSearchBoxBean idsbbean, int strIdx, int endIdx) throws Exception {
		return getFormatLineDAO().getFormDataList1(sysdocno, formseq, idsbbean, strIdx, endIdx);
	}
	
	/**
	 * 양식폼 내용에 대한  데이터 수정 보여주기 가져오기(데이터+수정/삭제버튼)
	 * @throws Exception 
	 */
	public List getFormDataList2(int sysdocno, int formseq, InputDeptSearchBoxBean idsbbean, int strIdx, int endIdx) throws Exception {
		return getFormatLineDAO().getFormDataList2(sysdocno, formseq, idsbbean, strIdx, endIdx);
	}	
	
	
	/**
	 * 행추가형 구조 및 속성 및 데이터 보기
	 * 
	 * @param sysdocno : 시스템 문서번호
	 * @param formseq : 양식일련번호
	 * @param deptcd : 부서코드
	 * @param usrid : 사용자ID
	 * @param chrgunitcd : 담당단위코드
	 * @param isfirst : 데이터가 없을 때 첫 행 자동추가 여부
	 * @param chgatt : true - 속성값 치환, false - 값만 치환
	 * 
	 * @return FormatLineBean
	 * @throws Exception 
	 */
	public FormatLineBean getFormatLineDataView(int sysdocno, int formseq, InputDeptSearchBoxBean idsbbean, boolean isfirst, boolean chgatt) throws Exception {
		FormatLineBean result = new FormatLineBean();
		String beforetr = "";
		String beforerows = "";
		String aftertr = "";
		//String beforetd = "";
		//String aftertd = "";
		int cutlen = 0;
		int headerlen = 0;
		int bodylen = 0 ;
		String cellname = "";
		String colattdata = "";
		String coldata = "";
		String replaceStr = "";
		String replaceBase = "";
		String replaceTmp = "";
		int startIdx = 0;
		int startIdx1 = 0;
		int rowspan = 0;
		
		//양식개요, header, body, tail 정보 및 테이블 row, col 수 가져오기
		String formcomment = getFormatLineDAO().getFormatLineView(sysdocno, formseq, "FORMCOMMENT", true).toString();
		String formhtml = getFormatLineDAO().getFormatLineView(sysdocno, formseq, "FORMHTML", true).toString();
		String formheaderhtml = getFormatLineDAO().getFormatLineView(sysdocno, formseq, "FORMHEADERHTML", true).toString();
		String formheadertable = formheaderhtml.substring(0,formheaderhtml.indexOf("<tr"));
		String formbodyhtml = getFormatLineDAO().getFormatLineView(sysdocno, formseq, "FORMBODYHTML", true).toString();
		String formtailhtml = getFormatLineDAO().getFormatLineView(sysdocno, formseq, "FORMTAILHTML", true).toString();
		int tblrows = 0;
		int tblcols = Integer.parseInt(getFormatLineDAO().getFormatLineView(sysdocno, formseq, "TBLCOLS", false).toString());
		
		int rowcnt = getFormatLineDAO().getFormatLineDataCnt(sysdocno, formseq, idsbbean);
		int defaultrow = Integer.parseInt(getFormatLineDAO().getFormatLineView(sysdocno, formseq, "TBLROWS", false).toString());
		
		tblrows = (rowcnt > 0 ? rowcnt : (isfirst ? defaultrow : 0));
		
		if(chgatt) {	//true이면 관리부분 추가
			//헤더 마지막 부분에 행추가 버튼 추가
			headerlen = formheaderhtml.length();
			beforetr = formheaderhtml.substring(0, formheaderhtml.indexOf("</tr>"));
			beforerows = formheaderhtml.substring(0, formheaderhtml.lastIndexOf("</tr>"));
			for(int i=0; i<beforerows.length();i++){
				startIdx = beforerows.indexOf("rowspan=\"");
				startIdx1 = beforerows.indexOf("\"", (startIdx+10));
				
				if(startIdx <= 0){
					break;
				}else{
					if(Integer.parseInt(beforerows.substring((startIdx+9),startIdx1)) >= rowspan){
						rowspan = Integer.parseInt(beforerows.substring((startIdx+9),startIdx1));
					}
				}
				
				beforerows = beforerows.substring(startIdx1,beforerows.length());
			}
			
			String addRowImg = "<td rowspan='" + rowspan + "' width='110' bgcolor='white'></td>";
			aftertr = formheaderhtml.substring(formheaderhtml.indexOf("</tr>"), headerlen);
			formheaderhtml = beforetr + addRowImg + aftertr ;
			
			int findIndex1 = formheaderhtml.indexOf("width=\"") + "width=\"".length();
			int findIndex2 = formheaderhtml.indexOf("\"", findIndex1);
			int width = Integer.parseInt(formheaderhtml.substring(findIndex1, findIndex2), 10) + 110;
			formheaderhtml = formheaderhtml.substring(0, findIndex1) + width + formheaderhtml.substring(findIndex2);
		}
		
		result.setSysdocno(sysdocno);
		result.setFormseq(formseq);
		result.setFormcomment(formcomment);
		result.setFormhtml(formhtml);
		result.setFormheaderhtml(formheaderhtml);
		result.setFormheadertable(formheadertable);
		result.setFormtailhtml(formtailhtml);
		result.setTblcols(tblcols);
		result.setTblrows(tblrows);
		result.setDatacnt(tblrows);
		
		//바디 부분 제일 첫번째에 연번 데이터 칼럼 추가
		bodylen = formbodyhtml.length();
		replaceBase = formbodyhtml.substring(0, formbodyhtml.indexOf("<td"));
		
		String colHeadAtt_ext = "bgcolor=\"rgb(225,225,255)\"";
		replaceTmp = replaceBase.replaceFirst("<tr", "<tr");
		formbodyhtml = formbodyhtml.replaceFirst(replaceBase, replaceTmp + "<td align='center' style='border:1 solid gray' " + colHeadAtt_ext + "></td>");
		
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
			
			colattdata = getFormatLineDAO().getAttLineFrmData(sysdocno, formseq, cellname);
			
			String[] gubun = colattdata.split("[|]");
			if ( "04".equals(gubun[0]) ) { 
				List attList = this.getAttListData(gubun[1]);
				if ( attList == null || attList.size() == 0 ) colattdata = "02|1";
			}
			
			if(chgatt) {	//true이면 각 칼럼 속성과 데이터를 치환				
				replaceStr = replaceAttributeData(cellname, colattdata, coldata);
			} else {	//false이면 데이터만 치환
				replaceStr = coldata;
			}
			
			//변환된 값을 formbodyhtml에 해당 위치에 치환
			//formbodyhtml = formbodyhtml.replaceFirst("\\$"+cellname, replaceStr);
			StringBuffer sb = new StringBuffer(formbodyhtml);
			String cell = "$"+cellname;
			int idx = formbodyhtml.indexOf(cell);
			sb.replace(idx, idx + cell.length(), replaceStr);
			formbodyhtml = sb.toString();
		}
		
		if(chgatt) {	//true이면 저장버튼 추가 
			bodylen = formbodyhtml.length();
			String addRowImg = "<td align='left'>&nbsp;<input type='text' onfocus='this.blur();savebutton.click()' style='width:0'><img src='/images/common/btn_add.gif' name='savebutton' onclick='checkSave(document.forms[0],\"insert\")' style='cursor:hand' alt='추가'></td>";
			beforetr = formbodyhtml.substring(0, formbodyhtml.lastIndexOf("</tr>"));
			aftertr = formbodyhtml.substring(formbodyhtml.lastIndexOf("</tr>"), bodylen);
			formbodyhtml = beforetr + Utils.replace(aftertr, aftertr, addRowImg + "</tr>");
		}
		
		result.setFormbodyhtml(formbodyhtml);

		return result;
	}	
	
	/**
	 * 행추가형 - 각 칼럼의 속성 및 데이터 변환
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
		
		//각 속성별 처리(gubun[0] : 속성 구분, gubun[1] : 속성 파라메타)
		if("01".equals(gubun[0])) {	//속성이 숫자인 칼럼(gubun[0]만 사용)
			replaceStr = "<input type='text' style='width:95%; ime-mode:disabled' align='center' name='" + colname + "' size='15' value=\"" + coldata +"\" onkeydown=key_entertotab() onkeypress=\"inputFilterKey('[0-9.-]');\" onchange=\"setChanged();instantCalculation('autocal');\">";
		} else if("02".equals(gubun[0])) {	//속성이 문자인 칼럼(gubun[1]의 값은 줄의 길이)
			if(Integer.parseInt(gubun[1]) > 1) {	//gubun[1]의 값이 1보다 크면 TEXTAREA로 표시
				replaceStr = "<textarea name='" + colname + "' rows='" + Integer.parseInt(gubun[1]) + "' style='width:95%; ime-mode:active' onchange=\"setChanged();\">" + coldata +"</textarea>";
			} else {	//gubun[1]의 값이 1보다 작으면 일반 텍스트박스로 표시
				replaceStr = "<input type='text' name='" + colname + "' value=\"" + coldata.replaceAll("\"", "&quot;") +"\" size='15' style='width:95%; ime-mode:active' onkeydown=key_entertotab() onchange=\"setChanged();\">";
			}
		} else if("03".equals(gubun[0])) {	//속성이 수식인 칼럼(초기 로드시에는 화면에 자동계산이라는 글자만 표시)
			//수식계산수정부분
			replaceStr = gubun[1];
			replaceStr = "<input type='hidden' name='" + colname + "' value=\"autocal\"><input type=\"text\" style=\"text-align:center;border:0;width:100%;\" disabled=\"true\" name=\"autocal\" cellname=\"" + colname + "\" title=\"자동계산:" + replaceStr + "\">";
//			if("".equals(coldata)) {
//				replaceStr = gubun[1];
//				replaceStr = "<input type='hidden' name='" + colname + "' value=\"autocal\"><input type=\"text\" style=\"text-align:center;border:0;width:100%;\" disabled=\"true\" name=\"autocal\" cellname=\"" + colname + "\" title=\"자동계산:" + replaceStr + "\">";
//			} else {
//				replaceStr = coldata;
//				replaceStr += "<input type='hidden' name='" + colname + "' value=\"autocal\">";
//			}
		} else if("04".equals(gubun[0])) {	//속성이 목록인 칼럼(gubun[1]의 값은 ATTLISTDTL과 ATTLISTMST에서 가져와야할 코드명)
			attList = new ArrayList();
			attList = getAttListData(gubun[1]);
			replaceStr = "<select name='" + colname + "' style='width:95%' align='center' onkeydown=key_entertotab() onchange=\"setChanged();\">";
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
	 * 입력하기, 입력완료 - 행추가형 데이터 저정
	 * @param sysdocno : 시스템 문서번호
	 * @param formseq : 양식일련번호
	 * @param rowList : 입력할 데이터 리스트
	 * @param tblcols : 칼럼수
	 * @param deptcd : 부서코드
	 * @param usrid : 사용자ID
	 * @param chrgunitcd : 담당단위코드
	 * @param mode : 처리모드
	 * 
	 * @return int : 결과코드
	 * @throws Exception 
	 */
	public int insertFormatLineData(int sysdocno, int formseq, List colList, int tblcols, String deptcd, String usrid, int chrgunitcd, String mode) throws Exception {
		Connection conn = null;

		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);
			
			result = insertFormatLineData(conn, sysdocno, formseq, colList, tblcols, deptcd, usrid, chrgunitcd, mode);
			 
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
	 * 입력하기, 입력완료 - 행추가형 데이터 저정
	 * @param Connection conn
	 * @param sysdocno : 시스템 문서번호
	 * @param formseq : 양식일련번호
	 * @param rowList : 입력할 데이터 리스트
	 * @param tblcols : 칼럼수
	 * @param deptcd : 부서코드
	 * @param usrid : 사용자ID
	 * @param chrgunitcd : 담당단위코드
	 * @param mode : 처리모드	
	 * 
	 * @return int : 결과코드
	 * @throws Exception 
	 */
	public int insertFormatLineData(Connection conn, int sysdocno, int formseq, List colList, int tblcols, String deptcd, String usrid, int chrgunitcd, String mode) throws Exception {

		int result = 0;
		int maxseq = 0;
		String colname = "";
		String resultCal = "";
		//List tmpList = null;
		StringBuffer insertQuery = null;
		DecimalFormat decimailFormat = new DecimalFormat("#.##########");
					
		maxseq = getMaxSeq(conn, sysdocno, formseq, deptcd, usrid);
		
		//입력하기전 기존 입력된 데이터를 삭제
		//result = getFormatLineDAO().deleteFormatLineData(conn, sysdocno, formseq, deptcd, usrid);
		
		//입력상태를 임시저장 상태로 변경한다.
		if(mode.equals("insert")) {
			result = getFormatLineDAO().updateInputState(conn, sysdocno, deptcd, usrid);
		}
		
		FormatManager fmgr = FormatManager.instance();
		String[][] deptInfo = fmgr.getInputDeptInfo(conn, sysdocno, deptcd);

		insertQuery = new StringBuffer();
		insertQuery.append("\n INSERT INTO DATALINEFRM(SYSDOCNO, FORMSEQ, TGTDEPTCD, INPUTUSRID, CHRGUNITCD, ROWSEQ,");
		insertQuery.append("\n                         DEPTCD1, DEPTNM1, DEPTCD2, DEPTNM2, DEPTCD3, DEPTNM3,        ");
		insertQuery.append("\n                         DEPTCD4, DEPTNM4, DEPTCD5, DEPTNM5, DEPTCD6, DEPTNM6         ");
		
		for(int j = 0; j < tblcols; j++) {
			if(j < 26) {
				colname = (char)('A' + j) + "";
			} else {
				colname = (char)('A' + j - 26) + "" + (char)('A' + j - 26);
			}
			
			insertQuery.append(", " + colname);
		}
		
		insertQuery.append(") \n VALUES(" + sysdocno + ", " + formseq + ", '" + deptcd + "', '" + usrid + "', " + chrgunitcd + ", " + (++maxseq));
		
		for ( int j = 0; j < deptInfo.length; j++) {
			insertQuery.append(", '" + deptInfo[j][0] + "', '" + deptInfo[j][1] + "'");
		}
		
		for(int k = 0; k < colList.size(); k++) {
			if(k < 26) {
				colname = (char)('A' + k) + "";
			} else {
				colname = (char)('A' + k - 26) + "" + (char)('A' + k - 26);
			}
			
			if((colname + ":" + "autocal").equals(colList.get(k))) {
				try {
					resultCal = decimailFormat.format(this.calExpression(conn, colList, sysdocno, formseq, colname));
				} catch (Exception e) {
					try {
						conn.rollback();
					} catch (Exception ex) {
						logger.error("ERROR",ex);
						throw ex;
					}

					throw new Exception((1) + ":" + e.getMessage());
				}
				
				insertQuery.append(", '" + resultCal + "'");
			} else {
				String value = null;
				if(k < 26) {
					value = colList.get(k).toString().substring(2, colList.get(k).toString().length());
				} else {
					value = colList.get(k).toString().substring(3, colList.get(k).toString().length());
				}				
				String[] gubun = getFormatLineDAO().getAttLineFrmData(sysdocno, formseq, colname).split("[|]");
				
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
		
		insertQuery.append(")");
		
		result = getFormatLineDAO().insertFormatLineData(conn, insertQuery);
		
		return result;
	}
	
	/** 
	 * 입력하기, 입력완료 - 행추가형 데이터 저정
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
	public int updateFormatLineData(int sysdocno, int formseq, List colList, int tblcols, String deptcd, String usrid, int chrgunitcd, int rowseq) throws Exception {
		Connection conn = null;
		int result = 0;
		//int maxseq = 0;
		String colname = "";
		String resultCal = "";
		StringBuffer updateQuery = null;
		DecimalFormat decimailFormat = new DecimalFormat("#.##########");
		
		try {
			conn = ConnectionManager.getConnection();
			
			conn.setAutoCommit(false);

			updateQuery = new StringBuffer();
			updateQuery.append("\n UPDATE DATALINEFRM 		");

			
			for(int j = 0; j < tblcols; j++) {
				if(j < 26) {
					colname = (char)('A' + j) + "";
				} else {
					colname = (char)('A' + j - 26) + "" + (char)('A' + j - 26);
				}
				
				if((colname + ":" + "autocal").equals(colList.get(j))) {
					try {
						resultCal = decimailFormat.format(this.calExpression(conn, colList, sysdocno, formseq, colname));
					} catch (Exception e) {
						try {
							conn.rollback();
						} catch (Exception ex) {
							logger.error("ERROR",ex);
							throw ex;
						}

						throw new Exception((1) + ":" + e.getMessage());
					}
					
					if(j == 0){
						updateQuery.append("SET " + colname + " = '"+ resultCal +"' ");
					}else{
						updateQuery.append(", " + colname + " = '"+ resultCal +"' ");
					}
				} else {
					String value = null;
					if(j < 26) {
						value = colList.get(j).toString().substring(2, colList.get(j).toString().length());
					} else {
						value = colList.get(j).toString().substring(3, colList.get(j).toString().length());
					}				 
					String[] gubun = getFormatLineDAO().getAttLineFrmData(sysdocno, formseq, colname).split("[|]");
					
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

					if(j == 0){
						updateQuery.append("SET " + colname + " = '" + value +"' ");
					}else{
						updateQuery.append(", " + colname + " = '" + value +"' ");
					}
				}			
			}			
			FormatManager fmgr = FormatManager.instance();
			String[][] deptInfo = fmgr.getInputDeptInfo(conn, sysdocno, deptcd);
			
			for ( int j = 0; j < deptInfo.length; j++) {
				updateQuery.append(", DEPTCD" + (j + 1) + " = '" + deptInfo[j][0] + "', DEPTNM" + (j + 1) + " = '" + deptInfo[j][1] + "'");
			}
						
			updateQuery.append("\n	WHERE SYSDOCNO = " + sysdocno 			);
			updateQuery.append("\n    AND FORMSEQ  = " + formseq 			);
			updateQuery.append("\n    AND TGTDEPTCD = '" + deptcd 	+"' "	);
			updateQuery.append("\n    AND INPUTUSRID = '" + usrid	+"' "	);
			updateQuery.append("\n    AND CHRGUNITCD = " + chrgunitcd		);
			updateQuery.append("\n    AND ROWSEQ = " + rowseq				);
			
			result = getFormatLineDAO().insertFormatLineData(conn, updateQuery);

			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				logger.error("ERROR",ex);
//				throw ex;
			}
			ConnectionManager.close(conn);
			throw e;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (Exception ex) {
				logger.error("ERROR", ex);
//				throw ex;
			}
			ConnectionManager.close(conn);
		}
		
		return result;
	}	
	
	/**
	 * 수식 계산 처리 함수 - 행추가형
	 * 
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
			colattdata = getFormatLineDAO().getAttLineFrmData(conn, sysdocno, formseq, colname);
			
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
	 * 공통 양식자료 보기 - 행추가형 속성 중 목록 데이터 가져오기
	 * 
	 * @param listcd : ATTLISTMST, ATTLISTDTL 코드이름
	 * 
	 * @return List
	 * @throws Exception 
	 */
	public List getAttListData(String listcd) throws Exception {
		return getFormatLineDAO().getAttListData(listcd);
	}
	
	public int getMaxSeq(Connection conn, int sysdocno, int formseq, String deptcd, String usrid)throws Exception{
		return getFormatLineDAO().getMaxSeq(conn, sysdocno, formseq, deptcd, usrid);
	}
	
	public int deleteFormatLineData(int sysdocno, int formseq, String deptcd, String usrid, int rowseq) throws Exception {
		return getFormatLineDAO().deleteFormatLineData(sysdocno, formseq, deptcd, usrid, rowseq);
	}
	
	public int deleteAllFormatLineData(int sysdocno, int formseq, String deptcd, String usrid) throws Exception {
		return getFormatLineDAO().deleteAllFormatLineData(sysdocno, formseq, deptcd, usrid);
	}
	
	/**
	 * 양식HTML을 헤더,바디,테일로 구분(필요없는 정보 삭제)
	 * @param String formhtml
	 * @return FormatLineBean 양식HTML
	 */
	public FormatLineBean FormatLineHtmlSeparator(String original_formhtml) throws Exception {
		
		FormatLineBean flbean  =  new FormatLineBean();
		
		//attTABLE, attTR, attTD에 테이블 재구성시 필요한 속성 정의
		String	attTABLE= "id=\"tbl\" border=\"0\" style=\"border-collapse:collapse\"";
		String	attTR	= "align=\"center\"";
		String	attTD	= "style=\"border:1 solid gray\"";
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
		String colHeadAtt_ext = "bgcolor=\"rgb(225,225,255)\"";
		int addTableWidth = 30;	//연번 컬럼 넓이
		
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
		
		//formheaderhtml 생성
		//table 태그 생성
		formheaderhtml.append(sTABLE + " " + attTABLE + " " + attTagWidth + "\"" + tblwidth + "\">\n");
		
		boolean rowHeadComp = false;
		startIndex += sTABLE.length();
		endIndex = html.indexOf(eTABLE, startIndex);
		for(trStartIndex = startIndex; trStartIndex < endIndex; ) {
			//table에 tr 태그 유무 체크
			trStartIndex = html.indexOf(sTR, trStartIndex);
			trEndIndex = html.indexOf(eTR, trStartIndex);
			if(trStartIndex != -1 && trStartIndex < trEndIndex
					&& trEndIndex != -1 && trEndIndex < endIndex) {
				//tr 태그 생성
				formheaderhtml.append("\t" + sTR + " " + attTR + " " + rowHeadAtt + ">\n");
				
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
						if(colspan != -1) {
							tmpStr.append(attTagColspan + "\"" + colspan + "\" ");
							if(colspan > 1) {
								tdCount += colspan - 1;
							}
						}
						if(rowspan != -1) {
							tmpStr.append(attTagRowspan + "\"" + rowspan + "\" ");
						}
						if(width != -1) {
							tmpStr.append(attTagWidth + "\"" + width + "\" ");
						}
						//table td 태그 생성
						formheaderhtml.append("\t\t" + sTD + " " + attTD + " " + tmpStr.toString() + ">");
						
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

						//속성셀 생성
						if(attValue.length() == 0
								|| (attValue.length() == 1 && attValue.getBytes()[0] == -95 && attValue.getBytes()[1] == -95)) {
							if(rowHeadComp == false) {
								if(colspan < 1) {
									colspan = 1;
								}
								if(tdCount - (colspan - 1) == 0 && rowHeadCount > 0 ) {
									rowHeadComp = true;	
								} else {
									return null;
								}
							}				
						} else {
							if(rowHeadComp == false) {
								formheaderhtml.append(attValue + eTD + "\n");
							} else {
								return null;
							}
						}

						tdStartIndex = tdEndIndex + 1;
						tdCount++;
					} else {
						break;
					}
				}
				
				formheaderhtml.append("\t" + eTR + "\n");

				if(rowHeadComp == false) {
					rowHeadCount++;
				} else {
					break;
				}
				
				if(tblcols < tdCount) {
					tblcols = tdCount;
					if(tblcols > 52) {
						flbean.setTblcols(53);
						return flbean;
					}
				}
			} else {
				if(rowHeadComp == false) {
					return null;
				} else {
					break;
				}
			}
			trStartIndex = trEndIndex + 1;
		}
		
		findIndex1 = formheaderhtml.lastIndexOf(eTR);
		formheaderhtml.delete(formheaderhtml.lastIndexOf(eTR, findIndex1 - 1) + eTR.length(), formheaderhtml.length());
		
		//formbodyhtml 생성
		tmpStr.delete(0, tmpStr.capacity());
		formbodyhtml.append("\t" + sTR + " " + attTR + ">\n");
		tmpStr.append("\t" + sTR + " " + attTR + ">\n");
		//A~Z~AA~ZZ 52글자(26*2) : 입력란 셀이름 입력
		String cellName = null;
		for(int i = 0; i < tblcols; i++) {
			if(i < 26) {
				cellName = "$" + (char)('A' + i);
			} else {
				cellName = "$" + (char)('A' + i - 26) + (char)('A' + i - 26);
			}
			formbodyhtml.append("\t\t" + sTD + " " + attTD + ">" + cellName + eTD + "\n");
			tmpStr.append("\t\t" + sTD + " " + attTD + ">&nbsp;" + eTD + "\n");
		}
		
		formbodyhtml.append("\t" + eTR + "\n");
		tmpStr.append("\t" + eTR + "\n");
		
		//formtailhtml 생성
		//table 태그 닫기
		formtailhtml.append(eTABLE);

		//formhtml 생성
		formhtml.append(formheaderhtml.toString());
		formhtml.append(tmpStr.toString());
		formhtml.append(formtailhtml.toString());
		
		//연번 컬럼 삽입(formbodyhtml에는 연번 컬럼을 추가하지 않고 formbodyhtml_ext에 추가)
		findIndex1 = formheaderhtml.indexOf(sTD);
		formheaderhtml.insert(findIndex1, sTD + " " + attTD + " " + attTagRowspan + "\"" + rowHeadCount + "\" " + attTagWidth + "\"" + addTableWidth + "\">연번" + eTD + "\n\t\t");
		
		//formheaderhtml의 width를 연번 컬럼 넓이에 맞게 조절
		findIndex1 = formheaderhtml.indexOf(attTagWidth) + attTagWidth.length() + 1;
		findIndex2 = formheaderhtml.indexOf("\"", findIndex1);
		formheaderhtml.replace(findIndex1, findIndex2, Integer.toString(tblwidth + addTableWidth, 10));
		
		//formhtml 생성
		formbodyhtml_ext.append(formbodyhtml.toString());
		findIndex1 = formbodyhtml_ext.indexOf(sTD);
		formbodyhtml_ext.insert(findIndex1, sTD + " " + attTD + " " + colHeadAtt_ext + ">&nbsp;" + eTD + "\n\t\t");
		
		tblrows = 1;	//행추가형은 1
		flbean.setFormhtml(formhtml.toString());
		flbean.setFormheaderhtml(formheaderhtml.toString());
		flbean.setFormbodyhtml(formbodyhtml.toString());
		flbean.setFormbodyhtml_ext(formbodyhtml_ext.toString());
		flbean.setFormtailhtml(formtailhtml.toString());
		flbean.setTblcols(tblcols);
		flbean.setTblrows(tblrows);
		flbean.setTblwidth(tblwidth);
		flbean.setColHeadCount(colHeadCount);
		flbean.setRowHeadCount(rowHeadCount);
		
		return flbean;
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
			attValue = getFormatLineDAO().getFormatLineAtt(sysdocno, formseq);
		} else if(sysdocno == 0 && formseq != 0) {
			attValue = getFormatLineDAO().getCommFormatLineAtt(formseq);
		}
		
		StringBuffer option = new StringBuffer("<option value=\"02|1\">문자:단문(1줄)</option>");
		
		while(true) {
			if(cellcount < 26) {
				cellname = "$" + (char)('A' + cellcount);
			} else {
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
	
	public String insertSortScriptHtml(String formhtml, String formheaderhtml) throws Exception {
		StringBuffer tmp = new StringBuffer(formheaderhtml);
		int rowHeadCount = FormatLineHtmlSeparator(formhtml).getRowHeadCount();
		int columnCount = FormatLineHtmlSeparator(formhtml).getColHeadCount() +
							FormatLineHtmlSeparator(formhtml).getTblcols();
		int arrColspan[] = new int[columnCount + 1];
		int tdNo = 0;
		int findIndex1 = 0;
		int findIndex2 = 0;
		int findIndex3 = 0;
		for(int i = 0; i < tmp.length(); ) {
			findIndex1 = tmp.indexOf("<td", i);
			if(findIndex1 > -1) {
				findIndex2 = tmp.indexOf("colspan", findIndex1);
				findIndex3 = tmp.indexOf(">", findIndex1);			
				for(int j = 0; j < arrColspan.length; j++) {
					if(arrColspan[j] == 0) {
						tdNo = j;
						break;
					}
				}
				if(findIndex2 > findIndex3 || findIndex2 == -1) {
					tmp.insert(findIndex3,
							" onmouseover=\"commentView()\" onmouseout=\"commentView()\" " + "" +
							"style=\"cursor:hand;\" onclick=\"sort(\'up\', " + tdNo + ", " + rowHeadCount + ")\"");
					arrColspan[tdNo] = 1;
				} else {

					int colspan = 0;
					String findValue = htmlSeparatorFindAttValue(tmp.substring(findIndex1, findIndex3 + 1), "colspan=");
					if(findValue != null && findValue.equals("") == false) {
						colspan = Integer.parseInt(findValue, 10);
					}

					for(int k = tdNo; k < tdNo + colspan; k++) {
						arrColspan[k] = 0;
					}
				}
				i = findIndex3 + 1;
			} else if(findIndex1 == -1) {
				break;
			}
		}

		return tmp.toString();
	}
}