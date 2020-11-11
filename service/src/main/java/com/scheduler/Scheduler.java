package com.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import service.common.util.HttpConnectionUtil;
import service.daejang.service.DaejangService;

@Component
public class Scheduler {
	
	Logger log = Logger.getLogger(this.getClass());

	@Resource(name="daejangService")
	private DaejangService daejangService;

	/*매분마다 확인*/
	//@Scheduled(cron = "*/15 * * * * *") //15초마다
	@Scheduled(cron = "0 1 10 * * *")
    public void cronTest1(){
        try {
        	
        	Date dt = new Date();
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, hh:mm:ss a");  
        	System.out.println("seoson batch Start!!! [" + sdf.format(dt).toString() + "]#####################################################################\t");
    		//서손 처리 자동 update select 전달인자는 사실 필요없음
        	daejangService.updateSeoson(sdf.format(dt).toString());
        	

        	//서손후 환불 처리
        	List<Map<String,Object>> list = daejangService.selectSeosonList();
        	System.out.println("selectSeosonList 확인 : " + list.toString());



    		Map<String,Object> tempMap = null;
        	String tno = "";
        	String app_time = "";
        	
        	for(int i=0; i<list.size(); i++){
    			tempMap = list.get(i);
    			
    			System.out.println("tempMap 확인 : " + tempMap.toString());
    			
    			tno = (String) tempMap.get("PAYMENT_ID");
    			app_time = (String) tempMap.get("PAYMENT_DATE");
    			
    			String url = "http://conf.gangnam.go.kr/gnconference/app/payment/cancel"; 	//URL
    			HashMap<String, String> param = new HashMap<String, String>();
    			
    			param.put("tno", tno);	//PARAM
    			param.put("app_time", app_time);	//PARAM
    			
    			HttpConnectionUtil.postRequest(url, param);
    		}
    		
    		System.out.println("seoson batch End!!! [" + sdf.format(dt).toString() + "]#####################################################################\t");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
