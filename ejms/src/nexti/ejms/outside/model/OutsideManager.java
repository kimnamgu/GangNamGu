/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 외부망 manager
 * 설명:
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
	 * 설문조사 답변건수확인
	 * 양식 수정여부를 확인하기 위해서
	 */
	public int rchMstCnt(int rchno) throws Exception{
		int result = 0;
		
		result = getOutsideDAO().rchMstCnt(rchno);
		
		return result;
	}
	
	/**
	 * 설문조사 답변 삭제
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
	 * 설문조사 삭제
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
	 * 설문조사그룹 삭제
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
	 * 설문조사 결과 가져오기
	 * @return
	 * @throws Exception
	 */
	public String getRchResult() throws Exception {
		
		return getOutsideDAO().getRchResult();
	}
	
	/**
	 * 외부망 설문조사결과 전송완료 처리
	 * @throws Exception
	 */
	public void rchResultTransmitComplete() throws Exception {
		getOutsideDAO().rchResultTransmitComplete();
	}
	
	/**
	 * 외부망 신청서결과 전송완료 처리
	 * @throws Exception
	 */
	public void reqResultTransmitComplete() throws Exception {
		getOutsideDAO().reqResultTransmitComplete();
	}
	
	/**
	 * 외부망 설문조사결과 리스트
	 * @return
	 * @throws Exception
	 */
	public String getRchResultList(String uid, String resident, String committee) throws Exception {
		
		return getOutsideDAO().getRchResultList(uid, resident, committee);
	}
	
	/**
	 * 외부망 설문조사 리스트
	 * @return
	 * @throws Exception
	 */
	public String getRchList(String syear, String uid, String resident, String committee) throws Exception {
		
		return getOutsideDAO().getRchList(syear, uid, resident, committee);
	}
	
	/**
	 * 외부망 신청서 리스트
	 * @return
	 * @throws Exception
	 */
	public String getReqList(String syear, String uid, String resident, String committee) throws Exception {
		
		return getOutsideDAO().getReqList(syear, uid, resident, committee);
	}
	
	/**
	 * 설문조사 저장 
	 * @param rchForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public int ResearchSave(ResearchBean Bean, ServletContext sContext) throws Exception{
		int result = 0;

		result = getOutsideDAO().rchAllSave(Bean, sContext);
		//강남이 아닌경우만 아래 타도록 강남은 받을때 rchno 값으로 데이터 업데이트 
		{
			if ( result > 0 ) {
				//파일 업로드
				Calendar cal = Calendar.getInstance();
				String saveDir = appInfo.getResearchSampleDir() + cal.get(Calendar.YEAR) + "/";
				File saveFolder = new File(appInfo.getRootRealPath() + saveDir);
				if(!saveFolder.exists()) saveFolder.mkdirs();
				
				//문항첨부파일 생성
				for ( int i = 0; Bean.getSubFileList() != null && i < Bean.getSubFileList().size(); i++ ) {
					ResearchSubBean rsBean = (ResearchSubBean)Bean.getSubFileList().get(i);
					String fileName = appInfo.getRootRealPath() + rsBean.getFilenm();
					String fileData = Utils.nullToEmptyString(rsBean.getFileToBase64Encoding());
					base64DecodeToFile(fileName, fileData);
				}
				
				//보기첨부파일 생성
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
	 * 설문조사그룹 저장 
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
	 * 설문조사상태
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
	 * 설문조사상태
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
	 * 설문조사 항목 목록 
	 * @param rchno
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public List getRchSubList(int rchno, String sessionId, String mode) throws Exception{		
		return getOutsideDAO().getRchSubList(rchno, sessionId, mode); 
	}
	
	/**
	 * 신청내역 저장
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
			//저장
			result = getOutsideDAO().insertReqData(mbean, sessi, context, saveDir);
		} else {
			//수정
			result = getOutsideDAO().updateReqData(mbean, context, saveDir);
		}
		
		return result;
	}
	
	/**
	 * 신청결과삭제
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
	 * 신청서양식 관련 모든 테이블 삭제	
	 */
	public int RequestDel(int reqformno, ServletContext sContext) throws Exception {
		int result = 0;
		
		result = getOutsideDAO().RequestDel(reqformno, sContext);
		
		return result;
	}
	
	/**
	 * 신청서 결과
	 * @return
	 * @throws Exception
	 */
	public String getReqResult(ServletContext sContext) throws Exception {
		
		return getOutsideDAO().getReqResult(sContext);
	}
	
	/**
	 * 신청서 저장
	 * @param rchForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public int RequestSave(FrmBean Bean, ServletContext sContext) throws Exception{
		int result = 0;

		result = getOutsideDAO().reqAllSave(Bean, sContext);
		
		//강남이 아닌경우만 아래 타도록 강남은 받을때 reqformno 값으로 데이터 업데이트 
		{
			if ( result > 0 ) {
				//파일 업로드
				Calendar cal = Calendar.getInstance();
				String saveDir = appInfo.getRequestSampleDir() + cal.get(Calendar.YEAR) + "/";
				File saveFolder = new File(appInfo.getRootRealPath() + saveDir);
				if(!saveFolder.exists()) saveFolder.mkdirs();
				
				//문항첨부파일 생성
				for ( int i = 0; Bean.getSubFileList() != null && i < Bean.getSubFileList().size(); i++ ) {
					ArticleBean aBean = (ArticleBean)Bean.getSubFileList().get(i);
					String fileName = appInfo.getRootRealPath() + aBean.getFilenm();
					String fileData = Utils.nullToEmptyString(aBean.getFileToBase64Encoding());
					base64DecodeToFile(fileName, fileData);
				}
				
				//보기첨부파일 생성
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
////////////////////// 설문조사 MANAGER 시작 //////////////////////
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
	 * 신청서 임시테이블 삭제
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
	 * 설문결과 가져오기
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
	 * 내 설문결과 개수 가져오기
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
	 * 설문답변자 목록 가져오기
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
	 * 설문조사그룹 마스터 가져오기
	 * @param rchno
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public ResearchBean getRchGrpMst(int rchgrpno, String sessionId) throws Exception{		
		return getOutsideDAO().getRchGrpMst(rchgrpno, sessionId); 
	}
	
	/**
	 * 설문조사 첨부파일 삭제
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
	 * 설문조사 첨부파일 삭제
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
			
		if ( examseq == 0 ) {	//문항첨부파일 삭제
			ResearchSubBean rchSubBean = getOutsideDAO().getRchSubFile(conn, sessionId, rchno, formseq);
			
			if ( rchSubBean != null ) {
				getOutsideDAO().delRchSubFile(conn, sessionId, rchno, formseq, rchSubBean.getFileseq());
				
				String delFile = rchSubBean.getFilenm();
				if ( delFile != null && delFile.trim().equals("") == false) {
					FileManager.doFileDelete(context.getRealPath(delFile));
				}
			}
		} else {	//보기첨부파일삭제
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
			//항목삭제
			result = getOutsideDAO().rchDelSub(rchno, sessionId, rchForm.getDelseq(), context);
			
		} else if("add".equals(mode)){
			if ( getOutsideDAO().getMaxSubSeq(rchno, sessionId) < 50 ) {
				//항목추가
				result = getOutsideDAO().insAddSub(rchno, sessionId);
			}
			
		} else if("make".equals(mode)){
			//항목만들기
			result = getOutsideDAO().insMakeSub(rchno, sessionId, Bean.getFormcount());			
			
		} else if("prev".equals(mode)){
			//미리보기
		} else if("comp".equals(mode)){
			//완료
			if(rchno == 0){
				resultrchno = getOutsideDAO().rchInsComp(sessionId, Bean, context, saveDir);
			} else {
				resultrchno = rchno;
			}
		}
		
		if(result >= 0) { 
			//성공
			result = 0;		
		} 
		
		//완료시에 받아온 신청양식 번호가 있으면 양식번호를 Return
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
		String[] formgrp = rchForm.getFormgrp();		//문학주제
		String[] formtitle = rchForm.getFormtitle();	//문항제목
		String[] formtype = rchForm.getFormtype();   	//입력형태(1:단일 2:복수 3:단답 4:장문)
		String[] security = rchForm.getSecurity();		//보안처리여부(Y:보안처리, N:없음)
		String[] examtype = rchForm.getExamtype();		//기타답변여
	
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
			String[] examcont = rchForm.getExamcont();	//보기제목
			
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
		
        int rtnCode = hcp.execute(); // 실행하기 
       
        if(rtnCode == 200){
        	result = Integer.parseInt(hcp.getContent(), 10);
        }
        
		return result;
	}
	
	/**
	 * 설문 마감 	
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
	 * 문항당 보기개수 가져오기
	 * @param rchno
	 * @return
	 * @throws Exception
	 */
	public int getRchSubExamcount(int rchno, String sessionId) throws Exception {
		return getOutsideDAO().getRchSubExamcount(rchno, sessionId);
	}
