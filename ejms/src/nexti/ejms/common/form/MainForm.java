/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 메인화면 actionform
 * 설명:
 */
package nexti.ejms.common.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class MainForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private List noticeList     = null;    //공지사항 목록
	private List recentList     = null;    //최근 취합목록
	private List rchList		= null;    //설문하기목록
	private List sinchungList   = null;    //신청서 목록
	private int input_ready     = 0;       //입력대기
	private int input_sanc_proc = 0;       //입력부서  결재진행
	private int delivery_ready  = 0;       //배부대기
	private int coll_sanc_proc  = 0;       //취합부서 결재진행
	private int coll_proc       = 0;       //취합부서 취합진행
	private int close_ready     = 0;       //마감대기
	private int no_sanc         = 0;       //미결재
	private int yes_sanc        = 0;       //결재진행	
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		
		return null;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
	
	}

	public List getSinchungList() {
		return sinchungList;
	}

	public void setSinchungList(List sinchungList) {
		this.sinchungList = sinchungList;
	}

	public int getClose_ready() {
		return close_ready;
	}

	public void setClose_ready(int close_ready) {
		this.close_ready = close_ready;
	}

	public int getColl_proc() {
		return coll_proc;
	}

	public void setColl_proc(int coll_proc) {
		this.coll_proc = coll_proc;
	}

	public int getColl_sanc_proc() {
		return coll_sanc_proc;
	}

	public void setColl_sanc_proc(int coll_sanc_proc) {
		this.coll_sanc_proc = coll_sanc_proc;
	}

	public int getDelivery_ready() {
		return delivery_ready;
	}

	public void setDelivery_ready(int delivery_ready) {
		this.delivery_ready = delivery_ready;
	}

	public int getInput_ready() {
		return input_ready;
	}

	public void setInput_ready(int input_ready) {
		this.input_ready = input_ready;
	}

	public int getInput_sanc_proc() {
		return input_sanc_proc;
	}

	public void setInput_sanc_proc(int input_sanc_proc) {
		this.input_sanc_proc = input_sanc_proc;
	}

	public int getNo_sanc() {
		return no_sanc;
	}

	public void setNo_sanc(int no_sanc) {
		this.no_sanc = no_sanc;
	}

	public List getNoticeList() {
		return noticeList;
	}

	public void setNoticeList(List noticeList) {
		this.noticeList = noticeList;
	}

	public List getRecentList() {
		return recentList;
	}

	public void setRecentList(List recentList) {
		this.recentList = recentList;
	}

	public int getYes_sanc() {
		return yes_sanc;
	}

	public void setYes_sanc(int yes_sanc) {
		this.yes_sanc = yes_sanc;
	}

	public List getRchList() {
		return rchList;
	}

	public void setRchList(List rchList) {
		this.rchList = rchList;
	}	
}