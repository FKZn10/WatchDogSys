package com.jpmsoft.userProducer.pojo.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
public class AnswerDTO {

    private String systemPrompt;
    private String userPromp;
}
