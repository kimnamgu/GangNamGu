/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������չ������� �˻� bean
 * ����:
 */
package nexti.ejms.commdocinfo.model;

public class CommCollDocSearchBean {
	private String	userid				= "";	//����ھ��̵�
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
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
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
	public String getSearchdept() {
		return searchdept;
	}
	public void setSearchdept(String searchdept) {
		this.searchdept = searchdept;
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
