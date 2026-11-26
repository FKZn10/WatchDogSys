package com.jpmsoft.projectProducer.service.peInfo.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.PeInfo.PeInfoRankingRequest;
import com.jpmsoft.common.model.requestAndResponse.PeInfo.PeInfoModel;
import com.jpmsoft.common.model.requestAndResponse.PeInfo.PeInfoRankingModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.common.utils.VoInfoUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.enums.PeStateEnum;
import com.jpmsoft.projectProducer.mapper.peInfo.PeInfoMapper;
import com.jpmsoft.projectProducer.pojo.dto.peinfo.PeInfoUnMeetDto;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeInfo;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.peInfo.IPeInfoDetailService;
import com.jpmsoft.projectProducer.service.peInfo.IPeInfoService;
import com.jpmsoft.projectProducer.service.peInfo.IPeResultService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PeInfoImpl extends ServiceImpl<PeInfoMapper, PeInfo> implements IPeInfoService {

    @Resource
    private IPeInfoDetailService peInfoDetailService;

    @Resource
    private IPeResultService peResultService;

    @Resource
    private ISysFileService sysFileService;

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private IApiUtilsService apiUtilsService;

    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdatePeInfo(PeInfoModel model) {
        PeInfo pojo = new PeInfo();
        BeanUtils.copyProperties(model, pojo);
        if (ValidateUtil.isEmpty(model.getPeId())) {
            pojo.setPeId(GenerateUUID.getUUID());
            pojo.setPeCreateTime(new Date());
            baseMapper.insert(pojo);
            sysFileService.saveFile(pojo.getPeId(), model.gettId(), pojo.getPeState() + "", pojo.getPeCreateUid(), pojo.getPeCreateUname()
                    , pojo.getPeCreateTime(), pojo.getPeProjectId(), model.getFileTitle(), model.getFileCode(), "create",
                    model.getFileRelationId(), null, model.gettCode(), pojo.getPeConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(model.getFileRelationId()), pojo.getPeId());

        } else {
            PeInfo entity = baseMapper.selectById(model.getPeId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            pojo.setPeCreateTime(entity.getPeCreateTime());
            pojo.setPeCreateUid(entity.getPeCreateUid());
            pojo.setPeCreateUname(entity.getPeCreateUname());
            baseMapper.updateById(pojo);
            sysFileService.saveFile(pojo.getPeId(), model.gettId(), pojo.getPeState() + "", pojo.getPeCreateUid(), pojo.getPeCreateUname()
                    , pojo.getPeCreateTime(), pojo.getPeProjectId(), model.getFileTitle(), model.getFileCode(), "update",
                    model.getFileRelationId(), null, model.gettCode(), pojo.getPeConId());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(model.getFileRelationId(), model.getPeReportSeal()),
                    Arrays.asList(model.getFileIds(), model.getPeReportSealIds()), pojo.getPeId());

        }
        peInfoDetailService.createOrUpdatePeInfoDetail(pojo.getPeId(), model.getPeInfoDetails());
        return BaseResponse.ok(pojo.getPeId());
    }

    @Override
    public BaseResponse getPeInfoList(PeInfoModel model, String uId) {
        String authSql = sysUserService.getAuthSql("Pe_Info", uId, "Pe_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Pe_Flow_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        Map<String, Object> params = getListFilter(authSql, model);
        IPage<PeInfoModel> page = new Page<>(model.getPage(), model.getSize());
        List<PeInfoModel> datas = baseMapper.getPeInfoList(page, params);
        List<PeInfoModel> models = new ArrayList<>();
        if (page.getTotal() > 0L) {
            datas.forEach(data -> {
                if (!ValidateUtil.isEmpty(data.getPePmId()))
                    data.setPePmId(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(data.getPePmId())));
                models.add(data);
            });
        }
        return BaseResponse.ok(models, Integer.parseInt(String.valueOf(page.getTotal())));
    }

    @Override
    public BaseResponse getPeInfoListNoAuth(PeInfoModel model) {
        Map<String, Object> params = getListFilter(null, model);
        IPage<PeInfoModel> page = new Page<>(model.getPage(), model.getSize());
        List<PeInfoModel> datas = baseMapper.getPeInfoList(page, params);
        List<PeInfoModel> models = new ArrayList<>();
        if (page.getTotal() > 0L) {
            datas.forEach(data -> {
                if (!ValidateUtil.isEmpty(data.getPePmId()))
                    data.setPePmId(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(data.getPePmId())));
                models.add(data);
            });
        }
        return BaseResponse.ok(models, Integer.parseInt(String.valueOf(page.getTotal())));
    }

    @Override
    public BaseResponse getPeInfoRanking(PeInfoRankingRequest request) {
        List<PeInfoRankingModel> datas = baseMapper.getPeInfoRanking(request);
        Integer count = baseMapper.getPeInfoRankingCount(request);
        return BaseResponse.ok(datas, count);
    }

    @Override
    public BaseResponse getPeInfoByPeId(String peId) {
        PeInfoModel model = baseMapper.getPeInfoByPeId(peId);
        if (model == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        model.setPeInfoDetails(peInfoDetailService.getPeInfoDetailByPeId(peId));
        if (!ValidateUtil.isEmpty(model.getPePmId()))
            model.setPePmId(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(model.getPePmId())));
        return BaseResponse.ok(model);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deletePeInfoByPeId(String peId) {
        PeInfo entity = baseMapper.selectById(peId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        peInfoDetailService.deletePeInfoToDeleteDetail(peId);
        baseMapper.deleteById(peId);
        sysFileService.deleteFileById(peId);
        return BaseResponse.ok();
    }


    @Override
    public BaseResponse updateFlowState(PeInfoModel model) {
        if (JudgeUtil.checkObject(model.getPeId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        PeInfo entity = baseMapper.selectById(model.getPeId());
        SysFile filePojo = sysFileService.getById(model.getPeId());
        if (entity == null || filePojo == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        entity.setPeFlowState(Integer.parseInt(filePojo.getFileflowstatus()));
        entity.setPeFlowStateTime(new Date());
        baseMapper.updateById(entity);
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse updatePeInfoWhenMeetingConfirm(PeInfoModel model) {
        if (JudgeUtil.checkObject(model.getPeId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        PeInfo entity = baseMapper.selectById(model.getPeId());
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        entity.setPeExtraPoints(model.getPeExtraPoints());
        entity.setPeDeductPoints(model.getPeDeductPoints());
        entity.setPeExtraPointsRemark(model.getPeExtraPointsRemark());
        entity.setPeDeductPointsRemark(model.getPeDeductPointsRemark());
        entity.setPeJoin(model.getPeJoin());
        entity.setPeState(model.getPeState());
        entity.setPeFinalGrade(model.getPeFinalGrade());
        entity.setPeFinalScore(model.getPeFinalScore());
        entity.setPeQualityScore(model.getPeQualityScore());
        entity.setPeSafetyScore(model.getPeSafetyScore());
        entity.setPeReportSeal(model.getPeReportSeal());
        baseMapper.updateById(entity);
        if (entity.getPeState().equals(PeStateEnum.Aldeay.getIndex().toString())) {
            peResultService.insertPeResult(entity.getPeId());
        }
        sysFileAnnexService.refreshRelationFile(Arrays.asList(model.getPeReportSeal()),
                Arrays.asList(model.getPeReportSealIds()), model.getPeId());
        return BaseResponse.ok();
    }

    @Override
    public List<PeInfoModel> getPeInfoByMaId(String maId) {
        List<PeInfoModel> datas = new ArrayList<>();
        List<PeInfoModel> models = baseMapper.getPeInfoByMaId(maId);
        if (ValidateUtil.isNotEmpty(models)) {
            datas = models.stream().map(data -> {
                if (!ValidateUtil.isEmpty(data.getPePmId()))
                    data.setPePmId(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(data.getPePmId())));
                return data;
            }).collect(Collectors.toList());
        }
        return datas;
    }

    @Override
    public BaseResponse getPeInfoUnMeetApproval(PeInfoUnMeetDto model, String uId) {

        Map<String, Object> params = new HashMap<>();
        params.put("start", !JudgeUtil.checkObject(model.getPageStart()) ? model.getPageStart() : 0);
        params.put("size", !JudgeUtil.checkObject(model.getSize()) ? model.getSize() == 0 ? 10 : model.getSize() : 10);
        params.put("sortFiled", !ValidateUtil.isEmpty(model.getSortFiled()) ? model.getSortFiled() : null);
        params.put("sortType", !ValidateUtil.isEmpty(model.getSortType()) ? model.getSortType() : null);
        params.put("peConId", !ValidateUtil.isEmpty(model.getPeConId()) ? model.getPeConId() : null);
        params.put("peProjectId", !ValidateUtil.isEmpty(model.getPeProjectId()) ? model.getPeProjectId() : null);
        params.put("peYear", !ValidateUtil.isEmpty(model.getPeYear()) ? model.getPeYear() : null);
        params.put("peQuarter", !ValidateUtil.isEmpty(model.getPeQuarter()) ? model.getPeQuarter() : null);
        params.put("peType", !ValidateUtil.isEmpty(model.getPeType()) ? model.getPeType() : null);
        params.put("peCompanyId", !ValidateUtil.isEmpty(model.getPeCompanyId()) ? model.getPeCompanyId() : null);
        params.put("peConType", !ValidateUtil.isEmpty(model.getPeConType()) ? model.getPeConType() : null);
        params.put("peStage", !ValidateUtil.isEmpty(model.getPeStage()) ? model.getPeStage() : null);
        params.put("peUnitId", !ValidateUtil.isEmpty(model.getPeUnitId()) ? model.getPeUnitId() : null);
        params.put("peJoin", !ValidateUtil.isEmpty(model.getPeJoin()) ? model.getPeJoin() : null);
        params.put("peState", !ValidateUtil.isEmpty(model.getPeState()) ? model.getPeState() : null);
        params.put("peCompetentDepartment", !ValidateUtil.isEmpty(model.getPeCompetentDepartment()) ? model.getPeCompetentDepartment() : null);
        params.put("pecFlowState", !ValidateUtil.isEmpty(model.getPecFlowState()) ? model.getPecFlowState() : null);
        params.put("eliminateLastMeeting", !ValidateUtil.isEmpty(model.getEliminateLastMeeting()) ? model.getEliminateLastMeeting() : null);
        params.put("whetherPage", !ValidateUtil.isEmpty(model.getWhetherPage()) ? model.getWhetherPage() : null);
        params.put("teId", !ValidateUtil.isEmpty(model.getTeId()) ? model.getTeId() : null);
        List<PeInfoModel> datas = baseMapper.getPeInfoUnMeetApproval(params);
        Integer total = baseMapper.getPeInfoUnMeetApprovalCount(params);
        List<PeInfoModel> models = new ArrayList<>();
        if (total != null && total > 0) {
            datas.forEach(data -> {
                if (!ValidateUtil.isEmpty(data.getPePmId()))
                    data.setPePmId(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(data.getPePmId())));
                models.add(data);
            });
        }
        return BaseResponse.ok(models, total);
    }


    private Map<String, Object> getListFilter(String authSql, PeInfoModel model) {
        QueryMap<PeInfoModel> query = new QueryMap<>(model);
        query.put("authSql", authSql).put("pConstructionUnit").put("peCompanyId").put("conType").put("peUnitId")
                .put("pName").put("conName").put("peCompanyName").put("peYear").put("peQuarter").put("peState")
                .put("peFlowState").put("peFlowStates").put("peType").put("peProjectId").put("pBuildUnitName")
                .put("peCreateUname").put("pePm").put("peContractCategory").put("peCompetentDepartment")
                .put(model.getConMoneyTotals(), "conMoneyTotalStart", "conMoneyTotalEnd")
                .put(model.getPeDates(), "peDateStart", "peDateEnd").put("pId")
                .put(model.getPeTeamScores(), "peTeamScoreStart", "peTeamScoreEnd")
                .put(model.getPeCreateTimes(), "peCreateTimeStart", "peCreateTimeEnd");
        return query.getQuery();
    }


    @Override
    public  BaseResponse getPeInfoStatistics(String yearStr){
        Map<String,Object>  data=new HashMap<>();
        data.put("gradeCount",baseMapper. getGradeCount(yearStr));
        data.put("dIdCount",baseMapper.getDIdCount(yearStr));
        data.put("conTypeCount",baseMapper.getConTypeCount( yearStr));
        return BaseResponse.ok(data);
    }

}
