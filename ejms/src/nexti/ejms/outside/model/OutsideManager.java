/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ܺθ� manager
 * ����:
 */
package nexti.ejms.outside.model;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.common.appInfo;
import nexti.ejms.research.form.ResearchForm;
import nexti.ejms.research.model.RchSearchBean;
import nexti.ejms.research.model.ResearchBean;
import nexti.ejms.research.model.ResearchExamBean;
import nexti.ejms.research.model.ResearchSubBean;
import nexti.ejms.sinchung.form.DataForm;
import nexti.ejms.sinchung.form.SinchungForm;
import nexti.ejms.sinchung.model.ArticleBean;
import nexti.ejms.sinchung.model.FrmBean;
import nexti.ejms.sinchung.model.ReqMstBean;
import nexti.ejms.sinchung.model.ReqSubBean;
import nexti.ejms.sinchung.model.SampleBean;
import nexti.ejms.sinchung.model.SearchBean;
import nexti.ejms.util.Base64;
import nexti.ejms.util.FileManager;
import nexti.ejms.util.HttpClientHp;
import nexti.ejms.util.Utils;

public class OutsideManager {
	
	private static Logger logger = Logger.getLogger(OutsideManager.class);
	
	private static OutsideManager instance = null;
	private static OutsideDAO dao = null;
	
	private OutsideManager() {
		
	}
	
	public static OutsideManager instance() {
		
		if(instance == null)
			instance = new OutsideManager();
		return instance;
	}

	private OutsideDAO getOutsideDAO() {
		
		if(dao == null)
			dao = new OutsideDAO(); 
		return dao;
	}	
	
	/**
	 * �������� �亯�Ǽ�Ȯ��
	 * ��� �������θ� Ȯ���ϱ� ���ؼ�
	 */
	public int rchMstCnt(int rchno) throws Exception{
		int result = 0;
		
		result = getOutsideDAO().rchMstCnt(rchno);
		
		return result;
	}
	
	/**
	 * �������� �亯 ����
	 * @param rchForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public int rchAnsDel(List ansList) throws Exception{
		int result = 0;

		result = getOutsideDAO().rchAnsDel(ansList);

		return result;
	}
	
	/**
	 * �������� ����
	 * @param rchno
	 * @return
	 * @throws Exception
	 */
	public int ResearchDlete(int rchno, ServletContext sContext) throws Exception{
		int result = 0;
		
		result = getOutsideDAO().ResearchDlete(rchno, sContext);
		
		return result;
	}
	
	/**
	 * ��������׷� ����
	 * @param rchno
	 * @return
	 * @throws Exception
	 */
	public int ResearchGrpDlete(int rchgrpno) throws Exception{
		int result = 0;
		
		result = getOutsideDAO().ResearchGrpDlete(rchgrpno);
		
		return result;
	}
	
	/**
	 * �������� ��� ��������
	 * @return
	 * @throws Exception
	 */
	public String getRchResult() throws Exception {
		
		return getOutsideDAO().getRchResult();
	}
	
	/**
	 * �ܺθ� ���������� ���ۿϷ� ó��
	 * @throws Exception
	 */
	public void rchResultTransmitComplete() throws Exception {
		getOutsideDAO().rchResultTransmitComplete();
	}
	
	/**
	 * �ܺθ� ��û����� ���ۿϷ� ó��
	 * @throws Exception
	 */
	public void reqResultTransmitComplete() throws Exception {
		getOutsideDAO().reqResultTransmitComplete();
	}
	
	/**
	 * �ܺθ� ���������� ����Ʈ
	 * @return
	 * @throws Exception
	 */
	public String getRchResultList(String uid, String resident, String committee) throws Exception {
		
		return getOutsideDAO().getRchResultList(uid, resident, committee);
	}
	
	/**
	 * �ܺθ� �������� ����Ʈ
	 * @return
	 * @throws Exception
	 */
	public String getRchList(String syear, String uid, String resident, String committee) throws Exception {
		
		return getOutsideDAO().getRchList(syear, uid, resident, committee);
	}
	
	/**
	 * �ܺθ� ��û�� ����Ʈ
	 * @return
	 * @throws Exception
	 */
	public String getReqList(String syear, String uid, String resident, String committee) throws Exception {
		
		return getOutsideDAO().getReqList(syear, uid, resident, committee);
	}
	
