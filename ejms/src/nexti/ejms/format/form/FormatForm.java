/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���վ�� actionform
 * ����:
 */
package nexti.ejms.format.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class FormatForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;

	private int		seqno			= 0;	//����
	private int		type			= 0;	//�ۼ����
	private int		commformseq		= 0;	//�������Ϸù�ȣ
	private int		usedsysdocno	= 0;	//����ߴ���Ľý��۹�����ȣ
	private int		usedformseq		= 0;	//����ߴ�����Ϸù�ȣ
	private String	deptcd			= "";	//���úμ��ڵ�
	
	private int		sysdocno		= 0;	//�ý��۹�����ȣ
	private int		formseq			= 0;	//����Ϸù�ȣ
	private String	formtitle		= "";	//�������
	private String	formkind		= "";	//�������
	private String	formkindname	= "";	//��������̸�
	private String	formcomment		= "";	//��İ���
	private String	formhtml		= "";	//��Ŀ���
	private String	formheaderhtml	= "";	//��ı���(���)
	private String	formbodyhtml	= "";	//��ı���(�ٵ�)
	private String	formtailhtml	= "";	//��ı���(����
	private int		tblcols			= 0;	//���̺� ������
	private int		tblrows			= 0;	//���̺� �ళ��
	private int		ord				= 0;	//���ļ���
	private String	crtdt			= "";	//��������
	private String	crtusrid		= "";	//�������ڵ�
	private String	uptdt			= "";	//��������
	private String	uptusrid		= "";	//�������ڵ�
	private List	listform		= null;	//��� ���
	
	private int		page			= 1;	//������
	private int  	treescroll		= 0;	//������ ��ũ��
	private String	searchdept		= "";	//���úμ����� ��ü����
	private String	searchtitle		= "";	//��������˻�����
	private String	searchcomment	= "";	//��������˻�����
	private String	searchuser		= "";	//�ۼ��ڰ˻�����
	private String	searchyear		= "";	//�ۼ��⵵�˻�����
	
	private String	attach			= "true";	//����÷������
	private int		reqformno		= 0;	//��û����Ĺ�ȣ
	private int		reqseq			= 0;	//��û��������ȣ
	private String	ea_id			= "";	//���ڰ�����̵�
	private String 	orggbn			= "";	//��������
	
	public String getOrggbn() {
		return orggbn;
	}
	public void setOrggbn(String orggbn) {
		this.orggbn = orggbn;
	}
	public String getEa_id() {
		return ea_id;
	}
	public void setEa_id(String ea_id) {
		this.ea_id = ea_id;
	}
	public String getSearchcomment() {
		return searchcomment;
	}
	public void setSearchcomment(String searchcomment) {
		this.searchcomment = searchcomment;
	}
	public int getReqseq() {
		return reqseq;
	}
	public void setReqseq(int reqseq) {
		this.reqseq = reqseq;
	}
	public int getReqformno() {
		return reqformno;
	}
	public void setReqformno(int reqformno) {
		this.reqformno = reqformno;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getAttach() {
		return attach;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		return null;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
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

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getSearchyear() {
		return searchyear;
	}

	public void setSearchyear(String searchyear) {
		this.searchyear = searchyear;
	}

	public String getSearchdept() {
		return searchdept;
	}

	public void setSearchdept(String searchdept) {
		this.searchdept = searchdept;
	}

	public String getSearchtitle() {
		return searchtitle;
	}

	public void setSearchtitle(String searchtitle) {
		this.searchtitle = searchtitle;
	}

	public String getSearchuser() {
		return searchuser;
	}

	public void setSearchuser(String searchuser) {
		this.searchuser = searchuser;
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

	public int getTreescroll() {
		return treescroll;
	}

	public void setTreescroll(int treescroll) {
		this.treescroll = treescroll;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public String getDeptcd() {
		return deptcd;
	}

	public void setDeptcd(String deptcd) {
		this.deptcd = deptcd;
	}
}