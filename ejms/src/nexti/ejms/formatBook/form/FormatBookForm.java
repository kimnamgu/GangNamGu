/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����ڷ��� actionform
 * ����:
 */
package nexti.ejms.formatBook.form;

import java.util.List;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class FormatBookForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private List		formdatalist	= null;
	private int 		datacnt				= 0;    //�ڷ��Է°Ǽ�

	private int			seqno			= 0;	//����
	private int			type			= 0;	//�ۼ����
	
	private int			page			= 0;	//������
	private String		deptcd			= "";	//�μ��ڵ�
	private String		makedt			= "";	//�ۼ��⵵
	private String		title			= "";	//����
	private String		makeusr			= "";	//�ۼ����̸�
	
	private int			sysdocno		= 0;	//�ý��۹�����ȣ
	private int			formseq			= 0;	//����Ϸù�ȣ
	private int			commformseq		= 0;	//�������Ϸù�ȣ
	private int			usedsysdocno	= 0;	//����ߴ���Ľý��۹�����ȣ
	private int			usedformseq		= 0;	//����ߴ�����Ϸù�ȣ
	private String		formtitle		= "";	//�������
	private String		formkind		= "";	//�������
	private String		formkindname	= "";	//��������̸�
	private String		formcomment		= "";	//��İ���
	private	String[]	listcategorynm1	= null;	//ī�װ����(ī�װ���ϰ�������)
	private	Collection	listcategorynm2	= null;	//ī�װ����(ī�װ���Ϻ����ֱ�)
	private int			filecount		= 0;	//��ϵ� ������� ����
	private List		listfilebook	= null;//���÷�����ϸ��
	private String		openinput		= "";	//�����Է�(Ÿ�μ��ڷẸ��)
	
	private FormFile	uploadfile		= null;	//÷�����ϸ�
	private String		rdb_sort		= "";   //���ռ���

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
	
	private boolean includeNotSubmitData	= false;	//�������ڷ� ����
	
	public String getOpeninput() {
		return openinput;
	}
	public void setOpeninput(String openinput) {
		this.openinput = openinput;
	}
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
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		return null;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
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

	public String getFormcomment() {
		return formcomment;
	}

	public void setFormcomment(String formcomment) {
		this.formcomment = formcomment;
	}

	public List getFormdatalist() {
		return formdatalist;
	}

	public void setFormdatalist(List formdatalist) {
		this.formdatalist = formdatalist;
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

	public String getMakedt() {
		return makedt;
	}

	public void setMakedt(String makedt) {
		this.makedt = makedt;
	}

	public String getMakeusr() {
		return makeusr;
	}

	public void setMakeusr(String makeusr) {
		this.makeusr = makeusr;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String[] getListcategorynm1() {
		return listcategorynm1;
	}

	public void setListcategorynm1(String[] listcategorynm1) {
		this.listcategorynm1 = listcategorynm1;
	}

	public Collection getListcategorynm2() {
		return listcategorynm2;
	}

	public void setListcategorynm2(Collection listcategorynm2) {
		this.listcategorynm2 = listcategorynm2;
	}

	public FormFile getUploadfile() {
		return uploadfile;
	}

	public void setUploadfile(FormFile uploadfile) {
		this.uploadfile = uploadfile;
	}

	public int getFilecount() {
		return filecount;
	}

	public void setFilecount(int filecount) {
		this.filecount = filecount;
	}

	public String getRdb_sort() {
		return rdb_sort;
	}

	public void setRdb_sort(String rdb_sort) {
		this.rdb_sort = rdb_sort;
	}

	public int getCommformseq() {
		return commformseq;
	}

	public void setCommformseq(int commformseq) {
		this.commformseq = commformseq;
	}

	public int getUsedformseq() {
		return usedformseq;
	}

	public void setUsedformseq(int usedformseq) {
		this.usedformseq = usedformseq;
	}

	public int getUsedsysdocno() {
		return usedsysdocno;
	}

	public void setUsedsysdocno(int usedsysdocno) {
		this.usedsysdocno = usedsysdocno;
	}

	public List getListfilebook() {
		return listfilebook;
	}

	public void setListfilebook(List listfilebook) {
		this.listfilebook = listfilebook;
	}
}