package com.jpmsoft.projectProducer.pojo.entity.visaChange;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 设计变更联系单
 *
 * @Author: 李文涛 aaron
 * @date: 2023/4/6 17:45
 */
@Data
@TableName("Con_Dccs")
public class ConDccs {

    @TableId(type = IdType.INPUT)
    private String dccsId;
    private String pId;
    private String conId;
    private String dccsCode;
    private String dccsName;
    private String dccsMajor;
    private String dccsMainUnit;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String teId;
}
