/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 신청서 manager
 * 설명:
 */
package nexti.ejms.sinchung.model;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import nexti.ejms.common.ConnectionManager;
import nexti.ejms.common.appInfo;
import nexti.ejms.sinchung.form.SinchungForm;
import nexti.ejms.sinchung.form.DataForm;
import nexti.ejms.util.Base64;
import nexti.ejms.util.HttpClientHp;
import nexti.ejms.util.Utils;

public class SinchungManager {
	
	private static Logger logger = Logger.getLogger(SinchungManager.class);
	
	private static SinchungManager instance = null;
	private static SinchungDAO dao = null;
	
	public static SinchungManager instance() {
		
		if(instance == null)
			instance = new SinchungManager();
		return instance;
	}

	private SinchungDAO getSinchungDAO() {
		
		if(dao == null)
			dao = new SinchungDAO(); 
		return dao;
	}
	
	/**
	 * 특정 처리상태의 전체개수 가져오기
	 * @param userid
	 * @param deptcd
	 * @param state
	 * @return
	 * @throws Exception
	 */
	public int getStateTotalCount(String userid, String deptcd, String state) throws Exception {
		return getSinchungDAO().getStateTotalCount(userid, deptcd, state);
	}
	
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
			
			result = getSinchungDAO().getReqFormExamFile(conn, sessionId, reqformno, formseq, examseq);
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
	 * 메인화면에 보여줄 목록 가져오기 : 구버전용, 최신버전에는 사용안함	
	 */
	public List mainShowList(String rep_dept) throws Exception{
		List result = null;
		
		result = getSinchungDAO().mainShowList(rep_dept);
		
		return result;		
	}
	
	/**
	 * 메인화면에 보여줄 목록 가져오기	
	 */
	public List mainShowList(String userid, String rep_dept) throws Exception{
		List result = null;
		
		result = getSinchungDAO().mainShowList(userid, rep_dept);
		
		return result;		
	}
	
	
	/**
	 * 신청이 진행중인 건이 있는지 확인한다.
	 * gbn(1): 결재전, gbn(2):결재후
	 * 양식 수정여부를 확인하기 위해서
	 */
	public int reqMstCnt(int reqformno, String gbn) throws Exception{
		int result = 0;
		
		result = getSinchungDAO().reqMstCnt(reqformno, gbn);
		
		return result;
	}
	
	/**
	 * 신청 진행중(접수중)인 신청서 건수
	 */
	public int jupsuCnt(String userid) throws Exception{
		int result = 0;
		
		result = getSinchungDAO().jupsuCnt(userid);
		
		return result;
	}
	
	/**
	 * 미처리된 접수 건수
	 */
	public int notProcCnt(String userid) throws Exception{
		int result = 0;
		
		result = getSinchungDAO().notProcCnt(userid);
		
		return result;
	}
	
	/**
	 * 신청하기 목록	
	 */
	public List doSinchungList(SearchBean search) throws Exception{
		List result = null;
		
		result = getSinchungDAO().doSinchungList(search);
		
		return result;		
	}
	
	/**
	 * 신청하기 전체건수 가져오기
	 */
	public int doSinchungTotCnt(SearchBean search) throws Exception {
		int result = 0;
		
		result = getSinchungDAO().doSinchungTotCnt(search);
		
		return result;
	}
	
	/**
	 * 내신청함 목록	
	 */
	public List mySinchungList(SearchBean search) throws Exception{
		List result = null;
		
		result = getSinchungDAO().mySinchungList(search);
		
		return result;		
	}
	
	/**
	 * 내신청함 전체건수 가져오기
	 */
	public int mySinchungTotCnt(SearchBean search) throws Exception {
		int result = 0;
		
		result = getSinchungDAO().mySinchungTotCnt(search);
		
		return result;
	}
	
