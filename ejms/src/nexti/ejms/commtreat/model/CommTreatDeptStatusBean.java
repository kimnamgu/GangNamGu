/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ó����Ȳ ������� bean
 * ����:
 */
package nexti.ejms.commtreat.model;

public class CommTreatDeptStatusBean {
	
	private String  tgtdeptcd		= "";   //�μ��ڵ�
	private String  tgtdeptnm		= "";   //�μ���
	private String	tgtdepttel		= "";	//�μ���ȭ��ȣ
	private String	submityn		= "";	//���⿩��
	private String	submitdt		= "";	//�����Ͻ�
	private String	submitstate		= "";	//��������ڵ�
	private String	submitstatenm	= "";	//������¸�
	private String	returncomment	= "";	//�ݼۻ���
	private String	modifyyn		= "0";	//��������
	
	public String getTgtdepttel() {
		return tgtdepttel;
	}
	public void setTgtdepttel(String tgtdepttel) {
		this.tgtdepttel = tgtdepttel;
	}
	public String getModifyyn() {
		return modifyyn;
	}
	public void setModifyyn(String modifyyn) {
		if (modifyyn==null || "".equals(modifyyn)) {
			modifyyn = "0";
		}
		this.modifyyn = modifyyn;
	}
	public String getReturncomment() {
		return returncomment;
	}
	public void setReturncomment(String returncomment) {
		this.returncomment = returncomment;
	}
	public String getSubmitdt() {
		return submitdt;
	}
	public void setSubmitdt(String submitdt) {
		this.submitdt = submitdt;
	}
	public String getSubmitstate() {
		return submitstate;
	}
	public void setSubmitstate(String submitstate) {
		this.submitstate = submitstate;
	}
	public String getSubmitstatenm() {
		return submitstatenm;
	}
	public void setSubmitstatenm(String submitstatenm) {
		this.submitstatenm = submitstatenm;
	}
	public String getSubmityn() {
		return submityn;
	}
	public void setSubmityn(String submityn) {
		this.submityn = submityn;
	}
	public String getTgtdeptcd() {
		return tgtdeptcd;
	}
	public void setTgtdeptcd(String tgtdeptcd) {
		this.tgtdeptcd = tgtdeptcd;
	}
	public String getTgtdeptnm() {
		return tgtdeptnm;
	}
	public void setTgtdeptnm(String tgtdeptnm) {
		this.tgtdeptnm = tgtdeptnm;
	}

}