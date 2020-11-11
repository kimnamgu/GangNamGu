/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ڷ���� ��� actionform
 * ����:
 */
package nexti.ejms.exhibit.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ExhibitListForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;

	private int 	page				= 1 ;	//������
	private String	check_detail		= "";	//������
	private int  	treescroll			= 0;	//������ ��ũ��
	
	private String	searchdept			= "";	//���úμ����� ��ü����
	private String 	searchdoctitle		= "";	//��������
	private String 	searchformtitle		= "";	//�������
	private String 	searchkey			= "";	//�˻���
	private String 	searchcrtdtfr		= "";	//�����ۼ���from
	private String 	searchcrtdtto		= "";	//�����ۼ���to
	private String 	searchbasicdatefr	= "";	//�ڷ������from
	private String 	searchbasicdateto	= "";	//�ڷ������to
	private String 	searchchrgusrnm		= "";	//���մ����
	private String 	searchinputusrnm	= "";	//�Է´����
	
	private List	doclist;					//�������
	private String 	orggbn				= "";	//��������
	
	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		return null;
	}

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
	}

	public String getOrggbn() {
		return orggbn;
	}

	public void setOrggbn(String orggbn) {
		this.orggbn = orggbn;
	}

	public int getTreescroll() {
		return treescroll;
	}

	public void setTreescroll(int treescroll) {
		this.treescroll = treescroll;
	}

	public List getDoclist() {
		return doclist;
	}

	public void setDoclist(List doclist) {
		this.doclist = doclist;
	}

	public String getSearchdept() {
		return searchdept;
	}

	public void setSearchdept(String searchdept) {
		this.searchdept = searchdept;
	}

	public String getCheck_detail() {
		return check_detail;
	}

	public void setCheck_detail(String check_detail) {
		this.check_detail = check_detail;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getSearchbasicdatefr() {
		return searchbasicdatefr;
	}

	public void setSearchbasicdatefr(String searchbasicdatefr) {
		this.searchbasicdatefr = searchbasicdatefr;
	}

	public String getSearchbasicdateto() {
		return searchbasicdateto;
	}

	public void setSearchbasicdateto(String searchbasicdateto) {
		this.searchbasicdateto = searchbasicdateto;
	}

	public String getSearchchrgusrnm() {
		return searchchrgusrnm;
	}

	public void setSearchchrgusrnm(String searchchrgusrnm) {
		this.searchchrgusrnm = searchchrgusrnm;
	}

	public String getSearchcrtdtfr() {
		return searchcrtdtfr;
	}

	public void setSearchcrtdtfr(String searchcrtdtfr) {
		this.searchcrtdtfr = searchcrtdtfr;
	}

	public String getSearchcrtdtto() {
		return searchcrtdtto;
	}

	public void setSearchcrtdtto(String searchcrtdtto) {
		this.searchcrtdtto = searchcrtdtto;
	}

	public String getSearchdoctitle() {
		return searchdoctitle;
	}

	public void setSearchdoctitle(String searchdoctitle) {
		this.searchdoctitle = searchdoctitle;
	}

	public String getSearchformtitle() {
		return searchformtitle;
	}

	public void setSearchformtitle(String searchformtitle) {
		this.searchformtitle = searchformtitle;
	}

	public String getSearchinputusrnm() {
		return searchinputusrnm;
	}

	public void setSearchinputusrnm(String searchinputusrnm) {
		this.searchinputusrnm = searchinputusrnm;
	}

	public String getSearchkey() {
		return searchkey;
	}

	public void setSearchkey(String searchkey) {
		this.searchkey = searchkey;
	}
	
}