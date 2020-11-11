/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��������� bean
 * ����:
 */
package nexti.ejms.user.model;

public class UserBean {
	private String	user_id			= "";	//�����ID
	private String	user_sn			= "";	//�ֹι�ȣ
	private String	user_name		= "";	//�̸�
	private String	dept_id			= "";	//�μ��ڵ�
	private String	dept_name		= "";	//�μ���
	private String	dept_fullname	= "";	//��ü�μ���
	private String	class_id		= "";	//�����ڵ�
	private String	class_name		= "";	//���޸�
	private String	position_id		= "";	//�����ڵ�
	private String	position_name	= "";	//������
	private String	grade_id		= "";	//����ڵ�
	private String	grade_name		= "";	//��޸�
	private int		usr_rank		= 0;	//����
	private String	email			= "";	//�̸���
	private String	tel				= "";	//��ȭ��ȣ
	private String	mobile			= "";	//�ڵ�����ȣ
	private String	use_yn			= "";	//��뿩��
	private String	con_yn			= "";	//���迩��
	private String	passwd			= "";	//��й�ȣ
	private String	mgryn			= "";	//�����ڿ���
	private String	delivery_yn		= "";	//��ο���
	private String	chrgunitcd		= "";	//�����������ڵ�
	private String	ea_id			= "";	//���ڰ��� ID
	private String	gpki_id			= "";	//GPKI ID
	private String	sex				= "M";	//����
	private int		age				= 20;	//���ɴ�
	private String	lstlogindt		= "";	//�ֱٷα����Ͻ�
	private String	crtdt			= "";	//�������
	private String	crtusrid		= "";	//�����
	private String	uptdt			= "";	//��������
	private String	uptusrid		= "";	//������
	
	private String	chrgunitnm		= "";	//������������
	private String	dept_fax		= "";	//�μ��ѽ�
	
//	private String user_stat       = "";   //����ڱٹ������ڵ�
//	private String user_stat_name  = "";   //����ڱٹ����¸�Ī
//	private String regularity      = "";   //ä�뱸��
//	private String org_id          = "";   //����ڵ�
//	private String org_name        = "";   //�����Ī
//	private String fax			   = "";   //�ѽ���ȣ
//	private String join_day        = "";   //�Ի�����
//	private String retire_day      = "";   //��������
//	private String add_info1       = "";   //�߰�����1
//	private String add_info2       = "";   //�߰�����2
//	private String add_info3       = "";   //�߰�����3
//	private String add_info4       = "";   //�߰�����4
//	private String add_info5       = "";   //�߰�����5
//	private String add_info6       = "";   //�߰�����6
//	private String add_info7       = "";   //�߰�����7
//	private String base_sys		   = "";   //���ؽý��۱���
//	private String reg_day         = "";   //�������
//	private String update_day      = "";   //��������
//	private String rduty_name      = "";   //����ü������
//	
//	private String chrcd           = "";   //������ �����ڵ�
//	private String chrnm           = "";   //������ ������Ī
//
//	private int 	user_age	= 20;	//����
//	private String	user_sex	= "M";	//����(����:M, ����:F)
	
	public String getDept_fax() {
		return dept_fax;
	}
	public void setDept_fax(String dept_fax) {
		this.dept_fax = dept_fax;
	}
	public String getChrgunitnm() {
		return chrgunitnm;
	}
	public void setChrgunitnm(String chrgunitnm) {
		this.chrgunitnm = chrgunitnm;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getChrgunitcd() {
		return chrgunitcd;
	}
	public void setChrgunitcd(String chrgunitcd) {
		this.chrgunitcd = chrgunitcd;
	}
	public String getClass_id() {
		return class_id;
	}
	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public String getCon_yn() {
		return con_yn;
	}
	public void setCon_yn(String con_yn) {
		this.con_yn = con_yn;
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
	public String getDelivery_yn() {
		return delivery_yn;
	}
	public void setDelivery_yn(String delivery_yn) {
		this.delivery_yn = delivery_yn;
	}
	public String getDept_fullname() {
		return dept_fullname;
	}
	public void setDept_fullname(String dept_fullname) {
		this.dept_fullname = dept_fullname;
	}
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getEa_id() {
		return ea_id;
	}
	public void setEa_id(String ea_id) {
		this.ea_id = ea_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGpki_id() {
		return gpki_id;
	}
	public void setGpki_id(String gpki_id) {
		this.gpki_id = gpki_id;
	}
	public String getGrade_id() {
		return grade_id;
	}
	public void setGrade_id(String grade_id) {
		this.grade_id = grade_id;
	}
	public String getGrade_name() {
		return grade_name;
	}
	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}
	public String getLstlogindt() {
		return lstlogindt;
	}
	public void setLstlogindt(String lstlogindt) {
		this.lstlogindt = lstlogindt;
	}
	public String getMgryn() {
		return mgryn;
	}
	public void setMgryn(String mgryn) {
		this.mgryn = mgryn;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getPosition_id() {
		return position_id;
	}
	public void setPosition_id(String position_id) {
		this.position_id = position_id;
	}
	public String getPosition_name() {
		return position_name;
	}
	public void setPosition_name(String position_name) {
		this.position_name = position_name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getUptdt() {
		return uptdt;
	}
	public void setUptdt(String uptdt) {
		this.uptdt = uptdt;
	}
	public String getUptusrid() {
		return uptusrid;
	}
	public void setUptusrid(String uptusrid) {
		this.uptusrid = uptusrid;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
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
	public String getUser_sn() {
		return user_sn;
	}
	public void setUser_sn(String user_sn) {
		this.user_sn = user_sn;
	}
	public int getUsr_rank() {
		return usr_rank;
	}
	public void setUsr_rank(int usr_rank) {
		this.usr_rank = usr_rank;
	}
}