///////////////////////////////////////////////////////////////
/////////////////////// 설문조사 MANAGER 끝 //////////////////////
///////////////////////////////////////////////////////////////


/////////////////////////////////////////////////////////////////
/////////////////////// 신청서 MANAGER 시작 ////////////////////////
/////////////////////////////////////////////////////////////////
	/**
	 * 신청문항보기첨부파일 가져오기
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
	 * 메인화면에 보여줄 목록 가져오기	
	 */
	public List mainShowList() throws Exception{
		List result = null;
		
		result = getOutsideDAO().mainShowList();
		
		return result;		
	}
	
	/**
	 * 신청이 진행중인 건이 있는지 확인한다.
	 * gbn(1): 결재전, gbn(2):결재후
	 * 양식 수정여부를 확인하기 위해서
	 */
	public int reqMstCnt(int reqformno, String gbn) throws Exception{
		int result = 0;
		
		result = getOutsideDAO().reqMstCnt(reqformno, gbn);
		
		return result;
	}
	
	/**
	 * 신청 진행중(접수중)인 신청서 건수
	 */
	public int jupsuCnt(String userid) throws Exception{
		int result = 0;
		
		result = getOutsideDAO().jupsuCnt(userid);
		
		return result;
	}
	
	/**
	 * 미처리된 접수 건수
	 */
	public int notProcCnt(String userid) throws Exception{
		int result = 0;
		
		result = getOutsideDAO().notProcCnt(userid);
		
		return result;
	}
	
	/**
	 * 신청하기 목록	
	 */
	public List doSinchungList(SearchBean search) throws Exception{
		List result = null;
		
		result = getOutsideDAO().doSinchungList(search);
		
		return result;		
	}
	
	/**
	 * 신청하기 전체건수 가져오기
	 */
	public int doSinchungTotCnt(SearchBean search) throws Exception {
		int result = 0;
		
		result = getOutsideDAO().doSinchungTotCnt(search);
		
		return result;
	}
	
	/**
	 * 내신청함 목록	
	 */
	public List mySinchungList(SearchBean search) throws Exception{
		List result = null;
		
		result = getOutsideDAO().mySinchungList(search);
		
		return result;		
	}
	
	/**
	 * 내신청함 전체건수 가져오기
	 */
	public int mySinchungTotCnt(SearchBean search) throws Exception {
		int result = 0;
		
		result = getOutsideDAO().mySinchungTotCnt(search);
		
		return result;
	}
	
	/**
	 * 접수내역 목록 가져오기
	 */
	public List reqDataList(SearchBean search) throws Exception {
		List result = null;
		
		result = getOutsideDAO().reqDataList(search);
		
		return result;
	}
	
	/**
	 * 접수내역 목록 전체 건수 가져오기
	 */
	public int reqDataTotCnt(SearchBean search) throws Exception {
		int result = 0;
		
		result = getOutsideDAO().reqDataTotCnt(search);
		
		return result;
	}
	
	/**
	 * 신청 내역 가져오기
	 */
	public ReqMstBean reqDataInfo(int reqformno, int reqseq) throws Exception {
		ReqMstBean mstInfo = null;
		
		mstInfo = getOutsideDAO().reqDataInfo(reqformno, reqseq);
		
		return mstInfo;
	}
	
	/**
	 * 신청서 관리 목록	
	 */
	public List reqFormList(SearchBean search) throws Exception{
		List result = null;
		
		result = getOutsideDAO().reqFormList(search);
		
		return result;		
	}
	
	/**
	 * 신청서 관리 전체건수 가져오기
	 */
	public int reqFormTotCnt(SearchBean search) throws Exception {
		int result = 0;
		
		result = getOutsideDAO().reqFormTotCnt(search);
		
		return result;
	}
	
	/**
	 * 기존양식 가져오기 목록	
	 */
	public List getUsedList(SearchBean search) throws Exception{
		List result = null;
		
		result = getOutsideDAO().getUsedList(search);
		
		return result;		
	}
	
	/**
	 * 기존양식 가져오기 전체건수 가져오기
	 */
	public int getUsedTotCnt(SearchBean search) throws Exception {
		int result = 0;
		
		result = getOutsideDAO().getUsedTotCnt(search);
		
		return result;
	}
	
	/**
	 * 기존양식 가져오기에서 선택
	 * 임시 테이블로 복사
	 */
	public int selectUsed(int reqformno, String sessi, ServletContext context, String saveDir) throws Exception{
		int result = 0;
		
		result = getOutsideDAO().selectUsed(reqformno, sessi, context, saveDir);
			
		return result;
	}
	
	/**
	 * 신청서 양식마스터 정보 보기
	 */
	public FrmBean reqFormInfo(int reqformno) throws Exception{
		FrmBean frmInfo = null;
		
		frmInfo = getOutsideDAO().reqFormInfo(reqformno);
		
		return frmInfo;
	}
	
	/**
	 * 신청서 양식마스터 정보 보기(TEMP)
	 */
	public FrmBean reqFormInfo_temp(String sessi) throws Exception{
		FrmBean frmInfo_temp = null;
		
		frmInfo_temp = getOutsideDAO().reqFormInfo_temp(sessi);
		
		return frmInfo_temp;
	}
	
	/**
	 * 신청서 문항목록 가져오기
	 */
	public List reqFormSubList(int reqformno) throws Exception{
		List result = null;
		
		result = getOutsideDAO().reqFormSubList(reqformno);
		
		return result;		
	}
	
	/**
	 * 신청서 문항목록 가져오기(TEMP)
	 */
	public List reqFormSubList_temp(String sessi, int examcount) throws Exception{
		List result = null;
		
		result = getOutsideDAO().reqFormSubList_temp(sessi, examcount);
		
		return result;		
	}
	
	/**
	 * 임시 테이블 삭제	
	 */
	public int deleteTempAll(String sessi, ServletContext context) throws Exception {
		int result = 0;
			
		result = getOutsideDAO().deleteTempAll(sessi, context);
		
		return result;
	}
	
	/**
	 * 임시테이블로 양식 데이터 복사
	 */
	public int copyToTemp(int reqformno, String sessi, ServletContext context, String saveDir) throws Exception {
		int result = 0;
		
		result = getOutsideDAO().copyToTemp(reqformno, sessi, context, saveDir);
		
		return result;
	}
	
	/**
	 * 신청서양식 관련 모든 테이블 삭제	
	 */
	public int deleteAll(int reqformno, String userid, ServletContext context) throws Exception {
		int result = 0;
		
		result = getOutsideDAO().deleteAll(reqformno, userid, context);
		
		return result;
	}	
	
	/**
	 * 신청취소
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
	 * 신청마스터에서 신청 일련번호 가져오기 (REQSEQ)
	 */
	public int getMaxReqseq(int reqformno) throws Exception{
		
		return getOutsideDAO().getMaxReqseq(reqformno);
	}
	
	/**
	 * 신청내역 저장
	 */
	public int saveReqData(DataForm dForm, String userid, String sessi, ServletContext context, String saveDir) throws Exception{
		int result = 0;
		
		ReqMstBean mbean = new ReqMstBean();
		BeanUtils.copyProperties(mbean, dForm);			
		mbean.setCrtusrid(userid);
		mbean.setUptusrid(userid);		
		mbean.setAnscontList(extraArticle(dForm, mbean));		
		
		if("s".equals(dForm.getMode())){
			//저장
			result = getOutsideDAO().insertReqData(mbean, sessi, context, saveDir);
		} else {
			//수정
			result = getOutsideDAO().updateReqData(mbean, context, saveDir);
		}
		
		return result;
	}
	
	/**
	 * 접수처리
	 * gbn: 일괄접수처리토글(all), 접수완료(1), 접수보류(2), 미결재(3), 신청중(4)
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
	 * 결재선 승인/검토 데이터 가져오기
	 * gubun: 검토(1),  승인(2)
	 */
	public List approvalInfo(String gubun, int reqformno, int reqseq) throws Exception {
		List result = null;
		
		result = getOutsideDAO().approvalInfo(gubun, reqformno, reqseq);
		
		return result;
	}
	
	/**
	 * 신청서 결재
	 */
	public int doSanc (int reqformno, int reqseq, String userid) throws Exception{
		int result = 0;
		
		result = getOutsideDAO().doSanc(reqformno, reqseq, userid);
		
		return result;
	}
	
	/**
	 * 신청서 마감 	
	 */
	public int docClose(int reqformno, String userid) throws Exception {
		int result = 0;
		
		result = getOutsideDAO().docClose(reqformno, userid);
		
		return result;
	}
	
	/**
	 * 결재된 건이 있는지 확인
	 */
	public boolean existSanc(int reqformno, int reqseq) throws Exception {
		boolean result = false;
				
		result = getOutsideDAO().existSanc(reqformno, reqseq);
				
		return result;
	}
	
	/**
	 * 추가항목 입력값 읽어오기
	 */
	private List extraArticle(DataForm dForm, ReqMstBean mbean)throws Exception{
		List ansList = null;
		List article = reqFormSubList(dForm.getReqformno());  //추가문항 정보	
		
		if(article != null){			
			ansList = new ArrayList();
			
			int idx_other = 0;	int idx_text  = 0;	int idx_area  = 0;
			for(int i=0;i<article.size();i++){
				//신청서 양식정보
				ArticleBean abean = (ArticleBean)article.get(i);   
				
				ReqSubBean subbean = new ReqSubBean();       //신청서 입력정보	
				subbean.setReqformno(dForm.getReqformno());  //신청양식번호
				subbean.setReqseq(dForm.getReqseq());        //신청번호
				subbean.setFormseq(dForm.getFormseq()[i]);   //문항번호
				
				String chk = "";
				switch(Integer.parseInt(abean.getFormtype())){
					case 1:    //단일선택형	
						
						subbean.setAnscont(dForm.getRadioans()[i]);
												
						//기타의견 셋팅
						if("Y".equals(abean.getExamtype())){
							if("5".equals(dForm.getRadioans()[i])){
								//기타가 선택되었을때만 기록
								subbean.setOther(dForm.getOther()[idx_other]);								
							}
							idx_other++;
						}										
												
						break;
					case 2:    //복수선택형		
						
						//체크박스 읽어오기
						boolean gitafl = false;  //기타체크여부
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
						
						//기타의견 셋팅
						if("Y".equals(abean.getExamtype())){
							if(gitafl){
								//기타가 선택되었을때만 기록
								subbean.setOther(dForm.getOther()[idx_other]);
							}
							idx_other++;
						}		
					
						break;
					case 3:    //단답형
						subbean.setAnscont(dForm.getTxtans()[idx_text]);
						idx_text++;
						break;
					case 4:    //장문형						
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
	 * 양식작성
	 */
	public int makeReqForm(SinchungForm sForm, ServletContext context, String saveDir) throws Exception{
				
		int result = -1;
		int resultreq = 0;
		int reqformno = sForm.getReqformno();		
		
		//frmBean 값 입력
		FrmBean fbean = new FrmBean();
		BeanUtils.copyProperties(fbean, sForm);	
		
		//입력기본정보 셋팅
		String btype = getBasicType(sForm.getBtype());
		fbean.setBasictype(btype);		
		
		//추가항목 문항값 설정		
		fbean.setArticleList(getArticle(sForm));
	
		//추가항목 첨부파일 설정
		sForm.setArticleList(fbean.getArticleList());
		fbean.setExamcontFile(orderExamcontFile(sForm));
		
		//항상 저장하는 로직을 수행한다.
		result = getOutsideDAO().saveAll(fbean, sForm, context, saveDir);
		
		if("del".equals(sForm.getMode())){
			//항목삭제
			result = getOutsideDAO().deleteArticle(0, sForm.getSessi(), sForm.getDelseq(), context);			
			
		} else if("add".equals(sForm.getMode())){
			if ( getOutsideDAO().getMaxFormseq(0, sForm.getSessi()) < 20 ) {
				//항목추가
				result = getOutsideDAO().insertArticle(0, sForm.getSessi());
			}
			
		} else if("make".equals(sForm.getMode())){
			//항목만들기			
			result = getOutsideDAO().makeArticle(0, sForm.getSessi(), fbean.getAcnt());
			
		} else if("comp".equals(sForm.getMode())){
			//완료
			if(reqformno == 0){
				resultreq = getOutsideDAO().saveComp(sForm.getSessi(), context, saveDir);
			} else {
				resultreq = getOutsideDAO().updateComp(reqformno, sForm.getSessi(), context, saveDir);
			}
		}
		
		if(result >= 0) { 
			//성공
			result = 0;						
		} 
		
		//완료시에 받아온 신청양식 번호가 있으면 양식번호를 Return
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
	 * 항목값 읽어오기
	 */
	private List getArticle(SinchungForm sForm) {
		List result = new ArrayList();
				
		int[] formseq = sForm.getFormseq();
		String[] title = sForm.getFormtitle();      //항목제목
		String[] require = sForm.getRequire();      //필수입력여부
		String[] formtype = sForm.getFormtype();    //입력형태
		String[] security = sForm.getSecurity();    //보안처리(Y:보안처리, N:없음)
		String[] helpexp  = sForm.getHelpexp();     //추가설명
		String[] examtype = sForm.getExamtype();    //기타YN
		
		if(formseq != null){
			for(int i=0;i<formseq.length;i++){
				ArticleBean abean = new ArticleBean();
				abean.setReqformno(sForm.getReqformno());
				abean.setSessi(sForm.getSessi());
				abean.setFormseq(formseq[i]);
				abean.setFormtitle(title[i]);
				
				//필수입력여부 설정
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
				
				//입력형태 설정
				abean.setFormtype(formtype[i]);
				
				//추가설명 설정(입력형태가 단답형(3), 장문형(4) 일때만 기록)
				if(Integer.parseInt(formtype[i]) >= 3){
					abean.setHelpexp(helpexp[i]);
				}
				
				//보안처리 설정
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
				
				//기타YN 설정
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
				
				//보기 설정
				abean.setSampleList(getSample(sForm, formseq[i], i));
				
				result.add(abean);
			}
		}
		return result;
	}
	
	/**
	 * 보기 읽어오기
	 */
	private List getSample(SinchungForm sForm, int formseq, int bunho) {
		List result = new ArrayList();		
		
		if (  sForm.getExamcont() != null ) {
			int subcount = sForm.getAcnt();
			int examcount = sForm.getExamcount();
			String[] examcont = sForm.getExamcont();	//보기제목
			
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
	 * 입력기본정보 값 읽어오기
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
		
        int rtnCode = hcp.execute(); // 실행하기 
       
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
	 * 문항당 보기개수 가져오기
	 * @param rchno
	 * @return
	 * @throws Exception
	 */
	public int getReqSubExamcount(int rchno, String sessionId) throws Exception {
		return getOutsideDAO().getReqSubExamcount(rchno, sessionId);
	}
	
	/**
	 * 결재진행중인 건수 체크
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public int apprProcCount(String userid) throws Exception {
		return getOutsideDAO().apprProcCount(userid);
	}
///////////////////////////////////////////////////////////////
/////////////////////// 신청서 MANAGER 끝 ////////////////////////
///////////////////////////////////////////////////////////////
}