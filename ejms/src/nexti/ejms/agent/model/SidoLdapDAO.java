package nexti.ejms.agent.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.agent.TaskBase;
import nexti.ejms.common.ConnectionManager;
import nexti.ejms.common.appInfo;
import nexti.ejms.dept.model.DeptBean;
import nexti.ejms.dept.model.DeptManager;
import nexti.ejms.user.model.UserBean;
import nexti.ejms.user.model.UserManager;
import nexti.ejms.util.Utils;

public class SidoLdapDAO extends TaskBase{
	private static Logger logger = Logger.getLogger(SidoLdapDAO.class);
	private static final String MALE="1";
	
	/**
	 * 시도LDAP 동기화 조직정보테이블의 동기화 데이터 가져오기.
	 */
	public List getSidoLdapList(Connection DBSido) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Connection con = null;
		PreparedStatement pstmt1 = null;
		
		List sidoLdapList = null;
		String maxdate = "";
		String pre_logdate = "";
		int pre_logseq = 0;
		int maxseq = 0;	
		int i = 0;
		int seq = 0;
		
		try{	
			//동기화최종정보테이블에서 최종으로 반영한 내용의 일자와 순번을 가져온다.
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append(" SELECT MAX(LOG_DATE) LOG_DATE, MAX(LOG_SEQ) LOG_SEQ	\n");
			selectQuery.append(" FROM CMMANLDLASTWRK									\n");
			selectQuery.append(" WHERE TRIM(SYS_GBN) = ?								\n");
			
			pstmt = DBSido.prepareStatement(selectQuery.toString());
			pstmt.setString(1, appInfo.getSidoldapSysgbn());
			rs = pstmt.executeQuery();	
			
			if(rs.next()){
				maxdate = rs.getString("LOG_DATE");
				maxseq = rs.getInt("LOG_SEQ");
			}
			
			setLastRunStat("최종변경이력 : 일자(" + maxdate + "), 순번(" + maxseq + ")");
			logger.info(getLastRunStat());

			try {rs.close();} catch (Exception e) {}
			try {pstmt.close();} catch (Exception e) {}
			
			//LOG_SEQ 최대길이 체크
			selectQuery.delete(0, selectQuery.capacity());
			selectQuery.append(" SELECT LENGTH(MAX(LOG_SEQ))   \n");
			selectQuery.append(" FROM CMMANLDSYDTL			   \n");
			
			pstmt = DBSido.prepareStatement(selectQuery.toString());
			rs = pstmt.executeQuery();

			try {rs.close();} catch (Exception e) {}
			try {pstmt.close();} catch (Exception e) {}

			maxdate = Utils.lPadCut(String.valueOf(maxdate), 14, "0");
			
			//동기화정보의 최종정보이후의 변경내용에 대한 일시 순서 사용자구분 명령형식을 가져온다.
			StringBuffer selectQuery1 = new StringBuffer();
			selectQuery1.append(" SELECT A.LOG_DATE LOG_DATE, A.LOG_SEQ LOG_SEQ, A.USER_GB USER_GB, A.DN, DECODE(A.USER_GB,'0',V.CHAR_USR_ID,'1',A.CODE) CODE,     			\n"); 
			selectQuery1.append("        A.TYPE, B.ATTR_NAME ATTR_NAME, B.ATTR_VAL ATTR_VAL                                     \n");
			selectQuery1.append(" FROM CMMANLDSYINF A,                                                                          \n");
			selectQuery1.append(" 		sidodba.BUSAN_ALRIMI_USER_VIEW V,                                                                          \n");
			selectQuery1.append("      (SELECT * FROM CMMANLDSYDTL                                                              \n");
			selectQuery1.append("       WHERE ATTR_NAME IN('resiNumber', 'displayName', 'ouCode', 'ou', 'orgFullName',          \n");	
			selectQuery1.append("                          'parentOUCode', 'positionCode', 'position', 'titleCode', 'title',    \n");	
			selectQuery1.append("                          'titleOrPosition', 'order', 'homePhone', 'mail', 'mobile',  		    \n");
			selectQuery1.append("                          'status', 'ouLevel', 'ouOrder', 'telephoneNumber', 'ouFax',          \n");	
			selectQuery1.append("                          'isSidoOnly', 'virParentOUCode', 'gender', 'topOUCode')              \n");
			if ( !"".equals(maxdate) && maxseq !=0 ) {
				selectQuery1.append("      AND ((LOG_DATE = '"+maxdate+"' AND LOG_SEQ > '"+maxseq+"') OR LOG_DATE > '"+maxdate+"') \n");
			} else {
				selectQuery1.append("      AND ((LOG_DATE = '20000101000000' AND LOG_SEQ > '1') OR LOG_DATE > '20000101000000') \n");
			}
			selectQuery1.append("      )B                                                                                       \n");
			selectQuery1.append(" WHERE A.LOG_DATE = B.LOG_DATE(+)                                                              \n");
			selectQuery1.append(" AND A.LOG_SEQ = B.LOG_SEQ(+)                                                                  \n");
			selectQuery1.append(" AND A.CODE = V.SID    																\n");
			selectQuery1.append(" AND ((A.USER_GB = '0' AND V.CHAR_USR_ID IS NOT NULL) OR A.USER_GB = '1')						\n");
//			selectQuery1.append(" AND A.USER_GB IN ('0', '1')                                                                   \n");
			if ( !"".equals(maxdate) && maxseq !=0 ) {
				selectQuery1.append("      AND ((A.LOG_DATE = '"+maxdate+"' AND A.LOG_SEQ > '"+maxseq+"') OR A.LOG_DATE > '"+maxdate+"') \n");
			}else{
				selectQuery1.append("      AND ((A.LOG_DATE = '20000101000000' AND A.LOG_SEQ > '1') OR A.LOG_DATE > '20000101000000') \n");
			}
			selectQuery1.append(" ORDER BY A.LOG_DATE, A.LOG_SEQ                                                                \n");
			
			pstmt = DBSido.prepareStatement(selectQuery1.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
			rs = pstmt.executeQuery();	
			
			rs.last(); 
			int count = rs.getRow(); 
			rs.beforeFirst(); 
			
			sidoLdapList = new ArrayList();
			SidoLdapBean bean = null;
			
		    con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			
			StringBuffer insertQuery = new StringBuffer();
			StringBuffer deleteQuery = new StringBuffer();
			
			deleteQuery.append("TRUNCATE TABLE SIDOLDAP_TEMP ");
		
			insertQuery.append("INSERT INTO SIDOLDAP_TEMP (SEQ, LOG_DATE, LOG_SEQ, USER_GB, DN, CODE, \n");     			
		    insertQuery.append("        TYPE, ATTR_NAME, ATTR_VAL) VALUES (?,?,?,?,?,?, ?,?,?) \n");
			
			pstmt1 = con.prepareStatement(deleteQuery.toString());
			pstmt1.executeUpdate();
			
			ConnectionManager.close(pstmt1);
			
			pstmt1 = con.prepareStatement(insertQuery.toString());
			
			while(rs.next()){
				int index = 0;
				pstmt1.setInt(++index, seq++);
				pstmt1.setString(++index, rs.getString("LOG_DATE"));
				pstmt1.setInt(++index, rs.getInt("LOG_SEQ"));
				pstmt1.setString(++index, rs.getString("USER_GB"));
				pstmt1.setString(++index, rs.getString("DN"));
				pstmt1.setString(++index, rs.getString("CODE"));
				
				pstmt1.setString(++index, rs.getString("TYPE"));
				pstmt1.setString(++index, rs.getString("ATTR_NAME"));
				pstmt1.setString(++index, rs.getString("ATTR_VAL"));
				
				pstmt1.executeUpdate();	
				
				
				if(pre_logdate.equals(rs.getString("LOG_DATE"))&&(pre_logseq == rs.getInt("LOG_SEQ"))){
					if("resiNumber".equals(rs.getString("ATTR_NAME"))){
						bean.setResinumber(rs.getString("ATTR_VAL"));
					}else if("displayName".equals(rs.getString("ATTR_NAME"))){
						bean.setDisplayname(rs.getString("ATTR_VAL"));
					}else if("ouCode".equals(rs.getString("ATTR_NAME"))){
						bean.setOucode(rs.getString("ATTR_VAL"));
					}else if("ou".equals(rs.getString("ATTR_NAME"))){
						bean.setOu(rs.getString("ATTR_VAL"));
					}else if("orgFullName".equals(rs.getString("ATTR_NAME"))){
						bean.setOrgfullname(rs.getString("ATTR_VAL"));
						
					}else if("parentOUCode".equals(rs.getString("ATTR_NAME"))){
						bean.setParentoucode(rs.getString("ATTR_VAL"));
					}else if("positionCode".equals(rs.getString("ATTR_NAME"))){
						bean.setPositioncode(rs.getString("ATTR_VAL"));
					}else if("position".equals(rs.getString("ATTR_NAME"))){
						bean.setPosition(rs.getString("ATTR_VAL"));
					}else if("titleCode".equals(rs.getString("ATTR_NAME"))){
						bean.setTitlecode(rs.getString("ATTR_VAL"));
					}else if("title".equals(rs.getString("ATTR_NAME"))){
						bean.setTitle(rs.getString("ATTR_VAL"));
					
					}else if("titleOrPosition".equals(rs.getString("ATTR_NAME"))){
						bean.setTitleorposition(rs.getString("ATTR_VAL"));
					}else if("order".equals(rs.getString("ATTR_NAME"))){
						bean.setUsrorder(rs.getString("ATTR_VAL"));
					}else if("homePhone".equals(rs.getString("ATTR_NAME"))){
						bean.setHomephone(rs.getString("ATTR_VAL"));
					}else if("mail".equals(rs.getString("ATTR_NAME"))){
						bean.setMail(rs.getString("ATTR_VAL"));
					}else if("mobile".equals(rs.getString("ATTR_NAME"))){
						bean.setMobile(rs.getString("ATTR_VAL"));
					
					}else if("status".equals(rs.getString("ATTR_NAME"))){
						bean.setStatus(rs.getString("ATTR_VAL"));
					}else if("ouLevel".equals(rs.getString("ATTR_NAME"))){
						bean.setOulevel(rs.getString("ATTR_VAL"));
					}else if("ouOrder".equals(rs.getString("ATTR_NAME"))){
						bean.setOuorder(rs.getString("ATTR_VAL"));
					}else if("telephoneNumber".equals(rs.getString("ATTR_NAME"))){
						bean.setTelephonenumber(rs.getString("ATTR_VAL"));
					}else if("ouFax".equals(rs.getString("ATTR_NAME"))){
						bean.setOufax(rs.getString("ATTR_VAL"));
					
					}else if("isSidoOnly".equals(rs.getString("ATTR_NAME"))){
						bean.setIssidoonly(rs.getString("ATTR_VAL"));
					}else if("virParentOUCode".equals(rs.getString("ATTR_NAME"))){
						bean.setVirparentoucode(rs.getString("ATTR_VAL"));
					}else if("gender".equals(rs.getString("ATTR_NAME"))){
						bean.setGender(rs.getString("ATTR_VAL"));
					}else if("topOUCode".equals(rs.getString("ATTR_NAME"))){
						bean.setTopoucode(rs.getString("ATTR_VAL"));
					}
					
					if(i==count-1){
						sidoLdapList.add(bean);
					}
				}else{
					if(i!=0){
						sidoLdapList.add(bean);
					}
					bean =  new SidoLdapBean();
					bean.setLogdate(rs.getString("LOG_DATE"));
					bean.setLogseq(rs.getInt("LOG_SEQ"));
					bean.setUsergb(rs.getString("USER_GB"));
					bean.setDn(rs.getString("DN"));
					bean.setCode(rs.getString("CODE"));
					
					bean.setType(rs.getString("TYPE"));
					if("resiNumber".equals(rs.getString("ATTR_NAME"))){
						bean.setResinumber(rs.getString("ATTR_VAL"));
					}else if("displayName".equals(rs.getString("ATTR_NAME"))){
						bean.setDisplayname(rs.getString("ATTR_VAL"));
					}else if("ouCode".equals(rs.getString("ATTR_NAME"))){
						bean.setOucode(rs.getString("ATTR_VAL"));
					}else if("ou".equals(rs.getString("ATTR_NAME"))){
						bean.setOu(rs.getString("ATTR_VAL"));
					}else if("orgFullName".equals(rs.getString("ATTR_NAME"))){
						bean.setOrgfullname(rs.getString("ATTR_VAL"));
						
					}else if("parentOUCode".equals(rs.getString("ATTR_NAME"))){
						bean.setParentoucode(rs.getString("ATTR_VAL"));
					}else if("positionCode".equals(rs.getString("ATTR_NAME"))){
						bean.setPositioncode(rs.getString("ATTR_VAL"));
					}else if("position".equals(rs.getString("ATTR_NAME"))){
						bean.setPosition(rs.getString("ATTR_VAL"));
					}else if("titleCode".equals(rs.getString("ATTR_NAME"))){
						bean.setTitlecode(rs.getString("ATTR_VAL"));
					}else if("title".equals(rs.getString("ATTR_NAME"))){
						bean.setTitle(rs.getString("ATTR_VAL"));
					
					}else if("titleOrPosition".equals(rs.getString("ATTR_NAME"))){
						bean.setTitleorposition(rs.getString("ATTR_VAL"));
					}else if("order".equals(rs.getString("ATTR_NAME"))){
						bean.setUsrorder(rs.getString("ATTR_VAL"));
					}else if("homeFaxPhoneNumber".equals(rs.getString("ATTR_NAME"))){
						bean.setHomephone(rs.getString("ATTR_VAL"));
					}else if("mail".equals(rs.getString("ATTR_NAME"))){
						bean.setMail(rs.getString("ATTR_VAL"));
					}else if("mobile".equals(rs.getString("ATTR_NAME"))){
						bean.setMobile(rs.getString("ATTR_VAL"));
					
					}else if("status".equals(rs.getString("ATTR_NAME"))){
						bean.setStatus(rs.getString("ATTR_VAL"));
					}else if("ouLevel".equals(rs.getString("ATTR_NAME"))){
						bean.setOulevel(rs.getString("ATTR_VAL"));
					}else if("ouOrder".equals(rs.getString("ATTR_NAME"))){
						bean.setOuorder(rs.getString("ATTR_VAL"));
					}else if("telephoneNumber".equals(rs.getString("ATTR_NAME"))){
						bean.setTelephonenumber(rs.getString("ATTR_VAL"));
					}else if("ouFax".equals(rs.getString("ATTR_NAME"))){
						bean.setOufax(rs.getString("ATTR_VAL"));
					
					}else if("isSidoOnly".equals(rs.getString("ATTR_NAME"))){
						bean.setIssidoonly(rs.getString("ATTR_VAL"));
					}else if("virParentOUCode".equals(rs.getString("ATTR_NAME"))){
						bean.setVirparentoucode(rs.getString("ATTR_VAL"));
					}else if("gender".equals(rs.getString("ATTR_NAME"))){
						bean.setGender(rs.getString("ATTR_VAL"));
					}else if("topOUCode".equals(rs.getString("ATTR_NAME"))){
						bean.setTopoucode(rs.getString("ATTR_VAL"));
					}
					
					//최종 변경 ATTRIBURE 구하기 
					String last_attribute = "";
					if(rs.getString("TYPE").equals("U")) last_attribute = getLastAttribute(DBSido, rs.getString("LOG_DATE"), rs.getInt("LOG_SEQ"));
					bean.setLast_attribute(last_attribute);
					
					if(i==count-1){
						sidoLdapList.add(bean);
					}
				}
				
				pre_logdate = rs.getString("LOG_DATE");
				pre_logseq = rs.getInt("LOG_SEQ");
				i++;
			}
			
			con.commit();
			
		 } catch (Exception e) {
			 try {con.rollback();} catch (Exception ex) {}
			 logger.error("ERROR", e);
			 throw new Exception("getSidoLdapList() error", e);
	     } finally {	
	    	 try {con.setAutoCommit(true);} catch (Exception e){}
	    	 try {rs.close();} catch (Exception e) {}
	    	 try {pstmt.close();} catch (Exception e) {}	
	    	 try {pstmt1.close();} catch (Exception e) {}
	    	 ConnectionManager.close(con);
	     }
	     
	     return sidoLdapList;
	}

