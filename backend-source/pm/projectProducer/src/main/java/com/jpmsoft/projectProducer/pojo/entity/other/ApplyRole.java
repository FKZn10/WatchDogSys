package com.jpmsoft.projectProducer.pojo.entity.other;


import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@TableName("apply_role")
public class ApplyRole {

    private String id;
    private String userid;
    @JsonProperty("pId")
    private String pId;
    private String name;
    private String phone;
    private String email;
    @JsonProperty("cId")
    private String cId;

    private String state;

}
