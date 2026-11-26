package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("Pre_Project_Register_Info_Score")
public class PreProjectRegisterInfoScore {
    private String id;
    private String pId;
    private String zsrId;
    private String fileId;
    private BigDecimal score;
    private String fileName;
    private String percentage;

}
