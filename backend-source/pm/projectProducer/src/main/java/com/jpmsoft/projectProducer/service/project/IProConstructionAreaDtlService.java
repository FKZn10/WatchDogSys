package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.Project.ProConstructionAreaDtlModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProConstructionAreaDtl;

import java.util.List;

public interface IProConstructionAreaDtlService extends IService<ProConstructionAreaDtl> {

    /**
     * 批量处理项目建筑面积详情
     * 先全部移除
     * 没有ID则新增数据
     * 有ID则修改数据
     *
     * @param proConstructionAreaDtls
     * @param pId
     * @param projectMethod           是创建还是修改项目
     */
    void insertProConstructionAreaDtls(List<ProConstructionAreaDtlModel> proConstructionAreaDtls, String pId, String projectMethod);

    /**
     * 根据多个PID（项目ID）获取数据集
     *
     * @param pIds
     * @return
     */
    List<ProConstructionAreaDtlModel> getProConstructionAreaDtlByPids(List<String> pIds);


    void deleteByPid(String pId);
}
