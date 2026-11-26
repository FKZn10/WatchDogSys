package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.mapper.SysLoginLogMapper;
import com.jpmsoft.userProducer.pojo.dto.SysLoginLogDto;
import com.jpmsoft.userProducer.pojo.dto.UserDto;
import com.jpmsoft.userProducer.pojo.entity.SysLoginLog;
import com.jpmsoft.userProducer.pojo.entity.SysUser;
import com.jpmsoft.userProducer.pojo.vo.SysLoginLogVo;
import com.jpmsoft.userProducer.service.IApiUtilsService;
import com.jpmsoft.userProducer.service.ISysConfigEntityService;
import com.jpmsoft.userProducer.service.ISysLoginLogService;
import com.jpmsoft.userProducer.utils.DateUtil;
import com.jpmsoft.userProducer.utils.IpObtainUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/15 10:26
 */
@Slf4j
@Service
public class SysLoginLogImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements ISysLoginLogService {

    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISysConfigEntityService sysConfigEntityService;


    @Override
    public void create(SysLoginLog pojo) {
        InterfaceEncDecDTO ied = sysConfigEntityService.getInterfaceEncDec();
        pojo.setSign(apiUtilsService.sign(ied, ied.getLoginLogSign(), pojo));
        baseMapper.insert(pojo);
    }

    @Override
    public void create(HttpServletRequest request, String uId, String loginType, UserDto user) {
        try {
            SysLoginLog pojo = new SysLoginLog();
            pojo.setSllId(GenerateUUID.getUUID());
            pojo.setUId(uId);
            pojo.setSllLoginTime(new Date());
            pojo.setSllLoginType(loginType);
            pojo.setSllIp(IpObtainUtils.getIPAddress(request));
            pojo.setAppDeviceType(user.getAppDeviceType());
            pojo.setAppVersion(user.getAppVersion());
            if (loginType.equalsIgnoreCase("loginout"))
                pojo.setSllAction("02");
            else pojo.setSllAction("01");
            InterfaceEncDecDTO ied = sysConfigEntityService.getInterfaceEncDec();
            pojo.setSign(apiUtilsService.sign(ied, ied.getLoginLogSign(), pojo));
            baseMapper.insert(pojo);
        } catch (Exception ex) {
            log.error("创建登录日志失败！" + ex.getMessage(), ex);
        }
    }

    @Override
    public Long getCount() {
        QueryWrapper<SysLoginLog> wrapper = new QueryWrapper<>();
        return baseMapper.selectCount(wrapper);
    }

    @Override
    public BaseResponse getSysLoginLogList(SysLoginLogDto request) {
        QueryMap<SysLoginLogDto> queryMap = new QueryMap<>(request);
        queryMap.put("uName").put("uAccount").put("sllLoginType").put("sllIp").put("sllAction").put(request.getSllLoginTimes(), "sllLoginTimeStart", "sllLoginTimeEnd");
        List<SysLoginLogVo> datas = baseMapper.getSysLoginLogList(queryMap.getQuery());
        Integer total = baseMapper.getSysLoginLogListCount(queryMap.getQuery());
        if (!CollectionUtils.isEmpty(datas)) {
            //密改验证
            InterfaceEncDecDTO encDec = sysConfigEntityService.getInterfaceEncDec();
            if (encDec.getLoginLogListVerify()) {
                List<SysLoginLog> list = baseMapper.selectBatchIds(datas.stream().map(SysLoginLogVo::getSllId).collect(Collectors.toList()));
                list.forEach(d -> {
                    apiUtilsService.signVerify(encDec, encDec.getLoginLogListVerify(), d, d.getSign());
                });
            }
        }
        return BaseResponse.ok(datas, total);
    }

    @Override
    public Integer getUserUseInterval(SysUser user, String loginType) {
        QueryWrapper<SysLoginLog> wrapper = new QueryWrapper<>();
        wrapper.eq("U_Id", user.getuId());
        if (ValidateUtil.isNotEmpty(loginType))
            wrapper.eq("Sll_Login_Type", loginType);
        wrapper.orderByDesc("Sll_Login_Time");
        wrapper.last("limit 1");
        SysLoginLog log = baseMapper.selectOne(wrapper);
        if (log != null)
            return DateUtil.daysBetweenDay(log.getSllLoginTime(), new Date());
        else
            return DateUtil.daysBetweenDay(user.getuCreatetime(), new Date());
    }
}
