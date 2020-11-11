/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공통취합문서정보 dao
 * 설명:
 */
package nexti.ejms.workplan.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.util.FileBean;
import nexti.ejms.util.Utils;

public class WorkplanDAO {
    
    private static Logger logger = Logger.getLogger(WorkplanDAO.class);
    
    /**
     * 계획 목록 가져오기
     * @param WorkplanSearchBean searchBean
     * @param int start
     * @param int end
     * @return List 계획목록(ArrayList)
     * @throws Exception 
     */
    public List getWorkplanList(WorkplanSearchBean searchBean, int start, int end) throws Exception {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List list = null;
        
        try {
            StringBuffer selectQuery = new StringBuffer();
            
            selectQuery.append("SELECT (CNT-SEQ+1) BUNHO, X.* \n" +
                               "  FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* \n" +
                               "          FROM (SELECT ROWNUM SEQ, A2.* \n" +
                               "                  FROM (SELECT A.PLANNO, A.TITLE, A.CHRGUSRNM, A.DEPTNM, A.CRTDT, A.READCNT, \n" +
                               "                               MAX(EXT1) EXT1, MAX(EXT2) EXT2, MAX(EXT3) EXT3, \n" +
                               "                               MAX(FILENM1) FILENM1, MAX(FILENM2) FILENM2, MAX(FILENM3) FILENM3, \n" +
                               "                               MAX(ORGFILENM1) ORGFILENM1, MAX(ORGFILENM2) ORGFILENM2, MAX(ORGFILENM3) ORGFILENM3, \n" +
                               "                               COUNT(B.RESULTNO) RESULTCNT \n" +
                               "                          FROM (SELECT A.PLANNO, A.TITLE, A.CHRGUSRNM, A.DEPTNM, A.CRTDT, B.EXT, A.READCNT, \n" +
                               "                                       DECODE(FILENO,'1',EXT) EXT1, DECODE(FILENO,'2',EXT) EXT2, DECODE(FILENO,'3',EXT) EXT3, \n" +
                               "                                       DECODE(FILENO,'1',FILENM) FILENM1, DECODE(FILENO,'2',FILENM) FILENM2, DECODE(FILENO,'3',FILENM) FILENM3, \n" +
                               "                                       DECODE(FILENO,'1',ORGFILENM) ORGFILENM1, DECODE(FILENO,'2',ORGFILENM) ORGFILENM2, DECODE(FILENO,'3',ORGFILENM) ORGFILENM3 \n" +
                               "                                  FROM WORKPLAN A, WORKPLANFILE B \n" +
                               "                                 WHERE A.PLANNO = B.PLANNO(+) \n" +
                               "                                   AND A.USEYN = 'Y' \n");
            //조직구분 조건
            if(Utils.isNotNull(searchBean.getOrggbn())){
            selectQuery.append("                                   AND A.DEPTCD IN (SELECT DEPT_ID FROM DEPT WHERE ORGGBN = '"+searchBean.getOrggbn()+"') \n");
            }
            //국명 조건
            if(Utils.isNotNull(searchBean.getSearchupperdeptcd())){
            selectQuery.append("                                   AND A.UPPERDEPTCD LIKE '%"+searchBean.getSearchupperdeptcd()+"%' \n");
            }
            //부서 조건
            if(Utils.isNotNull(searchBean.getSearchdeptcd())){
            selectQuery.append("                                   AND (A.DEPTCD IN (SELECT DEPT_ID \n" +
                               "                                                       FROM DEPT \n" +
                               "                                                      START WITH DEPT_ID = '"+searchBean.getSearchdeptcd()+"' \n" +
                               "                                                    CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID) \n" +
                               "                                        OR A.DEPTCD IN (SELECT DEPT_ID \n" +
                               "                                                          FROM DEPT \n" +
                               "                                                         WHERE DEPT_ID <> '" +nexti.ejms.common.appInfo.getRootid()+"' \n" +
                               "                                                         START WITH DEPT_ID = '"+searchBean.getSearchdeptcd()+"' \n" +
                               "                                                       CONNECT BY DEPT_ID = PRIOR UPPER_DEPT_ID)) \n");
            }
            //담당 조건/
            if(Utils.isNotNull(searchBean.getSearchchrgunitcd())){
            selectQuery.append("                                   AND A.CHRGUNITCD LIKE '%"+searchBean.getSearchchrgunitcd()+"%' \n");
            }
            //진행상황 조건
            if(Utils.isNotNull(searchBean.getSearchstatus())){
            selectQuery.append("                                   AND A.STATUS LIKE '%"+searchBean.getSearchstatus()+"%' \n");
            }
            //작성일 조건
            if(Utils.isNotNull(searchBean.getSearchstrdt()) && Utils.isNotNull(searchBean.getSearchenddt())){
            selectQuery.append("                                   AND A.CRTDT BETWEEN '"+searchBean.getSearchstrdt()+"' AND '"+searchBean.getSearchenddt()+"' \n");
            }
            //담당자명, 문서제목 조건
            if(Utils.isNotNull(searchBean.getSearchtitle())){
                if(searchBean.getSearchgubun().equals("1")){            //조회조건 : 성명
                    selectQuery.append("                           AND A.CHRGUSRNM ");
                }else if(searchBean.getSearchgubun().equals("2")){      //조회조건 : 제목
                    selectQuery.append("                           AND A.TITLE ");
                }
                selectQuery.append(" LIKE '%"+searchBean.getSearchtitle()+"%' \n");
            }
            selectQuery.append("                                                             ) A, WORKRESULT B \n" +
                               "                        WHERE A.PLANNO = B.PLANNO \n" +
                               "                        GROUP BY A.PLANNO, A.TITLE, A.CHRGUSRNM, A.DEPTNM, A.CRTDT, A.READCNT \n" +
                               "                        ORDER BY A.PLANNO DESC) A2) A1) X \n" +
                               " WHERE SEQ BETWEEN ? AND ?");
            
            con = ConnectionManager.getConnection();
            
            pstmt = con.prepareStatement(selectQuery.toString());
            pstmt.setInt(1, start);
            pstmt.setInt(2, end);
                                    
            rs = pstmt.executeQuery();
            
            list = new ArrayList();
            
            while (rs.next()) {
                WorkplanBean Bean = new WorkplanBean();
                
                Bean.setSeqno(rs.getInt("BUNHO"));
                Bean.setPlanno(rs.getInt("PLANNO"));
                Bean.setTitle(rs.getString("TITLE"));
                Bean.setChrgusrnm(rs.getString("CHRGUSRNM"));
                Bean.setDeptnm(rs.getString("DEPTNM"));
                Bean.setCrtdt(rs.getString("CRTDT"));
                Bean.setExt1(rs.getString("EXT1"));
                Bean.setExt2(rs.getString("EXT2"));
                Bean.setExt3(rs.getString("EXT3"));
                Bean.setFilenm1(rs.getString("FILENM1"));
                Bean.setFilenm2(rs.getString("FILENM2"));
                Bean.setFilenm3(rs.getString("FILENM3"));
                Bean.setOrgfilenm1(rs.getString("ORGFILENM1"));
                Bean.setOrgfilenm2(rs.getString("ORGFILENM2"));
                Bean.setOrgfilenm3(rs.getString("ORGFILENM3"));
                Bean.setReadcnt(rs.getInt("READCNT"));
                Bean.setResultcnt(rs.getInt("RESULTCNT"));
                
                list.add(Bean);
            }
        } catch(Exception e) {
            logger.error("ERROR", e);
            ConnectionManager.close(con,pstmt,rs);
            throw e;
        } finally {
            ConnectionManager.close(con,pstmt,rs);
        }    
        
        return list;
    }
    
