package com.jpmsoft.projectProducer.pojo.entity.other;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("SYS_Position_User_P")
public class SysPositionUserP {

    @TableId(type = IdType.INPUT)
    private String pupId;
    private String ppId;
    private String uId;
    private Integer pupSort;

    public String getPupId() {
        return pupId;
    }

    public void setPupId(String pupId) {
        this.pupId = pupId;
    }

    public String getPpId() {
        return ppId;
    }

    public void setPpId(String ppId) {
        this.ppId = ppId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Integer getPupSort() {
        return pupSort;
    }

    public void setPupSort(Integer pupSort) {
        this.pupSort = pupSort;
    }
}
