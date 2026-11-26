package com.jpmsoft.projectConsumer.pojo.dto.secInfo;

import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordModel;
import lombok.Data;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/31 14:35
 */
@Data
public class SecExpertSelectAndSelectionDTO {

    /**
     * 选取
     */
    private SecExpertSelectDto expertSelect;
    /**
     * 抽取
     */
    private SecSelectionRecordModel selectionRecord;
}
