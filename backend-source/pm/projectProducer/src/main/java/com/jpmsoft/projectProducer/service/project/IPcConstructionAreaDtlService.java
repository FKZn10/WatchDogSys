package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.Project.PcConstructionAreaDtlModel;
import com.jpmsoft.projectProducer.pojo.entity.project.PcConstructionAreaDtl;

import java.util.List;

public interface IPcConstructionAreaDtlService extends IService<PcConstructionAreaDtl> {

    /**
     * 批量处理项目建筑面积详情
     * 先全部移除
     * 没有ID则新增数据
     * 有ID则修改数据
     *
     * @param PcConstructionAreaDtls
     * @param pcId
     * @param projectMethod          是创建还是修改项目
     */
    void insertPcConstructionAreaDtls(List<PcConstructionAreaDtlModel> PcConstructionAreaDtls, String pcId, String projectMethod);

    /**
     * 根据多个PID（项目ID）获取数据集
     *
     * @param pcIds
     * @return
     */
    List<PcConstructionAreaDtlModel> getPcConstructionAreaDtlByPcIds(List<String> pcIds);


    void syncPcConstructionAreaDtl2Project(String pcId, String pId);
}
