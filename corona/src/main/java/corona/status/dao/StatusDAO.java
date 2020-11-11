package corona.status.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import corona.common.dao.AbstractDAO;

@Repository("statusDAO")
public class StatusDAO extends AbstractDAO{

	
	/*총괄*/
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectTotalStatus(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("status.selectTotalStatus", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectTotalSum(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("status.selectTotalSum", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectTotalStatisticsList(Map<String, Object> map) throws Exception{
		System.out.println("map확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("status.selectTotalStatisticsList", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectTotalMiddle(Map<String, Object> map) throws Exception{
		System.out.println("map확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("status.selectTotalMiddle", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectTotalExMiddle(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectList("status.selectTotalExMiddle", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectTotalExOverseaMiddle(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectList("status.selectTotalExOverseaMiddle", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectTotalDtlList(Map<String, Object> map) throws Exception{
		System.out.println("map확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("status.selectTotalDtlList", map);
	}
	
	/*확진자*/
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectConfirmStatus(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("status.selectConfirmStatus", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectConfirmDtlList(Map<String, Object> map) throws Exception{
		System.out.println("map확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("status.selectConfirmDtlList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectConfirmJipdanDtlList(Map<String, Object> map) throws Exception{
		System.out.println("map확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("status.selectConfirmJipdanDtlList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectConfirmMaplist(Map<String, Object> map) throws Exception{
		System.out.println("map확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("status.selectConfirmMaplist", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectConfirmMapJipdanlist(Map<String, Object> map) throws Exception{
		System.out.println("map확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("status.selectConfirmMapJipdanlist", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectConfirmJipdanSumStatus(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("status.selectConfirmJipdanSumStatus", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectConfirmAccumStatisticlist(Map<String, Object> map) throws Exception{
		System.out.println("map확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("status.selectConfirmAccumStatisticlist", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectConfirmStatisticlist(Map<String, Object> map) throws Exception{
		System.out.println("map확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("status.selectConfirmStatisticlist", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectConfirmShow(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("status.selectConfirmShow", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectConfirmSum(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("status.selectConfirmSum", map);
	}
	

	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectConfirmMiddleOversea(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectList("status.selectConfirmMiddleOversea", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectConfirmMiddle(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectList("status.selectConfirmMiddle", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectConfirmMiddleHospital(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectList("status.selectConfirmMiddleHospital", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectConfirmAccumMiddleHospital(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectList("status.selectConfirmAccumMiddleHospital", map);
	}
	
	/*자가격리자*/
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectIsoOverseaMiddle(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectList("status.selectIsoOverseaMiddle", map);
	}
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectIsoDomMiddle(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectList("status.selectIsoDomMiddle", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectIsoOverseaAccumMiddle(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectList("status.selectIsoOverseaAccumMiddle", map);
	}
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectIsoDomAccumMiddle(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectList("status.selectIsoDomAccumMiddle", map);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectDomesticStatus(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("status.selectDomesticStatus", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectOverseaStatus(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("status.selectOverseaStatus", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectIsoOverseaDtlList(Map<String, Object> map) throws Exception{
		System.out.println("map확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("status.selectIsoOverseaDtlList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectIsoDomesticDtlList(Map<String, Object> map) throws Exception{
		System.out.println("map확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("status.selectIsoDomesticDtlList", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectIsoStatisticsList(Map<String, Object> map) throws Exception{
		System.out.println("map확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("status.selectIsoStatisticsList", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectIsoMaplist(Map<String, Object> map) throws Exception{
		System.out.println("map확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("status.selectIsoMaplist", map);
	}
	
	/*상담민원*/
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectConsultStatus(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("status.selectConsultStatus", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectConsultDtlList(Map<String, Object> map) throws Exception{
		System.out.println("map확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("status.selectConsultDtlList", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectConsultMiddle(Map<String, Object> map) throws Exception{
		System.out.println("map확인 : " + map.toString());
		return (Map<String, Object>)selectOne("status.selectConsultMiddle", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectConsultStatisticsList(Map<String, Object> map) throws Exception{
		System.out.println("map확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("status.selectConsultStatisticsList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectConsultGubunStatisticsList(Map<String, Object> map) throws Exception{
		System.out.println("map확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("status.selectConsultGubunStatisticsList", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectIsoMiddleSumStatus(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("status.selectIsoMiddleSumStatus", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectIsoDomMiddleList(Map<String, Object> map) throws Exception{
		System.out.println("map확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("status.selectIsoDomMiddleList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectIsoOverseaMiddleList(Map<String, Object> map) throws Exception{
		System.out.println("map확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("status.selectIsoOverseaMiddleList", map);
	}
	
	/*선별진료소*/
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectClinicStatus(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("status.selectClinicStatus", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectClinicDtlList(Map<String, Object> map) throws Exception{
		System.out.println("map확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("status.selectClinicDtlList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectClinicConfirmDtlList(Map<String, Object> map) throws Exception{
		System.out.println("map확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("status.selectClinicConfirmDtlList", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectClinicMiddleSumStatus(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("status.selectClinicMiddleSumStatus", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectClinicGangnamMiddleList(Map<String, Object> map) throws Exception{
		System.out.println("selectClinicGangnamMiddleList map확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("status.selectClinicGangnamMiddleList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectClinicTasiguMiddleList(Map<String, Object> map) throws Exception{
		System.out.println("selectClinicTasiguMiddleList map확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("status.selectClinicTasiguMiddleList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectClinicOverseaMiddleList(Map<String, Object> map) throws Exception{
		System.out.println("selectClinicOverseaMiddleList map확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("status.selectClinicOverseaMiddleList", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectClinicStatisticsList(Map<String, Object> map) throws Exception{
		System.out.println("map확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("status.selectClinicStatisticsList", map);
	}
	
	/*선별진료소 케이스*/
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectClinicCaseStatus(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("status.selectClinicCaseStatus", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectClinicCaseDtlList(Map<String, Object> map) throws Exception{
		System.out.println("map확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("status.selectClinicCaseDtlList", map);
	}
	
	@SuppressWarnings("unchecked")
	public void updateClinicDae(Map<String, Object> map) throws Exception{
		update("status.updateClinicDae", map);
	}
	
	@SuppressWarnings("unchecked")
	public void updateClinicSo(Map<String, Object> map) throws Exception{
		update("status.updateClinicSo", map);
	}
}
