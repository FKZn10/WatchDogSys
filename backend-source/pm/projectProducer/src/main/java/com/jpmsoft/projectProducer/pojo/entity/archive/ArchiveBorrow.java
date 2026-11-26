package com.jpmsoft.projectProducer.pojo.entity.archive;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 档案借阅
 */
@Data
@TableName("Archive_Borrow")
public class ArchiveBorrow {
    @TableId(type = IdType.INPUT)
    private String abId;
    private String afId;
    private String abCreateUid;
    private String abCreateUname;
    private Date abCreateTime;
    private Date abTime;
    private Date abReturnTime;
    private String abUse;
    private String abApproverUid;
    private String abApproverUname;
    private String abOperatorUid;
    private String abOperatorUname;
    private String abRemark;
    private Integer abState;
    private Date abStateTime;
    private String teId;
}
