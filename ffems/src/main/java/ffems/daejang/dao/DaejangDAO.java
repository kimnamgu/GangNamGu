package ffems.daejang.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ffems.common.dao.AbstractDAO;

@Repository("daejangDAO")
public class DaejangDAO extends AbstractDAO{

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectFfemsDataList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectFfemsDataList", map);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectFastFinanceList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectList("daejang.selectFastFinanceList", map);
	}
	
		
	public int insertFinanceData(Map<String, Object> map) throws Exception{
		int rtn = 0;
		rtn = insert("daejang.insertFinanceData", map);
		return rtn;
		
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> ffemsDataDetail(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("daejang.ffemsDataDetail", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> mainSaupBasicDetail(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("daejang.mainSaupBasicDetail", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> mainSaupSubDetail(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("daejang.mainSaupSubDetail", map);
	}
	
	
	public void updateMainSaup(Map<String, Object> map) throws Exception{
		update("daejang.updateMainSaupBasic", map);
	}
	
	public void updateMainSaupSub(Map<String, Object> map) throws Exception{
		update("daejang.updateMainSaupSub", map);
	}

	public void deleteMainSaup(Map<String, Object> map) throws Exception{
		update("daejang.deleteMainSaup", map);
	}
	
	public void deleteMainSaupSub(Map<String, Object> map) throws Exception{
		update("daejang.deleteMainSaupSub", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> mSaupList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.mSaupList", map);
	}
	
	public Integer insertFile(Map<String, Object> map) throws Exception{
		insert("daejang.insertFile", map);
		
		return (Integer) map.get("FID");
	}
	

}
