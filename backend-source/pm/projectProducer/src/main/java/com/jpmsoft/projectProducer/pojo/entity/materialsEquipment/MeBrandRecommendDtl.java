package com.jpmsoft.projectProducer.pojo.entity.materialsEquipment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 材料设备品牌推荐表清单
 *
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 9:41
 */
@Data
@TableName("Me_Brand_Recommend_Dtl")
public class MeBrandRecommendDtl {

    @TableId(type = IdType.INPUT)
    private String brdId;
    private String brId;
    private Integer brdSort;
    private String metId;
    private String metName;
    private String brandId;
    private String brandName;
    private String brdRemark;
    private String brandType;
}
