/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��������� bean
 * ����:
 */
package nexti.ejms.formatFixed.model;

import java.util.Collection;
import java.util.List;

public class FormatFixedBean {

	private int datacnt				= 0;    //�ڷ��Է°Ǽ�
	
	private int 	seqno 			= 0; 	//����
	private String	deptcd			= "";	//�μ��ڵ�
	
	private int		sysdocno		= 0;	//�ý��۹�����ȣ
	private int		formseq			= 0;	//����Ϸù�ȣ
	private String	formtitle		= "";	//�������
	private String	formkind		= "";	//�������
	private String	formkindname	= "";	//��������̸�
	private String	formcomment		= "";	//��İ���
	private String	formhtml		= "";	//��Ŀ���
	private String	formheaderhtml	= "";	//��ı���(���)
	private String	formbodyhtml	= "";	//��ı���(�ٵ�)
	private String	formbodyhtml_ext= "";	//��ı���(�ٵ�) => ����ۼ��� ȭ�� ǥ�ÿ�
	private String	formtailhtml	= "";	//��ı���(����)
	private int		tblcols			= 0;	//���̺� ������
	private int		tblrows			= 0;	//���̺� �ళ��
	private int		ord				= 0;	//���ļ���
	private String	crtdt			= "";	//��������
	private String	crtusrid		= "";	//�������ڵ�
	private String	uptdt			= "";	//��������
	private String	uptusrid		= "";	//�������ڵ�
	private String[] cellatt		= null;	//��� �Է¶��Ӽ�
	private List	listform		= null;	//��� ���
	
	private int		tblwidth		= 0;	//���̺� ����
	private int		colHeadCount	= 0;	//���Ӹ��� ����
	private int		rowHeadCount	= 0;	//��Ӹ��� ���
	
	private String		sch_deptcd1					= "";	//�μ��ڵ�1
	private String		sch_deptcd2					= "";	//�μ��ڵ�2
	private String		sch_deptcd3					= "";	//�μ��ڵ�3
	private String		sch_deptcd4					= "";	//�μ��ڵ�4
	private String		sch_deptcd5					= "";	//�μ��ڵ�5
	private String		sch_deptcd6					= "";	//�μ��ڵ�6
	private String		sch_chrgunitcd				= "";	//�������ڵ�
	private String		sch_inputusrid				= "";	//�Է����ڵ�
	private Collection	sch_deptcd1_collection		= null;	//�μ����1
	private Collection	sch_deptcd2_collection		= null;	//�μ����2
	private Collection	sch_deptcd3_collection		= null;	//�μ����3
	private Collection	sch_deptcd4_collection		= null;	//�μ����4
	private Collection	sch_deptcd5_collection		= null;	//�μ����5
	private Collection	sch_deptcd6_collection		= null;	//�μ����6
	private Collection	sch_chrgunitcd_collection	= null;	//���������
	private Collection	sch_inputusrid_collection	= null;	//�Է��ڸ��
	
	private boolean	totalState						= false; //�Ѱ躸��
	private boolean subtotalState					= false; //�Ұ躸��
	private boolean	totalShowStringState			= false; //�Ѱ躸��
	private boolean subtotalShowStringState			= false; //�Ұ躸��
	
	private boolean includeNotSubmitData			= false; //�������ڷ� ����
	
