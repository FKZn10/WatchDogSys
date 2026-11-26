package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.Project.PcConstructionAreaDtlModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.project.PcConstructionAreaDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.PcConstructionAreaDtl;
import com.jpmsoft.projectProducer.pojo.entity.project.ProConstructionAreaDtl;
import com.jpmsoft.projectProducer.service.project.IPcConstructionAreaDtlService;
import com.jpmsoft.projectProducer.service.project.IProConstructionAreaDtlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PcConstructionAreaDtlImpl extends ServiceImpl<PcConstructionAreaDtlMapper, PcConstructionAreaDtl> implements IPcConstructionAreaDtlService {

    @Resource
    private IProConstructionAreaDtlService proConstructionAreaDtlService;


    @Override
    public void insertPcConstructionAreaDtls(List<PcConstructionAreaDtlModel> PcConstructionAreaDtls, String pcId, String projectMethod) {
        if (projectMethod.equals("create")) {
            if (PcConstructionAreaDtls != null && PcConstructionAreaDtls.size() > 0) {
                List<PcConstructionAreaDtl> addPojos = new ArrayList<>();
                PcConstructionAreaDtls.forEach(model -> {
                    PcConstructionAreaDtl pojo = new PcConstructionAreaDtl();
                    BeanUtils.copyProperties(model, pojo);
                    pojo.setPcId(pcId);
                    pojo.setCatId(GenerateUUID.getUUID());
                    addPojos.add(pojo);
                });
                if (addPojos.size() > 0)
                    saveBatch(addPojos);
            }
        } else if (projectMethod.equals("update")) {
            if (!ValidateUtil.isEmpty(pcId)) {
                QueryWrapper<PcConstructionAreaDtl> wrapper = new QueryWrapper<>();
                wrapper.eq("Pc_Id", pcId);
                baseMapper.delete(wrapper);
            }
            if (PcConstructionAreaDtls != null && PcConstructionAreaDtls.size() > 0) {
                List<PcConstructionAreaDtl> addPojos = new ArrayList<>();
                PcConstructionAreaDtls.forEach(model -> {
                    PcConstructionAreaDtl pojo = new PcConstructionAreaDtl();
                    BeanUtils.copyProperties(model, pojo);
                    pojo.setPcId(pcId);
                    if (ValidateUtil.isEmpty(model.getCatId())) {
                        pojo.setCatId(GenerateUUID.getUUID());
                        addPojos.add(pojo);
                    } else {
                        addPojos.add(pojo);
                    }
                });
                if (addPojos.size() > 0)
                    saveBatch(addPojos);
            }
        } else {
            log.info("insertPcConstructionAreaDtls 未知处理方式");
        }
    }

    @Override
    public List<PcConstructionAreaDtlModel> getPcConstructionAreaDtlByPcIds(List<String> pcIds) {
        if (pcIds != null && pcIds.size() > 0) {
            QueryWrapper<PcConstructionAreaDtl> wrapper = new QueryWrapper<>();
            wrapper.eq("Pc_Id", pcIds.get(0));
            for (int i = 1; i < pcIds.size(); i++) {
                wrapper.or().eq("Pc_Id", pcIds.get(i));
            }
            List<PcConstructionAreaDtl> pojos = baseMapper.selectList(wrapper);
            List<PcConstructionAreaDtlModel> data = new ArrayList<>();
            pojos.forEach(pojo -> {
                PcConstructionAreaDtlModel model = new PcConstructionAreaDtlModel();
                BeanUtils.copyProperties(pojo, model);
                data.add(model);
            });
            return data;
        } else
            return new ArrayList<>();
    }

    @Override
    public void syncPcConstructionAreaDtl2Project(String pcId, String pId) {
        proConstructionAreaDtlService.deleteByPid(pId);
        QueryWrapper<PcConstructionAreaDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Pc_Id", pcId);
        List<PcConstructionAreaDtl> datas = baseMapper.selectList(wrapper);
        if (datas != null && datas.size() > 0) {
            List<ProConstructionAreaDtl> entitys = new ArrayList<>();
            ProConstructionAreaDtl entity;
            for (PcConstructionAreaDtl data : datas) {
                entity = new ProConstructionAreaDtl();
                BeanUtils.copyProperties(data, entity);
                entity.setpId(pId);
                entity.setCatId(GenerateUUID.getUUID());
                entitys.add(entity);
            }
            if (entitys.size() > 0)
                proConstructionAreaDtlService.saveBatch(entitys);
        }
    }
}
