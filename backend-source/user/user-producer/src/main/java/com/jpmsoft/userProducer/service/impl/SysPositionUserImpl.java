package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysPositionUserMapper;
import com.jpmsoft.userProducer.pojo.dto.PositionUserDto;
import com.jpmsoft.userProducer.pojo.dto.SysPositionUserDto;
import com.jpmsoft.userProducer.pojo.entity.SysPositionUser;
import com.jpmsoft.userProducer.pojo.vo.SysUserVo;
import com.jpmsoft.userProducer.service.ISysPositionUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

@Service
public class SysPositionUserImpl extends ServiceImpl<SysPositionUserMapper, SysPositionUser> implements ISysPositionUserService {


    @Override
    public BaseResponse CreateOrDeletePU(SysPositionUserDto request) {
        if (request.getType().equalsIgnoreCase("CREATE")) {
            QueryWrapper<SysPositionUser> wrapper = new QueryWrapper<>();
            wrapper.eq("P_ID", request.getPId());
            wrapper.eq("U_ID", request.getUId());
            Long count = baseMapper.selectCount(wrapper);
            if (count > 0L) {
                return BaseResponse.fail(BaseMsg.infoIsExist);
            } else {
                SysPositionUser model = new SysPositionUser();
                BeanUtils.copyProperties(request, model);
                model.setPuId(GenerateUUID.getUUID());
                Integer maxSort = baseMapper.getMaxPuSort(request.getDId());
                model.setPuSort(ValidateUtil.isNull(maxSort) ? 1 : maxSort + 1);
                model.setPuDuty(Boolean.FALSE);
                model.setPuMainDuty(Boolean.FALSE);
                model.setPuMainDep(Boolean.FALSE);
                baseMapper.insert(model);
            }
        } else {
            if (ValidateUtil.isEmpty(request.getPuId())) return BaseResponse.fail(BaseMsg.notReqParam);
            else {
                baseMapper.deleteById(request.getPuId());
            }
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse updatePositionUser(SysPositionUserDto request) {
        SysPositionUser old = baseMapper.selectById(request.getPuId());
        if (ValidateUtil.isNull(old)) return BaseResponse.fail(BaseMsg.infoNotExist);
        SysPositionUser entity = new SysPositionUser();
        BeanUtils.copyProperties(request, entity);
        entity.setPuMainDep(old.getPuMainDep());
        entity.setPuMainDuty(old.getPuMainDuty());
        entity.setPuDuty(old.getPuDuty());
        baseMapper.updateById(entity);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse updateSortUpOrDown(String puId, String dId, String action) {
        SysPositionUser entity = baseMapper.selectById(puId);
        if (ValidateUtil.isNull(entity)) return BaseResponse.fail(BaseMsg.infoNotExist);
        QueryWrapper<SysPositionUser> wrapper = new QueryWrapper<>();
        wrapper.apply(String.format(" P_ID in (select P_ID from SYS_Position  where D_ID='%s' )", dId));
        if (action.equals("up")) {
            wrapper.lt("Pu_Sort", entity.getPuSort());
            wrapper.orderByDesc("Pu_Sort");
        } else {
            wrapper.gt("Pu_Sort", entity.getPuSort());
            wrapper.orderByAsc("Pu_Sort");
        }
        wrapper.last(" limit 1");
        SysPositionUser up = baseMapper.selectOne(wrapper);
        if (ValidateUtil.isNotNull(up)) {
            Integer sort = entity.getPuSort();
            entity.setPuSort(up.getPuSort());
            up.setPuSort(sort);
            updateBatchById(Arrays.asList(entity, up));
        } else return BaseResponse.fail(BaseMsg.notMove);
        return BaseResponse.ok();
    }

    @Override
    public void deletePositionUserByPid(String pId) {
        QueryWrapper<SysPositionUser> wrapper = new QueryWrapper<>();
        wrapper.eq("P_ID", pId);
        baseMapper.delete(wrapper);
    }

    @Override
    public BaseResponse GetUserPositionList(PositionUserDto request) {
        if (ValidateUtil.isEmpty(request.getdId())) request.setdId(null);
        if (ValidateUtil.isEmpty(request.getuName())) request.setuName(null);
        List<SysUserVo> data = baseMapper.GetUserPositionList(request.getdId(), request.getuName(), request.getSortFiled(), request.getSortType(), request.getPageStart(), request.getSize());
        Integer total = baseMapper.GetUserPositionListCount(request.getdId(), request.getuName());
        return BaseResponse.ok(data, total);
    }

    @Override
    public List<String> getUserIdsByDids(List<String> dIds) {
        return baseMapper.getUserIdsByDids(dIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse setMainDep(String puId, Boolean enable) {
        SysPositionUser entity = baseMapper.selectById(puId);
        if (ValidateUtil.isNull(entity)) return BaseResponse.fail(BaseMsg.infoNotExist);
        if (enable == Boolean.TRUE) baseMapper.disableOtherMainDep(entity.getuId());
        entity.setPuMainDep(enable);
        baseMapper.updateById(entity);
        return BaseResponse.ok(puId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse setMainDuty(String puId, String dId, Boolean enable) {
        SysPositionUser entity = baseMapper.selectById(puId);
        if (ValidateUtil.isNull(entity)) return BaseResponse.fail(BaseMsg.infoNotExist);
        if (enable == Boolean.TRUE) {
            List<String> oldMain = baseMapper.getCurrentDepAllMainDuty(dId);
            if (!CollectionUtils.isEmpty(oldMain))
                baseMapper.disableMainDuty(oldMain);
            entity.setPuDuty(enable);
        }
        entity.setPuMainDuty(enable);
        baseMapper.updateById(entity);
        return BaseResponse.ok(puId);
    }

    @Override
    public BaseResponse setDuty(String puId, Boolean enable) {
        SysPositionUser entity = baseMapper.selectById(puId);
        if (ValidateUtil.isNull(entity)) return BaseResponse.fail(BaseMsg.infoNotExist);
        entity.setPuDuty(enable);
        baseMapper.updateById(entity);
        return BaseResponse.ok(puId);
    }
}
