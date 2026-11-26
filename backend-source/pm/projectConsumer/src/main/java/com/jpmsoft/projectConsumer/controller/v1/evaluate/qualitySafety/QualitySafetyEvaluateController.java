package com.jpmsoft.projectConsumer.controller.v1.evaluate.qualitySafety;

import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectConsumer.feign.EvaluateFeign;
import com.jpmsoft.projectConsumer.request.qualitySafety.QualitySafetyEvaluateRequest;
import com.jpmsoft.projectConsumer.response.IndicatorDataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/dataEvaluate/QualitySafetyEvaluate")
@Slf4j
public class QualitySafetyEvaluateController {
    @Autowired
    private EvaluateFeign evaluateFeign;
    @PostMapping("/getQualitySafetyQuestionNumSG")
    //当月施工单位自查质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumSG(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumSG：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumSG(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumSGImportant")
    //当月施工单位自查质量问题数（条） 其中：影响功能和安全使用的重大质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumSGImportant(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumSGImportant：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumSGImportant(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumJL")
    //当月监理单位自查质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumJL(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumJL：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumJL(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumJLImportant")
    //当月监理单位自查质量问题数（条） 其中：影响功能和安全使用的重大质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumJLImportant(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumJLImportant：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumJLImportant(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumSGJL")
    //当月施工监理发现质量问题总数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumSGJL(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumSGJL：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumSGJL(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumSGJLSame")
    //当月施工监理同一质量问题重合数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumSGJLSame(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumSGJLSame：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumSGJLSame(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumSGJLSameImportant")
    //当月监理单位自查质量问题数（条） 其中：影响功能和安全使用的重大质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumSGJLSameImportant(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumSGJLSameImportant：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumSGJLSameImportant(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumYZ")
    //当月业主（代建）发现质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumYZ(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumYZ：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumYZ(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumYZImportant")
    //当月业主（代建）发现质量问题数（条）影响功能和安全使用的重大质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumYZImportant(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumYZImportant：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumYZImportant(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumYZSGSame")
    //当月业主（代建）与施工同一质量问题重合数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumYZSGSame(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumYZSGSame：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumYZSGSame(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumYZSGSameImportant")
    //当月业主（代建）发现质量问题数（条）影响功能和安全使用的重大质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumYZSGSameImportant(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumYZSGSameImportant：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumYZSGSameImportant(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumYZJLSame")
    //当月业主（代建）与监理同一质量问题重合数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumYZJLSame(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumYZJLSame：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumYZJLSame(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumYZJLSameImportant")
    //当月业主（代建）与监理同一质量问题重合数（条）影响功能和安全使用的重大质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumYZJLSameImportant(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumYZJLSameImportant：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumYZJLSameImportant(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumZG")
    //当月质量问题整改数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumZG(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumZG：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumZG(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumZGImportant")
    //当月质量问题整改数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumZGImportant(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumZGImportant：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumZGImportant(request);
    }

    @PostMapping("/getQualitySafetyQuestionZCRate")
    //当月不影响功能和安全使用的重大质量问题数(去除重合数)占当月发现质量问题总数（条）(去除重合数)
    public IndicatorDataResponse getQualitySafetyQuestionZCRate(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionZCRate：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionZCRate(request);
    }

    @PostMapping("/getQualitySafetyQuestionImportantRate")
    //当月施工监理发现质量问题总数(去除重合数)占当月发现质量问题总数（条）(去除重合数)比例
    public IndicatorDataResponse getQualitySafetyQuestionImportantRate(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionImportantRate：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionImportantRate(request);
    }


    @PostMapping("/getQualitySafetyQuestionNumSGAQ")
    //当月施工单位自查质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumSGAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumSGAQ：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumSGAQ(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumSGImportantAQ")
    //当月施工单位自查质量问题数（条） 其中：影响功能和安全使用的重大质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumSGImportantAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumSGImportantAQ：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumSGImportantAQ(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumJLAQ")
    //当月监理单位自查质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumJLAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumJLAQ：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumJLAQ(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumJLImportantAQ")
    //当月监理单位自查质量问题数（条） 其中：影响功能和安全使用的重大质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumJLImportantAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumJLImportantAQ：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumJLImportantAQ(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumSGJLAQ")
    //当月施工监理发现质量问题总数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumSGJLAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumSGJLAQ：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumSGJLAQ(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumSGJLSameAQ")
    //当月施工监理同一质量问题重合数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumSGJLSameAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumSGJLSameAQ：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumSGJLSameAQ(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumSGJLSameImportantAQ")
    //当月监理单位自查质量问题数（条） 其中：影响功能和安全使用的重大质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumSGJLSameImportantAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumSGJLSameImportantAQ：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumSGJLSameImportantAQ(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumYZAQ")
    //当月业主（代建）发现质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumYZAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumYZ：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumYZAQ(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumYZImportantAQ")
    //当月业主（代建）发现质量问题数（条）影响功能和安全使用的重大质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumYZImportantAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumYZImportantAQ：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumYZImportantAQ(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumYZSGSameAQ")
    //当月业主（代建）与施工同一质量问题重合数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumYZSGSameAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumYZSGSameAQ：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumYZSGSameAQ(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumYZSGSameImportantAQ")
    //当月业主（代建）发现质量问题数（条）影响功能和安全使用的重大质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumYZSGSameImportantAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumYZSGSameImportantAQ：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumYZSGSameImportantAQ(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumYZJLSameAQ")
    //当月业主（代建）与监理同一质量问题重合数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumYZJLSameAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumYZJLSameAQ：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumYZJLSameAQ(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumYZJLSameImportantAQ")
    //当月业主（代建）与监理同一质量问题重合数（条）影响功能和安全使用的重大质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumYZJLSameImportantAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumYZJLSameImportantAQ：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumYZJLSameImportantAQ(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumZGAQ")
    //当月质量问题整改数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumZGAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumZGAQ：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumZGAQ(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumZGImportantAQ")
    //当月质量问题整改数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumZGImportantAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumZGImportantAQ：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionNumZGImportantAQ(request);
    }

    @PostMapping("/getQualitySafetyQuestionZCRateAQ")
    //当月不影响功能和安全使用的重大质量问题数(去除重合数)占当月发现质量问题总数（条）(去除重合数)
    public IndicatorDataResponse getQualitySafetyQuestionZCRateAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionZCRateAQ：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionZCRateAQ(request);
    }

    @PostMapping("/getQualitySafetyQuestionImportantRateAQ")
    //当月施工监理发现质量问题总数(去除重合数)占当月发现质量问题总数（条）(去除重合数)比例
    public IndicatorDataResponse getQualitySafetyQuestionImportantRateAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionImportantRateAQ：{}",JacksonUtils.toJSONString(request));
        return evaluateFeign.getQualitySafetyQuestionImportantRateAQ(request);
    }
}
