package com.medialog.uplussave.sample.board.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.medialog.uplussave.sample.board.dto.Board;

/**
 * 샘플 게시판 매핑용 인터페이스
 * @filename BoardMapper.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
@Mapper
public interface BoardMapper {

    /**
     * 리스트 가져오기
     * @param board
     * @return
     * @author Lee Se Min
     * @date 2022-06-10
     */
	List<Board> getList(Board board);

	/**
	 * 리스트 개수 가져오기
	 * @param board
	 * @return
	 * @author Lee Se Min
	 * @date 2022-06-10
	 */
	int getListCnt(Board board);

	/**
	 * 단일데이터 가져오기
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
