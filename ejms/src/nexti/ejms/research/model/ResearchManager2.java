/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� manager
 * ����:
 */
package nexti.ejms.research.model;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.common.appInfo;
import nexti.ejms.research.form.ResearchForm;
import nexti.ejms.util.Base64;
import nexti.ejms.util.FileManager;
import nexti.ejms.util.HttpClientHp;
import nexti.ejms.util.Utils;
import nexti.ejms.util.XlsWritePOI;

public class ResearchManager2 {
	
	private static Logger logger = Logger.getLogger(ResearchManager2.class);
	
	private static ResearchManager2 instance = null;
	private static ResearchDAO dao = null;
	
	private ResearchManager2() {
		
	}
	
	public static ResearchManager2 instance() {
		
		if(instance == null)
			instance = new ResearchManager2();
		return instance;
	}

	private ResearchDAO getResearchDAO() {
		
		if(dao == null)
			dao = new ResearchDAO(); 
		return dao;
	}

	public int getRchSubCount(int rchno) throws Exception {
		return getResearchDAO().getRchSubCount(rchno);
	}
	
	public List getRchIndividualList(int rchno) throws Exception {
		return getResearchDAO().getRchIndividualList(rchno);
	}
	
	public boolean getRchResult(HttpServletResponse response, int rchno, String title) throws Exception {
		Connection con = null;
		boolean result = false;
		
		String fileName = title + ".xls";
		ArrayList sheetName = new ArrayList();
		ArrayList sheetData = new ArrayList();
		
		try {
			con = ConnectionManager.getConnection();
			
			List objectResultList = getResearchDAO().getRchObjectResultList(con, rchno);
			int objectResultFormseqCount = getResearchDAO().getRchObjectResultFormseqMaxCount(con, rchno);
			int objectResultExamseqCount = getResearchDAO().getRchObjectResultExamseqMaxCount(con, rchno);
			String[][] data1 = new String[objectResultFormseqCount + 1][objectResultExamseqCount * 2 + 1];
			
			List subjectResultList = getResearchDAO().getRchSubjectResultList(con, rchno);
			int subjectResultFormseqCount = getResearchDAO().getRchSubjectResultFormseqCount(con, rchno);
			int subjectResultExamseqMaxCount = getResearchDAO().getRchSubjectResultExamseqMaxCount(con, rchno);
			String[][] data2 = new String[subjectResultExamseqMaxCount + 1][subjectResultFormseqCount + 1];
			
			int rowIndex = 0;
			int colIndex = 0;
			int prevSeq = 0;
			
			int examseq = 1;
			data1[rowIndex][colIndex] = "����";
			for ( colIndex = 0; colIndex < objectResultExamseqCount * 2; colIndex += 2 ) {
				data1[rowIndex][colIndex+1] = "����" + examseq + " �����ڼ�";
				data1[rowIndex][colIndex+2] = "����" + examseq + " �������";
				examseq++;
			}
			
			for ( int i = 0; objectResultList != null && i < objectResultList.size(); i++ ) {
				HashMap hs = (HashMap)objectResultList.get(i);
				int formseq = Integer.parseInt((String)hs.get("FORMSEQ"));
				String anscont = (String)hs.get("ANSCONT");
				String rate = (String)hs.get("RATE");

				if ( prevSeq != formseq) {
					rowIndex++;
					colIndex = 0;
					prevSeq = formseq;
				}
				if ( colIndex == 0 ) {
					data1[rowIndex][colIndex++] = "����" + formseq;
				}
				data1[rowIndex][colIndex++] = anscont;
				data1[rowIndex][colIndex++] = rate;
			}
			
			rowIndex = 0;
			colIndex = 0;
			prevSeq = 0;
			
			data2[rowIndex][colIndex] = "����";
			for ( rowIndex = 0; rowIndex < subjectResultExamseqMaxCount; rowIndex++ ) {
				data2[rowIndex + 1][colIndex] = "�亯" + (rowIndex + 1);
			}
			
			for ( int i = 0; subjectResultList != null && i < subjectResultList.size(); i++ ) {
				HashMap hs = (HashMap)subjectResultList.get(i);
				int formseq = Integer.parseInt((String)hs.get("FORMSEQ"));
				String anscont = (String)hs.get("ANSCONT");

				if ( prevSeq != formseq) {
					rowIndex = 0;
					colIndex++;
					prevSeq = formseq;
				}
				if ( rowIndex == 0 ) {
					data2[rowIndex++][colIndex] = "����" + formseq;
				}
				data2[rowIndex++][colIndex] = anscont;
			}
			
			sheetName.add("������");
			sheetData.add(data1);
			sheetName.add("�ְ���");
			sheetData.add(data2);
			
			result = XlsWritePOI.writeXls(response, fileName, sheetName, sheetData);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(con);
		}
		
		return result;
	}
	
