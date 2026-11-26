package com.jpmsoft.projectProducer.pojo.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CjGanttTaskProcessResponse {
    private Integer gtpUploadTimes;
    private Integer state;
    private String  gtpId;
}
