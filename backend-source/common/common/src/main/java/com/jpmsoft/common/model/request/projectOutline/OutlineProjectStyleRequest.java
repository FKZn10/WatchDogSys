package com.jpmsoft.common.model.request.projectOutline;

import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 样式
 *
 * @author tianhelin
 * @date 2025/8/18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OutlineProjectStyleRequest extends BaseFileRequest {
    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String category;
    private Boolean status;
    private String styleName;
    private List<OutlineProjectStyleDtlRequest> styleList;
}
