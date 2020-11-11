package manpower.manage.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import manpower.common.dao.AbstractDAO;

@Repository("manageDAO")
public class ManageDAO extends AbstractDAO{
	
	/*공지사항*/
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectNoticeList(Map<String, Object> map) throws Exception{
		System.out.println("map 확인 : " + map.toString());
		return (List<Map<String, Object>>)selectPagingList("manage.selectNoticeList", map);
	}
	
	public void updateDelNoticeAllList(Map<String, Object> map) throws Exception{
		update("manage.updateDelNoticeAllList", map);
	}
	
	/*은행코드관리*/
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectBankList(Map<String, Object> map) throws Exception{
		System.out.println("map 확인 : " + map.toString());
		return (List<Map<String, Object>>)selectPagingList("manage.selectBankList", map);
	}
	
	public void updateDelBankAllList(Map<String, Object> map) throws Exception{
		update("manage.updateDelBankAllList", map);
	}
	
	@SuppressWarnings("unchecked")
	public int insertBankManageExcel(Map<String, String> paramMap) {
		int rtn = (int) insert("manage.insertBankManageExcel", paramMap);
		return rtn;
	}
	
}