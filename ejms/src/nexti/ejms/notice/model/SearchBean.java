/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� �˻����� bean
 * ����:
 */
package nexti.ejms.notice.model;

public class SearchBean {
	private String gubun       = "";   //����   
	private String  searchval  = "";   //�˻�����
	private int startidx       = 0;    //������ġ
	private int endidx         = 0;    //��������ġ	
	
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public int getEndidx() {
		return endidx;
	}
	public void setEndidx(int endidx) {
		this.endidx = endidx;
	}
	public String getSearchval() {
		return searchval;
	}
	public void setSearchval(String searchval) {
		this.searchval = searchval;
	}
	public int getStartidx() {
		return startidx;
	}
	public void setStartidx(int startidx) {
		this.startidx = startidx;
	}		
}
