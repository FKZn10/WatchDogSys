package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysPositionlvMapper;
import com.jpmsoft.userProducer.pojo.dto.PositionlvDto;
import com.jpmsoft.userProducer.pojo.entity.SysPositionlv;
import com.jpmsoft.userProducer.service.ISysPositionlvService;
import com.jpmsoft.userProducer.service.ISysSequenceSetService;
import com.jpmsoft.userProducer.utils.CheckNullUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class SysPositionlvImpl extends ServiceImpl<SysPositionlvMapper, SysPositionlv> implements ISysPositionlvService {

    @Resource
    private ISysSequenceSetService sysSequenceSetService;

    @Override
    public BaseResponse GetPositionLVList(PositionlvDto positionlv) {
        QueryWrapper<SysPositionlv> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(positionlv.getTeId()))
            wrapper.eq("Te_Id", positionlv.getTeId());
        if (!ValidateUtil.isEmpty(positionlv.getPositionPStatus()))
            wrapper.eq("Position_P_Status", positionlv.getPositionPStatus());
        if (!ValidateUtil.isEmpty(positionlv.getPlCode()))
            wrapper.like("PL_Code", positionlv.getPlCode());
        if (!ValidateUtil.isEmpty(positionlv.getPlName()))
            wrapper.like("PL_Name", positionlv.getPlName());

        if (!CheckNullUtil.checkString(positionlv.getSortType(), positionlv.getSortFiled())) {
            if (positionlv.getSortType().equals("ASC"))
                wrapper.orderByAsc(positionlv.getSortFiled());
            else
                wrapper.orderByDesc(positionlv.getSortFiled());
        } else
            wrapper.orderByAsc("PL_Sort");
        IPage<SysPositionlv> iPage = baseMapper.selectPage(new Page<>(positionlv.getPage(), positionlv.getSize()), wrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }

    @Override
    public BaseResponse GetPositionLVSelect(String teId) {
        QueryWrapper<SysPositionlv> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(teId)) wrapper.eq("Te_Id", teId);
        List<SysPositionlv> list = baseMapper.selectList(wrapper);
        List<Map<String, Object>> data = new ArrayList<>();
        list.forEach(model -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", model.getPlId());
            map.put("name", model.getPlName());
            data.add(map);
        });
        return BaseResponse.ok(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse CreateOrUpdatePositionLV(PositionlvDto positionlv) {
        SysPositionlv pojo;
        String codeRule = null;
        if (!ValidateUtil.isEmpty(positionlv.getPlId()))
            pojo = baseMapper.selectById(positionlv.getPlId());
        else {
            pojo = new SysPositionlv();
            if (sysSequenceSetService.sequenceRestEnable("SysPositionlv", positionlv.getTeId()) == true) {
                positionlv.setPlCode(sysSequenceSetService.generateCodeByTCode("SysPositionlv", positionlv, positionlv.getTeId()));
                if (ValidateUtil.isEmpty(positionlv.getPlCode()))
                    return BaseResponse.fail(BaseMsg.codeGenerateFail);
                codeRule = "SysPositionlv";
            }
        }

        pojo.setPlCode(positionlv.getPlCode());
        pojo.setPositionPStatus(positionlv.getPositionPStatus());
        pojo.setPlSort(positionlv.getPlSort());
        pojo.setPlSolution(positionlv.getPlSolution());
        pojo.setPlRemark(positionlv.getPlRemark());
        pojo.setPlName(positionlv.getPlName());
        pojo.setPlMdmNo(positionlv.getPlMdmNo());
        pojo.setTeId(positionlv.getTeId());
        QueryWrapper<SysPositionlv> wrapper = new QueryWrapper<>();
        wrapper.eq("PL_Code", positionlv.getPlCode());
        if (!ValidateUtil.isEmpty(positionlv.getPlId()))
            wrapper.ne("PL_ID", positionlv.getPlId());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(BaseMsg.infoIsExist);

        if (!ValidateUtil.isEmpty(positionlv.getPlId())) {
            baseMapper.updateById(pojo);
        } else {
            pojo.setPlCreateuId(positionlv.getPlCreateuId());
            pojo.setPlCreateuName(positionlv.getPlCreateuName());
            pojo.setPlId(UUID.randomUUID().toString().replaceAll("-", ""));
            pojo.setPlCreatetime(new Date());
            baseMapper.insert(pojo);
        }
        if (ValidateUtil.isNotEmpty(codeRule))
            sysSequenceSetService.refershAutoNo(codeRule, positionlv, positionlv.getTeId());
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse DeletePositionLV(PositionlvDto positionlv) {
        SysPositionlv pojo = baseMapper.selectById(positionlv.getPlId());
        if (pojo == null)
            return BaseResponse.fail(BaseMsg.infoNotExist);
        baseMapper.deleteById(pojo.getPlId());
        return BaseResponse.ok();
    }
}
