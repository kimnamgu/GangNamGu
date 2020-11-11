/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 프로그램 정보
 * 설명:
 */
package nexti.ejms.common;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import nexti.ejms.util.Base64;

public class appInfo {
	private static final String	CONFIG_FILE	=	"/main.properties";
	private static boolean		isLoading	=	false;
	private static Properties	pconfig		=	null;
	
	private static boolean	messanger;			//메신져연계
	private static String	messangerId;		//메신져연계시스템ID
	private static String	messangerCode;		//메신져연계시스템코드
	private static String	messangerUrl;		//메신져연계주소
	
	private static String	rootRealPath;		//웹어플리케이션 절대경로	
	private static String	appName;			//브라우저 타이틀
	private static String	web_address;		//WEB접속주소
	private static String	ap_address;			//AP접속주소
	private static String	rootid;				//기관코드
	private static boolean	multipleDelivery;	//다단취합 사용여부
	private static boolean	gpkilogin;			//gpki로그인 사용여부
	private static boolean	elecappr;			//전자결재연계 사용여부
	private static boolean	scheduleTimer;		//마감시한 에이전트 실행여부
	private static int 		scheduleInterval;	//마감시한 체크시간(초)
	private static boolean	deadlineTimer;		//마감시한 에이전트 실행여부
	private static int 		deadlineInterval;	//마감시한 체크시간(초)
	private static boolean  rchresultTimer;     //설문결과가져오기 마감시한 에이전트 실행여부
	private static int		rchresultInterval;  //설문결과가져오기 마감시한 체크시간(초)
	private static boolean  reqresultTimer;     //신청서결과가져오기 마감시한 에이전트 실행여부
	private static int		reqresultInterval;  //신청서결과가져오기 마감시한 체크시간(초)
	private static boolean  outside;			//외부망연계 실행여부
	private static String   outsideurl;			//외부망연계 주소
	private static String   outsideweburl;		//외부망연계 주소(행정망에서 민원서비스시)
	private static String 	serverkey;			//외부망연계 인증키(기본값:차세대123)
	private static String	uploadDir;			//업로드 디렉토리
	private static String	bookFrmCmptDir;		//제본자료형 최종자료
	private static String	bookFrmCommSampleDir; //제본자료형 공통양식샘플
	private static String	bookFrmDataDir;		//제본자료형 입력자료
	private static String	bookFrmSampleDir;	//제본자료형 샘플
	private static String	colldocSampleDir;	//취합문서 첨부파일
	private static String	noticeDir;			//공지사항 첨부파일
	private static String	requestDataDir;		//신청서 제출파일
	private static String	requestSampleDir;	//신청서 문항,보기 파일
	private static String	researchSampleDir;	//설문조사 문항,보기 파일
	private static String	workplanDir;			//업무관리 첨부파일
	private static String	tempDir;			//임시저장
	private static boolean	usrdept;			//사용자조직연계 에이전트 실행여부[새올행정]
	private static String	usrdeptDbtype;		//사용자조직연계 DB정보[새올행정]
	private static String	usrdeptDbip;		//사용자조직연계 DB IP [새올행정]
	private static String	usrdeptDbuser;		//사용자조직연계 DB 사용자[새올행정]
	private static String	usrdeptDbpass;		//사용자조직연계 DB 비밀번호[새올행정]
	private static String	usrdeptUserTable;	//사용자조직연계 DB 사용자테이블명[새올행정]
	private static String	usrdeptDeptTable;	//사용자조직연계 DB 부서테이블명[새올행정]
	private static String	usrdeptUserstat;	//사용자조직연계 사용자상태코드
	private static String	usrdeptRegularity;	//사용자조직연계 정규직구분
	
