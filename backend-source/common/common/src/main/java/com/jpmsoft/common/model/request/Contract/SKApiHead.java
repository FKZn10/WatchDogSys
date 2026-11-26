package com.jpmsoft.common.model.request.Contract;

import lombok.Data;

@Data
public class SKApiHead {
    public String TenantId;
    public String Timestamp;
    public String SystemNo;
    public String SystemName;
    public String RequestNo;
    public String MesgNo;
    public String AppUser;
    public String AppPwd;
}
