package com.jpmsoft.projectProducer.service.peInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeMeetingApprovalList;

import java.util.List;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/16 10:13
 */
public interface IPeMeetingApprovalListService extends IService<PeMeetingApprovalList> {

    void createOrUpdatePMAList(List<String> peIds, String maId);

    void deletePMAListByMaId(String maId);
}
