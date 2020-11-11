/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 시스템에이전트 보기 bean 
 * 설명:
 */
package nexti.ejms.agent.model;

public class SystemAgentRuntimeBean {
	private String p_id = "";			//프로그램ID
	private int p_seq = 0;				//순번
	private String p_type = "";			//타입
	private String p_typenm = "";		//타입명
	private String p_t1 = "";			//타입1 매일
	private String p_t2 = "";			//타입2 매주
	private String p_t3 = "";			//타입3 매월
	private String p_t4 = "";			//타입4 매년
	private String p_t5 = "";			//사용안함
	private String p_t6 = "";			//사용안함
	private String p_use = "";			//사용여부
	private String db_pass = "";
	private String made_man = "";
	private String made_dt = "";
	
	public String getDb_pass() {
		return db_pass;
	}
	public void setDb_pass(String db_pass) {
		this.db_pass = db_pass;
	}
	public String getMade_dt() {
		return made_dt;
	}
	public void setMade_dt(String made_dt) {
		this.made_dt = made_dt;
	}
	public String getMade_man() {
		return made_man;
	}
	public void setMade_man(String made_man) {
		this.made_man = made_man;
	}
	public String getP_id() {
		return p_id;
	}
	public void setP_id(String p_id) {
		this.p_id = p_id;
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
	public String getP_typenm() {
		return p_typenm;
	}
	public void setP_typenm(String p_typenm) {
		this.p_typenm = p_typenm;
	}


}
