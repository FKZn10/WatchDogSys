package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.SecInfo.SecInfoBudgetDeclareDtlChangeRequest;
import com.jpmsoft.common.model.response.SecInfo.SecInfoBudgetDeclareDtlChangeRes;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoBudgetDeclareDtlChange;

import java.util.List;

public interface ISecInfoBudgetDeclareDtlChangeService extends IService<SecInfoBudgetDeclareDtlChange> {


    /**
     *
      * @param secInfoBudgetDeclareDtlChangeRequestList   变更的概算数据
     * @param uId                                         操作人
     * @param action                                      新增、修改
     */
    void createOrUpdateSecInfoBudgetDeclareDtlChange(List<SecInfoBudgetDeclareDtlChangeRequest> secInfoBudgetDeclareDtlChangeRequestList,String uId, String action);


    List<SecInfoBudgetDeclareDtlChangeRes> getSecInfoBudgetDeclareDtlChangeList(List<String> sicIdList);


    /**
     * 通过标段变更表id删除变更的概算数据
     * @param sicIdList
     */
    void delSecInfoBudgetDeclareDtlChangeBySicId(List<String> sicIdList);

}
