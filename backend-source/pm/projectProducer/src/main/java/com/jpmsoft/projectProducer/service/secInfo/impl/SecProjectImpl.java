package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.SecInfo.SecProjectRequest;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecProjectMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecProject;
import com.jpmsoft.projectProducer.service.secInfo.ISecProjectService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SecProjectImpl extends ServiceImpl<SecProjectMapper, SecProject> implements ISecProjectService {

    @Override
    public void createOrUpdateSecProject(List<SecProjectRequest> datas, String secId, String secInfoCreateUid) {
        deleteSecProjectBySecId(secId);
        List<SecProject> entitys = new ArrayList<>();
        datas.forEach(data -> {
            SecProject entity = new SecProject();
            entity.setSecId(secId);
            entity.setPId(data.getpId());
            entity.setSpId(GenerateUUID.getUUID());
            entity.setSecInfoCreateUid(secInfoCreateUid);
            entitys.add(entity);
        });
        if (entitys.size() > 0)
            saveBatch(entitys);
    }

    @Override
    public void createOrUpdateSecProjectByBath(List<String> pIds,List<SecProjectRequest> secProjectList,  List<String> secIds, String secInfoCreateUid) {
        List<SecProject> entitys = new ArrayList<>();
        //兼容原来。secProjectList是新加属性
        if(CollectionUtil.isNotEmpty(secProjectList)){
            for (SecProjectRequest secProjectRequest : secProjectList) {
                for (String secId : secIds) {
                    SecProject entity = new SecProject();
                    entity.setSecId(secId);
                    entity.setPId(secProjectRequest.getpId());
                    entity.setPpId(secProjectRequest.getPpId());
                    entity.setSpId(GenerateUUID.getUUID());
                    entity.setSecInfoCreateUid(secInfoCreateUid);
                    entitys.add(entity);
                }
            }
        }else{
            for (String pId : pIds) {
                for (String secId : secIds) {
                    SecProject entity = new SecProject();
                    entity.setSecId(secId);
                    entity.setPId(pId);
                    entity.setSpId(GenerateUUID.getUUID());
                    entity.setSecInfoCreateUid(secInfoCreateUid);
                    entitys.add(entity);
                }
            }
        }

        if (entitys.size() > 0)
            saveBatch(entitys);
    }

    @Override
    public void deleteAllOldByBpId(String bpId) {
        baseMapper.deleteAllOldByBpId(bpId);
    }

    @Override
    public List<Map<String, String>> getSecInfoListGroupByProject(Map<String, Object> params) {
        return baseMapper.getSecInfoListGroupByProject(params);
    }

    @Override
    public Integer getSecInfoListGroupByProjectCount(Map<String, Object> params) {
        return baseMapper.getSecInfoListGroupByProjectCount(params);
    }

    @Override
    public Boolean checkSecProjectCp(String secId, List<String> cpdCodes) {
        if (ValidateUtil.isEmpty(cpdCodes)) return true;
        if (baseMapper.havingSecProjectCp(secId, cpdCodes) < 1) return true;
        Integer count = baseMapper.checkSecProjectCp(secId, cpdCodes);
        if (count != null && count > 0) return true;
        return false;
    }

    @Override
    public List<SecProjectResponse> getSecInfoProject(String secId) {
        if (ValidateUtil.isEmpty(secId)) return null;
        return baseMapper.getSecInfoProject(secId);
    }


    public void deleteSecProjectBySecId(String secId) {
        QueryWrapper<SecProject> wrapper = new QueryWrapper<>();
        wrapper.eq("Sec_Id", secId);
        baseMapper.delete(wrapper);
    }
}
