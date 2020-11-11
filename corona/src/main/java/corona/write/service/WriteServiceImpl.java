package corona.write.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import corona.write.dao.WriteDAO;

@Service("writeService")
public class WriteServiceImpl implements WriteService{
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="writeDAO")
	private WriteDAO writeDAO;
	
	/*확진자*/
	@Override
	public void insertConfirmWrite(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		writeDAO.insertConfirmWrite(map);
	}
	
	@Override
	public Map<String, Object> selectConfirmDetail(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = writeDAO.selectConfirmDetail(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public void updateConfirm(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		writeDAO.updateConfirm(map);
	}
	
	/*국내자가격리자*/
	@Override
	public void insertDomesticWrite(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		writeDAO.insertDomesticWrite(map);
	}
	
	@Override
	public Map<String, Object> selectDomesticDetail(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = writeDAO.selectDomesticDetail(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public void updateDomestic(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		writeDAO.updateDomestic(map);
	}
	
	/*해외입국자*/
	@Override
	public void insertOverseaWrite(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		writeDAO.insertOverseaWrite(map);
	}
	
	@Override
	public Map<String, Object> selectOverseaDetail(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = writeDAO.selectOverseaDetail(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public void updateOversea(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		writeDAO.updateOversea(map);
	}
	
	/*상담민원*/
	@Override
	public void insertConsultWrite(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		writeDAO.insertConsultWrite(map);
	}
	
	@Override
	public Map<String, Object> selectConsultDetail(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = writeDAO.selectConsultDetail(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public void updateConsult(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		writeDAO.updateConsult(map);
	}
	
	/*선별진료소*/
	@Override
	public void insertClinicWrite(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		writeDAO.insertClinicWrite(map);
	}
	
	@Override
	public Map<String, Object> selectClinicDetail(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = writeDAO.selectClinicDetail(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public void updateClinic(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		writeDAO.updateClinic(map);
	}
}
