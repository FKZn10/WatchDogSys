package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.RefershFileDto;
import com.jpmsoft.common.model.requestAndResponse.Oa.ReportedPersonnelDtlModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.feign.FileFeign;
import com.jpmsoft.projectProducer.mapper.oa.ReportedPersonnelDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.ReportedPersonnelDtl;
import com.jpmsoft.projectProducer.service.oa.IReportedPersonnelDtlService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportedPersonnelDtlImpl extends ServiceImpl<ReportedPersonnelDtlMapper, ReportedPersonnelDtl> implements IReportedPersonnelDtlService {
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private FileFeign fileFeign;

    @Override
    public void createOrUpdateReportedPersonnelDtl(List<ReportedPersonnelDtlModel> models, String rpId) {
        deleteByRpId(rpId);
        //排除没有传入用户ID的数据
        models = models.stream().filter(f -> ValidateUtil.isNotEmpty(f.getRpdUid())).collect(Collectors.toList());
        //region 过滤同岗位同用户人员
        List<String> ppUserId = new ArrayList<>();
        List<ReportedPersonnelDtlModel> datas = new ArrayList<>();
        for (ReportedPersonnelDtlModel data : models) {
            if (!ppUserId.contains(data.getRpdPpid() + data.getRpdUid())) {
                datas.add(data);
                ppUserId.add(data.getRpdPpid() + data.getRpdUid());
            }
        }
        //endregion
        if (!CollectionUtils.isEmpty(datas)) {
            List<ReportedPersonnelDtl> entitys = new ArrayList<>();
            ReportedPersonnelDtl entity;
            List<RefershFileDto> refershs = new ArrayList<>();
            for (ReportedPersonnelDtlModel data : datas) {
                entity = new ReportedPersonnelDtl();
                data.setRpdId(GenerateUUID.getUUID());
                data.setRpId(rpId);
                BeanUtils.copyProperties(data, entity);
                entitys.add(entity);
                RefershFileDto refersh = new RefershFileDto();
                refersh.setEntityId(entity.getRpdId());
                refersh.setRelationIds(Arrays.asList(data.getRpdQualification()));
                refersh.setFileIds(Arrays.asList(data.getRpdQualificationIds()));
                refershs.add(refersh);
            }
            if (entitys.size() > 0) {
                saveBatch(entitys);
                fileFeign.refreshRelationFileBath(refershs);
            }
        }
    }

    @Override
    public List<ReportedPersonnelDtlModel> getReportedPersonnelDtlByRpId(String rpId) {
        QueryWrapper<ReportedPersonnelDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Rp_Id", rpId);
        wrapper.ne("rpd_uid", "");
        wrapper.isNotNull("rpd_Uid");
        wrapper.orderByAsc("rpd_sort");
        List<ReportedPersonnelDtl> datas = baseMapper.selectList(wrapper);
        List<ReportedPersonnelDtlModel> models = new ArrayList<>();
        datas.forEach(data -> {
            ReportedPersonnelDtlModel model = new ReportedPersonnelDtlModel();
            BeanUtils.copyProperties(data, model);
            models.add(model);
        });
        return models;
    }

    @Override
    public String can2Filing(List<ReportedPersonnelDtlModel> datas, String pId) {
        String conf = sysConfigService.getConfigByCode("reportedPersonnel");
        if (ValidateUtil.isEmpty(conf)) return null;
        List<String> musts = Arrays.asList(conf.split(","));
        List<String> names = baseMapper.getProjPositionPName(pId,
                datas.stream().filter(n -> n.getRpdType().equals("移除")).map(ReportedPersonnelDtlModel::getRpdUid).distinct().collect(Collectors.toList()));
        names.addAll(datas.stream().filter(n -> n.getRpdType().equals("添加")).map(ReportedPersonnelDtlModel::getPpName).distinct().collect(Collectors.toList()));
        List<String> surplus = musts.stream().filter(n -> !names.contains(n)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(surplus))
            return String.join(",", surplus);
        return null;
    }

    private void deleteByRpId(String rpId) {
        QueryWrapper<ReportedPersonnelDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Rp_Id", rpId);
        baseMapper.delete(wrapper);
    }
}
