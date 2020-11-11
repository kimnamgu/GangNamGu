package sosong.incident.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import sosong.common.util.FileUtils;
import sosong.incident.dao.IncidentDAO;
import java.sql.SQLException;

@Service("incidentService")
public class IncidentServiceImpl implements IncidentService{
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="fileUtils")
	private FileUtils fileUtils;
	
	@Resource(name="incidentDAO")
	private IncidentDAO incidentDAO;
	
	@Override
	public List<Map<String, Object>> selectAllIncidentList(Map<String, Object> map) throws Exception {
		return incidentDAO.selectAllIncidentList(map);
	}
	
	@Override
	public List<Map<String, Object>> selectAllSimList(Map<String, Object> map) throws Exception {
		return incidentDAO.selectAllSimList(map);
	}
	
	@Override
	public List<Map<String, Object>> selectCrimIncidentList(Map<String, Object> map) throws Exception {
		return incidentDAO.selectCrimIncidentList(map);
	}
	
	@Override
	public List<Map<String, Object>> selectIndIncidentList(Map<String, Object> map) throws Exception {
		return incidentDAO.selectIndIncidentList(map);
	}
	

	@Override
	public Map<String, Object> incidentBasicDetail(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = incidentDAO.incidentBasicDetail(map);
		resultMap.put("map", tempMap);
		/*
		List<Map<String,Object>> list = incidentDAO.selectFileList(map);
		resultMap.put("list", list);
		*/
		return resultMap;
	}
	
	
	@Override
	public Map<String, Object> indTrialDetail(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = incidentDAO.indTrialDetail(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	
	@Override
	public Map<String, Object> crimListDetail(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = incidentDAO.crimListDetail(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	
	@Override
	public List<Map<String, Object>> selectIncidentStatisticsList1(Map<String, Object> map) throws Exception {
		return incidentDAO.selectIncidentStatisticsList1(map);
	}
	
	@Override
	public List<Map<String, Object>> selectIncidentStatisticsList2(Map<String, Object> map) throws Exception {
		return incidentDAO.selectIncidentStatisticsList2(map);
	}
	
	@Override
	public List<Map<String, Object>> selectIncidentStatisticsList3(Map<String, Object> map) throws Exception {
		return incidentDAO.selectIncidentStatisticsList3(map);
	}
	
	@Override
	public List<Map<String, Object>> selectIncidentStatisticsList4(Map<String, Object> map) throws Exception {
		return incidentDAO.selectIncidentStatisticsList4(map);
	}
	
	
	
	@Override
	public void insertIncidentBasic(Map<String, Object> map, HttpServletRequest request) throws Exception {
		incidentDAO.insertIncidentBasic(map);
	}
	
	
	@Override
	public void insertIndTrial(Map<String, Object> map, HttpServletRequest request) throws Exception {
		incidentDAO.insertIndTrial(map);
	}
	
	
	@Override
	public int insertCrimList(Map<String, Object> map, HttpServletRequest request) throws SQLException {
		try{
			incidentDAO.insertCrimListMast(map);
		
			//log.debug("#########crimId= " + "[" + map.get("ID") + "]");
			map.put("MAST_ID", map.get("ID") );
			map.put("ICDNT_NO", "crim" + map.get("ID") ); 		
			incidentDAO.insertCrimListSub(map);
			return 200;
		}catch(Exception e){
			 e.printStackTrace();
	         TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	         return 500;
		}
		
	}
	
	
	@Override
	public void updateIncidentBasic(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		
		incidentDAO.updateIncidentBasic(map);
		
	}
	
	@Override
	public void deleteIncidentBasic(Map<String, Object> map, HttpServletRequest request) throws Exception{
		incidentDAO.deleteIncidentBasic(map); //Main 데이터
		incidentDAO.deleteIndTrial(map); //Sub 데이터
	}
	
	
	@Override
	public void updateIndTrial(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		
		incidentDAO.updateIndTrial(map);
		
	}
	
	@Override
	public void deleteIndTrial(Map<String, Object> map, HttpServletRequest request) throws Exception{
		incidentDAO.deleteIndTrial(map);
	
	}
	
	
	@Override
	public void updateCrimList(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		
		
		incidentDAO.updateIncidentBasic(map);
		
		incidentDAO.updateIndTrial(map);
		
	}
	
	@Override
	public void deleteCrimList(Map<String, Object> map, HttpServletRequest request) throws Exception{
		
		incidentDAO.deleteIncidentBasic(map);
		
		incidentDAO.deleteIndTrial(map);	
	}
	
}
