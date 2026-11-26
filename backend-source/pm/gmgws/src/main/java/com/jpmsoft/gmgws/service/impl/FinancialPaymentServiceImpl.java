package com.jpmsoft.gmgws.service.impl;

import com.alibaba.excel.EasyExcel;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.gmgws.constants.FileMsg;
import com.jpmsoft.gmgws.listener.EasyExcelReadListener;
import com.jpmsoft.gmgws.mapper.FinancialPaymentMapper;
import com.jpmsoft.gmgws.pojo.entity.FinancialPayment;
import com.jpmsoft.gmgws.pojo.entity.SysUser;
import com.jpmsoft.gmgws.pojo.vo.FinancialPaymentVo;
import com.jpmsoft.gmgws.service.FinancialPaymentService;
import com.jpmsoft.gmgws.service.ISysUserService;
import com.jpmsoft.gmgws.utils.extension.FileImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class FinancialPaymentServiceImpl extends FileImpl<FinancialPaymentMapper, FinancialPayment, FinancialPaymentVo> implements FinancialPaymentService {

    @Autowired
    private ISysUserService iSysUserService;
    @Override
    public BaseResponse financeImport(MultipartFile file,String token)  throws IOException {
        if(file == null || file.isEmpty()){
            return BaseResponse.fail(FileMsg.excelNotData);
        }
            InputStream inputStream = file.getInputStream();
            EasyExcelReadListener listener = new EasyExcelReadListener();
            EasyExcel.read(inputStream, FinancialPaymentVo.class, listener).sheet().doRead();
            SysUser sysUser =  iSysUserService.getUserCreator(token);
            if(ValidateUtil.isEmpty(sysUser)){
                return BaseResponse.fail("创建人不存在或登录已失效");
            }
            List<FinancialPaymentVo> list = new ArrayList<>(listener.list);
            List<FinancialPayment> finaList = new ArrayList<>();

            for (FinancialPaymentVo vo : list) {
                FinancialPayment fina = new FinancialPayment();
                vo.setId(GenerateUUID.getUUID());
                vo.setCreateUid(sysUser.getuId());
                vo.setCreateTime(new Date());
                vo.setState(0);
                vo.setCreateUname(sysUser.getuName());
                vo.setFpApplicantName("深圳市光明区建筑工务署");
                vo.setFpApplicantCode("802001");
                vo.setFpAmountApplied(vo.getFpAmountApplied().setScale(2, BigDecimal.ROUND_DOWN));
                vo.setStateTime(new Date());
                BeanUtils.copyProperties(vo,fina);
                finaList.add(fina);
            }
            saveBatch(finaList);
            IntStream.range(0, finaList.size()).parallel().forEach(i -> {
                createOrUpdateFileEntity(finaList.get(i), list.get(i));
            });
        return BaseResponse.ok();
    }
}
