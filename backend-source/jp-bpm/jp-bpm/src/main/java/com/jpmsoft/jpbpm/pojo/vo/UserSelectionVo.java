package com.jpmsoft.jpbpm.pojo.vo;

import java.util.List;

public class UserSelectionVo {

    public String nodeid;
    public String nodename;
    public String slectType;
    public List<Userinfo> userlist;
    public String smsNotificationStatus;
    public Integer actNum;
    public String multipleChoice;
    public String actSelectAll;
    public String actSkipStatus;

    public String getNodeid() {
        return nodeid;
    }

    public void setNodeid(String nodeid) {
        this.nodeid = nodeid;
    }

    public String getNodename() {
        return nodename;
    }

    public void setNodename(String nodename) {
        this.nodename = nodename;
    }

    public List<Userinfo> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<Userinfo> userlist) {
        this.userlist = userlist;
    }

    public String getSlectType() {
        return slectType;
    }

    public void setSlectType(String slectType) {
        this.slectType = slectType;
    }

    public String getSmsNotificationStatus() {
        return smsNotificationStatus;
    }

    public void setSmsNotificationStatus(String smsNotificationStatus) {
        this.smsNotificationStatus = smsNotificationStatus;
    }

    public Integer getActNum() {
        return actNum;
    }

    public void setActNum(Integer actNum) {
        this.actNum = actNum;
    }

    public String getMultipleChoice() {
        return multipleChoice;
    }

    public void setMultipleChoice(String multipleChoice) {
        this.multipleChoice = multipleChoice;
    }

    public String getActSelectAll() {
        return actSelectAll;
    }

    public void setActSelectAll(String actSelectAll) {
        this.actSelectAll = actSelectAll;
    }

    public String getActSkipStatus() {
        return actSkipStatus;
    }

    public void setActSkipStatus(String actSkipStatus) {
        this.actSkipStatus = actSkipStatus;
    }

    public class Userinfo {

        public String userid;
        public String username;
        public Integer userNum;

        public Userinfo(){

        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Integer getUserNum() {
            return userNum;
        }

        public void setUserNum(Integer userNum) {
            this.userNum = userNum;
        }

    }
}
