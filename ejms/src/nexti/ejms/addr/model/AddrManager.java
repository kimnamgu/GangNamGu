/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ּ�ã�� manager
 * ����:
 */
package nexti.ejms.addr.model;

import java.util.List;

import nexti.ejms.addr.model.AddrDAO;

public class AddrManager {

	private static AddrManager instance = null;
	private static AddrDAO dao = null;
	
	public static AddrManager instance() {
		if (instance==null) instance = new AddrManager(); 
		return instance;
	}
	
	/**
	 * ���α׷� ���� DAO ��ü�� �޾ƿ´�.
	 * @return AddrDAO - ���α׷� ���� DAO ��ü
	 */
	private AddrDAO getAddrDAO(){
		if (dao==null) dao = new AddrDAO(); 
		return dao;
	}

	/**
	 * �ּ�ã�� ���
	 * @throws Exception 
	 */
	public List getAddrList(String sch_addr) throws Exception{
		List result = null;
		
		result = getAddrDAO().getAddrList(sch_addr);
		
		return result;		
	}
	/**
	 * ���θ� ������
	 * @throws Exception 
	 */
	public List getStreetProv() throws Exception{
		List result = null;
		
		result = getAddrDAO().getStreetProv();
		
		return result;		
	}
	/**
	 * ���θ� �ñ������
	 * @throws Exception 
	 */
	public List getStreetCity(AddrBean p) throws Exception{
		List result = null;
		
		result = getAddrDAO().getStreetCity(p);
		
		return result;		
	}
	/**
	 * ���θ� �ּҸ��
	 * @throws Exception 
	 */
	public List getStreetAddr(AddrBean p) throws Exception{
		List result = null;
		
		result = getAddrDAO().getStreetAddr(p);
		
		return result;		
	}

}
