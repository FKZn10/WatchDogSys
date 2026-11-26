package com.jpmsoft.gmgws.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/4 14:28
 */
@Data
public class BaseEntity {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private Integer state;
    private Date stateTime;
    private String createUid;
    private String createUname;
    private Date createTime;
}
