package com.jpmsoft.projectProducer.service.conBoq.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.ConBoq.ConBoqChangeDto;
import com.jpmsoft.common.model.requestAndResponse.ConBoq.ConBoqDtlChangeDto;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.conBoq.ConBoqChangeMapper;
import com.jpmsoft.projectProducer.pojo.entity.conBoq.ConBoq;
import com.jpmsoft.projectProducer.pojo.entity.conBoq.ConBoqChange;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.conBoq.IConBoqChangeService;
import com.jpmsoft.projectProducer.service.conBoq.IConBoqDtlChangeService;
import com.jpmsoft.projectProducer.service.conBoq.IConBoqService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 合同工程量清单变更主表业务实现
 */
@Service
public class ConBoqChangeServiceImpl extends ServiceImpl<ConBoqChangeMapper, ConBoqChange> implements IConBoqChangeService {
    @Resource
    private IConBoqDtlChangeService conBoqDtlChangeService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private IConBoqService conBoqService;

    /**
     * 新增或修改变更清单主表及子表数据
     *
     * @param request 变更清单数据
     * @return 操作结果
     */
    @Override
    public BaseResponse createOrUpdateConBoqChange(ConBoqChangeDto request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        long count = this.count(Wrappers.<ConBoqChange>lambdaQuery()
                .eq(ConBoqChange::getBoqId, request.getBoqId())
                .ne(ConBoqChange::getBoqcId, request.getBoqcId())
                .ne(ConBoqChange::getBoqcState, ApprovalStatusEnum.Invalid.getIndex())
                .ne(ConBoqChange::getBoqcState, ApprovalStatusEnum.Already_Over.getIndex())
        );
        if (count > 0) {
            return BaseResponse.fail("存在其他未完成的变更清单");
        }
        ConBoqChange change = new ConBoqChange();
        BeanUtils.copyProperties(request, change);
        LogUserInfoVo user = UserUtils.getUserInfo();
        if (ValidateUtil.isEmpty(change.getBoqcId())) {
            change.setBoqcId(GenerateUUID.getUUID());
            change.setBoqcCreateUid(user.getId());
            change.setBoqcCreateUname(user.getName());
            change.setBoqcCreateTime(new Date());
            change.setBoqcUpdateTime(new Date());
            change.setVersion(Math.toIntExact(getVersion(change.getBoqId())));
            this.save(change);
            sysFileService.saveFile(change.getBoqcId(), request.gettId(), change.getBoqcState() + "", change.getBoqcCreateUid(), change.getBoqcCreateUname()
                    , change.getBoqcCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), change.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), change.getBoqcId());
        } else {
            change.setBoqcUpdateTime(new Date());
            this.updateById(change);
            sysFileService.saveFile(change.getBoqcId(), request.gettId(), change.getBoqcState() + "", change.getBoqcCreateUid(), change.getBoqcCreateUname()
                    , change.getBoqcCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), change.getConId());
            sysFileAnnexService.refreshRelationFile(Collections.singletonList(request.getFileRelationId()), Collections.singletonList(request.getFileIds()), change.getBoqcId());
        }
        // 子表处理
        conBoqDtlChangeService.createConBoqDtlChange(request.getDtlList(), user, change.getBoqcId());
        return BaseResponse.ok(change.getBoqcId());
    }

    /**
     * 获取变更清单列表
     *
     * @param request 变更清单数据
     * @return 操作结果
     */
    @Override
    public BaseResponse getConBoqChangeList(ConBoqChangeDto request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        Page<ConBoqChangeDto> page = new Page<>(request.getPage(), request.getSize());
        QueryMap<ConBoqChangeDto> map = new QueryMap<>(request);
        map.put("version").put("boqId").put("boqcVersion").put("pName").put("conName");
        List<ConBoqChangeDto> list = baseMapper.getConBoqChangeList(page, map.getQuery());
        return BaseResponse.ok(list, Math.toIntExact(page.getTotal()));
    }

    /**
     * 根据id获取变更清单主表及子表数据
     *
     * @param id 变更主表ID
     * @return 操作结果
     */
    @Override
    public BaseResponse getConBoqChangeById(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        ConBoqChangeDto change = baseMapper.getConBoqChangeById(id);
        if (ValidateUtil.isNull(change)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        List<ConBoqDtlChangeDto> dtlList = conBoqDtlChangeService.getConBoqDtlChangeList(change.getBoqcId());
        change.setDtlList(dtlList);
        return BaseResponse.ok(change);
    }

    /**
     * 根据id删除变更清单主表及子表数据
     *
     * @param id 变更主表ID
     * @return 操作结果
     */
    @Override
    public BaseResponse deleteConBoqChangeById(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        baseMapper.deleteById(id);
        conBoqDtlChangeService.deleteByBoqcId(id);
        return BaseResponse.ok();
    }

    /**
     * 同步变更清单
     *
     * @param id 变更主表ID
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse syncConBoqChange(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        // 同步主表
        ConBoqChange change = this.getById(id);
        if (!JudgeUtil.isApprovedState(change.getBoqcState())) {
            return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        ConBoq conBoq = new ConBoq();
        conBoq.setBoqId(change.getBoqId());
        conBoq.setConId(change.getConId());
        conBoq.setBoqTitle(change.getBoqcTitle());
        conBoq.setBoqRemark(change.getBoqcRemark());
        conBoq.setBoqVersion(change.getBoqcVersion());
        conBoq.setBoqProductId(change.getBoqcProductId());
        conBoq.setBoqProductName(change.getBoqcProductName());
        conBoq.setBoqContractId(change.getBoqcContractId());
        conBoq.setBoqContractName(change.getBoqcContractName());
        conBoq.setBoqUpdateTime(new Date());
        conBoq.setPId(change.getPId());
        conBoq.setConBoqVersion(change.getConBoqVersion());
        conBoq.setConBoqType(change.getConBoqType());
        conBoq.setRunId(change.getRunId());
        conBoqService.updateById(conBoq);
        SysFile boqFile = sysFileService.getById(change.getBoqId());
        SysFile boqcFile = sysFileService.getById(id);
        // sysFileAnnexService.copyFileAnnex2NewRelation(Collections.singletonList(boqcFile.getFileRelationId()), Collections.singletonList(boqFile.getFileRelationId()), change.getBoqId(), true);
        boqFile.setFileRelationId(boqcFile.getFileRelationId());
        sysFileService.updateById(boqFile);
        // 处理子表
        conBoqDtlChangeService.syncConBoqChangeDtl(id, change.getBoqId());
        return BaseResponse.ok();
    }

    /**
     * 按现有变更记录数生成下一次变更序号。
     *
     * @param boqId 正式清单主表ID
     * @return 下一次变更序号
     */
    private long getVersion(String boqId) {
        return this.count(Wrappers.<ConBoqChange>lambdaQuery().eq(ConBoqChange::getBoqId, boqId)) + 1;
    }
}

