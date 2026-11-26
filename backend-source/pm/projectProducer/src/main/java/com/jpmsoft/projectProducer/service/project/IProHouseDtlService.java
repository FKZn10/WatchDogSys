package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.Project.ProHouseDtlModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProHouseDtl;

import java.util.List;

public interface IProHouseDtlService extends IService<ProHouseDtl> {

    /**
     * 批量处理规划房屋详细
     * 先全部移除
     * 没有ID则新增数据
     * 有ID则修改数据
     *
     * @param proHouseDtlModels
     * @param pId
     * @param projectMethod     是创建还是修改项目
     */
    void insertProHouseDtls(List<ProHouseDtlModel> proHouseDtlModels, String pId, String projectMethod);

    /**
     * 根据多个PID（项目ID）获取数据集
     *
     * @param pIds 项目ID
     * @return
     */
    List<ProHouseDtlModel> getProHouseDtlsByPids(List<String> pIds);
}
