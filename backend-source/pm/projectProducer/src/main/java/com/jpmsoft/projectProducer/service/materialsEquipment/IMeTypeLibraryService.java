package com.jpmsoft.projectProducer.service.materialsEquipment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.MeEnter.MeTypeLibraryRequest;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeTypeLibrary;

public interface IMeTypeLibraryService extends IService<MeTypeLibrary> {

    BaseResponse createOrUpdateMeTypeLibrary(MeTypeLibraryRequest request);

    BaseResponse getMeTypeLibraryList(MeTypeLibraryRequest request);

    BaseResponse upOrDownMeTypeLibrarySort(String metId, String action,String teId);

    BaseResponse delMeTypeLibraryById(String id);

    BaseResponse getDownPullListWithTree();

}
