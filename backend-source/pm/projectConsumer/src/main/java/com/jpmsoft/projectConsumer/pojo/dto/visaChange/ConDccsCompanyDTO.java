package com.jpmsoft.projectConsumer.pojo.dto.visaChange;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/6 17:51
 */
@Data
public class ConDccsCompanyDTO implements Serializable {
    private String id;
    private String dccsId;
    @JsonProperty("cId")
    private String cId;
}
