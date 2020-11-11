package corona.common.util;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.log4j.Logger;


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
	
	public static String ascToksc(String str) throws UnsupportedEncodingException {
		String result = str;
		if (result == null || result == "")
			return null;
		return new String(result.getBytes("8859_1"), "UTF-8");
	}
}
