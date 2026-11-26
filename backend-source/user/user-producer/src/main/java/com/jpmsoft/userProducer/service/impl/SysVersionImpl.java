package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysVersionMapper;
import com.jpmsoft.userProducer.pojo.dto.SysVersionDto;
import com.jpmsoft.userProducer.pojo.entity.SysVersion;
import com.jpmsoft.userProducer.service.ISysFileAnnexService;
import com.jpmsoft.userProducer.service.ISysVersionService;
import com.jpmsoft.userProducer.utils.CheckNullUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;

@Service
public class SysVersionImpl extends ServiceImpl<SysVersionMapper, SysVersion> implements ISysVersionService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSysVersion(SysVersionDto requset) {
        SysVersion entity = new SysVersion();
        BeanUtils.copyProperties(requset, entity);
        if (ValidateUtil.isEmpty(entity.getvId())) {
            entity.setvId(GenerateUUID.getUUID());
            entity.setvCreateTime(new Date());
            baseMapper.insert(entity);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(entity.getvPackage(), entity.getvSql(), entity.getvRequirements()
                    , entity.getvTestreport(), entity.getvStepdoc(), entity.getvOtherdoc()), entity.getvId());
        } else {
            SysVersion model = baseMapper.selectById(requset.getvId());
            if (model == null) return BaseResponse.fail(BaseMsg.infoNotExist);
            entity.setvCreateTime(model.getvCreateTime());
            entity.setvCreateUname(model.getvCreateUname());
            entity.setvCreateUid(model.getvCreateUid());
            baseMapper.updateById(entity);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(entity.getvPackage(), entity.getvSql(), entity.getvRequirements()
                            , entity.getvTestreport(), entity.getvStepdoc(), entity.getvOtherdoc())
                    , Arrays.asList(requset.getvPackageIds(), requset.getvSqlIds(), requset.getvRequirementsIds()
                            , requset.getvTestreportIds(), requset.getvStepdocIds(), requset.getvOtherdocIds()), entity.getvId());
        }
        return BaseResponse.ok(entity.getvId());
    }

    @Override
    public BaseResponse getSysVersionModelList(SysVersionDto request) {
        QueryWrapper<SysVersion> queryWrapper = new QueryWrapper<>();
        if (!ValidateUtil.isEmpty(request.getvName()))
            queryWrapper.like("V_Name", request.getvName());
        if (!ValidateUtil.isEmpty(request.getvCode()))
            queryWrapper.like("V_Code", request.getvCode());
        if (!ValidateUtil.isEmpty(request.getvContent()))
            queryWrapper.like("V_Content", request.getvContent());
        if (!ValidateUtil.isEmpty(request.getvInvolve()))
            queryWrapper.like("V_Involve", request.getvInvolve());
        if (!ValidateUtil.isEmpty(request.getvCreateUname()))
            queryWrapper.like("V_Create_Uname", request.getvCreateUname());
        if (!CheckNullUtil.checkString(request.getSortType(), request.getSortFiled())) {
            if (request.getSortType().equals("ASC")) {
                queryWrapper.orderByAsc(request.getSortFiled());
            } else {
                queryWrapper.orderByDesc(request.getSortFiled());
            }
        } else
            queryWrapper.orderByDesc("V_Create_Time");
        IPage<SysVersion> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), queryWrapper);
        Long longTotal = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), longTotal.intValue());
    }

    @Override
    public BaseResponse getSysVersionModelById(String vId) {
        SysVersion pojo = baseMapper.selectById(vId);
        if (pojo == null) return BaseResponse.fail(BaseMsg.infoNotExist);
        return BaseResponse.ok(pojo);
    }

    @Override
    public BaseResponse getDatabaseExpiredDate() {
        try {
            return BaseResponse.ok(baseMapper.getDatabaseExpiredDate());
        } catch (Exception ex) {
            log.error("查询达梦到期时间失败！" + ex.getMessage(),ex);
        }
        return BaseResponse.ok();
    }
}
