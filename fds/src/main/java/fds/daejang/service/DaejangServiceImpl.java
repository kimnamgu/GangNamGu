package fds.daejang.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import fds.common.util.FileUtils;
import fds.daejang.dao.DaejangDAO;

@Service("daejangService")
public class DaejangServiceImpl implements DaejangService{
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="fileUtils")
	private FileUtils fileUtils;
	
	@Resource(name="daejangDAO")
	private DaejangDAO daejangDAO;
	
	@Override
	public List<Map<String, Object>> selectFixedDateList(Map<String, Object> map) throws Exception {
		return daejangDAO.selectFixedDateList(map);
	}

	
	@Override
	public Map<String, Object> fixedDateDataDetail(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = daejangDAO.fixedDateDataDetail(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	
	@Override
	public void insertFixedDateData(Map<String, Object> map, HttpServletRequest request) throws Exception {
		
		String file_nm = null;		
		String s_dongcd = null;
		String[] arrayType = null;
		
		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
		
		//log.debug("######### impl file = " + "[" + list.get(0).get("ORIGINAL_FILE_NM") + "]");
		file_nm = (String) list.get(0).get("ORIGINAL_FILE_NM");
		arrayType =  file_nm.split("_");
		s_dongcd = arrayType[0];
				
		map.put("DONG_CD_OLD", s_dongcd);
		map.put("LINK_IMG_NM", list.get(0).get("ORIGINAL_FILE_NM"));
		
		daejangDAO.insertFixedDateData(map);
	}
	
	
	@Override
	public void updateFixedDateData(Map<String, Object> map, HttpServletRequest request) throws Exception{
		
		String s_file = null;
		String file_nm = null;		
		String s_dongcd = null;		
		String[] arrayType = null;
		
		s_file = request.getParameter("file");
		log.debug("### s_file = " + "[" + s_file + "]");
		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);		
		
		if(list.isEmpty() == false)
		{	
			file_nm = (String) list.get(0).get("ORIGINAL_FILE_NM");
			
			log.debug("### file_nm = " + "[" + file_nm + "]");
			
			arrayType =  file_nm.split("_");
			s_dongcd = arrayType[0];
					
			map.put("DONG_CD_OLD", s_dongcd);
			map.put("LINK_IMG_NM", list.get(0).get("ORIGINAL_FILE_NM"));
	    }
		
		daejangDAO.updateFixedDateData(map);
		
		
	}

	@Override
	public void deleteFixedDateData(Map<String, Object> map, HttpServletRequest request) throws Exception {
		daejangDAO.deleteFixedDateData(map);
	}

	
	
}