	/**
	 * 접수내역 목록 가져오기
	 */
	public List reqDataList(SearchBean search) throws Exception {
		List result = null;
		
		result = getSinchungDAO().reqDataList(search);
		
		return result;
	}
	
	/**
	 * 접수내역 목록 전체 건수 가져오기
	 */
	public int reqDataTotCnt(SearchBean search) throws Exception {
		int result = 0;
		
		result = getSinchungDAO().reqDataTotCnt(search);
		
		return result;
	}
	
	/**
	 * 신청 내역 가져오기
	 */
	public ReqMstBean reqDataInfo(int reqformno, int reqseq) throws Exception {
		ReqMstBean mstInfo = null;
		
		mstInfo = getSinchungDAO().reqDataInfo(reqformno, reqseq);
		
		return mstInfo;
	}
	
	/**
	 * 신청서 관리 목록	
	 */
	public List reqFormList(SearchBean search, String initentry, String isSysMgr, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm) throws Exception{
		List result = null;
		
		result = getSinchungDAO().reqFormList(search, initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm);
		
		return result;		
	}
	
	/**
	 * 신청서 관리 전체건수 가져오기
	 */
	public int reqFormTotCnt(SearchBean search, String initentry, String isSysMgr, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm) throws Exception {
		int result = 0;
		
		result = getSinchungDAO().reqFormTotCnt(search, initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm);
		
		return result;
	}
	
	/**
	 * 기존양식 가져오기 목록	
	 */
	public List getUsedList(SearchBean search) throws Exception{
		List result = null;
		
		result = getSinchungDAO().getUsedList(search);
		
		return result;		
	}
	
	/**
	 * 기존양식 가져오기 전체건수 가져오기
	 */
	public int getUsedTotCnt(SearchBean search) throws Exception {
		int result = 0;
		
		result = getSinchungDAO().getUsedTotCnt(search);
		
		return result;
	}
	
	/**
	 * 기존양식 가져오기에서 선택
	 * 임시 테이블로 복사
	 */
	public int selectUsed(int reqformno, String sessi, ServletContext context, String saveDir) throws Exception{
		int result = 0;
		
		result = getSinchungDAO().selectUsed(reqformno, sessi, context, saveDir);
			
		return result;
	}
	
	/**
	 * 신청서 양식마스터 정보 보기
	 */
	public FrmBean reqFormInfo(int reqformno) throws Exception{
		FrmBean frmInfo = null;
		
		frmInfo = getSinchungDAO().reqFormInfo(reqformno);
		
		return frmInfo;
	}
	
	/**
	 * 신청서 양식마스터 정보 보기(TEMP)
	 */
	public FrmBean reqFormInfo_temp(String sessi) throws Exception{
		FrmBean frmInfo_temp = null;
		
		frmInfo_temp = getSinchungDAO().reqFormInfo_temp(sessi);
		
		return frmInfo_temp;
	}
	
	/**
	 * 신청서 문항목록 가져오기
	 */
	public List reqFormSubList(int reqformno) throws Exception{
		List result = null;
		
		result = getSinchungDAO().reqFormSubList(reqformno);
		
		return result;		
	}
	
	/**
	 * 신청서 문항목록 가져오기(TEMP)
	 */
	public List reqFormSubList_temp(String sessi, int examcount) throws Exception{
		List result = null;
		
		result = getSinchungDAO().reqFormSubList_temp(sessi, examcount);
		
		return result;		
	}
	
	/**
	 * 임시 테이블 삭제	
	 */
	public int deleteTempAll(String sessi, ServletContext context) throws Exception {
		int result = 0;
			
		result = getSinchungDAO().deleteTempAll(sessi, context);
		
		return result;
	}
	
	/**
	 * 임시테이블로 양식 데이터 복사
	 */
	public int copyToTemp(int reqformno, String sessi, ServletContext context, String saveDir) throws Exception {
		int result = 0;
		
		result = getSinchungDAO().copyToTemp(reqformno, sessi, context, saveDir);
		
		return result;
	}
	
