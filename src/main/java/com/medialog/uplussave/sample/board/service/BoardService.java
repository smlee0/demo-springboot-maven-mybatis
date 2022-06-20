package com.medialog.uplussave.sample.board.service;

import com.medialog.uplussave.common.entity.Result;
import com.medialog.uplussave.sample.board.dto.Board;

/**
 * 샘플 게시판 서비스단 인터페이스
 * @filename BoardService.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
public interface BoardService {

    /**
     * 리스트 가져오기
     * @param board
     * @return
     * @author Lee Se Min
     * @date 2022-06-10
     */
    Result getList(Board board);

    /**
     * 단일 데이터 가져오기
     * @param board
     * @return
     * @author Lee Se Min
     * @date 2022-06-10
     */
    Board getData(Board board);

    /**
     * 이전글 가져오기
     * @param board
     * @return
     * @author Lee Se Min
     * @date 2022-06-15
     */
    Board getPrevData(Board board);

    /**
     * 다음글 가져오기
     * @param board
     * @return
     * @author Lee Se Min
     * @date 2022-06-15
     */
    Board getNextData(Board board);
}
