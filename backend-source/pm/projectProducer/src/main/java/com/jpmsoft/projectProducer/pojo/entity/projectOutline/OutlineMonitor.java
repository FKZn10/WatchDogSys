package com.jpmsoft.projectProducer.pojo.entity.projectOutline;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("Outline_Monitor")
public class OutlineMonitor {
    private String id;
    private String token;
    private String dataId;
    private String formCode;
    private String tCode;
    private String tId;
}
