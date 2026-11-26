package com.jpmsoft.projectProducer.pojo.vo.materialsEquipment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 材料设备品牌推荐表清单
 *
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 9:41
 */
@Data
public class MeBrandRecommendDtlVo implements Serializable {

    private static final long serialVersionUID = -1550998226767762740L;
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
