/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ڷ��̰� actionform
 * ����:
 */
package nexti.ejms.dataTransfer.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class DataTransferForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private int		historyno	= 0;	//�̷¹�ȣ
	private int		seq			= 0;	//ó����ȣ
	private	String	tablename	= "";	//�������̺�
	private	String	columnname	= "";	//�����÷�
	private	String	oldvalue	= "";	//��������
	private	String	cause		= "";	//�̰�����
	private	String	crtdt		= "";	//�̰��Ͻ�
	private	String	crtusrid	= "";	//�̰���ID
	
	private String 	sch_orggbn	= "";	//�˻�����:����� ���� (001:�����ú�û, 002:���ӱ��, 003:�����, 004:����ȸ, 005:��/��/��, 006: ��Ÿ)
	private String	sch_deptid	= "";	//�˻�����:�μ�
	private String	sch_userid	= "";	//�˻�����:�����
	private String	orgdept		= "";	//�̰��ڷ�μ�(����Ʈ�ڽ�)
	private String	orguser		= "";	//�̰��ڷ�μ�(����Ʈ�ڽ�)
	private String	tgtdept		= "";	//�̰����μ�(����Ʈ�ڽ�)
	private String	tgtuser		= "";	//�̰����μ�(����Ʈ�ڽ�)
	private List	datalist	= null;	//�̰��ڷ���
	private String[] gbn		= null;	//�����ڷᱸ��
	private String[] gbnid		= null;	//���浥����
	private String[] tgtuserid	= null;	//�̰��������ID
	
	private int		no			= 0;	//�ڷ��ȣ
	private String	type		= "";	//�ڷ�����
	private String	title		= "";	//����
	private String	deptid		= "";	//�μ�ID
	private String	deptnm		= "";	//�μ���
	private String	userid		= "";	//�����ID
	private String	usernm		= "";	//����ڸ�
	private String	existdeptid = "";	//�μ����翩��
	private String	existuserid = "";	//��������翩��
	private String	uptdt	= "";		//������������
	
	public String getSch_orggbn() {
		return sch_orggbn;
	}
	public void setSch_orggbn(String sch_orggbn) {
		this.sch_orggbn = sch_orggbn;
	}
	public String getOrgdept() {
		return orgdept;
	}
	public void setOrgdept(String orgdept) {
		this.orgdept = orgdept;
	}
	public String getOrguser() {
		return orguser;
	}
	public void setOrguser(String orguser) {
		this.orguser = orguser;
	}
	public String getTgtuser() {
		return tgtuser;
	}
	public void setTgtuser(String tgtuser) {
		this.tgtuser = tgtuser;
	}
	public String getTgtdept() {
		return tgtdept;
	}
	public void setTgtdept(String tgtdeptid) {
		this.tgtdept = tgtdeptid;
	}
	public String getExistdeptid() {
		return existdeptid;
	}
	public void setExistdeptid(String existdeptid) {
		this.existdeptid = existdeptid;
	}
	public String getExistuserid() {
		return existuserid;
	}
	public void setExistuserid(String existuserid) {
		this.existuserid = existuserid;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public String getDeptnm() {
		return deptnm;
	}
	public void setDeptnm(String deptnm) {
		this.deptnm = deptnm;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUptdt() {
		return uptdt;
	}
	public void setUptdt(String uptdt) {
		this.uptdt = uptdt;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsernm() {
		return usernm;
	}
	public void setUsernm(String usernm) {
		this.usernm = usernm;
	}
	public String[] getGbn() {
		return gbn;
	}
	public void setGbn(String[] type) {
		this.gbn = type;
	}
	public List getDatalist() {
		return datalist;
	}
	public void setDatalist(List dtlist) {
		this.datalist = dtlist;
	}
	public String getSch_deptid() {
		return sch_deptid;
	}
	public void setSch_deptid(String sch_deptid) {
		this.sch_deptid = sch_deptid;
	}
	public String getSch_userid() {
		return sch_userid;
	}
	public void setSch_userid(String sch_userid) {
		this.sch_userid = sch_userid;
	}
	public String[] getTgtuserid() {
		return tgtuserid;
	}
	public void setTgtuserid(String[] tgtuserid) {
		this.tgtuserid = tgtuserid;
	}
	public String[] getGbnid() {
		return gbnid;
	}
	public void setGbnid(String[] value) {
		this.gbnid = value;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getColumnname() {
		return columnname;
	}
	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}
	public String getCrtdt() {
		return crtdt;
	}
	public void setCrtdt(String crtdt) {
		this.crtdt = crtdt;
	}
	public String getCrtusrid() {
		return crtusrid;
	}
	public void setCrtusrid(String crtusrid) {
		this.crtusrid = crtusrid;
	}
	public int getHistoryno() {
		return historyno;
	}
	public void setHistoryno(int historyno) {
		this.historyno = historyno;
	}
	public String getOldvalue() {
		return oldvalue;
	}
	public void setOldvalue(String oldvalue) {
		this.oldvalue = oldvalue;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {return null;}
	public void reset(ActionMapping mapping, HttpServletRequest request) {}
}