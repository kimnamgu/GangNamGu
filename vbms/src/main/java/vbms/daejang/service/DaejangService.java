package vbms.daejang.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface DaejangService {

	List<Map<String, Object>> selectViolBuildingList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectViolBuildingUpList(Map<String, Object> map) throws Exception;
	
	Map<String, Object> violBuildingContent(Map<String, Object> map) throws Exception;
	
	void insBldMngDaejang(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void updateBldMngDaejang(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void deleteBldMngDaejang(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void deleteViolBuildingUpload(Map<String, Object> map, HttpServletRequest request) throws Exception;

	
	void insertDgnsSubj(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void excelUpload(Map<String, Object> map, HttpServletRequest request, File destFile) throws Exception;
	
	Map<String, Object> selfDgnsBasicInfo(Map<String, Object> map) throws Exception;
	
	
		
}
