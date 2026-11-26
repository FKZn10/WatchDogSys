package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 前端组件缓存
 * 归档目录选择/用户选择/字典选择等等
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/25 20:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WebComponentCacheDTO extends BaseModeRequest {

    private String id;
    private String wccCode;
    private String wccField;
    private String wccValue;
    private String wccKey;
    private String createUid;
    private String createUname;
    private Date createTime;
}