	public boolean isIncludeNotSubmitData() {
		return includeNotSubmitData;
	}
	public void setIncludeNotSubmitData(boolean includeNotSubmitData) {
		this.includeNotSubmitData = includeNotSubmitData;
	}
	public String getSch_chrgunitcd() {
		return sch_chrgunitcd;
	}
	public void setSch_chrgunitcd(String sch_chrgunitcd) {
		this.sch_chrgunitcd = sch_chrgunitcd;
	}
	public Collection getSch_chrgunitcd_collection() {
		return sch_chrgunitcd_collection;
	}
	public void setSch_chrgunitcd_collection(Collection sch_chrgunitcd_collection) {
		this.sch_chrgunitcd_collection = sch_chrgunitcd_collection;
	}
	public String getSch_deptcd1() {
		return sch_deptcd1;
	}
	public void setSch_deptcd1(String sch_deptcd1) {
		this.sch_deptcd1 = sch_deptcd1;
	}
	public Collection getSch_deptcd1_collection() {
		return sch_deptcd1_collection;
	}
	public void setSch_deptcd1_collection(Collection sch_deptcd1_collection) {
		this.sch_deptcd1_collection = sch_deptcd1_collection;
	}
	public String getSch_deptcd2() {
		return sch_deptcd2;
	}
	public void setSch_deptcd2(String sch_deptcd2) {
		this.sch_deptcd2 = sch_deptcd2;
	}
	public Collection getSch_deptcd2_collection() {
		return sch_deptcd2_collection;
	}
	public void setSch_deptcd2_collection(Collection sch_deptcd2_collection) {
		this.sch_deptcd2_collection = sch_deptcd2_collection;
	}
	public String getSch_deptcd3() {
		return sch_deptcd3;
	}
	public void setSch_deptcd3(String sch_deptcd3) {
		this.sch_deptcd3 = sch_deptcd3;
	}
	public Collection getSch_deptcd3_collection() {
		return sch_deptcd3_collection;
	}
	public void setSch_deptcd3_collection(Collection sch_deptcd3_collection) {
		this.sch_deptcd3_collection = sch_deptcd3_collection;
	}
	public String getSch_deptcd4() {
		return sch_deptcd4;
	}
	public void setSch_deptcd4(String sch_deptcd4) {
		this.sch_deptcd4 = sch_deptcd4;
	}
	public Collection getSch_deptcd4_collection() {
		return sch_deptcd4_collection;
	}
	public void setSch_deptcd4_collection(Collection sch_deptcd4_collection) {
		this.sch_deptcd4_collection = sch_deptcd4_collection;
	}
	public String getSch_deptcd5() {
		return sch_deptcd5;
	}
	public void setSch_deptcd5(String sch_deptcd5) {
		this.sch_deptcd5 = sch_deptcd5;
	}
	public Collection getSch_deptcd5_collection() {
		return sch_deptcd5_collection;
	}
	public void setSch_deptcd5_collection(Collection sch_deptcd5_collection) {
		this.sch_deptcd5_collection = sch_deptcd5_collection;
	}
	public String getSch_deptcd6() {
		return sch_deptcd6;
	}
	public void setSch_deptcd6(String sch_deptcd6) {
		this.sch_deptcd6 = sch_deptcd6;
	}
	public Collection getSch_deptcd6_collection() {
		return sch_deptcd6_collection;
	}
	public void setSch_deptcd6_collection(Collection sch_deptcd6_collection) {
		this.sch_deptcd6_collection = sch_deptcd6_collection;
	}
	public Collection getSch_inputusrid_collection() {
		return sch_inputusrid_collection;
	}
	public void setSch_inputusrid_collection(Collection sch_inputusrid_collection) {
		this.sch_inputusrid_collection = sch_inputusrid_collection;
	}
	public String getSch_inputusrid() {
		return sch_inputusrid;
	}
	public void setSch_inputusrid(String sch_inputusrid) {
		this.sch_inputusrid = sch_inputusrid;
	}	
	public boolean isSubtotalShowStringState() {
		return subtotalShowStringState;
	}
	public void setSubtotalShowStringState(boolean subtotalShowStringState) {
		this.subtotalShowStringState = subtotalShowStringState;
	}
	public boolean isTotalShowStringState() {
		return totalShowStringState;
	}
	public void setTotalShowStringState(boolean totalShowStringState) {
		this.totalShowStringState = totalShowStringState;
	}
	public boolean isSubtotalState() {
		return subtotalState;
	}
	public void setSubtotalState(boolean subtotalState) {
		this.subtotalState = subtotalState;
	}
	public boolean isTotalState() {
		return totalState;
	}
	public void setTotalState(boolean totalState) {
		this.totalState = totalState;
	}
	
