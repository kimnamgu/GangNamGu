package service.iljali.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import service.common.util.CommonUtils;
import service.iljali.dao.IljaliDAO;

@Service("iljaliService")
public class IljaliServiceImpl implements IljaliService {
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="iljaliDAO")
	private IljaliDAO iljaliDAO;
	
	/*임기제 공무원*/
	@Override
	public Map<String, Object> insertTermOfficialInfo(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = iljaliDAO.insertTermOfficialInfo(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	@Override
	public Map<String, Object> updateVolunteerTermOfficialInfo(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = iljaliDAO.updateVolunteerTermOfficialInfo(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	@Override
	public Map<String, Object> updateTermOfficialPaymentYN(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = iljaliDAO.updateTermOfficialPaymentYN(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	@Override
	public Map<String, Object> updateTermOfficialMaster(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = iljaliDAO.updateTermOfficialMaster(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	@Override
	public Map<String, Object> deleteTermOfficialInfo(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = iljaliDAO.deleteTermOfficialInfo(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	@Override
	public Map<String, Object> getTermOfficialInfo(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = iljaliDAO.getTermOfficialInfo(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	@Override
	public List<Map<String, Object>> selectTermOfficialList(Map<String, Object> map) throws Exception {
		
		List<Map<String, Object>> templist = iljaliDAO.selectTermOfficialList(map);
		
		return CommonUtils.keyChangeLower(templist);
	}
	
	
	/*기간제 근로자*/
	@Override
	public Map<String, Object> insertTermWorkerInfo(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = iljaliDAO.insertTermWorkerInfo(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	@Override
	public Map<String, Object> updateVolunteerTermWorkerInfo(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = iljaliDAO.updateVolunteerTermWorkerInfo(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	@Override
	public Map<String, Object> updateTermWorkerMaster(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = iljaliDAO.updateTermWorkerMaster(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	@Override
	public Map<String, Object> deleteTermWorkerInfo(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = iljaliDAO.deleteTermWorkerInfo(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	@Override
	public Map<String, Object> getTermWorkerInfo(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = iljaliDAO.getTermWorkerInfo(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	@Override
	public List<Map<String, Object>> selectTermWorkerList(Map<String, Object> map) throws Exception {
		
		List<Map<String, Object>> templist = iljaliDAO.selectTermWorkerList(map);
		
		return CommonUtils.keyChangeLower(templist);
	}
	
	/*공공사업참여자*/
	@Override
	public Map<String, Object> insertPublicBusinessInfo(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = iljaliDAO.insertPublicBusinessInfo(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	@Override
	public Map<String, Object> updateVolunteerPublicBusinessInfo(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = iljaliDAO.updateVolunteerPublicBusinessInfo(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	@Override
	public Map<String, Object> updatePublicBusinessMaster(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = iljaliDAO.updatePublicBusinessMaster(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	@Override
	public Map<String, Object> deletePublicBusinessInfo(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = iljaliDAO.deletePublicBusinessInfo(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	@Override
	public Map<String, Object> getPublicBusinessInfo(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = iljaliDAO.getPublicBusinessInfo(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	@Override
	public List<Map<String, Object>> selectPublicBusinessList(Map<String, Object> map) throws Exception {
		
		List<Map<String, Object>> templist = iljaliDAO.selectPublicBusinessList(map);
		
		return CommonUtils.keyChangeLower(templist);
	}
	
	
	/*고시공고 좋아요*/
	@Override
	public Map<String, Object> insertGood(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = iljaliDAO.insertGood(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	@Override
	public Map<String, Object> deleteGood(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = iljaliDAO.deleteGood(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	@Override
	public Map<String, Object> getGood(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = iljaliDAO.getGood(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	
	/*고시공고 스크랩*/
	@Override
	public Map<String, Object> insertScrap(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = iljaliDAO.insertScrap(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	@Override
	public Map<String, Object> deleteScrap(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = iljaliDAO.deleteScrap(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	@Override
	public Map<String, Object> getScrap(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = iljaliDAO.getScrap(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	@Override
	public List<Map<String, Object>> selectMyScrapList(Map<String, Object> map) throws Exception {
		
		List<Map<String, Object>> templist = iljaliDAO.selectMyScrapList(map);
		
		return CommonUtils.keyChangeLower(templist);
	}
	
	/*첨부파일*/
	@Override
	public Map<String, Object> fileupload(Map<String, Object> map) throws Exception {
		System.out.println("fileupload impl 타나");
		Map<String, Object> resultMap = iljaliDAO.fileupload(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	@Override
	public Map<String, Object> selectFile(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = iljaliDAO.selectFile(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	/*파일명 관리*/
	@Override
	public Map<String, Object> insertMangeIljaliFile(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = iljaliDAO.insertMangeIljaliFile(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	@Override
	public Map<String, Object> deleteMangeIljaliFile(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = iljaliDAO.deleteMangeIljaliFile(map);
		log.debug("resultMap => (" + resultMap + ")");
	
		return CommonUtils.changToLowerMapKey(resultMap);
	}
	
	@Override
	public List<Map<String, Object>> selectMangeIljaliFileList(Map<String, Object> map) throws Exception {
		
		List<Map<String, Object>> templist = iljaliDAO.selectMangeIljaliFileList(map);
		
		return CommonUtils.keyChangeLower(templist);
	}
	
	
	
	/*관리자화면*/
	@Override
	public List<Map<String, Object>> selectTermOfficialViewList(Map<String, Object> map) throws Exception {
		return iljaliDAO.selectTermOfficialViewList(map);
	}
	
	@Override
	public List<Map<String, Object>> selectTermWorkerViewList(Map<String, Object> map) throws Exception {
		return iljaliDAO.selectTermWorkerViewList(map);
	}
	
	@Override
	public List<Map<String, Object>> selectPublicBusinessViewList(Map<String, Object> map) throws Exception {
		return iljaliDAO.selectPublicBusinessViewList(map);
	}
	
}
