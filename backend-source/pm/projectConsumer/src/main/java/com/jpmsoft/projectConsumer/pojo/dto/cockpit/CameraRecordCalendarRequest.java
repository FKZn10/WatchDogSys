package com.jpmsoft.projectConsumer.pojo.dto.cockpit;
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown=true)
public class CameraRecordCalendarRequest extends CameraRecordingRequest {
 private String startDate,endDate,nextStartTime;
 public String getStartDate(){return startDate;} public void setStartDate(String v){startDate=v;}
 public String getEndDate(){return endDate;} public void setEndDate(String v){endDate=v;}
 public String getNextStartTime(){return nextStartTime;} public void setNextStartTime(String v){nextStartTime=v;}
}
