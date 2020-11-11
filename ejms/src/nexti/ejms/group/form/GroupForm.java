/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배포목록관리 actionform
 * 설명:
 */
package nexti.ejms.group.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class GroupForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private List mainlist = null;
	private List sublist = null;
	
	private String mode      	= "";     //mode
	private String gbn       	= "";     //구분 (s:시스템, a:관리자)
	private int grplistcd		= 0;     //목록코드
	private String grplistnm    = "";     //목록명칭
	private int ord          	= 0;      //정렬순서
	private int seq          	= 0;      //일련번호
	private String code 		= "";     //대상이름
	private String name		= "";     //대상명칭
	private String codegbn		= "";	  //대상구분
	private int posi         	= 0;      //주코드 스크롤 위치
	private int cposi        	= 0;      //부코드 스크롤 위치
	private String sch_grplist  = "";	  //조회조건배포목록마스터
	private String sch_orggbn	= "";	  //사용자구분( 001:광역시본청, 002:직속기관, 003:사업소, 004:시의회, 005:시/구/군, 006: 기타)
	
	public String getSch_orggbn() {
		return sch_orggbn;
	}

	public void setSch_orggbn(String sch_orggbn) {
		this.sch_orggbn = sch_orggbn;
	}

	public String getCodegbn() {
		return codegbn;
	}

	public void setCodegbn(String codegbn) {
		this.codegbn = codegbn;
	}

	public String getSch_grplist() {
		return sch_grplist;
	}

	public void setSch_grplist(String sch_grplist) {
		this.sch_grplist = sch_grplist;
	}

	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		return null;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
	}
	public List getSublist() {
		return sublist;
	}

	public void setSublist(List sublist) {
		this.sublist = sublist;
	}

	public List getMainlist() {
		return mainlist;
	}

	public void setMainlist(List mainlist) {
		this.mainlist = mainlist;
	}
	public String getGbn() {
		return gbn;
	}
	public void setGbn(String gbn) {
		this.gbn = gbn;
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

	public void setPosi(int posi) {
		this.posi = posi;
	}	

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public int getOrd() {
		return ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String deptcd) {
		this.code = deptcd;
	}

	public String getName() {
		return name;
	}

	public void setName(String deptnm) {
		this.name = deptnm;
	}

	public int getGrplistcd() {
		return grplistcd;
	}

	public void setGrplistcd(int grplistcd) {
		this.grplistcd = grplistcd;
	}

	public String getGrplistnm() {
		return grplistnm;
	}

	public void setGrplistnm(String grplistnm) {
		this.grplistnm = grplistnm;
	}		
}

