package oms.common.service;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import oms.common.dao.CommonDAO;

@Service("commonService")
public class CommonServiceImpl implements CommonService{
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="commonDAO")
	private CommonDAO commonDAO;

	@Override
	public Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception {
		return commonDAO.selectFileInfo(map);
	}
	
	@Override
	public List<Map<String, Object>> deptList(Map<String, Object> map) throws Exception {
		return commonDAO.deptList(map);
	}
	
	
	
	
	@Override
	public Map<String, Object> loginProcessId(Map<String, Object> map, HttpServletRequest request) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = commonDAO.loginProcessId(map);
		resultMap.put("map_i", tempMap);
		
		return resultMap;
	}
	
	@Override
	public Map<String, Object> loginProcessPw(Map<String, Object> map, HttpServletRequest request) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = commonDAO.loginProcessPw(map);
		resultMap.put("map_p", tempMap);
		
		return resultMap;
	}
	
	@Override
	public Map<String, Object> ssoLogin(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = commonDAO.ssoLogin(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public void insertUserinfo(Map<String, Object> map, HttpServletRequest request) throws Exception{
		commonDAO.insertUserinfo(map);
		
	}
	
	@Override
	public void updateUserinfo(Map<String, Object> map) throws Exception{
		commonDAO.updateUserinfo(map);
		
	}
	
	
	@Override
	public Map<String, Object> getOfficeworkStatics(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		
		Map<String, Object> tempMap = commonDAO.getOfficeworkStatics(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	
	@Override
	public List<Map<String, Object>> selectIdApproveList(Map<String, Object> map) throws Exception {
		return commonDAO.selectIdApproveList(map);
	}
	
	
	@Override
	public void updateIdApproveList(Map<String, Object> map, HttpServletRequest request) throws Exception{
		commonDAO.updateIdApproveList(map);
	}
}
