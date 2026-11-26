package com.jpmsoft.projectProducer.service.materialsEquipment.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.MeEnter.MeDetectionGuideModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.materialsEquipment.MeDetectionGuideMapper;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeDetectionGuide;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeDetectionGuideService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MeDetectionGuideImpl extends ServiceImpl<MeDetectionGuideMapper, MeDetectionGuide> implements IMeDetectionGuideService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateMeDetectionGuide(MeDetectionGuideModel model) {
        MeDetectionGuide entity = new MeDetectionGuide();
        BeanUtils.copyProperties(model, entity);
        if (ValidateUtil.isEmpty(model.getDgId())) {
            entity.setDgCreateTime(new Date());
            entity.setDgId(GenerateUUID.getUUID());
            entity.setHaveReport(2);
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getDgId(), model.gettId(), entity.getDgState() + "", entity.getDgCreateUid(), entity.getDgCreateUname()
                    , entity.getDgCreateTime(), model.getpId(), model.getFileTitle(), model.getFileCode(),
                    "create", model.getFileRelationId(), null, model.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(model.getFileRelationId(), entity.getDgId());

        } else {
            MeDetectionGuide data = baseMapper.selectById(model.getDgId());
            if (data == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(data.getDgState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setDgState(data.getDgState());
            entity.setDgStateTime(data.getDgStateTime());
            entity.setDgCreateTime(data.getDgCreateTime());
            entity.setDgCreateUid(data.getDgCreateUid());
            entity.setDgCreateUname(data.getDgCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getDgId(), model.gettId(), entity.getDgState() + "", entity.getDgCreateUid(), entity.getDgCreateUname()
                    , entity.getDgCreateTime(), model.getpId(), model.getFileTitle(), model.getFileCode(), "update",
                    model.getFileRelationId(), null, model.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(model.getFileRelationId(), model.getFileIds(), entity.getDgId());

        }
        return BaseResponse.ok(entity.getDgId());
    }

    @Override
    public BaseResponse getMeDetectionGuideList(MeDetectionGuideModel model, String uId) {
//        if (sysUserService.checkIsOutUser(uId))
//            return BaseResponse.fail(ResMsg.noPermission);
        Map<String, Object> params = new HashMap<>();
        params.put("authSql", sysUserService.onlyShowCancel(uId, "Dg_Create_Uid", "Dg_State"));
        params.put("sortFiled", ValidateUtil.isEmpty(model.getSortFiled()) ? "Dg_Create_Time" : model.getSortFiled());
        params.put("sortType", ValidateUtil.isEmpty(model.getSortType()) ? "DESC" : model.getSortType());
        params.put("start", !JudgeUtil.checkObject(model.getPageStart()) ? model.getPageStart() : 0);
        params.put("size", !JudgeUtil.checkObject(model.getSize()) ? model.getSize() == 0 ? 10 : model.getSize() : 10);

        params.put("teId", ValidateUtil.isEmpty(model.getTeId()) ? null : model.getTeId());
        params.put("dgType", ValidateUtil.isEmpty(model.getDgType()) ? null : model.getDgType());
        params.put("dgProductCategory", ValidateUtil.isEmpty(model.getDgProductCategory()) ? null : model.getDgProductCategory());
        params.put("dgProduct", ValidateUtil.isEmpty(model.getDgProduct()) ? null : model.getDgProduct());
        params.put("dgItem", ValidateUtil.isEmpty(model.getDgItem()) ? null : model.getDgItem());
        params.put("haveReport", ValidateUtil.isEmpty(model.getHaveReport()) ? null : model.getHaveReport());
        List<MeDetectionGuideModel> datas = baseMapper.getMeDetectionGuideList(params);
        Integer total = baseMapper.getMeDetectionGuideListCount(params);
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getMeDetectionGuideByDgId(String dgId) {
        MeDetectionGuideModel data = baseMapper.getMeDetectionGuideModelByDgId(dgId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse approvalData(String dgId) {
        MeDetectionGuide data = baseMapper.selectById(dgId);
        if(data==null){
            return BaseResponse.fail(ResMsg.noData);
        }
        data.setHaveReport(1);
        baseMapper.updateById(data);
        return  BaseResponse.ok();
    }
    @Override
    public BaseResponse setMeDetectionGuideData(String dgId) {
        MeDetectionGuide data = baseMapper.selectById(dgId);
        if(data==null){
            return BaseResponse.fail(ResMsg.noData);
        }
        data.setHaveReport(2);
        baseMapper.updateById(data);
        return  BaseResponse.ok();
    }
}
