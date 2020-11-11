/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Է¿Ϸ� actionform
 * ����:
 */
package nexti.ejms.inputing.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class InputCompleteForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private int seqno				= 0;			//����
	private int sysdocno			= 0;			//�ý��۹�����ȣ
	private String doctitle			= "";			//��������
	private String submistate		= "";			//�������
	private String inusrenddt		= "";			//�Է¿Ϸ��Ͻ�
	private String inputendreason	= "";			//�Է¿Ϸᱸ��
	private String coldeptnm		= "";   		//���պμ���Ī
	private String chrgusrnm		= "";   		//���մ���ڸ�
	private String deliverydt		= "";   		//�������
	private String inputusrid		= "";			//�Է´����ID
	
	private String searchvalue		= "";			//�˻���
	private String selyear			= "";			//��������
	private String initentry		= "first";		//�ʱ����Կ���
	private int page          		= 0 ;  			//������
	private List completeList     	= null;			//�Է� ���

	private String 	sch_old_deptcd	= "";			//�μ�����ȸ����
	private String  sch_deptcd		= "";			//�μ�����ȸ����
	private String 	sch_deptnm		= "";			//�μ�����ȸ����
	private String  sch_old_userid	= "";			//�������ȸ����
	private String  sch_userid		= "";			//�������ȸ����
	private String 	sch_usernm		= "";			//�������ȸ����

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

	public String getInputusrid() {
		return inputusrid;
	}

	public void setInputusrid(String inputusrid) {
		this.inputusrid = inputusrid;
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

	public String getSch_userid() {
		return sch_userid;
	}

	public void setSch_userid(String sch_userid) {
		this.sch_userid = sch_userid;
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
	 * @return the completeList
	 */
	public List getCompleteList() {
		return completeList;
	}

	/**
	 * @param completeList the completeList to set
	 */
	public void setCompleteList(List completeList) {
		this.completeList = completeList;
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
	 * @return the inputendreason
	 */
	public String getInputendreason() {
		return inputendreason;
	}

	/**
	 * @param inputendreason the inputendreason to set
	 */
	public void setInputendreason(String inputendreason) {
		this.inputendreason = inputendreason;
	}

	/**
	 * @return the inusrenddt
	 */
	public String getInusrenddt() {
		return inusrenddt;
	}

	/**
	 * @param inusrenddt the inusrenddt to set
	 */
	public void setInusrenddt(String inusrenddt) {
		this.inusrenddt = inusrenddt;
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
	 * @return the submistate
	 */
	public String getSubmistate() {
		return submistate;
	}

	/**
	 * @param submistate the submistate to set
	 */
	public void setSubmistate(String submistate) {
		this.submistate = submistate;
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

	/**
	 * @return the searchvalue
	 */
	public String getSearchvalue() {
		return searchvalue;
	}

	/**
	 * @param searchvalue the searchvalue to set
	 */
	public void setSearchvalue(String searchvalue) {
		this.searchvalue = searchvalue;
	}

	/**
	 * @return the selyear
	 */
	public String getSelyear() {
		return selyear;
	}

	/**
	 * @param selyear the selyear to set
	 */
	public void setSelyear(String selyear) {
		this.selyear = selyear;
	}

	/**
	 * @return the initentry
	 */
	public String getInitentry() {
		return initentry;
	}

	/**
	 * @param initentry the initentry to set
	 */
	public void setInitentry(String initentry) {
		this.initentry = initentry;
	}
}