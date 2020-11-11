package service.daejang.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import service.common.util.CommonUtils;
import service.common.util.FileUtils;
import service.daejang.dao.DaejangDAO;

@Service("daejangService")
public class DaejangServiceImpl implements DaejangService{
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="fileUtils")
	private FileUtils fileUtils;
	
	@Resource(name="daejangDAO")
	private DaejangDAO daejangDAO;
	
	
	
	@Override
	public List<Map<String, Object>> docIssueReserveList(Map<String, Object> map) throws Exception {
		return daejangDAO.docIssueReserveList(map);
	}

	@Override
	public List<Map<String, Object>> selectDocIssueReserveList(Map<String, Object> map) throws Exception {
		
		List<Map<String, Object>> templist = daejangDAO.selectDocIssueReserveList(map);
		
		return CommonUtils.keyChangeLower(templist);
	}
	
	@Override
	public List<Map<String, Object>> selectDocIssueReserveViewList(Map<String, Object> map) throws Exception {
		
		List<Map<String, Object>> templist = daejangDAO.selectDocIssueReserveViewList(map);
		
		return CommonUtils.keyChangeLower(templist);
	}
	
	@Override
	public List<Map<String, Object>> selectStatisticsList(Map<String, Object> map) throws Exception {
		
		List<Map<String, Object>> templist = daejangDAO.selectStatisticsList(map);
		System.out.println("templist 확인 : " + templist.toString());
		return CommonUtils.keyChangeLower(templist);
	}
	
	@Override
	public List<Map<String, Object>> getDocumentKindList(Map<String, Object> map) throws Exception {
		
		List<Map<String, Object>> templist = daejangDAO.getDocumentKindList(map);
		
		return CommonUtils.keyChangeLower(templist);
	}
	
	@Override
	public Map<String, Object> getDocIssueReserveDetail(Map<String, Object> map, HttpServletRequest request) throws Exception {
		
		
		Map<String, Object> tempMap = daejangDAO.getDocIssueReserveDetail(map);
		log.debug("tempMap => (" + tempMap + ")");
	
		return CommonUtils.changToLowerMapKey(tempMap);
	}
	
	@Override
	public Map<String, Object> insertDocIssueReserve(Map<String, Object> map, HttpServletRequest request) throws Exception {
		
		Map<String, Object> rtmap = new HashMap<>();
		
		rtmap = daejangDAO.insertDocIssueReserve(map);
		return rtmap;
	}
		
	@Override
	public Map<String, Object> updateDocIssueReserve(Map<String, Object> map, HttpServletRequest request) throws Exception{
		
		Map<String, Object> rtmap = new HashMap<>();
		
		rtmap = daejangDAO.updateDocIssueReserve(map);
		return rtmap;
	}
	
	
	@Override
	public Map<String, Object> getDocIssueReserveCount() throws Exception{
		
		Map<String, Object> rtmap = new HashMap<>();
		
		rtmap = daejangDAO.getDocIssueReserveCount();
		
		return rtmap;
	}
	
	@Override
	public Map<String,Object> getDocIssueReserveRecent() throws Exception{
		
		Map<String, Object> rtmap = new HashMap<>();
		
		rtmap = daejangDAO.getDocIssueReserveRecent();
		
		return rtmap;
		
	}
	
	@Override
	public void updateSeoson(String dt) throws Exception{
		daejangDAO.updateSeoson(dt);
	}
	
	
	@Override
	public List<Map<String, Object>> selectDocIssueStaticsDayList(Map<String, Object> map) throws Exception {
		
		List<Map<String, Object>> templist = daejangDAO.selectDocIssueStaticsDayList(map);
		
		return CommonUtils.keyChangeLower(templist);
	}
	
	@Override
	public List<Map<String, Object>> selectDocIssueStaticsMonthList(Map<String, Object> map) throws Exception {
		
		List<Map<String, Object>> templist = daejangDAO.selectDocIssueStaticsMonthList(map);
		
		return CommonUtils.keyChangeLower(templist);
	}
	
	@Override
	public List<Map<String, Object>> selectDocIssueStaticsYearList(Map<String, Object> map) throws Exception {
		
		List<Map<String, Object>> templist = daejangDAO.selectDocIssueStaticsYearList(map);
		
		return CommonUtils.keyChangeLower(templist);
	}
	
	
	@Override
	public List<Map<String, Object>> selectDocPeeStaticsDayList(Map<String, Object> map) throws Exception {
		
		List<Map<String, Object>> templist = daejangDAO.selectDocPeeStaticsDayList(map);
		
		return CommonUtils.keyChangeLower(templist);
	}
	
	@Override
	public List<Map<String, Object>> selectDocPeeStaticsMonthList(Map<String, Object> map) throws Exception {
		
		List<Map<String, Object>> templist = daejangDAO.selectDocPeeStaticsMonthList(map);
		
		return CommonUtils.keyChangeLower(templist);
	}
	
	@Override
	public List<Map<String, Object>> selectDocPeeStaticsYearList(Map<String, Object> map) throws Exception {
		
		List<Map<String, Object>> templist = daejangDAO.selectDocPeeStaticsYearList(map);
		
		return CommonUtils.keyChangeLower(templist);
	}

	
}
