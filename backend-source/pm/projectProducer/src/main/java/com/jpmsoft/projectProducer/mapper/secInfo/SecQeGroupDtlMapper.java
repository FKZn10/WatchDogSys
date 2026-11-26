package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.SecInfo.SecQeGroupDtlResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecQeGroupDtl;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface SecQeGroupDtlMapper extends BaseMapper<SecQeGroupDtl> {

    List<SecQeGroupDtlResponse> getSecQeGroupDtlByQegId(@RequestParam("qegId") String qegId);

    List<SecQeGroupDtlResponse> getSecQeGroupDtlBySecId(@RequestParam("secId") String secId);


}
