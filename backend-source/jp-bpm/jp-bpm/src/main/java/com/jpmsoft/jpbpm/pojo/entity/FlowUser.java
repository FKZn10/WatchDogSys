package com.jpmsoft.jpbpm.pojo.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("SYS_FlowUser")
public class FlowUser {
    @TableId(type = IdType.INPUT)
    public  String  flowuserid;
    public  String  flowuserflow;
    public  String  flowuseract;
    public  String  flowuserrole;
    private String teId;
}
