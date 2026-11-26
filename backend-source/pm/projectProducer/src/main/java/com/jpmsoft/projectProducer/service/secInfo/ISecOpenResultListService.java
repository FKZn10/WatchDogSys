package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecOpenResultListModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecOpenResultList;

import java.util.List;

public interface ISecOpenResultListService extends IService<SecOpenResultList> {

    void createOrUpdateSecOpenResultList(List<SecOpenResultListModel> datas, String orId);

}
