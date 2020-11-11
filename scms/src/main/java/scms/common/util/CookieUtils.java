package scms.common.util;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

	 
	 private static final String encoding = "UTF-8";
	 private static final String path = "/";
	 
	 
	 /**
	  * @description �듅�젙 key�쓽 荑좏궎媛믪쓣 List濡� 諛섑솚�븳�떎.
	  * @params key: 荑좏궎 �씠由�
	  */
	 public List<String> getValueList(String key, HttpServletRequest request) throws UnsupportedEncodingException{
	  Cookie[] cookies = request.getCookies();
	  String[] cookieValues = null;
	  String value = "";
	  List<String> list = null;
	  
	  // �듅�젙 key�쓽 荑좏궎媛믪쓣 ","濡� 援щ텇�븯�뿬 String 諛곗뿴�뿉 �떞�븘以��떎.
	  // ex) 荑좏궎�쓽 key/value ---> key = "clickItems", value = "211, 223, 303"(�긽�뭹 踰덊샇)
	  if(cookies != null){
	   for(int i=0;i<cookies.length;i++){
	    if(cookies[i].getName().equals(key)){
	     value = cookies[i].getValue();
	     cookieValues = (URLDecoder.decode(value, encoding)).split(",");
	     break;
	    }
	   }
	  }
	  
	  // String 諛곗뿴�뿉 �떞寃쇰뜕 媛믩뱾�쓣 List濡� �떎�떆 �떞�뒗�떎.
	  if(cookieValues != null){
	   list = new ArrayList<String>(Arrays.asList(cookieValues));
	   
	   if (list.size() > 3) { // 媛믪씠 3媛쒕�� 珥덇낵�븯硫�, 理쒓렐 寃� 3媛쒕쭔 �떞�뒗�떎.
	    int start = list.size() - 3;
	    List<String> copyList = new ArrayList<String>();
	    for (int i = start ; i < list.size() ; i++) {
	     copyList.add(list.get(i));
	    }
	    list = copyList;
	   }
	  }
	  return list;
	 }
	 
	 /**
	  * @description 荑좏궎瑜� �깮�꽦 �샊�� �뾽�뜲�씠�듃�븳�떎.
	  * @params key: 荑좏궎 �씠由�, value: 荑좏궎 �씠由꾧낵 吏앹쓣 �씠猷⑤뒗 媛�, day: 荑좏궎�쓽 �닔紐�
	  */
	 public void setCookie(String key, String value, int day, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
	  List<String> list = getValueList(key, request);
	  String sumValue = "";
	  int equalsValueCnt = 0;
	  
	  if(list != null){
	   for(int i=0; i<list.size(); i++){
	    sumValue += list.get(i) + ",";
	    if(list.get(i).equals(value)){
	     equalsValueCnt++;
	    }
	   }
	   if(equalsValueCnt != 0){ // 媛숈� 媛믪쓣 �꽔�쑝�젮怨� �븷 �븣�쓽 泥섎━
	    if(sumValue.substring(sumValue.length()-1).equals(",")){
	     sumValue = sumValue.substring(0, sumValue.length()-1);
	    }
	   }else{
	    sumValue += value;
	   }
	  }else{
	   sumValue = value;
	  }
	  
	  if(!sumValue.equals("")){
	   Cookie cookie = new Cookie(key, URLEncoder.encode(sumValue, encoding));
	   cookie.setMaxAge(60*60*24*day);
	   cookie.setPath(path);
	   response.addCookie(cookie);
	  }
	 }
	 
	/**
	  * @description 荑좏궎媛믩뱾 以� �듅�젙 媛믪쓣 �궘�젣�븳�떎.
	  * @params key: 荑좏궎 �씠由�, value: 荑좏궎 �씠由꾧낵 吏앹쓣 �씠猷⑤뒗 媛�
	  */
	 public void deleteCookie(String key, String value, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
	  List<String> list = getValueList(key, request);
	  list.remove(value);
	  
	  String sumValue = "";
	  if(list.size() != 0){
	   for(int i=0; i<list.size();i++){
	    sumValue += list.get(i)+",";
	   }
	   if(sumValue.substring(sumValue.length()-1).equals(",")){
	    sumValue = (sumValue.substring(0, sumValue.length()-1)).replaceAll(" ","");
	   }
	  }
	  Cookie cookie = null;
	  int time = 60*60*24*20;
	  
	  if(sumValue.equals("")){
	   time = 0;
	  }
	  
	  cookie = new Cookie(key, URLEncoder.encode(sumValue, encoding));
	  cookie.setMaxAge(time);
	  cookie.setPath(path);
	  response.addCookie(cookie);
	 }
	 

	 /**
	  * @description �씪諛섏쟻�씤 荑좏궎 �깮�꽦
	  * @params key: 荑좏궎 �씠由�, value: 荑좏궎 �씠由꾧낵 吏앹쓣 �씠猷⑤뒗 媛�,  day: 荑좏궎�쓽 �닔紐�
	  */
	 public static Cookie createNewCookie(String key, String value, int day, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
	  Cookie cookie = new Cookie(key, URLEncoder.encode(value, encoding));
	  cookie.setPath(path);
	  cookie.setMaxAge(60*60*24*day);
	  response.addCookie(cookie);
	  return cookie;
	 }

	 /**
	  * @description 荑좏궎�뱾�쓣 留듭쑝濡� 諛섑솚�븳�떎.
	  * @params 
	  */
	 public static HashMap getValueMap(HttpServletRequest request){
	  HashMap cookieMap = new HashMap();
	  
	  Cookie[] cookies = request.getCookies();
	  if (cookies != null) {
	   for (int i = 0; i < cookies.length; i++) {
	    cookieMap.put(cookies[i].getName(), cookies[i]);
	    System.out.println("name=[" + cookies[i].getName() + "]  val=[" + cookies[i] + "]\t");
	    
	   }
	  }
	  
	  System.out.println("length=[" + cookies.length + "] \t");
	  System.out.println("cookieMap=[" + cookieMap + "] \t");
	  return cookieMap;
	 }
	 
	 /**
	  * @description ","濡� 援щ텇�맂 媛믪씠 �븘�땶 �떒�씪 媛믪쑝濡� ���옣�맂 荑좏궎�쓽 媛믪쓣 諛섑솚�븳�떎.
	  * @params key: 荑좏궎 �씠由�
	  */
	 public static String getValue(String key, HttpServletRequest request) throws UnsupportedEncodingException {
	  Cookie cookie = (Cookie) getValueMap(request).get(key);
	  if (cookie == null) return null;
	  return URLDecoder.decode(cookie.getValue(), encoding);
	 }

	 /**
	  * @description 荑좏궎媛� �엳�뒗吏� �솗�씤.
	  * @params key: 荑좏궎 �씠由�
	  */
	 public boolean isExist(String key, HttpServletRequest request) {
	  return getValueMap(request).get(key) != null;
	 }
	 
}