	/**
	 * �������� ���� 
	 * @param rchForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public int ResearchSave(ResearchBean Bean, ServletContext sContext) throws Exception{
		int result = 0;

		result = getOutsideDAO().rchAllSave(Bean, sContext);
		//������ �ƴѰ�츸 �Ʒ� Ÿ���� ������ ������ rchno ������ ������ ������Ʈ 
		{
			if ( result > 0 ) {
				//���� ���ε�
				Calendar cal = Calendar.getInstance();
				String saveDir = appInfo.getResearchSampleDir() + cal.get(Calendar.YEAR) + "/";
				File saveFolder = new File(appInfo.getRootRealPath() + saveDir);
				if(!saveFolder.exists()) saveFolder.mkdirs();
				
				//����÷������ ����
				for ( int i = 0; Bean.getSubFileList() != null && i < Bean.getSubFileList().size(); i++ ) {
					ResearchSubBean rsBean = (ResearchSubBean)Bean.getSubFileList().get(i);
					String fileName = appInfo.getRootRealPath() + rsBean.getFilenm();
					String fileData = Utils.nullToEmptyString(rsBean.getFileToBase64Encoding());
					base64DecodeToFile(fileName, fileData);
				}
				
				//����÷������ ����
				for ( int i = 0; Bean.getExamFileList() != null && i < Bean.getExamFileList().size(); i++ ) {
					ResearchExamBean reBean = (ResearchExamBean)Bean.getExamFileList().get(i);
					String fileName = appInfo.getRootRealPath() + reBean.getFilenm();
					String fileData = Utils.nullToEmptyString(reBean.getFileToBase64Encoding());
					base64DecodeToFile(fileName, fileData);
				}
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
	public int ResearchGrpSave(ResearchBean Bean) throws Exception{
		int result = 0;

		result = getOutsideDAO().rchGrpAllSave(Bean);

		return result;
	}
	
	public boolean base64DecodeToFile(String fileName, String fileData) {
		boolean result = false;

		ByteArrayInputStream bais = null;
		BufferedOutputStream bos = null;		
		try {
			byte[] fileByte = fileData.getBytes("UTF-8");
			fileByte = Base64.decode(fileByte, 0, fileByte.length);
			
			byte[] buffer = new byte[4096];
			bais = new ByteArrayInputStream(fileByte);
			bos = new BufferedOutputStream(new FileOutputStream(fileName));
			int cnt;
			while ( (cnt = bais.read(buffer, 0, buffer.length) ) != -1 ) {
				bos.write(buffer, 0, cnt);
			}
			
			result = true;
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			try { bais.close(); } catch ( Exception e ) {}
			try { bos.close(); } catch ( Exception e ) {}
		}
		
		return result;
	}
	
	/**
	 * �����������
	 * @param rchno
	 * @return
	 * @throws Exception
	 */
	public int rchState(int rchno, String userid, int grpDuplicheck, int grpLimitcount) throws Exception{
		int result = 0;
		
		result = getOutsideDAO().rchState(rchno, userid, grpDuplicheck, grpLimitcount);
		
		return result;
	}
	
	/**
	 * �����������
	 * @param rchno
	 * @return
	 * @throws Exception
	 */
	public int rchGrpState(int rchgrpno) throws Exception{
		int result = 0;
		
		result = getOutsideDAO().rchGrpState(rchgrpno);
		
		return result;
	}
	
	/**
	 * �������� �׸� ��� 
	 * @param rchno
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public List getRchSubList(int rchno, String sessionId, String mode) throws Exception{		
		return getOutsideDAO().getRchSubList(rchno, sessionId, mode); 
	}
	
	/**
	 * ��û���� ����
	 */
	public int reqAnsSave(DataForm dForm, String userid, String sessi, ServletContext context, String saveDir) throws Exception{
		int result = 0;
		
		ReqMstBean mbean = new ReqMstBean();
		BeanUtils.copyProperties(mbean, dForm);
		mbean.setPresentid(userid);
		mbean.setCrtusrid(userid);
		mbean.setUptusrid(userid);		
		mbean.setAnscontList(extraArticle(dForm, mbean));		
		
		if("s".equals(dForm.getMode())){
			//����
			result = getOutsideDAO().insertReqData(mbean, sessi, context, saveDir);
		} else {
			//����
			result = getOutsideDAO().updateReqData(mbean, context, saveDir);
		}
		
		return result;
	}
	
	/**
	 * ��û�������
	 * @param rchForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public int reqAnsDel(List ansList, ServletContext sContext) throws Exception{
		int result = 0;

		result = getOutsideDAO().reqAnsDel(ansList, sContext);

		return result;
	}
	
	/**
	 * ��û����� ���� ��� ���̺� ����	
	 */
	public int RequestDel(int reqformno, ServletContext sContext) throws Exception {
		int result = 0;
		
		result = getOutsideDAO().RequestDel(reqformno, sContext);
		
		return result;
	}
	
	/**
	 * ��û�� ���
	 * @return
	 * @throws Exception
	 */
	public String getReqResult(ServletContext sContext) throws Exception {
		
		return getOutsideDAO().getReqResult(sContext);
	}
	
	/**
	 * ��û�� ����
	 * @param rchForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public int RequestSave(FrmBean Bean, ServletContext sContext) throws Exception{
		int result = 0;

		result = getOutsideDAO().reqAllSave(Bean, sContext);
		
		//������ �ƴѰ�츸 �Ʒ� Ÿ���� ������ ������ reqformno ������ ������ ������Ʈ 
		{
			if ( result > 0 ) {
				//���� ���ε�
				Calendar cal = Calendar.getInstance();
				String saveDir = appInfo.getRequestSampleDir() + cal.get(Calendar.YEAR) + "/";
				File saveFolder = new File(appInfo.getRootRealPath() + saveDir);
				if(!saveFolder.exists()) saveFolder.mkdirs();
				
				//����÷������ ����
				for ( int i = 0; Bean.getSubFileList() != null && i < Bean.getSubFileList().size(); i++ ) {
					ArticleBean aBean = (ArticleBean)Bean.getSubFileList().get(i);
					String fileName = appInfo.getRootRealPath() + aBean.getFilenm();
					String fileData = Utils.nullToEmptyString(aBean.getFileToBase64Encoding());
					base64DecodeToFile(fileName, fileData);
				}
				
				//����÷������ ����
				for ( int i = 0; Bean.getExamFileList() != null && i < Bean.getExamFileList().size(); i++ ) {
					SampleBean sBean = (SampleBean)Bean.getExamFileList().get(i);
					String fileName = appInfo.getRootRealPath() + sBean.getFilenm();
					String fileData = Utils.nullToEmptyString(sBean.getFileToBase64Encoding());
					base64DecodeToFile(fileName, fileData);
				}
			}
		}
		return result;
	}
	
////////////////////////////////////////////////////////////////
////////////////////// �������� MANAGER ���� //////////////////////
////////////////////////////////////////////////////////////////
	/**
	 * 
	 * @param rchno
	 * @return
	 * @throws Exception
	 */
	public int ResearchDelete(int rchno, ServletContext context) throws Exception{

		Connection conn = null;
		
		int result = 0;
		
		try {
			conn = ConnectionManager.getConnection(false);
			
			List rchSubList = getOutsideDAO().getRchSubFile(conn, "", rchno);
			
			for ( int i = 0; rchSubList != null && i < rchSubList.size(); i++ ) {
				ResearchSubBean rchSubBean = (ResearchSubBean)rchSubList.get(i);
				getOutsideDAO().delRchSubFile(conn, "", rchno, rchSubBean.getFormseq(), rchSubBean.getFileseq());
				
				String delFile = rchSubBean.getFilenm();
				if ( delFile != null && delFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delFile));
				}
			}
			
