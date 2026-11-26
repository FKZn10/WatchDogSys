package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.enums.AuditTypeEnum;
import com.jpmsoft.common.model.requestAndResponse.Project.PcSourcesFundsModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.project.PcInvDetailMapper;
import com.jpmsoft.projectProducer.pojo.dto.base.VueTreeDto;
import com.jpmsoft.projectProducer.pojo.entity.project.PcInvDetail;
import com.jpmsoft.projectProducer.pojo.entity.project.ProInvDetail;
import com.jpmsoft.projectProducer.rabbitMQ.Sender;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.project.IPcInvDetailService;
import com.jpmsoft.projectProducer.service.project.IProInvDetailService;
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
public class PcInvDetailImpl extends ServiceImpl<PcInvDetailMapper, PcInvDetail> implements IPcInvDetailService {
    @Resource
    private IProInvDetailService proInvDetailService;
    @Resource
    private Sender sender;
    @Resource
    private IApiUtilsService apiUtilsService;


    @Override
    public void insertPcInvDetail(List<PcSourcesFundsModel> pcSourcesFundsModels, String pId, String pcId,
                                  String uId, String uName, String projectMethod, Boolean tempSave) {
        if (projectMethod.equals("create")) {
            if (pcSourcesFundsModels != null && pcSourcesFundsModels.size() > 0) {
                List<PcInvDetail> addPojos = new ArrayList<>();
                pcSourcesFundsModels.forEach(model -> {
                    PcInvDetail pojo = new PcInvDetail();
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
                QueryWrapper<PcInvDetail> wrapper = new QueryWrapper<>();
                wrapper.eq("Pc_Id", pcId);
                baseMapper.delete(wrapper);
            }
            if (pcSourcesFundsModels != null && pcSourcesFundsModels.size() > 0) {
                List<PcInvDetail> addPojos = new ArrayList<>();
                pcSourcesFundsModels.forEach(model -> {
                    PcInvDetail pojo = new PcInvDetail();
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
    public List<PcSourcesFundsModel> getPcInvDetailByPcIds(List<String> pcIds) {
        if (pcIds != null && pcIds.size() > 0) {
            QueryWrapper<PcInvDetail> wrapper = new QueryWrapper<>();
            wrapper.eq("Pc_Id", pcIds.get(0));
            for (int i = 1; i < pcIds.size(); i++) {
                wrapper.or().eq("Pc_Id", pcIds.get(i));
            }
            List<PcInvDetail> pojos = baseMapper.selectList(wrapper);
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
    public void syncPcInvDetail2Project(String pcId, String pId, String uId, String uName) {
        QueryWrapper<PcInvDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("Pc_Id", pcId);
        List<PcInvDetail> datas = baseMapper.selectList(wrapper);
        if (datas != null && datas.size() > 0) {
            List<ProInvDetail> entitys = new ArrayList<>();
            ProInvDetail entity;
            for (PcInvDetail data : datas) {
                entity = new ProInvDetail();
                BeanUtils.copyProperties(data, entity);
                entity.setpId(pId);
                entity.setFsId(GenerateUUID.getUUID());
                entitys.add(entity);
            }
            auditSend(entitys, pId, pcId, uId, uName, "pro");
            proInvDetailService.deleteByPid(pId);
            proInvDetailService.saveBatch(entitys);
        } else
            proInvDetailService.deleteByPid(pId);
    }

    private void createOrUpdateAudit(List<PcInvDetail> entitys, String pId, String pcId, String uId, String uName) {
        List<ProInvDetail> news = entitys.stream().map(entity -> {
            ProInvDetail pro = new ProInvDetail();
            BeanUtils.copyProperties(entity, pro);
            return pro;
        }).collect(Collectors.toList());
        auditSend(news, pId, pcId, uId, uName, "change");
    }

    private void auditSend(List<ProInvDetail> entitys, String pId, String pcId, String uId, String uName, String action) {
        List<ProInvDetail> proFunds = proInvDetailService.getProInvDetailPojoByPid(pId);
        List<String> newCodes = entitys.stream().map(ProInvDetail::getFsCode).collect(Collectors.toList());
        List<String> oldCodes = proFunds.stream().map(ProInvDetail::getFsCode).collect(Collectors.toList());
        List<ProInvDetail> adds = entitys.stream().filter(n -> !oldCodes.contains(n.getFsCode())).collect(Collectors.toList());
        List<ProInvDetail> dels = proFunds.stream().filter(n -> !newCodes.contains(n.getFsCode())).collect(Collectors.toList());
        List<ProInvDetail> ups;
        if (!CollectionUtils.isEmpty(adds))
            ups = entitys.stream().filter(n -> !CollectionUtils.isEmpty(adds) && !adds.contains(n)).collect(Collectors.toList());
        else ups = entitys;
        List<VueTreeDto> dics = apiUtilsService.getDictionaryTree("JPMsoft_XMTZFL");
        for (ProInvDetail add : adds) {
            List<VueTreeDto> dic = dics.stream().filter(n -> n.getValue().equals(add.getFsCode())).collect(Collectors.toList());
            String type = add.getFsCode();
            if (!CollectionUtils.isEmpty(dic))
                type = dic.get(0).getTitle();
            String msg = "投资明细新增" + type + ", 金额：" + MathUtil.DecimalDivisionInt(add.getFsMoney(), 10000) + "万元";
            if (action.equals("pro")) sender.sendAudit(msg, uId, uName, pId, AuditTypeEnum.add.getkey());
            if (action.equals("change")) sender.sendAudit(msg, uId, uName, pcId, AuditTypeEnum.add.getkey());
        }

        for (ProInvDetail del : dels) {
            List<VueTreeDto> dic = dics.stream().filter(n -> n.getValue().equals(del.getFsCode())).collect(Collectors.toList());
            String type = del.getFsCode();
            if (!CollectionUtils.isEmpty(dic))
                type = dic.get(0).getTitle();
            String msg = "投资明细删除" + type + ", 金额：" + MathUtil.DecimalDivisionInt(del.getFsMoney(), 10000) + "万元";
            if (action.equals("pro")) sender.sendAudit(msg, uId, uName, pId, AuditTypeEnum.del.getkey());
            if (action.equals("change")) sender.sendAudit(msg, uId, uName, pcId, AuditTypeEnum.del.getkey());
        }

        for (ProInvDetail up : ups) {
            ProInvDetail old = proFunds.stream().filter(n -> n.getFsCode().equals(up.getFsCode())).findFirst().get();
            if (old.getFsMoney().compareTo(up.getFsMoney()) == 0) continue;
            List<VueTreeDto> dic = dics.stream().filter(n -> n.getValue().equals(up.getFsCode())).collect(Collectors.toList());
            String type = up.getFsCode();
            if (!CollectionUtils.isEmpty(dic))
                type = dic.get(0).getTitle();
            String msg = "投资明细更新" + type + ", 金额：" + MathUtil.DecimalDivisionInt(old.getFsMoney(), 10000) + "万元变更为"
                    + MathUtil.DecimalDivisionInt(up.getFsMoney(), 10000) + "万元";
            if (action.equals("pro")) sender.sendAudit(msg, uId, uName, pId, AuditTypeEnum.upd.getkey());
            if (action.equals("change")) sender.sendAudit(msg, uId, uName, pcId, AuditTypeEnum.upd.getkey());
        }
    }

}
