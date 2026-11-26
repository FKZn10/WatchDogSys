package com.jpmsoft.projectProducer.pojo.vo.contract;

import com.jpmsoft.projectProducer.pojo.dto.contract.ConUnitProjectSummaryDto;
import lombok.Data;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/25 14:11
 */
@Data
public class ConUnitProjectSummaryVo extends ConUnitProjectSummaryDto {

    private static final long serialVersionUID = -4186390149828233316L;
    private String boqdName;
    private String boqdCode;

}
