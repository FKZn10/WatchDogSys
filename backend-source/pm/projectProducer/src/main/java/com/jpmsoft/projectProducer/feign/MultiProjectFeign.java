package com.jpmsoft.projectProducer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.RequestBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "multi-project-cockpit-server", contextId = "multiProject")
public interface MultiProjectFeign {

    /***********************************************东大多项目驾驶舱***************************************************/

    /********************************************1、在建项目************************************************/

    /**
     * 新增在建项目数量
     *
     * @param request
     * @return
     */
    @PostMapping("/mpcProjectnumber/createMpcProjectnumber")
    BaseResponse createMpcProjectnumber(@RequestBody RequestBean request);

    /**
     * 更新在建项目数量
     *
     * @param request
     * @return
     */
    @PostMapping("/mpcProjectnumber/getMpcProjectnumberInfoById")
    BaseResponse getMpcProjectnumberInfoById(@RequestBody RequestBean request);

    /**
     * 获取数据根据type
     *
     * @param type
     * @return
     */
    @GetMapping("/mpcProjectnumber/getMpcProjectnumberListByType")
    BaseResponse getMpcProjectnumberListByType(@RequestParam String type);

    /**
     * 删除数据根据type
     *
     * @return
     */
    @GetMapping("/mpcProjectnumber/deleteByType")
    BaseResponse deleteByType();

    /********************************************2、项目投资进度************************************************/

    /**
     * 新增项目投资进度统计数据
     *
     * @param requestBean
     * @return
     */
    @PostMapping("/mpcProjectInvestmentProgress/createMpcProjectInvestmentProgress")
    BaseResponse createMpcProjectInvestmentProgress(@RequestBody RequestBean requestBean);

    /**
     * 删除数据根据type
     *
     * @return
     */
    @GetMapping("/mpcProjectInvestmentProgress/deleteByType")
    BaseResponse deleteProjectInvestmentProgressByType();

    /********************************************3、招投标情况一览 ************************************************/

    /**
     * 招投标情况一览统计数据
     *
     * @param requestBean
     * @return
     */
    @PostMapping("/mpcBiddingSituation/createMpcBiddingSituation")
    BaseResponse createMpcBiddingSituation(@RequestBody RequestBean requestBean);

    /**
     * 删除数据根据type
     *
     * @return
     */
    @GetMapping("/mpcBiddingSituation/deleteMpcBiddingSituationBySourceType")
    BaseResponse deleteMpcBiddingSituationBySourceType();

    /********************************************4、资金情况一览************************************************/

    /**
     * 新增资金情况一览统计数据
     *
     * @param requestBean
     * @return
     */
    @PostMapping("/mpcFunds/createMpcFunds")
    BaseResponse createMpcFunds(@RequestBody RequestBean requestBean);

    /**
     * 删除数据根据type
     *
     * @return
     */
    @GetMapping("/mpcFunds/deleteMpcFundsByType")
    BaseResponse deleteMpcFundsByType();

    /********************************************5、质量安全情况************************************************/

    /**
     * 新增质量安全情况统计数据
     *
     * @param requestBean
     * @return
     */
    @PostMapping("/mpcQualityAndSafety/createMpcQualityAndSafety")
    BaseResponse createMpcQualityAndSafety(@RequestBody RequestBean requestBean);

    /**
     * 删除数据根据type
     *
     * @return
     */
    @GetMapping("/mpcQualityAndSafety/deleteQualityAndSafetyByType")
    BaseResponse deleteQualityAndSafetyByType();

    /********************************************6、合同情况************************************************/

    /********************************************7、项目进度************************************************/

    /**
     * 项目进度统计数据
     *
     * @param requestBean
     * @return
     */
    @PostMapping("/mpcProjectSchedule/createMpcProjectSchedule")
    BaseResponse createMpcProjectSchedule(@RequestBody RequestBean requestBean);

    /**
     * 删除数据根据type
     *
     * @return
     */
    @GetMapping("/mpcProjectSchedule/deleteMpcProjectScheduleByType")
    BaseResponse deleteMpcProjectScheduleByType();

    /**
     * code获取配置信息
     *
     * @param code
     * @return
     */
    @GetMapping("/v1/SystemManage/GetConfigValueByCode")
    BaseResponse GetConfigValueByCode(@RequestParam String code);

    /********************************************8、变更签证情况************************************************/

    /**
     * 新增质量安全情况统计数据
     *
     * @param requestBean
     * @return
     */
    @PostMapping("/mpcChangevisaSituation/createMpcChangeVisaSituation")
    BaseResponse createMpcChangeVisaSituation(@RequestBody RequestBean requestBean);

    /**
     * 删除数据根据type
     *
     * @return
     */
    @GetMapping("/mpcChangevisaSituation/deleteChangeVisaSituationByType")
    BaseResponse deleteChangeVisaSituationByType();

}
