package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 定标资料
 */
@Data
@TableName("Sec_Determin_Doc")
public class SecDeterminDoc {

    @TableId(type = IdType.INPUT)
    private String bdId;
    private String secId;
    private Integer bdRecommend;
    private String bdRecommendUnit;
    private Integer bdExcellentTeam;
    private String bdExcellentTeamUnit;
    private String bdRemark;
    private String bdCreateUid;
    private String bdCreateUname;
    private Date bdCreateTime;
    private Integer bdState;
    private Date bdStateTime;
}
