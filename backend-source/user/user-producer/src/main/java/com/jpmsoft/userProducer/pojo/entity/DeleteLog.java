package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.userProducer.pojo.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yb.Sun
 * @date 2024/10/23 16:07
 */
@Data
@TableName("Delete_Log")
public class DeleteLog extends BaseEntity implements Serializable {
    private String id;
    private String deleteFunctionCode;
    private String deleteSourceId;
    private String deleteReason;
    private Date deleteTime;
}
