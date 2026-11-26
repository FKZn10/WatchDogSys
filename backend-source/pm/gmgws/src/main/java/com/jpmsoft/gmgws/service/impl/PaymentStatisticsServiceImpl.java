package com.jpmsoft.gmgws.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.gmgws.constants.FileMsg;
import com.jpmsoft.gmgws.feign.DictionaryFeign;
import com.jpmsoft.gmgws.listener.EasyExcelReadListener;
import com.jpmsoft.gmgws.mapper.PaymentStatisticsMapper;
import com.jpmsoft.gmgws.pojo.dto.SelectDto;
import com.jpmsoft.gmgws.pojo.entity.*;
import com.jpmsoft.gmgws.pojo.vo.PaymentStatisticsFileVo;
import com.jpmsoft.gmgws.pojo.vo.PaymentStatisticsVo;
import com.jpmsoft.gmgws.service.*;
import com.jpmsoft.gmgws.utils.extension.FileImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class PaymentStatisticsServiceImpl extends FileImpl<PaymentStatisticsMapper,PaymentStatistics, PaymentStatisticsFileVo> implements PaymentStatisticsService {

    @Autowired
    private PreProjectService preProjectService;

    @Autowired
    private ConContractService conContractService;

    @Autowired
    private IFileTemplateService fileTemplateService;

    @Autowired
    private ISysUserService iSysUserService;

    @Autowired
    private ISysCompanyService iSysCompanyService;

    @Autowired
    private DictionaryFeign dictionaryFeign;

    @Autowired
    private ISysConfigService iSysConfigService;




    @Override
    public BaseResponse importExcel(MultipartFile file) throws IOException {
        if(file == null || file.isEmpty()){
            return BaseResponse.fail(FileMsg.excelNotData);
        }

        InputStream inputStream = file.getInputStream();
        EasyExcelReadListener listener = new EasyExcelReadListener();
        EasyExcel.read(inputStream, PaymentStatisticsVo.class, listener).sheet().doRead();
        log.info("解析出来的数据:{}", JSONUtil.toJsonStr(listener.list));
        String errMsg = "";  //失败信息
        if(!listener.list.isEmpty()){
            int index = 1;
            List<PaymentStatisticsVo> list = new ArrayList<>(listener.list);
            for (PaymentStatisticsVo payment : list) {  //过滤项目名, 合同名,单位名
                payment.setEntryName(replaceSymbols(payment.getEntryName()));
                payment.setContractName(replaceSymbols(payment.getContractName()));
                payment.setPaymentCompany(replaceSymbols(payment.getPaymentCompany()));
            }
            List<String> pNames = list.stream().map(PaymentStatisticsVo::getEntryName).collect(Collectors.toList());
            List<String> collectNames = list.stream().map(PaymentStatisticsVo::getContractName).collect(Collectors.toList());
            List<PreProject> projects =  preProjectService.getProjectMame(pNames);
            List<SysUser> sysUser =  iSysUserService.getUser(list.stream().map(PaymentStatisticsVo::getCreatePeople).collect(Collectors.toList()));
            FileTemplate fileTemplate =  fileTemplateService.getTemplate("ConstructionPS");
            List<SysCompany> panyList = iSysCompanyService.getComPany();
            List<PaymentStatistics> paymentLists = baseMapper.selectList(null);

            SelectDto selectDto=new SelectDto();
            selectDto.setCodes(Arrays.asList("JPMsoft_DJZFLX"));//支付类型
            BaseResponse baseResponse = dictionaryFeign.getDictionaryTreeByCodes(selectDto);
            LinkedHashMap<String, List<LinkedHashMap<String, Object>>> originalData = (LinkedHashMap<String, List<LinkedHashMap<String, Object>>>) baseResponse.getData();
            List<LinkedHashMap<String, Object>> codeList = originalData.get("JPMsoft_DJZFLX");
            List<PaymentStatistics> dataList = new LinkedList<>(); //存储成功检查成功的数据
            List<PaymentStatisticsFileVo> fileVoList = new LinkedList<>(); //存储流程数据
            if(ValidateUtil.isEmpty(sysUser)){
                return BaseResponse.fail("创建人信息不存在");
            }
            List<ConContract> contractList = new ArrayList<>();
            if(ValidateUtil.isNotEmpty(projects)){
                List<String> collect = projects.stream().map(PreProject::getPId).collect(Collectors.toList());
                contractList = conContractService.getConName(collectNames, collect);
            }
            for (PaymentStatisticsVo vo : list) {
                List<PreProject> pNameList = projects.stream().filter(e -> e.getPName().equals(vo.getEntryName())).collect(Collectors.toList());
                if(ValidateUtil.isNotEmpty(pNameList)){
                    List<ConContract> conNameList = contractList.stream().filter(e -> e.getConName().equals(vo.getContractName())).filter(e->e.getConApprovalState().equals(7)).collect(Collectors.toList());
                    if(ValidateUtil.isNotEmpty(conNameList)){
                        if(conNameList.size() > 1){
                            errMsg += "第" +  index +"行, 项目名:" + vo.getEntryName() + "对应的合同名:"+vo.getContractName() + "有多条,导入失败;";
                            index++;
                        }else{
                            List<PreProject> preList = pNameList.stream().filter(e -> e.getPId().equals(conNameList.get(0).getConProjectId())).collect(Collectors.toList());
                            if(ValidateUtil.isNotEmpty(preList)){
                                if (preList.size() > 1){
                                    errMsg += "第" +  index +"行, 合同名:" + vo.getContractName() + " 项目名:"+vo.getEntryName() + "存在多个匹配,导入失败;";
                                    index++;
                                }else{
                                    Long count = paymentLists.stream().filter(e -> e.getPId().equals(preList.get(0).getPId()))
                                            .filter(e -> e.getConId().equals(conNameList.get(0).getConId())).count();
                                    if(count > 0){
                                        errMsg += "第" +  index +"行, 合同名:" + vo.getContractName() + " 项目名:"+vo.getEntryName() + "已存在,导入失败;";
                                    }
                                    List<SysUser> userList = sysUser.stream().filter(e -> e.getuName().equals(vo.getCreatePeople())).collect(Collectors.toList());
                                    if(ValidateUtil.isEmpty(userList)){
                                        errMsg += "第" +  index +"行" + " 用户:" + vo.getCreatePeople() + "不存在;";
                                        index++;
                                    }else{
                                        PaymentStatistics cs = new PaymentStatistics();
                                        //做只保留两位小数处理
                                        cs.setId(GenerateUUID.getUUID());
                                        cs.setState(7);
                                        cs.setStateTime(new Date());
                                        cs.setCreateUid( userList.get(0).getuId());
                                        cs.setCreateUname( userList.get(0).getuName());
                                        cs.setPId(preList.get(0).getPId());
                                        cs.setConId(conNameList.get(0).getConId());
                                        cs.setConCode(conNameList.get(0).getConCode());
                                        cs.setPsPaymentAmt(vo.getPaymentAmt().setScale(2, BigDecimal.ROUND_DOWN));
                                        cs.setPsApprovalAmt(vo.getApprovalAmt().setScale(2,BigDecimal.ROUND_DOWN));
                                        List<SysCompany> collect = panyList.stream().filter(e -> e.getcName().equals(vo.getPaymentCompany())).collect(Collectors.toList());
                                        if (ValidateUtil.isNotEmpty(collect)){
                                            cs.setPsPaymentCompany(collect.get(0).getcId());
                                        }
                                        cs.setPsPaymentType(getValueByTitle(codeList,vo.getPaymentType()));
                                        cs.setPsPaymentYear(vo.getPaymentYear());
                                        cs.setPsRemark(vo.getMrk());
                                        cs.setCreateTime(new Date());
                                        dataList.add(cs); //存储成功数据
                                        PaymentStatisticsFileVo fileVo = new PaymentStatisticsFileVo();
                                        fileVo.setId(cs.getId());
                                        fileVo.setCreateUid(userList.get(0).getuId());
                                        fileVo.setState(conNameList.get(0).getConApprovalState());
                                        fileVo.setCreateTime(new Date());
                                        fileVo.setCreateUname(userList.get(0).getuName());
                                        fileVo.setStateTime(conNameList.get(0).getConApprovalTime());
                                        fileVo.settCode(fileTemplate.gettCode());
                                        fileVo.settId(fileTemplate.gettId());
                                        fileVoList.add(fileVo);
                                    }
                                }
                            }else{
                                errMsg += "第" +  index +"行, 项目名:" + vo.getEntryName() + "与合同名:"+vo.getContractName() + "没有匹配的,导入失败;";
                                index++;
                            }
                        }

                    }else{
                        errMsg += "第" +  index +"行, 项目名:" + vo.getEntryName() + "对应的合同名:"+vo.getContractName() + "不存在或者审批未完成,导入失败;";
                        index++;
                    }
                }else{
                    errMsg += "第" +  index +"行, 项目名:" +  vo.getEntryName() + "不存在,导入失败;";
                }
                index++;
            }
            if(errMsg.equals("")){
                saveBatch(dataList);
                IntStream.range(0, dataList.size()).parallel().forEach(i -> {
                    createOrUpdateFileEntity(dataList.get(i), fileVoList.get(i));
                });
            }else{
              return  BaseResponse.fail(errMsg);
            }
        }else{
            return BaseResponse.fail("导入数据为空");
        }

        return errMsg.equals("") ? BaseResponse.ok():BaseResponse.fail(errMsg);
    }




    public static String getValueByTitle(List<LinkedHashMap<String, Object>> dataList, String title) {
        if (dataList != null) {
            for (LinkedHashMap<String, Object> item : dataList) {

                if (item.get("title").equals(title)) {
                    return String.valueOf(item.get("value"));
                }
            }
        }
        return title;
    }


    //过滤特定字符
    private String replaceSymbols(String input) {
        String fuzzyImportjson = iSysConfigService.getSysConfigByCode("FuzzyImport");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> symbolMap = objectMapper.readValue(fuzzyImportjson, Map.class);
            if (input == null || input.isEmpty()) {
                return input;
            }
            for (Map.Entry<String, String> entry : symbolMap.entrySet()) {
                input = input.replace(entry.getKey(), entry.getValue());
            }
        }catch (JsonProcessingException j){
            j.printStackTrace();
        }
        return input;
    }
}