	public String[] getCellatt() {
		return cellatt;
	}
	public void setCellatt(String[] cellatt) {
		this.cellatt = cellatt;
	}
	public String getCrtdt() {
		return crtdt;
	}
	public void setCrtdt(String crtdt) {
		this.crtdt = crtdt;
	}
	public String getCrtusrid() {
		return crtusrid;
	}
	public void setCrtusrid(String crtusrid) {
		this.crtusrid = crtusrid;
	}
	public int getDatacnt() {
		return datacnt;
	}
	public void setDatacnt(int datacnt) {
		this.datacnt = datacnt;
	}
	public String getDeptcd() {
		return deptcd;
	}
	public void setDeptcd(String deptcd) {
		this.deptcd = deptcd;
	}
	public String getFormbodyhtml() {
		return formbodyhtml;
	}
	public void setFormbodyhtml(String formbodyhtml) {
		this.formbodyhtml = formbodyhtml;
	}
	public String getFormcomment() {
		return formcomment;
	}
	public void setFormcomment(String formcomment) {
		this.formcomment = formcomment;
	}
	public String getFormheaderhtml() {
		return formheaderhtml;
	}
	public void setFormheaderhtml(String formheaderhtml) {
		this.formheaderhtml = formheaderhtml;
	}
	public String getFormhtml() {
		return formhtml;
	}
	public void setFormhtml(String formhtml) {
		this.formhtml = formhtml;
	}
	public String getFormkind() {
		return formkind;
	}
	public void setFormkind(String formkind) {
		this.formkind = formkind;
	}
	public String getFormkindname() {
		return formkindname;
	}
	public void setFormkindname(String formkindname) {
		this.formkindname = formkindname;
	}
	public int getFormseq() {
		return formseq;
	}
	public void setFormseq(int formseq) {
		this.formseq = formseq;
	}
	public String getFormtailhtml() {
		return formtailhtml;
	}
	public void setFormtailhtml(String formtailhtml) {
		this.formtailhtml = formtailhtml;
	}
	public String getFormtitle() {
		return formtitle;
	}
	public void setFormtitle(String formtitle) {
		this.formtitle = formtitle;
	}
	public List getListform() {
		return listform;
	}
	public void setListform(List listform) {
		this.listform = listform;
	}
	public int getOrd() {
		return ord;
	}
	public void setOrd(int ord) {
		this.ord = ord;
	}
	public int getSeqno() {
		return seqno;
	}
	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}
	public int getSysdocno() {
		return sysdocno;
	}
	public void setSysdocno(int sysdocno) {
		this.sysdocno = sysdocno;
	}
	public int getTblcols() {
		return tblcols;
	}
	public void setTblcols(int tblcols) {
		this.tblcols = tblcols;
	}
	public int getTblrows() {
		return tblrows;
	}
	public void setTblrows(int tblrows) {
		this.tblrows = tblrows;
	}
	public String getUptdt() {
		return uptdt;
	}
	public void setUptdt(String uptdt) {
		this.uptdt = uptdt;
	}
	public String getUptusrid() {
		return uptusrid;
	}
	public void setUptusrid(String uptusrid) {
		this.uptusrid = uptusrid;
	}
	public String getFormbodyhtml_ext() {
		return formbodyhtml_ext;
	}
	public void setFormbodyhtml_ext(String formbodyhtml_ext) {
		this.formbodyhtml_ext = formbodyhtml_ext;
	}
	public int getTblwidth() {
		return tblwidth;
	}
	public void setTblwidth(int tblwidth) {
		this.tblwidth = tblwidth;
	}
	public int getColHeadCount() {
		return colHeadCount;
	}
	public void setColHeadCount(int colHeadCount) {
		this.colHeadCount = colHeadCount;
	}
	public int getRowHeadCount() {
		return rowHeadCount;
	}
	public void setRowHeadCount(int rowHeadCount) {
		this.rowHeadCount = rowHeadCount;
	}
}