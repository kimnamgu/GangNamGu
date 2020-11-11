/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 시스템에이전트 actionform
 * 설명:
 */
package nexti.ejms.agent.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class SystemAgentForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	//디테일
	private String p_id = "";
	private String p_nm = "";
	private int p_seq = 0;		        //순번
	private String p_type = "";		    //타입
	private String p_typenm = "";		//타입명
	private String p_t1 = "";			//타입1 매일
	private String p_t1_1 = "";			//타입1 매일(시)
	private String p_t1_2 = "";			//타입1 매일(분)
	private String p_t2 = "";			//타입2 매주
	private String p_t2_1 = "";			//타입2 매주(요일)
	private String p_t2_2 = "";			//타입2 매주(시)
	private String p_t2_3 = "";			//타입2 매주(분)
	private String p_t3 = "";			//타입3 매월
	private String p_t3_1 = "";			//타입3 매월(일)
	private String p_t3_2 = "";			//타입3 매월(시)
	private String p_t3_3 = "";			//타입3 매월(분)
	private String p_t4 = "";			//타입4 매년
	private String p_t4_1 = "";			//타입4 매년(월)
	private String p_t4_2 = "";			//타입4 매년(일)
	private String p_t4_3 = "";			//타입4 매년(시_
	private String p_t4_4 = "";			//타입4 매년(분)
	private String p_t5 = "";			//사용안함
	private String p_t6 = "";			//사용안함
	private String p_use = "";		    //사용여부
	private String db_pass = "";

	private List saLists = null;	    //에이전트리스트

	private int page = 0;               //현재 페이지
	
	private String p_ctr = "";		    //control(실행:001, 중지:000)

	private String mode = "";		    //쓰기, 수정 구분(수정 : mode=m)

	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {
		
		return null;
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
	}

	public String getDb_pass() {
		return db_pass;
	}

	public void setDb_pass(String db_pass) {
		this.db_pass = db_pass;
	}

	public String getP_ctr() {
		return p_ctr;
	}

	public void setP_ctr(String p_ctr) {
		this.p_ctr = p_ctr;
	}

	public String getP_id() {
		return p_id;
	}

	public void setP_id(String p_id) {
		this.p_id = p_id;
	}

	public String getP_nm() {
		return p_nm;
	}

	public void setP_nm(String p_nm) {
		this.p_nm = p_nm;
	}

	public int getP_seq() {
		return p_seq;
	}

	public void setP_seq(int p_seq) {
		this.p_seq = p_seq;
	}

	public String getP_t1() {
		return p_t1;
	}

	public void setP_t1(String p_t1) {
		this.p_t1 = p_t1;
	}

	public String getP_t2() {
		return p_t2;
	}

	public void setP_t2(String p_t2) {
		this.p_t2 = p_t2;
	}

	public String getP_t3() {
		return p_t3;
	}

	public void setP_t3(String p_t3) {
		this.p_t3 = p_t3;
	}

	public String getP_t4() {
		return p_t4;
	}

	public void setP_t4(String p_t4) {
		this.p_t4 = p_t4;
	}

	public String getP_t5() {
		return p_t5;
	}

	public void setP_t5(String p_t5) {
		this.p_t5 = p_t5;
	}

	public String getP_t6() {
		return p_t6;
	}

	public void setP_t6(String p_t6) {
		this.p_t6 = p_t6;
	}

	public String getP_type() {
		return p_type;
	}

	public void setP_type(String p_type) {
		this.p_type = p_type;
	}

	public String getP_use() {
		return p_use;
	}

	public void setP_use(String p_use) {
		this.p_use = p_use;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List getSaLists() {
		return saLists;
	}

	public void setSaLists(List saLists) {
		this.saLists = saLists;
	}

	public String getP_t1_1() {
		return p_t1_1;
	}

	public void setP_t1_1(String p_t1_1) {
		this.p_t1_1 = p_t1_1;
	}

	public String getP_t1_2() {
		return p_t1_2;
	}

	public void setP_t1_2(String p_t1_2) {
		this.p_t1_2 = p_t1_2;
	}

	public String getP_t2_1() {
		return p_t2_1;
	}

	public void setP_t2_1(String p_t2_1) {
		this.p_t2_1 = p_t2_1;
	}

	public String getP_t2_2() {
		return p_t2_2;
	}

	public void setP_t2_2(String p_t2_2) {
		this.p_t2_2 = p_t2_2;
	}

	public String getP_t2_3() {
		return p_t2_3;
	}

	public void setP_t2_3(String p_t2_3) {
		this.p_t2_3 = p_t2_3;
	}

	public String getP_t3_1() {
		return p_t3_1;
	}

	public void setP_t3_1(String p_t3_1) {
		this.p_t3_1 = p_t3_1;
	}

	public String getP_t3_2() {
		return p_t3_2;
	}

	public void setP_t3_2(String p_t3_2) {
		this.p_t3_2 = p_t3_2;
	}

	public String getP_t3_3() {
		return p_t3_3;
	}

	public void setP_t3_3(String p_t3_3) {
		this.p_t3_3 = p_t3_3;
	}

	public String getP_t4_1() {
		return p_t4_1;
	}

	public void setP_t4_1(String p_t4_1) {
		this.p_t4_1 = p_t4_1;
	}

	public String getP_t4_2() {
		return p_t4_2;
	}

	public void setP_t4_2(String p_t4_2) {
		this.p_t4_2 = p_t4_2;
	}

	public String getP_t4_3() {
		return p_t4_3;
	}

	public void setP_t4_3(String p_t4_3) {
		this.p_t4_3 = p_t4_3;
	}

	public String getP_t4_4() {
		return p_t4_4;
	}

	public void setP_t4_4(String p_t4_4) {
		this.p_t4_4 = p_t4_4;
	}

	public String getP_typenm() {
		return p_typenm;
	}

	public void setP_typenm(String p_typenm) {
		this.p_typenm = p_typenm;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
}

