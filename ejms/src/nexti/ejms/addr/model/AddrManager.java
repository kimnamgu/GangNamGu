/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 주소찾기 manager
 * 설명:
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
	 * 프로그램 관리 DAO 객체를 받아온다.
	 * @return AddrDAO - 프로그램 관리 DAO 객체
	 */
	private AddrDAO getAddrDAO(){
		if (dao==null) dao = new AddrDAO(); 
		return dao;
	}

	/**
	 * 주소찾기 목록
	 * @throws Exception 
	 */
	public List getAddrList(String sch_addr) throws Exception{
		List result = null;
		
		result = getAddrDAO().getAddrList(sch_addr);
		
		return result;		
	}
	/**
	 * 도로명 지역목
	 * @throws Exception 
	 */
	public List getStreetProv() throws Exception{
		List result = null;
		
		result = getAddrDAO().getStreetProv();
		
		return result;		
	}
	/**
	 * 도로명 시군구목록
	 * @throws Exception 
	 */
	public List getStreetCity(AddrBean p) throws Exception{
		List result = null;
		
		result = getAddrDAO().getStreetCity(p);
		
		return result;		
	}
	/**
	 * 도로명 주소목록
	 * @throws Exception 
	 */
	public List getStreetAddr(AddrBean p) throws Exception{
		List result = null;
		
		result = getAddrDAO().getStreetAddr(p);
		
		return result;		
	}

}
