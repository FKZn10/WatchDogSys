
package com.jpmsoft.projectConsumer.pojo.dto.secInfo;

import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 招标意向
 *
 * @Author: 李文涛 aaron
 * @date: 2024/3/20 16:11
 */
@Data
public class SecIntentionDTO extends BaseFileRequest {
    @ProcessFields(type = ProcessFieldEnum.id)
    private String id;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer state;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date stateTime;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String createUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String createUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date createTime;

    private String siCode;
    private String siCatalogName;
    private String siOverview;
    private BigDecimal siAmt;
    private Date siTime;
    private String siRemark;
    private String secId;
    private String secName;
    private BigDecimal secMoney;
}
