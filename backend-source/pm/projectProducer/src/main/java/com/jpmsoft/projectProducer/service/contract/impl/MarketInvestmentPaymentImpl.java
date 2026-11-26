package com.jpmsoft.projectProducer.service.contract.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.response.pay.ConPayResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.contract.ConPayMapper;
import com.jpmsoft.projectProducer.mapper.contract.MarketInvestmentPaymentMapper;
import com.jpmsoft.projectProducer.pojo.dto.contract.MarketInvestmentPaymentDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.MarketInvestmentPayment;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.service.contract.IMarketInvestmentPaymentService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MarketInvestmentPaymentImpl extends ServiceImpl<MarketInvestmentPaymentMapper, MarketInvestmentPayment> implements IMarketInvestmentPaymentService {


    @Resource
    private ConPayMapper conPayMapper;

    @Resource
    private ISysConfigService sysConfigService;

    @Resource
    private ISysFileService sysFileService;

    @Resource
    private IMarketInvestmentPaymentService marketInvestmentPaymentService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse syncMarketInvestmentPayment() {

        List<ConPayResponse> conPaysToSync = conPayMapper.getConPaySyncMarketInvestmentPayment();
        if (conPaysToSync.isEmpty()) {
            return BaseResponse.ok("暂无新数据需要同步");
        }

        String syncConfig = sysConfigService.getConfigByCode("syncContractPaymentCreator");
        String createUid = null;
        String createUname = null;
        if (StringUtils.hasText(syncConfig)) {
            try {
                JSONObject jsonObject = JSON.parseObject(syncConfig);
                createUid = jsonObject.getString("createUid");
                createUname = jsonObject.getString("createUname");
            } catch (Exception e) {
                log.warn(syncConfig);
                log.error("解析同步创建人配置失败: {}", e);
            }
        }

        List<MarketInvestmentPayment> mips = new ArrayList<>(conPaysToSync.size());
        List<SysFile> sysFiles = new ArrayList<>(conPaysToSync.size());

        String finalCreateUid = createUid;
        String finalCreateUname = createUname;
        Date now = new Date();

        conPaysToSync.forEach(conPay -> {
            String newId = GenerateUUID.getUUID();

            MarketInvestmentPayment mip = new MarketInvestmentPayment();
            mip.setId(newId);
            mip.setPayId(conPay.getPayId());
            mip.setPId(conPay.getpId());
            mip.setConId(conPay.getConId());
            mip.setMipIsCon(conPay.getConId() != null ? 1 : 0);
            mip.setMipConId(conPay.getConId());
            mip.setState(0);
            if("1".equals(conPay.getPayIsHistory())) {
                mip.setState(7);
            }
            mip.setStateTime(now);
            mip.setCreateUid(finalCreateUid);
            mip.setCreateUname(finalCreateUname);
            mip.setCreateTime(now);
            mip.setMipConstructionUnit(conPay.getpConstructionUnitName());
            mip.setMipConsignee(conPay.getPayPayeeName());

            if (conPay.getConMoneyTotal() != null) {
                try {
                    mip.setMipConMoney(new BigDecimal(conPay.getConMoneyTotal()));
                } catch (Exception e) {
                    mip.setMipConMoney(BigDecimal.ZERO);
                }
            }
            mip.setMipConsignee(conPay.getPayPayeeName());
            mip.setMipBank(conPay.getPayPayeeBank());
            mip.setMipAccountNumber(conPay.getPayPayeeCa());

            String payType = conPay.getPayType();
            BigDecimal appliedAmount = ("02".equals(payType) && "0201".equals(conPay.getPayTypeDetailed()))
                    ? conPay.getPayApplyOwner()
                    : conPay.getPayApply();
            mip.setMipAppliedAmount(appliedAmount != null ? appliedAmount : BigDecimal.ZERO);

            mips.add(mip);

            SysFile sysFile = new SysFile();
            sysFile.setFileid(newId);
            sysFile.setFileproject(conPay.getpId());
            sysFile.setFilecontract(conPay.getConId());
            sysFile.setFilecreatedate(now);
            sysFile.setFilecreateuser(finalCreateUid);
            sysFile.setFilecreateusername(finalCreateUname);
            sysFile.setFileflowstatus("0");

            sysFiles.add(sysFile);
        });

        marketInvestmentPaymentService.saveBatch(mips, 1000);

        if (!sysFiles.isEmpty()) {
            sysFileService.saveBatch(sysFiles, 1000);
        }

        return BaseResponse.ok("同步成功，共新增 " + mips.size() + " 条记录");
    }

    @Override
    public BaseResponse getMarketInvestmentPaymentList(MarketInvestmentPaymentDto request) {
        QueryMap<MarketInvestmentPaymentDto> params = new QueryMap<>(request);
        params.put("pId").put("pName").put("conId").put("conName");
        List<MarketInvestmentPaymentDto> list = baseMapper.getMarketInvestmentPaymentList(params.getQuery());
        //此处mysql-plus使用IPage分页弹出优化SQL失败警告
        Integer count = baseMapper.getMarketInvestmentPaymentCount(params.getQuery());
        return BaseResponse.ok(list, count);
    }

}
