package oms.officework.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import oms.common.util.FileUtils;
import oms.officework.dao.OfficeworkDAO;


@Service("officeworkService")
public class OfficeworkServiceImpl implements OfficeworkService{
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="fileUtils")
	private FileUtils fileUtils;
	
	@Resource(name="officeworkDAO")
	private OfficeworkDAO officeworkDAO;
		
	
	@Override
	public List<Map<String, Object>> selectOfficeworkRegList(Map<String, Object> map) throws Exception {
		return officeworkDAO.selectOfficeworkRegList(map);
	}

	@Override
	public void insertofficeworkBasic(Map<String, Object> map, HttpServletRequest request) throws Exception {
		
		officeworkDAO.insertofficeworkBasic(map);
		
	}

	@Override
	public Map<String, Object> officeworkBasicDetail(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = officeworkDAO.officeworkBasicDetail(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}

	@Override
	public void updateOfficeworkBasic(Map<String, Object> map, HttpServletRequest request) throws Exception{
		officeworkDAO.updateOfficeworkBasic(map);
		
	}

	@Override
	public void deleteOfficeworkBasic(Map<String, Object> map) throws Exception {
		officeworkDAO.deleteOfficeworkBasic(map);
	}

	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public List<Map<String, Object>> selectConsignStatusList(Map<String, Object> map) throws Exception {
		return officeworkDAO.selectConsignStatusList(map);
	}
	
	@Override
	public void insertConsignStatus(Map<String, Object> map, HttpServletRequest request) throws Exception {
		officeworkDAO.insertConsignStatus(map);
		
		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
		for(int i=0, size=list.size(); i<size; i++){
			officeworkDAO.insertFile(list.get(i));
		}
				
	}
	
	@Override
	public Map<String, Object> consignStatusDetail(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = officeworkDAO.consignStatusDetail(map);
		resultMap.put("map", tempMap);
		
		List<Map<String,Object>> list = officeworkDAO.selectFileList(map);
		resultMap.put("list", list);
		
		return resultMap;
	}
	
	@Override
	public void updateConsignStatus(Map<String, Object> map, HttpServletRequest request) throws Exception{
		officeworkDAO.updateConsignStatus(map);
		
		officeworkDAO.deleteFileList(map);
		
		List<Map<String,Object>> list = fileUtils.parseUpdateFileInfo(map, request);
		Map<String,Object> tempMap = null;
		for(int i=0, size=list.size(); i<size; i++){
			tempMap = list.get(i);
			if(tempMap.get("IS_NEW").equals("Y")){
				officeworkDAO.insertFile(tempMap);
			}
			else{
				officeworkDAO.updateFile(tempMap);
			}
		}
		
	}

	@Override
	public void deleteConsignStatus(Map<String, Object> map) throws Exception {
		officeworkDAO.deleteConsignStatus(map);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public List<Map<String, Object>> selectBudgetStatusList(Map<String, Object> map) throws Exception {
		return officeworkDAO.selectBudgetStatusList(map);
	}
	
	@Override
	public void insertBudgetStatus(Map<String, Object> map, HttpServletRequest request) throws Exception {
		officeworkDAO.insertBudgetStatus(map);
		
	}
		
	@Override
	public Map<String, Object> budgetStatusDetail(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = officeworkDAO.budgetStatusDetail(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public void updateBudgetStatus(Map<String, Object> map, HttpServletRequest request) throws Exception{
		officeworkDAO.updateBudgetStatus(map);
	}

	@Override
	public void deleteBudgetStatus(Map<String, Object> map) throws Exception {
		officeworkDAO.deleteBudgetStatus(map);
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public List<Map<String, Object>> selectSuperviseStatusList(Map<String, Object> map) throws Exception {
		return officeworkDAO.selectSuperviseStatusList(map);
	}
	
	@Override
	public List<Map<String, Object>> selectManageEvalStatusList(Map<String, Object> map) throws Exception {
		return officeworkDAO.selectManageEvalStatusList(map);
	}
	
	@Override
	public void insertSuperviseStatus(Map<String, Object> map, HttpServletRequest request) throws Exception {
		officeworkDAO.insertSuperviseStatus(map);
		
	}
	
	@Override
	public void insertManageEvalStatus(Map<String, Object> map, HttpServletRequest request) throws Exception {
		officeworkDAO.insertManageEvalStatus(map);
		
	}
	
	@Override
	public Map<String, Object> superviseStatusDetail(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = officeworkDAO.superviseStatusDetail(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public void updateSuperviseStatus(Map<String, Object> map, HttpServletRequest request) throws Exception{
		officeworkDAO.updateSuperviseStatus(map);
	}

	@Override
	public void deleteSuperviseStatus(Map<String, Object> map) throws Exception {
		officeworkDAO.deleteSuperviseStatus(map);
	}
	
	
	@Override
	public Map<String, Object> manageEvalStatusDetail(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = officeworkDAO.manageEvalStatusDetail(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public void updateManageEvalStatus(Map<String, Object> map, HttpServletRequest request) throws Exception{
		officeworkDAO.updateManageEvalStatus(map);
	}

	@Override
	public void deleteManageEvalStatus(Map<String, Object> map) throws Exception {
		officeworkDAO.deleteManageEvalStatus(map);
	}
	
	
	
	
	
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public List<Map<String, Object>> selectqualiDelibStatusList(Map<String, Object> map) throws Exception {
		return officeworkDAO.selectqualiDelibStatusList(map);
	}
	
	@Override
	public List<Map<String, Object>> selectguAssembAgreementList(Map<String, Object> map) throws Exception {
		return officeworkDAO.selectguAssembAgreementList(map);
	}
	
	@Override
	public void insertQualiDelibStatus(Map<String, Object> map, HttpServletRequest request) throws Exception {
		officeworkDAO.insertQualiDelibStatus(map);
		
	}
	
	@Override
	public void insertGuAssembAgreement(Map<String, Object> map, HttpServletRequest request) throws Exception {
		officeworkDAO.insertGuAssembAgreement(map);
		
	}
	
	
	@Override
	public Map<String, Object> qualiDelibStatusDetail(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = officeworkDAO.qualiDelibStatusDetail(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public void updateQualiDelibStatus(Map<String, Object> map, HttpServletRequest request) throws Exception{
		officeworkDAO.updateQualiDelibStatus(map);
	}

	@Override
	public void deleteQualiDelibStatus(Map<String, Object> map) throws Exception {
		officeworkDAO.deleteQualiDelibStatus(map);
	}
	
	@Override
	public Map<String, Object> guAssembAgreementUpdateDetail(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = officeworkDAO.guAssembAgreementUpdateDetail(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public void updateGuAssembAgreement(Map<String, Object> map, HttpServletRequest request) throws Exception{
		officeworkDAO.updateGuAssembAgreement(map);
	}

	@Override
	public void deleteGuAssembAgreement(Map<String, Object> map) throws Exception {
		officeworkDAO.deleteGuAssembAgreement(map);
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	@Override
	public List<Map<String, Object>> selectTrustOworkStatusList(Map<String, Object> map, HttpServletRequest request) throws Exception {
		//log.debug("\t impl inquery #######  \t:  " + map);
		return officeworkDAO.selectTrustOworkStatusList(map);
	}
}
