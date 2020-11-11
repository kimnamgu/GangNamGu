/**
 * 작성일: 2010.08.26
 * 작성자: 대리 서동찬
 * 모듈명: 메세지 연계 bean(Active Post)
 * 설명: 
 */
package nexti.ejms.messanger;

import java.util.List;

public class MessangerRelayBean {
	private int relayMode			= -1;
	private int sysdocno			= 0;
	private int formseq				= 0;
	private String formkind			= "";
	private String deptcode			= "";
	private List list				= null;
	
	private String	doc_name_title		= "";
	private String	doc_desc_content	= "";
	private String	doc_url_link		= "";
	private String	snd_user_usersn		= "";
	private String	rcv_user_usersn		= "";
	
	public String getDeptcode() {
		return deptcode;
	}
	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public int getRelayMode() {
		return relayMode;
	}
	public void setRelayMode(int relayMode) {
		this.relayMode = relayMode;
	}
	public int getSysdocno() {
		return sysdocno;
	}
	public void setSysdocno(int sysdocno) {
		this.sysdocno = sysdocno;
	}
	public int getFormseq() {
		return formseq;
	}
	public void setFormseq(int formseq) {
		this.formseq = formseq;
	}
	public String getDoc_name_title() {
		return doc_name_title;
	}
	public void setDoc_name_title(String doc_name_title) {
		this.doc_name_title = doc_name_title;
	}
	public String getFormkind() {
		return formkind;
	}
	public void setFormkind(String formkind) {
		this.formkind = formkind;
	}
	public String getDoc_url_link() {
		return doc_url_link;
	}
	public void setDoc_url_link(String doc_url_link) {
		this.doc_url_link = doc_url_link;
	}
	public String getRcv_user_usersn() {
		return rcv_user_usersn;
	}
	public void setRcv_user_usersn(String rcv_user_usersn) {
		this.rcv_user_usersn = rcv_user_usersn;
	}
	public String getSnd_user_usersn() {
		return snd_user_usersn;
	}
	public void setSnd_user_usersn(String snd_user_usersn) {
		this.snd_user_usersn = snd_user_usersn;
	}
	public String getDoc_desc_content() {
		return doc_desc_content;
	}
	public void setDoc_desc_content(String doc_desc_content) {
		this.doc_desc_content = doc_desc_content;
	}	
}