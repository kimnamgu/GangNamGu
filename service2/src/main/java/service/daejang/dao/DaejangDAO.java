package service.daejang.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import service.common.dao.AbstractDAO;

@Repository("daejangDAO")
public class DaejangDAO extends AbstractDAO{

	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> docIssueReserveList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.docIssueReserveList", map);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectDocIssueReserveList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectDocIssueReserveList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectDocIssueReserveViewList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectDocIssueReserveViewList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectStatisticsList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectStatisticsList", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getDocumentKindList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectList("daejang.getDocumentKindList", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getDocIssueReserveDetail(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("daejang.docIssueReserveDetail", map);
	}
	
	
	public Map<String, Object> insertDocIssueReserve(Map<String, Object> map) throws Exception{
		
		Map<String, Object> rtmap = new HashMap<>();
		
		rtmap = insert("daejang.insertDocIssueReserve", map);
		
		return rtmap;
	}
	public Map<String, Object> updateDocIssueReserve(Map<String, Object> map) throws Exception{
		Map<String, Object> rtmap = new HashMap<>();
		System.out.println("map 확인 : " + map.toString());
		rtmap = update("daejang.updateDocIssueReserve", map);
		log.debug("log DaejangDao rtn==> [" + rtmap + "]");
		return rtmap;
	}
	
	
	public Map<String, Object> getDocIssueReserveCount() throws Exception{
		Map<String, Object> rtmap = new HashMap<>();
		
		return (Map<String, Object>) selectOne("daejang.getDocIssueReserveCount");
	}
	
	public Map<String, Object> getDocIssueReserveRecent() throws Exception{
		
		Map<String, Object> rtmap = new HashMap<>();
		
		rtmap = (Map<String, Object>) selectOne("daejang.getDocIssueReserveRecent");
		log.debug("log DaejangDao rtn==> [" + rtmap + "]");
		return rtmap;
	}
	
	public void updateSeoson(String dt) throws Exception{
		Map<String, Object> rtmap = new HashMap<>();
		
		rtmap = update("daejang.updateSeoson", dt);
		log.debug("log DaejangDao rtn==> [" + rtmap + "]");
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectDocIssueStaticsDayList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectDocIssueStaticsDayList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectDocIssueStaticsMonthList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectDocIssueStaticsMonthList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectDocIssueStaticsYearList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectDocIssueStaticsYearList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectDocPeeStaticsDayList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectDocPeeStaticsDayList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectDocPeeStaticsMonthList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectDocPeeStaticsMonthList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectDocPeeStaticsYearList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectDocPeeStaticsYearList", map);
	}
}
