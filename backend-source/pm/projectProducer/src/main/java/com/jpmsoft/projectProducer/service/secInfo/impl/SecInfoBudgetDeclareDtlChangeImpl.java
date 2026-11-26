package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.SecInfo.SecInfoBudgetDeclareDtlChangeRequest;
import com.jpmsoft.common.model.request.SecInfo.SecInfoChangeRequest;
import com.jpmsoft.common.model.response.SecInfo.SecInfoBudgetDeclareDtlChangeRes;
import com.jpmsoft.common.model.response.SecInfo.SecInfoChangeRes;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecInfoBudgetDeclareDtlChangeMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecInfoDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoBudgetDeclareDtl;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoBudgetDeclareDtlChange;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoChange;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.secInfo.ISecInfoBudgetDeclareDtlChangeService;
import com.jpmsoft.projectProducer.service.secInfo.ISecInfoBudgetDeclareDtlService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class SecInfoBudgetDeclareDtlChangeImpl extends ServiceImpl<SecInfoBudgetDeclareDtlChangeMapper, SecInfoBudgetDeclareDtlChange> implements ISecInfoBudgetDeclareDtlChangeService {

    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISecInfoBudgetDeclareDtlService secInfoBudgetDeclareDtlService;

    @Override
    public void createOrUpdateSecInfoBudgetDeclareDtlChange(List<SecInfoBudgetDeclareDtlChangeRequest> datas  ,String uId, String action) {
        if(CollectionUtil.isNotEmpty(datas)){
            if("create".equals(action)){
                 createSecInfoBudgetDeclareDtlChange(datas,uId);
            }else if("edit".equals(action)){
                List<String> sicIdList = datas.stream().map(n -> n.getSicId()).collect(Collectors.toList());
                List<SecInfoBudgetDeclareDtlChangeRes> changeRes = new ArrayList<>();
                if(CollectionUtil.isNotEmpty(sicIdList)){
                    changeRes = getSecInfoBudgetDeclareDtlChangeList(sicIdList);
                }
                List<String> sibdcIds = ValidateUtil.isEmpty(changeRes) ? new ArrayList<>() : changeRes.stream().map(SecInfoBudgetDeclareDtlChangeRes::getSibdcId).collect(Collectors.toList());
                List<String> editIds = datas.stream().filter(n -> !ValidateUtil.isEmpty(n.getSibdcId())).map(SecInfoBudgetDeclareDtlChangeRequest::getSibdcId).collect(Collectors.toList());
                List<SecInfoBudgetDeclareDtlChangeRequest> adds = datas.stream().filter(n -> ValidateUtil.isEmpty(n.getSibdcId()) || !sibdcIds.contains(n.getSibdcId())).collect(Collectors.toList());
                List<SecInfoBudgetDeclareDtlChangeRequest> updates = datas.stream().filter(n -> !ValidateUtil.isEmpty(n.getSibdcId()) && sibdcIds.contains(n.getSibdcId())).collect(Collectors.toList());
                List<String> dels = sibdcIds.stream().filter(n -> !editIds.contains(n)).collect(Collectors.toList());
                if (ValidateUtil.isNotEmpty(dels)) {
                    this.removeBatchByIds(dels);
                }
                createSecInfoBudgetDeclareDtlChange(adds,uId);
                if (ValidateUtil.isNotEmpty(updates)) {
                    List<SecInfoBudgetDeclareDtlChange> upSecs = new ArrayList<>();
                    List<Object> olds = new ArrayList<>();
                    List<Object> news = new ArrayList<>();
                    for (SecInfoBudgetDeclareDtlChangeRequest update : updates) {
                        SecInfoBudgetDeclareDtlChange upSec = new SecInfoBudgetDeclareDtlChange();
                        BeanUtils.copyProperties(update, upSec);
                        upSecs.add(upSec);
                        news.add(upSec);
                        olds.add(changeRes.stream().filter(n -> n.getSibdcId().equals(upSec.getSibdcId())).findFirst().get());
                    }
                    updateBatchById(upSecs);
                    apiUtilsService.insertFiledChangeRecordBath(olds, news, "Sec_Info_Budget_Declare_Dtl_Change", uId);
                }
            }
        }

    }

    private void createSecInfoBudgetDeclareDtlChange(List<SecInfoBudgetDeclareDtlChangeRequest> secInfoBudgetDeclareDtlChangeRequestList, String uId) {

        if(CollectionUtil.isNotEmpty(secInfoBudgetDeclareDtlChangeRequestList)){
            List<Object> olds = new ArrayList<>();
            List<Object> news = new ArrayList<>();
            List<SecInfoBudgetDeclareDtlChange> entitys=new ArrayList<>();
            List<String> sibdIdList = secInfoBudgetDeclareDtlChangeRequestList.stream().map(request -> request.getSibdId()).collect(Collectors.toList());
            List<SecInfoBudgetDeclareDtl> secInfoBudgetDeclareDtls = new ArrayList<>();
            if(CollectionUtil.isNotEmpty(sibdIdList)){
                secInfoBudgetDeclareDtls = secInfoBudgetDeclareDtlService.getSecInfoBudgetDeclareDtlByIds(sibdIdList);
            }
            for (SecInfoBudgetDeclareDtlChangeRequest secInfoBudgetDeclareDtlChangeRequest : secInfoBudgetDeclareDtlChangeRequestList) {
                SecInfoBudgetDeclareDtlChange entity=new SecInfoBudgetDeclareDtlChange();
                BeanUtil.copyProperties(secInfoBudgetDeclareDtlChangeRequest,entity);
                entity.setSibdcId(GenerateUUID.getUUID(entity.getSibdcId()));
                entitys.add(entity);
                if(CollectionUtil.isNotEmpty(secInfoBudgetDeclareDtls)){
                    SecInfoBudgetDeclareDtl secInfoBudgetDeclareDtl = secInfoBudgetDeclareDtls.stream().filter(e -> e.getSibdId().equals(secInfoBudgetDeclareDtlChangeRequest.getSibdId())).findFirst().orElse(null);
                    olds.add(secInfoBudgetDeclareDtl);
                }
                news.add(entity);
            }
            if(CollectionUtil.isNotEmpty(entitys)){
                this.saveBatch(entitys);
            }
            apiUtilsService.insertFiledChangeRecordBath(olds, news, "Sec_Info_Budget_Declare_Dtl_Change", uId);
        }




    }

    @Override
    public List<SecInfoBudgetDeclareDtlChangeRes> getSecInfoBudgetDeclareDtlChangeList(List<String> sicIdList) {
        return baseMapper.getSecInfoBudgetDeclareDtlChangeList(sicIdList);
    }

    @Override
    public void delSecInfoBudgetDeclareDtlChangeBySicId(List<String> sicIdList) {
        if(CollectionUtil.isNotEmpty(sicIdList)){
            baseMapper.delBySicId(sicIdList);
        }
    }

    private void delBySicId(List<String> sicIdList) {
        if(CollectionUtil.isNotEmpty(sicIdList)){
             baseMapper.delBySicId(sicIdList);
        }
    }


}
