package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.SecInfo.SecInviteDetailDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInviteDetail;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/23 10:32
 */
public interface ISecInviteDetailService extends IService<SecInviteDetail> {

    /**
     * 根据标段ID删除后重建数据
     *
     * @param datas
     * @param wsId
     */
    void createOrUpdateSecInviteDetail(List<SecInviteDetailDTO> datas, String wsId);

    void createOrUpdateSecInviteDetailBath(List<List<SecInviteDetailDTO>> details, List<String> wsIds);

    /**
     * 根据工作方案ID删除
     *
     * @param wsId
     */
    void delSecInviteDetailByWsId(String wsId);

    /**
     * 根据工作方案ID批量删除
     *
     * @param wsIds
     */
    void delBathByWsIds(List<String> wsIds);

    List<SecInviteDetailDTO> getSecInviteDetailByWsIds(List<String> wsIds);

    void createOrUpdateSecInviteDetailSir(List<SecInviteDetailDTO> secInviteDetails, String sirId);

    List<SecInviteDetailDTO> getSecInviteDetailBySirIds(List<String> sirIds);
}
