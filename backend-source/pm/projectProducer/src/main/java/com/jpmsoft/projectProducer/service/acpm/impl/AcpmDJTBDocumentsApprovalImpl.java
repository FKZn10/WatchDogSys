package com.jpmsoft.projectProducer.service.acpm.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.acpm.AcpmDJTBDocumentsApprovalMapper;
import com.jpmsoft.projectProducer.pojo.dto.acpm.AcpmDJTBDocumentsApprovalDto;
import com.jpmsoft.projectProducer.pojo.entity.acpm.AcpmDJTBDocumentsApproval;
import com.jpmsoft.projectProducer.pojo.vo.acpm.AcpmDJTBDocumentsApprovalVo;
import com.jpmsoft.projectProducer.pojo.vo.acpm.AcpmDJTBSubjectTenderVo;
import com.jpmsoft.projectProducer.service.acpm.IAcpmDJTBDocumentsApprovalService;
import com.jpmsoft.projectProducer.service.acpm.IAcpmDJTBSubjectTenderService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 15:56
 */
@Service
public class AcpmDJTBDocumentsApprovalImpl extends ServiceImpl<AcpmDJTBDocumentsApprovalMapper, AcpmDJTBDocumentsApproval> implements IAcpmDJTBDocumentsApprovalService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IAcpmDJTBSubjectTenderService acpmDJTBSubjectTenderService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "id", codeParam = "daCode", method = "setDaCode", codeRule = "DJTBDocumentsApproval", beanClass = AcpmDJTBDocumentsApprovalDto.class)
    public BaseResponse createOrUpdate(AcpmDJTBDocumentsApprovalDto request) {
        AcpmDJTBDocumentsApproval entity = new AcpmDJTBDocumentsApproval();
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
            AcpmDJTBDocumentsApproval oldEntity = baseMapper.selectById(request.getId());
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
        acpmDJTBSubjectTenderService.createOrUpdate(request.getSubjectTenders(), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getList(AcpmDJTBDocumentsApprovalDto request) {
        QueryMap<AcpmDJTBDocumentsApprovalDto> params = new QueryMap<>(request);
        setFilterMap(params, request);
        List<AcpmDJTBDocumentsApprovalVo> datas = baseMapper.getList(params.getQuery());
        Integer total = baseMapper.getCount(params.getQuery());
        if (ValidateUtil.isNotEmpty(datas)) {
            List<String> daIds = datas.stream().map(AcpmDJTBDocumentsApprovalVo::getId).collect(Collectors.toList());
            List<AcpmDJTBSubjectTenderVo> subjectTenderVos = acpmDJTBSubjectTenderService.getBathByDaIds(daIds);
            if (ValidateUtil.isNotEmpty(subjectTenderVos)) {
                List<AcpmDJTBDocumentsApprovalVo> results = new ArrayList<>();
                results = datas.stream().map(n -> {
                    List<AcpmDJTBSubjectTenderVo> subjectTenders = subjectTenderVos.stream().filter(v -> v.getDaId().equals(n.getId())).collect(Collectors.toList());
                    n.setSubjectTenders(subjectTenders);
                    return n;
                }).collect(Collectors.toList());
                return BaseResponse.ok(results, total);
            }
        }
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getById(String id) {
        AcpmDJTBDocumentsApprovalVo data = baseMapper.getById(id);
        if (ValidateUtil.isNotNull(data)) {
            List<AcpmDJTBSubjectTenderVo> subjectTenders = acpmDJTBSubjectTenderService.getByDaId(id);
            data.setSubjectTenders(subjectTenders);
        }
        return BaseResponse.ok(data);
    }


    private void setFilterMap(QueryMap<AcpmDJTBDocumentsApprovalDto> params, AcpmDJTBDocumentsApprovalDto request) {
        params.put("createUname").put("state").put("daCode").put("piaName").put("piaCode")
                .put("daArea").put("daAuthorizedAgent").put("daAuthorAgentName")
                .put(request.getDaTenderClosingTimes(), "daTenderClosingTimeStart", "aTenderClosingTimeEnd");
    }
}
