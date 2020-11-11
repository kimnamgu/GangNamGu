/**
 * 작성일: 2010.08.26
 * 작성자: 대리 서동찬
 * 모듈명: 메세지 연계(Active Post)
 * 설명:
 */
package nexti.ejms.messanger;

import nexti.ejms.common.appInfo;

import org.apache.log4j.Logger;

public class MessangerRelay extends Thread {
	private static Logger logger = Logger.getLogger(MessangerRelay.class);
	
	MessangerRelayBean mrBean = null;
	public final static int SEND_MESSAGE	= 0;	//일반 메세지 전송
	public final static int COLLECT_START	= 1;	//대상부서, 입력담당자 지정 후 문서발송시 메세지 전송 : 대상부서의 6급(016) 이상 직원, 지정된 입력담당자
	public final static int DELIVERY		= 2;	//입력담당자 지정 후 배부처리시 메세지 전송 : 입력담당자
	public final static int COLLECT_COMP	= 3;	//취합완료 후 공개처리시 메세지 전송 : 취합담당자, 입력담당자
	
	public MessangerRelay(MessangerRelayBean mrBean) {
		this.mrBean = mrBean;
	}
	
	public void run() {
		if ( appInfo.isMessanger() ) {
			MessangerRelayManager mrMgr = MessangerRelayManager.instance();
			try {	
				switch ( mrBean.getRelayMode() ) {
				case SEND_MESSAGE:
					mrMgr.sendMessage_SEND_MESSAGE(mrBean);
					break;
				case COLLECT_START:
					mrBean.setDoc_name_title("배부대기문서가 도착하였습니다,입력대기문서가 도착하였습니다");
					mrMgr.sendMessage_COLLECT_START(mrBean);
					break;
				case DELIVERY:
					mrBean.setDoc_name_title("입력대기문서가 도착하였습니다");
					mrMgr.sendMessage_DELIVERY(mrBean);
					break;
				case COLLECT_COMP:
					mrBean.setDoc_name_title("취합완료 후 공개된 문서가 있습니다");
					mrMgr.sendMessage_COLLECT_COMP(mrBean);
					break;
				}
			} catch ( Exception e ) {
				logger.error("MessangerRelay ERROR", e);
			}
		}
	}
}