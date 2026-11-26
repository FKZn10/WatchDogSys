package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.Project.ProConstructionAreaDtlModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.project.ProConstructionAreaDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.ProConstructionAreaDtl;
import com.jpmsoft.projectProducer.service.project.IProConstructionAreaDtlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProConstructionAreaDtlImpl extends ServiceImpl<ProConstructionAreaDtlMapper, ProConstructionAreaDtl> implements IProConstructionAreaDtlService {
    @Override
    public void insertProConstructionAreaDtls(List<ProConstructionAreaDtlModel> proConstructionAreaDtls, String pId, String projectMethod) {
        if (projectMethod.equals("create")) {
            if (proConstructionAreaDtls != null && proConstructionAreaDtls.size() > 0) {
                List<ProConstructionAreaDtl> addPojos = new ArrayList<>();
                proConstructionAreaDtls.forEach(model -> {
                    ProConstructionAreaDtl pojo = new ProConstructionAreaDtl();
                    BeanUtils.copyProperties(model, pojo);
                    pojo.setpId(pId);
                    pojo.setCatId(GenerateUUID.getUUID());
                    addPojos.add(pojo);
                });
                if (addPojos.size() > 0)
                    saveBatch(addPojos);
            }
        } else if (projectMethod.equals("update")) {
            if (!ValidateUtil.isEmpty(pId)) {
                QueryWrapper<ProConstructionAreaDtl> wrapper = new QueryWrapper<>();
                wrapper.eq("P_Id", pId);
                baseMapper.delete(wrapper);
            }
            if (proConstructionAreaDtls != null && proConstructionAreaDtls.size() > 0) {
                List<ProConstructionAreaDtl> addPojos = new ArrayList<>();
                proConstructionAreaDtls.forEach(model -> {
                    ProConstructionAreaDtl pojo = new ProConstructionAreaDtl();
                    BeanUtils.copyProperties(model, pojo);
                    pojo.setpId(pId);
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
            log.info("insertProConstructionAreaDtls 未知处理方式");
        }
    }

    @Override
    public List<ProConstructionAreaDtlModel> getProConstructionAreaDtlByPids(List<String> pIds) {
        if (pIds != null && pIds.size() > 0) {
            QueryWrapper<ProConstructionAreaDtl> wrapper = new QueryWrapper<>();
            wrapper.eq("P_Id", pIds.get(0));
            for (int i = 1; i < pIds.size(); i++) {
                wrapper.or().eq("P_Id", pIds.get(i));
            }
            List<ProConstructionAreaDtl> pojos = baseMapper.selectList(wrapper);
            List<ProConstructionAreaDtlModel> data = new ArrayList<>();
            pojos.forEach(pojo -> {
                ProConstructionAreaDtlModel model = new ProConstructionAreaDtlModel();
                BeanUtils.copyProperties(pojo, model);
                data.add(model);
            });
            return data;
        } else
            return new ArrayList<>();
    }

    @Override
    public void deleteByPid(String pId) {
        QueryWrapper<ProConstructionAreaDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", pId);
        baseMapper.delete(wrapper);
    }
}
