package nexti.ejms.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.CharArrayReader;
import java.io.File;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Clob;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Utils {
	
	/**
	 * SHA256 암호화
	 * @param value
	 * @return
	 */
	public static String SHA256(String value) {
		String result = null;
		try {
			MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
			sh.update(value.getBytes()); 
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer(); 
			for ( int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			result = sb.toString();
			
		} catch ( NoSuchAlgorithmException e ) {
			e.printStackTrace(); 
			result = null; 
		}

		return result;
	}
	
	
	/**
	 * CLOB 컬럼에서 데이터 저장하기
	 * @param rs
	 * @param columnName
	 * @return
	 */
	public static boolean writeClobData(ResultSet rs, String columnName, String columnData) {
		boolean result = false;
		Reader reader = null;
		BufferedReader bufReader = null;
		Writer writer = null;
		BufferedWriter bufWriter = null;		
		
		try {
			Clob clobData = rs.getClob(columnName);
			if ( clobData != null ) {
				writer = clobData.setCharacterStream(0);
				reader = new CharArrayReader(columnData.toCharArray());
			}
			
			int cnt = 0;
			char[] buf = new char[1024];
			if ( writer != null && reader != null ) {
				bufWriter = new BufferedWriter(writer);
				bufReader = new BufferedReader(reader);			
				while ( (cnt = bufReader.read(buf, 0, buf.length)) != -1 ) {
					bufWriter.write(buf, 0, cnt);
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bufWriter.close();
				writer.close();
				bufReader.close();
				reader.close();
			} catch (Exception e) {}
		}
		
		return result;
	}
	
	/**
	 * CLOB 컬럼에서 데이터 읽어오기
	 * @param rs
	 * @param columnName
	 * @return
	 */
	public static String readClobData(ResultSet rs, String columnName) {
		StringBuffer result = new StringBuffer();
		Reader reader = null;
		BufferedReader bufReader = null;
		
		try {
			reader = rs.getCharacterStream(columnName);
			
			int cnt = 0;
			char[] buf = new char[1024];
			if ( reader != null ) {
				bufReader = new BufferedReader(reader);
				while ( (cnt = bufReader.read(buf, 0, buf.length)) != -1 ) {
					result.append(buf, 0, cnt);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bufReader.close();
				reader.close();
			} catch (Exception e) {}
		}
		
		return result.toString();
	}
	
    /**
     * 문자열이 max보다 클경우 max 크기만큼만 잘라서 반환한다.
     * 
     * @param s - String
     * @param max - int
     * @return
     */
    public static String formatTitle( String s, int max ) 
    {
        if( s.length() <= max) return s;

        String tmp = null;        
        byte bTmp[] = null;
        String rets = "" ;

        for(int i=0, k=0; i < s.length(); i++) {
            tmp = s.substring( i, i+1 );
            bTmp = tmp.getBytes();
            if( bTmp.length > 1 ) {
                rets += tmp;
                k +=2;
            }  else {
                rets += tmp;
                k ++;
            }

            if( max <= k ) break;
        }
        
        return rets+"..";
    }
    
    /**
     * 바이트 단위로 문자 자름(euc-kr)
     * @param str:문자열
     * @param maxLen: 반환될 문자열 길이
     * @param padStr:자른후붙일문자열
     * @return
     */
    public static String subString(String str, int maxLen, String padStr) {
    	StringBuffer chr = new StringBuffer();
    	StringBuffer newStr = new StringBuffer();
    	for ( int len = 0, i = 0; i < str.length(); i++ ) {
    		chr.delete(0, chr.capacity());
    		chr.append(str.substring(i, i + 1));
    		len += chr.toString().getBytes().length;				
    		if ( len <= maxLen - padStr.getBytes().length ) {
    			newStr.append(chr);
    		} else {
    			str = newStr.toString() + padStr;
    			break;
    		}
    	}
    	return str;
    }
    	
	/**
	 * 문자갯수 초과시 자름
	 * @param srcstring
	 * @param srcsize
	 * @return 적용문자열
	 */
	public static String moreCut(String str,int num)
	{
		int eos=0, i=0, cnt=0;
		byte[] temp = str.getBytes();
		for(i = 0; i < num ; i++) { 
			if(temp[i] < 0) cnt ++; 
		}
		if (cnt%2 != 0) { 
			eos = num-1; 
		} else { 
			eos = num;
		}
		String s = new String(temp,0,eos);
		if (temp.length > num) {
			s += "..";
		}
		return s;
	} 
	
	/**
	 * 문자열 초과시 자르고, 미만시 지정문자로 채우기
	 * @param str, num, chr
	 * @param max
	 * @return 적용문자열
	 */
	public static String lPadCut(String str, int max, String chr) throws Exception
	{
		String ret="", tmp;
		if (str==null) str="";
		byte[] temp = str.getBytes();
		
		//널이면
		if (Utils.isNull(str)) {
			for (int i=0; i<max; i++) {
				ret += chr;
			}
			return ret;
		}

        for(int i=0, k=0; k < max; i++) {
            if( k > temp.length-1 ) {
                if( chr.getBytes().length > 1 ) {
                    k +=2;
                }  else {
                    k ++;
                }
            	ret += chr;
            	continue;
            }
            tmp = str.substring( i, i+1 );
            byte[] temp1 = tmp.getBytes();
            if( temp1.length > 1 ) {
                k +=2;
            }  else {
                k ++;
            }
        	ret += tmp;

        }
		
		return ret;
	} 
		
	/**
	 * 주민번호(13자리) => 현재나이
	 */
	public static int getAge(String juminno) throws Exception {
		
		int age=0, year=0;
		try {
			if ( ( juminno.substring(6,7).equals("1")) || (juminno.substring(6,7).equals("2")) ) {
				year = 1900;
			}
			else {
				year = 2000;
			}
			year += Integer.parseInt(juminno.substring(0,2));
			age = DateTime.getYear() - year;
		} catch (Exception e) {
			age = 0;
		}

		return age;
	}
	
	/**
	 * 문자열로 부터 주민번호 추출 (13자리 지정)
	 * @param juminno
	 * @return
	 */
	public static String getJumin(String juminno) {
		if (Utils.isNull(juminno)) return juminno;
		juminno = juminno.replaceAll("-","");
		if (juminno.length()>13) {
			juminno = juminno.substring(0, 12);
		} else {
			juminno = juminno.substring(0, juminno.length()-1);
		}
		return juminno;
	}
	
	/**
	 * \r\n 을 html의 <br>로 conver한다
	 */
	public static String convertHtmlBr(String comment) {
		//**********************************************************************
		if (comment == null)
			return "";
		int length = comment.length();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			String tmp = comment.substring(i, i + 1);
			if ("\r".compareTo(tmp) == 0) {
				tmp = comment.substring(++i, i + 1);
				if ("\n".compareTo(tmp) == 0)
					buffer.append("<br>\r");
				else
					buffer.append("\r");
			}
			buffer.append(tmp);
		}
		return buffer.toString();
	}
	
	/**
	 * \r\n 을 html의 <br>로, 빈칸을 &nbsp;로 conver한다
	 */
	public static String convertHtmlBrNbsp(String comment) {
		//**********************************************************************
		if (comment == null)
			return "";
		int length = comment.length();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			String tmp = comment.substring(i, i + 1);
			if ("\r".compareTo(tmp) == 0) {
				tmp = comment.substring(++i, i + 1);
				if ("\n".compareTo(tmp) == 0)
					buffer.append("<br>\r");
				else
					buffer.append("\r");
			} else if(" ".compareTo(tmp) == 0) {
				buffer.append("&nbsp;");
			}
			buffer.append(tmp);
		}
		return buffer.toString();
	}

	public static String ascToksc(String str)
		throws UnsupportedEncodingException {
		String result = str;
		if (result == null || result == "")
			return null;
		return new String(result.getBytes("8859_1"), "UTF-8");
	}

	public static String kscToasc(String str)
		throws Exception {
	if (str == null || str == "") return null;		
		
		//character의 코드를 바꾸어준다.
		String new_str = null;
		try{
			new_str = new String(str.getBytes("UTF-8"), "8859_1");
		} catch (UnsupportedEncodingException ex) 	{
			throw new Exception(ex.getMessage());
		}
		return new_str ;
	}	
	
 	public static String euc2utf_8(String str) 
    	throws Exception { 
        if(str == null || str.equals("")) return null; 
        
        String result = null;          
        try {            
            result = new String( str.getBytes("EUC-KR"), "UTF-8" ); 
        }catch(UnsupportedEncodingException ex ) {
        	throw new Exception(ex.getMessage());
        } 
        return result; 
    } 

	/**
	 * 파일의 확장자를 추출한다.
	 * 
	 * 예)
	 * [입력] "abcder.txt" ----> [출력] ".txt" 
	 * 
	 * @param str
	 * @return
	 */
	public static String getFileExtension( String str ) {
		return ( str.lastIndexOf( "." ) > 0 ) ? str.substring( str.lastIndexOf( "." ) ) : str;
	}
	
    /**
     * 문자열의 널값 검사를 한다.
     * <BR>문자열이 null 또는 white space인 경우에는 "참"을 반환한다.
     * 
     * @param str
     * @return  boolean
     */
    public static boolean isNull( String str ) {
        return ( str == null || "null".equals( str ) || "".equals( str ) ); 
    }
    
    /**
     * 문자열의 넙값 검사를 한다.
     * <BR>문자열이 null 또는 white space인 경우에는 "거짓"을 반환한다.
     * 
     * @param str
     * @return  boolean
     */
    public static boolean isNotNull( String str ) {
        return !( str == null || "".equals( str ) );
    }
    
    /**
     * 문자열이 null인 경우에는 whiteSpace를 반환한다.
     * 
     * @param string
     * @return
     */
    public static String nullToEmptyString( String string ) {
    	return isNull( ( string == null ) ? string : string.trim() ) ? "" : string;
    }
    
    /**
     * 요청한 페이지의 URL을 반환한다.
     * 
     * @param req
     * @return
     */
    public static String getURL( HttpServletRequest req ) {
        return req.getRequestURL().toString();
    }    

    /**
     * 문자열 해당코드로 변환한다..
     * 
     * @param str - String
     * @param encode - String
     * @param charsetName - String
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String encodeText( String str, String encode, String charsetName )  {
    	String result = null;
    	
    	try {
			result = isNull( str ) ? null : new String( str.getBytes( encode ), charsetName );
    	} catch ( UnsupportedEncodingException e ) {
    		e.printStackTrace();
    	}
    	
    	return result;
    }
    
    
    /**
     * 문자열을 euc-kr에서 8859_1로 디코딩 한다.
     * 
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decode( String str ) 
    {
        return encodeText( str, "euc-kr", "8859_1" ); 
    }

	/**
	 * Date 객체의 날짜를 pattern의 형태로 반환한다.
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
    public static String getPatternDate( Date date, String pattern ) {
    	return  new SimpleDateFormat( pattern ).format( date );
    }
    
    /**
     * 문자열을 8859_1에서 euc-kr로 인코딩 한다.
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */        
    public static String encode( String str ) 
    {
        return encodeText( str, "8859_1", "euc-kr" );
    }
	
    /**
     * 년도와 분기로 일자를 구한다.
     * @param str, str, str
     * @return
     * @throws UnsupportedEncodingException
     */   
    public static String getDate(String flag, String year, String quarter){
		String fromDt="";
		String toDt="";
		
		switch (Integer.parseInt(quarter)){
			case 1 :
				fromDt = "-01-01";
				toDt   = "-03-31";
				break;
			case 2 :
				fromDt = "-04-01";
				toDt   = "-06-30";
				break;
			case 3 :
				fromDt = "-07-01";
				toDt   = "-09-30";
				break;
			case 4 :
				fromDt = "-10-01";
				toDt   = "-12-31";
				break;			
		}
		
		if(flag.equals("from")){
			return year+fromDt;
		} else {
			return year+toDt;
		}	
	}
    
    //숫자만 추출
    public static String getNumeric(String value) {
    	String ret = "";
    	try {
    		for (int i=0; i<value.length(); i++) {
    			String num = String.valueOf(value.charAt(i));
    			if (isNumeric(num)) {
    				ret = ret + num;
    			}
    		}
    	} catch (Exception e) { 
    	} 	
		return ret;
    }
    
    //숫자인지 아닌지 체크
    public static boolean isNumeric(String value) 	{ 		
    	try	{ 			
    		Long.parseLong(value);
    		return true;
    	}	catch (Exception e) { 
    		return false;
    	}
    } 
    
    public static String toKorean (String str) 
		throws Exception{
		
		if (str == null)	return "";
		String new_str = null;
		 
		try {  
			new_str = new String (str.getBytes("8859_1"), "euc-kr");
			//인코딩이 UTF-8인 경우는
			// new_str = new String (str.getBytes("8859_1"), "UTF-8");
		} catch (UnsupportedEncodingException ex) 	{
			throw new Exception(ex.getMessage());
		}
		return new_str;
    }   

    //주민번호 유효성체크(회원가입 참고함)
    public static boolean validateJumin (String juminNo1,String juminNo2) {
		int M=0;
		int temp=0;
		
		if (juminNo1.length()!=6) return false; 
		if (juminNo2.length()!=7) return false; 
		
		try {
			int A = Integer.parseInt(juminNo1.substring(0,1).toString());
			int B = Integer.parseInt(juminNo1.substring(1,2).toString());
			int C = Integer.parseInt(juminNo1.substring(2,3).toString());
			int D = Integer.parseInt(juminNo1.substring(3,4).toString());
			int E = Integer.parseInt(juminNo1.substring(4,5).toString());
			int F = Integer.parseInt(juminNo1.substring(5,6).toString());
			int G = Integer.parseInt(juminNo2.substring(0,1).toString());
			int H = Integer.parseInt(juminNo2.substring(1,2).toString());
			int I = Integer.parseInt(juminNo2.substring(2,3).toString());
			int J = Integer.parseInt(juminNo2.substring(3,4).toString());
			int K = Integer.parseInt(juminNo2.substring(4,5).toString());
			int L = Integer.parseInt(juminNo2.substring(5,6).toString());
			M = Integer.parseInt(juminNo2.substring(6,7).toString());	
			
			temp = 11 - ((A*2)+(B*3)+(C*4)+(D*5)+(E*6)+(F*7)+(G*8)+(H*9)+(I*2)+(J*3)+(K*4)+(L*5))%11;
			
			if (M != (temp%10)){
				return false;
			} else { 
				return true; 
			}
		} catch (Exception e) {
			return false;
		}	
	}   
    
    //addtional code
	public static String E2K(String str) {
		try {
			if(str == null)
				return "";
			else
				return new String(str.getBytes("8859_1"), "KSC5601");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	public static String K2U(String str) {
		try {
			if(str == null)
				return "";
			else
				return new String(str.getBytes("KSC5601"), "UTF8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	public static String U2K(String str) {
		try {
			if(str == null)
				return "";
			else
				return new String(str.getBytes("UTF8"), "KSC5601");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	public static String U2E(String str) {
		try {
			if(str == null)
				return "";
			else
				return new String(str.getBytes("UTF8"), "8859_1");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	public static String E2U(String str) {
		try {
			if(str == null)
				return "";
			else
				return new String(str.getBytes("8859_1"), "UTF8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	public static String K2E(String korean) {
		String english = null;

		if (korean == null) return null;

		english = new String(korean);
		try {
			english = new String(korean.getBytes("KSC5601"), "8859_1");
		} catch (UnsupportedEncodingException e) {
			english = korean;
		}
		return english;
	}
	
	public static String to_date( String pattern ) {
		java.util.Date now = new java.util.Date();

		return  new SimpleDateFormat( pattern ).format(now);
	}
	
	public static String to_date( java.util.Date now , String pattern ) {
		return  new SimpleDateFormat( pattern ).format(now);
	}
	public static String to_date(Object now, String pattern) {
		java.util.Date newnow = (java.util.Date) now;
		return new SimpleDateFormat(pattern).format(newnow);
	}

	public static String[] split(String str, String delimiter) {
		StringTokenizer st = new StringTokenizer(str, delimiter);
		String [] split = new String[st.countTokens()];
		int i = 0;

		while (st.hasMoreTokens()) {
			split[i] = st.nextToken();
			i++;
		}
		return split;
	}

	public static void delDirectory(String path) {
		File file = new File(path);

		if(file.isDirectory()) {
			if(!file.delete()) {
				String filelist[] = file.list();

				for(int i = 0; i < filelist.length; i++) {
					File newfile = new File(file, filelist[i]);
					newfile.delete();
				}
				file.delete();
			}
		}
	}

	public static String today() {
		Date datetime = new java.util.Date();
		SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd");
		return formatTime.format(datetime);
	}

	public static String strTrans(String str, String strDefault) {
		if(str == null || str.length() == 0)
			return strDefault;
		else
			return str;
	}
	public static String strTrans(Object str, String strDefault) {
		if(str == null || str.toString().length()== 0)
			return strDefault;
		else
			return str.toString();
	}

	public static int intTrans(String str, int intDefault) {
		if(str == null || str.length() == 0)
			return intDefault;
		else
			return Integer.parseInt(str);
	}
	public static int intTest (Object str, int intDefault) {
		if(str == null || str.toString().length() == 0)
			return intDefault;
		else
			return Integer.parseInt(str.toString());
	}

	public static String nl2br(String str) {
		return replace(str, "\r\n", "<br>");
	}

	public static String notHtml(String str){
		int i, len=str.length();
		StringBuffer notHtml = new StringBuffer();
		char ch;

		for(i=0 ; i<len ; i++){
			ch = str.charAt(i);
			if(ch=='<')
				notHtml.append("&lt");
			else if(ch=='>')
				notHtml.append("&gt");
			else if(ch=='&')
				notHtml.append("&amp;");
			else if(ch=='%')    
				notHtml.append("&#37");
			else if(ch=='\n')   
				notHtml.append("<br>");
			else if(ch==' ')
				notHtml.append("&nbsp;");
			else
				notHtml.append(ch);
		}

		return notHtml.toString();
	}

	public static String cal( Calendar cal ) {
		return  cal.get(Calendar.YEAR) + "년 " +
		(cal.get(Calendar.MONTH)+1) + "월 " +
		cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) + "일 ";
	}

	public static String conv(String str,String chartype){
		String str2 = "";
		if ( str == null )
			return str2;
		if (chartype == null)
		{
			chartype = "8859_1";
		}
		try{
			str2 = new String(str.getBytes(chartype), "KSC5601");
		}catch (UnsupportedEncodingException e) {
			return null;
		}
		return str2;
	}

	public static void setMenuIndex(HttpServletRequest httpservletrequest, String strValue) {
		try
		{
			Utils.setAtt(httpservletrequest, "curMnu", (String)strValue);	 	
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
	}

	public static String getMenuIndex(HttpServletRequest httpservletrequest) {
		String strValue = "";
		try
		{
			strValue = (String)Utils.getAtt(httpservletrequest, "curMnu");			
		}
		catch (Exception ex)
		{
			System.out.println(ex);
			return "";
		}
		return strValue;		
	}

	public static void setAtt(HttpServletRequest httpservletrequest, String strKey, String strValue) {
		try
		{
			httpservletrequest.setAttribute(strKey, (String)strValue);	 	
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
	}

	public static String getAtt(HttpServletRequest httpservletrequest, String strKey) {
		String strValue = "";
		try
		{
			strValue = (String)httpservletrequest.getAttribute(strKey);
		}
		catch (Exception ex)
		{
			System.out.println(ex);
			return "";
		}
		return strValue;		
	}

	public static String requestParsing(HttpServletRequest httpservletrequest)
	{
		StringBuffer stringbuffer = new StringBuffer();
		for(Enumeration enumeration = httpservletrequest.getParameterNames(); enumeration.hasMoreElements();)
		{
			String s = (String)enumeration.nextElement();
			String as[] = httpservletrequest.getParameterValues(s);
			for(int i = 0; i < as.length; i++) {
				stringbuffer.append(s + "=");
				stringbuffer.append(isoToKSC(as[i]));
				if(i != as.length - 1) stringbuffer.append("&");
			}

			if(enumeration.hasMoreElements()) stringbuffer.append("&");
		}

		return stringbuffer.toString();
	}

	public static String isoToKSC(String s)
	{
		if(s == null)
			return "";
		try
		{
			char ac[] = (new String(s)).trim().toCharArray();
			boolean flag = false;
			for(int i = 0; i < ac.length; i++)
			{
				if(ac[i] < 0)
					ac[i] = (char)(256 + ac[i]);
				if(ac[i] >= '\241' && ac[i] <= '\376')
				{
					if(flag)
						return new String((new String(s)).trim().getBytes("8859_1"), "KSC5601");
					flag = true;
				} else
				{
					flag = false;
				}
			}

		}
		catch(UnsupportedEncodingException unsupportedencodingexception)
		{
			return "";
		}
		catch(NullPointerException nullpointerexception1)
		{
			return "";
		}
		try
		{
			return s;
		}
		catch(NullPointerException nullpointerexception)
		{
			return "";
		}
	}

	public static void fixNull(Object o) {
		if (o == null) return;

		Class c = o.getClass();
		if (c.isPrimitive()) return;

		Field[] fields = c.getFields();
		try {

			for (int i = 0 ; i < fields.length; i++) {

				Object f = fields[i].get(o);
				Class fc = fields[i].getType();

				if (fc.getName().equals("java.lang.String")) {
					if (f == null)
						fields[i].set(o, "");
					else
						fields[i].set(o, f);
				}
			}
		} catch (Exception e) {}
	}

	public static String makeCharstr(String s, int i)
	{
		StringBuffer stringbuffer = new StringBuffer();
		if(s == null)
			s = "";
		else
			s = s.trim();
		for(int j = i; j > 0; j--)
		{
			if(j > s.length())
			{
				stringbuffer.append("0");
				continue;
			}
			stringbuffer.append(s);
			break;
		}

		return stringbuffer.toString();
	}

	public static String getRandomCode(int i)
	{
		Integer integer = null;
		Random random = new Random();
		int j = 0;
		int k = 1;
		for(int l = 1; l < i; l++)
			k *= 10;

		for(int i1 = 0; i1 < i; i1++)
			j += 8 * (int)Math.pow(10D, i1);

		try
		{
			integer = new Integer(random.nextInt(j) + k);
		}
		catch(Exception exception)
		{
			return "0";
		}
		return integer.toString();
	}

	public static String getRandomKey(int av_cnt, int av_seed) {
		boolean bool=true;
		Integer nInteger=null;
		Random random=new Random();
		Vector v_vector=new Vector();
		try {  
			if (av_cnt > av_seed){
				StringBuffer lv_buff = new StringBuffer();
				for(int i=1;i<=av_seed;i++){
					if (i == av_seed)
						lv_buff.append(new Integer(i).toString());
					else
						lv_buff.append(new Integer(i).toString() + ",");

				}
				return lv_buff.toString();
			}

			for(int i=0;i<av_cnt;i++){
				while(bool){
					nInteger = new Integer(random.nextInt(av_seed)+1);
					if(v_vector.contains(nInteger)){
						nInteger=null;
					} else{
						bool=false;
					}
				}
				v_vector.addElement(nInteger);
				bool=true;
			}
		} catch (Exception ex) {
			return "";
		}  
		StringBuffer lv_buff = new StringBuffer();
		try {

			for(int i=0;i<v_vector.size();i++){
				if (i == v_vector.size() - 1)
					lv_buff.append(((Integer)v_vector.elementAt(i)).toString());
				else
					lv_buff.append(((Integer)v_vector.elementAt(i)).toString() + ",");
			}
		} catch (Exception ex) {
			return "";
		}
		return lv_buff.toString();
	}


	public static String cutString(String av_str, int av_cnt) {
		if(av_str == null) return "";
		if(av_str.length() <= av_cnt) return av_str;
		return av_str.substring(0, av_cnt) + "...";
	}

	public static String cutString(String av_str, int av_cnt, String av_default_str) {
		if (av_str.length() <= av_cnt) return av_str;
		return av_str.substring(0, av_cnt) + av_default_str;
	}

	public static String getFileSize(String file_url) {

		String strFileSize = null;

		try {

			File file = new File(file_url);
			
			double fileSize = (double)file.length();
			
			double dbyte = 1024;
			double kByteFileSize = fileSize / dbyte;
			String temp = String.valueOf(kByteFileSize);

			strFileSize = temp.substring(0,temp.indexOf(".")+3);

		} catch (Exception ex) {
			return "";
		}
		return strFileSize;
	}

	public static String oneLine(String title, int line_length ){

		if ( title.length() <= line_length )
			return title;

		int title_length = 0;   
		int html_length  = 0;   
		int start_offset = 0;   
		int end_offset   = 0;   

		char s_html = '<';
		char e_html = '>';

		int depth = 0;      

		for( ; ; ) {
			
			if( (start_offset = title.indexOf(s_html, end_offset)) >= 0 ) {
				title_length += start_offset - end_offset - 1;  

				
				if ( title_length > line_length )
					break;

				end_offset = title.indexOf(e_html, start_offset); 
				html_length += end_offset - start_offset + 1;  

				if ( title.charAt(start_offset+1) == '/' )
					depth--; 
				else
					depth++; 

			} else {
				start_offset = 0;
				title_length += title.length() - end_offset - 1;
				break;
			}
		}

		if ( title_length <= line_length ) {
			return title;
		} else {

			String ret_str = title.substring(0,line_length+html_length) + "..";
			
			for( int i = 0; i < depth; i++ ) {
				start_offset = title.indexOf(s_html, end_offset);
				end_offset = title.indexOf(e_html, start_offset);
				ret_str += title.substring(start_offset,end_offset+1);
			}
			return ret_str;
		}
	}
	
	public static ArrayList getYearList(int startYear, int endYear) {
		ArrayList yearList = new ArrayList();
				
		int addYear = startYear;
		
		yearList.add(new Integer(addYear));
		
		for (int i = 0 ; addYear != endYear; i++){
			if (startYear > endYear)
				addYear = addYear - 1;
			else if (startYear < endYear)
				addYear = addYear + 1;
			
			yearList.add(new Integer(addYear));
		}
		
		return yearList;
	}
	
	public static ArrayList getMonthList(){
		ArrayList monthList = new ArrayList();
		
		String month = "";
		
		for (int i = 1 ; i < 13; i++){
			
			month = String.valueOf(i);
			
			if (i < 10)
				month = "0" + month;
			
			monthList.add(month);
		}
		
		return monthList;
	}
	
	public static ArrayList getDayList(){
		ArrayList dayList = new ArrayList();
		
		String day = "";
		
		for (int i = 1 ; i < 32; i++){
			
			day = String.valueOf(i);
			
			if (i < 10)
				day = "0" + day;
			
			dayList.add(day);
		}
		
		return dayList;
	}
	
	public static String getYear(){
		String year = "";
		
		Calendar now = Calendar.getInstance();
		year = String.valueOf(now.get(Calendar.YEAR));
		
		return year;
	}
	
	public static String getMonth(){
		String month = "";
		
		Calendar now = Calendar.getInstance();
		int iMon = now.get(Calendar.MONTH) + 1;
		
		month = (iMon < 10)? "0" + iMon : "" + iMon;
		
		return month;
	}
	
	public static String getDay(){
		String day = "";
		
		Calendar now = Calendar.getInstance();
		int iDay = now.get(Calendar.DATE);
		
		day = (iDay < 10)? "0" + iDay : "" + iDay;
		
		return day;
	}
	
	public static String getHour(){
		String hour = "";
		
		Calendar now = Calendar.getInstance();
		int iHour = now.get(Calendar.HOUR_OF_DAY);
		
		hour = (iHour < 10)? "0" + iHour : "" + iHour;
		
		return hour;
	}

	public static String getMin(){
		String min = "";
		
		Calendar now = Calendar.getInstance();
		int iMin = now.get(Calendar.MINUTE);
		
		min = (iMin < 10)? "0" + iMin : "" + iMin;
		
		return min;
	}
	
	public static String getToday(String format){
		String today = "";
		
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.KOREA);
		today = sdf.format(new Date());
		
		return today;
	}
	
    public static Date check(String s, String format) throws java.text.ParseException {
        if ( s == null )
            throw new java.text.ParseException("date string to check is null", 0);
        if ( format == null )
            throw new java.text.ParseException("format string to check date is null", 0);

        SimpleDateFormat formatter = new SimpleDateFormat (format, Locale.KOREA);
        java.util.Date date = null;
        try {
            date = formatter.parse(s);
        }
        catch(java.text.ParseException e) {
            /*
            throw new java.text.ParseException(
                e.getMessage() + " with format \"" + format + "\"",
                e.getErrorOffset()
            );
            */
            throw new java.text.ParseException(" wrong date:\"" + s +
            "\" with format \"" + format + "\"", 0);
        }

        if ( ! formatter.format(date).equals(s) )
            throw new java.text.ParseException(
                "Out of bound date:\"" + s + "\" with format \"" + format + "\"",
                0
            );
        return date;
    }
	
    public static int daysBetween(String from, String to) throws java.text.ParseException {
        return daysBetween(from, to, "yyyyMMdd");
    }

    public static int daysBetween(String from, String to, String format) throws java.text.ParseException {

        java.util.Date d1 = check(from, format);
        java.util.Date d2 = check(to, format);

        long duration = d2.getTime() - d1.getTime();

        return (int)( duration/(1000 * 60 * 60 * 24) );
        // seconds in 1 day
    }
    
    public static String makeTime(String timeStr, int flag) {
		if(timeStr!=null&& timeStr.length()==12){
			String date = timeStr.substring(0,4)+"-"+timeStr.substring(4,6)+"-"+timeStr.substring(6,8);
			String timeH = timeStr.substring(8,10);
			if(timeH.substring(0,1).equals("0"))timeH = timeH.substring(1,2);			
			String timeS = timeStr.substring(10);
			if(flag==1)timeStr = date+" "+timeH+":"+timeS; //占쏙옙체占싹쏙옙
			if(flag==2)timeStr = date;//占쏙옙짜占쏙옙
			if(flag==3)timeStr = timeH;//占시몌옙
			if(flag==4)timeStr = timeS;//占싻몌옙
		}else{
			timeStr = "";
		}
		return timeStr;
	}
    
    public static String convertDate(String date, String format) {
    	String newDate = "";
    	
    	SimpleDateFormat formatter = new SimpleDateFormat (format, Locale.KOREA);
    	
    	try {
			newDate = formatter.format(formatter.parse(date));
		} catch (ParseException e) {
			try {
				newDate = formatter.format(check(date, format));
			} catch (ParseException e1) {
				newDate = date;
			}
		}
    	
    	return newDate;
    }
    
    public static String engToKor(String str) {
		try {
			return new String(str.getBytes("8859_1"),"EUC-KR");
		} catch (Exception e) {
		    return "";
		}
	}

	public static String korToEng(String str) {
		try {
			return new String(str.getBytes("EUC-KR"),"8859_1");
		} catch (Exception e) {
		    return "";
		}
	}

	public static String getDate(Date date, String format) {
	    if(date==null || format == null)return null;
	    SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

    public static String showHtml(String str) {
        if(str==null)
            return "";
        return replace( replace(str, "<", "&lt;"),
                     "\n", "<br>");
    }

	public static String showBrIgnore(String str) {
		return replace(str, "\r\n", "");
	}

    public static String showHtmlBr(String str) {
        if(str==null)
            return "";
        return replace( str, "\r\n", "<br>");
    }    

    public static String showHtmlEx(String str) {
        if(str==null)
            return "";
        return replace( replace(replace(replace(str, "&", "&amp;"), 
                "<", "&lt;"), 
                	">", "&gt;"),
                     "\n", "<br>");
    }    

    public static String makeHtmlTag(String str){
    	if (str == null)
    		return "";
    	
    	return replace(replace(replace(replace(replace(str, "&amp;", "&"), "&lt;", "<"), "&gt;", ">"), "&quot;", "\""), "&#34;", "'");
    }
    
    public static String showHtmlBrEx(String str) {
        if(str==null)
            return "";
        return replace( replace( str," ","&nbsp;"), "\n", "<br>");
    }        

    public static String showHtml_space(String str) {
        if(str==null)
            return "";
        return replace(replace( replace(str, "<", "&lt;"),
                     "\n", "<br>")," ","&nbsp;");
    }

	public static String nullCheck(String str) {
		return nullCheck(str, "");
	}

	public static String nullCheck(String str, String defaultStr) {
		return (str==null || str.trim().equals(""))?defaultStr:str;
	}


	public static int nullIntCheck(String Req,int ifNulltoReplace ) {
		try {
			return ( Req == null || Req.trim().equals("")) ? ifNulltoReplace : Integer.parseInt(Req.toString());
		}catch(NumberFormatException e){
			return ifNulltoReplace;
		}
	}
	
	public static int nullIntCheck(String Req ) {
		try {
			if (Req == null || Req.trim().equals("")) return 0;
			else               return Integer.parseInt(Req.toString());
		}catch(NumberFormatException e){
			return 0;
		}
	}	
	
    public static String replace(
    	String mainString,
    	String oldString,
    	String newString) {
    	if (mainString == null) {
    		return null;
    	}
    	if (oldString == null || oldString.length() == 0) {
    		return mainString;
    	}
    	if (newString == null) {
    		newString = "";
    	}

    	int i = mainString.lastIndexOf(oldString);
    	if (i < 0)
    		return mainString;

    	StringBuffer mainSb = new StringBuffer(mainString);

    	while (i >= 0) {
    		mainSb.replace(i, (i + oldString.length()), newString);
    		i = mainString.lastIndexOf(oldString, i - 1);
    	}
    	return mainSb.toString();
    }

	public static String crop(String str, int i) {
    	if (str==null) return "";
    	return (str.length()>i) ? str.substring(0,i)+".." : str;
    }

	public static String cropByte(String str, int i, String trail) {
    	if (str==null) return "";
    	String tmp = str;
    	int slen = 0, blen = 0;
    	char c;
    	try {
        	if(tmp.getBytes("MS949").length>i) {
        		while (blen+1 < i) {
        			c = tmp.charAt(slen);
        			blen++;
        			slen++;
        			if ( c  > 127 ) blen++;  //2-byte character..
        		}
        		tmp=tmp.substring(0,slen)+trail;
        	}
        } catch(java.io.UnsupportedEncodingException e) {}
    	return tmp;
    }

	public static void setCookie(HttpServletResponse response,
                                 String name,
                                 String value,
                                 int iMinute)
                                 throws java.io.UnsupportedEncodingException {
    	value = java.net.URLEncoder.encode(value, "EUC-KR");
    	Cookie cookie = new Cookie(name, value);
    	cookie.setMaxAge(60 * iMinute);
    	cookie.setPath("/");
    	response.addCookie(cookie);
    }

	public static String markKeyword(String str, String keyword) {
//		System.out.println("[***TL***] markKeyword() : " + keyword);
		keyword =
			replace(
				replace(replace(keyword, "[", "\\["), ")", "\\)"),
				"(", "\\(");

		Pattern p = Pattern.compile( keyword , Pattern.CASE_INSENSITIVE );
		Matcher m = p.matcher( str );
		int start = 0;
		int lastEnd = 0;
		StringBuffer sbuf = new StringBuffer();
		while( m.find() ) {
			start = m.start();
			sbuf.append( str.substring(lastEnd, start) )
				.append("<font color='blue'>"+m.group()+"</font>" );
			lastEnd = m.end();
		} 
		return sbuf.append(str.substring(lastEnd)).toString() ;
	}

	public static String getPercent(double a, double b, int digit) {
		if (Double.isNaN(a) || Double.isNaN(b)) return "";
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(digit);
		return nf.format(a/b);
	}

	public static String getPercent(int a, int b, int digit) {
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(digit);
		return nf.format(new Integer(a).doubleValue()/new Integer(b).doubleValue());		
	}

    public static String getBirth(String jumin) {
		int bYear = Integer.parseInt(jumin.substring(0,2)) + 1900 + ((Integer.parseInt(jumin.substring(7,8)) -1) / 2) * 100;
		return bYear + "년 " + Integer.parseInt(jumin.substring(2,4)) + "월 " + Integer.parseInt(jumin.substring(4,6)) + "일";
    }
	
    public static String getSex(String jumin) {
	  int sex = Integer.parseInt(jumin.substring(7,8));
	  if (sex % 2 == 1) {
		  return "남자";
	  }
	 return "여자";    	
    }

	public static String  replaceNR(String org){
		String str = "";
		String var1 = "\n";
		String var2 = "\r";
		String tgt1 = "<$!>";
		String tgt2 = "<&!>";
		int	end	= 0;
		int	begin =	0;
	
		while (true) {
			end	= org.indexOf(var1, begin);
			if (end	== -1) {
				end	= org.length();
				str	+= org.substring(begin,	end);
				break;
			}
			str	+= org.substring(begin,	end) + tgt1;
			begin =	end	+ var1.length();
		}
	
		org = str;
		str = "";
		end	= 0;
		begin =	0;
	
		while (true) {
			end	= org.indexOf(var2, begin);
			if (end	== -1) {
				end	= org.length();
				str	+= org.substring(begin,	end);
				break;
			}
			str	+= org.substring(begin,	end) + tgt2;
			begin =	end	+ var2.length();
		}
		return str;
	}
	
	public static int getDiffer(String startDate, String endDate){
		
		int deffer = 0;

		return deffer;
	}

    public static String replacePhoneNum(String phoneNumber) {
    	String replacePhoneNumber = nullCheck(phoneNumber, "");
    	replacePhoneNumber = replacePhoneNumber.trim();
    	replacePhoneNumber = replace(replacePhoneNumber, " ", "");
    	replacePhoneNumber = replace(replacePhoneNumber, "-", "");
    	replacePhoneNumber = replace(replacePhoneNumber, "(", "");
    	replacePhoneNumber = replace(replacePhoneNumber, ")", "");
        return replacePhoneNumber;
    }


    public static String [] resetPhoneNum(String phoneNumber, String stx) {
    	String str = replacePhoneNum(phoneNumber);
    	StringBuffer sb = new StringBuffer();
    	String resetPhoneNumber [] = new String[3];

    	if (str.length() > 6 && str.length() < 9 ) {
    		sb.append(stx);
    		sb.append(str);
    	} else if (str.length() > 8) {
    		sb.append(str);
    	} else {
    		return null;
    	}

    	if (stx.equals("02")) {
    		if (sb.toString().length() == 9) { // 020001111
    			resetPhoneNumber [0] = sb.toString().substring(0,2);
    			resetPhoneNumber [1] = sb.toString().substring(2,5);
    			resetPhoneNumber [2] = sb.toString().substring(5);
    		} else if (sb.toString().length() == 10) { // 0200001111
    			resetPhoneNumber [0] = sb.toString().substring(0,2);
    			resetPhoneNumber [1] = sb.toString().substring(2,6);
    			resetPhoneNumber [2] = sb.toString().substring(6);
    		}
    	} else {
    		if (sb.toString().length() == 10) { // 0310001111
    			resetPhoneNumber [0] = sb.toString().substring(0,3);
    			resetPhoneNumber [1] = sb.toString().substring(3,6);
    			resetPhoneNumber [2] = sb.toString().substring(6);
    		} else if (sb.toString().length() == 11) { // 03100001111
    			resetPhoneNumber [0] = sb.toString().substring(0,3);
    			resetPhoneNumber [1] = sb.toString().substring(3,7);
    			resetPhoneNumber [2] = sb.toString().substring(7);
    		}
    	}
    	return resetPhoneNumber;
    }
    
    public static String numToMoney(int intVal){
    	DecimalFormat df = new DecimalFormat("###,###");
    	return df.format(intVal);
    }
    
    public static String numToMoney(long longVal){
    	DecimalFormat df = new DecimalFormat("###,###");
    	return df.format(longVal);
    }
    
    public static String numToMoney(float floatVal){
    	DecimalFormat df = new DecimalFormat("###,###.##");
    	return df.format(floatVal);
    }
    
    public static String numToMoney(double doubleVal){
    	DecimalFormat df = new DecimalFormat("###,###.##");
    	return df.format(doubleVal);
    }

    
	//문자열 채우기
	public static String fillCh(String p_ch, String ln){		
		String result = "";
		
		for(int i=0;i < Integer.parseInt(ln) + (Integer.parseInt(ln) - 2); i++){
			result = result + p_ch;
		}			
		
		return result;	
	}
}