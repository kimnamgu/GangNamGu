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
	 * XML ������ �������� ���丮��
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
			if (fl != null && fl.length > 0) {// ���丮 ���� ������ �����ϰ�
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
						//�ݼ��϶�
						int adminNum = Integer.parseInt("0" + Utils.nullToEmptyString(readInfFIle(headerFileName, "administrative_num")));
						String date = readInfFIle(headerFileName, "date");
						if ( applyReturned(adminNum, date) > 0 ) {// �Ľ� �� �����ͺ��̽� �Է��� ������
							clearDirectory();
							logger.info("�ݼ�ó���Ļ��� : " + rootDir + directory);
						}
					} else {
						if (notiFile.exists()) {
							boolean isCopied = copyFile(dtdDir, dtdFileName);						
							int u_cnt = ( (isCopied) ? parseXmlFile2DB(notiFile) : -1 );
							if ( u_cnt > 0 ) {// �Ľ� �� �����ͺ��̽� �Է��� ������
								clearDirectory();
								logger.info("����ó���Ļ��� : " + rootDir + directory);
							}
						} else { // �Ľ��� �ǹ̰� ���� ���ϵ�
							clearDirectory();
							logger.info("�߸������ϻ��� : " + rootDir + directory);
						}
					}
				}
				
				int adminNum = Integer.parseInt("0" + Utils.nullToEmptyString(readInfFIle(headerFileName, "administrative_num")));
				delNeedlessFile(adminNum);	//�ʿ���� ���� ����
			} else {
				clearDirectory();
				logger.info("����ִµ��丮���� : " + rootDir + directory);
			}
		} catch (Exception e) {
			logger.error("���ڰ�����Ź���_run() : " + rootDir + directory, e);
		} 
	}
	
	public void delNeedlessFile(int adminNum) {
		try {
			ElecApprManager eamgr = ElecApprManager.instance();
			if ( eamgr.isColldocSancneed(adminNum) == false
					&& eamgr.isRequestSancneed(adminNum) == false) {
				clearDirectory();
				logger.info("�ʿ�������ϻ��� : " + rootDir + directory);
			}
		} catch (Exception e) {
			logger.error("���ڰ�����Ź���_delNeedlessFile() : " + rootDir + directory, e);
		}
	}
	
	/**
	 * ���� ����(exchange.dtd)
	 */
	public boolean copyFile(String pm_sExtraDir, String pm_sDes) {

		InputStream inFile = null;
		OutputStream outFile = null;
		boolean result = false;
		int nInRead = 0;

		try {
			// �̹� ��� ������ ������ ��� ����
			if ((new File(pm_sDes)).exists())
				(new File(pm_sDes)).delete();
			inFile = new FileInputStream(new File(pm_sExtraDir));
			outFile = new FileOutputStream(pm_sDes, true);

			while ((nInRead = inFile.read()) != -1) {
				outFile.write(nInRead);
			}
			result = true;
		} catch ( FileNotFoundException fnfe ) {
			logger.error("���ڰ�����Ź���_copyFile() : ������ �����ϴ�" + rootDir + directory);
		} catch (Exception e) {
			logger.error("���ڰ�����Ź���_copyFile() : " + rootDir + directory, e);
		} finally {
			try {
			inFile.close();
			outFile.close();
			} catch (Exception e) {}
		}
		return result;
	}

	/**
	 * XML ���� �� ���丮�� �����Ѵ�.
	 */
	public void clearDirectory() {
		try {
			String xmlFileName = rootDir + directory + ".xml";
			String notiDirName = rootDir + directory;
			File notiDir = new File(notiDirName);
			String[] notiDirfl = notiDir.list();
	
			(new File(xmlFileName)).delete();// XML ���� ����
	
			for (int i = 0; notiDirfl != null && i < notiDirfl.length; i++) {
				(new File(notiDirName + "/" + notiDirfl[i])).delete();// ���丮 �� ���� ��� ����
			}
			notiDir.delete();// Unpack ���丮 ����
		} catch (Exception e) {
			logger.error("���ڰ�����Ź���_clearDirectory() : " + rootDir + directory, e);
		}
	}
	
	/**
	 * INF���� Key�� Value��������
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
			logger.error("���ڰ�����Ź���_copyFile() : ������ �����ϴ�" + rootDir + directory);
		} catch ( Exception e ) {
			logger.error("���ڰ�����Ź���_readInfFIle() : " + rootDir + directory, e);
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
			eaBean.setSancresult("��ȴ��(����) �ݼ�");
			eaBean.setSancyn("1");
			eaBean.setUptusrid("MONITORING");
			eaBean.setSeq(adminNum);
			eaBean.setSancdt(date);
			
			if ( eamgr.isColldocSancneed(adminNum) == true ) {	//���չ����ݼ�ó��
				result = eamgr.updateColldocSancInfo(eaBean);
			} else if ( eamgr.isRequestSancneed(adminNum) == true ) {	//��û���ݼ�ó��
				result = eamgr.updateRequestSancInfo(eaBean);
			}			
		} catch ( Exception e ) {
			logger.error("���ڰ�����Ź���_applyReturned() : " + rootDir + directory, e);
		}
		
		return result;
	}
	
	/**
	 * ��� XML���Ϻм�
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
			 * 1. �ʿ� ���� ����ȭ
			 * - SANC_STATUS : ������� - 1.��ȴ�� -> 2. ���� -> 3. ���� (�ݷ�/������)
			 ********************/
			_SANCTION_STATUS = xmlUtil.getSubTagAttribute(root, "SANCTION_INFO","status"); //5. ��� ���� ���� (����,�Ϸ�)
			
			if(!"�Ϸ�".equals(_SANCTION_STATUS)){
				return 1;
			}
			
			_ADMIN_NUM = Integer.parseInt(xmlUtil.getTagValue(root, "ADMINISTRATIVE_NUM"));//1.������ȣ
			//_TITLE = xmlUtil.getTagValue(root, "TITLE"); //4.����
			//_USECASE_GBN = xmlUtil.getTagValueByName(root, "ADDENDUM","NAME","USECASE_GBN"); //6.�������̽� ���̵�
			//_RESULT_URL = xmlUtil.getTagValueByName(root, "ADDENDUM","NAME","RESULT_URL"); //7. ����� ó���� �� ����ں� ���α׷� 
			//_BODY= xmlUtil.getTagValue(root, "BODY"); //7. ��������
			//8. ������� 
			NodeList sancUserList=xmlUtil.getTagList(root,"LINE");
			for(int i=0; i<sancUserList.getLength(); i++){
				Element line=(Element)sancUserList.item(i);
				ExchangeVO user=new ExchangeVO();
				user.setSancLevel(i+1);
				user.setSancDt(line.getElementsByTagName("DATE").item(0).getFirstChild()==null?"":line.getElementsByTagName("DATE").item(0).getFirstChild().getNodeValue());
				
				Element sanction=xmlUtil.getTag(line, "SANCTION");
				user.setSancType(sanction.getAttribute("type"));		//���,����,����,����,���,����
				user.setSancResult(sanction.getAttribute("result"));	//����(�³���:����), ������,�ݷ�,�̰�(�³���:��ó��)
				
				Element person=xmlUtil.getTag(sanction,"PERSON");
				user.setUserId(person.getElementsByTagName("USERID").item(0).getFirstChild().getNodeValue());
				user.setName(person.getElementsByTagName("NAME").item(0).getFirstChild().getNodeValue());
				user.setPosition(person.getElementsByTagName("POSITION").item(0).getFirstChild().getNodeValue());
				user.setDept(person.getElementsByTagName("DEPT").item(0).getFirstChild().getNodeValue());
				user.setOrg(person.getElementsByTagName("ORG").item(0).getFirstChild().getNodeValue());
				
				_SANC_USER_LIST.add(user); 
			}//for
			
			/*********************
			 * 2. ������ �ݿ� 
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
			
			if ( eamgr.isColldocSancneed(_ADMIN_NUM) == true ) {	//���չ�������ó��
				
				cnt = eamgr.updateColldocSancInfo(eaBean);
				
				if ( cnt > 0 ) {
					
					int result = 0;
					
					if ( "����".equals(evo.getSancResult()) ) {
						//�ڵ�
						eaBean = eamgr.selectColldocSancInfo(_ADMIN_NUM);
						InputingManager inputmgr = InputingManager.instance();
						result = inputmgr.inputingComplete(eaBean.getSysdocno(), eaBean.getInputusrid(), eaBean.getTgtdeptcd());
					} else if ( "����".equals(evo.getSancResult()) || "1�ΰ���".equals(evo.getSancResult()) ) {
						//�³���
						eaBean = eamgr.selectColldocSancInfo(_ADMIN_NUM);
						InputingManager inputmgr = InputingManager.instance();
						result = inputmgr.inputingComplete(eaBean.getSysdocno(), eaBean.getInputusrid(), eaBean.getTgtdeptcd());
					} else if ( "������ ".equals(evo.getSancResult()) || "�ݷ� ".equals(evo.getSancResult()) || "�̰� ".equals(evo.getSancResult()) ) {
						//�ڵ�
						result = 1;
					} else if ( "�ߴ� ".equals(evo.getSancResult()) || "�����".equals(evo.getSancResult()) || "��ó�� ".equals(evo.getSancResult()) ) {
						//�³���
						result = 1;
					}
					
					if ( result > 0 ) {
						System.out.println("�������ڰ���ó�� : ������ȣ(" + _ADMIN_NUM + ", " + evo.getSancResult() + ")");
					} else {
						cnt = 0;
						eaBean.setSancresult(evo.getSancResult());
						eaBean.setSancyn("0");
						eamgr.updateColldocSancInfo(eaBean);
					}
				}
			}
			
			if ( eamgr.isRequestSancneed(_ADMIN_NUM) == true ) {	//��û������ó��
				
				cnt = eamgr.updateRequestSancInfo(eaBean);
				
				if ( cnt > 0 ) {
					
					int result = 0;
					
					if ( "����".equals(evo.getSancResult()) ) {
						//�ڵ�
						eaBean = eamgr.selectRequestSancInfo(_ADMIN_NUM);
						SinchungManager smgr = SinchungManager.instance();
						result = smgr.procJupsu("4", eaBean.getReqformno(), eaBean.getReqseq());
					} else if ( "����".equals(evo.getSancResult()) || "1�ΰ���".equals(evo.getSancResult()) ) {
						//�³���
						eaBean = eamgr.selectRequestSancInfo(_ADMIN_NUM);
						SinchungManager smgr = SinchungManager.instance();
						result = smgr.procJupsu("4", eaBean.getReqformno(), eaBean.getReqseq());
					} else if ( "������ ".equals(evo.getSancResult()) || "�ݷ� ".equals(evo.getSancResult()) || "�̰� ".equals(evo.getSancResult()) ) {
						//�ڵ�
						result = 1;
					} else if ( "�ߴ� ".equals(evo.getSancResult()) || "�����".equals(evo.getSancResult()) || "��ó�� ".equals(evo.getSancResult()) ) {
						//�³���
						result = 1;
					}
					
					if ( result > 0 ) {
						System.out.println("��û�����ڰ���ó�� : ������ȣ(" + _ADMIN_NUM + ", " + evo.getSancResult() + ")");
					} else {
						cnt = 0;
						eaBean.setSancresult(evo.getSancResult());
						eaBean.setSancyn("0");
						eamgr.updateRequestSancInfo(eaBean);
					}
				}
			}
		} catch (Exception e) {
			logger.error("���ڰ�����Ź���_parseXmlFile2DB() : " + rootDir + directory, e);
		} 
		
		return cnt;
	}	
}
