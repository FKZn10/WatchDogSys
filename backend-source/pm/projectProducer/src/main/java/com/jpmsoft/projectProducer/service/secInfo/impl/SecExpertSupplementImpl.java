package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.VoInfoUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecExpertSupplementMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertSupplementDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertSupplement;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecSelectionRecordDtl;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecSupplementRecordDtl;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecExpertSupplementVO;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecSupplementRecordDtlVO;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertSupplementService;
import com.jpmsoft.projectProducer.service.secInfo.ISecSelectionRecordDtlService;
import com.jpmsoft.projectProducer.service.secInfo.ISecSupplementRecordDtlService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/11/27 14:41
 */
@Service
public class SecExpertSupplementImpl extends FileImpl<SecExpertSupplementMapper, SecExpertSupplement, SecExpertSupplementDTO> implements ISecExpertSupplementService {
    @Resource
    private ISecSupplementRecordDtlService secSupplementRecordDtlService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISecSelectionRecordDtlService secSelectionRecordDtlService;

    @Override
    public BaseResponse createOrUpdate(SecExpertSupplementDTO request) {
        SecExpertSupplement entity = new SecExpertSupplement();
        createOrUpdateFileEntity(entity, request);
        secSupplementRecordDtlService.createOrUpdate(request.getSecSupplementRecordDtls(), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse selectList(SecExpertSupplementDTO request) {
        QueryMap<SecExpertSupplementDTO> params = new QueryMap<>(request);
        params.put("srId").put("sevId").put("createUname").put("state")
                .put("pName").put("secName");
        IPage<SecExpertSupplementVO> page = new Page<>(request.getPage(), request.getSize());
        List<SecExpertSupplementVO> data = baseMapper.selectList(page, params.getQuery());
        if (!CollectionUtils.isEmpty(data))
            data.forEach(this::idNumberVo);
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse selectById(String id) {
        return BaseResponse.ok(idNumberVo(baseMapper.selectSupplementById(id)));
    }

    @Override
    public BaseResponse approveAppend(String id) {
        SecExpertSupplement entity = getById(id);
        if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (!Objects.equals(entity.getState(), ApprovalStatusEnum.Already_Over.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        List<SecSupplementRecordDtl> data = secSupplementRecordDtlService.getBySesId(id);
        if (!CollectionUtils.isEmpty(data)) {
            List<SecSelectionRecordDtl> entities = data.stream().map(d -> {
                SecSelectionRecordDtl e = new SecSelectionRecordDtl();
                BeanUtils.copyProperties(d, e);
                e.setSrdId(GenerateUUID.getUUID());
                e.setSrId(entity.getSrId());
                return e;
            }).collect(Collectors.toList());
            secSelectionRecordDtlService.saveBatch(entities);
        }
        return BaseResponse.ok();
    }

    private SecExpertSupplementVO idNumberVo(SecExpertSupplementVO data) {
        if (data != null) {
            if (!CollectionUtils.isEmpty(data.getSecSupplementRecordDtls())) {
                List<SecSupplementRecordDtlVO> rds = data.getSecSupplementRecordDtls().stream().peek(e -> {
                    e.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(e.getExIdNumber())));
                    e.setExHomePhone(VoInfoUtil.phoneHandle(e.getExHomePhone()));
                }).collect(Collectors.toList());
                data.setSecSupplementRecordDtls(rds);
            }
        }
        return data;
    }

}
