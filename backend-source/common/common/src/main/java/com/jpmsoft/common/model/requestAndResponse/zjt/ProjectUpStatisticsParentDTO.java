package com.jpmsoft.common.model.requestAndResponse.zjt;

import lombok.Data;

import java.util.List;

@Data
public class ProjectUpStatisticsParentDTO {

    //总填报分数
    private int fillingTotal;
    //监管单位数据
    private int jgTotal;
    //实施单位数量
    private int ssTotal;
    //查看指定月份填报项目数量
    private int projectTotal;
    //查看指定月份填报单位数量
    private int cpyTotal;
    private List<ProjectUpStatisticsDTO> data;
}
