//package com.medialog.uplussave.common.mapper;
//
//import java.util.List;
//import java.util.Map;
//import org.apache.ibatis.annotations.Mapper;
//import com.medialog.uplussave.common.entity.Roles;
//import com.namandnam.nni.common.vo.AttachFile;
//import com.namandnam.nni.common.vo.AttachFiles;
//import com.namandnam.nni.common.vo.SampleVo;
//
//@Mapper
//public interface CommonMapper {
//
//	List<Roles> selectRolesList();
//
//	AttachFiles fileInfoSelectOne(String fileNo);
//
//	AttachFile selectOneFileInfo(String fileIdx);
//
//	AttachFile selectOneByIdx(String fileIdx);
//
//	int insertIntoAttachFiles( AttachFiles af );
//
//	int updateIntoAttachFiles( AttachFiles af );
//
//	int insertIntoAttachFile( AttachFile af );
//
//	int updateIntoAttachFile( AttachFile af );
//
//	int deleteAttachFile( String fileIdx );
//
//
//	/**
//	 * 아래부터는 sample 관련 method
//	 */
//
//	SampleVo selectOneSampleData( String idx );
//
//	int insertIntoSampleData( SampleVo sv );
//
//	List<Map<String, Object>> selectSampleList( List<String> af );
//
//    List<AttachFiles> selectFilesList(AttachFiles attach);
//
//    int deleteFileOne(String no);
//
//	int deleteFile(AttachFiles attachFiles);
//
//	void updateIntoFileList(AttachFiles attachFile);
//
//	/**
//	 * 아래부터는 POPUP 관련
//	 */
//	List<AttachFiles> selectFilesTnoList(AttachFiles attachFiles);
//
//	List<AttachFiles> selectPopupFilesList(AttachFiles attach);
//
//	int updateOverrideAttachFiles(AttachFiles attach);
//
//	AttachFiles selectPopupFileData(AttachFiles attach);
//
//}
