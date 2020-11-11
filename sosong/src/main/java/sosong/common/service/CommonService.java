package sosong.common.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface CommonService {

	Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception;
	
	Map<String, Object> loginProcessId(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	Map<String, Object> loginProcessPw(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	Map<String, Object> ssoLogin(Map<String, Object> map) throws Exception;
	
	void updateUserinfo(Map<String, Object> map) throws Exception;
	
	void insertUserinfo(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	List<Map<String, Object>> deptList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> popJikWonList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectIdApproveList(Map<String, Object> map) throws Exception;
	
	void updateIdApprove(Map<String, Object> map) throws Exception;
	
	Map<String, Object> idApproveListDetail(Map<String, Object> map) throws Exception;
	
	void updateIdApproveList(Map<String, Object> map) throws Exception;
	
	void deleteIdApproveList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectJudgmentList(Map<String, Object> map) throws Exception;
	
	
	
	
	List<Map<String, Object>> selectArimService(Map<String, Object> map) throws Exception;
	
	String getnBeforeDay(Map<String, Object> map) throws Exception;
	
	void sosongSendMail(Map<String, Object> map) throws Exception;
	
	void insertSendMsg(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> popSmsSendList(Map<String, Object> map) throws Exception;
	
}
