package service.iljali.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import service.common.dao.AbstractDAO;

@Repository("iljaliDAO")
public class IljaliDAO extends AbstractDAO{

	/*임기제 공무원*/
	@SuppressWarnings("unchecked")
	public Map<String, Object> insertTermOfficialInfo(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) insert("iljali.insertTermOfficialInfo", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> updateVolunteerTermOfficialInfo(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) update("iljali.updateVolunteerTermOfficialInfo", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> updateTermOfficialPaymentYN(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) update("iljali.updateTermOfficialPaymentYN", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> updateTermOfficialMaster(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) update("iljali.updateTermOfficialMaster", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> deleteTermOfficialInfo(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) update("iljali.deleteTermOfficialInfo", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getTermOfficialInfo(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("iljali.getTermOfficialInfo", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectTermOfficialList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("iljali.selectTermOfficialList", map);
	}
	
	/*기간제 근로자*/
	@SuppressWarnings("unchecked")
	public Map<String, Object> insertTermWorkerInfo(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) insert("iljali.insertTermWorkerInfo", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> updateVolunteerTermWorkerInfo(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) update("iljali.updateVolunteerTermWorkerInfo", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> updateTermWorkerMaster(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) update("iljali.updateTermWorkerMaster", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> deleteTermWorkerInfo(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) update("iljali.deleteTermWorkerInfo", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getTermWorkerInfo(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("iljali.getTermWorkerInfo", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectTermWorkerList(Map<String, Object> map) throws Exception{
		System.out.println("map 확인 : " + map.toString());
		return (List<Map<String, Object>>)selectPagingList("iljali.selectTermWorkerList", map);
	}
	
	
	/*공공근로사업참여자*/
	@SuppressWarnings("unchecked")
	public Map<String, Object> insertPublicBusinessInfo(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) insert("iljali.insertPublicBusinessInfo", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> updateVolunteerPublicBusinessInfo(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) update("iljali.updateVolunteerPublicBusinessInfo", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> updatePublicBusinessMaster(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) update("iljali.updatePublicBusinessMaster", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> deletePublicBusinessInfo(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) update("iljali.deletePublicBusinessInfo", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getPublicBusinessInfo(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("iljali.getPublicBusinessInfo", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectPublicBusinessList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("iljali.selectPublicBusinessList", map);
	}
	
	
	/*고시공고 좋아요*/
	@SuppressWarnings("unchecked")
	public Map<String, Object> insertGood(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) insert("iljali.insertGood", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> deleteGood(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) update("iljali.deleteGood", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getGood(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("iljali.getGood", map);
	}
	
	/*고시공고 스크랩*/
	@SuppressWarnings("unchecked")
	public Map<String, Object> insertScrap(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) insert("iljali.insertScrap", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> deleteScrap(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) update("iljali.deleteScrap", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getScrap(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("iljali.getScrap", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectMyScrapList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("iljali.selectMyScrapList", map);
	}
	
	/*첨부파일*/
	@SuppressWarnings("unchecked")
	public Map<String, Object> fileupload(Map<String, Object> map) throws Exception{
		System.out.println("fileupload dao 타나 : " + map.toString());
		return (Map<String, Object>) insert("iljali.fileupload", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectFile(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("iljali.selectFile", map);
	}
	
	/*파일명 관리*/
	@SuppressWarnings("unchecked")
	public Map<String, Object> insertMangeIljaliFile(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) insert("iljali.insertMangeIljaliFile", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> deleteMangeIljaliFile(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) update("iljali.deleteMangeIljaliFile", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectMangeIljaliFileList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("iljali.selectMangeIljaliFileList", map);
	}
	
	/*관리자 화면*/
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectTermOfficialViewList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("iljali.selectTermOfficialViewList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectTermWorkerViewList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("iljali.selectTermWorkerViewList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectPublicBusinessViewList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("iljali.selectPublicBusinessViewList", map);
	}
	
	
}
