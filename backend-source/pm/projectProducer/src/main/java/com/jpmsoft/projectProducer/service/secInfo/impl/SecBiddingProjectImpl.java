package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.SecInfo.SecBiddingProjectRequest;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecBiddingProjectMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBiddingProject;
import com.jpmsoft.projectProducer.service.secInfo.ISecBiddingProjectService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SecBiddingProjectImpl extends ServiceImpl<SecBiddingProjectMapper, SecBiddingProject> implements ISecBiddingProjectService {


    @Override
    public void createOrUpdateSecBiddingProject(List<String> pIds, List<SecBiddingProjectRequest> secBiddingProjectList , String bpId, String createUid) {
        deleteOldAll(bpId);
        if (pIds != null && pIds.size() > 0) {
            List<SecBiddingProject> entitys = new ArrayList<>();
            //secBiddingProjectList是新增属性，旧版没有这个属性。
            if(CollectionUtil.isNotEmpty(secBiddingProjectList)){
                for (SecBiddingProjectRequest secBiddingProjectRequest : secBiddingProjectList) {
                    SecBiddingProject entity = new SecBiddingProject();
                    entity.setSbpId(GenerateUUID.getUUID());
                    entity.setBpId(bpId);
                    entity.setPId(secBiddingProjectRequest.getpId());
                    entity.setPpId(secBiddingProjectRequest.getPpId());
                    entity.setSbpCreateUid(createUid);
                    entitys.add(entity);
                }
            }else{
                for (String pId : pIds) {
                    SecBiddingProject entity = new SecBiddingProject();
                    entity.setSbpId(GenerateUUID.getUUID());
                    entity.setBpId(bpId);
                    entity.setPId(pId);
                    entity.setSbpCreateUid(createUid);
                    entitys.add(entity);
                }
            }

            if (entitys.size() > 0)
                saveBatch(entitys);
        }
    }

    @Override
    public Boolean haveSBPCount(List<String> pIds, String bpId) {
        if (ValidateUtil.isEmpty(pIds)) return false;
        if (baseMapper.haveSBPCount(pIds, ValidateUtil.isEmpty(bpId) ? null : bpId) > 0)
            return true;
        return false;
    }

    @Override
    public List<String> getSecBiddingProjectPids(String bpId) {
        if (ValidateUtil.isEmpty(bpId)) return new ArrayList<>();
        return baseMapper.getSecBiddingProjectPids(bpId);
    }

    @Override
    public  Map<String,Object>  getSecBiddingProject(String bpId) {
        List<SecBiddingProject> secBiddingProject = baseMapper.getSecBiddingProject(bpId);

        Map<String,Object> map=new HashMap<>();
        List<SecBiddingProjectRequest> secBiddingProjectRequestList=new ArrayList<>();
        List<String> pIds =new ArrayList<>();
        if(CollectionUtil.isNotEmpty(secBiddingProject)){
            pIds = secBiddingProject.stream().map(e -> e.getPId()).distinct().collect(Collectors.toList());
            secBiddingProjectRequestList=BeanUtils.copyList(secBiddingProject,SecBiddingProjectRequest.class);
        }
        map.put("pIds",pIds);
        map.put("secBiddingProjectRequestList",secBiddingProjectRequestList);
        return map;
    }


    private void deleteOldAll(String bpId) {
        QueryWrapper<SecBiddingProject> wrapper = new QueryWrapper<>();
        wrapper.eq("Bp_Id", bpId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<SecBiddingProject> getSecBiddingPidData(String projectId) {
        LambdaQueryWrapper<SecBiddingProject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SecBiddingProject::getPId,projectId);
        return baseMapper.selectList(queryWrapper);
    }
}
