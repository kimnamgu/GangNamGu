/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합업무사용현황 actionform
 * 설명:
 */
package nexti.ejms.statistics.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CollsttcsForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String 	orggbn			= "";		//구분
	private String 	orggbn_dt		= "";		//구분-세부
	private int		gbn				= 0;	
	private String	search_frdate	= null;
	private String	search_todate	= null;
	private List	list			= null;   //목록
	private long	totcount		= 0;

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		
		return null;
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
	}

	public int getGbn() {
		return gbn;
	}

	public void setGbn(int gbn) {
		this.gbn = gbn;
	}

	public long getTotcount() {
		return totcount;
	}

	public void setTotcount(long totcount) {
		this.totcount = totcount;
	}

	public String getSearch_frdate() {
		return search_frdate;
	}

	public void setSearch_frdate(String search_frdate) {
		this.search_frdate = search_frdate;
	}

	public String getSearch_todate() {
		return search_todate;
	}

	public void setSearch_todate(String search_todate) {
		this.search_todate = search_todate;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	/**
	 * @return the orggbn
	 */
	public String getOrggbn() {
		return orggbn;
	}

	/**
	 * @param orggbn the orggbn to set
	 */
	public void setOrggbn(String orggbn) {
		this.orggbn = orggbn;
	}

	/**
	 * @return the orggbn_dt
	 */
	public String getOrggbn_dt() {
		return orggbn_dt;
	}

	/**
	 * @param orggbn_dt the orggbn_dt to set
	 */
	public void setOrggbn_dt(String orggbn_dt) {
		this.orggbn_dt = orggbn_dt;
	}

}