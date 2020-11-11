package sosong.incident.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public interface IncidentService {

	List<Map<String, Object>> selectAllIncidentList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectAllSimList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectCrimIncidentList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectIndIncidentList(Map<String, Object> map) throws Exception;
	
	Map<String, Object> incidentBasicDetail(Map<String, Object> map) throws Exception;
	
	Map<String, Object> indTrialDetail(Map<String, Object> map) throws Exception;
	
	Map<String, Object> crimListDetail(Map<String, Object> map) throws Exception;
	
	
	List<Map<String, Object>> selectIncidentStatisticsList1(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectIncidentStatisticsList2(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectIncidentStatisticsList3(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectIncidentStatisticsList4(Map<String, Object> map) throws Exception;
	
	
	void insertIncidentBasic(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void insertIndTrial(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	int insertCrimList(Map<String, Object> map, HttpServletRequest request) throws SQLException;

	void updateIncidentBasic(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void deleteIncidentBasic(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	
	void updateIndTrial(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void deleteIndTrial(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	
	void updateCrimList(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void deleteCrimList(Map<String, Object> map, HttpServletRequest request) throws Exception;
}
