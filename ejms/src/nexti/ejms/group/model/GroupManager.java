/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 배포목록관리 manager
 * 설명:
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
			
			//배포목록 주코드 등록
			bean = new GroupBean();
			bean.setGrplistnm(groupName);
		    bean.setCrtusrid(user_id);
		    bean.setCrtusrgbn("1");
			grplistcd = insertGrpMst(bean);
			
			//배포목록 부코드 등록
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
	 * 배포목록 마스타 추가	
	 */
	public int insertGrpMst(GroupBean bean) throws Exception {
		return getGroupDAO().insertGrpMst(bean);
	}

	/** 
	 * 배포목록 마스타 수정	
	 */
	public int modifyGrpMst(GroupBean bean) throws Exception {
		return getGroupDAO().modifyGrpMst(bean);
	}

	/** 
	 * 배포목록 마스타 삭제	
	 */
	public int deleteGrpMst(int grplistcd) throws Exception {
		return getGroupDAO().deleteGrpMst(grplistcd);
	}

	/** 
	 * 마스터 속성목록	
	 */
	public List getGrpMstList(String user_gbn, String crtusrid, String crtusrgbn) throws Exception {
		return getGroupDAO().getGrpMstList(user_gbn, crtusrid, crtusrgbn);
	}
	
	/** 
	 * 특정 마스터코드(LISTCD)에 대한 Detail List
	 */
	public List getGrpDtlList(int grplistcd, String codegbn, String userid) throws Exception {
		List subList = null;
		
		subList = getGroupDAO().getGrpDtlList(grplistcd, codegbn, userid);
		
		return subList;
	}
	
	/** 
	 * 배포목록 마스터보기
	 */
	public GroupBean getGrpMstInfo(int grplistcd) throws Exception {
		return getGroupDAO().getGrpMstInfo(grplistcd);
	}

	/** 
	 * 배포목록 디테일보기
	 */
	public GroupBean getGrpDtlInfo(int grplistcd, int seq) throws Exception {
		return getGroupDAO().getGrpDtlInfo(grplistcd, seq);
	}

	/** 
	 * 배포목록마스터명 가져오기
	 */
	public String getGrpListName(int grplistcd) throws Exception {
		return getGroupDAO().getGrpListName(grplistcd);
	}
	
	/** 
	 * 배포목록 마스터 존재유무 
	 * return true : 존재함
	 * return false : 존재안함
	 * */
	public boolean existedGrp(int grplistcd) throws Exception {
		return getGroupDAO().existedGrp(grplistcd);
	}
	
	/**
	 * 배포목록 디테일 등록
	 * @param grplistcd
	 * @param deptList
	 * @return
	 * @throws Exception
	 */
	public int insertGrpDtl(int grplistcd, ArrayList deptList) throws Exception{
		return getGroupDAO().insertGrpDtl(grplistcd, deptList);
	}	
	
	/**
	 * 배포목록디테일 삭제
	 * @param grplistcd
	 * @param deptList
	 * @return
	 * @throws Exception
	 */
	public int deleteGrpDtl(int grplistcd, int seq) throws Exception{
		return getGroupDAO().deleteGrpDtl(grplistcd, seq);
	}	
	
	/** 
	 * 배포목록마스터명 가져오기
	 */
	public String getGrpDtlXml(int grplistcd, String userid) throws Exception {
		return getGroupDAO().getGrpDtlXml(grplistcd, userid);
	}

	/** 
	 * 배포목록디테일 부서명칭 수정	
	 */
	public int modifyDetailNameFormCode(String dept_id, String dept_name) throws Exception {
		return getGroupDAO().modifyDetailNameFormCode(dept_id, dept_name);
	}	
}
