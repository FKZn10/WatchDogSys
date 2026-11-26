package com.jpmsoft.common.model.requestAndResponse;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.util.Date;

/**
 * @author yb.Sun
 * @date 2024/10/23 16:24
 */
@Data
public class DeleteLogDto  extends BaseFileDto {
    private String id;
    private String deleteFunctionCode;
    private String deleteSourceId;
    private String deleteReason;
    private Date deleteTime;
}
