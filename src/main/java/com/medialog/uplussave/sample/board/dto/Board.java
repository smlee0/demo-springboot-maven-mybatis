package com.medialog.uplussave.sample.board.dto;

import java.io.Serializable;
import org.apache.ibatis.type.Alias;
import com.medialog.uplussave.common.entity.PageableParams;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 샘플 게시판 Board 테이블 컬럼
 * @filename Board.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
//@Getter
//@Setter
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Alias("Board")
@EqualsAndHashCode(callSuper=false)
public class Board extends PageableParams implements Serializable {

    // Board 테이블 컬럼
    private String seq;
    private String title;
    private String contents;
    private String views;
    private String topYn;
    private String mainPstYn;
    private String linkUrl;
    private String regDt;
    private String regIdn;
    private String modDt;
    private String modIdn;

    // 검색 등 기타 값은 아래에 기재
    private String searchTxt;
    private String searchType;

}
