package com.medialog.uplussave.common.util.legacy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.PropertyUtils;
import org.json.simple.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CmmnUtil {

	/**
	 * Vitual start IDX
	 * @param totCnt
	 * @param currentPage
	 * @param rowsPerPage
	 * @return
	 */
	public static int startIdx(int totCnt, int currentPage, int rowsPerPage) {

		int startRow = totCnt - ( currentPage - 1 ) * rowsPerPage;
		int offSet   = 1;	//using Jstl idx start 1....

		return startRow + offSet;
	}

	/**
	 *  Desc : ucube 응답 parameter인 prodNo(12자리)를 화면출력용 핸드폰번호 형태로 변환 (ex : 010-1234-5678)
	 * @param prodNo
	 * @return
	 */
	public static String convertProdNoToCtn(String prodNo) {
		String ctn = new String();
		ctn = prodNo.substring(0, 3)+"-"+prodNo.substring(4, 8)+"-"+prodNo.substring(8, 12); // xxx0xxxxxxxx -> xxx-xxxx-xxxx 포맷으로 변환

		return MaskUtil.phoneMasking(ctn);
	}

	/**
	 *  Desc : ucube 응답 parameter인 prodNo(12자리)를 화면출력용 핸드폰번호 형태로 변환 (ex : 010-1234-5678)
	 *  		  ucube 응답 parameter인 prodNo(11자리)를 화면출력용 핸드폰번호 형태로 변환 (ex : 010-1234-5678)
	 *  		  ucube 응답 parameter인 prodNo(10자리)를 화면출력용 핸드폰번호 형태로 변환 (ex : 010-123-4567)
	 * @param prodNo
	 * @return
	 */
	public static String convertProdNoToCtnNoMasking(String prodNo) {
		String ctn = prodNo.replaceAll("-", "");
		if(prodNo.length() == 10) {
			ctn = prodNo.substring(0, 3)+"-"+prodNo.substring(3, 6)+"-"+prodNo.substring(6, 10); // xxxxxxxxxx -> xxx-xxx-xxxx 포맷으로 변환
		} else if(prodNo.length() == 11) {
			ctn = prodNo.substring(0, 3)+"-"+prodNo.substring(3, 7)+"-"+prodNo.substring(7, 11); // xxxxxxxxxxx -> xxx-xxxx-xxxx 포맷으로 변환
		} else if(prodNo.length() == 12) {
			ctn = prodNo.substring(0, 3)+"-"+prodNo.substring(4, 8)+"-"+prodNo.substring(8, 12); // xxx0xxxxxxxx -> xxx-xxxx-xxxx 포맷으로 변환
		} else {
			ctn = prodNo;
		}
		return ctn;
	}

	/**
	 *  Desc : 11자리인 핸드폰번호를 ucube 요청 parameter인 prodNo(12자리)로 변환
	 *  Desc : 10자리인 핸드폰번호를 ucube 요청 parameter인 prodNo(12자리)로 변환  8-26일 추가
	 * @param prodNo
	 * @return
	 */
	public static String convertCtnToProdNo(String ctn) {
		StringBuffer sbProdNo = new StringBuffer();
		ctn.trim();
		if(ctn.length() == 11){
			sbProdNo.append(ctn);
			sbProdNo.insert(3, '0');
		}else if(ctn.length() == 10){
			sbProdNo.append(ctn);
			sbProdNo.insert(3, '0');
			sbProdNo.insert(4, '0');
		}else{
			sbProdNo.append(ctn);
		}
		return sbProdNo.toString();
	}

	/**
	 *  Desc : 현재 년월을 yyyyMM형태로 리턴
	 * @param prodNo
	 * @return
	 */
	public static String getCurrentYearMonth() {
		Date timestamp =  new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

		return sdf.format(timestamp).toString();
	}

	/**
	 *  Desc : 현재 년월일을 yyyyMMdd형태로 리턴
	 * @param prodNo
	 * @return
	 */
	public static String getCurrentDate() {
		Date timestamp =  new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		return sdf.format(timestamp).toString();
	}

	/**
	 *  Desc : 현재 년월일을 yyyy-MM-dd형태로 리턴
	 * @param prodNo
	 * @return
	 */
	public static String getCurrentDatehyphne() {
		Date timestamp =  new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		return sdf.format(timestamp).toString();
	}

	/**
	 *  Desc : 현재 년월일을 yyyy-MM형태로 리턴
	 * @param prodNo
	 * @return
	 */
	public static String getCurrenthyphne() {
		Date timestamp =  new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

		return sdf.format(timestamp).toString();
	}

	/**
	 *  Desc : 현재 년월일을 yyyyMMddHHmmss형태로 리턴
	 * @param prodNo
	 * @return
	 */
	public static String getCurrentDateTime() {
		Date timestamp =  new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

		return sdf.format(timestamp).toString();
	}

	/**
	 *  Desc : 현재 시간을 입력된 포멧 형태로 리턴
	 * @param prodNo
	 * @return
	 */
	public static String getCurrentTime(String pattern) {
		Date timestamp =  new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		return sdf.format(timestamp).toString();
	}
	/**
	 *  Desc : 텍스트 파일 읽기 (File)
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static String readTextFileNew(String filePath) throws Exception
	{
		StringBuffer sbContent = new StringBuffer();

		String result = "";

		FileInputStream fis = new FileInputStream(new File(filePath));
		InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
		BufferedReader bw = new BufferedReader(isr);

		while((result = bw.readLine()) != null) {
			sbContent.append(result+"\r\n");
		}
		bw.close();

		return sbContent.toString();
	}
	/**
	 *  Desc : 텍스트 파일 읽기
	 * @param filePath
	 * @return
	 */
	public static String readTextFile(String filePath) throws IOException
	{
		String sHtml = "";
		StringBuffer sbContent = new StringBuffer();
		BufferedReader in = null;

		try
		{
			URL url = new URL(filePath);
			URLConnection urlconn = url.openConnection();
			in = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));

			while((sHtml = in.readLine()) != null)
			{
				sbContent.append(sHtml+"\r\n");
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (in != null) {
				in.close();
			}
		}

		return sbContent.toString();
	}

	/**
	 *  Desc : 현재시간으로부터 년, 월, 일을 증감한다.
	 *  @Mehtod Name : addYearMonthDay
	 *  @param year
	 *  @param month
	 *  @param day
	 *  @return
	 */
	public static String addYearMonthDay(int year, int month, int day) {

		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		cal.setTime(date);

		if (year != 0) {
            cal.add(Calendar.YEAR, year);
        }
		if (month != 0) {
            cal.add(Calendar.MONTH, month);
        }
		if (day != 0) {
            cal.add(Calendar.DATE, day);
        }

		return sdf.format(cal.getTime());
	}

	/**
	 *  Desc : 현재시간으로부터 년, 월을 증감한다.
	 *  @Mehtod Name : addYearMonth
	 *  @param year
	 *  @param month
	 *  @param day
	 *  @return
	 */
	public static String addYearMonth(int year, int month) {

		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

		cal.setTime(date);

		if (year != 0) {
            cal.add(Calendar.YEAR, year);
        }
		if (month != 0) {
            cal.add(Calendar.MONTH, month);
        }

		return sdf.format(cal.getTime());
	}

	/**
	 *  Desc : param에 값이 없을 경우 2번쨰 param을 리턴
	 *  @Mehtod Name : NVL
	 *  @param param
	 *  @param returnValue
	 *  @return
	 */
	public static String NVL(String param, String returnValue) throws Exception {
		if(param == null || param.length() == 0) {
			return returnValue;
		}else {
			return param;
		}
	}
	/**
	 *  추가 2014/12/09  ok84j
	 *  Desc : param에 값이 없을 경우 2번쨰 param을 리턴
	 *  @Mehtod Name : NVL
	 *  @param param
	 *  @param returnValue
	 *  @return
	 */
	public static String NVL(String param)  {
		if(param == null || param.length() == 0) {
			return "";
		}else {
			return param;
		}
	}
	/**
	 *  Desc : NULL 여부 체크
	 *  @Mehtod Name : isNull
	 *  @param value
	 *  @return
	 */
	public static boolean isNull(String value) {
		if(value == null) {
            return true;
        }

		try{
			if(value.length() > 0) {
				return false;
			}
			else {
				return true;
			}
		}
		catch(NullPointerException e) {
			return true;
		}

	}

	/**
	 * 카드번호 포맷으로 변환한다. (XXXX-XXXX-XXXX-XXXX)
	 * 카드번호가 16자리만 포맷으로 변환한다.
	 * @param card
	 * @return
	 */
	public static String getCardFormat(String card){
		if (card == null || card.indexOf("-") >= 0) {
            return card;
        }

		int cardLength = card.length();
		StringBuilder nCard = new StringBuilder();

		if (cardLength == 16) {
			nCard.append(card.substring(0, 4)).append("-").append(card.substring(4, 8)).append("-").append(card.substring(8, 12)).append("-").append(card.substring(12));
		} else {
			nCard.append(card);
		}

		return nCard.toString();
	}

	/**
	 * 객체를 ArrayList<HashMap<String, String>> 로 변환한다.
	 *
	 * @param beans
	 * @return
	 */
	public static ArrayList<HashMap<String, String>> convertArrayList(Object[] beans)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		int i=0;
		ArrayList<HashMap<String, String>> al = new ArrayList<>();
		for(Object bean : beans) {

			al.add(i, setHashMap(bean));
			//al.
			i++;
		}

		return al;
	}

	/**
	 * 객체를 HashMap<String, String>> 로 변환한다.
	 *
	 * @param setHashMap
	 * @return
	 */
	public static HashMap<String, String> setHashMap(Object bean)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		HashMap<String, String> n = new HashMap<>();

		HashMap<String, Object> describe = (HashMap<String, Object>) PropertyUtils.describe(bean);
		Iterator<String> it = describe.keySet().iterator();
		while(it.hasNext()) {
			String prop = it.next();
			Object obj = PropertyUtils.getProperty(bean, prop);
			if(obj == null) {
				n.put(prop, "");
			}
			else {
				n.put(prop, obj.toString());
			}
		}

		return n;
		/*
		ArrayList<HashMap<String, String>> test = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> n = new HashMap<String, String>();
		n.put("a", "a");
		n.put("b", "b");
		test.add(n);

		HashMap<String, String> m = test.get(0);//it will get the first HashMap Stored in array list

		String strArr[] = new String[m.size()];
		int i = 0;
		for (HashMap<String, String> hash : test) {
		    for (String current : hash.values()) {
		        strArr[i] = current;
		        i++;
		    }
		}
		*/


	}



	/**
	 * 미성년자 체크  2014/12/16 ok84j
	 *
	 * @param setHashMap
	 * @return
	 */
	public static boolean underAge(String personalId, int limitAge){

		boolean isUnder = false;

		String birthDay = "";

		if(personalId.charAt(6)  == '1' || personalId.charAt(6)  == '2' ||
				personalId.charAt(6)  == '5' || personalId.charAt(6)  == '6'){
			birthDay = "19" + personalId.substring(0, 6);
		} else if(personalId.charAt(6)  == '3' || personalId.charAt(6)  == '4' ||
				personalId.charAt(6)  == '7' || personalId.charAt(6)  == '8'){
			birthDay = "20" + personalId.substring(0, 6);
		}

		Calendar today = Calendar.getInstance();

		today.add(Calendar.YEAR, -limitAge);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		try {
			if(today.getTime().compareTo(format.parse(birthDay)) < 0){
				isUnder = true;
			}
		} catch (ParseException pe ) {
			isUnder = false;
		}

		return isUnder;

	}

	/**
	 * 미성년자 체크  2021/4/6 kjs973
	 *
	 * @param setHashMap
	 * @return
	 */
	public static boolean underAgeYYYYMMDD(String birthDay, int limitAge){

		boolean isUnder = false;

		Calendar today = Calendar.getInstance();

		today.add(Calendar.YEAR, -limitAge);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		try {
			if(today.getTime().compareTo(format.parse(birthDay)) < 0){
				isUnder = true;
			}
		} catch (ParseException pe ) {
			isUnder = false;
		}

		return isUnder;

	}

	/**
	 * 20150408 이민철
	 * 법인고객
	 * esb데이터가 법인일경우 GC로 치환한다.
	 * @param
	 * @return
	 */
	public static String CustType(String custKdCd){

		String custDvCd1 = custKdCd.substring(0, 1);
		String custDvCd2 = custKdCd.substring(0, 2);

		//개인
		if(custDvCd1.equals("I")) {
			custKdCd = "II";
		}
		else if(custDvCd1.equals("G")) {
			//개인사업자 OR 외국인개인사업자
			if(custDvCd2.equals("GE") || custDvCd2.equals("GF")) {
				custKdCd = "GE";
			}
			//법인
			else {
				custKdCd = "GC";
			}
		}

		return custKdCd;
	}

	/**
	 * 20150408 이민철
	 *  Desc : ucube 응답 parameter인 prodNo(12자리)를 11자리핸드폰번호 형태로 변환 (ex : 01012345678)
	 * @param prodNo
	 * @return
	 */
	public static String convertCtnToCtnNoMasking(String prodNo) {
		String ctn = prodNo.replaceAll("-", "");

		if (ctn.length() == 12) {
			ctn = ctn.substring(0,3)+ctn.substring(4,8)+ctn.substring(8,12); // xxx0xxxxxxxx 12자리 -> xxxxxxxxxxx 11자리 포맷으로 변환
		}

		return ctn;
	}

	/**
	 * 고객유형체크
	 * @param custKdCd : 홈페이지고객유형코드 , eCustKdCd : esb고객유형코드
	 * @return
	 */
	public static boolean custTypeCheck(String custKdCd, String eCustKdCd){

		boolean chkType = false;

		if(custKdCd.equals(eCustKdCd)){
			chkType = true;
		}

		return chkType;

	}


	/**
	 *  Desc : client ip 조회 2005/07/01 ok84j
	 *  @Mehtod Name : getDefaultValue
	 *  @param value
	 *  @param defaultValue
	 *  @return
	 */
	public static String getClientIp(HttpServletRequest request){

		String ipAddr = request.getHeader("X-Forwarded-For");
		if(ipAddr == null || ipAddr.length() ==0 || "unknown".equalsIgnoreCase(ipAddr)) {
			ipAddr = 	request.getHeader("Proxy-Clent-IP");
		}
		if(ipAddr == null || ipAddr.length() ==0 || "unknown".equalsIgnoreCase(ipAddr)) {
			ipAddr = 	request.getHeader("WL-Proxy-Client-IP");
		}
		if(ipAddr == null || ipAddr.length() ==0 || "unknown".equalsIgnoreCase(ipAddr)) {
			ipAddr = 	request.getHeader("HTTP_CLIENT_IP");
		}
		if(ipAddr == null || ipAddr.length() ==0 || "unknown".equalsIgnoreCase(ipAddr)) {
			ipAddr = 	request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		//add. ok84j 2017/08/02 ok84j
		if(ipAddr == null || ipAddr.length() ==0 || "unknown".equalsIgnoreCase(ipAddr)) {
			ipAddr = 	request.getHeader("X-Cluster-Client-Ip");
		}
		if(ipAddr == null || ipAddr.length() ==0 || "unknown".equalsIgnoreCase(ipAddr)) {
			ipAddr = 	request.getRemoteAddr();
		}

		return ipAddr;
	}

	/**
	 *  Desc : 해당 연도 달의 첫번째 날과 마지막날을 구한다.
	 *  @Mehtod Name : getDefaultValue
	 *  @param str
	 *  @param str2 해당년월
	 *  @return
	 */
	public static String getDateOfMonth(String str, String str2) throws ParseException{
		Calendar cal = Calendar.getInstance();

		SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
		Date to = transFormat.parse(str2);
		cal.setTime(to);
		String day = "";
		//str : 시작일(first) or 종료일(last)
		if("first".equals(str)){
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.getActualMinimum(Calendar.DATE));
		}else if("last".equals(str)){
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.getActualMaximum(Calendar.DATE));
		}
		day = transFormat.format(cal.getTime());

		return day;
	}

	/**
	 * 미성년자 체크  2015/11/09 KIT
	 * EX) 19700915
	 * @param setHashMap
	 * @return
	 */
	public static boolean underAgeCheck(String personalId, int limitAge){

		boolean isUnder = false;

		String birthDay = "";

		birthDay = personalId.substring(0, 8);

		Calendar today = Calendar.getInstance();

		today.add(Calendar.YEAR, -limitAge);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		try {
			if(today.getTime().compareTo(format.parse(birthDay)) < 0){
				isUnder = true;
			}
		} catch (ParseException pe ) {
			isUnder = false;
		}

		return isUnder;

	}

	/**
	 *  Desc : 전화번호 형식의 문자열 내부에 하이픈(-)를 추가한다.
	 *  CmmnUtil.getFormatStr("01012345678") = "010-1234-5678"
	 *  @Mehtod Name : getFormatStr
	 *  @param text
	 *  @return
	 */
	public static String getFormatStr(String text) {
		if (text.length() == 11) {
			return text.substring(0, 3).concat("-").concat(text.substring(3, 7)).concat("-").concat(text.substring(7, 11));
		} else if (text.length() == 10) {
			return text.substring(0, 3).concat("-").concat(text.substring(3, 6)).concat("-").concat(text.substring(6, 10));
		} else if (text.length() == 12) {  //추가 2014/11/25 ok84j
			return text.substring(0, 4).concat("-").concat(text.substring(4, 8)).concat("-").concat(text.substring(8, 12));
		} else {
			return text;
		}
	}

	/**
	 * 주민번호 앞자리를 생년월일형식으로 변경
	 * @param sDate
	 * @return
	 * @throws ParseException
	 */
	public static String formatDateBday(String sDate) throws ParseException {
		sDate = sDate.substring(0, sDate.length()-1);
		SimpleDateFormat originalFormat = new SimpleDateFormat("yymmdd");
		SimpleDateFormat newFormat = new SimpleDateFormat("yyyy.mm.dd");

		Date originalDate = originalFormat.parse(sDate);
		String newDate = newFormat.format(originalDate);

		return newDate;
	}

	/**
	 * 주민번호 앞자리를 생년월일형식으로 요청한 패턴으로 변경
	 * @param sDate
	 * @return
	 * @throws ParseException
	 */
	public static String formatDateBday(String sDate, String pattern) throws ParseException {
		sDate = sDate.substring(0, sDate.length()-1);
		SimpleDateFormat originalFormat = new SimpleDateFormat("yymmdd");
		SimpleDateFormat newFormat = new SimpleDateFormat(pattern);

		Date originalDate = originalFormat.parse(sDate);
		String newDate = newFormat.format(originalDate);

		return newDate;
	}

	/**
	 *  Desc : 전화번호 형식인(13자리 ex: 010-1111-1111) 핸드폰번호를 ucube 요청 parameter인 prodNo(12자리)로 변환 010011111111
	 * @param prodNo
	 * @return
	 */
	public static String convertPhoneToProdNo(String ctn) {
		StringBuffer sbProdNo = new StringBuffer();
		if(ctn.length() == 13){
			sbProdNo.append(ctn);
			sbProdNo.insert(2, '0');
//			0100-1111-1111
			ctn = sbProdNo.toString();
			ctn = ctn.substring(0, 4).concat(ctn.substring(5, 9)).concat(ctn.substring(10, 14));
//			010011111111
		}else{
			sbProdNo.append(ctn);
		}
		return ctn;
	}

	public static void alertMsg(HttpServletResponse res, String msg, boolean isBack) throws Exception{
		PrintWriter p = new PrintWriter(res.getOutputStream());
		res.setContentType("text/html;charset=UTF-8");
		res.setCharacterEncoding("UTF-8");
		p.write("<script type='text/javascript'>alert('"+msg+"');"+(isBack?"history.back();":"")+"</script>");
		p.flush();
	}

	public static void alertRedirectMsg(HttpServletResponse res, String msg, String url) throws Exception{
		PrintWriter p = new PrintWriter(res.getOutputStream());
		res.setContentType("text/html;charset=UTF-8");
		res.setCharacterEncoding("UTF-8");
		p.write("<script type='text/javascript'>");

		if(!"".equals(StringUtil.nvl(msg))) {
            p.write("alert('"+msg+"');");
        }

		p.write("location.href='"+url+"';");
		p.write("</script>");
		p.flush();
	}

	public static void alertClosetMsg(HttpServletResponse res, String msg) throws Exception{
		PrintWriter p = new PrintWriter(res.getOutputStream());
		res.setContentType("text/html;charset=UTF-8");
		res.setCharacterEncoding("UTF-8");
		p.write("<script type='text/javascript'>");

		if(!"".equals(StringUtil.nvl(msg))) {
            p.write("alert('"+msg+"');");
        }

		p.write("self.opener = self;");
		p.write("window.close();");
		p.write("</script>");
		p.flush();
	}

	/**
	 * Describe :: 신청번호 채번 규칙 > yyyyMMddHHmmssSSS+랜덤숫자1자리+대리점번호
	 * @param   :: agentId
	 * @return  :: String
	 */
	public static String getApfmNo(String agentId) {
		//SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMddHHmmss" );
		//String dateStr = sdf.format( new Date() ) + agentId;

		//SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMddHHmmssSSS" );
		//Random random = new Random();
		//int randomInt = random.nextInt(10);				// 랜덤1자리
		//String dateStr = sdf.format( new Date() ) + String.valueOf(randomInt) + agentId;

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		long timeMillis = timestamp.getTime() + 20000000000000L;
		String dateStr = "" + timeMillis + "" + agentId;
		return dateStr ;
	}


	/**
	 *  Desc : 현재시간으로부터  월을 증감한다.
	 *  @Mehtod Name : addYearMonth
	 *  @param year
	 *  @param month
	 *  @param day
	 *  @return
	 */
	public static String addMonth(int month) {

		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

		cal.setTime(date);

		if (month != 0) {
            cal.add(Calendar.MONTH, month);
        }

		return sdf.format(cal.getTime());
	}

	/**
	 * map > vo 변환
	 * @param map
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static <T> T convertToValueObject(Map<String, Object> map, Class<T> type) throws Exception {
		if(type == null) {
			throw new NullPointerException("Class cannot be null");
		}

		T instance = type.getConstructor().newInstance();

		if(map == null || map.isEmpty()) {
			return instance;
		}

		for(Map.Entry<String, Object> entrSet : map.entrySet()) {
			Field[] fields = type.getDeclaredFields();

			for(Field field : fields) {
				field.setAccessible(true);

				String fieldName = field.getName();

				boolean isSameType = entrSet.getValue().getClass().equals(field.getType());
				boolean isSameName = entrSet.getKey().equals(fieldName);

				if(isSameType && isSameName) {
					field.set(instance, map.get(fieldName));
				}
			}
		}

		return instance;
	}

	/**
	 * list<map> > list<vo> 변환
	 * @param list
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> convertToValueObjects(List<Map<String, Object>> list, Class<T> type) throws Exception {
		if(list == null || list.isEmpty()){
			return Collections.emptyList();
		}

		List<T> convertList = new ArrayList<>();

		for(Map<String, Object> map : list) {
			convertList.add(CmmnUtil.convertToValueObject(map, type));
		}

		return convertList;
	}

	/**
	 * @param JSONObject
	 * @apiNote JSONObject를 Map<String, String> 형식으로 변환처리.
	 * @return Map<String,String>
	 * **/
	public static Map<String, Object> getMapFromJsonObject(JSONObject jsonObj) throws Exception {
		Map<String, Object> map = null;

		try {
			map = new ObjectMapper().readValue(jsonObj.toString(), Map.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * @param Map<String, Object>
	 * @apiNote Map<String, Object>를 JSONObject로 변환처리.
	 * @return JSONObject
	 * **/
	public static JSONObject convertMapToJson(Map<String, Object> map) {

		JSONObject json = new JSONObject();
		String key = "";
		Object value = null;
		for(Map.Entry<String, Object> entry : map.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			json.put(key, value);
		}
		return json;
	}

	//64Base 인코딩
	public static String encode64Base(String value) {
		if((value == null) || "".equals(value)) {
			return "";
		}
		else {
			byte[] targetBytes = value.getBytes();

			Encoder encoder = Base64.getEncoder();
			byte[] encodedBytes = encoder.encode(targetBytes);

			return new String(encodedBytes);
		}
	}
	/**
	 * 성년자 출생일 시작일  2021/12/27 kjs973
	 *
	 * @param limitAge
	 * @param dateFormat
	 * @return
	 */
	public static String overAgeStartYYYYMMDD(int limitAge,String dateFormat){
		Calendar today = Calendar.getInstance();

		today.add(Calendar.YEAR, -limitAge);
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		return format.format(today.getTime()).toString();
	}
}
