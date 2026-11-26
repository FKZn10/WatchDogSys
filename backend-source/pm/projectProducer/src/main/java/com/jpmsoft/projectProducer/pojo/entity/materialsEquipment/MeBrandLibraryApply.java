package com.jpmsoft.projectProducer.pojo.entity.materialsEquipment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 材料设备品牌库建立（更新）审批表
 *
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 9:03
 */
@Data
@TableName("Me_BrandLibrary_Apply")
public class MeBrandLibraryApply {

    @TableId(type = IdType.INPUT)
    private String blaId;
    private String blaCode;
    private String blaTitle;
    private String blaRemark;
    private Date blaDate;
    private Integer state;
    private Date stateTime;
    private String createUid;
    private String createUname;
    private Date createTime;
    private String teId;
}
