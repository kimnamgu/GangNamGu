/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� �˻����� bean
 * ����:
 */
package nexti.ejms.research.model;

public class RchSearchBean  {
		
	private int 	sch_rchno 		= 0;			//������ȣ
	private String 	sch_orggbn		= "";			//����ڱ���
	private String  selyear			= ""; 			//�⵵��ȸ����
	private String  sch_title		= "";   		//������ȸ����
	private String  sch_deptcd		= "";			//�μ���ȸ����
	private String  sch_sex			= "";           //������ȸ����
	private String  sch_age			= "";           //������ȸ����
	private String[]  sch_exam;						//������ȸ����
	private int	sch_ansusrseq		= 0;			//�亯�ڿ���
	
	
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