package ffems.daejang.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface DaejangService {

	List<Map<String, Object>> selectFfemsDataList(Map<String, Object> map) throws Exception;
	
	Map<String, Object> ffemsDataDetail(Map<String, Object> map) throws Exception;
		
	void insertMainSaup(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	int excelUpload(Map<String, Object> map, HttpServletRequest request, File destFile) throws Exception;
	
}
