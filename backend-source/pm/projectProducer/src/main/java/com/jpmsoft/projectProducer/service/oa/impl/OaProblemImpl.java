package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaProblemModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.oa.OaProblemMapper;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaProblem;
import com.jpmsoft.projectProducer.service.oa.IOaProblemDtlService;
import com.jpmsoft.projectProducer.service.oa.IOaProblemService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 项目问题登记表
 */
@Service
public class OaProblemImpl extends ServiceImpl<OaProblemMapper, OaProblem> implements IOaProblemService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private IOaProblemDtlService oaProblemDtlService;

    @Resource
    private ISysUserService sysUserService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateOaProblem(OaProblemModel request) {
        OaProblem entity = new OaProblem();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getPmId())) {
            entity.setPmCreateTime(new Date());
            entity.setPmId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getPmId(), request.gettId(), entity.getPmState() + "", entity.getPmCreateUid(), entity.getPmCreateUname()
                    , entity.getPmCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getPmId());

        } else {
            OaProblem model = baseMapper.selectById(request.getPmId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getPmState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setPmState(model.getPmState());
            entity.setPmStateTime(model.getPmStateTime());
            entity.setPmCreateTime(model.getPmCreateTime());
            entity.setPmCreateUid(model.getPmCreateUid());
            entity.setPmCreateUname(model.getPmCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getPmId(), request.gettId(), entity.getPmState() + "", entity.getPmCreateUid(), entity.getPmCreateUname()
                    , entity.getPmCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getPmId());

        }
        oaProblemDtlService.createOrUpdateOaProblemDtl(request.getOaProblemDtls(), entity.getPmId());
        return BaseResponse.ok(entity.getPmId());
    }

    @Override
    public BaseResponse getOaProblemList(OaProblemModel request, String uId) {
        String authSql = sysUserService.getAuthSql("OA_Problem", uId, "Pm_Create_Uid", OutUserAuthEnum.NOT.getIndex(), "Pm_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<OaProblemModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("conName").put("pId")
                .put("conId").put("secId").put("secName").put("pmTitle")
                .put("pmCode").put("pmRemark").put("pmState").put("pmCreateUid");
        IPage<OaProblemModel> page = new Page<>(request.getPage(), request.getSize());
        List<OaProblemModel> datas = baseMapper.getOaProblemList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getOaProblemById(String pmId) {
        OaProblemModel data = baseMapper.getOaProblemById(pmId);
        return BaseResponse.ok(data);
    }
}
