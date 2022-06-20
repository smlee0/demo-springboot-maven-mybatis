package com.medialog.uplussave.sample.board.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.medialog.uplussave.common.entity.Result;
import com.medialog.uplussave.common.util.Constants;
import com.medialog.uplussave.sample.board.dto.Board;
import com.medialog.uplussave.sample.board.mapper.BoardMapper;
import com.medialog.uplussave.sample.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;

/**
 * 샘플 게시판 서비스 구현단
 * @filename BoardServiceImpl.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
@Slf4j
@Service
public class BoardServiceImpl implements BoardService {

    @Resource
    private BoardMapper boardMapper;

    @Override
    public Result getList(Board board) {

        int totalRecordCount = 0;

        //TODO 카테고리 존재 시 불러오기

        // 게시글 목록 및 게시글 개수
        totalRecordCount = boardMapper.getListCnt(board);
        List<Board> list = boardMapper.getList(board);
        log.debug(">>> list: " + list);

        // 페이징 처리
        board.setTotalRecordCount(totalRecordCount);
        board.paginate();

        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put( "count", totalRecordCount);
        data.put( Constants.KEY_PARAMS, board);

        return Result.builder().data(data).success(true).build();
    }

    @Override
    public Board getData(Board board) {
        return boardMapper.getData(board);
    }

    @Override
    public Board getPrevData(Board board) {
        return boardMapper.getPrevData(board);
    }

    @Override
    public Board getNextData(Board board) {
        return boardMapper.getNextData(board);
    }
}
