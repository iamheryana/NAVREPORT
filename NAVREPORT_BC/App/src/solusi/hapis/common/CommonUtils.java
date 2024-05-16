package solusi.hapis.common;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class CommonUtils {
	
	// Indonesian Date Time Format 
	public final static String DATE_FORMAT_DDMMYYYY = "dd-MM-yyyy";
	
	// This Format for Search Only
	public final static String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
		
	// This Format for Search Only
	public final static String DATE_FORMAT_YYYYMM = "yyyyMM";
	
	// This Format for Search Only
	public final static String DATE_FORMAT_YYYY = "yyyy";
	
	// This Format for Audit Trail
	public final static String DATE_FORMAT_DDMMYYYYHHMMSS = "dd-MM-yyyy HH:mm:ss";
		
	// Number Format
	public final static String NUMBER_FORMAT_3 = "#,###,##0.000";
	
	public final static String NUMBER_FORMAT = "#,###,##0.00";
	
	public final static String NUMBER_FORMAT_1 = "#,###,##0";
	
	// Page Size
	public final static int PAGE_SIZE = 10;
	public final static int PAGE_SIZE_DETAIL = 5;
	
	// Tab Setting
	public final static int ENABLED_TAB = 1; // See DefaultTreecell.java
	public final static int MAX_TAB = 5; // See DefaultTreecell.java
	
	@SuppressWarnings("rawtypes")
	public static boolean isNotEmpty(Collection collection) {
		if (collection != null && collection.size() > 0)
			return true;

		return false;
	}

	@SuppressWarnings("rawtypes")
	public static boolean isNotEmpty(List list) {
		if (list != null && list.size() > 0)
			return true;

		return false;
	}

	public static boolean isNotEmpty(String str) {
		if (str != null && str.trim().length() > 0)
			return true;

		return false;
	}

	public static boolean isNotEmpty(Date dte) {
		if (dte != null && dte.toString().trim().length() > 0)
			return true;

		return false;
	}

	public static boolean isNotEmpty(Object obj) {
		if (obj != null && obj.toString().trim().length() > 0)
			return true;

		return false;
	}

	public static Date getTimeFromString(String str) {
		if (isNotEmpty(str)) {
			if (str.length() == 5 && isNumber(str.substring(0, 2))
					&& isNumber(str.substring(3, 5))
					&& str.substring(2, 3).equals(":")) {
				if (isPostiveNumber(Long.parseLong(str.substring(0, 2)))
						&& isPostiveNumber(Long.parseLong(str.substring(3, 5)))) {
					if (Long.parseLong(str.substring(0, 2)) < 24
							&& Long.parseLong(str.substring(3, 5)) < 60) {
						Calendar cal = Calendar.getInstance();
						cal.set(Calendar.HOUR,
								Integer.parseInt(str.substring(0, 2)));
						cal.set(Calendar.MINUTE,
								Integer.parseInt(str.substring(3, 5)));

						return cal.getTime();
					}
				}
			}
		}

		return null;
	}

	public static String getStringFromTime(Date date) {
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			String string = cal.get(Calendar.HOUR) + ":"
					+ cal.get(Calendar.MINUTE);
			return string;
		}

		return null;
	}

	public static boolean isNumber(String str) {
		try {
			Long.parseLong(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean isPostiveNumber(long number) {
		if (number >= 0)
			return true;
		return false;
	}

	public static int getDayNumber(Date date) {
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			dayOfWeek = dayOfWeek - 1;
			if (dayOfWeek == 0)
				dayOfWeek = 7;
			return dayOfWeek;
		}

		return 0;
	}

	public static String getFormatStringDate(Date tanggal, String format) {
		if (tanggal != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(tanggal);
		}
		return null;
	}



	public static boolean isToday(Date date) {
		if (date != null) {
			String date1 = getFormatStringDate(date);
			String today = getFormatStringDate(new Date());
			return date1.equals(today);
		}
		return false;
	}

	/*
	 * Format Date into String with format dd-MM-yyyy
	 */
	public static String getFormatStringDate(Date date) {
		if (date != null) {
			return getFormatStringDate(date, "dd-MM-yyyy");
		}
		return null;
	}

	public static String formatStringToNPWP(String npwp) {
		if (CommonUtils.isNotEmpty(npwp)) {
			String vKode = npwp.replace(".", "").replaceAll("-", "");
			int vPanjang = vKode.length();
			StringBuilder sb = new StringBuilder();
			sb.append(vKode);
			if (vPanjang > 2) {
				sb.insert(2, ".");
				vPanjang += 1;
			}
			if (vPanjang > 6) {
				sb.insert(6, ".");
				vPanjang += 1;
			}
			if (vPanjang > 10) {
				sb.insert(10, ".");
				vPanjang += 1;
			}
			if (vPanjang > 12) {
				sb.insert(12, "-");
				vPanjang += 1;
			}
			if (vPanjang > 16) {
				sb.insert(16, ".");
			}
			return sb.toString();
		}
		return null;
	}

	public static String formatStringNPWPYa(String npwpya) {
		if (CommonUtils.isNotEmpty(npwpya)) {
			StringBuffer sb = new StringBuffer(npwpya);
			sb.replace(11, 12, npwpya);
			return sb.toString();
			
		}
		return npwpya;
	}

	public static String formatNPWPToString(String npwp) {
		if (CommonUtils.isNotEmpty(npwp) && npwp.length() == 20) {
			StringBuffer sb = new StringBuffer(npwp);
			sb.deleteCharAt(16);
			sb.deleteCharAt(12);
			sb.deleteCharAt(10);
			sb.deleteCharAt(6);
			sb.deleteCharAt(2);
			return sb.toString();
		}
		return npwp;
	}

	public static boolean isNPWPformat(String npwp) {
		if (CommonUtils.isNotEmpty(npwp) && npwp.length() == 20) {
			boolean valid = true;
			
			StringBuffer sb = new StringBuffer(npwp);
			StringBuffer separator = new StringBuffer();
			separator.append(sb.charAt(2));
			separator.append(sb.charAt(6));
			separator.append(sb.charAt(10));
			separator.append(sb.charAt(12));
			separator.append(sb.charAt(16));
			valid = valid && separator.toString().equals("...-.");
			
			if(!valid)
				return valid;
			
			sb.deleteCharAt(16);
			sb.deleteCharAt(12);
			sb.deleteCharAt(10);
			sb.deleteCharAt(6);
			sb.deleteCharAt(2);

			String paterninput = "^[A-Z0-9]+$";
			Pattern pattern = Pattern.compile(paterninput);
			Matcher matcher = pattern.matcher(sb.toString());
			valid = valid && matcher.matches();
			
			return valid;
		}
		return false;
	}

	public static boolean isEmailPattern(String email) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}


	public static boolean isStringToNumber(String convert) {
		if(convert == null){
			return true;
		}
		if(convert != null && convert.equals("") ){
			return true;
		}
		
		String paterninput = "^[0-9]+$";
		Pattern pattern = Pattern.compile(paterninput);
		Matcher matcher = pattern.matcher(convert);
		return matcher.matches();
	}
	
	public static String toUpperCase(String value){
        if (value != null) {
        	if(value.trim().length() != 0){
	            if (value.toUpperCase().trim().length() == 0) {
	                value = value;
	            } else {
	                value = value.toUpperCase().trim();
	            }
        	} else {
        		value = value;
        	}
        }

        return value;
	}
	
	/*
	public static String toUpperCase(String value){
        if (value != null && value.trim().length() != 0) {
        	return value.toUpperCase().trim();
        } else {
        	return null;
        }
	}
	*/
	
	/**
	 * <b>Convert String To Date</b> <br/>
	 * <b>Example of Usage:</b> {@code CommonUtils.convertStringToDate("2000")} <br/>
	 * 
	 * @author hari 
	 * @param string
	 * 		The input string
	 * @return date 
	 * 		The output date
	 */
	public static Date convertStringToDate(String string) {
		if (StringUtils.isNotBlank(string) && StringUtils.isNumeric(string)) {
			DateFormat formatter;
			try {
				if(string.length() == 8) {
					formatter= new SimpleDateFormat(DATE_FORMAT_YYYYMMDD);
					return formatter.parse(string);
				} else if(string.length() == 6) {
					formatter= new SimpleDateFormat(DATE_FORMAT_YYYYMM);
					return formatter.parse(string);
				} else if(string.length() == 4) {
					formatter= new SimpleDateFormat(DATE_FORMAT_YYYY);
					return formatter.parse(string);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * <b>Convert String To Date</b> <br/>
	 * <b>Example of Usage:</b> {@code CommonUtils.convertStringToDate("2000", yyyy)} <br/>
	 * 
	 * @author hari 
	 * @param string
	 * 		The input string
	 * @param format
	 * 		The date format
	 * @return date 
	 * 		The output date
	 */
	public static Date convertStringToDate(String string, String format) {
		if (StringUtils.isNotBlank(string)) {
			try {
				DateFormat formatter = new SimpleDateFormat(DATE_FORMAT_DDMMYYYY);
				if (StringUtils.isNotBlank(format)) {
					formatter = new SimpleDateFormat(format);
				}
				return formatter.parse(string);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * <b>Convert Date To String</b> <br/>
	 * <b>Example of Usage:</b> {@code CommonUtils.convertDateToString(new Date())} <br/>
	 * 
	 * @author hari 
	 * @param date
	 * 		The input date
	 * @return string 
	 * 		The output string
	 */
	public static String convertDateToString(Date date) {
		if (date != null) {
			DateFormat formatter = new SimpleDateFormat(DATE_FORMAT_DDMMYYYY);
			return formatter.format(date);
		}
		return "";
	}
	
	/**
	 * <b>Convert Date To String</b> <br/>
	 * <b>Example of Usage:</b> {@code CommonUtils.convertDateToString(new Date(),"MM-yyyy")} <br/>
	 * 
	 * @author hari 
	 * @param date
	 * 		The input date
	 * @param format
	 * 		The format date
	 * @return string 
	 * 		The output string
	 */
	public static String convertDateToString(Date date, String format) {
		if (date != null) {
			DateFormat formatter = new SimpleDateFormat(format);
			return formatter.format(date);
		}
		return "";
	}

	public static Integer getTahun(Date tanggal) {
		if (tanggal != null) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(tanggal);
			return cal.get(Calendar.YEAR);
		}
		return 0;
	}

	public static Integer getBulan(Date tanggal) {
		if (tanggal != null) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(tanggal);
			return cal.get(Calendar.MONTH);
		}
		return 0;
	}

	public static Integer getTanggal(Date tanggal) {
		if (tanggal != null) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(tanggal);
			return cal.get(Calendar.DAY_OF_MONTH);
		}
		return 0;
	}
	
	
	/**
	 * <b>Add Year</b> <br/>
	 * <b>Example of Usage:</b> {@code CommonUtils.addYear(new Date(), 3) // Add 3 years from the current date} <br/>
	 * 
	 * @author hari 
	 * @param date
	 * 		The input date
	 * @param number
	 * 		The input number of year
	 * @return date 
	 * 		The output of date
	 */
	public static Date addYear(Date date, int number) {
		if (date != null && number > 0) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(date);
			cal.add(Calendar.YEAR, number);
			return cal.getTime();
		}
		return date;
	}
	
	/**
	 * <b>Add Month</b> <br/>
	 * <b>Example of Usage:</b> {@code CommonUtils.addMonth(new Date(), 3) // Add 3 month from the current date} <br/>
	 * 
	 * @author hari 
	 * @param date
	 * 		The input date
	 * @param number
	 * 		The input number of month
	 * @return date 
	 * 		The output of date
	 */
	public static Date addMonth(Date date, int number) {
		if (date != null && number > 0) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(date);
			cal.add(Calendar.MONTH, number);
			return cal.getTime();
		}
		return date;
	}
	
	/**
	 * <b>Add Day</b> <br/>
	 * <b>Example of Usage:</b> {@code CommonUtils.addDay(new Date(), 3) // Add 3 days from the current date} <br/>
	 * 
	 * @author hari 
	 * @param date
	 * 		The input date
	 * @param number
	 * 		The input number of day
	 * @return date 
	 * 		The output of date
	 */
	public static Date addDay(Date date, int number) {
		if (date != null && number > 0) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(date);
			cal.add(Calendar.DATE, number);
			return cal.getTime();
		}
		return date;
	}
	
	/**
	 * <b>Create Second Parameter For Search</b> <br/>
	 * <b>Example of Usage:</b> <br/>
	 * 1. {@code CommonUtils.createSecondParameterForSearch("2012") // 2013} <br/>
	 * 2. {@code CommonUtils.createSecondParameterForSearch("201207") // 201308} <br/>
	 * 
	 * @author hari 
	 * @param string
	 * 		The input string
	 * @return date 
	 * 		The output of date
	 */
	public static Date createSecondParameterForSearch(String string) {
		if (StringUtils.isNotBlank(string) && StringUtils.isNumeric(string)) {
			DateFormat formatter;
			try {
				if(string.length() == 8) {
					formatter= new SimpleDateFormat(DATE_FORMAT_YYYYMMDD);
					Date date = CommonUtils.addYear(formatter.parse(string), 1);
					date = CommonUtils.addMonth(formatter.parse(string), 1);
					date = CommonUtils.addDay(formatter.parse(string), 1);
					return date;
				} else if(string.length() == 6) {
					formatter= new SimpleDateFormat(DATE_FORMAT_YYYYMM);
					Date date = CommonUtils.addYear(formatter.parse(string), 1);
					date = CommonUtils.addMonth(formatter.parse(string), 1);
					return date;
				} else if(string.length() == 4) {
					formatter= new SimpleDateFormat(DATE_FORMAT_YYYY);
					return CommonUtils.addYear(formatter.parse(string), 1);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * <b>Checking Valid Date </b> <br/>
	 * <b>Example of Usage:</b> <br/>
	 * 1. {@code CommonUtils.isValidDateFormat("2012") // true} <br/>
	 * 2. {@code CommonUtils.isValidDateFormat("201207") // true} <br/>
	 * 3. {@code CommonUtils.isValidDateFormat("20120708") // true} <br/>
	 * 4. {@code CommonUtils.isValidDateFormat("20") // false} <br/>
	 * 5. {@code CommonUtils.isValidDateFormat("aa") // false} <br/>
	 * 6. {@code CommonUtils.isValidDateFormat("--") // false} <br/>
	 * 
	 * @author hari 
	 * @param string
	 * 		The input string
	 * @return boolean
	 * 
	 */
	public static boolean isValidDateFormat(String string) {
		if (StringUtils.isNotBlank(string) && StringUtils.isNumeric(string) && (string.length() == 4 || string.length() == 6 || string.length() == 8) ) {
			return true;
		}
		return false;
	}
	
	/**
	 * <b>Add Day</b> <br/>
	 * <b>Example of Usage:</b> {@code CommonUtils.firstDate(new Date()) // first date from the current month} <br/>
	 * 
	 * @author Oznob 
	 * @param date
	 * 		The input date
	 * @return date 
	 * 		The output of date
	 */
	public static Date firstDate(Date date) {
		if (date != null	) {				
			Calendar cal = new GregorianCalendar();
			cal.setTime(date);
			cal.set(Calendar.DATE, 1);
			System.out.println(cal.getTime());			
			return cal.getTime();
		}
		return date;
	}	
	/**
	 * <b>Add Day</b> <br/>
	 * <b>Example of Usage:</b> {@code CommonUtils.firstDate(new Date()) // first date from the current month} <br/>
	 * 
	 * @author Oznob 
	 * @param date
	 * 		The input date
	 * @return date 
	 * 		The output of date
	 */
	public static Date lastDate(Date date) {
		if (date != null	) {				
			Calendar cal = new GregorianCalendar();
			cal.setTime(date);			
			cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE)); // changed calendar to cal		
			System.out.println(cal.getTime());
						
			return cal.getTime();
		}
		return date;
	}		
	
	
	/**
	 * <b>Format Number </b> <br/>
	 * <b>Example of Usage:</b> <br/>
	 * {@code CommonUtils.formatNumber(10000000000.50656565) // 10,000,000.51} <br/>
	 * 
	 * @author hari 
	 * @param number
	 * 		The input number
	 * @return string
	 * 
	 */
	public static String formatNumberDecimal3(Object number) {
		if(number != null) {
			NumberFormat formatter = new DecimalFormat(CommonUtils.NUMBER_FORMAT_3);
			return formatter.format(number);
		}
		return "";
	}
	
	public static String formatNumber(Object number) {
		if(number != null) {
			NumberFormat formatter = new DecimalFormat(CommonUtils.NUMBER_FORMAT);
			return formatter.format(number);
		}
		return "";
	}
	
	public static String formatNumberNonDecimal(Object number) {
		if(number != null) {
			NumberFormat formatter = new DecimalFormat(CommonUtils.NUMBER_FORMAT_1);
			return formatter.format(number);
		}
		return "";
	}
	
	public static String formatNumberManual(Object number, String format) {
		if(number != null) {
			NumberFormat formatter = new DecimalFormat(format);
			return formatter.format(number);
		}
		return "";
	}
	
	public static String convertToFormatNumberManual(String number, String format) {
		if(number != null) {
			int setNumber = Integer.parseInt(number);
			Number getNumber = setNumber;
			NumberFormat formatter = new DecimalFormat(format);
			return formatter.format(getNumber);
		}
		
		return "";
	}
	
	public static Date truncateDate2 (Date date){
		if (date == null) {
			return null;
		}
		DateFormat df = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT);  
		String dateString = df.format(date);  
		Date dateWithoutTimestamp = null;
		try {  
            dateWithoutTimestamp = df.parse(dateString);  
		} catch (ParseException e) {
			return null;
		}
		
        return dateWithoutTimestamp;
	}
	
// Di remark untuk jangka waktu yang belum di ketahui	
	public static Date truncateDate (Date date){
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar.getTime();
	}
	
	public static boolean isSameDate(Date date1, Date date2){
		String string1 = getFormatStringDate(date1);
		String string2 = getFormatStringDate(date2);
		return string1.equals(string2);
	}

	public static String convertNoFakturPajak(String noFP){
		String vResult = "";
		
		for(int x = 0 ; x < noFP.length(); x++){
			if(noFP.charAt(x) == '0' || noFP.charAt(x) == '1' ||
			   noFP.charAt(x) == '2' || noFP.charAt(x) == '3' ||
			   noFP.charAt(x) == '4' || noFP.charAt(x) == '5' ||
			   noFP.charAt(x) == '6' || noFP.charAt(x) == '7' ||
			   noFP.charAt(x) == '8' || noFP.charAt(x) == '9' ){
				vResult = vResult + noFP.charAt(x);
			}
		}
		return vResult;
	}

	public static String info_terbilang(long value) {
		String [] bilangan ={"","satu","dua","tiga","empat","lima","enam","tujuh","delapan","sembilan","sepuluh","sebelas"};
		String temp=" ";
		if (value<12){
			temp = " " + bilangan[(int)value];
		} else if(value<20){
			temp = info_terbilang(value-10) + " belas";
		} else if(value<100){
			temp = info_terbilang(value/10) + " puluh" + info_terbilang(value%10);
		} else if(value<200){
			temp = " seratus" + info_terbilang(value-100);
		} else if(value<1000){
			temp = info_terbilang(value/100) + " ratus" + info_terbilang(value%100);
		} else if(value<2000){
			temp = "seribu"+ info_terbilang(value-1000);
		} else if(value<1000000){
			temp = info_terbilang(value/1000) + " ribu" + info_terbilang (value%1000);
		} else if(value<1000000000){
			temp = info_terbilang(value/1000000) + " juta" + info_terbilang (value%1000000);
		} else if(value>=1000000000){
			temp = info_terbilang(value/1000000000) + "milyar" + info_terbilang (value%1000000000);
		}
		return temp;
	}
	
	public static void main(String[] args) throws ParseException {
//		String x = "010.031-16.45893176";
//		String noFP = x.substring(4, x.length());
//		String efakturNoFP =  noFP.replaceAll("-", "");
//		String efakturNoFP2 =  efakturNoFP.replaceAll(".", "-");
		
		//System.out.println("x : "+convertNoFakturPajak(noFP));		
		//System.out.println("efakturNoFP2 : "+efakturNoFP2);	
		
		//System.out.println("info_terbilang : "+info_terbilang(501000));
		
		// System.out.println(formatStringToNPWP("121231231123123"));
		//System.out.println(isNPWPformat("12.123.123.1-123.123"));
//		String empty = "24";
//		short s= 0;
//		 s = Short.parseShort(empty);
//		 System.out.println(s);
//		System.out.println(isStringToNumber("9"));
		// System.out.println(isEmailPattern("122@3e.co.id"));
		
		
//		DecimalFormat decimalFormat = new DecimalFormat();
//		decimalFormat.setParseBigDecimal(true);
//		try {
//			BigDecimal bigDecimal = (BigDecimal) decimalFormat.parse("10000.25");
//			
//			System.out.println("bigDecimal : "+bigDecimal);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		System.out.println("asasa");
//
//		
//		int vPeriodeLenghth = 2;
//	
//		String vPeriode = "W";
//
//		
//		Date vR1From = new Date();
//		Date vR1Upto = new Date();
//		Date vR2From = new Date();
//		Date vR2Upto = new Date();
//		Date vR3From = new Date();
//		Date vR3Upto = new Date();
//		Date vR4From = new Date();
//		Date vR4Upto = new Date();
//		Date vR5From = new Date();
//		Date vR5Upto = new Date();
//		Date vR6From = new Date();
//		Date vR6Upto = new Date();
//		Date vR7From = new Date();
//		Date vR7Upto = new Date();
//		
//
//		
//		String strTglFrom = "27/12/2016";
//		SimpleDateFormat dfTglFrom = new SimpleDateFormat("dd/MM/yyyy");
//		Date vParseTglFrom = dfTglFrom.parse(strTglFrom);
//		//Calendar cParseTglFrom = Calendar.getInstance();
//		//cParseTglFrom.setTime(vParseTglFrom);
//		
//		Date vTglFrom = vParseTglFrom;//vParseTglFromcParseTglFrom.getTime();   
//
//		
////		Date vTglFrom = new Date();
//		Calendar cTglFrom = Calendar.getInstance();		
//		cTglFrom.setTime(vTglFrom);
//		
//
//		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
//		int monthTglFrom = cTglFrom.get(Calendar.MONTH) + 1;
//		int dayTglFrom = cTglFrom.get(Calendar.DAY_OF_MONTH);
//		    
//		vR1From = cTglFrom.getTime();
//		
//		String dR1Upto="1/1/1900";
//		String dR2From="1/1/1900";
//		String dR2Upto="1/1/1900";		
//		String dR3From="1/1/1900";
//		String dR3Upto="1/1/1900";		
//		String dR4From="1/1/1900";
//		String dR4Upto="1/1/1900";		
//		String dR5From="1/1/1900";
//		String dR5Upto="1/1/1900";					
//		String dR6From="1/1/1900";
//		String dR6Upto="1/1/1900";		
//		String dR7From="1/1/1900";
//		String dR7Upto="1/1/1900";
//		
//		if (dayTglFrom <= 15 ){
//
//			//vR1Upto
//			Calendar cR1Upto = Calendar.getInstance();
//			cR1Upto.setTime(vR1From);
//			cR1Upto.set(Calendar.DAY_OF_MONTH, cR1Upto.getActualMaximum(Calendar.DAY_OF_MONTH));
//			vR1Upto = cR1Upto.getTime();
//			
//			if(monthTglFrom+1 <=12){
//				dR2From = "1/"+(monthTglFrom+1)+"/"+yearTglFrom;
//				dR2Upto = "15/"+(monthTglFrom+1)+"/"+yearTglFrom;
//				
//				dR3From = "16/"+(monthTglFrom+1)+"/"+yearTglFrom;
//				
//				if(monthTglFrom+2 <=12){
//					dR4From = "1/"+(monthTglFrom+2)+"/"+yearTglFrom;
//					dR4Upto = "15/"+(monthTglFrom+2)+"/"+yearTglFrom;
//					
//					dR5From = "16/"+(monthTglFrom+2)+"/"+yearTglFrom;
//					
//					if(monthTglFrom+3 <=12){
//						dR6From = "1/"+(monthTglFrom+3)+"/"+yearTglFrom;
//						dR6Upto = "15/"+(monthTglFrom+3)+"/"+yearTglFrom;
//						
//						dR7From = "16/"+(monthTglFrom+3)+"/"+yearTglFrom;
//						
//						if(monthTglFrom+4 <=12){
//							dR7Upto = "15/"+(monthTglFrom+4)+"/"+yearTglFrom;
//						} else {
//							dR7Upto = "15/1/"+(yearTglFrom+1);
//						}
//					} else {
//						dR6From = "1/1/"+(yearTglFrom+1);
//						dR6Upto = "15/1/"+(yearTglFrom+1);
//						
//						dR7From = "16/1/"+(yearTglFrom+1);
//						dR7Upto = "15/2/"+(yearTglFrom+1);
//					}
//				} else {
//					dR4From = "1/1/"+(yearTglFrom+1);
//					dR4Upto = "15/1/"+(yearTglFrom+1);
//					
//					dR5From = "16/1/"+(yearTglFrom+1);
//					
//					dR6From = "1/2/"+(yearTglFrom+1);
//					dR6Upto = "15/2/"+(yearTglFrom+1);
//					
//					dR7From = "16/2/"+(yearTglFrom+1);
//					dR7Upto = "15/3/"+(yearTglFrom+1);
//				}
//			} else {
//				dR2From = "1/1/"+(yearTglFrom+1);
//				dR2Upto = "15/1/"+(yearTglFrom+1);
//				
//				dR3From = "16/1/"+(yearTglFrom+1);
//				
//				dR4From = "1/2/"+(yearTglFrom+1);
//				dR4Upto = "15/2/"+(yearTglFrom+1);
//				
//				dR5From = "16/2/"+(yearTglFrom+1);
//				
//				dR6From = "1/3/"+(yearTglFrom+1);
//				dR6Upto = "15/3/"+(yearTglFrom+1);
//				
//				dR7From = "16/3/"+(yearTglFrom+1);
//				dR7Upto = "15/4/"+(yearTglFrom+1);
//				
//			}
//			
//			//vR2From
//			SimpleDateFormat dfR2From= new SimpleDateFormat("dd/MM/yyyy");
//			vR2From = dfR2From.parse(dR2From);
//			
//			//vR2Upto
//			SimpleDateFormat dfR2Upto= new SimpleDateFormat("dd/MM/yyyy");
//			vR2Upto = dfR2Upto.parse(dR2Upto);
//				
//			//vR3From
//			SimpleDateFormat dfR3From= new SimpleDateFormat("dd/MM/yyyy");
//			vR3From = dfR3From.parse(dR3From);
//			
//			//vR3Upto
//			Calendar cR3Upto = Calendar.getInstance();
//			cR3Upto.setTime(vR3From);
//			cR3Upto.set(Calendar.DAY_OF_MONTH, cR3Upto.getActualMaximum(Calendar.DAY_OF_MONTH));
//			vR3Upto = cR3Upto.getTime();
//		
//			//vR4From
//			SimpleDateFormat dfR4From= new SimpleDateFormat("dd/MM/yyyy");
//			vR4From = dfR4From.parse(dR4From);
//			
//			//vR4Upto
//			SimpleDateFormat dfR4Upto= new SimpleDateFormat("dd/MM/yyyy");
//			vR4Upto = dfR4Upto.parse(dR4Upto);
//			
//			//vR5From
//			SimpleDateFormat dfR5From= new SimpleDateFormat("dd/MM/yyyy");
//			vR5From = dfR5From.parse(dR5From);
//			
//			//vR5Upto
//			Calendar cR5Upto = Calendar.getInstance();
//			cR5Upto.setTime(vR5From);
//			cR5Upto.set(Calendar.DAY_OF_MONTH, cR5Upto.getActualMaximum(Calendar.DAY_OF_MONTH));
//			vR5Upto = cR5Upto.getTime();
//			
//			//vR6From
//			SimpleDateFormat dfR6From= new SimpleDateFormat("dd/MM/yyyy");
//			vR6From = dfR6From.parse(dR6From);
//			
//			//vR6Upto
//			SimpleDateFormat dfR6Upto= new SimpleDateFormat("dd/MM/yyyy");
//			vR6Upto = dfR6Upto.parse(dR6Upto);
//			
//			//vR7From
//			SimpleDateFormat dfR7From= new SimpleDateFormat("dd/MM/yyyy");
//			vR7From = dfR7From.parse(dR7From);
//			
//			//vR7Upto
//			SimpleDateFormat dfR7Upto= new SimpleDateFormat("dd/MM/yyyy");
//			vR7Upto = dfR7Upto.parse(dR7Upto);
//			
//					
//			
//		} else {
//					
//			if(monthTglFrom+1 <=12){
//				dR1Upto = "15/"+(monthTglFrom+1)+"/"+yearTglFrom;
//				
//				dR2From = "16/"+(monthTglFrom+1)+"/"+yearTglFrom;
//					
//				if(monthTglFrom+2 <=12){
//					dR3From = "1/"+(monthTglFrom+2)+"/"+yearTglFrom;
//					dR3Upto = "15/"+(monthTglFrom+2)+"/"+yearTglFrom;
//					
//					dR4From = "16/"+(monthTglFrom+2)+"/"+yearTglFrom;
//					
//					if(monthTglFrom+3 <=12){
//						dR5From = "1/"+(monthTglFrom+3)+"/"+yearTglFrom;
//						dR5Upto = "15/"+(monthTglFrom+3)+"/"+yearTglFrom;
//						
//						dR6From = "16/"+(monthTglFrom+3)+"/"+yearTglFrom;
//						
//						if(monthTglFrom+4 <=12){
//							dR7From = "1/"+(monthTglFrom+4)+"/"+yearTglFrom;
//						} else {
//							dR7From = "1/1/"+(yearTglFrom+1);
//						}
//					} else {
//						dR5From = "1/1/"+(yearTglFrom+1);
//						dR5Upto = "15/1/"+(yearTglFrom+1);
//						
//						dR6From = "16/1/"+(yearTglFrom+1);
//						
//						dR7From = "1/2/"+(yearTglFrom+1);
//					}
//				
//				} else {
//					dR3From = "1/1/"+(yearTglFrom+1);
//					dR3Upto = "15/1/"+(yearTglFrom+1);
//					
//					dR4From = "16/1/"+(yearTglFrom+1);
//		
//					dR5From = "1/2/"+(yearTglFrom+1);
//					dR5Upto = "15/2/"+(yearTglFrom+1);
//					
//					dR6From = "16/2/"+(yearTglFrom+1);
//					
//					dR7From = "1/3/"+(yearTglFrom+1);
//				}
//			} else {
//				dR1Upto = "15/1/"+(yearTglFrom+1);
//				
//				dR2From = "16/1/"+(yearTglFrom+1);
//				
//				dR3From = "1/2/"+(yearTglFrom+1);
//				dR3Upto = "15/2/"+(yearTglFrom+1);
//				
//				dR4From = "16/2/"+(yearTglFrom+1);
//	
//				dR5From = "1/3/"+(yearTglFrom+1);
//				dR5Upto = "15/3/"+(yearTglFrom+1);
//				
//				dR6From = "16/3/"+(yearTglFrom+1);
//				
//				dR7From = "1/4/"+(yearTglFrom+1);
//			}
//			
//			//vR1Upto
//			SimpleDateFormat dfR1Upto= new SimpleDateFormat("dd/MM/yyyy");
//			vR1Upto = dfR1Upto.parse(dR1Upto);
//			
//			//vR2From
//			SimpleDateFormat dfR2From= new SimpleDateFormat("dd/MM/yyyy");
//			vR2From = dfR2From.parse(dR2From);
//			
//			//vR2Upto
//			Calendar cR2Upto = Calendar.getInstance();
//			cR2Upto.setTime(vR2From);
//			cR2Upto.set(Calendar.DAY_OF_MONTH, cR2Upto.getActualMaximum(Calendar.DAY_OF_MONTH));
//			vR2Upto = cR2Upto.getTime();
//			
//			//vR3From
//			SimpleDateFormat dfR3From= new SimpleDateFormat("dd/MM/yyyy");
//			vR3From = dfR3From.parse(dR3From);
//			
//			//vR3Upto
//			SimpleDateFormat dfR3Upto= new SimpleDateFormat("dd/MM/yyyy");
//			vR3Upto = dfR3Upto.parse(dR3Upto);
//			
//			//vR4From
//			SimpleDateFormat dfR4From= new SimpleDateFormat("dd/MM/yyyy");
//			vR4From = dfR4From.parse(dR4From);
//			
//			//vR4Upto
//			Calendar cR4Upto = Calendar.getInstance();
//			cR4Upto.setTime(vR4From);
//			cR4Upto.set(Calendar.DAY_OF_MONTH, cR4Upto.getActualMaximum(Calendar.DAY_OF_MONTH));
//			vR4Upto = cR4Upto.getTime();
//			
//			//vR5From
//			SimpleDateFormat dfR5From= new SimpleDateFormat("dd/MM/yyyy");
//			vR5From = dfR5From.parse(dR5From);
//			
//			//vR5Upto
//			SimpleDateFormat dfR5Upto= new SimpleDateFormat("dd/MM/yyyy");
//			vR5Upto = dfR5Upto.parse(dR5Upto);
//			
//			//vR6From
//			SimpleDateFormat dfR6From= new SimpleDateFormat("dd/MM/yyyy");
//			vR6From = dfR6From.parse(dR6From);
//			
//			//vR6Upto
//			Calendar cR6Upto = Calendar.getInstance();
//			cR6Upto.setTime(vR6From);
//			cR6Upto.set(Calendar.DAY_OF_MONTH, cR6Upto.getActualMaximum(Calendar.DAY_OF_MONTH));
//			vR6Upto = cR6Upto.getTime();
//			
//			//vR7From
//			SimpleDateFormat dfR7From= new SimpleDateFormat("dd/MM/yyyy");
//			vR7From = dfR7From.parse(dR7From);
//			
//			//vR7Upto
//			Calendar cR7Upto = Calendar.getInstance();
//			cR7Upto.setTime(vR7From);
//			cR7Upto.set(Calendar.DAY_OF_MONTH, cR7Upto.getActualMaximum(Calendar.DAY_OF_MONTH));
//			vR7Upto = cR7Upto.getTime();
//			
//			
//		}
////		if (vPeriode.equals("D")){
////			vR1From = cTglFrom.getTime();
////			
////			cTglFrom.add(Calendar.DAY_OF_YEAR, vPeriodeLenghth);
////			vR2From= cTglFrom.getTime();
////			
////			cTglFrom.add(Calendar.DAY_OF_YEAR, vPeriodeLenghth);
////			vR3From= cTglFrom.getTime();
////			
////			cTglFrom.add(Calendar.DAY_OF_YEAR, vPeriodeLenghth);
////			vR4From= cTglFrom.getTime();
////			
////			cTglFrom.add(Calendar.DAY_OF_YEAR, vPeriodeLenghth);
////			vR5From= cTglFrom.getTime();
////			
////			cTglFrom.add(Calendar.DAY_OF_YEAR, vPeriodeLenghth);
////			vR6From= cTglFrom.getTime();
////			
////			Calendar cTglUpto = Calendar.getInstance();		
////			cTglUpto.setTime(vR2From);
////			
////			cTglUpto.add(Calendar.DAY_OF_YEAR, -1);
////			vR1Upto = cTglUpto.getTime();
////			
////			cTglUpto.add(Calendar.DAY_OF_YEAR, vPeriodeLenghth);
////			vR2Upto = cTglUpto.getTime();
////			
////			cTglUpto.add(Calendar.DAY_OF_YEAR, vPeriodeLenghth);
////			vR3Upto = cTglUpto.getTime();
////			
////			cTglUpto.add(Calendar.DAY_OF_YEAR, vPeriodeLenghth);
////			vR4Upto = cTglUpto.getTime();
////			
////			cTglUpto.add(Calendar.DAY_OF_YEAR, vPeriodeLenghth);
////			vR5Upto = cTglUpto.getTime();
////			
////			cTglUpto.add(Calendar.DAY_OF_YEAR, vPeriodeLenghth);
////			vR6Upto = cTglUpto.getTime();
////		}
////		
////		if (vPeriode.equals("W")){
////			vR1From = cTglFrom.getTime();
////			
////			cTglFrom.add(Calendar.WEEK_OF_YEAR, vPeriodeLenghth);
////			vR2From= cTglFrom.getTime();
////			
////			cTglFrom.add(Calendar.WEEK_OF_YEAR, vPeriodeLenghth);
////			vR3From= cTglFrom.getTime();
////			
////			cTglFrom.add(Calendar.WEEK_OF_YEAR, vPeriodeLenghth);
////			vR4From= cTglFrom.getTime();
////			
////			cTglFrom.add(Calendar.WEEK_OF_YEAR, vPeriodeLenghth);
////			vR5From= cTglFrom.getTime();
////			
////			cTglFrom.add(Calendar.WEEK_OF_YEAR, vPeriodeLenghth);
////			vR6From= cTglFrom.getTime();
////			
////			Calendar cTglUpto = Calendar.getInstance();		
////			cTglUpto.setTime(vR2From);
////			
////			cTglUpto.add(Calendar.DAY_OF_YEAR, -1);
////			vR1Upto = cTglUpto.getTime();
////			
////			cTglUpto.add(Calendar.WEEK_OF_YEAR, vPeriodeLenghth);
////			vR2Upto = cTglUpto.getTime();
////			
////			cTglUpto.add(Calendar.WEEK_OF_YEAR, vPeriodeLenghth);
////			vR3Upto = cTglUpto.getTime();
////			
////			cTglUpto.add(Calendar.WEEK_OF_YEAR, vPeriodeLenghth);
////			vR4Upto = cTglUpto.getTime();
////			
////			cTglUpto.add(Calendar.WEEK_OF_YEAR, vPeriodeLenghth);
////			vR5Upto = cTglUpto.getTime();
////			
////			cTglUpto.add(Calendar.WEEK_OF_YEAR, vPeriodeLenghth);
////			vR6Upto = cTglUpto.getTime();
////		}
////		
////		if (vPeriode.equals("M")){
////			vR1From = cTglFrom.getTime();
////			
////			cTglFrom.add(Calendar.MONTH, vPeriodeLenghth);
////			vR2From= cTglFrom.getTime();
////			
////			cTglFrom.add(Calendar.MONTH, vPeriodeLenghth);
////			vR3From= cTglFrom.getTime();
////			
////			cTglFrom.add(Calendar.MONTH, vPeriodeLenghth);
////			vR4From= cTglFrom.getTime();
////			
////			cTglFrom.add(Calendar.MONTH, vPeriodeLenghth);
////			vR5From= cTglFrom.getTime();
////			
////			cTglFrom.add(Calendar.MONTH, vPeriodeLenghth);
////			vR6From= cTglFrom.getTime();
////			
////			Calendar cTglUpto = Calendar.getInstance();		
////			cTglUpto.setTime(vR2From);
////			
////			cTglUpto.add(Calendar.DAY_OF_YEAR, -1);
////			vR1Upto = cTglUpto.getTime();
////			
////			cTglUpto.add(Calendar.MONTH, vPeriodeLenghth);
////			vR2Upto = cTglUpto.getTime();
////			
////			cTglUpto.add(Calendar.MONTH, vPeriodeLenghth);
////			vR3Upto = cTglUpto.getTime();
////			
////			cTglUpto.add(Calendar.MONTH, vPeriodeLenghth);
////			vR4Upto = cTglUpto.getTime();
////			
////			cTglUpto.add(Calendar.MONTH, vPeriodeLenghth);
////			vR5Upto = cTglUpto.getTime();
////			
////			cTglUpto.add(Calendar.MONTH, vPeriodeLenghth);
////			vR6Upto = cTglUpto.getTime();
////		}
////		
////		if (vPeriode.equals("Y")){
////			vR1From = cTglFrom.getTime();
////			
////			cTglFrom.add(Calendar.YEAR, vPeriodeLenghth);
////			vR2From= cTglFrom.getTime();
////			
////			cTglFrom.add(Calendar.YEAR, vPeriodeLenghth);
////			vR3From= cTglFrom.getTime();
////			
////			cTglFrom.add(Calendar.YEAR, vPeriodeLenghth);
////			vR4From= cTglFrom.getTime();
////			
////			cTglFrom.add(Calendar.YEAR, vPeriodeLenghth);
////			vR5From= cTglFrom.getTime();
////			
////			cTglFrom.add(Calendar.YEAR, vPeriodeLenghth);
////			vR6From= cTglFrom.getTime();
////			
////			Calendar cTglUpto = Calendar.getInstance();		
////			cTglUpto.setTime(vR2From);
////			
////			cTglUpto.add(Calendar.DAY_OF_YEAR, -1);
////			vR1Upto = cTglUpto.getTime();
////			
////			cTglUpto.add(Calendar.YEAR, vPeriodeLenghth);
////			vR2Upto = cTglUpto.getTime();
////			
////			cTglUpto.add(Calendar.YEAR, vPeriodeLenghth);
////			vR3Upto = cTglUpto.getTime();
////			
////			cTglUpto.add(Calendar.YEAR, vPeriodeLenghth);
////			vR4Upto = cTglUpto.getTime();
////			
////			cTglUpto.add(Calendar.YEAR, vPeriodeLenghth);
////			vR5Upto = cTglUpto.getTime();
////			
////			cTglUpto.add(Calendar.YEAR, vPeriodeLenghth);
////			vR6Upto = cTglUpto.getTime();
////		}
//
//
//		System.out.println("vR1 : "+ vR1From + " s/d "+vR1Upto);
//		System.out.println("vR2 : "+ vR2From + " s/d "+vR2Upto);
//		System.out.println("vR3 : "+ vR3From + " s/d "+vR3Upto);
//		System.out.println("vR4 : "+ vR4From + " s/d "+vR4Upto);
//		System.out.println("vR5 : "+ vR5From + " s/d "+vR5Upto);
//		System.out.println("vR6 : "+ vR6From + " s/d "+vR6Upto);
//		System.out.println("vR7 : "+ vR7From + " s/d "+vR7Upto);
//		
		
		
		
	}
	
    public static String convertClientAddress(Authentication authentication) {
        try {
            WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
            return details.getRemoteAddress();
        } catch (ClassCastException e) {
            // securitycontext ist vom falschen Typ!
            return "<unbekannt>";
        }
    }

}