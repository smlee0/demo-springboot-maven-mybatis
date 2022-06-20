package com.medialog.uplussave.common.util.legacy;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map.Entry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

@Aspect
public class ApimAspect {
	private final Logger log = LoggerFactory.getLogger(ApimAspect.class);

	@SuppressWarnings("unchecked")
	public void afterMethodCall( Object  obj) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> result = objectMapper.convertValue(obj, HashMap.class);

        if(result.containsKey("entrNo")) {     //가입번호
        	result.put("entrId", result.remove("entrNo"));
        }
        if(result.containsKey("billAcntNo")) { //청구계정번호
        	result.put("billAcntId", result.remove("billAcntNo"));
        }
        if(result.containsKey("custNo")) {     //고객번호
        	result.put("custId", result.remove("custNo"));
        }
        if(result.containsKey("msg")) {     //고객번호
        	result.put("message", result.remove("msg"));
        }

       for(Entry<String, Object> elem : result.entrySet()){
        	if(elem.getValue() != null && "org.json.simple.JSONObject".equals(elem.getValue().getClass().getName())) {

        		JSONObject jsonObj =  (JSONObject) elem.getValue();

        		if(jsonObj.containsKey("entrId")) {
        			jsonObj.put("entrNo",  jsonObj.remove("entrId"));
        		}
        		if(jsonObj.containsKey("billAcntId")) {
        			jsonObj.put("billAcntNo",  jsonObj.remove("billAcntId"));
        		}
        		if(jsonObj.containsKey("custId")) {
        			jsonObj.put("custNo",  jsonObj.remove("custId"));
        		}
        		if(jsonObj.containsKey("msg")) {
        			jsonObj.put("message",  jsonObj.remove("msg"));
        		}

        	} else if(elem.getValue() != null && "org.json.simple.JSONArray".equals(elem.getValue().getClass().getName())) {
        		JSONArray jsonArr =  (JSONArray) elem.getValue();
        		for(int i=0;i<jsonArr.size();i++){
        			JSONObject jsonObj = (JSONObject) jsonArr.get(i);
        			if(jsonObj.containsKey("entrId")) {
            			jsonObj.put("entrNo",  jsonObj.remove("entrId"));
            		}
            		if(jsonObj.containsKey("billAcntId")) {
            			jsonObj.put("billAcntNo",  jsonObj.remove("billAcntId"));
            		}
            		if(jsonObj.containsKey("custId")) {
            			jsonObj.put("custNo",  jsonObj.remove("custId"));
            		}
            		if(jsonObj.containsKey("msg")) {
            			jsonObj.put("message",  jsonObj.remove("msg"));
            		}

        		}
        	}
        }

	}

	@SuppressWarnings("unchecked")
	@Around("serviceMethod()")
	public Object aroundExecution(ProceedingJoinPoint jp) throws Throwable {

		String methodName  = jp.getSignature().getName();
		Method method      = ((MethodSignature) jp.getSignature()).getMethod();

		Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] parameterValues  = jp.getArgs();

        //파라미터 값 Map
		HashMap<String, Object> paramMap = new HashMap<>();

        if ( parameterTypes.length != 0 ) {
            for ( int i = 0 ; i < parameterTypes.length ; i++ ) {

                if ("java.util.HashMap".equals(parameterTypes[i].getName())){
                    paramMap =(HashMap<String, Object>)parameterValues[i];
                    if(paramMap.containsKey("entrNo")) {     //가입번호
                    	paramMap.put("entrId", paramMap.remove("entrNo"));
                    }
                    if(paramMap.containsKey("billAcntNo")) { //청구계정번호
                    	paramMap.put("billAcntId", paramMap.remove("billAcntNo"));
                    }
                    if(paramMap.containsKey("custNo")) {     //고객번호
                    	paramMap.put("custId", paramMap.remove("custNo"));
                    }
                    if(paramMap.containsKey("custMgmtNo")) {     //가상계좌고객관리ID 청구계정번호
                    	paramMap.put("vactCustMgmtId", paramMap.remove("custMgmtNo"));
                    }
                }
            }
        }

		StackTraceElement[] stack = new Throwable().getStackTrace();
		for (StackTraceElement s : stack) {
			if (!s.getClassName().startsWith("medialog")) {
                continue;
            }
		}

		long start = System.currentTimeMillis();
		try {
			return jp.proceed();
		}
		catch (Exception e) {
			log.error("", e);
			throw e;
		}
		finally {
			long finish = System.currentTimeMillis();
			long elapsedTime = finish - start;

			DateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
			log.debug(new StringBuilder().append("{ESB PROCESS TIME} ==> ").append("[" + methodName + "]")
					.append("[START : " + simple.format(start) + "]")
					.append("[END : " + simple.format(finish) + "]")
					.append("[ELAPSED TIME : " + elapsedTime + "]").toString());
		}
	}
}
