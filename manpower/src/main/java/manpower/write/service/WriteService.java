package manpower.write.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface WriteService {

	/*공지사항*/
	void insertNoticeWrite(Map<String, Object> map, HttpServletRequest request) throws Exception;
	Map<String, Object> selectNoticeDetail(Map<String, Object> map) throws Exception;
	void updateNotice(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	/*은행코드관리*/
	void insertBankWrite(Map<String, Object> map, HttpServletRequest request) throws Exception;
	Map<String, Object> selectBankDetail(Map<String, Object> map) throws Exception;
	void updateBank(Map<String, Object> map, HttpServletRequest request) throws Exception;
}
