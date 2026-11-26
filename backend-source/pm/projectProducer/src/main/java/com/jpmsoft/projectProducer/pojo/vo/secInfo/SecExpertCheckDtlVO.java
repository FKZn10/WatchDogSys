package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 专家考核内容
 *
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 9:50
 */
@Data
public class SecExpertCheckDtlVO {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String ecId;
    private String secdContent;
    private Integer secdResult;
    private BigDecimal secdScore;
    private Integer secdSort;
    private Date ecStateTime;
}
