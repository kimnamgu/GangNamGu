package nexti.ejms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * DateTime Class ��뿹
 *
 *
 * <table>
 * <tr><td>������(yyyy/MM/dd HH:mm:ss)</td><td>DateTime.getCurrentTime()</td></tr>
 * <tr><td>������(yyyy-MM-dd)</td><td>DateTime.getCurrentDate()</td></tr>
 * <tr><td>���ش�</td><td>DateTime.getYear()</td></tr>
 * <tr><td>�̹�����		:DateTime.getMonth()</td></tr>
 * <tr><td>������			:DateTime.getDay()</td></tr>
 * <tr><td>������			:DateTime.getDayOfWeek()</td></tr>
 * <tr><td>����ð�		:DateTime.getHour()</td></tr>
 * <tr><td>�����			:DateTime.getMinute()</td></tr>
 * <tr><td>���� ��			:DateTime.getSecond()</td></tr>
 * <tr><td>���� ��¥		:DateTime.getDateString()</td></tr>
 * <tr><td>���� ��¥		:DateTime.getDateString(".")</td></tr>
 * <tr><td>���� ��¥		:DateTime.getDateString("/")</td></tr>
 * <tr><td>�ð���Ʈ��		:DateTime.getTimeString()</td></tr>
 * <tr><td>TimeStamp		:DateTime.getTimeStampString()</td></tr>
 * <tr><td>DateTimeMin 	:DateTime.getDateTimeMin()</td></tr>
 * <tr><td>DateTimeString 	:DateTime.getDateTimeString()</td></tr>
 * <tr><td>�����Ǵ�		:DateTime.checkEmbolism(2002)</td></tr>
 * <tr><td>�ϼ� ���ϱ�		:DateTime.getMonthDate("2002","2")</td></tr>
 * daysBetween    ��¥�� �ϼ����ϱ�
 * addDays        �ϼ� ���ϱ�
 * whichDay       ���ϱ��ϱ�
 * monthsBetween  ��¥�� �������ϱ�
 * addMonths      ���� ���ϱ�
 * lastDayOfMonth �� ���� ��������¥ ���ϱ�
 * addYears       ��� ���ϱ�
 * </table>
 */

public class DateTime {

	public static String dateSep = "-";
	public static String timeSep = ":";
	public static String dateSep_1 = ".";

	private static final String[] day = {"��", "��", "ȭ", "��", "��", "��", "��" };

	public DateTime() {}
	
	/**
	 * ����ð� ���ϱ�
	 */
    public static String getCurrentTime() {
        String time = "";
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        time = df.format(cal.getTime());   
        return time;
    }    

	/**
	 * ���糯¥ ���ϱ�
	 */
    public static String getCurrentDate() {
        String time = "";
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        time = df.format(cal.getTime());   
        return time;
    }
    
	/**
	 * ���糯�� ���ϳ�¥ ���ϱ�
	 */
    public static String getCurrentNextDate() throws java.text.ParseException {
    	return addDays(getCurrentDate(), 1, "yyyy-MM-dd");
    }
    
	/**
	 * ���糯�� YYYY-MM-DD HH24:MI:SS
	 */
    public static String getCurrentDateTime() {
    	return getCurrentDate() + " " + getTimeString();
    }
    
   	/**
     *   ����⵵���� DAY ��¥ ��ŭ ���Ͽ� ������ 1�� 1���� �����´�.     
     */	
    public static String getYearFirstDay(int days) throws java.text.ParseException {
    	String time = "";
    	time = addDays(getCurrentDate(),days,"yyyy-MM-dd");
    	time = time.substring(0,4)+"-01-01";
    	return time;
    }
    
