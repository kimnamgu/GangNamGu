package com.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import sds.common.common.CommandMap;
import sds.common.service.CommonService;
import sds.common.util.Nvl;

@Component
public class Scheduler {
	
	Logger log = Logger.getLogger(this.getClass());

    @Resource(name="commonService")
	private CommonService commonService;
     
    @Scheduled(cron = "0 40 07 * * *")
    public void cronTest1(){
        try {
        	
        	Date dt = new Date();
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, hh:mm:ss a");  
        	System.out.println("batch Start!!! [" + sdf.format(dt).toString() + "]#####################################################################\t");
        	
        	HttpServletRequest request = null;
        	
    		List<Map<String,Object>> list = null;
    		CommandMap commandMap = new CommandMap();    	
    
    		/* 부서 데이터 동기화 */
    		list = null;
    		commandMap.clear();		
    		commonService.deleteDgnsUserData(commandMap.getMap());
    		
    		commandMap.clear();
    		list = commonService.selectSsoUserList(commandMap.getMap());
    		
    		for(int i= 0; i < list.size(); i++)
    		{		
    			commandMap.clear();
    			commandMap.put("USER_ID", list.get(i).get("USER_ID"));			
    			commandMap.put("USER_NAME", list.get(i).get("USER_NAME"));
    			commandMap.put("USER_PASS", "111111");
    			if("wangs2012".equals(list.get(i).get("USER_ID")) || "epvmzhs233".equals(list.get(i).get("USER_ID"))){  //관리자 아이디만 관리자 권한으로
    				commandMap.put("USER_RIGHT", "A");
    			}
    			else{
    				commandMap.put("USER_RIGHT", "U");
    			}
    			
    			//조재열 주임님은 개인요청에 의해 특정 비밀번호로 설정 
    			if("alias".equals(list.get(i).get("USER_ID"))){
    				commandMap.put("USER_PASS", "Hufs2020!!!!!!");
    			}
    			
    			
    			commandMap.put("DEPT_ID", Nvl.nvlStr(list.get(i).get("DEPT_ID")));			
    			commandMap.put("DEPT_NAME", Nvl.nvlStr(list.get(i).get("DEPT_NAME")));
    			commandMap.put("CLASS_ID", Nvl.nvlStr(list.get(i).get("CLASS_ID")));
    			commandMap.put("CLASS_NAME", Nvl.nvlStr(list.get(i).get("CLASS_NAME")));
    			commandMap.put("POSITION_ID", Nvl.nvlStr(list.get(i).get("POSITION_ID")));
    			commandMap.put("POSITION_NAME", Nvl.nvlStr(list.get(i).get("POSITION_NAME")));
    			commandMap.put("APPLY_REASON", "system");			
    			commandMap.put("USER_STATUS", "0");
    			
    			commonService.insertUserinfo(commandMap.getMap(), request);
    		}
        
    		System.out.println("batch End!!! [" + sdf.format(dt).toString() + "]#####################################################################\t");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
