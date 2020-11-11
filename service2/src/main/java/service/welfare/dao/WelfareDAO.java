package service.welfare.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import service.common.dao.AbstractDAO;

@Repository("welfareDAO")

public class WelfareDAO extends AbstractDAO{
	
	// 셀렉트
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> welfarSelect(Map<String, Object> map) throws Exception{
		log.debug("bb1 : " + map.toString());
		if(map.get("daoGubunCode").equals("00")){
			return (List<Map<String, Object>>)selectPagingList("welfare.welfarSelect", map);
		}else{
			return (List<Map<String, Object>>)selectPagingList("welfare.welfareSelectCt", map);
		}
		
	}
	// 인설트
	@SuppressWarnings("unchecked")
	public Map<String, Object> welfarInsert(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) insert("welfare.welfarInsert", map);
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
//		rtmap = update("welfare.updateDocIssueReserve", map);
		return rtmap;
	}
	

}
