package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author jjz
 * &#064;date  2025/08/19
 * 河北项目形象进度
 */
@Data
@TableName("project_profile")
public class ProjectProfile {

    private String id;

    // 图片类型 01 施工效果图  02 形象进度图
    private String ppType;

    // 当前阶段
    private String ppCurrent;

    // 形象进度
    private String progress;

    // 标段id
    private String secId;

    // 日期
    private Date pDate;

    // 备注
    private String remark;

    // 项目id
    private String pId;

    // 文件状态
    private String fileStatus;

    private String createUid;

    private Date createTime;

    private String createUname;

    private Integer state;

    private Date stateTime;

    // 是否同步形象进度到驾驶舱 01 显示  02 隐藏
    private String imgShow;

}
