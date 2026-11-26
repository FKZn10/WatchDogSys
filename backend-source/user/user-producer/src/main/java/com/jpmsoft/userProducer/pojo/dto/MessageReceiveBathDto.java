package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.userProducer.constants.BaseMsg;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/11 15:00
 */
@Data
public class MessageReceiveBathDto implements Serializable {

    private static final long serialVersionUID = 8420443570304477919L;
    @NotEmpty(message = BaseMsg.smReceiveIdNoNull)
    private List<String> receiveUserIds;
    @NotBlank(message = BaseMsg.smIdNoNull)
    private String smId;
}
