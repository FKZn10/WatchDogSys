package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.project.ProFundsUsageDetailsDto;
import com.jpmsoft.projectProducer.pojo.entity.project.ProFundsUsageDetails;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/7 15:45
 */
public interface IProFundsUsageDetailsService extends IService<ProFundsUsageDetails> {

    void createOrUpdateProFundsUsageDetailsBath(List<List<ProFundsUsageDetailsDto>> datas, List<String> cplId);

    void delProFundsUsageDetailsByCplId(String cplId);

    void delProFundsUsageDetailsByPcpId(String pcpId);

    List<ProFundsUsageDetailsDto> getProFundsUsageDetailsByPcpId(String pcpId);

    /**
     * 年度计划申报管理ID查询
     *
     * @param ycpId
     * @return
     */
    List<ProFundsUsageDetailsDto> getProFundsUsageDetailsByYcpId(String ycpId);

    List<ProFundsUsageDetailsDto> getProFundsUsageDetailsByCplIds(List<String> cplIds);
}
