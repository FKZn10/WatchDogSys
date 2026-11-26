package com.jpmsoft.projectProducer.service.materialsEquipment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.materialsEquipment.MeBrandLibraryDto;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeBrandLibrary;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/23 9:53
 */
public interface IMeBrandLibraryService extends IService<MeBrandLibrary> {

    BaseResponse createOrUpdateMeBrandLibrary(MeBrandLibraryDto request);

    void createOrUpdateMeBrandLibrary(List<MeBrandLibraryDto> datas, String blaId,String teId);

    /**
     * 同步材料设备品牌库建立（更新）审批表的流程状态
     *
     * @param blaId
     * @return
     */
    BaseResponse syncMeBrandLibraryApplyState(String blaId);

    List<MeBrandLibrary> getMeBrandLibraryByBlaId(String blaId);

    BaseResponse getMeBrandLibraryList(MeBrandLibraryDto request, String uId);

    BaseResponse getMeBrandLibraryById(String id);

    BaseResponse getMeBrandLibraryByMetId(String metId);
}
