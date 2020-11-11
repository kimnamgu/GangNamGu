/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ϱ� actionform
 * ����:
 */
package nexti.ejms.delivery.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class DeliveryForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private int seqno         	= 0;      	//����
	private int sysdocno 		= 0;   		//�ý��۹�����ȣ
	private String doctitle   	= "";     	//����
	private String deliverydt 	= "";     	//��������
	private String coldeptnm  	= "";     	//���պμ���Ī
	private String chrgusrnm  	= "";     	//���մ���ڸ�
	private String enddt      	= "";     	//�����Ͻ�
	private String remaintime 	= "";     	//�����ð�
	
	private int page          	= 0 ;     	//������
	private List deliList     	= null;   	//���� ���

	private String 	initentry		= "first";		//�ʱ����Կ���
	private String 	sch_old_userid	= "";			//�������ȸ����
	private String 	sch_usernm		= "";			//�������ȸ����
	private String 	sch_old_deptcd	= "";			//�μ�����ȸ����
	private String  sch_deptcd		= "";			//�μ���ȸ����
	private String 	sch_deptnm		= "";			//�μ�����ȸ����

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		
		return null;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
	}

	public String getSch_usernm() {
		return sch_usernm;
	}

	public void setSch_usernm(String sch_usernm) {
		this.sch_usernm = sch_usernm;
	}

	public String getSch_old_userid() {
		return sch_old_userid;
	}

	public void setSch_old_userid(String sch_old_userid) {
		this.sch_old_userid = sch_old_userid;
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

	public String getChrgusrnm() {
		return chrgusrnm;
	}

	public void setChrgusrnm(String chrgusrnm) {
		this.chrgusrnm = chrgusrnm;
	}

	public String getColdeptnm() {
		return coldeptnm;
	}

	public void setColdeptnm(String coldeptnm) {
		this.coldeptnm = coldeptnm;
	}

	public String getDeliverydt() {
		return deliverydt;
	}

	public void setDeliverydt(String deliverydt) {
		this.deliverydt = deliverydt;
	}

	public String getEnddt() {
		return enddt;
	}

	public void setEnddt(String enddt) {
		this.enddt = enddt;
	}

	public String getRemaintime() {
		return remaintime;
	}

	public void setRemaintime(String remaintime) {
		this.remaintime = remaintime;
	}

	public int getSeqno() {
		return seqno;
	}

	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}

	public String getDoctitle() {
		return doctitle;
	}

	public void setDoctitle(String doctitle) {
		this.doctitle = doctitle;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List getDeliList() {
		return deliList;
	}

	public void setDeliList(List deliList) {
		this.deliList = deliList;
	}
	
	public int getSysdocno() {
		return sysdocno;
	}
	
	public void setSysdocno(int sysdocno) {
		this.sysdocno = sysdocno;
	}
}