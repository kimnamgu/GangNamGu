/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 컨텍스트리스너
 * 설명: WAS시작과 종료시 호출(시스템초기화 및 에이전트실행)
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
		
		//application 변수 등록
		//ctx.setAttribute("isSSO",appInfo.isSSOLink());           // SSO 사용여부 (true, false)

		/******************************************************************/
		// Timer용 Agent		=> 종료시 반드시 terminate() 호출되어야 함
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
		
		// 조직연계.Timer
		if (appInfo.isUsrdept()) {
			ControlUsrDeptGetting.getInstance().start();
		}

		// 조직연계.Timer - SidoLdap
		if (appInfo.isSidoldap()) {
			ControlSidoLdapGetting.getInstance().start();
		}
		
		// 조직연계.Timer - 웹서버비스 세올
		if (appInfo.isWsSaeall()) {
			ControlWsUsrDeptGetting.getInstance().start();
		}
		
		// 전자결재결과 모니터링
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
			
			//전자결재결과 모니터링
			if ( appInfo.isElecappr() ) {
				exchangeMonitor.runThread = false;
			}
			
			clearDatabase(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 시퀀스 체크
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
	 * 임시테이블 삭제 
	 */
	private void clearDatabase(ServletContextEvent event) {
		ctx = event.getServletContext();
		
		try {
			//취합문서 임시정보 테이블 내용 삭제
			ColldocManager.instance().delColldocTempData_TGT_SANC("%");
			
			//신청서 임시테이블 삭제
			SinchungManager.instance().deleteTempAll("%", ctx);
			
			//설문조사 임시테이블 삭제
			ResearchManager.instance().delResearchTempData("%", ctx);
			
			//설문조사그룹 임시테이블 삭제
			ResearchManager.instance().delResearchGrpTempData("%");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}