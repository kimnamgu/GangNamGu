package nexti.ejms.elecAppr.exchange.monitor;

import java.io.File;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import nexti.ejms.elecAppr.exchange.ExchangeCmd;
import nexti.ejms.elecAppr.model.ElecApprManager;

public class ExchangeMonitor implements Runnable{
	private static Logger logger = Logger.getLogger(ExchangeMonitor.class);	
	private int interval=0;
	public boolean runThread = true;
	
	/**
	 * 전자결재 결과 모니터링 프로그램 
	 */
	public ExchangeMonitor(){}
	
	public void run(){
		ResourceBundle resource=ResourceBundle.getBundle(ExchangeCmd.RESOURCE_FILE_NAME);
		this.interval=1000*60*Integer.parseInt(resource.getString("interval"));	// 1초 = 1000
		while(runThread){
			try {
				if ( ElecApprManager.instance().isSancneed() == true ) {
					String tempDir=resource.getString("exchange_receive_root_dir");
					String tempDtd=resource.getString("exchange_dtd");
					File seekDir = new File(tempDir);
					String[] dirList = seekDir.list();
					if(dirList!=null){
						for (int i = 0; i < dirList.length; i++) {
							if ((new File(tempDir+ dirList[i])).isDirectory()) {
								ExchangeResult result=new ExchangeResult(dirList[i]);
								result.setRootDir(tempDir);
								result.setDtdDir(tempDtd);
								try {
									Thread t=new Thread(result);
									t.start();
									logger.info("전자결재결과 수신 : " + tempDir + dirList[i]);
								} catch (Exception e) {
									logger.error(e); 
								}
							}//if
						}//for
					}//if
				}
			} catch (Exception e) {
				logger.error(e); 
			} finally {
				try {
					Thread.sleep(interval);
				} catch (Exception e) {
					logger.error(e);
				}
			}
		}
	}
}