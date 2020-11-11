package sds.common.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository("commonDAO")
public class CommonDAO extends AbstractDAO{

	@SuppressWarnings("unchecked")
	public Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception{
		return (Map<String, Object>)selectOne("common.selectFileInfo", map);
	}

	@SuppressWarnings("unchecked")
	public void updateDownCount(Map<String, Object> map) throws Exception{
		update("common.updateDownCount", map);
	}
	
	@SuppressWarnings("unchecked")
	public void updateApplyCount(Map<String, Object> map) throws Exception{
		update("common.updateApplyCount", map);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> loginProcessId(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("login.loginProcessId", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> loginProcessPw(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("login.loginProcessPw", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> ssoLogin(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne2("sso.ssoLogin", map);
	}
	
	
	public void updateUserinfo(Map<String, Object> map) throws Exception{
		insert("login.updateUserinfo", map);
	}

	
	public void insertUserinfo(Map<String, Object> map) throws Exception{
		insert("login.insertUserinfo", map);
	}
	
	
	public void updateEmployeeTransData(Map<String, Object> map) throws Exception{
		update("diagnose.updateEmployeeTransData", map);
	}
	
	public void insertDeptData(Map<String, Object> map) throws Exception{
		insert("diagnose.insertDeptData", map);
	}
	
	public void deleteDeptData(Map<String, Object> map) throws Exception{
		delete("diagnose.deleteDeptData", map);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectEmployeeTransIdList() throws Exception{
		
		return (List<Map<String, Object>>)selectList("common.selectEmployeeTransIdList");
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectDeptList(Map<String, Object> map) throws Exception{
		
		return (List<Map<String, Object>>)selectList2("sso.selectDeptList", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> yearList(Map<String, Object> map) throws Exception{
		
		return (List<Map<String, Object>>)selectList("common.yearList", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> dongList(Map<String, Object> map) throws Exception{
		
		return (List<Map<String, Object>>)selectList("common.dongList", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectIdApproveList(Map<String, Object> map) throws Exception{
		
		return (List<Map<String, Object>>)selectPagingList("common.selectIdApproveList", map);
	}
	
	public void updateIdApprove(Map<String, Object> map) throws Exception{
		update("common.updateIdApprove", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> idApproveListDetail(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("common.idApproveListDetail", map);
	}
	
	public void updateIdApproveList(Map<String, Object> map) throws Exception{
		update("common.updateIdApproveList", map);
	}
	
	
	public void deleteIdApproveList(Map<String, Object> map) throws Exception{
		update("common.deleteIdApproveList", map);
	}
	
	
	
	
	public void deleteDgnsUserData(Map<String, Object> map) throws Exception{
		update("common.deleteDgnsUserData", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectSsoUserList(Map<String, Object> map) throws Exception{
		
		return (List<Map<String, Object>>)selectList2("sso.selectSsoUserList", map);
	}
	
}

