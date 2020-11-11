/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ��û�� manager
 * ����:
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
	 * Ư�� ó�������� ��ü���� ��������
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
	 * ����ȭ�鿡 ������ ��� �������� : ��������, �ֽŹ������� ������	
	 */
	public List mainShowList(String rep_dept) throws Exception{
		List result = null;
		
		result = getSinchungDAO().mainShowList(rep_dept);
		
		return result;		
	}
	
	/**
	 * ����ȭ�鿡 ������ ��� ��������	
	 */
	public List mainShowList(String userid, String rep_dept) throws Exception{
		List result = null;
		
		result = getSinchungDAO().mainShowList(userid, rep_dept);
		
		return result;		
	}
	
	
	/**
	 * ��û�� �������� ���� �ִ��� Ȯ���Ѵ�.
	 * gbn(1): ������, gbn(2):������
	 * ��� �������θ� Ȯ���ϱ� ���ؼ�
	 */
	public int reqMstCnt(int reqformno, String gbn) throws Exception{
		int result = 0;
		
		result = getSinchungDAO().reqMstCnt(reqformno, gbn);
		
		return result;
	}
	
	/**
	 * ��û ������(������)�� ��û�� �Ǽ�
	 */
	public int jupsuCnt(String userid) throws Exception{
		int result = 0;
		
		result = getSinchungDAO().jupsuCnt(userid);
		
		return result;
	}
	
	/**
	 * ��ó���� ���� �Ǽ�
	 */
	public int notProcCnt(String userid) throws Exception{
		int result = 0;
		
		result = getSinchungDAO().notProcCnt(userid);
		
		return result;
	}
	
	/**
	 * ��û�ϱ� ���	
	 */
	public List doSinchungList(SearchBean search) throws Exception{
		List result = null;
		
		result = getSinchungDAO().doSinchungList(search);
		
		return result;		
	}
	
	/**
	 * ��û�ϱ� ��ü�Ǽ� ��������
	 */
	public int doSinchungTotCnt(SearchBean search) throws Exception {
		int result = 0;
		
		result = getSinchungDAO().doSinchungTotCnt(search);
		
		return result;
	}
	
	/**
	 * ����û�� ���	
	 */
	public List mySinchungList(SearchBean search) throws Exception{
		List result = null;
		
		result = getSinchungDAO().mySinchungList(search);
		
		return result;		
	}
	
	/**
	 * ����û�� ��ü�Ǽ� ��������
	 */
	public int mySinchungTotCnt(SearchBean search) throws Exception {
		int result = 0;
		
		result = getSinchungDAO().mySinchungTotCnt(search);
		
		return result;
	}
	
	/**
	 * �������� ��� ��������
	 */
	public List reqDataList(SearchBean search) throws Exception {
		List result = null;
		
		result = getSinchungDAO().reqDataList(search);
		
		return result;
	}
	
	/**
	 * �������� ��� ��ü �Ǽ� ��������
	 */
	public int reqDataTotCnt(SearchBean search) throws Exception {
		int result = 0;
		
		result = getSinchungDAO().reqDataTotCnt(search);
		
		return result;
	}
	
	/**
	 * ��û ���� ��������
	 */
	public ReqMstBean reqDataInfo(int reqformno, int reqseq) throws Exception {
		ReqMstBean mstInfo = null;
		
		mstInfo = getSinchungDAO().reqDataInfo(reqformno, reqseq);
		
		return mstInfo;
	}
	
	/**
	 * ��û�� ���� ���	
	 */
	public List reqFormList(SearchBean search, String initentry, String isSysMgr, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm) throws Exception{
		List result = null;
		
		result = getSinchungDAO().reqFormList(search, initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm);
		
		return result;		
	}
	
	/**
	 * ��û�� ���� ��ü�Ǽ� ��������
	 */
	public int reqFormTotCnt(SearchBean search, String initentry, String isSysMgr, String sch_deptcd, String sch_deptnm, String sch_userid, String sch_usernm) throws Exception {
		int result = 0;
		
		result = getSinchungDAO().reqFormTotCnt(search, initentry, isSysMgr, sch_deptcd, sch_deptnm, sch_userid, sch_usernm);
		
		return result;
	}
	
	/**
	 * ������� �������� ���	
	 */
	public List getUsedList(SearchBean search) throws Exception{
		List result = null;
		
		result = getSinchungDAO().getUsedList(search);
		
		return result;		
	}
	
	/**
	 * ������� �������� ��ü�Ǽ� ��������
	 */
	public int getUsedTotCnt(SearchBean search) throws Exception {
		int result = 0;
		
		result = getSinchungDAO().getUsedTotCnt(search);
		
		return result;
	}
	
	/**
	 * ������� �������⿡�� ����
	 * �ӽ� ���̺�� ����
	 */
	public int selectUsed(int reqformno, String sessi, ServletContext context, String saveDir) throws Exception{
		int result = 0;
		
		result = getSinchungDAO().selectUsed(reqformno, sessi, context, saveDir);
			
		return result;
	}
	
	/**
	 * ��û�� ��ĸ����� ���� ����
	 */
	public FrmBean reqFormInfo(int reqformno) throws Exception{
		FrmBean frmInfo = null;
		
		frmInfo = getSinchungDAO().reqFormInfo(reqformno);
		
		return frmInfo;
	}
	
	/**
	 * ��û�� ��ĸ����� ���� ����(TEMP)
	 */
	public FrmBean reqFormInfo_temp(String sessi) throws Exception{
		FrmBean frmInfo_temp = null;
		
		frmInfo_temp = getSinchungDAO().reqFormInfo_temp(sessi);
		
		return frmInfo_temp;
	}
	
	/**
	 * ��û�� ���׸�� ��������
	 */
	public List reqFormSubList(int reqformno) throws Exception{
		List result = null;
		
		result = getSinchungDAO().reqFormSubList(reqformno);
		
		return result;		
	}
	
	/**
	 * ��û�� ���׸�� ��������(TEMP)
	 */
	public List reqFormSubList_temp(String sessi, int examcount) throws Exception{
		List result = null;
		
		result = getSinchungDAO().reqFormSubList_temp(sessi, examcount);
		
		return result;		
	}
	
	/**
	 * �ӽ� ���̺� ����	
	 */
	public int deleteTempAll(String sessi, ServletContext context) throws Exception {
		int result = 0;
			
		result = getSinchungDAO().deleteTempAll(sessi, context);
		
		return result;
	}
	
	/**
	 * �ӽ����̺�� ��� ������ ����
	 */
	public int copyToTemp(int reqformno, String sessi, ServletContext context, String saveDir) throws Exception {
		int result = 0;
		
		result = getSinchungDAO().copyToTemp(reqformno, sessi, context, saveDir);
		
		return result;
	}
	
	/**
	 * ��û����� ���� ��� ���̺� ����	
	 */
	public int deleteAll(int reqformno, String userid, ServletContext context) throws Exception {
		int result = 0;
		
		result = getSinchungDAO().deleteAll(reqformno, userid, context);
		
		return result;
	}	
	
	/**
	 * ��û���
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
	 * ��û�����Ϳ��� ��û �Ϸù�ȣ �������� (REQSEQ)
	 */
	public int getMaxReqseq(int reqformno) throws Exception{
		
		return getSinchungDAO().getMaxReqseq(reqformno);
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
			result = getSinchungDAO().insertReqData(mbean, sessi, context, saveDir);
		} else {
			//����
			result = getSinchungDAO().updateReqData(mbean, context, saveDir);
		}
		
		return result;
	}
	
	/**
	 * ����ó��
	 * gbn: �ϰ�����ó�����(all), �����Ϸ�(1), ��������(2), �̰���(3), ��û��(4)
	 */
	public int procJupsu(String gbn, int reqformno, int reqseq) throws Exception {
		return procJupsu(gbn, reqformno, Integer.toString(reqseq));
	}
	
	/**
	 * ����ó��
	 * gbn: �ϰ�����ó�����(all), �����Ϸ�(1), ��������(2), �̰���(3), ��û��(4)
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
	 * ���缱 ����/���� ������ ��������
	 * gubun: ����(1),  ����(2)
	 */
	public List approvalInfo(String gubun, int reqformno, int reqseq) throws Exception {
		List result = null;
		
		result = getSinchungDAO().approvalInfo(gubun, reqformno, reqseq);
		
		return result;
	}
	
	/**
	 * ��û�� ����
	 */
	public int doSanc (int reqformno, int reqseq, String userid) throws Exception{
		int result = 0;
		
		result = getSinchungDAO().doSanc(reqformno, reqseq, userid);
		
		return result;
	}
	
	/**
	 * ��û�� ���� 	
	 */
	public int docClose(int reqformno, String userid) throws Exception {
		int result = 0;
		
		result = getSinchungDAO().docClose(reqformno, userid);
		
		return result;
	}
	
	/**
	 * ����� ���� �ִ��� Ȯ��
	 */
	public boolean existSanc(int reqformno, int reqseq) throws Exception {
		boolean result = false;
				
		result = getSinchungDAO().existSanc(reqformno, reqseq);
				
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
							if( Integer.toString(abean.getSampleList().size()).equals(dForm.getRadioans()[i])){
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
									if( Integer.toString(abean.getSampleList().size()).equals(chk_temp[1])){ gitafl = true; }
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
	 * ����ۼ�
	 */
	public int makeReqForm(SinchungForm sForm, ServletContext context, String saveDir) throws Exception{
		logger.info("SinchungManager makeReqForm start");
		int result = -1;
		int resultreq = 0;
		int reqformno = sForm.getReqformno();
		String sessionId = sForm.getSessi();
		String mode = sForm.getMode();
		
		//frmBean �� �Է�
		FrmBean fbean = new FrmBean();
		BeanUtils.copyProperties(fbean, sForm);	
		
		//�Է±⺻���� ����
		String btype = getBasicType(sForm.getBtype());
		fbean.setBasictype(btype);		
		
		sForm.setReqformno(0);
		
		//�߰��׸� ���װ� ����		
		fbean.setArticleList(getArticle(sForm));
	
		//�߰��׸� ÷������ ����
		sForm.setArticleList(fbean.getArticleList());
		fbean.setExamcontFile(orderExamcontFile(sForm));
		
		sForm.setReqformno(reqformno);
		
		//�׻� �����ϴ� ������ �����Ѵ�.
		result = getSinchungDAO().saveAll(fbean, sForm, context, saveDir);
		logger.info("mode["+mode+"]");
		if ( "del".equals(mode) ) {
			//�׸����
			result = getSinchungDAO().deleteArticle(0, sessionId, sForm.getDelseq(), context);			
			
		} else if ( "add".equals(mode) ) {
			if ( getSinchungDAO().getMaxFormseq(0, sessionId) < 20 ) {
				//�׸��߰�
				result = getSinchungDAO().insertArticle(0, sessionId);
			}
		} else if ( "make".equals(mode) ) {
			//�׸񸸵��			
			result = getSinchungDAO().makeArticle(0, sessionId, fbean.getAcnt());
		} else if ( "prev".equals(mode) ) {
			//�̸�����
		} else if ( "temp".equals(mode) || "comp".equals(mode) ) {
			//�ӽ�����, �Ϸ�
			if ( reqformno == 0 ) {
				resultreq = getSinchungDAO().saveComp(sessionId, context, saveDir);
			} else {
				resultreq = getSinchungDAO().updateComp(reqformno, sessionId, context, saveDir);
			}
		}
		
		if ( result >= 0 ) { 
			//����
			result = 0;
			
			//�������׼��� ����
			if ( sForm.getChangeFormseq() != null && sForm.getFormseq().length == sForm.getChangeFormseq().length) {
				if ( "make".equals(mode) || "add".equals(mode) || "prev".equals(mode) || "comp".equals(mode)) {
					getSinchungDAO().changeFormseq(resultreq, sessionId, sForm.getChangeFormseq());
				} else if ( "temp".equals(mode) ) {
					//�ӽ������� �������̺�� �ӽ����̺� ��� �����ؾ� ��
					getSinchungDAO().changeFormseq(0, sessionId, sForm.getChangeFormseq());
					getSinchungDAO().changeFormseq(resultreq, sessionId, sForm.getChangeFormseq());
				}
			}
		} 
		
		//�Ϸ�ÿ� �޾ƿ� ��û��� ��ȣ�� ������ ��Ĺ�ȣ�� Return
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
	 * �׸� �о����
	 */
	private List getArticle(SinchungForm sForm) throws Exception {
		List result = new ArrayList();
				
		int[] formseq = sForm.getFormseq();
		String[] title = sForm.getFormtitle();      //�׸�����
		String[] require = sForm.getRequire();      //�ʼ��Է¿���
		String[] formtype = sForm.getFormtype();    //�Է�����
		String[] security = sForm.getSecurity();    //����ó��(Y:����ó��, N:����)
		String[] helpexp  = sForm.getHelpexp();     //�߰�����
		String[] examtype = sForm.getExamtype();    //��ŸYN
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
				
				//�ʼ��Է¿��� ����
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
	private List getSample(SinchungForm sForm, int formseq, int bunho) throws Exception {
		List result = new ArrayList();		
		
		if ( sForm.getExamcont() != null ) {			
			
			String[] examcont = sForm.getExamcont();	//��������		
					
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
			hcp.setParam("ex_frsq" + i, new Integer(subbean.getEx_frsq()).toString());			//���� ���� �߰�(����� ���׿� ����� ����) 2018.2.28 
		    hcp.setParam("ex_exsq" + i, subbean.getEx_exsq());		//���� ���� �߰�(����� ���׿� ����� ����) 2018.2.28 
			if(subbean.getSampleList() != null){
				hcp.setParam("examsize"+i, new Integer(subbean.getSampleList().size()).toString());
				for(int j=0; j<subbean.getSampleList().size(); j++){
					SampleBean exambean = (SampleBean)subbean.getSampleList().get(j);
					hcp.setParam("examseq"+i+""+j, new Integer(exambean.getExamseq()).toString());
					hcp.setParam("examcont"+i+""+j, exambean.getExamcont());
				}
			}
		}
		//������ �ƴѰ�츸 �Ʒ� Ÿ���� ������ ������ reqformno ������ ������ ������Ʈ 
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
		
        int rtnCode = hcp.execute(); // �����ϱ� 
       
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
	 ** ��������, �ֽŹ������� ������
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
	 * ���״� ���ⰳ�� ��������
	 * @param reqformno
	 * @return
	 * @throws Exception
	 */
	public int getReqSubExamcount(int reqformno, String sessionId) throws Exception {
		return getSinchungDAO().getReqSubExamcount(reqformno, sessionId);
	}
	
	/**
	 * ������������ �Ǽ� üũ
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public int apprProcCount(String userid) throws Exception {
		return getSinchungDAO().apprProcCount(userid);
	}

	/**
	 * ��û�� - �������ΰ�� �˻� ���ǿ� �ش��ϴ� �� ��������
	 */
	public String getSearch(SearchBean search, String sch_deptcd, String sch_userid) throws Exception{
		return getSinchungDAO().getSearch(search, sch_deptcd, sch_userid);
	}
}