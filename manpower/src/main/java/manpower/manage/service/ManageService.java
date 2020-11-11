package manpower.manage.service;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface ManageService {

	
	/*공지사항*/
	List<Map<String, Object>> selectNoticeList(Map<String, Object> map) throws Exception;
	
	void updateDelNoticeAllList(Map<String, Object> map) throws Exception;
	
	/*은행코드관리*/
	List<Map<String, Object>> selectBankList(Map<String, Object> map) throws Exception;
	void updateDelBankAllList(Map<String, Object> map) throws Exception;
	
	Map<String, Object> bankExcelUp(File destFile,String ins_id) throws Exception;
	
}
