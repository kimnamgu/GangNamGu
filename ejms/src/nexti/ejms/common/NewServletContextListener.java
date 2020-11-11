/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���ؽ�Ʈ������
 * ����: WAS���۰� ����� ȣ��(�ý����ʱ�ȭ �� ������Ʈ����)
 */
package nexti.ejms.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import nexti.ejms.agent.ControlDeadlineTimer;
import nexti.ejms.agent.ControlOutsideRchGetting;
import nexti.ejms.agent.ControlOutsideReqGetting;
import nexti.ejms.agent.ControlScheduleTimer;
import nexti.ejms.agent.ControlSidoLdapGetting;
import nexti.ejms.agent.ControlUsrDeptGetting;
import nexti.ejms.agent.ControlWsUsrDeptGetting;
import nexti.ejms.colldoc.model.ColldocManager;
import nexti.ejms.research.model.ResearchManager;
import nexti.ejms.sinchung.model.SinchungManager;
import nexti.ejms.elecAppr.exchange.monitor.ExchangeMonitor;

public final class NewServletContextListener implements ServletContextListener {	
    private ServletContext ctx = null;
    private ExchangeMonitor exchangeMonitor = null;
    private Thread exchangeMonitorThread = null;
    
	public NewServletContextListener() {}
	
	public void contextInitialized(ServletContextEvent event){
		ctx = event.getServletContext();
		appInfo.setRootRealPath(ctx.getRealPath(""));
		if (!appInfo.ConfigLoad()) {
			ctx.log("Properties file is invalid");
		}
		
		if (!OutsideInfo.ConfigLoad()) {
			ctx.log("Properties file is invalid");
		}

		ctx.log("ejms system start");
		ctx.log("WebApplication RealPath = "+appInfo.getRootRealPath());
		
		checkSequence();
		clearDatabase(event);
		
		//application ���� ���
		//ctx.setAttribute("isSSO",appInfo.isSSOLink());           // SSO ��뿩�� (true, false)

		/******************************************************************/
		// Timer�� Agent		=> ����� �ݵ�� terminate() ȣ��Ǿ�� ��
		// Schedule.Timer
		if (appInfo.isScheduleTimer()) {
			ControlScheduleTimer.getInstance().start();
		}
		// Deadline.Timer
		if (appInfo.isDeadlineTimer()) {
			ControlDeadlineTimer.getInstance().start();
		}

		// rchresult.Timer
		if (appInfo.isRchresultTimer()) {
			ControlOutsideRchGetting.getInstance().start();
		}
		
		// reqresult.Timer
		if (appInfo.isReqresultTimer()) {
			ControlOutsideReqGetting.getInstance().start();
		}
		
		// ��������.Timer
		if (appInfo.isUsrdept()) {
			ControlUsrDeptGetting.getInstance().start();
		}

		// ��������.Timer - SidoLdap
		if (appInfo.isSidoldap()) {
			ControlSidoLdapGetting.getInstance().start();
		}
		
		// ��������.Timer - �������� ����
		if (appInfo.isWsSaeall()) {
			ControlWsUsrDeptGetting.getInstance().start();
		}
		
		// ���ڰ����� ����͸�
		if ( appInfo.isElecappr() ) {
			exchangeMonitor = new ExchangeMonitor();
			exchangeMonitorThread = new Thread(exchangeMonitor);
			exchangeMonitorThread.start();
		}
	}

	public void contextDestroyed(ServletContextEvent event){
		ctx = event.getServletContext();
		
		try {
			event.getServletContext().log("Now Terminating >>");
			ControlScheduleTimer.getInstance().terminate();
			ControlDeadlineTimer.getInstance().terminate();
			ControlOutsideRchGetting.getInstance().terminate();
			ControlOutsideReqGetting.getInstance().terminate();
			ControlUsrDeptGetting.getInstance().terminate();
			ControlSidoLdapGetting.getInstance().terminate();
			
			//���ڰ����� ����͸�
			if ( appInfo.isElecappr() ) {
				exchangeMonitor.runThread = false;
			}
			
			clearDatabase(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ������ üũ
	 */
	
	private void checkSequence() {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			String[] SEQ = {"LOGINSEQ",	"DOCSEQ",	"RCHSEQ",	"RCHGRPSEQ",	"REQFORMSEQ",	"SANCSEQ"};
			String[] COL = {"SEQ",		"SYSDOCNO", "RCHNO", 	"RCHGRPNO",		"REQFORMNO", 	"SEQ"};
			String[] TBL = {"LOGINLOG",	"DOCMST",	"RCHMST",	"RCHGRPMST",	"REQFORMMST",	"SANCTGT"};
			
			con = ConnectionManager.getConnection();
			for ( int i = 0; i < SEQ.length; i++ ) {
				try {
					int seq = 0;
					stmt = con.createStatement();
					rs = stmt.executeQuery("SELECT " + SEQ[i] + ".NEXTVAL FROM DUAL");
					rs.next();
					seq = rs.getInt(1);
					rs.close();
					stmt.close();
					
					stmt = con.createStatement();
					rs = stmt.executeQuery("SELECT NVL(MAX(" + COL[i] + "), 0) FROM " + TBL[i]);
					rs.next();
					seq = rs.getInt(1) - seq;
					rs.close();
					stmt.close();
					
					if ( seq != 0 ) {
						stmt = con.createStatement();
						stmt.executeQuery("ALTER SEQUENCE " + SEQ[i] + " INCREMENT BY " + seq);
						stmt.close();
						
						stmt = con.createStatement();
						stmt.executeQuery("SELECT " + SEQ[i] + ".NEXTVAL FROM DUAL");
						stmt.close();
					}
					
					stmt = con.createStatement();
					stmt.executeQuery("ALTER SEQUENCE " + SEQ[i] + " INCREMENT BY 1");
					stmt.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(con);
		}
	}
	
	/**
	 * �ӽ����̺� ���� 
	 */
	private void clearDatabase(ServletContextEvent event) {
		ctx = event.getServletContext();
		
		try {
			//���չ��� �ӽ����� ���̺� ���� ����
			ColldocManager.instance().delColldocTempData_TGT_SANC("%");
			
			//��û�� �ӽ����̺� ����
			SinchungManager.instance().deleteTempAll("%", ctx);
			
			//�������� �ӽ����̺� ����
			ResearchManager.instance().delResearchTempData("%", ctx);
			
			//��������׷� �ӽ����̺� ����
			ResearchManager.instance().delResearchGrpTempData("%");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}