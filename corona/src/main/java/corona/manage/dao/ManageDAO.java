package corona.manage.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import corona.common.dao.AbstractDAO;

@Repository("manageDAO")
public class ManageDAO extends AbstractDAO{

	/*확진자*/
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectConfirmList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("manage.selectConfirmList", map);
	}
	
	@SuppressWarnings("unchecked")
	public int insertConfirmManageExcel(Map<String, String> paramMap) {
		int rtn = (int) insert("manage.insertConfirmManageExcel", paramMap);
		return rtn;
	}
	
	public void updateDelConfirmAllList(Map<String, Object> map) throws Exception{
		update("manage.updateDelConfirmAllList", map);
	}
	
	public void updateConfirmShow(Map<String, Object> map) throws Exception{
		update("manage.updateConfirmShow", map);
	}
	
	/*국내접촉자*/
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectDomesticContactList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("manage.selectDomesticContactList", map);
	}
	
	@SuppressWarnings("unchecked")
	public int insertDomesticContactManageExcel(Map<String, String> paramMap) {
		int rtn = (int) insert("manage.insertDomesticContactManageExcel", paramMap);
		return rtn;
	}
	
	public void updateDelDomesticAllList(Map<String, Object> map) throws Exception{
		update("manage.updateDelDomesticAllList", map);
	}
	
	
	/*해외입국자*/
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectOverseaList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("manage.selectOverseaList", map);
	}
	
	@SuppressWarnings("unchecked")
	public int insertOverseaManageExcel(Map<String, String> paramMap) {
		int rtn = (int) insert("manage.insertOverseaManageExcel", paramMap);
		return rtn;
	}
	
	public void updateDelOverseaAllList(Map<String, Object> map) throws Exception{
		update("manage.updateDelOverseaAllList", map);
	}
	
	/*상담민원*/
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectConsultList(Map<String, Object> map) throws Exception{
		System.out.println("map 확인 : " + map.toString());
		return (List<Map<String, Object>>)selectPagingList("manage.selectConsultList", map);
	}
	
	@SuppressWarnings("unchecked")
	public int insertConsultExcel(Map<String, String> paramMap) {
		System.out.println("paramMap" + paramMap.toString());
		int rtn = (int) insert("manage.insertConsultExcel", paramMap);
		return rtn;
	}
	
	public void updateDelConsultAllList(Map<String, Object> map) throws Exception{
		update("manage.updateDelConsultAllList", map);
	}
	
	/*선별진료소*/
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectClinicList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("manage.selectClinicList", map);
	}
	
	@SuppressWarnings("unchecked")
	public int insertClinicManageExcel(Map<String, String> paramMap) {
		int rtn = (int) insert("manage.insertClinicManageExcel", paramMap);
		return rtn;
	}
	
	public void updateDelClinicAllList(Map<String, Object> map) throws Exception{
		update("manage.updateDelClinicAllList", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectGangnamguArrangeList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectList("manage.selectGangnamguArrangeList", map);
	}
	
	public void updateGangnamguArrange(Map<String, Object> map) throws Exception{
		update("manage.updateGangnamguArrange", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectTasiguArrangeList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectList("manage.selectTasiguArrangeList", map);
	}
	
	public void updateTasiguArrange(Map<String, Object> map) throws Exception{
		update("manage.updateTasiguArrange", map);
	}
	
	
	/*엑셀*/
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectConfirmExcel(Map<String, Object> map) throws Exception{
		System.out.println("selectConfirmExcel map 확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("manage.selectConfirmExcel", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectClinicExcel(Map<String, Object> map) throws Exception{
		System.out.println("selectClinicExcel map 확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("manage.selectClinicExcel", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectOverseaExcel(Map<String, Object> map) throws Exception{
		System.out.println("selectOverseaExcel map 확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("manage.selectOverseaExcel", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectDomesticExcel(Map<String, Object> map) throws Exception{
		System.out.println("selectDomesticExcel map 확인 : " + map.toString());
		return (List<Map<String, Object>>)selectList("manage.selectDomesticExcel", map);
	}
}
