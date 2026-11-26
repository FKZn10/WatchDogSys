package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.SecInfo.SecInviteRosterDto;
import com.jpmsoft.common.model.response.SecInfo.SecInviteRosterVo;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInviteRoster;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/23 10:32
 */
public interface ISecInviteRosterService extends IService<SecInviteRoster> {

    /**
     * 根据标段ID删除后重建数据
     *
     * @param datas
     * @param dId
     */
    void createOrUpdateSecInviteRoster(List<SecInviteRosterDto> datas, String dId);

    void createOrUpdateSecInviteRosterBath(List<List<SecInviteRosterDto>> roters, List<String> dIds);

    /**
     * 标段ID删除
     *
     * @param dId
     */
    void delSecInviteRosterBydId(String dId);

    /**
     * 根据标段ID批量删除
     *
     * @param dIds
     */
    void delBathBydIds(List<String> dIds);

    List<SecInviteRosterVo> getSecInviteRosterBydIds(List<String> dIds);
}
