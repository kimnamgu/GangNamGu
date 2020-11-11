/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 외부망 공통
 * 설명:
 */
package nexti.ejms.outside.action;

import java.util.ArrayList;

import nexti.ejms.common.OutsideInfo;
import nexti.ejms.outside.model.OutsideBean;

public class OutsideUtil {
	
	public static boolean outsidIpChk(String clientip)
	{
		boolean result = false; 
		
		String ip = "";
		String ip1 = "";
		int	iplength = 0;
		
		ArrayList ipList = OutsideInfo.getIpList();

		if(ipList != null){
			OutsideBean outbean = null;
			for(int i=0; i<ipList.size(); i++){
				outbean = (OutsideBean)ipList.get(i);
				ip = outbean.getIp();
				iplength = ip.length();
				
				//org.apache.log4j.Logger.getLogger(OutsideUtil.class).info("접근IP : " + clientip);
				
				ip1 = clientip.substring(0,iplength);
				if(ip.equals(ip1)){
					result = true;
					break;
				}
			}
		}
		   
		return result;
	}		
}
