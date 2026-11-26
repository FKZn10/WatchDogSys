package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProApprovalDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectRegisterDtlModel;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProApprovalDtl;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectRegisterDtl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IPreProApprovalDtlService extends IService<PreProApprovalDtl> {

    void createOrUpdatePreProApprovalDtl(List<PreProApprovalDtlModel> datas, String paId);

    void deletePreProApprovalDtlByPrId(String paId);

    List<PreProApprovalDtlModel> getPreProApprovalDtlByPrId(String paId);

    List<PreProApprovalDtlModel> getPreProApprovalDtlByPId(String pId);

    BigDecimal getPreProApprovalDtlAmountByPid(String pId);


    Map<String, List<PreProApprovalDtlModel>> getPreProApprovalDtlModelByBdIds(List<String> pIds, List<String> padFids);

}
