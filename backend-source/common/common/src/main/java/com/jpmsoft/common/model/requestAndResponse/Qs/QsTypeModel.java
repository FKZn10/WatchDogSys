package com.jpmsoft.common.model.requestAndResponse.Qs;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 质量安全类别
 */
@Data
public class QsTypeModel extends BaseModeRequest {
    private String qstId;
    //类别名称
    private String qstName;
    //类别编码
    private String qstCode;
    //父级id
    private String qstParentId;
    //类别归类
    private String qstCategory;
    //影响功能和安全使用的重大质量问题  0：否  1：是
    private Integer qstIsImportant;
    /**
     * 严重程度：
     * 1： 空 或者 /
     * 2：严重     等同于重大问题，qstIsImportant=1
     * 3：较严重
     * 4：一般
     * 5：轻微
     */
    private Integer severityLevel;
    private String qstItem;
    private Integer qstSort;
    private String qstRemark;
    private Integer qstState;
    private Date qstCreateTime;
    private String qstCreateUid;
    private String qstCreateUname;
    private String qstPunish;
    private String qstScore;
    private String qstPath;//全路径
    private String qstPathName;
    private List<String> qstPathList;//全路径
    private List<String> qstPathNameList;
    private Integer level;//层级
    private String qstNumber;//序号
    //单位
    private String qstUnit;
    /**
     * 处置要求
     */
    private String qstDisposal;
    private List<QsTypeModel> childNodes;

    //选定组id；义乌需求：如果有选定组，当前修改的类别名称（文字部分）在这个选定类别的子级中也有相同名称（文字部分相同即可），那么同时修改这些类别的金额、数值。
    private String selectGroupQstId;

    //涉及到的条目id
    private List<String> groupQstIdList;

}
