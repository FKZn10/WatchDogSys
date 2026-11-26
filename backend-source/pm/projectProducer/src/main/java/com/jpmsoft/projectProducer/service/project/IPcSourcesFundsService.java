package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.Project.PcSourcesFundsModel;
import com.jpmsoft.projectProducer.pojo.entity.project.PcSourcesFunds;

import java.util.List;

public interface IPcSourcesFundsService extends IService<PcSourcesFunds> {

    /**
     * 批量处理项目资金来源
     * 先全部移除
     * 没有ID则新增数据
     * 有ID则修改数据
     *
     * @param pcSourcesFundsModels
     * @param pcId
     * @param projectMethod        是创建还是修改项目
     */
    void insertPcSourcesFunds(List<PcSourcesFundsModel> pcSourcesFundsModels, String pId
            , String pcId, String uId, String uName, String projectMethod, Boolean tempSave);

    /**
     * 根据多个PID（项目ID）获取数据集
     *
     * @param pcIds 项目ID
     * @return
     */
    List<PcSourcesFundsModel> getPcSourcesFundsByPcIds(List<String> pcIds);


    void syncPcSourcesFunds2Project(String pcId, String pId, String uId, String uName);
}
