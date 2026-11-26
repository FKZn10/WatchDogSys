package com.jpmsoft.userProducer.pojo.vo;

import com.jpmsoft.userProducer.pojo.dto.SysTenantPayDTO;
import lombok.Data;

import java.util.Date;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/7 14:56
 */
@Data
public class SysTenantPayVO extends SysTenantPayDTO {

    private static final long serialVersionUID = 160805072407620141L;

    private Date teExpirationTime;

}
