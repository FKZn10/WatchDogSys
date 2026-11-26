package com.jpmsoft.projectProducer.pojo.dto.fund;

import com.jpmsoft.common.base.BaseFileDto;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import lombok.Data;

import java.util.Date;

@Data
public class FundNumberInfoDto extends BaseFileDto {
    @ProcessFields(type = ProcessFieldEnum.id)
    private String id;
    private String pId;
    private String fnName;
    private String fnCode;
    private String prId;
    private Double fnAmount;
    private Date fnReceiptVerificationDate;
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
}
