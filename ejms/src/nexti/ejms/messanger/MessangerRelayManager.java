/**
 * 작성일: 2010.08.26
 * 작성자: 대리 서동찬
 * 모듈명: 메세지 연계 manager(Active Post)
 * 설명: 
 */
package nexti.ejms.messanger;

import java.util.List;

import org.apache.log4j.Logger;

import nexti.ejms.common.appInfo;
import nexti.ejms.util.NewHttpClient;

public class MessangerRelayManager {
	private static Logger logger = Logger.getLogger(MessangerRelayManager.class);
	
	private static MessangerRelayManager instance = null;
	private static MessangerRelayDAO dao = null;
	
	private MessangerRelayManager() {}
	
	public static MessangerRelayManager instance() {
		if ( instance == null ) instance = new MessangerRelayManager(); 
		return instance;
	}
	
	private MessangerRelayDAO getMessangerRelayDAO() {
		if ( dao == null ) dao = new MessangerRelayDAO(); 
		return dao;
	}
	
	private void sendMessage(MessangerRelayBean mrBean) {
		if ( mrBean.getList() == null || mrBean.getList().size() == 0 ) return;
		NewHttpClient nhc = null;

		try {
			String MSG_ID = appInfo.getMessangerId();
			String SYS_NAME = appInfo.getAppName();
			String MSG_CODE = appInfo.getMessangerCode();
			String DOC_NAME = mrBean.getDoc_name_title();
			String DOC_DESC = mrBean.getDoc_desc_content();
			String DOC_URL = appInfo.getWeb_address() + "ssocount.do?usersn=<%userid%>&act=";
			String SND_USER = mrBean.getSnd_user_usersn();
			String RCV_USER = null;
			String ALARM_TYPE = "";
			
			switch ( mrBean.getRelayMode() ) {
			case MessangerRelay.SEND_MESSAGE:
				DOC_URL += "";	//메인화면
				break;
			case MessangerRelay.COLLECT_START:
				DOC_URL += "deliv";	//배부하기목록
				break;
			case MessangerRelay.DELIVERY:
				DOC_URL += "input";	//입력하기목록
				break;
			case MessangerRelay.COLLECT_COMP:
				DOC_URL += "";	//메인화면
				break;
			}
			
			StringBuffer rcv_user_list = new StringBuffer();
			for ( int i = 0; i < mrBean.getList().size(); i++ ) {
				rcv_user_list.append((String)mrBean.getList().get(i) + ",");
			}
			RCV_USER = rcv_user_list.toString();
			
			//파라미터 순서 : MSG_ID, SYS_NAME, MSG_CODE, SND_USER, RCV_USER, DOC_NAME, DOC_URL, DOC_DESC, ALARM_TYPE
			nhc = new NewHttpClient(appInfo.getMessangerUrl());
			nhc.setMethodType(NewHttpClient.POSTTYPE);
	        nhc.setParam("MSG_ID", MSG_ID); 
	        nhc.setParam("SYS_NAME", SYS_NAME);
	        nhc.setParam("MSG_CODE", MSG_CODE);
	        nhc.setParam("SND_USER", SND_USER);
	        nhc.setParam("RCV_USER", RCV_USER);
	        nhc.setParam("DOC_NAME", DOC_NAME);
	        nhc.setParam("DOC_URL", DOC_URL);
	        nhc.setParam("DOC_DESC", "※ 제목을 클릭하면 해당화면으로 이동합니다\n\n" + DOC_DESC);
	        nhc.setParam("ALARM_TYPE", ALARM_TYPE);
	
	        int result = nhc.execute();
	        String resultMessage = "\n" + MSG_ID + " | " + SYS_NAME + " | " + MSG_CODE + " | " +
	        					   SND_USER + " | " + RCV_USER + " | " + DOC_URL + " | " +
	        					   DOC_NAME + " | " + DOC_DESC.replaceAll("\\n", " ");
	        if ( result == 200 ) {
	        	logger.info("MessangerRelay Succeed : " + resultMessage);
	        } else {
	        	logger.info("MessangerRelay Fail : " + resultMessage);
	        }
		} catch (Exception e) {
			logger.info("ERROR", e);
		}
		
		return;
	}
	
	public void sendMessage_SEND_MESSAGE(MessangerRelayBean mrBean) {		
		sendMessage(mrBean);
		return;
	}
	
	public void sendMessage_COLLECT_START(MessangerRelayBean mrBean) {
		String[] doc_name_title = mrBean.getDoc_name_title().split(",");
		
		MessangerRelayBean bean = getMessangerRelayDAO().get_COLLECT_START(mrBean);
		if ( bean != null ) {
			mrBean.setDoc_name_title(doc_name_title[0]);
			mrBean.setDoc_desc_content(bean.getDoc_name_title() + "\n" + bean.getDoc_desc_content());
			mrBean.setSnd_user_usersn(bean.getSnd_user_usersn());
			mrBean.setList(bean.getList());
			sendMessage(mrBean);
		}
		
		bean = getMessangerRelayDAO().get_COLLECT_START_DELIVERY(mrBean);
		if ( bean != null ) {
			mrBean.setRelayMode(MessangerRelay.DELIVERY);
			mrBean.setDoc_name_title(doc_name_title[1]);
			mrBean.setDoc_desc_content(bean.getDoc_name_title() + "\n" + bean.getDoc_desc_content());
			mrBean.setSnd_user_usersn(bean.getSnd_user_usersn());
			mrBean.setList(bean.getList());
			sendMessage(mrBean);
		}
		return;
	}
	
	public void sendMessage_DELIVERY(MessangerRelayBean mrBean) {
		MessangerRelayBean bean = getMessangerRelayDAO().get_DELIVERY(mrBean);
		if ( bean != null ) {
			mrBean.setDoc_desc_content(bean.getDoc_name_title() + "\n" + bean.getDoc_desc_content());
			mrBean.setSnd_user_usersn(bean.getSnd_user_usersn());
			mrBean.setList(bean.getList());
			sendMessage(mrBean);
		}
		return;
	}
	
	public void sendMessage_COLLECT_COMP(MessangerRelayBean mrBean) {
		MessangerRelayBean bean = getMessangerRelayDAO().get_COLLECT_COMP(mrBean);
		if ( bean != null ) {
			mrBean.setDoc_desc_content(bean.getDoc_name_title() + "\n" + bean.getDoc_desc_content());
			mrBean.setSnd_user_usersn(bean.getSnd_user_usersn());
			mrBean.setList(bean.getList());
			sendMessage(mrBean);
		}
		return;
	}
	
	public List getDeliveryWaitUserList(int sysdocno, String deptcode) {
		return getMessangerRelayDAO().getDeliveryWaitUserList(sysdocno, deptcode);
	}
}