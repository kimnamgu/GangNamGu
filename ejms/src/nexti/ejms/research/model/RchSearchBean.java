/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사 검색조건 bean
 * 설명:
 */
package nexti.ejms.research.model;

public class RchSearchBean  {
		
	private int 	sch_rchno 		= 0;			//설문번호
	private String 	sch_orggbn		= "";			//사용자구분
	private String  selyear			= ""; 			//년도조회조건
	private String  sch_title		= "";   		//제목조회조건
	private String  sch_deptcd		= "";			//부서조회조건
	private String  sch_sex			= "";           //성별조회조건
	private String  sch_age			= "";           //연령조회조건
	private String[]  sch_exam;						//보기조회조건
	private int	sch_ansusrseq		= 0;			//답변자연번
	
	
	public String getSch_orggbn() {
		return sch_orggbn;
	}
	public void setSch_orggbn(String sch_orggbn) {
		this.sch_orggbn = sch_orggbn;
	}
	public int getSch_ansusrseq() {
		return sch_ansusrseq;
	}
	public void setSch_ansusrseq(int sch_ansusrseq) {
		this.sch_ansusrseq = sch_ansusrseq;
	}
	public int getSch_rchno() {
		return sch_rchno;
	}
	public void setSch_rchno(int sch_rchno) {
		this.sch_rchno = sch_rchno;
	}
	public String getSch_deptcd() {
		return sch_deptcd;
	}
	public void setSch_deptcd(String sch_deptcd) {
		this.sch_deptcd = sch_deptcd;
	}
	public String getSch_title() {
		return sch_title;
	}
	public void setSch_title(String sch_title) {
		this.sch_title = sch_title;
	}
	public String getSelyear() {
		return selyear;
	}
	public void setSelyear(String selyear) {
		this.selyear = selyear;
	}
	public String getSch_age() {
		return sch_age;
	}
	public void setSch_age(String sch_age) {
		this.sch_age = sch_age;
	}
	public String[] getSch_exam() {
		return sch_exam;
	}
	public void setSch_exam(String[] sch_exam) {
		this.sch_exam = sch_exam;
	}
	public String getSch_sex() {
		return sch_sex;
	}
	public void setSch_sex(String sch_sex) {
		this.sch_sex = sch_sex;
	}

}