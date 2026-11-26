package com.jpmsoft.userProducer.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysLoggerInfoMapper;
import com.jpmsoft.userProducer.pojo.dto.SysLoggerInfoDto;
import com.jpmsoft.userProducer.pojo.entity.*;
import com.jpmsoft.userProducer.pojo.vo.VuePowerTreeVo;
import com.jpmsoft.userProducer.service.*;
import com.jpmsoft.userProducer.utils.CheckNullUtil;
import com.jpmsoft.userProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class SysLoggerInfoImpl extends ServiceImpl<SysLoggerInfoMapper, SysLoggerInfo> implements ISysLoggerInfoService {

    @Value("${system.logSign}")
    private Boolean logSign;
    @Resource
    private ISysConfigEntityService sysConfigEntityService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISysPowerService sysPowerService;
    @Resource
    private ISysInterfaceService sysInterfaceService;

    @Resource
    private ISysFormService iSysFormService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private ISysUserService iSysUserService;


    @Override
    public BaseResponse GetLoggerInfoList(SysLoggerInfoDto entity) {
        QueryWrapper<SysLoggerInfo> queryWrapper = new QueryWrapper<>();
        if (!CheckNullUtil.checkString(entity.getlClientip()))
            queryWrapper.eq("L_ClientIP", entity.getlClientip());
        if (!CheckNullUtil.checkString(entity.getlServerip()))
            queryWrapper.eq("L_ServerIP", entity.getlServerip());
        if (!CheckNullUtil.checkString(entity.getlHttpstatus()))
            queryWrapper.eq("L_HttpStatus", entity.getlHttpstatus());
        if (!CheckNullUtil.checkString(entity.getlActionEnd()))
            queryWrapper.eq("l_action_end", entity.getlActionEnd());

        if (!CheckNullUtil.checkString(entity.getIfId()))
            queryWrapper.eq("if_id", entity.getIfId());
        if (!CheckNullUtil.checkString(entity.getlRequestmode()))
            queryWrapper.eq("L_RequestMode", entity.getlRequestmode());
        if (!ValidateUtil.isEmpty(entity.getlUrl()))
            queryWrapper.like("L_Url", entity.getlUrl());
        if (!ValidateUtil.isEmpty(entity.getlCreateuName()))
            queryWrapper.like("L_CreateU_Name", entity.getlCreateuName());
        if (entity.isUnSuccess())
            queryWrapper.ne("L_HttpStatus", HttpStatus.SC_OK);
        if (!CollectionUtils.isEmpty(entity.getlRequesttimes())) {
            if (entity.getlRequesttimes().size() == 1)
                queryWrapper.gt("L_Requesttime", entity.getlRequesttimes().get(0));
            else {
                if (ValidateUtil.isNotEmpty(entity.getlRequesttimes().get(0)))
                    queryWrapper.gt("L_Requesttime", entity.getlRequesttimes().get(0));
                if (ValidateUtil.isNotEmpty(entity.getlRequesttimes().get(1)))
                    queryWrapper.lt("L_Requesttime", entity.getlRequesttimes().get(1));
            }
        }
        if (!CheckNullUtil.checkString(entity.getSortType(), entity.getSortFiled())) {
            if (entity.getSortType().equals("ASC"))
                queryWrapper.orderByAsc(entity.getSortFiled());
            else
                queryWrapper.orderByDesc(entity.getSortFiled());
        } else
            queryWrapper.orderByDesc("L_RequestTime");
        if (!CheckNullUtil.checkString(entity.getlFunModule())){
            List<String> codes = new ArrayList<>();
            List<VuePowerTreeVo> list;
            BaseResponse powerTreeList = sysPowerService.getPowerTreeList(null, null, null);
            if(powerTreeList.getData() != null){
                JSONArray jsonArray = JSONUtil.parseArray(powerTreeList.getData());
                list = jsonArray.toList(VuePowerTreeVo.class);
            } else {
                list = null;
            }
            if(ValidateUtil.isNotEmpty(list)){
                codes = list.stream().filter( e -> e.getPowerCode().equals(entity.getlFunModule()) && e.getPowerParentid().equals("0"))
                        .flatMap(  e ->{
                            Stream<String> firstLevelStream = Stream.of(e.getPowerCode());
                            Stream<String> secondLevelStream = getSecondLevelPowerCodes(e.getChildren());
                            return Stream.concat(firstLevelStream, secondLevelStream);
                        }).collect(Collectors.toList());
            }
            if(ValidateUtil.isNotEmpty(codes)){
                queryWrapper.in("l_fun_module", codes);
            }else{
                queryWrapper.eq("l_fun_module", entity.getlFunModule());
            }
        }
        IPage<SysLoggerInfo> iPage = baseMapper.selectPage(new Page<>(entity.getPage(), entity.getSize()), queryWrapper);
        Long total = iPage.getTotal();
        List<SysLoggerInfo> data = iPage.getRecords();
        if (!CollectionUtils.isEmpty(data)) {
            List<SysPower> powers=sysPowerService.allPowerList(null);
            List<SysInterface> interfaces=sysInterfaceService.getAll();

            data.forEach(d->{
                if (ValidateUtil.isNotEmpty(d.getlFunModule())) {
                    List<SysPower> ps = powers.stream().filter(f -> f.getPowerCode().equals(d.getlFunModule())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(ps))
                        d.setPowerName(ps.stream().findFirst().get().getPowerName());
                }
                if(ValidateUtil.isNotNull(interfaces)){
                    Optional<SysInterface> matchingInterface = interfaces.stream()
                            .filter(i -> d.getlUrl().replaceAll("//","/").contains(i.getIfUrl()))
                            .findFirst();
                    matchingInterface.ifPresent(i -> d.setInterfaceName(i.getIfName()));
                }
            });
            if (ValidateUtil.isNotNull(logSign) && logSign) {
                InterfaceEncDecDTO ied = sysConfigEntityService.getInterfaceEncDec();
                if (ValidateUtil.isNotNull(ied) && ValidateUtil.isNotEmpty(ied.getLogVerifyUrl())) {
                    data.forEach(d -> {
                        BaseResponse res = apiUtilsService.sendPost(ied.getLogVerifyUrl(), d, null);
                        if (ValidateUtil.isNotNull(res) && ValidateUtil.isNotNull(res.getData())
                                && Boolean.parseBoolean(res.getData().toString())) {
                            d.setSign(null);
                        } else
                            throw new JPMException(BaseMsg.dataVerifyError);
                    });
                }
            }

        }
        return BaseResponse.ok(data, total.intValue());
    }


    @Override
    public BaseResponse DeleteLoggerInfo(SysLoggerInfoDto entity) {
        SysLoggerInfo obj = baseMapper.selectById(entity.getlId());
        if (obj != null) {
            baseMapper.deleteById(entity.getlId());
            return BaseResponse.ok();
        } else {
            return BaseResponse.fail(BaseMsg.infoNotExist);
        }
    }


    @Override
    public BaseResponse insertSysLoggerInfos(SysLoggerInfoDto entity) {
        SysLoggerInfo entityobj = new SysLoggerInfo();
        entityobj.setlUrl(entity.getlUrl());
        entityobj.setlServerip(entity.getlServerip());
        entityobj.setlResponsetime(entity.getlResponsetime());
        entityobj.setlRequesttype(entity.getlRequesttype());
        entityobj.setlRequesttime(entity.getlRequesttime());
        entityobj.setlRequestparameter(entity.getlRequestparameter().toString());
        entityobj.setlRequestmode(entity.getlRequestmode());
        entityobj.setlId(entity.getlId());
        entityobj.setlHttpstatus(entity.getlHttpstatus());
        entityobj.setlContent(entity.getlContent().toString());
        entityobj.setlClientip(entity.getlClientip());
        entityobj.setlActionEnd(entity.getlActionEnd());
        entityobj.setlFunModule(entity.getlFunModule());
        baseMapper.insert(entityobj);
        return BaseResponse.ok();
    }

    private static Stream<String> getSecondLevelPowerCodes(Object children) {
        if (children == null) {
            return Stream.empty();
        }

        List<VuePowerTreeVo> childrenList = JSONUtil.toList(JSONUtil.parseArray(children), VuePowerTreeVo.class);
        if (childrenList == null || childrenList.isEmpty()) {
            return Stream.empty();
        }

        return childrenList.stream().map(child -> child.getPowerCode());
    }


    @Override
    public BaseResponse refreshIfIdAndMode(int size) {
        List<SysLoggerInfo> logs=baseMapper.getAllNull(size);
        List<SysInterface> interfaces=sysInterfaceService.getAll();
        List<SysLoggerInfo> data=new ArrayList<>();
        log.info("查询条数:"+logs.size());
        log.info("接口配置条数:"+interfaces.size());
        for (SysLoggerInfo l : logs) {
            List<SysInterface> ifs=interfaces.stream().filter(f->
                    l.getlUrl().replaceAll("//","/").contains(f.getIfUrl())
            ).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(ifs)) {
                l.setIfId(ifs.stream().findFirst().get().getId());
                l.setlFunModule(ifs.stream().findFirst().get().getIfPower());
            }else {
                l.setlFunModule("未知");
            }
            data.add(l);
        }
        updateBatchById(data);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse importLowInterface(String token) {
         String uid = redisUtils.getUid(token);
         if(ValidateUtil.isEmpty(uid))return BaseResponse.fail(BaseMsg.tokenInvalid);
        SysUser sysUser =  iSysUserService.getById(uid);
        if(ValidateUtil.isEmpty(sysUser))return BaseResponse.fail("用户不存在");
        LambdaQueryWrapper<SysForm> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysForm::getSyDataSourceType,"api");
         List<SysForm> sysForms = iSysFormService.getBaseMapper().selectList(queryWrapper);
         List<SysInterface> createList = new ArrayList<>();
        List<SysInterface> interfaces=sysInterfaceService.refreshRedis();
        for (SysForm sysForm : sysForms) {
             long count = interfaces.stream().filter(e -> e.getIfUrl().replaceAll("//", "/").contains(sysForm.getSyCreateRequestUrl().replaceAll("//","/"))).count();
             if(count <= 0){
                 SysInterface sys = new SysInterface();
                 sys.setId(GenerateUUID.getUUID());
                 sys.setCreateTime(new Date());
                 sys.setCreateUid(sysUser.getuId());
                 sys.setCreateUname(sysUser.getuName());
                 sys.setIfRecord("true");
                 sys.setIfType(sysForm.getSyCreateRequestType());
                 sys.setIfName("新增" + sysForm.getSyName());
                 sys.setIfStatus("01");
                 sys.setIfPower(sysForm.getTCode());
                 sys.setIfUrl(sysForm.getSyCreateRequestUrl());
                 createList.add(sys);
             }
        }
        for (SysForm sysForm : sysForms) {
            long count = interfaces.stream().filter(e -> e.getIfUrl().replaceAll("//", "/").contains(sysForm.getSyCreateRequestUrl().replaceAll("//","/"))).count();
            if(count <= 0){
                SysInterface sys = new SysInterface();
                sys.setId(GenerateUUID.getUUID());
                sys.setCreateTime(new Date());
                sys.setCreateUid(sysUser.getuId());
                sys.setCreateUname(sysUser.getuName());
                sys.setIfRecord("true");
                sys.setIfType(sysForm.getSyDetailsRequestType());
                sys.setIfName("修改" + sysForm.getSyName());
                sys.setIfStatus("01");
                sys.setIfPower(sysForm.getTCode());
                sys.setIfUrl(sysForm.getSyDetailsRequestUrl());
                createList.add(sys);
            }

        }

        sysInterfaceService.saveBatch(createList);

        return BaseResponse.ok();
    }

}
