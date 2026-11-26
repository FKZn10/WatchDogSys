package com.jpmsoft.projectProducer.pojo.entity.oa;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 知识库目录
 */
@Data
@TableName("OA_Knowledge_Base")
public class OaKnowledgeBase {

    @TableId(type = IdType.INPUT)
    private String kbId;
    private String kbName;
    private String kbParentId;
    private String kbRemark;
    private Date kbCreateTime;
    private String kbCreateUid;
    private String kbCreateUname;
    private Integer kbState;
    private String kbCode;
    private String kbType;
    private String kbPermission;
    private Integer kbSort;
    private String kbNature;
    private String kbPurpose;
    private String kbLink;
    private String teId;

    @TableField(exist = false)
    private List<String> powerCodes;
}
