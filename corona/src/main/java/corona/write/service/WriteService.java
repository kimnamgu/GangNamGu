package corona.write.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface WriteService {

	/*Ȯ����*/
	void insertConfirmWrite(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	Map<String, Object> selectConfirmDetail(Map<String, Object> map) throws Exception;
	
	void updateConfirm(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	
	/*���� �ڰ��ܸ���*/
	void insertDomesticWrite(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	Map<String, Object> selectDomesticDetail(Map<String, Object> map) throws Exception;
	
	void updateDomestic(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	/*�ؿ��Ա���*/
	void insertOverseaWrite(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	Map<String, Object> selectOverseaDetail(Map<String, Object> map) throws Exception;
	
	void updateOversea(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	/*���ο�*/
	void insertConsultWrite(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	Map<String, Object> selectConsultDetail(Map<String, Object> map) throws Exception;
	
	void updateConsult(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	/*���������*/
	void insertClinicWrite(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	Map<String, Object> selectClinicDetail(Map<String, Object> map) throws Exception;
	
	void updateClinic(Map<String, Object> map, HttpServletRequest request) throws Exception;
}