    /**
     * 계획 목록 갯수 가져오기
     * @param WorkplanSearchBean searchBean
     * @return int 계획개수
     * @throws Exception 
     */
    public int getWorkplanTotCnt(WorkplanSearchBean searchBean) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        int totalCount = 0;
        
        try {
            StringBuffer selectQuery = new StringBuffer();
            
            selectQuery.append("SELECT COUNT(*) \n" +
                               "  FROM (SELECT PLANNO, TITLE, CHRGUSRNM, DEPTNM, CRTDT, READCNT, \n" +
                               "               SUBSTR(MAX(SYS_CONNECT_BY_PATH(EXT,',')),2) FILEEXT \n" +
                               "          FROM (SELECT A.PLANNO, A.TITLE, A.CHRGUSRNM, A.DEPTNM, A.CRTDT, B.EXT, A.READCNT, \n" +
                               "                       ROW_NUMBER() OVER(PARTITION BY A.PLANNO ORDER BY A.PLANNO) RNUM \n" +
                               "                  FROM WORKPLAN A, WORKPLANFILE B \n" +
                               "                 WHERE A.PLANNO = B.PLANNO(+) \n" +
                               "                   AND A.USEYN = 'Y' \n");
            //국명 조건
            if(Utils.isNotNull(searchBean.getSearchupperdeptcd())){
            selectQuery.append("                   AND A.UPPERDEPTCD LIKE '%"+searchBean.getSearchupperdeptcd()+"%' \n");
            }
            //부서 조건
            if(Utils.isNotNull(searchBean.getSearchdeptcd())){
                selectQuery.append("                                   AND A.DEPTCD IN (SELECT DEPT_ID \n" +
                        "                                                      FROM DEPT \n" +
                        "                                                     START WITH DEPT_ID = '"+searchBean.getSearchdeptcd()+"' \n" +
                        "                                                    CONNECT BY PRIOR DEPT_ID = UPPER_DEPT_ID) \n");
            }
            //담당 조건
            if(Utils.isNotNull(searchBean.getSearchchrgunitcd())){
            selectQuery.append("                   AND A.CHRGUNITCD LIKE '%"+searchBean.getSearchchrgunitcd()+"%' \n");
            }
            //진행상황 조건
            if(Utils.isNotNull(searchBean.getSearchstatus())){
            selectQuery.append("                   AND A.STATUS LIKE '%"+searchBean.getSearchstatus()+"%' \n");
            }
            //작성일 조건
            if(Utils.isNotNull(searchBean.getSearchstrdt()) && Utils.isNotNull(searchBean.getSearchenddt())){
            selectQuery.append("                   AND A.CRTDT BETWEEN '"+searchBean.getSearchstrdt()+"' AND '"+searchBean.getSearchenddt()+"' \n");
            }
            //담당자명, 문서제목 조건
            if(Utils.isNotNull(searchBean.getSearchtitle())){
                if(searchBean.getSearchgubun().equals("1")){
                    selectQuery.append("                   AND A.CHRGUSRNM ");
                }else if(searchBean.getSearchgubun().equals("2")){
                    selectQuery.append("                   AND A.TITLE ");
                }
                selectQuery.append(" LIKE '%"+searchBean.getSearchtitle()+"%' \n");
            }
            selectQuery.append("       ) \n");
            selectQuery.append("        START WITH RNUM = 1 \n" +
                               "        CONNECT BY PRIOR PLANNO = PLANNO AND PRIOR RNUM = RNUM - 1 \n" +
                               "        GROUP BY PLANNO, TITLE, CHRGUSRNM, DEPTNM, DEPTNM, CRTDT, READCNT) \n");
            
            conn = ConnectionManager.getConnection();
            
            pstmt =    conn.prepareStatement(selectQuery.toString());
            
            rs = pstmt.executeQuery();
            
            if ( rs.next() ){
                totalCount = rs.getInt(1);
            }

         } catch (Exception e) {
            logger.error("ERROR", e);
            ConnectionManager.close(conn,pstmt,rs);
            throw e;
         } finally {           
            ConnectionManager.close(conn,pstmt,rs);
         }
        return totalCount;
    }
    /**
     * 실적계획 데이터 가져오기
     * @param planno : 계획번호
     * @return WorkplanBean
     * @throws Exception 
     */
    public WorkplanBean viewWorkplan(int planno) throws Exception {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        WorkplanBean planBean = new WorkplanBean();
        
        try {
            StringBuffer selectQuery = new StringBuffer();
            
            selectQuery.append("SELECT TITLE, \n" +
                               "       STRDT, \n" +
                               "       ENDDT, \n" +
                               "       STATUS, \n" +
                               "       UPPERDEPTCD, \n" +
                               "       UPPERDEPTNM, \n" +
                               "       DEPTCD, \n" +
                               "       DEPTNM, \n"+ 
                               "       CHRGUNITCD, \n" +
                               "       CHRGUNITNM, \n" +
                               "       CHRGUSRCD, \n" +
                               "       CHRGUSRNM, \n" +
                               "       CRTDT, \n" +
                               "       USEYN \n" +
                               "  FROM WORKPLAN \n" +
                               " WHERE PLANNO = ? \n" +
                               "   AND USEYN = 'Y' \n" +
                               " ORDER BY PLANNO");
            
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(selectQuery.toString());
            pstmt.setInt(1, planno);
                                    
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                planBean.setPlanno(planno);
                planBean.setTitle(rs.getString("TITLE"));
                planBean.setStrdt(rs.getString("STRDT"));
                planBean.setEnddt(rs.getString("ENDDT"));
                planBean.setStatus(rs.getString("STATUS"));
                planBean.setUpperdeptcd(rs.getString("UPPERDEPTCD"));
                planBean.setUpperdeptnm(rs.getString("UPPERDEPTNM"));
                planBean.setDeptcd(rs.getString("DEPTCD"));
                planBean.setDeptnm(rs.getString("DEPTNM"));
                planBean.setChrgunitcd(rs.getInt("CHRGUNITCD"));
                planBean.setChrgunitnm(rs.getString("CHRGUNITNM"));
                planBean.setChrgusrcd(rs.getString("CHRGUSRCD"));
                planBean.setChrgusrnm(rs.getString("CHRGUSRNM"));
                planBean.setCrtdt(rs.getString("CRTDT"));
                planBean.setUseyn(rs.getString("USEYN"));
            }
            
            String[] columnName = {"CONTENT"};
            String[] columnData = new String[columnName.length];
            for(int i=0; i<columnName.length; i++){
                selectQuery.delete(0, selectQuery.capacity());
                selectQuery.append("SELECT " + columnName[i] + " FROM WORKPLAN WHERE PLANNO = ?");
                PreparedStatement p = con.prepareStatement(selectQuery.toString());
                p.setInt(1, planno);
                ResultSet r = p.executeQuery();
                if ( r.next() ) columnData[i] = Utils.readClobData(r, columnName[i]);
                ConnectionManager.close(p, r);
            }
            planBean.setContent(columnData[0]);
            
            selectQuery.delete(0, selectQuery.length());
            selectQuery.append("SELECT FILENO, FILENM, ORGFILENM, FILESIZE, EXT, ORD \n" +
                               "  FROM WORKPLANFILE \n" +
                               " WHERE PLANNO = ? ");
            pstmt = con.prepareStatement(selectQuery.toString());
            pstmt.setInt(1, planno);
            
            rs = pstmt.executeQuery();
            while(rs.next()){
                switch(rs.getInt("FILENO")){
                    case 1:
                        planBean.setFileno1(rs.getInt("FILENO"));
                        planBean.setFilenm1(rs.getString("FILENM"));
                        planBean.setOrgfilenm1(rs.getString("ORGFILENM"));
                        planBean.setFilesize1(rs.getInt("FILESIZE"));
                        planBean.setExt1(rs.getString("EXT"));
                        planBean.setOrd1(rs.getInt("ORD"));
                        break;
                    case 2:
                        planBean.setFileno2(rs.getInt("FILENO"));
                        planBean.setFilenm2(rs.getString("FILENM"));
                        planBean.setOrgfilenm2(rs.getString("ORGFILENM"));
                        planBean.setFilesize2(rs.getInt("FILESIZE"));
                        planBean.setExt2(rs.getString("EXT"));
                        planBean.setOrd2(rs.getInt("ORD"));
                        break;
                    case 3:
                        planBean.setFileno3(rs.getInt("FILENO"));
                        planBean.setFilenm3(rs.getString("FILENM"));
                        planBean.setOrgfilenm3(rs.getString("ORGFILENM"));
                        planBean.setFilesize3(rs.getInt("FILESIZE"));
                        planBean.setExt3(rs.getString("EXT"));
                        planBean.setOrd3(rs.getInt("ORD"));
                        break;
                }
            }
            
        } catch(Exception e) {
            logger.error("ERROR", e);
            ConnectionManager.close(con,pstmt,rs);
            throw e;
        } finally {
            ConnectionManager.close(con,pstmt,rs);
        }    
        
        return planBean;
    }
    /**
     * 상위부서 데이터 가져오기
     * @param Connection conn
     * @param WorkplanBean wpbean
     * @return WorkplanBean
     * @throws Exception 
     */
    public WorkplanBean getUpperDept(Connection conn, WorkplanBean wpbean) throws Exception {
        String query = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try{            
            query = "SELECT DEPT_ID, DEPT_NAME \n" +
                    "  FROM DEPT \n" +
                    " WHERE DEPT_ID IN (SELECT UPPER_DEPT_ID FROM DEPT WHERE DEPT_ID = ?)";
            pstmt = conn.prepareStatement(query);
            
            pstmt.setString(1, wpbean.getDeptcd());
            
            rs = pstmt.executeQuery();
            
            if( rs.next() ) {
                wpbean.setUpperdeptcd(rs.getString("DEPT_ID"));
                wpbean.setUpperdeptnm(rs.getString("DEPT_NAME"));
            }
            
        } catch(Exception e) {
            logger.error("ERROR", e);
            ConnectionManager.close(pstmt, rs);
            throw e;
        } finally {
            ConnectionManager.close(pstmt, rs);
        }
        return wpbean;
    }
    /**
     * 업무계획 조회수 증가
     * @param planno
     * @return
     * @throws Exception
     */
    public int workplanReadCount(int planno) throws Exception {
        StringBuffer query = new StringBuffer();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;
        
        try{
            query.append("UPDATE WORKPLAN \n");
            query.append("   SET READCNT = READCNT+1 \n");
            query.append(" WHERE PLANNO = ?");
            
            conn = ConnectionManager.getConnection();
            pstmt = conn.prepareStatement(query.toString());
            
            pstmt.setInt(1, planno);
            
            if ( pstmt.executeUpdate() > 0 ) {
                ConnectionManager.close(pstmt);
                
                query.delete(0, query.capacity());
                query.append("SELECT READCNT \n");
                query.append("FROM WORKPLAN \n");
                query.append("WHERE PLANNO = ? \n");

                pstmt = conn.prepareStatement(query.toString());
                pstmt.setInt(1, planno);
                rs = pstmt.executeQuery();

                if ( rs.next() ) {
                    result = rs.getInt(1);
                }
            }
        } catch(Exception e){
            logger.error("ERROR", e);
            ConnectionManager.close(conn, pstmt, rs);
            throw e;
        } finally{
            ConnectionManager.close(conn, pstmt, rs);
        }
        return result;
    }
    /**
     * 업무계획 수정
     * @param conn
     * @param wpbean
     * @return
     * @throws Exception
     */
    public int modifyWorkplan(Connection conn, WorkplanBean wpbean) throws Exception {
        StringBuffer query = new StringBuffer();
        int planno = wpbean.getPlanno();
        
        PreparedStatement pstmt = null;
        try{
            query.append("UPDATE WORKPLAN \n" +
                         "   SET TITLE=?, STRDT=?, ENDDT=?, STATUS=?, USEYN=?,  \n" +
                         "       CHRGUNITCD=?, CHRGUNITNM=?, CONTENT=EMPTY_CLOB(), \n" +
                         "       CHRGUSRCD=?, CHRGUSRNM=?, \n" +
                         "       UPTDT=TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS'), UPTUSRID=? \n" +
                         " WHERE PLANNO = ? ");
            pstmt = conn.prepareStatement(query.toString());
            
            int idx = 0;
            pstmt.setString(++idx, wpbean.getTitle());
            pstmt.setString(++idx, wpbean.getStrdt());
            pstmt.setString(++idx, wpbean.getEnddt());
            pstmt.setString(++idx, wpbean.getStatus());
            pstmt.setString(++idx, wpbean.getUseyn());
            pstmt.setInt(++idx, wpbean.getChrgunitcd());
            pstmt.setString(++idx, wpbean.getChrgunitnm());
            pstmt.setString(++idx, wpbean.getChrgusrcd());
            pstmt.setString(++idx, wpbean.getChrgusrnm());
            pstmt.setString(++idx, wpbean.getUptusrid());
            pstmt.setInt(++idx, planno);
            
            if( pstmt.executeUpdate() > 0 ){
                String[] columnName = {"CONTENT"};
                String[] columnData = {wpbean.getContent()};
                for( int i=0; i<columnName.length; i++ ){
                    query.delete(0, query.capacity());
                    query.append("SELECT " + columnName[i] + " FROM WORKPLAN WHERE PLANNO = ? FOR UPDATE");
                    PreparedStatement p = conn.prepareStatement(query.toString());
                    p.setInt(1, planno);
                    ResultSet r = p.executeQuery();
                    if( r.next() ) Utils.writeClobData(r, columnName[i], columnData[i]);
                    ConnectionManager.close(p, r);
                }
            }
        } catch(Exception e){
            logger.error("ERROR", e);
            ConnectionManager.close(pstmt);
            throw e;
        }finally{
            ConnectionManager.close(pstmt);
        }
        return planno;
    }
    
    public int newWorkplan(Connection conn, WorkplanBean wpbean) throws Exception {
        StringBuffer query = new StringBuffer();
        int planno = 0;
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{            
            query.append("SELECT NVL(MAX(PLANNO), 0)+1 PLANNO FROM WORKPLAN");
            pstmt = conn.prepareStatement(query.toString());
            rs = pstmt.executeQuery();
            
            if(rs.next()){
                planno = rs.getInt(1);
            }
            
            ConnectionManager.close(pstmt, rs);
            
            query.delete(0, query.capacity());
            query.append("INSERT INTO " +
                         " WORKPLAN(PLANNO,      TITLE,      STRDT,      ENDDT,      STATUS," +
                         "          READCNT,     USEYN,      UPPERDEPTCD,UPPERDEPTNM, " +
                         "          DEPTCD,      DEPTNM,     CHRGUNITCD, CHRGUNITNM,     CHRGUSRCD, " +
                         "          CHRGUSRNM,   CRTUSRID,   UPTUSRID, " +
                         "          CRTDT,       UPTDT) " +
                         "   VALUES(?,           ?,          ?,          ?,          '1', " +
                         "          0,          'Y',         ?,          ?, " +
                         "          ?,           ?,          ?,          ?,          ?, " +
                         "          ?,           ?,          ?, " +
                         "          TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'))");
            pstmt = conn.prepareStatement(query.toString());
            
            int idx = 0;
            pstmt.setInt(++idx, planno);
            pstmt.setString(++idx, wpbean.getTitle());
            pstmt.setString(++idx, wpbean.getStrdt());
            pstmt.setString(++idx, wpbean.getEnddt());
            pstmt.setString(++idx, wpbean.getUpperdeptcd());
            pstmt.setString(++idx, wpbean.getUpperdeptnm());
            pstmt.setString(++idx, wpbean.getDeptcd());
            pstmt.setString(++idx, wpbean.getDeptnm());
            pstmt.setInt(++idx, wpbean.getChrgunitcd());
            pstmt.setString(++idx, wpbean.getChrgunitnm());
            pstmt.setString(++idx, wpbean.getChrgusrcd());
            pstmt.setString(++idx, wpbean.getChrgusrnm());
            pstmt.setString(++idx, wpbean.getUptusrid());
            pstmt.setString(++idx, wpbean.getUptusrid());
            
            if( pstmt.executeUpdate() > 0 ){
                String[] columnName = {"CONTENT"};
                String[] columnData = {wpbean.getContent()};
                for( int i=0; i<columnName.length; i++ ){
                    query.delete(0, query.capacity());
                    query.append("SELECT " + columnName[i] + " FROM WORKPLAN WHERE PLANNO = ? FOR UPDATE");
                    PreparedStatement p = conn.prepareStatement(query.toString());
                    p.setInt(1, planno);
                    ResultSet r = p.executeQuery();
                    if( r.next() ) Utils.writeClobData(r, columnName[i], columnData[i]);
                    ConnectionManager.close(p, r);
                }
            }

        }catch(Exception e){
            logger.error("ERROR", e);
            ConnectionManager.close(pstmt, rs);
            throw e;
        }finally{
            ConnectionManager.close(pstmt, rs);
        }
        return planno;
    }
 
    
    /**
     * 업무게획 첨부파일 추가
     * @param conn
     * @param fileBean
     * @return
     * @throws Exception
     */
    public int addWorkplanFile(Connection conn, FileBean fileBean, int planno) throws Exception {
        PreparedStatement pstmt = null;
        
        int result = 0;
        
        try {           
            StringBuffer sql = new StringBuffer();
            
            sql.append("INSERT INTO \n");
            sql.append("WORKPLANFILE(PLANNO,FILENO, FILENM, ORGFILENM,  FILESIZE,   EXT,    ORD) \n");
            sql.append("      VALUES(?,     ?,      ?,      ?,          ?,          ?,      ?  ) \n");
            
            pstmt = conn.prepareStatement(sql.toString());

            pstmt.setInt(1, planno);
            pstmt.setInt(2, fileBean.getSeq());
            pstmt.setString(3, fileBean.getFilenm());
            pstmt.setString(4, fileBean.getOriginfilenm());
            pstmt.setInt(5, fileBean.getFilesize());
            pstmt.setString(6, fileBean.getExt());
            pstmt.setInt(7, fileBean.getSeq());
            
            result = pstmt.executeUpdate();
            
        } catch (Exception e) {
            logger.error("ERROR",e);
            ConnectionManager.close(pstmt);
            throw e;
        } finally {        
            ConnectionManager.close(pstmt);
        }
        
        return result;
    }
    
    /**
     * 업무계획 첨부파일 삭제
     * @param conn
     * @param planno
     * @param fileno
     * @return
     * @throws Exception
     */
    public int delWorkplanFile(Connection conn, int planno, int fileno) throws Exception {
        
        PreparedStatement pstmt = null;
        
        int result = 0;
        
        try {           
            StringBuffer sql = new StringBuffer();
            
            sql.append("DELETE FROM WORKPLANFILE \n");
            sql.append("WHERE PLANNO = ? \n");
            sql.append("AND FILENO = ? \n");
            
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setInt(1, planno);
            pstmt.setInt(2, fileno);
            
            result = pstmt.executeUpdate();
            
            conn.commit();
        } catch (Exception e) {
            logger.error("ERROR",e);
            ConnectionManager.close(pstmt);
            throw e;
        } finally {
            try {pstmt.close();} catch(Exception ex) {}     
        }
        
        return result;      
    }
    
    
    /**
     * 업무계획 첨부파일 정보
     * @param conn
     * @param planno
     * @param fileno
     * @return
     * @throws Exception
     */
    public WorkplanFileBean getWorkplanFile(Connection conn, int planno, int fileno) throws Exception {
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        WorkplanFileBean result = null;
        
        try {
            StringBuffer sql = new StringBuffer();
            
            sql.append("SELECT PLANNO, FILENO, FILENM, ORGFILENM, FILESIZE, EXT, ORD \n");
            sql.append("FROM WORKPLANFILE \n");
            sql.append("WHERE PLANNO = ? \n");
            sql.append("AND FILENO = ? \n");
            
            pstmt = conn.prepareStatement(sql.toString());
            
            pstmt.setInt(1, planno);
            pstmt.setInt(2, fileno);
            
            rs = pstmt.executeQuery();
            
            if ( rs.next() ) {      
                result = new WorkplanFileBean();
                
                result.setPlanno(rs.getInt("PLANNO"));
                result.setFileno(rs.getInt("FILENO"));
                result.setFilenm(rs.getString("FILENM"));
                result.setOrgfilenm(rs.getString("ORGFILENM"));
                result.setFilesize(rs.getInt("FILESIZE"));
                result.setExt(rs.getString("EXT"));
                result.setOrd(rs.getInt("ORD"));
            }
        } catch (Exception e) {
            logger.error("ERROR",e);
            ConnectionManager.close(pstmt, rs);
            throw e;
        } finally {        
            ConnectionManager.close(pstmt, rs);
        }
        
        return result;
    }
    /*--------------------------------------------------------------*/
    
    /**
     * 업무계획 새번호 가져오기
     * @param conn
     * @return
     * @throws Exception
     */
    public int getNextPlanno(Connection conn) throws Exception {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;
        
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT NVL(MAX(PLANNO), 0) + 1 \n");
            sql.append("FROM WORKPLAN \n");
            
            pstmt = conn.prepareStatement(sql.toString());
            rs = pstmt.executeQuery();
            
            if ( rs.next() ) {
                result = rs.getInt(1);
            }   
        } catch(Exception e) {
            logger.error("ERROR", e);
            ConnectionManager.close(pstmt, rs);
            throw e;
        } finally {
            ConnectionManager.close(pstmt, rs);
        }
        
        return result;
    }
    
    /**
     * 업무실적 새번호 가져오기
     * @param conn
     * @return
     * @throws Exception
     */
    public int getNextWorkResultno(Connection conn, int planno) throws Exception {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;

        try {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT NVL(MAX(RESULTNO), 0) + 1 \n");
            sql.append("FROM WORKRESULT \n");
            sql.append("WHERE PLANNO = ? \n");

            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setInt(1, planno);
            rs = pstmt.executeQuery();

            if ( rs.next() ) {
                result = rs.getInt(1);
            }
        } catch( Exception e ) {
            logger.error("ERROR", e);
            ConnectionManager.close(pstmt, rs);
            throw e;
        } finally {
            ConnectionManager.close(pstmt, rs);
        }

        return result;
    }

    /**
     * 업무계획실적 가져오기
     * @param planno
     * @param resultno
     * @return
     * @throws Exception
     */
    public WorkplanBean getWorkResult(int planno, int resultno) throws Exception {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        WorkplanBean result = null;

        try {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT W.PLANNO, W.RESULTNO, W.TITLE, W.READCNT, W.USEYN, \n");
            sql.append("       W.UPPERDEPTCD, W.UPPERDEPTNM, W.DEPTCD, W.DEPTNM, W.CHRGUNITCD, \n");
            sql.append("       W.CHRGUNITNM, W.CHRGUSRCD, W.CHRGUSRNM, W.CRTDT, W.CRTUSRID, \n");
            sql.append("       W.UPTDT, W.UPTUSRID, \n");
            sql.append("       WF.FILENO, WF.FILENM, WF.ORGFILENM, WF.FILESIZE, WF.EXT, WF.ORD \n");
            sql.append("FROM WORKRESULT W, WORKRESULTFILE WF \n");
            sql.append("WHERE W.PLANNO = WF.PLANNO(+) \n");
            sql.append("AND W.RESULTNO = WF.RESULTNO(+) \n");
            sql.append("AND W.USEYN = 'Y' \n");
            sql.append("AND W.PLANNO = ? \n");
            sql.append("AND W.RESULTNO = ? \n");
            sql.append("ORDER BY WF.ORD, WF.FILENO \n");

            con = ConnectionManager.getConnection(false);
            pstmt = con.prepareStatement(sql.toString());
            pstmt.setInt(1, planno);
            pstmt.setInt(2, resultno);
            rs = pstmt.executeQuery();

            while ( rs.next() ) {
                if ( result == null ) {
                    result = new WorkplanBean();
                    result.setPlanno(rs.getInt("PLANNO"));
                    result.setResultno(rs.getInt("RESULTNO"));
                    result.setTitle(rs.getString("TITLE"));
                    result.setReadcnt(rs.getInt("READCNT"));
                    result.setUpperdeptcd(rs.getString("UPPERDEPTCD"));
                    result.setUpperdeptnm(rs.getString("UPPERDEPTNM"));
                    result.setDeptcd(rs.getString("DEPTCD"));
                    result.setDeptnm(rs.getString("DEPTNM"));
                    result.setChrgunitcd(rs.getInt("CHRGUNITCD"));
                    result.setChrgunitnm(rs.getString("CHRGUNITNM"));
                    result.setChrgusrcd(rs.getString("CHRGUSRCD"));
                    result.setChrgusrnm(rs.getString("CHRGUSRNM"));
                    result.setCrtdt(rs.getString("CRTDT"));
                    result.setCrtusrid(rs.getString("CRTUSRID"));
                    result.setUptdt(rs.getString("UPTDT"));
                    result.setUptusrid(rs.getString("UPTUSRID"));

                    String[] columnName = {"CONTENT"};
                    String[] columnData = new String[columnName.length];
                    for ( int i = 0; i < columnName.length; i++ ) {
                        sql.delete(0, sql.capacity());
                        sql.append("SELECT " + columnName[i] + " FROM WORKRESULT WHERE PLANNO = ? AND RESULTNO = ?");
                        PreparedStatement p = con.prepareStatement(sql.toString());
                        p.setInt(1, planno);
                        p.setInt(2, resultno);
                        ResultSet r = p.executeQuery();
                        if ( r.next() ) columnData[i] = Utils.readClobData(r, columnName[i]);
                        ConnectionManager.close(p, r);
                    }
                    result.setContent(columnData[0]);
                }

                if ( rs.getInt("FILENO") == 1 ) {
                    result.setFileno1(rs.getInt("FILENO"));
                    result.setFilenm1(rs.getString("FILENM"));
                    result.setOrgfilenm1(rs.getString("ORGFILENM"));
                    result.setFilesize1(rs.getInt("FILESIZE"));
                    result.setExt1(rs.getString("EXT"));
                    result.setOrd1(rs.getInt("ORD"));
                } else if ( rs.getInt("FILENO") == 2 ) {
                    result.setFileno2(rs.getInt("FILENO"));
                    result.setFilenm2(rs.getString("FILENM"));
                    result.setOrgfilenm2(rs.getString("ORGFILENM"));
                    result.setFilesize2(rs.getInt("FILESIZE"));
                    result.setExt2(rs.getString("EXT"));
                    result.setOrd2(rs.getInt("ORD"));
                } else if ( rs.getInt("FILENO") == 3 ) {
                    result.setFileno3(rs.getInt("FILENO"));
                    result.setFilenm3(rs.getString("FILENM"));
                    result.setOrgfilenm3(rs.getString("ORGFILENM"));
                    result.setFilesize3(rs.getInt("FILESIZE"));
                    result.setExt3(rs.getString("EXT"));
                    result.setOrd3(rs.getInt("ORD"));
                }
            }
        } catch ( Exception e ) {
            logger.error("ERROR", e);
            ConnectionManager.close(con, pstmt, rs);
            throw e;
        } finally {
            ConnectionManager.close(con, pstmt, rs);
        }

        return result;
    }

    /**
     * 업무계획실적 조회수 증가
     * @param planno
     * @param resultno
     * @return
     * @throws Exception
     */
    public int readCount(int planno, int resultno) throws Exception {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;

        try {
            StringBuffer sql = new StringBuffer();
            sql.append("UPDATE WORKRESULT \n");
            sql.append("SET READCNT = NVL(READCNT, 0) + 1 \n");
            sql.append("WHERE PLANNO = ? \n");
            sql.append("AND RESULTNO = ? \n");

            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql.toString());
            pstmt.setInt(1, planno);
            pstmt.setInt(2, resultno);

            if ( pstmt.executeUpdate() > 0 ) {
                ConnectionManager.close(pstmt);

                sql.delete(0, sql.capacity());
                sql.append("SELECT READCNT \n");
                sql.append("FROM WORKRESULT \n");
                sql.append("WHERE PLANNO = ? \n");
                sql.append("AND RESULTNO = ? \n");

                pstmt = con.prepareStatement(sql.toString());
                pstmt.setInt(1, planno);
                pstmt.setInt(2, resultno);
                rs = pstmt.executeQuery();

                if ( rs.next() ) {
                    result = rs.getInt(1);
                }
            }
        } catch( Exception e ) {
            logger.error("ERROR", e);
            ConnectionManager.close(con, pstmt, rs);
            throw e;
        } finally {
            ConnectionManager.close(con, pstmt, rs);
        }

        return result;
    }

    /**
     * 업무계획실적목록 개수가져오기
     * @param planno
     * @return
     * @throws Exception
     */
    public int getWorkResultListCount(int planno) throws Exception {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;

        try {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT COUNT(*) \n");
            sql.append("FROM WORKRESULT W, \n");
            sql.append("     (SELECT PLANNO, RESULTNO, \n");
            sql.append("             MAX(DECODE(FILENO, 1, FILENO, '')) FILENO1, MAX(DECODE(FILENO, 1, FILENM, '')) FILENM1, MAX(DECODE(FILENO, 1, ORGFILENM, '')) ORGFILENM1, \n");
            sql.append("             MAX(DECODE(FILENO, 1, FILESIZE, '')) FILESIZE1, MAX(DECODE(FILENO, 1, EXT, '')) EXT1, MAX(DECODE(FILENO, 1, ORD, '')) ORD1, \n");
            sql.append("             MAX(DECODE(FILENO, 2, FILENO, '')) FILENO2, MAX(DECODE(FILENO, 2, FILENM, '')) FILENM2, MAX(DECODE(FILENO, 2, ORGFILENM, '')) ORGFILENM2, \n");
            sql.append("             MAX(DECODE(FILENO, 2, FILESIZE, '')) FILESIZE2, MAX(DECODE(FILENO, 2, EXT, '')) EXT2, MAX(DECODE(FILENO, 2, ORD, '')) ORD2, \n");
            sql.append("             MAX(DECODE(FILENO, 3, FILENO, '')) FILENO3, MAX(DECODE(FILENO, 3, FILENM, '')) FILENM3, MAX(DECODE(FILENO, 3, ORGFILENM, '')) ORGFILENM3, \n");
            sql.append("             MAX(DECODE(FILENO, 3, FILESIZE, '')) FILESIZE3, MAX(DECODE(FILENO, 3, EXT, '')) EXT3, MAX(DECODE(FILENO, 3, ORD, '')) ORD3 \n");
            sql.append("      FROM WORKRESULTFILE \n");
            sql.append("      GROUP BY PLANNO, RESULTNO) WF \n");
            sql.append("WHERE W.PLANNO = WF.PLANNO(+) \n");
            sql.append("AND W.RESULTNO = WF.RESULTNO(+) \n");
            sql.append("AND W.USEYN = 'Y' \n");
            sql.append("AND W.PLANNO = ? \n");

            con = ConnectionManager.getConnection(false);
            pstmt = con.prepareStatement(sql.toString());
            pstmt.setInt(1, planno);
            rs = pstmt.executeQuery();

            if ( rs.next() ) {
                result = rs.getInt(1);
            }
        } catch ( Exception e ) {
            logger.error("ERROR", e);
            ConnectionManager.close(con, pstmt, rs);
            throw e;
        } finally {
            ConnectionManager.close(con, pstmt, rs);
        }

        return result;
    }

    /**
     * 업무계획실적목록 가져오기
     * @param planno
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    public List getWorkResultList(int planno, int start, int end) throws Exception {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List result = null;

        try {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT (CNT-SEQ+1) BUNHO, X.* \n");
            sql.append("  FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* \n");
            sql.append("          FROM (SELECT ROWNUM SEQ, A.* \n");
            sql.append("                  FROM ( \n");
            sql.append("                        SELECT W.PLANNO, W.RESULTNO, W.TITLE, W.READCNT, W.USEYN, \n");
            sql.append("                               W.UPPERDEPTCD, W.UPPERDEPTNM, W.DEPTCD, W.DEPTNM, W.CHRGUNITCD, \n");
            sql.append("                               W.CHRGUNITNM, W.CHRGUSRCD, W.CHRGUSRNM, W.CRTDT, W.CRTUSRID, \n");
            sql.append("                               W.UPTDT, W.UPTUSRID, \n");
            sql.append("                               WF.FILENO1, WF.FILENM1, WF.ORGFILENM1, WF.FILESIZE1, WF.EXT1, WF.ORD1, \n");
            sql.append("                               WF.FILENO2, WF.FILENM2, WF.ORGFILENM2, WF.FILESIZE2, WF.EXT2, WF.ORD2, \n");
            sql.append("                               WF.FILENO3, WF.FILENM3, WF.ORGFILENM3, WF.FILESIZE3, WF.EXT3, WF.ORD3 \n");
            sql.append("                          FROM WORKRESULT W, \n");
            sql.append("                               (SELECT PLANNO, RESULTNO, \n");
            sql.append("                                       MAX(DECODE(FILENO, 1, FILENO, '')) FILENO1, MAX(DECODE(FILENO, 1, FILENM, '')) FILENM1, MAX(DECODE(FILENO, 1, ORGFILENM, '')) ORGFILENM1, \n");
            sql.append("                                       MAX(DECODE(FILENO, 1, FILESIZE, '')) FILESIZE1, MAX(DECODE(FILENO, 1, EXT, '')) EXT1, MAX(DECODE(FILENO, 1, ORD, '')) ORD1, \n");
            sql.append("                                       MAX(DECODE(FILENO, 2, FILENO, '')) FILENO2, MAX(DECODE(FILENO, 2, FILENM, '')) FILENM2, MAX(DECODE(FILENO, 2, ORGFILENM, '')) ORGFILENM2, \n");
            sql.append("                                       MAX(DECODE(FILENO, 2, FILESIZE, '')) FILESIZE2, MAX(DECODE(FILENO, 2, EXT, '')) EXT2, MAX(DECODE(FILENO, 2, ORD, '')) ORD2, \n");
            sql.append("                                       MAX(DECODE(FILENO, 3, FILENO, '')) FILENO3, MAX(DECODE(FILENO, 3, FILENM, '')) FILENM3, MAX(DECODE(FILENO, 3, ORGFILENM, '')) ORGFILENM3, \n");
            sql.append("                                       MAX(DECODE(FILENO, 3, FILESIZE, '')) FILESIZE3, MAX(DECODE(FILENO, 3, EXT, '')) EXT3, MAX(DECODE(FILENO, 3, ORD, '')) ORD3 \n");
            sql.append("                                  FROM WORKRESULTFILE \n");
            sql.append("                                 GROUP BY PLANNO, RESULTNO) WF \n");
            sql.append("                         WHERE W.PLANNO = WF.PLANNO(+) \n");
            sql.append("                           AND W.RESULTNO = WF.RESULTNO(+) \n");
            sql.append("                           AND W.USEYN = 'Y' \n");
            sql.append("                           AND W.PLANNO = ? \n");
            sql.append("                         ORDER BY W.RESULTNO DESC \n");
            sql.append("                        ) A) A1) X \n");
            sql.append(" WHERE SEQ BETWEEN ? AND ? \n");

            con = ConnectionManager.getConnection(false);
            pstmt = con.prepareStatement(sql.toString());
            pstmt.setInt(1, planno);
            pstmt.setInt(2, start);
            pstmt.setInt(3, end);
            rs = pstmt.executeQuery();

            while ( rs.next() ) {
                WorkplanBean wBean = new WorkplanBean();
                wBean.setSeqno(rs.getInt("BUNHO"));
                wBean.setPlanno(rs.getInt("PLANNO"));
                wBean.setResultno(rs.getInt("RESULTNO"));
                wBean.setTitle(rs.getString("TITLE"));
                wBean.setReadcnt(rs.getInt("READCNT"));
                wBean.setUpperdeptcd(rs.getString("UPPERDEPTCD"));
                wBean.setUpperdeptnm(rs.getString("UPPERDEPTNM"));
                wBean.setDeptcd(rs.getString("DEPTCD"));
                wBean.setDeptnm(rs.getString("DEPTNM"));
                wBean.setChrgunitcd(rs.getInt("CHRGUNITCD"));
                wBean.setChrgunitnm(rs.getString("CHRGUNITNM"));
                wBean.setChrgusrcd(rs.getString("CHRGUSRCD"));
                wBean.setChrgusrnm(rs.getString("CHRGUSRNM"));
                wBean.setCrtdt(rs.getString("CRTDT"));
                wBean.setCrtusrid(rs.getString("CRTUSRID"));
                wBean.setUptdt(rs.getString("UPTDT"));
                wBean.setUptusrid(rs.getString("UPTUSRID"));

                String[] columnName = {"CONTENT"};
                String[] columnData = new String[columnName.length];
                for ( int i = 0; i < columnName.length; i++ ) {
                    sql.delete(0, sql.capacity());
                    sql.append("SELECT " + columnName[i] + " FROM WORKRESULT WHERE PLANNO = ? AND RESULTNO = ?");
                    PreparedStatement p = con.prepareStatement(sql.toString());
                    p.setInt(1, rs.getInt("PLANNO"));
                    p.setInt(2, rs.getInt("RESULTNO"));
                    ResultSet r = p.executeQuery();
                    if ( r.next() ) columnData[i] = Utils.readClobData(r, columnName[i]);
                    ConnectionManager.close(p, r);
                }
                wBean.setContent(columnData[0]);

                wBean.setFileno1(rs.getInt("FILENO1"));
                wBean.setFilenm1(rs.getString("FILENM1"));
                wBean.setOrgfilenm1(rs.getString("ORGFILENM1"));
                wBean.setFilesize1(rs.getInt("FILESIZE1"));
                wBean.setExt1(rs.getString("EXT1"));
                wBean.setOrd1(rs.getInt("ORD1"));

                wBean.setFileno2(rs.getInt("FILENO2"));
                wBean.setFilenm2(rs.getString("FILENM2"));
                wBean.setOrgfilenm2(rs.getString("ORGFILENM2"));
                wBean.setFilesize2(rs.getInt("FILESIZE2"));
                wBean.setExt2(rs.getString("EXT2"));
                wBean.setOrd2(rs.getInt("ORD2"));

                wBean.setFileno3(rs.getInt("FILENO3"));
                wBean.setFilenm3(rs.getString("FILENM3"));
                wBean.setOrgfilenm3(rs.getString("ORGFILENM3"));
                wBean.setFilesize3(rs.getInt("FILESIZE3"));
                wBean.setExt3(rs.getString("EXT3"));
                wBean.setOrd3(rs.getInt("ORD3"));

                if ( result == null ) result = new ArrayList();
                result.add(wBean);
            }
        } catch ( Exception e ) {
            logger.error("ERROR", e);
            ConnectionManager.close(con, pstmt, rs);
            throw e;
        } finally {
            ConnectionManager.close(con, pstmt, rs);
        }

        return result;
    }



    /**
     * 업무계획실적 저장하기
     * @param wBean
     * @return
     * @throws Exception
     */
    public int insertWorkResult(Connection con, WorkplanBean wBean) throws Exception {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;

        try {
            StringBuffer sql = new StringBuffer();
            sql.append("INSERT INTO WORKRESULT \n");
            sql.append("(PLANNO, RESULTNO, TITLE, READCNT, USEYN, \n");
            sql.append(" UPPERDEPTCD, UPPERDEPTNM, DEPTCD, DEPTNM, CHRGUNITCD, \n");
            sql.append(" CHRGUNITNM, CHRGUSRCD, CHRGUSRNM, CRTDT, CRTUSRID, \n");
            sql.append(" UPTDT, UPTUSRID) \n");
            sql.append("VALUES \n");
            sql.append("(?, ?, ?, ?, ?, \n");
            sql.append(" ?, ?, ?, ?, ?, \n");
            sql.append(" ?, ?, ?, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), ?, \n");
            sql.append(" TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), ?) \n");

            int resultno = getNextWorkResultno(con, wBean.getPlanno());

            pstmt = con.prepareStatement(sql.toString());
            pstmt.setInt(1, wBean.getPlanno());
            pstmt.setInt(2, resultno);
            pstmt.setString(3, wBean.getTitle());
            pstmt.setInt(4, wBean.getReadcnt());
            pstmt.setString(5, wBean.getUseyn());
            pstmt.setString(6, wBean.getUpperdeptcd());
            pstmt.setString(7, wBean.getUpperdeptnm());
            pstmt.setString(8, wBean.getDeptcd());
            pstmt.setString(9, wBean.getDeptnm());
            pstmt.setInt(10, wBean.getChrgunitcd());
            pstmt.setString(11, wBean.getChrgunitnm());
            pstmt.setString(12, wBean.getChrgusrcd());
            pstmt.setString(13, wBean.getChrgusrnm());
            pstmt.setString(14, wBean.getCrtusrid());
            pstmt.setString(15, wBean.getUptusrid());

            if ( pstmt.executeUpdate() > 0 ) {
                String[] columnName = {"CONTENT"};
                String[] columnData = {wBean.getContent()};
                for ( int i = 0; i < columnName.length; i++ ) {
                    sql.delete(0, sql.capacity());
                    sql.append("SELECT " + columnName[i] + " FROM WORKRESULT WHERE PLANNO = ? AND RESULTNO = ? FOR UPDATE");
                    PreparedStatement p = con.prepareStatement(sql.toString());
                    p.setInt(1, wBean.getPlanno());
                    p.setInt(2, resultno);
                    ResultSet r = p.executeQuery();
                    if ( r.next() ) Utils.writeClobData(r, columnName[i], columnData[i]);
                    ConnectionManager.close(p, r);
                }

                result = resultno;
            }
        } catch ( Exception e ) {
            logger.error("ERROR", e);
            ConnectionManager.close(pstmt, rs);
            throw e;
        } finally {
            ConnectionManager.close(pstmt, rs);
        }

        return result;
    }

    /**
     * 업무계획실적 수정하기
     * @param wBean
     * @return
     * @throws Exception
     */
    public int updateWorkResult(Connection con, WorkplanBean wBean) throws Exception {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;

        try {
            StringBuffer sql = new StringBuffer();
            sql.append("UPDATE WORKRESULT \n");
            sql.append("SET TITLE = ?, CONTENT=EMPTY_CLOB(), USEYN = ?, CHRGUNITCD = ?, CHRGUNITNM =?, CHRGUSRCD = ?, CHRGUSRNM = ?,  UPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), UPTUSRID = ? \n");
            sql.append("WHERE PLANNO = ? \n");
            sql.append("AND RESULTNO = ? \n");

            pstmt = con.prepareStatement(sql.toString());           
            pstmt.setString(1, wBean.getTitle());
            pstmt.setString(2, wBean.getUseyn());
            pstmt.setInt(3, wBean.getChrgunitcd());
            pstmt.setString(4, wBean.getChrgunitnm());
            pstmt.setString(5, wBean.getChrgusrcd());
            pstmt.setString(6, wBean.getChrgusrnm());
            pstmt.setString(7, wBean.getUptusrid());
            pstmt.setInt(8, wBean.getPlanno());
            pstmt.setInt(9, wBean.getResultno());

            if ( pstmt.executeUpdate() > 0 ) {
                if ( "Y".equals(wBean.getUseyn()) ) {
                    String[] columnName = {"CONTENT"};
                    String[] columnData = {wBean.getContent()};
                    for ( int i = 0; i < columnName.length; i++ ) {
                        sql.delete(0, sql.capacity());
                        sql.append("SELECT " + columnName[i] + " FROM WORKRESULT WHERE PLANNO = ? AND RESULTNO = ? FOR UPDATE");
                        PreparedStatement p = con.prepareStatement(sql.toString());
                        p.setInt(1, wBean.getPlanno());
                        p.setInt(2, wBean.getResultno());
                        ResultSet r = p.executeQuery();
                        if ( r.next() ) Utils.writeClobData(r, columnName[i], columnData[i]);
                        ConnectionManager.close(p, r);
                    }
                }

                result = wBean.getResultno();
            }
        } catch ( Exception e ) {
            logger.error("ERROR", e);
            ConnectionManager.close(pstmt, rs);
            throw e;
        } finally {
            ConnectionManager.close(pstmt, rs);
        }

        return result;
    }

    /**
     * 업무실적첨부파일 가져오기
     * @param conn
     * @param planno
     * @param resultno
     * @param fileno
     * @return
     * @throws Exception
     */
    public WorkplanFileBean getWorkResultFile(Connection conn, int planno, int resultno, int fileno) throws Exception {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        WorkplanFileBean result = null;

        try {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT PLANNO, RESULTNO, FILENO, FILENM, ORGFILENM, FILESIZE, EXT, ORD \n");
            sql.append("FROM WORKRESULTFILE \n");
            sql.append("WHERE PLANNO = ? \n");
            sql.append("AND RESULTNO = ? \n");
            sql.append("AND FILENO = ? \n");

            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setInt(1, planno);
            pstmt.setInt(2, resultno);
            pstmt.setInt(3, fileno);
            rs = pstmt.executeQuery();

            if ( rs.next() ) {
                result = new WorkplanFileBean();
                result.setPlanno(rs.getInt("PLANNO"));
                result.setResultno(rs.getInt("RESULTNO"));
                result.setFileno(rs.getInt("FILENO"));
                result.setFilenm(rs.getString("FILENM"));
                result.setOrgfilenm(rs.getString("ORGFILENM"));
                result.setFilesize(rs.getInt("FILESIZE"));
                result.setExt(rs.getString("EXT"));
                result.setOrd(rs.getInt("ORD"));
            }
        } catch ( Exception e ) {
            logger.error("ERROR",e);
            ConnectionManager.close(pstmt, rs);
            throw e;
        } finally {
            ConnectionManager.close(pstmt, rs);
        }

        return result;
    }

    /**
     * 업무실적첨부파일 삭제
     * @param conn
     * @param planno
     * @param resultno
     * @param fileno
     * @return
     * @throws Exception
     */
    public int deleteWorkResultFile(Connection conn, int planno, int resultno, int fileno) throws Exception {
        PreparedStatement pstmt = null;
        int result = 0;

        try {
            StringBuffer sql = new StringBuffer();
            sql.append("DELETE \n");
            sql.append("FROM WORKRESULTFILE \n");
            sql.append("WHERE PLANNO = ? \n");
            sql.append("AND RESULTNO = ? \n");
            sql.append("AND FILENO = ? \n");

            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setInt(1, planno);
            pstmt.setInt(2, resultno);
            pstmt.setInt(3, fileno);
            result = pstmt.executeUpdate();
        } catch ( Exception e ) {
            logger.error("ERROR",e);
            ConnectionManager.close(pstmt);
            throw e;
        } finally {
            ConnectionManager.close(pstmt);
        }

        return result;
    }

    /**
     * 업무실적첨부파일 추가
     * @param conn
     * @param fileBean
     * @param planno
     * @param resultno
     * @return
     * @throws Exception
     */
    public int addWorkResultFile(Connection conn, FileBean fileBean, int planno, int resultno) throws Exception {
        PreparedStatement pstmt = null;
        int result = 0;

        try {
            StringBuffer sql = new StringBuffer();
            sql.append("INSERT INTO WORKRESULTFILE \n");
            sql.append("(PLANNO, RESULTNO, FILENO, FILENM, ORGFILENM, FILESIZE, EXT, ORD) \n");
            sql.append("VALUES \n");
            sql.append("(?, ?, ?, ?, ?, ?, ?, ?) \n");

            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setInt(1, planno);
            pstmt.setInt(2, resultno);
            pstmt.setInt(3, fileBean.getSeq());
            pstmt.setString(4, fileBean.getFilenm());
            pstmt.setString(5, fileBean.getOriginfilenm());
            pstmt.setInt(6, fileBean.getFilesize());
            pstmt.setString(7, fileBean.getExt());
            pstmt.setInt(8, fileBean.getSeq());
            result = pstmt.executeUpdate();
        } catch ( Exception e ) {
            logger.error("ERROR",e);
            ConnectionManager.close(pstmt);
            throw e;
        } finally {
            ConnectionManager.close(pstmt);
        }

        return result;
    }
}