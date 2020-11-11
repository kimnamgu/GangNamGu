package fds.daejang.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface DaejangService {

	List<Map<String, Object>> selectFixedDateList(Map<String, Object> map) throws Exception;
	
	Map<String, Object> fixedDateDataDetail(Map<String, Object> map) throws Exception;
	
	
	void insertFixedDateData(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void updateFixedDateData(Map<String, Object> map, HttpServletRequest request) throws Exception;

	void deleteFixedDateData(Map<String, Object> map, HttpServletRequest request) throws Exception;
		
}