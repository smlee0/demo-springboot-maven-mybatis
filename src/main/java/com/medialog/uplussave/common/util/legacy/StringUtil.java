
package com.medialog.uplussave.common.util.legacy;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Base64;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class StringUtil {
	private final static String WHITE_SPACE = " \t\n\r\f";

	private StringUtil() {}

	public static String toString(Object obj)
	{
		if(obj == null)
		{
			return null;
		}
		else if(obj instanceof Vector)
		{
			return toString((Vector<?>)obj);
		}
		else if(obj instanceof Document)
		{
			return toString(obj);
		}
		else
		{
			return obj.toString();
		}
	}

	public static String toString(Vector<?> v)
	{
		if(v == null) {
            return null;
        }
		Object obj = v.firstElement();
		if(obj instanceof Document)
		{
			StringBuffer sb = new StringBuffer("[");
			for ( int i = 0 ; i < v.size(); i++)
			{
				if(i != 0) {
                    sb.append(", ");
                }
				sb.append(toString(v.elementAt(i)));
			}
			sb.append("]");
			return sb.toString();
		}
		else
		{
			return v.toString();
		}
	}
/*
	public static String toString(Document doc)
	{
		return DOMWriter.nodeToString(doc);
	}
*/

	/** String[val1, val2, val3]; Vector?? convert  **/
/*
	public static Vector convertVector(String val)
	{
		if(val == null) return null;
		if(val.startsWith("["))
		{
			if(val.substring(val.length()-1).equals("]"))
			{
				val = val.substring(1,val.length()-1);
				Vector v = new Vector();
				if(val.startsWith("<?"))
				{
					JStringTokenizer st = new JStringTokenizer(val,">, ");
					while(st.hasMoreTokens())
					{

						String xml = st.nextToken();
						if(!StringUtil.right(xml,1).equals(">"))
						{
							xml += ">";
						}
						try
						{
							v.add(parseXML(xml));
						}
						catch(Exception e)
						{
							System.out.println(xml);
							System.out.println(DateUtil.getCurrentTime("yyyyMMdd HHmmss")); e.printStackTrace();
						}
					}
				}
				else
				{
					JStringTokenizer st=new JStringTokenizer(val, ", ");
					String oneToken ="" ;
					while(st.hasMoreTokens())
					{
						oneToken = st.nextToken(", ");
						Object obj=oneToken.trim();
						int pos = oneToken.indexOf("{");
						int pos1= oneToken.indexOf("[");
						String delim = null;
						if(pos>pos1)
						{
							delim="}";
						}
						else if(pos < pos1)
						{
							delim = "]";
						}
						if(delim != null)
						{
							oneToken += ", " + st.nextToken(delim + ", ");
							if(st.hasMoreTokens())
							{
								oneToken  += delim;
							}
							if(delim.equals("}"))
							{
								obj = convertHashMap(oneToken);
							}
							else
							{
								obj = convertVector(oneToken);
							}
						}

						v.add(obj);
					}

         	}
				return v;
			}
		}
		return null;
	}
*/
	/** ?????(str)???? ??? ?????(repleatStr)?? ?? ?? ?????? **/
	public static int getRepeatCount(String str, String repeatStr)
	{
		int cnt = 0;
		int pos = -1;
		while((pos=str.indexOf(repeatStr)) >=0)
		{
			str=str.substring(pos+repeatStr.length());
			cnt++;
		}
		return cnt;
	}

	/** String{key1=val1, key2=val2, key3=val3}; HashMap8?? convert  **/
/*
	public static HashMap convertHashMap(String val)
	{
		if(val == null) return null;
		int pos = val.indexOf("{");
		HashMap map = null;
		int pos1= val.lastIndexOf("}");
		if(pos1 > pos)
		{
			val = val.substring(pos+1, pos1);
			JStringTokenizer st = new JStringTokenizer(val, ", ");
			map = new HashMap();
			String oneToken ="" ;
			String key = null;
			while(st.hasMoreTokens())
			{
				oneToken = st.nextToken(", ");

				if(oneToken.indexOf("={") >=0)
				{
					int pos2 = oneToken.indexOf("},");
					boolean result = false;
					if(pos2>=0)
					{
						result = right(oneToken,1).equals("}");
					}
					else
					{
						result = true;
					}
					if(result==true)
					{
						oneToken += ", " +  st.nextToken("}, ");
						if(st.hasMoreTokens())
						{
							oneToken  += "}";
						}
					}
				}
				else if(	oneToken.indexOf("=[") >=0)
				{
					int pos2 = oneToken.indexOf("]");
					boolean result = false;
					if(pos2>=0)
					{
						result = right(oneToken,1).equals("]");
					}
					else
					{
						result = true;
					}
					if(result==true)
					{
						oneToken += ", " +  st.nextToken("], ");
						//System.out.println("+++++++++++++++" + oneToken);
						if(st.hasMoreTokens())
						{
							oneToken  += "]";
						}
					}
				}

//				if(oneToken.indexOf("{") >=0 || oneToken.indexOf("[") >=0)
//				{
//					if(getRepeatCnt(oneToken,"{") > getRepeatCnt(oneToken,"}") || getRepeatCnt(oneToken,"[") > getRepeatCnt(oneToken,"]")  )
//					{
//						oneToken += ", ";
//						continue;
//					}
				}

				int pos3 = oneToken.indexOf("=");
				if(pos3 >=0 )
				{
					key = oneToken.substring(0,pos3).trim();
					map.put(key, oneToken.substring(pos3+1).trim());
					oneToken = "";
					//System.out.println("put  "  + oneToken.substring(0,pos3) + "," + oneToken.substring(pos3+1));
				}
				else
				{
					if(key != null)
					{
						String newVal = ((String)map.get(key)) +", " + oneToken;
						map.put(key, newVal);
					}
				}
			}
		}
		return map;
	}
*/
	public static Document parseXML(DocumentBuilder builder,String xml) throws  SAXException,IOException
	{

		if(xml == null) {
            return null;
        }
		return builder.parse(new InputSource(new StringReader(xml)));
	}
	public static Document parseXML(String xml) throws   SAXException,IOException,FactoryConfigurationError,ParserConfigurationException
	{
		if(xml == null) {
            return null;
        }
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		return parseXML(builder, xml);
	}

	/**
	 * Returns a <code>boolean</code> with a value represented by the specified String.
	 *
	 * The <code>boolean</code> returned represents the value true if the string argument is equal, ignoring case, to the string "true".
	 * If the string argument is null, returns the default value argument.
	 *
	 * @param value a string.
	 * @param defaultValue default value.
	 * @return the <code>boolean</code> value represented by the string.
	 */
	public static boolean parseBoolean(String value, boolean defaultValue)
	{
		return (value == null) ? defaultValue : Boolean.valueOf(value).booleanValue();
	}

	/**
	 * Returns a <code>boolean</code> with a value represented by the specified String.
	 *
	 * The <code>boolean</code> returned represents the value true if the string argument is equal, ignoring case, to the string "true".
	 * If the string argument is null, return false.
	 *
	 * @param value a string.
	 * @return the <code>boolean</code> value represented by the string.
	 */
	public static boolean parseBoolean(String value)
	{
		return parseBoolean(value, false);
	}

	//??????????????????????????????????????????????????

	/**
	 * Returns the first character.
	 *
	 * if the first character is not exist (string argument is null or empty), returns the default value argument.
	 *
	 * @param value a string.
	 * @param defaultValue default value.
	 * @return the first character.
	 */
	public static char parseChar(String value, char defaultValue)
	{
		return (value == null || value.equals("")) ? defaultValue : value.charAt(0);
	}

	/**
	 * Returns the first character.
	 *
	 * If the first character is not exist (string argument is null or empty), return (char) 0.
	 *
	 * @param value a string.
	 * @return the first character.
	 */
	public static char parseChar(String value)
	{
		return parseChar(value, (char) 0);
	}

	//??????????????????????????????????????????????????

	/**
	 * Parses the string argument as a signed decimal integer.
	 *
	 * If the string argument is null or empty or not a number(<code>NumberFormatException<code> occur),
	 * return default value argument.
	 *
	 * @param value a string.
	 * @param defaultValue default value.
	 * @return the integer represented by the argument in decimal.
	 */
	public static int parseInt(String value, int defaultValue)
	{
		try
		{
			return (value == null || value.equals("") ) ? defaultValue : Integer.parseInt(value);
		}
		catch (NumberFormatException e)
		{
			return defaultValue;
		}
	}

	/**
	 * Parses the string argument as a signed decimal integer.
	 *
	 * If the string argument is null or empty or not a number(<code>NumberFormatException<code> occur),
	 * return -1.
	 *
	 * @param value a string.
	 * @return the integer represented by the argument in decimal.
	 */
	public static int parseInt(String value)
	{
		return parseInt(value, 0);
	}

	//??????????????????????????????????????????????????

	/**
	 * Parses the string argument as a signed decimal <code>long</code>.
	 *
	 * If the string argument is null or empty or not a number(<code>NumberFormatException<code> occur),
	 * return default value argument.
	 *
	 * @param value a string.
	 * @param defaultValue default value.
	 * @return the <code>long</code> represented by the argument in decimal.
	 */
	public static long parseLong(String value, long defaultValue)
	{
		try
		{
			return (value == null || value.equals("") ) ? defaultValue : Long.parseLong(value);
		}
		catch (NumberFormatException e)
		{
			return defaultValue;
		}
	}

	/**
	 * Parses the string argument as a signed decimal <code>long</code>.
	 *
	 * If the string argument is null or empty or not a number(<code>NumberFormatException<code> occur),
	 * return -1L.
	 *
	 * @param value a string.
	 * @return the <code>long</code> represented by the argument in decimal.
	 */
	public static long parseLong(String value)
	{
		return parseLong(value, -1L);
	}

	//??????????????????????????????????????????????????

	/**
	 * Returns a new <code>float</code> initialized to the value represented by the specified <code>String</code>.
	 *
	 * If the string argument is null or empty or not a number(<code>NumberFormatException<code> occur),
	 * return default value argument.
	 *
	 * @param value a string.
	 * @param defaultValue default value.
	 * @return the <code>float</code> represented by the argument.
	 */
	public static float parseFloat(String value, float defaultValue)
	{
		try
		{
			return (value == null || value.equals("") ) ? defaultValue : Float.parseFloat(value);
		}
		catch (NumberFormatException e)
		{
			return defaultValue;
		}
	}

	/**
	 * Returns a new <code>float</code> initialized to the value represented by the specified <code>String</code>.
	 *
	 * If the string argument is null or empty or not a number(<code>NumberFormatException<code> occur),
	 * return -1.0F.
	 *
	 * @param value a string.
	 * @return the <code>float</code> represented by the argument.
	 */
	public static float parseFloat(String value)
	{
		return parseFloat(value, -1.0F);
	}

	//??????????????????????????????????????????????????

	/**
	 * Returns a new <code>double</code> initialized to the value represented by the specified <code>String</code>.
	 *
	 * If the string argument is null or empty or not a number(<code>NumberFormatException<code> occur),
	 * return default value argument.
	 *
	 * @param value a string.
	 * @param defaultValue default value.
	 * @return the <code>double</code> represented by the argument.
	 */
	public static double parseDouble(String value, double defaultValue)
	{
		try
		{
			return (value == null || value.equals("") ) ? defaultValue : Double.parseDouble(value);
		}
		catch (NumberFormatException e)
		{
			return defaultValue;
		}
	}

	/**
	 * Returns a new <code>double</code> initialized to the value represented by the specified <code>String</code>.
	 *
	 * If the string argument is null or empty or not a number(<code>NumberFormatException<code> occur),
	 * return -1.0.
	 *
	 * @param value a string.
	 * @return the <code>double</code> represented by the argument.
	 */
	public static double parseDouble(String value)
	{
		return parseDouble(value, -1.0);
	}

	//??????????????????????????????????????????????????

	/**
	 * If the value argument value is null or empty, returns defaultValue argument value;
	 * if the value argument value is not null and not empty, returns value argument value.
	 *
	 * @param value a string.
	 * @param defaultValue default value.
	 * @return the string argument or default value argument.
	 * @see #nvl(String value, String defaultValue)
	 */
	public static String evl(String value)
	{
		return ( value == null || value.equals("") ) ? "" : value;
	}
	public static String evl(String value, String defaultValue)
	{
		return ( value == null || value.equals("") ) ? defaultValue : value;
	}

	/**
	 * If the value argument value is null, returns "";
	 * if the value argument value is not null, returns value argument value.
	 *
	 * @param value a string.
	 * @param defaultValue default value.
	 * @return the string argument or default value argument.
	 * @see #evl(String value, String defaultValue)
	 */
	public static String nvl(String value)
	{
		return nvl( value , "" );
	}
	public static String nvl(Object value)
	{
		return nvl( value , "" );
	}

	/**
	 * ??????? null ???? new String[0] ????
	 * ????迭???? null ?? ????? ???? ???? null ; "" 8?? ????? ????
	 *
	 * @param value a string[].
	 * @param defaultValue default value.
	 * @return the string argument or default value argument.
	 */
	public static String[] nvls(String[] values){

		return nvls(values,"");
	}

	/**
	 * If the value argument value is null, returns defaultValue argument value;
	 * if the value argument value is not null, returns value argument value.
	 *
	 * @param value a string.
	 * @param defaultValue default value.
	 * @return the string argument or default value argument.
	 * @see #evl(String value, String defaultValue)
	 */
	public static String nvl(String value, String defaultValue)
	{
		return ( value == null ) ? defaultValue : value;
	}
	public static String nvl(Object value, String defaultValue)
	{
		String	resultStr	= "";
		if(value instanceof String) {
			resultStr 	= ((String)value);
		}
		else if(value instanceof BigDecimal) {
			resultStr 	= ((BigDecimal)value).toString();
		}
		else if(value instanceof Double) {
			resultStr 	= ((Double)value).toString();
		}
		return ( value == null ) ? defaultValue : resultStr;
	}



	/**
	 * ??????? null ???? new String[0] ????
	 * ????迭???? null ?? ????? ???? ???? null ; [defaultValue] 8?? ????? ????
	 *
	 * @param value a string[].
	 * @param defaultValue default value.
	 * @return the string[] argument or default value argument.
	 */
	public static String[] nvls(String[] values, String defaultValue){

		if(values == null) {
            return new String[0];
        }

		String[] str = new String[values.length];

		for(int i=0; i<str.length; i++){
			str[i] = nvl(values[i],defaultValue);
		}
		return str;
	}

	//??????????????????????????????????????????????????

	/**
	 * Searches for the first occurence of the given element argument, beginning the search at index,
	 * and testing for equality using the equals method.
	 *
	 * @param arr <code>java.lang.Object</code> Array
	 * @param element an object.
	 * @param index the non-negative index to start searching from.
	 * @return the index of the first occurrence of the element argument in the object array argument at position index.
	 * returns -1 if the element object is not found in the object array argument.
	 */
	public static int indexOf(Object[] arr, Object element, int index)
	{
		if (arr == null) {
            return -1;
        }

		for (int i=index; i<arr.length; i++)
		{
			if (arr[i] == null)
			{
				if (element==null) {
                    return i;
                }
			}
			else
			{
				if (element==null) {
                    continue;
                }
				if (arr[i].equals(element)) {
                    return i;
                }
			}
		}

		return -1;
	}

	/**
	 * Searches for the first occurence of the given element argument, testing for equality using the equals method.
	 *
	 * @param arr <code>java.lang.Object</code> Array
	 * @param element an object.
	 * @return the index of the first occurrence of the argument in the object array,
	 * returns -1 if the element object is not found in the object array argument.
	 */
	public static int indexOf(Object[] arr, Object element)
	{
		return indexOf(arr, element, 0);
	}

	//??????????????????????????????????????????????????

	/**
	 * Searches backwards for the specified object, starting from the specified index, and returns an index to it.
	 *
	 * @param arr <code>java.lang.Object</code> Array
	 * @param element an object.
	 * @param index the index to start searching from.
	 * @return the index of the last occurrence of the specified object in the object array argument at position less than
	 * or equal to index in the object array.
	 * returns -1 if the element object is not found in the object array argument.
	 */
	public static int lastIndexOf(Object[] arr, Object element, int index)
	{
		if (arr == null) {
            return -1;
        }

		for (int i=arr.length-index; i>=0; i--)
		{
			if (arr[i] == null)
			{
				if (element==null) {
                    return i;
                }
			}
			else
			{
				if (element==null) {
                    continue;
                }
				if (arr[i].equals(element)) {
                    return i;
                }
			}
		}

		return -1;
	}

	/**
	 * Returns the index of the last occurrence of the specified object in the object array argument.
	 *
	 * @param arr <code>java.lang.Object</code> Array
	 * @param element an object.
	 * @return the index of the last occurrence of the specified object in this the object array argument;
	 * returns -1 if the element object is not found.
	 */
	public static int lastIndexOf(Object[] arr, Object element)
	{
		return lastIndexOf(arr, element, 1);
	}

	//??????????????????????????????????????????????????

	/**
	 * Removes white space from both ends of the string argument.
	 *
	 * If the argument string is null, returns empty string (&quot;&quot;).<br>
	 * <caption>white space</caption>
	 * <table>
	 * <tr><td>'\t'</td>            <td>&#92;u0009</td>
	 *     <td><code>HORIZONTAL TABULATION</code></td></tr>
	 * <tr><td>'\n'</td>            <td>&#92;u000A</td>
	 *     <td><code>NEW LINE</code></td></tr>
	 * <tr><td>'\f'</td>            <td>&#92;u000C</td>
	 *     <td><code>FORM FEED</code></td></tr>
	 * <tr><td>'\r'</td>            <td>&#92;u000D</td>
	 *     <td><code>CARRIAGE RETURN</code></td></tr>
	 * <tr><td>'&nbsp;&nbsp;'</td>  <td>&#92;u0020</td>
	 *     <td><code>SPACE</code></td></tr>
	 * </table>
	 *
	 * @param value a string.
	 * @return a trimed string, with white space removed from the front and end.
	 * @see java.lang.String#trim().
	 * @see #ltrim(String value).
	 * @see #rtrim(String value).
	 */
	public static String trim(String value)
	{
		return (value == null) ? "" : value.trim();
	}

	/**
	 * Removes white space from left ends of the string argument.
	 *
	 * If the argument string is null, returns empty string (&quot;&quot;).
	 *
	 * @param value a string.
	 * @return a left trimed string, with white space removed from the front.
	 * @see java.lang.String#trim().
	 * @see #trim(String value).
	 * @see #rtrim(String value).
	 */
	public static String ltrim(String value)
	{
		if (value == null) {
            return "";
        }

		for (int i=0; i<value.length(); i++)
		{
			if ( WHITE_SPACE.indexOf( value.charAt(i) ) == -1 ) {
                return value.substring(i);
            }
		}

		return "";
	}

	/**
	 * Removes white space from right ends of the string argument.
	 *
	 * If the argument string is null, returns empty string (&quot;&quot;).
	 *
	 * @param value a string.
	 * @return a right trimed string, with white space removed from the end.
	 * @see java.lang.String#trim().
	 * @see #trim(String value).
	 * @see #ltrim(String value)
	 */
	public static String rtrim(String value)
	{
		if ( value == null || value.equals("") ) {
            return "";
        }

		for (int i=value.length()-1; i>=0; i--)
		{
			if ( WHITE_SPACE.indexOf( value.charAt(i) ) == -1 ) {
                return value.substring(0, i+1);
            }
		}

		return "";
	}

	//??????????????????????????????????????????????????

	/**
	 * Returns value argument,
	 * left-padded to length padLen argument with the sequence of character in padChar argument.
	 *
	 * If the value argument is null, value argument think of empty string (&quot;&quot;).
	 *
	 * @param value a string value.
	 * @param padLen the total length of the return value.
	 * @param padChar padded character.
	 * @return left padded string.
	 */
	public static String lpad(String value, int padLen, char padChar)
	{
		if (value == null) {
            value = "";
        }

		while (value.length() < padLen)
		{
			value = padChar + value;
		}

		return value;
	}

	/**
	 * Returns value argument,
	 * left-padded to length padLen argument with the sequence of character in padChar argument.
	 *
	 * @param value a short value.
	 * @param padLen the total length of the return value.
	 * @param padChar padded character.
	 * @return left padded string.
	 */
	public static String lpad(short value, int padLen, char padChar)
	{
		return lpad( String.valueOf(value), padLen, padChar );
	}

	/**
	 * Returns value argument,
	 * left-padded to length padLen argument with the sequence of character in padChar argument.
	 *
	 * @param value a int value.
	 * @param padLen the total length of the return value.
	 * @param padChar padded character.
	 * @return left padded string.
	 */
	public static String lpad(int value, int padLen, char padChar)
	{
		return lpad( String.valueOf(value), padLen, padChar );
	}

	/**
	 * Returns value argument,
	 * left-padded to length padLen argument with the sequence of character in padChar argument.
	 *
	 * @param value a long value.
	 * @param padLen the total length of the return value.
	 * @param padChar padded character.
	 * @return left padded string.
	 */
	public static String lpad(long value, int padLen, char padChar)
	{
		return lpad( String.valueOf(value), padLen, padChar );
	}

	/**
	 * Returns value argument,
	 * left-padded to length padLen argument with the sequence of character in padChar argument.
	 *
	 * @param value a float value.
	 * @param padLen the total length of the return value.
	 * @param padChar padded character.
	 * @return left padded string.
	 */
	public static String lpad(float value, int padLen, char padChar)
	{
		return lpad( String.valueOf(value), padLen, padChar );
	}

	/**
	 * Returns value argument,
	 * left-padded to length padLen argument with the sequence of character in padChar argument.
	 *
	 * @param value a double value.
	 * @param padLen the total length of the return value.
	 * @param padChar padded character.
	 * @return left padded string.
	 */
	public static String lpad(double value, int padLen, char padChar)
	{
		return lpad( String.valueOf(value), padLen, padChar );
	}

	//??????????????????????????????????????????????????

	/**
	 * Returns value argument,
	 * right-padded to length padLen argument with the sequence of character in padChar argument.
	 *
	 * If the value argument is null, value argument think of empty string (&quot;&quot;).
	 *
	 * @param value a string value.
	 * @param padLen the total length of the return value.
	 * @param padChar padded character.
	 * @return right padded string.
	 */
	public static String rpad(String value, int padLen, char padChar)
	{
		if (value == null) {
            value = "";
        }

		while (value.length() < padLen)
		{
			value = value + padChar;
		}

		return value;
	}

	/**
	 * Returns value argument,
	 * right-padded to length padLen argument with the sequence of character in padChar argument.
	 *
	 * @param value a short value.
	 * @param padLen the total length of the return value.
	 * @param padChar padded character.
	 * @return right padded string.
	 */
	public static String rpad(short value, int padLen, char padChar)
	{
		return rpad( String.valueOf(value), padLen, padChar );
	}

	/**
	 * Returns value argument,
	 * right-padded to length padLen argument with the sequence of character in padChar argument.
	 *
	 * @param value a int value.
	 * @param padLen the total length of the return value.
	 * @param padChar padded character.
	 * @return right padded string.
	 */
	public static String rpad(int value, int padLen, char padChar)
	{
		return rpad( String.valueOf(value), padLen, padChar );
	}

	/**
	 * Returns value argument,
	 * right-padded to length padLen argument with the sequence of character in padChar argument.
	 *
	 * @param value a long value.
	 * @param padLen the total length of the return value.
	 * @param padChar padded character.
	 * @return right padded string.
	 */
	public static String rpad(long value, int padLen, char padChar)
	{
		return rpad( String.valueOf(value), padLen, padChar );
	}

	/**
	 * Returns value argument,
	 * right-padded to length padLen argument with the sequence of character in padChar argument.
	 *
	 * @param value a float value.
	 * @param padLen the total length of the return value.
	 * @param padChar padded character.
	 * @return right padded string.
	 */
	public static String rpad(float value, int padLen, char padChar)
	{
		return rpad( String.valueOf(value), padLen, padChar );
	}

	/**
	 * Returns value argument,
	 * right-padded to length padLen argument with the sequence of character in padChar argument.
	 *
	 * @param value a double value.
	 * @param padLen the total length of the return value.
	 * @param padChar padded character.
	 * @return right padded string.
	 */
	public static String rpad(double value, int padLen, char padChar)
	{
		return rpad( String.valueOf(value), padLen, padChar );
	}

	//??????????????????????????????????????????????????

	/**
	 * Formats a number to the specific formatter stirng.
	 *
	 * @param no a number string.
	 * @param formatter number formatter string.
	 * @return formatted number string.
	 * @exception NumberFormatException if the no argument is not a number.
	 */
	public static String format(String no, String formatter)
	{
		return format(Double.parseDouble(no), formatter);
	}

	/**
	 * Formats a number to the specific formatter stirng.
	 *
	 * @param no a int number.
	 * @param formatter number formatter string.
	 * @return formatted number string.
	 */
	public static String format(int no, String formatter)
	{
		return format((long) no, formatter);
	}

	/**
	 * Formats a number to the specific formatter stirng.
	 *
	 * @param no a float number.
	 * @param formatter number formatter string.
	 * @return formatted number string.
	 */
	public static String format(float no, String formatter)
	{
		return format((double) no, formatter);
	}

	/**
	 * Formats a number to the specific formatter stirng.
	 *
	 * @param no a long number.
	 * @param formatter number formatter string.
	 * @return formatted number string.
	 */
	public static String format(long no, String formatter)
	{
		DecimalFormat df = new DecimalFormat(formatter);
		return df.format(no);
	}

	/**
	 * Formats a number to the specific formatter stirng.
	 *
	 * @param no a double number.
	 * @param formatter number formatter string.
	 * @return formatted number string.
	 */
	public static String format(double no, String formatter)
	{
		DecimalFormat df = new DecimalFormat(formatter);
		return df.format(no);
	}

	public static String[] split(String str, String delim)
	{
		return split(str, delim, true);
	}

	public static String[] split(String str, String strDelim, boolean isSkipNull)
	{
		if (str == null) {
            return null;
        }

		String[] arr = null;

		if (isSkipNull)
		{
			StringTokenizer st = new StringTokenizer( str, strDelim );
			arr = new String[ st.countTokens() ];
			for (int i=0; i<arr.length && st.hasMoreTokens(); i++)
			{
				arr[i] = st.nextToken();
			}
		}
		else
		{
			Vector<String> vt = new Vector<>();
			boolean setNull = str.startsWith(strDelim);
			StringTokenizer st = new StringTokenizer( str, strDelim, true );
			while ( st.hasMoreTokens() )
			{
				String value = st.nextToken();
				if ( strDelim.equals(value) )
				{
					if (setNull) {
                        vt.add((String) null);
                    } else {
                        setNull = true;
                    }
				}
				else
				{
					vt.add(value);
					setNull = false;
				}
			}

			if (setNull) { //if (str.endsWith(strDelim))
            	vt.add((String) null);
            }

			if (vt.size() > 0)
			{
				arr = new String[vt.size()];
				vt.copyInto(arr);
			}
		}

		return arr;
	}
	//??????????????????????????????????????????????????

	/**
	 * Split a special character separated string, and returns a <code>String</code> array.
	 *
	 * @param str special character separated string
	 * @param delim delimeter character.
	 * @param isSkipNull
	 * @return splitted string array.
	 */
	public static String[] split(String str, char delim, boolean isSkipNull)
	{
		if (str == null) {
            return null;
        }

		String[] arr = null;
		String strDelim = String.valueOf(delim);

		if (isSkipNull)
		{
			StringTokenizer st = new StringTokenizer( str, strDelim );
			arr = new String[ st.countTokens() ];
			for (int i=0; i<arr.length && st.hasMoreTokens(); i++)
			{
				arr[i] = st.nextToken();
			}
		}
		else
		{
			Vector<String> vt = new Vector<>();
			boolean setNull = str.startsWith(strDelim);
			StringTokenizer st = new StringTokenizer( str, strDelim, true );
			while ( st.hasMoreTokens() )
			{
				String value = st.nextToken();
				if ( strDelim.equals(value) )
				{
					if (setNull) {
                        vt.add((String) null);
                    } else {
                        setNull = true;
                    }
				}
				else
				{
					vt.add(value);
					setNull = false;
				}
			}

			if (setNull) { //if (str.endsWith(strDelim))
            	vt.add((String) null);
            }

			if (vt.size() > 0)
			{
				arr = new String[vt.size()];
				vt.copyInto(arr);
			}
		}

		return arr;
	}

	/**
	 * Split a comma separated string, and returns a <code>String</code> array.
	 *
	 * @param str comma separated string
	 * @param isSkipNull
	 * @return splitted string array.
	 */
	public static String[] split(String str, boolean isSkipNull)
	{
		return split(str, ',', isSkipNull);
	}

	/**
	 * Split a special character separated string, and returns a <code>String</code> array.
	 *
	 * @param str special character separated string
	 * @param delim delimeter character.
	 * @return splitted string array.
	 */
	public static String[] split(String str, char delim)
	{
		return split(str, delim, true);
	}

	/**
	 * Split a comma separated string, and returns a <code>String</code> array.
	 *
	 * @param str comma separated string
	 * @return splitted string array.
	 */
	public static String[] split(String str)
	{
		return split(str, ',', true);
	}

	//??????????????????????????????????????????????????

	/**
	 * Join a string array element with a special character.
	 *
	 * @param arr string array
	 * @param delim delimeter character
	 * @param isSkipNull
	 * @return joined string
	 */
	public static String join(String[] arr, char delim, boolean isSkipNull)
	{
		if (arr == null || arr.length == 0) {
            return null;
        }

		int intCnt = 0;
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<arr.length; i++)
		{
			if (isSkipNull)
			{
				if (arr[i] != null)
				{
					if (intCnt > 0) {
                        sb.append(delim);
                    }
					sb.append(arr[i]);
					intCnt++;
				}
			}
			else
			{
				if (i > 0) {
                    sb.append(delim);
                }
				sb.append(arr[i] == null ? "" : arr[i]);
			}
		}

		return sb.toString();
	}

	/**
	 * Join a string array element with a comma.
	 *
	 * @param arr string array
	 * @param isSkipNull
	 * @return joined string
	 */
	public static String join(String[] arr, boolean isSkipNull)
	{
		return join(arr, ',', isSkipNull);
	}

	/**
	 * Join a string array element with a special character.
	 *
	 * @param arr string array
	 * @param delim delimeter character
	 * @return joined string
	 */
	public static String join(String[] arr, char delim)
	{
		return join(arr, delim, true);
	}

	/**
	 * Join a string array element with a comma.
	 *
	 * @param arr string array
	 * @return joined string
	 */
	public static String join(String[] arr)
	{
		return join(arr, ',', true);
	}
	//??????????????????????????????????????????????????

	public static Properties splitNameValue(String str, char delim)
	{
		if (str == null) {
            return null;
        }

		Properties pt = new Properties();
		StringTokenizer st = new StringTokenizer( str, String.valueOf(delim) );
		while ( st.hasMoreTokens() )
		{
			String strElement = st.nextToken();
			int index = strElement.indexOf("=");
			if (index == -1) {
                continue;
            }

			try
			{
				pt.setProperty( strElement.substring(0, index), strElement.substring(index+1) );
			}
			catch (StringIndexOutOfBoundsException e)
			{
				pt.setProperty( strElement.substring(0, index), "" );
			}
		}

		return pt.size() == 0 ? null : pt;
	}

	public static Properties splitNameValue(String str)
	{
		return splitNameValue(str, ',');
	}

	public static String joinNameValue(Properties pt, char delim)
	{
		if (pt == null || pt.size() == 0) {
            return null;
        }

		Enumeration<?> en = pt.propertyNames();
		StringBuffer sb = new StringBuffer();
		int intCnt  = 0;
		while ( en.hasMoreElements() )
		{
			String strKey = (String) en.nextElement();
			if (intCnt > 0) {
                sb.append(delim);
            }
			sb.append(strKey);
			sb.append("=");
			sb.append( pt.getProperty(strKey) );

			intCnt++;
		}

		return sb.toString();
	}

	public static String joinNameValue(Properties pt)
	{
		return joinNameValue(pt, ',');
	}

	//??????????????????????????????????????????????????

	/**
	 * Remove a special character from the str argument.
	 *
	 * @param str a string
	 * @param rmChar removed character.
	 * @return a string that special character is removed .
	 */
	public static String removeChar(String str, char rmChar)
	{
		if (str==null || str.indexOf(rmChar) == -1) {
            return str;
        }

		StringBuffer sb = new StringBuffer();
		char[] arr = str.toCharArray();
		for (int i=0; i<arr.length; i++)
		{
			if ( arr[i] != rmChar ) {
                sb.append( arr[i] );
            }
		}

		return sb.toString();
	}

	/**
	 * Remove a special characters from the str argument.
	 *
	 * @param str a string
	 * @param rmChar removed characters.
	 * @return a string that special characters are removed .
	 */
	public static String removeChar(String str, char[] rmChar)
	{
		if ( str==null || rmChar == null ) {
            return str;
        }

		char[] arr = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<arr.length; i++)
		{
			boolean isAppend = true;
			for (int k=0; k<rmChar.length; k++)
			{
				if ( arr[i] == rmChar[k] )
				{
					isAppend = false;
					break;
				}
			}
			if (isAppend) {
                sb.append( arr[i] );
            }
		}

		return sb.toString();
	}


	//??????????????????????????????????????????????????
	//??????????????????????????????????????????????????

	/**
	 * Encode a string from the specific encoding name to other encoding name.
	 *
	 * @param str a source string
	 * @param fromEnc source string encoding name
	 * @param toEnc target encoding name
	 * @return encoded string.
	 * @exception UnsupportedEncodingException If the named encoding is not supported.
	 */
	public static String encodeText(String str, String fromEnc, String toEnc) throws UnsupportedEncodingException
	{
		return (str == null) ? null : new String(str.getBytes(fromEnc), toEnc);
	}

	/**
	 * Encode a string from &quot;8859_1&quot; to &quot;default.encoding&quot;.
	 *


	 * @return encoded string.
	 * @exception UnsupportedEncodingException If &quot;default.encoding&quot; is not supported.
	 */
	/*
	public static String encodeText(String str) throws UnsupportedEncodingException
	{
		if (PropertyUtil.getEncoding()==null) return str;
		return encodeText(str, "8859_1", PropertyUtil.getEncoding());
	}
*/
	/**
	 * Encode a string from &quot;default.encoding&quot; to &quot;8859_1&quot;.
	 *
	 * @param str a source string
	 * @return encoded string.
	 * @exception UnsupportedEncodingException If &quot;default.encoding&quot; is not supported.
	 */
	/*
	public static String decodeText(String str) throws UnsupportedEncodingException
	{
		if (PropertyUtil.getEncoding()==null) return str;
		return encodeText(str, PropertyUtil.getEncoding(), "8859_1");
	}
*/
	/**
	 * convert a HTML special character string.
	 * <pre><code>
	 * &lt; --&gt; &amp;lt;
	 * &gt; --&gt; &amp;gt;
	 * &quot; --&gt; &amp;gt;
	 * &amp; --&gt; &amp;amp;</code></pre>
	 *
	 * @param value HTML special character string.
	 * @param converted HTML special character string.
	 */
	public static String convertInput(String value)
	{
		if (value == null) {
            return "";
        }

		char[] arr = value.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<arr.length; i++)
		{
			switch (arr[i])
			{
				case '\"':
					sb.append("&quot;");
					break;

				case '<':
					sb.append("&lt;");
					break;

				case '>':
					sb.append("&gt;");
					break;

				case '&':
					sb.append("&amp;");
					break;

				default :
					sb.append(arr[i]);
			}
		}

		return sb.toString();
	}

	/**
	 * convert a HTML special character string.
	 * <pre><code>
	 * &lt; --&gt; &amp;lt;
	 * &gt; --&gt; &amp;gt;
	 * &quot; --&gt; &amp;gt;
	 * &amp; --&gt; &amp;amp;
	 * \r or \n --&gt; &gt;br&lt\n
	 * \t --&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;
	 * ' ' --&gt; &nbsp;</code></pre>
	 *
	 * @param value HTML special character string.
	 * @param converted HTML special character string.
	 */
	public static String convertHtml(String value)
	{
		if (value == null) {
            return "";
        }

		char[] arr = value.toCharArray();
		StringBuffer sb = new StringBuffer();

		for (int i=0; i<arr.length; i++)
		{
			switch (arr[i])
			{
				case '\n':
					sb.append("<br>\n");
					break;

				case '\r':
					break;

				case '\t':
					sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					break;

				case '\"':
					sb.append("&quot;");
					break;

				case '<':
					sb.append("&lt;");
					break;

				case '>':
					sb.append("&gt;");
					break;

				case ' ':
					sb.append("&nbsp;");
					break;

				case '&':
					sb.append("&amp;");
					break;

				default	:
					sb.append(arr[i]);
			}
		}

		return sb.toString();
	}

	/**
	 * convert a special character string.
	 * <pre><code>
	 * &lt; --&gt; &amp;lt;
	 * &gt; --&gt; &amp;gt;
	 * &quot; --&gt; &amp;quot;
	 * &amp; --&gt; &amp;amp;
	 * @param value HTML special character string.
	 * @param converted HTML special character string.
	 */
	public static String convertSpecialChar(String value)
	{
		if (value == null) {
            return "";
        }

		char[] arr = value.toCharArray();
		StringBuffer sb = new StringBuffer();

		for (int i=0; i<arr.length; i++)
		{
			switch (arr[i])
			{

				case '\"':
					sb.append("&quot;");
					break;

				case '<':
					sb.append("&lt;");
					break;

				case '>':
					sb.append("&gt;");
					break;
				case '&':
					sb.append("&amp;");
					break;

				default	:
					sb.append(arr[i]);
			}
		}

		return sb.toString();
	}

	/**
	 * convert a HTML special character string.
	 * <pre><code>
	 * &quot; --&gt; &amp;gt;
	 * &amp; --&gt; &amp;amp;
	 * \r or \n --&gt; &gt;br&lt\n
	 * \t --&gt; &amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;
	 * ' ' --&gt; &nbsp;</code></pre>
	 *
	 * @param value HTML special character string.
	 * @param converted HTML special character string.
	 */
	public static String convertPre(String value)
	{
		if (value == null) {
            return "";
        }

		char[] arr = value.toCharArray();
		StringBuffer sb = new StringBuffer();

		for (int i=0; i<arr.length; i++)
		{
			switch (arr[i])
			{
				case '\n':
					sb.append("<br>\n");
					break;

				case '\r':
					break;

				case '\t':
					sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					break;

				case '\"':
					sb.append("&quot;");
					break;

				case ' ':
					sb.append("&nbsp;");
					break;

				case '&':
					sb.append("&amp;");
					break;

				default	:
					sb.append(arr[i]);
			}
		}

		return sb.toString();
	}

	/**
	 * @param double no
	 * @param String
	 */
	public static String makeMoneyType(Object value)
	{
		String	result = "";
		if(value instanceof String) {
			result	= makeMoneyType((String)value);
		}
		else if(value instanceof BigDecimal) {
			result	= makeMoneyType((BigDecimal)value);
		}
		else if(value instanceof Integer) {
			result	= makeMoneyType(value.toString());
		}
		return result;
	}
	/**
	 * @param double no
	 * @param String
	 */
	public static String makeMoneyType(double no)
	{
		return NumberFormat.getInstance().format(no);
	}
	/**
	 * @param float no
	 * @param String
	 */
	public static String makeMoneyType(float no)
	{
		return ( makeMoneyType( (new Float(no)).doubleValue() ) );
	}
	/**
	 * @param String no
	 * @param String
	 */
	public static String makeMoneyType(int no)
	{
		return ( makeMoneyType( (new Integer(no)).longValue() ) );
	}
	/**
	 * @param long no
	 * @param String
	 */
	public static String makeMoneyType(long no)
	{
		return NumberFormat.getInstance().format(no);
	}
	/**
	 * ?????8?? ??? ??: ????? 123,333???·? ???
	 * @param String no
	 * @param String
	 */
	public static String makeMoneyType(BigDecimal value) {
		if(value == null) {
            return "0";
        }
		return makeMoneyType(value.toString());
	}

	/**
	 * ?????8?? ??? ??: ????? 123,333???·? ???
	 * @param String no
	 * @param String
	 */
	public static String makeMoneyType(String no)
	{
		if(no==null || "".equals(no)) {
			return "0";
		}
		for ( int i=0; i< no.length(); i++)
		{
			if(no.charAt(i)=='0')
			{
				continue;
			}
			else
			{
				no = no.substring(i);
				break;
			}
		}
		int index = no.indexOf(".");
		if (index == -1)
		{
			return makeMoneyType( Long.parseLong(no) );
		}
		else
		{
			return (
					makeMoneyType( Long.parseLong(no.substring(0, index)) ) +
							no.substring(index, no.length())
			);
		}
	}


	/**
	 * 문자 제거
	 * @param String
	 * @return String
	 */
	public static String removeStr(String str, String srchString) throws Exception {
		return StringUtil.replace(str, srchString, "");
	}


	/**
	 * strSrc String; ??? strOld String; a?? strNew String8?? ??????.
	 * ??? ??￥: (2001-03-24 ???? 2:21:10)
	 * @return java.lang.String
	 * @param strSrc,strOld,strNew java.lang.String
	 */
	public static String replace(String originString,String srchString,String repString){
		if(originString == null) {
            return null;
        }
		if(originString.equals("")) {
            return "";
        }
		if(srchString == null || srchString.equals("")) {
            return originString;
        }

		String result = "";
		int i;

		do
		{
			i = originString.indexOf(srchString);

			if(i != -1)
			{
				result += originString.substring(0, i);
				result += repString;
				originString = originString.substring(i + srchString.length());
			}
			else
			{
				result += originString;
				break;
			}
		}
		while(i != -1);

		return result;
	}

	public static String replaceIgnoreCase(String originString,String srchString,String repString){
		if(originString == null) {
            return null;
        }
		if(originString.equals("")) {
            return "";
        }
		if(srchString == null || srchString.equals("")) {
            return originString;
        }
		String result = "";
		int i;

		do
		{
			i = originString.toLowerCase().indexOf(srchString.toLowerCase());

			if(i != -1)
			{
				result += originString.substring(0, i);
				result += repString;
				originString = originString.substring(i + srchString.length());
			}
			else
			{
				result += originString;
				break;
			}
		}
		while(i != -1);

		return result;
	}

	/**
	 * 문자열 길이.
	 * @param String
	 * @return int
	 */
	public static int length(String str)
	{
		String str2 = nvl(str);
		return str2.length();
	}

	/**
	 * 문자열 길이(Byte).
	 * @param String
	 * @return int
	 */
	public static int byteLength(String str)
	{
		String str2 = nvl(str);
		return str2.getBytes().length;
	}

	/** return str.substring(0,endIdx) **/
	public static String left(String str, int len)
	{
		if(str==null) {
            return "";
        } else {
            return substr(str,0,len);
        }
	}

	/** return str.substring(0,endIdx) **/
	public static String left(Object str, int len)
	{
		String	resultStr = null;
		if(str==null) {
			return "";
		}
		if(str instanceof String) {
			resultStr 	= ((String)str);
		}
		else if(str instanceof BigDecimal) {
			resultStr 	= ((BigDecimal)str).toString();
		}
		else if(str instanceof Double) {
			resultStr 	= ((Double)str).toString();
		}
//		else if(str instanceof java.sql.Timestamp) {
//			resultStr 	= DateUtil.converFormat((java.sql.Timestamp)str, "yyyy-MM-dd HH:mm:ss");
//		}

		return substr(resultStr,0,len);
	}

	/** return str.substring(str.length() - len) **/
	public static String right(String str, int len)
	{
		if(str==null) {
            return "";
        } else {
            return substr(str, -len);
        }
	}

	public static String getInnerString(String str, String start, String last)
	{
		int pos = str.indexOf(start);
		if  ( pos>=0)
		{
			int pos1 = str.indexOf(last, pos+1);
			if ( pos1 > pos)
			{
				return str.substring(pos+start.length(), pos1);
			}
		}
		return "";
	}

	public static String getInnerStringIgnoreCase(String str, String start, String last)
	{
		String str1 = str.toUpperCase();
		int pos = str1.indexOf(start.toUpperCase());
		if  ( pos>=0)
		{
			int pos1 = str1.indexOf(last.toUpperCase(), pos+1);
			if ( pos1 > pos)
			{
				return str.substring(pos+start.length(), pos1);
			}
		}
		return "";
	}

	/**
	 * ?????? ??? ????; ????; ??f??? ?????? ?????
	 * @param String filename
	 * @return String
	 */
	public static String getFileExtension(String filename)
	{
		if (filename == null) {
            return null;
        }

		int len = filename.length();
		if (len == 0) {
            return filename;
        }

		int last = filename.lastIndexOf(".");
		if (last == -1) {
            return filename;
        }

		return filename.substring( last + 1, len );
	}

	/**
	 * ??? ?? ????(???? ???ε?)
	 * @param String str
	 * @return String
	 */
	public static String getChangeArray( String str ){
		StringBuffer sb = new StringBuffer();

		if( str == null ) {
            return "";
        }

		for( int i = 0; i < str.length(); i++ ){
			if( str.charAt( i ) != '+' ){
				sb.append( str.charAt( i ) );
			}else {
				sb.append( "%20" );
			}
		}

		return sb.toString();
	}

	/**
	 * String; truncLen???????? ?????? ??? ...8?? ?????(??????? -> ??????...)
	 * @param String str
	 * @param String truncLen
	 * @return String
	 */
	public static String truncateString(String str, int truncLen)
	{
		return truncateString(str, truncLen, "...");
	}

	/**
	 * String; truncLen???????? ?????? ??? lastStr?? ?????.
	 * @param String str
	 * @param String truncLen
	 * @return String
	 */
	public static String truncateString(String str, int truncLen, String lastStr)
	{
		if (str == null) {
            return str;
        }

		int len = str.length();
		if (len <= truncLen) {
            return str;
        }

		return str.substring(0, truncLen) + ((lastStr == null) ? "" : lastStr);
	}


	/**
	 * Enter?? <br>??8?? ?ν????
	 */
	public static String setBR(String asomething)
	{
		int i,len = asomething.length();
		StringBuffer dest = new StringBuffer(len+500);
		for( i = 0 ; i < len ; i++)
		{
			if( asomething.charAt(i) == '\n') {
                dest.append("<br>");
            } else {
                dest.append(asomething.charAt(i));
            }
		}
		return dest.toString();
	}

	/**
	 * HTML ?±?? ????? ??? ??? ?????? ??????? ??u ???????? ?????? ??? ??.
	 * @param  String str
	 * @return String
	 */
	public static String escapeHTML(String str){

		if(null==str) {
            return str;
        }

		char c = ' ';

		StringBuffer sb=new StringBuffer(str.length());

		for(int i=0;i<str.length();i++){
			c=str.charAt(i);
			switch (c){
				case '&'    :   sb.append("&amp;");     break;
				case '"'    :   sb.append("&quot;");    break;
				case '<'    :   sb.append("&lt;");      break;
				case '>'    :   sb.append("&gt;");      break;
				case '\''   :   sb.append("&#39;");     break;
				case ' '    :   sb.append("&nbsp;");    break;
				default     :   sb.append(c);
			}
		}

		return sb.toString();
	}


	/**
	 * 문자형 체크.
	 * @param String
	 * @return boolean
	 */
	public static boolean isString(String str){
		return Pattern.matches("[0-9a-zA-Z]+", str);

	}

	/**
	 * 숫자형 체크.
	 * @param String
	 * @return boolean
	 */
	public static boolean isNumeric(String str){
		return Pattern.matches("[0-9]+", str);

	}


	/**
	 * ??? String ???? ???????? true, ???? false ????
	 * @param  String str
	 * @return boolean
	 */
	public static boolean isNumberic(String str){
		return Pattern.matches("[0-9]+", str);

	}

	/**
	 * ??? String ???? ???????? parseInt?? ??; , ???? -1 ????
	 * @param  String str
	 * @return int
	 */
	public static int isNumberic2(String str){
		if(Pattern.matches("[0-9]+", str)) {
            return parseInt(str);
        } else {
            return -1;
        }
	}

	/**
	 * ?? ?????; ????'????? ?????? ??????.
	 * substring(??, ????'?)
	 * @param  ??		String
	 * @param  ????'?	int
	 * @return String
	 */
	public static String substring(String str, int startPos){
		if(str==null) {
            return "";
        }

		int strLen = str.length();
		if(startPos < 0 || strLen <= startPos) {
            return "";
        }

		return str.substring(startPos);
	}

	/**
	 * ?? ?????; ????'????? ??'????? ??????.
	 * substring(??, ????'?, ??'?)
	 * @param  ??		String
	 * @param  ????'?	int
	 * @param  ??'?	int
	 * @return String
	 */
	public static String substring(String str, int startPos, int endPos){
		if(str==null) {
            return "";
        }

		int strLen = str.length();
		if(startPos < 0 || strLen <= startPos) {
            return "";
        }

		if(strLen < endPos) {
            return "";
        }

		if(startPos > endPos) {
            return "";
        }

		return str.substring(startPos, endPos);
	}

	/**
	 * ?? ?????; ????'????? ?????? ??????.(substring ???? ???????.)
	 * substring(??, ????'?)
	 * @param  ??		String
	 * @param  ????'?	int
	 * @return String
	 * @see	  String data="123456789"; substr(a, 2);	//"3456789"
	 * @see	  String data="123456789"; substr(a, -2);	//"89"
	 */
	public static String substr(Object str, int startPos){
		return substr((String) str, startPos);
	}
	public static String substr(String str, int startPos){
		if(str==null) {
            return "";
        }

		int strLen = str.length();
		if(strLen <= startPos) {
            return "";
        }

		if(startPos < 0 && startPos < -strLen) {
            return "";
        }

		if(startPos < 0) {
            return str.substring(strLen+startPos);
        } else {
            return str.substring(startPos);
        }
	}

	/**
	 * ?? ?????; ????'????? ?????? ??????.(substring ???? ???????.)
	 * substring(??, ????'?, ????)
	 * @param  ??		String
	 * @param  ????'?	int
	 * @param  ??'?		int
	 * @return String
	 * @see	  String data="123456789"; substr(a, 2, 5);		//"34567"
	 * @see	  String data="123456789"; substr(a, -2, 1);	//"8"
	 * @see	  String data="123456789"; substr(a, 1, -1);	//"2345678"
	 * @see	  String data="123456789"; substr(a, -5, -1);	//"5678"
	 */
	public static String substr(Object str, int startPos, int length){
		return substr((String) str, startPos, length);
	}
	public static String substr(String str, int startPos, int length){
		if(str==null) {
            return "";
        }

		int strLen = str.length();
		if(strLen <= startPos) {
            return "";
        }

		if(startPos < 0 && startPos < -strLen) {
            return "";
        }
		if(length == 0) {
            return "";
        }
		if(length < 0 && length <= -strLen) {
            return "";
        }

		if(startPos < 0)
         {
            startPos = strLen+startPos;		// -?? ???
        }
		int endPos = 0;
		if(length < 0) {
            endPos = strLen+length;							// -?? ???
        } else {
            endPos = startPos+length;
        }
		if(endPos > strLen) {
            endPos = strLen;
        }
		if(startPos >= endPos) {
            return "";
        }

		return str.substring(startPos, endPos);
	}

	/**
	 * ??????? ????? ????'?? ???? ?????? ?????; ?? 6????? ???????.
	 *
	 * @return
	 */
	public static String getRandomString() {
		return getRandomString(6);
	}

	/**
	 * ??????? ????? ????'?? ???? ?????? ?????; ??d?? ????? ???????.
	 *
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {

		Random rn = new Random();
		StringBuffer sb = new StringBuffer();

		String[] alphabet = { "A","B","C","D","E","F","G","H","I","J","K","L","M","N",
				"O","P","Q","R","S","T","U","V","W","X","Y","Z" };
		String[] numeric  = {"0","1","2","3","4","5","6","7","8","9" };

		for(int i=0;i<length;i++) {
			int selArray = rn.nextInt(2);
			if(selArray == 0) {
				int idx = rn.nextInt(alphabet.length);
				sb.append(alphabet[idx]);
			}else{
				int idx2 = rn.nextInt(numeric.length);
				sb.append(numeric[idx2]);
			}
		}
		return sb.toString();
	}

	/**
	 * ????? ????'?? ???? 6????? ????? ???·? ???
	 * @return
	 */
	public static String getRandomNumber() {
		return getRandomNumber(6);
	}

	/**
	 * ????? ????'?? ???? ??d?? ?????? ????? ???·? ???
	 * @param length
	 * @return
	 */
	public static String getRandomNumber(int length) {

		Random rn = new Random();
		StringBuffer sb = new StringBuffer();

		String[] numeric  = {"0","1","2","3","4","5","6","7","8","9" };

		int idx = 0;

		for (int i = 0; i < length; i++) {
			idx = rn.nextInt(numeric.length);
			sb.append(numeric[idx]);
		}
		return sb.toString();
	}

	/**
	 * ??????? ??????? ??? str ??????? ????? keyword ?? a??? ???? ????? ??:?? ????±?? ?????? ????? ???
	 * <p>
	 * added by pwjman
	 * </p>
	 * @param str
	 * @param keyword
	 * @return
	 */
	public static String markText(String str, String keyword) {
		return markText(str, keyword, "color:red");
	}

	/**
	 * ??????? ??????? ??? str ??????? ????? keyword ?? a??? css style; ?????? ????? ???
	 * <p>
	 * by kenu at http://okjsp.pe.kr
	 * </p>
	 * @param str
	 * @param keyword
	 * @param style
	 * @return
	 */
	public static String markText(String str, String keyword, String style) {
		keyword = replace(replace(replace(keyword, "[", "\\["), ")", "\\)"),
				"(", "\\(");

		Pattern p = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		int start = 0;
		int lastEnd = 0;
		StringBuffer sbuf = new StringBuffer();
		while (m.find()) {
			start = m.start();
			sbuf.append(str.substring(lastEnd, start)).append("<FONT style='").append(style).append("'>").append(
					m.group()).append("</FONT>");
			lastEnd = m.end();
		}
		return sbuf.append(str.substring(lastEnd)).toString();
	}


	// byte 구하기
	public static int getBytes(Object value) {
		String	str = "";
		if(value instanceof String) {
			str	= (String)value;
		}
		else if(value instanceof BigDecimal) {
			str	= ((BigDecimal)value).toString();
		}
		else if(value instanceof Double) {
			str	= ((Double)value).toString();
		}
		return getBytes(str);
	}

	public static int getBytes(String value) {
		int		byteSize		= 0;

		for(int i=0; i<value.length(); i++)
		{
			byte[] ar = value.substring(i, i+1).getBytes();
			// 리눅스에서는 3바이트 처리 -> 2바이트로 인식
			byteSize	+= ar.length;
		}

		return byteSize;
	}

	// 한글 자르기
	public static String cutHangul(Object value, int len) {
		return cutHangul(value, len, true);
	}
	public static String cutHangul(Object value, int len, boolean point) {
		String	str = "";
		if(value instanceof String) {
			str	= (String)value;
		}
		else if(value instanceof BigDecimal) {
			str	= ((BigDecimal)value).toString();
		}
		else if(value instanceof Double) {
			str	= ((Double)value).toString();
		}
		return cutHangul(str, len, point);
	}
	public static String cutHangul(String str, int len) {
		return cutHangul(str, len, true);
	}
	public static String cutHangul(String str, int len, boolean point) {
		if(str == null) {
            return "";
        }
		if(str.equals("") || str.getBytes().length <= len)
		{
			return str;
		}

		String a = str;
		int i = 0;
		String imsi = "";
		String rlt = "";
		imsi = a.substring(0,1);
		while(i < len)
		{
			byte[] ar = imsi.getBytes();
			// 리눅스에서는 3바이트 처리 -> 2바이트로 인식
			if(ar.length > 2) {
				i += 2;
			}
			else {
				i += 1;
			}

			if(i <= len) {
				rlt += imsi;
			}

			a = substring(a, 1);

			if(a.length() == 1)
			{
				imsi = a;
			}
			else if(a.length() > 1)
			{
				imsi = a.substring(0,1);
			}
			else {
				break;
			}
		}

		if(point && i >= len) {
			rlt = rlt + "..";
		}

		return rlt;
	}


	/*
	 * 정수 나누기
	 * @param 	int	정수
	 * @param 	int 나누기 정수
	 * @return	int	결과
	 */
	public static int divideCeil(int value1, int value2) {
		double		dValue1 = Double.parseDouble(String.valueOf(value1));
		double		dValue2 = Double.parseDouble(String.valueOf(value2));

		return (int)Math.ceil(dValue1 / dValue2);
	}


	// --------------------------------------------------------------------

	/*
	 * history.back() 자바스크립트
	 * @param
	 * @return	자바스크립트
	 */
	public static String printHistoryBack() {
		StringBuffer sb = new StringBuffer();

		sb.append("<script>");
		sb.append("		history.back();");
		sb.append("</script>");

		return sb.toString();
	}


	/**
	 * Exception 을 String 으로 convert 해준다.
	 * @param Exception e
	 * @return String str
	 */
	public static String exceptionToString(Exception e) {
		if( e == null ) {
            return "";
        }

		StringWriter sw = new StringWriter();
		PrintWriter o = new PrintWriter(sw);
		e.printStackTrace(o);
		return sw.toString();
	}


	/**
	 * 태그 제거.
	 * @param String
	 * @return String
	 */
	public static String convertHTML(String str) {
		String		result = "";

		if(str != null) {
			// 제거
			str = str.replaceAll("<", "&lt;");
			str = str.replaceAll(">", "&gt;");
			// 변환
			str = str.replaceAll("&lt;p", "<p");
			str = str.replaceAll("&lt;P", "<P");
			str = str.replaceAll("&lt;br", "<br");
			str = str.replaceAll("&lt;BR", "<BR");

			result = str;
		}

		return result;
	}

	/**
	 * 태그 제거.
	 * @param String
	 * @return String
	 */
	public static String convertSQLToHTML(String str) {
		String		result = "";

		if(str != null) {
			// 제거
			str = str.replaceAll("&lt;", "<");
			str = str.replaceAll("&gt;", ">" );
			str = str.replaceAll("&quot;", "\"");
			str = str.replaceAll("\n", "<br/>");

			result = str;
		}

		return result;
	}

	/**
	 * 화일 확장자 체크.
	 * @param String
	 * @return boolean
	 */
	public static boolean isBadFileExt(String fileName) throws Exception {
		boolean		result = true;

		if(fileName == null || "".equals(fileName)) {
			result = false;
		}
		else {
			String	fileExt = StringUtil.substring(fileName, fileName.lastIndexOf('.') + 1);
			if(fileExt.equalsIgnoreCase("cgi") || fileExt.equalsIgnoreCase("jsp") || fileExt.equalsIgnoreCase("php") || fileExt.equalsIgnoreCase("php3") || fileExt.equalsIgnoreCase("pl")  || fileExt.equalsIgnoreCase("inc") || fileExt.equalsIgnoreCase("xml") || fileExt.equalsIgnoreCase("js")) {
				result = true;
			}
			else {
				result = false;
			}
		}

		return result;
	}

	public static boolean isBadFileExt(String fileName,String[] arrFileExt) throws Exception {
		boolean		result = true;

		if(fileName == null || "".equals(fileName)) {
			result = false;
		}
		else {
			String	fileExt = StringUtil.substring(fileName, fileName.lastIndexOf('.') + 1);
			for(int i=0; i<arrFileExt.length; i++)
			{
				if(fileExt.equalsIgnoreCase(arrFileExt[i])){
					result = true;
					break;
				}
				else
				{
					result = false;
				}
			}
		}

		return result;
	}



	/**
	 * 입력값 체크.
	 * @param String
	 * @return String
	 */
	public static String formValidation(String str, int minLength, int maxLength) throws Exception {
		return formValidation(str, minLength, maxLength, null, false);
	}

	public static String formValidation(String str, int minLength, int maxLength, String type) throws Exception {
		return formValidation(str, minLength, maxLength, type, false);
	}

	public static String formValidation(String str, int minLength, int maxLength, String type, boolean isNull) throws Exception {
		// minLength
		if(byteLength(str) < minLength) {
			throw new Exception(str+" is short. < "+minLength);
		}
		// maxLength
		if(byteLength(str) > maxLength) {
			throw new Exception(str+" is long. > "+maxLength);
		}
		// 문자형 타입
		if("string".equalsIgnoreCase(type) && !isString(str)) {
			throw new Exception(str+" is not String.");
		}
		// 숫자형 타입
		else if("numeric".equalsIgnoreCase(type) && !isNumeric(str) ) {
			throw new Exception(str+" is not Numeric.");
		}
		// null
		if(!isNull && str==null) {
			throw new Exception("is Null.");
		}

		return str;
	}

	/**
	 * 문자형 -> 숫자형 변환
	 * @param String
	 * @return int
	 */
	public static int toInt(String str) throws Exception {
		int	result = 0;

		if(StringUtil.isNumeric(str)) {
			result = Integer.parseInt(str);
		}

		return result;
	}


	/**
	 * @see  검색 파라미터값을 Xss인코딩 체크
	 * @author Baram132 2009.12.17
	 * @param charValue
	 * @return
	 */
	public static String getCharInvalidFormCheck(String charValue)
	{
		String returnValue = "";

		if("".equals(charValue) || charValue == null) {
            return returnValue;
        }

		if(charValue.indexOf("'") != -1) {
            charValue 	= charValue.replaceAll("'","");
        }
		if(charValue.indexOf("%") != -1) {
            charValue 	= charValue.replaceAll("%","");
        }
		if(charValue.indexOf("<") != -1) {
            charValue 	= charValue.replaceAll("<","");
        }
		if(charValue.indexOf(">") != -1) {
            charValue 	= charValue.replaceAll(">","");
        }
		if(charValue.indexOf("+") != -1) {
            charValue 	= charValue.replaceAll("+","");
        }
		if(charValue.indexOf("/") != -1) {
            charValue  = charValue.replaceAll("/","");
        }
		if(charValue.indexOf("\"")!= -1) {
            charValue  = charValue.replaceAll("\"","");
        }
		if(charValue.indexOf("--")!= -1) {
            charValue 	= charValue.replaceAll("--","");
        }
		if(charValue.indexOf("!-")!= -1) {
            charValue 	= charValue.replaceAll("!-","");
        }
		if(charValue.indexOf("-!")!= -1) {
            charValue 	= charValue.replaceAll("!-","");
        }
		if(charValue.indexOf("script")!= -1) {
            charValue 	= charValue.replaceAll("script","");
        }

		return returnValue = charValue ;
	}


	public static String get64BaseDecoder(String str) throws IOException
	{
		@SuppressWarnings("restriction")
		Base64.Decoder decoder = Base64.getDecoder();
		//1.문자열을 base64디코드 한다.
		@SuppressWarnings("restriction")
		byte[] ciphertextArray = decoder.decode(str);
		String cipherString = new String(ciphertextArray);
		System.out.println("====================================0");
		System.out.println(cipherString);

		/*
		System.out.println("====================================1");
		int salt = Integer.parseInt(cipherString.substring(0,cipherString.indexOf("=")));
		System.out.println("====================================2");
		cipherString = cipherString.substring(cipherString.indexOf("=")+1,cipherString.length());
		System.out.println("====================================3");

        StringTokenizer sttk = new StringTokenizer(cipherString);
        StringBuffer result2 = new StringBuffer("");
        String temp = "";
        while (sttk.hasMoreTokens())
        {
        		temp = sttk.nextToken();
                result2.append((char)(Integer.parseInt(temp, 16)-salt));
        }
		*/

		//3.string to hex
		//return result2.toString();
		return cipherString;

	}

	public static HashMap<String, Object> createResult(String resultCode, String resultMsg) {
		HashMap<String, Object> result = new HashMap<>();
		result.put("resultCode", 	resultCode);
		result.put("resultMsg", 	resultMsg);

		return result;

	}

	// XML 파싱 오류를 발생시키는 &#? 문자 제거
	final static Pattern nonValidPattern = Pattern.compile("&#(x?)([0-9a-fA-F]+);");
	public static String stripNonValidXMLCharacters(String str) {
		StringBuffer out = new StringBuffer();
		Matcher matcher = nonValidPattern.matcher(str);
		int value = 0;
		boolean isHex = false;
		boolean valid = false;
		while (matcher.find())	  {
			isHex = matcher.group(1) != null;
			value = Integer.parseInt(matcher.group(2), isHex ? 16 : 10);
			valid = false;
			if ((value == 0x9) ||
					(value == 0xA) ||
					(value == 0xD) ||
					((value >= 0x20) && (value <= 0xD7FF)) ||
					((value >= 0xE000) && (value <= 0xFFFD)) ||
					((value >= 0x10000) && (value <= 0x10FFFF))) {
                valid = true;
            }
			if (!valid) {
                matcher.appendReplacement(out, "");
            }
		}
		matcher.appendTail(out);
		return out.toString();
	}

	/** * String UnEscape 처리 * * @param src * @return */
	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src .substring(pos + 2, pos + 6), 16);
					tmp.append(ch); lastPos = pos + 6;
				}
				else {
					ch = (char) Integer.parseInt(src .substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			}
			else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				}
				else {
					tmp.append(src.substring(lastPos, pos));

					lastPos = pos;

				}

			}

		} return tmp.toString();

	}

	/** * String Escape 처리 * @param src * @return */
	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);

			if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j)) {
                tmp.append(j);
            } else if (j < 256) {
				tmp.append("%");
				if (j < 16) {
                    tmp.append("0");
                }
				tmp.append(Integer.toString(j, 16));
			}
			else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}

		}

		return tmp.toString();

	}

	/************************************************
	 * url 스트링에서 파라미터를 파싱한다.
	 * @param	String	url
	 * @param	String	파라미터명
	 * @return	String	값
	 ************************************************/
	public static String getUrlParameterValue(String url, String parameterName) {
		String	value	= "";
//		System.out.println("# url="+url);
		int	pos		= url.indexOf(parameterName+"=");
		if(pos > -1) {
//			System.out.println("# pos="+pos);
			int	pos2		= url.indexOf("&", pos+parameterName.length()+1);
			if(pos2 > -1) {
//				System.out.println("# pos2="+pos2);
				value	= StringUtil.substring(url, pos+parameterName.length()+1, pos2);
			}
			else {
				value	= StringUtil.substr(url, pos+parameterName.length()+1);
			}
		}

		return value;
	}

	public static String toNumFormat(int num) {
		DecimalFormat df = new DecimalFormat("#,###");
		return df.format(num);
	}


}