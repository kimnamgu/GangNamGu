/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �����ϱ� �˻����� bean
 * ����:
 */
package nexti.ejms.approval.model;

public class SearchBean {
	private String docGbn      	= "";   //��������
	private String user_id      = "";  	//������ ID	
	private String deptcd		= "";	//�μ��ڵ�
	private int startidx       	= 0;    //������ġ
	private int endidx         	= 0;    //��������ġ	
		
	public String getDocGbn() {
		return docGbn;
	}
	public String getDeptcd() {
		return deptcd;
	}
	public void setDeptcd(String deptcd) {
		this.deptcd = deptcd;
	}
	public void setDocGbn(String docGbn) {
		this.docGbn = docGbn;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getEndidx() {
		return endidx;
	}
	public void setEndidx(int endidx) {
		this.endidx = endidx;
	}
	public int getStartidx() {
		return startidx;
	}
	public void setStartidx(int startidx) {
		this.startidx = startidx;
	}		
}
