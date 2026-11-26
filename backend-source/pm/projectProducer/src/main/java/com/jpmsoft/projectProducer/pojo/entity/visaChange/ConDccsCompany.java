package com.jpmsoft.projectProducer.pojo.entity.visaChange;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 抄送单位
 *
 * @Author: 李文涛 aaron
 * @date: 2023/4/6 17:47
 */
@Data
@TableName("Con_Dccs_Company")
public class ConDccsCompany {

    @TableId(type = IdType.INPUT)
    private String id;
    private String dccsId;
    private String cId;
}
