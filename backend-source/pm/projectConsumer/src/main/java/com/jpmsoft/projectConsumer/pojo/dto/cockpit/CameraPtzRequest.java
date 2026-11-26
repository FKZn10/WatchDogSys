package com.jpmsoft.projectConsumer.pojo.dto.cockpit;
import com.jpmsoft.common.model.requestAndResponse.SmartSite.SmartSiteCameraModel;
/** Module-local transport model; identical wire fields in Producer and Consumer. */
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class CameraPtzRequest extends SmartSiteCameraModel {
    private String action, command, leaseId;
    private Integer speed;
    public String getAction(){return action;} public void setAction(String value){action=value;}
    public String getCommand(){return command;} public void setCommand(String value){command=value;}
    public String getLeaseId(){return leaseId;} public void setLeaseId(String value){leaseId=value;}
    public Integer getSpeed(){return speed;} public void setSpeed(Integer value){speed=value;}
}
