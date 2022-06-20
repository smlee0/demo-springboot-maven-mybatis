package com.medialog.uplussave.common.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.google.common.net.HttpHeaders;

/**
 * 공통 유틸리티 클래스
 * @filename CommUtil.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
public final class CommUtil {
	public static final int iterationNb = 1000;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private CommUtil() {
	}

	/**
	 * 해당 String이 null 이거나 length가 0인지 검사
	 * @param str
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.trim().length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 해당 Object가 null 인지 검사
	 * @param obj
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		try {
			if (((String) obj).trim().length() == 0) {
				return true;
			}
		} catch (Exception e) {}
		return false;
	}
	/**
	 * LTRIM 왼쪽 공백 제거
	 * @param source
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String ltrim(String source) {
		if (isEmpty(source)) {
			return "";
		}
		return source.replaceAll("^\\s+", "");
	}

	/**
	 * RTRIM 오른쪽 공백 제거
	 * @param source
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String rtrim(String source) {
		if (isEmpty(source)) {
			return "";
		}
		return source.replaceAll("\\s+$", "");
	}

	/**
	 * TRIM 왼쪽 / 오른쪽 공백 모두 제거
	 * @param source
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String trim(String source) {
		return ltrim(rtrim(source));
	}

	/**
	 * 해당 int를 넘어온 format 형식으로 반환하여 String type으로 반환
	 * @param num
	 * @param format
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String decimalFormat(int num, String format) {
		DecimalFormat df = new DecimalFormat(format);
		return df.format(num);
	}

	/**
	 * 해당 int에 세자리마다 콤마를 찍어 String type으로 반환한다.
	 * @param num
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String decimalFormat(int num) {
		return decimalFormat(num, "#,###");
	}

	/**
	 * 해당 long 값을 세자리 콤마를 찍어 String type으로 반환
	 * @param num
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String decimalFormat(long num) {
		return decimalFormat(num, "#,###");
	}

	/**
	 * 해당 long값을 format 형식으로 반환
	 * @param num
	 * @param format
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String decimalFormat(long num, String format) {
		DecimalFormat df = new DecimalFormat(format);
		return df.format(num);
	}

	/**
	 * 해당 double값을 세자리 콤마를 찍어 반환
	 * @param num
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String decimalFormat(double num) {
		return decimalFormat(num, "#,###.##");
	}

	/**
	 * 해당 float값을 세자리 콤마를 찍어 반환
	 * @param num
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String decimalFormat(float num) {
		return decimalFormat(num, "#,###.##");
	}

	/**
	 * 해당 double 값을 format 형식으로 반환
	 * @param num
	 * @param format
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String decimalFormat(double num, String format) {
		DecimalFormat df = new DecimalFormat(format);
		return df.format(num);
	}

	/**
	 * 문자열을 숫자형으로 변경
	 * @param str
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static int getIntVal(String str) {
		if (isEmpty(str)) {
			return 0;
		}
		return Integer.parseInt(nvl(str, "0"));
	}

	/**
	 * 글 자르기
	 * @param inputStr
	 * @param limit
	 * @return
	 * @throws UnsupportedEncodingException
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String cutStringLimit(String inputStr, int limit) throws UnsupportedEncodingException {
		String r_val = inputStr;
		int oF = 0, oL = 0, rF = 0, rL = 0;
		int limitPrev = 0;
		try {
			byte[] bytes = r_val.getBytes("UTF-8");
			int j = 0;
			if (limitPrev > 0) {
				while (j < bytes.length) {
					if ((bytes[j] & 0x80) != 0) {
						oF += 2;
						rF += 3;
						if (oF + 2 > limitPrev) {
							break;
						}
						j += 3;
					} else {
						if (oF + 1 > limitPrev) {
							break;
						}
						++oF;
						++rF;
						++j;
					}
				}
			}
			j = rF;
			while (j < bytes.length) {
				if ((bytes[j] & 0x80) != 0) {
					if (oL + 2 > limit) {
						break;
					}
					oL += 2;
					rL += 3;
					j += 3;
				} else {
					if (oL + 1 > limit) {
						break;
					}
					++oL;
					++rL;
					++j;
				}
			}
			r_val = new String(bytes, rF, rL, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return r_val;
	}

	/**
	 * 날짜 포맷(YYYY)
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String getYYYY() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Calendar cal = Calendar.getInstance();
		return sdf.format(cal.getTime());
	}

	/**
	 * 날짜 포맷(MM)
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String getMM() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		Calendar cal = Calendar.getInstance();
		return sdf.format(cal.getTime());
	}

	/**
	 * 날짜 포맷(YYYYMM)
	 * @param addMonth
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String getYYYYMM(int addMonth) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, addMonth);
		return sdf.format(cal.getTime());
	}

	/**
	 * 날짜 포맷(YYYYMMDD)
	 * @param format
	 * @param addDate
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String getYYYYMMDD(String format, int addDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, addDate);
		return sdf.format(cal.getTime());
	}

	/**
	 * 날짜 포맷(YYYYMMDDHH)
	 * @param addMin
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String getYYYYMMDDHH(int addMin) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, addMin);
		return sdf.format(cal.getTime());
	}

	/**
	 * UUID 생성기
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 *
	 * @param request
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String getRealRootPath(HttpServletRequest request) {
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		return rootPath;
	}

	/**
	 * 랜덤 토큰 생성
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String generateToken() {
		byte random[] = new byte[16];
		StringBuilder buffer = new StringBuilder();
		Random randomSource = new Random();
		randomSource.nextBytes(random);

		for (int j = 0; j < random.length; j++) {
			byte b1 = (byte) ((random[j] & 0xf0) >> 4);
			byte b2 = (byte) (random[j] & 0x0f);
			if (b1 < 10) {
				buffer.append((char) ('0' + b1));
			} else {
				buffer.append((char) ('A' + (b1 - 10)));
			}
			if (b2 < 10) {
				buffer.append((char) ('0' + b2));
			} else {
				buffer.append((char) ('A' + (b2 - 10)));
			}
		}

		return buffer.toString();
	}

	/**
	 * 랜덤 숫자 발생 10자리
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String getRandomKey() {
		String chararray = "123456789";
		Random random = new Random(System.currentTimeMillis());
		String tmpIdPassword = "";
		for (int i = 0; i < 16; i++) {
			tmpIdPassword += chararray.charAt(random.nextInt(chararray.length()));
		}
		return tmpIdPassword;
	}

	/**
	 * 6자리 인증코드 생성
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String getRandomCertNo() {
		String chararray = "0123456789";
		Random random = new Random(System.currentTimeMillis());
		String tmpIdPassword = "";
		for (int i = 0; i < 6; i++) {
			tmpIdPassword += chararray.charAt(random.nextInt(chararray.length()));
		}
		return tmpIdPassword;
	}

	public static boolean equals(String source, String target) {
		if (CommUtil.isEmpty(source) || CommUtil.isEmpty(target)) {
			return false;
		}

		return source.equals(target);
	}

	/**
	 * 문자열 빈 값 체크 및 기본값 설정
	 * @param value
	 * @param defaultValue
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String nvl(String value, String defaultValue)	{
		return ( isEmpty(value) ) ? defaultValue : value;
	}

	/**
	 * Object 빈 값 체크 및 기본값 설정
	 * @param value
	 * @param defaultValue
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String nvl(Object value, String defaultValue) {
		return ( isEmpty(value) ) ? defaultValue : String.valueOf(value);
	}

	/**
	 * 문자열 빈 값 체크 및 기본값 설정
	 * @param value
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String nvl(String value) {
		return nvl(value, "");
	}

	/**
	 * Object 빈 값 체크 및 기본값 설정
	 * @param value
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String nvl(Object value)	{
		return nvl(value, "");
	}

	/**
	 * 클라이언트 IP 주소 획득
	 * @param request
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-FORWARDED-FOR");

		if (isEmpty(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (isEmpty(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (isEmpty(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * HTML 태그 제거
	 * @param html
	 * @return
	 * @throws Exception
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String removeTag(String html) throws Exception {
		return html.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
	}

	/**
	 * 관리자 비밀번호 생성
	 * @param pwd
	 * @param pwdKey
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String getEncryptAdmPwd(String pwd, String pwdKey) {
		if ( !isEmpty(pwd) && !isEmpty(pwdKey) ) {
			return getSHA512Hash(pwd, Long.parseLong(pwdKey));
		} else {
			return "";
		}
	}

	/**
	 * SHA512 암호화
	 * @param password
	 * @param salt
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String getSHA512Hash(String password, long salt) {
		String strEencrypt = "";
		byte[] byteSalt;

		byteSalt = intToByteArray(salt);

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			digest.reset();
			digest.update(byteSalt);
			byte[] input = digest.digest(password.getBytes("UTF-8"));
			for (int i = 0; i < iterationNb; i++) {
				digest.reset();
				input = digest.digest(input);
			}
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < input.length; i++) {
				hexString.append(Integer.toString((input[i] & 0xff) + 0x100, 16).substring(1));
			}
			strEencrypt = hexString + "";
		} catch (NoSuchAlgorithmException nsae) {
			System.out.println("[ERROR] = [" + nsae.getMessage() + "]");
			return ("");
		} catch (UnsupportedEncodingException e) {
			System.out.println("[ERROR] = [" + e.getMessage() + "]");
			return ("");
		}
		return strEencrypt;
	}

	/**
	 * int를 바이트 배열로 변경(?)
	 * @param data
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static byte[] intToByteArray(long data) {
		return new byte[] { (byte) ((data >> 56) & 0xff), (byte) ((data >> 48) & 0xff), (byte) ((data >> 40) & 0xff),
				(byte) ((data >> 32) & 0xff), (byte) ((data >> 24) & 0xff), (byte) ((data >> 16) & 0xff),
				(byte) ((data >> 8) & 0xff), (byte) ((data >> 0) & 0xff) };
	}

	/**
	 * ???
	 * @param v
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String cleanFName(String v) {
		if (CommUtil.isEmpty(v)) {
			return CommUtil.getYYYYMMDDHH(0);
		}
		return v.replace("%#&\\$", "");
	}

	/**
	 * HTML 태그 제거
	 * @param value
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String cleanXSS(String value) {
		String cleanValue = null;
		if (value != null) {
			//cleanValue = Normalizer.normalize(value, Normalizer.Form.NFD);
			cleanValue = Normalizer.normalize(value, Normalizer.Form.NFC);

			// Avoid null characters
			cleanValue = cleanValue.replaceAll("\0", "");

			// Avoid anything between script tags
			Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
			cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

			// Avoid anything in a src='...' type of expression
			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

			// Remove any lonesome </script> tag
			scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
			cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

			// Remove any lonesome <script ...> tag
			scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

			// Avoid eval(...) expressions
			scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

			// Avoid expression(...) expressions
			scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

			// Avoid javascript:... expressions
			scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
			cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

			// Avoid vbscript:... expressions
			scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
			cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

			// Avoid onload= expressions
			scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
		}
		//cleanValue = stripTag(cleanValue);
		return cleanValue;
	}

	/**
	 * HTML 태그 제거(Arr)
	 * @param value
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String[] cleanXSSArr(String[] value) {
		for(int i= 0; i < value.length; i++){
			value[i] = cleanXSS(value[i]);
		}
		return value;
	}

	/**
	 * 문자열을 배열로 변경( \\, )
	 * @param str
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String[] getStrArr(String str) {
		if (isEmpty(str)) {
			return null;
		}
		String[] strings = str.split("\\,");

		return strings;
	}

	/**
	 * 숫자 배열 리턴
	 * @param str
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static int[] getIntArr(String str) {
		if (isEmpty(str)) {
			return null;
		}
		String[] strings = str.split("\\,");
		int[] intVal = new int[strings.length];
		for (int i = 0; i < intVal.length; i++) {
			intVal[i] = getIntVal(strings[i]);
		}
		Arrays.sort(intVal);
		return intVal;
	}

	/**
	 * int 값 리턴
	 * @param p
	 * @param dfVal
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static int parseIntValue(String p, String dfVal) {
		int pageIndex = 0;
		try {
			pageIndex = Integer.parseInt(p);
		} catch(NumberFormatException ex) {
			try {
				pageIndex = Integer.parseInt(dfVal);
			} catch(NumberFormatException ex2) {
				pageIndex = 1;
			}
		}
		return pageIndex;
	}

	/**
	 * 0 채워진 문자열 리턴
	 * @param Num
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String zeroFillString(int Num) {
		return String.format("%02d", Num);
	}

	/**
	 * 전달된 변수값의 태그 제거 처리
	 * @param req
	 * @return
	 * @throws Exception
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static HashMap<String, Object> getMap(HttpServletRequest req) throws Exception {
		Enumeration<String> e1 = req.getParameterNames();
		HashMap<String, Object> parMap = new HashMap<>();
		while (e1.hasMoreElements()) {
			String key = e1.nextElement();
			parMap.put(key, CommUtil.cleanXSS(req.getParameter(key)));
		}
		e1 = null;
		req.setAttribute("paramMap", parMap);
		return parMap;
	}

	/**
	 * URLEncoder로 변환된 문자열 반환
	 * @param str
	 * @return
	 * @throws Exception
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String URLEncode(String str) throws Exception {
		String result = "";
		try {
			if(str != null) {
				result = URLEncoder.encode(str, "UTF-8");
			}
		} catch (Exception ex) {
			throw new Exception("StringManager.URLEncode(\""+str+"\")\r\n"+ex.getMessage());
		}
		return result;
	}

	/**
	 * URLDecoder로 변환된 문자열 반환
	 * @param str
	 * @return
	 * @throws Exception
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String URLDecode(String str) throws Exception {
		String result = "";
		try {
			if(str != null) {
				result = URLDecoder.decode(str, "UTF-8");
			}
		} catch (Exception ex) {
			throw new Exception("StringManager.URLDecode(\""+str+"\")\r\n"+ex.getMessage());
		}
		return result;
	}

	/**
	 * ???
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static HttpServletRequest getCurrentRequest() {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest servletRequest = sra.getRequest();
		return servletRequest;
	}

	/**
	 * ???
	 * @param sdate
	 * @param edate
	 * @param stat
	 * @return
	 * @throws Exception
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String getDisplayStatus(String sdate, String edate, String stat) throws Exception {
		String rtn = "";
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");

		String nowDT = transFormat.format(new Date());

		if (!CommUtil.isEmpty(sdate) && !CommUtil.isEmpty(edate) && !CommUtil.isEmpty(stat)) {
			Date from = transFormat.parse(sdate);
			Date to = transFormat.parse(edate);
			Date now = transFormat.parse(nowDT);

			if ("1".equals(stat)) {
				//open
				int compare1 = now.compareTo(from);
				if (compare1 >= 0) {
					//open
					rtn = "O";
					int compare2 = to.compareTo(now);
					if (compare2 >= 0) {
						//open
						rtn = "O";
					} else {
						//close
						rtn = "X";
					}
				} else {
					rtn = "X";
				}
			} else {
				//close
				rtn = "X";
			}
		} else if (!CommUtil.isEmpty(sdate) && !CommUtil.isEmpty(stat)) {
			Date from = transFormat.parse(sdate);
			Date now = transFormat.parse(nowDT);

			if ("1".equals(stat)) {
				//open
				int compare1 = now.compareTo(from);
				if (compare1 >= 0) {
					//open
					rtn = "O";
				} else {
					rtn = "X";
				}
			} else {
				//close
				rtn = "X";
			}
		} else if (!CommUtil.isEmpty(edate) && !CommUtil.isEmpty(stat)) {
			Date to = transFormat.parse(edate);
			Date now = transFormat.parse(nowDT);
			if ("1".equals(stat)) {
				//open
				int compare2 = to.compareTo(now);
				if (compare2 >= 0) {
					//open
					rtn = "O";
				} else {
					//close
					rtn = "X";
				}
			} else {
				//close
				rtn = "X";
			}
		}
		return rtn;
	}

	/**
	 * 파일사이즈 구하기
	 * @param fsize
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String getFileSize(String fsize) {
		String rtn = "";
		String[] units = {" Byte", " KB", " MB"};
		long filesize = 0;
		int gkey = 0;
		double changeSize = 0;

		try {
			filesize = Long.parseLong(fsize);
			for (int x = 0; (filesize / (double)1024) > 0; x++, filesize /= (double)1024) {
				gkey = x;
				changeSize = filesize;
			}
			rtn = changeSize + units[gkey];
		} catch (Exception e) {
			rtn = "0.0 Byte";
		}
		return rtn;
	}

	/**
	 * 특정 문자열을 소문자로 변경 후 리턴
	 * @param v
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String lowerCase(String v) {
		if (CommUtil.isEmpty(v)) {
			return "";
		}
		return v.toLowerCase();
	}

	/**
	 * 해당 패턴이 존재하면 Replace 진행("\\<.*?\\>")
	 * @param inval
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String stripTag(String inval) {
		if (inval != null && !CommUtil.isEmpty(inval)) {
			return inval.replaceAll("\\<.*?\\>", "");
		}
		return "";
	}

	/**
	 * 랜덤 비밀번호 추출
	 * @param len
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String getRamdomPassword(int len) {
		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		int idx = 0;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < len; i++) {
			idx = (int) (charSet.length * Math.random()); // 36 * 생성된 난수를 Int로 추출 (소숫점제거)
			System.out.println("idx :::: "+idx);
			sb.append(charSet[idx]);
		}
		return sb.toString();
	}

	/**
	 * 이메일발송 템플릿 양식 가져오기
	 * @param strPath
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String getEmailTemplate(String strPath) {
		BufferedReader strRead = null;
		StringBuffer strRtn = new StringBuffer();
		String templatePath = strPath;

		try {
			strRead = new BufferedReader(new InputStreamReader(new FileInputStream(templatePath), "UTF-8"));

			String line;
			while ((line = strRead.readLine()) != null) {
				strRtn.append(line);
				strRtn.append("\n");
			}
		} catch (Exception var13) {
			var13.printStackTrace();
			System.out.println(var13.getMessage());
		} finally {
			if (strRead != null) {
				try {
					strRead.close();
				} catch (IOException var12) {
					var12.printStackTrace();
				}
			}

		}
		return strRtn.toString();
	}

	/**
	 * 전화번호 포맷
	 * @param src
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static String phoneFormat(String src) {
		if (src == null) {
			return "";
		}
		if (src.length() == 8) {
			return src.replaceFirst("^([0-9]{4})([0-9]{4})$", "$1-$2");
		} else if (src.length() == 12) {
			return src.replaceFirst("(^[0-9]{4})([0-9]{4})([0-9]{4})$", "$1-$2-$3");
		}
		return src.replaceFirst("(^02|[0-9]{3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3");
	}

	/**
	 * JSON 문자열을 HTTP 응답으로 출력
	 * @param response
	 * @param jsonText
	 * @throws IOException
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static void jsonToResponse(HttpServletResponse response, String jsonText)
			throws IOException {
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.getOutputStream().write(jsonText.getBytes(StandardCharsets.UTF_8.name()));
	}

	/**
	 * 모바일 디바이스 체크
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	public static Boolean isMobileDevice() {
		HttpServletRequest request = CommUtil.getCurrentRequest();
		String userAgent = request.getHeader(HttpHeaders.USER_AGENT);

		return !StringUtils.isEmpty(userAgent) && Pattern
				.compile("(iphone|ipad|ipod|android)", Pattern.CASE_INSENSITIVE).matcher(userAgent).find();
	}

	/**
	 * PC이면 Y
	 * @param req
	 * @return
	 * @throws Exception
	 * @author Lee Se Min
	 * @date 2022-06-14
	 */
	public static boolean checkDev(HttpServletRequest request) throws Exception {
		Device device = DeviceUtils.getCurrentDevice(request);

		if (!device.isNormal() && !device.isTablet()){
			//  retrieveMenu(request,  model);
		}

		if (device.isTablet()){
			return device.isTablet();
		} else {
			return device.isNormal();
		}
	}

	/**
	 * 디바이스 판별하여 PC/MOBILE 페이지 분기처리
	 * @param request
	 * @param rtnUrl
	 * @return
	 * @throws Exception
	 * @author Lee Se Min
	 * @date 2022-06-15
	 */
	public static String checkDevReturnPageUrl(HttpServletRequest request, String rtnUrl) throws Exception {
		boolean plaform = CommUtil.checkDev(request);
		String tmpRtnUrl;

		if (plaform) {
			tmpRtnUrl = Constants.VIEW_PATH_PC + rtnUrl;
		} else {
			tmpRtnUrl = Constants.VIEW_PATH_MOBILE + rtnUrl;
		}

		return tmpRtnUrl;
	}
}
