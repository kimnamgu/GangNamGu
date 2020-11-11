package vbms.daejang.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import vbms.common.dao.AbstractDAO;

@Repository("daejangDAO")
public class DaejangDAO extends AbstractDAO{

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectViolBuildingList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectViolBuildingList", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectViolBuildingUpList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectViolBuildingUpList", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> violBuildingContent(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("daejang.violBuildingContent", map);
	}
	
	

	
	
	public void insBldMngDaejang(Map<String, Object> map) throws Exception{
		insert("daejang.insBldMngDaejang", map);
	}
	
	public void updateBldMngDaejang(Map<String, Object> map) throws Exception{
		update("daejang.updateBldMngDaejang", map);
	}
	
	
	public void deleteBldMngDaejang(Map<String, Object> map) throws Exception{
		update("daejang.deleteBldMngDaejang", map);
	}
	
	
	public void deleteViolBuildingUploadData(Map<String, Object> map) throws Exception{
		update("daejang.deleteBldMngDaejangData", map);
	}
	
	public void deleteViolBuildingUploadFile(Map<String, Object> map) throws Exception{
		update("daejang.deleteFile", map);
	}

	
	public Integer insertFile(Map<String, Object> map) throws Exception{
		insert("daejang.insertFile", map);
		
		return (Integer) map.get("FID");
	}
	
	
}
