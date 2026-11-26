package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.Project.ProHouseDtlModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.project.ProHouseDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.ProHouseDtl;
import com.jpmsoft.projectProducer.service.project.IProHouseDtlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProHouseDtlImpl extends ServiceImpl<ProHouseDtlMapper, ProHouseDtl> implements IProHouseDtlService {

    @Override
    public void insertProHouseDtls(List<ProHouseDtlModel> proHouseDtlModels, String pId, String projectMethod) {
        if (projectMethod.equals("create")) {
            if (proHouseDtlModels != null && proHouseDtlModels.size() > 0) {
                List<ProHouseDtl> addPojos = new ArrayList<>();
                proHouseDtlModels.forEach(model -> {
                    ProHouseDtl pojo = new ProHouseDtl();
                    BeanUtils.copyProperties(model, pojo);
                    pojo.setHouseId(GenerateUUID.getUUID());
                    pojo.setpId(pId);
                    addPojos.add(pojo);
                });
                if (addPojos.size() > 0)
                    saveBatch(addPojos);
            }
        } else if (projectMethod.equals("update")) {
            if (!ValidateUtil.isEmpty(pId)) {
                QueryWrapper<ProHouseDtl> wrapper = new QueryWrapper<>();
                wrapper.eq("P_Id", pId);
                baseMapper.delete(wrapper);
            }
            if (proHouseDtlModels != null && proHouseDtlModels.size() > 0) {
                List<ProHouseDtl> addPojos = new ArrayList<>();
                proHouseDtlModels.forEach(model -> {
                    ProHouseDtl pojo = new ProHouseDtl();
                    BeanUtils.copyProperties(model, pojo);
                    pojo.setpId(pId);
                    if (ValidateUtil.isEmpty(model.getHouseId())) {
                        pojo.setHouseId(GenerateUUID.getUUID());
                        addPojos.add(pojo);
                    } else {
                        addPojos.add(pojo);
                    }
                });
                if (addPojos.size() > 0)
                    this.saveBatch(addPojos);
            }
        } else {
            log.info("insertProSourcesFunds 未知处理方式");
        }
    }

    @Override
    public List<ProHouseDtlModel> getProHouseDtlsByPids(List<String> pIds) {
        if (pIds != null && pIds.size() > 0) {
            QueryWrapper<ProHouseDtl> wrapper = new QueryWrapper<>();
            wrapper.eq("P_Id", pIds.get(0));
            for (int i = 1; i < pIds.size(); i++) {
                wrapper.or().eq("P_Id", pIds.get(i));
            }
            List<ProHouseDtl> pojos = baseMapper.selectList(wrapper);
            List<ProHouseDtlModel> data = new ArrayList<>();
            pojos.forEach(pojo -> {
                ProHouseDtlModel model = new ProHouseDtlModel();
                BeanUtils.copyProperties(pojo, model);
                data.add(model);
            });
            return data;
        } else
            return new ArrayList<>();
    }
}
