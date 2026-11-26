package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 专家选取记录（专家库、建管专家库等）
 */
@Data
@TableName("Sec_Selection_Record_Multiple")
public class SecSelectionRecordMultiple {
    @TableId(type = IdType.INPUT)
    private String id;
    //专家库类型：1、专家库 2、建管专家库
    private Integer expertDbType;
    //对应库的选取记录表id
    private String srId;
    //项目id
    private String pId;
    //标段id
    private String secId;

    private Integer state;
    private Date stateTime;
    private Date createTime;
    private String createUid;
    private String createUname;
    private String teId;
    /**
     * 备注
     */
    private String srmRemark;

}
