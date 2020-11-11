package gift.daejang.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import gift.common.util.FileUtils;
import gift.daejang.dao.DaejangDAO;

@Service("daejangService")
public class DaejangServiceImpl implements DaejangService{
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="fileUtils")
	private FileUtils fileUtils;
	
	@Resource(name="daejangDAO")
	private DaejangDAO daejangDAO;
	
	@Override
	public List<Map<String, Object>> selectGiftAcceptList(Map<String, Object> map) throws Exception {
		return daejangDAO.selectGiftAcceptList(map);
	}

	@Override
	public List<Map<String, Object>> selectGiftPrintList(Map<String, Object> map) throws Exception {
		return daejangDAO.selectGiftPrintList(map);
	}
	
	
	@Override
	public void insertGiftAccept(Map<String, Object> map, HttpServletRequest request) throws Exception {
		daejangDAO.insertGiftAccept(map);
		
		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
		for(int i=0, size=list.size(); i<size; i++){
			daejangDAO.insertFile(list.get(i));
		}
	}
	
	@Override
	public Map<String, Object> giftAcceptDetail(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = daejangDAO.giftAcceptDetail(map);
		resultMap.put("map", tempMap);
		
		List<Map<String,Object>> list = daejangDAO.selectFileList(map);
		resultMap.put("list", list);
		
		return resultMap;
	}
	
	
	@Override
	public void updateGiftAccept(Map<String, Object> map, HttpServletRequest request) throws Exception{
		daejangDAO.updateGiftAccept(map);
		
		daejangDAO.deleteFileList(map);
		List<Map<String,Object>> list = fileUtils.parseUpdateFileInfo(map, request);
		Map<String,Object> tempMap = null;
		for(int i=0, size=list.size(); i<size; i++){
			tempMap = list.get(i);
			if(tempMap.get("IS_NEW").equals("Y")){
				daejangDAO.insertFile(tempMap);
			}
			else{
				daejangDAO.updateFile(tempMap);
			}
		}
		
	}

	@Override
	public void deleteGiftAccept(Map<String, Object> map) throws Exception {
		daejangDAO.deleteGiftAccept(map);
	}

	
	
	
	@Override
	public Map<String, Object> giftMngStatus(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = daejangDAO.giftMngStatus(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	
	@Override
	public void insertGiftMng(Map<String, Object> map, HttpServletRequest request) throws Exception {
		daejangDAO.insertGiftMng(map);
		
		
	}
	
	@Override
	public void updateGiftMng(Map<String, Object> map, HttpServletRequest request) throws Exception{
		daejangDAO.updateGiftMng(map);
	
	}
	
	@Override
	public void deleteGiftMng(Map<String, Object> map, HttpServletRequest request) throws Exception{
		daejangDAO.deleteGiftMng(map);
	
	}
	
	
	
	
	@Override
	public Map<String, Object> giftSellStatus(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = daejangDAO.giftSellStatus(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	
	@Override
	public void insertGiftSell(Map<String, Object> map, HttpServletRequest request) throws Exception {
		daejangDAO.insertGiftSell(map);	
	
	}
	
	
	
	@Override
	public void updateGiftSell(Map<String, Object> map, HttpServletRequest request) throws Exception{
		daejangDAO.updateGiftSell(map);
	
	}
	
	@Override
	public void deleteGiftSell(Map<String, Object> map, HttpServletRequest request) throws Exception{
		daejangDAO.deleteGiftSell(map);
	
	}
}
