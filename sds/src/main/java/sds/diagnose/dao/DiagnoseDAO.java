package sds.diagnose.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import sds.common.dao.AbstractDAO;

@Repository("diagnoseDAO")
public class DiagnoseDAO extends AbstractDAO{

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectSelfDgnsMastList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("diagnose.selectSelfDgnsMastList", map);
	}
		
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selfDgnsContentView(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("diagnose.selfDgnsContentView", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selfDgnsSubjList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectList("diagnose.selfDgnsSubjList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selfDgnsQstList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectList("diagnose.selfDgnsQstList", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selfDgnsExamList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectList("diagnose.selfDgnsExamList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selfDgnsQstListCnt(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectList("diagnose.selfDgnsQstListCnt", map);
	}
	
	
	
	
	public void insertDgnsMast(Map<String, Object> map) throws Exception{
		insert("diagnose.insertDgnsMast", map);
	}
	
	public void updateDgnsMast(Map<String, Object> map) throws Exception{
		update("diagnose.updateDgnsMast", map);
	}
	
	
	public void deleteDgnsMast(Map<String, Object> map) throws Exception{
		update("diagnose.deleteDgnsMast", map);
	}
	
	
	
	
	public void insertDgnsSubj(Map<String, Object> map) throws Exception{
		insert("diagnose.insertDgnsSubj", map);
	}
	
	
	public void insertDgnsQuest(Map<String, Object> map) throws Exception{
		insert("diagnose.insertDgnsQuest", map);
	}
	
	
	public void insertDgnsExamp(Map<String, Object> map) throws Exception{
		insert("diagnose.insertDgnsExamp", map);
	}
	
	
	
	public void updateDgnsSubj(Map<String, Object> map) throws Exception{
		update("diagnose.updateDgnsSubj", map);
	}
	
	public void updateDgnsQuest(Map<String, Object> map) throws Exception{
		update("diagnose.updateDgnsQuest", map);
	}
	
	public void updateDgnsExamp(Map<String, Object> map) throws Exception{
		update("diagnose.updateDgnsExamp", map);
	}
	
	
	
	
}
