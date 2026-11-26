package com.jpmsoft.projectProducer.pojo.vo.blxw;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
@Data
public class BlxwInfoPrintVo extends BlxwInfoVo{
    @JsonFormat(pattern = "yyyy年MM月dd日",timezone = "GMT+8")
    private Date blxwTalkTime;
    private String blxwLevelName;
    private String blxwHandleTypeName;
    @JsonFormat(pattern = "yyyy年MM月dd日",timezone = "GMT+8")
    private Date blxwRectifyTime;
    @JsonFormat(pattern = "yyyy年MM月dd日",timezone = "GMT+8")
    private Date createTime;
}
