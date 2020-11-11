/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 담당단위관리 actionform
 * 설명:
 */
package nexti.ejms.chrgUnit.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ChrgUnitForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private List deptList = null;     //부서목록
	private List chrgList = null;     //담당단위 목록
	
	private String mode = "";         //mode
	private String dept_id = "";      //부서코드
	private String dept_name = "";    //부서명칭
	private String user_id   = "";    //사용자아이디
	private String user_name = "";	  //사용자명
	private int chrgunitcd = 0;       //담당단위 코드
	private String chrgunitnm = "";   //담당단위 명칭
	private int ord  = 0;             //정렬순서

	private int posi = 0;             //주코드 스크롤 위치
	private int cposi = 0;            //부코드 스크롤 위치
	private String orggbn = "";		  // 구분 ( 1:광역시본청, 2:직속기관, 3:사업소, 4:시의회, 5:시/구/군, 6: 기타)

	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		return null;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
	}
	
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public int getOrd() {
		return ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}

	public List getChrgList() {
		return chrgList;
	}

	public void setChrgList(List chrgList) {
		this.chrgList = chrgList;
	}

	public List getDeptList() {
		return deptList;
	}

	public void setDeptList(List deptList) {
		this.deptList = deptList;
	}	

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public int getCposi() {
		return cposi;
	}

	public void setCposi(int cposi) {
		this.cposi = cposi;
	}

	public int getPosi() {
		return posi;
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

	public void setPosi(int posi) {
		this.posi = posi;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getOrggbn() {
		return orggbn;
	}

	public void setOrggbn(String orggbn) {
		this.orggbn = orggbn;
	}
}

