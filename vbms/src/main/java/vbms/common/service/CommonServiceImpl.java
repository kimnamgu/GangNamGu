package vbms.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import vbms.common.dao.CommonDAO;

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
	public void updateDownCount(Map<String, Object> map) throws Exception {
		commonDAO.updateDownCount(map);
	}
	
	
	@Override
	public void updateApplyCount(Map<String, Object> map) throws Exception {
		commonDAO.updateApplyCount(map);
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
	public void updateEmployeeTransData(Map<String, Object> map) throws Exception{
		commonDAO.updateEmployeeTransData(map);
		
	}
	
	@Override
	public void insertDeptData(Map<String, Object> map) throws Exception{
		commonDAO.insertDeptData(map);
		
	}
	
	@Override
	public void deleteDeptData(Map<String, Object> map) throws Exception{
		commonDAO.deleteDeptData(map);
		
	}
	
	
	
	@Override
	public List<Map<String, Object>> selectEmployeeTransIdList() throws Exception {
		return commonDAO.selectEmployeeTransIdList();
	}
	
	
	@Override
	public List<Map<String, Object>> selectDeptList(Map<String, Object> map) throws Exception {
		return commonDAO.selectDeptList(map);
	}
	
	@Override
	public List<Map<String, Object>> structList(Map<String, Object> map) throws Exception {
		return commonDAO.structList(map);
	}
	
	@Override
	public List<Map<String, Object>> purposeList(Map<String, Object> map) throws Exception {
		return commonDAO.purposeList(map);
	}
	
	
	@Override
	public List<Map<String, Object>> stateList(Map<String, Object> map) throws Exception {
		return commonDAO.stateList(map);
	}
	
		
	@Override
	public List<Map<String, Object>> dongList(Map<String, Object> map) throws Exception {
		return commonDAO.dongList(map);
	}
	
	
	@Override
	public List<Map<String, Object>> popJikWonList(Map<String, Object> map) throws Exception {
		return commonDAO.popJikWonList(map);
	}
	
	
	
	@Override
	public List<Map<String, Object>> selectIdApproveList(Map<String, Object> map) throws Exception {
		return commonDAO.selectIdApproveList(map);
	}
	
	@Override
	public void updateIdApprove(Map<String, Object> map) throws Exception{
		commonDAO.updateIdApprove(map);
		
	}
	
	@Override
	public Map<String, Object> idApproveListDetail(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = commonDAO.idApproveListDetail(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public void updateIdApproveList(Map<String, Object> map) throws Exception{
		commonDAO.updateIdApproveList(map);
		
	}
	
	
	@Override
	public void deleteIdApproveList(Map<String, Object> map) throws Exception{
		commonDAO.deleteIdApproveList(map);
	}
	
	
	
	
	@Override
	public void deleteDgnsUserData(Map<String, Object> map) throws Exception{
		commonDAO.deleteDgnsUserData(map);
	}
	
	
	@Override
	public List<Map<String, Object>> selectSsoUserList(Map<String, Object> map) throws Exception {
		return commonDAO.selectSsoUserList(map);
	}
	
	
	
	
	@Override
	public String getnBeforeDay(Map<String, Object> map) throws Exception {
		return commonDAO.getnBeforeDay(map);
	}
	
	
	@Override
	public List<Map<String, Object>> selectArimService(Map<String, Object> map) throws Exception {
		return commonDAO.selectArimService(map);
	}
	
	
	
	
	
	@Override
	public void insertSendMsg(Map<String, Object> map) throws Exception {
		commonDAO.insertSendMsg(map);
	}
	
	@Override
	public List<Map<String, Object>> popSmsSendList(Map<String, Object> map) throws Exception {
		return commonDAO.popSmsSendList(map);
	}
	
	
	
}
