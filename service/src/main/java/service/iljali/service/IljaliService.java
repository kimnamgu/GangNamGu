package service.iljali.service;

import java.util.List;
import java.util.Map;

public interface IljaliService {

	/*임기제 공무원*/
	Map<String, Object> insertTermOfficialInfo(Map<String, Object> map) throws Exception;
	
	Map<String, Object> updateVolunteerTermOfficialInfo(Map<String, Object> map) throws Exception;
	
	Map<String, Object> updateTermOfficialPaymentYN(Map<String, Object> map) throws Exception;
	
	Map<String, Object> updateTermOfficialMaster(Map<String, Object> map) throws Exception;
	
	Map<String, Object> deleteTermOfficialInfo(Map<String, Object> map) throws Exception;
	
	Map<String, Object> getTermOfficialInfo(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectTermOfficialList(Map<String, Object> map) throws Exception;
	
	/*기간제 근로자*/
	Map<String, Object> insertTermWorkerInfo(Map<String, Object> map) throws Exception;
	
	Map<String, Object> updateVolunteerTermWorkerInfo(Map<String, Object> map) throws Exception;
	
	Map<String, Object> updateTermWorkerMaster(Map<String, Object> map) throws Exception;
	
	Map<String, Object> deleteTermWorkerInfo(Map<String, Object> map) throws Exception;
	
	Map<String, Object> getTermWorkerInfo(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectTermWorkerList(Map<String, Object> map) throws Exception;
	
	/*공공근로사업참여자*/
	Map<String, Object> insertPublicBusinessInfo(Map<String, Object> map) throws Exception;
	
	Map<String, Object> updateVolunteerPublicBusinessInfo(Map<String, Object> map) throws Exception;
	
	Map<String, Object> updatePublicBusinessMaster(Map<String, Object> map) throws Exception;
	
	Map<String, Object> deletePublicBusinessInfo(Map<String, Object> map) throws Exception;
	
	Map<String, Object> getPublicBusinessInfo(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectPublicBusinessList(Map<String, Object> map) throws Exception;
	
	/*고시공고 좋아요*/
	Map<String, Object> insertGood(Map<String, Object> map) throws Exception;
	
	Map<String, Object> deleteGood(Map<String, Object> map) throws Exception;
	
	Map<String, Object> getGood(Map<String, Object> map) throws Exception;
	
	/*고시공고 스크랩*/
	Map<String, Object> insertScrap(Map<String, Object> map) throws Exception;
	
	Map<String, Object> deleteScrap(Map<String, Object> map) throws Exception;
	
	Map<String, Object> getScrap(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectMyScrapList(Map<String, Object> map) throws Exception;
	
	
	/*첨부파일*/
	Map<String, Object> fileupload(Map<String, Object> map) throws Exception;
	
	Map<String, Object> selectFile(Map<String, Object> map) throws Exception;
	
	/*파일명관리*/
	Map<String, Object> insertMangeIljaliFile(Map<String, Object> map) throws Exception;
	
	Map<String, Object> deleteMangeIljaliFile(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectMangeIljaliFileList(Map<String, Object> map) throws Exception;
	
	
	/*관리자 화면*/
	List<Map<String, Object>> selectTermOfficialViewList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectTermWorkerViewList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectPublicBusinessViewList(Map<String, Object> map) throws Exception;

}

