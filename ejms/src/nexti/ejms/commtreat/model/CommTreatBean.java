/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ó����Ȳ bean
 * ����:
 */
package nexti.ejms.commtreat.model;

import java.util.List;

public class CommTreatBean {
	private int		level			= 0;
	private int		grpgbn			= 0;	
	private int		sysdocno		= 0;	//�ý��۹�����ȣ
	private String	sessionid		= "";	//���Ǿ��̵�
	private String	tgtdeptcd		= "";	//����μ��ڵ�
	private String	tgtdeptnm		= "";	//����μ���Ī
	private String	tgtdeptfullnm	= "";	//����μ���ü��Ī
	private String	submitstate		= "";	//�������
	private String	submitstatenm	= "";	//������¸�
	private String	modifyyn		= "";	//�������ɿ���(����1,����0)
	private String	returncomment	= "";	//�ݼۻ���
	private String	inusrsenddt		= "";	//�Է´�������Ͻ�
	private String	appntusrnm		= "";	//�Է´�������ڼ���
	private String	submitdt		= "";	//�����Ͻ�
	private String	predeptcd		= "";	//��������μ��ڵ�
	private String	inputusrid		= "";	//�Է´����ID
	private String	inputusrnm		= "";	//�Է´���ڼ���
	private int		chrgunitcd		= 0;	//�������ڵ�
	private String	chrgunitnm		= "";	//��������
	private String	inputstate		= "";	//�Է»���
	private String	inputstatenm	= "";	//�Է»��¸�
	private String	inputcomp		= "";	//�Է¿Ϸ�ó���Ͻ�

	private String	sancusrnm1		= "";	//�����ڸ�
	private String	sancusrnm2		= "";	//�����ڸ�
	private String	tcnt			= "";	//������
	private String	scnt			= "";	//����Ϸ�
	private String	fcnt			= "";	//������
	private String	docstate		= "";	//��������
	private String	docstatenm		= "";	//��������
	private String	enddt			= "";	//��������
	private String	inputdeptcd		= "";	//�Էºμ���
	private String	deliverydt		= "";	//����Ͻ�
	
	private List	inputuser1		= null;	//�Է´���� ���
	private List	inputuser2		= null;	//���Է´���� ���
	private List	sancList1		= null;	//������ ���
	private List	sancList2		= null;	//������ ���
	
	private String inputusersn		= "";	//�Է´�����ֹι�ȣ
	
	public String getInputusersn() {
		return inputusersn;
	}
	public void setInputusersn(String inputusersn) {
		this.inputusersn = inputusersn;
	}
	public int getGrpgbn() {
		return grpgbn;
	}
	public void setGrpgbn(int grpgbn) {
		this.grpgbn = grpgbn;
	}
	public String getTgtdeptfullnm() {
		return tgtdeptfullnm;
	}
	public void setTgtdeptfullnm(String tgtdeptfullnm) {
		this.tgtdeptfullnm = tgtdeptfullnm;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	public String getInputstatenm() {
		return inputstatenm;
	}
	public void setInputstatenm(String inputstatenm) {
		this.inputstatenm = inputstatenm;
	}
	public String getSubmitstatenm() {
		return submitstatenm;
	}
	public void setSubmitstatenm(String submitstatenm) {
		this.submitstatenm = submitstatenm;
	}
	public String getAppntusrnm() {
		return appntusrnm;
	}
	public void setAppntusrnm(String appntusrnm) {
		this.appntusrnm = appntusrnm;
	}
	public int getChrgunitcd() {
		return chrgunitcd;
	}
	public void setChrgunitcd(int chrgunitcd) {
		this.chrgunitcd = chrgunitcd;
	}
	public String getChrgunitnm() {
		return chrgunitnm;
	}
	public void setChrgunitnm(String chrgunitnm) {
		this.chrgunitnm = chrgunitnm;
	}
	public String getDeliverydt() {
		return deliverydt;
	}
	public void setDeliverydt(String deliverydt) {
		this.deliverydt = deliverydt;
	}
	public String getDocstate() {
		return docstate;
	}
	public void setDocstate(String docstate) {
		this.docstate = docstate;
	}
	public String getDocstatenm() {
		return docstatenm;
	}
	public void setDocstatenm(String docstatenm) {
		this.docstatenm = docstatenm;
	}
	public String getEnddt() {
		return enddt;
	}
	public void setEnddt(String enddt) {
		this.enddt = enddt;
	}
	public String getFcnt() {
		return fcnt;
	}
	public void setFcnt(String fcnt) {
		this.fcnt = fcnt;
	}
	public String getInputcomp() {
		return inputcomp;
	}
	public void setInputcomp(String inputcomp) {
		this.inputcomp = inputcomp;
	}
	public String getInputdeptcd() {
		return inputdeptcd;
	}
	public void setInputdeptcd(String inputdeptcd) {
		this.inputdeptcd = inputdeptcd;
	}
	public String getInputstate() {
		return inputstate;
	}
	public void setInputstate(String inputstate) {
		this.inputstate = inputstate;
	}
	public List getInputuser1() {
		return inputuser1;
	}
	public void setInputuser1(List inputuser1) {
		this.inputuser1 = inputuser1;
	}
	public List getInputuser2() {
		return inputuser2;
	}
	public void setInputuser2(List inputuser2) {
		this.inputuser2 = inputuser2;
	}
	public String getInputusrid() {
		return inputusrid;
	}
	public void setInputusrid(String inputusrid) {
		this.inputusrid = inputusrid;
	}
	public String getInputusrnm() {
		return inputusrnm;
	}
	public void setInputusrnm(String inputusrnm) {
		this.inputusrnm = inputusrnm;
	}
	public String getInusrsenddt() {
		return inusrsenddt;
	}
	public void setInusrsenddt(String inusrsenddt) {
		this.inusrsenddt = inusrsenddt;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getModifyyn() {
		return modifyyn;
	}
	public void setModifyyn(String modifyyn) {
		this.modifyyn = modifyyn;
	}
	public String getPredeptcd() {
		return predeptcd;
	}
	public void setPredeptcd(String predeptcd) {
		this.predeptcd = predeptcd;
	}
	public String getReturncomment() {
		return returncomment;
	}
	public void setReturncomment(String returncomment) {
		this.returncomment = returncomment;
	}
	public List getSancList1() {
		return sancList1;
	}
	public void setSancList1(List sancList1) {
		this.sancList1 = sancList1;
	}
	public List getSancList2() {
		return sancList2;
	}
	public void setSancList2(List sancList2) {
		this.sancList2 = sancList2;
	}
	public String getSancusrnm1() {
		return sancusrnm1;
	}
	public void setSancusrnm1(String sancusrnm1) {
		this.sancusrnm1 = sancusrnm1;
	}
	public String getSancusrnm2() {
		return sancusrnm2;
	}
	public void setSancusrnm2(String sancusrnm2) {
		this.sancusrnm2 = sancusrnm2;
	}
	public String getScnt() {
		return scnt;
	}
	public void setScnt(String scnt) {
		this.scnt = scnt;
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
	public int getSysdocno() {
		return sysdocno;
	}
	public void setSysdocno(int sysdocno) {
		this.sysdocno = sysdocno;
	}
	public String getTcnt() {
		return tcnt;
	}
	public void setTcnt(String tcnt) {
		this.tcnt = tcnt;
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