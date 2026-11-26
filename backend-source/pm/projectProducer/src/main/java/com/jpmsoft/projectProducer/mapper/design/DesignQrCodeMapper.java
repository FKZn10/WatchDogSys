package com.jpmsoft.projectProducer.mapper.design;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.Design.DesignQrCodeRequest;
import com.jpmsoft.common.model.request.Design.DesignResultsIssuedRequest;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignQrCodeModel;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignQrCode;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface DesignQrCodeMapper extends BaseMapper<DesignQrCode> {
    DesignQrCodeModel getDesignQrCodeById(@RequestParam("id") String id);

    List<DesignQrCodeModel> getDesignQrCodeList(
            @Param("page") Page<DesignQrCodeRequest> page,
            @Param("params") DesignQrCodeRequest request,
            @Param("authSql") String authSql,
            @Param("ids") List<String> ids);

    List<Map<String, Object>> getAllValues(
            @Param("selectClause") String selectClause,
            @Param("params") DesignQrCodeRequest params,
            @Param("authSql") String authSql,
            @Param("ids") List<String> ids);

    DesignQrCodeModel getDesignQrCodeDateById(
            @Param("id") String id,
            @Param("authSql") String authsql);

    DesignQrCode findLatestByDate(@Param("datePrefix") String datePrefix);
}
