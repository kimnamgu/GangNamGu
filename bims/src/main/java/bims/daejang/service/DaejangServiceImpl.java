package bims.daejang.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import bims.common.util.FileUtils;
import bims.daejang.dao.DaejangDAO;

@Service("daejangService")
public class DaejangServiceImpl implements DaejangService{
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="fileUtils")
	private FileUtils fileUtils;
	
	@Resource(name="daejangDAO")
	private DaejangDAO daejangDAO;
	
	@Override
	public List<Map<String, Object>> selectMainSaupList(Map<String, Object> map) throws Exception {
		return daejangDAO.selectMainSaupList(map);
	}
	
	@Override
	public List<Map<String, Object>> selectApiMainSaupList(Map<String, Object> map) throws Exception {
		return daejangDAO.selectApiMainSaupList(map);
	}

	@Override
	public List<Map<String, Object>> selectMainSaupSubList(Map<String, Object> map) throws Exception {
		return daejangDAO.selectMainSaupSubList(map);
	}
	
	@Override
	public Map<String, Object> mainSaupDetail(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = daejangDAO.mainSaupDetail(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	
	@Override
	public Map<String, Object> mainSaupBasicDetail(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = daejangDAO.mainSaupBasicDetail(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	
	
	@Override
	public Map<String, Object> mainSaupSubDetail(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = daejangDAO.mainSaupSubDetail(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	
	@Override
	public void insertMainSaup(Map<String, Object> map, HttpServletRequest request) throws Exception {
		
		daejangDAO.insertMainSaup(map);
		
		/*List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
		for(int i=0, size=list.size(); i<size; i++){
			daejangDAO.insertFile(list.get(i));
		}*/
	}
	
	
	@Override
	public void insertMainSaupSub(Map<String, Object> map, HttpServletRequest request) throws Exception {
		
		daejangDAO.insertMainSaupSub(map);
		
		/*List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
		for(int i=0, size=list.size(); i<size; i++){
			daejangDAO.insertFile(list.get(i));
		}*/
	}
	
	
	
	@Override
	public void updateMainSaup(Map<String, Object> map, HttpServletRequest request) throws Exception{
		
		daejangDAO.updateMainSaup(map);
	}
	
	@Override
	public void updateMainSaupSub(Map<String, Object> map, HttpServletRequest request) throws Exception{
		
		daejangDAO.updateMainSaupSub(map);
	}

	@Override
	public void deleteMainSaup(Map<String, Object> map, HttpServletRequest request) throws Exception {
		daejangDAO.deleteMainSaup(map);
	}

	@Override
	public void deleteMainSaupSub(Map<String, Object> map, HttpServletRequest request) throws Exception {
		daejangDAO.deleteMainSaupSub(map);
	}
	
	
	@Override
	public List<Map<String, Object>> mSaupList(Map<String, Object> map) throws Exception {
		return daejangDAO.mSaupList(map);
	}
	
}
