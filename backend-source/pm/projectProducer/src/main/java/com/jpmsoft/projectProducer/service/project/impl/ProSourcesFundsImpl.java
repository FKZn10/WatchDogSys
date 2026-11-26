package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProSourcesFundsModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.project.ProSourcesFundsMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.ProSourcesFunds;
import com.jpmsoft.projectProducer.pojo.entity.project.ProSourcesFundsReg;
import com.jpmsoft.projectProducer.service.project.IProSourcesFundsRegService;
import com.jpmsoft.projectProducer.service.project.IProSourcesFundsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ProSourcesFundsImpl extends ServiceImpl<ProSourcesFundsMapper, ProSourcesFunds> implements IProSourcesFundsService {

    @Resource
    private IProSourcesFundsRegService proSourcesFundsRegService;

    @Override
    public void insertProSourcesFunds(List<ProSourcesFundsModel> proSourcesFundsModels, String pId, String projectMethod) {
        if (projectMethod.equals("create")) {
            if (proSourcesFundsModels != null && proSourcesFundsModels.size() > 0) {
                List<ProSourcesFunds> addPojos = new ArrayList<>();
                proSourcesFundsModels.forEach(model -> {
                    ProSourcesFunds pojo = new ProSourcesFunds();
                    BeanUtils.copyProperties(model, pojo);
                    pojo.setFsId(GenerateUUID.getUUID());
                    pojo.setFsCreateTime(new Date());
                    pojo.setPId(pId);
                    addPojos.add(pojo);
                });
                if (addPojos.size() > 0)
                    this.saveBatch(addPojos);
            }
        } else if (projectMethod.equals("update")) {
            if (!ValidateUtil.isEmpty(pId)) {
                QueryWrapper<ProSourcesFunds> wrapper = new QueryWrapper<>();
                wrapper.eq("P_Id", pId);
                baseMapper.delete(wrapper);
            }
            if (proSourcesFundsModels != null && proSourcesFundsModels.size() > 0) {
                List<ProSourcesFunds> addPojos = new ArrayList<>();
                proSourcesFundsModels.forEach(model -> {
                    ProSourcesFunds pojo = new ProSourcesFunds();
                    BeanUtils.copyProperties(model, pojo);
                    pojo.setPId(pId);
                    if (ValidateUtil.isEmpty(model.getFsId())) {
                        pojo.setFsId(GenerateUUID.getUUID());
                        pojo.setFsCreateTime(new Date());
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
    public List<ProSourcesFundsModel> getProSourcesFundsByPids(List<String> pIds) {
        if (pIds != null && pIds.size() > 0) {
            QueryWrapper<ProSourcesFunds> wrapper = new QueryWrapper<>();
            wrapper.eq("P_Id", pIds.get(0));
            for (int i = 1; i < pIds.size(); i++) {
                wrapper.or().eq("P_Id", pIds.get(i));
            }
            List<ProSourcesFunds> pojos = baseMapper.selectList(wrapper);
            List<ProSourcesFundsModel> data = new ArrayList<>();
            pojos.forEach(pojo -> {
                ProSourcesFundsModel model = new ProSourcesFundsModel();
                BeanUtils.copyProperties(pojo, model);
                data.add(model);
            });
            return data;
        } else
            return new ArrayList<>();
    }

    @Override
    public void copyDataByFundsReg(String prId, String pId) {
        List<ProSourcesFundsReg> datas = proSourcesFundsRegService.getProSourcesFundsRegByPrId(prId);
        if (datas != null && datas.size() > 0) {
            List<ProSourcesFunds> entitys = new ArrayList<>();
            datas.forEach(data -> {
                ProSourcesFunds entity = new ProSourcesFunds();
                BeanUtils.copyProperties(data, entity);
                entity.setPId(pId);
                entity.setFsCreateTime(new Date());
                entitys.add(entity);
            });
            if (entitys.size() > 0)
                saveBatch(entitys);
        }
    }

    @Override
    public BaseResponse getSourcesFundsSumByPids(List<String> pIds) {
        if (ValidateUtil.isEmpty(pIds)) return BaseResponse.ok(new ArrayList<>());
        List<Map<String, Object>> datas = baseMapper.getSourcesFundsSumByPids(pIds);
        return BaseResponse.ok(datas);
    }

    @Override
    public List<ProSourcesFunds> getProSourcesFundsPojoByPids(List<String> pIds) {
        if (pIds != null && pIds.size() > 0) {
            QueryWrapper<ProSourcesFunds> wrapper = new QueryWrapper<>();
            wrapper.eq("P_Id", pIds.get(0));
            for (int i = 1; i < pIds.size(); i++) {
                wrapper.or().eq("P_Id", pIds.get(i));
            }
            List<ProSourcesFunds> pojos = baseMapper.selectList(wrapper);
            return pojos;
        } else
            return new ArrayList<>();
    }

    @Override
    public List<ProSourcesFunds> getProSourcesFundsPojoByPid(String pId) {
        QueryWrapper<ProSourcesFunds> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", pId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public void deleteByPid(String pId) {
        QueryWrapper<ProSourcesFunds> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", pId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<ProSourcesFunds> getByProjectIds(List<String> pIds) {
        if (ValidateUtil.isEmpty(pIds)) return null;
        QueryWrapper<ProSourcesFunds> wrapper = new QueryWrapper<>();
        wrapper.in("P_Id", pIds);
        List<ProSourcesFunds> proSourcesFundsList = list(wrapper);
        return proSourcesFundsList;
    }
}