	/**
	 * 최종 변경 ATTRIBURE 구하기
	 */
	public String getLastAttribute(Connection DBSido, String log_date, int log_seq) throws Exception{
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		String last_attribute = "";		
		
		try {
			StringBuffer selectQuery = new StringBuffer();			
			selectQuery.append(" SELECT SUBSTR(MAX(SYS_CONNECT_BY_PATH(ATTR_NAME, ',') ),2) AS LAST_ATTRIBUTE       	                         \n"); 		
			selectQuery.append(" FROM                                                      	 		                                             \n");
			selectQuery.append(" 	(                                                        			                                         \n");
			selectQuery.append(" 	SELECT A.LOG_DATE, ATTR_NAME, ATTR_VAL, ROW_NUMBER () OVER (PARTITION BY A.LOG_DATE ORDER BY A.LOG_DATE) RN  \n");                 			
			selectQuery.append(" 	FROM CMMANLDSYINF A, CMMANLDSYDTL B                      			                                         \n");
			selectQuery.append(" 	WHERE A.LOG_DATE = B.LOG_DATE(+) AND A.LOG_SEQ = B.LOG_SEQ(+)                                                \n");
			selectQuery.append(" 	AND A.TYPE = 'U' AND A.LOG_DATE like '"+log_date+"'  			                                             \n");
			selectQuery.append(" 	AND A.LOG_SEQ =  "+log_seq+" AND ATTR_VAL IS NOT NULL     			                                         \n");
			selectQuery.append(" 	)                                                                                                            \n");
			selectQuery.append(" START WITH RN = 1                                                                                             	 \n");
			selectQuery.append(" CONNECT BY  PRIOR RN = RN -1                                                                                  	 \n");
			
			pstmt =	DBSido.prepareStatement(selectQuery.toString());

			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				last_attribute = rs.getString(1);
			}

		 } catch (Exception e) {
			logger.error("ERROR", e);
			ConnectionManager.close(pstmt,rs);
			throw e;
	     } finally {	       
			ConnectionManager.close(pstmt,rs);
	     }
	     
