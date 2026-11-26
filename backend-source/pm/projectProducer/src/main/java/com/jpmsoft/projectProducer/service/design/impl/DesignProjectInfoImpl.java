package com.jpmsoft.projectProducer.service.design.impl;



import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignConceptModel;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignProjectInfoModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.design.DesignProjectInfoMapper;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignProjectInfo;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoBudget;
import com.jpmsoft.projectProducer.service.design.IDesignProjectInfoService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DesignProjectInfoImpl  extends FileImpl<DesignProjectInfoMapper, DesignProjectInfo, DesignProjectInfoModel> implements IDesignProjectInfoService{


    @Override
    public void createOrUpdateDesignProjectInfo(DesignProjectInfoModel designProjectInfoModel) {
        toHistoryData(designProjectInfoModel);

        DesignProjectInfo designProjectInfo=new DesignProjectInfo();
        BeanUtil.copyProperties(designProjectInfoModel,designProjectInfo);
        designProjectInfo.setDelFlag(1);
        designProjectInfo.setStatus(1);
        designProjectInfo.setId(GenerateUUID.getUUID());
        baseMapper.insert(designProjectInfo);
    }

    @Override
    public List<DesignProjectInfoModel> getDesignProjectInfoList(DesignProjectInfoModel request,String uId) {
        QueryMap<DesignProjectInfoModel> params = getFilter(request);
        List<DesignProjectInfoModel> list = baseMapper.getDesignProjectInfoList(params.getQuery());
        return list;
    }

    @Override
    public DesignProjectInfoModel getDesignProjectInfoByBusinessId(String businessId, Integer businessType, String pId) {
        DesignProjectInfoModel designProjectInfoModel=new DesignProjectInfoModel();
        designProjectInfoModel.setBusinessId(businessId);
        designProjectInfoModel.setBusinessType(businessType);
        designProjectInfoModel.setpId(pId);
        designProjectInfoModel.setStatus(1);
        List<DesignProjectInfoModel> designProjectInfoList = getDesignProjectInfoList(designProjectInfoModel, null);
        DesignProjectInfoModel infoModel=new DesignProjectInfoModel();
        if(CollectionUtil.isNotEmpty(designProjectInfoList)){
            infoModel= designProjectInfoList.get(0);
        }
        return infoModel;
    }

    @Override
    public void delDesignProjectInfoByBusinessId(String businessId, Integer businessType) {
        baseMapper.delDesignProjectInfoByBusinessId(businessId,businessType);
    }

    private QueryMap<DesignProjectInfoModel> getFilter(DesignProjectInfoModel request) {
        QueryMap<DesignProjectInfoModel> params = new QueryMap<>(request);
        params.put("id").put("businessId").put("businessType").put("pId").put("status")
                .put("CreateUid").put("CreateUname");
        return params;
    }

    private void toHistoryData(DesignProjectInfoModel designProjectInfoModel) {

        QueryWrapper<DesignProjectInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("business_id", designProjectInfoModel.getBusinessId())
                .eq("business_type", designProjectInfoModel.getBusinessType())
                .eq("p_id", designProjectInfoModel.getpId())
                .eq("del_flag", 1)
                .eq("status", 1);
        List<DesignProjectInfo> designProjectInfos = baseMapper.selectList(wrapper);
        if(CollectionUtil.isNotEmpty(designProjectInfos)){
            designProjectInfos.stream().forEach(e->e.setStatus(0));
            this.saveOrUpdateBatch(designProjectInfos);
        }

    }


}
