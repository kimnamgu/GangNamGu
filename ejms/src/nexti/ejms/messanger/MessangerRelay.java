/**
 * �ۼ���: 2010.08.26
 * �ۼ���: �븮 ������
 * ����: �޼��� ����(Active Post)
 * ����:
 */
package nexti.ejms.messanger;

import nexti.ejms.common.appInfo;

import org.apache.log4j.Logger;

public class MessangerRelay extends Thread {
	private static Logger logger = Logger.getLogger(MessangerRelay.class);
	
	MessangerRelayBean mrBean = null;
	public final static int SEND_MESSAGE	= 0;	//�Ϲ� �޼��� ����
	public final static int COLLECT_START	= 1;	//���μ�, �Է´���� ���� �� �����߼۽� �޼��� ���� : ���μ��� 6��(016) �̻� ����, ������ �Է´����
	public final static int DELIVERY		= 2;	//�Է´���� ���� �� ���ó���� �޼��� ���� : �Է´����
	public final static int COLLECT_COMP	= 3;	//���տϷ� �� ����ó���� �޼��� ���� : ���մ����, �Է´����
	
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
					mrBean.setDoc_name_title("��δ�⹮���� �����Ͽ����ϴ�,�Է´�⹮���� �����Ͽ����ϴ�");
					mrMgr.sendMessage_COLLECT_START(mrBean);
					break;
				case DELIVERY:
					mrBean.setDoc_name_title("�Է´�⹮���� �����Ͽ����ϴ�");
					mrMgr.sendMessage_DELIVERY(mrBean);
					break;
				case COLLECT_COMP:
					mrBean.setDoc_name_title("���տϷ� �� ������ ������ �ֽ��ϴ�");
					mrMgr.sendMessage_COLLECT_COMP(mrBean);
					break;
				}
			} catch ( Exception e ) {
				logger.error("MessangerRelay ERROR", e);
			}
		}
	}
}