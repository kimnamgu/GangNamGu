/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���α׷� ����
 * ����:
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
	
	private static boolean	messanger;			//�޽�������
	private static String	messangerId;		//�޽�������ý���ID
	private static String	messangerCode;		//�޽�������ý����ڵ�
	private static String	messangerUrl;		//�޽��������ּ�
	
	private static String	rootRealPath;		//�����ø����̼� ������	
	private static String	appName;			//������ Ÿ��Ʋ
	private static String	web_address;		//WEB�����ּ�
	private static String	ap_address;			//AP�����ּ�
	private static String	rootid;				//����ڵ�
	private static boolean	multipleDelivery;	//�ٴ����� ��뿩��
	private static boolean	gpkilogin;			//gpki�α��� ��뿩��
	private static boolean	elecappr;			//���ڰ��翬�� ��뿩��
	private static boolean	scheduleTimer;		//�������� ������Ʈ ���࿩��
	private static int 		scheduleInterval;	//�������� üũ�ð�(��)
	private static boolean	deadlineTimer;		//�������� ������Ʈ ���࿩��
	private static int 		deadlineInterval;	//�������� üũ�ð�(��)
	private static boolean  rchresultTimer;     //��������������� �������� ������Ʈ ���࿩��
	private static int		rchresultInterval;  //��������������� �������� üũ�ð�(��)
	private static boolean  reqresultTimer;     //��û������������� �������� ������Ʈ ���࿩��
	private static int		reqresultInterval;  //��û������������� �������� üũ�ð�(��)
	private static boolean  outside;			//�ܺθ����� ���࿩��
	private static String   outsideurl;			//�ܺθ����� �ּ�
	private static String   outsideweburl;		//�ܺθ����� �ּ�(���������� �ο����񽺽�)
	private static String 	serverkey;			//�ܺθ����� ����Ű(�⺻��:������123)
	private static String	uploadDir;			//���ε� ���丮
	private static String	bookFrmCmptDir;		//�����ڷ��� �����ڷ�
	private static String	bookFrmCommSampleDir; //�����ڷ��� �����Ļ���
	private static String	bookFrmDataDir;		//�����ڷ��� �Է��ڷ�
	private static String	bookFrmSampleDir;	//�����ڷ��� ����
	private static String	colldocSampleDir;	//���չ��� ÷������
	private static String	noticeDir;			//�������� ÷������
	private static String	requestDataDir;		//��û�� ��������
	private static String	requestSampleDir;	//��û�� ����,���� ����
	private static String	researchSampleDir;	//�������� ����,���� ����
	private static String	workplanDir;			//�������� ÷������
	private static String	tempDir;			//�ӽ�����
	private static boolean	usrdept;			//������������� ������Ʈ ���࿩��[��������]
	private static String	usrdeptDbtype;		//������������� DB����[��������]
	private static String	usrdeptDbip;		//������������� DB IP [��������]
	private static String	usrdeptDbuser;		//������������� DB �����[��������]
	private static String	usrdeptDbpass;		//������������� DB ��й�ȣ[��������]
	private static String	usrdeptUserTable;	//������������� DB ��������̺��[��������]
	private static String	usrdeptDeptTable;	//������������� DB �μ����̺��[��������]
	private static String	usrdeptUserstat;	//������������� ����ڻ����ڵ�
	private static String	usrdeptRegularity;	//������������� ����������
	
	private static boolean	wsSaeall;           //������������� ������Ʈ ���࿩��[������]   
	private static boolean	wsSaeallEncrypt;    //������������� ��ȣȭ ����
	private static String	wsSaeallifid;       //������������� IFID     			
	private static String	wsSaeallsrcorgcd;   //������������� SRCORDCD     		
	private static String	wsSaealltgtorgcd;   //������������� TGTORGCD     	
	private static String	wsSaeallusrqueryid; //������������� QUERYID_USER    			
	private static String	wsSaealldeptqueryid;//������������� QUERYID_DEPT     	
	private static String	wsSaeallcallurl;    //������������� URL     	
	private static String	wsSaeallgpkildapserver;//������������� LDAPSERVER  	
	private static String	wsSaeallgpkilic;    //������������� GPKI_LIC     	
	private static String	wsSaeallgpkipath;    //������������� GPKI_PATH
	private static String	wsSaeallgpkildap;   //������������� GPKI_LDAP
	private static String	wsSaeallsaeallgpkiid;//������������� SAEALL GPKI ID     	
	private static String	wsSaeallgpkiid;     //������������� GPKI_ID     	
	private static String	wsSaeallgpkipwd;    //������������� GPKIPWD;    
	
	private static boolean	sidoldap;			//������������� ������Ʈ ���࿩��[�õ�LDAP]
	private static String	sidoldapDbtype;		//������������� DB����[�õ�LDAP]
	private static String	sidoldapDbip;		//������������� DB IP[�õ�LDAP] 
	private static String	sidoldapDbuser;		//������������� DB �����[�õ�LDAP]
	private static String	sidoldapDbpass;		//������������� DB ��й�ȣ[�õ�LDAP]
	private static String	sidoldapSysgbn;		//������������� DB �ý��۱���[�õ�LDAP]
	
	private static boolean	infoLinkQuery;				//�������� ���� ���࿩��
	private static String[]	infoLinkQuery_BeforeQuery;	//�������� �� ����
	private static String[]	infoLinkQuery_AfterQuery;	//�������� �� ����

	//�ѱ�ó��
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