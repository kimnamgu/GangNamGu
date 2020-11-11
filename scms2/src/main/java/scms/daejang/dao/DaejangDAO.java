package scms.daejang.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import scms.common.dao.AbstractDAO;

@Repository("daejangDAO")
public class DaejangDAO extends AbstractDAO{

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectPrvCnrtCompList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectPrvCnrtCompList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectPrvCnrtCompInsList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectPrvCnrtCompInsList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectPrvCnrtContractList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectPrvCnrtContractList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectPrvCnrtDutyList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectPrvCnrtDutyList", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectPrvCnrtExpertList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectPrvCnrtExpertList", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectPrvCnrtReasonList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectPrvCnrtReasonList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectPrvCnrtEvalList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectPrvCnrtEvalList", map);
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectServiceContractEvalList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectSCotherList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectPrvCnrtPlanList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectPrvCnrtPlanList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectPrvCnrtAcceptList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectPrvCnrtAcceptList", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectPrvCnrtStatistics1(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectPrvCnrtStatistics1", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectPrvCnrtStatistics2(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectPrvCnrtStatistics2", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectPrvCnrtStatistics1List(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectPrvCnrtStatistics1List", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectCountList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectCountList", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> prvCnrtPlanInfo(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("daejang.prvCnrtPlanInfo", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> prvCnrtCompInfo(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("daejang.prvCnrtCompInfo", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> prvCnrtExpertUpdate(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("daejang.prvCnrtExpertUpdate", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> prvCnrtDutyUpdate(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("daejang.prvCnrtDutyUpdate", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> prvCnrtEvalUpdate(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("daejang.prvCnrtEvalUpdate", map);
	}
	
	
	
	public void insertPrvCnrtComp(Map<String, Object> map) throws Exception{
		insert("daejang.insertPrvCnrtComp", map);
	}
		
	public void insertPrvCnrtContract(Map<String, Object> map) throws Exception{
		insert("daejang.insertPrvCnrtContract", map);
	}
	
	
	public void updatePrvCnrtPlan(Map<String, Object> map) throws Exception{
		update("daejang.updatePrvCnrtPlan", map);
	}
	
	
	
	public void updatePrvCnrtComp(Map<String, Object> map) throws Exception{
		update("daejang.updatePrvCnrtComp", map);
	}
	
	
	
	public void updatePrvCnrtDuty(Map<String, Object> map) throws Exception{
		update("daejang.updatePrvCnrtDuty", map);
	}
	
	
	public void updatePrvCnrtExpert(Map<String, Object> map) throws Exception{
		update("daejang.updatePrvCnrtExpert", map);
	}
	
	public void updatePrvCnrtReason(Map<String, Object> map) throws Exception{
		update("daejang.updatePrvCnrtReason", map);
	}
		
	
	public void updatePrvCnrtEval(Map<String, Object> map) throws Exception{
		update("daejang.updatePrvCnrtEval", map);
	}
	
	
	public void deletePrvCnrtPlan(Map<String, Object> map) throws Exception{
		update("daejang.deletePrvCnrtPlan", map);
	}
	
	
	public void deletePrvCnrtComp(Map<String, Object> map) throws Exception{
		update("daejang.deletePrvCnrtComp", map);
	}
	
	
	public void deletePrvCnrtDuty(Map<String, Object> map) throws Exception{
		update("daejang.deletePrvCnrtDuty", map);
	}
	
	public void deletePrvCnrtExpert(Map<String, Object> map) throws Exception{
		update("daejang.deletePrvCnrtExpert", map);
	}
	
	
	
	public void insScOtherWork(Map<String, Object> map) throws Exception{
		insert("daejang.insScOtherWork", map);
	}
	
	public void insertPrvCnrtExpert(Map<String, Object> map) throws Exception{
		insert("daejang.insertPrvCnrtExpert", map);
	}
	
	
	public void insScReason(Map<String, Object> map) throws Exception{
		insert("daejang.insScOtherWork", map);
	}
	
	public void insScEval(Map<String, Object> map) throws Exception{
		insert("daejang.insScOtherWork", map);
	}
	
	public void insertPrvCnrtPlan(Map<String, Object> map) throws Exception{
		log.debug("TETE :: " + map.toString());
		insert("daejang.insertPrvCnrtPlan", map);
	}

	
	public void updateDgnsSubj(Map<String, Object> map) throws Exception{
		update("daejang.updateDgnsSubj", map);
	}
	
	public void updateDgnsQuest(Map<String, Object> map) throws Exception{
		update("daejang.updateDgnsQuest", map);
	}
	
	public void updateDgnsExamp(Map<String, Object> map) throws Exception{
		update("daejang.updateDgnsExamp", map);
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> chkSaup(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectList("daejang.chkSaup", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> popSaupList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectList("daejang.selectPrvCnrtPlanList", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> attachFileList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectList("daejang.attachFileList", map);
	}
	
	
	
	public void insertFile(Map<String, Object> map) throws Exception{
		insert("daejang.insertFile", map);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectFileList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectList("daejang.selectFileList", map);
	}

	public void deleteFileList(Map<String, Object> map) throws Exception{
		update("daejang.deleteFileList", map);
	}

	public void updateFile(Map<String, Object> map) throws Exception{
		update("daejang.updateFile", map);
	}
	
	
	
	
	public List<Map<String, Object>> selectPrvCnrtBoardList(Map<String, Object> map) throws Exception{
		
		return (List<Map<String, Object>>)selectPagingList("daejang.selectPrvCnrtBoardList", map);
	}

	public void insertPrvCnrtBoard(Map<String, Object> map) throws Exception{
		insert("daejang.insertPrvCnrtBoard", map);
	}
	
	public void updateHitCnt(Map<String, Object> map) throws Exception{
		update("daejang.updateHitCnt", map);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> selectPrvCnrtBoardDetail(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("daejang.selectPrvCnrtBoardDetail", map);
	}

	public void updatePrvCnrtBoard(Map<String, Object> map) throws Exception{
		update("daejang.updatePrvCnrtBoard", map);
	}

	public void deletePrvCnrtBoard(Map<String, Object> map) throws Exception{
		update("daejang.deletePrvCnrtBoard", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectPrvCnrtCurrentBuildList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectPrvCnrtCurrentBuildList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectExPrvCnrtCurrentBuildList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.selectExPrvCnrtCurrentBuildList", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectUpFileInfo(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("daejang.selectUpFileInfo", map);
	}
	
	public void updateDelAllFileList(Map<String, Object> map) throws Exception{
		update("daejang.updateDelAllFileList", map);
	}
	
	public void updateDelAllList(Map<String, Object> map) throws Exception{
		update("daejang.updateDelAllList", map);
	}
	
	/*������ �� ����Ʈ*/
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectSatisfactionList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectList("daejang.selectSatisfactionList", map);
	}
	
	
	public void orderPlanPriorApprovalInsert(Map<String, Object> map) throws Exception{
		insert("daejang.orderPlanPriorApprovalInsert", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> orderPlanPriorApprovalList(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList("daejang.orderPlanPriorApprovalList", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> rderPlanPriorAppInfo(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("daejang.rderPlanPriorAppInfo", map);
	}
	
	
	
	public void updatePrivateSaup(Map<String, Object> map) throws Exception{
		update("daejang.updatePrivateSaup", map);
	}

	
	
}
