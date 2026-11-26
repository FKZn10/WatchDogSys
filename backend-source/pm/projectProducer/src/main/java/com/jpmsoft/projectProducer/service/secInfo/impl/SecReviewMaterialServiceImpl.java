package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.SecInfo.SecReviewMaterialRequest;
import com.jpmsoft.common.model.response.SecInfo.SecReviewMaterialResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.secInfo.SecReviewMaterialMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecReviewMaterial;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.secInfo.ISecReviewMaterialService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 资料请审单
 *
 */
@Service
public class SecReviewMaterialServiceImpl extends ServiceImpl<SecReviewMaterialMapper, SecReviewMaterial> implements ISecReviewMaterialService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    public void createOrUpdateReviewMaterial(List<SecReviewMaterialRequest> reviewMaterialList, String createOrUpdate, String sourceType, String businessId) {
        deleteByBusinessId(sourceType, businessId);
        if(CollectionUtil.isNotEmpty(reviewMaterialList)){
            List<SecReviewMaterial> list=new ArrayList<>();
            List<Map<String, String>> data = new ArrayList<>();
            List<String> relationIdList=new ArrayList<>();
            for (SecReviewMaterialRequest request : reviewMaterialList) {
                relationIdList.add(request.getRmOriginalRelationId());
                relationIdList.add(request.getRmCopyRelationId());
                relationIdList.add(request.getRmElectronicRelationId());
                Map<String, String> params = new HashMap<>();
                params.put("fileRelationId", request.getRmOriginalRelationId());
                params.put("fileIds", request.getRmOriginalFileIds());
                data.add(params);
                params = new HashMap<>();
                params.put("fileRelationId", request.getRmCopyRelationId());
                params.put("fileIds", request.getRmCopyFileIds());
                data.add(params);
                params = new HashMap<>();
                params.put("fileRelationId", request.getRmElectronicRelationId());
                params.put("fileIds", request.getRmElectronicFileIds());
                data.add(params);
                SecReviewMaterial entity = new SecReviewMaterial();
                BeanUtil.copyProperties(request,entity);
                entity.setRmId(GenerateUUID.getUUID(entity.getRmId()));
                entity.setSourceType(sourceType);
                entity.setBusinessId(businessId);
                list.add(entity);
            }

            if("create".equals(createOrUpdate)){
                sysFileAnnexService.addFormRefreshRelationFile(relationIdList, businessId);
            }else {
                sysFileAnnexService.refreshRelationFile(data, businessId);
            }

            if(CollectionUtil.isNotEmpty(list)){
                saveOrUpdateBatch(list);
            }
        }

    }

    @Override
    public void deleteByBusinessId(String sourceType, String businessId) {
        if(StrUtil.isNotBlank(businessId) && StrUtil.isNotBlank(sourceType)){
            baseMapper.deleteByBusinessId(sourceType, businessId);
        }
    }

    @Override
    public List<SecReviewMaterialResponse> getReviewMaterialList(String sourceType, String businessId) {
        List<SecReviewMaterialResponse> list = baseMapper.getReviewMaterialList(sourceType, businessId);
        return list;
    }


}