			List rchExamList = getOutsideDAO().getRchExamFile(conn, "", rchno, 0);
			
			for ( int j = 0; rchExamList != null && j < rchExamList.size(); j++ ) {
				ResearchExamBean rchExamBean = (ResearchExamBean)rchExamList.get(j);
				getOutsideDAO().delRchExamFile(conn, "", rchno, rchExamBean.getFormseq(), rchExamBean.getExamseq(), rchExamBean.getFileseq());
				
				String delFile = rchExamBean.getFilenm();
				if ( delFile != null && delFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delFile));
				}
			}
		
			result = getOutsideDAO().ResearchDlete(conn, rchno);
		
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
	 * ��û�� �ӽ����̺� ����
	 * @param sessionid
	 * @throws Exception
	 */
	public void delResearchTempData(String sessionid, ServletContext context) throws Exception {
		
		Connection conn = null;
		
		try {
			conn = ConnectionManager.getConnection(false);
			
			List rchSubList = getOutsideDAO().getRchSubFile(conn, sessionid, 0);
			
			for ( int i = 0; rchSubList != null && i < rchSubList.size(); i++ ) {
				ResearchSubBean rchSubBean = (ResearchSubBean)rchSubList.get(i);
				getOutsideDAO().delRchSubFile(conn, sessionid, 0, rchSubBean.getFormseq(), rchSubBean.getFileseq());
				
				String delSubFile = rchSubBean.getFilenm();
				if ( delSubFile != null && delSubFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delSubFile));
				}
			}
			
			List rchExamList = getOutsideDAO().getRchExamFile(conn, sessionid, 0, 0);
			
			for ( int j = 0; rchExamList != null && j < rchExamList.size(); j++ ) {
				ResearchExamBean rchExamBean = (ResearchExamBean)rchExamList.get(j);
				getOutsideDAO().delRchExamFile(conn, sessionid, 0, rchExamBean.getFormseq(), rchExamBean.getExamseq(), rchExamBean.getFileseq());
				
				String delExamFile = rchExamBean.getFilenm();
				if ( delExamFile != null && delExamFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delExamFile));
				}
			}
		
			getOutsideDAO().delResearchTempData(conn, sessionid);
		
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
	 * 
	 * @param usrid
	 * @param sch_title
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List getResearchMyList(String usrid, String deptcd, String sch_title, int start, int end) throws Exception{
		List result = null;
		
		result = getOutsideDAO().getResearchMyList(usrid, deptcd, sch_title, start, end);
		
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
		return getOutsideDAO().getResultSubList(rchno, range, schbean, sessionId); 
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
		
		result = getOutsideDAO().getResearchMyTotCnt(usrid, deptcd, sch_title, mode);
		
		return result;
	}
	
	/*
	 * �����亯�� ��� ��������
	 */
	public List getRchAnsusrList(int rchno) throws Exception {
		return getOutsideDAO().getRchAnsusrList(rchno);
	}
	
	/**
	 * 
	 * @param rchno
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public ResearchBean getRchMst(int rchno, String sessionId) throws Exception{		
		return getOutsideDAO().getRchMst(rchno, sessionId); 
	}
	
	/**
	 * ��������׷� ������ ��������
	 * @param rchno
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public ResearchBean getRchGrpMst(int rchgrpno, String sessionId) throws Exception{		
		return getOutsideDAO().getRchGrpMst(rchgrpno, sessionId); 
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
			ResearchSubBean rchSubBean = getOutsideDAO().getRchSubFile(conn, sessionId, rchno, formseq);
			
			if ( rchSubBean != null ) {
				getOutsideDAO().delRchSubFile(conn, sessionId, rchno, formseq, rchSubBean.getFileseq());
				
				String delFile = rchSubBean.getFilenm();
				if ( delFile != null && delFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delFile));
				}
			}
		} else {	//����÷�����ϻ���
			ResearchExamBean rchExamBean = getOutsideDAO().getRchExamFile(conn, sessionId, rchno, formseq, examseq);
			
			if ( rchExamBean != null ) {
				getOutsideDAO().delRchExamFile(conn, sessionId, rchno, formseq, examseq, rchExamBean.getFileseq());
				
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
		return getOutsideDAO().getRchSubList(rchno, sessionId, examcount, mode); 
	}	
	
	public FormFile[] orderExamcontFile(ResearchForm rchForm) throws Exception {
		
		if ( rchForm.getListrch() == null ) return null;
		
		int examcount = rchForm.getExamcount();
		
		FormFile[] result = new FormFile[rchForm.getListrch().size() * examcount];
		FormFile[] examcontFile = rchForm.getExamcontFile();
		String[] examcontFileYN = rchForm.getExamcontFileYN();
		String[] examcont = rchForm.getExamcont();
		
		int prevExamcount = 0;
		while ( prevExamcount < rchForm.getExamcontFile().length
				&& rchForm.getExamcontFile()[prevExamcount] != null ) {
			prevExamcount++;
		}
		prevExamcount = prevExamcount / rchForm.getFormcount();
		
		for ( int i = 0; i < rchForm.getListrch().size(); i++ ) {
			int offsetIdx = i * examcount;
			for ( int j = 0; j < prevExamcount; j++ ) {
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
			}
		}

		return result;
	}
	
	/**
	 * 
	 * @param rchForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public int ResearchSave(ResearchForm rchForm, ServletContext context, String saveDir) throws Exception{
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

		result = getOutsideDAO().rchAllSave(Bean, rchForm, context, saveDir);
		
		if("del".equals(mode)){
			//�׸����
			result = getOutsideDAO().rchDelSub(rchno, sessionId, rchForm.getDelseq(), context);
			
		} else if("add".equals(mode)){
			if ( getOutsideDAO().getMaxSubSeq(rchno, sessionId) < 50 ) {
				//�׸��߰�
				result = getOutsideDAO().insAddSub(rchno, sessionId);
			}
			
		} else if("make".equals(mode)){
			//�׸񸸵��
			result = getOutsideDAO().insMakeSub(rchno, sessionId, Bean.getFormcount());			
			
		} else if("prev".equals(mode)){
			//�̸�����
		} else if("comp".equals(mode)){
			//�Ϸ�
			if(rchno == 0){
				resultrchno = getOutsideDAO().rchInsComp(sessionId, Bean, context, saveDir);
			} else {
				resultrchno = rchno;
			}
		}
		
		if(result >= 0) { 
			//����
			result = 0;		
		} 
		
		//�Ϸ�ÿ� �޾ƿ� ��û��� ��ȣ�� ������ ��Ĺ�ȣ�� Return
		if(resultrchno > 0){
			
			int outResult = 0;
			if("comp".equals(mode) && "2".equals(rchForm.getRange())){
				outResult = ResearhOutside(Bean, resultrchno);
			}
			
			if(outResult < 0) {
				result = outResult;
			} else {
				result = resultrchno;
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
		
		if(formseq != null){
			for(int i=0; i<formtitle.length; i++ ){
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
			int subcount = rchForm.getFormcount();
			int examcount = rchForm.getExamcount();
			String[] examcont = rchForm.getExamcont();	//��������
			
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
	 * 
	 * @param Bean
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List getUsedRchList(ResearchBean Bean, int start, int end) throws Exception {
		return getOutsideDAO().getUsedRchList(Bean, start, end);
	}
	
	/**
	 * 
	 * @param Bean
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public int getUsedRchTotCnt(ResearchBean Bean) throws Exception {
		return getOutsideDAO().getUsedRchTotCnt(Bean);
	}
	
	/**
	 * 
	 * @param Bean
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List getRchParticiList(String usrid, String deptcd, String schtitle, int start, int end) throws Exception {
		return getOutsideDAO().getRchParticiList(usrid, deptcd, schtitle, start, end);
	}
	
	/**
	 * 
	 * @param Bean
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public int getRchParticiTotCnt(String usrid, String deptcd, String schtitle) throws Exception {
		return getOutsideDAO().getRchParticiTotCnt(usrid, deptcd, schtitle);
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
		
		result =  getOutsideDAO().rchAnsSave(ansList, rchno, usrid, usrnm);
		
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
	public int rchChoice(int rchno, String sessionId, String usrid, String usrnm, String deptcd, String deptnm, String coltel, ServletContext context, String saveDir) throws Exception {
		return getOutsideDAO().rchChoice(rchno, sessionId, usrid, usrnm, deptcd, deptnm, coltel, context, saveDir);
	}	
	
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

		hcp.setMethodType(1); 
		
        int rtnCode = hcp.execute(); // �����ϱ� 
       
        if(rtnCode == 200){
        	result = Integer.parseInt(hcp.getContent(), 10);
        }
        
		return result;
	}
	
	/**
	 * ���� ���� 	
	 */
	public int rchClose(int rchno, String userid) throws Exception {
		int result = 0;
		
		result = getOutsideDAO().rchClose(rchno, userid);
		
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
		return getOutsideDAO().getRchOutsideList();
	}
	
	/**
	 * ���״� ���ⰳ�� ��������
	 * @param rchno
	 * @return
	 * @throws Exception
	 */
	public int getRchSubExamcount(int rchno, String sessionId) throws Exception {
		return getOutsideDAO().getRchSubExamcount(rchno, sessionId);
	}
