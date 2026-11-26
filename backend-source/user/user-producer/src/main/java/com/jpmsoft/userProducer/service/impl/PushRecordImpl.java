package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.PushTypeEnum;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.model.requestAndResponse.PushData.PushRecordModel;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.PushRecordMapper;
import com.jpmsoft.userProducer.pojo.entity.PushRecord;
import com.jpmsoft.userProducer.service.IApiUtilsService;
import com.jpmsoft.userProducer.service.IPushRecordService;
import com.jpmsoft.userProducer.service.ISysConfigEntityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PushRecordImpl extends ServiceImpl<PushRecordMapper, PushRecord> implements IPushRecordService {

    @Resource
    private ISysConfigEntityService sysConfigEntityService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Value("${system.logSign}")
    private Boolean logSign;

    @Override
    public BaseResponse getPushRecordList(PushRecordModel request) {
        boolean fb = sysConfigEntityService.pushRecordTypeFenTable();
        if (fb && ValidateUtil.isEmpty(request.getPrType())) request.setPrType(PushTypeEnum.Task.getkey());
        QueryMap<PushRecordModel> queryMap = new QueryMap<>(request);
        if (fb) {
            queryMap.put("fenTable", true);
        } else
            queryMap.put("fenTable", false);
        queryMap.put("prState").put("prType").put("prMsgId").put("prRequest")
                .put("prDataId").put(request.getPrTimes(), "prTimeStart", "prTimeEnd");
        List<PushRecordModel> datas = baseMapper.getPushRecordList(queryMap.getQuery());
        Integer total = baseMapper.getPushRecordListCount(queryMap.getQuery());
        if (!CollectionUtils.isEmpty(datas) && logSign) {
            InterfaceEncDecDTO encDec = sysConfigEntityService.getInterfaceEncDec();
            datas.forEach(d -> {
                apiUtilsService.signVerify(encDec, logSign, d, d.getSign());
            });
        }
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse rePushDataAgain(String prId) {
        PushRecord entity = baseMapper.selectById(prId);
        if (entity == null)
            return BaseResponse.fail(BaseMsg.infoNotExist);
        return null;
    }
}
