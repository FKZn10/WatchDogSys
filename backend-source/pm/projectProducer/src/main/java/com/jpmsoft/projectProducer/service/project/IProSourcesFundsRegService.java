package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.Project.ProSourcesFundsRegModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProSourcesFundsReg;

import java.util.List;

public interface IProSourcesFundsRegService extends IService<ProSourcesFundsReg> {

    void createOrUpdateProSourcesFundsReg(List<ProSourcesFundsRegModel> datas, String prId);

    void deleteProSourcesFundsRegByPrId(String prId);

    List<ProSourcesFundsReg> getProSourcesFundsRegByPrId(String prId);
}
