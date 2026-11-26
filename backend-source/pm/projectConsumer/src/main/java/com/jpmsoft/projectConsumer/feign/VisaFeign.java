package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.*;
import com.jpmsoft.projectConsumer.pojo.dto.visaChange.ConDccsDTO;
import com.jpmsoft.projectConsumer.pojo.dto.visaChange.ConDclsDTO;
import com.jpmsoft.projectConsumer.pojo.dto.visaChange.ConEnFeeDTO;
import com.jpmsoft.projectConsumer.pojo.dto.visaChange.ConSvBudgetDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 变更签证相关
 */
@FeignClient(name = "project-producer", contextId = "visaChange")
public interface VisaFeign {

    /**********************************************设计变更单***************************************************/
    @PostMapping("/v1/conDco/createOrUpdateConDco")
    BaseResponse createOrUpdateConDco(@RequestBody ConDcoRequest request);

    @PostMapping("/v1/conDco/getConDcoList")
    BaseResponse getConDcoList(@RequestBody ConDcoRequest request, @RequestHeader String token);

    @PostMapping("/v1/conDco/getConDcoListGm")
    BaseResponse getConDcoListGm(@RequestBody ConDcoRequest request, @RequestHeader String token);

    @PostMapping("/v1/conDco/getConDcoListNoAuth")
    BaseResponse getConDcoListNoAuth(@RequestBody ConDcoRequest request);

    @GetMapping("/v1/conDco/getConDcoByDcoId")
    BaseResponse getConDcoByDcoId(@RequestParam String dcoId);

    /**********************************************施工变更预算***************************************************/
    @PostMapping("/v1/conDcBudget/createOrUpdateConDcBudget")
    BaseResponse createOrUpdateConDcBudget(@RequestBody ConDcBudgetRequest request);

    @PostMapping("/v1/conDcBudget/getConDcBudgetList")
    BaseResponse getConDcBudgetList(@RequestBody ConDcBudgetRequest request, @RequestHeader String token);

    @GetMapping("/v1/conDcBudget/getConDcBudgetByDcbId")
    BaseResponse getConDcBudgetByDcbId(@RequestParam String dcbId);

    @GetMapping("/v1/conDcBudget/getDcbNotContainAmtByConId")
    BaseResponse getDcbNotContainAmtByConId(@RequestParam String conId);

    /**********************************************工程洽商指令单***************************************************/
    @PostMapping("/v1/conEnis/createOrUpdateConEnis")
    BaseResponse createOrUpdateConEnis(@RequestBody ConEnisRequest request);

    @PostMapping("/v1/conEnis/getConEnisList")
    BaseResponse getConEnisList(@RequestBody ConEnisRequest request, @RequestHeader String token);

    @PostMapping("/v1/conEnis/getConEnisListGm")
    BaseResponse getConEnisListGm(@RequestBody ConEnisRequest request, @RequestHeader String token);

    @PostMapping("/v1/conEnis/getConEnisListNoAuth")
    BaseResponse getConEnisListNoAuth(@RequestBody ConEnisRequest request);

    @GetMapping("/v1/conEnis/getConEnisById")
    BaseResponse getConEnisById(@RequestParam String enisId);

    /**********************************************工程洽商施工方案及预算***************************************************/

    @PostMapping("/v1/conEnBudget/createOrUpdateConEnBudget")
    BaseResponse createOrUpdateConEnBudget(@RequestBody ConEnBudgetRequest request);

    @PostMapping("/v1/conEnBudget/getConEnBudgetList")
    BaseResponse getConEnBudgetList(@RequestBody ConEnBudgetRequest request, @RequestHeader String token);

    @GetMapping("/v1/conEnBudget/getConEnBudgetById")
    BaseResponse getConEnBudgetById(@RequestParam String enbId);

    @GetMapping("/v1/conEnBudget/getEnbNotContainAmtByConId")
    BaseResponse getEnbNotContainAmtByConId(@RequestParam String conId);

    /**********************************************工程量核实***************************************************/
    @PostMapping("/v1/conEnVerify/createOrUpdateConEnVerify")
    BaseResponse createOrUpdateConEnVerify(@RequestBody ConEnVerifyRequest request);

    @PostMapping("/v1/conEnVerify/getConEnVerifyList")
    BaseResponse getConEnVerifyList(@RequestBody ConEnVerifyRequest request, @RequestHeader String token);

    @GetMapping("/v1/conEnVerify/getConEnVerifyById")
    BaseResponse getConEnVerifyById(@RequestParam String envId);

    /**********************************************工程洽商变更记录***************************************************/

    @PostMapping("/v1/conEncr/createOrUpdateConEncr")
    BaseResponse createOrUpdateConEncr(@RequestBody ConEncrRequest request);

    @PostMapping("/v1/conEncr/getConEncrList")
    BaseResponse getConEncrList(@RequestBody ConEncrRequest request, @RequestHeader String token);

