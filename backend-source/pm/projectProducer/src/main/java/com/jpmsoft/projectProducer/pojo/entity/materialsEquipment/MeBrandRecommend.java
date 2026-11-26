package com.jpmsoft.projectProducer.pojo.entity.materialsEquipment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 材料设备品牌推荐表
 *
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 9:40
 */
@Data
@TableName("Me_Brand_Recommend")
public class MeBrandRecommend {

    @TableId(type = IdType.INPUT)
    private String brId;
    private String brCode;
    private String brTitle;
    private String pId;
    private String secId;
    private String brRemark;
    private Integer state;
    private Date stateTime;
    private String createUid;
    private String createUname;
    private Date createTime;
    private String teId;
}
