package corona.write.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface WriteService {

	/*확진자*/
	void insertConfirmWrite(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	Map<String, Object> selectConfirmDetail(Map<String, Object> map) throws Exception;
	
	void updateConfirm(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	
	/*국내 자각겨리자*/
	void insertDomesticWrite(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	Map<String, Object> selectDomesticDetail(Map<String, Object> map) throws Exception;
	
	void updateDomestic(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	/*해외입국자*/
	void insertOverseaWrite(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	Map<String, Object> selectOverseaDetail(Map<String, Object> map) throws Exception;
	
	void updateOversea(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	/*상담민원*/
	void insertConsultWrite(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	Map<String, Object> selectConsultDetail(Map<String, Object> map) throws Exception;
	
	void updateConsult(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	/*선별진료소*/
	void insertClinicWrite(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	Map<String, Object> selectClinicDetail(Map<String, Object> map) throws Exception;
	
	void updateClinic(Map<String, Object> map, HttpServletRequest request) throws Exception;
}
