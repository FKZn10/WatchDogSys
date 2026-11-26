package com.jpmsoft.projectProducer.pojo.entity.oa;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 变更超时会议解锁
 *
 * @Author: 李文涛 aaron
 * @date: 2024/10/29 14:34
 */
@Data
@TableName("Change_Timeout_Unlock")
public class ChangeTimeoutUnlock extends BaseEntity {

    private String cmnId;
    private String ctuPeople;
    private Date ctuTime;
    private String ctuProcessedUnit;
    private String ctuTimeoutReason;
    private String ctuHandlingSuggestion;
    private String ctuRemark;
}
