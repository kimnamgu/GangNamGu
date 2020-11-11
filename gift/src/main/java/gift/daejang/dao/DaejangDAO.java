package gift.daejang.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import gift.common.dao.AbstractDAO;

@Repository("daejangDAO")
public class DaejangDAO extends AbstractDAO{

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectGiftAcceptList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectGiftAcceptList", map);
	}
		
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectGiftPrintList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectGiftPrintList", map);
	}

	public void insertGiftAccept(Map<String, Object> map) throws Exception{
		insert("daejang.insertGiftAccept", map);
	}
	
	public void insertFile(Map<String, Object> map) throws Exception{
		insert("common.insertFile", map);
	}
	
	public void deleteFileList(Map<String, Object> map) throws Exception{
		update("common.deleteFileList", map);
	}

	public void updateFile(Map<String, Object> map) throws Exception{
		update("common.updateFile", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> giftAcceptDetail(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("daejang.giftAcceptDetail", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectFileList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectList("common.selectFileList", map);
	}
	
	
	public void updateGiftAccept(Map<String, Object> map) throws Exception{
		update("daejang.updateGiftAccept", map);
	}

	public void deleteGiftAccept(Map<String, Object> map) throws Exception{
		update("daejang.deleteGiftAccept", map);
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> giftMngStatus(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("daejang.giftMngStatus", map);
	}
	
	
	public void insertGiftMng(Map<String, Object> map) throws Exception{
		update("daejang.insertGiftMng", map);
	}
	
	public void updateGiftMng(Map<String, Object> map) throws Exception{
		update("daejang.updateGiftMng", map);
	}
	
	public void deleteGiftMng(Map<String, Object> map) throws Exception{
		update("daejang.deleteGiftMng", map);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> giftSellStatus(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("daejang.giftSellStatus", map);
	}
	
	
	public void insertGiftSell(Map<String, Object> map) throws Exception{
		update("daejang.insertGiftSell", map);
	}
	
	
	public void updateGiftSell(Map<String, Object> map) throws Exception{
		update("daejang.updateGiftSell", map);
	}
	
	public void deleteGiftSell(Map<String, Object> map) throws Exception{
		update("daejang.deleteGiftSell", map);
	}

}