	private static boolean	wsSaeall;           //사용자조직연계 에이전트 실행여부[웹서비스]   
	private static boolean	wsSaeallEncrypt;    //사용자조직연계 암호화 여부
	private static String	wsSaeallifid;       //사용자조직연계 IFID     			
	private static String	wsSaeallsrcorgcd;   //사용자조직연계 SRCORDCD     		
	private static String	wsSaealltgtorgcd;   //사용자조직연계 TGTORGCD     	
	private static String	wsSaeallusrqueryid; //사용자조직연계 QUERYID_USER    			
	private static String	wsSaealldeptqueryid;//사용자조직연계 QUERYID_DEPT     	
	private static String	wsSaeallcallurl;    //사용자조직연계 URL     	
	private static String	wsSaeallgpkildapserver;//사용자조직연계 LDAPSERVER  	
	private static String	wsSaeallgpkilic;    //사용자조직연계 GPKI_LIC     	
	private static String	wsSaeallgpkipath;    //사용자조직연계 GPKI_PATH
	private static String	wsSaeallgpkildap;   //사용자조직연계 GPKI_LDAP
	private static String	wsSaeallsaeallgpkiid;//사용자조직연계 SAEALL GPKI ID     	
	private static String	wsSaeallgpkiid;     //사용자조직연계 GPKI_ID     	
	private static String	wsSaeallgpkipwd;    //사용자조직연계 GPKIPWD;    
	
	private static boolean	sidoldap;			//사용자조직연계 에이전트 실행여부[시도LDAP]
	private static String	sidoldapDbtype;		//사용자조직연계 DB정보[시도LDAP]
	private static String	sidoldapDbip;		//사용자조직연계 DB IP[시도LDAP] 
	private static String	sidoldapDbuser;		//사용자조직연계 DB 사용자[시도LDAP]
	private static String	sidoldapDbpass;		//사용자조직연계 DB 비밀번호[시도LDAP]
	private static String	sidoldapSysgbn;		//사용자조직연계 DB 시스템구분[시도LDAP]
	
	private static boolean	infoLinkQuery;				//정보연계 쿼리 실행여부
	private static String[]	infoLinkQuery_BeforeQuery;	//정보연계 전 쿼리
	private static String[]	infoLinkQuery_AfterQuery;	//정보연계 후 쿼리

	//한글처리
	private static String getProperty(String key, String defaultValue) throws Exception {
		return new String(pconfig.getProperty(key, defaultValue).getBytes("ISO-8859-1"), "EUC-KR").trim();
	}
	
