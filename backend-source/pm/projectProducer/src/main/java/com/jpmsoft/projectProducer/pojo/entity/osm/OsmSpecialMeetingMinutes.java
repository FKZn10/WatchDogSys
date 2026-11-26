package com.jpmsoft.projectProducer.pojo.entity.osm;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 专题会议纪要(OsmSpecialMeetingMinutes)实体类
 *
 * @since 2024-12-05 11:55:00
 */
@Data
@TableName("osm_special_meeting_minutes")
public class OsmSpecialMeetingMinutes implements Serializable {
    private static final long serialVersionUID = 594386965410785772L;
    /**
     * id
     */
    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 编码
     */
    private String code;
    /**
     * 处理人id
     */
    private String handlerUserId;
    /**
     * 处理人姓名
     */
    private String handlerUserName;
    /**
     * 提交时间
     */
    private Date handlerTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建人id
     */
    private String createUid;
    /**
     * 创建人姓名
     */
    private String createUname;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 流程状态
     */
    private Integer state;
    /**
     * 审批时间
     */
    private Date stateTime;


}

