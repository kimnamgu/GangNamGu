/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ϱ� �ݼ�ó�� �ݼ����� actionform
 * ����:
 */
package nexti.ejms.delivery.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class DelveryRetDocViewForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private String doctitle			= "";	//��������
	private String coldeptnm		= "";	//���պμ���
	private String chrgunitnm		= "";	//���մ�������
	private String chrgusrnm		= "";	//���մ���ڸ�
	private String coldeptcd		= "";	//���պμ��ڵ�
	private String coldepttel		= "";	//���պμ���ȭ��ȣ	

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

	/**
	 * @return the chrgunitnm
	 */
	public String getChrgunitnm() {
		return chrgunitnm;
	}

	/**
	 * @param chrgunitnm the chrgunitnm to set
	 */
	public void setChrgunitnm(String chrgunitnm) {
		this.chrgunitnm = chrgunitnm;
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
	 * @return the coldeptcd
	 */
	public String getColdeptcd() {
		return coldeptcd;
	}

	/**
	 * @param coldeptcd the coldeptcd to set
	 */
	public void setColdeptcd(String coldeptcd) {
		this.coldeptcd = coldeptcd;
	}

	/**
	 * @return the coldepttel
	 */
	public String getColdepttel() {
		return coldepttel;
	}

	/**
	 * @param coldepttel the coldepttel to set
	 */
	public void setColdepttel(String coldepttel) {
		this.coldepttel = coldepttel;
	}
}