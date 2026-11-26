package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.model.requestAndResponse.Sms.SysShortMessageLogModel;
import com.jpmsoft.userProducer.mapper.SysShortMessageLogMapper;
import com.jpmsoft.userProducer.pojo.entity.SysShortMessageLog;
import com.jpmsoft.userProducer.service.IApiUtilsService;
import com.jpmsoft.userProducer.service.ISysConfigEntityService;
import com.jpmsoft.userProducer.service.ISysShortMessageLogService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysShortMessageLogImpl extends ServiceImpl<SysShortMessageLogMapper, SysShortMessageLog> implements ISysShortMessageLogService {

    @Resource
    private ISysConfigEntityService sysConfigEntityService;
    @Resource
    private IApiUtilsService apiUtilsService;


    @Override
    public BaseResponse getSysShortMessageLogList(SysShortMessageLogModel request) {
        QueryMap<SysShortMessageLogModel> queryMap = new QueryMap<>(request);
        queryMap.put("smlUname").put("smlMobilePhone").put("unSuccess");
        List<SysShortMessageLogModel> datas = baseMapper.getSysShortMessageLogList(queryMap.getQuery());
        Integer total = baseMapper.getSysShortMessageLogListCount(queryMap.getQuery());
        //密改验证
        if (!CollectionUtils.isEmpty(datas)) {
            InterfaceEncDecDTO encDec = sysConfigEntityService.getInterfaceEncDec();
            if (encDec.getSmsListVerify()) {
                List<SysShortMessageLog> list = baseMapper.selectBatchIds(datas.stream().map(SysShortMessageLogModel::getSmlId).collect(Collectors.toList()));
                list.forEach(d -> {
                    apiUtilsService.signVerify(encDec, encDec.getSmsListVerify(), d, d.getSign());
                });
            }
        }
        return BaseResponse.ok(datas, total);
    }
}
