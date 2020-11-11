/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��οϷ� actionform
 * ����:
 */
package nexti.ejms.delivery.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class DeliveryCompForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private int seqno         	= 0;      	//����
	private int sysdocno 		= 0;   		//�ý��۹�����ȣ
	private String doctitle   	= "";     	//����
	private String submitstate	= "";		//�������
	private String deliverydt 	= "";     	//�������
	private String coldeptnm  	= "";     	//���պμ���Ī
	private String chrgusrnm  	= "";     	//���մ���ڸ�
	private String enddt      	= "";     	//�����Ͻ�
	private String remaintime 	= "";     	//�����ð�
	private String inputperdeli = "";		//�Է�/���
	
	private int page          	= 0 ;     	//������
	private List deliCompList  = null;   	//���� ���

	private String 	initentry		= "first";		//�ʱ����Կ���
	private String 	sch_old_userid	= "";			//�������ȸ����
	private String 	sch_usernm		= "";			//�������ȸ����
	private String 	sch_old_deptcd	= "";			//�μ�����ȸ����
	private String  sch_deptcd		= "";			//�μ���ȸ����
	private String 	sch_deptnm		= "";			//�μ�����ȸ����

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

	public String getInitentry() {
		return initentry;
	}

	public void setInitentry(String initentry) {
		this.initentry = initentry;
	}

	public String getSch_deptcd() {
		return sch_deptcd;
	}

	public void setSch_deptcd(String sch_deptcd) {
		this.sch_deptcd = sch_deptcd;
	}

	public String getSch_deptnm() {
		return sch_deptnm;
	}

	public void setSch_deptnm(String sch_deptnm) {
		this.sch_deptnm = sch_deptnm;
	}

	public String getSch_old_deptcd() {
		return sch_old_deptcd;
	}

	public void setSch_old_deptcd(String sch_old_deptcd) {
		this.sch_old_deptcd = sch_old_deptcd;
	}

	public String getSch_old_userid() {
		return sch_old_userid;
	}

	public void setSch_old_userid(String sch_old_userid) {
		this.sch_old_userid = sch_old_userid;
	}

	public String getSch_usernm() {
		return sch_usernm;
	}

	public void setSch_usernm(String sch_usernm) {
		this.sch_usernm = sch_usernm;
	}

	/**
	 * @return the chrgusrnm
	 */
	public String getChrgusrnm() {
		return chrgusrnm;
	}

	/**
	 * @param chrgusrnm the chrgusrnm to set
	 */
	public void setChrgusrnm(String chrgusrnm) {
		this.chrgusrnm = chrgusrnm;
	}

	/**
	 * @return the coldeptnm
	 */
	public String getColdeptnm() {
		return coldeptnm;
	}

	/**
	 * @param coldeptnm the coldeptnm to set
	 */
	public void setColdeptnm(String coldeptnm) {
		this.coldeptnm = coldeptnm;
	}

	/**
	 * @return the deliComList
	 */
	public List getDeliCompList() {
		return deliCompList;
	}

	/**
	 * @param deliComList the deliComList to set
	 */
	public void setDeliCompList(List deliCompList) {
		this.deliCompList = deliCompList;
	}

	/**
	 * @return the deliverydt
	 */
	public String getDeliverydt() {
		return deliverydt;
	}

	/**
	 * @param deliverydt the deliverydt to set
	 */
	public void setDeliverydt(String deliverydt) {
		this.deliverydt = deliverydt;
	}
	
	/**
	 * @return the submitstate
	 */
	public String getSubmitstate() {
		return submitstate;
	}

	/**
	 * @param submitstate the submitstate to set
	 */
	public void setSubmitstate(String submitstate) {
		this.submitstate = submitstate;
	}

	/**
	 * @return the doctitle
	 */
	public String getDoctitle() {
		return doctitle;
	}

	/**
	 * @param doctitle the doctitle to set
	 */
	public void setDoctitle(String doctitle) {
		this.doctitle = doctitle;
	}

	/**
	 * @return the enddt
	 */
	public String getEnddt() {
		return enddt;
	}

	/**
	 * @param enddt the enddt to set
	 */
	public void setEnddt(String enddt) {
		this.enddt = enddt;
	}

	/**
	 * @return the inputperdeli
	 */
	public String getInputperdeli() {
		return inputperdeli;
	}

	/**
	 * @param inputperdeli the inputperdeli to set
	 */
	public void setInputperdeli(String inputperdeli) {
		this.inputperdeli = inputperdeli;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the remaintime
	 */
	public String getRemaintime() {
		return remaintime;
	}

	/**
	 * @param remaintime the remaintime to set
	 */
	public void setRemaintime(String remaintime) {
		this.remaintime = remaintime;
	}

	/**
	 * @return the seqno
	 */
	public int getSeqno() {
		return seqno;
	}

	/**
	 * @param seqno the seqno to set
	 */
	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}

	/**
	 * @return the sysdocno
	 */
	public int getSysdocno() {
		return sysdocno;
	}

	/**
	 * @param sysdocno the sysdocno to set
	 */
	public void setSysdocno(int sysdocno) {
		this.sysdocno = sysdocno;
	}
	
	
}