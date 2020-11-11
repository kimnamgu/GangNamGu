package service.daejang.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface DaejangService {

	List<Map<String, Object>> docIssueReserveList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectDocIssueReserveList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectDocIssueReserveViewList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectStatisticsList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> getDocumentKindList(Map<String, Object> map) throws Exception;
	
	Map<String, Object> getDocIssueReserveDetail(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	Map<String, Object> insertDocIssueReserve(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	Map<String, Object> updateDocIssueReserve(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	Map<String, Object> getDocIssueReserveCount() throws Exception;
	
	Map<String,Object> getDocIssueReserveRecent(Map<String, Object> map) throws Exception;
	
	void updateSeoson(String dt) throws Exception;
	List<Map<String, Object>> selectSeosonList() throws Exception;
	
	Map<String,Object> getDocumentKindInfo(Map<String, Object> map) throws Exception;
	
	
	List<Map<String, Object>> selectDocIssueStaticsDayList(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectDocIssueStaticsMonthList(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectDocIssueStaticsYearList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectDocPeeStaticsDayList(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectDocPeeStaticsMonthList(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectDocPeeStaticsYearList(Map<String, Object> map) throws Exception;
	
}
