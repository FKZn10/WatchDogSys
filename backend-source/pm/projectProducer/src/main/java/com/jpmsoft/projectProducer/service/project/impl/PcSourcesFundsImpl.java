package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.enums.AuditTypeEnum;
import com.jpmsoft.common.model.requestAndResponse.Project.PcSourcesFundsModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.project.PcSourcesFundsMapper;
import com.jpmsoft.projectProducer.pojo.dto.base.VueTreeDto;
import com.jpmsoft.projectProducer.pojo.entity.project.PcSourcesFunds;
import com.jpmsoft.projectProducer.pojo.entity.project.ProSourcesFunds;
import com.jpmsoft.projectProducer.rabbitMQ.Sender;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.project.IPcSourcesFundsService;
import com.jpmsoft.projectProducer.service.project.IProSourcesFundsService;
import com.jpmsoft.projectProducer.utils.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PcSourcesFundsImpl extends ServiceImpl<PcSourcesFundsMapper, PcSourcesFunds> implements IPcSourcesFundsService {

    @Resource
    private IProSourcesFundsService proSourcesFundsService;

    @Resource
    private Sender sender;

    @Resource
    private IApiUtilsService apiUtilsService;


    @Override
    public void insertPcSourcesFunds(List<PcSourcesFundsModel> pcSourcesFundsModels, String pId,
                                     String pcId, String uId, String uName, String projectMethod, Boolean tempSave) {
        if (projectMethod.equals("create")) {
            if (pcSourcesFundsModels != null && pcSourcesFundsModels.size() > 0) {
                List<PcSourcesFunds> addPojos = new ArrayList<>();
                pcSourcesFundsModels.forEach(model -> {
                    PcSourcesFunds pojo = new PcSourcesFunds();
                    BeanUtils.copyProperties(model, pojo);
                    pojo.setFsId(GenerateUUID.getUUID());
                    pojo.setFsCreateTime(new Date());
                    pojo.setPcId(pcId);
                    addPojos.add(pojo);
                });
                if (addPojos.size() > 0) {
                    this.saveBatch(addPojos);
                    if (tempSave != null && !tempSave)
                        createOrUpdateAudit(addPojos, pId, pcId, uId, uName);
                }
            }
        } else if (projectMethod.equals("update")) {
            if (!ValidateUtil.isEmpty(pcId)) {
                QueryWrapper<PcSourcesFunds> wrapper = new QueryWrapper<>();
                wrapper.eq("Pc_Id", pcId);
                baseMapper.delete(wrapper);
            }
            if (pcSourcesFundsModels != null && pcSourcesFundsModels.size() > 0) {
                List<PcSourcesFunds> addPojos = new ArrayList<>();
                pcSourcesFundsModels.forEach(model -> {
                    PcSourcesFunds pojo = new PcSourcesFunds();
                    BeanUtils.copyProperties(model, pojo);
                    pojo.setPcId(pcId);
                    if (ValidateUtil.isEmpty(model.getFsId())) {
                        pojo.setFsId(GenerateUUID.getUUID());
                        pojo.setFsCreateTime(new Date());
                        addPojos.add(pojo);
                    } else {
                        addPojos.add(pojo);
                    }
                });
                if (addPojos.size() > 0) {
                    this.saveBatch(addPojos);
                    if (tempSave != null && !tempSave)
                        createOrUpdateAudit(addPojos, pId, pcId, uId, uName);
                }
            }
        } else {
            log.info("insertPcSourcesFunds 未知处理方式");
        }
    }

    @Override
    public List<PcSourcesFundsModel> getPcSourcesFundsByPcIds(List<String> pcIds) {
        if (pcIds != null && pcIds.size() > 0) {
            QueryWrapper<PcSourcesFunds> wrapper = new QueryWrapper<>();
            wrapper.eq("Pc_Id", pcIds.get(0));
            for (int i = 1; i < pcIds.size(); i++) {
                wrapper.or().eq("Pc_Id", pcIds.get(i));
            }
            List<PcSourcesFunds> pojos = baseMapper.selectList(wrapper);
            List<PcSourcesFundsModel> data = new ArrayList<>();
            pojos.forEach(pojo -> {
                PcSourcesFundsModel model = new PcSourcesFundsModel();
                BeanUtils.copyProperties(pojo, model);
                data.add(model);
            });
            return data;
        } else
            return new ArrayList<>();
    }

    @Override
    public void syncPcSourcesFunds2Project(String pcId, String pId, String uId, String uName) {
        QueryWrapper<PcSourcesFunds> wrapper = new QueryWrapper<>();
        wrapper.eq("Pc_Id", pcId);
        List<PcSourcesFunds> datas = baseMapper.selectList(wrapper);
        if (datas != null && datas.size() > 0) {
            List<ProSourcesFunds> entitys = new ArrayList<>();
            ProSourcesFunds entity;
            for (PcSourcesFunds data : datas) {
                entity = new ProSourcesFunds();
                BeanUtils.copyProperties(data, entity);
                entity.setPId(pId);
                entity.setFsId(GenerateUUID.getUUID());
                entitys.add(entity);
            }
            sendAudit(entitys, pId, pcId, uId, uName, "pro");
            proSourcesFundsService.deleteByPid(pId);
            proSourcesFundsService.saveBatch(entitys);
        } else
            proSourcesFundsService.deleteByPid(pId);
    }


    private void createOrUpdateAudit(List<PcSourcesFunds> entitys, String pId, String pcId, String uId, String uName) {
        List<ProSourcesFunds> news = entitys.stream().map(entity -> {
            ProSourcesFunds pro = new ProSourcesFunds();
            BeanUtils.copyProperties(entity, pro);
            return pro;
        }).collect(Collectors.toList());
        sendAudit(news, pId, pcId, uId, uName, "change");
    }


    private void sendAudit(List<ProSourcesFunds> entitys, String pId, String pcId, String uId, String uName, String action) {
        List<ProSourcesFunds> proFunds = proSourcesFundsService.getProSourcesFundsPojoByPid(pId);
        List<String> newCodes = entitys.stream().map(ProSourcesFunds::getFsCode).collect(Collectors.toList());
        List<String> oldCodes = proFunds.stream().map(ProSourcesFunds::getFsCode).collect(Collectors.toList());
        List<ProSourcesFunds> adds = entitys.stream().filter(n -> !oldCodes.contains(n.getFsCode())).collect(Collectors.toList());
        List<ProSourcesFunds> dels = proFunds.stream().filter(n -> !newCodes.contains(n.getFsCode())).collect(Collectors.toList());
        List<ProSourcesFunds> ups;
        if (!CollectionUtils.isEmpty(adds))
            ups = entitys.stream().filter(n -> !CollectionUtils.isEmpty(adds) && !adds.contains(n)).collect(Collectors.toList());
        else ups = entitys;
        List<VueTreeDto> dics = apiUtilsService.getDictionaryTree("JPMsoft_XMZJLY");
        for (ProSourcesFunds add : adds) {
            List<VueTreeDto> dic = dics.stream().filter(n -> n.getValue().equals(add.getFsCode())).collect(Collectors.toList());
            String type = add.getFsCode();
            if (!CollectionUtils.isEmpty(dic))
                type = dic.get(0).getTitle();
            String msg = "资金来源新增" + type + ", 金额：" + MathUtil.DecimalDivisionInt(add.getFsMoney(), 10000) + "万元";
            if (action.equals("pro")) sender.sendAudit(msg, uId, uName, pId, AuditTypeEnum.add.getkey());
            if (action.equals("change")) sender.sendAudit(msg, uId, uName, pcId, AuditTypeEnum.add.getkey());
        }

        for (ProSourcesFunds del : dels) {
            List<VueTreeDto> dic = dics.stream().filter(n -> n.getValue().equals(del.getFsCode())).collect(Collectors.toList());
            String type = del.getFsCode();
            if (!CollectionUtils.isEmpty(dic))
                type = dic.get(0).getTitle();
            String msg = "资金来源删除" + type + ", 金额：" + MathUtil.DecimalDivisionInt(del.getFsMoney(), 10000) + "万元";
            if (action.equals("pro")) sender.sendAudit(msg, uId, uName, pId, AuditTypeEnum.del.getkey());
            if (action.equals("change")) sender.sendAudit(msg, uId, uName, pcId, AuditTypeEnum.del.getkey());
        }

        for (ProSourcesFunds up : ups) {
            ProSourcesFunds old = proFunds.stream().filter(n -> n.getFsCode().equals(up.getFsCode())).findFirst().get();
            if (old.getFsMoney().compareTo(up.getFsMoney()) == 0) continue;
            List<VueTreeDto> dic = dics.stream().filter(n -> n.getValue().equals(up.getFsCode())).collect(Collectors.toList());
            String type = up.getFsCode();
            if (!CollectionUtils.isEmpty(dic))
                type = dic.get(0).getTitle();
            String msg = "资金来源更新" + type + ", 金额：" + MathUtil.DecimalDivisionInt(old.getFsMoney(), 10000) + "万元变更为"
                    + MathUtil.DecimalDivisionInt(up.getFsMoney(), 10000) + "万元";
            if (action.equals("pro")) sender.sendAudit(msg, uId, uName, pId, AuditTypeEnum.upd.getkey());
            if (action.equals("change")) sender.sendAudit(msg, uId, uName, pcId, AuditTypeEnum.upd.getkey());
        }
    }

}
