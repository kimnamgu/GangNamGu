/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��������� manager
 * ����:
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
	 * ��������� ��ļӼ� ��������
	 * @param int sysdocno 
	 * @param int formseq
	 * @return String[] �������
	 * @throws Exception
	 */
	public String[] getFormatFixedAtt(int sysdocno, int formseq) throws Exception {
		return getFormatFixedDAO().getFormatFixedAtt(sysdocno, formseq);
	}
	
	/**
	 * ��� �̸�����
	 * @param FormatFixedBean ffbean
	 * @param FormatBean fbean
	 * @param String depttype
	 * @return int ���ళ��
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
	 * ������ �̸�����
	 * @param FormatFixedBean ffbean
	 * @param FormatBean fbean
	 * @param String depttype
	 * @return int ���ళ��
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
			
			fbean.setTblcols(getFormatFormView(sysdocno, formseq, "�μ�").getTblcols());
			fbean.setTblrows(getFormatFormView(sysdocno, formseq, "�μ�").getTblrows());
			
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
	 * ����ߴ���� �̸�����
	 * @param FormatFixedBean ffbean
	 * @param FormatBean fbean
	 * @param String depttype
	 * @return int ���ళ��
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
			
			fbean.setTblcols(getFormatFormView(sysdocno, formseq, "�μ�").getTblcols());
			fbean.setTblrows(getFormatFormView(sysdocno, formseq, "�μ�").getTblrows());
			
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
	 * ��� �̸����� �ӽõ����� ����
	 * @param Connection conn
	 * @param FormatBean fbean
	 * @param String[] cellatt
	 * @param String depttype
	 * @param int count
	 * @return int ���ళ��
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
		List colListSet = new ArrayList();
		for(int i = 0; i < tblrows; i++) {
			colListSet.add(colList);
		}
		
		//�μ����¿� ���� �ӽúμ��� ��������
		List deptList = getFormatFixedDAO().getListTempDeptData(conn, depttype, count);
		if ( deptList == null || deptList.size() < count ) {
			deptList = getFormatFixedDAO().getListTempDeptData(conn, "��", count);
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
	 * ��� �̸�����
	 * @param int sysdocno
	 * @param int formseq
	 * @return int ���ళ��
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
	 * ��� �Է¼Ӽ� �����ϱ�
	 * @param FormatFixedBean ffbean
	 * @param FormatBean fbean
	 * @return int ���ళ��
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
	 * ������ �Է¼Ӽ� �����ϱ�
	 * @param FormatFixedBean flbean
	 * @param FormatBean fbean
	 * @return int ���ళ��
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
	 * ��� �Է¼Ӽ� �����ϱ�
	 * @param FormatFixedBean ffbean
	 * @param FormatBean fbean
	 * @return int ���ళ��
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
			
			//���θ����(formseq=0)�� �� ���ο� �������Ϸù�ȣ ��������
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
	 * ������ �Է¼Ӽ� �����ϱ�
	 * @param FormatFixedBean ffbean
	 * @param FormatBean fbean
	 * @return int ���ళ��
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
			
			//���θ����(formseq=0)�� �� ���ο� �������Ϸù�ȣ ��������
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
	 * ����� ���� ��������
	 * 
	 * @param sysdocno : �ý��۹�����ȣ
	 * @param formseq : ����Ϸù�ȣ
	 * 
	 * @return FormatFixedBean : ��������� ������ ������ �ִ� Bean
	 * @throws Exception 
	 */
	public FormatFixedBean getFormatFormView(int sysdocno, int formseq, String deptnm) throws Exception{
		FormatFixedBean result = new FormatFixedBean();
		int cutlen = 0;
		String colname = "";
		
		//��İ���, header, body, tail ���� �� ���̺� row, col �� ��������
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
		
		//ù��° FOR���� ������� �ݺ�
		for(int i = 0; i < tblrows; i++) {
			//�ι�°  FOR���� Į�������� �ݺ��ϸ鼭 $A, $B, $C ������ �� �κ� ����
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
		}
		
		//�� ����� Bean�� ����
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
	 * ����� ���뿡 ���� ������ ����Ʈ ��������
	 * @throws Exception 
	 */
	public List getFormDataList(int sysdocno, int formseq) throws Exception {
		return getFormatFixedDAO().getFormDataList(sysdocno, formseq);
	}
	
	/**
	 * ���߰��� ���� �� �Ӽ� �� ������ ����
	 * 
	 * @param sysdocno : �ý��� ������ȣ
	 * @param formseq : ����Ϸù�ȣ
	 * @param deptcd : �μ��ڵ�
	 * @param usrid : �����ID
	 * @param chrgunitcd : �������ڵ�
	 * @param chgatt : true - �Ӽ��� ġȯ, false - ���� ġȯ
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
		
		//��İ���, header, body, tail ���� �� ���̺� row, col �� ��������
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
		
		//ù��° FOR���� ������� �ݺ�
		for(int i = 0; i < tblrows; i++) {
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
				
				if(chgatt) {	//true�̸� �� Į�� �Ӽ��� �����͸� ġȯ					
					replaceStr = replaceAttributeData(cellname, colattdata, coldata);
				} else {	//false�̸� �����͸� ġȯ
					if("01".equals(gubun[0]) || "03".equals(gubun[0])) {	//�Ӽ��� ������ Į��(gubun[0]�� ���)
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
				
				//��ȯ�� ���� formbodyhtml�� �ش� ��ġ�� ġȯ
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
	 * ��������� - �� Į���� �Ӽ� �� ������ ��ȯ
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
		
		//�� �Ӽ��� ó��(gubun[0] : �Ӽ� ����, gubun[1] : �Ӽ� ������Ƽ)
		if("01".equals(gubun[0])) {	//�Ӽ��� ������ Į��(gubun[0]�� ���)
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
		} else if("02".equals(gubun[0])) {	//�Ӽ��� ������ Į��(gubun[1]�� ���� ���� ����)
			if(Integer.parseInt(gubun[1]) > 1) {	//gubun[1]�� ���� 1���� ũ�� TEXTAREA�� ǥ��
				replaceStr = "<textarea style='width:95%; ime-mode:active' name='" + colname + "' rows='" + Integer.parseInt(gubun[1]) + "' onchange=\"setChanged();\">" + coldata +"</textarea>";
			} else {	//gubun[1]�� ���� 1���� ������ �Ϲ� �ؽ�Ʈ�ڽ��� ǥ��
				replaceStr = "<input type='text' name='" + colname + "' style='width:95%; ime-mode:active' value=\"" + coldata.replaceAll("\"", "&quot;") +"\" onkeydown=key_entertotab() onchange=\"setChanged();\">";
			}
		} else if("03".equals(gubun[0])) {	//�Ӽ��� ������ Į��(�ʱ� �ε�ÿ��� ȭ�鿡 �ڵ�����̶�� ���ڸ� ǥ��)
			//���İ������κ�
			replaceStr = gubun[1];
			replaceStr = "<input type='hidden' name='" + colname + "' value=\"autocal\"><input type=\"text\" style=\"text-align:center;border:0;width:100%;\" disabled=\"true\" name=\"autocal\" cellname=\"" + colname + "\" title=\"�ڵ����:" + replaceStr + "\">";
		} else if("04".equals(gubun[0])) {	//�Ӽ��� ����� Į��(gubun[1]�� ���� ATTLISTDTL�� ATTLISTMST���� �����;��� �ڵ��)
			attList = new ArrayList();
			attList = FormatLineManager.instance().getAttListData(gubun[1]);
			replaceStr = "<select name='" + colname + "' style='width:95%' onkeydown=key_entertotab() onchange=\"setChanged();\">";
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
	 * �Է��ϱ�, �Է¿Ϸ� - ��������� ������ ����
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
	 * �Է��ϱ�, �Է¿Ϸ� - ��������� ������ ����
	 * @param Connection conn
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
	public int insertFormatFixedData(Connection conn, int sysdocno, int formseq, List rowList, int tblcols, String deptcd, String usrid, int chrgunitcd, String mode) throws Exception {

		int result = 0;
		String colname = "";
		String resultCal = "";
		List tmpList = null;
		StringBuffer insertQuery = null;
		DecimalFormat decimailFormat = new DecimalFormat("#.##########");
			
		result = getFormatFixedDAO().deleteFormatFixedData(conn, sysdocno, formseq, deptcd, usrid);
		
		if(mode.equals("insert")){
			//�Է»��¸� �ӽ����� ���·� �����Ѵ�.		
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
	 * ���� ��� ó�� �Լ� - ���߰���
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
	 * ����� ���뿡 ���� ������ ����Ʈ ��������
	 * @throws Exception 
	 */
	public List getFormFixedDataList(FormatFixedBean schBean) throws Exception {
		return getFormatFixedDAO().getFormFixedDataList(schBean);
	}
	
	/**
	 * ���HTML�� ���,�ٵ�,���Ϸ� ����(�ʿ���� ���� ����)
	 * @param String formhtml
	 * @return FormatFixedBean ���HTML
	 */
	public FormatFixedBean FormatFixedHtmlSeparator(String original_formhtml) throws Exception {
		
		FormatFixedBean ffbean  =  new FormatFixedBean();
		
		//attTABLE, attTR, attTD�� ���̺� �籸���� �ʿ��� �Ӽ� ����
		String	attTABLE= "id=\"tbl\" border=\"1\" bordercolor=\"gray\" style=\"border-collapse:collapse\"";
		String	attTR	= "align=\"center\"";
		String	attTD	= "";
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
		String colHeadAtt = "bgcolor=\"rgb(240,240,255)\""; 
		String colHeadAtt_ext = "bgcolor=\"rgb(225,225,255)\"";
		int addTableWidth = 100;	//���� �÷� ����
		
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
		StringBuffer emptyformbodyhtml = new StringBuffer();	//�Ӽ�($A,$B...)�������� HTML
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
		
		//formheaderhtml, formbodyhtml ����
		//table �±� ����
		formheaderhtml.append(sTABLE + " " + attTABLE + " " + attTagWidth + "\"" + tblwidth + "\">\n");
		
		boolean colHeadComp = false;
		boolean rowHeadComp = false;
		int totalTdCount = 0;
		startIndex += sTABLE.length();
		endIndex = html.indexOf(eTABLE, startIndex);
		for(trStartIndex = startIndex; trStartIndex < endIndex; ) {
			//table�� tr �±� ���� üũ
			trStartIndex = html.indexOf(sTR, trStartIndex);
			trEndIndex = html.indexOf(eTR, trStartIndex);
			if(trStartIndex != -1 && trStartIndex < trEndIndex
					&& trEndIndex != -1 && trEndIndex < endIndex) {
				//tr �±� ����
				if(rowHeadComp == false) {
					formheaderhtml.append("\t" + sTR + " " + attTR + " " + rowHeadAtt + ">\n");
				} else {
					formbodyhtml.append("\t" + sTR + " " + attTR + ">\n");
					emptyformbodyhtml.append("\t" + sTR + " " + attTR + ">\n");
				}
				
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
						//table td �±� ����
						if(rowHeadComp == false) {
							formheaderhtml.append("\t\t" + sTD + " " + attTD + " " + tmpStr.toString() + ">");
						} else {
							formbodyhtml.append("\t\t" + sTD + " " + attTD + " " + tmpStr.toString() + ">");
							emptyformbodyhtml.append("\t\t" + sTD + " " + attTD + " " + tmpStr.toString() + ">");
						}
						
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
						tmpStr.delete(0, tmpStr.capacity());
						
						//�Ӽ��� ����
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
								
							//A~Z~AA~ZZ 52����(26*2) : �Է¶� ���̸� �Է�
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
					
					//�� ���� �Է¶� ���� ������ üũ
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
		tblrows = tblrows - rowHeadCount;	//���߰����� 1
		
		//formtailhtml ����
		//table �±� �ݱ�
		formtailhtml.append(eTABLE);

		//formhtml ����
		formhtml.append(formheaderhtml.toString());
		formhtml.append(emptyformbodyhtml.toString());
		formhtml.append(formtailhtml.toString());
		
		//���� �÷� ����(formbodyhtml���� ���� �÷��� �߰����� �ʰ� formbodyhtml_ext�� �߰�)
		findIndex1 = formheaderhtml.indexOf(sTD);
		formheaderhtml.insert(findIndex1, sTD + " " + attTD + " " + attTagRowspan + "\"" + rowHeadCount + "\" " + attTagWidth + "\"" + addTableWidth + "\">�μ�" + eTD + "\n\t\t");
		
		//formheaderhtml�� width�� ���� �÷� ���̿� �°� ����
		findIndex1 = formheaderhtml.indexOf(attTagWidth) + attTagWidth.length() + 1;
		findIndex2 = formheaderhtml.indexOf("\"", findIndex1);
		formheaderhtml.replace(findIndex1, findIndex2, Integer.toString(tblwidth + addTableWidth, 10));
		
		//formbodyhtml_ext ����
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
			attValue = getFormatFixedDAO().getFormatFixedAtt(sysdocno, formseq);
		} else if(sysdocno == 0 && formseq != 0) {
			attValue = getFormatFixedDAO().getCommFormatFixedAtt(formseq);
		}
		
		StringBuffer option = new StringBuffer("<option value=\"01|\">����</option>");
		
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
}