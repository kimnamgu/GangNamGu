/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� �˻����� bean
 * ����:
 */
package nexti.ejms.sinchung.model;

public class SearchBean {
	private int reqformno    = 0;    //��û ��Ĺ�ȣ
	private String deptid    = "";   //�μ��ڵ�
	private String userid    = "";   //������ڵ�
	private String title     = "";   //����
	private String presentnm = ""; 	 //��û��
	private String presentid = ""; 	 //��û��ID
	private String syear     = "";   //�ۼ��⵵
	private String procFL    = "";   //ó������(0:��ó��, 1:��ü)
	private String gbn       = "";   //����
	private int start_idx    = 0;    //��ȸ ������ġ
	private int end_idx      = 0;    //��ȸ �� ��ġ
	private String	strdt	 = "";	//��ȸ ������
	private String	enddt	 = "";	//��ȸ ������
	private boolean unlimited	= true;	//���Ѿ���
	
	public String getPresentid() {
		return presentid;
	}
	public void setPresentid(String presentid) {
		this.presentid = presentid;
	}
	public String getEnddt() {
		return enddt;
	}
	public void setEnddt(String enddt) {
		this.enddt = enddt;
	}
	public String getStrdt() {
		return strdt;
	}
	public void setStrdt(String strdt) {
		this.strdt = strdt;
	}
	public boolean isUnlimited() {
		return unlimited;
	}
	public void setUnlimited(boolean unlimited) {
		this.unlimited = unlimited;
	}
	public int getReqformno() {
		return reqformno;
	}
	public void setReqformno(int reqformno) {
		this.reqformno = reqformno;
	}
	public String getPresentnm() {
		return presentnm;
	}
	public void setPresentnm(String presentnm) {
		this.presentnm = presentnm;
	}
	public String getSyear() {
		return syear;
	}
	public void setSyear(String syear) {
		this.syear = syear;
	}
	public String getGbn() {
		return gbn;
	}
	public void setGbn(String gbn) {
		this.gbn = gbn;
	}
	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getEnd_idx() {
		return end_idx;
	}
	public void setEnd_idx(int end_idx) {
		this.end_idx = end_idx;
	}
	public String getProcFL() {
		return procFL;
	}
	public void setProcFL(String procFL) {
		this.procFL = procFL;
	}
	public int getStart_idx() {
		return start_idx;
	}
	public void setStart_idx(int start_idx) {
		this.start_idx = start_idx;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}	
}
