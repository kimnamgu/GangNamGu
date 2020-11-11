package corona.status.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import corona.common.util.FileUtils;
import corona.status.dao.StatusDAO;

@Service("statusService")
public class StatusServiceImpl implements StatusService{
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="fileUtils")
	private FileUtils fileUtils;
	
	@Resource(name="statusDAO")
	private StatusDAO statusDAO;
	
	/*총괄*/
	public Map<String, Object> selectTotalStatus(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = statusDAO.selectTotalStatus(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	public Map<String, Object> selectTotalSum(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = statusDAO.selectTotalSum(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	
	@Override
	public List<Map<String, Object>> selectTotalMiddle(Map<String, Object> map) throws Exception {
		return statusDAO.selectTotalMiddle(map);
	}
	
	@Override
	public List<Map<String, Object>> selectTotalExMiddle(Map<String, Object> map) throws Exception {
		return statusDAO.selectTotalExMiddle(map);
	}
	
	@Override
	public List<Map<String, Object>> selectTotalExOverseaMiddle(Map<String, Object> map) throws Exception {
		return statusDAO.selectTotalExOverseaMiddle(map);
	}
	
	
	
	@Override
	public List<Map<String, Object>> selectTotalDtlList(Map<String, Object> map) throws Exception {
		return statusDAO.selectTotalDtlList(map);
	}
	
	@Override
	public List<Map<String, Object>> selectTotalStatisticsList(Map<String, Object> map) throws Exception {
		return statusDAO.selectTotalStatisticsList(map);
	}
	
	
	/*확진자*/
	@Override
	public Map<String, Object> selectConfirmStatus(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = statusDAO.selectConfirmStatus(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public Map<String, Object> selectConfirmShow(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = statusDAO.selectConfirmShow(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public Map<String, Object> selectConfirmSum(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = statusDAO.selectConfirmSum(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public List<Map<String, Object>> selectConfirmDtlList(Map<String, Object> map) throws Exception {
		return statusDAO.selectConfirmDtlList(map);
	}

	@Override
	public List<Map<String, Object>> selectConfirmJipdanDtlList(Map<String, Object> map) throws Exception {
		return statusDAO.selectConfirmJipdanDtlList(map);
	}
	
	@Override
	public List<Map<String, Object>> selectConfirmMaplist(Map<String, Object> map) throws Exception {
		return statusDAO.selectConfirmMaplist(map);
	}
	
	@Override
	public List<Map<String, Object>> selectConfirmMapJipdanlist(Map<String, Object> map) throws Exception {
		return statusDAO.selectConfirmMapJipdanlist(map);
	}
	
	public Map<String, Object> selectConfirmJipdanSumStatus(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = statusDAO.selectConfirmJipdanSumStatus(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public List<Map<String, Object>> selectConfirmAccumStatisticlist(Map<String, Object> map) throws Exception {
		return statusDAO.selectConfirmAccumStatisticlist(map);
	}
	
	@Override
	public List<Map<String, Object>> selectConfirmStatisticlist(Map<String, Object> map) throws Exception {
		return statusDAO.selectConfirmStatisticlist(map);
	}
	
	@Override
	public List<Map<String, Object>> selectConfirmMiddleOversea(Map<String, Object> map) throws Exception {
		return statusDAO.selectConfirmMiddleOversea(map);
	}
	
	@Override
	public List<Map<String, Object>> selectConfirmMiddle(Map<String, Object> map) throws Exception {
		return statusDAO.selectConfirmMiddle(map);
	}
	
	@Override
	public List<Map<String, Object>> selectConfirmMiddleHospital(Map<String, Object> map) throws Exception {
		return statusDAO.selectConfirmMiddleHospital(map);
	}
	
	@Override
	public List<Map<String, Object>> selectConfirmAccumMiddleHospital(Map<String, Object> map) throws Exception {
		return statusDAO.selectConfirmAccumMiddleHospital(map);
	}
	
	/*자가격리자*/
	@Override
	public List<Map<String, Object>> selectIsoOverseaMiddle(Map<String, Object> map) throws Exception {
		return statusDAO.selectIsoOverseaMiddle(map);
	}
	
	@Override
	public List<Map<String, Object>> selectIsoDomMiddle(Map<String, Object> map) throws Exception {
		return statusDAO.selectIsoDomMiddle(map);
	}
	
	@Override
	public List<Map<String, Object>> selectIsoOverseaAccumMiddle(Map<String, Object> map) throws Exception {
		return statusDAO.selectIsoOverseaAccumMiddle(map);
	}
	
	@Override
	public List<Map<String, Object>> selectIsoDomAccumMiddle(Map<String, Object> map) throws Exception {
		return statusDAO.selectIsoDomAccumMiddle(map);
	}
	
	
	@Override
	public Map<String, Object> selectDomesticStatus(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = statusDAO.selectDomesticStatus(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public Map<String, Object> selectOverseaStatus(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = statusDAO.selectOverseaStatus(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public List<Map<String, Object>> selectIsoOverseaDtlList(Map<String, Object> map) throws Exception {
		return statusDAO.selectIsoOverseaDtlList(map);
	}
	
	@Override
	public List<Map<String, Object>> selectIsoDomesticDtlList(Map<String, Object> map) throws Exception {
		return statusDAO.selectIsoDomesticDtlList(map);
	}
	
	@Override
	public List<Map<String, Object>> selectIsoStatisticsList(Map<String, Object> map) throws Exception {
		return statusDAO.selectIsoStatisticsList(map);
	}
	
	@Override
	public List<Map<String, Object>> selectIsoMaplist(Map<String, Object> map) throws Exception {
		return statusDAO.selectIsoMaplist(map);
	}
	
	
	public Map<String, Object> selectIsoMiddleSumStatus(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = statusDAO.selectIsoMiddleSumStatus(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public List<Map<String, Object>> selectIsoDomMiddleList(Map<String, Object> map) throws Exception {
		return statusDAO.selectIsoDomMiddleList(map);
	}
	
	@Override
	public List<Map<String, Object>> selectIsoOverseaMiddleList(Map<String, Object> map) throws Exception {
		return statusDAO.selectIsoOverseaMiddleList(map);
	}
	
	/*상담민원*/
	@Override
	public Map<String, Object> selectConsultStatus(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = statusDAO.selectConsultStatus(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public List<Map<String, Object>> selectConsultDtlList(Map<String, Object> map) throws Exception {
		return statusDAO.selectConsultDtlList(map);
	}
	
	@Override
	public Map<String, Object> selectConsultMiddle(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = statusDAO.selectConsultMiddle(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public List<Map<String, Object>> selectConsultStatisticsList(Map<String, Object> map) throws Exception {
		return statusDAO.selectConsultStatisticsList(map);
	}
	
	@Override
	public List<Map<String, Object>> selectConsultGubunStatisticsList(Map<String, Object> map) throws Exception {
		return statusDAO.selectConsultGubunStatisticsList(map);
	}
	
	/*선별진료소*/
	@Override
	public Map<String, Object> selectClinicStatus(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = statusDAO.selectClinicStatus(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public List<Map<String, Object>> selectClinicDtlList(Map<String, Object> map) throws Exception {
		return statusDAO.selectClinicDtlList(map);
	}
	
	@Override
	public List<Map<String, Object>> selectClinicConfirmDtlList(Map<String, Object> map) throws Exception {
		return statusDAO.selectClinicConfirmDtlList(map);
	}
	
	public Map<String, Object> selectClinicMiddleSumStatus(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = statusDAO.selectClinicMiddleSumStatus(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public List<Map<String, Object>> selectClinicGangnamMiddleList(Map<String, Object> map) throws Exception {
		return statusDAO.selectClinicGangnamMiddleList(map);
	}
	
	@Override
	public List<Map<String, Object>> selectClinicTasiguMiddleList(Map<String, Object> map) throws Exception {
		return statusDAO.selectClinicTasiguMiddleList(map);
	}
	
	@Override
	public List<Map<String, Object>> selectClinicOverseaMiddleList(Map<String, Object> map) throws Exception {
		return statusDAO.selectClinicOverseaMiddleList(map);
	}
	
	@Override
	public List<Map<String, Object>> selectClinicStatisticsList(Map<String, Object> map) throws Exception {
		return statusDAO.selectClinicStatisticsList(map);
	}
	
	/*선별진료소 케이스*/
	@Override
	public Map<String, Object> selectClinicCaseStatus(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = statusDAO.selectClinicCaseStatus(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public List<Map<String, Object>> selectClinicCaseDtlList(Map<String, Object> map) throws Exception {
		return statusDAO.selectClinicCaseDtlList(map);
	}
	
	@Override
	public void updateClinicDae(Map<String, Object> map) throws Exception {
		statusDAO.updateClinicDae(map);
	}
	@Override
	public void updateClinicSo(Map<String, Object> map) throws Exception {
		statusDAO.updateClinicSo(map);
	}
}
