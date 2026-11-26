package com.jpmsoft.projectProducer.service.acpm.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.acpm.AcpmInvituoMarginApplicationMapper;
import com.jpmsoft.projectProducer.pojo.dto.acpm.AcpmInvituoMarginApplicationDto;
import com.jpmsoft.projectProducer.pojo.entity.acpm.AcpmInvituoMarginApplication;
import com.jpmsoft.projectProducer.pojo.vo.acpm.AcpmInvituoMarginApplicationVo;
import com.jpmsoft.projectProducer.service.acpm.IAcpmInvituoMarginApplicationService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 15:36
 */
@Service
public class AcpmInvituoMarginApplicationImpl extends ServiceImpl<AcpmInvituoMarginApplicationMapper, AcpmInvituoMarginApplication> implements IAcpmInvituoMarginApplicationService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "id", codeParam = "imaCode", method = "setImaCode", codeRule = "InvituoMarginApplication", beanClass = AcpmInvituoMarginApplicationDto.class)
    public BaseResponse createOrUpdate(AcpmInvituoMarginApplicationDto request) {
        AcpmInvituoMarginApplication entity = new AcpmInvituoMarginApplication();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getId())) {
            Date now = new Date();
            entity.setUpdateTime(now);
            entity.setCreateTime(now);
            entity.setId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getId(), request.gettId(), request.getState() + "", request.getCreateUid(), request.getCreateUname()
                    , entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getId());
        } else {
            AcpmInvituoMarginApplication oldEntity = baseMapper.selectById(request.getId());
            if (ValidateUtil.isNull(oldEntity)) return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setCreateTime(oldEntity.getCreateTime());
            entity.setCreateUid(oldEntity.getCreateUid());
            entity.setCreateUname(oldEntity.getCreateUname());
            entity.setUpdateTime(new Date());
            entity.setState(oldEntity.getState());
            entity.setStateTime(oldEntity.getStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname()
                    , entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getId());
        }
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getList(AcpmInvituoMarginApplicationDto request) {
        QueryMap<AcpmInvituoMarginApplicationDto> params = new QueryMap<>(request);
        setFilterMap(params, request);
        List<AcpmInvituoMarginApplicationVo> datas = baseMapper.getList(params.getQuery());
        Integer total = baseMapper.getCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getById(String id) {
        AcpmInvituoMarginApplicationVo data = baseMapper.getById(id);
        return BaseResponse.ok(data);
    }


    private void setFilterMap(QueryMap<AcpmInvituoMarginApplicationDto> params, AcpmInvituoMarginApplicationDto request) {
        params.put("piaName").put("piaCode").put("piaArea").put("piaType").put("piaDeveloper")
                .put("imaPayeeName").put("imaPayeeBank").put("imaPayeeCaName").put("imaPayeeCa")
                .put("imaRemark").put("imaCode").put("createUname").put("state")
                .put(request.getImaBidBondMoneys(), "imaBidBondMoneyStart", "imaBidBondMoneyEnd");
    }
}
