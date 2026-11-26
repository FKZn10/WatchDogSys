package com.jpmsoft.jputils.dto;

import lombok.Data;

import java.util.List;

/**
 * @author yb.Sun
 * @date 2025/1/16 21:55
 */
@Data
public class InvoiceInfoDto {
    /**
     * 发票代码
     */
    private String fpdm;

    /**
     * 发票类型
     */
    private String fplx;

    /**
     * 发票号码
     */
    private String fphm;

    /**
     * 开票日期
     */
    private String kprq;

    /**
     * 销售方名称
     */
    private String xfMc;

    /**
     * 销售方纳税人识别号
     */
    private String xfNsrsbh;

    /**
     * 销售方联系方式
     */
    private String xfContact;

    /**
     * 销售方开户行
     */
    private String xfBank;

    /**
     * 购方名称
     */
    private String gfMc;

    /**
     * 购方纳税人识别号"
     */
    private String gfNsrsbh;

    /**
     * 购方联系方式
     */
    private String gfContact;

    /**
     * 购方开户行
     */
    private String gfBank;

    /**
     * 发票校验码
     */
    private String code;

    /**
     * 发票机器码
     */
    private String num;

    /**
     * 是否作废(String 0 未作废|2 已作废|3 红冲发票|7 部分冲红|8 全额冲红)
     */
    private String del;

    /**
     * "是否成品油发票(Boolean true|false)
     */
    private String isOil;

    /**
     * 税额合计
     */
    private String taxamount;

    /**
     * 价格合计
     */
    private String goodsamount;

    /**
     * 价税合计
     */
    private String sumamount;

    /**
     * 数量合计
     */
    private String quantityAmount;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 税局查验次数
     */
    private String queryCount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 详细清单类目
     */
    private List<GoodsData> goodsData ;

    /**
     * 是否含销货清单票(String Y|N)
     */
    private String isGoodsList;

    private GoodsTotalInfo goodsTotalInfo;
    private OcrInfo ocr;

    /**
     * 特殊票种类型
     */
    private String tspzlx;

    /**
     * 票种
     */
    private String pz;

    /**
     *  价税合计（中文大写）
     */
    private String jshjcn;

    /**
     *  业务类型： 0退 1售 2改
     */
    private String ywlx;

    /**
     * 出发时间
     */
    private String cfsj;

    /**
     * 空调特征
     */
    private String kttz;

    /**
     * 车次
     */
    private String cc;

    /**
     * 席别
     */
    private String xb;

    /**
     * 乘车日期
     */
    private String ccrq;

    /**
     * 车厢
     */
    private String cx;

    /**
     *  出发站
     */
    private String cfz;

    /**
     *  电子客票号
     */
    private String dzkph;

    /**
     * 姓名
     */
    private String name;

    /**
     * 证件号
     */
    private String zjh;

    /**
     * 到达站
     */
    private String ddz;

    /**
     * 税率
     */
    private String taxRate;

    /**
     * 席位
     */
    private String xw;

    /**
     * 地区
     */
    private String dq;
}
