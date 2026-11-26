package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
public class SysPortalIndexConfigDto extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = 883054484778726100L;

    private String id;

    private String createUid;

    private String createUname;

    private Date createTime;

    private String state;

    private Date stateTime;

    private String portalName;

    private String portalCode;

    private String portalState;

    private String portalDesignId;

    private String pdName;

    private List<SysPortalIndexComponentDto>  components;

    private String systemIdentifier;
}
