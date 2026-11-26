package com.jpmsoft.projectProducer.service.peInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.peInfo.PeMeetingApprovalListMapper;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeMeetingApprovalList;
import com.jpmsoft.projectProducer.service.peInfo.IPeMeetingApprovalListService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/16 10:14
 */
@Service
public class PeMeetingApprovalListImpl extends ServiceImpl<PeMeetingApprovalListMapper, PeMeetingApprovalList> implements IPeMeetingApprovalListService {

    @Override
    public void createOrUpdatePMAList(List<String> peIds, String maId) {
        deletePMAListByMaId(maId);
        if (ValidateUtil.isNotEmpty(peIds)) {
            List<PeMeetingApprovalList> entitys = new ArrayList<>();
            entitys = peIds.stream().map(n -> {
                PeMeetingApprovalList entity = new PeMeetingApprovalList();
                entity.setPeId(n);
                entity.setMaId(maId);
                entity.setMalId(GenerateUUID.getUUID());
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entitys);
        }
    }

    @Override
    public void deletePMAListByMaId(String maId) {
        QueryWrapper<PeMeetingApprovalList> wrapper = new QueryWrapper<>();
        wrapper.eq("Ma_Id", maId);
        baseMapper.delete(wrapper);
    }

}
