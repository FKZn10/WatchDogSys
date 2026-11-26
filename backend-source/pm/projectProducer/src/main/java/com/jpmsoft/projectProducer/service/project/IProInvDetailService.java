package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProSourcesFundsModel;
import com.jpmsoft.projectProducer.pojo.dto.project.ProMonthlyReportDto;
import com.jpmsoft.projectProducer.pojo.entity.project.ProInvDetail;

import java.math.BigDecimal;
import java.util.List;

public interface IProInvDetailService extends IService<ProInvDetail> {

    /**
     * 批量处理项目资金来源
     * 先全部移除
     * 没有ID则新增数据
     * 有ID则修改数据
     *
     * @param proSourcesFundsModels
     * @param pId
     * @param projectMethod         是创建还是修改项目
     */
    void insertProSourcesFunds(List<ProSourcesFundsModel> proSourcesFundsModels, String pId, String projectMethod);

    /**
     * 根据多个PID（项目ID）获取数据集
     *
     * @param pIds 项目ID
     * @return
     */
    List<ProSourcesFundsModel> getProSourcesFundsByPids(List<String> pIds);


    /**
     * 复制项目注册资金来源数据
     *
     * @param prId
     */
    void copyDataByFundsReg(String prId, String pId);

    /**
     * 统计项目资金来源
     *
     * @param pIds
     * @return
     */
    BaseResponse getSourcesFundsSumByPids(List<String> pIds);

    List<ProInvDetail> getProInvDetailPojoByPids(List<String> pIds);

    List<ProInvDetail> getProInvDetailPojoByPid(String pId);

    void deleteByPid(String pId);

    BigDecimal getPInvTotal(ProMonthlyReportDto request);
}
