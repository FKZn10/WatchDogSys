package com.jpmsoft.common.model.request.SecInfo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (定标)中标单位
 *
 * @author: 李文涛 aaron
 * @date: 2022/7/25 10:57
 */
@Data
public class SecDeterminCpyDto implements Serializable {


    private static final long serialVersionUID = 4178060354837412664L;
    private String id;
    private String drId;
    private String sdcUnit;
    private Date createTime;
}
