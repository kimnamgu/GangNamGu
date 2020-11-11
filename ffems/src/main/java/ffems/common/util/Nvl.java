package ffems.common.util;

import ffems.common.util.CommonLib;

public class Nvl {
	public static String nvlStr(Object str) {
		if(str == null) { return ""; }
		else { return nvlStr(str.toString()); }
	}
	
    //���������? ����   
    public static String nvlStr(String str) {
    	if(CommonLib.isBlank(str)) return "";  
    	else return str;
    }
    
    //str�� null�϶� defaultStr�� �ٲ��� 
    public static String nvlStr(String str, String defaultStr) {
    	return CommonLib.defaultIfEmpty(str, defaultStr);
    }
    
    //str�迭�� ���� ���������? ����
    public static String[] nvlStr(String[] str) {
    	if(str == null) {
    		str = new String[0];
    		return str; 
    	}
        for(int i = 0; i < str.length; i++) {
            str[i] = nvlStr(str[i]);
        }
        return str;
    }
    
    public static int nvlInt(Object str) {
    	if(str == null)  return 0; 
    	else  return nvlInt(str.toString());
    }
    
    //str�� int������ ��ȯ�� ����(str�� null�̸� 0����)
    public static int nvlInt(String str) {
        if(CommonLib.isBlank(str)) return 0;
        else return Integer.parseInt(str);
    }
    //str�� int������ ��ȯ�� ����(str�� null�̸� defaultInt����)
    public static int nvlInt(String str, int defaultInt) {
        try {
            if(CommonLib.isBlank(str)) return defaultInt;
            else return Integer.parseInt(str);
        } catch(NumberFormatException e) {
            return defaultInt;
        }
    }
    //str�迭�� int������ ���� ��ȯ�� ����
    public static int[] nvlInt(String[] str) {
        int[] returnInt = new int[str.length];
        for(int i = 0; i < str.length; i++) {
            returnInt[i] = nvlInt(str[i]);
        }
        return returnInt;
    }
    
    public static long nvlLong(Object str) {
    	if(str == null) return 0;
    	else return nvlLong(str.toString());
    }
    
    public static long nvlLong(String str) {
        if(CommonLib.isBlank(str)) return 0;
        else return Long.parseLong(str);
    }

    public static long nvlLong(String str, long defaultLong) {
        try {
            if(CommonLib.isBlank(str)) return defaultLong;
            else return Long.parseLong(str);
        } catch(NumberFormatException e) {
            return defaultLong;
        }
    }
    
    public static float nvlFloat(String str) {
    	if(CommonLib.isBlank(str)) return 0;
        else return Float.parseFloat(str);
    }
    
    public static double nvlDouble(String str) {
    	if(CommonLib.isBlank(str)) return 0;
        else return Double.parseDouble(str);
    }
    
    public static long[] nvlLong(String[] str) {
        long[] returnLong = new long[str.length];
        for(int i = 0; i < str.length; i++) {
            returnLong[i] = nvlInt(str[i]);
        }
        return returnLong;
    }
    
    public static String nvlStr2(Object str)
    {
        if(str == null)
            return "";
        else
            return nvlStr2(str.toString());
    }

    public static String nvlStr3(Object str)
    {
        if(str == null)
            return "";
        else
            return nvlStr3(str.toString());
    }

    public static String nvlStr2(String str)
    {
        if(CommonLib.isBlank(str))
        {
            return "";
        } else
        {
            str = str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
            str = str.replaceAll("eval\\((.*)\\)", "");
            str = str.replaceAll("[\\\"\\'][\\s]*javascript:(.*)[\\\"\\']", "\"\"");
            str = str.replaceAll("script", "");
            str = str.replaceAll("'", "\"");
            str = str.replaceAll("\"", "\\\"");
            return str;
        }
    }

    public static String nvlStr2(String str, String defaultStr)
    {
        if(CommonLib.isBlank(str))
            str = "";
        str = str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        str = str.replaceAll("eval\\((.*)\\)", "");
        str = str.replaceAll("[\\\"\\'][\\s]*javascript:(.*)[\\\"\\']", "\"\"");
        str = str.replaceAll("script", "");
        str = str.replaceAll("'", "\"");
        str = str.replaceAll("\"", "\\\"");
        return CommonLib.defaultIfEmpty(str, defaultStr);
    }

    public static String nvlStr3(String str, String defaultStr)
    {
        if(CommonLib.isBlank(str))
            str = "";
        str = str.replaceAll("eval\\((.*)\\)", "");
        str = str.replaceAll("[\\\"\\'][\\s]*javascript:(.*)[\\\"\\']", "\"\"");
        str = str.replaceAll("script", "");
        return CommonLib.defaultIfEmpty(str, defaultStr);
    }
    
    public static String nvlStrFilter(String str, String defaultStr)
    {
        if(CommonLib.isBlank(str))
            str = "";
        str = str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        str = str.replaceAll("eval\\((.*)\\)", "");
        str = str.replaceAll("[\\\"\\'][\\s]*javascript:(.*)[\\\"\\']", "\"\"");
        while (str.indexOf("script") > -1) {
            str = str.replaceAll("script", "");
        }
        return CommonLib.defaultIfEmpty(str, defaultStr);
    }
}
