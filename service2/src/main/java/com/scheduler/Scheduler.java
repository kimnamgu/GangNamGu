package com.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import service.daejang.service.DaejangService;

@Component
public class Scheduler {
	
	Logger log = Logger.getLogger(this.getClass());

	@Resource(name="daejangService")
	private DaejangService daejangService;

	/*매분마다 확인*/
	//@Scheduled(cron = "*/15 * * * * *") //15초마다
	@Scheduled(cron = "0 1 18 * * *")
    public void cronTest1(){
        try {
        	
        	Date dt = new Date();
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, hh:mm:ss a");  
        	System.out.println("seoson batch Start!!! [" + sdf.format(dt).toString() + "]#####################################################################\t");
    		//서손 처리 자동 update select 전달인자는 사실 필요없음
        	daejangService.updateSeoson(sdf.format(dt).toString());
    		
    		System.out.println("seoson batch End!!! [" + sdf.format(dt).toString() + "]#####################################################################\t");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
