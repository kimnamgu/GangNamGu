package sosong.incident.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import sosong.common.dao.AbstractDAO;

@Repository("incidentDAO")
public class IncidentDAO extends AbstractDAO{

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectAllIncidentList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("incident.selectAllIncidentList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectAllSimList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("incident.selectAllSimList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectCrimIncidentList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("incident.selectCrimIncidentList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectIndIncidentList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("incident.selectIndIncidentList", map);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> incidentBasicDetail(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("incident.incidentBasicDetail", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> indTrialDetail(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("incident.indTrialDetail", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> crimListDetail(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("incident.crimListDetail", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectIncidentStatisticsList1(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("incident.selectIncidentStatisticsList1", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectIncidentStatisticsList2(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("incident.selectIncidentStatisticsList2", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectIncidentStatisticsList3(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("incident.selectIncidentStatisticsList3", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectIncidentStatisticsList4(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("incident.selectIncidentStatisticsList4", map);
	}
	
	
	
	public void insertIncidentBasic(Map<String, Object> map) throws Exception{
		insert("incident.insertIncidentBasic", map);
	}
	
	
	public void insertIndTrial(Map<String, Object> map) throws Exception{
		insert("incident.insertIndTrial", map);
	}
	
	public void insertCrimListMast(Map<String, Object> map) throws Exception{
		insert("incident.insertIncidentBasic", map);
	}
	
	public void insertCrimListSub(Map<String, Object> map) throws Exception{
		insert("incident.insertIndTrial", map);
	}
	
		
	public void updateIncidentBasic(Map<String, Object> map) throws Exception{
		update("incident.updateIncidentBasic", map);
	}
	
	public void deleteIncidentBasic(Map<String, Object> map) throws Exception{
		update("incident.deleteIncidentBasic", map);
	}
	
	public void updateIndTrial(Map<String, Object> map) throws Exception{
		update("incident.updateIndTrial", map);
	}
	
	public void deleteIndTrial(Map<String, Object> map) throws Exception{
		update("incident.deleteIndTrial", map);
	}
	
}
