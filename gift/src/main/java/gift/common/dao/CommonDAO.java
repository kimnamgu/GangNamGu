package gift.common.dao;

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
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> popJikWonList(Map<String, Object> map) throws Exception{
		
		return (List<Map<String, Object>>)selectList2("sso.popJikWonList", map);
	}
}


