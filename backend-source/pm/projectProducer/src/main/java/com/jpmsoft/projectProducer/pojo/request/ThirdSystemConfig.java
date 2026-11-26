package com.jpmsoft.projectProducer.pojo.request;

import lombok.Data;

@Data
public class ThirdSystemConfig {
   private String IPHost;
   private String AppID;
   private String AppKey;
   private String CallBackURL;
   private String ApplyTypeId;

}
