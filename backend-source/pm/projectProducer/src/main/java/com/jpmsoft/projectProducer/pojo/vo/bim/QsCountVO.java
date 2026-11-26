package com.jpmsoft.projectProducer.pojo.vo.bim;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/1/11 10:37
 * @Description:
 * @Version: 1.0
 */
@Data
public class QsCountVO implements Serializable {
    private static final long serialVersionUID = 5982184063465342131L;

    /**
     * 已解决数量
     */
    Long resolvedNum;

    /**
     * 未解决数量
     */
    Long unresolvedNum;
}