		return last_attribute;
	}	
	
	/**
	 * 변경된 LDAP동기화정보를 사용자정보와 부서정보 테이블에 갱신 - [관]LDAP사용자정보(연계용), [관]부서정보임시(연계용)    
	 */    
	public int changeSidoLDAP1(List sidoLdapList) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		
		int result  = 0;
		
		int userInsertTry = 0;
		int userUpdateTry = 0;
		int deptInsertTry = 0;
		int deptUpdateTry = 0;
		int userInsertComp = 0;
		int userUpdateComp = 0;
		int deptInsertComp = 0;
		int deptUpdateComp = 0;
		int wrongData = 0;
		int ignoreData = 0;
		int updateCount = 0;
		String maxIdCount = "";
		
		try{			
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);

			for ( int i = 0; i < sidoLdapList.size(); i++ ) {
				SidoLdapBean bean = (SidoLdapBean)sidoLdapList.get(i);

				//사용자구분 > 0:사용자, 1:조직
				if ( "0".equals(bean.getUsergb()) ) {		//사용자정보 업데이트
					if ( bean.getResinumber().equals("") ) bean.setResinumber(bean.getCode());
					
					if ( "I".equals(bean.getType()) ) {		//추가(I)
						UserBean ubean = UserManager.instance().getUserInfoForRegno(bean.getResinumber());
						if ( ubean == null ) {		//사용자가 없으면
							userInsertTry++;
							int rst = 0;
							DeptBean dbean = DeptManager.instance().getDeptInfo(bean.getOucode());
							if ( dbean != null ) {
								if ( "".equals(bean.getDisplayname().trim()) ) bean.setDisplayname("이름없음");
								
								if ( "".equals(maxIdCount) ) {
									maxIdCount = UserManager.instance().getNextIdSeq(con, MALE);
								} else {
									maxIdCount = Integer.toString(Integer.parseInt(maxIdCount) + 1);
								}
								maxIdCount = Utils.lPadCut(maxIdCount, 7, "0");
								
								if ( MALE.equals("1") ) {
									bean.setUser_id("A" + maxIdCount);
								} else {
									bean.setUser_id("B" + maxIdCount);
								}
	
								if ( bean.getUser_id().length() <= 20 ) {	
									rst = userTempInsert(con, bean); 
									if ( rst > 0 ) {
										updateCount += rst;
										userInsertComp += rst;
									}
								}
							}
							if ( rst == 0 ) ignoreData++;
						} else if ( ubean != null ) {		//사용자가 있으면
							bean.setType("U");
							userUpdateTry++;
							int rst = userTempUpdate(con, bean);
							if ( rst > 0 ) {
								updateCount += rst;
								userUpdateComp += rst;
							} else {
								ignoreData++;
							}
							if ( rst == 0 ) ignoreData++;
						}
					} else if ( "U".equals(bean.getType()) || "D".equals(bean.getType()) ) {		//갱신(U), 삭제(D)
						userUpdateTry++;
						int rst = userTempUpdate(con, bean);
						if ( rst > 0 ) {
							updateCount += rst;
							userUpdateComp += rst;
						} else {
							ignoreData++;
						}
						if ( rst == 0 ) ignoreData++;
					} else {
						wrongData++;
					}
				} else if ( "1".equals(bean.getUsergb()) ) {		//부서정보 업데이트					
					if ( bean.getOucode().equals("") ) bean.setOucode(bean.getCode());
					if ("I".equals(bean.getType())) {			//삽입(I)
						DeptBean dbean = DeptManager.instance().getDeptInfo(bean.getOucode());
						if ( dbean == null ) {		//부서가 없으면
							deptInsertTry++;
							int rst = deptTempInsert(con, bean);
							if ( rst > 0 ) {
								updateCount += rst;
								deptInsertComp += rst;
							} else {
								ignoreData++;
							}
						} else {		//부서가 있으면
							bean.setType("U");
							deptUpdateTry++;					
							int rst = deptTempUpdate(con, bean);
							if ( rst > 0 ) {
								updateCount += rst;
								deptUpdateComp += rst;
							} else {
								ignoreData++;
							}
						}
					} else if( "U".equals(bean.getType()) || "D".equals(bean.getType()) ) {		//갱신(U), 삭제(D)
						deptUpdateTry++;					
						int rst = deptTempUpdate(con, bean);
						if ( rst > 0 ) {
							updateCount += rst;
							deptUpdateComp += rst;
						} else {
							ignoreData++;
						}
					} else {
						wrongData++;
					}
				} else {
					wrongData++;
				}
				
				if ( updateCount > 0 ) {
					result += updateCount;
					updateCount = 0;
					con.commit();
				}
			}
			
			if ( result > 0 ) {
				//하위부서가 상위부서보다 먼저 추가(Insert)되면 대표기관코드를 가져올 수 없으므로 조직변경 후 대표기관코드를 적용
				for ( int i=0; i<sidoLdapList.size(); i++ ) {
					SidoLdapBean bean = (SidoLdapBean)sidoLdapList.get(i);
					
					if ( "1".equals(bean.getUsergb()) ) {
						//if ( bean.getCode().length() > 7 ) continue;
						if ( "I".equals(bean.getType()) || "U".equals(bean.getType()) ) {	//삽입(I)과  갱신(U)
							deptTempUpdate(con, bean);
						}
					}
				}
				
				con.commit();
				
				logger.info("UI(" + userInsertTry + ":" + userInsertComp + "), " +
							"UU(" + userUpdateTry + ":" + userUpdateComp + "), " +
							"DI(" + deptInsertTry + ":" + deptInsertComp + "), " +
							"DU(" + deptUpdateTry + ":" + deptUpdateComp + "), " +
							"wrongData(" + wrongData + "), " +
							"ignoreData(" + ignoreData + ")");
			}
			
		} catch ( Exception e ) {			
			result = 0;
			try { con.rollback(); } catch ( Exception ex ) {}
			logger.error("ERROR : changeSidoLDAP1() : changeSidoLDAP1 오류발생으로 중단");
			throw e;
	     } finally {
	    	 try { con.setAutoCommit(true); } catch ( Exception e ){}
	    	 ConnectionManager.close(con,pstmt);	  
	     }
	     
	     return result;    
	}
	
	/**
	 * 사용자정보 등록 - [관]LDAP사용자정보(연계용)
	 */
	public int userTempInsert(Connection con, SidoLdapBean bean) throws Exception {
		PreparedStatement pstmt = null;	
		
		int result = 0;		
		int index  = 0;
				
		try{			
            //사용자정보 저장
			StringBuffer insertQuery = new StringBuffer();
			try {
				insertQuery.append("DELETE USR_TEMP_LDAP WHERE APPLY_YN = 'N' AND TRIM(RESINUMBER) = TRIM('" + bean.getCode() +"')");
				con.prepareStatement(insertQuery.toString()).executeUpdate();
			} finally {
				insertQuery.delete(0, insertQuery.capacity());
				ConnectionManager.close(pstmt);
			}
			
			insertQuery.append(" INSERT INTO USR_TEMP_LDAP                                              \n");
			insertQuery.append("             (RESINUMBER, DISPLAYNAME, OUCODE, OU, ORGFULLNAME,         \n");
			insertQuery.append("             PARENTOUCODE, POSITIONCODE, POSITION, TITLECODE, TITLE,    \n");
			insertQuery.append("             TITLEORPOSITION, USRORDER, HOMEPHONE, MAIL, MOBILE,        \n");
			insertQuery.append("             STATUS, CMDTYPE,  LAST_ATTRIBUTE, APPLY_YN, APPLYDT,       \n");
			insertQuery.append("             CRTDT, USER_ID)                                            \n");
			insertQuery.append(" SELECT TRIM(?), ?, ?, ?, ?,                                            \n");
			insertQuery.append("        ?, ?, ?, ?, ?,                                                  \n");
			insertQuery.append("        ?, ?, ?, ?, ?,                                                  \n");
			insertQuery.append("        ?, ?, ?, ?, TO_CHAR(SYSDATE,'YYYYMMDD HH24:MI:SS'),             \n");
			insertQuery.append("        TO_CHAR(SYSDATE,'YYYYMMDD HH24:MI:SS'), ?                       \n");
			insertQuery.append(" FROM DUAL WHERE NOT EXISTS (SELECT * FROM USR_TEMP_LDAP WHERE TRIM(RESINUMBER) = TRIM(?)) \n");
						
			pstmt = con.prepareStatement(insertQuery.toString());
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getCode()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getDisplayname()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOucode()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOu()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOrgfullname()));
			
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getParentoucode()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getPositioncode()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getPosition()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getTitlecode()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getTitle()));
			
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getTitleorposition()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getUsrorder()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getTelephonenumber()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getMail()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getMobile()));

			pstmt.setString(++index, Utils.nullToEmptyString(bean.getStatus()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getType()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getLast_attribute()));
			pstmt.setString(++index, "Y");
			
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getUser_id()));
			
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getCode()));

			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			result = 0;
	     } finally {	
	    	try {pstmt.close();} catch (Exception e) {}
	     }	
		return result;
	}
	
	
	/**
	 * 사용자정보 수정 - [관]LDAP사용자정보(연계용)
	 */
	public int userTempUpdate(Connection con, SidoLdapBean bean)  throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;		
		int index  = 0;	
		
		try{			
			// 1. 시도ldapList TYPE UPDATE, DELETE 이면 부서 코드를 수정한다.
        	StringBuffer updateQuery = new StringBuffer();
        	updateQuery.append(" UPDATE USR_TEMP_LDAP																													\n");
        	updateQuery.append(" SET DISPLAYNAME = NVL(?,DISPLAYNAME), OUCODE = NVL(?,OUCODE), OU = NVL(?,OU), ORGFULLNAME = NVL(?,ORGFULLNAME),                       	\n");
        	updateQuery.append(" 	 PARENTOUCODE = NVL(?,PARENTOUCODE), POSITIONCODE = NVL(?,POSITIONCODE), POSITION = NVL(?,POSITION), TITLECODE = NVL(?,TITLECODE),  \n");
        	updateQuery.append(" 	 TITLE = NVL(?,TITLE), TITLEORPOSITION = NVL(?,TITLEORPOSITION), USRORDER = NVL(?,USRORDER), HOMEPHONE = NVL(?,HOMEPHONE),          \n");
        	updateQuery.append(" 	 MAIL = NVL(?,MAIL), MOBILE = NVL(?,MOBILE), STATUS = NVL(?,STATUS), CMDTYPE = NVL(?,CMDTYPE),                                      \n");
        	updateQuery.append("  	 LAST_ATTRIBUTE = NVL(?,LAST_ATTRIBUTE), APPLY_YN = NVL(?,APPLY_YN), APPLYDT = TO_CHAR(SYSDATE,'YYYYMMDD HH24:MI:SS'),              \n");
        	updateQuery.append(" 	 UPTDT = TO_CHAR(SYSDATE,'YYYYMMDD HH24:MI:SS'), USER_ID = NVL(?,USER_ID)                                                           \n");
        	updateQuery.append(" WHERE TRIM(RESINUMBER) = TRIM(?)  																													\n");
        	
			pstmt = con.prepareStatement(updateQuery.toString());
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getDisplayname()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOucode()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOu()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOrgfullname()));
			
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getParentoucode()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getPositioncode()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getPosition()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getTitlecode()));
			
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getTitle()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getTitleorposition()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getUsrorder()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getTelephonenumber()));
			
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getMail()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getMobile()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getStatus()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getType()));
			
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getLast_attribute()));
			pstmt.setString(++index, "Y");
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getUser_id()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getCode()));
			
			result = pstmt.executeUpdate();
	        
		}catch (Exception e) {
			result = 0;
	     } finally {	
			try {pstmt.close();} catch (Exception e) {}
	     }	
		return result;
	}
	
	/**
	 * 부서정보 등록 - [관]부서정보임시(연계용)
	 */
	public int deptTempInsert(Connection con, SidoLdapBean bean) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		int index  = 0;
				
		try{			
            //조직정보 저장
			StringBuffer insertQuery = new StringBuffer();
			try {
				insertQuery.append("DELETE DEPT_TEMP_LDAP WHERE APPLY_YN = 'N' AND TRIM(OUCODE) = TRIM('" + bean.getCode() +"')");
				con.prepareStatement(insertQuery.toString()).executeUpdate();
			} finally {
				insertQuery.delete(0, insertQuery.capacity());
				ConnectionManager.close(pstmt);
			}
			
			insertQuery.append(" INSERT INTO DEPT_TEMP_LDAP(OUCODE, OU, ORGFULLNAME, PARENTOUCODE, TOPOUCODE,       	  \n");
			insertQuery.append("                          OULEVEL, OUORDER, TELEPHONENUMBER, OUFAX, ISSIDOONLY,           \n");
			insertQuery.append("                          STATUS, VIRPARENTOUCODE, CMDTYPE, LAST_ATTRIBUTE, APPLY_YN,      \n");
			insertQuery.append("                          APPLYDT, CRTDT)                                                 \n");
			insertQuery.append(" SELECT TRIM(?), ?, ?, ?, ?,                                                              \n");
			insertQuery.append("        ?, ?, ?, ?, ?,                                                                    \n");
			insertQuery.append("        ?, ?, ?, ?, ?,                                                                    \n");
			insertQuery.append("        TO_CHAR(SYSDATE,'YYYYMMDD HH24:MI:SS'), TO_CHAR(SYSDATE,'YYYYMMDD HH24:MI:SS')    \n");
			insertQuery.append(" FROM DUAL WHERE NOT EXISTS (SELECT * FROM DEPT_TEMP_LDAP WHERE TRIM(OUCODE) = TRIM(?))   \n");
						
			pstmt = con.prepareStatement(insertQuery.toString());
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getCode()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOu()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOrgfullname()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getParentoucode()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getTopoucode()));
			
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOulevel()));
			if("".equals(Utils.nullToEmptyString(bean.getOuorder()))) bean.setOuorder("99999");
			pstmt.setInt(++index, Integer.parseInt(bean.getOuorder()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getTelephonenumber()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOufax()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getIssidoonly()));

			pstmt.setString(++index, Utils.nullToEmptyString(bean.getStatus()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getVirparentoucode()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getType()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getLast_attribute()));
			pstmt.setString(++index, "Y");
			
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getCode()));
			
			result = pstmt.executeUpdate();				
			
		} catch (Exception e) {
			result = 0;
			logger.error("ERROR : deptTempInsert() : 잘못된 값이 있어 해당 데이터 무시");
	     } finally {	
	    	try {pstmt.close();} catch (Exception e) {}
	     }	
		return result;
	}
	
	
	/**
	 * 부서정보 업데이트 - [관]부서정보임시(연계용)
	 */
	public int deptTempUpdate(Connection con, SidoLdapBean bean)  throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;			
		int index  = 0;
		
		try{
			// 1. 시도ldapList TYPE UPDATE, DELETE 이면 부서 코드를 수정한다.
        	StringBuffer updateQuery = new StringBuffer();
        	updateQuery.append(" UPDATE DEPT_TEMP_LDAP                                                                                                                 \n"); 
        	updateQuery.append("        SET OU = NVL(?,OU), ORGFULLNAME = NVL(?,ORGFULLNAME), PARENTOUCODE = NVL(?,PARENTOUCODE), TOPOUCODE = NVL(?,TOPOUCODE),        \n");
        	updateQuery.append(" 		   OULEVEL = NVL(?,OULEVEL), OUORDER = DECODE(?, -1, OUORDER, ?), TELEPHONENUMBER = NVL(?,TELEPHONENUMBER), OUFAX = NVL(?,OUFAX),         \n");
        	updateQuery.append(" 		   ISSIDOONLY = NVL(?,ISSIDOONLY), STATUS = NVL(?,STATUS), VIRPARENTOUCODE = NVL(?,VIRPARENTOUCODE), CMDTYPE = NVL(?,CMDTYPE), \n");
        	updateQuery.append(" 		   LAST_ATTRIBUTE = NVL(?,LAST_ATTRIBUTE), APPLY_YN = NVL(?,APPLY_YN), APPLYDT = TO_CHAR(SYSDATE,'YYYYMMDD HH24:MI:SS'),       \n");
        	updateQuery.append(" 		   UPTDT = TO_CHAR(SYSDATE,'YYYYMMDD HH24:MI:SS')                                                                              \n");
        	updateQuery.append(" WHERE TRIM(OUCODE) = TRIM(?)                                                                                                                      \n");
			
			pstmt = con.prepareStatement(updateQuery.toString());
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOu()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOrgfullname()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getParentoucode()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getTopoucode()));
			
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOulevel()));
						
			if("".equals(Utils.nullToEmptyString(bean.getOuorder()))) bean.setOuorder("-1");
			pstmt.setInt(++index, Integer.parseInt(bean.getOuorder()));
			pstmt.setInt(++index, Integer.parseInt(bean.getOuorder()));

			pstmt.setString(++index, Utils.nullToEmptyString(bean.getTelephonenumber()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOufax()));
			
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getIssidoonly()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getStatus()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getVirparentoucode()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getType()));
			
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getLast_attribute()));
			pstmt.setString(++index, "Y");
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getCode()));
			
			result = pstmt.executeUpdate();
	        
		}catch (Exception e) {
			result = 0;
			logger.error("ERROR : deptTempUpdate() : 잘못된 값이 있어 해당 데이터 무시");
	     } finally {	
			try {pstmt.close();} catch (Exception e) {}
	     }	
		return result;
	}

	/**
	 * 변경된 LDAP동기화정보를 사용자정보와 부서정보 테이블에 갱신- [관]사용자정보, [관]부서정보  
	 */    
	public int changeSidoLDAP2() throws Exception{
		int updateCount = 0;
		int updateDeptCount = 0;
		int updateUsrCount = 0;

		try{
			//사용자
			int usrCnt = getApplyYnCnt("USR_TEMP_LDAP");
			if(usrCnt > 0){
				updateUsrCount = getUsrTempSelect();
				if(updateUsrCount > 0){
					setLastRunStat("적용대상 사용자정보(" + updateUsrCount + "), 처리중...");
					logger.info(getLastRunStat());
					getApplyYnUpdate("USR_TEMP_LDAP");
				}
			}
			
			//부서
			int deptCnt = getApplyYnCnt("DEPT_TEMP_LDAP");
			if(deptCnt > 0){
				updateDeptCount = getDeptTempSelect();
				if(updateDeptCount > 0){
					setLastRunStat("적용대상 부서정보(" + updateDeptCount + "), 처리중...");
					logger.info(getLastRunStat());
					getApplyYnUpdate("DEPT_TEMP_LDAP");
				}
			}
			updateCount = updateUsrCount + updateDeptCount;
			logger.info("적용완료 : 사용자정보(" + updateUsrCount + "), 부서정보(" + updateDeptCount + ")");
			
		} catch (Exception e) {
			updateCount = 0;
			logger.error("ERROR : changeSidoLDAP2() : changeSidoLDAP2 오류발생으로 중단");
			throw e;
	     }
	     
	     return updateCount;
	}

	/** 
	 *  사용자정보 테이블 조회 
	 */
	public int getUsrTempSelect() throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {	       	
	       	StringBuffer selectQuery = new StringBuffer();
	       	selectQuery.append(" SELECT RESINUMBER, DISPLAYNAME, OUCODE, OU, ORGFULLNAME,          	\n");
	       	selectQuery.append("        PARENTOUCODE, POSITIONCODE, POSITION, TITLECODE, TITLE,    	\n");
			selectQuery.append("        TITLEORPOSITION, USRORDER, HOMEPHONE, MAIL, MOBILE,        	\n");
			selectQuery.append("        STATUS, CMDTYPE,  LAST_ATTRIBUTE, APPLY_YN, APPLYDT,       	\n");
			selectQuery.append("        USER_ID                                             		\n");  
	       	selectQuery.append(" FROM USR_TEMP_LDAP													\n");
	       	selectQuery.append(" WHERE APPLY_YN = 'Y'												\n");
	       	
		    conn = ConnectionManager.getConnection(false);			
		    pstmt = conn.prepareStatement(selectQuery.toString());	
			rs = pstmt.executeQuery();		
			
			while (rs.next()) {
				SidoLdapBean bean = new SidoLdapBean();
				bean.setResinumber(rs.getString("RESINUMBER"));
				bean.setDisplayname(rs.getString("DISPLAYNAME"));
				bean.setOucode(rs.getString("OUCODE"));
				bean.setOu(rs.getString("OU"));
				bean.setOrgfullname(rs.getString("ORGFULLNAME"));
				
				bean.setParentoucode(rs.getString("PARENTOUCODE"));
				bean.setParentoucode(rs.getString("POSITIONCODE"));
				bean.setPosition(rs.getString("POSITION"));
				bean.setTitlecode(rs.getString("TITLECODE"));
				bean.setTitle(rs.getString("TITLE"));
				
				bean.setTitleorposition(rs.getString("TITLEORPOSITION"));
				bean.setUsrorder(rs.getString("USRORDER"));
				bean.setTelephonenumber(rs.getString("HOMEPHONE"));
				bean.setMail(rs.getString("MAIL"));
				bean.setMobile(rs.getString("MOBILE"));
				
				bean.setStatus(rs.getString("STATUS"));
				bean.setCmdtype(rs.getString("CMDTYPE"));
				bean.setLast_attribute(rs.getString("LAST_ATTRIBUTE"));
				bean.setApply_yn(rs.getString("APPLY_YN"));
				bean.setApplydt(rs.getString("APPLYDT"));
				
				bean.setUser_id(rs.getString("USER_ID"));
				
				if ( getUsrCnt(bean.getResinumber()) > 0 ) {
					result += getUsrUpdate(conn, bean);
				} else {
					if ( !"".equals(Utils.nullToEmptyString(bean.getUser_id())) ) {
						result += getUsrInsert(conn, bean);
					}
				}
			}
			
			conn.commit();
		 } catch (Exception e) {
			 try {conn.rollback();} catch (Exception ex) {}
			 logger.error("ERROR", e);
	     } finally {	       
	    	 ConnectionManager.close(conn,pstmt,rs);	   
	     }
	     
	     return result;
	}	

	/**
	 * 사용자정보 등록 - [관]부서정보
	 */
	public int getUsrInsert(Connection con, SidoLdapBean bean) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		int index  = 0;
		String status = "";
				
		try{						
            //조직정보 저장
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append(" INSERT INTO USR                                                            \n");
			insertQuery.append("        (USER_ID, USER_SN, USER_NAME, DEPT_ID, DEPT_NAME,                  	\n");
			insertQuery.append("        DEPT_FULLNAME, CLASS_ID, CLASS_NAME, POSITION_ID, POSITION_NAME,   	\n");
			insertQuery.append("        USR_RANK, EMAIL, TEL, MOBILE, USE_YN,                             	\n");
			insertQuery.append("        CON_YN, PASSWD, MGRYN, SEX, AGE,                                   	\n");
			insertQuery.append("        CRTDT, CRTUSRID)                                                   	\n");
			insertQuery.append(" SELECT ?, ?, ?, ?, ?,                                                    	\n");
			insertQuery.append("        ?, ?, ?, ?, ?,                                                    	\n");
			insertQuery.append("        ?, ?, ?, ?, ?,                                                    	\n");
			insertQuery.append("        'Y', '1', 'N', ?, ?,                                                \n");
			insertQuery.append("        TO_CHAR(SYSDATE,'YYYYMMDD HH24:MI:SS'), ?           				\n");
			insertQuery.append(" FROM DUAL WHERE NOT EXISTS (SELECT * FROM USR WHERE USER_ID = ?)			\n");
			
			pstmt = con.prepareStatement(insertQuery.toString());
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getUser_id()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getResinumber()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getDisplayname()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOucode()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOu()));

			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOrgfullname()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getPositioncode()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getPosition()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getTitlecode()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getTitle()));
			
			int prevIndex = index;
			try {
				if("".equals(Utils.nullToEmptyString(bean.getOuorder()))) bean.setOuorder("99999");
				pstmt.setInt(++index, Integer.parseInt(bean.getOuorder()));
			} catch ( Exception e ) {
				index = prevIndex;
				bean.setOuorder("99999");
				pstmt.setInt(++index, Integer.parseInt(bean.getOuorder()));
			}
			
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getMail()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getTelephonenumber()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getMobile()));

			if ( "D".equals(bean.getStatus()) || "D".equals(bean.getCmdtype()) ) status= "N";
			else status= "Y";
			pstmt.setString(++index, status);

			if ( !"".equals(Utils.nullToEmptyString(bean.getResinumber())) ) {
				if("1".equals(MALE)){
					pstmt.setString(++index, "M");
				}else{
					pstmt.setString(++index, "F");
				}
			} else {
				pstmt.setString(++index, "M");
			}
			
			pstmt.setInt(++index, Utils.getAge(bean.getResinumber()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getUser_id()));
			
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getUser_id()));

			result = pstmt.executeUpdate();				
			
		} catch (Exception e) {
			result = 0;
			logger.error("ERROR : getUsrInsert() : 잘못된 값이 있어 해당 데이터 무시");
	     } finally {	
	    	try {pstmt.close();} catch (Exception e) {}
	     }	
		return result;
	}
	
	/**
	 * 사용자정보 업데이트 - [관]부서정보
	 */
	public int getUsrUpdate(Connection con, SidoLdapBean bean)  throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;			
		int index  = 0;
		String status = "";
		
		try{
        	StringBuffer updateQuery = new StringBuffer();
        	updateQuery.append(" UPDATE USR                                                                                           	   \n");
        	updateQuery.append(" SET USER_ID = NVL(?,USER_ID), USER_NAME = NVL(?,USER_NAME), DEPT_ID = NVL(?,DEPT_ID),                     \n");
        	updateQuery.append("    DEPT_NAME = NVL(?,DEPT_NAME), DEPT_FULLNAME = NVL(?,DEPT_FULLNAME), CLASS_ID = NVL(?,CLASS_ID),        \n");
        	updateQuery.append("    CLASS_NAME= NVL(?,CLASS_NAME), POSITION_ID = NVL(?,POSITION_ID), POSITION_NAME = NVL(?,POSITION_NAME), \n"); 
        	updateQuery.append("    USR_RANK = DECODE(?, -1, USR_RANK, ?), EMAIL = NVL(?,EMAIL), TEL = NVL(?,TEL), MOBILE = NVL(?,MOBILE),            \n");
        	updateQuery.append("    USE_YN = NVL(?,USE_YN), SEX = NVL(?,SEX),  AGE = NVL(?,AGE), 										   \n");
        	updateQuery.append(" 	UPTDT = TO_CHAR(SYSDATE,'YYYYMMDD HH24:MI:SS'), UPTUSRID = NVL(?,UPTUSRID)   		                   \n");
        	updateQuery.append(" WHERE USER_SN = ?                                                   	                                   \n");
			
			pstmt = con.prepareStatement(updateQuery.toString());
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getUser_id()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getDisplayname()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOucode()));
			
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOu()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOrgfullname()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getPositioncode()));
			
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getPosition()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getTitlecode()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getTitle()));

			int prevIndex = index;
			try {
				if("".equals(Utils.nullToEmptyString(bean.getOuorder()))) bean.setOuorder("-1");
				pstmt.setInt(++index, Integer.parseInt(bean.getOuorder()));
				pstmt.setInt(++index, Integer.parseInt(bean.getOuorder()));
			} catch ( Exception e ) {
				index = prevIndex;
				bean.setOuorder("-1");
				pstmt.setInt(++index, Integer.parseInt(bean.getOuorder()));
				pstmt.setInt(++index, Integer.parseInt(bean.getOuorder()));
			}
			
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getMail()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getTelephonenumber()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getMobile()));		

			if ( "D".equals(bean.getStatus()) || "D".equals(bean.getCmdtype()) ) status= "N";
			else status= "Y";
			pstmt.setString(++index, status);
			
			if ( !"".equals(Utils.nullToEmptyString(bean.getResinumber())) ) {
				if(MALE.equals("1")){
					pstmt.setString(++index, "M");
				}else{
					pstmt.setString(++index, "F");
				}
			} else {
				pstmt.setString(++index, "M");
			}

			pstmt.setInt(++index, Utils.getAge(bean.getResinumber()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getUser_id()));
			
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getResinumber()));
			
			result = pstmt.executeUpdate();
	        
		}catch (Exception e) {
			result = 0;
	     } finally {	
			try {pstmt.close();} catch (Exception e) {}
	     }	
		return result;
	}
	
	
	/** 
	 *  부서정보 테이블 조회 
	 */
	public int getDeptTempSelect() throws Exception {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {	       	
	       	StringBuffer selectQuery = new StringBuffer();
	       	selectQuery.append(" SELECT OUCODE, OU, ORGFULLNAME, PARENTOUCODE, TOPOUCODE,          	\n");      
	       	selectQuery.append("        OULEVEL, OUORDER, TELEPHONENUMBER, OUFAX, ISSIDOONLY,      	\n");      
	       	selectQuery.append("        STATUS, VIRPARENTOUCODE, CMDTYPE, LAST_ATTRIBUTE, APPLY_YN  	\n");   
	       	selectQuery.append(" FROM DEPT_TEMP_LDAP												\n");
	       	selectQuery.append(" WHERE APPLY_YN = 'Y'												\n");
	       	
		    conn = ConnectionManager.getConnection(false);			
		    pstmt = conn.prepareStatement(selectQuery.toString());	
			rs = pstmt.executeQuery();		

			while (rs.next()) {
				SidoLdapBean bean = new SidoLdapBean();
				bean.setOucode(rs.getString("OUCODE"));
				bean.setOu(rs.getString("OU"));
				bean.setOrgfullname(rs.getString("ORGFULLNAME"));
				bean.setParentoucode(rs.getString("PARENTOUCODE"));
				bean.setTopoucode(rs.getString("TOPOUCODE"));
						
				bean.setOulevel(rs.getString("OULEVEL"));
				bean.setOuorder(rs.getString("OUORDER"));
				bean.setTelephonenumber(rs.getString("TELEPHONENUMBER"));
				bean.setOufax(rs.getString("OUFAX"));
				bean.setIssidoonly(rs.getString("ISSIDOONLY"));
				
				bean.setStatus(rs.getString("STATUS"));
				bean.setVirparentoucode(rs.getString("VIRPARENTOUCODE"));
				bean.setCmdtype(rs.getString("CMDTYPE"));
				bean.setLast_attribute(rs.getString("LAST_ATTRIBUTE"));
				bean.setApply_yn(rs.getString("APPLY_YN"));
				
				if(getDeptCnt(bean.getOucode()) > 0 ){
					result += getDeptUpdate(conn, bean);
				}else{
					if ( !"".equals(Utils.nullToEmptyString(bean.getOucode())) ) {
						result += getDeptInsert(conn, bean);
					}
				}
			}
			conn.commit();
		 } catch (Exception e) {
			 try {conn.rollback();} catch (Exception ex) {}
			 logger.error("ERROR", e);
	     } finally {	       
	    	 ConnectionManager.close(conn,pstmt,rs);	   
	     }
	     
	     return result;
	}	
	
	/**
	 * 부서정보 등록 - [관]부서정보
	 */
	public int getDeptInsert(Connection con, SidoLdapBean bean) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;
		int index  = 0;
		String status = "";
				
		try{						
            //조직정보 저장
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append(" INSERT INTO DEPT                                                         \n");
			insertQuery.append(" 	    (DEPT_ID, DEPT_NAME, DEPT_FULLNAME, UPPER_DEPT_ID, TOP_DEPT_ID,   \n");
			insertQuery.append(" 	     DEPT_DEPTH, DEPT_RANK, DEPT_TEL, DEPT_FAX, ORGGBN,               \n");
			insertQuery.append(" 	     MAIN_YN, USE_YN, CON_YN, CRTDT, CRTUSRID)                        \n");
			insertQuery.append(" SELECT ?, ?, ?, ?, ?,                                                    \n");
			insertQuery.append("        ?, ?, ?, ?, ?,                                                    \n");
			insertQuery.append("        'Y', ?, 'Y', TO_CHAR(SYSDATE,'YYYYMMDD HH24:MI:SS'), ?            \n");
			insertQuery.append(" FROM DUAL WHERE NOT EXISTS (SELECT * FROM DEPT WHERE DEPT_ID = ?)        \n");
			
			pstmt = con.prepareStatement(insertQuery.toString());
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOucode()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOu()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOrgfullname()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getParentoucode()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getTopoucode()));
			
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOulevel()));
			
			int prevIndex = index;
			try {
				if("".equals(Utils.nullToEmptyString(bean.getOuorder()))) bean.setOuorder("99999");
				pstmt.setInt(++index, Integer.parseInt(bean.getOuorder()));
			} catch ( Exception e ) {
				index = prevIndex;
				bean.setOuorder("99999");
				pstmt.setInt(++index, Integer.parseInt(bean.getOuorder()));
			}
			
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getTelephonenumber()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOufax()));
			
			try {
				prevIndex = index;
				pstmt.setString(++index, Utils.nullToEmptyString(bean.getVirparentoucode()).substring(3, 6));
			} catch ( Exception e ) {
				index = prevIndex;
				pstmt.setString(++index, "005");
			}

			if ( "D".equals(bean.getStatus()) || "D".equals(bean.getCmdtype()) || ("Y".equals(bean.getIssidoonly()) && "C".equals(bean.getStatus())) ) status= "N";
			else status= "Y";
			pstmt.setString(++index, status);
			
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOucode()));
			
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOucode()));
			
			result = pstmt.executeUpdate();					
			
		} catch (Exception e) {
			result = 0;
			logger.error("ERROR : getDeptInsert() : 잘못된 값이 있어 해당 데이터 무시");
	     } finally {	
	    	try {pstmt.close();} catch (Exception e) {}
	     }	
		return result;
	}
	
	/**
	 * 부서정보 업데이트 - [관]부서정보
	 */
	public int getDeptUpdate(Connection con, SidoLdapBean bean)  throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;			
		int index  = 0;
		
		try{
        	StringBuffer updateQuery = new StringBuffer();
        	updateQuery.append(" UPDATE DEPT                                                                                                 	\n");
        	updateQuery.append(" SET DEPT_NAME = NVL(?,DEPT_NAME), DEPT_FULLNAME = NVL(?,DEPT_FULLNAME), UPPER_DEPT_ID = NVL(?,UPPER_DEPT_ID), 	\n");
        	updateQuery.append(" 	  TOP_DEPT_ID = NVL(?,TOP_DEPT_ID), DEPT_DEPTH = NVL(?,DEPT_DEPTH), DEPT_RANK = DECODE(?, -1, DEPT_RANK, ?), \n");
        	updateQuery.append(" 	  DEPT_TEL = NVL(?,DEPT_TEL), DEPT_FAX = NVL(?,DEPT_FAX),ORGGBN = NVL(?,ORGGBN), USE_YN = NVL(?,USE_YN),    \n");
        	updateQuery.append(" 	  UPTDT = TO_CHAR(SYSDATE,'YYYYMMDD HH24:MI:SS'), UPTUSRID= NVL(?,UPTUSRID)                                 \n");
        	updateQuery.append(" WHERE DEPT_ID = ?                                                                                            	\n");
			
			pstmt = con.prepareStatement(updateQuery.toString());
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOu()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOrgfullname()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getParentoucode()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getTopoucode()));
			
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOulevel()));
			
			int prevIndex = index;
			try {
				if("".equals(Utils.nullToEmptyString(bean.getOuorder()))) bean.setOuorder("-1");
				pstmt.setInt(++index, Integer.parseInt(bean.getOuorder()));
				pstmt.setInt(++index, Integer.parseInt(bean.getOuorder()));
			} catch ( Exception e ) {
				index = prevIndex;
				bean.setOuorder("-1");
				pstmt.setInt(++index, Integer.parseInt(bean.getOuorder()));
				pstmt.setInt(++index, Integer.parseInt(bean.getOuorder()));
			}
			
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getTelephonenumber()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOufax()));

			try {
				prevIndex = index;
				pstmt.setString(++index, Utils.nullToEmptyString(bean.getVirparentoucode()).substring(3, 6));
			} catch ( Exception e ) {
				index = prevIndex;
				pstmt.setString(++index, "");
			}
			
			String status = "";
			if ( "D".equals(bean.getStatus()) || "D".equals(bean.getCmdtype()) || "Y".equals(bean.getIssidoonly()) ) status= "N";
			else status= "Y";
			pstmt.setString(++index, status);
			
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getUser_id()));
			pstmt.setString(++index, Utils.nullToEmptyString(bean.getOucode()));
			
			result = pstmt.executeUpdate();
	        
		}catch (Exception e) {
			result = 0;
			logger.error("ERROR : getDeptUpdate() : 잘못된 값이 있어 해당 데이터 업데이트 중지");
	     } finally {	
			try {pstmt.close();} catch (Exception e) {}
	     }	
		return result;
	}
	
	/** 
	 *  dept_id 존재여부 - [관]부서정보
	 */
	public int getDeptCnt(String dept_id) {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		
		try {	       	
	       	StringBuffer selectQuery = new StringBuffer();
	       	
	       	selectQuery.append(" SELECT COUNT(*) FROM DEPT 	\n");
	       	selectQuery.append(" WHERE DEPT_ID = ?	    	\n");
		    
		    conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(selectQuery.toString());	
		    pstmt.setString(1, dept_id);
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				count = rs.getInt(1);
			}
		 } catch (Exception e) {
			 count = 0;
			 logger.error("ERROR", e);
	     } finally {	       
	    	 ConnectionManager.close(conn,pstmt,rs);	   
	     }
	     return count;
	}	

	/** 
	 *  USER_SN 존재여부 - [관]사용자정보
	 */
	public int getUsrCnt(String user_sn) {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		
		try {	       	
	       	StringBuffer selectQuery = new StringBuffer();
	       	
	       	selectQuery.append(" SELECT COUNT(*) FROM USR 	\n");
	       	selectQuery.append(" WHERE USER_SN = ?	    	\n");
		    
		    conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(selectQuery.toString());	
		    pstmt.setString(1, user_sn);
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				count = rs.getInt(1);
			}
		 } catch (Exception e) {
			 count = 0;
			 logger.error("ERROR", e);
	     } finally {	       
	    	 ConnectionManager.close(conn,pstmt,rs);	   
	     }
	     return count;
	}	
	
	/** 
	 *  APPLY_YN = N 인거 조회 - [관]LDAP사용자정보(연계용), [관]부서정보임시(연계용)
	 */
	public int getApplyYnCnt(String tableName) {
		Connection conn = null;
        ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		
		try {	       	
	       	StringBuffer selectQuery = new StringBuffer();
	       	
	       	selectQuery.append(" SELECT COUNT(*) FROM "+tableName+" 	\n");
	       	selectQuery.append(" WHERE APPLY_YN = 'Y'					\n");
		    
		    conn = ConnectionManager.getConnection();
		    pstmt = conn.prepareStatement(selectQuery.toString());	
			
			rs = pstmt.executeQuery();
			
			if ( rs.next() ){
				count = rs.getInt(1);
			}
		 } catch (Exception e) {
			 logger.error("ERROR", e);
	     } finally {	       
	    	 ConnectionManager.close(conn,pstmt,rs);	   
	     }
	     return count;
	}	

	/** 
	 *  APPLY_YN = N 인거 조회 - [관]LDAP사용자정보(연계용), [관]부서정보임시(연계용)
	 */
	public int getApplyYnUpdate(String tableName) {
		Connection conn = null;       
		PreparedStatement pstmt = null;
		int result = 0;		
		
		try {
			StringBuffer updateQuery = new StringBuffer();
						
			updateQuery.append(" UPDATE "+tableName+"	 												\n");
			updateQuery.append(" SET APPLY_YN = 'N', APPLYDT = TO_CHAR(SYSDATE,'YYYYMMDD HH24:MI:SS')	\n");
			updateQuery.append(" WHERE APPLY_YN = 'Y'													\n");
			
			conn = ConnectionManager.getConnection();
			pstmt =	conn.prepareStatement(updateQuery.toString());
			result = pstmt.executeUpdate();
			
		 } catch (Exception e) {			
			 logger.error("ERROR",e);			
	     } finally {
			ConnectionManager.close(conn,pstmt);				
	     }
		return result;
	}	
	
	/**
	 * LDAP동기화최종정보 테이블에 동기화 이력 갱신
	 */
	public int sidoUpdate(Connection DBSido, SidoLdapBean bean) throws Exception{
		PreparedStatement pstmt = null;
		
		int result  = 0;

		try{
			StringBuffer updateQuery = new StringBuffer();			
			updateQuery.delete(0, updateQuery.capacity());
			updateQuery.append(" UPDATE CMMANLDLASTWRK \n");
			updateQuery.append(" SET LOG_DATE = ?, LOG_SEQ = ? \n");
			updateQuery.append(" WHERE TRIM(SYS_GBN) = ? \n");
						
			pstmt = DBSido.prepareStatement(updateQuery.toString());
		
			pstmt.setString(1, bean.getLogdate());
			pstmt.setInt(2, bean.getLogseq());
			pstmt.setString(3, appInfo.getSidoldapSysgbn());
			
			result = pstmt.executeUpdate();
			
			if (result > 0) {
				DBSido.commit();
			} else {
				DBSido.rollback();
			}
		} catch (Exception e) {
			result = 0;
			try {DBSido.rollback();} catch (Exception ex) {}
			logger.error("ERROR : 동기화 이력 갱신 실패");
	     } finally {
	    	 try {pstmt.close();} catch (Exception e) {}
	     }	
	     
		return result;
	}	
}