	/**
	 * �������� ����
	 * @param rchno
	 * @return
	 * @throws Exception
	 */
	public int ResearchDelete(int rchno, ServletContext context) throws Exception{

		Connection conn = null;
		
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection(false);
			
			List rchSubList = getResearchDAO().getRchSubFile(conn, "", rchno);
			
			for ( int i = 0; rchSubList != null && i < rchSubList.size(); i++ ) {
				ResearchSubBean rchSubBean = (ResearchSubBean)rchSubList.get(i);
				getResearchDAO().delRchSubFile(conn, "", rchno, rchSubBean.getFormseq(), rchSubBean.getFileseq());
				
				String delFile = rchSubBean.getFilenm();
				if ( delFile != null && delFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delFile));
				}
			}
			
			List rchExamList = getResearchDAO().getRchExamFile(conn, "", rchno, 0);
			
			for ( int j = 0; rchExamList != null && j < rchExamList.size(); j++ ) {
				ResearchExamBean rchExamBean = (ResearchExamBean)rchExamList.get(j);
				getResearchDAO().delRchExamFile(conn, "", rchno, rchExamBean.getFormseq(), rchExamBean.getExamseq(), rchExamBean.getFileseq());
				
				String delFile = rchExamBean.getFilenm();
				if ( delFile != null && delFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delFile));
				}
			}
		
			result = getResearchDAO().ResearchDlete(conn, rchno);
		
			conn.commit();
		} catch(Exception e) {
			conn.rollback();
			logger.error("ERROR", e);
			ConnectionManager.close(conn);
			throw e;
		} finally {
			ConnectionManager.close(conn);
		}
		
		return result;
	}
	
	/**
	 * ��������׷� ����
	 * @param rchgrpno
	 * @return
	 * @throws Exception
	 */
	public int ResearchGrpDelete(int rchgrpno) throws Exception{

		Connection conn = null;
		
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection(false);
			
			result = getResearchDAO().ResearchGrpDlete(conn, rchgrpno);
		
			conn.commit();
		} catch(Exception e) {
			conn.rollback();
			logger.error("ERROR", e);
			ConnectionManager.close(conn);
			throw e;
		} finally {
			ConnectionManager.close(conn);
		}
		
		return result;
	}
	
	/**
	 * �������� �ӽ����̺� ����
	 * @param sessionid
	 * @throws Exception
	 */
	public void delResearchTempData(String sessionid, ServletContext context) throws Exception {
		
		Connection conn = null;
		
		try {
			conn = ConnectionManager.getConnection(false);
			
			List rchSubList = getResearchDAO().getRchSubFile(conn, sessionid, 0);
			
			for ( int i = 0; rchSubList != null && i < rchSubList.size(); i++ ) {
				ResearchSubBean rchSubBean = (ResearchSubBean)rchSubList.get(i);
				getResearchDAO().delRchSubFile(conn, sessionid, 0, rchSubBean.getFormseq(), rchSubBean.getFileseq());
				
				String delSubFile = rchSubBean.getFilenm();
				if ( delSubFile != null && delSubFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delSubFile));
				}
			}
			
			List rchExamList = getResearchDAO().getRchExamFile(conn, sessionid, 0, 0);
			
			for ( int j = 0; rchExamList != null && j < rchExamList.size(); j++ ) {
				ResearchExamBean rchExamBean = (ResearchExamBean)rchExamList.get(j);
				getResearchDAO().delRchExamFile(conn, sessionid, 0, rchExamBean.getFormseq(), rchExamBean.getExamseq(), rchExamBean.getFileseq());
				
				String delExamFile = rchExamBean.getFilenm();
				if ( delExamFile != null && delExamFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delExamFile));
				}
			}
		
			getResearchDAO().delResearchTempData(conn, sessionid);
		
			conn.commit();
		} catch(Exception e) {
			conn.rollback();
			logger.error("ERROR", e);
			ConnectionManager.close(conn);
			throw e;
		} finally {
			ConnectionManager.close(conn);
		}
	}

	/**
	 * ��������׷� �ӽ����̺� ����
	 * @param sessionid
	 * @throws Exception
	 */
	public void delResearchGrpTempData(String sessionid) throws Exception {
		
		Connection conn = null;
		
		try {
			conn = ConnectionManager.getConnection(false);
		
			getResearchDAO().delResearchGrpTempData(conn, sessionid);
		
			conn.commit();
		} catch(Exception e) {
			conn.rollback();
			logger.error("ERROR", e);
			ConnectionManager.close(conn);
			throw e;
		} finally {
			ConnectionManager.close(conn);
		}
	}	
	
	/**
	 * ���������� ���
	 * @param usrid
	 * @param sch_title
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List getResearchMyList(String usrid, String deptcd, String initentry, String isSysMgr, String groupyn, String sch_title, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, int start, int end) throws Exception{
		List result = null;
		
		result = getResearchDAO().getResearchMyList(usrid, deptcd, initentry, isSysMgr, groupyn, sch_title, sch_deptcd, sch_deptnm, sch_userid, sch_usernm, start, end);
		
		return result;		
	}
	
	/**
	 * ����������׷� ���
	 * @param usrid
	 * @param sch_title
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List getResearchGrpMyList(String usrid, String deptcd, String initentry, String isSysMgr, String sch_title, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, int start, int end) throws Exception{
		List result = null;
		
		result = getResearchDAO().getResearchGrpMyList(usrid, deptcd, initentry, isSysMgr, sch_title, sch_deptcd, sch_deptnm, sch_userid, sch_usernm, start, end);
		
		return result;		
	}
	
	/**
	 * ������� ��������
	 * 	/**
	 * 
	 * @param rchno
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public List getResultSubList(int rchno, String range, RchSearchBean schbean, String sessionId) throws Exception{		
		return getResearchDAO().getResultSubList(rchno, range, schbean, sessionId); 
	}
	
	/**
	 * �� �������� ���� ��������
	 * @param usrid
	 * @param deptcd
	 * @param sch_title
	 * @return
	 * @throws Exception
	 */
	public int getResearchTotCnt(String usrid, String deptcd, String initentry, String isSysMgr, String groupyn, String sch_title,String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, String mode) throws Exception{
		int result = 0;
		
		result = getResearchDAO().getResearchTotCnt(usrid, deptcd, initentry, isSysMgr, groupyn, sch_title, sch_deptcd, sch_deptnm, sch_userid, sch_usernm, mode);
		
		return result;
	}
	
	/**
	 * �� �����׷����� ���� ��������
	 * @param usrid
	 * @param deptcd
	 * @param sch_title
	 * @return
	 * @throws Exception
	 */
	public int getResearchGrpTotCnt(String usrid, String deptcd, String initentry, String isSysMgr, String sch_title,String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm, String mode) throws Exception{
		int result = 0;
		
		result = getResearchDAO().getResearchGrpTotCnt(usrid, deptcd, initentry, isSysMgr, sch_title, sch_deptcd, sch_deptnm, sch_userid, sch_usernm, mode);
		
		return result;
	}
	
	/**
	 * �� ������� ���� ��������
	 * @param usrid
	 * @param deptcd
	 * @param sch_title
	 * @return
	 * @throws Exception
	 */
	public int getResearchMyTotCnt(String usrid, String deptcd, String sch_title, String mode) throws Exception{
		int result = 0;
		
		result = getResearchDAO().getResearchMyTotCnt(usrid, deptcd, sch_title, mode);
		
		return result;
	}
	
	
	/*
	 * �����亯�� ��� ��������
	 */
	public List getRchAnsusrList(int rchno) throws Exception {
		return getResearchDAO().getRchAnsusrList(rchno);
	}
	
	/**
	 * �������� ������ ��������
	 * @param rchno
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public ResearchBean getRchMst(int rchno, String sessionId) throws Exception{		
		return getResearchDAO().getRchMst(rchno, sessionId); 
	}
	
	/**
	 * ��������׷� ������ ��������
	 * @param rchno
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public ResearchBean getRchGrpMst(int rchgrpno, String sessionId) throws Exception{		
		return getResearchDAO().getRchGrpMst(rchgrpno, sessionId); 
	}
	
	/**
	 * �������� ÷������ ����
	 * @param rchExamBean
	 * @param context
	 * @throws Exception
	 */
	public void delRchSubExamFile(ResearchExamBean rchExamBean, ServletContext context) throws Exception {
		
		Connection conn = null;
		
		try {
			conn = ConnectionManager.getConnection(false);
			
			delRchSubExamFile(conn, rchExamBean, context);
			
			conn.commit();
		} catch(Exception e) {
			try {
				conn.rollback();
			} catch(Exception ex) {
				logger.error("ERROR", ex);
			}
			ConnectionManager.close(conn);
			throw e;
		} finally {
			ConnectionManager.close(conn);
		}
	}
	
	/**
	 * �������� ÷������ ����
	 * @param conn
	 * @param rchExamBean
	 * @param context
	 * @throws Exception
	 */
	public void delRchSubExamFile(Connection conn, ResearchExamBean rchExBean, ServletContext context) throws Exception {
		
		String sessionId = rchExBean.getSessionId();
		int rchno = rchExBean.getRchno();
		int formseq = rchExBean.getFormseq();
		int examseq = rchExBean.getExamseq();
			
		if ( examseq == 0 ) {	//����÷������ ����
			ResearchSubBean rchSubBean = getResearchDAO().getRchSubFile(conn, sessionId, rchno, formseq);
			
			if ( rchSubBean != null ) {
				getResearchDAO().delRchSubFile(conn, sessionId, rchno, formseq, rchSubBean.getFileseq());
				
				String delFile = rchSubBean.getFilenm();
				if ( delFile != null && delFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delFile));
				}
			}
		} else {	//����÷�����ϻ���
			ResearchExamBean rchExamBean = getResearchDAO().getRchExamFile(conn, sessionId, rchno, formseq, examseq);
			
			if ( rchExamBean != null ) {
				getResearchDAO().delRchExamFile(conn, sessionId, rchno, formseq, examseq, rchExamBean.getFileseq());
				
				String delFile = rchExamBean.getFilenm();
				if ( delFile != null && delFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delFile));
				}
			}
		}
	}
	
	/**
	 * 
	 * @param rchno
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public List getRchSubList(int rchno, String sessionId, int examcount, String mode) throws Exception{		
		return getResearchDAO().getRchSubList(rchno, sessionId, examcount, mode); 
	}	
	
	public FormFile[] orderExamcontFile(ResearchForm rchForm) throws Exception {
		
		int formcount = rchForm.getFormcount();
		int examcount = rchForm.getExamcount();
		
		List subList = null;
		subList = getRchSubList(rchForm.getRchno(), rchForm.getSessionId(), examcount, "");
		int subcount = subList.size();
		
		if ( rchForm.getListrch() == null || subcount == 0 ) return null;
		
		FormFile[] result = new FormFile[formcount * examcount];
		FormFile[] examcontFile = rchForm.getExamcontFile();
		String[] examcontFileYN = rchForm.getExamcontFileYN();
		String[] examcont = rchForm.getExamcont();
		
		int prevExamcount = 0;
		while ( prevExamcount < rchForm.getExamcontFile().length
				&& rchForm.getExamcontFile()[prevExamcount] != null ) {
			prevExamcount++;
		}
		prevExamcount = prevExamcount / subcount;
		
		for ( int i = 0; i < rchForm.getListrch().size(); i++ ) {
			int offsetIdx = i * examcount;
			for ( int j = 0; j < prevExamcount; j++ ) {
				try {
					int idx = i * prevExamcount + j;
					
					if ( prevExamcount < examcount && j >= prevExamcount && j < examcount ) {
						offsetIdx++;
					} else if ( prevExamcount > examcount && j>= examcount) {
						continue;
					} else if ( examcont[idx] != null && examcont[idx].trim().equals("") == false &&
							examcontFile[idx].getFileName().trim().equals("") == false &&
							examcontFileYN[idx] != null ) {
						result[offsetIdx++] = examcontFile[idx];
					} else if ( examcont[idx] != null && examcont[idx].trim().equals("") == false &&
							examcontFileYN[idx] == null ) {
						offsetIdx++;
					} else if (examcontFile[idx].getFileName().trim().equals("") == true &&
							examcontFileYN[idx] != null ) {
						offsetIdx++;
					}
				} catch ( Exception e ) {}
			}
		}

		return result;
	}
	
	/**
	 * �������� ����
	 * @param rchForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public int ResearchSave(ResearchForm rchForm, ServletContext context, String saveDir) throws Exception {
		int result = -1;
		int resultrchno = 0;
		
		int rchno = rchForm.getRchno();
		String sessionId =  rchForm.getSessionId();
		String mode = rchForm.getMode();
		
		ResearchBean Bean = new ResearchBean();
		
		BeanUtils.copyProperties(Bean, rchForm);
	    
		Bean.setListrch(RchSubList(rchForm));
		
		rchForm.setListrch(Bean.getListrch());
		
		Bean.setExamcontFile(orderExamcontFile(rchForm));

		result = getResearchDAO().rchAllSave(Bean, rchForm, context, saveDir);
		
		if ( "del".equals(mode) ) {
			//�׸����
			result = getResearchDAO().rchDelSub(rchno, sessionId, rchForm.getDelseq(), context);
		} else if ( "add".equals(mode) ) {
			if ( getResearchDAO().getMaxSubSeq(rchno, sessionId) < 50 ) {
				//�׸��߰�
				result = getResearchDAO().insAddSub(rchno, sessionId);
			}
		} else if ( "make".equals(mode) ) {
			//�׸񸸵��
			result = getResearchDAO().insMakeSub(rchno, sessionId, Bean.getFormcount());			
		} else if ( "prev".equals(mode) ) {
			//�̸�����
		} else if ( "temp".equals(mode) || "comp".equals(mode) ) {
			//�ӽ�����, �Ϸ�
			if ( rchno == 0 ) {
				resultrchno = getResearchDAO().rchInsComp(sessionId, Bean, context, saveDir);
			} else {
				resultrchno = rchno;
			}
		}
		
		if ( result >= 0 ) { 
			//����
			result = 0;
			
			//�������׼��� ����
			if ( rchForm.getChangeFormseq() != null && rchForm.getFormseq().length == rchForm.getChangeFormseq().length) {
				if ( "make".equals(mode) || "add".equals(mode) || "prev".equals(mode) || "comp".equals(mode) ) {
					getResearchDAO().changeFormseq(resultrchno, sessionId, rchForm.getChangeFormseq());
				} else if ( "temp".equals(mode) ) {
					//�ӽ������� �������̺�� �ӽ����̺� ��� �����ؾ� ��
					getResearchDAO().changeFormseq(0, sessionId, rchForm.getChangeFormseq());
					getResearchDAO().changeFormseq(resultrchno, sessionId, rchForm.getChangeFormseq());
				}
			}
		}
		
		//�Ϸ�ÿ� �޾ƿ� ��û��� ��ȣ�� ������ ��Ĺ�ȣ�� Return
		if ( resultrchno > 0 ) {
			
			int outResult = 0;
			if ( ("temp".equals(mode) || "comp".equals(mode)) && "2".equals(rchForm.getRange()) ) {
				outResult = ResearhOutside(Bean, resultrchno);
			}
			
			if ( outResult < 0 ) {
				result = outResult;
			} else {
				result = resultrchno;
			}
		}
		
		return result;
	}
	
	/**
	 * ��������׷� ����
	 * @param rchForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public int ResearchGrpSave(ResearchForm rchForm) throws Exception {
		int result = -1;
		int resultrchgrpno = 0;
		
		int rchgrpno = rchForm.getRchgrpno();
		String sessionId =  rchForm.getSessionId();
		String mode = rchForm.getMode();
		
		ResearchBean Bean = new ResearchBean();
		
		BeanUtils.copyProperties(Bean, rchForm);

		result = getResearchDAO().rchGrpAllSave(Bean, rchForm);
		
		if ( "prev".equals(mode) ) {
			//�̸�����
		} else if ( "temp".equals(mode) || "comp".equals(mode) ) {
			//�ӽ�����, �Ϸ�
			if ( rchgrpno == 0 ) {
				resultrchgrpno = getResearchDAO().rchGrpInsComp(sessionId, Bean);
			} else {
				resultrchgrpno = rchgrpno;
			}
		}
		
		if ( result >= 0 ) { 
			//����
			result = 0;
		}
		
		//�Ϸ�ÿ� �޾ƿ� ��û��� ��ȣ�� ������ ��Ĺ�ȣ�� Return
		if ( resultrchgrpno > 0 ) {
			
			int outResult = 0;
			if ( ("temp".equals(mode) || "comp".equals(mode)) && "2".equals(rchForm.getRange()) ) {
				outResult = ResearhGrpOutside(Bean, resultrchgrpno);
			}
			
			if ( outResult < 0 ) {
				result = outResult;
			} else {
				result = resultrchgrpno;
			}
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param rchno
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public List RchSubList(ResearchForm rchForm) throws Exception{	
		List subList = null;
		int subseq = 0;
		int[]    formseq = rchForm.getFormseq();
		String[] formgrp = rchForm.getFormgrp();		//��������
		String[] formtitle = rchForm.getFormtitle();	//��������
		String[] formtype = rchForm.getFormtype();   	//�Է�����(1:���� 2:���� 3:�ܴ� 4:�幮)
		String[] security = rchForm.getSecurity();		//����ó������(Y:����ó��, N:����)
		String[] examtype = rchForm.getExamtype();		//��Ÿ�亯��
	
		subList = new ArrayList();
		
		if ( formseq != null ) {
			int formCount = rchForm.getFormcount();
			if ( formCount > formseq.length  ) formCount = formseq.length;
			
			for ( int i=0; i < formCount; i++ ) {
				ResearchSubBean bean = new ResearchSubBean();
				if(formseq[i] == 0){
					subseq = i+1;
				}else{
					subseq = formseq[i];
				}
				bean.setFormseq(subseq);
				bean.setFormgrp(formgrp[i]);
				bean.setFormtitle(formtitle[i]);
				bean.setFormtype(formtype[i]);
				
				if(formtype[i].equals("03")){
					bean.setExamtype("N");
					if(security != null ){
						for(int j=0; j<security.length; j++){
							if(security[j].equals(String.valueOf(i))){
								bean.setSecurity("Y");
								break;
							}
						}
					}else{
						bean.setSecurity("N");
					}
				}else if(formtype[i].equals("04")){
					bean.setExamtype("N");
					bean.setSecurity("N");
				}else{
					if(examtype != null ){
						for(int j=0; j<examtype.length; j++){
							if(examtype[j].equals(String.valueOf(i))){
								bean.setExamtype("Y");
								break;
							}
						}
					}else{
						bean.setExamtype("N");
					}					
					bean.setSecurity("N");
					bean.setExamList(RchExamList(rchForm, i, subseq));
				}	
				subList.add(bean);
			}
		}
		
		return subList;
	}
	
	/**
	 * 
	 * @param rchno
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public List RchExamList(ResearchForm rchForm, int seq, int formseq) throws Exception{	
		List examList = null;
		
		if ( rchForm.getExamcont() != null ) {			
			String[] examcont = rchForm.getExamcont();	//��������
			
			int examcount = rchForm.getExamcount();
			if ( examcount > rchForm.getExamcont().length  ) examcount = rchForm.getExamcont().length;
			
			List subList = null;
			subList = getRchSubList(rchForm.getRchno(), rchForm.getSessionId(), examcount, "");			
			int subcount = subList.size();
			
			int start = seq * (examcont.length / subcount);
			int end   = start + examcount;
			int examseq = 0;
			int count = 0;
			
			examList = new ArrayList();
			ResearchExamBean bean = null;
			for(int i=start;i<end;i++){			
				bean = new ResearchExamBean();
				if(count < examcont.length / subcount && !"".equals(examcont[i].trim())){
					examseq = examseq + 1;
					bean.setFormseq(formseq);
					bean.setExamseq(examseq);
					bean.setExamcont(examcont[i].trim());
	
					examList.add(bean);
				}
				count++;
			}
			
			while ( examList.size() < examcount ) {
				bean = new ResearchExamBean();
				examseq = examseq + 1;
				bean.setFormseq(formseq);
				bean.setExamseq(examseq);
				bean.setExamcont("");
	
				examList.add(bean);
			}
		}

		return examList;		
	}	
	
	/**
	 * ������������ ��������
	 * @param Bean
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List getUsedRchList(ResearchBean Bean, int start, int end) throws Exception {
		return getResearchDAO().getUsedRchList(Bean, start, end);
	}
	
	/**
	 * ������������׷� ��������
	 * @param Bean
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List getUsedRchGrpList(ResearchBean Bean, int start, int end) throws Exception {
		return getResearchDAO().getUsedRchGrpList(Bean, start, end);
	}
	
	/**
	 * ������������ �������� �Ǽ�
	 * @param Bean
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public int getUsedRchTotCnt(ResearchBean Bean) throws Exception {
		return getResearchDAO().getUsedRchTotCnt(Bean);
	}
	
	/**
	 * ������������׷� �������� �Ǽ�
	 * @param Bean
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public int getUsedRchGrpTotCnt(ResearchBean Bean) throws Exception {
		return getResearchDAO().getUsedRchGrpTotCnt(Bean);
	}
	
	/**
	 * 
	 * @param Bean
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List getRchParticiList(String usrid, String deptcd, String schtitle, String groupyn, int start, int end) throws Exception {
		return getResearchDAO().getRchParticiList(usrid, deptcd, schtitle, groupyn, start, end);
	}
	
	/**
	 * 
	 * @param Bean
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public int getRchParticiTotCnt(String usrid, String deptcd, String schtitle, String groupyn) throws Exception {
		return getResearchDAO().getRchParticiTotCnt(usrid, deptcd, schtitle, groupyn);
	}	
	
	/**
	 * 
	 * @param Bean
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public int rchAnsSave(List ansList, int rchno, String usrid, String usrnm) throws Exception {
		int result = 0;
		
		result =  getResearchDAO().rchAnsSave(ansList, rchno, usrid, usrnm);
		
		return result;
	}
	
	/**
	 * �������� �����������簡������
	 * @param Bean
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public int rchChoice(int rchno, String sessionId, String usrid, String usrnm, String deptcd, String deptnm, String coltel, ServletContext context, String saveDir) throws Exception {
		return getResearchDAO().rchChoice(rchno, sessionId, usrid, usrnm, deptcd, deptnm, coltel, context, saveDir);
	}
	
	/**
	 * ��������׷� �����������簡������
	 * @param Bean
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public int rchGrpChoice(int rchgrpno, String sessionId, String usrid, String usrnm, String deptcd, String deptnm, String coltel) throws Exception {
		return getResearchDAO().rchGrpChoice(rchgrpno, sessionId, usrid, usrnm, deptcd, deptnm, coltel);
	}
	
	/**
	 * �ܺθ� �������� ����
	 * @param bean
	 * @param rchno
	 * @return
	 * @throws Exception
	 */
	public int ResearhOutside(ResearchBean bean, int rchno) throws Exception{
		int result = 0;
		
		String urlStr = appInfo.getOutsideurl()+"/outsideRchSave.do";
		HttpClientHp hcp = new HttpClientHp(urlStr);
		
		hcp.setParam("rchno", new Integer(rchno).toString());
		hcp.setParam("title", bean.getTitle());
		hcp.setParam("strdt", bean.getStrdt());
		hcp.setParam("enddt", bean.getEnddt());
		hcp.setParam("coldeptcd", bean.getColdeptcd());
		hcp.setParam("coldeptnm", bean.getColdeptnm());
		hcp.setParam("coldepttel", bean.getColdepttel());
		hcp.setParam("chrgusrid", bean.getChrgusrid());		
		hcp.setParam("chrgusrnm", bean.getChrgusrnm());
		hcp.setParam("summary", bean.getSummary());
		hcp.setParam("exhibit", bean.getExhibit());
		hcp.setParam("opentype", bean.getOpentype());
		hcp.setParam("range", bean.getRange());
		hcp.setParam("rangedetail", bean.getRangedetail());
		hcp.setParam("imgpreview", bean.getImgpreview());
		hcp.setParam("duplicheck", bean.getDuplicheck());
		hcp.setParam("limitcount", new Integer(bean.getLimitcount()).toString());
		hcp.setParam("groupyn", bean.getGroupyn());
		hcp.setParam("headcont", bean.getHeadcont());
		hcp.setParam("tailcont", bean.getTailcont());
		hcp.setParam("anscount", new Integer(bean.getAnscount()).toString());
		hcp.setParam("subsize", new Integer(bean.getListrch().size()).toString());
		for(int i=0; i<bean.getListrch().size(); i++){
			ResearchSubBean subbean = (ResearchSubBean)bean.getListrch().get(i);		
			hcp.setParam("formseq"+i, new Integer(subbean.getFormseq()).toString());
			hcp.setParam("formgrp"+i, subbean.getFormgrp());
			hcp.setParam("formtitle"+i, subbean.getFormtitle());
			hcp.setParam("formtype"+i, subbean.getFormtype());
			hcp.setParam("security"+i, subbean.getSecurity());
			hcp.setParam("examtype"+i, subbean.getExamtype());
			if(subbean.getExamList() != null){
				hcp.setParam("examsize"+i, new Integer(subbean.getExamList().size()).toString());
				for(int j=0; j<subbean.getExamList().size(); j++){
					ResearchExamBean exambean = (ResearchExamBean)subbean.getExamList().get(j);
					hcp.setParam("examseq"+i+""+j, new Integer(exambean.getExamseq()).toString());
					hcp.setParam("examcont"+i+""+j, exambean.getExamcont());
				}
			}
		}
		//������ �ƴѰ�츸 �Ʒ� Ÿ���� ������ ������ rchno ������ ������ ������Ʈ 
		{
			Connection conn = ConnectionManager.getConnection();
			int subFileCount = 0;
			int examFileCount = 0;
			for ( int i = 0; bean.getListrch() != null && i < bean.getListrch().size(); i++ ) {
				ResearchSubBean subbean = (ResearchSubBean)bean.getListrch().get(i);
	
				ResearchSubBean rsBean = getResearchDAO().getRchSubFile(conn, "", rchno, subbean.getFormseq());
				if ( rsBean != null ) {
					hcp.setParam("subfilerchno"+subFileCount, Integer.toString(rchno));
					hcp.setParam("subfileformseq"+subFileCount, Integer.toString(rsBean.getFormseq()));
					hcp.setParam("subfilefileseq"+subFileCount, Integer.toString(rsBean.getFileseq()));
					hcp.setParam("subfilefilenm"+subFileCount, rsBean.getFilenm());
					hcp.setParam("subfileoriginfilenm"+subFileCount, rsBean.getOriginfilenm());
					hcp.setParam("subfilefilesize"+subFileCount, Integer.toString(rsBean.getFilesize()));
					hcp.setParam("subfileext"+subFileCount, rsBean.getExt());
					hcp.setParam("subfileord"+subFileCount, Integer.toString(rsBean.getOrd()));
					hcp.setParam("subfilebase64encoding"+subFileCount, base64EncodeToFile(appInfo.getRootRealPath() + rsBean.getFilenm()));
					subFileCount++;
				}
				
				for ( int j = 0; subbean.getExamList() != null && j < subbean.getExamList().size(); j++ ) {
					ResearchExamBean exambean = (ResearchExamBean)subbean.getExamList().get(j);
					
					ResearchExamBean reBean = getResearchDAO().getRchExamFile(conn, "", rchno, subbean.getFormseq(), exambean.getExamseq());
					if ( reBean != null ) {
						hcp.setParam("examfilerchno"+examFileCount, Integer.toString(rchno));
						hcp.setParam("examfileformseq"+examFileCount, Integer.toString(reBean.getFormseq()));
						hcp.setParam("examfileexamseq"+examFileCount, Integer.toString(reBean.getExamseq()));
						hcp.setParam("examfilefileseq"+examFileCount, Integer.toString(reBean.getFileseq()));
						hcp.setParam("examfilefilenm"+examFileCount, reBean.getFilenm());
						hcp.setParam("examfileoriginfilenm"+examFileCount, reBean.getOriginfilenm());
						hcp.setParam("examfilefilesize"+examFileCount, Integer.toString(reBean.getFilesize()));
						hcp.setParam("examfileext"+examFileCount, reBean.getExt());
						hcp.setParam("examfileord"+examFileCount, Integer.toString(reBean.getOrd()));
						hcp.setParam("examfilebase64encoding"+examFileCount, base64EncodeToFile(appInfo.getRootRealPath() + reBean.getFilenm()));
						examFileCount++;
					}
				}		
			}
			hcp.setParam("subfilecount", Integer.toString(subFileCount));
			hcp.setParam("examfilecount", Integer.toString(examFileCount));
			ConnectionManager.close(conn);
		}
		hcp.setMethodType(1);
		
        int rtnCode = hcp.execute(); // �����ϱ� 
       
        if(rtnCode == 200){
        	result = Integer.parseInt(hcp.getContent(), 10);
        }
        
		return result;
	}
	
	/**
	 * �ܺθ� ��������׷� ����
	 * @param bean
	 * @param rchno
	 * @return
	 * @throws Exception
	 */
	public int ResearhGrpOutside(ResearchBean bean, int rchgrpno) throws Exception{
		int result = 0;
		
		String urlStr = appInfo.getOutsideurl()+"/outsideRchGrpSave.do";
		HttpClientHp hcp = new HttpClientHp(urlStr);
		
		hcp.setParam("rchgrpno", new Integer(rchgrpno).toString());
		hcp.setParam("title", bean.getTitle());
		hcp.setParam("strdt", bean.getStrdt());
		hcp.setParam("enddt", bean.getEnddt());
		hcp.setParam("coldeptcd", bean.getColdeptcd());
		hcp.setParam("coldeptnm", bean.getColdeptnm());
		hcp.setParam("coldepttel", bean.getColdepttel());
		hcp.setParam("chrgusrid", bean.getChrgusrid());		
		hcp.setParam("chrgusrnm", bean.getChrgusrnm());
		hcp.setParam("summary", bean.getSummary());
		hcp.setParam("exhibit", bean.getExhibit());
		hcp.setParam("opentype", bean.getOpentype());
		hcp.setParam("range", bean.getRange());
		hcp.setParam("rangedetail", bean.getRangedetail());
		hcp.setParam("imgpreview", bean.getImgpreview());
		hcp.setParam("duplicheck", bean.getDuplicheck());
		hcp.setParam("limitcount", new Integer(bean.getLimitcount()).toString());
		hcp.setParam("groupyn", bean.getGroupyn());
		hcp.setParam("rchnolist", bean.getRchnolist());
		hcp.setParam("headcont", bean.getHeadcont());
		hcp.setParam("tailcont", bean.getTailcont());
		hcp.setParam("anscount", new Integer(bean.getAnscount()).toString());
		
		hcp.setMethodType(1);
		
        int rtnCode = hcp.execute(); // �����ϱ� 
       
        if(rtnCode == 200){
        	result = Integer.parseInt(hcp.getContent(), 10);
        }
        
		return result;
	}
	
	public String base64EncodeToFile(String fileName) {		
		String result = null;
		
		BufferedInputStream bis = null;
		ByteArrayOutputStream baos = null;
		try {
			byte buffer[] = new byte[4096];
			bis = new BufferedInputStream(new FileInputStream(fileName));
			baos = new ByteArrayOutputStream();
			int cnt;
			while ( (cnt = bis.read(buffer, 0, buffer.length) ) != -1 ) {
				baos.write(buffer, 0, cnt);
			}
			
			result = new String(Base64.encodeBytes(baos.toByteArray()).getBytes("UTF-8"));
		} catch ( Exception e) {
			e.printStackTrace();
		} finally {
			try { bis.close(); } catch ( Exception e ) {}
			try { baos.close(); } catch ( Exception e ) {}
		}
		
		return Utils.nullToEmptyString(result);
	}
	
	/**
	 * ���� ���� 	
	 */
	public int rchClose(int rchno, String userid) throws Exception {
		int result = 0;
		
		result = getResearchDAO().rchClose(rchno, userid);
		
		return result;
	}
	
	/**
	 * �����׷� ���� 	
	 */
	public int rchGrpClose(int rchgrpno, String userid) throws Exception {
		int result = 0;
		
		result = getResearchDAO().rchGrpClose(rchgrpno, userid);
		
		return result;
	}
	
	/**
	 * �������� ó������
	 */
	public int rchState(int rchno, String deptcd, String userid, int grpDuplicheck, int grpLimitcount) throws Exception{
		int result = 0;
		result = getResearchDAO().rchState(rchno, deptcd, userid, grpDuplicheck, grpLimitcount);
		
		return result;
	}
	
	/**
	 * ��������׷� ó������
	 */
	public int rchGrpState(int rchgrpno, String deptcd, String userid) throws Exception{
		int result = 0;
		result = getResearchDAO().rchGrpState(rchgrpno, deptcd, userid);
		
		return result;
	}
	
	/**
	 * 
	 * @param Bean
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List getRchOutsideList() throws Exception {
		return getResearchDAO().getRchOutsideList();
	}
	
	/**
	 * ���״� ���ⰳ�� ��������
	 * @param rchno
	 * @return
	 * @throws Exception
	 */
	public int getRchSubExamcount(int rchno, String sessionId) throws Exception {
		return getResearchDAO().getRchSubExamcount(rchno, sessionId);
	}
	
	/**
	 * �������� - �������ΰ�� �˻� ���ǿ� �ش��ϴ� �� ��������
	 */
	public String getSearch(String groupyn, String sch_title, String sch_deptcd, String sch_userid) throws Exception{
		return getResearchDAO().getSearch(groupyn, sch_title, sch_deptcd, sch_userid);
	}
	
	/**
	 * ��������׷� - �������ΰ�� �˻� ���ǿ� �ش��ϴ� �� ��������
	 */
	public String getGrpSearch(String groupyn, String sch_title, String sch_deptcd, String sch_userid) throws Exception{
		return getResearchDAO().getGrpSearch(groupyn, sch_title, sch_deptcd, sch_userid);
	}
	
	/**
	 * ������������ Ȯ��
	 */
	public int checkExhibit(int rchno) throws Exception {
		int result = 0;
		
		result = getResearchDAO().checkExhibit(rchno);
		
		return result;
	}
}