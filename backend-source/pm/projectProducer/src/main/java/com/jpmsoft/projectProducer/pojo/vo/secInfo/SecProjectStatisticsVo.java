package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecProjectModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/8 15:24
 */
@Data
public class SecProjectStatisticsVo implements Serializable {

    private static final long serialVersionUID = -3128605398536987036L;
    private BigDecimal secMoneyTotalSum = BigDecimal.ZERO;
    private List<SecProjectModel> data;
}
