package com.jpmsoft.common.model.requestAndResponse.Blxw;

import com.jpmsoft.common.model.requestAndResponse.NoticeInfoModel;
import lombok.Data;

@Data
public class BlxwNoticeInfoModel extends NoticeInfoModel {
    //班子会议题附件
    private String supportingAnnex;
    //证明文件ids
    private String supportingAnnexFileIds;
}
