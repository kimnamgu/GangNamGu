package bims.daejang.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface DaejangService {

	List<Map<String, Object>> selectMainSaupList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectApiMainSaupList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectMainSaupSubList(Map<String, Object> map) throws Exception;
	
	Map<String, Object> mainSaupDetail(Map<String, Object> map) throws Exception;
	
	Map<String, Object> mainSaupBasicDetail(Map<String, Object> map) throws Exception;
	
	Map<String, Object> mainSaupSubDetail(Map<String, Object> map) throws Exception;
	
	
	void insertMainSaup(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void insertMainSaupSub(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	
	void updateMainSaup(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void updateMainSaupSub(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	
	void deleteMainSaup(Map<String, Object> map, HttpServletRequest request) throws Exception;

	void deleteMainSaupSub(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	
	List<Map<String, Object>> mSaupList(Map<String, Object> map) throws Exception;
}
