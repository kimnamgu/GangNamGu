package corona.write.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import corona.common.dao.AbstractDAO;

@Repository("writeDAO")
public class WriteDAO extends AbstractDAO{

	/*확진자*/
	public void insertConfirmWrite(Map<String, Object> map) throws Exception{
		insert("write.insertConfirmWrite", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectConfirmDetail(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("write.selectConfirmDetail", map);
	}
	
	public void updateConfirm(Map<String, Object> map) throws Exception{
		System.out.println("dao updateConfirm확인");
		System.out.println("map확인" + map.toString());
		update("write.updateConfirm", map);
	}
	
	/*국내자가격리자*/
	public void insertDomesticWrite(Map<String, Object> map) throws Exception{
		insert("write.insertDomesticWrite", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectDomesticDetail(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("write.selectDomesticDetail", map);
	}
	
	public void updateDomestic(Map<String, Object> map) throws Exception{
		update("write.updateDomestic", map);
	}
	
	/*해외입국자*/
	public void insertOverseaWrite(Map<String, Object> map) throws Exception{
		insert("write.insertOverseaWrite", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectOverseaDetail(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("write.selectOverseaDetail", map);
	}
	
	public void updateOversea(Map<String, Object> map) throws Exception{
		System.out.println("map 확인 2 : " + map.toString());
		update("write.updateOversea", map);
	}
	
	/*상담민원*/
	public void insertConsultWrite(Map<String, Object> map) throws Exception{
		insert("write.insertConsultWrite", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectConsultDetail(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("write.selectConsultDetail", map);
	}
	
	public void updateConsult(Map<String, Object> map) throws Exception{
		update("write.updateConsult", map);
	}
	
	/*선별진료소*/
	public void insertClinicWrite(Map<String, Object> map) throws Exception{
		insert("write.insertClinicWrite", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectClinicDetail(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("write.selectClinicDetail", map);
	}
	
	public void updateClinic(Map<String, Object> map) throws Exception{
		System.out.println("dao updateClinic확인");
		System.out.println("map확인" + map.toString());
		update("write.updateClinic", map);
	}
}

