/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 주소찾기 dao
 * 설명:
 */
package nexti.ejms.addr.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;

public class AddrDAO {
	
	private static Logger logger = Logger.getLogger(AddrDAO.class);
	
	/**
	 * 결재하기 목록
	 * @throws Exception 
	 */
	public List getAddrList(String sch_addr) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List addrList = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("\n SELECT ZIPCODE, SIDO||' '||GUGUN || ' '|| DONG AS ADDR, BUNGI ");
			selectQuery.append("\n   FROM ADDR 			");			
			selectQuery.append("\n  WHERE DONG LIKE ? 	");
			selectQuery.append("\n  ORDER BY SEQ		");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if("".equals(sch_addr)){
				pstmt.setString(1, sch_addr);
			}else{
				pstmt.setString(1, "%"+sch_addr+"%");
			}
									
			rs = pstmt.executeQuery();
			
			addrList = new ArrayList();
			
			while (rs.next()) {
				AddrBean bean = new AddrBean();
				
				bean.setZipcode(rs.getString("ZIPCODE"));;
				bean.setAddr(rs.getString("ADDR"));
				bean.setBungi(rs.getString("BUNGI"));
				
				addrList.add(bean);
			}							
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return addrList;
	}
	/**
	 * 결재하기 목록
	 * @throws Exception 
	 */
	public List getStreetProv() throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List addrList = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("\n select addr_prov PROV,substr(street_nm_cd,0,2) ord,count(*) cnt  ");
			selectQuery.append("\n  from zip_radr_c 			");			
			selectQuery.append("\n  group by addr_prov,substr(street_nm_cd,0,2) 	");
			selectQuery.append("\n  order by ord		");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			
//			if("".equals(sch_addr)){
//				pstmt.setString(1, sch_addr);
//			}else{
//				pstmt.setString(1, "%"+sch_addr+"%");
//			}
									
			rs = pstmt.executeQuery();
			
			addrList = new ArrayList();
			
			while (rs.next()) {
				AddrBean bean = new AddrBean();
				
				bean.setProvince(rs.getString("PROV"));
				addrList.add(bean);
			}							
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return addrList;
	}
	/**
	 * 결재하기 목록
	 * @throws Exception 
	 */
	public List getStreetCity(AddrBean p) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List addrList = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("\n select addr_prov PROV,addr_city CITY,count(*) CNT   ");
			selectQuery.append("\n   from zip_radr_c			");		
			selectQuery.append("\n   where addr_prov=?			");			
			selectQuery.append("\n   group by addr_prov,addr_city 	");
			selectQuery.append("\n   order by addr_city		");
			
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			
			
			pstmt.setString(1, p.getProvince());
			rs = pstmt.executeQuery();
			
			addrList = new ArrayList();
			
			while (rs.next()) {
				AddrBean bean = new AddrBean();
				
				bean.setProvince(rs.getString("PROV"));
				bean.setCity(rs.getString("CITY"));
				addrList.add(bean);
			}							
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return addrList;
	}
	/**
	 * 결재하기 목록
	 * @throws Exception 
	 */
	public List getStreetAddr(AddrBean p) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List addrList = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("\n select distinct substr(zip,0,3)||'-'||substr(zip,3,3) ZIP,mti_dlvpl_nm BDNM,bd_no_main BDNO,flaw_dong_nm DONG   ");
			selectQuery.append("\n ,addr_prov||' '||addr_city||' '||street_nm ADDR   ");
			selectQuery.append("\n   from zip_radr_c			");		
			selectQuery.append("\n    where addr_prov=? and addr_city=? 	");
			if(p.getStreet()!= null && !p.getStreet().trim().equals(""))
				selectQuery.append("\n    and street_nm like ?||'%'			");			
			selectQuery.append("\n    order by addr	");
			System.out.println(selectQuery.toString());
			con = ConnectionManager.getConnection();
			
			pstmt = con.prepareStatement(selectQuery.toString());
			
			
			pstmt.setString(1, p.getProvince());
			pstmt.setString(2, p.getCity());
			if(p.getStreet()!= null && !p.getStreet().trim().equals(""))
				pstmt.setString(3, p.getStreet());
			
			rs = pstmt.executeQuery();
			
			addrList = new ArrayList();
			
			while (rs.next()) {
				AddrBean bean = new AddrBean();
				
				bean.setAddr(rs.getString("ADDR"));
				bean.setZipcode(rs.getString("ZIP"));
				bean.setBdno(rs.getString("BDNO"));
				bean.setBdnm(rs.getString("BDNM"));
				bean.setDong(rs.getString("DONG"));
				addrList.add(bean);
			}							
			
		} catch(Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return addrList;
	}

}
