package com.jpmsoft.projectProducer.service.other.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.UserTypeEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.BpmFeign;
import com.jpmsoft.projectProducer.mapper.other.OuterUserRegisterMapper;
import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskDto;
import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskListDto;
import com.jpmsoft.projectProducer.pojo.dto.other.OuterRegisterActionUserDTO;
import com.jpmsoft.projectProducer.pojo.dto.other.OuterUserRegisterDTO;
import com.jpmsoft.projectProducer.pojo.entity.other.*;
import com.jpmsoft.projectProducer.service.other.*;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class OuterUserRegisterImpl extends FileImpl<OuterUserRegisterMapper, OuterUserRegister, OuterUserRegisterDTO>
        implements IOuterUserRegisterService {

    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private BpmFeign bpmFeign;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ISysCompanyService sysCompanyService;
    @Resource
    private IOuterUserPositionService outerUserPositionService;

    @Resource
    private ISysPositionUserPService sysPositionUserPService;
    @Resource
    private ISysPositionUserService sysPositionUserService;


    private String getCreateToken(String uid) {
        String token = null;
        Object redisToken = redisUtils.get(uid);
        if (ValidateUtil.isEmpty(redisToken)) {
            token = GenerateUUID.getToekn();
            redisUtils.set(uid, token);
            redisUtils.set(token, uid);
        } else token = redisToken.toString();
        return token;
    }


    @Override
//    @Transactional(rollbackFor = Exception.class)
    public BaseResponse registerUser(OuterUserRegisterDTO request) {
        request.setId(null);
        if (ValidateUtil.isAnObjEmpty(request.getOurAccount(), request.getOurUserName(),
                request.getOurPwd(), request.getOurCreditCode(), request.getOurCompanyName(),
                request.gettCode()))
            return BaseResponse.fail(ResMsg.notReqParam);
        LambdaQueryWrapper<OuterUserRegister> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OuterUserRegister::getOurAccount, request.getOurAccount());
        wrapper.ne(OuterUserRegister::getState, ApprovalStatusEnum.Invalid.getIndex());//非作废的数据
        //判断是否存在非作废的审批数据或者账户已存在
        if (baseMapper.exists(wrapper) || sysUserService.existsAccount(request.getOurAccount()))
            return BaseResponse.fail(ResMsg.outerAccountExist);
        String pwd = apiUtilsService.SelfSm2Decrypt(request.getOurPwd());
        if (ValidateUtil.isEmpty(pwd))
            return BaseResponse.fail(ResMsg.outerPwdError);
        OuterRegisterActionUserDTO actionUser = sysConfigService.getOuterRegisterActionUser();
        if (ValidateUtil.isNull(actionUser))
            return BaseResponse.fail(String.format(ResMsg.notConf, "outerRegisterActionUserInfo"));
        request.setCreateTime(new Date());
        request.setCreateUid(actionUser.getOuterCreateUid());
        request.setCreateUname(actionUser.getOuterCreateUname());
        request.setState(ApprovalStatusEnum.Not_Started.getIndex());
        request.setStateTime(new Date());
        request.setOurUserSalt(GenerateUUID.getUUID());
        request.setOurPwd(apiUtilsService.PasswrodEncryptionResultNoToken(pwd, request.getOurUserSalt(), request.getOurAccount()));
        OuterUserRegister entity = new OuterUserRegister();
        createOrUpdateFileEntity(entity, request);
        //生成代办
        BatchhandlingTaskDto task = new BatchhandlingTaskDto();
        task.setCurrentNode("createNode");
        task.setOnlineConditions("false");
        task.settCode(request.gettCode());
        task.setTeId("All");
        BatchhandlingTaskListDto tkl = new BatchhandlingTaskListDto();
        tkl.setFileId(entity.getId()).setUId(actionUser.getOuterCreateUid()).setProcessed(actionUser.getOuterApproveUid()).setOperator(actionUser.getOuterCreateUid());
        task.setSendList(Arrays.asList(tkl));
        BaseResponse taskRes = bpmFeign.batchSendTask(task, getCreateToken(actionUser.getOuterCreateUid()));
        if (taskRes == null || !taskRes.getSuccess()) throw new JPMException(ResMsg.taskCreateFail);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse approve2CreateUser(String id) {
        OuterUserRegister user = baseMapper.selectById(id);
        if (user == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (!Objects.equals(user.getState(), ApprovalStatusEnum.Already_Over.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        OuterRegisterActionUserDTO actionUser = sysConfigService.getOuterRegisterActionUser();
        if (ValidateUtil.isNull(actionUser))
            return BaseResponse.fail(String.format(ResMsg.notConf, "outerRegisterActionUserInfo"));
        LambdaQueryWrapper<SysCompany> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysCompany::getcCreditcode, user.getOurCreditCode());
        wrapper.eq(SysCompany::getcState, 1);
        List<SysCompany> companyList = sysCompanyService.list(wrapper);
        SysCompany company = null;
        if (!CollectionUtils.isEmpty(companyList)) {
            company = companyList.get(0);
        } else {//单位不存在 创建单位
            company = new SysCompany();
            company.setcId(GenerateUUID.getUUID());
            company.setcCreditcode(user.getOurCreditCode());
            company.setcName(user.getOurCompanyName());
            company.setcType(user.getOurCompanyType());
            company.setcCreatetime(new Date());
            company.setcCreateuId(actionUser.getOuterCreateUid());
            company.setcCreateuName(actionUser.getOuterCreateUname());
            company.setcState("1");
            sysCompanyService.save(company);
        }
        if (sysUserService.existsAccount(user.getOurAccount()))
            return BaseResponse.fail(ResMsg.outerAccountExist);
        //region 用户生成
        SysUser sysUser = new SysUser();
        sysUser.setUId(GenerateUUID.getUUID());
        sysUser.setCId(company.getcId());
        sysUser.setUType(user.getOurUserType());
        sysUser.setUIdcard(user.getOurUserIdcard());
        sysUser.setUPlaintextNum(apiUtilsService.SelfSm2Decrypt(user.getOurUserIdcard()));
        sysUser.setUAccount(user.getOurAccount());
        sysUser.setUPassword(user.getOurPwd());
        sysUser.setUSalt(user.getOurUserSalt());
        sysUser.setUName(user.getOurUserName());
        sysUser.setUSex(user.getOurUserSex());
        sysUser.setUEmail(user.getOurUserEmail());
        sysUser.setUMtel(user.getOurUserPhone());
        sysUser.setUBirthday(user.getOurUserBirthday());
        sysUser.setUCreatetime(new Date());
        sysUser.setUCreateuId(actionUser.getOuterCreateUid());
        sysUser.setUCreateuName(actionUser.getOuterCreateUname());
        sysUser.setUState("1");
        sysUser.setUFirstLogin(0);
        sysUserService.save(sysUser);
        //endregion

        //region 生成岗位关联
        List<OuterUserPosition> positions = outerUserPositionService.selectByOuterId(id);
        if (!CollectionUtils.isEmpty(positions))
            if (sysUser.getUType().equals(UserTypeEnum.Inner_User.getIndex() + "")) {//内部用户
                List<SysPositionUser> userPosition = new ArrayList<>();
                for (OuterUserPosition position : positions) {
                    SysPositionUser p = new SysPositionUser();
                    p.setPuId(GenerateUUID.getUUID());
                    p.setuId(sysUser.getUId());
                    p.setpId(position.getInnerPid());
                    p.setPuDuty(Boolean.FALSE);
                    p.setPuMainDep(Boolean.FALSE);
                    p.setPuMainDuty(Boolean.FALSE);
                    userPosition.add(p);
                }
                sysPositionUserService.saveBatch(userPosition);
            } else {//外部用户
                List<SysPositionUserP> userPosition = new ArrayList<>();
                for (OuterUserPosition position : positions) {
                    SysPositionUserP p = new SysPositionUserP();
                    p.setPupId(GenerateUUID.getUUID());
                    p.setuId(sysUser.getUId());
                    p.setPpId(position.getOuterPid());
                    userPosition.add(p);
                }
                sysPositionUserPService.saveBatch(userPosition);
            }
        //endregion
        return BaseResponse.ok(sysUser.getUId());
    }
}