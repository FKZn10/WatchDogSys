package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.SecInfo.SecReviewMaterialRequest;
import com.jpmsoft.common.model.response.SecInfo.SecReviewMaterialResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecReviewMaterial;

import java.util.List;

public interface ISecReviewMaterialService extends IService<SecReviewMaterial> {


    /**
     *
     * @param reviewMaterialList   资料清单
     * @param createOrUpdate       新增或修改
     * @param sourceType           来源类型
     * @param businessId           业务表id
     */
    void createOrUpdateReviewMaterial(List<SecReviewMaterialRequest> reviewMaterialList, String createOrUpdate,String sourceType, String businessId);

    /**
     * 通过业务id删除资料清单
     * @param sourceType
     * @param businessId
     */
    void deleteByBusinessId(String sourceType, String businessId);

    /**
     * 通过业务id获取资料清单
     * @param sourceType
     * @param businessId
     * @return
     */
    List<SecReviewMaterialResponse> getReviewMaterialList(String sourceType, String businessId);

}
