package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 抽取专家（建管）
 *
 */
@Data
public class ExtractJGExpertModel implements Serializable {

    private static final long serialVersionUID = -3171669600101179527L;

    /**
     * 需要排除的单位
     * 将无法抽取到该单位下的专家
     * 需基础配置secExpertLogicalConf内启用 否则传入无效
     */
    private String excludeCpy;
    /**
     * 需要屏蔽的专家
     */
    private List<SecExpertShieldDto> secExpertShields;

    //内部专家
    private JGExpertModel insideExpert;

    //外部专家
    private JGExpertModel outsideExpert;

    /**
     * 回避企业id
     */
    private String avoidCompanyId;

    private String avoidCompanyName;

    //开标时间
    private Date srBidTime;

    //标段id
    private String secId;

    //招标方式    JPMsoft_ZBFS  01、公开招标 02、邀请招标
    private String secBiddingType;


}
