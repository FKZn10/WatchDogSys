package com.jpmsoft.common.model.request.Contract;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class SKThirdSystemConfig {
   private String IPHost;
   private String fileUrl;
   private String TenantId;
   private long Timestamp;
   private String SystemNo;
   private String SystemName;
   private String RequestNo;
   private String MesgNo;
   private String AppUser;
   private String AppPwd;
   public String PayAccountNo;
}
