package scms.daejang.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import scms.common.util.FileUtils;
import scms.daejang.dao.DaejangDAO;


@Service("daejangService")
public class DaejangServiceImpl implements DaejangService{
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="fileUtils")
	private FileUtils fileUtils;
	
	@Resource(name="daejangDAO")
	private DaejangDAO daejangDAO;

	
	@Override
	public List<Map<String, Object>> selectPrvCnrtCompList(Map<String, Object> map) throws Exception {
		return daejangDAO.selectPrvCnrtCompList(map);
	}
	
	
	@Override
	public List<Map<String, Object>> selectPrvCnrtCompInsList(Map<String, Object> map) throws Exception {
		return daejangDAO.selectPrvCnrtCompInsList(map);
	}
	
	
	
	@Override
	public List<Map<String, Object>> selectPrvCnrtContractList(Map<String, Object> map) throws Exception {
		return daejangDAO.selectPrvCnrtContractList(map);
	}
	
	
	
	
	
	@Override
	public List<Map<String, Object>> selectPrvCnrtDutyList(Map<String, Object> map) throws Exception {
		return daejangDAO.selectPrvCnrtDutyList(map);
	}

	
	@Override
	public List<Map<String, Object>> selectPrvCnrtExpertList(Map<String, Object> map) throws Exception {
		return daejangDAO.selectPrvCnrtExpertList(map);
	}

	
	
	@Override
	public List<Map<String, Object>> selectPrvCnrtReasonList(Map<String, Object> map) throws Exception {
		return daejangDAO.selectPrvCnrtReasonList(map);
	}
	
	
	@Override
	public List<Map<String, Object>> selectPrvCnrtEvalList(Map<String, Object> map) throws Exception {
		return daejangDAO.selectPrvCnrtEvalList(map);
	}
	
	
	@Override
	public List<Map<String, Object>> selectPrvCnrtPlanList(Map<String, Object> map) throws Exception {
		return daejangDAO.selectPrvCnrtPlanList(map);
	}
	
	@Override
	public List<Map<String, Object>> selectPrvCnrtAcceptList(Map<String, Object> map) throws Exception {
		return daejangDAO.selectPrvCnrtAcceptList(map);
	}
	
	
	@Override
	public List<Map<String, Object>> selectPrvCnrtStatistics1(Map<String, Object> map) throws Exception {
		return daejangDAO.selectPrvCnrtStatistics1(map);
	}
	
	
	@Override
	public List<Map<String, Object>> selectPrvCnrtStatistics2(Map<String, Object> map) throws Exception {
		return daejangDAO.selectPrvCnrtStatistics2(map);
	}
	
	
	@Override
	public List<Map<String, Object>> selectPrvCnrtStatistics1List(Map<String, Object> map) throws Exception {
		return daejangDAO.selectPrvCnrtStatistics1List(map);
	}
	
	@Override
	public List<Map<String, Object>> selectCountList(Map<String, Object> map) throws Exception {
		return daejangDAO.selectCountList(map);
	}
	
	
	
	@Override
	public Map<String, Object> prvCnrtPlanInfo(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = daejangDAO.prvCnrtPlanInfo(map);
		resultMap.put("map", tempMap);
				
		List<Map<String,Object>> list = daejangDAO.selectFileList(map);
		resultMap.put("list", list);
		
		return resultMap;
	}
	
	
	@Override
	public Map<String, Object> prvCnrtCompInfo(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = daejangDAO.prvCnrtCompInfo(map);
		resultMap.put("map", tempMap);
		
		List<Map<String,Object>> list = daejangDAO.selectFileList(map);
		resultMap.put("list", list);
		
		map.put("BOARD_GB", "6"); 
		List<Map<String,Object>> list6 = daejangDAO.selectFileList(map);
		resultMap.put("list6", list6);
		
		return resultMap;
	}
	
	
	@Override
	public Map<String, Object> prvCnrtExpertUpdate(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = daejangDAO.prvCnrtExpertUpdate(map);
		resultMap.put("map", tempMap);
		
		List<Map<String,Object>> list = daejangDAO.selectFileList(map);
		resultMap.put("list", list);
		
		return resultMap;
	}
	
	
	@Override
	public Map<String, Object> prvCnrtDutyUpdate(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = daejangDAO.prvCnrtDutyUpdate(map);
		resultMap.put("map", tempMap);
		
		List<Map<String,Object>> list = daejangDAO.selectFileList(map);
		resultMap.put("list", list);
		
		return resultMap;
	}
	
	@Override
	public Map<String, Object> prvCnrtEvalUpdate(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = daejangDAO.prvCnrtEvalUpdate(map);
		resultMap.put("map", tempMap);
		
		List<Map<String,Object>> list = daejangDAO.selectFileList(map);
		resultMap.put("list", list);
		
		return resultMap;
	}
	
	
	
	
	@Override
	public void insertPrvCnrtComp(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		
		//List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
	
		daejangDAO.insertPrvCnrtComp(map);
		
		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
		for(int i=0, size=list.size(); i<size; i++){
			daejangDAO.insertFile(list.get(i));
		}
			
	}
	
	
	@Override
	public void insertPrvCnrtContract(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		
		daejangDAO.insertPrvCnrtContract(map);
		
		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
		for(int i=0, size=list.size(); i<size; i++){
			daejangDAO.insertFile(list.get(i));
		}
	}
	
	
	@Override
	public void insertPrvCnrtReason(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		
		daejangDAO.insertPrvCnrtContract(map);
		
		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
		for(int i=0, size=list.size(); i<size; i++){
			daejangDAO.insertFile(list.get(i));
		}
		
	}
	
	
	
	@Override
	public void updatePrvCnrtPlan(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		
		daejangDAO.updatePrvCnrtPlan(map);
		
		daejangDAO.deleteFileList(map);
		List<Map<String,Object>> list = fileUtils.parseUpdateFileInfo(map, request);
		Map<String,Object> tempMap = null;
		for(int i=0, size=list.size(); i<size; i++){
			tempMap = list.get(i);
			if(tempMap.get("IS_NEW").equals("Y")){
				daejangDAO.insertFile(tempMap);
			}
			else{
				daejangDAO.updateFile(tempMap);
			}
		}
	}
	
	
	@Override
	public void updatePrvCnrtComp(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		
		daejangDAO.updatePrvCnrtComp(map);
		
		daejangDAO.deleteFileList(map);
		
		map.put("BOARD_GB", "6"); 
		daejangDAO.deleteFileList(map);
		
		map.put("BOARD_GB", "5"); 
		
		List<Map<String,Object>> list = fileUtils.parseUpdateFileInfo(map, request);
		Map<String,Object> tempMap = null;
		for(int i=0, size=list.size(); i<size; i++){
			tempMap = list.get(i);
			if(tempMap.get("IS_NEW").equals("Y")){
				daejangDAO.insertFile(tempMap);
			}
			else{
				daejangDAO.updateFile(tempMap);
			}
		}
	}
	
	@Override
	public void updatePrvCnrtDuty(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		
		daejangDAO.updatePrvCnrtDuty(map);
		
		daejangDAO.deleteFileList(map);
		List<Map<String,Object>> list = fileUtils.parseUpdateFileInfo(map, request);
		Map<String,Object> tempMap = null;
		for(int i=0, size=list.size(); i<size; i++){
			tempMap = list.get(i);
			if(tempMap.get("IS_NEW").equals("Y")){
				daejangDAO.insertFile(tempMap);
			}
			else{
				daejangDAO.updateFile(tempMap);
			}
		}
	}
	
	@Override
	public void updatePrvCnrtExpert(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		
		daejangDAO.updatePrvCnrtExpert(map);
		
		daejangDAO.deleteFileList(map);
		List<Map<String,Object>> list = fileUtils.parseUpdateFileInfo(map, request);
		Map<String,Object> tempMap = null;
		for(int i=0, size=list.size(); i<size; i++){
			tempMap = list.get(i);
			if(tempMap.get("IS_NEW").equals("Y")){
				daejangDAO.insertFile(tempMap);
			}
			else{
				daejangDAO.updateFile(tempMap);
			}
		}
	}
	
	
	@Override
	public void updatePrvCnrtReason(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		
		daejangDAO.updatePrvCnrtReason(map);	
		log.debug("zz :: " + map.toString());
		daejangDAO.deleteFileList(map);
		List<Map<String,Object>> list = fileUtils.parseUpdateFileInfo(map, request);
		Map<String,Object> tempMap = null;
		for(int i=0, size=list.size(); i<size; i++){
			tempMap = list.get(i);
			if(tempMap.get("IS_NEW").equals("Y")){
				daejangDAO.insertFile(tempMap);
			}
			else{
				daejangDAO.updateFile(tempMap);
			}
		}
	}
	
	
	@Override
	public void updatePrvCnrtEval(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		
		daejangDAO.updatePrvCnrtEval(map);
		
		daejangDAO.deleteFileList(map);
		List<Map<String,Object>> list = fileUtils.parseUpdateFileInfo(map, request);
		Map<String,Object> tempMap = null;
		for(int i=0, size=list.size(); i<size; i++){
			tempMap = list.get(i);
			if(tempMap.get("IS_NEW").equals("Y")){
				daejangDAO.insertFile(tempMap);
			}
			else{
				daejangDAO.updateFile(tempMap);
			}
		}
	}
	
	
	@Override
	public void deletePrvCnrtPlan(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		
		daejangDAO.deletePrvCnrtPlan(map);			
	}
	
	
	@Override
	public void deletePrvCnrtComp(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		
		daejangDAO.deletePrvCnrtComp(map);			
	}
	
	
	@Override
	public void deletePrvCnrtDuty(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		
		daejangDAO.deletePrvCnrtDuty(map);			
	}
	
	
	@Override
	public void deletePrvCnrtExpert(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		
		daejangDAO.deletePrvCnrtExpert(map);			
	}
	
	
	
	
	
	
	
	
	
	
	@Override
	public void insScOtherWork(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		
		daejangDAO.insScOtherWork(map);
		
		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
		for(int i=0, size=list.size(); i<size; i++){
			daejangDAO.insertFile(list.get(i));
		}
			
	}
	
	
	@Override
	public void insertPrvCnrtExpert(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		
		//List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
	
		daejangDAO.insertPrvCnrtExpert(map);
				
		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
		for(int i=0, size=list.size(); i<size; i++){
			daejangDAO.insertFile(list.get(i));
		}
			
	}
	
	
	
	@Override
	public void insScReason(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		
		//List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
	
		daejangDAO.insScReason(map);	
			
	}
	
	
	@Override
	public void insScEval(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		
		daejangDAO.insScEval(map);
		
		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
		for(int i=0, size=list.size(); i<size; i++){
			daejangDAO.insertFile(list.get(i));
		}
			
	}
	
	@Override
	public void insertPrvCnrtPlan(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		try{
//			daejangDAO.insertPrvCnrtPlan(map);
			daejangDAO.updatePrivateSaup(map);
		}catch(Exception e){
			log.debug("발주계획 오류!!!!!");
			log.debug("발주계획 오류 사유 ::" + e);
		}
		
		
//		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
//		for(int i=0, size=list.size(); i<size; i++){
//			daejangDAO.insertFile(list.get(i));
//		}
			
	}

	@Override
	public void updatePrivateSaup(Map<String, Object> map ) throws Exception {
		try{
//			daejangDAO.insertPrvCnrtPlan(map);
			daejangDAO.updatePrivateSaup(map);
		}catch(Exception e){
			log.debug("발주계획 오류!!!!!");
			log.debug("발주계획 오류 사유 ::" + e);
		}
		
		
//		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
//		for(int i=0, size=list.size(); i<size; i++){
//			daejangDAO.insertFile(list.get(i));
//		}
		
	}
	
	
	@Override
	public Map<String,Object> upEstFile(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
		for(int i=0, size=list.size(); i<size; i++){
			daejangDAO.insertFile(list.get(i));
		}
		Map<String,Object> rtnmap = daejangDAO.selectUpFileInfo(list.get(0));
		
		return rtnmap;
	}
	
	@Override
	public List<Map<String, Object>> chkSaup(Map<String, Object> map) throws Exception {
		return daejangDAO.chkSaup(map);
	}
	
	@Override
	public List<Map<String, Object>> popSaupList(Map<String, Object> map) throws Exception {
		return daejangDAO.popSaupList(map);
	}
	
	
	@Override
	public List<Map<String, Object>> attachFileList(Map<String, Object> map) throws Exception {
		return daejangDAO.attachFileList(map);
	}
	
	
	
	
	
	
	
	@Override
	public List<Map<String, Object>> selectPrvCnrtBoardList(Map<String, Object> map) throws Exception {
		return daejangDAO.selectPrvCnrtBoardList(map);
		
	}

	@Override
	public void insertPrvCnrtBoard(Map<String, Object> map, HttpServletRequest request) throws Exception {
		daejangDAO.insertPrvCnrtBoard(map);
		
		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
		for(int i=0, size=list.size(); i<size; i++){
			daejangDAO.insertFile(list.get(i));
		}
	}
	
	@Override
	public Map<String, Object> selectPrvCnrtBoardDetail(Map<String, Object> map) throws Exception {
		daejangDAO.updateHitCnt(map);
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = daejangDAO.selectPrvCnrtBoardDetail(map);
		resultMap.put("map", tempMap);
		
		List<Map<String,Object>> list = daejangDAO.selectFileList(map);
		resultMap.put("list", list);
		
		return resultMap;
	}

	@Override
	public void updatePrvCnrtBoard(Map<String, Object> map, HttpServletRequest request) throws Exception{
		daejangDAO.updatePrvCnrtBoard(map);
		
		daejangDAO.deleteFileList(map);
		List<Map<String,Object>> list = fileUtils.parseUpdateFileInfo(map, request);
		Map<String,Object> tempMap = null;
		for(int i=0, size=list.size(); i<size; i++){
			tempMap = list.get(i);
			if(tempMap.get("IS_NEW").equals("Y")){
				daejangDAO.insertFile(tempMap);
			}
			else{
				daejangDAO.updateFile(tempMap);
			}
		}
	}

	@Override
	public void deletePrvCnrtBoard(Map<String, Object> map) throws Exception {
		daejangDAO.deletePrvCnrtBoard(map);
	}

	
	@Override
	public List<Map<String, Object>> selectPrvCnrtCurrentBuildList(Map<String, Object> map) throws Exception {
		return daejangDAO.selectPrvCnrtCurrentBuildList(map);
	}
	
	@Override
	public List<Map<String, Object>> selectExPrvCnrtCurrentBuildList(Map<String, Object> map) throws Exception {
		return daejangDAO.selectExPrvCnrtCurrentBuildList(map);
	}
	
	/*������ ��*/
	@Override
	public List<Map<String, Object>> selectSatisfactionList(Map<String, Object> map) throws Exception {
		return daejangDAO.selectSatisfactionList(map);
	}
	
	
	// 발주계획 리스트 인설트
	@Override
	public void orderPlanPriorApprovalInsert(Map<String, Object> map,  HttpServletRequest request) throws Exception {
		
		//List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
	
		
		daejangDAO.orderPlanPriorApprovalInsert(map);
		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
		for(int i=0, size=list.size(); i<size; i++){
			daejangDAO.insertFile(list.get(i));
		}
	}
	
	
	// 발주계획 리스트
	@Override
	public List<Map<String, Object>> orderPlanPriorApprovalList(Map<String, Object> map) throws Exception {
		return daejangDAO.orderPlanPriorApprovalList(map);
	}
	
	
	
	@Override
	public Map<String, Object> prvCnrtPlanInfoTe(Map<String, Object> map) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = daejangDAO.rderPlanPriorAppInfo(map);
		resultMap.put("map", tempMap);
				
		List<Map<String,Object>> list = daejangDAO.selectFileList(map);
		resultMap.put("list", list);
		
		return resultMap;
	}
	
}
