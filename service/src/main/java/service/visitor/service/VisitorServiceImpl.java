package service.visitor.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import service.common.util.CommonUtils;
import service.visitor.dao.VisitorDAO;


@Service("visitorService")
public class VisitorServiceImpl implements VisitorService {
	Logger log = Logger.getLogger(this.getClass());
	
	
	@Resource(name="visitorDAO")
	private VisitorDAO visitorDAO;
	
	@Override
	public Map<String, Object> getVisitorInfo(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = visitorDAO.getVisitorInfo(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	@Override
	public Map<String, Object> getVisitorLocationInfo(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = visitorDAO.getVisitorLocationInfo(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	
	@Override
	public Map<String, Object> insertVisitorInfo(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = visitorDAO.insertVisitorInfo(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	@Override
	public Map<String, Object> updateVisitorInfo(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = visitorDAO.updateVisitorInfo(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	@Override
	public Map<String, Object> insertFootprint(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = visitorDAO.insertFootprint(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	
	@Override
	public List<Map<String, Object>> selectVisitorFootprintList(Map<String, Object> map) throws Exception {
		return visitorDAO.selectVisitorFootprintList(map);
	}
	
	@Override
	public List<Map<String, Object>> selectStatisticsList(Map<String, Object> map) throws Exception {
		
		List<Map<String, Object>> templist = visitorDAO.selectStatisticsList(map);
		return CommonUtils.keyChangeLower(templist);
	}
	
	@Override
	public List<Map<String, Object>> selectVisitorFootprintListExcel(Map<String, Object> map) throws Exception {
		return visitorDAO.selectVisitorFootprintListExcel(map);
	}
	
}
