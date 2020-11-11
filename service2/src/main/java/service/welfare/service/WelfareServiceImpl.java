package service.welfare.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import service.common.util.CommonUtils;
import service.welfare.dao.WelfareDAO;

@Service("welfareService")
public class WelfareServiceImpl implements WelfareService {
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="welfareDAO")
	private WelfareDAO welfareDAO;
	
	
	
	/* 셀렉트 API*/
	@Override
	public List<Map<String, Object>> welfarSelect(Map<String, Object> map) throws Exception {
		return welfareDAO.welfarSelect(map);
	}
	
	
	// 인설트 API
	@Override
	public Map<String, Object> welfarInsert(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = welfareDAO.welfarInsert(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	
	// 로그인
	@Override
	public Map<String, Object> loginProcessId(Map<String, Object> map, HttpServletRequest request) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = welfareDAO.loginProcessId(map);
		resultMap.put("map_i", tempMap);
		
		return resultMap;
	}
	
	// 로그인
	@Override
	public Map<String, Object> loginProcessPw(Map<String, Object> map, HttpServletRequest request) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = welfareDAO.loginProcessPw(map);
		resultMap.put("map_p", tempMap);
		
		return resultMap;
	}
	
	
	// 상태 업데이트
	@Override
	public Map<String, Object> updateDocIssueReserve(Map<String, Object> map, HttpServletRequest request) throws Exception{
		
		log.debug("테스트 map :: " + map.toString());
		Map<String, Object> rtmap = new HashMap<>();
		
//		rtmap = welfareDAO.updateDocIssueReserve(map);
		return rtmap;
	}
	
	
}
