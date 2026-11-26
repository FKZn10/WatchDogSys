package com.jpmsoft.common.model.request.projectOutline;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 体系模板节点数据源
 *
 * @author tianhelin
 * @date 2025/6/11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OutlineNodeDatasourceRequest extends BaseModeRequest {
    private String id;
    /**
     * 数据源配置ID
     */
    private String odId;
    /**
     * 体系模板-目录节点ID
     */
    private String oslId;
    private Date createTime;
    private String createUid;
    private String createUname;

    // 以下是数据源的字段
    private String odName;
    private String odType;
    private String odSource;
    private String odStatus;
}
