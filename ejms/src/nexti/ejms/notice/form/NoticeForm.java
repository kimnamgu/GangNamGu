/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� actionform
 * ����:
 */
package nexti.ejms.notice.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class NoticeForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private int seqno           = 0;      //����
	private int seq             = 0;      //�������ױ۹�ȣ	
	private String title        = "";     //����
	private String content      = "";     //����
	private int visitno         = 0;      //��ȸ��
	private String crtdt        = "";     //�ۼ�����
	private String crtusrnm     = "";     //�ۼ��ڼ���
	private FormFile[] fileList = new FormFile[3];    //÷������
	
	private int page            = 1 ;     //������
	private String gubun        = "";     //�˻����� (1:����, 2:����)
	private String searchval    = "";     //�˻�����
	private List noticeList     = null;   //���
	private List flist          = null;   //���ϸ�� ����Ʈ
	private int		fileseq		= 0;	//÷�����Ϲ�ȣ
	
	private String gbn          = "";     //�۵�����(D:����)

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		
		return null;
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
	}

	public String getGbn() {
		return gbn;
	}

	public void setGbn(String gbn) {
		this.gbn = gbn;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCrtdt() {
		return crtdt;
	}

	public void setCrtdt(String crtdt) {
		this.crtdt = crtdt;
	}

	public String getCrtusrnm() {
		return crtusrnm;
	}

	public void setCrtusrnm(String crtusrnm) {
		this.crtusrnm = crtusrnm;
	}

	public FormFile[] getFileList() {
		return fileList;
	}

	public void setFileList(FormFile[] fileList) {
		this.fileList = fileList;
	}

	public int getSeqno() {
		return seqno;
	}

	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getVisitno() {
		return visitno;
	}

	public void setVisitno(int visitno) {
		this.visitno = visitno;
	}

	public List getNoticeList() {
		return noticeList;
	}

	public void setNoticeList(List noticeList) {
		this.noticeList = noticeList;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getGubun() {
		return gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}

	public String getSearchval() {
		return searchval;
	}

	public void setSearchval(String searchval) {
		this.searchval = searchval;
	}

	public List getFlist() {
		return flist;
	}

	public void setFlist(List flist) {
		this.flist = flist;
	}

	public int getFileseq() {
		return fileseq;
	}

	public void setFileseq(int fileseq) {
		this.fileseq = fileseq;
	}		
}