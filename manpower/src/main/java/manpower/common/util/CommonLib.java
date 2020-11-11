package manpower.common.util;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommonLib {

	static Log commonLog = LogFactory.getLog("WM.COMMON");

	
	
    /**
     * 占쏙옙占쏙옙챨占�? 占쏙옙占쌘울옙占쏙옙 占쏙옙占싹깍옙
     *
     * @return String "yyyy-MM-dd HH:mm:ss" 占쏙옙占쏙옙챨占�? 
     */
	public static String getTimeStampString()
	{
		SimpleDateFormat date = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss", java.util.Locale.KOREA);
		return date.format(new java.util.Date());
	}
	
    /**
     * 占쏙옙占쏙옙챨占�? 占쏙옙占쌘울옙占쏙옙 占쏙옙占싹깍옙
     *
     * @return String "yyyyMMddHHmmss" 占쏙옙占쏙옙챨占�? 
     */
	public static String getTimeStampStringShort()
	{
		SimpleDateFormat date = new SimpleDateFormat ("yyyyMMddHHmmss", java.util.Locale.KOREA);
		return date.format(new java.util.Date());
	}
	
    /**
     * 占쏙옙占쏙옙챨占�? 占쏙옙占쌘울옙占쏙옙 占쏙옙占싹깍옙
     *
     * @return String "yyyyMMddHHmmss" 占쏙옙占쏙옙챨占�? 
     */
	public static String getTodayString()
	{
		SimpleDateFormat date = new SimpleDateFormat ("yyyy/MM/dd", java.util.Locale.KOREA);
		return date.format(new java.util.Date());
	}
	
	/**
     * 占쏙옙占썹날짜 占쏙옙占쌘울옙占쏙옙 占쏙옙占싹깍옙
     *
     * @return String "yyyyMMdd" 
     */
	public static String getTodayStringShort()
	{
		SimpleDateFormat date = new SimpleDateFormat ("yyyyMMdd", java.util.Locale.KOREA);
		return date.format(new java.util.Date());
	}
	
	
    /**
     * 占쏙옙짜 占쏙옙占쌘울옙 Date 占쏙옙체占쏙옙 占쏙옙환
     *
     * @param str "yyyy-MM-dd HH:mm:ss" String 占쏙옙占쌘울옙
     * @return Date 占쏙옙체 
     * @exception ParseException
     */
	public static Date GetDateTime(String str) throws ParseException
	{
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date userDate = formatter.parse(str);
        return userDate;
	}

	/**
	 * <PRE>
	 * Desc : 占쏙옙占쏙옙 채占쏙옙占�? 
	 * @Method : lfill
	 * @param string
	 * @param len
	 * @return
	 * </PRE>
	 */
	public static String lfill(String string, int len) {
		return lfill(string, len, ' ');
	}
	
	/**
	 * 占쏙옙占싱몌옙큼 占쏙옙占쏙옙 특占쏙옙占쏙옙占쏙옙 채占쏙옙占�?
	 * @param string
	 * @param len
	 * @param c
	 * @return
	 */
	public static String lfill(String string, int len, char c) {
		StringBuffer sb = new StringBuffer();
		int strlen = string.getBytes().length;
		for(int i=strlen;i<len;i++) {
			sb.append(c);
		}
		sb.append(string);
		return sb.toString();
	}

	/**
	 * <PRE>
	 * Desc : 占쏙옙占쏙옙 채占쏙옙占�? 
	 * @Method : rfill
	 * @param string
	 * @param len
	 * @return
	 * </PRE>
	 */
	public static String rfill(String string, int len) {
		return rfill(string, len, ' ');
	}
	
	/**
	 * 占쏙옙占싱몌옙큼 占쏙옙占쏙옙 특占쏙옙占쏙옙占쏙옙 채占쏙옙占�?
	 * @param string
	 * @param len
	 * @param c
	 * @return
	 */
	public static String rfill(String string, int len, char c) {
		StringBuffer sb = new StringBuffer();
		String str=CommonLib.cutKString(string, len);
		int strlen = str.getBytes().length;
		sb.append(str);
		for(int i=strlen;i<len;i++) {
			sb.append(c);
		}
		return sb.toString();
	}
	
	/**
	 * <PRE>
	 * Desc : 占쏙옙占쌘울옙 占싼깍옙占쏙옙 占쏙옙占쌉되억옙 占쌍댐옙占쏙옙 체크
	 * @Method : koreanCheck
	 * @param target
	 * @return
	 * @auther : 천창환
	 * </PRE>
	 */
	public static boolean koreanCheck(String target) {
       boolean iskorean = false;
       if (target == null) { return iskorean; }
       int tlength = target.length();
       char[] chs = new char[tlength];
       for (int i = 0; i < tlength; i++) {
               char ch = target.charAt(i);
               if (ch < 0xac00 || ch > 0xd7a3) { chs[i] = ch; } else { iskorean = true; break; }
       }
       return iskorean;
	}

    /**
     * str 占쏙옙  占싼깍옙 체크占쏙옙 i占쏙옙큼 占쌘몌옙
     * @param str
     * @param i
     * @param trail
     * @return
     */
    public static String cutKString(String str, int i) {
    	//str占쏙옙 占쏙옙占쏙옙見占�? 占쏙옙占�? 占쏙옙占쏙옙
    	if(CommonLib.isBlank(str)) {
            return "";
        }
    	
    	if(CommonLib.koreanCheck(str))
    	{
	        String tmp = str;
	        int slen = 0;
	        int blen = 0;
	        char c;
	        
	        //占싼깍옙占쌉뤄옙占싹띰옙 占쏙옙占쏙옙트占쏙옙占쏙옙 i占쏙옙큼 占쌘몌옙占쏙옙
	        try {
	            if (tmp.getBytes("KSC5601").length > i) {
	                while ((blen + 1) < i) {	
	                    c = tmp.charAt(slen);
	                    blen++;
	                    slen++;
	
	                    if (c > 127) {
	                        blen++; //2-byte character..
	                    }
	                }
	
	                tmp = tmp.substring(0, slen);
	            }
	        } catch (java.io.UnsupportedEncodingException e) {
	        }
	        return tmp;
    	}
    	else
    	{
    		return str;
    	}

    }
	
	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
    
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	public static String defaultIfEmpty(String str, String defaultStr) {
        return CommonLib.isEmpty(str) ? defaultStr : str;
    }

	public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

	
	/**
	 * 占쌉력뱄옙占쏙옙 IP占쏙옙 '192.007.105.035' 占쏙옙占승뤄옙 占쏙옙占쏙옙占쏙옙占싹울옙占쌔댐옙
	 * @param string
	 * @return String
	 */
	public static String getIPAddress(String ip) {
		StringBuffer result=new StringBuffer();
		int idx;
		while((idx = ip.indexOf('.'))!=-1) {
			result.append(lfill(ip.substring(0, idx),3,'0')).append(".");
			ip = ip.substring(idx+1);
		}
		result.append(lfill(ip,3,'0'));
		return result.toString();
	}
	
	

	/**<PRE>
	 * Desc : 占쏙옙占쏙옙占싶곤옙占쏙옙 token占쏙옙占쏙옙 1占쏙옙占쏙옙占�?占쏙옙 占쏙옙占쏙옙 占쏙옙 占썼열占쏙옙 占쏙옙占�? 占쏙옙占쏙옙占쏙옙占쏙옙 占쌌뒤몌옙 占쏙옙占쏙옙占싼댐옙.(占싹반뱄옙占쏙옙, html占쏙옙占쏙옙) 
	 * @Method : getArrayToken
	 * @param dataValue : 占쏙옙占쏙옙占싶곤옙(a,b,c) 
	 * @param token : 占쏙옙占싻곤옙
	 * @param startString : 占썼열占쏙옙 占쏙옙占�? 占쏙옙 占쌌울옙 占쏙옙占쏙옙 占쏙옙트占쏙옙
	 * @param endString : 占썼열占쏙옙 占쏙옙占�? 占쏙옙 占쌘울옙 占쏙옙占쏙옙 占쏙옙트占쏙옙
	 * @return 
	 * @throws Exception
	 * @auther : 占쏙옙占쏙옙占쏙옙
	 * </PRE>
	 */
	public static String getArrayToken(String dataValue, String token, String startString, String endString){
		
		String arrayTypeStr = "";

		if(dataValue != null && !token.equals("")){
			String[] arrayType = dataValue.split(token);
			if(arrayType != null){
				if(arrayType.length == 1){
					arrayTypeStr = arrayType[0];
					
				}else{
					for(int i = 0 ; i < arrayType.length ; i++){
						arrayTypeStr += startString + arrayType[i] + endString;
					}				
				}
			}
		}
		
		return arrayTypeStr;
	} 	
	
	
	
	public static String getStringForCLOB(Clob clob) {
		String str = "";
		StringBuffer sbf = new StringBuffer();
		Reader br = null;
		char[] buf = new char[1024];
		int readcnt;
		try {
			br = clob.getCharacterStream();
			while ((readcnt=br.read(buf,0,1024))!=-1) {
				sbf.append(buf,0,readcnt);
			}
		} catch (Exception e) {
			LogFactory.getLog("WM.ERROR").debug("Failed to create String object from CLOB  >>> [ " + e + " ]");
		}finally{
			if(br!=null)
			try {
				br.close();
			} catch (IOException e) {
				LogFactory.getLog("WM.ERROR").debug("Failed to close BufferedReader object  >>> [ " + e + " ]");
			}
		}
		return sbf.toString();
	
	}
}
