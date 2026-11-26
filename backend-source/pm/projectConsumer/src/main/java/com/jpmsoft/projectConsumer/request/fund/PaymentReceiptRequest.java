package com.jpmsoft.projectConsumer.request.fund;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.util.List;

@Data
public class PaymentReceiptRequest extends BaseFileRequest {
	private String id;
    private String projectId;
	/** 线下开具状态
	 * 申请中 0
	 * 未开具 1
	 * 已开具 2
	 * 已作废 3
	 * 已回收 4
	 * 已停止 5
	 * */
	private Integer prIssuedState;
	/** 核销状态
	 * 申请中 0
	 * 未核销 1
	 * 核销成功 2
	 * 核销退回 3
	 * 已停止 4
	 * 核销退回 5
	 * */
	private Integer prVerifyState;
	/** 退回原因 */
	private String prRollbackReason;

    @JsonProperty("pName")
    private String pName;

    private Integer state;

    private String prIsClaim;

    /**
     * cifdId
     */
    private String cifId;

	private List<String> prVerifyStates;

	private String hasUnwrittenAmount;
}
