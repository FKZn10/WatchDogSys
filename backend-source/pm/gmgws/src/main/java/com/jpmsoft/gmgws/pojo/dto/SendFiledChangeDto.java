package com.jpmsoft.gmgws.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 字段变更记录
 * <p>
 * Created By:李文涛 aaron
 * Created Time:2022/6/22 10:33
 */
@Data
public class SendFiledChangeDto implements Serializable {

    private static final long serialVersionUID = -6658346620731800207L;

    private String olds;
    private String news;
    private String tableName;
    private String userId;

}
