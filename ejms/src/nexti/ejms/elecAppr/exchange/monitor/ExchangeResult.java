package nexti.ejms.elecAppr.exchange.monitor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import nexti.ejms.elecAppr.exchange.ExchangeVO;
import nexti.ejms.elecAppr.model.ElecApprBean;
import nexti.ejms.elecAppr.model.ElecApprManager;
import nexti.ejms.inputing.model.InputingManager;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.util.Utils;
import nexti.ejms.util.XMLUtil;

public class ExchangeResult implements Runnable {
	private static Logger logger = Logger.getLogger(ExchangeResult.class);	
	private String directory = "";
	private String rootDir=""; 
	private String dtdDir=""; 
	
	/**
	 * XML 서버로 보내야할 디렉토리명
	 */
	public String getDirectory() {
		return directory;
	}	

	public String getRootDir() {
		return rootDir;
	}

	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}
	
	public String getDtdDir() {
		return dtdDir;
	}

	public void setDtdDir(String dtdDir) {
		this.dtdDir = dtdDir;
	}
	public ExchangeResult(String directory) {
		this.directory = directory;
	}

	public void run() {
		try {
			String[] fl = new File(rootDir + directory).list();
			if (fl != null && fl.length > 0) {// 디렉토리 내에 파일이 존재하고
				String headerFileName = rootDir + directory
						+ "/header.inf";
				String notiFileName = rootDir + directory
						+ "/notification.xml";
				String eofFileName = rootDir + directory
						+ "/eof.inf";
				String dtdFileName = rootDir + directory
						+ "/exchange.dtd"; 
				File notiFile = new File(notiFileName);
				File eofFile = new File(eofFileName);
				
				if (eofFile.exists()) {
					if ( "return".equals(readInfFIle(headerFileName, "type")) ) {
						//반송일때
						int adminNum = Integer.parseInt("0" + Utils.nullToEmptyString(readInfFIle(headerFileName, "administrative_num")));
						String date = readInfFIle(headerFileName, "date");
						if ( applyReturned(adminNum, date) > 0 ) {// 파싱 후 데이터베이스 입력이 성공시
							clearDirectory();
							logger.info("반송처리후삭제 : " + rootDir + directory);
						}
					} else {
						if (notiFile.exists()) {
							boolean isCopied = copyFile(dtdDir, dtdFileName);						
							int u_cnt = ( (isCopied) ? parseXmlFile2DB(notiFile) : -1 );
							if ( u_cnt > 0 ) {// 파싱 후 데이터베이스 입력이 성공시
								clearDirectory();
								logger.info("정상처리후삭제 : " + rootDir + directory);
							}
						} else { // 파싱의 의미가 없는 파일들
							clearDirectory();
							logger.info("잘못된파일삭제 : " + rootDir + directory);
						}
					}
				}
				
				int adminNum = Integer.parseInt("0" + Utils.nullToEmptyString(readInfFIle(headerFileName, "administrative_num")));
				delNeedlessFile(adminNum);	//필요없는 파일 삭제
			} else {
				clearDirectory();
				logger.info("비어있는디렉토리삭제 : " + rootDir + directory);
			}
		} catch (Exception e) {
			logger.error("전자결재수신문서_run() : " + rootDir + directory, e);
		} 
	}
	
	public void delNeedlessFile(int adminNum) {
		try {
			ElecApprManager eamgr = ElecApprManager.instance();
			if ( eamgr.isColldocSancneed(adminNum) == false
					&& eamgr.isRequestSancneed(adminNum) == false) {
				clearDirectory();
				logger.info("필요없는파일삭제 : " + rootDir + directory);
			}
		} catch (Exception e) {
			logger.error("전자결재수신문서_delNeedlessFile() : " + rootDir + directory, e);
		}
	}
	
	/**
	 * 파일 복사(exchange.dtd)
	 */
	public boolean copyFile(String pm_sExtraDir, String pm_sDes) {

		InputStream inFile = null;
		OutputStream outFile = null;
		boolean result = false;
		int nInRead = 0;

		try {
			// 이미 대상 파일이 존재할 경우 삭제
			if ((new File(pm_sDes)).exists())
				(new File(pm_sDes)).delete();
			inFile = new FileInputStream(new File(pm_sExtraDir));
			outFile = new FileOutputStream(pm_sDes, true);

			while ((nInRead = inFile.read()) != -1) {
				outFile.write(nInRead);
			}
			result = true;
		} catch ( FileNotFoundException fnfe ) {
			logger.error("전자결재수신문서_copyFile() : 파일이 없습니다" + rootDir + directory);
		} catch (Exception e) {
			logger.error("전자결재수신문서_copyFile() : " + rootDir + directory, e);
		} finally {
			try {
			inFile.close();
			outFile.close();
			} catch (Exception e) {}
		}
		return result;
	}

	/**
	 * XML 문서 및 디렉토리를 삭제한다.
	 */
	public void clearDirectory() {
		try {
			String xmlFileName = rootDir + directory + ".xml";
			String notiDirName = rootDir + directory;
			File notiDir = new File(notiDirName);
			String[] notiDirfl = notiDir.list();
	
			(new File(xmlFileName)).delete();// XML 파일 삭제
	
			for (int i = 0; notiDirfl != null && i < notiDirfl.length; i++) {
				(new File(notiDirName + "/" + notiDirfl[i])).delete();// 디렉토리 안 파일 모두 삭제
			}
			notiDir.delete();// Unpack 디렉토리 삭제
		} catch (Exception e) {
			logger.error("전자결재수신문서_clearDirectory() : " + rootDir + directory, e);
		}
	}
	
	/**
	 * INF파일 Key로 Value가져오기
	 */
	public String readInfFIle(String filenm, String key) {
		String result = null;
		BufferedReader br = null;
		
		try {
			String readData = null;
			br = new BufferedReader(new FileReader(filenm));
			
			while ( (readData = br.readLine()) != null ) {
				String[] data = readData.split("=");
				if ( data !=null && data.length > 0 ) {
					if ( data[0].equals(key) ) {
						result = data[1];
						break;
					}
				}
			}
		} catch ( FileNotFoundException fnfe ) {
			logger.error("전자결재수신문서_copyFile() : 파일이 없습니다" + rootDir + directory);
		} catch ( Exception e ) {
			logger.error("전자결재수신문서_readInfFIle() : " + rootDir + directory, e);
		} finally {
			try { br.close(); } catch (Exception e) {}
		}
		
		return result;
	}
	
	public int applyReturned(int adminNum, String date) {
		int result = 0;
		
		try {
			ElecApprManager eamgr = ElecApprManager.instance();
			
			ElecApprBean eaBean = new ElecApprBean();
			eaBean.setGubun("");
			eaBean.setSancresult("기안대기(연계) 반송");
			eaBean.setSancyn("1");
			eaBean.setUptusrid("MONITORING");
			eaBean.setSeq(adminNum);
			eaBean.setSancdt(date);
			
			if ( eamgr.isColldocSancneed(adminNum) == true ) {	//취합문서반송처리
				result = eamgr.updateColldocSancInfo(eaBean);
			} else if ( eamgr.isRequestSancneed(adminNum) == true ) {	//신청서반송처리
				result = eamgr.updateRequestSancInfo(eaBean);
			}			
		} catch ( Exception e ) {
			logger.error("전자결재수신문서_applyReturned() : " + rootDir + directory, e);
		}
		
		return result;
	}
	
	/**
	 * 결과 XML파일분석
	 */
	public int parseXmlFile2DB(File xmlFile) {
		
		int cnt = 0;
		
		try {
			int _ADMIN_NUM = 0;
			String _SANCTION_STATUS = "";
			//String _TITLE = "";
			//String _USECASE_GBN="";
			//String _RESULT_URL="";
			//String _BODY = "";
			ArrayList _SANC_USER_LIST=new ArrayList(); 
			
			XMLUtil xmlUtil = new XMLUtil();
			Element root = xmlUtil.loadXmlDocument(xmlFile);
			
			/*********************
			 * 1. 필요 정보 세분화
			 * - SANC_STATUS : 진행상태 - 1.기안대기 -> 2. 진행 -> 3. 승인 (반려/기안취소)
			 ********************/
			_SANCTION_STATUS = xmlUtil.getSubTagAttribute(root, "SANCTION_INFO","status"); //5. 기안 진행 상태 (진행,완료)
			
			if(!"완료".equals(_SANCTION_STATUS)){
				return 1;
			}
			
			_ADMIN_NUM = Integer.parseInt(xmlUtil.getTagValue(root, "ADMINISTRATIVE_NUM"));//1.고유번호
			//_TITLE = xmlUtil.getTagValue(root, "TITLE"); //4.제목
			//_USECASE_GBN = xmlUtil.getTagValueByName(root, "ADDENDUM","NAME","USECASE_GBN"); //6.유스케이스 아이디
			//_RESULT_URL = xmlUtil.getTagValueByName(root, "ADDENDUM","NAME","RESULT_URL"); //7. 결과를 처리할 각 사용자별 프로그램 
			//_BODY= xmlUtil.getTagValue(root, "BODY"); //7. 본문내용
			//8. 결재라인 
			NodeList sancUserList=xmlUtil.getTagList(root,"LINE");
			for(int i=0; i<sancUserList.getLength(); i++){
				Element line=(Element)sancUserList.item(i);
				ExchangeVO user=new ExchangeVO();
				user.setSancLevel(i+1);
				user.setSancDt(line.getElementsByTagName("DATE").item(0).getFirstChild()==null?"":line.getElementsByTagName("DATE").item(0).getFirstChild().getNodeValue());
				
				Element sanction=xmlUtil.getTag(line, "SANCTION");
				user.setSancType(sanction.getAttribute("type"));		//기안,검토,협조,전결,대결,결재
				user.setSancResult(sanction.getAttribute("result"));	//승인(온나라:결재), 기안취소,반려,미결(온나라:미처리)
				
				Element person=xmlUtil.getTag(sanction,"PERSON");
				user.setUserId(person.getElementsByTagName("USERID").item(0).getFirstChild().getNodeValue());
				user.setName(person.getElementsByTagName("NAME").item(0).getFirstChild().getNodeValue());
				user.setPosition(person.getElementsByTagName("POSITION").item(0).getFirstChild().getNodeValue());
				user.setDept(person.getElementsByTagName("DEPT").item(0).getFirstChild().getNodeValue());
				user.setOrg(person.getElementsByTagName("ORG").item(0).getFirstChild().getNodeValue());
				
				_SANC_USER_LIST.add(user); 
			}//for
			
			/*********************
			 * 2. 결재결과 반영 
			 ********************/
			ElecApprManager eamgr = ElecApprManager.instance();
			ExchangeVO evo = (ExchangeVO)_SANC_USER_LIST.get(_SANC_USER_LIST.size() - 1);
			
			ElecApprBean eaBean = new ElecApprBean();
			eaBean.setGubun("");
			eaBean.setSancresult(evo.getSancResult());
			eaBean.setSancyn("1");
			eaBean.setUptusrid("MONITORING");
			eaBean.setSeq(_ADMIN_NUM);
			
			eaBean.setSancusrid(evo.getUserId());
			eaBean.setSancusrnm(evo.getDept() + " " + evo.getPosition() + " " + evo.getName());
			eaBean.setSancdt(evo.getSancDt());
			
			if ( eamgr.isColldocSancneed(_ADMIN_NUM) == true ) {	//취합문서결재처리
				
				cnt = eamgr.updateColldocSancInfo(eaBean);
				
				if ( cnt > 0 ) {
					
					int result = 0;
					
					if ( "승인".equals(evo.getSancResult()) ) {
						//핸디
						eaBean = eamgr.selectColldocSancInfo(_ADMIN_NUM);
						InputingManager inputmgr = InputingManager.instance();
						result = inputmgr.inputingComplete(eaBean.getSysdocno(), eaBean.getInputusrid(), eaBean.getTgtdeptcd());
					} else if ( "결재".equals(evo.getSancResult()) || "1인결재".equals(evo.getSancResult()) ) {
						//온나라
						eaBean = eamgr.selectColldocSancInfo(_ADMIN_NUM);
						InputingManager inputmgr = InputingManager.instance();
						result = inputmgr.inputingComplete(eaBean.getSysdocno(), eaBean.getInputusrid(), eaBean.getTgtdeptcd());
					} else if ( "기안취소 ".equals(evo.getSancResult()) || "반려 ".equals(evo.getSancResult()) || "미결 ".equals(evo.getSancResult()) ) {
						//핸디
						result = 1;
					} else if ( "중단 ".equals(evo.getSancResult()) || "재검토".equals(evo.getSancResult()) || "미처리 ".equals(evo.getSancResult()) ) {
						//온나라
						result = 1;
					}
					
					if ( result > 0 ) {
						System.out.println("취합전자결재처리 : 관리번호(" + _ADMIN_NUM + ", " + evo.getSancResult() + ")");
					} else {
						cnt = 0;
						eaBean.setSancresult(evo.getSancResult());
						eaBean.setSancyn("0");
						eamgr.updateColldocSancInfo(eaBean);
					}
				}
			}
			
			if ( eamgr.isRequestSancneed(_ADMIN_NUM) == true ) {	//신청서결재처리
				
				cnt = eamgr.updateRequestSancInfo(eaBean);
				
				if ( cnt > 0 ) {
					
					int result = 0;
					
					if ( "승인".equals(evo.getSancResult()) ) {
						//핸디
						eaBean = eamgr.selectRequestSancInfo(_ADMIN_NUM);
						SinchungManager smgr = SinchungManager.instance();
						result = smgr.procJupsu("4", eaBean.getReqformno(), eaBean.getReqseq());
					} else if ( "결재".equals(evo.getSancResult()) || "1인결재".equals(evo.getSancResult()) ) {
						//온나라
						eaBean = eamgr.selectRequestSancInfo(_ADMIN_NUM);
						SinchungManager smgr = SinchungManager.instance();
						result = smgr.procJupsu("4", eaBean.getReqformno(), eaBean.getReqseq());
					} else if ( "기안취소 ".equals(evo.getSancResult()) || "반려 ".equals(evo.getSancResult()) || "미결 ".equals(evo.getSancResult()) ) {
						//핸디
						result = 1;
					} else if ( "중단 ".equals(evo.getSancResult()) || "재검토".equals(evo.getSancResult()) || "미처리 ".equals(evo.getSancResult()) ) {
						//온나라
						result = 1;
					}
					
					if ( result > 0 ) {
						System.out.println("신청서전자결재처리 : 관리번호(" + _ADMIN_NUM + ", " + evo.getSancResult() + ")");
					} else {
						cnt = 0;
						eaBean.setSancresult(evo.getSancResult());
						eaBean.setSancyn("0");
						eamgr.updateRequestSancInfo(eaBean);
					}
				}
			}
		} catch (Exception e) {
			logger.error("전자결재수신문서_parseXmlFile2DB() : " + rootDir + directory, e);
		} 
		
		return cnt;
	}	
}
