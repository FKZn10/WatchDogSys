package com.jpmsoft.projectProducer.pojo.dto.cockpit;
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown=true)
public class CameraRecordingRequest extends com.jpmsoft.common.model.requestAndResponse.SmartSite.SmartSiteCameraModel {
 private String startTime,endTime,recordSource;
 public String getStartTime(){return startTime;} public void setStartTime(String v){startTime=v;}
 public String getEndTime(){return endTime;} public void setEndTime(String v){endTime=v;}
 public String getRecordSource(){return recordSource;} public void setRecordSource(String v){recordSource=v;}
}
