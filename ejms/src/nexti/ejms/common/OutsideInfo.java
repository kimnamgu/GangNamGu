/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 외부망정보
 * 설명:
 */
package nexti.ejms.common;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import nexti.ejms.outside.model.OutsideBean;

public class OutsideInfo {
	private static final String CONFIG_FILE = "/outside.properties";	
	protected static boolean isLoading=false;
	private static String rootRealPath;

	private static String   clientip;
	private static String 	serverkey;	
	private static ArrayList ipList = new ArrayList();

	protected static boolean ConfigLoad() {
		if(isLoading) return true;
		Properties pconfig = null;
		InputStream is = null;

		try {
			pconfig = new Properties();
			is = OutsideInfo.class.getResourceAsStream(CONFIG_FILE);
			pconfig.load(is);
			
			String[] cips = null;
			
			clientip	= pconfig.getProperty("client.ip","");
			serverkey	= pconfig.getProperty("client.serverkey","");
			
			if (!"".equals(clientip)){
				cips = clientip.split(";");
			}
			
			if (cips != null){
				OutsideBean bean = null;
				for (int i = 0; i < cips.length; i++){	
					bean = new OutsideBean();
					bean.setIp(cips[i]);
					ipList.add(bean);
				}
			}
			
			isLoading = true;
			
		} catch (Exception e) {
			e.printStackTrace();
			isLoading = false;
			return false;
		} finally {
			try { is.close(); } catch (Exception e) {}
		}
		return true;
	}	

	public static String getRootRealPath() {
		return rootRealPath;
	}
	
	public static void setRootRealPath(String rootRealPath) {
		OutsideInfo.rootRealPath = rootRealPath;
	}

	public static boolean isLoading() {
		return isLoading;
	}

	public static String getServerkey() {
		return serverkey;
	}

	public static String getClientip() {
		return clientip;
	}

	public static ArrayList getIpList() {
		return ipList;
	}

}
