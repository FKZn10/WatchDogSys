package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceLeaveModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.oa.OaAttendanceLeaveEliminateMapper;
import com.jpmsoft.projectProducer.mapper.oa.OaAttendanceLeaveMapper;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAttendanceLeave;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAttendanceLeaveEliminate;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.service.oa.IOaAttendanceLeaveService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OaAttendanceLeaveImpl extends ServiceImpl<OaAttendanceLeaveMapper, OaAttendanceLeave> implements IOaAttendanceLeaveService {
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private OaAttendanceLeaveEliminateMapper oaAttendanceLeaveEliminateMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateAttendanceLeave(OaAttendanceLeaveModel request) {
        OaAttendanceLeave entity = new OaAttendanceLeave();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getAlId())) {
            entity.setAlId(GenerateUUID.getUUID());
            entity.setAlCreateTime(new Date());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getAlId(), request.gettId(), entity.getAlState() + "", entity.getAlCreateUid(), entity.getAlCreateUname()
                    , entity.getAlCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getAlId());

        } else {
            OaAttendanceLeave model = baseMapper.selectById(entity.getAlId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setAlCreateTime(model.getAlCreateTime());
            entity.setAlCreateUid(model.getAlCreateUid());
            entity.setAlCreateUname(model.getAlCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getAlId(), request.gettId(), entity.getAlState() + "", entity.getAlCreateUid(), entity.getAlCreateUname()
                    , entity.getAlCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getAlId());


        }
        return BaseResponse.ok(entity.getAlId());
    }

    @Override
    public BaseResponse getAttendanceLeaveList(OaAttendanceLeaveModel request) {
        QueryMap<OaAttendanceLeaveModel> params = new QueryMap<>(request);
        params.put("alType").put("alUname").put("alState");
        List<OaAttendanceLeaveModel> datas = baseMapper.getAttendanceLeaveList(params.getQuery());
        if (!CollectionUtils.isEmpty(datas)) {
            QueryWrapper<OaAttendanceLeaveEliminate> wrapper = new QueryWrapper<>();
            wrapper.in("Al_Id", datas.stream().map(OaAttendanceLeaveModel::getAlId).collect(Collectors.toList()));
            List<OaAttendanceLeaveEliminate> list = oaAttendanceLeaveEliminateMapper.selectList(wrapper);
            if (!CollectionUtils.isEmpty(list)) {
                datas.forEach(oaAttendanceLeaveModel -> {
                    List<OaAttendanceLeaveEliminate> oaes = list.stream().filter(n -> n.getAlId().equals(oaAttendanceLeaveModel.getAlId())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(oaes)) {
                        oaAttendanceLeaveModel.setApproveStatus(oaes.get(0).getApproveStatus());
                        oaAttendanceLeaveModel.setEliminateId(oaes.get(0).getObjectId());
                    } else {
                        oaAttendanceLeaveModel.setApproveStatus("0");
                    }
                });
            }
        }
        Integer total = baseMapper.getAttendanceLeaveListCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getAttendanceLeaveById(String alId) {
        OaAttendanceLeaveModel data = baseMapper.getAttendanceLeaveById(alId);
        LambdaQueryWrapper<OaAttendanceLeaveEliminate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OaAttendanceLeaveEliminate::getAlId, data.getAlId());
        List<OaAttendanceLeaveEliminate> list = this.oaAttendanceLeaveEliminateMapper.selectList(wrapper);
        if (list != null && list.size() > 0) {
            data.setApproveStatus(list.get(0).getApproveStatus());
            data.setEliminateId(list.get(0).getObjectId());
        }
        return BaseResponse.ok(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse update(OaAttendanceLeaveModel request) {

        OaAttendanceLeave entity = new OaAttendanceLeave();
        BeanUtils.copyProperties(request, entity);
        OaAttendanceLeave model = baseMapper.selectById(entity.getAlId());
        if (model == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        entity.setAlCreateTime(model.getAlCreateTime());
        entity.setAlCreateUid(model.getAlCreateUid());
        entity.setAlCreateUname(model.getAlCreateUname());
        baseMapper.updateById(entity);

        //消假表
        OaAttendanceLeaveEliminate oaAttendanceLeaveEliminate = new OaAttendanceLeaveEliminate();
        oaAttendanceLeaveEliminate.setAlId(model.getAlId());
        if (ValidateUtil.isNull(request.getEliminateId())) {
            oaAttendanceLeaveEliminate.setObjectId(GenerateUUID.getUUID());
            this.oaAttendanceLeaveEliminateMapper.insert(oaAttendanceLeaveEliminate);
        } else {
            oaAttendanceLeaveEliminate.setObjectId(request.getEliminateId());
        }
        sysFileService.saveFile(oaAttendanceLeaveEliminate.getObjectId(), request.gettId(), oaAttendanceLeaveEliminate.getApproveStatus() + "", entity.getAlCreateUid(), entity.getAlCreateUname()
                , entity.getAlCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                request.getFileRelationId(), null, request.gettCode(), null);
        sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getAlId());
        return BaseResponse.ok(oaAttendanceLeaveEliminate.getObjectId());
    }

    @Override
    public BaseResponse getDetialById(String alId) {
        OaAttendanceLeaveEliminate oaAttendanceLeaveEliminate = this.oaAttendanceLeaveEliminateMapper.selectById(alId);
        OaAttendanceLeaveModel data = new OaAttendanceLeaveModel();
        if (Objects.nonNull(oaAttendanceLeaveEliminate)) {
            data = baseMapper.getAttendanceLeaveById(oaAttendanceLeaveEliminate.getAlId());
        }
        return BaseResponse.ok(data);
    }

    @Transactional(rollbackFor = Exception.class)
    public BaseResponse updateApproveStatus(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return new BaseResponse(500, false, "缺少参数");
        }
        SysFile sysFile = sysFileService.getById(id);
        OaAttendanceLeaveEliminate eliminate = this.oaAttendanceLeaveEliminateMapper.selectById(id);
        if (ValidateUtil.isNull(sysFile) || ValidateUtil.isNull(eliminate)) {
            return new BaseResponse(500, false, "数据有误");
        }
        eliminate.setApproveStatus(sysFile.getFileflowstatus());
        this.oaAttendanceLeaveEliminateMapper.updateById(eliminate);
        return new BaseResponse(true, "操作成功！");
    }
}
