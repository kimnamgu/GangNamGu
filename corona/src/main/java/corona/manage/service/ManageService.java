package corona.manage.service;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface ManageService {
	
	/*확진자*/
	List<Map<String, Object>> selectConfirmList(Map<String, Object> map) throws Exception;
	Map<String, Object> confirmManageExcelUp(File destFile,String ins_id) throws Exception;
	
	void updateDelConfirmAllList(Map<String, Object> map) throws Exception;
	void updateConfirmShow(Map<String, Object> map) throws Exception;
	
	/*국내접촉자*/
	List<Map<String, Object>> selectDomesticContactList(Map<String, Object> map) throws Exception;
	Map<String, Object> domesticContactExcelUp(File destFile,String ins_id) throws Exception;
	
	/*해외입국자*/
	List<Map<String, Object>> selectOverseaList(Map<String, Object> map) throws Exception;
	Map<String, Object> overseaExcelUp(File destFile,String ins_id) throws Exception;
	
	/*상담민원*/
	List<Map<String, Object>> selectConsultList(Map<String, Object> map) throws Exception;
	Map<String, Object> consultExcelUp(File destFile,String ins_id) throws Exception;
	
	/*선별진료소*/
	List<Map<String, Object>> selectClinicList(Map<String, Object> map) throws Exception;
	Map<String, Object> clinicExcelUp(File destFile,String ins_id) throws Exception;
	
	List<Map<String, Object>> selectGangnamguArrangeList(Map<String, Object> map) throws Exception;
	void updateGangnamguArrange(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectTasiguArrangeList(Map<String, Object> map) throws Exception;
	void updateTasiguArrange(Map<String, Object> map) throws Exception;
	
	
	
	/*엑셀다운로드*/
	List<Map<String, Object>> selectConfirmExcel(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectClinicExcel(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectOverseaExcel(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectDomesticExcel(Map<String, Object> map) throws Exception;

}
