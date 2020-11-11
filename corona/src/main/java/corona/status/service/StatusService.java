package corona.status.service;

import java.util.List;
import java.util.Map;

public interface StatusService {

	/*총괄*/
	Map<String, Object> selectTotalStatus(Map<String, Object> map) throws Exception;
	Map<String, Object> selectTotalSum(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectTotalMiddle(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectTotalExOverseaMiddle(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectTotalExMiddle(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectTotalDtlList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectTotalStatisticsList(Map<String, Object> map) throws Exception;
	
	
	
	
	/*확진자*/
	Map<String, Object> selectConfirmStatus(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectConfirmDtlList(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectConfirmJipdanDtlList(Map<String, Object> map) throws Exception;
	
	
	List<Map<String, Object>> selectConfirmMaplist(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectConfirmMapJipdanlist(Map<String, Object> map) throws Exception;
	
	Map<String, Object> selectConfirmJipdanSumStatus(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectConfirmAccumStatisticlist(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectConfirmStatisticlist(Map<String, Object> map) throws Exception;
	

	
	List<Map<String, Object>> selectConfirmMiddleOversea(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectConfirmMiddle(Map<String, Object> map) throws Exception;
	
	
	Map<String, Object> selectConfirmShow(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectConfirmMiddleHospital(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectConfirmAccumMiddleHospital(Map<String, Object> map) throws Exception;
	
	Map<String, Object> selectConfirmSum(Map<String, Object> map) throws Exception;
	
	/*자가격리자*/
	
	List<Map<String, Object>> selectIsoOverseaMiddle(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectIsoDomMiddle(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectIsoOverseaAccumMiddle(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectIsoDomAccumMiddle(Map<String, Object> map) throws Exception;
	
	Map<String, Object> selectDomesticStatus(Map<String, Object> map) throws Exception;
	Map<String, Object> selectOverseaStatus(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectIsoOverseaDtlList(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectIsoDomesticDtlList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectIsoStatisticsList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectIsoMaplist(Map<String, Object> map) throws Exception;
	
	
	
	Map<String, Object> selectIsoMiddleSumStatus(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectIsoDomMiddleList(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectIsoOverseaMiddleList(Map<String, Object> map) throws Exception;
	
	/*상담민원*/
	Map<String, Object> selectConsultStatus(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectConsultDtlList(Map<String, Object> map) throws Exception;
	
	Map<String, Object> selectConsultMiddle(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectConsultStatisticsList(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectConsultGubunStatisticsList(Map<String, Object> map) throws Exception;
	
	/*선별진료소*/
	Map<String, Object> selectClinicStatus(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectClinicDtlList(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectClinicConfirmDtlList(Map<String, Object> map) throws Exception;
	
	Map<String, Object> selectClinicMiddleSumStatus(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectClinicGangnamMiddleList(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectClinicTasiguMiddleList(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectClinicOverseaMiddleList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectClinicStatisticsList(Map<String, Object> map) throws Exception;
	
	/*선별진료소 케이스*/
	Map<String, Object> selectClinicCaseStatus(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectClinicCaseDtlList(Map<String, Object> map) throws Exception;
	
	
	void updateClinicDae(Map<String, Object> map) throws Exception;
	void updateClinicSo(Map<String, Object> map) throws Exception;
}
