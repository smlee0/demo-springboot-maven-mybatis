package com.medialog.uplussave.common.entity;

import java.util.Map;
import org.apache.ibatis.type.Alias;
import lombok.Data;

/**
 * 권한 VO
 * @filename Roles.java
 * @author Lee Se Min
 * @since 2022-06-10
 *
 * COPYRIGHT © MEDIALOG CORP. ALL RIGHTS RESERVED.
 */
@Data
@Alias("Roles")
public class Roles {

	 String idx;
	 String roles;

	 String code;
	 String prCode;
	 String rolesIdx;
	 String name;

	 Map<String, Object> authData;
}
