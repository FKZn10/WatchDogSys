package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecJGExpertAuditDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecJGExpertAudit;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

public interface ISecJGExpertAuditService extends IService<SecJGExpertAudit> {

    /**
     * 创建修改评标专家申请
     * @param request
     * @param user
     * @return
     */
    BaseResponse createOrUpdateSecJGExpertAudit(SecJGExpertAuditDTO request, LogUserInfoVo user);

    /**
     * 创建修改评标专家申请（创建时自动生成名称）
     * @param request
     * @param user
     * @return
     */
    BaseResponse createOrUpdateSecJGExpertAuditDz(SecJGExpertAuditDTO request, LogUserInfoVo user);

    /**
     * 分页查询评标专家申请
     * @param request
     * @return
     */
    BaseResponse getSecJGExpertAuditList(SecJGExpertAuditDTO request);

    /**
     * ID查询评标专家申请
     * @param id
     * @return
     */
    BaseResponse getSecJGExpertAuditById(String id);

    /**
     * 审批通过更新专家状态
     * @param id
     * @return
     */
    BaseResponse approveSyncExpertState(String id);

}
