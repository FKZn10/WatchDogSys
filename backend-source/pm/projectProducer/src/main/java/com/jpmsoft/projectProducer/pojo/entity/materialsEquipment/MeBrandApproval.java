package com.jpmsoft.projectProducer.pojo.entity.materialsEquipment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 项目品牌报审
 *
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 10:17
 */
@Data
@TableName("Me_Brand_Approval")
public class MeBrandApproval {
    @TableId(type = IdType.INPUT)
    private String baId;
    private String pId;
    private String conId;
    private String secId;
    private String baCode;
    private String baPm;
    private String baCategory;
    private String baType;
    private String baProfessional;
    private String baTechnical;
    private String baRemark;
    /**
     * 本次报审品种名称
     */
    private String baMetNames;
    /**
     * 本次报审品牌名称
     */
    private String baBrands;
    /**
     * 本次报审品牌数量
     */
    private String badCounts;
    private Integer state;
    private Date stateTime;
    private String createUid;
    private String createUname;
    private Date createTime;
    private String baBidFile;
    private String baLicenseFile;
    private String baQaFile;
    private String baAgentFile;
    private String baSamplePic;
    private String baInfoPic;
    private String baPhysicalPic;
    private String baOtherFile;
    private String teId;

    /**
     * 报审次数
     */
    private Integer reviewsCount;
}
