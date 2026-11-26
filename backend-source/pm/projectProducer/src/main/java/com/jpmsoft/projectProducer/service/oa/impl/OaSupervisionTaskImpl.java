package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.Oa.OaSupervisionTaskRequest;
import com.jpmsoft.common.model.response.Oa.OaSupervisionTaskResponse;
import com.jpmsoft.common.model.response.Oa.OaSupervisionTaskStatistics;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.oa.OaSupervisionTaskMapper;
import com.jpmsoft.projectProducer.pojo.entity.oa.OASupervisionTaskDepartment;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaSupervisionTask;
import com.jpmsoft.projectProducer.pojo.entity.other.SysDepartment;
import com.jpmsoft.projectProducer.service.oa.IOaSupervisionTaskDepartmentService;
import com.jpmsoft.projectProducer.service.oa.IOaSupervisionTaskService;
import com.jpmsoft.projectProducer.service.other.ISysDepartmentService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OaSupervisionTaskImpl extends ServiceImpl<OaSupervisionTaskMapper, OaSupervisionTask> implements IOaSupervisionTaskService {

    @Resource
    private IOaSupervisionTaskDepartmentService oASupervisionTaskDepartmentService;
    @Resource
    private ISysDepartmentService sysDepartmentService;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "stId", codeParam = "stCode", method = "setStCode", codeRule = "OaSupervisionTask", beanClass = OaSupervisionTaskRequest.class)
    public BaseResponse createOrUpdateOaSupervisionTask(OaSupervisionTaskRequest request) {
        OaSupervisionTask entity = new OaSupervisionTask();
        if (ValidateUtil.isEmpty(request.getStCode()))
            return BaseResponse.fail(ResMsg.codeIsEmpty);

        BeanUtils.copyProperties(request, entity);
        QueryWrapper<OaSupervisionTask> wrapper = new QueryWrapper<>();
        wrapper.eq("St_Code", request.getStCode());
        if (!ValidateUtil.isEmpty(request.getStId()))
            wrapper.ne("St_Id", request.getStId());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.codeIsRepeat);


        if (ValidateUtil.isEmpty(entity.getStId())) {
            entity.setStCreateTime(new Date());
            entity.setStId(GenerateUUID.getUUID());
            List<OASupervisionTaskDepartment> stdlist = new ArrayList<>();
            if (!ValidateUtil.isEmpty(entity.getStDId())) {
                String[] didlist = entity.getStDId().split(",");
                for (String did : didlist
                ) {
                    OASupervisionTaskDepartment stdobj = new OASupervisionTaskDepartment();
                    stdobj.setStdDid(did);
                    stdobj.setStdId(GenerateUUID.getUUID());
                    stdobj.setStId(entity.getStId());
                    stdlist.add(stdobj);

                }
            }
            oASupervisionTaskDepartmentService.saveBatch(stdlist);
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getStId(), request.gettId(), entity.getStState() + "", entity.getStCreateUid(), entity.getStCreateUname()
                    , entity.getStCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getStId());

        } else {
            OaSupervisionTask model = baseMapper.selectById(request.getStId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getStState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setStState(model.getStState());
            entity.setStStateTime(model.getStStateTime());
            entity.setStCreateTime(model.getStCreateTime());
            entity.setStCreateUid(model.getStCreateUid());
            entity.setStCreateUname(model.getStCreateUname());
            List<OASupervisionTaskDepartment> stdlist = new ArrayList<>();
            if (!ValidateUtil.isEmpty(entity.getStDId())) {
                String[] didlist = entity.getStDId().split(",");
                for (String did : didlist
                ) {
                    OASupervisionTaskDepartment stdobj = new OASupervisionTaskDepartment();
                    stdobj.setStdDid(did);
                    stdobj.setStdId(GenerateUUID.getUUID());
                    stdobj.setStId(entity.getStId());
                    stdlist.add(stdobj);

                }
            }
            QueryWrapper<OASupervisionTaskDepartment> stdwrapper = new QueryWrapper<>();
            stdwrapper.eq("St_Id", entity.getStId());
            List<OASupervisionTaskDepartment> stdoldlist = oASupervisionTaskDepartmentService.list(stdwrapper);
            baseMapper.updateById(entity);
            oASupervisionTaskDepartmentService.saveBatch(stdlist);
            if (stdoldlist.size() > 0) {
                List<String> idlist = stdoldlist.stream().map(n -> n.getStdDid()).collect(Collectors.toList());
                oASupervisionTaskDepartmentService.removeByIds(idlist);
            }
            sysFileService.saveFile(entity.getStId(), request.gettId(), entity.getStState() + "", entity.getStCreateUid(), entity.getStCreateUname()
                    , entity.getStCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getStId());

        }
        return BaseResponse.ok(entity.getStId());
    }

    @Override
    public BaseResponse getOaSupervisionTaskList(OaSupervisionTaskRequest request, String uId) {
//        String authSql = sysUserService.getAuthSqlMainTableOnlyHaveProject(request.getcId(), request.getConstructionControl()
//                , request.getConstructionUnit(), request.getDepartments(), uId, "St_Create_Uid");
//        if (ValidateUtil.isEmpty(authSql))
//            return  BaseResponse.fail(ResMsg.noPermission);
        QueryMap<OaSupervisionTaskRequest> params = new QueryMap<>(request);
//        params.put("authSql", authSql);
        params.put("pId").put("pName").put("stDId").put("stCode");
        List<OaSupervisionTaskResponse> datas = baseMapper.getOaSupervisionTaskList(params.getQuery());
        Integer total = baseMapper.getOaSupervisionTaskListCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getOaSupervisionTaskByStId(String stId) {
        OaSupervisionTaskResponse entity = baseMapper.getOaSupervisionTaskByStId(stId);
        if (!ValidateUtil.isEmpty(entity.getStDId())) {
            String[] ids = entity.getStDId().split(",");
            QueryWrapper<SysDepartment> dwrapper = new QueryWrapper<>();
            dwrapper.in("D_ID", ids);
            List<SysDepartment> dlist = sysDepartmentService.list(dwrapper);
            if (dlist.size() > 0) {
                List<String> dnamelist = dlist.stream().map(n -> n.getdName()).collect(Collectors.toList());
                String d_name = String.join(",", dnamelist);
                entity.setStDName(d_name);
            }
        }
        return BaseResponse.ok(entity);
    }

    @Override
    public BaseResponse exportOaSupervisionTaskStatistics(OaSupervisionTaskRequest request) {
        List<OaSupervisionTaskStatistics> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("year", request.getYear());
        params.put("teId", ValidateUtil.isEmpty(request.getTeId()) ? null : request.getTeId());
        params.put("stDId", ValidateUtil.isEmpty(request.getStDId()) ? null : request.getStDId());
        params.put("stType", ValidateUtil.isEmpty(request.getStType()) ? null : request.getStType());
        params.put("stHandleType", ValidateUtil.isEmpty(request.getStHandleType()) ? null : request.getStHandleType());
        List<OaSupervisionTaskResponse> datas = baseMapper.exportOaSupervisionTaskStatistics(params);

        if (datas != null && datas.size() > 0) {
            Date now = new Date();
            Map<String, List<OaSupervisionTaskResponse>> groupMap = datas.stream().
                    collect(Collectors.groupingBy(n -> n.getStDName() + "&|$" + n.getStType() + "&|$" + n.getStHandleType()));
            result = groupMap.keySet().stream().map(key -> {
                OaSupervisionTaskStatistics model = new OaSupervisionTaskStatistics();
                String[] temp = key.split("&\\|\\$");
                model.setYear(request.getYear());
                model.setStDName(temp[0].equals("null") ? null : temp[0]);
                model.setStType(temp[1]);
                model.setStHandleType(temp[2]);
                List<OaSupervisionTaskResponse> goups = groupMap.get(key);
                model.setTotal(goups.size());
                List<OaSupervisionTaskResponse> inProcess = goups.stream().filter(n -> n.getStState() != null && Objects.equals(n.getStState(), ApprovalStatusEnum.Already_Over.getIndex())).collect(Collectors.toList());
                model.setCompleted(inProcess.size());
                model.setInProcess(model.getTotal() - model.getCompleted());
                //未超时
                List<OaSupervisionTaskResponse> normoal = goups.stream().filter(n ->
                        (Objects.equals(n.getStState(), ApprovalStatusEnum.Already_Over.getIndex()) && n.getStStateTime().getTime() <= n.getStTerm().getTime()) ||
                                (!Objects.equals(n.getStState(), ApprovalStatusEnum.Already_Over.getIndex()) && now.getTime() <= n.getStTerm().getTime())
                ).collect(Collectors.toList());
                model.setNormal(normoal.size());
                //超时完成
                List<OaSupervisionTaskResponse> handled = goups.stream().filter(n -> Objects.equals(n.getStState(), ApprovalStatusEnum.Already_Over.getIndex()) && n.getStStateTime().getTime() > n.getStTerm().getTime()).collect(Collectors.toList());
                model.setHandled(handled.size());
                //超时未完成
                List<OaSupervisionTaskResponse> notHandled = goups.stream().filter(n -> !Objects.equals(n.getStState(), ApprovalStatusEnum.Already_Over.getIndex()) && now.getTime() > n.getStTerm().getTime()).collect(Collectors.toList());
                model.setNotHandled(notHandled.size());
                return model;
            }).collect(Collectors.toList());
        }
        return BaseResponse.ok(result);
    }
}
