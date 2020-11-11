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
import scms.common.common.CommandMap;
import scms.common.service.CommonService;
import scms.common.util.Nvl;

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
    
    		/* �겫占쏙옙苑� 占쎈쑓占쎌뵠占쎄숲 占쎈짗疫꿸퀬�넅 */
    		list = null;
    		commandMap.clear();		
    		commonService.deleteScmsUserData(commandMap.getMap());
    		
    		commandMap.clear();
    		list = commonService.selectSsoUserList(commandMap.getMap());
    		
    		for(int i= 0; i < list.size(); i++)
    		{		
    			commandMap.clear();
    			commandMap.put("USER_ID", list.get(i).get("USER_ID"));			
    			commandMap.put("USER_NAME", list.get(i).get("USER_NAME"));
    			commandMap.put("USER_PASS", list.get(i).get("USER_ID") + "3423");
    			if("25382170".equals(list.get(i).get("USER_ID")) || "kimsh798".equals(list.get(i).get("USER_ID")) || "bluegreen".equals(list.get(i).get("USER_ID"))){  //이영순팀장, 박지혜 주임
    				commandMap.put("USER_RIGHT", "A");
    			}
    			else{
    				commandMap.put("USER_RIGHT", "U");
    			}
    			commandMap.put("DEPT_ID", Nvl.nvlStr(list.get(i).get("DEPT_ID")));			
    			commandMap.put("DEPT_NAME", Nvl.nvlStr(list.get(i).get("DEPT_NAME")));
    			commandMap.put("CLASS_ID", Nvl.nvlStr(list.get(i).get("CLASS_ID")));
    			commandMap.put("CLASS_NAME", Nvl.nvlStr(list.get(i).get("CLASS_NAME")));
    			commandMap.put("POSITION_ID", Nvl.nvlStr(list.get(i).get("POSITION_ID")));
    			commandMap.put("POSITION_NAME", Nvl.nvlStr(list.get(i).get("POSITION_NAME")));
    			commandMap.put("USER_TEL", Nvl.nvlStr(list.get(i).get("TEL")));
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
