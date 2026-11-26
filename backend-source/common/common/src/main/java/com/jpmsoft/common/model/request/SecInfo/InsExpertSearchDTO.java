package com.jpmsoft.common.model.request.SecInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class InsExpertSearchDTO extends BaseModeRequest {

    @ClassInfo(title = "姓名", type = "string", value = "exName", dbValue = "ex_Name", no = 1)
    private List<String> exName;
    //@ClassInfo(title = "身份证号", type = "string", value = "exIdNumber", dbValue = "projectName", no = 2)
    //private List<String> exIdNumber;
    @JsonProperty("cName")
    @ClassInfo(title = "工作单位", type = "string", value = "cName", dbValue = "c_Name", no = 3)
    private List<String> cName;
    @ClassInfo(title = "所在部门", type = "string", value = "exDName", dbValue = "ex_D_Name", no = 4)
    private List<String> exDName;
    @ClassInfo(title = "建设工程专业技术人员职称", type = "dictionary", value = "exTitle", dictionary = "JPMsoft_JSGCZYJSRYZC", dbValue = "ex_Title", no = 5)
    private List<String> exTitle;
    @ClassInfo(title = "工程注册类证书", type = "dictionary", value = "exPracticeCode", dictionary = "JPMsoft_GCZCLZS", dbValue = "ex_Practice_Code", no = 6)
    private List<String> exPracticeCode;
    @ClassInfo(title = "通讯方式", type = "string", value = "exCellphoneNumber", dbValue = "ex_Cellphone_Number", no = 7)
    private List<String> exCellphoneNumber;
    @ClassInfo(title = "入库状态", type = "bool", value = "inLibrary", dbValue = "in_Library", no = 8)
    private List<String> inLibrary;
    @ClassInfo(title = "入库时间", type = "date", value = "exFinishDate", dbValue = "ex_Finish_Date", no = 9)
    private String exFinishDate;

    @JsonProperty("exState")
    private Integer exState;

    private String exApplicantId;

    /**
     * 是否专家库管理员查询
     * 查询审批通过的或者申请人为自己的
     */
    private Boolean expertAdmin=false;
    /**
     * 01 集团专家
     * 02 小城投专家
     * 缺省01
     */
    private String exCategory = "01"; // 分类
}
