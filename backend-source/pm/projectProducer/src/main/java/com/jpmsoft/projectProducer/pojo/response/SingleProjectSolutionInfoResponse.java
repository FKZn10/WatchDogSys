package com.jpmsoft.projectProducer.pojo.response;

import com.jpmsoft.projectProducer.pojo.vo.project.SingleProjectSolutionInfoVo;
import lombok.Data;

import java.util.List;

@Data
public class SingleProjectSolutionInfoResponse {
    private String projectName;
    private List<SingleProjectSolutionInfoVo> list;
}
