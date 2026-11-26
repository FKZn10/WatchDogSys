package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ContractAuthorityBathDTO;
import com.jpmsoft.common.model.request.ContractAuthorityDTO;
import com.jpmsoft.common.model.request.SysContractAuthorityRequest;
import com.jpmsoft.projectProducer.pojo.entity.contract.SysContractAuthority;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ISysContractAuthorityService extends IService<SysContractAuthority> {

    /**
     * 根据单位获取其带权限的合同ID集合
     *
     * @param cId
     * @return
     */
    List<String> getConIdByCidFromContractAuthority(String cId);

    /**
     * 合同权限以人为主 单位授权为辅助
     * 人禁用或者授权 则直接忽略单位授权
     * 人未授权 则以单位授权为主
     *
     * @param cId
     * @param uid
     * @return
     */
    ContractAuthorityDTO getConIdByCidFromContractAuthority(String cId, String uid);

    BaseResponse createContractAuthority(SysContractAuthorityRequest request);

    /**
     * 批量创建或者批量删除
     *
     * @param request
     * @return
     */
    BaseResponse createOrDelAuthBath(ContractAuthorityBathDTO request, LogUserInfoVo user);

    BaseResponse deleteContractAuthBath(List<String> ids);

    BaseResponse deleteContractAuthorityByCaId(String caId);

    BaseResponse getContractAuthorityList(SysContractAuthorityRequest request, String uId);
}
