/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���վ�� bean
 * ����:
 */
package nexti.ejms.format.model;

public class FormatBean {

	private int 	seqno 			= 0; 	//����
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
	
	private int		page			= 0;	//������
	private int  	treescroll		= 0;	//������ ��ũ��
	private String	searchdept		= "";	//���úμ����� ��ü����
	private String	searchtitle		= "";	//��������˻�����
	private String	searchcomment	= "";	//��������˻�����
	private String	searchuser		= "";	//�ۼ��ڰ˻�����
	private String	searchyear		= "";	//�ۼ��⵵�˻�����
	
	private String	attach		= "false";	//����÷������
	
	public String getSearchcomment() {
		return searchcomment;
	}
	public void setSearchcomment(String searchcomment) {
		this.searchcomment = searchcomment;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
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
	public void setFormheaderhtml(String formhederhtml) {
		this.formheaderhtml = formhederhtml;
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
	public String getFormhtml() {
		return formhtml;
	}
	public void setFormhtml(String formhtml) {
		this.formhtml = formhtml;
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
	public int getTreescroll() {
		return treescroll;
	}
	public void setTreescroll(int treescroll) {
		this.treescroll = treescroll;
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