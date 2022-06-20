package com.medialog.uplussave.sample.board.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.medialog.uplussave.common.entity.Result;
import com.medialog.uplussave.common.util.CommUtil;
import com.medialog.uplussave.common.util.Constants;
import com.medialog.uplussave.sample.board.dto.Board;
import com.medialog.uplussave.sample.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 샘플게시판 컨트롤러
 * @filename BoardController.java
 * @author Lee Se Min
 * @since 2022-06-09
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
@Slf4j
@Controller
@RequestMapping("/sample/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * 샘플 게시글 리스트
     * @param model
     * @param req
     * @param board
     * @return String
     * @author Lee Se Min
     * @throws Exception
     * @date 2022-06-09
     */
    @GetMapping(value = "/list")
    public String list(ModelMap model, HttpServletRequest request, Board board) throws Exception {
        log.debug("====== BoardController.list Start. ======");

        log.debug(">>> getSearchTxt: " + board.getSearchTxt());
        log.debug(">>> getSearchType: " + board.getSearchType());

        Result result = boardService.getList(board);

        model.addAttribute(Constants.KEY_RESULT, result);
        model.addAttribute("board", board);

        return CommUtil.checkDevReturnPageUrl(request, "/sample/board/list");
    }

    /**
     * 샘플 게시글 상세보기
     * @param seq
     * @param model
     * @param request
     * @return String
     * @author Lee Se Min
     * @date 2022-06-09
     */
    @GetMapping(value = "/view/{seq}")
    public String view(@PathVariable String seq, ModelMap model, HttpServletRequest request) throws Exception {
        log.debug("====== BoardController.view Start. ======");

        Board board = Board.builder()
                .seq(seq)
                .build();

        // 단일 상세 데이터
        model.addAttribute(Constants.KEY_DATA, boardService.getData(board));

        //TODO 이전/다음글 데이터
        model.addAttribute("prevData", boardService.getPrevData(board));
        model.addAttribute("nextData", boardService.getNextData(board));

        return CommUtil.checkDevReturnPageUrl(request, "/sample/board/view");
    }

    /**
     * 샘플 게시글 등록하기
     * @param seq
     * @param model
     * @param request
     * @param board
     * @return String
     * @author Lee Se Min
     * @date 2022-06-09
     */
    @GetMapping(value = "/reg")
    public String reg(ModelMap model, HttpServletRequest request) throws Exception {
        log.debug("====== BoardController.reg Start. ======");

        return CommUtil.checkDevReturnPageUrl(request, "/sample/board/reg");

    }

    /**
     * 샘플 게시글 수정하기
     * @param seq
     * @param model
     * @param request
     * @param board
     * @return String
     * @author Lee Se Min
     * @date 2022-06-09
     */
    @GetMapping(value = "/edit/{seq}")
    public String edit(@PathVariable String seq, ModelMap model, HttpServletRequest request) throws Exception {
        log.debug("====== BoardController.edit Start. ======");

        Board board = Board.builder()
                .seq(seq)
                .build();

        model.addAttribute(Constants.KEY_DATA, boardService.getData(board));

        return CommUtil.checkDevReturnPageUrl(request, "/sample/board/reg");
    }

    /**
     * 샘플 게시글 등록 Action
     * @param board
     * @param mreq
     * @return Result
     * @author Lee Se Min
     * @date 2022-06-10
     */
    @PostMapping(value = "/")
    @ResponseBody
    public Result postAct(Board board, MultipartHttpServletRequest mreq) {
        log.debug("====== BoardController.postAct Start. ======");
        Result result = checkValid(board);

        //result에 success값이 true일 때
        if (result.isSuccess()) {

//            if(insertRegData > 0) {
//                result.setSuccess(true);
//                result.setMessage("등록 하였습니다.");
//            } else {
//                result.setSuccess(false);
//                result.setMessage("등록에 실패 하였습니다. 잠시 후 다시 시도해 주세요.");
//                return result;
//            }

            //TODO 파일 등록 처리
            if (mreq != null) {

            }

        } else {
            result.setSuccess(false);
            result.setMessage("등록에 실패 하였습니다. 잠시 후 다시 시도해 주세요.");
            return result;
        }

        return result;
    }

    /**
     * 샘플 게시글 수정 Action
     * @param board
     * @param mreq
     * @return Result
     * @author Lee Se Min
     * @date 2022-06-10
     */
    @PutMapping(value = "/")
    @ResponseBody
    public Result putAct(Board board, MultipartHttpServletRequest mreq) {
        log.debug("====== BoardController.putAct Start. ======");

        Result result = checkValid(board);

        return result;
    }

    /**
     * 샘플 게시글 삭제 Action
     * @param board
     * @param mreq
     * @return Result
     * @author Lee Se Min
     * @date 2022-06-10
     */
    @DeleteMapping(value = "/")
    @ResponseBody
    public Result delAct(Board board, MultipartHttpServletRequest mreq) {
        log.debug("====== BoardController.delAct Start. ======");
        Result result = Result.builder()
                .success(false)
                .message("게시글 삭제 중 오류가 발생하였습니다. 잠시 후 다시 시도해주세요.")
                .build();

        //TODO 다중 삭제 일 경우
//        List<String> noList = new ArrayList<>();
//        for (Map<String, String> item : param)
//            noList.add(item.get("no"));

        //TODO 삭제여부(1 or 0)으로 판단하여 result값 변경
//        if (boardService.delData(noList) > 0) {
//            fileService.deleteFile(noList, "DATA_ROOM");
//            result.setSuccess(true);
//            result.setMessage("삭제 되었습니다.");
//        }

        return result;
    }

    /**
     * 유효성 체크
     * @param board
     * @return Result
     * @author Lee Se Min
     * @date 2022-06-10
     */
    private Result checkValid(Board board) {
        log.debug("====== BoardController.checkValid Start. ======");
        //TODO 유효성 체크 필요(내부 이중 체크)
        boolean isSuccess = true;
        String message = "";

        if (board.getTitle().length() > 200) {
            isSuccess = false;
            message = "게시글 제목을 200자 미만으로 설정해주세요.";
        }

        return Result.builder()
                .success(isSuccess)
                .message(message)
                .build();
    }


    /**
     * 파일 확장자 체크
     * @param file
     * @return boolean
     * @author Lee Se Min
     * @date 2022-06-10
     */
    private boolean checkFileExtension(MultipartFile file) {
        log.debug("====== BoardController.checkFileExtension Start. ======");
        //TODO 확장자 추가 및 제거 필요
        String[] validExt = {"gif","png","jpg","jpeg","doc","docx","xls","xlsx","hwp","pdf","ppt","pptx","zip","mp4"};

        String originalFileName = CommUtil.cleanFName(file.getOriginalFilename());
        String originalFileExtension = (FilenameUtils.getExtension(originalFileName)).toLowerCase();

        for (int i = 0; i < validExt.length; i++) {
            if (originalFileExtension.toLowerCase().equalsIgnoreCase(validExt[i])) {
                return true;
            }
        }
        return false;
    }



}
