package com.jpmsoft.common.model.requestAndResponse;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

/**
 * @author yb.Sun
 * @date 2024/10/25 09:14
 */
@Data
public class RemarkInfoDto extends BaseFileDto {
    private String id;
    private String remarkFunctionCode;
    private String remarkSourceId;
    private String remarkContext;
}
