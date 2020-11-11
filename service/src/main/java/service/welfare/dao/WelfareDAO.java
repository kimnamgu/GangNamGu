package service.welfare.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import service.common.dao.AbstractDAO;

@Repository("welfareDAO")

public class WelfareDAO extends AbstractDAO{
	
	// api 셀렉트
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> welfarSelect(Map<String, Object> map) throws Exception{
		System.out.println("dao map확인 : " + map.toString());
		return (List<Map<String, Object>>)selectPagingList("welfare.welfarSelect", map);
	}
	
	// api 인설트
	@SuppressWarnings("unchecked")
	public Map<String, Object> welfarInsert(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) insert("welfare.welfarInsert", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> welfareSelectCt(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("welfare.welfareSelectCt", map);
	}
	
	
	// 로그인
	@SuppressWarnings("unchecked")
	public Map<String, Object> loginProcessId(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("welfare.loginProcessId", map);
	}
	
	// 로그인 비밀번호
	@SuppressWarnings("unchecked")
	public Map<String, Object> loginProcessPw(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("welfare.loginProcessPw", map);
	}

	// 상태 업데이트
	public Map<String, Object> updateDocIssueReserve(Map<String, Object> map) throws Exception{
		Map<String, Object> rtmap = new HashMap<>();
		log.debug("map 확인 : " + map.toString());
		log.debug("log DaejangDao rtn==> [" + rtmap + "]");
		rtmap = update("welfare.updateDocIssueReserve", map);
		return rtmap;
	}
	
	
	// 정보 업데이트
	public Map<String, Object> updateWelfareInfo(Map<String, Object> map) throws Exception{
		Map<String, Object> rtmap = new HashMap<>();
		log.debug("map 확인 : " + map.toString());
		log.debug("log DaejangDao rtn==> [" + rtmap + "]");
		rtmap = update("welfare.updateWelfareInfo", map);
		return rtmap;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> welfarUserSelectList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectList("welfare.welfarUserSelectList", map);
	}
	

}
