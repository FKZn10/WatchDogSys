package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.project.ProConstructionSourcesFundsDto;
import com.jpmsoft.projectProducer.pojo.entity.project.ProConstructionSourcesFunds;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/7 15:44
 */
public interface IProConstructionSourcesFundsService extends IService<ProConstructionSourcesFunds> {

    void createOrUpdateProConstructionSourcesFundsBath(List<List<ProConstructionSourcesFundsDto>> datas, List<String> cplId);

    void delProConstructionSourcesFundsByCplId(String cplId);

    void delProConstructionSourcesFundsByPcpId(String pcpId);

    List<ProConstructionSourcesFundsDto> getProConstructionSourcesFundsByPcpId(String pcpId);

    /**
     * 年度计划申报管理ID查询
     *
     * @param ycpId
     * @return
     */
    List<ProConstructionSourcesFundsDto> getProConstructionSourcesFundsByYcpId(String ycpId);

    List<ProConstructionSourcesFundsDto> getProConstructionSourcesFundsByCplIds(List<String> cplIds);
}
