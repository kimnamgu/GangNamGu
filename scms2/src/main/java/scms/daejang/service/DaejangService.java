package scms.daejang.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface DaejangService {

	List<Map<String, Object>> selectPrvCnrtCompList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectPrvCnrtCompInsList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectPrvCnrtContractList(Map<String, Object> map) throws Exception;
	
	
	
	
	List<Map<String, Object>> selectPrvCnrtDutyList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectPrvCnrtExpertList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectPrvCnrtReasonList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectPrvCnrtEvalList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectPrvCnrtPlanList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectPrvCnrtAcceptList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectPrvCnrtStatistics1(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectPrvCnrtStatistics2(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectPrvCnrtStatistics1List(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectCountList(Map<String, Object> map) throws Exception;
	
	
	Map<String, Object> prvCnrtPlanInfo(Map<String, Object> map) throws Exception;
	
	Map<String, Object> prvCnrtCompInfo(Map<String, Object> map) throws Exception;
	
	Map<String, Object> prvCnrtDutyUpdate(Map<String, Object> map) throws Exception;
		
	Map<String, Object> prvCnrtExpertUpdate(Map<String, Object> map) throws Exception;
	
	Map<String, Object> prvCnrtEvalUpdate(Map<String, Object> map) throws Exception;
	
	
	
	void insertPrvCnrtComp(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void insertPrvCnrtContract(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void insertPrvCnrtReason(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void updatePrvCnrtPlan(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void updatePrvCnrtComp(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void updatePrvCnrtDuty(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void updatePrvCnrtExpert(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void updatePrvCnrtReason(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void updatePrvCnrtEval(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	
	void deletePrvCnrtPlan(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void deletePrvCnrtComp(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void deletePrvCnrtDuty(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void deletePrvCnrtExpert(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	
	
	void insScOtherWork(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void insertPrvCnrtExpert(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void insScReason(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void insScEval(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	void insertPrvCnrtPlan(Map<String, Object> map, HttpServletRequest request) throws Exception;
	

	
	List<Map<String, Object>> chkSaup(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> popSaupList(Map<String, Object> map) throws Exception;
	
	
	List<Map<String, Object>> attachFileList(Map<String, Object> map) throws Exception;
	
	
	List<Map<String, Object>> selectPrvCnrtBoardList(Map<String, Object> map) throws Exception;

	void insertPrvCnrtBoard(Map<String, Object> map, HttpServletRequest request) throws Exception;

	Map<String, Object> selectPrvCnrtBoardDetail(Map<String, Object> map) throws Exception;

	void updatePrvCnrtBoard(Map<String, Object> map, HttpServletRequest request) throws Exception;

	void deletePrvCnrtBoard(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectPrvCnrtCurrentBuildList(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectExPrvCnrtCurrentBuildList(Map<String, Object> map) throws Exception;
	
	Map<String,Object> upEstFile(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	/*������ �� */
	List<Map<String, Object>> selectSatisfactionList(Map<String, Object> map) throws Exception;
	
	// 발주계획 리스트 인설트
	void orderPlanPriorApprovalInsert(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	
	// 발주계획 리스트
	List<Map<String, Object>> orderPlanPriorApprovalList(Map<String, Object> map ) throws Exception;

	Map<String, Object> prvCnrtPlanInfoTe(Map<String, Object> map) throws Exception;

	void updatePrivateSaup(Map<String, Object> map) throws Exception;

}
