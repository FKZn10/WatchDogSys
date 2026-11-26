package com.jpmsoft.projectProducer.pojo.entity.acpm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 投标主体
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 15:57
 */
@Data
@TableName("Acpm_DJTBSubjectTender")
public class AcpmDJTBSubjectTender {

    @TableId(type = IdType.INPUT)
    private String dasId;
    private String daId;
    private String cId;
    private String dasSort;
}
