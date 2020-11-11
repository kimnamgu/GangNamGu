package sds.diagnose.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface DiagnoseService {

	List<Map<String, Object>> selectSelfDgnsMastList(Map<String, Object> map) throws Exception;
	
	Map<String, Object> selfDgnsContentView(Map<String, Object> map) throws Exception;
	
	void insertDgnsMast(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void updateDgnsMast(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void deleteDgnsMast(Map<String, Object> map, HttpServletRequest request) throws Exception;

	
	void insertDgnsSubj(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void excelUpload(Map<String, Object> map, HttpServletRequest request, File destFile, String flag) throws Exception;
	
	Map<String, Object> selfDgnsBasicInfo(Map<String, Object> map) throws Exception;
	
	
		
}
