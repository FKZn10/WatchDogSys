package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.FilerecordRequest;
import com.jpmsoft.common.model.request.SysCoordinationProcessRequest;
import com.jpmsoft.common.model.request.UserCoordinationProcessRequest;
import com.jpmsoft.common.model.response.CoordinationProcessResponse;
import com.jpmsoft.jpbpm.mapper.SysCoordinationProcessMapper;
import com.jpmsoft.jpbpm.pojo.entity.SysCoordinationProcess;
import com.jpmsoft.jpbpm.pojo.entity.SysUser;
import com.jpmsoft.jpbpm.service.SysCoordinationProcessService;
import com.jpmsoft.jpbpm.service.SysUserService;
import com.jpmsoft.jpbpm.util.CheckNullUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jpmsoft.common.utils.ValidateUtil;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class SysCoordinationProcessImpl extends ServiceImpl<SysCoordinationProcessMapper, SysCoordinationProcess> implements SysCoordinationProcessService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public BaseResponse havetobereadinglist(FilerecordRequest request) {
        return new BaseResponse();
    }

    @Override
    public BaseResponse getUserCoordinationProcessList(String userId, UserCoordinationProcessRequest request,String customerId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("start", !CheckNullUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !CheckNullUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("sortFiled", !ValidateUtil.isEmpty(request.getSortFiled()) ? request.getSortFiled() : "CP_Time");
        params.put("sortType", !ValidateUtil.isEmpty(request.getSortType()) ? request.getSortType() : "desc");

        params.put("cpName", !ValidateUtil.isEmpty(request.getCpName()) ? request.getCpName() : null);
        params.put("pName", !ValidateUtil.isEmpty(request.getpName()) ? request.getpName() : null);
        params.put("cpActName", !ValidateUtil.isEmpty(request.getCpActName()) ? request.getCpActName() : null);
        params.put("cpFileTitle", !ValidateUtil.isEmpty(request.getCpFileTitle()) ? request.getCpFileTitle() : null);
        params.put("cpSendUser", !ValidateUtil.isEmpty(request.getCpSendUser()) ? request.getCpSendUser() : null);
        params.put("teId", "All".equals(customerId)?null:customerId);
        List<CoordinationProcessResponse> datas = baseMapper.getUserCoordinationProcessList(params);
        Integer total = baseMapper.getUserCoordinationProcessListCount(params);
        return new BaseResponse("获取成功", datas, total);
    }

    @Override
    public BaseResponse getCoordinationProcessList(SysCoordinationProcessRequest request,String customerId) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", !CheckNullUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !CheckNullUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("sortFiled", !ValidateUtil.isEmpty(request.getSortFiled()) ? request.getSortFiled() : "CP_Time");
        params.put("sortType", !ValidateUtil.isEmpty(request.getSortType()) ? request.getSortType() : "desc");

        params.put("cpName", !ValidateUtil.isEmpty(request.getCpName()) ? request.getCpName() : null);
        params.put("pName", !ValidateUtil.isEmpty(request.getpName()) ? request.getpName() : null);
        params.put("cpActName", !ValidateUtil.isEmpty(request.getCpActName()) ? request.getCpActName() : null);
        params.put("cpFileTitle", !ValidateUtil.isEmpty(request.getCpFileTitle()) ? request.getCpFileTitle() : null);
        params.put("cpSendUser", !ValidateUtil.isEmpty(request.getCpSendUser()) ? request.getCpSendUser() : null);
        params.put("cpUser", !ValidateUtil.isEmpty(request.getCpUser()) ? request.getCpUser() : null);
        params.put("teId", "All".equals(customerId)?null:customerId);
        List<CoordinationProcessResponse> datas = baseMapper.getCoordinationProcessList(params);
        Integer total = baseMapper.getCoordinationProcessListCount(params);
        return new BaseResponse("获取成功", datas, total);
    }


    @Override
    public BaseResponse getCoordinationProcessByfileid(FilerecordRequest requst,String customerId) {
        QueryWrapper<SysCoordinationProcess> wrapper = new QueryWrapper<>();
        wrapper.eq("CP_FileID", requst.getEntityid());
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        List<SysCoordinationProcess> Coordination = baseMapper.selectList(wrapper);
        List<String> userid = Coordination.stream().map(n -> n.getCpUser()).collect(Collectors.toList());
        List<SysUser> users = sysUserService.getUserAllByUids(userid,customerId);
        List<CoordinationProcessResponse> newData = new ArrayList<>();
        Coordination.forEach(data -> {

            CoordinationProcessResponse objtask = new CoordinationProcessResponse();
            BeanUtils.copyProperties(data, objtask);
            if (!ValidateUtil.isEmpty(data.getCpUser())) {
                List<SysUser> user = users.stream().filter(n -> n.getuId().equals(data.getCpUser())).collect(Collectors.toList());
                if ( !CollectionUtils.isEmpty(user)) {
                    SysUser u=user.stream().findFirst().get();
                    objtask.setCpUser(u.getuId());
                    objtask.setCpUserName(u.getuName());
                    objtask.setCpUserType(u.getuType());
                }
            }
            newData.add(objtask);
        });
        List<CoordinationProcessResponse> newDataentity = newData.stream().sorted(Comparator.comparing(CoordinationProcessResponse::getCpTime)).collect(Collectors.toList());
        return new BaseResponse("获取数据成功！", newDataentity, newDataentity.size());
    }


    @Override
    public List<SysCoordinationProcess> GetysCoordinationProcessbyFileid(String Fileid,String customerId) {
        QueryWrapper<SysCoordinationProcess> wrapper = new QueryWrapper<>();
        wrapper.eq("CP_FileID", Fileid);
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        return baseMapper.selectList(wrapper);
    }

}
