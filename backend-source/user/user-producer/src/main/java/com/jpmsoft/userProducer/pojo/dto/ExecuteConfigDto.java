package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.userProducer.constants.BaseMsg;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/3 8:58
 */
@Data
public class ExecuteConfigDto implements Serializable {

    private static final long serialVersionUID = 38532199587260431L;

    @NotBlank(message = BaseMsg.reqCodeIsEmpty)
    private String code;
    private Map<String, Object> params;

}
