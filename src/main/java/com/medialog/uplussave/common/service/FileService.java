//package com.medialog.uplussave.common.service;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//import org.apache.commons.io.FilenameUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import com.medialog.uplussave.common.mapper.CommonMapper;
//import com.medialog.uplussave.common.util.CommUtil;
//import com.medialog.uplussave.common.util.OSValidator;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class FileService {
//
//	@Value("${env.file.path}")
//	private String prefix_upload_path;
//
//	@Value("${env.file.editor.path}")
//	private String prefix_editor_upload_path;
//
//	@Value("${env.file.dataroom.path}")
//	private String suffix_upload_path;
//
//	private final CommonMapper commonMapper;
//
//	/**
//	 *
//	 * @param fileNo
//	 * @return
//	 * @throws Exception
//	 */
//	public AttachFiles fileInfoSelectOne( String fileNo ) throws Exception {
//
//		log.debug(" fileNo : " + fileNo );
//		return commonMapper.fileInfoSelectOne( fileNo );
//	}
//
//	/**
//	 *
//	 * @param file
//	 * @return
//	 * @throws Exception
//	 */
//	public AttachFiles saveFilesInfo( MultipartFile file ) throws Exception {
//
//		return saveFilesInfo( file, null );
//	}
//
//	/**
//	 *
//	 * @param file
//	 * @param subPath
//	 * @return
//	 * @throws Exception
//	 */
//	public AttachFiles saveFilesInfo(MultipartFile file, String subPath ) throws Exception {
//	    log.debug(">>> subPath: " + subPath);
//
//		String[] validExt = {"gif","png","jpg","jpeg","doc","docx","xls","xlsx","hwp","pdf","ppt","pptx","zip","mp4"};
//		//String[] validType = {"", "", ""};
//
//		boolean isCan = false;
//		AttachFiles attach = new AttachFiles();
//
//		String originalFileName = null;
//		String originalFileExtension = null;
//		String originalFileContentType = null;
//		String originalFileSize = null;
//		String storedFileName = null;
//
//		if (file != null && !file.getOriginalFilename().isEmpty()) {
//			try {
//				//String yyymm = subPath == null ? CommUtil.getYYYYMM(0) : subPath;
//				//String filePath = prefix_upload_path + suffix_upload_path + File.separator + yyymm;
//				String filePath = prefix_upload_path;
//				if(subPath != null) {
//					// filePath = prefix_upload_path + suffix_upload_path + "/" + subPath;
//					filePath = prefix_upload_path + subPath;
//					/*if(subPath.equals("/public")) {
//						filePath = prefix_upload_path + subPath;
//					}*/
//				}
//				log.debug(">>> filepath: " + filePath);
//
//				File dirs = new File(filePath);
//				if (dirs.exists() == false) {
//					dirs.mkdirs();
//					if( !OSValidator.isWindows() ) {
//						Runtime.getRuntime().exec("chmod -R 777 " + filePath);
//					}
//				}
//
//				//byte[] bytes = file.getBytes();
//				originalFileName = CommUtil.cleanFName(file.getOriginalFilename());
//				originalFileExtension = (FilenameUtils.getExtension(originalFileName)).toLowerCase();
//				originalFileSize = "" + file.getSize();
//				originalFileContentType = file.getContentType();
//
//				for (int i = 0; i < validExt.length; i++) {
//					if (originalFileExtension.equalsIgnoreCase(validExt[i])) {
//						isCan = true;
//						break;
//					}
//				}
//
//				if (isCan) {
//
//					storedFileName = CommUtil.getUUID() + "." + originalFileExtension;
//
//					//attach.setFileType( CommUtil.nvl(originalFileContentType) );
//					attach.setFilename( CommUtil.nvl(storedFileName) );
//					attach.setFilenameOrg( CommUtil.nvl(originalFileName) );
//					attach.setFileSize( originalFileSize );
//					attach.setFilePath( subPath );
//
//					//attach.setSubPath( yyymm );	//editor에서 사용하기 위해서
//
//					String uploadPath = filePath + File.separator +  storedFileName;
//
//					file.transferTo(new File(uploadPath));
//				}
//			} catch(IOException e) {
//				log.error("fail to process file", e);
//				throw e;
//			} finally {
//			}
//		}
//		return attach;
//	}
//
//	/**
//	 *
//	 * @param fileIdx
//	 * @return
//	 * @throws Exception
//	 */
//	public int deleteFileInfo( String fileIdx ){
//
//		return commonMapper.deleteAttachFile( fileIdx );
//	}
//
//
//    public List<AttachFiles> selectFilesList(AttachFiles attach) {
//		return commonMapper.selectFilesList(attach);
//    }
//
//	public int deleteFileOne(String no) {
//		AttachFiles attachFiles = commonMapper.fileInfoSelectOne(no);
//
//		if(attachFiles != null && !attachFiles.equals("")) {
//			String filePath = prefix_upload_path + attachFiles.getFilePath() + File.separator + attachFiles.getFilename();
//            log.debug(">>> filePath: " + filePath);
//
//			File file = new File(filePath);
//			if(file.exists()) {
//				file.delete();
//			}
//		}
//
//		return commonMapper.deleteFileOne(no);
//	}
//
//	public int deleteFile(List<String> noList, String tableNm) {
//		AttachFiles attachFiles = new AttachFiles();
//		attachFiles.setTableNm(tableNm);
//		attachFiles.setNoList(noList);
//
//		List<AttachFiles> selectFilesTnoList = commonMapper.selectFilesTnoList(attachFiles);
//
//		if(selectFilesTnoList != null && !selectFilesTnoList.equals("")) {
//			for (int i = 0; i < selectFilesTnoList.size(); i++) {
//				selectFilesTnoList.get(i).getFilePath();
//				String filePath = prefix_upload_path
//						+ selectFilesTnoList.get(i).getFilePath()
//						+ File.separator
//						+ selectFilesTnoList.get(i).getFilename();
//				log.debug(">>> filePath: " + filePath);
//
//				File file = new File(filePath);
//				if(file.exists()) {
//					file.delete();
//				}
//			}
//		}
//
//		return commonMapper.deleteFile(attachFiles);
//	}
//
//
//
//
//	//POPUP 관련
//    public AttachFiles selectPopupFileData(String no, String tableNm) {
//    	AttachFiles attachFiles = new AttachFiles();
//		attachFiles.setTableNm(tableNm);
//		attachFiles.setNo(no);
//
//		return commonMapper.selectPopupFileData(attachFiles);
//    }
//
//
//}
