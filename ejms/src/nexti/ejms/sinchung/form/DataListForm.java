/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 신청내용 목록 actionform
 * 설명:
 */
package nexti.ejms.sinchung.form;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class DataListForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private int reqformno    = 0;        //신청양식번호	
	private String presentnm = "";       //신청자
	private String procGbn   = "1";    	 //미처리 여부(0:미처리 , 1:전체)
	
	private String title     = "";       //제목
	private String coldeptnm = "";       //담당부서명
	private String coltel    = "";       //부서전화번호	
	private String chrgusrnm = "";       //담당자명
	private String strdt     = "";       //시작일
	private String enddt     = "";       //종료일
	private String gbn       = "1";      //신청진행(0:신청중, 1:전체), 미처리 여부(0:미처리 , 1:전체)
	private int page         = 1 ;       //페이지
		
	private List dataList = null;        //신청 리스트

	public String getPresentnm() {
		return presentnm;
	}

	public void setPresentnm(String presentnm) {
		this.presentnm = presentnm;
	}

	public String getProcGbn() {
		return procGbn;
	}

	public void setProcGbn(String procGbn) {
		this.procGbn = procGbn;
	}

	public int getReqformno() {
		return reqformno;
	}

	public void setReqformno(int reqformno) {
		this.reqformno = reqformno;
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

	public String getColtel() {
		return coltel;
	}

	public void setColtel(String coltel) {
		this.coltel = coltel;
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

	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	public String getGbn() {
		return gbn;
	}

	public void setGbn(String gbn) {
		this.gbn = gbn;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}