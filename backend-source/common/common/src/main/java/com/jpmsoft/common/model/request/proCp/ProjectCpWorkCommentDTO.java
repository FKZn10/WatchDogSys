package com.jpmsoft.common.model.request.proCp;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.util.Date;

/**
 * 工作办理记录评论
 *
 * @Author: 李文涛 aaron
 * @date: 2024/1/9 11:57
 */
@Data
public class ProjectCpWorkCommentDTO extends BaseModeRequest {
    private String id;
    private String cpwrId;
    private String cpwcContent;
    private String createUid;
    private String createUname;
    private Date createTime;
}
