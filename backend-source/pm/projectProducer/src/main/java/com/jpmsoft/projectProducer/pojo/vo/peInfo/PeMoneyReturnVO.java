package com.jpmsoft.projectProducer.pojo.vo.peInfo;

import com.jpmsoft.projectProducer.pojo.dto.peinfo.PeMoneyReturnDTO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/26 14:19
 */
@Data
public class PeMoneyReturnVO implements Serializable {

    private static final long serialVersionUID = -1849268720695824005L;

    private BigDecimal mrConAmtSum = BigDecimal.ZERO;
    private BigDecimal mrAmtSum = BigDecimal.ZERO;
    private List<PeMoneyReturnDTO> data;
}