	/**
	 * 신청서양식 관련 모든 테이블 삭제	
	 */
	public int deleteAll(int reqformno, String userid, ServletContext context) throws Exception {
		int result = 0;
		
		result = getSinchungDAO().deleteAll(reqformno, userid, context);
		
		return result;
	}	
	
	/**
	 * 신청취소
	 */
	public int cancelSinchung(int reqformno, int reqseq, int gubun, String userid, ServletContext context) throws Exception {
		int result = 0;
		
		if ( gubun == 1 ) {
			result = getSinchungDAO().cancelSinchung(reqformno, reqseq, userid, context);
		} else {
			result = getSinchungDAO().cancelSanc(reqformno, reqseq);
			
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
		
		return getSinchungDAO().getMaxReqseq(reqformno);
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
			result = getSinchungDAO().insertReqData(mbean, sessi, context, saveDir);
		} else {
			//수정
			result = getSinchungDAO().updateReqData(mbean, context, saveDir);
		}
		
		return result;
	}
	
	/**
	 * 접수처리
	 * gbn: 일괄접수처리토글(all), 접수완료(1), 접수보류(2), 미결재(3), 신청중(4)
	 */
	public int procJupsu(String gbn, int reqformno, int reqseq) throws Exception {
		return procJupsu(gbn, reqformno, Integer.toString(reqseq));
	}
	
	/**
	 * 접수처리
	 * gbn: 일괄접수처리토글(all), 접수완료(1), 접수보류(2), 미결재(3), 신청중(4)
	 */
	public int procJupsu(String gbn, int reqformno, String reqseq) throws Exception {
		int result = 0;
		
		if ( gbn.equals("all") == true ) {
			result = getSinchungDAO().procTotalJupsu(reqformno);
		} else if ( gbn.equals("select") == true ) {
			result = getSinchungDAO().procSelectJupsu(reqformno, reqseq);
		} else {
			result = getSinchungDAO().procJupsu(gbn, reqformno, reqseq);
		}
		
		return result;
	}
	
	/**
	 * 결재선 승인/검토 데이터 가져오기
	 * gubun: 검토(1),  승인(2)
	 */
	public List approvalInfo(String gubun, int reqformno, int reqseq) throws Exception {
		List result = null;
		
		result = getSinchungDAO().approvalInfo(gubun, reqformno, reqseq);
		
		return result;
	}
	
	/**
	 * 신청서 결재
	 */
	public int doSanc (int reqformno, int reqseq, String userid) throws Exception{
		int result = 0;
		
		result = getSinchungDAO().doSanc(reqformno, reqseq, userid);
		
		return result;
	}
	
	/**
	 * 신청서 마감 	
	 */
	public int docClose(int reqformno, String userid) throws Exception {
		int result = 0;
		
		result = getSinchungDAO().docClose(reqformno, userid);
		
		return result;
	}
	
