package com.jpmsoft.common.model.request.SecInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class JudgeExtractionSearchDTO extends BaseModeRequest {

    @JsonProperty("pName")
    @ClassInfo(title = "项目名称", type = "string", value = "pName", dbValue = "p_Name", no = 1)
    private List<String> pName;
    @ClassInfo(title = "标段名称", type = "string", value = "secName", dbValue = "sec_Name", no = 2)
    private List<String> secName;
    @ClassInfo(title = "申请单位", type = "string", value = "cName", dbValue = "c_Name", no = 3)
    @JsonProperty("cName")
    private List<String> cName;
    @ClassInfo(title = "集团本部评委人数", type = "string", value = "insideCount", dbValue = "inside_Count", no = 4)
    private List<Integer> insideCount;
    @ClassInfo(title = "外部评委人数", type = "string", value = "outCount", dbValue = "out_Count", no = 5)
    private List<Integer> outCount;
    @ClassInfo(title = "创建人", type = "string", value = "createUname", dbValue = "create_Uname", no = 6)
    private List<String> createUname;
    @ClassInfo(title = "创建时间", type = "date", value = "createTime", dbValue = "create_Time", no = 7)
    private List<String> createTime;
    @ClassInfo(title = "审批状态", type = "string", value = "state", dbValue = "state", no = 8)
    private List<Integer> state;

    private List<String> participationOrFinished;

    
    /**
     * 回避单位
     *
     */
    private String avoidUnit;
    /**
     * 内部抽取数量
     */
    private Integer insideNum=0;
    /**
     * 外部抽取数量
     */
    private Integer outNum=0;
    /**
     * 回避专家用户ID集合
     */
    private List<String> avoidUser;
    /**
     * 区分是集团还是小城投
     * 集团 01
     * 小城投 02
     */
    private String jeCategory = "01"; // 分类
}
