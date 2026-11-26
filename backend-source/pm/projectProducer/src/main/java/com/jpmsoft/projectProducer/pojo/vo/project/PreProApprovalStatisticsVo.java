package com.jpmsoft.projectProducer.pojo.vo.project;

import com.jpmsoft.common.model.requestAndResponse.Project.PreProApprovalModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/18 16:07
 */
@Data
public class PreProApprovalStatisticsVo implements Serializable {


    private static final long serialVersionUID = -2371795159055101820L;
    private BigDecimal paTotalInvSum = BigDecimal.ZERO;
    private List<PreProApprovalModel> data;
}
