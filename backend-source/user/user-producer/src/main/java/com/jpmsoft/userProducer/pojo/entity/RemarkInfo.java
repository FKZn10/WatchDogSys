package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.userProducer.pojo.BaseEntity;
import lombok.Data;
import java.io.Serializable;
/**
 * @author yb.Sun
 * @date 2024/10/25 09:12
 */
@Data
@TableName("Remark_Info")
public class RemarkInfo extends BaseEntity implements Serializable {
    private String id;
    private String remarkFunctionCode;
    private String remarkSourceId;
    private String remarkContext;
}
