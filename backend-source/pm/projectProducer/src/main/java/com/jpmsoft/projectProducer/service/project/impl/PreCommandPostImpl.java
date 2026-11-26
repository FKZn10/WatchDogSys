package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.requestAndResponse.Project.PreCommandPostModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.project.PreCommandPostMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.PreCommandPost;
import com.jpmsoft.projectProducer.pojo.vo.project.ComInvestmentSumVo;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.project.IPreCommandPostDtlService;
import com.jpmsoft.projectProducer.service.project.IPreCommandPostService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class PreCommandPostImpl extends ServiceImpl<PreCommandPostMapper, PreCommandPost> implements IPreCommandPostService {
    @Resource
    private ISysFileService sysFileService;

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IPreCommandPostDtlService preCommandPostDtlService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdatePreCommandPost(PreCommandPostModel request) {
        PreCommandPost entity = new PreCommandPost();
        BeanUtils.copyProperties(request, entity);

        if (ValidateUtil.isEmpty(entity.getComId())) {
            entity.setComCreateTime(new Date());
            entity.setComId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getComId(), request.gettId(), entity.getComState() + "", entity.getComCreateUid(), entity.getComCreateUname()
                    , entity.getComCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getComId());

        } else {
            PreCommandPost model = baseMapper.selectById(request.getComId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getComState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setComState(model.getComState());
            entity.setComStateTime(model.getComStateTime());
            entity.setComCreateTime(model.getComCreateTime());
            entity.setComCreateUid(model.getComCreateUid());
            entity.setComCreateUname(model.getComCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getComId(), request.gettId(), entity.getComState() + "", entity.getComCreateUid(), entity.getComCreateUname()
                    , entity.getComCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getComId());

        }
        preCommandPostDtlService.createOrUpdatePreCommandPostDtl(request.getPreCommandPostDtls(), entity.getComId());
        return BaseResponse.ok(entity.getComId());
    }

    @Override
    public BaseResponse getPreCommandPostList(PreCommandPostModel request, String uId) {
        QueryMap<PreCommandPostModel> params = getFilter(request, uId);
        IPage<PreCommandPostModel> page = getList(request, params);
        return BaseResponse.ok(page.getRecords(), (int) page.getTotal());
    }

    @Override
    public BaseResponse getPreCommandPostStatisticsList(PreCommandPostModel request, String uId) {
        QueryMap<PreCommandPostModel> params = getFilter(request, uId);
        IPage<PreCommandPostModel> page = getList(request, params);
        ComInvestmentSumVo data = baseMapper.getComInvestmentSum(params.getQuery());
        if (data == null) data = new ComInvestmentSumVo();
        data.setData(page.getRecords());
        return BaseResponse.ok(data, (int) page.getTotal());
    }


    private QueryMap<PreCommandPostModel> getFilter(PreCommandPostModel request, String uId) {
        String authSql = sysUserService.getAuthSql("Pre_Command_Post", uId, "Com_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Com_State");
        if (ValidateUtil.isEmpty(authSql))
            throw new JPMException(ResMsg.noPermission);
        QueryMap<PreCommandPostModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("pId").put("comOverview")
                .put("comCreateCid").put("comCreateCName").put("comState").put("comCreateUname")
                .put(request.getComCreateTimes(), "comCreateTimeStart", "comCreateTimeEnd");
        return params;
    }

    private IPage<PreCommandPostModel> getList(PreCommandPostModel request, QueryMap<PreCommandPostModel> params) {
        IPage<PreCommandPostModel> page = new Page<>(request.getPage(), request.getSize());
        List<PreCommandPostModel> datas = baseMapper.getPreCommandPostList(page, params.getQuery());
        page.setRecords(datas);
        return page;
    }

    @Override
    public BaseResponse getPreCommandPostById(String comId) {
        PreCommandPostModel data = baseMapper.getPreCommandPostById(comId);
        return BaseResponse.ok(data);
    }
}