///////////////////////////////////////////////////////////////
/////////////////////// �������� MANAGER �� //////////////////////
///////////////////////////////////////////////////////////////


/////////////////////////////////////////////////////////////////
/////////////////////// ��û�� MANAGER ���� ////////////////////////
/////////////////////////////////////////////////////////////////
	/**
	 * ��û���׺���÷������ ��������
	 * @param conn
	 * @param sessionId
	 * @param reqformno
	 * @param formseq
	 * @param examseq
	 * @return
	 * @throws Exception
	 */
	public SampleBean getReqFormExamFile(String sessionId, int reqformno, int formseq, int examseq) throws Exception {
		Connection conn = null;
		
		SampleBean result = null;
		
		try {
			conn = ConnectionManager.getConnection();
			
			result = getOutsideDAO().getReqFormExamFile(conn, sessionId, reqformno, formseq, examseq);
		} catch (Exception e) {
			logger.error("error", e);
			ConnectionManager.close(conn);
			throw e;
		} finally {
			ConnectionManager.close(conn);
		}
		
		return result;
	}
	
	/**
	 * ����ȭ�鿡 ������ ��� ��������	
	 */
	public List mainShowList() throws Exception{
		List result = null;
		
		result = getOutsideDAO().mainShowList();
		
		return result;		
	}
	
	/**
	 * ��û�� �������� ���� �ִ��� Ȯ���Ѵ�.
	 * gbn(1): ������, gbn(2):������
	 * ��� �������θ� Ȯ���ϱ� ���ؼ�
	 */
	public int reqMstCnt(int reqformno, String gbn) throws Exception{
		int result = 0;
		
		result = getOutsideDAO().reqMstCnt(reqformno, gbn);
		
		return result;
	}
	
	/**
	 * ��û ������(������)�� ��û�� �Ǽ�
	 */
	public int jupsuCnt(String userid) throws Exception{
		int result = 0;
		
		result = getOutsideDAO().jupsuCnt(userid);
		
		return result;
	}
	
	/**
	 * ��ó���� ���� �Ǽ�
	 */
	public int notProcCnt(String userid) throws Exception{
		int result = 0;
		
		result = getOutsideDAO().notProcCnt(userid);
		
		return result;
	}
	
	/**
	 * ��û�ϱ� ���	
	 */
	public List doSinchungList(SearchBean search) throws Exception{
		List result = null;
		
		result = getOutsideDAO().doSinchungList(search);
		
		return result;		
	}
	
	/**
	 * ��û�ϱ� ��ü�Ǽ� ��������
	 */
	public int doSinchungTotCnt(SearchBean search) throws Exception {
		int result = 0;
		
		result = getOutsideDAO().doSinchungTotCnt(search);
		
		return result;
	}
	
	/**
	 * ����û�� ���	
	 */
	public List mySinchungList(SearchBean search) throws Exception{
		List result = null;
		
		result = getOutsideDAO().mySinchungList(search);
		
		return result;		
	}
	
	/**
	 * ����û�� ��ü�Ǽ� ��������
	 */
	public int mySinchungTotCnt(SearchBean search) throws Exception {
		int result = 0;
		
		result = getOutsideDAO().mySinchungTotCnt(search);
		
		return result;
	}
	
	/**
	 * �������� ��� ��������
	 */
	public List reqDataList(SearchBean search) throws Exception {
		List result = null;
		
		result = getOutsideDAO().reqDataList(search);
		
		return result;
	}
	
	/**
	 * �������� ��� ��ü �Ǽ� ��������
	 */
	public int reqDataTotCnt(SearchBean search) throws Exception {
		int result = 0;
		
		result = getOutsideDAO().reqDataTotCnt(search);
		
		return result;
	}
	
	/**
	 * ��û ���� ��������
	 */
	public ReqMstBean reqDataInfo(int reqformno, int reqseq) throws Exception {
		ReqMstBean mstInfo = null;
		
		mstInfo = getOutsideDAO().reqDataInfo(reqformno, reqseq);
		
		return mstInfo;
	}
	
	/**
	 * ��û�� ���� ���	
	 */
	public List reqFormList(SearchBean search) throws Exception{
		List result = null;
		
		result = getOutsideDAO().reqFormList(search);
		
		return result;		
	}
	
	/**
	 * ��û�� ���� ��ü�Ǽ� ��������
	 */
	public int reqFormTotCnt(SearchBean search) throws Exception {
		int result = 0;
		
		result = getOutsideDAO().reqFormTotCnt(search);
		
		return result;
	}
	
	/**
	 * ������� �������� ���	
	 */
	public List getUsedList(SearchBean search) throws Exception{
		List result = null;
		
		result = getOutsideDAO().getUsedList(search);
		
		return result;		
	}
	
	/**
	 * ������� �������� ��ü�Ǽ� ��������
	 */
	public int getUsedTotCnt(SearchBean search) throws Exception {
		int result = 0;
		
		result = getOutsideDAO().getUsedTotCnt(search);
		
		return result;
	}
	
	/**
	 * ������� �������⿡�� ����
	 * �ӽ� ���̺�� ����
	 */
	public int selectUsed(int reqformno, String sessi, ServletContext context, String saveDir) throws Exception{
		int result = 0;
		
		result = getOutsideDAO().selectUsed(reqformno, sessi, context, saveDir);
			
		return result;
	}
	
	/**
	 * ��û�� ��ĸ����� ���� ����
	 */
	public FrmBean reqFormInfo(int reqformno) throws Exception{
		FrmBean frmInfo = null;
		
		frmInfo = getOutsideDAO().reqFormInfo(reqformno);
		
		return frmInfo;
	}
	
	/**
	 * ��û�� ��ĸ����� ���� ����(TEMP)
	 */
	public FrmBean reqFormInfo_temp(String sessi) throws Exception{
		FrmBean frmInfo_temp = null;
		
		frmInfo_temp = getOutsideDAO().reqFormInfo_temp(sessi);
		
		return frmInfo_temp;
	}
	
	/**
	 * ��û�� ���׸�� ��������
	 */
	public List reqFormSubList(int reqformno) throws Exception{
		List result = null;
		
		result = getOutsideDAO().reqFormSubList(reqformno);
		
		return result;		
	}
	
	/**
	 * ��û�� ���׸�� ��������(TEMP)
	 */
	public List reqFormSubList_temp(String sessi, int examcount) throws Exception{
		List result = null;
		
		result = getOutsideDAO().reqFormSubList_temp(sessi, examcount);
		
		return result;		
	}
	
	/**
	 * �ӽ� ���̺� ����	
	 */
	public int deleteTempAll(String sessi, ServletContext context) throws Exception {
		int result = 0;
			
		result = getOutsideDAO().deleteTempAll(sessi, context);
		
		return result;
	}
	
	/**
	 * �ӽ����̺�� ��� ������ ����
	 */
	public int copyToTemp(int reqformno, String sessi, ServletContext context, String saveDir) throws Exception {
		int result = 0;
		
		result = getOutsideDAO().copyToTemp(reqformno, sessi, context, saveDir);
		
		return result;
	}
	
	/**
	 * ��û����� ���� ��� ���̺� ����	
	 */
	public int deleteAll(int reqformno, String userid, ServletContext context) throws Exception {
		int result = 0;
		
		result = getOutsideDAO().deleteAll(reqformno, userid, context);
		
		return result;
	}	
	
	/**
	 * ��û���
	 */
	public int cancelSinchung(int reqformno, int reqseq, int gubun, String userid, ServletContext context) throws Exception {
		int result = 0;
		
		if ( gubun == 1 ) {
			result = getOutsideDAO().cancelSinchung(reqformno, reqseq, userid, context);
		} else {
			result = getOutsideDAO().cancelSanc(reqformno, reqseq);
			
			if ( result > 0 ) {
				procJupsu("4", reqformno, reqseq);
			}
		}
		
		return result;
	}
	
	/**
	 * ��û�����Ϳ��� ��û �Ϸù�ȣ �������� (REQSEQ)
	 */
	public int getMaxReqseq(int reqformno) throws Exception{
		
		return getOutsideDAO().getMaxReqseq(reqformno);
	}
	
	/**
	 * ��û���� ����
	 */
	public int saveReqData(DataForm dForm, String userid, String sessi, ServletContext context, String saveDir) throws Exception{
		int result = 0;
		
		ReqMstBean mbean = new ReqMstBean();
		BeanUtils.copyProperties(mbean, dForm);			
		mbean.setCrtusrid(userid);
		mbean.setUptusrid(userid);		
		mbean.setAnscontList(extraArticle(dForm, mbean));		
		
		if("s".equals(dForm.getMode())){
			//����
			result = getOutsideDAO().insertReqData(mbean, sessi, context, saveDir);
		} else {
			//����
			result = getOutsideDAO().updateReqData(mbean, context, saveDir);
		}
		
		return result;
	}
	
	/**
	 * ����ó��
	 * gbn: �ϰ�����ó�����(all), �����Ϸ�(1), ��������(2), �̰���(3), ��û��(4)
	 */
	public int procJupsu(String gbn, int reqformno, int reqseq) throws Exception {
		int result = 0;
		
		if ( gbn.equals("all") == true ) {
			result = getOutsideDAO().procTotalJupsu(reqformno);
		} else {
			result = getOutsideDAO().procJupsu(gbn, reqformno, reqseq);
		}
		
		return result;
	}
	
	/**
	 * ���缱 ����/���� ������ ��������
	 * gubun: ����(1),  ����(2)
	 */
	public List approvalInfo(String gubun, int reqformno, int reqseq) throws Exception {
		List result = null;
		
		result = getOutsideDAO().approvalInfo(gubun, reqformno, reqseq);
		
		return result;
	}
	
	/**
	 * ��û�� ����
	 */
	public int doSanc (int reqformno, int reqseq, String userid) throws Exception{
		int result = 0;
		
		result = getOutsideDAO().doSanc(reqformno, reqseq, userid);
		
		return result;
	}
	
	/**
	 * ��û�� ���� 	
	 */
	public int docClose(int reqformno, String userid) throws Exception {
		int result = 0;
		
		result = getOutsideDAO().docClose(reqformno, userid);
		
		return result;
	}
	
	/**
	 * ����� ���� �ִ��� Ȯ��
	 */
	public boolean existSanc(int reqformno, int reqseq) throws Exception {
		boolean result = false;
				
		result = getOutsideDAO().existSanc(reqformno, reqseq);
				
		return result;
	}
	
	/**
	 * �߰��׸� �Է°� �о����
	 */
	private List extraArticle(DataForm dForm, ReqMstBean mbean)throws Exception{
		List ansList = null;
		List article = reqFormSubList(dForm.getReqformno());  //�߰����� ����	
		
		if(article != null){			
			ansList = new ArrayList();
			
			int idx_other = 0;	int idx_text  = 0;	int idx_area  = 0;
			for(int i=0;i<article.size();i++){
				//��û�� �������
				ArticleBean abean = (ArticleBean)article.get(i);   
				
				ReqSubBean subbean = new ReqSubBean();       //��û�� �Է�����	
				subbean.setReqformno(dForm.getReqformno());  //��û��Ĺ�ȣ
				subbean.setReqseq(dForm.getReqseq());        //��û��ȣ
				subbean.setFormseq(dForm.getFormseq()[i]);   //���׹�ȣ
				
				String chk = "";
				switch(Integer.parseInt(abean.getFormtype())){
					case 1:    //���ϼ�����	
						
						subbean.setAnscont(dForm.getRadioans()[i]);
												
						//��Ÿ�ǰ� ����
						if("Y".equals(abean.getExamtype())){
							if("5".equals(dForm.getRadioans()[i])){
								//��Ÿ�� ���õǾ������� ���
								subbean.setOther(dForm.getOther()[idx_other]);								
							}
							idx_other++;
						}										
												
						break;
					case 2:    //����������		
						
						//üũ�ڽ� �о����
						boolean gitafl = false;  //��Ÿüũ����
						if (dForm.getChkans() != null){
							for(int j=0;j<dForm.getChkans().length;j++){
								String[] chk_temp = dForm.getChkans()[j].split(",");
								if(chk_temp[0].equals(String.valueOf(i))){
									chk = chk + chk_temp[1] + ",";
									if("5".equals(chk_temp[1])){ gitafl = true; }
								}
							}
						}
						if(chk.length()>0){subbean.setAnscont(chk.substring(0, chk.length()-1));}
						
						//��Ÿ�ǰ� ����
						if("Y".equals(abean.getExamtype())){
							if(gitafl){
								//��Ÿ�� ���õǾ������� ���
								subbean.setOther(dForm.getOther()[idx_other]);
							}
							idx_other++;
						}		
					
						break;
					case 3:    //�ܴ���
						subbean.setAnscont(dForm.getTxtans()[idx_text]);
						idx_text++;
						break;
					case 4:    //�幮��						
						subbean.setAnscont(dForm.getAreaans()[idx_area]);
						idx_area++;
						break;
				}
								
				ansList.add(subbean);
			}
		}
		
		return ansList;
	}
	
	public FormFile[] orderExamcontFile(SinchungForm reqform) throws Exception {
		
		if ( reqform.getArticleList() == null ) return null;
	
		int examcount = reqform.getExamcount();
		
		FormFile[] result = new FormFile[reqform.getArticleList().size() * examcount];
		FormFile[] examcontFile = reqform.getExamcontFile();
		String[] examcontFileYN = reqform.getExamcontFileYN();
		String[] examcont = reqform.getExamcont();
		
		int prevExamcount = 0;
		while ( prevExamcount < reqform.getExamcontFile().length
				&& reqform.getExamcontFile()[prevExamcount] != null ) {
			prevExamcount++;
		}
		prevExamcount = prevExamcount / reqform.getAcnt();
		
		for ( int i = 0; i < reqform.getArticleList().size(); i++ ) {
			int offsetIdx = i * examcount;
			for ( int j = 0; j < prevExamcount; j++ ) {
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
			}
		}
		
		return result;
	}
		
	/**
	 * ����ۼ�
	 */
	public int makeReqForm(SinchungForm sForm, ServletContext context, String saveDir) throws Exception{
				
		int result = -1;
		int resultreq = 0;
		int reqformno = sForm.getReqformno();		
		
		//frmBean �� �Է�
		FrmBean fbean = new FrmBean();
		BeanUtils.copyProperties(fbean, sForm);	
		
		//�Է±⺻���� ����
		String btype = getBasicType(sForm.getBtype());
		fbean.setBasictype(btype);		
		
		//�߰��׸� ���װ� ����		
		fbean.setArticleList(getArticle(sForm));
	
		//�߰��׸� ÷������ ����
		sForm.setArticleList(fbean.getArticleList());
		fbean.setExamcontFile(orderExamcontFile(sForm));
		
		//�׻� �����ϴ� ������ �����Ѵ�.
		result = getOutsideDAO().saveAll(fbean, sForm, context, saveDir);
		
		if("del".equals(sForm.getMode())){
			//�׸����
			result = getOutsideDAO().deleteArticle(0, sForm.getSessi(), sForm.getDelseq(), context);			
			
		} else if("add".equals(sForm.getMode())){
			if ( getOutsideDAO().getMaxFormseq(0, sForm.getSessi()) < 20 ) {
				//�׸��߰�
				result = getOutsideDAO().insertArticle(0, sForm.getSessi());
			}
			
		} else if("make".equals(sForm.getMode())){
			//�׸񸸵��			
			result = getOutsideDAO().makeArticle(0, sForm.getSessi(), fbean.getAcnt());
			
		} else if("comp".equals(sForm.getMode())){
			//�Ϸ�
			if(reqformno == 0){
				resultreq = getOutsideDAO().saveComp(sForm.getSessi(), context, saveDir);
			} else {
				resultreq = getOutsideDAO().updateComp(reqformno, sForm.getSessi(), context, saveDir);
			}
		}
		
		if(result >= 0) { 
			//����
			result = 0;						
		} 
		
		//�Ϸ�ÿ� �޾ƿ� ��û��� ��ȣ�� ������ ��Ĺ�ȣ�� Return
		if(resultreq > 0){
			
			int outResult = 0;
			if("comp".equals(sForm.getMode()) && "2".equals(sForm.getRange())){
				outResult = SinchungOutside(fbean, resultreq);
			}
			
			if(outResult < 0) { 
				result = outResult;
			} else  {
				result = resultreq;
			}
		}
				
		return result;
	}
	
	/**
	 * �׸� �о����
	 */
	private List getArticle(SinchungForm sForm) {
		List result = new ArrayList();
				
		int[] formseq = sForm.getFormseq();
		String[] title = sForm.getFormtitle();      //�׸�����
		String[] require = sForm.getRequire();      //�ʼ��Է¿���
		String[] formtype = sForm.getFormtype();    //�Է�����
		String[] security = sForm.getSecurity();    //����ó��(Y:����ó��, N:����)
		String[] helpexp  = sForm.getHelpexp();     //�߰�����
		String[] examtype = sForm.getExamtype();    //��ŸYN
		
		if(formseq != null){
			for(int i=0;i<formseq.length;i++){
				ArticleBean abean = new ArticleBean();
				abean.setReqformno(sForm.getReqformno());
				abean.setSessi(sForm.getSessi());
				abean.setFormseq(formseq[i]);
				abean.setFormtitle(title[i]);
				
				//�ʼ��Է¿��� ����
				if(require != null){
					for(int j=0;j<require.length;j++){				
						if(require[j].equals(String.valueOf(i))){
							abean.setRequire("Y");
							break;
						}
					}
				} else {
					abean.setRequire("N");
				}
				
				//�Է����� ����
				abean.setFormtype(formtype[i]);
				
				//�߰����� ����(�Է����°� �ܴ���(3), �幮��(4) �϶��� ���)
				if(Integer.parseInt(formtype[i]) >= 3){
					abean.setHelpexp(helpexp[i]);
				}
				
				//����ó�� ����
				if(Integer.parseInt(formtype[i]) > 2 && security != null){
					for(int j=0;j<security.length;j++){					
						if(security[j].equals(String.valueOf(i))){
							abean.setSecurity("Y");
							break;
						}
					}
				} else {
					abean.setSecurity("N");
				}
				
				//��ŸYN ����
				if(examtype != null){
					for(int j=0;j<examtype.length;j++){				
						if(examtype[j].equals(String.valueOf(i))){
							abean.setExamtype("Y");
							break;
						}
					}
				} else {
					abean.setExamtype("N");
				}
				
				//���� ����
				abean.setSampleList(getSample(sForm, formseq[i], i));
				
				result.add(abean);
			}
		}
		return result;
	}
	
	/**
	 * ���� �о����
	 */
	private List getSample(SinchungForm sForm, int formseq, int bunho) {
		List result = new ArrayList();		
		
		if (  sForm.getExamcont() != null ) {
			int subcount = sForm.getAcnt();
			int examcount = sForm.getExamcount();
			String[] examcont = sForm.getExamcont();	//��������
			
			int start = bunho * (examcont.length / subcount);
			int end   = start + examcount;
			int examseq = 0;
			int count = 0;
			
			result = new ArrayList();
			SampleBean sbean = null;
			for(int i=start;i<end;i++){			
				sbean = new SampleBean();
				if(count < examcont.length / subcount && !"".equals(examcont[i].trim())){
					examseq = examseq + 1;
					sbean.setReqformno(sForm.getReqformno());
					sbean.setSessi(sForm.getSessi());
					sbean.setFormseq(formseq);
					sbean.setExamseq(examseq);
					sbean.setExamcont(examcont[i].trim());
	
					result.add(sbean);
				}
				count++;
			}
			
			while ( result.size() < examcount ) {
				sbean = new SampleBean();
				examseq = examseq + 1;
				sbean.setReqformno(sForm.getReqformno());
				sbean.setSessi(sForm.getSessi());
				sbean.setFormseq(formseq);
				sbean.setExamseq(examseq);
				sbean.setExamcont("");
	
				result.add(sbean);
			}
		}
		
		return result;
	}
	
	/**
	 * �Է±⺻���� �� �о����
	 */
	private String getBasicType(String[] btype){
		String result = "";
		
		for(int i=0;i<btype.length;i++){
			if(i>0){
				result = result + ",";
			}
			result = result + btype[i];
		}
		
		return result;
	}
	
	public int SinchungOutside(FrmBean bean, int reqformno) throws Exception{
		int result = 0;
		
		String urlStr = appInfo.getOutsideurl()+"/outsideReqSave.do";
		HttpClientHp hcp = new HttpClientHp(urlStr);
		
		hcp.setParam("reqformno", new Integer(reqformno).toString());
		hcp.setParam("title", bean.getTitle());
		hcp.setParam("strdt", bean.getStrdt());
		hcp.setParam("enddt", bean.getEnddt());
		hcp.setParam("coldeptcd", bean.getColdeptcd());
		hcp.setParam("coldeptnm", bean.getColdeptnm());
		hcp.setParam("coltel", bean.getColtel());
		hcp.setParam("chrgusrid", bean.getChrgusrid());
		hcp.setParam("chrgusrnm", bean.getChrgusrnm());
		hcp.setParam("summary", bean.getSummary());
		hcp.setParam("range", bean.getRange());
		hcp.setParam("imgpreview", bean.getImgpreview());
		hcp.setParam("duplicheck", bean.getDuplicheck());
		hcp.setParam("limitcount", new Integer(bean.getLimitcount()).toString());
		hcp.setParam("sancneed", bean.getSancneed());
		hcp.setParam("basictype", bean.getBasictype());
		hcp.setParam("headcont", bean.getHeadcont());
		hcp.setParam("tailcont", bean.getTailcont());
		hcp.setParam("subsize",new Integer(bean.getArticleList().size()).toString());
		for(int i=0; i<bean.getArticleList().size(); i++){
			ArticleBean subbean = (ArticleBean)bean.getArticleList().get(i);		
			hcp.setParam("formseq"+i, new Integer(subbean.getFormseq()).toString());
			hcp.setParam("require"+i, subbean.getRequire());
			hcp.setParam("formtitle"+i, subbean.getFormtitle());
			hcp.setParam("formtype"+i, subbean.getFormtype());
			hcp.setParam("security"+i, subbean.getSecurity());
			hcp.setParam("helpexp"+i, subbean.getHelpexp());
			hcp.setParam("examtype"+i, subbean.getExamtype());
			if(subbean.getSampleList() != null){
				hcp.setParam("examsize"+i, new Integer(subbean.getSampleList().size()).toString());
				for(int j=0; j<subbean.getSampleList().size(); j++){
					SampleBean exambean = (SampleBean)subbean.getSampleList().get(j);
					hcp.setParam("examseq"+i+""+j, new Integer(exambean.getExamseq()).toString());
					hcp.setParam("examcont"+i+""+j, exambean.getExamcont());
				}
			}
		}

		hcp.setMethodType(1); 
		
        int rtnCode = hcp.execute(); // �����ϱ� 
       
        if(rtnCode == 200){
        	result = Integer.parseInt(hcp.getContent(), 10);
        }
        
		return result;
	}
	
	/**
	 * *
	 * 
	 */
	public int reqState(int reqformno, String userid) throws Exception{
		int result = 0;
		result = getOutsideDAO().reqState(reqformno, userid);
		
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
	public List getReqOutsideList() throws Exception {
		return getOutsideDAO().getReqOutsideList();
	}
	
	/**
	 * ���״� ���ⰳ�� ��������
	 * @param rchno
	 * @return
	 * @throws Exception
	 */
	public int getReqSubExamcount(int rchno, String sessionId) throws Exception {
		return getOutsideDAO().getReqSubExamcount(rchno, sessionId);
	}
	
	/**
	 * ������������ �Ǽ� üũ
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public int apprProcCount(String userid) throws Exception {
		return getOutsideDAO().apprProcCount(userid);
	}
///////////////////////////////////////////////////////////////
/////////////////////// ��û�� MANAGER �� ////////////////////////
///////////////////////////////////////////////////////////////
}