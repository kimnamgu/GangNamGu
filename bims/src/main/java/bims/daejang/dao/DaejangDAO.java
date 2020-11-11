package bims.daejang.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import bims.common.dao.AbstractDAO;

@Repository("daejangDAO")
public class DaejangDAO extends AbstractDAO{

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectMainSaupList(Map<String, Object> map) throws Exception{
		System.out.println("map 확인 : " + map.toString());
		return (List<Map<String, Object>>)selectPagingList("daejang.selectMainSaupList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectApiMainSaupList(Map<String, Object> map) throws Exception{
		System.out.println("map 확인 : " + map.toString());
		return (List<Map<String, Object>>)selectPagingList("daejang.selectApiMainSaupList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectMainSaupSubList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectMainSaupSubList", map);
	}
	
		
	public void insertMainSaup(Map<String, Object> map) throws Exception{
		insert("daejang.insertMainSaup", map);
	}
	
	
	public void insertMainSaupSub(Map<String, Object> map) throws Exception{
		insert("daejang.insertMainSaupSub", map);
	}
	
	public void insertFile(Map<String, Object> map) throws Exception{
		insert("common.insertFile", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> mainSaupDetail(Map<String, Object> map) throws Exception{
		System.out.println("mainSaupDetail Ȯ�� ");
		return (Map<String, Object>) selectOne("daejang.mainSaupDetail", map);
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

}
