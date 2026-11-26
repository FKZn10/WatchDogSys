package com.jpmsoft.projectProducer.service.qualitySafety.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.requestAndResponse.Qs.QsGroundCollapseModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.qualitySafety.QsGroundCollapseMapper;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsGroundCollapse;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsGroundCollapseService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class QsGroundCollapseImpl extends ServiceImpl<QsGroundCollapseMapper, QsGroundCollapse> implements IQsGroundCollapseService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateQsGroundCollapse(QsGroundCollapseModel request) {
        QsGroundCollapse entity = new QsGroundCollapse();
        BeanUtils.copyProperties(request, entity);

        if (ValidateUtil.isEmpty(request.getGcId())) {
            entity.setGcId(GenerateUUID.getUUID());
            entity.setGcCreateTime(new Date());
            entity.setGcStateTime(null);
            entity.setGcState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getGcId(), request.gettId(), entity.getGcState() + "", entity.getGcCreateUid(), entity.getGcCreateUname()
                    , entity.getGcCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getGcId());

        } else {
            QsGroundCollapse model = baseMapper.selectById(request.getGcId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getGcState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setGcCreateTime(model.getGcCreateTime());
            entity.setGcCreateUid(model.getGcCreateUid());
            entity.setGcCreateUname(model.getGcCreateUname());
            entity.setGcState(model.getGcState());
            entity.setGcStateTime(model.getGcStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getGcId(), request.gettId(), entity.getGcState() + "", entity.getGcCreateUid(), entity.getGcCreateUname()
                    , entity.getGcCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getGcId());

        }
        return BaseResponse.ok(entity.getGcId());
    }

    @Override
    public BaseResponse getQsGroundCollapseList(QsGroundCollapseModel request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Gc_Create_Uid", OutUserAuthEnum.NOT.getIndex(), "Gc_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<QsGroundCollapseModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("pNameHistory").put("gcHas").put("pId")
                .put("gcExplain").put("gcRemark").put("gcCreateUname").put("gcState");
        IPage<QsGroundCollapseModel> page = new Page<>(request.getPage(), request.getSize());
        List<QsGroundCollapseModel> datas = baseMapper.getQsGroundCollapseList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getQsGroundCollapseById(String gcId) {
        QsGroundCollapseModel data = baseMapper.getQsGroundCollapseById(gcId);
        return BaseResponse.ok(data);
    }
}
