package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysTPositionMapper;
import com.jpmsoft.userProducer.pojo.dto.TPositionDto;
import com.jpmsoft.userProducer.pojo.entity.SysTPosition;
import com.jpmsoft.userProducer.pojo.vo.SysTPositionVo;
import com.jpmsoft.userProducer.service.ISysTPositionRoleService;
import com.jpmsoft.userProducer.service.ISysTPositionService;
import com.jpmsoft.userProducer.utils.CheckNullUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysTPositionImpl extends ServiceImpl<SysTPositionMapper, SysTPosition> implements ISysTPositionService {


    @Resource
    private ISysTPositionRoleService sysTPositionRoleService;


    @Override
    public BaseResponse GetT_PositionList(TPositionDto request) {
        if (ValidateUtil.isEmpty(request.getTdId()))
            request.setTdId(null);
        if (ValidateUtil.isEmpty(request.getTpName()))
            request.setTpName(null);
        List<SysTPositionVo> models = baseMapper.GetT_PositionList(request.getTdId(), request.getTpName(), request.getSortType(), request.getSortFiled(), request.getToId());
        List<SysTPositionVo> data = models.stream().skip((request.getPage() - 1) * request.getSize()).limit(request.getSize()).collect(Collectors.toList());
        return BaseResponse.ok(data, models.size());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse CreateOrUpdateT_Position(TPositionDto request) {
        SysTPosition pojo;
        if (CheckNullUtil.checkString(request.getTpId()))
            pojo = new SysTPosition();
        else {
            pojo = baseMapper.selectById(request.getTpId());
            if (CheckNullUtil.checkObject(pojo))
                return BaseResponse.fail(BaseMsg.infoNotExist);
        }
        pojo.setTpCode(request.getTpCode());
        pojo.setTpStatus(request.getTpStatus());
        if (!ValidateUtil.isEmpty(request.getTpSort()))
            pojo.setTpSort(Integer.parseInt(request.getTpSort()));
        pojo.setTpRemark(request.getTpRemark());
        pojo.setTpName(request.getTpName());
        pojo.setTdId(request.getTdId());
        if (baseMapper.getPositionCountByTOrgan(request.getTpCode(), request.getTpId(), request.getTdId()) > 0)
            return BaseResponse.fail(BaseMsg.infoIsExist);
        if (CheckNullUtil.checkString(request.getTpId())) {
            pojo.setTpId(GenerateUUID.getUUID());
            baseMapper.insert(pojo);
            sysTPositionRoleService.createTPositionRole(pojo.getTpId(), request.getRoleIds());
        } else {
            sysTPositionRoleService.createTPositionRole(pojo.getTpId(), request.getRoleIds());
            baseMapper.updateById(pojo);
        }
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse DeleteT_Position(TPositionDto request) {
        SysTPosition entity = baseMapper.selectById(request.getTpId());
        if (entity != null) {
            sysTPositionRoleService.deleteTPositionRole(request.getTpId());
            baseMapper.deleteById(request.getTpId());
            return BaseResponse.ok();
        } else
            return BaseResponse.fail(BaseMsg.infoNotExist);
    }

    @Override
    public List<SysTPosition> getTPositionByTdId(String tdId) {
        QueryWrapper<SysTPosition> wrapper = new QueryWrapper<>();
        wrapper.eq("TD_ID", tdId);
        return baseMapper.selectList(wrapper);
    }
}
