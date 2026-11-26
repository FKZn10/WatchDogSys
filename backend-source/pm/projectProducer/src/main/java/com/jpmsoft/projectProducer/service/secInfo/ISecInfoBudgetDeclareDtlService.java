package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.SecInfo.SecInfoBudgetDeclareDtlDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoBudgetDeclareDtl;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ISecInfoBudgetDeclareDtlService extends IService<SecInfoBudgetDeclareDtl> {


    List<SecInfoBudgetDeclareDtl> getDataList(String pId, String ppId);


    Map<String,Float> getAmountMap(String pId, String ppId);


    List<SecInfoBudgetDeclareDtlDto> getSecInfoBudgetDeclareDtlBySecIds(List<String> secIds);

    void createOrUpdateSecInfoBudgetDeclareDtl(List<SecInfoBudgetDeclareDtl> secInfoBudgetDeclareDtlList);

    void delSecInfoBudgetDeclareDtlByBpId(String bpId);

    List<SecInfoBudgetDeclareDtl> getSecInfoBudgetDeclareDtlByIds(List<String> sibdIdList);


}
