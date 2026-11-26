package com.jpmsoft.gmgws.controller;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.gmgws.feign.DictionaryFeign;
import com.jpmsoft.gmgws.pojo.vo.StatisticsVo;
import com.jpmsoft.gmgws.service.FinancialPaymentService;
import com.jpmsoft.gmgws.service.InfrastrExcelService;
import com.jpmsoft.gmgws.service.PaymentStatisticsService;
import com.jpmsoft.gmgws.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/gmgws")
@Api(tags = "光明统计", value = "光明统计")
public class InfrastrExcelController {


    @Autowired
    private InfrastrExcelService infrastrExcelService;

    @Autowired
    private PaymentStatisticsService paymentStatisticsService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private FinancialPaymentService financialPaymentService;

    @Autowired
    private DictionaryFeign dictionaryFeign;




    //基建统计导入
    @ApiOperation(value = "Excel数据导入")
    @PostMapping("/importExcel")
    public BaseResponse importExcel(MultipartFile file) throws IOException {
        return  paymentStatisticsService.importExcel(file);
    }

    @ApiOperation(value = "财政支付申请导入")
    @PostMapping("/financeImport")
    public BaseResponse financeImport(MultipartFile file, @RequestHeader String token) throws IOException {
        return  financialPaymentService.financeImport(file,redisUtils.getUid(token));
    }

    //excel模板下载
//    @ApiOperation(value = "Excel模板下载")
//    @GetMapping("/excelTemplate")
//    public ResponseEntity<InputStreamResource> excelTemplateDownload() throws IOException {
//        // 模板文件路径
//        String templateFilePath = "/template/gmmb.xls";
//        // 获取资源文件输入流
//        ClassPathResource resource = new ClassPathResource(templateFilePath);
//        InputStream inputStream = resource.getInputStream();
//
//        // 设置响应头，包括文件名和MIME类型
//        String filename = "gmmb.xls";
//        String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        headers.setContentDispositionFormData("attachment", encodedFilename);
//
//        // 返回包含文件流的ResponseEntity对象
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(new InputStreamResource(inputStream));
//
//    }


    /** 查询合同统计
     *
     * @param contract  合同
     * @param company  单位
     * @return
     */
    @ApiOperation(value = "合同付款情况统计")
    @PostMapping("/getContractList")
    public BaseResponse getContractList(@RequestBody StatisticsVo statisticsVo){
       return infrastrExcelService.getContractList(statisticsVo);
    }

    /**
     *  查询合同支付
     * @param statisticsVo
     * @return
     */
    @ApiOperation(value = "合同付款情况统计(新)")
    @PostMapping("/getContractStatisticsList")
    public BaseResponse getContractStatisticsList(@RequestBody StatisticsVo statisticsVo, @RequestHeader String token){
        return infrastrExcelService.getContractStatisticsList(statisticsVo, redisUtils.getUid(token));
    }



    /**
     *查询项目支付
     * @param contract   合同
     * @param project  项目
     * @param Company  单位
     * @return
     */
    @ApiOperation(value = "项目付款情况统计")
    @PostMapping("/getProjectList")
    public BaseResponse getProjectList(@RequestBody StatisticsVo statisticsVo,@RequestHeader String token){
        return infrastrExcelService.getProjectList(statisticsVo,redisUtils.getUid(token));
    }


    //合同统计台账 qiyong
    @ApiOperation(value = "合同付款审批台账")
    @PostMapping("/getContractLedger")
    public BaseResponse getContractLedger(@RequestBody StatisticsVo statisticsVo){
        return infrastrExcelService.getContractLedger(statisticsVo);
    }


    //资金支付统计
    @ApiOperation(value = "资金支付统计")
    @PostMapping("/getCapitalPayment")
    public BaseResponse getCapitalPayment(@RequestBody StatisticsVo statisticsVo){
        return infrastrExcelService.getCapitalPayment(statisticsVo);
    }


    @ApiOperation(value = "根据id查询付款明细")
    @GetMapping("/getPaymentDetails")
    public BaseResponse  getPaymentDetails(@RequestParam String conId){
        return infrastrExcelService.getPaymentDetails(conId);
    }

    @ApiOperation(value = "项目付款情况统计(鉴权)")
    @PostMapping("/getProjectAuthenticationList")
    public BaseResponse getProjectAuthenticationList(@RequestBody StatisticsVo statisticsVo,@RequestHeader String token){
        return infrastrExcelService.getProjectAuthenticationList(statisticsVo,redisUtils.getUid(token));
    }


}