	protected static boolean ConfigLoad() {
		if (isLoading) return true;
		InputStream is = null;

		try {
			is = appInfo.class.getResourceAsStream(CONFIG_FILE);
			pconfig = new Properties();
			pconfig.load(is);
			
			messanger				= Boolean.valueOf(getProperty("messanger","false")).booleanValue();
			messangerId				= getProperty("messanger.id","");
			messangerCode			= getProperty("messanger.code","");
			messangerUrl			= getProperty("messanger.url","");
			
			appName					= getProperty("appname","");
			web_address				= getProperty("web_address","");
			ap_address				= getProperty("ap_address","");
			rootid					= getProperty("rootid","");
			multipleDelivery		= Boolean.valueOf(getProperty("multipleDelivery","false")).booleanValue();
			gpkilogin				= Boolean.valueOf(getProperty("gpkilogin","false")).booleanValue();
			elecappr				= Boolean.valueOf(getProperty("elecappr","false")).booleanValue();
			
			outside					= Boolean.valueOf(getProperty("outside","false")).booleanValue();
			outsideurl				= getProperty("outside.url","");
			outsideweburl			= getProperty("outside.weburl","");
			serverkey				= getProperty("outside.serverkey","");
			
			scheduleTimer	   		= Boolean.valueOf(getProperty("schedule.timer","false")).booleanValue();
			scheduleInterval		= Integer.parseInt(getProperty("schedule.interval","60"));
			deadlineTimer     		= Boolean.valueOf(getProperty("deadline.timer","false")).booleanValue();
			deadlineInterval		= Integer.parseInt(getProperty("deadline.interval","60"));
			rchresultTimer     		= Boolean.valueOf(getProperty("rchresult.timer","false")).booleanValue() & outside;
			rchresultInterval	    = Integer.parseInt(getProperty("rchresult.interval","60"));
			reqresultTimer        	= Boolean.valueOf(getProperty("reqresult.timer","false")).booleanValue() & outside;
			reqresultInterval	    = Integer.parseInt(getProperty("reqresult.interval","60"));
			
			uploadDir			    = getProperty("upload","/upload/");
			bookFrmCmptDir		    = uploadDir + getProperty("bookFrmCmpt","");
			bookFrmCommSampleDir    = uploadDir + getProperty("bookFrmCommSample","");
			bookFrmDataDir		    = uploadDir + getProperty("bookFrmData","");
			bookFrmSampleDir	    = uploadDir + getProperty("bookFrmSample","");
			colldocSampleDir	    = uploadDir + getProperty("colldocSample","");
			noticeDir			    = uploadDir + getProperty("notice","");
			requestDataDir		    = uploadDir + getProperty("requestData","");
			requestSampleDir	    = uploadDir + getProperty("requestSample","");
			researchSampleDir	    = uploadDir + getProperty("researchSample","");
			workplanDir	    		= uploadDir + getProperty("workplan","");
			tempDir				    = uploadDir + getProperty("temp","");
			
			sidoldap     		    = Boolean.valueOf(getProperty("sidoldap","false")).booleanValue();
			sidoldapDbtype		    = getProperty("sidoldap.dbtype","");
			sidoldapDbip		    = getProperty("sidoldap.dbip","");
			sidoldapDbuser		    = getProperty("sidoldap.dbuser","");
			sidoldapDbpass		    = new String(Base64.decode(getProperty("sidoldap.dbpass","")));
			sidoldapSysgbn		    = getProperty("sidoldap.sysgbn","");
			
			usrdept     		    = Boolean.valueOf(getProperty("usrdept","false")).booleanValue();// ^ sidoldap;
			usrdeptDbtype		    = getProperty("usrdept.dbtype","");
			usrdeptDbip			    = getProperty("usrdept.dbip","");
			usrdeptDbuser		    = getProperty("usrdept.dbuser","");
			usrdeptDbpass		    = new String(Base64.decode(getProperty("usrdept.dbpass","")));
			usrdeptUserTable	    = getProperty("usrdept.usertable","");
			usrdeptDeptTable	    = getProperty("usrdept.depttable","");
			usrdeptUserstat		    = getProperty("usrdept.userstat","");
			usrdeptRegularity	    = getProperty("usrdept.regularity","");		
			
			wsSaeall                = Boolean.valueOf(getProperty("wsSaeall","false")).booleanValue();
			wsSaeallEncrypt         = Boolean.valueOf(getProperty("wsSaeall.encrypt","false")).booleanValue();
			wsSaeallifid            = getProperty("wsSaeall.ifid","");			
			wsSaeallsrcorgcd        = getProperty("wsSaeall.srcorgcd","");		
			wsSaealltgtorgcd        = getProperty("wsSaeall.tgtorgcd","");	
			wsSaeallusrqueryid      = getProperty("wsSaeall.usrqueryid","");			
			wsSaealldeptqueryid     = getProperty("wsSaeall.deptqueryid","");	
			wsSaeallcallurl         = getProperty("wsSaeall.callurl","");	
			wsSaeallgpkildapserver  = getProperty("wsSaeall.gpkildapserver","");	
			wsSaeallgpkilic         = getProperty("wsSaeall.gpkilic","");
			wsSaeallgpkipath		= getProperty("wsSaeall.gpkipath","");
			wsSaeallgpkildap        = getProperty("wsSaeall.gpkildap","");	
			wsSaeallsaeallgpkiid    = getProperty("wsSaeall.saeallgpkiid","");	
			wsSaeallgpkiid          = getProperty("wsSaeall.gpkiid","");	
			wsSaeallgpkipwd         = new String(Base64.decode(getProperty("wsSaeall.gpkipwd","")));
			
			infoLinkQuery		    = Boolean.valueOf(getProperty("infoLinkQuery","false")).booleanValue();
			
			String infoLinkQuerySeparator = getProperty("infoLinkQuery.separator",";");
			String infoLinkQueryBeforeQuery = getProperty("infoLinkQuery.beforeQuery","");
			String infoLinkQueryAfterQuery = getProperty("infoLinkQuery.afterQuery","");
			ArrayList infoLinkQueryBeforeQueryList = new ArrayList();
			ArrayList infoLinkQueryAfterQueryList = new ArrayList();
			
			int idx1 = 0, idx2;
			while ( (idx2 = infoLinkQueryBeforeQuery.indexOf(infoLinkQuerySeparator, idx1)) > -1 ) {
				String query = infoLinkQueryBeforeQuery.substring(idx1, idx2).trim();
				if ( query.length() > 0 ) infoLinkQueryBeforeQueryList.add(query);
				idx1 = idx2 + infoLinkQuerySeparator.length();
			}
			infoLinkQuery_BeforeQuery = new String[infoLinkQueryBeforeQueryList.size()];
			infoLinkQueryBeforeQueryList.toArray(infoLinkQuery_BeforeQuery);
			
			idx1 = 0;
			while ( (idx2 = infoLinkQueryAfterQuery.indexOf(infoLinkQuerySeparator, idx1)) > -1 ) {
				String query = infoLinkQueryAfterQuery.substring(idx1, idx2).trim();
				if ( query.length() > 0 ) infoLinkQueryAfterQueryList.add(query);
				idx1 = idx2 + infoLinkQuerySeparator.length();
			}
			infoLinkQuery_AfterQuery = new String[infoLinkQueryAfterQueryList.size()];
			infoLinkQueryAfterQueryList.toArray(infoLinkQuery_AfterQuery);
			

			isLoading = true;
		} catch (Exception e) {
			e.printStackTrace();
			isLoading = false;
		} finally {
			try { is.close(); } catch ( Exception e ) {}
		}
		
		return isLoading;
	}

