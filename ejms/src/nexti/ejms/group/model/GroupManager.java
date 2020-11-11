/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ������ϰ��� manager
 * ����:
 */
package nexti.ejms.group.model;

import java.util.ArrayList;
import java.util.List;

public class GroupManager {
	private static GroupManager instance = null;
	private static GroupDAO dao = null;
	
	public static GroupManager instance() {
		if (instance==null) instance = new GroupManager(); 
		return instance;
	}
	
	private GroupDAO getGroupDAO(){
		if (dao==null) dao = new GroupDAO(); 
		return dao;
	}
	
	public void saveGroup(String type, String groupName, String codeList, String codeGbnList, String nameList, String deptId, String user_id) throws Exception {
		if ( type.equals("insert") == true ) {
			int grplistcd = 0;
			GroupBean bean = null;
			
			//������� ���ڵ� ���
			bean = new GroupBean();
			bean.setGrplistnm(groupName);
		    bean.setCrtusrid(user_id);
		    bean.setCrtusrgbn("1");
			grplistcd = insertGrpMst(bean);
			
			//������� ���ڵ� ���
			if ( grplistcd > 0 ) {
				String[] cdList = codeList.split(",");
				String[] cdgbnList = codeGbnList.split(",");
				String[] nmList = nameList.split(",");
				ArrayList grpList = new ArrayList();
				
				if (cdList != null){
					for (int i = 0; i < cdList.length; i++){
						bean = new GroupBean();
						bean.setCode(cdList[i]);
						bean.setName(nmList[i]);
						bean.setCodegbn(Integer.toString((Integer.parseInt("0" + cdgbnList[i]) - 2)));
						grpList.add(bean);
						bean = null;
					}
				}
				 
				insertGrpDtl(grplistcd, grpList);
			}
		} else if ( type.equals("delete") == true ) {
			deleteGrpMst(Integer.parseInt("0" + deptId));
		}
	}
	
	/** 
	 * ������� ����Ÿ �߰�	
	 */
	public int insertGrpMst(GroupBean bean) throws Exception {
		return getGroupDAO().insertGrpMst(bean);
	}

	/** 
	 * ������� ����Ÿ ����	
	 */
	public int modifyGrpMst(GroupBean bean) throws Exception {
		return getGroupDAO().modifyGrpMst(bean);
	}

	/** 
	 * ������� ����Ÿ ����	
	 */
	public int deleteGrpMst(int grplistcd) throws Exception {
		return getGroupDAO().deleteGrpMst(grplistcd);
	}

	/** 
	 * ������ �Ӽ����	
	 */
	public List getGrpMstList(String user_gbn, String crtusrid, String crtusrgbn) throws Exception {
		return getGroupDAO().getGrpMstList(user_gbn, crtusrid, crtusrgbn);
	}
	
	/** 
	 * Ư�� �������ڵ�(LISTCD)�� ���� Detail List
	 */
	public List getGrpDtlList(int grplistcd, String codegbn, String userid) throws Exception {
		List subList = null;
		
		subList = getGroupDAO().getGrpDtlList(grplistcd, codegbn, userid);
		
		return subList;
	}
	
	/** 
	 * ������� �����ͺ���
	 */
	public GroupBean getGrpMstInfo(int grplistcd) throws Exception {
		return getGroupDAO().getGrpMstInfo(grplistcd);
	}

	/** 
	 * ������� �����Ϻ���
	 */
	public GroupBean getGrpDtlInfo(int grplistcd, int seq) throws Exception {
		return getGroupDAO().getGrpDtlInfo(grplistcd, seq);
	}

	/** 
	 * ������ϸ����͸� ��������
	 */
	public String getGrpListName(int grplistcd) throws Exception {
		return getGroupDAO().getGrpListName(grplistcd);
	}
	
	/** 
	 * ������� ������ �������� 
	 * return true : ������
	 * return false : �������
	 * */
	public boolean existedGrp(int grplistcd) throws Exception {
		return getGroupDAO().existedGrp(grplistcd);
	}
	
	/**
	 * ������� ������ ���
	 * @param grplistcd
	 * @param deptList
	 * @return
	 * @throws Exception
	 */
	public int insertGrpDtl(int grplistcd, ArrayList deptList) throws Exception{
		return getGroupDAO().insertGrpDtl(grplistcd, deptList);
	}	
	
	/**
	 * ������ϵ����� ����
	 * @param grplistcd
	 * @param deptList
	 * @return
	 * @throws Exception
	 */
	public int deleteGrpDtl(int grplistcd, int seq) throws Exception{
		return getGroupDAO().deleteGrpDtl(grplistcd, seq);
	}	
	
	/** 
	 * ������ϸ����͸� ��������
	 */
	public String getGrpDtlXml(int grplistcd, String userid) throws Exception {
		return getGroupDAO().getGrpDtlXml(grplistcd, userid);
	}

	/** 
	 * ������ϵ����� �μ���Ī ����	
	 */
	public int modifyDetailNameFormCode(String dept_id, String dept_name) throws Exception {
		return getGroupDAO().modifyDetailNameFormCode(dept_id, dept_name);
	}	
}
