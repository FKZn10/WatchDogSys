package com.jpmsoft.projectProducer.pojo.entity.completion;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("building_info_repository")
public class BuildingInfoRepository {

    /**
     * 主键ID
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 上级ID
     */
    private String parentId;

    /**
     * 建筑名称
     */
    private String birName;

    /**
     * 所属校区/区域
     */
    private String birRegion;

    /**
     * 用途说明
     */
    private String birRemark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人ID
     */
    private String createUid;

    /**
     * 创建人名称
     */
    private String createUname;


}
