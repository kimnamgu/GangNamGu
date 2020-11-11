package manpower.write.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import manpower.common.util.FileUtils;
import manpower.write.dao.WriteDAO;


@Service("writeService")
public class WriteServiceImpl implements WriteService{
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="fileUtils")
	private FileUtils fileUtils;
	
	@Resource(name="writeDAO")
	private WriteDAO writeDAO;

	/*공지사항*/
	@Override
	public void insertNoticeWrite(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		writeDAO.insertNoticeWrite(map);
	}
	
	@Override
	public Map<String, Object> selectNoticeDetail(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = writeDAO.selectNoticeDetail(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public void updateNotice(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		writeDAO.updateNotice(map);
	}
	
	/*은행코드관리*/
	@Override
	public void insertBankWrite(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		writeDAO.insertBankWrite(map);
	}
	
	@Override
	public Map<String, Object> selectBankDetail(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = writeDAO.selectBankDetail(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public void updateBank(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		writeDAO.updateBank(map);
	}
	
	
}