package sosong.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import sosong.common.dao.CommonDAO;
import sosong.common.alrim.SendArimMail;

@Service("commonService")
public class CommonServiceImpl implements CommonService{
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="commonDAO")
	private CommonDAO commonDAO;
	
	@Autowired
	private JavaMailSenderImpl mailSender;

	@Override
	public Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception {
		return commonDAO.selectFileInfo(map);
	}
	
	
	@Override
	public Map<String, Object> loginProcessId(Map<String, Object> map, HttpServletRequest request) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = commonDAO.loginProcessId(map);
		resultMap.put("map_i", tempMap);
		
		return resultMap;
	}
	
	
	@Override
	public Map<String, Object> loginProcessPw(Map<String, Object> map, HttpServletRequest request) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = commonDAO.loginProcessPw(map);
		resultMap.put("map_p", tempMap);
		
		return resultMap;
	}
	
	@Override
	public Map<String, Object> ssoLogin(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = commonDAO.ssoLogin(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public void insertUserinfo(Map<String, Object> map, HttpServletRequest request) throws Exception{
		commonDAO.insertUserinfo(map);
		
	}
	
	@Override
	public void updateUserinfo(Map<String, Object> map) throws Exception{
		commonDAO.updateUserinfo(map);
		
	}
	
	
	@Override
	public List<Map<String, Object>> deptList(Map<String, Object> map) throws Exception {
		return commonDAO.deptList(map);
	}
	
	@Override
	public List<Map<String, Object>> popJikWonList(Map<String, Object> map) throws Exception {
		return commonDAO.popJikWonList(map);
	}
	
	@Override
	public List<Map<String, Object>> selectIdApproveList(Map<String, Object> map) throws Exception {
		return commonDAO.selectIdApproveList(map);
	}
	
	@Override
	public void updateIdApprove(Map<String, Object> map) throws Exception{
		commonDAO.updateIdApprove(map);
		
	}
	
	@Override
	public Map<String, Object> idApproveListDetail(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = commonDAO.idApproveListDetail(map);
		resultMap.put("map", tempMap);
		
		return resultMap;
	}
	
	@Override
	public void updateIdApproveList(Map<String, Object> map) throws Exception{
		commonDAO.updateIdApproveList(map);
		
	}
	
	
	@Override
	public void deleteIdApproveList(Map<String, Object> map) throws Exception{
		commonDAO.deleteIdApproveList(map);
		
	}
	
	@Override
	public List<Map<String, Object>> selectJudgmentList(Map<String, Object> map) throws Exception {
		return commonDAO.selectJudgmentList(map);
	}
	
	
	
	
	
	
	@Override
	public String getnBeforeDay(Map<String, Object> map) throws Exception {
		return commonDAO.getnBeforeDay(map);
	}
	
	
	@Override
	public List<Map<String, Object>> selectArimService(Map<String, Object> map) throws Exception {
		return commonDAO.selectArimService(map);
	}
	
	
	
	@Override
	public void sosongSendMail(Map<String, Object> map) throws Exception {
		
		try { 
			SimpleMailMessage message = new SimpleMailMessage();
			 
			  log.debug("[webmail] ============== start!!!!  =============\t");
			
			  message.setFrom("mskim7402@gmail.com");
			  message.setTo("bluegreen@gangnam.go.kr");
			  message.setSubject("kkkk");
			  message.setText("bbb");		
			  mailSender.send(message);
			  
			  log.debug("[webmail] ============== end!!!!  =============\t");
		 }catch(Exception e){
			  e.printStackTrace();
		 }
		
		SendArimMail.sendMail();
	}
	
	
	
	@Override
	public void insertSendMsg(Map<String, Object> map) throws Exception {
		commonDAO.insertSendMsg(map);
	}
	
	@Override
	public List<Map<String, Object>> popSmsSendList(Map<String, Object> map) throws Exception {
		return commonDAO.popSmsSendList(map);
	}
}