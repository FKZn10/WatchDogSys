package com.jpmsoft.projectProducer.service.qualitySafety.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.Qs.QsRecordDutyDTO;
import com.jpmsoft.common.model.response.Qs.QsRecordResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.qualitySafety.QsPTSpotCheckMapper;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.QsPTSpotCheckDto;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsPTSpotCheck;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsPTSpotCheckRecord;
import com.jpmsoft.projectProducer.pojo.vo.qualitySafety.QsPTSpotCheckRecordVo;
import com.jpmsoft.projectProducer.pojo.vo.qualitySafety.QsPTSpotCheckVo;
import com.jpmsoft.projectProducer.pojo.vo.qualitySafety.QsPlanTaskVo;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsPTSpotCheckRecordService;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsPTSpotCheckService;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsRecordDutyService;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsRecordService;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QsPTSpotCheckImpl extends BaseFileImpl<QsPTSpotCheckMapper, QsPTSpotCheck,QsPTSpotCheckDto> implements IQsPTSpotCheckService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IQsPTSpotCheckRecordService qsPTSpotCheckRecordService;
    @Resource
    private IQsRecordDutyService qsRecordDutyService;
    @Resource
    private IQsRecordService qsRecordService;
    @Resource
    private ISysFileService sysFileService;

    @Override
    public BaseResponse createOrUpdateQsPTSpotCheck(QsPTSpotCheckDto request) {
        if(CollectionUtil.isEmpty(request.getQsPTSpotCheckRecordList()))
            throw new JPMException("抽查清单不能为空!");
        QsPTSpotCheck entity = new QsPTSpotCheck();
        entity = createOrUpdateEntity(entity, request);
        if (ValidateUtil.isEmpty(request.getId()))
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId()), entity.getId());
        else
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId()),
                    Arrays.asList(request.getFileIds()), entity.getId());
        qsPTSpotCheckRecordService.createOrUpdateQsPTSpotCheckRecord(request.getQsPTSpotCheckRecordList(), entity.getId());
        //修改原数据整改状态
        qsPTSpotCheckRecordService.updateRectifyStatus(request.getQsPTSpotCheckRecordList(),"01");
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getQsPTSpotCheckList(QsPTSpotCheckDto request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Create_Uid", OutUserAuthEnum.Organization.getIndex(), "State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        return selectList(request, authSql);
    }

    @Override
    public BaseResponse getQsPTSpotCheckListNoAuth(QsPTSpotCheckDto request) {
        return selectList(request, null);
    }

    public BaseResponse selectList(QsPTSpotCheckDto request, String authSql) {
        QueryMap<QsPTSpotCheckDto> query = new QueryMap<>(request);
        query.put("authSql", authSql).put("pId").put("pName").put("conName").put("ptPmName").put("ptCseName")
                .put("ptType").put("state").put("createUname").put("ptGroupLeaderName").put("ptMeasure")
                .put("ptGroupMemberName").put("ptRectifyName").put("ptTeamLeaderName").put("ptIsConstruction")
                .put("ptContractorName").put("ptSupervisorName").put("ptCode").put("ptCategory")
                .put(request.getPtEvaluationTimes(), "ptEvaluationTimeStart", "ptEvaluationTimeEnd");
        IPage<QsPTSpotCheckDto> page = new Page<>(request.getPage(), request.getSize());
        List<QsPTSpotCheckDto> data = baseMapper.getQsPTSpotCheckList(page, query.getQuery());
        return BaseResponse.ok(data, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getQsPTSpotCheckById(String id) {
        QsPTSpotCheckVo data = baseMapper.getQsPTSpotCheckById(id);
        if (data != null) {
            List<QsPTSpotCheckRecordVo> qsPTSpotCheckRecordList = data.getQsPTSpotCheckRecordList();
            getQsRecordDutyList(qsPTSpotCheckRecordList);
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse approvalData(QsPTSpotCheckDto request) {
        String id = request.getId();
        SysFile fileEntity;
        if (ValidateUtil.isEmpty(id))
            throw new JPMException("缺少请求参数");
        else {
            QsPTSpotCheck entity = baseMapper.selectById(id);
            if (entity == null)
                return BaseResponse.fail(ResMsg.noData);
            fileEntity = sysFileService.getById(id);
            //流程状态 ：0、未发起流程（暂存） 1、审核中 2、退回发起人（审核不通过） 3、流程停止 4、流程已启动 5、流程等待中 6、流程暂停中 7、审核通过 8、流程改变中 9、流程退回
            String fileFlowStatus = fileEntity.getFileflowstatus();

            //如果该条抽查作废，需要将原来的整改状态改回去
            if("3".equals(fileFlowStatus)){
                List<QsPTSpotCheckRecord> qsPTSpotCheckRecordList = qsPTSpotCheckRecordService.getSpotCheckRecordByQsscId(id);
                qsPTSpotCheckRecordService.updateRectifyStatusByIds(qsPTSpotCheckRecordList);
            }

        }
        return  BaseResponse.ok();
    }

    /**
     * 获取质量安全检查记录责任人数据
     * @param qsPTSpotCheckRecordList
     */
    private void getQsRecordDutyList(List<QsPTSpotCheckRecordVo> qsPTSpotCheckRecordList) {
        if (!CollectionUtils.isEmpty(qsPTSpotCheckRecordList)) {
            List<QsRecordDutyDTO> qrds = qsRecordDutyService.getQsRecordDutyByQsrIds(qsPTSpotCheckRecordList.stream().map(QsRecordResponse::getQsrId).collect(Collectors.toList()));
            if (!CollectionUtils.isEmpty(qrds)) {
                qsPTSpotCheckRecordList.forEach(d -> {
                    List<QsRecordDutyDTO> qrd = qrds.stream().filter(f -> f.getQsrId().equals(d.getQsrId())).collect(Collectors.toList());
                    d.setQsRecordDutys(qrd);
                    if (!CollectionUtils.isEmpty(qrd)) {
                        StringBuilder cpyNames = new StringBuilder();
                        StringBuilder userNames = new StringBuilder();
                        for (QsRecordDutyDTO q : qrd) {
                            if (ValidateUtil.isNotEmpty(q.getQrdCpyName())) {
                                if (ValidateUtil.isEmpty(cpyNames))
                                    cpyNames.append(q.getQrdCpyName());
                                else cpyNames.append(",").append(q.getQrdCpyName());
                            }
                            if (ValidateUtil.isNotEmpty(q.getQrdUserName())) {
                                if (ValidateUtil.isEmpty(userNames))
                                    userNames.append(q.getQrdUserName());
                                else userNames.append(",").append(q.getQrdUserName());
                            }
                        }
                        d.setQrdUserNames(userNames.toString());
                        d.setQrdCpyNames(cpyNames.toString());
                    }
                });
            }
        }
    }


}
