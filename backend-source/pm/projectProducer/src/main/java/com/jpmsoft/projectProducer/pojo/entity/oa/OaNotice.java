package com.jpmsoft.projectProducer.pojo.entity.oa;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 通知公告
 */
@Data
@TableName("OA_Notice")
public class OaNotice {

    @TableId(type = IdType.INPUT)
    private String nId;
    private String nTitle;
    private String nCode;
    private String nType;
    private String nContent;
    private String nKeyword;
    private String nCreateUid;
    private String nCreateUname;
    private Date nCreateTime;
    private Integer nState;
    private Date nStateTime;
    private String dId;
    private String pId;
    private String cId;
    private String nThumbnail;
    /**
     * 范围类型
     */
    private String rangeType;
    /**
     * 具体范围
     */
    private String specificScopeid;
    /**
     *
     */
    private String specificScopeName;
    /**
     * 优先程度
     */
    private String priority;
    private String teId;

    /**
     * 是否添加文号
     */
    private String addSymbol;
}
