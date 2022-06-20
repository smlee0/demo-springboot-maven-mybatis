//package com.medialog.uplussave.common.util.legacy;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//
//@Component
//public class FileUtil implements InitializingBean{
//
//	@Value("#{system}")
//	private Properties properties;
//
//	private static String mhpDomain;
//
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		this.mhpDomain = properties.getProperty("mhp.file");
//	}
//
//	/**
//	 * 파일 여러개 업로드
//	 * @param request
//	 * @param uploadPath
//	 * @return
//	 * @throws Exception
//	 */
//	public static List<Map<String,Object>> uploadFiles(HttpServletRequest request, String uploadPath) throws Exception {
//
//		String uploadFullPath = mhpDomain + uploadPath;
//
//		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
//		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
//
//		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
//        Map<String, Object> listMap = null;
//
//		while (iterator.hasNext()) {
//			String name			= iterator.next();
//			MultipartFile file 	= multipartHttpServletRequest.getFile(name);
//
//			if(file.getSize() > 0){
//				//System.out.println("fileName:::" + file.getName() + ", orgFileName:::" + file.getOriginalFilename());
//
//				String fileFullName = file.getOriginalFilename(); // 예) Koala.jpg
//				String fileName = fileFullName.substring(0, fileFullName.lastIndexOf(".")); // 예) Koala
//				String fileExt = fileFullName.substring(fileFullName.lastIndexOf("."), fileFullName.length()); // 예) .jpg
//
//				// 파일 확장자 필터링
//				if(!fileExt.equals(".exe") && !fileExt.equals(".bat")){
//
//					//디렉토리 없을경우 생성
//					File dir = new File(uploadFullPath);
//					if(!dir.exists()) {
//						dir.mkdirs();
//					}
//
//					System.out.println("uploadFullPath : " + uploadFullPath + " // fileFullName : " + fileFullName);
//					//파일 중복체크
//					File checkFile = new File(uploadFullPath, fileFullName);
//					int count = 0;
//					while (!checkFile.createNewFile() && count < 9999) {
//						count++;
//						fileFullName = fileName + "(" + count + ")" + fileExt;
//						checkFile = new File(checkFile.getParent(), fileFullName);
//				    }
//
//					file.transferTo(checkFile);
//
//					//System.out.println("fileUploadPath:::"+uploadFullPath + fileFullName);
//
//					listMap = new HashMap<String,Object>();
//		            listMap.put("uploadFilePath", uploadPath + fileFullName);
//		            listMap.put("fileName", fileFullName);
//		            listMap.put("fileSize", file.getSize());
//		            listMap.put("fieldNm", name);
//		            list.add(listMap);
//				}
//			}
//		}
//
//		return list;
//	}
//
//
//	/**
//	 * 디렉토리에서 파일 삭제
//	 * @param delFileName
//	 * @param uploadPath
//	 * @return
//	 */
//	public static String deleteFile(String delFileName, String uploadPath){
//
//		String uploadFullPath = mhpDomain + uploadPath;
//
//		try{
//			File checkFile = new File(uploadFullPath, delFileName);
//			if(checkFile.exists()){
//				checkFile.delete();
//			}
//
//		}catch(Exception e){
//			return "fail";
//		}
//
//        return "success";
//	}
//
//}
