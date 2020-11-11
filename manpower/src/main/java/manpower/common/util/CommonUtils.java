package manpower.common.util;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ConfigurableWebApplicationContext;


public class CommonUtils {
	private static final Logger log = Logger.getLogger(CommonUtils.class);
	
	public static String getRandomString(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static void printMap(Map<String,Object> map){
		Iterator<Entry<String,Object>> iterator = map.entrySet().iterator();
		Entry<String,Object> entry = null;
		log.debug("--------------------printMap--------------------\n");
		while(iterator.hasNext()){
			entry = iterator.next();
			log.debug("key : "+entry.getKey()+",\tvalue : "+entry.getValue());
		}
		log.debug("");
		log.debug("------------------------------------------------\n");
	}
	
	public static void printList(List<Map<String,Object>> list){
		Iterator<Entry<String,Object>> iterator = null;
		Entry<String,Object> entry = null;
		log.debug("--------------------printList--------------------\n");
		int listSize = list.size();
		for(int i=0; i<listSize; i++){
			log.debug("list index : "+i);
			iterator = list.get(i).entrySet().iterator();
			while(iterator.hasNext()){
				entry = iterator.next();
				log.debug("key : "+entry.getKey()+",\tvalue : "+entry.getValue());
			}
			log.debug("\n");
		}
		log.debug("------------------------------------------------\n");
	}
	
	
	public static String changeEnterBr(String s) {
	  	
	   	if ( s == null ) return null;
        	        
        String val=s.replaceAll("\r\n|\n|\r", "<br>");	        
        //val=val.replaceAll("\r\n|\n|\r", "");
       
        return val;
    }
	
	
	/**
	  * Object type 占쏙옙占시셔�
	  * 
	  * @param obj 
	  * @return Boolean : true / false
	  */
	 public static Boolean empty(Object obj) {
	  if (obj instanceof String) return obj == null || "".equals(obj.toString().trim());
	  else if (obj instanceof List) return obj == null || ((List) obj).isEmpty();
	  else if (obj instanceof Map) return obj == null || ((Map) obj).isEmpty();
	  else if (obj instanceof Object[]) return obj == null || Array.getLength(obj) == 0;
	  else return obj == null;
	 }
	 
	 /**
	  * Object type 占쏙옙占시셔�
	  * 
	  * @param obj
	  * @return Boolean : true / false
	  */
	 public static Boolean notEmpty(Object obj) {
	  return !empty(obj);
	 }

	
	 public static String outUH1(String str)
	 {
		 	String tmp = "";
		 
		 	//System.out.println("val1 = [" + str.substring(0,1) + "]");
		    if(str.substring(0,1).equals("1"))
			{
				tmp = "공사";
			}
			
			if(str.substring(1,2).equals("1"))
			{	
				if(tmp == "")	
					tmp = "용역";
				else
					tmp = tmp + ",용역";	
			}
			
			if(str.substring(2,3).equals("1"))	
			{	
				if(tmp == "")	
					tmp = "물품";
				else
					tmp = tmp + ",물품";	
			}
					   
	       return tmp;	    
	 }
	 
	 
	 
	 public static String outUH2(String str)
	 {
		 	String tmp = "";
		 
		 	//System.out.println("val1 = [" + str.substring(0,1) + "]");
		    if(str.substring(0,1).equals("1"))
			{
				tmp = "디자인";
			}
			
			if(str.substring(1,2).equals("1"))
			{	
				if(tmp == "")	
					tmp = "인쇄물";
				else
					tmp = tmp + ",인쇄물";	
			}
			
			if(str.substring(2,3).equals("1"))	
			{	
				if(tmp == "")	
					tmp = "동영상";
				else
					tmp = tmp + ",동영상";	
			}
			
			if(str.substring(3,4).equals("1"))		
			{	
				if(tmp == "")	
					tmp = "행사기획";
				else
					tmp = tmp + ",행사기획";	
			}
			
			if(str.substring(4,5).equals("1"))		
			{	
				if(tmp == "")	
					tmp = "종합홍보";
				else
					tmp = tmp + ",종합홍보";	
			}
			
			if(str.substring(5,6).equals("1"))		
			{	
				if(tmp == "")	
					tmp = "기타";
				else
					tmp = tmp + ",기타";	
			}
		   
	       return tmp;	    
	 }
}
