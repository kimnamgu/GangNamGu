package service.common.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.json.JSONObject;


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
	
	
	//맴에 있는 데이터 key 값을 소문자로 변경
	public static Map changToLowerMapKey(Map map){
		
		
		Map<String, Object> origin = map;
		Map<String, Object> temp = new HashMap<String, Object>();
		
		if(map == null)
		{	
			return null;
		}
		
		Set<String> set = origin.keySet();
		Iterator<String> e = set.iterator();
		while(e.hasNext()){
			String key = e.next();
			Object value = (Object)origin.get(key);
			temp.put(key.toLowerCase(), value);			
		}
		origin = null;
		return temp;
	}
	
	

	
	////List에 있는 데이터 key 값을 소문자로 변경
	public static List keyChangeLower(List<Map<String, Object>> list) {

		
		List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
		
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> tm = new HashMap<String, Object>(list.get(i));
			Iterator<String> iteratorKey = tm.keySet().iterator(); // 키값 오름차순
   		    Map newMap = new HashMap();

			//키값 내림차순 정렬
			while (iteratorKey.hasNext()) {
				String key = iteratorKey.next();
				newMap .put(key.toLowerCase(), tm.get(key));
			}
			newList.add(newMap);
		}
		return newList;
	}
	
	
	
	 public static JSONObject getJsonStringFromMap( Map<String, Object> map )
	 {
        JSONObject jsonObject = new JSONObject();
        for( Map.Entry<String, Object> entry : map.entrySet() ) {
            String key = entry.getKey();
            Object value = entry.getValue();
            jsonObject.put(key, value);
        }
        
        return jsonObject;
	 }
	 
	 //제우스 한글깨짐 수정
	public static String ascToksc(String str) throws UnsupportedEncodingException {
		String result = str;
		if (result == null || result == "")
			return null;
		return new String(result.getBytes("8859_1"), "UTF-8");
	}
}
