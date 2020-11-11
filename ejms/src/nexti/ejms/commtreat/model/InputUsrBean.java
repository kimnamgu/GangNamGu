/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 입력담당자 담당단위 변경 bean
 * 설명:
 */
package nexti.ejms.commtreat.model;

public class InputUsrBean {
	private String chrgunitnm  = "";    //담당단위 명칭
	private String inputusrnm  = "";    //입력담당자명
	private String chrusrnm    = "";    //담당단위 : 입력담당자명,...
	
	public String getChrusrnm() {
		return chrusrnm;
	}
	public void setChrusrnm(String chrusrnm) {
		this.chrusrnm = chrusrnm;
	}
	public String getChrgunitnm() {
		return chrgunitnm;
	}	
	public void setChrgunitnm(String chrgunitnm) {
		this.chrgunitnm = chrgunitnm;
	}
	public String getInputusrnm() {
		return inputusrnm;
	}
	public void setInputusrnm(String inputusrnm) {
		this.inputusrnm = inputusrnm;
	}	
}
