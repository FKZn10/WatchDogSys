package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.SecInfo.SecQeGroupDtlRequest;
import com.jpmsoft.common.model.response.SecInfo.SecQeGroupDtlResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecQeGroupDtl;

import java.util.List;

public interface ISecQeGroupDtlService extends IService<SecQeGroupDtl> {

    /**
     * 资格审查小组ID查看是否有详情数据
     *
     * @param qegId
     * @return
     */
    Boolean havingQeGroupDtlByQegId(String qegId);


    void createOrUpdateQeGroupDtl(List<SecQeGroupDtlRequest> models, String qegId);


    List<SecQeGroupDtlResponse> getSecQeGroupDtlByQegId(String qegId);

    List<SecQeGroupDtlResponse> getSecQeGroupDtlBySecId(String secId);
}
