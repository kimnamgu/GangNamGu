package corona.write.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import corona.common.dao.AbstractDAO;

@Repository("writeDAO")
public class WriteDAO extends AbstractDAO{

	/*Ȯ����*/
	public void insertConfirmWrite(Map<String, Object> map) throws Exception{
		insert("write.insertConfirmWrite", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectConfirmDetail(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("write.selectConfirmDetail", map);
	}
	
	public void updateConfirm(Map<String, Object> map) throws Exception{
		System.out.println("dao updateConfirmȮ��");
		System.out.println("mapȮ��" + map.toString());
		update("write.updateConfirm", map);
	}
	
	/*�����ڰ��ݸ���*/
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
	
	/*�ؿ��Ա���*/
	public void insertOverseaWrite(Map<String, Object> map) throws Exception{
		insert("write.insertOverseaWrite", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectOverseaDetail(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("write.selectOverseaDetail", map);
	}
	
	public void updateOversea(Map<String, Object> map) throws Exception{
		System.out.println("map Ȯ�� 2 : " + map.toString());
		update("write.updateOversea", map);
	}
	
	/*���ο�*/
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
	
	/*���������*/
	public void insertClinicWrite(Map<String, Object> map) throws Exception{
		insert("write.insertClinicWrite", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectClinicDetail(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("write.selectClinicDetail", map);
	}
	
	public void updateClinic(Map<String, Object> map) throws Exception{
		System.out.println("dao updateClinicȮ��");
		System.out.println("mapȮ��" + map.toString());
		update("write.updateClinic", map);
	}
}

