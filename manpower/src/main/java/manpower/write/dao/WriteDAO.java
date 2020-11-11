package manpower.write.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import manpower.common.dao.AbstractDAO;

@Repository("writeDAO")
public class WriteDAO extends AbstractDAO{

	/*공지사항*/
	public void insertNoticeWrite(Map<String, Object> map) throws Exception{
		insert("write.insertNoticeWrite", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectNoticeDetail(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("write.selectNoticeDetail", map);
	}
	
	public void updateNotice(Map<String, Object> map) throws Exception{
		System.out.println("dao updateNotice확인");
		System.out.println("map확인" + map.toString());
		update("write.updateNotice", map);
	}
	
	/*은행코드관리*/
	public void insertBankWrite(Map<String, Object> map) throws Exception{
		insert("write.insertBankWrite", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectBankDetail(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("write.selectBankDetail", map);
	}
	
	public void updateBank(Map<String, Object> map) throws Exception{
		System.out.println("dao updateBank확인");
		System.out.println("map확인" + map.toString());
		update("write.updateBank", map);
	}
}
