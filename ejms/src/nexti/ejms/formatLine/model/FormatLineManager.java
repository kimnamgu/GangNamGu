/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���߰��� manager
 * ����:
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
	 * ���߰����� ��ļӼ� ��������
	 * @param int sysdocno 
	 * @param int formseq
	 * @return String[] �������
	 * @throws Exception
	 */
	public String[] getFormatLineAtt(int sysdocno, int formseq) throws Exception {
		return getFormatLineDAO().getFormatLineAtt(sysdocno, formseq);
	}
	
	/**
	 * ��� �̸�����
	 * @param FormatLineBean flbean
	 * @param FormatBean fbean
	 * @return int ���ళ��
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
	 * ������ �̸�����
	 * @param FormatLineBean flbean
	 * @param FormatBean fbean
	 * @return int ���ళ��
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
	 * ����ߴ���� �̸�����
	 * @param FormatLineBean flbean
	 * @param FormatBean fbean
	 * @return int ���ళ��
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
	 * ��� �̸����� �ӽõ����� ����
	 * @param Connection conn
	 * @param FormatBean fbean
	 * @param String[] cellatt
	 * @param int count
	 * @return int ���ళ��
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
				coldata = "�� ";
			} else if(att[0].equals("03") == true) {
				coldata = "autocal";
			} else if(att[0].equals("04") == true) {	
				List attlist = AttrManager.instance().getFormatAttList("%", "%");
				
				if(attlist == null || attlist.size() == 0) {
					coldata = "�� ";
				} else {
					for(int j = 0; j < attlist.size(); j++) {
						AttrBean attbean = (AttrBean)attlist.get(j);
						
						if(attbean.getListcd().equals(att[1].toString()) == true) {
							coldata = ((AttrBean)attbean.getListdtlList().get(0)).getListdtlnm();
							break;
						} else if(j + 1 == attlist.size()){
							coldata = "�� ";
						}
					}		
				}
			}		
			
			colList.add(colname + ":" + coldata);
		}

		//�ӽõ����� �Է�
		for(int datacnt = 0; datacnt < count; datacnt++) {
			result += insertFormatLineData(conn, sysdocno, formseq, colList, tblcols, dept_code, user_id, 1, "");
		}
		
		getFormatLineDAO().addFormatLinePreviewTempData(conn, fbean, "", 10);
		
		return result;
	}
	
	/**
	 * ��� �̸�����
	 * @param int sysdocno
	 * @param int formseq
	 * @return int ���ళ��
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
	 * ��� �Է¼Ӽ� �����ϱ�
	 * @param FormatLineBean flbean
	 * @param FormatBean fbean
	 * @return int ���ళ��
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
			
			//���θ����(formseq=0)�� �� ���ο� �������Ϸù�ȣ ��������
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
	 * ��� �Է¼Ӽ� �����ϱ�
	 * @param FormatLineBean flbean
	 * @param FormatBean fbean
	 * @return int ���ళ��
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
	 * ������ �Է¼Ӽ� �����ϱ�
	 * @param FormatLineBean flbean
	 * @param FormatBean fbean
	 * @return int ���ళ��
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
	 * ������ �Է¼Ӽ� �����ϱ�
	 * @param FormatLineBean flbean
	 * @param FormatBean fbean
	 * @return int ���ళ��
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
			
			//���θ����(formseq=0)�� �� ���ο� �������Ϸù�ȣ ��������
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
	 * ����� ���� ��������
	 * 
	 * @param sysdocno : �ý��۹�����ȣ
	 * @param formseq : ����Ϸù�ȣ
	 * @param deptcd : �μ��ڵ�
	 * @param usrid : �����ID
	 * 
	 * @return FormatLineBean : ���߰��� ������ ������ �ִ� Bean
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
		
		//��İ���, header, body, tail ���� �� ���̺� row, col �� ��������
		String formcomment = getFormatLineDAO().getFormatLineView(sysdocno, formseq, "FORMCOMMENT", true).toString();
		String formheaderhtml = getFormatLineDAO().getFormatLineView(sysdocno, formseq, "FORMHEADERHTML", true).toString();
		String formbodyhtml = getFormatLineDAO().getFormatLineView(sysdocno, formseq, "FORMBODYHTML", true).toString();
		String formtailhtml = getFormatLineDAO().getFormatLineView(sysdocno, formseq, "FORMTAILHTML", true).toString();
		int tblcols = Integer.parseInt(getFormatLineDAO().getFormatLineView(sysdocno, formseq, "TBLCOLS", false).toString());
		int tblrows = Integer.parseInt(getFormatLineDAO().getFormatLineView(sysdocno, formseq, "TBLROWS", false).toString());
		
		//�ٵ� �κ� ���� ù��°�� ���� ������ Į�� �߰�
		bodylen = formbodyhtml.length();
		aftertd = formbodyhtml.substring(formbodyhtml.indexOf("<td"), bodylen);
		beforetd = formbodyhtml.substring(0, formbodyhtml.indexOf("<td"));
		String colHeadAtt_ext = "bgcolor=\"rgb(225,225,255)\"";
		formbodyhtml = Utils.replace(formbodyhtml, formbodyhtml, beforetd + "<td style='border:1 solid gray' " + colHeadAtt_ext + ">&nbsp;</td>") + aftertd;
		
		//��� ���� ���� $A, $B �� ����
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
			
			//��ȯ�� ���� formbodyhtml�� �ش� ��ġ�� ġȯ
			formbodyhtml = formbodyhtml.replaceFirst("\\$" + colname, "&nbsp;");
		}
		
		//�� ����� Bean�� ����
		result.setFormcomment(formcomment);
		result.setFormheaderhtml(formheaderhtml);
		result.setFormbodyhtml(formbodyhtml);
		result.setFormtailhtml(formtailhtml);
		result.setTblcols(tblcols);
		result.setTblrows(tblrows);
		
		return result;
	}
	
	/**
	 * ����� ���뿡 ���� ������ ����Ʈ ��������
	 * @throws Exception 
	 */
	public List getFormDataList(int sysdocno, int formseq, InputDeptSearchBoxBean idsbbean, boolean isTotalState, boolean isTotalShowStringState, boolean isIncludeNotSubmitData) throws Exception {
		return getFormatLineDAO().getFormDataList(sysdocno, formseq, idsbbean, isTotalState, isTotalShowStringState, isIncludeNotSubmitData);
	}
	
	/**
	 * ����� ���뿡 ����  ������ ���� �����ֱ� ��������(�����ͼ���->���̾�)
	 * @throws Exception 
	 */
	public List getFormDataList1(int sysdocno, int formseq, InputDeptSearchBoxBean idsbbean, int strIdx, int endIdx) throws Exception {
		return getFormatLineDAO().getFormDataList1(sysdocno, formseq, idsbbean, strIdx, endIdx);
	}
	
	/**
	 * ����� ���뿡 ����  ������ ���� �����ֱ� ��������(������+����/������ư)
	 * @throws Exception 
	 */
	public List getFormDataList2(int sysdocno, int formseq, InputDeptSearchBoxBean idsbbean, int strIdx, int endIdx) throws Exception {
		return getFormatLineDAO().getFormDataList2(sysdocno, formseq, idsbbean, strIdx, endIdx);
	}	
	
	
	/**
	 * ���߰��� ���� �� �Ӽ� �� ������ ����
	 * 
	 * @param sysdocno : �ý��� ������ȣ
	 * @param formseq : ����Ϸù�ȣ
	 * @param deptcd : �μ��ڵ�
	 * @param usrid : �����ID
	 * @param chrgunitcd : �������ڵ�
	 * @param isfirst : �����Ͱ� ���� �� ù �� �ڵ��߰� ����
	 * @param chgatt : true - �Ӽ��� ġȯ, false - ���� ġȯ
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
		
		//��İ���, header, body, tail ���� �� ���̺� row, col �� ��������
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
		
		if(chgatt) {	//true�̸� �����κ� �߰�
			//��� ������ �κп� ���߰� ��ư �߰�
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
		
		//�ٵ� �κ� ���� ù��°�� ���� ������ Į�� �߰�
		bodylen = formbodyhtml.length();
		replaceBase = formbodyhtml.substring(0, formbodyhtml.indexOf("<td"));
		
		String colHeadAtt_ext = "bgcolor=\"rgb(225,225,255)\"";
		replaceTmp = replaceBase.replaceFirst("<tr", "<tr");
		formbodyhtml = formbodyhtml.replaceFirst(replaceBase, replaceTmp + "<td align='center' style='border:1 solid gray' " + colHeadAtt_ext + "></td>");
		
		//�� �Ӽ��� �ٵ� �� �ش� ��ġ�� �ִ� ���μ���
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
			
			if(chgatt) {	//true�̸� �� Į�� �Ӽ��� �����͸� ġȯ				
				replaceStr = replaceAttributeData(cellname, colattdata, coldata);
			} else {	//false�̸� �����͸� ġȯ
				replaceStr = coldata;
			}
			
			//��ȯ�� ���� formbodyhtml�� �ش� ��ġ�� ġȯ
			//formbodyhtml = formbodyhtml.replaceFirst("\\$"+cellname, replaceStr);
			StringBuffer sb = new StringBuffer(formbodyhtml);
			String cell = "$"+cellname;
			int idx = formbodyhtml.indexOf(cell);
			sb.replace(idx, idx + cell.length(), replaceStr);
			formbodyhtml = sb.toString();
		}
		
		if(chgatt) {	//true�̸� �����ư �߰� 
			bodylen = formbodyhtml.length();
			String addRowImg = "<td align='left'>&nbsp;<input type='text' onfocus='this.blur();savebutton.click()' style='width:0'><img src='/images/common/btn_add.gif' name='savebutton' onclick='checkSave(document.forms[0],\"insert\")' style='cursor:hand' alt='�߰�'></td>";
			beforetr = formbodyhtml.substring(0, formbodyhtml.lastIndexOf("</tr>"));
			aftertr = formbodyhtml.substring(formbodyhtml.lastIndexOf("</tr>"), bodylen);
			formbodyhtml = beforetr + Utils.replace(aftertr, aftertr, addRowImg + "</tr>");
		}
		
		result.setFormbodyhtml(formbodyhtml);

		return result;
	}	
	
	/**
	 * ���߰��� - �� Į���� �Ӽ� �� ������ ��ȯ
	 * 
	 * @param colname : Į����
	 * @param colattdata : Į���Ӽ�
	 * @param coldata : Į��������
	 * 
	 * @return String : ġȯ ���
	 * @throws Exception 
	 */
	public String replaceAttributeData(String colname, String colattdata, String coldata) throws Exception {
		String replaceStr = "";
		List attList = null;
		String[] gubun = new String[2];
		
		gubun = colattdata.split("[|]");
		
		//�� �Ӽ��� ó��(gubun[0] : �Ӽ� ����, gubun[1] : �Ӽ� �Ķ��Ÿ)
		if("01".equals(gubun[0])) {	//�Ӽ��� ������ Į��(gubun[0]�� ���)
			replaceStr = "<input type='text' style='width:95%; ime-mode:disabled' align='center' name='" + colname + "' size='15' value=\"" + coldata +"\" onkeydown=key_entertotab() onkeypress=\"inputFilterKey('[0-9.-]');\" onchange=\"setChanged();instantCalculation('autocal');\">";
		} else if("02".equals(gubun[0])) {	//�Ӽ��� ������ Į��(gubun[1]�� ���� ���� ����)
			if(Integer.parseInt(gubun[1]) > 1) {	//gubun[1]�� ���� 1���� ũ�� TEXTAREA�� ǥ��
				replaceStr = "<textarea name='" + colname + "' rows='" + Integer.parseInt(gubun[1]) + "' style='width:95%; ime-mode:active' onchange=\"setChanged();\">" + coldata +"</textarea>";
			} else {	//gubun[1]�� ���� 1���� ������ �Ϲ� �ؽ�Ʈ�ڽ��� ǥ��
				replaceStr = "<input type='text' name='" + colname + "' value=\"" + coldata.replaceAll("\"", "&quot;") +"\" size='15' style='width:95%; ime-mode:active' onkeydown=key_entertotab() onchange=\"setChanged();\">";
			}
		} else if("03".equals(gubun[0])) {	//�Ӽ��� ������ Į��(�ʱ� �ε�ÿ��� ȭ�鿡 �ڵ�����̶�� ���ڸ� ǥ��)
			//���İ������κ�
			replaceStr = gubun[1];
			replaceStr = "<input type='hidden' name='" + colname + "' value=\"autocal\"><input type=\"text\" style=\"text-align:center;border:0;width:100%;\" disabled=\"true\" name=\"autocal\" cellname=\"" + colname + "\" title=\"�ڵ����:" + replaceStr + "\">";
//			if("".equals(coldata)) {
//				replaceStr = gubun[1];
//				replaceStr = "<input type='hidden' name='" + colname + "' value=\"autocal\"><input type=\"text\" style=\"text-align:center;border:0;width:100%;\" disabled=\"true\" name=\"autocal\" cellname=\"" + colname + "\" title=\"�ڵ����:" + replaceStr + "\">";
//			} else {
//				replaceStr = coldata;
//				replaceStr += "<input type='hidden' name='" + colname + "' value=\"autocal\">";
//			}
		} else if("04".equals(gubun[0])) {	//�Ӽ��� ����� Į��(gubun[1]�� ���� ATTLISTDTL�� ATTLISTMST���� �����;��� �ڵ��)
			attList = new ArrayList();
			attList = getAttListData(gubun[1]);
			replaceStr = "<select name='" + colname + "' style='width:95%' align='center' onkeydown=key_entertotab() onchange=\"setChanged();\">";
			replaceStr += "<option value=\"\">==����==</option>";
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
	 * �Է��ϱ�, �Է¿Ϸ� - ���߰��� ������ ����
	 * @param sysdocno : �ý��� ������ȣ
	 * @param formseq : ����Ϸù�ȣ
	 * @param rowList : �Է��� ������ ����Ʈ
	 * @param tblcols : Į����
	 * @param deptcd : �μ��ڵ�
	 * @param usrid : �����ID
	 * @param chrgunitcd : �������ڵ�
	 * @param mode : ó�����
	 * 
	 * @return int : ����ڵ�
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
	 * �Է��ϱ�, �Է¿Ϸ� - ���߰��� ������ ����
	 * @param Connection conn
	 * @param sysdocno : �ý��� ������ȣ
	 * @param formseq : ����Ϸù�ȣ
	 * @param rowList : �Է��� ������ ����Ʈ
	 * @param tblcols : Į����
	 * @param deptcd : �μ��ڵ�
	 * @param usrid : �����ID
	 * @param chrgunitcd : �������ڵ�
	 * @param mode : ó�����	
	 * 
	 * @return int : ����ڵ�
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
		
		//�Է��ϱ��� ���� �Էµ� �����͸� ����
		//result = getFormatLineDAO().deleteFormatLineData(conn, sysdocno, formseq, deptcd, usrid);
		
		//�Է»��¸� �ӽ����� ���·� �����Ѵ�.
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
	 * �Է��ϱ�, �Է¿Ϸ� - ���߰��� ������ ����
	 * 
	 * @param sysdocno : �ý��� ������ȣ
	 * @param formseq : ����Ϸù�ȣ
	 * @param rowList : �Է��� ������ ����Ʈ
	 * @param tblcols : Į����
	 * @param deptcd : �μ��ڵ�
	 * @param usrid : �����ID
	 * @param chrgunitcd : �������ڵ�	
	 * 
	 * @return int : ����ڵ�
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
	 * ���� ��� ó�� �Լ� - ���߰���
	 * 
	 * @param Connection conn
	 * @param sysdocno : �ý��� ������ȣ
	 * @param formseq : ����Ϸù�ȣ
	 * @param deptcd : �μ��ڵ�
	 * 
	 * @return String : ���� ó�� ��� 
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
	 * ���� ����ڷ� ���� - ���߰��� �Ӽ� �� ��� ������ ��������
	 * 
	 * @param listcd : ATTLISTMST, ATTLISTDTL �ڵ��̸�
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
	 * ���HTML�� ���,�ٵ�,���Ϸ� ����(�ʿ���� ���� ����)
	 * @param String formhtml
	 * @return FormatLineBean ���HTML
	 */
	public FormatLineBean FormatLineHtmlSeparator(String original_formhtml) throws Exception {
		
		FormatLineBean flbean  =  new FormatLineBean();
		
		//attTABLE, attTR, attTD�� ���̺� �籸���� �ʿ��� �Ӽ� ����
		String	attTABLE= "id=\"tbl\" border=\"0\" style=\"border-collapse:collapse\"";
		String	attTR	= "align=\"center\"";
		String	attTD	= "style=\"border:1 solid gray\"";
		String 	sTABLE 	= "<table";						//�����±״� >���� ����
		String 	eTABLE	= "</table>";
		String 	sTR		= "<tr";						//�����±״� >���� ����
		String 	eTR		= "</tr>";
		String 	sTD		= "<td";						//�����±״� >���� ����
		String 	eTD		= "</td>";
		String	attTagRowspan = "rowspan=";
		String	attTagColspan = "colspan=";
		String	attTagWidth = "width=";
		
		String rowHeadAtt = "bgcolor=\"rgb(210,210,255)\""; 
		String colHeadAtt_ext = "bgcolor=\"rgb(225,225,255)\"";
		int addTableWidth = 30;	//���� �÷� ����
		
		//FormatLineBean�� ����� ��ȯ�Ǵ� ����
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
		
		//�ӽ� ��뺯��
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
		
		//����html�� table �±� ���� üũ
		startIndex = html.indexOf(sTABLE, 0);
		endIndex = html.indexOf(eTABLE, startIndex);
		if(startIndex != -1 && endIndex != -1) {
			//���̺� �±� �̿��� �±� ����
			html.delete(endIndex + eTABLE.length(), html.length());
			html.delete(0, startIndex);
		} else {
			return null;
		}
		
		//table width ��������
		startIndex = html.indexOf(sTABLE, 0);
		endIndex = html.indexOf(">", startIndex);
		tmpStr.append(html.substring(startIndex, endIndex + 1));
		
		String tmp = htmlSeparatorFindAttValue(tmpStr.toString(), attTagWidth);
		if(tmp != null && tmp.equals("") == false) {
			tblwidth = Integer.parseInt(tmp, 10);
		}
		
		//formheaderhtml ����
		//table �±� ����
		formheaderhtml.append(sTABLE + " " + attTABLE + " " + attTagWidth + "\"" + tblwidth + "\">\n");
		
		boolean rowHeadComp = false;
		startIndex += sTABLE.length();
		endIndex = html.indexOf(eTABLE, startIndex);
		for(trStartIndex = startIndex; trStartIndex < endIndex; ) {
			//table�� tr �±� ���� üũ
			trStartIndex = html.indexOf(sTR, trStartIndex);
			trEndIndex = html.indexOf(eTR, trStartIndex);
			if(trStartIndex != -1 && trStartIndex < trEndIndex
					&& trEndIndex != -1 && trEndIndex < endIndex) {
				//tr �±� ����
				formheaderhtml.append("\t" + sTR + " " + attTR + " " + rowHeadAtt + ">\n");
				
				int tdCount = 0;
				trStartIndex += sTR.length();
				for(tdStartIndex = trStartIndex; tdStartIndex < trEndIndex; ) {
					//tr�� td �±� ���� üũ
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
						//table td �±� ����
						formheaderhtml.append("\t\t" + sTD + " " + attTD + " " + tmpStr.toString() + ">");
						
						//td�� �� ���� ��������
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
						}	//<TD></TD> ���̿��� �����͸� ����
						String attValue = tmpStr.toString().trim();

						//�Ӽ��� ����
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
		
		//formbodyhtml ����
		tmpStr.delete(0, tmpStr.capacity());
		formbodyhtml.append("\t" + sTR + " " + attTR + ">\n");
		tmpStr.append("\t" + sTR + " " + attTR + ">\n");
		//A~Z~AA~ZZ 52����(26*2) : �Է¶� ���̸� �Է�
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
		
		//formtailhtml ����
		//table �±� �ݱ�
		formtailhtml.append(eTABLE);

		//formhtml ����
		formhtml.append(formheaderhtml.toString());
		formhtml.append(tmpStr.toString());
		formhtml.append(formtailhtml.toString());
		
		//���� �÷� ����(formbodyhtml���� ���� �÷��� �߰����� �ʰ� formbodyhtml_ext�� �߰�)
		findIndex1 = formheaderhtml.indexOf(sTD);
		formheaderhtml.insert(findIndex1, sTD + " " + attTD + " " + attTagRowspan + "\"" + rowHeadCount + "\" " + attTagWidth + "\"" + addTableWidth + "\">����" + eTD + "\n\t\t");
		
		//formheaderhtml�� width�� ���� �÷� ���̿� �°� ����
		findIndex1 = formheaderhtml.indexOf(attTagWidth) + attTagWidth.length() + 1;
		findIndex2 = formheaderhtml.indexOf("\"", findIndex1);
		formheaderhtml.replace(findIndex1, findIndex2, Integer.toString(tblwidth + addTableWidth, 10));
		
		//formhtml ����
		formbodyhtml_ext.append(formbodyhtml.toString());
		findIndex1 = formbodyhtml_ext.indexOf(sTD);
		formbodyhtml_ext.insert(findIndex1, sTD + " " + attTD + " " + colHeadAtt_ext + ">&nbsp;" + eTD + "\n\t\t");
		
		tblrows = 1;	//���߰����� 1
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
	 * �Ӽ��� ã��
	 * @param String html
	 * @param String attStr
	 * @return String �Ӽ���
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
	 * formbodyhtml�� �Է¼Ӽ������� ����
	 * @param String formbodyhtml
	 * @param int tblcols
	 * @param int sysdocno
	 * @param int formseq
	 * @return String formbodyhtml
	 * @throws Exception 
	 */
	public String makeAttCell(String formbodyhtml, int tblcols, int sysdocno, int formseq) throws Exception {
		
		//A~Z~AA~ZZ 52����(26*2)
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
		
		StringBuffer option = new StringBuffer("<option value=\"02|1\">����:�ܹ�(1��)</option>");
		
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
					option.append("����</option>");
				} else if(optionSet[0].equals("02") == true) {
					if(optionSet[1].equals("1")) {
						option.append("����:�ܹ�(1��)</option>");
					} else {
						option.append("����:�幮</option>");
					}
				} else if(optionSet[0].equals("03") == true) {
					option.append("����:" + optionSet[1] + "</option>");
				} else if(optionSet[0].equals("04") == true) {	
					List attlist = AttrManager.instance().getFormatAttList("%", "%");
					
					if(attlist == null || attlist.size() == 0) {
						option.append("���:" + optionSet[1] + "</option>");
					} else {
						for(int i = 0; i < attlist.size(); i++) {
							AttrBean bean = (AttrBean)attlist.get(i);
							
							if(bean.getListcd().equals(optionSet[1].toString()) == true) {
								option.append("���:" + bean.getListnm() + "</option>");
								break;
							} else if(i + 1 == attlist.size()){
								option.append("���:" + optionSet[1] + "</option>");
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