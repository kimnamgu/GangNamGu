package nexti.ejms.research.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletContext;
import nexti.ejms.commapproval.model.commapprovalBean;
import nexti.ejms.commapproval.model.commapprovalManager;
import nexti.ejms.common.ConnectionManager;
import nexti.ejms.research.form.ResearchForm;
import nexti.ejms.util.FileBean;
import nexti.ejms.util.FileManager;
import nexti.ejms.util.Utils;
import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

public class ResearchDAO
{
  private static Logger logger = Logger.getLogger(ResearchDAO.class);

  public int changeFormseq(int rchno, String sessionId, int[] formseq)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    int result = 0;
    try
    {
      con = ConnectionManager.getConnection(false);

      String[][] tableName = { { "RCHSUB", "RCHSUBFILE", "RCHEXAM", "RCHEXAMFILE" }, 
        { "RCHSUB_TEMP", "RCHSUBFILE_TEMP", "RCHEXAM_TEMP", "RCHEXAMFILE_TEMP" } };

      int MAX_FORMSEQ_CNT = 60;
      for (int repeat = 0; repeat <= MAX_FORMSEQ_CNT; repeat += MAX_FORMSEQ_CNT) {
        for (int i = 0; i < formseq.length; i++) {
          int newFormseq = 0;
          int oldFormseq = 0;
          if (repeat == 0) {
            newFormseq = i + 1 + MAX_FORMSEQ_CNT;
            oldFormseq = i + 1;
          } else {
            newFormseq = formseq[i];
            oldFormseq = i + 1 + MAX_FORMSEQ_CNT;
          }

          if (rchno == 0)
            for (int j = 0; j < tableName[1].length; j++) {
              StringBuffer sql = new StringBuffer();
              sql.append("UPDATE " + tableName[1][j] + "\n");
              sql.append("SET FORMSEQ = ?\n");
              sql.append("WHERE SESSIONID LIKE ?\n");
              sql.append("AND FORMSEQ = ?\n");

              pstmt = con.prepareStatement(sql.toString());
              pstmt.setInt(1, newFormseq);
              pstmt.setString(2, sessionId);
              pstmt.setInt(3, oldFormseq);

              result += pstmt.executeUpdate();
              pstmt.clearParameters();
            }
          else {
            for (int j = 0; j < tableName[0].length; j++) {
              StringBuffer sql = new StringBuffer();
              sql.append("UPDATE " + tableName[0][j] + "\n");
              sql.append("SET FORMSEQ = ?\n");
              sql.append("WHERE RCHNO = ?\n");
              sql.append("AND FORMSEQ = ?\n");

              pstmt = con.prepareStatement(sql.toString());
              pstmt.setInt(1, newFormseq);
              pstmt.setInt(2, rchno);
              pstmt.setInt(3, oldFormseq);

              result += pstmt.executeUpdate();
              pstmt.clearParameters();
            }
          }
        }
      }

      con.commit(); } catch (Exception e) {
      try {
        con.rollback(); } catch (Exception localException1) {
      }logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt);
      throw e; } finally {
      try {
        con.setAutoCommit(true); } catch (Exception localException2) {
      }ConnectionManager.close(con, pstmt);
    }

    return result;
  }

  public int getRchSubCount(int rchno) throws Exception {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int result = 0;
    try
    {
      StringBuffer sql = new StringBuffer();
      sql.append("SELECT COUNT(*) \n");
      sql.append("FROM RCHSUB \n");
      sql.append("WHERE RCHNO = ? \n");

      con = ConnectionManager.getConnection();
      pstmt = con.prepareStatement(sql.toString());

      pstmt.setInt(1, rchno);

      rs = pstmt.executeQuery();

      if (rs.next())
        result = rs.getInt(1);
    }
    catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }

    return result;
  }

  public List getRchIndividualList(int rchno) throws Exception {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List result = null;
    try
    {
      StringBuffer sql = new StringBuffer();
      sql.append("SELECT ANSUSRSEQ, R.CRTUSRID, CRTUSRNM, DEPT_ID, DEPT_FULLNAME, CLASS_NAME \n");
      sql.append("FROM RCHANS R, USR U \n");
      sql.append("WHERE R.CRTUSRID = U.USER_ID(+) \n");
      sql.append("AND RCHNO = ? \n");
      sql.append("GROUP BY ANSUSRSEQ, R.CRTUSRID, CRTUSRNM, DEPT_ID, DEPT_FULLNAME, CLASS_NAME \n");
      sql.append("ORDER BY ANSUSRSEQ \n");

      con = ConnectionManager.getConnection();
      pstmt = con.prepareStatement(sql.toString());

      pstmt.setInt(1, rchno);

      rs = pstmt.executeQuery();

      while (rs.next()) {
        HashMap hs = new HashMap();
        hs.put("ANSUSRSEQ", Utils.nullToEmptyString(rs.getString("ANSUSRSEQ")));
        hs.put("USER_ID", Utils.nullToEmptyString(rs.getString("CRTUSRID")));
        hs.put("USER_NAME", Utils.nullToEmptyString(rs.getString("CRTUSRNM")));
        hs.put("DEPT_ID", Utils.nullToEmptyString(rs.getString("DEPT_ID")));
        hs.put("DEPT_NAME", Utils.nullToEmptyString(rs.getString("DEPT_FULLNAME")));
        hs.put("CLASS_NAME", Utils.nullToEmptyString(rs.getString("CLASS_NAME")));
        hs.put("ANSLIST", getRchIndividualAnsList(con, rchno, rs.getInt("ANSUSRSEQ")));

        if (result == null) result = new ArrayList();
        result.add(hs);
      }
    } catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }

    return result;
  }

  public List getRchIndividualAnsList(Connection con, int rchno, int ansusrseq) throws Exception {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List result = null;
    try
    {
      StringBuffer sql = new StringBuffer();
      sql.append("SELECT ANSUSRSEQ, FORMSEQ, ANSCONT, OTHER \n");
      sql.append("FROM RCHANS R, USR U \n");
      sql.append("WHERE R.CRTUSRID = U.USER_ID(+) \n");
      sql.append("AND RCHNO = ? \n");
      sql.append("AND ANSUSRSEQ = ? \n");
      sql.append("ORDER BY ANSUSRSEQ, FORMSEQ \n");

      pstmt = con.prepareStatement(sql.toString());

      pstmt.setInt(1, rchno);
      pstmt.setInt(2, ansusrseq);

      rs = pstmt.executeQuery();

      while (rs.next()) {
        HashMap hs = new HashMap();
        hs.put("ANSUSRSEQ", Utils.nullToEmptyString(rs.getString("ANSUSRSEQ")));
        hs.put("FORMSEQ", Utils.nullToEmptyString(rs.getString("FORMSEQ")));
        hs.put("ANSCONT", Utils.nullToEmptyString(rs.getString("ANSCONT")));
        hs.put("OTHER", Utils.nullToEmptyString(rs.getString("OTHER")));

        if (result == null) result = new ArrayList();
        result.add(hs);
      }
    } catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(pstmt, rs);
    }

    return result;
  }

  public List getRchObjectResultList(Connection con, int rchno) throws Exception {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List result = null;
    try
    {
      StringBuffer sql = new StringBuffer();
      sql.append("SELECT RE.FORMSEQ, RE.EXAMSEQ, NVL(ANSCONT, 0) ANSCONT, ROUND(NVL(ANSCONT, 0) / ANSUSRSEQ_CNT * 100) || '%' RATE \n");
      sql.append("FROM RCHSUB RS, RCHEXAM RE, (SELECT COUNT(DISTINCT ANSUSRSEQ) ANSUSRSEQ_CNT FROM RCHANS WHERE RCHNO = ?), \n");
      sql.append("     (SELECT RE.RCHNO, RE.FORMSEQ, RE.EXAMSEQ, COUNT(ANSCONT) ANSCONT \n");
      sql.append("      FROM RCHEXAM RE, RCHANS RA \n");
      sql.append("      WHERE RE.RCHNO = RA.RCHNO \n");
      sql.append("      AND RE.FORMSEQ = RA.FORMSEQ \n");
      sql.append("      AND TO_CHAR(RE.EXAMSEQ) IN ( \n");
      sql.append("          SELECT SUBSTR(',' || RA.ANSCONT || ',', \n");
      sql.append("                        INSTR(',' || RA.ANSCONT || ',', ',', 1, LEVEL) + 1, \n");
      sql.append("                        INSTR(',' || RA.ANSCONT || ',', ',', 1, LEVEL + 1) - INSTR(',' || RA.ANSCONT || ',', ',', 1, LEVEL) - 1) SUB \n");
      sql.append("          FROM DUAL \n");
      sql.append("          CONNECT BY LEVEL <= LENGTH(',' || RA.ANSCONT || ',') - LENGTH(REPLACE(',' || RA.ANSCONT || ',', ',')) - 1 \n");
      sql.append("          ) \n");
      sql.append("      AND RE.RCHNO = ? \n");
      sql.append("      GROUP BY RE.RCHNO, RE.FORMSEQ, RE.EXAMSEQ) RA \n");
      sql.append("WHERE RS.RCHNO = RE.RCHNO \n");
      sql.append("AND RS.FORMSEQ = RE.FORMSEQ \n");
      sql.append("AND RE.RCHNO = RA.RCHNO(+) \n");
      sql.append("AND RE.FORMSEQ = RA.FORMSEQ(+) \n");
      sql.append("AND RE.EXAMSEQ = RA.EXAMSEQ(+) \n");
      sql.append("AND RS.FORMTYPE IN ('01', '02') \n");
      sql.append("AND RS.RCHNO = ? \n");
      sql.append("ORDER BY RS.FORMSEQ, RE.EXAMSEQ \n");

      pstmt = con.prepareStatement(sql.toString());

      pstmt.setInt(1, rchno);
      pstmt.setInt(2, rchno);
      pstmt.setInt(3, rchno);

      rs = pstmt.executeQuery();

      while (rs.next()) {
        HashMap hs = new HashMap();
        hs.put("FORMSEQ", Utils.nullToEmptyString(rs.getString("FORMSEQ")));
        hs.put("ANSCONT", Utils.nullToEmptyString(rs.getString("ANSCONT")));
        hs.put("RATE", Utils.nullToEmptyString(rs.getString("RATE")));

        if (result == null) result = new ArrayList();
        result.add(hs);
      }
    } catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(pstmt, rs);
    }

    return result;
  }

  public int getRchObjectResultFormseqMaxCount(Connection con, int rchno) throws Exception {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int result = 0;
    try
    {
      StringBuffer sql = new StringBuffer();
      sql.append("SELECT COUNT(DISTINCT RS.FORMSEQ) \n");
      sql.append("FROM RCHSUB RS, RCHEXAM RE \n");
      sql.append("WHERE RS.RCHNO = RE.RCHNO \n");
      sql.append("AND RS.FORMSEQ = RE.FORMSEQ \n");
      sql.append("AND RS.FORMTYPE IN ('01', '02') \n");
      sql.append("AND RS.RCHNO = ? \n");

      pstmt = con.prepareStatement(sql.toString());

      pstmt.setInt(1, rchno);

      rs = pstmt.executeQuery();

      if (rs.next())
        result = rs.getInt(1);
    }
    catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(pstmt, rs);
    }

    return result;
  }

  public int getRchObjectResultExamseqMaxCount(Connection con, int rchno) throws Exception {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int result = 0;
    try
    {
      StringBuffer sql = new StringBuffer();
      sql.append("SELECT COUNT(DISTINCT RE.EXAMSEQ) \n");
      sql.append("FROM RCHSUB RS, RCHEXAM RE \n");
      sql.append("WHERE RS.RCHNO = RE.RCHNO \n");
      sql.append("AND RS.FORMSEQ = RE.FORMSEQ \n");
      sql.append("AND RS.FORMTYPE IN ('01', '02') \n");
      sql.append("AND RS.RCHNO = ? \n");

      pstmt = con.prepareStatement(sql.toString());

      pstmt.setInt(1, rchno);

      rs = pstmt.executeQuery();

      if (rs.next())
        result = rs.getInt(1);
    }
    catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(pstmt, rs);
    }

    return result;
  }

  public List getRchSubjectResultList(Connection con, int rchno) throws Exception {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List result = null;
    try
    {
      StringBuffer sql = new StringBuffer();
      sql.append("SELECT RS.FORMSEQ, \n");
      sql.append("       CASE WHEN RS.FORMTYPE IN ('01', '02') AND RS.EXAMTYPE = 'Y' AND RA.OTHER IS NOT NULL THEN OTHER \n");
      sql.append("            WHEN RS.FORMTYPE IN ('03', '04') AND RA.ANSCONT IS NOT NULL THEN ANSCONT END ANSCONT \n");
      sql.append("FROM RCHSUB RS, RCHANS RA \n");
      sql.append("WHERE RS.RCHNO = RA.RCHNO \n");
      sql.append("AND RS.FORMSEQ = RA.FORMSEQ \n");
      sql.append("AND ((RS.FORMTYPE IN ('01', '02') AND RS.EXAMTYPE = 'Y' AND RA.OTHER IS NOT NULL) \n");
      sql.append("     OR (RS.FORMTYPE IN ('03', '04') AND RA.ANSCONT IS NOT NULL)) \n");
      sql.append("AND RS.RCHNO = ? \n");
      sql.append("ORDER BY RS.FORMSEQ, RA.ANSUSRSEQ \n");

      pstmt = con.prepareStatement(sql.toString());

      pstmt.setInt(1, rchno);

      rs = pstmt.executeQuery();

      while (rs.next()) {
        HashMap hs = new HashMap();
        hs.put("FORMSEQ", Utils.nullToEmptyString(rs.getString("FORMSEQ")));
        hs.put("ANSCONT", Utils.nullToEmptyString(rs.getString("ANSCONT")));

        if (result == null) result = new ArrayList();
        result.add(hs);
      }
    } catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(pstmt, rs);
    }

    return result;
  }

  public int getRchSubjectResultFormseqCount(Connection con, int rchno) throws Exception {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int result = 0;
    try
    {
      StringBuffer sql = new StringBuffer();
      sql.append("SELECT COUNT(DISTINCT RS.FORMSEQ) \n");
      sql.append("FROM RCHSUB RS, RCHANS RA \n");
      sql.append("WHERE RS.RCHNO = RA.RCHNO \n");
      sql.append("AND RS.FORMSEQ = RA.FORMSEQ \n");
      sql.append("AND ((RS.FORMTYPE IN ('01', '02') AND RS.EXAMTYPE = 'Y' AND RA.OTHER IS NOT NULL) \n");
      sql.append("     OR (RS.FORMTYPE IN ('03', '04') AND RA.ANSCONT IS NOT NULL)) \n");
      sql.append("AND RS.RCHNO = ? \n");

      pstmt = con.prepareStatement(sql.toString());

      pstmt.setInt(1, rchno);

      rs = pstmt.executeQuery();

      if (rs.next())
        result = rs.getInt(1);
    }
    catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(pstmt, rs);
    }

    return result;
  }

  public int getRchSubjectResultExamseqMaxCount(Connection con, int rchno) throws Exception {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int result = 0;
    try
    {
      StringBuffer sql = new StringBuffer();
      sql.append("SELECT MAX(COUNT(RS.FORMSEQ)) \n");
      sql.append("FROM RCHSUB RS, RCHANS RA \n");
      sql.append("WHERE RS.RCHNO = RA.RCHNO \n");
      sql.append("AND RS.FORMSEQ = RA.FORMSEQ \n");
      sql.append("AND ((RS.FORMTYPE IN ('01', '02') AND RS.EXAMTYPE = 'Y' AND RA.OTHER IS NOT NULL) \n");
      sql.append("     OR (RS.FORMTYPE IN ('03', '04') AND RA.ANSCONT IS NOT NULL)) \n");
      sql.append("AND RS.RCHNO = ? \n");
      sql.append("GROUP BY RS.FORMSEQ \n");

      pstmt = con.prepareStatement(sql.toString());

      pstmt.setInt(1, rchno);

      rs = pstmt.executeQuery();

      if (rs.next())
        result = rs.getInt(1);
    }
    catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(pstmt, rs);
    }

    return result;
  }

  public List getRchSubFile(Connection conn, String sessionId, int rchno)
    throws Exception
  {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List result = null;
    try
    {
      StringBuffer sql = new StringBuffer();

      if (rchno == 0) {
        sql.append("SELECT SESSIONID, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
        sql.append("FROM RCHSUBFILE_TEMP \n");
        sql.append("WHERE SESSIONID LIKE ? \n");
      } else {
        sql.append("SELECT RCHNO, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
        sql.append("FROM RCHSUBFILE \n");
        sql.append("WHERE RCHNO=? \n");
      }

      pstmt = conn.prepareStatement(sql.toString());

      if (rchno == 0)
        pstmt.setString(1, sessionId);
      else {
        pstmt.setInt(1, rchno);
      }

      rs = pstmt.executeQuery();

      while (rs.next()) {
        ResearchSubBean rchSubBean = new ResearchSubBean();

        rchSubBean.setSessionId(sessionId);
        rchSubBean.setRchno(rchno);
        rchSubBean.setFormseq(rs.getInt("FORMSEQ"));
        rchSubBean.setFileseq(rs.getInt("FILESEQ"));
        rchSubBean.setFilenm(rs.getString("FILENM"));
        rchSubBean.setOriginfilenm(rs.getString("ORIGINFILENM"));
        rchSubBean.setFilesize(rs.getInt("FILESIZE"));
        rchSubBean.setExt(rs.getString("EXT"));
        rchSubBean.setOrd(rs.getInt("ORD"));

        if (result == null) {
          result = new ArrayList();
        }
        result.add(rchSubBean);
      }
    } catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(pstmt, rs);
    }

    return result;
  }

  public List getRchExamFile(Connection conn, String sessionId, int rchno, int formseq)
    throws Exception
  {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List result = null;
    try
    {
      StringBuffer sql = new StringBuffer();

      if (rchno == 0) {
        sql.append("SELECT SESSIONID, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
        sql.append("FROM RCHEXAMFILE_TEMP \n");
        sql.append("WHERE SESSIONID LIKE ? \n");
      } else {
        sql.append("SELECT RCHNO, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
        sql.append("FROM RCHEXAMFILE \n");
        sql.append("WHERE RCHNO=? \n");
      }
      if (formseq != 0) {
        sql.append("AND FORMSEQ=? \n");
      }

      pstmt = conn.prepareStatement(sql.toString());

      if (rchno == 0)
        pstmt.setString(1, sessionId);
      else {
        pstmt.setInt(1, rchno);
      }
      if (formseq != 0) {
        pstmt.setInt(2, formseq);
      }

      rs = pstmt.executeQuery();

      while (rs.next()) {
        ResearchExamBean rchExamBean = new ResearchExamBean();

        rchExamBean.setSessionId(sessionId);
        rchExamBean.setRchno(rchno);
        rchExamBean.setFormseq(rs.getInt("FORMSEQ"));
        rchExamBean.setExamseq(rs.getInt("EXAMSEQ"));
        rchExamBean.setFileseq(rs.getInt("FILESEQ"));
        rchExamBean.setFilenm(rs.getString("FILENM"));
        rchExamBean.setOriginfilenm(rs.getString("ORIGINFILENM"));
        rchExamBean.setFilesize(rs.getInt("FILESIZE"));
        rchExamBean.setExt(rs.getString("EXT"));
        rchExamBean.setOrd(rs.getInt("ORD"));

        if (result == null) {
          result = new ArrayList();
        }
        result.add(rchExamBean);
      }
    } catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(pstmt, rs);
    }

    return result;
  }

  public int setOrderRchExamFile(Connection conn, String sessionId, int rchno, int formseq)
    throws Exception
  {
    PreparedStatement pstmt = null;
    int result = 0;
    try
    {
      StringBuffer sql = new StringBuffer();

      if (rchno == 0) {
        sql.append("UPDATE RCHEXAMFILE_TEMP R \n");
        sql.append("SET R.EXAMSEQ = (SELECT NEWEXAMSEQ \n");
        sql.append("                 FROM (SELECT ROWNUM NEWEXAMSEQ, EXAMSEQ \n");
        sql.append("                       FROM (SELECT SESSIONID, FORMSEQ, EXAMSEQ \n");
        sql.append("                             FROM RCHEXAMFILE_TEMP \n");
        sql.append("                             WHERE SESSIONID LIKE ? \n");
        sql.append("                             AND FORMSEQ = ? \n");
        sql.append("                             ORDER BY EXAMSEQ)) \n");
        sql.append("                 WHERE EXAMSEQ = R.EXAMSEQ) \n");
        sql.append("WHERE SESSIONID LIKE ? \n");
        sql.append("AND FORMSEQ = ? \n");
        sql.append("AND EXAMSEQ NOT IN (SELECT EXAMSEQ \n");
        sql.append("                    FROM RCHEXAM_TEMP \n");
        sql.append("                    WHERE SESSIONID LIKE ? \n");
        sql.append("                    AND FORMSEQ = ?) \n");
      } else {
        sql.append("UPDATE RCHEXAMFILE R \n");
        sql.append("SET R.EXAMSEQ = (SELECT NEWEXAMSEQ \n");
        sql.append("                 FROM (SELECT ROWNUM NEWEXAMSEQ, EXAMSEQ \n");
        sql.append("                       FROM (SELECT RCHNO, FORMSEQ, EXAMSEQ \n");
        sql.append("                             FROM RCHEXAMFILE \n");
        sql.append("                             WHERE RCHNO = ? \n");
        sql.append("                             AND FORMSEQ = ? \n");
        sql.append("                             ORDER BY EXAMSEQ)) \n");
        sql.append("                 WHERE EXAMSEQ = R.EXAMSEQ) \n");
        sql.append("WHERE RCHNO = ? \n");
        sql.append("AND FORMSEQ = ? \n");
        sql.append("AND EXAMSEQ NOT IN (SELECT EXAMSEQ \n");
        sql.append("                    FROM RCHEXAM \n");
        sql.append("                    WHERE RCHNO = ? \n");
        sql.append("                    AND FORMSEQ = ?) \n");
      }

      pstmt = conn.prepareStatement(sql.toString());

      if (rchno == 0) {
        pstmt.setString(1, sessionId);
        pstmt.setInt(2, formseq);
        pstmt.setString(3, sessionId);
        pstmt.setInt(4, formseq);
        pstmt.setString(5, sessionId);
        pstmt.setInt(6, formseq);
      } else {
        pstmt.setInt(1, rchno);
        pstmt.setInt(2, formseq);
        pstmt.setInt(3, rchno);
        pstmt.setInt(4, formseq);
        pstmt.setInt(5, rchno);
        pstmt.setInt(6, formseq);
      }

      result = pstmt.executeUpdate();
    } catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(pstmt);
      throw e;
    } finally {
      ConnectionManager.close(pstmt);
    }

    return result;
  }

  public List getRchExamUnusedFile(Connection conn, String sessionId, int rchno, int formseq)
    throws Exception
  {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List result = null;
    try
    {
      StringBuffer sql = new StringBuffer();

      if (rchno == 0) {
        sql.append("SELECT SESSIONID, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
        sql.append("FROM RCHEXAMFILE_TEMP R \n");
        sql.append("WHERE SESSIONID LIKE ? \n");
        sql.append("AND FORMSEQ=? \n");
        sql.append("AND EXAMSEQ NOT IN (SELECT EXAMSEQ \n");
        sql.append("                    FROM  RCHEXAM_TEMP \n");
        sql.append("                    WHERE SESSIONID LIKE R.SESSIONID \n");
        sql.append("                    AND FORMSEQ=R.FORMSEQ) \n");
      } else {
        sql.append("SELECT RCHNO, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
        sql.append("FROM RCHEXAMFILE R \n");
        sql.append("WHERE RCHNO=? \n");
        sql.append("AND FORMSEQ=? \n");
        sql.append("AND EXAMSEQ NOT IN (SELECT EXAMSEQ \n");
        sql.append("                    FROM  RCHEXAM \n");
        sql.append("                    WHERE RCHNO=R.RCHNO \n");
        sql.append("                    AND FORMSEQ=R.FORMSEQ) \n");
      }

      pstmt = conn.prepareStatement(sql.toString());

      if (rchno == 0) {
        pstmt.setString(1, sessionId);
        pstmt.setInt(2, formseq);
      } else {
        pstmt.setInt(1, rchno);
        pstmt.setInt(2, formseq);
      }

      rs = pstmt.executeQuery();

      while (rs.next()) {
        ResearchExamBean rchExamBean = new ResearchExamBean();

        rchExamBean.setSessionId(sessionId);
        rchExamBean.setRchno(rchno);
        rchExamBean.setFormseq(rs.getInt("FORMSEQ"));
        rchExamBean.setExamseq(rs.getInt("EXAMSEQ"));
        rchExamBean.setFileseq(rs.getInt("FILESEQ"));
        rchExamBean.setFilenm(rs.getString("FILENM"));
        rchExamBean.setOriginfilenm(rs.getString("ORIGINFILENM"));
        rchExamBean.setFilesize(rs.getInt("FILESIZE"));
        rchExamBean.setExt(rs.getString("EXT"));
        rchExamBean.setOrd(rs.getInt("ORD"));

        if (result == null) {
          result = new ArrayList();
        }
        result.add(rchExamBean);
      }
    } catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(pstmt, rs);
    }

    return result;
  }

  public ResearchSubBean getRchSubFile(Connection conn, String sessionId, int rchno, int formseq)
    throws Exception
  {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ResearchSubBean result = null;
    try
    {
      StringBuffer sql = new StringBuffer();

      if (rchno == 0) {
        sql.append("SELECT SESSIONID, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
        sql.append("FROM RCHSUBFILE_TEMP \n");
        sql.append("WHERE SESSIONID LIKE ? \n");
        sql.append("AND FORMSEQ=? \n");
      } else {
        sql.append("SELECT RCHNO, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
        sql.append("FROM RCHSUBFILE \n");
        sql.append("WHERE RCHNO=? \n");
        sql.append("AND FORMSEQ=? \n");
      }

      pstmt = conn.prepareStatement(sql.toString());

      if (rchno == 0) {
        pstmt.setString(1, sessionId);
        pstmt.setInt(2, formseq);
      } else {
        pstmt.setInt(1, rchno);
        pstmt.setInt(2, formseq);
      }

      rs = pstmt.executeQuery();

      if (rs.next()) {
        result = new ResearchSubBean();

        result.setSessionId(sessionId);
        result.setRchno(rchno);
        result.setFormseq(rs.getInt("FORMSEQ"));
        result.setFileseq(rs.getInt("FILESEQ"));
        result.setFilenm(rs.getString("FILENM"));
        result.setOriginfilenm(rs.getString("ORIGINFILENM"));
        result.setFilesize(rs.getInt("FILESIZE"));
        result.setExt(rs.getString("EXT"));
        result.setOrd(rs.getInt("ORD"));
      }
    } catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(pstmt, rs);
    }

    return result;
  }

  public ResearchExamBean getRchExamFile(Connection conn, String sessionId, int rchno, int formseq, int examseq)
    throws Exception
  {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ResearchExamBean result = null;
    try
    {
      StringBuffer sql = new StringBuffer();

      if (rchno == 0) {
        sql.append("SELECT SESSIONID, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
        sql.append("FROM RCHEXAMFILE_TEMP \n");
        sql.append("WHERE SESSIONID LIKE ? \n");
        sql.append("AND FORMSEQ=? \n");
        sql.append("AND EXAMSEQ=? \n");
      } else {
        sql.append("SELECT RCHNO, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD \n");
        sql.append("FROM RCHEXAMFILE \n");
        sql.append("WHERE RCHNO=? \n");
        sql.append("AND FORMSEQ=? \n");
        sql.append("AND EXAMSEQ=? \n");
      }

      pstmt = conn.prepareStatement(sql.toString());

      if (rchno == 0) {
        pstmt.setString(1, sessionId);
        pstmt.setInt(2, formseq);
        pstmt.setInt(3, examseq);
      } else {
        pstmt.setInt(1, rchno);
        pstmt.setInt(2, formseq);
        pstmt.setInt(3, examseq);
      }

      rs = pstmt.executeQuery();

      if (rs.next()) {
        result = new ResearchExamBean();

        result.setSessionId(sessionId);
        result.setRchno(rchno);
        result.setFormseq(rs.getInt("FORMSEQ"));
        result.setExamseq(rs.getInt("EXAMSEQ"));
        result.setFileseq(rs.getInt("FILESEQ"));
        result.setFilenm(rs.getString("FILENM"));
        result.setOriginfilenm(rs.getString("ORIGINFILENM"));
        result.setFilesize(rs.getInt("FILESIZE"));
        result.setExt(rs.getString("EXT"));
        result.setOrd(rs.getInt("ORD"));
      }
    } catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(pstmt, rs);
    }

    return result;
  }

  public int addRchSubFile(Connection conn, String sessionId, int rchno, int formseq, FileBean fileBean)
    throws Exception
  {
    PreparedStatement pstmt = null;

    int result = 0;
    try
    {
      StringBuffer sql = new StringBuffer();

      if (rchno == 0) {
        sql.append("INSERT INTO \n");
        sql.append("RCHSUBFILE_TEMP(SESSIONID, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD) \n");
        sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?) \n");
      } else {
        sql.append("INSERT INTO \n");
        sql.append("RCHSUBFILE(RCHNO, FORMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD) \n");
        sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?) \n");
      }

      pstmt = conn.prepareStatement(sql.toString());

      if (rchno == 0)
        pstmt.setString(1, sessionId);
      else {
        pstmt.setInt(1, rchno);
      }
      pstmt.setInt(2, formseq);
      pstmt.setInt(3, fileBean.getFileseq());
      pstmt.setString(4, fileBean.getFilenm());
      pstmt.setString(5, fileBean.getOriginfilenm());
      pstmt.setInt(6, fileBean.getFilesize());
      pstmt.setString(7, fileBean.getExt());
      pstmt.setInt(8, fileBean.getFileseq());

      result = pstmt.executeUpdate();
    } catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(pstmt);
      throw e;
    } finally {
      ConnectionManager.close(pstmt);
    }

    return result;
  }

  public int addRchExamFile(Connection conn, String sessionId, int rchno, int formseq, int examseq, FileBean fileBean)
    throws Exception
  {
    PreparedStatement pstmt = null;

    int result = 0;
    try
    {
      StringBuffer sql = new StringBuffer();

      if (rchno == 0) {
        sql.append("INSERT INTO \n");
        sql.append("RCHEXAMFILE_TEMP(SESSIONID, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD) \n");
        sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) \n");
      } else {
        sql.append("INSERT INTO \n");
        sql.append("RCHEXAMFILE(RCHNO, FORMSEQ, EXAMSEQ, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD) \n");
        sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) \n");
      }
      pstmt = conn.prepareStatement(sql.toString());

      if (rchno == 0)
        pstmt.setString(1, sessionId);
      else {
        pstmt.setInt(1, rchno);
      }
      pstmt.setInt(2, formseq);
      pstmt.setInt(3, examseq);
      pstmt.setInt(4, fileBean.getFileseq());
      pstmt.setString(5, fileBean.getFilenm());
      pstmt.setString(6, fileBean.getOriginfilenm());
      pstmt.setInt(7, fileBean.getFilesize());
      pstmt.setString(8, fileBean.getExt());
      pstmt.setInt(9, fileBean.getFileseq());

      result = pstmt.executeUpdate();
    } catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(pstmt);
      throw e;
    } finally {
      ConnectionManager.close(pstmt);
    }

    return result;
  }

  public int delRchSubFile(Connection conn, String sessionId, int rchno, int formseq, int fileseq)
    throws Exception
  {
    PreparedStatement pstmt = null;

    int result = 0;
    try
    {
      StringBuffer sql = new StringBuffer();

      if (rchno == 0) {
        sql.append("DELETE FROM RCHSUBFILE_TEMP \n");
        sql.append("WHERE SESSIONID LIKE ? AND FORMSEQ=? AND FILESEQ=? \n");
      } else {
        sql.append("DELETE FROM RCHSUBFILE \n");
        sql.append("WHERE RCHNO=? AND FORMSEQ=? AND FILESEQ=? \n");
      }

      pstmt = conn.prepareStatement(sql.toString());

      if (rchno == 0)
        pstmt.setString(1, sessionId);
      else {
        pstmt.setInt(1, rchno);
      }
      pstmt.setInt(2, formseq);
      pstmt.setInt(3, fileseq);

      result = pstmt.executeUpdate();
    } catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(pstmt);
      throw e;
    } finally {
      ConnectionManager.close(pstmt);
    }

    return result;
  }

  public int delRchExamFile(Connection conn, String sessionId, int rchno, int formseq, int examseq, int fileseq)
    throws Exception
  {
    PreparedStatement pstmt = null;

    int result = 0;
    try
    {
      StringBuffer sql = new StringBuffer();

      if (rchno == 0) {
        sql.append("DELETE FROM RCHEXAMFILE_TEMP \n");
        sql.append("WHERE SESSIONID LIKE ? AND FORMSEQ=? AND EXAMSEQ=? AND FILESEQ=? \n");
      } else {
        sql.append("DELETE FROM RCHEXAMFILE \n");
        sql.append("WHERE RCHNO=? AND FORMSEQ=? AND EXAMSEQ=? AND FILESEQ=? \n");
      }

      pstmt = conn.prepareStatement(sql.toString());

      if (rchno == 0)
        pstmt.setString(1, sessionId);
      else {
        pstmt.setInt(1, rchno);
      }
      pstmt.setInt(2, formseq);
      pstmt.setInt(3, examseq);
      pstmt.setInt(4, fileseq);

      pstmt.executeUpdate();
    } catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(pstmt);
      throw e;
    } finally {
      ConnectionManager.close(pstmt);
    }

    return result;
  }

  public List getResearchMyList(String usrid, String deptcd, String initentry, String isSysMgr, String groupyn, String sch_title, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, int start, int end)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List reserchList = null;
    int bindPos = 0;
    try
    {
      StringBuffer selectQuery = new StringBuffer();
      selectQuery.append("\n SELECT (CNT-SEQ+1) BUNHO, SEQ, RCHNO, TITLE, RANGE, GROUPYN, STRDT, ENDDT, TDAY, CHRGUSRID ");
      selectQuery.append("\n   FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* \t\t\t\t\t\t\t\t\t");
      selectQuery.append("\n           FROM (SELECT ROWNUM SEQ, A2.* \t\t\t\t\t\t\t\t\t\t");
      selectQuery.append("\n\t\t             FROM (SELECT A.RCHNO, A.TITLE, A.RANGE, A.GROUPYN, REPLACE(SUBSTR(STRDT,6),'-','/') STRDT,  ");
      selectQuery.append("\n\t\t\t\t\t\t\t  REPLACE(SUBSTR(ENDDT,6),'-','/') ENDDT, TRUNC(TO_DATE(ENDDT,'YYYY-MM-DD')-SYSDATE)TDAY, CHRGUSRID ");
      selectQuery.append("\n                         FROM RCHMST A \t\t\t\t\t\t\t\t\t\t");
      selectQuery.append("\n                     \t   WHERE GROUPYN LIKE ?\t\t\t\t\t\t\t\t\t");

      if (initentry.equals("first")) {
        selectQuery.append("\t                   AND A.COLDEPTCD = ?\t\t\t\t\t\t\t \t    \n");
        selectQuery.append("                       AND A.CHRGUSRID = ?  \t\t\t\t\t\t\t\t\n");
      }
      else if (isSysMgr.equals("001")) {
        if (!"".equals(sch_deptcd)) selectQuery.append("\n  AND A.COLDEPTCD LIKE ?                           \n");
        if (!"".equals(sch_userid)) selectQuery.append("\n  AND A.CHRGUSRID LIKE ?                           \n");
        if (("".equals(sch_deptcd)) && (!"".equals(sch_deptnm))) selectQuery.append("\n  AND A.COLDEPTNM LIKE ? \n");
        if (("".equals(sch_userid)) && (!"".equals(sch_usernm))) selectQuery.append("\n  AND A.CHRGUSRNM LIKE ? \n"); 
      }
      else { selectQuery.append("                     AND A.COLDEPTCD = ?\t\t\t\t\t\t\t\t\n");
        selectQuery.append("                     AND A.CHRGUSRID = ? \t\t\t\t\t\t\t\t\n");
      }

      if ((!"".equals(sch_title)) || (sch_title != null)) {
        selectQuery.append("\n                     AND A.TITLE LIKE  ?\t\t\t\t\t\t\t\t  \t");
      }

      selectQuery.append("\n                          ORDER BY TDAY DESC, A.ENDDT DESC, A.CRTDT DESC, A.UPTDT DESC) A2) A1) \t\t\t\t\t\t");
      selectQuery.append("\n  WHERE SEQ BETWEEN ? AND ? \t\t\t\t\t\t\t\t\t\t\t\t\t");

      con = ConnectionManager.getConnection();
      pstmt = con.prepareStatement(selectQuery.toString());

      pstmt.setString(++bindPos, "%" + groupyn);

      if (initentry.equals("first")) {
        pstmt.setString(++bindPos, deptcd);
        pstmt.setString(++bindPos, usrid);
      }
      else if (isSysMgr.equals("001")) {
        if (!"".equals(sch_deptcd)) pstmt.setString(++bindPos, "%" + sch_deptcd + "%");
        if (!"".equals(sch_userid)) pstmt.setString(++bindPos, "%" + sch_userid + "%");
        if (("".equals(sch_deptcd)) && (!"".equals(sch_deptnm))) pstmt.setString(++bindPos, "%" + sch_deptnm + "%");
        if (("".equals(sch_userid)) && (!"".equals(sch_usernm))) pstmt.setString(++bindPos, "%" + sch_usernm + "%"); 
      }
      else { pstmt.setString(++bindPos, deptcd);
        pstmt.setString(++bindPos, usrid);
      }

      if ((!sch_title.equals("")) || (sch_title != null)) {
        pstmt.setString(++bindPos, "%" + sch_title + "%");
      }
      pstmt.setInt(++bindPos, start);
      pstmt.setInt(++bindPos, end);

      rs = pstmt.executeQuery();

      reserchList = new ArrayList();

      while (rs.next()) {
        ResearchBean Bean = new ResearchBean();

        Bean.setSeqno(rs.getInt("BUNHO"));
        Bean.setRchno(rs.getInt("RCHNO"));
        Bean.setTitle(rs.getString("TITLE"));
        Bean.setRange(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 1));
        Bean.setRangedetail(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 2));
        Bean.setGroupyn(rs.getString("GROUPYN"));
        Bean.setStrdt(rs.getString("STRDT"));
        Bean.setEnddt(rs.getString("ENDDT"));
        Bean.setTday(rs.getInt("TDAY"));
        Bean.setChrgusrid(rs.getString("CHRGUSRID"));

        reserchList.add(Bean);
      }
    }
    catch (SQLException e)
    {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }

    return reserchList;
  }

  public List getResearchGrpMyList(String usrid, String deptcd, String initentry, String isSysMgr, String sch_title, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, int start, int end)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List reserchList = null;
    int bindPos = 0;
    try
    {
      StringBuffer selectQuery = new StringBuffer();
      selectQuery.append("\n SELECT (CNT-SEQ+1) BUNHO, SEQ, RCHGRPNO, TITLE, RANGE, STRDT, ENDDT, TDAY, CHRGUSRID ");
      selectQuery.append("\n   FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* \t\t\t\t\t\t\t\t\t");
      selectQuery.append("\n           FROM (SELECT ROWNUM SEQ, A2.* \t\t\t\t\t\t\t\t\t\t");
      selectQuery.append("\n\t\t             FROM (SELECT A.RCHGRPNO, A.TITLE, A.RANGE, REPLACE(SUBSTR(STRDT,6),'-','/') STRDT,  ");
      selectQuery.append("\n\t\t\t\t\t\t\t  REPLACE(SUBSTR(ENDDT,6),'-','/') ENDDT, TRUNC(TO_DATE(ENDDT,'YYYY-MM-DD')-SYSDATE)TDAY, CHRGUSRID ");
      selectQuery.append("\n                         FROM RCHGRPMST A \t\t\t\t\t\t\t\t\t\t");
      selectQuery.append("\n                     \t   WHERE 1 = 1\t\t\t\t\t\t\t\t\t\t\t");

      if (initentry.equals("first")) {
        selectQuery.append("\t                   AND A.COLDEPTCD = ?\t\t\t\t\t\t\t \t    \n");
        selectQuery.append("                       AND A.CHRGUSRID = ?  \t\t\t\t\t\t\t\t\n");
      }
      else if (isSysMgr.equals("001")) {
        if (!"".equals(sch_deptcd)) selectQuery.append("\n  AND A.COLDEPTCD LIKE ?                           \n");
        if (!"".equals(sch_userid)) selectQuery.append("\n  AND A.CHRGUSRID LIKE ?                           \n");
        if (("".equals(sch_deptcd)) && (!"".equals(sch_deptnm))) selectQuery.append("\n  AND A.COLDEPTNM LIKE ? \n");
        if (("".equals(sch_userid)) && (!"".equals(sch_usernm))) selectQuery.append("\n  AND A.CHRGUSRNM LIKE ? \n"); 
      }
      else { selectQuery.append("                     AND A.COLDEPTCD = ?\t\t\t\t\t\t\t\t\n");
        selectQuery.append("                     AND A.CHRGUSRID = ? \t\t\t\t\t\t\t\t\n");
      }

      if ((!"".equals(sch_title)) || (sch_title != null)) {
        selectQuery.append("\n                     AND A.TITLE LIKE  ?\t\t\t\t\t\t\t\t  \t");
      }

      selectQuery.append("\n                          ORDER BY TDAY DESC, A.ENDDT DESC, A.CRTDT DESC, A.UPTDT DESC) A2) A1) \t\t\t\t\t\t");
      selectQuery.append("\n  WHERE SEQ BETWEEN ? AND ? \t\t\t\t\t\t\t\t\t\t\t\t\t");

      con = ConnectionManager.getConnection();
      pstmt = con.prepareStatement(selectQuery.toString());

      if (initentry.equals("first")) {
        pstmt.setString(++bindPos, deptcd);
        pstmt.setString(++bindPos, usrid);
      }
      else if (isSysMgr.equals("001")) {
        if (!"".equals(sch_deptcd)) pstmt.setString(++bindPos, "%" + sch_deptcd + "%");
        if (!"".equals(sch_userid)) pstmt.setString(++bindPos, "%" + sch_userid + "%");
        if (("".equals(sch_deptcd)) && (!"".equals(sch_deptnm))) pstmt.setString(++bindPos, "%" + sch_deptnm + "%");
        if (("".equals(sch_userid)) && (!"".equals(sch_usernm))) pstmt.setString(++bindPos, "%" + sch_usernm + "%"); 
      }
      else { pstmt.setString(++bindPos, deptcd);
        pstmt.setString(++bindPos, usrid);
      }

      if ((!sch_title.equals("")) || (sch_title != null)) {
        pstmt.setString(++bindPos, "%" + sch_title + "%");
      }
      pstmt.setInt(++bindPos, start);
      pstmt.setInt(++bindPos, end);

      rs = pstmt.executeQuery();

      reserchList = new ArrayList();

      while (rs.next()) {
        ResearchBean Bean = new ResearchBean();

        Bean.setSeqno(rs.getInt("BUNHO"));
        Bean.setRchgrpno(rs.getInt("RCHGRPNO"));
        Bean.setTitle(rs.getString("TITLE"));
        Bean.setRange(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 1));
        Bean.setRangedetail(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 2));
        Bean.setStrdt(rs.getString("STRDT"));
        Bean.setEnddt(rs.getString("ENDDT"));
        Bean.setTday(rs.getInt("TDAY"));
        Bean.setChrgusrid(rs.getString("CHRGUSRID"));

        reserchList.add(Bean);
      }
    }
    catch (SQLException e)
    {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }

    return reserchList;
  }

  public int getResearchMyTotCnt(String usrid, String deptcd, String sch_title, String mode)
    throws Exception
  {
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    int totalCount = 0;
    int bindPos = 0;
    try
    {
      StringBuffer selectQuery = new StringBuffer();

      selectQuery.append("\n SELECT COUNT(CHRGUSRID) \t\t");
      selectQuery.append("\n   FROM RCHMST \t\t\t\t");
      selectQuery.append("\n  WHERE GROUPYN = 'N' \t\t");
      selectQuery.append("\n    AND CHRGUSRID = ? \t\t");
      selectQuery.append("\n    AND COLDEPTCD = ? \t\t");

      if ("main".equals(mode)) {
        selectQuery.append("\n  AND TO_CHAR(SYSDATE,'YYYY-MM-DD')BETWEEN STRDT AND ENDDT ");
      }

      if ((!sch_title.equals("")) || (sch_title != null)) {
        selectQuery.append("\n  AND TITLE LIKE  ?\t\t");
      }

      conn = ConnectionManager.getConnection();

      pstmt = conn.prepareStatement(selectQuery.toString());
      pstmt.setString(++bindPos, usrid);
      pstmt.setString(++bindPos, deptcd);
      if ((!sch_title.equals("")) || (sch_title != null)) {
        pstmt.setString(++bindPos, "%" + sch_title + "%");
      }

      rs = pstmt.executeQuery();

      if (rs.next())
        totalCount = rs.getInt(1);
    }
    catch (SQLException e)
    {
      logger.error("ERROR", e);
      ConnectionManager.close(conn, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(conn, pstmt, rs);
    }
    return totalCount;
  }

  public int getResearchTotCnt(String usrid, String deptcd, String initentry, String isSysMgr, String groupyn, String sch_title, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, String mode)
    throws Exception
  {
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    int totalCount = 0;
    int bindPos = 0;
    try
    {
      StringBuffer selectQuery = new StringBuffer();

      selectQuery.append("\n SELECT COUNT(CHRGUSRID) \t\t\t");
      selectQuery.append("\n   FROM RCHMST A\t\t\t\t\t");
      selectQuery.append("\n  WHERE GROUPYN LIKE ?\t\t\t\t");

      if (initentry.equals("first")) {
        selectQuery.append("\t                   AND A.COLDEPTCD = ?\t\t\t\t\t\t\t \t    \n");
        selectQuery.append("                       AND A.CHRGUSRID = ?  \t\t\t\t\t\t\t\t\n");
      }
      else if (isSysMgr.equals("001")) {
        if (!"".equals(sch_deptcd)) selectQuery.append("\n  AND A.COLDEPTCD LIKE ?                           \n");
        if (!"".equals(sch_userid)) selectQuery.append("\n  AND A.CHRGUSRID LIKE ?                           \n");
        if (("".equals(sch_deptcd)) && (!"".equals(sch_deptnm))) selectQuery.append("\n  AND A.COLDEPTNM LIKE ? \n");
        if (("".equals(sch_userid)) && (!"".equals(sch_usernm))) selectQuery.append("\n  AND A.CHRGUSRNM LIKE ? \n"); 
      }
      else { selectQuery.append("                     AND A.COLDEPTCD = ?\t\t\t\t\t\t\t\t\n");
        selectQuery.append("                     AND A.CHRGUSRID = ? \t\t\t\t\t\t\t\t\n");
      }

      if ("main".equals(mode)) {
        selectQuery.append("\n  AND TO_CHAR(SYSDATE,'YYYY-MM-DD') BETWEEN A.STRDT AND A.ENDDT ");
      }

      if ((!sch_title.equals("")) || (sch_title != null)) {
        selectQuery.append("\n  AND A.TITLE LIKE  ?\t\t\t");
      }

      conn = ConnectionManager.getConnection();
      pstmt = conn.prepareStatement(selectQuery.toString());

      pstmt.setString(++bindPos, "%" + groupyn);

      if (initentry.equals("first")) {
        pstmt.setString(++bindPos, deptcd);
        pstmt.setString(++bindPos, usrid);
      }
      else if (isSysMgr.equals("001")) {
        if (!"".equals(sch_deptcd)) pstmt.setString(++bindPos, "%" + sch_deptcd + "%");
        if (!"".equals(sch_userid)) pstmt.setString(++bindPos, "%" + sch_userid + "%");
        if (("".equals(sch_deptcd)) && (!"".equals(sch_deptnm))) pstmt.setString(++bindPos, "%" + sch_deptnm + "%");
        if (("".equals(sch_userid)) && (!"".equals(sch_usernm))) pstmt.setString(++bindPos, "%" + sch_usernm + "%"); 
      }
      else { pstmt.setString(++bindPos, deptcd);
        pstmt.setString(++bindPos, usrid);
      }

      if ((!sch_title.equals("")) || (sch_title != null)) {
        pstmt.setString(++bindPos, "%" + sch_title + "%");
      }

      rs = pstmt.executeQuery();

      if (rs.next())
        totalCount = rs.getInt(1);
    }
    catch (SQLException e)
    {
      logger.error("ERROR", e);
      ConnectionManager.close(conn, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(conn, pstmt, rs);
    }
    return totalCount;
  }

  public int getResearchGrpTotCnt(String usrid, String deptcd, String initentry, String isSysMgr, String sch_title, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, String mode)
    throws Exception
  {
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    int totalCount = 0;
    int bindPos = 0;
    try
    {
      StringBuffer selectQuery = new StringBuffer();

      selectQuery.append("\n SELECT COUNT(CHRGUSRID) \t\t\t");
      selectQuery.append("\n   FROM RCHGRPMST A\t\t\t\t");
      selectQuery.append("\n  WHERE 1 = 1\t\t\t\t\t\t");

      if (initentry.equals("first")) {
        selectQuery.append("\t                   AND A.COLDEPTCD = ?\t\t\t\t\t\t\t \t    \n");
        selectQuery.append("                       AND A.CHRGUSRID = ?  \t\t\t\t\t\t\t\t\n");
      }
      else if (isSysMgr.equals("001")) {
        if (!"".equals(sch_deptcd)) selectQuery.append("\n  AND A.COLDEPTCD LIKE ?                           \n");
        if (!"".equals(sch_userid)) selectQuery.append("\n  AND A.CHRGUSRID LIKE ?                           \n");
        if (("".equals(sch_deptcd)) && (!"".equals(sch_deptnm))) selectQuery.append("\n  AND A.COLDEPTNM LIKE ? \n");
        if (("".equals(sch_userid)) && (!"".equals(sch_usernm))) selectQuery.append("\n  AND A.CHRGUSRNM LIKE ? \n"); 
      }
      else { selectQuery.append("                     AND A.COLDEPTCD = ?\t\t\t\t\t\t\t\t\n");
        selectQuery.append("                     AND A.CHRGUSRID = ? \t\t\t\t\t\t\t\t\n");
      }

      if ("main".equals(mode)) {
        selectQuery.append("\n  AND TO_CHAR(SYSDATE,'YYYY-MM-DD') BETWEEN A.STRDT AND A.ENDDT ");
      }

      if ((!sch_title.equals("")) || (sch_title != null)) {
        selectQuery.append("\n  AND A.TITLE LIKE  ?\t\t\t");
      }

      conn = ConnectionManager.getConnection();
      pstmt = conn.prepareStatement(selectQuery.toString());

      if (initentry.equals("first")) {
        pstmt.setString(++bindPos, deptcd);
        pstmt.setString(++bindPos, usrid);
      }
      else if (isSysMgr.equals("001")) {
        if (!"".equals(sch_deptcd)) pstmt.setString(++bindPos, "%" + sch_deptcd + "%");
        if (!"".equals(sch_userid)) pstmt.setString(++bindPos, "%" + sch_userid + "%");
        if (("".equals(sch_deptcd)) && (!"".equals(sch_deptnm))) pstmt.setString(++bindPos, "%" + sch_deptnm + "%");
        if (("".equals(sch_userid)) && (!"".equals(sch_usernm))) pstmt.setString(++bindPos, "%" + sch_usernm + "%"); 
      }
      else { pstmt.setString(++bindPos, deptcd);
        pstmt.setString(++bindPos, usrid);
      }

      if ((!sch_title.equals("")) || (sch_title != null)) {
        pstmt.setString(++bindPos, "%" + sch_title + "%");
      }

      rs = pstmt.executeQuery();

      if (rs.next())
        totalCount = rs.getInt(1);
    }
    catch (SQLException e)
    {
      logger.error("ERROR", e);
      ConnectionManager.close(conn, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(conn, pstmt, rs);
    }
    return totalCount;
  }

  public List getRchAnsusrList(int rchno)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List result = null;
    try
    {
      StringBuffer selectQuery = new StringBuffer();
      selectQuery.append("\n SELECT RCHNO, ANSUSRSEQ, CRTUSRID, NVL(CRTUSRNM, '익명' || ANSUSRSEQ) CRTUSRNM ");
      selectQuery.append("\n FROM RCHANS ");
      selectQuery.append("\n WHERE RCHNO = ? ");
      selectQuery.append("\n GROUP BY RCHNO, ANSUSRSEQ, CRTUSRID, CRTUSRNM ");

      con = ConnectionManager.getConnection();
      pstmt = con.prepareStatement(selectQuery.toString());

      pstmt.setInt(1, rchno);

      rs = pstmt.executeQuery();

      while (rs.next()) {
        if (result == null) {
          result = new ArrayList();
        }

        ResearchBean rbean = new ResearchBean();
        rbean.setRchno(rs.getInt("RCHNO"));
        rbean.setAnsusrseq(rs.getInt("ANSUSRSEQ"));
        rbean.setCrtusrid(rs.getString("CRTUSRID"));
        rbean.setCrtusrnm(rs.getString("CRTUSRNM"));

        result.add(rbean);
      }
    }
    catch (SQLException e) {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }
    return result;
  }

  public ResearchBean getRchMst(int rchno, String sessionId)
    throws Exception
  {
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    ResearchBean Bean = null;

    StringBuffer selectQuery = new StringBuffer();

    if (rchno == 0) {
      selectQuery.append("\n SELECT SESSIONID, TITLE, STRDT, SUBSTR(A.STRDT,1,4)||'년 '||SUBSTR(A.STRDT,6,2)||'월 '||SUBSTR(A.STRDT,9,2)||'일' STRYMD, ");
      selectQuery.append("\n        ENDDT, SUBSTR(A.ENDDT,1,4)||'년 '||SUBSTR(A.ENDDT,6,2)||'월 '||SUBSTR(A.ENDDT,9,2)||'일' ENDYMD, ");
      selectQuery.append("\n        COLDEPTCD, COLDEPTNM, COLDEPTTEL, CHRGUSRID, CHRGUSRNM, SUMMARY, EXHIBIT, OPENTYPE, RANGE, IMGPREVIEW, ");
      selectQuery.append("\n        DUPLICHECK, LIMITCOUNT, GROUPYN, TGTDEPTNM, HEADCONT, TAILCONT, ANSCOUNT, OTHERTGTNM,	LIMIT1, LIMIT2, RCHTARGET1, RCHTARGET2,");
      selectQuery.append("\n        (SELECT DEPT_TEL FROM DEPT WHERE DEPT_ID = A.COLDEPTCD) DEPT_TEL, ");
      selectQuery.append("\n        (SELECT SUBSTR(MAX(SYS_CONNECT_BY_PATH(TARGET, ', ')), 3) ");
      selectQuery.append("\n         FROM (SELECT ROWNUM RN, TARGET ");
      selectQuery.append("\n               FROM (SELECT TGTNAME || '(' || TGTCODE || ')' TARGET ");
      selectQuery.append("\n                     FROM RCHOTHERTARGET_TEMP ");
      selectQuery.append("\n                     WHERE SESSIONID LIKE ? ");
      selectQuery.append("\n                     AND TGTGBN = '1' ");
      selectQuery.append("\n                     ORDER BY TGTCODE)) ");
      selectQuery.append("\n         START WITH RN = 1 ");
      selectQuery.append("\n         CONNECT BY PRIOR RN = RN - 1) OTHERTGTNM ");
      selectQuery.append("\n   FROM RCHMST_TEMP A\t\t\t\t");
      selectQuery.append("\n  WHERE SESSIONID LIKE ? \t\t");
    } else {
      selectQuery.append("\n SELECT RCHNO, TITLE, STRDT, SUBSTR(A.STRDT,1,4)||'년 '||SUBSTR(A.STRDT,6,2)||'월 '||SUBSTR(A.STRDT,9,2)||'일' STRYMD, ");
      selectQuery.append("\n        ENDDT, SUBSTR(A.ENDDT,1,4)||'년 '||SUBSTR(A.ENDDT,6,2)||'월 '||SUBSTR(A.ENDDT,9,2)||'일' ENDYMD, ");
      selectQuery.append("\n        COLDEPTCD, COLDEPTNM, COLDEPTTEL, CHRGUSRID, CHRGUSRNM, SUMMARY, EXHIBIT, OPENTYPE, RANGE, IMGPREVIEW, ");
      selectQuery.append("\n        DUPLICHECK, LIMITCOUNT, GROUPYN, TGTDEPTNM, HEADCONT, TAILCONT, ANSCOUNT, OTHERTGTNM, 	LIMIT1, LIMIT2, RCHTARGET1, RCHTARGET2,");
      selectQuery.append("\n        (SELECT DEPT_TEL FROM DEPT WHERE DEPT_ID = A.COLDEPTCD) DEPT_TEL, ");
      selectQuery.append("\n        (SELECT SUBSTR(MAX(SYS_CONNECT_BY_PATH(TARGET, ', ')), 3) ");
      selectQuery.append("\n         FROM (SELECT ROWNUM RN, TARGET ");
      selectQuery.append("\n               FROM (SELECT TGTNAME || '(' || TGTCODE || ')' TARGET ");
      selectQuery.append("\n                     FROM RCHOTHERTARGET ");
      selectQuery.append("\n                     WHERE RCHNO = ? ");
      selectQuery.append("\n                     AND TGTGBN = '1' ");
      selectQuery.append("\n                     ORDER BY TGTCODE)) ");
      selectQuery.append("\n         START WITH RN = 1 ");
      selectQuery.append("\n         CONNECT BY PRIOR RN = RN - 1) OTHERTGTNM ");
      selectQuery.append("\n   FROM RCHMST A\t\t\t\t");
      selectQuery.append("\n  WHERE RCHNO = ? \t\t");
    }
    try
    {
      conn = ConnectionManager.getConnection();

      pstmt = conn.prepareStatement(selectQuery.toString());

      if (rchno == 0) {
        pstmt.setString(1, sessionId);
        pstmt.setString(2, sessionId);
      } else {
        pstmt.setInt(1, rchno);
        pstmt.setInt(2, rchno);
      }

      rs = pstmt.executeQuery();

      if (rs.next()) {
        Bean = new ResearchBean();
        if (rchno == 0) {
          Bean.setRchno(rchno);
          Bean.setSessionId(rs.getString("SESSIONID"));
        } else {
          Bean.setRchno(rs.getInt("RCHNO"));
          Bean.setSessionId(sessionId);
        }

        Bean.setTitle(rs.getString("TITLE"));
        Bean.setStrdt(rs.getString("STRDT"));
        Bean.setStrymd(rs.getString("STRYMD"));
        Bean.setEnddt(rs.getString("ENDDT"));
        Bean.setEndymd(rs.getString("ENDYMD"));
        Bean.setColdeptcd(rs.getString("COLDEPTCD"));
        Bean.setColdeptnm(rs.getString("COLDEPTNM"));
        Bean.setColdepttel(rs.getString("COLDEPTTEL"));
        Bean.setChrgusrid(rs.getString("CHRGUSRID"));
        Bean.setChrgusrnm(rs.getString("CHRGUSRNM"));
        Bean.setSummary(rs.getString("SUMMARY"));
        Bean.setExhibit(rs.getString("EXHIBIT"));
        Bean.setOpentype(rs.getString("OPENTYPE"));
        Bean.setRange(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 1));
        Bean.setRangedetail(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 2));
        Bean.setImgpreview(rs.getString("IMGPREVIEW"));
        Bean.setDuplicheck(rs.getString("DUPLICHECK"));
        Bean.setLimitcount(rs.getInt("LIMITCOUNT"));
        Bean.setGroupyn(rs.getString("GROUPYN"));
        Bean.setTgtdeptnm(rs.getString("TGTDEPTNM"));
        Bean.setHeadcont(rs.getString("HEADCONT"));
        Bean.setTailcont(rs.getString("TAILCONT"));
        Bean.setTelno(rs.getString("DEPT_TEL"));
        Bean.setAnscount(rs.getInt("ANSCOUNT"));
        Bean.setOthertgtnm(rs.getString("OTHERTGTNM"));
        Bean.setLimit1(rs.getString("LIMIT1"));
        Bean.setLimit2(rs.getString("LIMIT2"));
        Bean.setRchtarget1(rs.getString("RCHTARGET1"));
        Bean.setRchtarget2(rs.getString("RCHTARGET2"));
      }
    }
    catch (SQLException e) {
      logger.error("ERROR", e);
      ConnectionManager.close(conn, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(conn, pstmt, rs);
    }
    return Bean;
  }

  public ResearchBean getRchGrpMst(int rchgrpno, String sessionId)
    throws Exception
  {
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    ResearchBean Bean = null;

    StringBuffer selectQuery = new StringBuffer();

    if (rchgrpno == 0) {
      selectQuery.append("\n SELECT SESSIONID, TITLE, STRDT, SUBSTR(A.STRDT,1,4)||'년 '||SUBSTR(A.STRDT,6,2)||'월 '||SUBSTR(A.STRDT,9,2)||'일' STRYMD, ");
      selectQuery.append("\n        ENDDT, SUBSTR(A.ENDDT,1,4)||'년 '||SUBSTR(A.ENDDT,6,2)||'월 '||SUBSTR(A.ENDDT,9,2)||'일' ENDYMD, ");
      selectQuery.append("\n        COLDEPTCD, COLDEPTNM, COLDEPTTEL, CHRGUSRID, CHRGUSRNM, SUMMARY, EXHIBIT, OPENTYPE, RANGE, IMGPREVIEW, ");
      selectQuery.append("\n        DUPLICHECK, LIMITCOUNT, GROUPYN, RCHNOLIST, TGTDEPTNM, HEADCONT, TAILCONT, ANSCOUNT, ");
      selectQuery.append("\n        (SELECT DEPT_TEL FROM DEPT WHERE DEPT_ID = A.COLDEPTCD) DEPT_TEL, ");
      selectQuery.append("\n        (SELECT SUBSTR(MAX(SYS_CONNECT_BY_PATH(TARGET, ', ')), 3) ");
      selectQuery.append("\n         FROM (SELECT ROWNUM RN, TARGET ");
      selectQuery.append("\n               FROM (SELECT TGTNAME || '(' || TGTCODE || ')' TARGET ");
      selectQuery.append("\n                     FROM RCHGRPOTHERTARGET_TEMP ");
      selectQuery.append("\n                     WHERE SESSIONID LIKE ? ");
      selectQuery.append("\n                     AND TGTGBN = '1' ");
      selectQuery.append("\n                     ORDER BY TGTCODE)) ");
      selectQuery.append("\n         START WITH RN = 1 ");
      selectQuery.append("\n         CONNECT BY PRIOR RN = RN - 1) OTHERTGTNM ");
      selectQuery.append("\n   FROM RCHGRPMST_TEMP A\t\t\t\t");
      selectQuery.append("\n  WHERE SESSIONID LIKE ? \t\t");
    } else {
      selectQuery.append("\n SELECT RCHGRPNO, TITLE, STRDT, SUBSTR(A.STRDT,1,4)||'년 '||SUBSTR(A.STRDT,6,2)||'월 '||SUBSTR(A.STRDT,9,2)||'일' STRYMD, ");
      selectQuery.append("\n        ENDDT, SUBSTR(A.ENDDT,1,4)||'년 '||SUBSTR(A.ENDDT,6,2)||'월 '||SUBSTR(A.ENDDT,9,2)||'일' ENDYMD, ");
      selectQuery.append("\n        COLDEPTCD, COLDEPTNM, COLDEPTTEL, CHRGUSRID, CHRGUSRNM, SUMMARY, EXHIBIT, OPENTYPE, RANGE, IMGPREVIEW, ");
      selectQuery.append("\n        DUPLICHECK, LIMITCOUNT, GROUPYN, RCHNOLIST, TGTDEPTNM, HEADCONT, TAILCONT, ANSCOUNT, ");
      selectQuery.append("\n        (SELECT DEPT_TEL FROM DEPT WHERE DEPT_ID = A.COLDEPTCD) DEPT_TEL, ");
      selectQuery.append("\n        (SELECT SUBSTR(MAX(SYS_CONNECT_BY_PATH(TARGET, ', ')), 3) ");
      selectQuery.append("\n         FROM (SELECT ROWNUM RN, TARGET ");
      selectQuery.append("\n               FROM (SELECT TGTNAME || '(' || TGTCODE || ')' TARGET ");
      selectQuery.append("\n                     FROM RCHGRPOTHERTARGET ");
      selectQuery.append("\n                     WHERE RCHGRPNO = ? ");
      selectQuery.append("\n                     AND TGTGBN = '1' ");
      selectQuery.append("\n                     ORDER BY TGTCODE)) ");
      selectQuery.append("\n         START WITH RN = 1 ");
      selectQuery.append("\n         CONNECT BY PRIOR RN = RN - 1) OTHERTGTNM ");
      selectQuery.append("\n   FROM RCHGRPMST A\t\t\t\t");
      selectQuery.append("\n  WHERE RCHGRPNO = ? \t\t");
    }
    try
    {
      conn = ConnectionManager.getConnection();

      pstmt = conn.prepareStatement(selectQuery.toString());

      if (rchgrpno == 0) {
        pstmt.setString(1, sessionId);
        pstmt.setString(2, sessionId);
      } else {
        pstmt.setInt(1, rchgrpno);
        pstmt.setInt(2, rchgrpno);
      }

      rs = pstmt.executeQuery();

      if (rs.next()) {
        Bean = new ResearchBean();
        if (rchgrpno == 0) {
          Bean.setRchgrpno(rchgrpno);
          Bean.setSessionId(rs.getString("SESSIONID"));
        } else {
          Bean.setRchgrpno(rs.getInt("RCHGRPNO"));
          Bean.setSessionId(sessionId);
        }

        Bean.setTitle(rs.getString("TITLE"));
        Bean.setStrdt(rs.getString("STRDT"));
        Bean.setStrymd(rs.getString("STRYMD"));
        Bean.setEnddt(rs.getString("ENDDT"));
        Bean.setEndymd(rs.getString("ENDYMD"));
        Bean.setColdeptcd(rs.getString("COLDEPTCD"));
        Bean.setColdeptnm(rs.getString("COLDEPTNM"));
        Bean.setColdepttel(rs.getString("COLDEPTTEL"));
        Bean.setChrgusrid(rs.getString("CHRGUSRID"));
        Bean.setChrgusrnm(rs.getString("CHRGUSRNM"));
        Bean.setSummary(rs.getString("SUMMARY"));
        Bean.setExhibit(rs.getString("EXHIBIT"));
        Bean.setOpentype(rs.getString("OPENTYPE"));
        Bean.setRange(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 1));
        Bean.setRangedetail(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 2));
        Bean.setImgpreview(rs.getString("IMGPREVIEW"));
        Bean.setDuplicheck(rs.getString("DUPLICHECK"));
        Bean.setLimitcount(rs.getInt("LIMITCOUNT"));
        Bean.setGroupyn(rs.getString("GROUPYN"));
        Bean.setRchnolist(rs.getString("RCHNOLIST"));
        Bean.setTgtdeptnm(rs.getString("TGTDEPTNM"));
        Bean.setHeadcont(rs.getString("HEADCONT"));
        Bean.setTailcont(rs.getString("TAILCONT"));
        Bean.setTelno(rs.getString("DEPT_TEL"));
        Bean.setAnscount(rs.getInt("ANSCOUNT"));
        Bean.setOthertgtnm(rs.getString("OTHERTGTNM"));
        
        List rchGrpList = commapprovalManager.instance().getResearchGrpList(rchgrpno, sessionId);
        String title = "";
        if (rchGrpList.size() > 1) {
          title = ((commapprovalBean)rchGrpList.get(0)).getTgtname();
          title = "[" + title + "] 설문조사 외 " + (rchGrpList.size() - 1) + "종";
        } else if (rchGrpList.size() == 1) {
          title = ((commapprovalBean)rchGrpList.get(0)).getTgtname();
        }
        Bean.setRchname(title);
      }
    }
    catch (SQLException e) {
      logger.error("ERROR", e);
      ConnectionManager.close(conn, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(conn, pstmt, rs);
    }
    return Bean;
  }

  public List getRchSubList(int rchno, String sessionId, int examcount, String mode)
    throws Exception
  {
	  //logger.error("getRchSubList");
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List rchSubList = null;
    StringBuffer selectQuery = new StringBuffer();
    if (rchno == 0) {
      selectQuery.append("\n SELECT A.FORMSEQ, FORMGRP, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD, NVL(REQUIRE , 'Y') AS REQUIRE ");
      selectQuery.append("\n   FROM RCHSUB_TEMP A, RCHSUBFILE_TEMP B ");
      selectQuery.append("\n  WHERE A.SESSIONID = B.SESSIONID(+) ");
      selectQuery.append("\n    AND A.FORMSEQ = B.FORMSEQ(+) ");
      selectQuery.append("\n    AND A.SESSIONID LIKE ? \t\t");
      selectQuery.append("\n  ORDER BY FORMSEQ \t\t\t");
    } else {
      selectQuery.append("\n SELECT A.FORMSEQ, FORMGRP, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD, NVL(REQUIRE , 'Y') AS REQUIRE ");
      selectQuery.append("\n   FROM RCHSUB A, RCHSUBFILE B ");
      selectQuery.append("\n  WHERE A.RCHNO = B.RCHNO(+) ");
      selectQuery.append("\n    AND A.FORMSEQ = B.FORMSEQ(+) ");
      selectQuery.append("\n    AND A.RCHNO = ? \t\t\t");
      selectQuery.append("\n  ORDER BY FORMSEQ \t\t\t");
    }
    //logger.info(selectQuery);
    try
    {
      con = ConnectionManager.getConnection();

      pstmt = con.prepareStatement(selectQuery.toString());

      if (rchno == 0){
        pstmt.setString(1, sessionId);
        //logger.info("1 : "+sessionId);
      }else {
        pstmt.setInt(1, rchno);
        //logger.info("1 : "+rchno);
      }

      rs = pstmt.executeQuery();

      rchSubList = new ArrayList();

      while (rs.next()) {
        ResearchSubBean Bean = new ResearchSubBean();
        int formseq = rs.getInt("FORMSEQ");

        Bean.setFormseq(rs.getInt("FORMSEQ"));
        Bean.setFormgrp(rs.getString("FORMGRP"));
        Bean.setFormtitle(rs.getString("FORMTITLE"));
        Bean.setFormtype(rs.getString("FORMTYPE"));
        Bean.setSecurity(rs.getString("SECURITY"));
        Bean.setExamtype(rs.getString("EXAMTYPE"));
        Bean.setFileseq(rs.getInt("FILESEQ"));
        Bean.setFilenm(rs.getString("FILENM"));
        Bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
        Bean.setFilesize(rs.getInt("FILESIZE"));
        Bean.setExt(rs.getString("EXT"));
        Bean.setOrd(rs.getInt("ORD"));
        Bean.setRequire(rs.getString("REQUIRE"));
        Bean.setExamList(rchExamList(rchno, sessionId, formseq, examcount, mode));

        rchSubList.add(Bean);
      }
    }
    catch (SQLException e) {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }

    return rchSubList;
  }

  public List getResultSubList(int rchno, String range, RchSearchBean schbean, String sessionId)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List rchSubList = null;
    StringBuffer selectQuery = new StringBuffer();

    String[] sch_exam = schbean.getSch_exam();
    int cnt = 0;

    selectQuery.append("\n SELECT A.FORMSEQ, FORMGRP, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD, NVL(REQUIRE , 'Y') AS REQUIRE ");
    selectQuery.append("\n   FROM RCHSUB A, RCHSUBFILE B ");
    selectQuery.append("\n  WHERE A.RCHNO = B.RCHNO(+) ");
    selectQuery.append("\n    AND A.FORMSEQ = B.FORMSEQ(+) ");
    selectQuery.append("\n    AND A.RCHNO = ? \t\t\t");
    selectQuery.append("\n  ORDER BY FORMSEQ \t\t\t");
    try
    {
      con = ConnectionManager.getConnection();

      pstmt = con.prepareStatement(selectQuery.toString());

      pstmt.setInt(1, rchno);

      rs = pstmt.executeQuery();

      rchSubList = new ArrayList();

      while (rs.next()) {
        ResearchSubBean Bean = new ResearchSubBean();
        int formseq = rs.getInt("FORMSEQ");

        Bean.setFormseq(rs.getInt("FORMSEQ"));
        Bean.setFormgrp(rs.getString("FORMGRP"));
        Bean.setFormtitle(rs.getString("FORMTITLE"));
        Bean.setFormtype(rs.getString("FORMTYPE"));
        Bean.setSecurity(rs.getString("SECURITY"));
        Bean.setExamtype(rs.getString("EXAMTYPE"));
        if (("01".equals(rs.getString("FORMTYPE"))) || ("02".equals(rs.getString("FORMTYPE")))) {
          if (sch_exam != null) {
            for (int i = 0; i < sch_exam.length; i++)
              if (cnt == i) {
                Bean.setSch_exam(sch_exam[i]);
                break;
              }
          }
          else {
            Bean.setSch_exam("%");
          }
          cnt++;
          Bean.setExamList(getResultExamList(rchno, range, schbean, formseq));
          if ("Y".equals(rs.getString("EXAMTYPE")))
            Bean.setOtherList(rchOtherList(rchno, range, schbean, formseq));
        }
        else {
          Bean.setExamList(rchAnsList(rchno, range, schbean, formseq));
        }
        Bean.setFileseq(rs.getInt("FILESEQ"));
        Bean.setFilenm(rs.getString("FILENM"));
        Bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
        Bean.setFilesize(rs.getInt("FILESIZE"));
        Bean.setExt(rs.getString("EXT"));
        Bean.setOrd(rs.getInt("ORD"));
        Bean.setRequire(rs.getString("REQUIRE"));

        rchSubList.add(Bean);
      }
    }
    catch (SQLException e) {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }

    return rchSubList;
  }

  public List getResultExamList(int rchno, String range, RchSearchBean schbean, int formseq)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List rchExamList = null;
    String[] schexam = (String[])null;
    String inexamans = "";
    if (schbean.getSch_exam() != null)
    {
      schexam = schbean.getSch_exam();

      for (int i = 0; i < schexam.length; i++) {
        if ("".equals(inexamans)) {
          if (!"%".equals(schexam[i])) {
            inexamans = schexam[i];
          }
        }
        else if (!"%".equals(schexam[i])) {
          inexamans = inexamans + "," + schexam[i];
        }
      }

    }

    StringBuffer selectQuery = new StringBuffer();

    selectQuery.append("SELECT RA.RCHNO, RA.FORMSEQ, RE.EXAMSEQ, RE.EXAMCONT, \n");
    if (inexamans.equals("")) {
      selectQuery.append("       SUM(CASE WHEN INSTR(',' || REPLACE(RA.ANSCONT, ',', ',,') || ',', ',' || RE.EXAMSEQ || ',') > 0 THEN 1 ELSE 0 END) CNT \n");
    } else {
      String[] selectExam = inexamans.split("[,]");
      String prevFormNumber = "";

      selectQuery.append("       SUM(CASE WHEN INSTR(',' || REPLACE(RA.ANSCONT, ',', ',,') || ',', ',' || RE.EXAMSEQ || ',') > 0 THEN \n");
      for (int i = 0; i < selectExam.length; i++) {
        int div = 0;
        if (Integer.parseInt("0" + prevFormNumber) > 9) div = 1;
        int selectExamLength = selectExam[i].length();
        String formNumber = selectExam[i].substring(0, selectExamLength / 2 + div);
        String examNumber = selectExam[i].substring(selectExamLength / 2 + div);
        prevFormNumber = formNumber;

        selectQuery.append("       CASE WHEN RA.ANSUSRSEQ IN (SELECT ANSUSRSEQ \n");
        selectQuery.append("                                 FROM RCHANS \n");
        selectQuery.append("                                 WHERE RCHNO = RA.RCHNO \n");
        selectQuery.append("                                 AND FORMSEQ = " + formNumber + " \n");
        if (examNumber.equals("0"))
          selectQuery.append("                                 AND OTHER IS NOT NULL) THEN \n");
        else {
          selectQuery.append("                                 AND INSTR(',' || REPLACE(ANSCONT, ',', ',,') || ',', '," + examNumber + ",') > 0) THEN \n");
        }
      }
      selectQuery.append("1 \n");
      for (int i = 0; i < selectExam.length; i++) {
        selectQuery.append("END \n");
      }
      selectQuery.append("ELSE 0 END) CNT \n");
    }

    if (range.equals("2")) {
      selectQuery.append("FROM RCHANS RA, RCHEXAM RE \n");
      selectQuery.append("WHERE RA.RCHNO = RE.RCHNO \n");
      selectQuery.append("AND RA.FORMSEQ = RE.FORMSEQ \n");
      selectQuery.append("AND RA.RCHNO = ? \n");
      selectQuery.append("AND RA.FORMSEQ = ? \n");
    }
    else
    {
      selectQuery.append("FROM RCHANS RA, RCHEXAM RE, USR U \n");
      selectQuery.append("WHERE RA.RCHNO = RE.RCHNO \n");
      selectQuery.append("AND RA.FORMSEQ = RE.FORMSEQ \n");
      if (("%".equals(schbean.getSch_deptcd())) && ("%".equals(schbean.getSch_sex())) && ("".equals(schbean.getSch_age())))
        selectQuery.append("AND RA.CRTUSRID = U.USER_ID(+) \n");
      else {
        selectQuery.append("AND RA.CRTUSRID = U.USER_ID \n");
      }
      selectQuery.append("AND RA.RCHNO = ? \n");
      selectQuery.append("AND RA.FORMSEQ = ? \n");
      selectQuery.append("AND NVL(U.DEPT_ID, ' ') LIKE ? \n");
      selectQuery.append("AND NVL(U.SEX, 'M') LIKE ? \n");
      if (!schbean.getSch_age().equals("")) {
        selectQuery.append("AND NVL(U.AGE, 20) = ? \n");
      }
    }

    if (schbean.getSch_ansusrseq() > 0) {
      selectQuery.append("AND RA.ANSUSRSEQ = " + schbean.getSch_ansusrseq() + "  \n");
    }

    selectQuery.append("GROUP BY RA.RCHNO, RA.FORMSEQ, RE.EXAMSEQ, RE.EXAMCONT \n");
    selectQuery.append("ORDER BY RA.RCHNO, RA.FORMSEQ, RE.EXAMSEQ \n");
    try
    {
      con = ConnectionManager.getConnection();
      pstmt = con.prepareStatement(selectQuery.toString());

      if (range.equals("2")) {
        pstmt.setInt(1, rchno);
        pstmt.setInt(2, formseq);
      } else {
        pstmt.setInt(1, rchno);
        pstmt.setInt(2, formseq);

        pstmt.setString(3, schbean.getSch_deptcd());
        pstmt.setString(4, schbean.getSch_sex());

        if (!schbean.getSch_age().equals("")) {
          pstmt.setInt(5, Integer.parseInt(schbean.getSch_age().toString()));
        }
      }

      rs = pstmt.executeQuery();

      rchExamList = new ArrayList();
      while (rs.next()) {
        ResearchExamBean bean = new ResearchExamBean();

        bean.setFormseq(rs.getInt("FORMSEQ"));
        bean.setExamseq(rs.getInt("EXAMSEQ"));
        bean.setExamcont(rs.getString("EXAMCONT"));
        bean.setExamcnt(rs.getInt("CNT"));

        ResearchExamBean rchExamBean = getRchExamFile(con, "", rchno, bean.getFormseq(), bean.getExamseq());
        if (rchExamBean != null) {
          bean.setFileseq(rchExamBean.getFileseq());
          bean.setFilenm(rchExamBean.getFilenm());
          bean.setOriginfilenm(rchExamBean.getOriginfilenm());
          bean.setFilesize(rchExamBean.getFilesize());
          bean.setExt(rchExamBean.getExt());
          bean.setOrd(rchExamBean.getOrd());
        }

        rchExamList.add(bean);
      }
    }
    catch (SQLException e) {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }

    return rchExamList;
  }

  public List rchOtherList(int rchno, String range, RchSearchBean schbean, int formseq)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    List rchOtherList = null;
    String[] schexam = (String[])null;
    String inexamans = "";
    if (schbean.getSch_exam() != null)
    {
      schexam = schbean.getSch_exam();

      for (int i = 0; i < schexam.length; i++) {
        if ("".equals(inexamans)) {
          if (!"%".equals(schexam[i])) {
            inexamans = schexam[i];
          }
        }
        else if (!"%".equals(schexam[i])) {
          inexamans = inexamans + "," + schexam[i];
        }
      }

    }

    StringBuffer selectQuery = new StringBuffer();

    selectQuery.append("SELECT DISTINCT RA.RCHNO, RA.ANSUSRSEQ, RA.FORMSEQ, RA.OTHER ");

    if (range.equals("2")) {
      selectQuery.append("FROM RCHANS RA, RCHEXAM RE ");
      selectQuery.append("WHERE RA.RCHNO = RE.RCHNO ");
      selectQuery.append("AND RA.FORMSEQ = RE.FORMSEQ ");
      selectQuery.append("AND RA.RCHNO = ? ");
      selectQuery.append("AND RA.FORMSEQ = ? ");
      selectQuery.append("AND RA.OTHER IS NOT NULL ");
    } else {
      selectQuery.append("FROM RCHANS RA, RCHEXAM RE, USR U ");
      selectQuery.append("WHERE RA.RCHNO = RE.RCHNO ");
      selectQuery.append("AND RA.FORMSEQ = RE.FORMSEQ ");
      selectQuery.append("AND RA.OTHER IS NOT NULL ");
      if (("%".equals(schbean.getSch_deptcd())) && ("%".equals(schbean.getSch_sex())) && ("".equals(schbean.getSch_age())))
        selectQuery.append("AND RA.CRTUSRID = U.USER_ID(+) ");
      else {
        selectQuery.append("AND RA.CRTUSRID = U.USER_ID ");
      }
      selectQuery.append("AND RA.RCHNO = ? ");
      selectQuery.append("AND RA.FORMSEQ = ? ");
      selectQuery.append("AND NVL(U.DEPT_ID, ' ') LIKE ? ");
      selectQuery.append("AND NVL(U.SEX, 'M') LIKE ? ");
      if (!schbean.getSch_age().equals("")) {
        selectQuery.append("AND NVL(U.AGE, 20) = ? ");
      }
    }
    if (!inexamans.equals("")) {
      String[] selectExam = inexamans.split("[,]");
      String prevFormNumber = "";

      for (int i = 0; i < selectExam.length; i++) {
        int div = 0;
        if (Integer.parseInt("0" + prevFormNumber) > 9) div = 1;
        int selectExamLength = selectExam[i].length();
        String formNumber = selectExam[i].substring(0, selectExamLength / 2 + div);
        String examNumber = selectExam[i].substring(selectExamLength / 2 + div);
        prevFormNumber = formNumber;

        selectQuery.append("AND RA.ANSUSRSEQ IN (SELECT ANSUSRSEQ ");
        selectQuery.append("                    FROM RCHANS ");
        selectQuery.append("                    WHERE RCHNO = RA.RCHNO ");
        selectQuery.append("                    AND FORMSEQ = " + formNumber + " ");
        if (examNumber.equals("0"))
          selectQuery.append("                    AND OTHER IS NOT NULL) ");
        else {
          selectQuery.append("                    AND INSTR(',' || REPLACE(ANSCONT, ',', ',,') || ',', '," + examNumber + ",') > 0) ");
        }
      }
    }

    if (schbean.getSch_ansusrseq() > 0) {
      selectQuery.append("AND RA.ANSUSRSEQ = " + schbean.getSch_ansusrseq() + "  ");
    }

    selectQuery.append("ORDER BY RA.RCHNO, RA.FORMSEQ, RA.OTHER ");
    try
    {
      con = ConnectionManager.getConnection();
      pstmt = con.prepareStatement(selectQuery.toString());

      if (range.equals("2")) {
        pstmt.setInt(1, rchno);
        pstmt.setInt(2, formseq);
      } else {
        pstmt.setInt(1, rchno);
        pstmt.setInt(2, formseq);

        pstmt.setString(3, schbean.getSch_deptcd());
        pstmt.setString(4, schbean.getSch_sex());

        if (!schbean.getSch_age().equals("")) {
          pstmt.setInt(5, Integer.parseInt(schbean.getSch_age().toString()));
        }
      }

      rs = pstmt.executeQuery();

      rchOtherList = new ArrayList();
      while (rs.next()) {
        ResearchAnsBean bean = new ResearchAnsBean();

        bean.setFormseq(rs.getInt("FORMSEQ"));
        bean.setOther(rs.getString("OTHER"));

        rchOtherList.add(bean);
      }
    }
    catch (SQLException e) {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }

    return rchOtherList;
  }

  public List rchAnsList(int rchno, String range, RchSearchBean schbean, int formseq)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List rchAnsList = null;
    String[] schexam = (String[])null;
    String inexamans = "";
    if (schbean.getSch_exam() != null)
    {
      schexam = schbean.getSch_exam();

      for (int i = 0; i < schexam.length; i++) {
        if ("".equals(inexamans)) {
          if (!"%".equals(schexam[i])) {
            inexamans = schexam[i];
          }
        }
        else if (!"%".equals(schexam[i])) {
          inexamans = inexamans + "," + schexam[i];
        }
      }

    }

    StringBuffer selectQuery = new StringBuffer();

    selectQuery.append("SELECT DISTINCT RA.RCHNO, RA.ANSUSRSEQ, RA.FORMSEQ, RA.ANSCONT ");

    if (range.equals("2")) {
      selectQuery.append("FROM RCHANS RA, RCHEXAM RE ");
      selectQuery.append("WHERE RA.RCHNO = RE.RCHNO(+) ");
      selectQuery.append("AND RA.FORMSEQ = RE.FORMSEQ(+) ");
      selectQuery.append("AND RA.RCHNO = ? ");
      selectQuery.append("AND RA.FORMSEQ = ? ");
    } else {
      selectQuery.append("FROM RCHANS RA, RCHEXAM RE, USR U ");
      selectQuery.append("WHERE RA.RCHNO = RE.RCHNO(+) ");
      selectQuery.append("AND RA.FORMSEQ = RE.FORMSEQ(+) ");
      if (("%".equals(schbean.getSch_deptcd())) && ("%".equals(schbean.getSch_sex())) && ("".equals(schbean.getSch_age())))
        selectQuery.append("AND RA.CRTUSRID = U.USER_ID(+) ");
      else {
        selectQuery.append("AND RA.CRTUSRID = U.USER_ID ");
      }
      selectQuery.append("AND RA.RCHNO = ? ");
      selectQuery.append("AND RA.FORMSEQ = ? ");
      selectQuery.append("AND NVL(U.DEPT_ID, ' ') LIKE ? ");
      selectQuery.append("AND NVL(U.SEX, 'M') LIKE ? ");
      if (!schbean.getSch_age().equals("")) {
        selectQuery.append("AND NVL(U.AGE, 20) = ? ");
      }
    }
    if (!inexamans.equals("")) {
      String[] selectExam = inexamans.split("[,]");
      String prevFormNumber = "";

      for (int i = 0; i < selectExam.length; i++) {
        int div = 0;
        if (Integer.parseInt("0" + prevFormNumber) > 9) div = 1;
        int selectExamLength = selectExam[i].length();
        String formNumber = selectExam[i].substring(0, selectExamLength / 2 + div);
        String examNumber = selectExam[i].substring(selectExamLength / 2 + div);
        prevFormNumber = formNumber;

        selectQuery.append("AND RA.ANSUSRSEQ IN (SELECT ANSUSRSEQ ");
        selectQuery.append("                    FROM RCHANS ");
        selectQuery.append("                    WHERE RCHNO = RA.RCHNO ");
        selectQuery.append("                    AND FORMSEQ = " + formNumber + " ");
        if (examNumber.equals("0"))
          selectQuery.append("                    AND OTHER IS NOT NULL) ");
        else {
          selectQuery.append("                    AND INSTR(',' || REPLACE(ANSCONT, ',', ',,') || ',', '," + examNumber + ",') > 0) ");
        }
      }
    }

    if (schbean.getSch_ansusrseq() > 0) {
      selectQuery.append("AND RA.ANSUSRSEQ = " + schbean.getSch_ansusrseq() + "  ");
    }

    selectQuery.append("ORDER BY RA.RCHNO, RA.FORMSEQ, RA.ANSCONT ");
    try
    {
      con = ConnectionManager.getConnection();
      pstmt = con.prepareStatement(selectQuery.toString());

      if (range.equals("2")) {
        pstmt.setInt(1, rchno);
        pstmt.setInt(2, formseq);
      } else {
        pstmt.setInt(1, rchno);
        pstmt.setInt(2, formseq);

        pstmt.setString(3, schbean.getSch_deptcd());
        pstmt.setString(4, schbean.getSch_sex());

        if (!schbean.getSch_age().equals("")) {
          pstmt.setInt(5, Integer.parseInt(schbean.getSch_age().toString()));
        }
      }

      rs = pstmt.executeQuery();

      rchAnsList = new ArrayList();
      while (rs.next()) {
        ResearchAnsBean bean = new ResearchAnsBean();

        bean.setFormseq(rs.getInt("FORMSEQ"));
        bean.setAnscont(rs.getString("ANSCONT"));

        rchAnsList.add(bean);
      }
    }
    catch (SQLException e) {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }

    return rchAnsList;
  }

  public List rchExamList(int rchno, String sessionId, int formseq, int examcount, String mode)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List rchExamList = null;

    StringBuffer selectQuery = new StringBuffer();

    if (rchno == 0) {
      selectQuery.append("\n SELECT T1.SESSIONID, T1.FORMSEQ, T1.EXAMSEQ, EXAMCONT,  0 CNT, FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD ");
      selectQuery.append("\n   FROM RCHEXAM_TEMP T1, RCHEXAMFILE_TEMP T2 ");
      selectQuery.append("\n  WHERE T1.SESSIONID = T2.SESSIONID(+) ");
      selectQuery.append("\n    AND T1.FORMSEQ = T2.FORMSEQ(+) ");
      selectQuery.append("\n    AND T1.EXAMSEQ = T2.EXAMSEQ(+) ");
      selectQuery.append("\n    AND T1.SESSIONID LIKE ? \t");
      selectQuery.append("\n    AND T1.FORMSEQ = ? \t");
      selectQuery.append("\n ORDER BY EXAMSEQ\t\t\t");
    }
    else {
      selectQuery.append("\n SELECT T1.RCHNO, T1.FORMSEQ, T1.EXAMSEQ, T1.EXAMCONT, ");
      selectQuery.append("\n \t      (SELECT COUNT(DISTINCT ANSUSRSEQ) FROM RCHANS WHERE T1.RCHNO = RCHNO AND T1.FORMSEQ = FORMSEQ AND ANSCONT LIKE '%'||T1.EXAMSEQ||'%' ) CNT, ");
      selectQuery.append("\n        FILESEQ, FILENM, ORIGINFILENM, FILESIZE, EXT, ORD ");
      selectQuery.append("\n   FROM RCHEXAM T1, RCHEXAMFILE T2 ");
      selectQuery.append("\n  WHERE T1.RCHNO = T2.RCHNO(+) ");
      selectQuery.append("\n    AND T1.FORMSEQ = T2.FORMSEQ(+) ");
      selectQuery.append("\n    AND T1.EXAMSEQ = T2.EXAMSEQ(+) ");
      selectQuery.append("\n    AND T1.RCHNO = ? \t");
      selectQuery.append("\n    AND T1.FORMSEQ = ? ");
      selectQuery.append("\n ORDER BY EXAMSEQ\t\t\t");
    }

    try
    {
      con = ConnectionManager.getConnection();
      pstmt = con.prepareStatement(selectQuery.toString());

      if (rchno == 0) {
        pstmt.setString(1, sessionId);
        pstmt.setInt(2, formseq);
      } else {
        pstmt.setInt(1, rchno);
        pstmt.setInt(2, formseq);
      }

      rs = pstmt.executeQuery();

      rchExamList = new ArrayList();
      int cnt = 0;
      while (rs.next()) {
        ResearchExamBean bean = new ResearchExamBean();

        bean.setFormseq(rs.getInt("FORMSEQ"));
        bean.setExamseq(rs.getInt("EXAMSEQ"));
        bean.setExamcont(rs.getString("EXAMCONT"));
        bean.setExamcnt(rs.getInt("CNT"));
        bean.setFileseq(rs.getInt("FILESEQ"));
        bean.setFilenm(rs.getString("FILENM"));
        bean.setOriginfilenm(rs.getString("ORIGINFILENM"));
        bean.setFilesize(rs.getInt("FILESIZE"));
        bean.setExt(rs.getString("EXT"));
        bean.setOrd(rs.getInt("ORD"));

        rchExamList.add(bean);
        cnt++;
      }

      if ("".equals(mode))
      {
        for (int i = 0; i < examcount - cnt; i++) {
          ResearchExamBean bean = new ResearchExamBean();
          rchExamList.add(bean);
        }
      }
    }
    catch (SQLException e) {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }

    return rchExamList;
  }

  public int rchAnsCnt(int rchno, int formseq, int examseq)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int result = 0;

    StringBuffer selectQuery = new StringBuffer();

    selectQuery.append("\n SELECT COUNT(DISTINCT ANSUSRSEQ) ");
    selectQuery.append("\n   FROM RCHANS\t\t");
    selectQuery.append("\n  WHERE RCHNO = ? \t");
    selectQuery.append("\n    AND FORMSEQ = ? \t");
    selectQuery.append("\n    AND ANSCONT LIKE '%'||?||'%' ");
    try
    {
      con = ConnectionManager.getConnection();
      pstmt = con.prepareStatement(selectQuery.toString());

      pstmt.setInt(1, rchno);
      pstmt.setInt(2, formseq);
      pstmt.setInt(3, examseq);

      rs = pstmt.executeQuery();

      if (rs.next())
        result = rs.getInt(1);
    }
    catch (SQLException e)
    {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }

    return result;
  }

  public int rchAllSave(ResearchBean Bean, ResearchForm rchForm, ServletContext context, String saveDir)
    throws Exception
  {
	
	  
    Connection con = null;
    int result = 0;
    try
    {
      con = ConnectionManager.getConnection(false);

      if ((checkCnt(Bean.getSessionId())) || (Bean.getRchno() > 0))
      {
        result = rchUptMst(con, Bean, 0);

        result += rchInsSub(con, Bean.getListrch(), Bean.getSessionId(), Bean.getRchno());

        File saveFolder = new File(context.getRealPath(saveDir));
        if (!saveFolder.exists()) saveFolder.mkdirs();

        String[] formattitleFileYN = Bean.getFormtitleFileYN();
        String[] examcontFileYN = Bean.getExamcontFileYN();
        String[] formtitle = Bean.getFormtitle();
        String[] examcont = Bean.getExamcont();
        int examcount = Bean.getExamcount();

        int i = 0;
        do { ResearchSubBean rchSubBean = getRchSubFile(con, Bean.getSessionId(), Bean.getRchno(), i + 1);

          if ((rchSubBean != null) && (
            (formattitleFileYN[i] == null) || (
            (Bean.getFormtitleFile()[i] != null) && (!Bean.getFormtitleFile()[i].getFileName().equals(""))))) {
            delRchSubFile(con, Bean.getSessionId(), Bean.getRchno(), i + 1, 1);

            String delFile = rchSubBean.getFilenm();
            if ((delFile != null) && (!delFile.trim().equals(""))) {
              FileManager.doFileDelete(context.getRealPath(delFile));
            }
          }

          if ((Bean.getFormtitleFile()[i] != null) && (!Bean.getFormtitleFile()[i].getFileName().equals(""))) {
            FileBean subFileBean = FileManager.doFileUpload(Bean.getFormtitleFile()[i], context, saveDir);

            if (subFileBean != null) {
              int addResult = 0;
              subFileBean.setFileseq(1);
              addResult = addRchSubFile(con, Bean.getSessionId(), Bean.getRchno(), i + 1, subFileBean);
              if (addResult == 0)
                throw new Exception("첨부실패:DB업데이트");
            }
            else {
              throw new Exception("첨부실패:파일업로드");
            }
          }

          List subList = null;
          subList = getRchSubList(rchForm.getRchno(), rchForm.getSessionId(), examcount, "");
          int subcount = subList.size();

          int prevExamcount = 0;
          while ((prevExamcount < rchForm.getExamcontFile().length) && 
            (rchForm.getExamcontFile()[prevExamcount] != null)) {
            prevExamcount++;
          }
          prevExamcount /= subcount;

          if (examcount >= prevExamcount) {
            for (int j = 0; (examcont != null) && (j < prevExamcount); j++) {
              ResearchExamBean rchExamBean = getRchExamFile(con, Bean.getSessionId(), Bean.getRchno(), i + 1, j + 1);
              try {
                if ((rchExamBean != null) && (
                  (examcont[(i * prevExamcount + j)].trim().equals("")) || (examcontFileYN[(i * prevExamcount + j)] == null) || (
                  (Bean.getExamcontFile()[(i * prevExamcount + j)] != null) && (!Bean.getExamcontFile()[(i * prevExamcount + j)].getFileName().equals(""))))) {
                  delRchExamFile(con, Bean.getSessionId(), Bean.getRchno(), i + 1, j + 1, 1);

                  String delFile = rchExamBean.getFilenm();
                  if ((delFile != null) && (!delFile.trim().equals("")))
                    FileManager.doFileDelete(context.getRealPath(delFile));
                }
              }
              catch (Exception localException1) {
              }
              try {
                if ((Bean.getExamcontFile()[(i * prevExamcount + j)] != null) && (!Bean.getExamcontFile()[(i * prevExamcount + j)].getFileName().equals(""))) {
                  FileBean examFileBean = FileManager.doFileUpload(Bean.getExamcontFile()[(i * prevExamcount + j)], context, saveDir);

                  if (examFileBean != null) {
                    int addResult = 0;
                    examFileBean.setFileseq(1);
                    addResult = addRchExamFile(con, Bean.getSessionId(), Bean.getRchno(), i + 1, j + 1, examFileBean);
                    if (addResult == 0)
                      throw new Exception("첨부실패:DB업데이트");
                  }
                  else {
                    throw new Exception("첨부실패:파일업로드");
                  }
                }
              } catch (Exception localException2) {
              }
            }
            setOrderRchExamFile(con, Bean.getSessionId(), Bean.getRchno(), i + 1);
          }

          List rchExamUnusedList = getRchExamUnusedFile(con, Bean.getSessionId(), Bean.getRchno(), i + 1);

          for (int k = 0; (rchExamUnusedList != null) && (k < rchExamUnusedList.size()); k++) {
            ResearchExamBean rchExamBean = (ResearchExamBean)rchExamUnusedList.get(k);

            if (rchExamBean != null) {
              delRchExamFile(con, Bean.getSessionId(), Bean.getRchno(), i + 1, rchExamBean.getExamseq(), rchExamBean.getFileseq());

              String delFile = rchExamBean.getFilenm();
              if ((delFile != null) && (!delFile.trim().equals("")))
                FileManager.doFileDelete(context.getRealPath(delFile));
            }
          }
          i++; if (formtitle == null) break;  } while (i < formtitle.length);
      }
      else
      {
        result = rchInsMst(con, Bean);
      }
      con.commit();
    } catch (Exception e) {
      con.rollback();
      logger.error("ERROR", e);
      ConnectionManager.close(con);
      throw e;
    } finally {
      con.setAutoCommit(true);
      ConnectionManager.close(con);
    }
    return result;
  }

  public int rchGrpAllSave(ResearchBean Bean, ResearchForm rchForm)
    throws Exception
  {
    Connection con = null;
    int result = 0;
    try
    {
      con = ConnectionManager.getConnection(false);

      if ((checkCntGrp(Bean.getSessionId())) || (Bean.getRchgrpno() > 0))
      {
        result = rchGrpUptMst(con, Bean, 0);
      }
      else {
        result = rchGrpInsMst(con, Bean);
      }
      con.commit();
    } catch (Exception e) {
      con.rollback();
      logger.error("ERROR", e);
      ConnectionManager.close(con);
      throw e;
    } finally {
      con.setAutoCommit(true);
      ConnectionManager.close(con);
    }
    return result;
  }

  public int rchInsComp(String sessionId, ResearchBean Bean, ServletContext context, String saveDir)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int rchno = 0;
    try
    {
      con = ConnectionManager.getConnection();
      con.setAutoCommit(false);

      if (Bean.getMdrchno() == 0)
      {
        StringBuffer selectQuery1 = new StringBuffer();
        selectQuery1.append("SELECT RCHSEQ.NEXTVAL FROM DUAL ");

        pstmt = con.prepareStatement(selectQuery1.toString());

        rs = pstmt.executeQuery();

        if (rs.next()) {
          rchno = rs.getInt(1);
        }
        if (pstmt != null)
          try {
            rs.close();
            pstmt.close();
            pstmt = null;
          }
          catch (SQLException localSQLException)
          {
          }
        StringBuffer insertQuery2 = new StringBuffer();
        insertQuery2.append("\n INSERT INTO RCHMST ");
        insertQuery2.append("\n SELECT " + rchno + ", TITLE, STRDT, ENDDT, COLDEPTCD, ");
        insertQuery2.append("\n        COLDEPTNM, COLDEPTTEL, CHRGUSRID, CHRGUSRNM, SUMMARY, EXHIBIT, OPENTYPE, RANGE, IMGPREVIEW, DUPLICHECK, LIMITCOUNT, ");
        insertQuery2.append("\n        GROUPYN, TGTDEPTNM, HEADCONT, TAILCONT, ANSCOUNT, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), CRTUSRID, '', '' , OTHERTGTNM , LIMIT1, LIMIT2, RCHTARGET1, RCHTARGET2");
        insertQuery2.append("\n   FROM RCHMST_TEMP ");
        insertQuery2.append("\n  WHERE SESSIONID LIKE ? ");
        pstmt = con.prepareStatement(insertQuery2.toString());
        pstmt.setString(1, sessionId);
        
        pstmt.executeUpdate();
        if (pstmt != null)
          try {
            pstmt.close();
            pstmt = null;
          }
          catch (SQLException localSQLException1)
          {
          }
        StringBuffer selectQuery3 = new StringBuffer();
        selectQuery3.append("\n INSERT INTO RCHSUB ");
        selectQuery3.append("\n SELECT " + rchno + ", FORMSEQ, FORMGRP, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE, REQUIRE ");
        selectQuery3.append("\n   FROM RCHSUB_TEMP ");
        selectQuery3.append("\n  WHERE SESSIONID LIKE ? ");
        
        pstmt = con.prepareStatement(selectQuery3.toString());
        pstmt.setString(1, sessionId);

        pstmt.executeUpdate();
        if (pstmt != null)
          try {
            pstmt.close();
            pstmt = null;
          }
          catch (SQLException localSQLException2)
          {
          }
        StringBuffer selectQuery4 = new StringBuffer();
        selectQuery4.append("\n INSERT INTO RCHEXAM ");
        selectQuery4.append("\n SELECT " + rchno + ", FORMSEQ, EXAMSEQ, EXAMCONT ");
        selectQuery4.append("\n   FROM RCHEXAM_TEMP ");
        selectQuery4.append("\n  WHERE SESSIONID LIKE ? ");
        pstmt = con.prepareStatement(selectQuery4.toString());
        pstmt.setString(1, sessionId);
        
        pstmt.executeUpdate();
        if (pstmt != null)
          try {
            pstmt.close();
            pstmt = null;
          } catch (SQLException localSQLException3) {
          }
      } else {
        rchUptMst(con, Bean, Bean.getMdrchno());

        rchInsSub(con, Bean.getListrch(), Bean.getSessionId(), Bean.getMdrchno());

        StringBuffer delSubQuery = new StringBuffer();
        delSubQuery.append("\n DELETE FROM RCHDEPT \t");
        delSubQuery.append("\n  WHERE RCHNO = ? \t");
        
        pstmt = con.prepareStatement(delSubQuery.toString());
        pstmt.setInt(1, Bean.getMdrchno());

        pstmt.executeUpdate();
        if (pstmt != null)
          try {
            pstmt.close();
            pstmt = null;
          }
          catch (SQLException localSQLException4) {
          }
        StringBuffer delSubQuery1 = new StringBuffer();
        delSubQuery1.append("\n DELETE FROM RCHDEPTLIST \t");
        delSubQuery1.append("\n  WHERE RCHNO = ? \t");
        
        pstmt = con.prepareStatement(delSubQuery1.toString());
        pstmt.setInt(1, Bean.getMdrchno());

        pstmt.executeUpdate();
        if (pstmt != null)
          try {
            pstmt.close();
            pstmt = null;
          }
          catch (SQLException localSQLException5) {
          }
        StringBuffer delSubQuery2 = new StringBuffer();
        delSubQuery2.append("\n DELETE FROM RCHOTHERTARGET \t");
        delSubQuery2.append("\n  WHERE RCHNO = ? \t");
       
        pstmt = con.prepareStatement(delSubQuery2.toString());
        pstmt.setInt(1, Bean.getMdrchno());

        pstmt.executeUpdate();
        if (pstmt != null)
          try {
            pstmt.close();
            pstmt = null;
          }
          catch (SQLException localSQLException6) {
          }
        rchno = Bean.getMdrchno();
      }

      delRchSubExamAllFile(con, "", rchno, context);

      copyRchSubExamFile(con, sessionId, rchno, context, saveDir, "SAVE");

      StringBuffer selectQuery5 = new StringBuffer();
      selectQuery5.append("\n INSERT INTO RCHDEPT ");
      selectQuery5.append("\n SELECT '" + rchno + "', TGTCODE, TGTNAME, TGTGBN ");
      selectQuery5.append("\n   FROM RCHDEPT_TEMP  ");
      selectQuery5.append("\n  WHERE SESSIONID LIKE ? ");
      pstmt = con.prepareStatement(selectQuery5.toString());
      pstmt.setString(1, sessionId);
     
      pstmt.executeUpdate();
      if (pstmt != null)
        try {
          pstmt.close();
          pstmt = null;
        }
        catch (SQLException localSQLException7)
        {
        }
      StringBuffer selectQuery6 = new StringBuffer();
      selectQuery6.append("\n INSERT INTO RCHDEPTLIST ");
      selectQuery6.append("\n SELECT '" + rchno + "', SEQ, GRPCD, GRPNM, GRPGBN ");
      selectQuery6.append("\n   FROM RCHDEPTLIST_TEMP  ");
      selectQuery6.append("\n  WHERE SESSIONID LIKE ? ");
      pstmt = con.prepareStatement(selectQuery6.toString());
      pstmt.setString(1, sessionId);
     
      pstmt.executeUpdate();
      if (pstmt != null)
        try {
          pstmt.close();
          pstmt = null;
        }
        catch (SQLException localSQLException8)
        {
        }
      StringBuffer selectQuery7 = new StringBuffer();
      selectQuery7.append("\n INSERT INTO RCHOTHERTARGET ");
      selectQuery7.append("\n SELECT '" + rchno + "', TGTCODE, TGTNAME, TGTGBN ");
      selectQuery7.append("\n   FROM RCHOTHERTARGET_TEMP  ");
      selectQuery7.append("\n  WHERE SESSIONID LIKE ? ");
      pstmt = con.prepareStatement(selectQuery7.toString());
      pstmt.setString(1, sessionId);
      
      pstmt.executeUpdate();
      if (pstmt != null)
        try {
          pstmt.close();
          pstmt = null;
        }
        catch (SQLException localSQLException9) {
        }
      con.commit();
    } catch (Exception e) {
      rchno = -1;
      con.rollback();
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      con.setAutoCommit(true);
      ConnectionManager.close(con, pstmt, rs);
    }
    return rchno;
  }

  public int rchGrpInsComp(String sessionId, ResearchBean Bean)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int rchgrpno = 0;
    try
    {
      con = ConnectionManager.getConnection();
      con.setAutoCommit(false);

      if (Bean.getMdrchgrpno() == 0)
      {
        StringBuffer selectQuery1 = new StringBuffer();
        selectQuery1.append("SELECT RCHGRPSEQ.NEXTVAL FROM DUAL ");

        pstmt = con.prepareStatement(selectQuery1.toString());

        rs = pstmt.executeQuery();

        if (rs.next()) {
          rchgrpno = rs.getInt(1);
        }
        if (pstmt != null)
          try {
            rs.close();
            pstmt.close();
            pstmt = null;
          }
          catch (SQLException localSQLException)
          {
          }
        StringBuffer insertQuery2 = new StringBuffer();
        insertQuery2.append("\n INSERT INTO RCHGRPMST ");
        insertQuery2.append("\n SELECT " + rchgrpno + ", TITLE, STRDT, ENDDT, COLDEPTCD, ");
        insertQuery2.append("\n        COLDEPTNM, COLDEPTTEL, CHRGUSRID, CHRGUSRNM, SUMMARY, EXHIBIT, OPENTYPE, RANGE, IMGPREVIEW, DUPLICHECK, LIMITCOUNT, ");
        insertQuery2.append("\n        GROUPYN, RCHNOLIST, TGTDEPTNM, HEADCONT, TAILCONT, ANSCOUNT, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), CRTUSRID, '', '', ,OTHERTGTNM  ");
        insertQuery2.append("\n   FROM RCHGRPMST_TEMP ");
        insertQuery2.append("\n  WHERE SESSIONID LIKE ? ");

        pstmt = con.prepareStatement(insertQuery2.toString());
        pstmt.setString(1, sessionId);

        pstmt.executeUpdate();
        if (pstmt != null)
          try {
            pstmt.close();
            pstmt = null;
          } catch (SQLException localSQLException1) {
          }
      } else {
        rchGrpUptMst(con, Bean, Bean.getMdrchgrpno());

        StringBuffer delSubQuery = new StringBuffer();
        delSubQuery.append("\n DELETE FROM RCHGRPDEPT \t");
        delSubQuery.append("\n  WHERE RCHGRPNO = ? \t");

        pstmt = con.prepareStatement(delSubQuery.toString());
        pstmt.setInt(1, Bean.getMdrchgrpno());

        pstmt.executeUpdate();
        if (pstmt != null)
          try {
            pstmt.close();
            pstmt = null;
          }
          catch (SQLException localSQLException2) {
          }
        StringBuffer delSubQuery1 = new StringBuffer();
        delSubQuery1.append("\n DELETE FROM RCHGRPDEPTLIST \t");
        delSubQuery1.append("\n  WHERE RCHGRPNO = ? \t");

        pstmt = con.prepareStatement(delSubQuery1.toString());
        pstmt.setInt(1, Bean.getMdrchgrpno());

        pstmt.executeUpdate();
        if (pstmt != null)
          try {
            pstmt.close();
            pstmt = null;
          }
          catch (SQLException localSQLException3) {
          }
        StringBuffer delSubQuery2 = new StringBuffer();
        delSubQuery2.append("\n DELETE FROM RCHGRPOTHERTARGET \t");
        delSubQuery2.append("\n  WHERE RCHGRPNO = ? \t");

        pstmt = con.prepareStatement(delSubQuery2.toString());
        pstmt.setInt(1, Bean.getMdrchgrpno());

        pstmt.executeUpdate();
        if (pstmt != null)
          try {
            pstmt.close();
            pstmt = null;
          }
          catch (SQLException localSQLException4) {
          }
        rchgrpno = Bean.getMdrchgrpno();
      }

      StringBuffer selectQuery5 = new StringBuffer();
      selectQuery5.append("\n INSERT INTO RCHGRPDEPT ");
      selectQuery5.append("\n SELECT '" + rchgrpno + "', TGTCODE, TGTNAME, TGTGBN ");
      selectQuery5.append("\n   FROM RCHGRPDEPT_TEMP  ");
      selectQuery5.append("\n  WHERE SESSIONID LIKE ? ");
      pstmt = con.prepareStatement(selectQuery5.toString());
      pstmt.setString(1, sessionId);

      pstmt.executeUpdate();
      if (pstmt != null)
        try {
          pstmt.close();
          pstmt = null;
        }
        catch (SQLException localSQLException5)
        {
        }
      StringBuffer selectQuery6 = new StringBuffer();
      selectQuery6.append("\n INSERT INTO RCHGRPDEPTLIST ");
      selectQuery6.append("\n SELECT '" + rchgrpno + "', SEQ, GRPCD, GRPNM, GRPGBN ");
      selectQuery6.append("\n   FROM RCHGRPDEPTLIST_TEMP  ");
      selectQuery6.append("\n  WHERE SESSIONID LIKE ? ");
      pstmt = con.prepareStatement(selectQuery6.toString());
      pstmt.setString(1, sessionId);

      pstmt.executeUpdate();
      if (pstmt != null)
        try {
          pstmt.close();
          pstmt = null;
        }
        catch (SQLException localSQLException6)
        {
        }
      StringBuffer selectQuery7 = new StringBuffer();
      selectQuery7.append("\n INSERT INTO RCHGRPOTHERTARGET ");
      selectQuery7.append("\n SELECT '" + rchgrpno + "', TGTCODE, TGTNAME, TGTGBN ");
      selectQuery7.append("\n   FROM RCHGRPOTHERTARGET_TEMP  ");
      selectQuery7.append("\n  WHERE SESSIONID LIKE ? ");
      pstmt = con.prepareStatement(selectQuery7.toString());
      pstmt.setString(1, sessionId);

      pstmt.executeUpdate();
      if (pstmt != null)
        try {
          pstmt.close();
          pstmt = null;
        }
        catch (SQLException localSQLException7) {
        }
      con.commit();
    } catch (Exception e) {
      rchgrpno = -1;
      con.rollback();
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      con.setAutoCommit(true);
      ConnectionManager.close(con, pstmt, rs);
    }
    return rchgrpno;
  }

  public int rchInsMst(Connection conn, ResearchBean Bean)
    throws Exception
  {
    PreparedStatement pstmt = null;
    int result = 0;
    int bindPos = 0;
    int[] ret = (int[])null;

    String summary = null;
    String tgtdeptnm = null;
    String headcont = null;
    String tailcont = null;
    String othertgtnm = null;
    String limit1 = null;
    String limit2 = null;
    String rchtarget1 = null;
    String rchtarget2 = null;
    
    if (Bean.getSummary() != null)
      summary = Bean.getSummary().replaceAll("'", "''");
    else {
      summary = "";
    }

    if (Bean.getTgtdeptnm() != null)
      tgtdeptnm = Bean.getTgtdeptnm().replaceAll("'", "''");
    else {
      tgtdeptnm = "";
    }
    if (Bean.getOthertgtnm() != null)
        othertgtnm = Bean.getOthertgtnm().replaceAll("'", "''");
      else {
    	othertgtnm = "";
      }
   
    if (Bean.getHeadcont() != null){
      headcont = Bean.getHeadcont().replaceAll("'", "''");
      headcont = new String(headcont.getBytes("x-windows-949"), "x-windows-949");
    }else {
      headcont = "";
    }

    if (Bean.getTailcont() != null){
      tailcont = Bean.getTailcont().replaceAll("'", "''");
      tailcont = new String(tailcont.getBytes("x-windows-949"), "x-windows-949");
    }else {
      tailcont = "";
    }
 
    if(Bean.getLimit1chk().equals("on")){
    	limit1 = tgtdeptnm;
    
    }else{
    	
    	rchtarget1 = tgtdeptnm;
    }

    if(Bean.getLimit2chk().equals("on")){
    	limit2 = othertgtnm;
    
    }else{
    	
    	rchtarget2 = othertgtnm;
    }
    
    
    
    String insMstQuery = "";

    insMstQuery = "INSERT INTO RCHMST_TEMP(\t\tSESSIONID,\tTITLE,\t\tSTRDT,\t\tENDDT,\t\tCOLDEPTCD,        COLDEPTNM,\tCOLDEPTTEL, CHRGUSRID,\tCHRGUSRNM,\tSUMMARY,\tEXHIBIT,\tOPENTYPE,        RANGE,\t\tIMGPREVIEW,\tDUPLICHECK, LIMITCOUNT, GROUPYN,\tTGTDEPTNM,\tHEADCONT,        TAILCONT,\tANSCOUNT,\tCRTDT,\t\tCRTUSRID,\tOTHERTGTNM,\tLIMIT1,\tLIMIT2,\tRCHTARGET1,\tRCHTARGET2) VALUES(?,\t\t\t?,\t\t\t?,\t\t\t?,\t\t\t?,        ?,\t\t\t?,\t\t\t?,\t\t\t?,\t\t\t'" + 
      summary + "', ?,\t?, " + 
      "       ?,\t\t\t?,\t\t\t?,\t\t\t?,\t\t\t?,\t\t\t'" + tgtdeptnm + "', '" + headcont + "', " + 
      "\t\t'" + tailcont + "',\t0,\tTO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), ?,?,?,?,?,?) ";

    pstmt = conn.prepareStatement(insMstQuery);

    pstmt.setString(++bindPos, Bean.getSessionId());
    pstmt.setString(++bindPos, new String(Bean.getTitle().getBytes("x-windows-949"), "x-windows-949"));
    pstmt.setString(++bindPos, Bean.getStrdt());
    pstmt.setString(++bindPos, Bean.getEnddt());
    pstmt.setString(++bindPos, Bean.getColdeptcd());
    pstmt.setString(++bindPos, Bean.getColdeptnm());
    pstmt.setString(++bindPos, Bean.getColdepttel());
    pstmt.setString(++bindPos, Bean.getChrgusrid());
    pstmt.setString(++bindPos, Bean.getChrgusrnm());
    pstmt.setString(++bindPos, Bean.getExhibit());
    pstmt.setString(++bindPos, Bean.getOpentype());
    pstmt.setString(++bindPos, "1".equals(Bean.getRange()) ? Bean.getRange() : Bean.getRangedetail());
    pstmt.setString(++bindPos, Bean.getImgpreview());
    pstmt.setString(++bindPos, Bean.getDuplicheck());
    pstmt.setInt(++bindPos, Bean.getLimitcount());
    pstmt.setString(++bindPos, Bean.getGroupyn());
    pstmt.setString(++bindPos, Bean.getChrgusrid());
    
    pstmt.setString(++bindPos, othertgtnm);
    
    
    pstmt.setString(++bindPos, limit1);
    pstmt.setString(++bindPos, limit2);
    pstmt.setString(++bindPos, rchtarget1);
    pstmt.setString(++bindPos, rchtarget2);
    
    result = pstmt.executeUpdate();

    if (pstmt != null)
      try {
        pstmt.close();
        pstmt = null;
      }
      catch (SQLException localSQLException) {
      }
    StringBuffer insSubQuery = new StringBuffer();

    insSubQuery.append("\n INSERT INTO RCHSUB_TEMP(SESSIONID, FORMSEQ, FORMGRP, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE, REQUIRE ) ");
    insSubQuery.append("\n VALUES (?, ?, '', '', '1', 'N', 'N', 'Y') ");

    pstmt = conn.prepareStatement(insSubQuery.toString());

    for (int i = 0; i < Bean.getFormcount(); i++)
    {
      pstmt.setString(1, Bean.getSessionId());
      pstmt.setInt(2, i + 1);

      pstmt.addBatch();
    }

    ret = pstmt.executeBatch();
    result += ret.length;

    if (pstmt != null)
      try {
        pstmt.close();
        pstmt = null;
      }
      catch (SQLException localSQLException1) {
      }
    return result;
  }

  public int rchGrpInsMst(Connection conn, ResearchBean Bean)
    throws Exception
  {
    PreparedStatement pstmt = null;
    int result = 0;
    int bindPos = 0;

    String summary = null;
    String tgtdeptnm = null;
    String headcont = null;
    String tailcont = null;

    if (Bean.getSummary() != null)
      summary = Bean.getSummary().replaceAll("'", "''");
    else {
      summary = "";
    }

    if (Bean.getTgtdeptnm() != null)
      tgtdeptnm = Bean.getTgtdeptnm().replaceAll("'", "''");
    else {
      tgtdeptnm = "";
    }

    if (Bean.getHeadcont() != null)
      headcont = new String(Bean.getHeadcont().replaceAll("'", "''").getBytes("x-windows-949"), "x-windows-949");
    else {
      headcont = "";
    }

    if (Bean.getTailcont() != null)
      tailcont = new String(Bean.getTailcont().replaceAll("'", "''").getBytes("x-windows-949"), "x-windows-949");
    else {
      tailcont = "";
    }

    String insMstQuery = "";

    insMstQuery = "INSERT INTO RCHGRPMST_TEMP(\t\tSESSIONID,\tTITLE,\t\tSTRDT,\t\tENDDT,\t\tCOLDEPTCD,        COLDEPTNM,\tCOLDEPTTEL, CHRGUSRID,\tCHRGUSRNM,\tSUMMARY,\tEXHIBIT,\tOPENTYPE,        RANGE,\t\tIMGPREVIEW,\tDUPLICHECK, LIMITCOUNT, GROUPYN,\tRCHNOLIST,\tTGTDEPTNM,\tHEADCONT,        TAILCONT,\tANSCOUNT,\tCRTDT,\t\tCRTUSRID) VALUES(?,\t\t\t?,\t\t\t?,\t\t\t?,\t\t\t?,        ?,\t\t\t?,\t\t\t?,\t\t\t?,\t\t\t'" + 
      summary + "', ?,\t?, " + 
      "       ?,\t\t\t?,\t\t\t?,\t\t\t?,\t\t\t?,\t\t\t?,\t'" + tgtdeptnm + "', '" + headcont + "', " + 
      "\t\t'" + tailcont + "',\t0,\tTO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), ?) ";

    pstmt = conn.prepareStatement(insMstQuery);

    pstmt.setString(++bindPos, Bean.getSessionId());
    pstmt.setString(++bindPos, new String(Bean.getTitle().getBytes("x-windows-949"), "x-windows-949"));
    pstmt.setString(++bindPos, Bean.getStrdt());
    pstmt.setString(++bindPos, Bean.getEnddt());
    pstmt.setString(++bindPos, Bean.getColdeptcd());
    pstmt.setString(++bindPos, Bean.getColdeptnm());
    pstmt.setString(++bindPos, Bean.getColdepttel());
    pstmt.setString(++bindPos, Bean.getChrgusrid());
    pstmt.setString(++bindPos, Bean.getChrgusrnm());
    pstmt.setString(++bindPos, Bean.getExhibit());
    pstmt.setString(++bindPos, Bean.getOpentype());
    pstmt.setString(++bindPos, "1".equals(Bean.getRange()) ? Bean.getRange() : Bean.getRangedetail());
    pstmt.setString(++bindPos, Bean.getImgpreview());
    pstmt.setString(++bindPos, Bean.getDuplicheck());
    pstmt.setInt(++bindPos, Bean.getLimitcount());
    pstmt.setString(++bindPos, Bean.getGroupyn());
    pstmt.setString(++bindPos, Bean.getRchnolist());
    pstmt.setString(++bindPos, Bean.getChrgusrid());

    result = pstmt.executeUpdate();

    if (pstmt != null)
      try {
        pstmt.close();
        pstmt = null;
      }
      catch (SQLException localSQLException) {
      }
    return result;
  }

  public int rchInsSub(Connection conn, List subList, String sessionId, int rchno)
    throws Exception
  {
    PreparedStatement pstmt = null;
    int[] ret = (int[])null;
    int result = 0;

    StringBuffer delSubQuery = new StringBuffer();
    String insSubQuery = "";

    if (rchno == 0) {
      delSubQuery.append("\n DELETE FROM RCHSUB_TEMP \t");
      delSubQuery.append("\n  WHERE SESSIONID LIKE ? \t");

      insSubQuery = "INSERT INTO RCHSUB_TEMP(\t\t SESSIONID,\tFORMSEQ,\tFORMGRP,\tFORMTITLE,\t\t\t\t FORMTYPE, \tSECURITY, \tEXAMTYPE, \tREQUIRE\t ) VALUES( ?,\t\t\t?,\t\t\t?,\t\t\t?,\t\t\t\t\t ?, \t\t?,\t\t?, \t\t\t? ) ";
    }
    else
    {
      delSubQuery.append("\n DELETE FROM RCHSUB \t");
      delSubQuery.append("\n  WHERE RCHNO = ? \t");

      insSubQuery = "INSERT INTO RCHSUB(\t\t RCHNO,\t\tFORMSEQ,\tFORMGRP,\tFORMTITLE,\t\t\t\t FORMTYPE, \tSECURITY,   \tEXAMTYPE, \tREQUIRE\t  ) VALUES( ?,\t\t\t?,\t\t\t?,\t\t\t?,\t\t\t\t\t ?, \t\t?,\t\t?, \t\t\t? ) ";
    }

    try
    {
      pstmt = conn.prepareStatement(delSubQuery.toString());

      if (rchno == 0)
        pstmt.setString(1, sessionId);
      else {
        pstmt.setInt(1, rchno);
      }

      result = pstmt.executeUpdate();

      if (pstmt != null)
        try {
          pstmt.close();
          pstmt = null;
        }
        catch (SQLException localSQLException) {
        }
      pstmt = conn.prepareStatement(insSubQuery);

      if (subList != null) {
        ResearchSubBean subBean = null;
        for (int i = 0; i < subList.size(); i++) {
          subBean = (ResearchSubBean)subList.get(i);
          if (rchno == 0)
            pstmt.setString(1, sessionId);
          else {
            pstmt.setInt(1, rchno);
          }
          pstmt.setInt(2, subBean.getFormseq());
          pstmt.setString(3, new String(subBean.getFormgrp().getBytes("x-windows-949"), "x-windows-949"));
          pstmt.setString(4, new String(subBean.getFormtitle().getBytes("x-windows-949"), "x-windows-949"));
          pstmt.setString(5, subBean.getFormtype());
          pstmt.setString(6, subBean.getSecurity());
          pstmt.setString(7, subBean.getExamtype());
          if ( ("01".equals(subBean.getFormtype()) || "02".equals(subBean.getFormtype())) && subBean.getExamList() != null ) {
				int cnt = 0;
				for ( ; cnt < subBean.getExamList().size(); cnt++ ) {
					ResearchExamBean examBean = (ResearchExamBean)subBean.getExamList().get(cnt);
					if ( examBean != null && !"".equals(Utils.nullToEmptyString(examBean.getExamcont())) ) break;
				}
				if ( cnt == subBean.getExamList().size() ) {
              pstmt.setString(5, "03");
              pstmt.setString(6, "N");
              pstmt.setString(7, "N");
            }
          }
          pstmt.setString(8, subBean.getRequire());
          //logger.info("pstmt["+subBean.getFormseq()+"]["+subBean.getFormgrp()+"]["+subBean.getFormtitle()+"]["+subBean.getFormtype()+"]["+subBean.getSecurity()+"]["+subBean.getExamtype()+"]["+subBean.getRequire()+"]");
          pstmt.addBatch();
        }
      }

      ret = pstmt.executeBatch();
      result += ret.length;

      if (pstmt != null)
        try {
          pstmt.close();
          pstmt = null;
        }
        catch (SQLException localSQLException1) {
        }
      if (subList != null) {
        ResearchSubBean subBean = null;
        for (int i = 0; i < subList.size(); i++) {
          subBean = (ResearchSubBean)subList.get(i);
          result += rchInsExam(conn, subBean.getExamList(), subBean.getFormseq(), sessionId, rchno, i);
        }
      }
    }
    catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(pstmt);
      throw e; } finally {
      try {
        pstmt.close(); } catch (Exception localException1) {
      }
    }
    return result;
  }

  public int insAddSub(int rchno, String sessionId)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    int result = 0;
    StringBuffer insertQuery = new StringBuffer();

    if (rchno == 0) {
      insertQuery.append("INSERT INTO RCHSUB_TEMP (SESSIONID, FORMSEQ, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE, REQUIRE ) ");
      insertQuery.append("VALUES(?, ?, '', '1', 'N', 'N', 'Y') ");
    } else {
      insertQuery.append("INSERT INTO RCHSUB (RCHNO, FORMSEQ, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE, REQUIRE ) ");
      insertQuery.append("VALUES(?, ?, '', '1', 'N', 'N', 'Y') ");
    }
    try
    {
      con = ConnectionManager.getConnection();
      pstmt = con.prepareStatement(insertQuery.toString());

      if (rchno == 0)
        pstmt.setString(1, sessionId);
      else {
        pstmt.setInt(1, rchno);
      }

      pstmt.setInt(2, getMaxSubSeq(rchno, sessionId));

      result = pstmt.executeUpdate();
    }
    catch (Exception e) {
      result = -1;
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt);
    }
    return result;
  }

  public int insMakeSub(int rchno, String sessionId, int formcount)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int precnt = getCntSubSeq(rchno, sessionId);
    int maxseq = getMaxSubSeq(rchno, sessionId);
    int result = 0;
    int[] ret = (int[])null;

    StringBuffer insertQuery = new StringBuffer();
    StringBuffer selectQuery = new StringBuffer();

    StringBuffer delteQuery = new StringBuffer();

    if (rchno == 0) {
      insertQuery.append("INSERT INTO RCHSUB_TEMP(SESSIONID, FORMSEQ, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE, REQUIRE ) ");
      insertQuery.append("VALUES(?, ?, '', '1', 'N', 'N', 'Y') ");

      delteQuery.append("DELETE FROM RCHSUB_TEMP WHERE SESSIONID LIKE ? AND FORMSEQ = ? ");

      selectQuery.append("SELECT NVL(MAX(FORMSEQ),0) FROM RCHSUB_TEMP WHERE SESSIONID LIKE ? ");
    } else {
      insertQuery.append("INSERT INTO RCHSUB(RCHNO, FORMSEQ, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE, REQUIRE ) ");
      insertQuery.append("VALUES(?, ?, '', '1', 'N', 'N', 'Y') ");

      delteQuery.append("DELETE FROM RCHSUB WHERE RCHNO = ? AND FORMSEQ = ? ");

      selectQuery.append("SELECT NVL(MAX(FORMSEQ),0) FROM RCHSUB WHERE RCHNO = ? ");
    }
    try
    {
      con = ConnectionManager.getConnection();
      con.setAutoCommit(false);

      if (formcount == precnt)
        return 0;
      if (formcount > precnt) {
        int cnt = formcount - precnt;

        pstmt = con.prepareStatement(insertQuery.toString());
        for (int i = 0; i < cnt; i++)
        {
          if (rchno == 0)
            pstmt.setString(1, sessionId);
          else {
            pstmt.setInt(1, rchno);
          }

          pstmt.setInt(2, maxseq + i);
          pstmt.addBatch();
        }
        ret = pstmt.executeBatch();
        result += ret.length;

        if (pstmt != null)
          try {
            pstmt.close();
            pstmt = null;
          } catch (SQLException localSQLException) {
          }
      } else if (formcount < precnt)
      {
        int cnt = precnt - formcount;

        for (int i = 0; i < cnt; i++)
        {
          pstmt = con.prepareStatement(selectQuery.toString());

          if (rchno == 0)
            pstmt.setString(1, sessionId);
          else {
            pstmt.setInt(1, rchno);
          }

          rs = pstmt.executeQuery();

          int formseq = 0;
          if (rs.next()) {
            formseq = rs.getInt(1);
          }

          if (pstmt != null)
            try {
              rs.close();
              pstmt.close();
              pstmt = null;
            }
            catch (SQLException localSQLException1) {
            }
          pstmt = con.prepareStatement(delteQuery.toString());

          if (rchno == 0)
            pstmt.setString(1, sessionId);
          else {
            pstmt.setInt(1, rchno);
          }
          pstmt.setInt(2, formseq);

          result += pstmt.executeUpdate();

          if (pstmt != null)
            try {
              pstmt.close();
              pstmt = null;
            }
            catch (SQLException localSQLException2)
            {
            }
          result += rchDelExam(con, rchno, sessionId, formseq);
        }
      }

      con.commit();
    } catch (Exception e) {
      try {
        result = -1;
        con.rollback();
      } catch (Exception ex) {
        logger.error("ERROR", ex);
      }

      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      try {
        con.setAutoCommit(true);
      } catch (Exception e) {
        logger.error("ERROR", e);
      }

      ConnectionManager.close(con, pstmt, rs);
    }
    try
    {
      con.setAutoCommit(true);
    } catch (Exception e) {
      logger.error("ERROR", e);
    }

    ConnectionManager.close(con, pstmt, rs);

    return result;
  }

  public int rchInsExam(Connection conn, List examList, int formseq, String sessionId, int rchno, int cnt)
    throws Exception
  {
    PreparedStatement pstmt = null;
    int result = 0;
    int[] ret = (int[])null;

    StringBuffer delExamQuery = new StringBuffer();
    String insExamQuery = "";

    if (rchno == 0) {
      delExamQuery.append("\n DELETE FROM RCHEXAM_TEMP ");
      delExamQuery.append("\n  WHERE SESSIONID LIKE ? \t");

      insExamQuery = "INSERT INTO RCHEXAM_TEMP(\t\tSESSIONID,\t\tFORMSEQ,\tEXAMSEQ,\tEXAMCONT\t ) VALUES(?,\t\t\t?,\t\t\t?,\t\t\t?\t\t\t\t) ";
    }
    else
    {
      delExamQuery.append("\n DELETE FROM RCHEXAM ");
      delExamQuery.append("\n  WHERE RCHNO = ? \t");

      insExamQuery = "INSERT INTO RCHEXAM(\t\tRCHNO,\t\tFORMSEQ,\tEXAMSEQ,\tEXAMCONT\t ) VALUES(?,\t\t\t?,\t\t\t?,\t\t\t?\t\t\t) ";
    }

    if (cnt == 0) {
      pstmt = conn.prepareStatement(delExamQuery.toString());

      if (rchno == 0)
        pstmt.setString(1, sessionId);
      else {
        pstmt.setInt(1, rchno);
      }

      result = pstmt.executeUpdate();

      if (pstmt != null)
        try {
          pstmt.close();
          pstmt = null;
        }
        catch (SQLException localSQLException) {
        }
    }
    ResearchExamBean examBean = null;
    pstmt = conn.prepareStatement(insExamQuery);
    if (examList != null) {
      for (int j = 0; j < examList.size(); j++) {
        examBean = (ResearchExamBean)examList.get(j);

        if (!"".equals(Utils.nullToEmptyString(examBean.getExamcont())))
        {
          if (rchno == 0)
            pstmt.setString(1, sessionId);
          else {
            pstmt.setInt(1, rchno);
          }
          pstmt.setInt(2, formseq);
          pstmt.setInt(3, examBean.getExamseq());
          pstmt.setString(4, new String(examBean.getExamcont().getBytes("x-windows-949"), "x-windows-949"));

          pstmt.addBatch();
        }
      }
      ret = pstmt.executeBatch();
      result += ret.length;
    }

    if (pstmt != null)
      try {
        pstmt.close();
        pstmt = null;
      }
      catch (SQLException localSQLException1) {
      }
    return result;
  }

  public int rchUptMst(Connection conn, ResearchBean Bean, int mdrchno)
    throws Exception
  {
    PreparedStatement pstmt = null;
    int result = 0;
    try
    {
      String summary = null;
      String tgtdeptnm = null;
      String othertgtnm = null;
      String headcont = null;
      String tailcont = null;
      String query = null;

      String limit1 = null;
      String limit2 = null;
      String limit1Chk = Bean.getLimit1chk();
      String limit2Chk = Bean.getLimit2chk();
      String rchtarget1 = null;
      String rchtarget2 = null;
      if (Bean.getSummary() != null)
        summary = Bean.getSummary().replaceAll("'", "''");
      else {
        summary = "";
      }
      summary = new String(summary.getBytes("x-windows-949"), "x-windows-949");
      if (Bean.getTgtdeptnm() != null)
        tgtdeptnm = Bean.getTgtdeptnm().replaceAll("'", "''");
      else {
        tgtdeptnm = "";
      }
      if (Bean.getOthertgtnm() != null)
    	  othertgtnm = Bean.getOthertgtnm().replaceAll("'", "''");
        else {
        	othertgtnm = "";
        }

      if (Bean.getHeadcont() != null){
        headcont = Bean.getHeadcont().replaceAll("'", "''");
        headcont = new String(headcont.getBytes("x-windows-949"), "x-windows-949");
      }else {
        headcont = "";
      }

      if (Bean.getTailcont() != null){
        tailcont = Bean.getTailcont().replaceAll("'", "''");
        tailcont = new String(tailcont.getBytes("x-windows-949"), "x-windows-949");
      }else {
        tailcont = "";
      }
      limit1Chk = Bean.getLimit1chk();
      limit2Chk = Bean.getLimit2chk();
      if(limit1Chk.equals("")){
    	  limit1 = "";
    	  rchtarget1 = tgtdeptnm;
      }else{
    	  limit1 = tgtdeptnm;
    	  rchtarget1 = "";
      }
      if(limit2Chk.equals("")){
    	  limit2 = "";
    	  rchtarget2 = othertgtnm;
      }else{
    	  limit2 = othertgtnm;
    	  rchtarget2 = "";
      }
   
      if (mdrchno == 0){
    	query = "UPDATE RCHMST_TEMP   SET\tTITLE = ?,\t\tSTRDT = ?,\t\tENDDT = ?,\t\tCOLDEPTCD = ?,  COLDEPTNM = ?,\tCOLDEPTTEL = ?, \t    CHRGUSRID = ?,\tCHRGUSRNM = ?,\tSUMMARY = '" + 
          summary + "',\tEXHIBIT = ?,\tOPENTYPE = ?, \tRANGE = ?,\tIMGPREVIEW = ?,\tDUPLICHECK = ?, LIMITCOUNT = ?, " + 
          "\t\tGROUPYN = ?, \tTGTDEPTNM = '" + tgtdeptnm + "',\tOTHERTGTNM = '" + othertgtnm + "',\tHEADCONT = '" + headcont + "',\tTAILCONT = '" + tailcont + "',\tUPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS')," + 
          "\t\tUPTUSRID = ?    ,\tLIMIT1= ?,\tLIMIT2= ?, \tRCHTARGET1=?, \tRCHTARGET2=?" + 
          " WHERE SESSIONID LIKE ? ";
      }else {
    	query = "UPDATE RCHMST   SET\tTITLE = ?,\t\tSTRDT = ?,\t\tENDDT = ?,\t\tCOLDEPTCD = ?,  COLDEPTNM = ?,\tCOLDEPTTEL = ?, \t    CHRGUSRID = ?,\tCHRGUSRNM = ?,\tSUMMARY = '" + 
          summary + "',\tEXHIBIT = ?,\tOPENTYPE = ?, \tRANGE = ?,\tIMGPREVIEW = ?,\tDUPLICHECK = ?, LIMITCOUNT = ?, " + 
          "\t\tGROUPYN = ?, \tTGTDEPTNM = '" + tgtdeptnm + "',\tOTHERTGTNM = '" + othertgtnm + "',\tHEADCONT = '" + headcont + "',\tTAILCONT = '" + tailcont + "',\tUPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS')," + 
          "\t\tUPTUSRID = ?  ,\tLIMIT1= ?,\tLIMIT2= ?, \tRCHTARGET1=?, \tRCHTARGET2=?   " + 
          " WHERE RCHNO =  ?  ";
      }

      pstmt = conn.prepareStatement(query);

      pstmt.setString(1, new String(Bean.getTitle().getBytes("x-windows-949"), "x-windows-949"));
      pstmt.setString(2, Bean.getStrdt());
      pstmt.setString(3, Bean.getEnddt());
      pstmt.setString(4, Bean.getColdeptcd());
      pstmt.setString(5, Bean.getColdeptnm());
      pstmt.setString(6, Bean.getColdepttel());
      pstmt.setString(7, Bean.getChrgusrid());
      pstmt.setString(8, Bean.getChrgusrnm());
      pstmt.setString(9, Bean.getExhibit());
      pstmt.setString(10, Bean.getOpentype());
      pstmt.setString(11, "1".equals(Bean.getRange()) ? Bean.getRange() : Bean.getRangedetail());
      pstmt.setString(12, Bean.getImgpreview());
      pstmt.setString(13, Bean.getDuplicheck());
      pstmt.setInt(14, Bean.getLimitcount());
      pstmt.setString(15, Bean.getGroupyn());
      pstmt.setString(16, Bean.getChrgusrid());
      pstmt.setString(17, limit1);
      pstmt.setString(18, limit2);
      pstmt.setString(19, rchtarget1);
      pstmt.setString(20, rchtarget2);
      if (mdrchno == 0)
        pstmt.setString(21, Bean.getSessionId());
      else {
        pstmt.setInt(21, mdrchno);
      }

      result = pstmt.executeUpdate();
    }
    catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(pstmt);
      throw e; } finally {
      try {
        pstmt.close(); } catch (Exception localException1) {
      }
    }
    return result;
  }

  public int rchGrpUptMst(Connection conn, ResearchBean Bean, int mdrchgrpno)
    throws Exception
  {
    PreparedStatement pstmt = null;
    int result = 0;
    try
    {
      String summary = null;
      String tgtdeptnm = null;
      String othertgtnm = null;
      String headcont = null;
      String tailcont = null;
      String query = null;

      if (Bean.getSummary() != null)
        summary = Bean.getSummary().replaceAll("'", "''");
      else {
        summary = "";
      }
      summary = new String(summary.getBytes("x-windows-949"), "x-windows-949");
      if (Bean.getTgtdeptnm() != null)
        tgtdeptnm = Bean.getTgtdeptnm().replaceAll("'", "''");
      else {
        tgtdeptnm = "";
      }
      if (Bean.getOthertgtnm() != null)
    	  othertgtnm = Bean.getOthertgtnm().replaceAll("'", "''");
        else {
        	othertgtnm = "";
        }
      if (Bean.getHeadcont() != null){
        headcont = Bean.getHeadcont().replaceAll("'", "''");
      	headcont = new String(headcont.getBytes("x-windows-949"), "x-windows-949");
      }else {
        headcont = "";
      }

      if (Bean.getTailcont() != null){
        tailcont = Bean.getTailcont().replaceAll("'", "''");
        tailcont = new String(tailcont.getBytes("x-windows-949"), "x-windows-949");
      }else {
        tailcont = "";
      }

      if (mdrchgrpno == 0)
        query = "UPDATE RCHGRPMST_TEMP   SET\tTITLE = ?,\t\tSTRDT = ?,\t\tENDDT = ?,\t\tCOLDEPTCD = ?,  COLDEPTNM = ?,\tCOLDEPTTEL = ?, \t    CHRGUSRID = ?,\tCHRGUSRNM = ?,\tSUMMARY = '" + 
          summary + "',\tEXHIBIT = ?,\tOPENTYPE = ?, \tRANGE = ?,\tIMGPREVIEW = ?,\tDUPLICHECK = ?, LIMITCOUNT = ?, " + 
          "\t\tGROUPYN = ?, \tRCHNOLIST = ?,\tTGTDEPTNM = '" + tgtdeptnm + "',\tOTHERTGTNM = '" + othertgtnm + "',\tHEADCONT = '" + headcont + "',\tTAILCONT = '" + tailcont + "',\tUPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS')," + 
          "\t\tUPTUSRID = ?    " + 
          " WHERE SESSIONID LIKE ? ";
      else {
        query = "UPDATE RCHGRPMST   SET\tTITLE = ?,\t\tSTRDT = ?,\t\tENDDT = ?,\t\tCOLDEPTCD = ?,  COLDEPTNM = ?,\tCOLDEPTTEL = ?, \t    CHRGUSRID = ?,\tCHRGUSRNM = ?,\tSUMMARY = '" + 
          summary + "',\tEXHIBIT = ?,\tOPENTYPE = ?, \tRANGE = ?,\tIMGPREVIEW = ?,\tDUPLICHECK = ?, LIMITCOUNT = ?, " + 
          "\t\tGROUPYN = ?, \tRCHNOLIST = ?,\tTGTDEPTNM = '" + tgtdeptnm + "',\tOTHERTGTNM = '" + othertgtnm + "',\tHEADCONT = '" + headcont + "',\tTAILCONT = '" + tailcont + "',\tUPTDT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS')," + 
          "\t\tUPTUSRID = ?    " + 
          " WHERE RCHGRPNO =  ?  ";
      }

      pstmt = conn.prepareStatement(query);

      pstmt.setString(1, new String(Bean.getTitle().getBytes("x-windows-949"), "x-windows-949"));
      pstmt.setString(2, Bean.getStrdt());
      pstmt.setString(3, Bean.getEnddt());
      pstmt.setString(4, Bean.getColdeptcd());
      pstmt.setString(5, Bean.getColdeptnm());
      pstmt.setString(6, Bean.getColdepttel());
      pstmt.setString(7, Bean.getChrgusrid());
      pstmt.setString(8, Bean.getChrgusrnm());
      pstmt.setString(9, Bean.getExhibit());
      pstmt.setString(10, Bean.getOpentype());
      pstmt.setString(11, "1".equals(Bean.getRange()) ? Bean.getRange() : Bean.getRangedetail());
      pstmt.setString(12, Bean.getImgpreview());
      pstmt.setString(13, Bean.getDuplicheck());
      pstmt.setInt(14, Bean.getLimitcount());
      pstmt.setString(15, Bean.getGroupyn());
      pstmt.setString(16, Bean.getRchnolist());
      pstmt.setString(17, Bean.getChrgusrid());

      if (mdrchgrpno == 0)
        pstmt.setString(18, Bean.getSessionId());
      else {
        pstmt.setInt(18, mdrchgrpno);
      }

      result = pstmt.executeUpdate();
    }
    catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(pstmt);
      throw e; } finally {
      try {
        pstmt.close(); } catch (Exception localException1) {
      }
    }
    return result;
  }

  public int delRchExamAllFile(Connection conn, String sessionId, int rchno, int formseq, ServletContext context)
    throws Exception
  {
    int result = 0;

    ResearchSubBean rchSubBean = getRchSubFile(conn, sessionId, rchno, formseq);

    if (rchSubBean != null) {
      delRchSubFile(conn, sessionId, rchno, formseq, rchSubBean.getFileseq());

      String delFile = rchSubBean.getFilenm();
      if ((delFile != null) && (!delFile.trim().equals(""))) {
        FileManager.doFileDelete(context.getRealPath(delFile));
      }
    }

    List rchExamList = getRchExamFile(conn, sessionId, rchno, formseq);

    for (int i = 0; (rchExamList != null) && (i < rchExamList.size()); i++) {
      ResearchExamBean rchExamBean = (ResearchExamBean)rchExamList.get(i);

      if (rchExamBean != null) {
        delRchExamFile(conn, sessionId, rchno, formseq, rchExamBean.getExamseq(), rchExamBean.getFileseq());

        String delFile = rchExamBean.getFilenm();
        if ((delFile != null) && (!delFile.trim().equals(""))) {
          FileManager.doFileDelete(context.getRealPath(delFile));
        }
      }
    }

    return result;
  }

  public int delRchSubExamAllFile(Connection conn, String sessionId, int rchno, ServletContext context)
    throws Exception
  {
    int result = 0;

    List rchSubList = getRchSubFile(conn, sessionId, rchno);

    for (int i = 0; (rchSubList != null) && (i < rchSubList.size()); i++) {
      ResearchSubBean rchSubBean = (ResearchSubBean)rchSubList.get(i);

      if (rchSubBean != null) {
        delRchSubFile(conn, sessionId, rchno, rchSubBean.getFormseq(), rchSubBean.getFileseq());

        String delFile = rchSubBean.getFilenm();
        if ((delFile != null) && (!delFile.trim().equals(""))) {
          FileManager.doFileDelete(context.getRealPath(delFile));
        }
      }
    }

    List rchExamList = getRchExamFile(conn, sessionId, rchno, 0);

    for (int i = 0; (rchExamList != null) && (i < rchExamList.size()); i++) {
      ResearchExamBean rchExamBean = (ResearchExamBean)rchExamList.get(i);

      if (rchExamBean != null) {
        delRchExamFile(conn, sessionId, rchno, rchExamBean.getFormseq(), rchExamBean.getExamseq(), rchExamBean.getFileseq());

        String delFile = rchExamBean.getFilenm();
        if ((delFile != null) && (!delFile.trim().equals(""))) {
          FileManager.doFileDelete(context.getRealPath(delFile));
        }
      }
    }

    return result;
  }

  public int rchDelSub(int rchno, String sessionId, int formseq, ServletContext context)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    int result = 0;
    StringBuffer sql = new StringBuffer();

    if (rchno == 0)
      sql.append("DELETE FROM RCHSUB_TEMP WHERE SESSIONID LIKE ? AND FORMSEQ = ? ");
    else {
      sql.append("DELETE FROM RCHSUB WHERE RCHNO = ? AND FORMSEQ = ? ");
    }
    try
    {
      con = ConnectionManager.getConnection();
      con.setAutoCommit(false);

      pstmt = con.prepareStatement(sql.toString());
      if (rchno == 0)
        pstmt.setString(1, sessionId);
      else {
        pstmt.setInt(1, rchno);
      }
      pstmt.setInt(2, formseq);
      result = pstmt.executeUpdate();

      ConnectionManager.close(pstmt);

      result += rchDelExam(con, rchno, sessionId, formseq);

      delRchExamAllFile(con, sessionId, rchno, formseq, context);

      String kfield = null;
      String[] updateTable = (String[])null;
      String[] updateTable1 = { "RCHSUB_TEMP", "RCHEXAM_TEMP", "RCHSUBFILE_TEMP", "RCHEXAMFILE_TEMP" };
      String[] updateTable2 = { "RCHSUB", "RCHEXAM", "RCHSUBFILE", "RCHEXAMFILE" };
      if (rchno == 0) {
        updateTable = updateTable1;
        kfield = "SESSIONID";
      } else {
        updateTable = updateTable2;
        kfield = "RCHNO";
      }

      for (int i = 0; i < updateTable.length; i++) {
        sql.delete(0, sql.capacity());
        sql.append("UPDATE " + updateTable[i] + " SET FORMSEQ = FORMSEQ - 1 WHERE " + kfield + " = ? AND FORMSEQ > ? ");

        pstmt = con.prepareStatement(sql.toString());
        if (rchno == 0)
          pstmt.setString(1, sessionId);
        else {
          pstmt.setInt(1, rchno);
        }
        pstmt.setInt(2, formseq);
        result = pstmt.executeUpdate();

        ConnectionManager.close(pstmt);
      }

      con.commit();
    } catch (Exception e) {
      try {
        result = -1;
        con.rollback();
      } catch (Exception ex) {
        logger.error("ERROR", ex);
      }

      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt);
      throw e;
    } finally {
      try {
        con.setAutoCommit(true);
      } catch (Exception e) {
        logger.error("ERROR", e);
      }

      ConnectionManager.close(con, pstmt);
    }
    return result;
  }

  private int rchDelExam(Connection con, int rchno, String sessionId, int formseq)
    throws Exception
  {
    PreparedStatement pstmt = null;
    int result = 0;

    StringBuffer delteQuery = new StringBuffer();
    if (rchno == 0)
      delteQuery.append("DELETE FROM RCHEXAM_TEMP WHERE SESSIONID LIKE ? AND FORMSEQ = ? ");
    else {
      delteQuery.append("DELETE FROM RCHEXAM WHERE RCHNO = ? AND FORMSEQ = ? ");
    }
    try
    {
      pstmt = con.prepareStatement(delteQuery.toString());

      if (rchno == 0)
        pstmt.setString(1, sessionId);
      else {
        pstmt.setInt(1, rchno);
      }

      pstmt.setInt(2, formseq);

      result = pstmt.executeUpdate();
    }
    catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(pstmt);
      throw e; } finally {
      try {
        pstmt.close(); } catch (Exception localException1) {  }
    }
    return result;
  }

  public boolean checkCnt(String sessionId)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    boolean result = false;
    try
    {
      StringBuffer selectQuery = new StringBuffer();

      selectQuery.append("SELECT COUNT(*) FROM RCHMST_TEMP WHERE SESSIONID LIKE ? ");

      con = ConnectionManager.getConnection();
      pstmt = con.prepareStatement(selectQuery.toString());
      pstmt.setString(1, sessionId);

      rs = pstmt.executeQuery();

      if ((rs.next()) && 
        (rs.getInt(1) > 0)) {
        result = true;
      }
    }
    catch (Exception e)
    {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }

    return result;
  }

  public boolean checkCntGrp(String sessionId)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    boolean result = false;
    try
    {
      StringBuffer selectQuery = new StringBuffer();

      selectQuery.append("SELECT COUNT(*) FROM RCHGRPMST_TEMP WHERE SESSIONID LIKE ? ");

      con = ConnectionManager.getConnection();
      pstmt = con.prepareStatement(selectQuery.toString());
      pstmt.setString(1, sessionId);

      rs = pstmt.executeQuery();

      if ((rs.next()) && 
        (rs.getInt(1) > 0)) {
        result = true;
      }
    }
    catch (Exception e)
    {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }

    return result;
  }

  public int getMaxSubSeq(int rchno, String sessionId)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int result = 0;

    StringBuffer selectQuery = new StringBuffer();

    if (rchno == 0) {
      selectQuery.append("SELECT NVL(MAX(FORMSEQ),0)+1 FROM RCHSUB_TEMP WHERE SESSIONID LIKE ? ");
    }
    else {
      selectQuery.append("SELECT NVL(MAX(FORMSEQ),0)+1 FROM RCHSUB WHERE RCHNO = ? ");
    }
    try
    {
      con = ConnectionManager.getConnection();
      pstmt = con.prepareStatement(selectQuery.toString());

      if (rchno == 0)
        pstmt.setString(1, sessionId);
      else {
        pstmt.setInt(1, rchno);
      }

      rs = pstmt.executeQuery();

      if (rs.next())
        result = rs.getInt(1);
    }
    catch (Exception e)
    {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }

    return result;
  }

  private int getCntSubSeq(int rchno, String sessionId)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int result = 0;

    StringBuffer selectQuery = new StringBuffer();

    if (rchno == 0)
      selectQuery.append("SELECT COUNT(*) FROM RCHSUB_TEMP WHERE SESSIONID LIKE ? ");
    else {
      selectQuery.append("SELECT COUNT(*) FROM RCHSUB WHERE RCHNO = ? ");
    }
    try
    {
      con = ConnectionManager.getConnection();
      pstmt = con.prepareStatement(selectQuery.toString());

      if (rchno == 0)
        pstmt.setString(1, sessionId);
      else {
        pstmt.setInt(1, rchno);
      }

      rs = pstmt.executeQuery();

      if (rs.next())
        result = rs.getInt(1);
    }
    catch (Exception e)
    {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }

    return result;
  }

  public List getUsedRchList(ResearchBean schBean, int start, int end)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List list = null;
    try
    {
      StringBuffer selectQuery = new StringBuffer();

      selectQuery.append("SELECT (CNT-SEQ+1) BUNHO, X.* FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.*       FROM (SELECT ROWNUM SEQ, A2.*              FROM (SELECT T1.RCHNO, T1.TITLE, REPLACE(SUBSTR(T1.STRDT,6,5),'-','/') STRDT,  REPLACE(SUBSTR(T1.ENDDT,6,5),'-','/') ENDDT, RANGE                   FROM RCHMST  T1, CCD T2                   WHERE T1.RANGE = T2.CCDSUBCD                     AND T2.CCDCD = '013'    \t\t\t\t \tAND T1.OPENTYPE = '1' \n");

      if (Utils.isNotNull(schBean.getSch_deptcd())) {
        selectQuery.append("                    AND T1.COLDEPTCD IN (SELECT T1.DEPT_ID \n                                          FROM DEPT T1 \n                                         START WITH T1.DEPT_ID = '" + 
          schBean.getSch_deptcd() + "' \n" + 
          "                                       CONNECT BY PRIOR T1.DEPT_ID = T1.UPPER_DEPT_ID) \n");
      }

      if (Utils.isNotNull(schBean.getSch_title())) {
        selectQuery.append("                    AND T1.TITLE LIKE '%" + schBean.getSch_title() + "%' \n");
      }

      if (Utils.isNotNull(schBean.getSelyear())) {
        selectQuery.append("                    AND TO_CHAR(TO_DATE(T1.ENDDT,'YYYY-MM-DD HH24:MI:SS'), 'YYYY') = '" + schBean.getSelyear() + "' \n");
      }

      selectQuery.append("\t\t\t\t  ORDER BY T1.ENDDT DESC, T1.CRTDT DESC, T1.UPTDT DESC) A2) A1) X \nWHERE SEQ BETWEEN ? AND ? \n");

      con = ConnectionManager.getConnection();

      pstmt = con.prepareStatement(selectQuery.toString());
      pstmt.setInt(1, start);
      pstmt.setInt(2, end);

      rs = pstmt.executeQuery();

      list = new ArrayList();

      while (rs.next()) {
        ResearchBean Bean = new ResearchBean();

        Bean.setSeqno(rs.getInt("BUNHO"));
        Bean.setRchno(rs.getInt("RCHNO"));
        Bean.setTitle(rs.getString("TITLE"));
        Bean.setStrdt(rs.getString("STRDT"));
        Bean.setEnddt(rs.getString("ENDDT"));
        Bean.setRange(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 1));
        Bean.setRangedetail(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 2));

        list.add(Bean);
      }
    }
    catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }

    return list;
  }

  public List getUsedRchGrpList(ResearchBean schBean, int start, int end)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List list = null;
    try
    {
      StringBuffer selectQuery = new StringBuffer();

      selectQuery.append("SELECT (CNT-SEQ+1) BUNHO, X.* FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.*       FROM (SELECT ROWNUM SEQ, A2.*              FROM (SELECT T1.RCHGRPNO, T1.TITLE, REPLACE(SUBSTR(T1.STRDT,6,5),'-','/') STRDT,  REPLACE(SUBSTR(T1.ENDDT,6,5),'-','/') ENDDT, RANGE                   FROM RCHGRPMST  T1, CCD T2                   WHERE T1.RANGE = T2.CCDSUBCD                     AND T2.CCDCD = '013'    \t\t\t\t \tAND T1.OPENTYPE = '1' \n");

      if (Utils.isNotNull(schBean.getSch_deptcd())) {
        selectQuery.append("                    AND T1.COLDEPTCD IN (SELECT T1.DEPT_ID \n                                          FROM DEPT T1 \n                                         START WITH T1.DEPT_ID = '" + 
          schBean.getSch_deptcd() + "' \n" + 
          "                                       CONNECT BY PRIOR T1.DEPT_ID = T1.UPPER_DEPT_ID) \n");
      }

      if (Utils.isNotNull(schBean.getSch_title())) {
        selectQuery.append("                    AND T1.TITLE LIKE '%" + schBean.getSch_title() + "%' \n");
      }

      if (Utils.isNotNull(schBean.getSelyear())) {
        selectQuery.append("                    AND TO_CHAR(TO_DATE(T1.ENDDT,'YYYY-MM-DD HH24:MI:SS'), 'YYYY') = '" + schBean.getSelyear() + "' \n");
      }

      selectQuery.append("\t\t\t\t  ORDER BY T1.ENDDT DESC, T1.CRTDT DESC, T1.UPTDT DESC) A2) A1) X \nWHERE SEQ BETWEEN ? AND ? \n");

      con = ConnectionManager.getConnection();

      pstmt = con.prepareStatement(selectQuery.toString());
      pstmt.setInt(1, start);
      pstmt.setInt(2, end);

      rs = pstmt.executeQuery();

      list = new ArrayList();

      while (rs.next()) {
        ResearchBean Bean = new ResearchBean();

        Bean.setSeqno(rs.getInt("BUNHO"));
        Bean.setRchgrpno(rs.getInt("RCHGRPNO"));
        Bean.setTitle(rs.getString("TITLE"));
        Bean.setStrdt(rs.getString("STRDT"));
        Bean.setEnddt(rs.getString("ENDDT"));
        Bean.setRange(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 1));
        Bean.setRangedetail(Integer.toString(rs.getInt("RANGE") * 10).substring(0, 2));

        list.add(Bean);
      }
    }
    catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }

    return list;
  }

  public int getUsedRchTotCnt(ResearchBean Bean)
    throws Exception
  {
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    int totalCount = 0;
    try
    {
      StringBuffer selectQuery = new StringBuffer();

      selectQuery.append("SELECT COUNT(*)CNT \nFROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* \n      FROM (SELECT ROWNUM SEQ, A2.*  \n            FROM (SELECT RCHNO, TITLE, STRDT, ENDDT, RANGE \n                    FROM RCHMST  \n                   WHERE OPENTYPE = '1' \n");

      if (Utils.isNotNull(Bean.getSch_deptcd())) {
        selectQuery.append("                     AND COLDEPTCD IN (SELECT T1.DEPT_ID \n                                           FROM DEPT T1 \n                                          START WITH T1.DEPT_ID = '" + 
          Bean.getSch_deptcd() + "' \n" + 
          "                                        CONNECT BY PRIOR T1.DEPT_ID = T1.UPPER_DEPT_ID) \n");
      }

      if (Utils.isNotNull(Bean.getSch_title())) {
        selectQuery.append("                     AND TITLE LIKE '%" + Bean.getSch_title() + "%' \n");
      }

      if (Utils.isNotNull(Bean.getSelyear())) {
        selectQuery.append("                     AND TO_CHAR(TO_DATE(ENDDT,'YYYY-MM-DD HH24:MI:SS'), 'YYYY') = '" + Bean.getSelyear() + "' \n");
      }
      selectQuery.append("\t\t\t\t   ORDER BY CRTDT DESC) A2) A1) X \n ");

      con = ConnectionManager.getConnection();

      pstmt = con.prepareStatement(selectQuery.toString());

      rs = pstmt.executeQuery();

      if (rs.next())
        totalCount = rs.getInt(1);
    }
    catch (Exception e)
    {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }
    return totalCount;
  }

  public int getUsedRchGrpTotCnt(ResearchBean Bean)
    throws Exception
  {
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    int totalCount = 0;
    try
    {
      StringBuffer selectQuery = new StringBuffer();

      selectQuery.append("SELECT COUNT(*)CNT \nFROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* \n      FROM (SELECT ROWNUM SEQ, A2.*  \n            FROM (SELECT RCHGRPNO, TITLE, STRDT, ENDDT, RANGE \n                    FROM RCHGRPMST  \n                   WHERE OPENTYPE = '1' \n");

      if (Utils.isNotNull(Bean.getSch_deptcd())) {
        selectQuery.append("                     AND COLDEPTCD IN (SELECT T1.DEPT_ID \n                                           FROM DEPT T1 \n                                          START WITH T1.DEPT_ID = '" + 
          Bean.getSch_deptcd() + "' \n" + 
          "                                        CONNECT BY PRIOR T1.DEPT_ID = T1.UPPER_DEPT_ID) \n");
      }

      if (Utils.isNotNull(Bean.getSch_title())) {
        selectQuery.append("                     AND TITLE LIKE '%" + Bean.getSch_title() + "%' \n");
      }

      if (Utils.isNotNull(Bean.getSelyear())) {
        selectQuery.append("                     AND TO_CHAR(TO_DATE(ENDDT,'YYYY-MM-DD HH24:MI:SS'), 'YYYY') = '" + Bean.getSelyear() + "' \n");
      }
      selectQuery.append("\t\t\t\t   ORDER BY CRTDT DESC) A2) A1) X \n ");

      con = ConnectionManager.getConnection();

      pstmt = con.prepareStatement(selectQuery.toString());

      rs = pstmt.executeQuery();

      if (rs.next())
        totalCount = rs.getInt(1);
    }
    catch (Exception e)
    {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }
    return totalCount;
  }

  public List getRchParticiList(String usrid, String deptcd, String userDeptName, String getUserGradeId, String schtitle, String groupyn, int start, int end)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List list = null;
    try
    {
      StringBuffer selectQuery = new StringBuffer();

      selectQuery.append("SELECT (CNT-SEQ+1) BUNHO, X.* \n");
      selectQuery.append("FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* \n");
      selectQuery.append("      FROM (SELECT ROWNUM SEQ, A2.* \n");
      selectQuery.append("             FROM ( \n");
      if (!"Y".equals(groupyn)) {
        selectQuery.append("                   SELECT DISTINCT 'N' GBN, T1.RCHNO, T1.TITLE, COLDEPTNM, REPLACE(SUBSTR(T1.STRDT,6,5),'-','/') STRDT,  REPLACE(SUBSTR(T1.ENDDT,6,5),'-','/') ENDDT, T1.CRTDT, T1.UPTDT \n");
        selectQuery.append("                     FROM RCHMST T1, RCHDEPT T2, RCHOTHERTARGET T3 \n");
        selectQuery.append("                    WHERE T1.RCHNO = T2.RCHNO(+) \n");
        selectQuery.append("                      AND T1.RCHNO = T3.RCHNO(+) \n");
        selectQuery.append("                         AND (T1.RCHTARGET1 LIKE '%" + Utils.nullToEmptyString(userDeptName) + "%' OR T1.RCHTARGET1 IS NULL) \n");
        selectQuery.append("                         AND (T1.RCHTARGET2 LIKE '%" + Utils.nullToEmptyString(getUserGradeId) + "%' OR T1.RCHTARGET2 IS NULL) \n");
        selectQuery.append("                         AND (T1.LIMIT1 NOT LIKE '%" + Utils.nullToEmptyString(userDeptName) + "%' OR T1.LIMIT1 IS NULL) \n");
        selectQuery.append("                         AND (T1.LIMIT2 NOT LIKE '%" + Utils.nullToEmptyString(getUserGradeId) + "%' OR T1.LIMIT2 IS NULL) \n");
        selectQuery.append("\t\t\t\t\t  AND T1.OPENTYPE = '1' \n");
        selectQuery.append("\t\t\t\t\t  AND T1.RANGE = '1' \n");
        selectQuery.append("\t\t\t\t\t  AND T1.GROUPYN LIKE 'N' \n");
        selectQuery.append("\t\t\t\t\t  AND T3.TGTGBN(+) = '1' \n");
        selectQuery.append("                      AND TO_CHAR(SYSDATE,'YYYY-MM-DD') BETWEEN T1.STRDT AND T1.ENDDT \n");
        selectQuery.append("                      AND T1.TITLE LIKE '%" + Utils.nullToEmptyString(schtitle) + "%' \n");
        selectQuery.append("                      AND ((CASE NVL(T2.TGTCODE, '0') WHEN '0' THEN 1 WHEN '" + deptcd + "' THEN 1 ELSE 0 END = 1 \n");
        selectQuery.append("                           OR CASE NVL(T2.TGTCODE, '0') WHEN '0' THEN 1 WHEN '" + usrid + "' THEN 1 ELSE 0 END = 1) \n");
        selectQuery.append("                          OR CASE NVL(T3.TGTCODE, '0') WHEN '0' THEN 1 WHEN (SELECT GRADE_ID FROM USR WHERE USER_ID = '" + usrid + "') THEN 1 ELSE 0 END = 1) \n");
        selectQuery.append("                      AND 0 < (SELECT DECODE(LIMITCOUNT, 0, COUNT(DISTINCT ANSUSRSEQ) + 1, LIMITCOUNT) - COUNT(DISTINCT ANSUSRSEQ) ");
        selectQuery.append("                               FROM RCHMST RM, RCHANS RA ");
        selectQuery.append("                               WHERE RM.RCHNO = RA.RCHNO(+) ");
        selectQuery.append("                               AND RM.RCHNO = T1.RCHNO ");
        selectQuery.append("                               GROUP BY LIMITCOUNT) ");
        selectQuery.append("                      AND 0 = CASE DUPLICHECK WHEN '2' THEN (SELECT COUNT(*) FROM RCHANS \n");
        selectQuery.append("                                                             WHERE T1.RCHNO = RCHNO \n");
        selectQuery.append("                                                             AND CRTUSRID = '" + usrid + "') ELSE 0 END \n");
      }

      if ((!"Y".equals(groupyn)) && (!"N".equals(groupyn))) {
        selectQuery.append("                   UNION ALL \n");
      }

      if (!"N".equals(groupyn)) {
        selectQuery.append("                   SELECT DISTINCT 'Y' GBN, T1.RCHGRPNO RCHNO, T1.TITLE, COLDEPTNM, REPLACE(SUBSTR(T1.STRDT,6,5),'-','/') STRDT,  REPLACE(SUBSTR(T1.ENDDT,6,5),'-','/') ENDDT, T1.CRTDT, T1.UPTDT \n");
        selectQuery.append("                     FROM RCHGRPMST T1, RCHGRPDEPT T2, RCHGRPOTHERTARGET T3 \n");
        selectQuery.append("                    WHERE T1.RCHGRPNO = T2.RCHGRPNO(+) \n");
        selectQuery.append("                      AND T1.RCHGRPNO = T3.RCHGRPNO(+) \n");
        selectQuery.append("\t\t\t\t\t  AND T1.OPENTYPE = '1' \n");
        selectQuery.append("\t\t\t\t\t  AND T1.RANGE = '1' \n");
        selectQuery.append("\t\t\t\t\t  AND T1.GROUPYN LIKE '%' \n");
        selectQuery.append("\t\t\t\t\t  AND T3.TGTGBN(+) = '1' \n");
        selectQuery.append("                      AND TO_CHAR(SYSDATE,'YYYY-MM-DD')BETWEEN T1.STRDT AND T1.ENDDT \n");
        selectQuery.append("                      AND T1.TITLE LIKE '%" + Utils.nullToEmptyString(schtitle) + "%' \n");
        selectQuery.append("                      AND ((CASE NVL(T2.TGTCODE, '0') WHEN '0' THEN 1 WHEN '" + deptcd + "' THEN 1 ELSE 0 END = 1 \n");
        selectQuery.append("                           OR CASE NVL(T2.TGTCODE, '0') WHEN '0' THEN 1 WHEN '" + usrid + "' THEN 1 ELSE 0 END = 1) \n");
        selectQuery.append("                          OR CASE NVL(T3.TGTCODE, '0') WHEN '0' THEN 1 WHEN (SELECT GRADE_ID FROM USR WHERE USER_ID = '" + usrid + "') THEN 1 ELSE 0 END = 1) \n");
        selectQuery.append("                      AND TRIM(RCHNOLIST) IS NOT NULL \n");
      }

      selectQuery.append("\t\t\t\t    ORDER BY ENDDT DESC, CRTDT DESC, UPTDT DESC) A2) A1) X \n");
      selectQuery.append("WHERE SEQ BETWEEN ? AND ? \n");

      con = ConnectionManager.getConnection();

      pstmt = con.prepareStatement(selectQuery.toString());
      pstmt.setInt(1, start);
      pstmt.setInt(2, end);

      rs = pstmt.executeQuery();

      list = new ArrayList();

      while (rs.next()) {
        ResearchBean Bean = new ResearchBean();

        Bean.setSeqno(rs.getInt("BUNHO"));
        Bean.setGroupyn(rs.getString("GBN"));
        if ("Y".equals(Bean.getGroupyn()))
          Bean.setRchgrpno(rs.getInt("RCHNO"));
        else {
          Bean.setRchno(rs.getInt("RCHNO"));
        }
        Bean.setTitle(rs.getString("TITLE"));
        Bean.setColdeptnm(rs.getString("COLDEPTNM"));
        Bean.setStrdt(rs.getString("STRDT"));
        Bean.setEnddt(rs.getString("ENDDT"));

        list.add(Bean);
      }
    }
    catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }

    return list;
  }

  public int getRchParticiTotCnt(String usrid, String deptcd, String gradeName,String getUserGradeId, String schtitle, String groupyn)
    throws Exception
  {
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    int totalCount = 0;
    try
    {
      StringBuffer selectQuery = new StringBuffer();

      selectQuery.append("SELECT COUNT(*)CNT \n");
      selectQuery.append("FROM (SELECT (MAX(SEQ)OVER()) CNT, A1.* ");
      selectQuery.append("      FROM (SELECT ROWNUM SEQ, A2.*  ");
      selectQuery.append("             FROM ( \n");
      if (!"Y".equals(groupyn)) {
        selectQuery.append("                   SELECT DISTINCT 'N' GBN, T1.RCHNO, T1.TITLE, COLDEPTNM, REPLACE(SUBSTR(T1.STRDT,6,5),'-','/') STRDT,  REPLACE(SUBSTR(T1.ENDDT,6,5),'-','/') ENDDT, T1.CRTDT, T1.UPTDT \n");
        selectQuery.append("                     FROM RCHMST T1, RCHDEPT T2, RCHOTHERTARGET T3 \n");
        selectQuery.append("                    WHERE T1.RCHNO = T2.RCHNO(+) \n");
        selectQuery.append("                      AND T1.RCHNO = T3.RCHNO(+) \n");
        selectQuery.append("						AND (T1.RCHTARGET1 LIKE '%" + Utils.nullToEmptyString(getUserGradeId) + "%' OR T1.RCHTARGET1 IS NULL) \n");
        selectQuery.append("                      AND (T1.RCHTARGET2 LIKE '%" + Utils.nullToEmptyString(gradeName) + "%' OR T1.RCHTARGET2 IS NULL) \n");
        selectQuery.append("                         AND (T1.LIMIT1 NOT LIKE '%" + Utils.nullToEmptyString(getUserGradeId) + "%' OR T1.LIMIT1 IS NULL) \n");
        selectQuery.append("                         AND (T1.LIMIT2 NOT LIKE '%" + Utils.nullToEmptyString(gradeName) + "%' OR T1.LIMIT2 IS NULL) \n");
        selectQuery.append("\t\t\t\t\t  AND T1.OPENTYPE = '1' \n");
        selectQuery.append("\t\t\t\t\t  AND T1.RANGE = '1' \n");
        selectQuery.append("\t\t\t\t\t  AND T1.GROUPYN LIKE 'N' \n");
        selectQuery.append("\t\t\t\t\t  AND T3.TGTGBN(+) = '1' \n");
        selectQuery.append("                      AND TO_CHAR(SYSDATE,'YYYY-MM-DD')BETWEEN T1.STRDT AND T1.ENDDT \n");
        selectQuery.append("                      AND T1.TITLE LIKE '%" + Utils.nullToEmptyString(schtitle) + "%' \n");
        selectQuery.append("                      AND ((CASE NVL(T2.TGTCODE, '0') WHEN '0' THEN 1 WHEN '" + deptcd + "' THEN 1 ELSE 0 END = 1 \n");
        selectQuery.append("                           OR CASE NVL(T2.TGTCODE, '0') WHEN '0' THEN 1 WHEN '" + usrid + "' THEN 1 ELSE 0 END = 1) \n");
        selectQuery.append("                          OR CASE NVL(T3.TGTCODE, '0') WHEN '0' THEN 1 WHEN (SELECT GRADE_ID FROM USR WHERE USER_ID = '" + usrid + "') THEN 1 ELSE 0 END = 1) \n");
        selectQuery.append("                      AND 0 < (SELECT DECODE(LIMITCOUNT, 0, COUNT(DISTINCT ANSUSRSEQ) + 1, LIMITCOUNT) - COUNT(DISTINCT ANSUSRSEQ) ");
        selectQuery.append("                               FROM RCHMST RM, RCHANS RA ");
        selectQuery.append("                               WHERE RM.RCHNO = RA.RCHNO(+) ");
        selectQuery.append("                               AND RM.RCHNO = T1.RCHNO ");
        selectQuery.append("                               GROUP BY LIMITCOUNT) ");
        selectQuery.append("                      AND 0 = CASE DUPLICHECK WHEN '2' THEN (SELECT COUNT(*) FROM RCHANS \n");
        selectQuery.append("                                                             WHERE T1.RCHNO = RCHNO \n");
        selectQuery.append("                                                             AND CRTUSRID = '" + usrid + "') ELSE 0 END \n");
      }

      if ((!"Y".equals(groupyn)) && (!"N".equals(groupyn))) {
        selectQuery.append("                   UNION ALL \n");
      }

      if (!"N".equals(groupyn)) {
        selectQuery.append("                   SELECT DISTINCT 'Y' GBN, T1.RCHGRPNO RCHNO, T1.TITLE, COLDEPTNM, REPLACE(SUBSTR(T1.STRDT,6,5),'-','/') STRDT,  REPLACE(SUBSTR(T1.ENDDT,6,5),'-','/') ENDDT, T1.CRTDT, T1.UPTDT \n");
        selectQuery.append("                     FROM RCHGRPMST T1, RCHGRPDEPT T2, RCHGRPOTHERTARGET T3 \n");
        selectQuery.append("                    WHERE T1.RCHGRPNO = T2.RCHGRPNO(+) \n");
        selectQuery.append("                      AND T1.RCHGRPNO = T3.RCHGRPNO(+) \n");
        selectQuery.append("\t\t\t\t\t  AND T1.OPENTYPE = '1' \n");
        selectQuery.append("\t\t\t\t\t  AND T1.RANGE = '1' \n");
        selectQuery.append("\t\t\t\t\t  AND T1.GROUPYN LIKE '%' \n");
        selectQuery.append("\t\t\t\t\t  AND T3.TGTGBN(+) = '1' \n");
        selectQuery.append("                      AND TO_CHAR(SYSDATE,'YYYY-MM-DD')BETWEEN T1.STRDT AND T1.ENDDT \n");
        selectQuery.append("                      AND T1.TITLE LIKE '%" + Utils.nullToEmptyString(schtitle) + "%' \n");
        selectQuery.append("                      AND ((CASE NVL(T2.TGTCODE, '0') WHEN '0' THEN 1 WHEN '" + deptcd + "' THEN 1 ELSE 0 END = 1 \n");
        selectQuery.append("                           OR CASE NVL(T2.TGTCODE, '0') WHEN '0' THEN 1 WHEN '" + usrid + "' THEN 1 ELSE 0 END = 1) \n");
        selectQuery.append("                          OR CASE NVL(T3.TGTCODE, '0') WHEN '0' THEN 1 WHEN (SELECT GRADE_ID FROM USR WHERE USER_ID = '" + usrid + "') THEN 1 ELSE 0 END = 1) \n");
        selectQuery.append("                      AND TRIM(RCHNOLIST) IS NOT NULL \n");
      }

      selectQuery.append("\t\t\t\t    ORDER BY ENDDT DESC, CRTDT DESC, UPTDT DESC) A2) A1) X \n");

      con = ConnectionManager.getConnection();

      pstmt = con.prepareStatement(selectQuery.toString());

      rs = pstmt.executeQuery();

      if (rs.next())
        totalCount = rs.getInt(1);
    }
    catch (Exception e)
    {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }
    return totalCount;
  }

  public int rchAnsSave(List ansList, int rchno, String usrid, String usrnm)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int[] ret = (int[])null;
    int result = 0;
    int maxseq = 0;

    StringBuffer selectQuery1 = new StringBuffer();
    StringBuffer selectQuery2 = new StringBuffer();
    StringBuffer deleteQuery = new StringBuffer();
    StringBuffer updateQuery = new StringBuffer();
    String insertQuery = "";

    selectQuery1.append("\n SELECT DISTINCT ANSUSRSEQ FROM RCHANS WHERE RCHNO = ? AND CRTUSRID = ?");
    selectQuery2.append("\n SELECT NVL(MAX(ANSUSRSEQ),0) FROM RCHANS WHERE RCHNO = ? ");

    deleteQuery.append("\n DELETE FROM RCHANS WHERE RCHNO = ? AND CRTUSRID = ?");

    insertQuery = "INSERT INTO RCHANS (RCHNO,\tANSUSRSEQ,\tFORMSEQ,\tANSCONT,\tOTHER,  CRTDT,\tCRTUSRID,\tCRTUSRNM) VALUES(?, ?, ?, ?, ?,        TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), '" + 
      usrid + "', '" + usrnm + "') ";

    updateQuery.append("\n UPDATE RCHMST SET ANSCOUNT = (SELECT COUNT(DISTINCT ANSUSRSEQ) FROM RCHANS WHERE RCHNO = ?) WHERE RCHNO = ? ");
    try {
      con = ConnectionManager.getConnection();
      con.setAutoCommit(false);

      pstmt = con.prepareStatement(selectQuery1.toString());
      pstmt.setInt(1, rchno);
      pstmt.setString(2, usrid);

      rs = pstmt.executeQuery();

      if ((rs.next()) && (rs.getInt(1) > 1)) {
        maxseq = rs.getInt(1);

        ConnectionManager.close(pstmt, rs);
        pstmt = con.prepareStatement(deleteQuery.toString());

        pstmt.setInt(1, rchno);
        pstmt.setString(2, usrid);

        pstmt.executeUpdate();
      } else {
        ConnectionManager.close(pstmt, rs);

        pstmt = con.prepareStatement(selectQuery2.toString());
        pstmt.setInt(1, rchno);

        rs = pstmt.executeQuery();
        if (rs.next()) {
          maxseq = rs.getInt(1) + 1;
        }
      }

      ConnectionManager.close(pstmt, rs);

      ResearchAnsBean ansBean = null;

      pstmt = con.prepareStatement(insertQuery);
      for (int j = 0; j < ansList.size(); j++) {
        ansBean = (ResearchAnsBean)ansList.get(j);

        pstmt.setInt(1, rchno);
        pstmt.setInt(2, maxseq);
        pstmt.setInt(3, ansBean.getFormseq());
        pstmt.setString(4, ansBean.getAnscont());
        pstmt.setString(5, ansBean.getOther());

        pstmt.addBatch();
      }

      ret = pstmt.executeBatch();
      result += ret.length;

      ConnectionManager.close(pstmt);

      pstmt = con.prepareStatement(updateQuery.toString());

      pstmt.setInt(1, rchno);
      pstmt.setInt(2, rchno);

      result += pstmt.executeUpdate();
      ConnectionManager.close(pstmt);

      con.commit();
    } catch (SQLException e) {
      con.rollback();
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      con.setAutoCommit(true);
      ConnectionManager.close(con, pstmt, rs);
    }

    return result;
  }

  public int rchChoice(int rchno, String sessionId, String usrid, String usrnm, String deptcd, String deptnm, String coltel, ServletContext context, String saveDir)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    int result = 0;
    try
    {
      con = ConnectionManager.getConnection();
      con.setAutoCommit(false);

      StringBuffer insertQuery2 = new StringBuffer();
      insertQuery2.append("\n INSERT INTO RCHMST_TEMP ");
      insertQuery2.append("\n SELECT '" + sessionId + "', TITLE, STRDT, ENDDT, '" + deptcd + "', ");
      insertQuery2.append("\n        '" + deptnm + "', '" + coltel + "', '" + usrid + "', '" + usrnm + "', SUMMARY, EXHIBIT, OPENTYPE, RANGE, IMGPREVIEW, DUPLICHECK, LIMITCOUNT, GROUPYN, TGTDEPTNM, ");
      insertQuery2.append("\n        HEADCONT, TAILCONT, 0, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), '" + usrid + "', '', '',OTHERTGTNM, LIMIT1, LIMIT2, RCHTARGET1, RCHTARGET2 ");
      insertQuery2.append("\n   FROM RCHMST ");
      insertQuery2.append("\n  WHERE RCHNO = ? ");

      pstmt = con.prepareStatement(insertQuery2.toString());
      pstmt.setInt(1, rchno);

      result = pstmt.executeUpdate();
      if (pstmt != null)
        try {
          pstmt.close();
          pstmt = null;
        }
        catch (SQLException localSQLException)
        {
        }
      StringBuffer selectQuery3 = new StringBuffer();
      selectQuery3.append("\n INSERT INTO RCHSUB_TEMP ");
      selectQuery3.append("\n SELECT '" + sessionId + "', FORMSEQ, FORMGRP, FORMTITLE, FORMTYPE, SECURITY, EXAMTYPE, REQUIRE ");
      selectQuery3.append("\n   FROM RCHSUB ");
      selectQuery3.append("\n  WHERE RCHNO = ? ");

      pstmt = con.prepareStatement(selectQuery3.toString());
      pstmt.setInt(1, rchno);

      result += pstmt.executeUpdate();
      if (pstmt != null)
        try {
          pstmt.close();
          pstmt = null;
        }
        catch (SQLException localSQLException1)
        {
        }
      StringBuffer selectQuery4 = new StringBuffer();
      selectQuery4.append("\n INSERT INTO RCHEXAM_TEMP ");
      selectQuery4.append("\n SELECT '" + sessionId + "', FORMSEQ, EXAMSEQ, EXAMCONT ");
      selectQuery4.append("\n   FROM RCHEXAM ");
      selectQuery4.append("\n  WHERE RCHNO = ? ");
      pstmt = con.prepareStatement(selectQuery4.toString());
      pstmt.setInt(1, rchno);

      result += pstmt.executeUpdate();
      if (pstmt != null)
        try {
          pstmt.close();
          pstmt = null;
        }
        catch (SQLException localSQLException2)
        {
        }
      StringBuffer selectQuery5 = new StringBuffer();
      selectQuery5.append("\n INSERT INTO RCHDEPT_TEMP ");
      selectQuery5.append("\n SELECT '" + sessionId + "', TGTCODE, TGTNAME, TGTGBN ");
      selectQuery5.append("\n   FROM RCHDEPT ");
      selectQuery5.append("\n  WHERE RCHNO = ? ");
      pstmt = con.prepareStatement(selectQuery5.toString());
      pstmt.setInt(1, rchno);

      result += pstmt.executeUpdate();
      if (pstmt != null)
        try {
          pstmt.close();
          pstmt = null;
        }
        catch (SQLException localSQLException3)
        {
        }
      StringBuffer selectQuery6 = new StringBuffer();
      selectQuery6.append("\n INSERT INTO RCHDEPTLIST_TEMP ");
      selectQuery6.append("\n SELECT '" + sessionId + "', SEQ, GRPCD, GRPNM, GRPGBN ");
      selectQuery6.append("\n   FROM RCHDEPTLIST ");
      selectQuery6.append("\n  WHERE RCHNO = ? ");
      pstmt = con.prepareStatement(selectQuery6.toString());
      pstmt.setInt(1, rchno);

      result += pstmt.executeUpdate();
      if (pstmt != null)
        try {
          pstmt.close();
          pstmt = null;
        }
        catch (SQLException localSQLException4)
        {
        }
      StringBuffer selectQuery7 = new StringBuffer();
      selectQuery7.append("\n INSERT INTO RCHOTHERTARGET_TEMP ");
      selectQuery7.append("\n SELECT '" + sessionId + "', TGTCODE, TGTNAME, TGTGBN ");
      selectQuery7.append("\n   FROM RCHOTHERTARGET ");
      selectQuery7.append("\n  WHERE RCHNO = ? ");
      pstmt = con.prepareStatement(selectQuery7.toString());
      pstmt.setInt(1, rchno);

      result += pstmt.executeUpdate();
      if (pstmt != null)
        try {
          pstmt.close();
          pstmt = null;
        }
        catch (SQLException localSQLException5)
        {
        }
      result += copyRchSubExamFile(con, sessionId, rchno, context, saveDir, "VIEW");

      con.commit();
    } catch (Exception e) {
      con.rollback();
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt);
      throw e;
    } finally {
      con.setAutoCommit(true);
      ConnectionManager.close(con, pstmt);
    }
    return result;
  }

  public int rchGrpChoice(int rchgrpno, String sessionId, String usrid, String usrnm, String deptcd, String deptnm, String coltel)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    int result = 0;
    try
    {
      con = ConnectionManager.getConnection();
      con.setAutoCommit(false);

      StringBuffer insertQuery2 = new StringBuffer();
      insertQuery2.append("\n INSERT INTO RCHGRPMST_TEMP ");
      insertQuery2.append("\n SELECT '" + sessionId + "', TITLE, STRDT, ENDDT, '" + deptcd + "', ");
      insertQuery2.append("\n        '" + deptnm + "', '" + coltel + "', '" + usrid + "', '" + usrnm + "', SUMMARY, EXHIBIT, OPENTYPE, RANGE, IMGPREVIEW, DUPLICHECK, LIMITCOUNT, GROUPYN, RCHNOLIST, TGTDEPTNM, ");
      insertQuery2.append("\n        HEADCONT, TAILCONT, 0, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), '" + usrid + "', '', '',OTHERTGTNM, LIMIT1, LIMIT2 ");
      insertQuery2.append("\n   FROM RCHGRPMST ");
      insertQuery2.append("\n  WHERE RCHGRPNO = ? ");

      pstmt = con.prepareStatement(insertQuery2.toString());
      pstmt.setInt(1, rchgrpno);

      result = pstmt.executeUpdate();
      if (pstmt != null)
        try {
          pstmt.close();
          pstmt = null;
        }
        catch (SQLException localSQLException)
        {
        }
      StringBuffer selectQuery5 = new StringBuffer();
      selectQuery5.append("\n INSERT INTO RCHGRPDEPT_TEMP ");
      selectQuery5.append("\n SELECT '" + sessionId + "', TGTCODE, TGTNAME, TGTGBN ");
      selectQuery5.append("\n   FROM RCHGRPDEPT ");
      selectQuery5.append("\n  WHERE RCHGRPNO = ? ");
      pstmt = con.prepareStatement(selectQuery5.toString());
      pstmt.setInt(1, rchgrpno);

      result += pstmt.executeUpdate();
      if (pstmt != null)
        try {
          pstmt.close();
          pstmt = null;
        }
        catch (SQLException localSQLException1)
        {
        }
      StringBuffer selectQuery6 = new StringBuffer();
      selectQuery6.append("\n INSERT INTO RCHGRPDEPTLIST_TEMP ");
      selectQuery6.append("\n SELECT '" + sessionId + "', SEQ, GRPCD, GRPNM, GRPGBN ");
      selectQuery6.append("\n   FROM RCHGRPDEPTLIST ");
      selectQuery6.append("\n  WHERE RCHGRPNO = ? ");
      pstmt = con.prepareStatement(selectQuery6.toString());
      pstmt.setInt(1, rchgrpno);

      result += pstmt.executeUpdate();
      if (pstmt != null)
        try {
          pstmt.close();
          pstmt = null;
        }
        catch (SQLException localSQLException2)
        {
        }
      StringBuffer selectQuery7 = new StringBuffer();
      selectQuery7.append("\n INSERT INTO RCHGRPOTHERTARGET_TEMP ");
      selectQuery7.append("\n SELECT '" + sessionId + "', TGTCODE, TGTNAME, TGTGBN ");
      selectQuery7.append("\n   FROM RCHGRPOTHERTARGET ");
      selectQuery7.append("\n  WHERE RCHGRPNO = ? ");
      pstmt = con.prepareStatement(selectQuery7.toString());
      pstmt.setInt(1, rchgrpno);

      result += pstmt.executeUpdate();
      if (pstmt != null)
        try {
          pstmt.close();
          pstmt = null;
        }
        catch (SQLException localSQLException3) {
        }
      con.commit();
    } catch (Exception e) {
      con.rollback();
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt);
      throw e;
    } finally {
      con.setAutoCommit(true);
      ConnectionManager.close(con, pstmt);
    }
    return result;
  }

  public int copyRchSubExamFile(Connection conn, String sessionId, int rchno, ServletContext context, String saveDir, String mode)
    throws Exception
  {
    int result = 0;

    List rchSubFile = null;
    if (mode.equals("VIEW"))
      rchSubFile = getRchSubFile(conn, "", rchno);
    else if (mode.equals("SAVE")) {
      rchSubFile = getRchSubFile(conn, sessionId, 0);
    }
    for (int i = 0; (rchSubFile != null) && (i < rchSubFile.size()); i++) {
      ResearchSubBean rchSubBean = (ResearchSubBean)rchSubFile.get(i);

      String newFilenm = "";
      try {
        newFilenm = FileManager.doFileCopy(context.getRealPath(rchSubBean.getFilenm()));
      } catch (FileNotFoundException e) {
        continue;
      }

      if (!newFilenm.equals("")) {
        FileBean subFileBean = new FileBean();
        subFileBean.setFileseq(rchSubBean.getFileseq());
        subFileBean.setFilenm(saveDir + newFilenm);
        subFileBean.setOriginfilenm(rchSubBean.getOriginfilenm());
        subFileBean.setFilesize(rchSubBean.getFilesize());
        subFileBean.setExt(rchSubBean.getExt());

        int addResult = 0;
        if (mode.equals("VIEW"))
          addResult = addRchSubFile(conn, sessionId, 0, rchSubBean.getFormseq(), subFileBean);
        else if (mode.equals("SAVE")) {
          addResult = addRchSubFile(conn, "", rchno, rchSubBean.getFormseq(), subFileBean);
        }
        if (addResult == 0) {
          throw new Exception("첨부실패(저장):DB업데이트");
        }
        result += addResult;
      } else {
        throw new Exception("첨부실패(저장):파일업로드");
      }
    }

    List rchExamFile = null;
    if (mode.equals("VIEW"))
      rchExamFile = getRchExamFile(conn, "", rchno, 0);
    else if (mode.equals("SAVE")) {
      rchExamFile = getRchExamFile(conn, sessionId, 0, 0);
    }
    for (int i = 0; (rchExamFile != null) && (i < rchExamFile.size()); i++) {
      ResearchExamBean rchExamBean = (ResearchExamBean)rchExamFile.get(i);

      String newFilenm = "";
      try {
        newFilenm = FileManager.doFileCopy(context.getRealPath(rchExamBean.getFilenm()));
      } catch (FileNotFoundException e) {
        continue;
      }

      if (!newFilenm.equals("")) {
        FileBean subFileBean = new FileBean();
        subFileBean.setFileseq(rchExamBean.getFileseq());
        subFileBean.setFilenm(saveDir + newFilenm);
        subFileBean.setOriginfilenm(rchExamBean.getOriginfilenm());
        subFileBean.setFilesize(rchExamBean.getFilesize());
        subFileBean.setExt(rchExamBean.getExt());

        int addResult = 0;
        if (mode.equals("VIEW"))
          addResult = addRchExamFile(conn, sessionId, 0, rchExamBean.getFormseq(), rchExamBean.getExamseq(), subFileBean);
        else if (mode.equals("SAVE")) {
          addResult = addRchExamFile(conn, "", rchno, rchExamBean.getFormseq(), rchExamBean.getExamseq(), subFileBean);
        }
        if (addResult == 0) {
          throw new Exception("첨부실패(저장):DB업데이트");
        }
        result += addResult;
      } else {
        throw new Exception("첨부실패(저장):파일업로드");
      }
    }

    return result;
  }

  public void delResearchTempData(Connection conn, String sessionid)
    throws Exception
  {
    PreparedStatement pstmt = null;
    try
    {
      String[] table = { "RCHMST_TEMP", "RCHSUB_TEMP", "RCHEXAM_TEMP", 
        "RCHDEPT_TEMP", "RCHDEPTLIST_TEMP", "RCHOTHERTARGET_TEMP" };

      for (int i = 0; i < table.length; i++)
      {
        String sql = 
          "DELETE FROM " + 
          table[i] + " " + 
          "WHERE SESSIONID LIKE ?";

        pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, sessionid);
        pstmt.executeUpdate();
        ConnectionManager.close(pstmt);
      }
    }
    catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(pstmt);
      throw e;
    } finally {
      ConnectionManager.close(pstmt);
    }
  }

  public void delResearchGrpTempData(Connection conn, String sessionid)
    throws Exception
  {
    PreparedStatement pstmt = null;
    try
    {
      String[] table = { "RCHGRPMST_TEMP", "RCHGRPDEPT_TEMP", "RCHGRPDEPTLIST_TEMP", "RCHGRPOTHERTARGET_TEMP" };

      for (int i = 0; i < table.length; i++)
      {
        String sql = 
          "DELETE FROM " + 
          table[i] + " " + 
          "WHERE SESSIONID LIKE ?";

        pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, sessionid);
        pstmt.executeUpdate();
        ConnectionManager.close(pstmt);
      }
    }
    catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(pstmt);
      throw e;
    } finally {
      ConnectionManager.close(pstmt);
    }
  }

  public int ResearchDlete(Connection conn, int rchno)
    throws Exception
  {
    PreparedStatement pstmt = null;

    int result = 0;
    try
    {
      conn = ConnectionManager.getConnection();

      String[] table = { "RCHMST", "RCHSUB", "RCHEXAM", "RCHANS", 
        "RCHDEPT", "RCHDEPTLIST", "RCHOTHERTARGET" };

      for (int i = 0; i < table.length; i++)
      {
        String sql = 
          "DELETE FROM " + 
          table[i] + " " + 
          "WHERE RCHNO LIKE ?";

        pstmt = conn.prepareStatement(sql);

        pstmt.setInt(1, rchno);
        result += pstmt.executeUpdate();
        ConnectionManager.close(pstmt);
      }
    }
    catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(pstmt);
      throw e;
    } finally {
      ConnectionManager.close(pstmt);
    }

    return result;
  }

  public int ResearchGrpDlete(Connection conn, int rchgrpno)
    throws Exception
  {
    PreparedStatement pstmt = null;

    int result = 0;
    try
    {
      conn = ConnectionManager.getConnection();

      String[] table = { "RCHGRPMST", "RCHGRPDEPT", "RCHGRPDEPTLIST", "RCHGRPOTHERTARGET" };

      for (int i = 0; i < table.length; i++)
      {
        String sql = 
          "DELETE FROM " + 
          table[i] + " " + 
          "WHERE RCHGRPNO LIKE ?";

        pstmt = conn.prepareStatement(sql);

        pstmt.setInt(1, rchgrpno);
        result += pstmt.executeUpdate();
        ConnectionManager.close(pstmt);
      }
    }
    catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(pstmt);
      throw e;
    } finally {
      ConnectionManager.close(pstmt);
    }

    return result;
  }

  public int rchClose(int rchno, String userid)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    int result = 0;
    try
    {
      StringBuffer updateQuery = new StringBuffer();

      updateQuery.append("UPDATE RCHMST SET ENDDT = TO_CHAR(SYSDATE - 1, 'YYYY-MM-DD') ");
      updateQuery.append("WHERE RCHNO = ? ");
      updateQuery.append("  AND CHRGUSRID = ? ");

      con = ConnectionManager.getConnection();
      pstmt = con.prepareStatement(updateQuery.toString());

      pstmt.setInt(1, rchno);
      pstmt.setString(2, userid);

      result = pstmt.executeUpdate();
    }
    catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt);
    }
    return result;
  }

  public int rchGrpClose(int rchgrpno, String userid)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    int result = 0;
    try
    {
      StringBuffer updateQuery = new StringBuffer();

      updateQuery.append("UPDATE RCHGRPMST SET ENDDT = TO_CHAR(SYSDATE - 1, 'YYYY-MM-DD') ");
      updateQuery.append("WHERE RCHGRPNO = ? ");
      updateQuery.append("  AND CHRGUSRID = ? ");

      con = ConnectionManager.getConnection();
      pstmt = con.prepareStatement(updateQuery.toString());

      pstmt.setInt(1, rchgrpno);
      pstmt.setString(2, userid);

      result = pstmt.executeUpdate();
    }
    catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt);
    }
    return result;
  }

  public int rchState(int rchno, String deptcd, String userid, int grpDuplicheck, int grpLimitcount)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int result = 0;
    try
    {
      StringBuffer selectQuery = new StringBuffer();

      selectQuery.append("SELECT COUNT(T1.RCHNO) \n");
      selectQuery.append("FROM RCHMST T1, RCHDEPT T2, RCHOTHERTARGET T3 \n");
      selectQuery.append("WHERE T1.RCHNO = T2.RCHNO(+) \n");
      selectQuery.append("AND T1.RCHNO = T3.RCHNO(+) \n");
      selectQuery.append("AND T1.RCHNO = ? \n");
      if ((grpDuplicheck == -1) && (grpLimitcount == -1)) {
        selectQuery.append("AND TO_CHAR(SYSDATE,'YYYY-MM-DD') BETWEEN STRDT AND ENDDT \n");
        selectQuery.append("AND ((CASE NVL(T2.TGTCODE, '0') WHEN '0' THEN 1 WHEN '" + deptcd + "' THEN 1 ELSE 0 END = 1 \n");
        selectQuery.append("     OR CASE NVL(T2.TGTCODE, '0') WHEN '0' THEN 1 WHEN '" + userid + "' THEN 1 ELSE 0 END = 1) \n");
        selectQuery.append("    OR CASE NVL(T3.TGTCODE, '0') WHEN '0' THEN 1 WHEN (SELECT GRADE_ID FROM USR WHERE USER_ID = '" + userid + "') THEN 1 ELSE 0 END = 1) \n");
      }

      con = ConnectionManager.getConnection();
      pstmt = con.prepareStatement(selectQuery.toString());

      pstmt.setInt(1, rchno);

      rs = pstmt.executeQuery();

      if (rs.next()) {
        result = rs.getInt(1);
      }

      if (result > 0) {
        selectQuery.delete(0, selectQuery.capacity());
        if (grpLimitcount == -1)
          selectQuery.append("\n SELECT DECODE(LIMITCOUNT, 0, COUNT(DISTINCT ANSUSRSEQ) + 1, LIMITCOUNT) - COUNT(DISTINCT ANSUSRSEQ) ");
        else {
          selectQuery.append("\n SELECT DECODE(" + grpLimitcount + ", 0, COUNT(DISTINCT ANSUSRSEQ) + 1, " + grpLimitcount + ") - COUNT(DISTINCT ANSUSRSEQ) ");
        }
        selectQuery.append("\n FROM RCHMST RM, RCHANS RA ");
        selectQuery.append("\n WHERE RM.RCHNO = RA.RCHNO(+)  ");
        selectQuery.append("\n AND RM.RCHNO = ? ");
        selectQuery.append("\n GROUP BY LIMITCOUNT ");

        ConnectionManager.close(pstmt, rs);
        pstmt = con.prepareStatement(selectQuery.toString());

        pstmt.setInt(1, rchno);

        rs = pstmt.executeQuery();

        if (rs.next()) {
          result = rs.getInt(1);
          if (result < 0) result = 0;
        }
      }

      if (result > 0) {
        selectQuery.delete(0, selectQuery.capacity());
        selectQuery.append("\n SELECT COUNT(R1.RCHNO)");
        selectQuery.append("\n FROM RCHANS R1, RCHMST R2");
        selectQuery.append("\n WHERE R1.RCHNO = R2.RCHNO");
        if (grpDuplicheck == -1)
          selectQuery.append("\n AND R2.DUPLICHECK = '2'");
        else {
          selectQuery.append("\n AND '" + grpDuplicheck + "' = '2'");
        }
        selectQuery.append("\n AND R1.RCHNO = ? AND R1.CRTUSRID = ?");

        ConnectionManager.close(pstmt, rs);
        pstmt = con.prepareStatement(selectQuery.toString());

        pstmt.setInt(1, rchno);
        pstmt.setString(2, userid);

        rs = pstmt.executeQuery();

        if (rs.next()) {
          result = rs.getInt(1);

          if (result > 0)
            result = -1;
          else
            result = 1;
        }
      }
    }
    catch (Exception e)
    {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }
    return result;
  }

  public int rchGrpState(int rchgrpno, String deptcd, String userid)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int result = 0;
    try
    {
      StringBuffer selectQuery = new StringBuffer();

      selectQuery.append("SELECT COUNT(T1.RCHGRPNO) \n");
      selectQuery.append("FROM RCHGRPMST T1, RCHGRPDEPT T2, RCHGRPOTHERTARGET T3 \n");
      selectQuery.append("WHERE T1.RCHGRPNO = T2.RCHGRPNO(+) \n");
      selectQuery.append("AND T1.RCHGRPNO = T3.RCHGRPNO(+) \n");
      selectQuery.append("AND TO_CHAR(SYSDATE,'YYYY-MM-DD') BETWEEN STRDT AND ENDDT \n");
      selectQuery.append("AND T1.RCHGRPNO = ? \n");
      selectQuery.append("AND ((CASE NVL(T2.TGTCODE, '0') WHEN '0' THEN 1 WHEN '" + deptcd + "' THEN 1 ELSE 0 END = 1 \n");
      selectQuery.append("     OR CASE NVL(T2.TGTCODE, '0') WHEN '0' THEN 1 WHEN '" + userid + "' THEN 1 ELSE 0 END = 1) \n");
      selectQuery.append("    OR CASE NVL(T3.TGTCODE, '0') WHEN '0' THEN 1 WHEN (SELECT GRADE_ID FROM USR WHERE USER_ID = '" + userid + "') THEN 1 ELSE 0 END = 1) \n");

      con = ConnectionManager.getConnection();
      pstmt = con.prepareStatement(selectQuery.toString());

      pstmt.setInt(1, rchgrpno);

      rs = pstmt.executeQuery();

      if (rs.next())
        result = rs.getInt(1);
    }
    catch (Exception e)
    {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }
    return result;
  }

  public List getRchOutsideList()
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List list = null;
    try
    {
      StringBuffer selectQuery = new StringBuffer();

      selectQuery.append(" SELECT T1.RCHNO     FROM RCHMST T1   WHERE T1.OPENTYPE = '1'\tAND T1.RANGE LIKE '2%' \n");

      selectQuery.append("    AND TO_CHAR(SYSDATE,'YYYY-MM-DD')BETWEEN T1.STRDT AND T1.ENDDT \n");
      selectQuery.append("  ORDER BY CRTDT DESC \n");

      con = ConnectionManager.getConnection();

      pstmt = con.prepareStatement(selectQuery.toString());

      rs = pstmt.executeQuery();

      list = new ArrayList();

      while (rs.next()) {
        ResearchAnsBean Bean = new ResearchAnsBean();
        Bean.setRchno(rs.getInt("RCHNO"));

        list.add(Bean);
      }
    }
    catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }
    return list;
  }

  public int getRchSubExamcount(int rchno, String sessionId)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int result = -1;
    try
    {
      StringBuffer selectQuery = new StringBuffer();

      if (rchno == 0) {
        selectQuery.append("SELECT MAX(COUNT(EXAMSEQ)) \n");
        selectQuery.append("FROM RCHEXAM_TEMP \n");
        selectQuery.append("WHERE SESSIONID LIKE ? \n");
        selectQuery.append("GROUP BY FORMSEQ \n");
      } else {
        selectQuery.append("SELECT MAX(COUNT(EXAMSEQ)) \n");
        selectQuery.append("FROM RCHEXAM \n");
        selectQuery.append("WHERE RCHNO = ? \n");
        selectQuery.append("GROUP BY FORMSEQ \n");
      }

      con = ConnectionManager.getConnection();

      pstmt = con.prepareStatement(selectQuery.toString());

      if (rchno == 0)
        pstmt.setString(1, sessionId);
      else {
        pstmt.setInt(1, rchno);
      }

      rs = pstmt.executeQuery();

      if (rs.next())
        result = rs.getInt(1);
    }
    catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }
    return result;
  }

  public String getSearch(String groupyn, String sch_title, String sch_deptcd, String sch_userid)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String result = "";

    label630: 
    try { StringBuffer selectQuery = new StringBuffer();
      selectQuery.append(" SELECT NVL(SUM(COUNT(*)),0) AS CNT\t\t\t\t\t\n");
      selectQuery.append(" FROM RCHMST\t\t\t\t\t\t\t\t \t    \n");
      selectQuery.append(" WHERE COLDEPTCD LIKE  '%" + sch_deptcd + "%' \t\t\t\n");
      selectQuery.append(" AND CHRGUSRID LIKE    '%" + sch_userid + "%'\t\t\t\n");
      selectQuery.append(" AND TITLE LIKE '%" + sch_title + "%'\t\t\t\t\t\n");
      selectQuery.append(" AND GROUPYN LIKE '%" + groupyn + "'\t\t\t\t\t\t\n");
      selectQuery.append(" GROUP BY COLDEPTNM, CHRGUSRNM, TITLE\t\t\t\t\n");

      con = ConnectionManager.getConnection();
      pstmt = con.prepareStatement(selectQuery.toString());
      rs = pstmt.executeQuery();

      if (rs.next()) {
        if (rs.getInt("CNT") != 1) break label630;
        ConnectionManager.close(pstmt, rs);

        if ((!"".equals(sch_deptcd)) && (sch_deptcd != null)) {
          selectQuery = new StringBuffer();
          selectQuery.append(" SELECT COLDEPTCD\t\t\t\t\t\t \n");
          selectQuery.append(" FROM RCHMST\t\t\t\t\t\t\t \n");
          selectQuery.append(" WHERE COLDEPTCD LIKE '%" + sch_deptcd + "%' \n");
          selectQuery.append(" AND TITLE LIKE '%" + sch_title + "%' \t\t \n");
          selectQuery.append(" AND GROUPYN LIKE '%" + groupyn + "' \t\t \n");

          pstmt = con.prepareStatement(selectQuery.toString());
          rs = pstmt.executeQuery();

          if (rs.next()) {
            result = rs.getString("COLDEPTCD");
          }
          ConnectionManager.close(pstmt, rs);
        }

        if ((!"".equals(sch_userid)) && (sch_userid != null)) {
          selectQuery = new StringBuffer();
          selectQuery.append(" SELECT CHRGUSRID\t\t\t\t\t\t \t\n");
          selectQuery.append(" FROM RCHMST\t\t\t\t\t\t\t\t\n");
          selectQuery.append(" WHERE CHRGUSRID LIKE '%" + sch_userid + "%' \t\n");
          selectQuery.append(" AND TITLE LIKE '%" + sch_title + "%' \t\t \t\n");
          selectQuery.append(" AND GROUPYN LIKE '%" + groupyn + "'\t\t \t\n");

          pstmt = con.prepareStatement(selectQuery.toString());
          rs = pstmt.executeQuery();

          if (rs.next()) {
            result = rs.getString("CHRGUSRID");
          }
          ConnectionManager.close(pstmt, rs);
        }

      }

    }
    catch (SQLException e)
    {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }

    return result;
  }

  public String getGradeName(String user_id) throws Exception {		
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			

			selectQuery.append(" SELECT GRADE_NAME, USER_ID 							\n");
			selectQuery.append(" FROM USR 									\n");
			selectQuery.append(" WHERE UPPER(USER_ID) = UPPER(NVL(?, '')) 	\n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, user_id);
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				result = rs.getString(1);
			}			
			
		} catch(Exception e) {
			
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return result;
	}
  
  public String getUserDeptName(String user_id) throws Exception {		
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			

			selectQuery.append(" SELECT DEPT_NAME, USER_ID 							\n");
			selectQuery.append(" FROM USR 									\n");
			selectQuery.append(" WHERE UPPER(USER_ID) = UPPER(NVL(?, '')) 	\n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, user_id);
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				result = rs.getString(1);
			}			
			
		} catch(Exception e) {
			
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return result;
	}
  public String getUserGradeId(String user_id) throws Exception {		
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			

			selectQuery.append(" SELECT GRADE_ID, USER_ID 							\n");
			selectQuery.append(" FROM USR 									\n");
			selectQuery.append(" WHERE UPPER(USER_ID) = UPPER(NVL(?, '')) 	\n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, user_id);
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				result = rs.getString(1);
			}			
			
		} catch(Exception e) {
			
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return result;
	}
  
  public String getLimit1Chk(int researchNo) throws Exception {		
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			

			selectQuery.append(" SELECT LIMIT1 							\n");
			selectQuery.append(" FROM RCHMST 									\n");
			selectQuery.append(" WHERE RCHNO = ? 	\n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, researchNo);
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				result = rs.getString(1);
			}			
			
		} catch(Exception e) {
			
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return result;
	}
  public String getLimit2Chk(int researchNo) throws Exception {		
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			

			selectQuery.append(" SELECT LIMIT2 							\n");
			selectQuery.append(" FROM RCHMST 									\n");
			selectQuery.append(" WHERE RCHNO = ? 	\n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setInt(1, researchNo);
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				result = rs.getString(1);
			}			
			
		} catch(Exception e) {
			
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return result;
	}
  public String getLimit1Chk1(String sessionId) throws Exception {		
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			

			selectQuery.append(" SELECT LIMIT1 							\n");
			selectQuery.append(" FROM RCHMST_TEMP 									\n");
			selectQuery.append(" WHERE SESSIONID = ? 	\n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, sessionId);
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				result = rs.getString(1);
			}			
			
		} catch(Exception e) {
			
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return result;
	}
  public String getLimit2Chk2(String sessionId) throws Exception {		
		Connection con = null;	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		
		try {
			StringBuffer selectQuery = new StringBuffer();
			

			selectQuery.append(" SELECT LIMIT2 							\n");
			selectQuery.append(" FROM RCHMST_TEMP 									\n");
			selectQuery.append(" WHERE SESSIONID = ? 	\n");
			
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, sessionId);
						
			rs = pstmt.executeQuery();					
			
			if ( rs.next() ){
				result = rs.getString(1);
			}			
			
		} catch(Exception e) {
			
			ConnectionManager.close(con,pstmt,rs);
			throw e;
		} finally {
			ConnectionManager.close(con,pstmt,rs);
		}	
		
		return result;
	}
  public String getGrpSearch(String groupyn, String sch_title, String sch_deptcd, String sch_userid)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String result = "";

    label630: 
    try { StringBuffer selectQuery = new StringBuffer();
      selectQuery.append(" SELECT NVL(SUM(COUNT(*)),0) AS CNT\t\t\t\t\t\n");
      selectQuery.append(" FROM RCHGRPMST\t\t\t\t\t\t\t\t \t    \n");
      selectQuery.append(" WHERE COLDEPTCD LIKE  '%" + sch_deptcd + "%' \t\t\t\n");
      selectQuery.append(" AND CHRGUSRID LIKE    '%" + sch_userid + "%'\t\t\t\n");
      selectQuery.append(" AND TITLE LIKE '%" + sch_title + "%'\t\t\t\t\t\n");
      selectQuery.append(" AND GROUPYN LIKE '%" + groupyn + "'\t\t\t\t\t\t\n");
      selectQuery.append(" GROUP BY COLDEPTNM, CHRGUSRNM, TITLE\t\t\t\t\n");

      con = ConnectionManager.getConnection();
      pstmt = con.prepareStatement(selectQuery.toString());
      rs = pstmt.executeQuery();

      if (rs.next()) {
        if (rs.getInt("CNT") != 1) break label630;
        ConnectionManager.close(pstmt, rs);

        if ((!"".equals(sch_deptcd)) && (sch_deptcd != null)) {
          selectQuery = new StringBuffer();
          selectQuery.append(" SELECT COLDEPTCD\t\t\t\t\t\t \n");
          selectQuery.append(" FROM RCHGRPMST\t\t\t\t\t\t\t \n");
          selectQuery.append(" WHERE COLDEPTCD LIKE '%" + sch_deptcd + "%' \n");
          selectQuery.append(" AND TITLE LIKE '%" + sch_title + "%' \t\t \n");
          selectQuery.append(" AND GROUPYN LIKE '%" + groupyn + "' \t\t \n");

          pstmt = con.prepareStatement(selectQuery.toString());
          rs = pstmt.executeQuery();

          if (rs.next()) {
            result = rs.getString("COLDEPTCD");
          }
          ConnectionManager.close(pstmt, rs);
        }

        if ((!"".equals(sch_userid)) && (sch_userid != null)) {
          selectQuery = new StringBuffer();
          selectQuery.append(" SELECT CHRGUSRID\t\t\t\t\t\t \t\n");
          selectQuery.append(" FROM RCHGRPMST\t\t\t\t\t\t\t\t\n");
          selectQuery.append(" WHERE CHRGUSRID LIKE '%" + sch_userid + "%' \t\n");
          selectQuery.append(" AND TITLE LIKE '%" + sch_title + "%' \t\t \t\n");
          selectQuery.append(" AND GROUPYN LIKE '%" + groupyn + "'\t\t \t\n");

          pstmt = con.prepareStatement(selectQuery.toString());
          rs = pstmt.executeQuery();

          if (rs.next()) {
            result = rs.getString("CHRGUSRID");
          }
          ConnectionManager.close(pstmt, rs);
        }

      }

    }
    catch (SQLException e)
    {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }

    return result;
  }

  public int checkExhibit(int rchno)
    throws Exception
  {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int result = 0;
    try
    {
      StringBuffer sql = new StringBuffer();
      sql.append("SELECT COUNT(*) \n");
      sql.append("FROM RCHMST \n");
      sql.append("WHERE RCHNO = ? \n");
      sql.append("  AND EXHIBIT = '1' \n");
      sql.append("  AND RANGE LIKE '2%' \n");

      con = ConnectionManager.getConnection();
      pstmt = con.prepareStatement(sql.toString());

      pstmt.setInt(1, rchno);
      rs = pstmt.executeQuery();

      if (rs.next())
        result = rs.getInt(1);
    }
    catch (Exception e) {
      logger.error("ERROR", e);
      ConnectionManager.close(con, pstmt, rs);
      throw e;
    } finally {
      ConnectionManager.close(con, pstmt, rs);
    }

    return result;
  }
  
  public int checkLimit(int rchno, String userDeptName, String getUserGradeId)
		    throws Exception
		  {
		    Connection con = null;
		    PreparedStatement pstmt = null;
		    ResultSet rs = null;
		    int result = 0;
		    try
		    {
		      StringBuffer sql = new StringBuffer();
		      sql.append("SELECT COUNT(*) \n");
		      sql.append("FROM RCHMST \n");
		      sql.append("WHERE RCHNO = ? \n");
		      sql.append("  AND (LIMIT1 LIKE '%"+userDeptName+"%' OR LIMIT2 LIKE '%"+getUserGradeId+"%')\n");
		      con = ConnectionManager.getConnection();
		      pstmt = con.prepareStatement(sql.toString());

		      pstmt.setInt(1, rchno);
		      rs = pstmt.executeQuery();

		      if (rs.next())
		        result = rs.getInt(1);
		    }
		    catch (Exception e) {
		      logger.error("ERROR", e);
		      ConnectionManager.close(con, pstmt, rs);
		      throw e;
		    } finally {
		      ConnectionManager.close(con, pstmt, rs);
		    }

		    return result;
		  }
  public int checkRchTarget(int rchno, String userDeptName, String getUserGradeId)
		    throws Exception
		  {
		    Connection con = null;
		    PreparedStatement pstmt = null;
		    ResultSet rs = null;
		    int result = 0;
		    try
		    {
		      StringBuffer sql = new StringBuffer();
		      sql.append("SELECT COUNT(*) \n");
		      sql.append("FROM RCHMST \n");
		      sql.append("WHERE RCHNO = ? \n");
		      sql.append("  AND (RCHTARGET1  LIKE '%"+ Utils.nullToEmptyString(userDeptName)+"%' OR RCHTARGET2  LIKE '%"+Utils.nullToEmptyString(getUserGradeId)+"%'\n");
		      sql.append("OR ( RCHTARGET1 IS NULL  AND RCHTARGET2 IS NULL)) \n" );
		      
		      con = ConnectionManager.getConnection();
		      pstmt = con.prepareStatement(sql.toString());

		      pstmt.setInt(1, rchno);
		      rs = pstmt.executeQuery();

		      if (rs.next())
		        result = rs.getInt(1);
		    }
		    catch (Exception e) {
		      logger.error("ERROR", e);
		      ConnectionManager.close(con, pstmt, rs);
		      throw e;
		    } finally {
		      ConnectionManager.close(con, pstmt, rs);
		    }

		    return result;
		  }
}