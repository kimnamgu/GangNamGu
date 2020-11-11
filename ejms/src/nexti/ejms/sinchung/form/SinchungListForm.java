/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� ��� actionform
 * ����:
 */
package nexti.ejms.sinchung.form;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class SinchungListForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private String search_title = "";   //�˻�����
	private String syear      = "";     //�⵵
	private String procGbn    = "1";    //��ó�� ����(0:��ó�� , 1:��ü)
	private int page          = 1 ;     //������
		
	private List sinchungList = null; 	//��û������Ʈ
	
	private int treescroll	  = 0;      //�μ�Tree�� ��ũ�� ������ ����..
	private String deptcd     = "";     //�μ��ڵ�
	private String deptnm	  = "";     //�μ���Ī

	private String  sch_deptcd		= "";			//�μ���ȸ����
	private String 	sch_old_deptcd	= "";			//�μ�����ȸ����
	private String 	sch_deptnm		= "";			//�μ�����ȸ����
	private String  sch_old_userid	= "";			//�������ȸ����
	private String  sch_userid		= "";			//�������ȸ����
	private String 	sch_usernm		= "";			//�������ȸ����
	private String 	initentry		= "first";		//�ʱ����Կ���
	
	private String  orggbn			= "";
	
	public String getOrggbn() {
		return orggbn;
	}

	public void setOrggbn(String orggbn) {
		this.orggbn = orggbn;
	}

	public String getInitentry() {
		return initentry;
	}

	public void setInitentry(String initentry) {
		this.initentry = initentry;
	}

	public String getSch_deptcd() {
		return sch_deptcd;
	}

	public void setSch_deptcd(String sch_deptcd) {
		this.sch_deptcd = sch_deptcd;
	}

	public String getSch_deptnm() {
		return sch_deptnm;
	}

	public void setSch_deptnm(String sch_deptnm) {
		this.sch_deptnm = sch_deptnm;
	}

	public String getSch_old_deptcd() {
		return sch_old_deptcd;
	}

	public void setSch_old_deptcd(String sch_old_deptcd) {
		this.sch_old_deptcd = sch_old_deptcd;
	}

	public String getSch_old_userid() {
		return sch_old_userid;
	}

	public void setSch_old_userid(String sch_old_userid) {
		this.sch_old_userid = sch_old_userid;
	}

	public String getSch_userid() {
		return sch_userid;
	}

	public void setSch_userid(String sch_userid) {
		this.sch_userid = sch_userid;
	}

	public String getSch_usernm() {
		return sch_usernm;
	}

	public void setSch_usernm(String sch_usernm) {
		this.sch_usernm = sch_usernm;
	}

	public String getSearch_title() {
		return search_title;
	}

	public void setSearch_title(String search_title) {
		this.search_title = search_title;
	}

	public String getSyear() {
		return syear;
	}

	public void setSyear(String syear) {
		this.syear = syear;			
	}

	public String getDeptnm() {
		return deptnm;
	}

	public void setDeptnm(String deptnm) {
		this.deptnm = deptnm;
	}

	public String getDeptcd() {
		return deptcd;
	}

	public void setDeptcd(String deptcd) {
		this.deptcd = deptcd;
	}

	public int getTreescroll() {
		return treescroll;
	}

	public void setTreescroll(int treescroll) {
		this.treescroll = treescroll;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getProcGbn() {
		return procGbn;
	}

	public void setProcGbn(String procGbn) {
		this.procGbn = procGbn;
	}

	public List getSinchungList() {
		return sinchungList;
	}

	public void setSinchungList(List sinchungList) {
		this.sinchungList = sinchungList;
	}	
}