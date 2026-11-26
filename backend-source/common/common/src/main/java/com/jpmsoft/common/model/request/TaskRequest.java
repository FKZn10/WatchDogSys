package com.jpmsoft.common.model.request;

import com.jpmsoft.common.model.BaseModeRequest;

public class TaskRequest extends BaseModeRequest {

    //用户id
    public String userid;

    public String statusstr;

    public String getStatusstr() {
        return statusstr;
    }

    public void setStatusstr(String statusstr) {
        this.statusstr = statusstr;
    }

    public String getUserid() {
        return userid;
    }


    public void setUserid(String userid) {
        this.userid = userid;
    }


}
