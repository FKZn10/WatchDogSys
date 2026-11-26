package com.jpmsoft.projectProducer.pojo.request.ganttplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OverdueRequest {
    private String taskId;
    private String gtoExplanation;
}
