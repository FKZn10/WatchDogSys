package com.jpmsoft.common.model.requestAndResponse;

import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 系统配置
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysConfigBaseDTO extends BasePageEntity {
    /**
     * 主键
     */
    private String id;

    /**
     * 创建人id
     */
    private String createUid;

    /**
     * 创建人名称
     */
    private String createUname;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人id
     */
    private String updateUid;

    /**
     * 更新人名称
     */
    private String updateUname;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 类型
     */
    private String confType;

    /**
     * 分类
     */
    private String confCategory;

    /**
     * 编码
     */
    private String confCode;

    /**
     * 配置内容
     */
    private String confContent;

    /**
     * 状态
     */
    private Integer status;
}
