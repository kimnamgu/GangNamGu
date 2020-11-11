/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���ε�������
 * ����: ���ǻ����� �Ҹ�� ȣ��(����:session.setAttribute("listener", new NewHttpSessionBindingListener());) 
 */
package nexti.ejms.common;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.research.model.ResearchManager;

public class NewHttpSessionBindingListener implements HttpSessionBindingListener {
	public void valueBound(HttpSessionBindingEvent event) {	
		clearDatabase(event);
	}

	public void valueUnbound(HttpSessionBindingEvent event) {
		clearDatabase(event);
	}
	
	/**
	 * �ӽ����̺� ���� 
	 *
	 */
	private void clearDatabase(HttpSessionBindingEvent event) {
		
		HttpSession session = event.getSession();
		String sessi = session.getId().split("[!]")[0];
		ServletContext context = event.getSession().getServletContext();
		
		//�������� ���ǰ��������� �ߺ��α��� ���ó��
		try { if ( !session.isNew() ) return; } catch ( Exception e ) {}
		
		try {
			//���չ��� �ӽ����� ���̺� ���� ����
			ColldocManager cdmgr = ColldocManager.instance();
			cdmgr.delColldocTempData_TGT_SANC(sessi);
			
			//��û�� �ӽ����̺� ����
			SinchungManager smgr = SinchungManager.instance();
			smgr.deleteTempAll(sessi, context);
			
			//�������� �ӽ����̺� ����
			ResearchManager rmgr = ResearchManager.instance();
			rmgr.delResearchTempData(sessi, context);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}