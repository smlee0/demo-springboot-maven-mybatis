//package com.medialog.uplussave.common.controller;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.net.URLEncoder;
//import java.util.HashMap;
//
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.namandnam.nni.common.service.FileService;
//import com.namandnam.nni.common.utils.Encrypt;
//import com.namandnam.nni.common.vo.AttachFiles;
//import org.apache.commons.io.FileUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.namandnam.nni.common.vo.AttachFile;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Controller
//@RequiredArgsConstructor
//@RequestMapping(value = "/file")
//public class FileController {
//
//	@Value("${env.file.path}")
//	private String prefix_upload_path;
//
//	private final FileService fileService;
//
//	/**
//	 * 에디터 안에서 이미지 업로드
//	 *
//	 * @param upload
//	 * @return
//	 */
//	@RequestMapping(value = "/editorImageUpload", method = RequestMethod.POST)
//	@ResponseBody
//	public HashMap<String, Object> editorImageUpload( @RequestParam MultipartFile upload, HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		HashMap<String, Object> rt = new HashMap<String, Object>();
//
//		try {
//
//			AttachFiles af = fileService.saveFilesInfo(upload, "/public");
//
//			// String fileUrl = "/attach/" + af.getSubPath() + "/" + af.getFilename();
//			String fileUrl = "/attach/" + af.getFilename();
//
//			rt.put("filename", af.getFilename());
//			rt.put("uploaded", 1);
//			rt.put("url", fileUrl);
//
//		} catch(IOException e) {
//			e.printStackTrace();
//		}
//
//		return rt;
//	}
//
//	/**
//	 * 파일 단일 다운로드
//	 *
//	 * @param fileNo
//	 * @return
//	 */
//	@RequestMapping(value="/download")
//	public void fileDonwload(@RequestParam("fileNo") String fileNo, HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		try {
//
//			//TODO fileIdx 기준으로 정보를 경로를 가져온다.
//			AttachFiles af = fileService.fileInfoSelectOne(fileNo);
//
//			if( af != null && !af.getFilename().equals("") ) {
//
//				String filePath = prefix_upload_path + af.getFilePath() + File.separator + af.getFilename();
//				log.debug(">>> filePath: " + filePath);
//
//				File downloadFile = new File(filePath);
//				ServletOutputStream outStream = null;
//				FileInputStream inputStream = null;
//
//				//파일이 존재 할 경우
//				if(downloadFile.exists()) {
//					try {
//
//						outStream = response.getOutputStream();
//						inputStream = new FileInputStream(downloadFile);
//
//						String downFileName = java.net.URLEncoder.encode( af.getFilenameOrg(), "UTF-8");
//
//						response.setContentType("application/octet-stream");
//						response.setContentLength((int)downloadFile.length());
//						response.setHeader("Content-Disposition", "attachment; fileName=\"" + downFileName +"\";");
//						response.setHeader("Content-Transfer-Encoding", "binary");
//						//Writing InputStream to OutputStream
//						byte[] outByte = new byte[4096];
//						while(inputStream.read(outByte, 0, 4096) != -1){
//							outStream.write(outByte, 0, 4096);
//						}
//					} catch (Exception ee) {
//						ee.printStackTrace();
//					} finally {
//						inputStream.close();
//						outStream.flush();
//						outStream.close();
//					}
//				}//file exist
//				else {
//					log.debug("NO FILE");
//				}
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//
//
//	/**
//	 * 에디터 안에서 이미지 업로드 (backup)
//	 *
//	 * @param upload
//	 * @return
//	 */
//	@RequestMapping(value = "/editorImageUpload.do", method = RequestMethod.POST)
//    @ResponseBody
//    public HashMap<String, Object> communityImageUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam MultipartFile upload) throws Exception {
//
//        HashMap<String, Object> rt = new HashMap<String, Object>();
//
//        try {
//
//    		AttachFile af = fileService.saveFileInfo(upload, "public");
//
//            String fileUrl = "/attach/" + af.getSubPath() + "/" + af.getFileName();
//
//            rt.put("filename", af.getFileName());
//            rt.put("uploaded", 1);
//            rt.put("url", fileUrl);
//
//        } catch(IOException e) {
//            e.printStackTrace();
//        }
//
//		return rt;
//    }
//
//	/**
//	 * 파일 다운로드 (backup)
//	 *
//	 * @param fileIdx
//	 * @return
//	 */
//	@RequestMapping(value="/download.do")
//	public void donwloadPage(HttpServletRequest request, HttpServletResponse response, @RequestParam("fileIdx") String fileIdx) throws Exception {
//
//		try {
//
//			//TODO fileIdx 기준으로 정보를 경로를 가져온다.
//			AttachFile af = fileService.selectOneFileInfo(fileIdx);
//
//			if( af != null && !af.getFileName().equals("") ) {
//
//				String filePath = af.getFilePath() + File.separator + af.getFileName();
//
//				File downloadFile = new File(filePath);
//				ServletOutputStream outStream = null;
//				FileInputStream inputStream = null;
//
//				//파일이 존재 할 경우
//				if(downloadFile.exists()) {
//					try {
//
//						outStream = response.getOutputStream();
//						inputStream = new FileInputStream(downloadFile);
//
//						String downFileName = java.net.URLEncoder.encode( af.getOrgnlFileName(), "UTF-8");
//
//						response.setContentType("application/octet-stream");
//						response.setContentLength((int)downloadFile.length());
//						response.setHeader("Content-Disposition", "attachment; fileName=\"" + downFileName +"\";");
//					    response.setHeader("Content-Transfer-Encoding", "binary");
//						//Writing InputStream to OutputStream
//						byte[] outByte = new byte[4096];
//						while(inputStream.read(outByte, 0, 4096) != -1){
//							outStream.write(outByte, 0, 4096);
//						}
//					} catch (Exception ee) {
//						ee.printStackTrace();
//					} finally {
//						inputStream.close();
//						outStream.flush();
//						outStream.close();
//					}
//				}//file exist
//				else {
//					log.debug("NO FILE");
//				}
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//    }
//
//	/**
//	 * 파일 다운로드2 (backup)
//	 *
//	 * @param idx
//	 * @return
//	 */
//	@GetMapping("/fileDown.do")
//	public void downloadFile(@RequestParam("fileIdx") String idx, HttpServletResponse response) {
//		try {
//			AttachFile attachFile = fileService.selectOneByIdx(idx);
//		    byte fileByte[] = FileUtils.readFileToByteArray(new File(attachFile.getFilePath() + File.separator + attachFile.getFileName()));
//
//		    response.setContentType("application/octet-stream");
//		    response.setContentLength(fileByte.length);
//		    response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(attachFile.getOrgnlFileName(),"UTF-8")+"\";");
//		    response.setHeader("Content-Transfer-Encoding", "binary");
//		    response.getOutputStream().write(fileByte);
//
//		    response.getOutputStream().flush();
//		    response.getOutputStream().close();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//
//	/**
//	 * 파일 단일 다운로드
//	 * 암호화된 fileNo decode하여 파일 정보 확인
//	 * @param encData
//	 * @return
//	 */
//	@RequestMapping(value="/encInfo")
//	public void encInfoDonwload(@RequestParam("encData") String encData, HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		try {
//
//			//TODO fileIdx 기준으로 정보를 경로를 가져온다.
//			String fileNo = Encrypt.decryptAES256(encData);
//			AttachFiles af = fileService.fileInfoSelectOne(fileNo);
//
//			if( af != null && !af.getFilename().equals("") ) {
//
//				String filePath = prefix_upload_path + af.getFilePath() + File.separator + af.getFilename();
//				log.debug(">>> filePath: " + filePath);
//
//				File downloadFile = new File(filePath);
//				ServletOutputStream outStream = null;
//				FileInputStream inputStream = null;
//
//				//파일이 존재 할 경우
//				if(downloadFile.exists()) {
//					try {
//
//						outStream = response.getOutputStream();
//						inputStream = new FileInputStream(downloadFile);
//
//						String downFileName = java.net.URLEncoder.encode( af.getFilenameOrg(), "UTF-8");
//
//						response.setContentType("application/octet-stream");
//						response.setContentLength((int)downloadFile.length());
//						response.setHeader("Content-Disposition", "attachment; fileName=\"" + downFileName +"\";");
//						response.setHeader("Content-Transfer-Encoding", "binary");
//						//Writing InputStream to OutputStream
//						byte[] outByte = new byte[4096];
//						while(inputStream.read(outByte, 0, 4096) != -1){
//							outStream.write(outByte, 0, 4096);
//						}
//					} catch (Exception ee) {
//						ee.printStackTrace();
//					} finally {
//						inputStream.close();
//						outStream.flush();
//						outStream.close();
//					}
//				}//file exist
//				else {
//					log.debug("NO FILE");
//				}
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//
//}
