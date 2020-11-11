package service.visitor.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import service.common.dao.AbstractDAO;

@Repository("visitorDAO")
public class VisitorDAO extends AbstractDAO{

	@SuppressWarnings("unchecked")
	public Map<String, Object> getVisitorInfo(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("visitor.getVisitorInfo", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getVisitorLocationInfo(Map<String, Object> map) throws Exception{
		System.out.println("getVisitorLocationInfo 맵확인 : " + map.toString());
		return (Map<String, Object>) selectOne("visitor.getVisitorLocationInfo", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> insertVisitorInfo(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) insert("visitor.insertVisitorInfo", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> updateVisitorInfo(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) update("visitor.updateVisitorInfo", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> insertFootprint(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) insert("visitor.insertFootprint", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectVisitorFootprintList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("visitor.selectVisitorFootprintList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectStatisticsList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("visitor.selectStatisticsList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectVisitorFootprintListExcel(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("visitor.selectVisitorFootprintListExcel", map);
	}

}
