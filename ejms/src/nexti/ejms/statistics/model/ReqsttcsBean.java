/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�������Ȳ bean
 * ����:
 */
package nexti.ejms.statistics.model;

public class ReqsttcsBean {
	
	private int seqno 				= 0;			//����
	private String deptnm			= "";			//�μ���
	private long reqcount			= 0;			//��û���
	private long anscount			= 0;			//�亯�Ǽ�
	private String usernm			= "";
	private String title			= "";
	private String summary			= "";
	private String strdt			= "";
	private String enddt			= "";
	
	public String getUsernm() {
		return usernm;
	}
	public void setUsernm(String usernm) {
		this.usernm = usernm;
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
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDeptnm() {
		return deptnm;
	}
	public void setDeptnm(String deptnm) {
		this.deptnm = deptnm;
	}
	public int getSeqno() {
		return seqno;
	}
	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}
	public long getAnscount() {
		return anscount;
	}
	public void setAnscount(long anscount) {
		this.anscount = anscount;
	}
	public long getReqcount() {
		return reqcount;
	}
	public void setReqcount(long reqcount) {
		this.reqcount = reqcount;
	}

}