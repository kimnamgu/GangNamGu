package service.visitor.service;

import java.util.List;
import java.util.Map;

public interface VisitorService {
	Map<String, Object> getVisitorInfo(Map<String, Object> map) throws Exception;
	
	Map<String, Object> getVisitorLocationInfo(Map<String, Object> map) throws Exception;
	
	Map<String, Object> insertVisitorInfo(Map<String, Object> map) throws Exception;
	
	Map<String, Object> updateVisitorInfo(Map<String, Object> map) throws Exception;
	
	Map<String, Object> insertFootprint(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectVisitorFootprintList(Map<String, Object> map) throws Exception;

	List<Map<String, Object>> selectStatisticsList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectVisitorFootprintListExcel(Map<String, Object> map) throws Exception;
}