    @PostMapping("/v1/conEncr/getConEncrListNoAuth")
    BaseResponse getConEncrListNoAuth(@RequestBody ConEncrRequest request);

    @GetMapping("/v1/conEncr/getConEncrById")
    BaseResponse getConEncrById(@RequestParam String encrId);

    /**********************************************现场签证指令单***************************************************/
    @PostMapping("/v1/conSvis/createOrUpdateConSvis")
    BaseResponse createOrUpdateConSvis(@RequestBody ConSvisRequest request);

    @PostMapping("/v1/conSvis/getConSvisList")
    BaseResponse getConSvisList(@RequestBody ConSvisRequest request, @RequestHeader String token);

    @GetMapping("/v1/conSvis/getConSvisById")
    BaseResponse getConSvisById(@RequestParam String svisId);

    /**********************************************现场签证工程量核实***************************************************/
    @PostMapping("/v1/conSvVerify/createOrUpdateConSvVerify")
    BaseResponse createOrUpdateConSvVerify(@RequestBody ConSvVerifyRequest request);

    @PostMapping("/v1/conSvVerify/getConSvVerifyList")
    BaseResponse getConSvVerifyList(@RequestBody ConSvVerifyRequest request, @RequestHeader String token);

    @GetMapping("/v1/conSvVerify/getConSvVerifyById")
    BaseResponse getConSvVerifyById(@RequestParam String svvId);

    /**********************************************现场签证变更记录***************************************************/

    @PostMapping("/v1/conSvcr/createOrUpdateConSvcr")
    BaseResponse createOrUpdateConSvcr(@RequestBody ConSvcrRequest request);

    @PostMapping("/v1/conSvcr/getConSvcrList")
    BaseResponse getConSvcrList(@RequestBody ConSvcrRequest request, @RequestHeader String token);

    @GetMapping("/v1/conSvcr/getConSvcrById")
    BaseResponse getConSvcrById(@RequestParam String svcrId);

    /**********************************************专家论证会申请***************************************************/
    @PostMapping("/v1/conEcm/createOrUpdateConEcm")
    BaseResponse createOrUpdateConEcm(@RequestBody ConEcmRequest request);

    @PostMapping("/v1/conEcm/getConEcmList")
    BaseResponse getConEcmList(@RequestBody ConEcmRequest request, @RequestHeader String token);

    @PostMapping("/v1/conEcm/getConEcmListNoAuth")
    BaseResponse getConEcmListNoAuth(@RequestBody ConEcmRequest request);

    @GetMapping("/v1/conEcm/getConEcmById")
    BaseResponse getConEcmById(@RequestParam String ecmId);

    /**********************************************设计变更联系单***************************************************/

    @PostMapping("/v1/conDccs/createOrUpdateConDccs")
    BaseResponse createOrUpdateConDccs(@RequestBody ConDccsDTO request);

    @PostMapping("/v1/conDccs/getConDccsList")
    BaseResponse getConDccsList(@RequestBody ConDccsDTO request, @RequestHeader String token);

    @GetMapping("/v1/conDccs/getConDccsById")
    BaseResponse getConDccsById(@RequestParam String id);

    /**********************************************设计变更记录单***************************************************/


    @PostMapping("/v1/conDcls/createOrUpdateConDcls")
    BaseResponse createOrUpdateConDcls(@RequestBody ConDclsDTO request);

    @PostMapping("/v1/conDcls/getConDclsList")
    BaseResponse getConDclsList(@RequestBody ConDclsDTO request, @RequestHeader String token);

    @GetMapping("/v1/conDcls/getConDclsById")
    BaseResponse getConDclsById(@RequestParam String id);

    /**********************************************现场签证内部核准单***************************************************/

    @PostMapping("/v1/conSvBudget/createOrUpdateConSvBudget")
    BaseResponse createOrUpdateConSvBudget(@RequestBody ConSvBudgetDTO request);

    @PostMapping("/v1/conSvBudget/getConSvBudgetList")
    BaseResponse getConSvBudgetList(@RequestBody ConSvBudgetDTO request, @RequestHeader String token);

    @GetMapping("/v1/conSvBudget/getConSvBudgetById")
    BaseResponse getConSvBudgetById(@RequestParam String id);

    @GetMapping("/v1/conSvBudget/getCsbNotContainAmtByConId")
    BaseResponse getCsbNotContainAmtByConId(@RequestParam String conId);
    /**********************************************洽商费用申请***************************************************/

    @PostMapping("/v1/conEnFee/createOrUpdateConEnFee")
    BaseResponse createOrUpdateConEnFee(@RequestBody ConEnFeeDTO request);

    @PostMapping("/v1/conEnFee/getConEnFeeList")
    BaseResponse getConEnFeeList(@RequestBody ConEnFeeDTO request, @RequestHeader String token);

    @GetMapping("/v1/conEnFee/getConEnFeeById")
    BaseResponse getConEnFeeById(@RequestParam String id);

}