	/**
	 * 결재된 건이 있는지 확인
	 */
	public boolean existSanc(int reqformno, int reqseq) throws Exception {
		boolean result = false;
				
		result = getSinchungDAO().existSanc(reqformno, reqseq);
				
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
							if( Integer.toString(abean.getSampleList().size()).equals(dForm.getRadioans()[i])){
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
									if( Integer.toString(abean.getSampleList().size()).equals(chk_temp[1])){ gitafl = true; }
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
		
		int formcount = reqform.getAcnt();
		int examcount = reqform.getExamcount();
		
		List subList = null;
		if(reqform.getReqformno() == 0){
			subList = reqFormSubList_temp(reqform.getSessi(), examcount);
		} else {
			subList = reqFormSubList(reqform.getReqformno());
		}
		int subcount = subList.size();
		
		if ( reqform.getArticleList() == null || subcount == 0 ) return null;
		
		FormFile[] result = new FormFile[formcount * examcount];
		FormFile[] examcontFile = reqform.getExamcontFile();
		String[] examcontFileYN = reqform.getExamcontFileYN();
		String[] examcont = reqform.getExamcont();
		
		int prevExamcount = 0;
		while ( prevExamcount < reqform.getExamcontFile().length
				&& reqform.getExamcontFile()[prevExamcount] != null ) {
			prevExamcount++;
		}
		prevExamcount = prevExamcount / subcount;
		
		for ( int i = 0; i < reqform.getArticleList().size(); i++ ) {
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
	 * 양식작성
	 */
	public int makeReqForm(SinchungForm sForm, ServletContext context, String saveDir) throws Exception{
		logger.info("SinchungManager makeReqForm start");
		int result = -1;
		int resultreq = 0;
		int reqformno = sForm.getReqformno();
		String sessionId = sForm.getSessi();
		String mode = sForm.getMode();
		
		//frmBean 값 입력
		FrmBean fbean = new FrmBean();
		BeanUtils.copyProperties(fbean, sForm);	
		
		//입력기본정보 셋팅
		String btype = getBasicType(sForm.getBtype());
		fbean.setBasictype(btype);		
		
		sForm.setReqformno(0);
		
		//추가항목 문항값 설정		
		fbean.setArticleList(getArticle(sForm));
	
		//추가항목 첨부파일 설정
		sForm.setArticleList(fbean.getArticleList());
		fbean.setExamcontFile(orderExamcontFile(sForm));
		
		sForm.setReqformno(reqformno);
		
		//항상 저장하는 로직을 수행한다.
		result = getSinchungDAO().saveAll(fbean, sForm, context, saveDir);
		logger.info("mode["+mode+"]");
		if ( "del".equals(mode) ) {
			//항목삭제
			result = getSinchungDAO().deleteArticle(0, sessionId, sForm.getDelseq(), context);			
			
		} else if ( "add".equals(mode) ) {
			if ( getSinchungDAO().getMaxFormseq(0, sessionId) < 20 ) {
				//항목추가
				result = getSinchungDAO().insertArticle(0, sessionId);
			}
		} else if ( "make".equals(mode) ) {
			//항목만들기			
			result = getSinchungDAO().makeArticle(0, sessionId, fbean.getAcnt());
		} else if ( "prev".equals(mode) ) {
			//미리보기
		} else if ( "temp".equals(mode) || "comp".equals(mode) ) {
			//임시저장, 완료
			if ( reqformno == 0 ) {
				resultreq = getSinchungDAO().saveComp(sessionId, context, saveDir);
			} else {
				resultreq = getSinchungDAO().updateComp(reqformno, sessionId, context, saveDir);
			}
		}
		
		if ( result >= 0 ) { 
			//성공
			result = 0;
			
			//질문문항순서 변경
			if ( sForm.getChangeFormseq() != null && sForm.getFormseq().length == sForm.getChangeFormseq().length) {
				if ( "make".equals(mode) || "add".equals(mode) || "prev".equals(mode) || "comp".equals(mode)) {
					getSinchungDAO().changeFormseq(resultreq, sessionId, sForm.getChangeFormseq());
				} else if ( "temp".equals(mode) ) {
					//임시저장은 원본테이블과 임시테이블 모두 변경해야 함
					getSinchungDAO().changeFormseq(0, sessionId, sForm.getChangeFormseq());
					getSinchungDAO().changeFormseq(resultreq, sessionId, sForm.getChangeFormseq());
				}
			}
		} 
		
		//완료시에 받아온 신청양식 번호가 있으면 양식번호를 Return
		if ( resultreq > 0 ) {
			
			int outResult = 0;
			if ( ("temp".equals(mode) || "comp".equals(mode)) && "2".equals(sForm.getRange()) ) {
				outResult = SinchungOutside(fbean, resultreq);
			}
			
			if ( outResult < 0 ) { 
				result = outResult;
			} else  {
				result = resultreq;
			}
		}
		logger.info("SinchungManager makeReqForm end");
		return result;
	}
	
	/**
	 * 항목값 읽어오기
	 */
	private List getArticle(SinchungForm sForm) throws Exception {
		List result = new ArrayList();
				
		int[] formseq = sForm.getFormseq();
		String[] title = sForm.getFormtitle();      //항목제목
		String[] require = sForm.getRequire();      //필수입력여부
		String[] formtype = sForm.getFormtype();    //입력형태
		String[] security = sForm.getSecurity();    //보안처리(Y:보안처리, N:없음)
		String[] helpexp  = sForm.getHelpexp();     //추가설명
		String[] examtype = sForm.getExamtype();    //기타YN
		String[] frsqs = sForm.getFrsqs()[0].toString().split(",");
	    String[] exsqs = sForm.getExsqs()[0].toString().split(",");
		if(formseq != null){
			int formCount = sForm.getAcnt();
			if ( formCount > formseq.length  ) formCount = formseq.length;
			
			for(int i=0;i<formseq.length;i++){
				ArticleBean abean = new ArticleBean();
				abean.setReqformno(sForm.getReqformno());
				abean.setSessi(sForm.getSessi());
				abean.setFormseq(formseq[i]);
				abean.setFormtitle(title[i]);
				
				//필수입력여부 설정
				/*if(require != null){
					for(int j=0;j<require.length;j++){				
						if(require[j].equals(String.valueOf(i))){
							abean.setRequire("Y");
							break;
						}
					}
				} else {
					abean.setRequire("N");
				}*/
				 String requireYn ="N";
			        if (require != null) {
			            for (int j = 0; j < require.length; j++){
				              if (require[j].equals(String.valueOf(i))) {
				            	requireYn ="Y";
				                break;
				              }
			            }
			          }
			    abean.setRequire(requireYn);
				
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
				try {
		      		if(frsqs[i] instanceof String && frsqs[i] != null && !"".equals(frsqs[i])){
		      			abean.setEx_frsq(Integer.parseInt(frsqs[i]));
		      			abean.setEx_exsq(exsqs[i]);
		      		}else{
		      			abean.setEx_exsq("0");
		      			abean.setEx_frsq(0);
		      		}
				} catch(Exception e){ 
					abean.setEx_exsq("0");
					abean.setEx_frsq(0);
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
	private List getSample(SinchungForm sForm, int formseq, int bunho) throws Exception {
		List result = new ArrayList();		
		
		if ( sForm.getExamcont() != null ) {			
			
			String[] examcont = sForm.getExamcont();	//보기제목		
					
			int examcount = sForm.getExamcount();
			if ( examcount > sForm.getExamcont().length  ) examcount = sForm.getExamcont().length;
			
			List subList = null;
			if ( sForm.getReqformno() == 0 ) {
				subList = reqFormSubList_temp(sForm.getSessi(), examcount);
			} else {
				subList = reqFormSubList(sForm.getReqformno());
			}
			int subcount = subList.size();
			//int start = bunho * (examcont.length / subcount);
			int start = bunho * (subcount==0?0:(examcont.length / subcount));
			int end   = start + examcount;
			int examseq = 0;
			int count = 0;
			
			result = new ArrayList();
			SampleBean sbean = null;
			for(int i=start;i<end;i++){			
				sbean = new SampleBean();
				//if(count < examcont.length / subcount && !"".equals(examcont[i].trim())){
				if(count < (subcount==0?0:(examcont.length / subcount)) && !"".equals(examcont[i].trim())){
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
		logger.info("SinchungOutside start ["+reqformno+"]");
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
		hcp.setParam("rangedetail", bean.getRangedetail());
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
			System.out.println(i +" : "+subbean.getFormseq()+" : "+subbean.getFormtitle());
			hcp.setParam("require"+i, subbean.getRequire());
			hcp.setParam("formtitle"+i, subbean.getFormtitle());
			hcp.setParam("formtype"+i, subbean.getFormtype());
			hcp.setParam("security"+i, subbean.getSecurity());
			hcp.setParam("helpexp"+i, subbean.getHelpexp());
			hcp.setParam("examtype"+i, subbean.getExamtype());
			hcp.setParam("ex_frsq" + i, new Integer(subbean.getEx_frsq()).toString());			//동적 문항 추가(연계된 문항에 보기와 연계) 2018.2.28 
		    hcp.setParam("ex_exsq" + i, subbean.getEx_exsq());		//동적 문항 추가(연계된 문항에 보기와 연계) 2018.2.28 
			if(subbean.getSampleList() != null){
				hcp.setParam("examsize"+i, new Integer(subbean.getSampleList().size()).toString());
				for(int j=0; j<subbean.getSampleList().size(); j++){
					SampleBean exambean = (SampleBean)subbean.getSampleList().get(j);
					hcp.setParam("examseq"+i+""+j, new Integer(exambean.getExamseq()).toString());
					hcp.setParam("examcont"+i+""+j, exambean.getExamcont());
				}
			}
		}
		//강남이 아닌경우만 아래 타도록 강남은 받을때 reqformno 값으로 데이터 업데이트 
		{
			Connection conn = ConnectionManager.getConnection();
			int subFileCount = 0;
			int examFileCount = 0;
			for ( int i = 0; bean.getArticleList() != null && i < bean.getArticleList().size(); i++ ) {
				ArticleBean subbean = (ArticleBean)bean.getArticleList().get(i);
	
				ArticleBean rsBean = getSinchungDAO().getReqFormSubFile(conn, "", reqformno, subbean.getFormseq());
				if ( rsBean != null ) {
					hcp.setParam("subfilereqformno"+subFileCount, Integer.toString(reqformno));
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
				
				for ( int j = 0; subbean.getSampleList() != null && j < subbean.getSampleList().size(); j++ ) {
					SampleBean exambean = (SampleBean)subbean.getSampleList().get(j);
					
					SampleBean reBean = getSinchungDAO().getReqFormExamFile(conn, "", reqformno, subbean.getFormseq(), exambean.getExamseq());
					if ( reBean != null ) {
						hcp.setParam("examfilereqformno"+examFileCount, Integer.toString(reqformno));
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
		
        int rtnCode = hcp.execute(); // 실행하기 
       
        if(rtnCode == 200){
        	result = Integer.parseInt(hcp.getContent(), 10);
        }
        logger.info("SinchungOutside end ");
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
	 * *
	 * 
	 */
	public int reqState(int reqformno, String userid) throws Exception{
		int result = 0;
		result = getSinchungDAO().reqState(reqformno, userid);
		
		return result;
	}
	
	/**
	 ** 구버전용, 최신버전에는 사용안함
	 * 
	 */
	public int reqState(int reqformno) throws Exception{
		int result = 0;
		result = getSinchungDAO().reqState(reqformno);
		
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
		return getSinchungDAO().getReqOutsideList();
	}
	
	/**
	 * 문항당 보기개수 가져오기
	 * @param reqformno
	 * @return
	 * @throws Exception
	 */
	public int getReqSubExamcount(int reqformno, String sessionId) throws Exception {
		return getSinchungDAO().getReqSubExamcount(reqformno, sessionId);
	}
	
	/**
	 * 결재진행중인 건수 체크
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public int apprProcCount(String userid) throws Exception {
		return getSinchungDAO().apprProcCount(userid);
	}

	/**
	 * 신청서 - 관리자인경우 검색 조건에 해당하는 값 가져오기
	 */
	public String getSearch(SearchBean search, String sch_deptcd, String sch_userid) throws Exception{
		return getSinchungDAO().getSearch(search, sch_deptcd, sch_userid);
	}
}