	/*
	 * date�� format�� ��ȯ
	 */
    public static String chDateFormat(Date dt, String format) {
    	String dateString = "";
    	if (dt!=null && "0000-00-00".equals(dt)==false)
    	{
	    	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);
	    	dateString = formatter.format(dt);
    	}
		return dateString;
     }
    
    /**
	 *
	 * For example, String time = DateTime.getFormatString("yyyy-MM-dd HH:mm:ss");
	 *
	 * @param java.lang.String pattern  "yyyy, MM, dd, HH, mm, ss and more"
	 * @return formatted string representation of current day and time with  your pattern.
	 */
	public static int getNumberByPattern(String pattern) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat (pattern, java.util.Locale.KOREA);
		String dateString = formatter.format(new java.util.Date());
		return Integer.parseInt(dateString);
	}

    /**
    * "2006.08.24" Ÿ���� ��Ʈ���� ���ϴ� Method
    *
    * @param
    * @exception
    * @author
    */
  	public static String getDateString(Date dt) {
  		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
	    return df.format(dt);
    }

    /**
     * "2006.08.24 08:11" Ÿ���� ��Ʈ���� ���ϴ� Method
     *
     * @param
     * @exception
     * @author
     */
   	public static String getDateTimeString(Date dt) {
   		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm");
 	    return df.format(dt);
     }
  	
    /**
    * ���糯¥�� �⵵�� ���ϴ� Method
    *
    * @param
    * @exception
    * @author
    */
	public static int getYear() { return getNumberByPattern("yyyy"); }

	/**
    * ���糯¥�� ���� ���ϴ� Method
    *
    * @param
    * @exception
    * @author
    */
	public static int getMonth() { return getNumberByPattern("MM"); }

    /**
    * ���糯¥�� ���ڸ� ���ϴ� Method
    *
    * @param
    * @exception
    * @author
    */
    public static int getDay() { return getNumberByPattern("dd"); }

    /**
    * ���� ������ ���ϴ� Method
    *
    * @param
    * @exception
    * @author
    */
    public static String getDayOfWeek() {
	    Calendar c = Calendar.getInstance();
	    return day[c.get(java.util.Calendar.DAY_OF_WEEK)-1];
	}

    /**
    * ���� �ð��� �ð��� ���ϴ� Method
    *
    * @param
    * @exception
    * @author
    */
    public static int getHour() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.HOUR_OF_DAY);
	}

    /**
    * ���� �ð��� ���� ���ϴ� Method
    *
    * @param
    * @exception
    * @author
    */
    public static int getMinute() {
	    Calendar c = Calendar.getInstance();
	    return c.get(Calendar.MINUTE);
	}

    /**
    * ����ð��� ���� ���ϴ� Method
    *
    * @param
    * @exception
    * @author
    */
    public static int getSecond() {
	    Calendar c = Calendar.getInstance();
	    return c.get(Calendar.SECOND);
	}

    /**
    * YYY-MM-DD ������ ��Ʈ���� ���ϴ� Method
    * @param pattern String
    * @exception
    * @author
    */
    public static String getDateString() {
    		return getYear() + "-" + getMonth() + "-" + getDay();
	}

	/**
    * YYY-MM-DD ������ ��Ʈ���� Pattern�� ���� ���ϴ� Method
    * Pattern ���� ���� ��� �ݿ�
    * @param pattern String
    * @exception
    * @author
    */
    public static String getDateString(String pattern) {
    		return getYear() + pattern + getMonth() + pattern + getDay();
	}

     /**
    * HH:MI:SS ������ ��Ʈ���� ���ϴ� Method
    *
    * @param
    * @exception
    * @author
    */
  	public static String getTimeString() {
	  	String hour = getHour() + "";
	    String min = getMinute() + "";
	    String sec = getSecond() + "";

	    if(hour.length() == 1) {
	      hour = "0" + hour;
	    }
	    if(min.length() == 1) {
	      min = "0" + min;
	    }
	    if(sec.length() == 1) {
	      sec = "0" + sec;
	    }

	    return hour + timeSep + min + timeSep + sec;


  	}

    /**
    * YYYY-MM-DD HH:MI:SS ������ ��Ʈ���� ���ϴ� Method
    *
    * @param
    * @exception
    * @author
    */
  	public static String getTimeStampString() {
    	return getDateString("-") + " " + getTimeString();
    }

    /**
    * YYYYMMDDHHMI ������ ��Ʈ���� ���ϴ� Method
    *
    * @param
    * @exception
    * @author
    */
  	public static String getDateTimeMin() {
	    String month = getMonth() + "";
	    String day = getDay() + "";
	    String hour = getHour() + "";
	    String min = getMinute() + "";

	    if(month.length() == 1) {
	      month = "0" + month;
	    }
	    if(day.length() == 1) {
	      day = "0" + day;
	    }
	    if(hour.length() == 1) {
	      hour = "0" + hour;
	    }
	    if(min.length() == 1) {
	      min = "0" + min;
	    }
	    return getYear()+""+month+""+day+""+hour+""+min;
	}

    /**
    * ��/��/��/��/��/�� ��Ʈ���� ���ϴ� Method
    *
    * @param
    * @exception
    * @author
    */
  	public static String getDateTimeString() {
	    String month = getMonth() + "";
	    String day = getDay() + "";
	    String hour = getHour() + "";
	    String min = getMinute() + "";
	    String sec = getSecond() + "";

	    if(month.length() == 1) {
	      month = "0" + month;
	    }
	    if(day.length() == 1) {
	      day = "0" + day;
	    }
	    if(hour.length() == 1) {
	      hour = "0" + hour;
	    }
	    if(min.length() == 1) {
	      min = "0" + min;
	    }
	    if(sec.length() == 1) {
	      sec = "0" + sec;
	    }
	    return getYear()+""+month+""+day+""+hour+""+min + sec;
    }

	public static String getParseDateString(String dateTime, String pattern){
		if ( dateTime != null ){
			String year = dateTime.substring(0, 4);
			String month = dateTime.substring(4, 6);
			String day = dateTime.substring(6, 8);		
		
			return year + pattern + month + pattern + day;				
		} else {
			return "";	
		}	
	}


  	/**
   	* �־��� �⵵�� ���������� ���ϴ� Method
   	*
   	* @param int year
   	* @exception
   	* @author
   	*/
  	public static boolean checkEmbolism(int year) {
	    int remain = 0;
	    int remain_1 = 0;
	    int remain_2 = 0;

	    remain = year % 4;
	    remain_1 = year % 100;
	    remain_2 = year % 400;

	    // the ramain is 0 when year is divided by 4;
	    if (remain == 0) {
	      // the ramain is 0 when year is divided by 100;
	      if (remain_1 == 0) {
	        // the remain is 0 when year is divided by 400;
	        if (remain_2 == 0) return true;
	        else return false;
	      } else  return true;
	    }
	    return false;
	 }
  	
	   /**
	   * �־��� ��,���� �ϼ��� ���ϴ� Method
	   *
	   * @param    String year, String month
	   * @exception
	   * @author
	   */
  		public static int getMonthDate(String year, String month) {
	     int [] dateMonth = new int[12];
	
	     dateMonth[0] = 31;
	     dateMonth[1] = 28;
	     dateMonth[2] = 31;
	     dateMonth[3] = 30;
	     dateMonth[4] = 31;
	     dateMonth[5] = 30;
	     dateMonth[6] = 31;
	     dateMonth[7] = 31;
	     dateMonth[8] = 30;
	     dateMonth[9] = 31;
	     dateMonth[10] = 30;
	     dateMonth[11] = 31;
	
	     if (checkEmbolism(Integer.parseInt(year))) { dateMonth[1] = 29; }
	
	     int day = dateMonth[Integer.parseInt(month) - 1];
	
	     return day;
	  }

	  public static Date getDate(String s) throws ParseException {
		  return getDate(s, "yyyyMMdd");
		 }

		 /**
		  * check date string validation with an user defined format.
		  * @param s date string you want to check.
		  * @param format string representation of the date format. For example, "yyyy-MM-dd".
		     * @return date java.util.Date
		  */
		 public static Date getDate(String s, String format) throws ParseException {
		  if ( s == null )
		   throw new ParseException("date string to check is null", 0);
		  if ( format == null )
		   throw new ParseException("format string to check date is null", 0);

		  java.text.SimpleDateFormat formatter =
		            new SimpleDateFormat (format, java.util.Locale.KOREA);
		  java.util.Date date = null;
		  try {
		   date = formatter.parse(s);
		  }
		  catch(ParseException e) {
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

		 /**
		  * check date string validation with the default format "yyyyMMdd".
		  * @param s date string you want to check with default format "yyyyMMdd"
		  * @return boolean true ��¥ ������ �°�, �����ϴ� ��¥�� ��
		  *                 false ��¥ ������ ���� �ʰų�, �������� �ʴ� ��¥�� ��
		  */
		 public static boolean isValid(String s) throws Exception {
		  return DateTime.isValid(s, "yyyyMMdd");
		 }

		 /**
		  * check date string validation with an user defined format.
		  * @param s date string you want to check.
		  * @param format string representation of the date format. For example, "yyyy-MM-dd".
		  * @return boolean true ��¥ ������ �°�, �����ϴ� ��¥�� ��
		  *                 false ��¥ ������ ���� �ʰų�, �������� �ʴ� ��¥�� ��
		  */
		 public static boolean isValid(String s, String format) {
		/*
		  if ( s == null )
		   throw new NullPointerException("date string to check is null");
		  if ( format == null )
		   throw new NullPointerException("format string to check date is null");
		*/
		  java.text.SimpleDateFormat formatter =
		      new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);
		  java.util.Date date = null;
		  try {
		   date = formatter.parse(s);
		  }
		  catch(java.text.ParseException e) {
		            return false;
		  }

		  if ( ! formatter.format(date).equals(s) )
		            return false;

		        return true;
		 }

		 /**
		  *
		  * For example, String time = DateTime.getFormatString("yyyy-MM-dd HH:mm:ss");
		  *
		  * @param java.lang.String pattern  "yyyy, MM, dd, HH, mm, ss and more"
		  * @return formatted string representation of current day and time with  your pattern.
		  */
		 public static String getFormatString(String pattern) {
		  java.text.SimpleDateFormat formatter =
		            new java.text.SimpleDateFormat (pattern, java.util.Locale.KOREA);
		  String dateString = formatter.format(new java.util.Date());
		  return dateString;
		 }

		 /**
		  * @return formatted string representation of current day with  "yyyyMMdd".
		  */
		 public static String getShortDateString() {
		  java.text.SimpleDateFormat formatter =
		            new java.text.SimpleDateFormat ("yyyyMMdd", java.util.Locale.KOREA);
		  return formatter.format(new java.util.Date());
		 }

		 /**
		  * @return formatted string representation of current time with  "HHmmss".
		  */
		 public static String getShortTimeString() {
		  java.text.SimpleDateFormat formatter =
		            new java.text.SimpleDateFormat ("HHmmss", java.util.Locale.KOREA);
		  return formatter.format(new java.util.Date());
		 }

		 /**
		  * return days between two date strings with default defined format.(yyyyMMdd)
		  * @param s date string you want to check.
		  * @return int ��¥ ������ �°�, �����ϴ� ��¥�� �� ������ ����
		  *           ������ �߸� �Ǿ��ų� �������� �ʴ� ��¥: java.text.ParseException �߻�
		  *          0: �Ͽ��� (java.util.Calendar.SUNDAY �� ��)
		  *          1: ������ (java.util.Calendar.MONDAY �� ��)
		  *          2: ȭ���� (java.util.Calendar.TUESDAY �� ��)
		  *          3: ������ (java.util.Calendar.WENDESDAY �� ��)
		  *          4: ����� (java.util.Calendar.THURSDAY �� ��)
		  *          5: �ݿ��� (java.util.Calendar.FRIDAY �� ��)
		  *          6: ����� (java.util.Calendar.SATURDAY �� ��)
		  * ��) String s = "20000529";
		  *  int dayOfWeek = whichDay(s, format);
		  *  if (dayOfWeek == java.util.Calendar.MONDAY)
		  *      System.out.println(" ������: " + dayOfWeek);
		  *  if (dayOfWeek == java.util.Calendar.TUESDAY)
		  *      System.out.println(" ȭ����: " + dayOfWeek);
		  */
		    public static int whichDay(String s) throws java.text.ParseException {
		        return whichDay(s, "yyyyMMdd");
		    }

		 /**
		  * return days between two date strings with user defined format.
		  * @param s date string you want to check.
		  * @param format string representation of the date format. For example, "yyyy-MM-dd".
		  * @return int ��¥ ������ �°�, �����ϴ� ��¥�� �� ������ ����
		  *           ������ �߸� �Ǿ��ų� �������� �ʴ� ��¥: java.text.ParseException �߻�
		  *          0: �Ͽ��� (java.util.Calendar.SUNDAY �� ��)
		  *          1: ������ (java.util.Calendar.MONDAY �� ��)
		  *          2: ȭ���� (java.util.Calendar.TUESDAY �� ��)
		  *          3: ������ (java.util.Calendar.WENDESDAY �� ��)
		  *          4: ����� (java.util.Calendar.THURSDAY �� ��)
		  *          5: �ݿ��� (java.util.Calendar.FRIDAY �� ��)
		  *          6: ����� (java.util.Calendar.SATURDAY �� ��)
		  * ��) String s = "2000-05-29";
		  *  int dayOfWeek = whichDay(s, "yyyy-MM-dd");
		  *  if (dayOfWeek == java.util.Calendar.MONDAY)
		  *      System.out.println(" ������: " + dayOfWeek);
		  *  if (dayOfWeek == java.util.Calendar.TUESDAY)
		  *      System.out.println(" ȭ����: " + dayOfWeek);
		  */

		    public static int whichDay(String s, String format) throws java.text.ParseException {
		   java.text.SimpleDateFormat formatter =
		      new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);
		  java.util.Date date = getDate(s, format);

		        java.util.Calendar calendar = formatter.getCalendar();
		  calendar.setTime(date);
		        return calendar.get(java.util.Calendar.DAY_OF_WEEK);
		    }
 
		 /**
		  * return days between two date strings with default defined format.("yyyyMMdd")
		  * @param String from date string
		  * @param String to date string
		  * @return int ��¥ ������ �°�, �����ϴ� ��¥�� �� 2�� ���� ������ ���� ����
		  *           ������ �߸� �Ǿ��ų� �������� �ʴ� ��¥: java.text.ParseException �߻�
		  */
		    public static int daysBetween(String from, String to) throws java.text.ParseException {
		        return daysBetween(from, to, "yyyyMMdd");
		    }

		 /**
		  * return days between two date strings with user defined format.
		  * @param String from date string
		  * @param String to date string
		  * @return int ��¥ ������ �°�, �����ϴ� ��¥�� �� 2�� ���� ������ ���� ����
		  *           ������ �߸� �Ǿ��ų� �������� �ʴ� ��¥: java.text.ParseException �߻�
		  */
		    public static int daysBetween(String from, String to, String format) throws java.text.ParseException {
		        //java.text.SimpleDateFormat formatter =
		        //new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);
		        java.util.Date d1 = getDate(from, format);
		        java.util.Date d2 = getDate(to, format);

		        long duration = d2.getTime() - d1.getTime();

		        return (int)( duration/(1000 * 60 * 60 * 24) );
		        // seconds in 1 day
		    }

		 /**
		  * return age between two date strings with default defined format.("yyyyMMdd")
		  * @param String from date string
		  * @param String to date string
		  * @return int ��¥ ������ �°�, �����ϴ� ��¥�� �� 2�� ���� ������ ���� ����
		  *           ������ �߸� �Ǿ��ų� �������� �ʴ� ��¥: java.text.ParseException �߻�
		  */
		    public static int ageBetween(String from, String to) throws java.text.ParseException {
		        return ageBetween(from, to, "yyyyMMdd");
		    }

		 /**
		  * return age between two date strings with user defined format.
		  * @param String from date string
		  * @param String to date string
		  * @param format string representation of the date format. For example, "yyyy-MM-dd".
		  * @return int ��¥ ������ �°�, �����ϴ� ��¥�� �� 2�� ���� ������ ���� ����
		  *           ������ �߸� �Ǿ��ų� �������� �ʴ� ��¥: java.text.ParseException �߻�
		  */
		    public static int ageBetween(String from, String to, String format) throws java.text.ParseException {
		        return (int)(daysBetween(from, to, format) / 365 );
		    }

	  /**
	   * return add day to date strings
	   * @param String date string
	   * @param int ���� �ϼ�
	   * @return int ��¥ ������ �°�, �����ϴ� ��¥�� �� �ϼ� ���ϱ�
	   *           ������ �߸� �Ǿ��ų� �������� �ʴ� ��¥: java.text.ParseException �߻�
	   */
	     public static String addDays(String s, int day) throws java.text.ParseException {
	         return addDays(s, day, "yyyyMMdd");
	     }

	  /**
	   * return add day to date strings with user defined format.
	   * @param String date string
	   * @param int ���� �ϼ�
	   * @param format string representation of the date format. For example, "yyyy-MM-dd".
	   * @return int ��¥ ������ �°�, �����ϴ� ��¥�� �� �ϼ� ���ϱ�
	   *           ������ �߸� �Ǿ��ų� �������� �ʴ� ��¥: java.text.ParseException �߻�
	   */
	     public static String addDays(String s, int day, String format) throws java.text.ParseException{
	    java.text.SimpleDateFormat formatter =
	       new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);
	   java.util.Date date = getDate(s, format);

	         date.setTime(date.getTime() + ((long)day * 1000 * 60 * 60 * 24));
	         return formatter.format(date);
	     }

	  /**
	   * return add day to date strings with user defined format.
	   * @param date
	   * @param int ���� �ϼ�
	   * @return int ��¥ ������ �°�, �����ϴ� ��¥�� �� �ϼ� ���ϱ�
	   *           ������ �߸� �Ǿ��ų� �������� �ʴ� ��¥: java.text.ParseException �߻�
	   */
	     public static Date addDays(Date s, int day) throws Exception {
	         s.setTime(s.getTime() + ((long)day * 1000 * 60 * 60 * 24));
	         return s;
	     }
	     
	  /**
	   * return add month to date strings
	   * @param String date string
	   * @param int ���� ����
	   * @return int ��¥ ������ �°�, �����ϴ� ��¥�� �� ���� ���ϱ�
	   *           ������ �߸� �Ǿ��ų� �������� �ʴ� ��¥: java.text.ParseException �߻�
	   */
	     public static String addMonths(String s, int month) throws Exception {
	         return addMonths(s, month, "yyyyMMdd");
	     }

	  /**
	   * return add month to date strings with user defined format.
	   * @param String date string
	   * @param int ���� ����
	   * @param format string representation of the date format. For example, "yyyy-MM-dd".
	   * @return int ��¥ ������ �°�, �����ϴ� ��¥�� �� ���� ���ϱ�
	   *           ������ �߸� �Ǿ��ų� �������� �ʴ� ��¥: java.text.ParseException �߻�
	   */
	     public static String addMonths(String s, int addMonth, String format) throws Exception {
	    java.text.SimpleDateFormat formatter =
	       new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);
	   java.util.Date date = getDate(s, format);

	    java.text.SimpleDateFormat yearFormat =
	       new java.text.SimpleDateFormat("yyyy", java.util.Locale.KOREA);
	    java.text.SimpleDateFormat monthFormat =
	       new java.text.SimpleDateFormat("MM", java.util.Locale.KOREA);
	    java.text.SimpleDateFormat dayFormat =
	       new java.text.SimpleDateFormat("dd", java.util.Locale.KOREA);
	         int year = Integer.parseInt(yearFormat.format(date));
	         int month = Integer.parseInt(monthFormat.format(date));
	         int day = Integer.parseInt(dayFormat.format(date));

	         month += addMonth;
	         if (addMonth > 0) {
	             while (month > 12) {
	                 month -= 12;
	                 year += 1;
	             }
	         } else {
	             while (month <= 0) {
	                 month += 12;
	                 year -= 1;
	             }
	         }
	    java.text.DecimalFormat fourDf = new java.text.DecimalFormat("0000");
	    java.text.DecimalFormat twoDf = new java.text.DecimalFormat("00");
	         String tempDate = String.valueOf(fourDf.format(year))
	                          + String.valueOf(twoDf.format(month))
	                          + String.valueOf(twoDf.format(day));
	         java.util.Date targetDate = null;

	         try {
	             targetDate = getDate(tempDate, "yyyyMMdd");
	         } catch(java.text.ParseException pe) {
	             day = lastDay(year, month);
	             tempDate = String.valueOf(fourDf.format(year))
	                          + String.valueOf(twoDf.format(month))
	                          + String.valueOf(twoDf.format(day));
	             targetDate = getDate(tempDate, "yyyyMMdd");
	         }

	         return formatter.format(targetDate);
	     }

	     public static String addYears(String s, int year) throws java.text.ParseException {
	         return addYears(s, year, "yyyyMMdd");
	     }

	     public static String addYears(String s, int year, String format) throws java.text.ParseException {
	    java.text.SimpleDateFormat formatter =
	       new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);
	   java.util.Date date = getDate(s, format);
	         date.setTime(date.getTime() + ((long)year * 1000 * 60 * 60 * 24 * (365 + 1)));
	         return formatter.format(date);
	     }

	     public static int monthsBetween(String from, String to) throws java.text.ParseException {
	         return monthsBetween(from, to, "yyyyMMdd");
	     }

	     public static int monthsBetween(String from, String to, String format) throws java.text.ParseException {
	    //java.text.SimpleDateFormat formatter =
	       //new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);
	         java.util.Date fromDate = getDate(from, format);
	         java.util.Date toDate = getDate(to, format);

	         // if two date are same, return 0.
	         if (fromDate.compareTo(toDate) == 0) return 0;

	    java.text.SimpleDateFormat yearFormat =
	       new java.text.SimpleDateFormat("yyyy", java.util.Locale.KOREA);
	    java.text.SimpleDateFormat monthFormat =
	       new java.text.SimpleDateFormat("MM", java.util.Locale.KOREA);
	    java.text.SimpleDateFormat dayFormat =
	       new java.text.SimpleDateFormat("dd", java.util.Locale.KOREA);

	         int fromYear = Integer.parseInt(yearFormat.format(fromDate));
	         int toYear = Integer.parseInt(yearFormat.format(toDate));
	         int fromMonth = Integer.parseInt(monthFormat.format(fromDate));
	         int toMonth = Integer.parseInt(monthFormat.format(toDate));
	         int fromDay = Integer.parseInt(dayFormat.format(fromDate));
	         int toDay = Integer.parseInt(dayFormat.format(toDate));

	         int result = 0;
	         result += ((toYear - fromYear) * 12);
	         result += (toMonth - fromMonth);

//	         if (((toDay - fromDay) < 0) ) result += fromDate.compareTo(toDate);
	         // ceil�� floor�� ȿ��
	         if (((toDay - fromDay) > 0) ) result += toDate.compareTo(fromDate);

	         return result;
	     }

	     public static String lastDayOfMonth(String src) throws java.text.ParseException {
	         return lastDayOfMonth(src, "yyyyMMdd");
	     }

	     public static String lastDayOfMonth(String src, String format) throws java.text.ParseException {
	    java.text.SimpleDateFormat formatter =
	       new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);
	   java.util.Date date = getDate(src, format);

	    java.text.SimpleDateFormat yearFormat =
	       new java.text.SimpleDateFormat("yyyy", java.util.Locale.KOREA);
	    java.text.SimpleDateFormat monthFormat =
	       new java.text.SimpleDateFormat("MM", java.util.Locale.KOREA);

	         int year = Integer.parseInt(yearFormat.format(date));
	         int month = Integer.parseInt(monthFormat.format(date));
	         int day = lastDay(year, month);

	         java.text.DecimalFormat fourDf = new java.text.DecimalFormat("0000");
	    java.text.DecimalFormat twoDf = new java.text.DecimalFormat("00");
	         String tempDate = String.valueOf(fourDf.format(year))
	                          + String.valueOf(twoDf.format(month))
	                          + String.valueOf(twoDf.format(day));
	         date = getDate(tempDate, format);

	         return formatter.format(date);
	     }

	     private static int lastDay(int year, int month) throws java.text.ParseException {
	         int day = 0;
	         switch(month)
	         {
	             case 1:
	             case 3:
	             case 5:
	             case 7:
	             case 8:
	             case 10:
	             case 12: day = 31;
	                      break;
	             case 2: if ((year % 4) == 0) {
	                         if ((year % 100) == 0 && (year % 400) != 0) { day = 28; }
	                         else { day = 29; }
	                     } else { day = 28; }
	                     break;
	             default: day = 30;
	         }
	         return day;
	     }

}
