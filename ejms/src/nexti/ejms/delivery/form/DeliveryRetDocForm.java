/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배부하기 반송처리 actionform
 * 설명:
 */
package nexti.ejms.delivery.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class DeliveryRetDocForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private int sysdocno 			= 0;	//시스템문서번호
	private String tgtdeptcd 		= "";	//제출부서코드
	private String submitstate		= "";	//제출상태
	private String returncomment	= "";	//반송사유

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
	 * @return the returncomment
	 */
	public String getReturncomment() {
		return returncomment;
	}

	/**
	 * @param returncomment the returncomment to set
	 */
	public void setReturncomment(String returncomment) {
		this.returncomment = returncomment;
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
	 * @return the tgtdeptcd
	 */
	public String getTgtdeptcd() {
		return tgtdeptcd;
	}

	/**
	 * @param tgtdeptcd the tgtdeptcd to set
	 */
	public void setTgtdeptcd(String tgtdeptcd) {
		this.tgtdeptcd = tgtdeptcd;
	}
}