	public static String getOutsideweburl() {
		return outsideweburl;
	}
	public static String[] getInfoLinkQuery_AfterQuery() {
		return infoLinkQuery_AfterQuery;
	}
	public static String[] getInfoLinkQuery_BeforeQuery() {
		return infoLinkQuery_BeforeQuery;
	}
	public static boolean isInfoLinkQuery() {
		return infoLinkQuery;
	}
	public static String getCONFIG_FILE() {
		return CONFIG_FILE;
	}
	public static String getSidoldapSysgbn() {
		return sidoldapSysgbn;
	}
	public static boolean isMessanger() {
		return messanger;
	}
	public static String getMessangerCode() {
		return messangerCode;
	}
	public static String getMessangerId() {
		return messangerId;
	}
	public static String getMessangerUrl() {
		return messangerUrl;
	}
	public static boolean isMultipleDelivery() {
		return multipleDelivery;
	}
	public static String getUsrdeptDeptTable() {
		return usrdeptDeptTable;
	}
	public static String getUsrdeptUserTable() {
		return usrdeptUserTable;
	}
	public static String getRequestDataDir() {
		return requestDataDir;
	}
	public static String getRequestSampleDir() {
		return requestSampleDir;
	}
	public static String getWorkplanDir() {
		return workplanDir;
	}
	public static String getResearchSampleDir() {
		return researchSampleDir;
	}
	public static String getBookFrmCmptDir() {
		return bookFrmCmptDir;
	}
	public static String getBookFrmCommSampleDir() {
		return bookFrmCommSampleDir;
	}
	public static String getBookFrmDataDir() {
		return bookFrmDataDir;
	}
	public static String getBookFrmSampleDir() {
		return bookFrmSampleDir;
	}
	public static String getColldocSampleDir() {
		return colldocSampleDir;
	}
	public static String getNoticeDir() {
		return noticeDir;
	}
	public static String getTempDir() {
		return tempDir;
	}
	public static String getUploadDir() {
		return uploadDir;
	}
	public static String getRootid() {
		return rootid;
	}
	public static String getRootRealPath() {
		return rootRealPath;
	}
	public static void setRootRealPath(String rootRealPath) {
		appInfo.rootRealPath = rootRealPath;
	}
	public static boolean isLoading() {
		return isLoading;
	}
	public static boolean isScheduleTimer() {
		return scheduleTimer;
	}
	public static int getScheduleInterval() {
		return scheduleInterval;
	}
	public static boolean isDeadlineTimer() {
		return deadlineTimer;
	}
	public static int getDeadlineInterval() {
		return deadlineInterval;
	}
	public static boolean isUsrdept() {
		return usrdept;
	}
	public static String getUsrdeptDbip() {
		return usrdeptDbip;
	}
	public static String getUsrdeptDbpass() {
		return usrdeptDbpass;
	}
	public static String getUsrdeptDbtype() {
		return usrdeptDbtype;
	}
	public static String getUsrdeptDbuser() {
		return usrdeptDbuser;
	}
	public static String getOutsideurl() {
		return outsideurl;
	}
	public static int getRchresultInterval() {
		return rchresultInterval;
	}
	public static boolean isRchresultTimer() {
		return rchresultTimer;
	}
	public static boolean isOutside() {
		return outside;
	}
	public static String getServerkey() {
		return serverkey;
	}
	public static int getReqresultInterval() {
		return reqresultInterval;
	}
	public static boolean isReqresultTimer() {
		return reqresultTimer;
	}
	public static String getAppName() {
		return appName;
	}
	public static String getWeb_address() {
		return web_address;
	}
	public static String getAp_address() {
		return ap_address;
	}
	public static boolean isGpkilogin() {
		return gpkilogin;
	}
	public static boolean isElecappr() {
		return elecappr;
	}
	public static Properties getPconfig() {
		return pconfig;
	}
	public static boolean isSidoldap() {
		return sidoldap;
	}
	public static String getSidoldapDbip() {
		return sidoldapDbip;
	}
	public static String getSidoldapDbpass() {
		return sidoldapDbpass;
	}
	public static String getSidoldapDbtype() {
		return sidoldapDbtype;
	}
	public static String getSidoldapDbuser() {
		return sidoldapDbuser;
	}
	public static String getUsrdeptRegularity() {
		return usrdeptRegularity;
	}
	public static String getUsrdeptUserstat() {
		return usrdeptUserstat;
	}
	public static boolean isWsSaeall() {
		return wsSaeall;
	}
	public static String getWsSaeallifid() {
		return wsSaeallifid;
	}
	public static boolean isWsSaeallEncrypt() {
		return wsSaeallEncrypt;
	}
	public static String getWsSaeallusrqueryid() {
		return wsSaeallusrqueryid;
	}
	public static String getWsSaealldeptqueryid() {
		return wsSaealldeptqueryid;
	}
	public static String getWsSaeallcallurl() {
		return wsSaeallcallurl;
	}
	public static String getWsSaeallgpkildapserver() {
		return wsSaeallgpkildapserver;
	}
	public static String getWsSaeallgpkilic() {
		return wsSaeallgpkilic;
	}
	public static String getWsSaeallgpkildap() {
		return wsSaeallgpkildap;
	}
	public static String getWsSaeallsaeallgpkiid() {
		return wsSaeallsaeallgpkiid;
	}
	public static String getWsSaeallgpkiid() {
		return wsSaeallgpkiid;
	}
	public static String getWsSaeallgpkipwd() {
		return wsSaeallgpkipwd;
	}
	public static String getWsSaeallgpkipath() {
		return wsSaeallgpkipath;
	}
	public static String getWsSaeallsrcorgcd() {
		return wsSaeallsrcorgcd;
	}
	public static String getWsSaealltgtorgcd() {
		return wsSaealltgtorgcd;
	}
	
}