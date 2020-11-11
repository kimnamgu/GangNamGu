/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����ڷ��� bean
 * ����:
 */
package nexti.ejms.formatBook.model;

import java.util.Collection;
import java.util.List;

public class FormatBookBean {

	private int 	seqno 			= 0; 	//����
	
	private int		sysdocno		= 0;	//�ý��۹�����ȣ
	private int		formseq			= 0;	//����Ϸù�ȣ
	private String	formtitle		= "";	//�������
	private String	formkind		= "";	//�������
	private String	formkindname	= "";	//��������̸�
	private String	formcomment		= "";	//��İ���
	private int		ord				= 0;	//���ļ���
	private	String[] listcategorynm1	= null;	//ī�װ����(ī�װ���ϰ�������)
	
	private String  deptcd			= "";   //�μ�
	private List	formdatalist	= null; //��ȸ����
	private List	listfilebook	= null;	//��ĸ���Ʈ
	private String	rdb_sort		= "";   //���ռ���
	
	private String	filenm			= "";
	private String	originfilenm	= "";
	private int		filesize		= 0;
	
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
	public String getFilenm() {
		return filenm;
	}
	public void setFilenm(String filenm) {
		this.filenm = filenm;
	}
	public int getFilesize() {
		return filesize;
	}
	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}
	public String getOriginfilenm() {
		return originfilenm;
	}
	public void setOriginfilenm(String originfilenm) {
		this.originfilenm = originfilenm;
	}
	public List getListfilebook() {
		return listfilebook;
	}
	public void setListfilebook(List listfilebook) {
		this.listfilebook = listfilebook;
	}
	public String getFormcomment() {
		return formcomment;
	}
	public void setFormcomment(String formcomment) {
		this.formcomment = formcomment;
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
	public String getFormtitle() {
		return formtitle;
	}
	public void setFormtitle(String formtitle) {
		this.formtitle = formtitle;
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
	public String getDeptcd() {
		return deptcd;
	}
	public void setDeptcd(String deptcd) {
		this.deptcd = deptcd;
	}
	public List getFormdatalist() {
		return formdatalist;
	}
	public void setFormdatalist(List formdatalist) {
		this.formdatalist = formdatalist;
	}
	public String getRdb_sort() {
		return rdb_sort;
	}
	public void setRdb_sort(String rdb_sort) {
		this.rdb_sort = rdb_sort;
	}
	public String[] getListcategorynm1() {
		return listcategorynm1;
	}
	public void setListcategorynm1(String[] listcategorynm1) {
		this.listcategorynm1 = listcategorynm1;
	}

}