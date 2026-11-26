package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProApprovalDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectRegisterDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.ProSourcesFundsRegModel;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectRegisterDtl;
import com.jpmsoft.projectProducer.pojo.entity.project.ProSourcesFundsReg;

import java.util.List;

public interface IPreProjectRegisterDtlService extends IService<PreProjectRegisterDtl> {

    void createOrUpdatePreProjectRegisterDtl(List<PreProjectRegisterDtlModel> datas, String prId);

    void deletePreProjectRegisterDtlByPrId(String prId);

    List<PreProjectRegisterDtlModel> getPreProjectRegisterDtlByPrId(String prId);

}
