package com.jpmsoft.common.model.request.SecInfo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 标段采购明细
 *
 * @Author: 李文涛 aaron
 * @date: 2024/1/17 13:44
 */
@Data
public class SecProcurementDetailsDTO implements Serializable {

    private static final long serialVersionUID = -6281838505187767793L;
    private String id;
    private String secId;
    private String pdCatalog;
    private String pdMaterialName;
    private BigDecimal pdPrice;
    private BigDecimal pdNum;
    private Boolean pdImports;
    private String pdBrand;
    private String pdModel;
    private String pdBrandTwo;
    private String pdModelTwo;
    private Boolean pdRiskDevice;
    private String pdProofFile;
    private String pdMain;
    private String pdSalesAsk;
    private String pdOtherFile;
    private String pdOtherImg;
    private String pdServiceContent;
    private String pdCurrency;
    private String pdAaus;
    private Integer pdSort;
    private String pdCatalogName;
    private String pdCountry;

    private String pdProofFileIds;
    private String pdOtherFileIds;
    private String pdOtherImgIds;
}
