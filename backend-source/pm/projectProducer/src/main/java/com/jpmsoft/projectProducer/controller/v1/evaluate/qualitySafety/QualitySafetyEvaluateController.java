package com.jpmsoft.projectProducer.controller.v1.evaluate.qualitySafety;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.jpmsoft.projectProducer.pojo.request.evaluate.qualitySafety.QualitySafetyEvaluateRequest;
import com.jpmsoft.projectProducer.pojo.response.IndicatorDataResponse;
import com.jpmsoft.projectProducer.service.qualitySafety.IQualitySafetyEvaluateService;
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
    private IQualitySafetyEvaluateService qualitySafetyEvaluateService;

    @PostMapping("/getQualitySafetyQuestionNumSG")
    //当月施工单位自查质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumSG(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("质量");
        log.info("getQualitySafetyQuestionNumSG：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumSG(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumSGImportant")
    //当月施工单位自查质量问题数（条） 其中：影响功能和安全使用的重大质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumSGImportant(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("质量");
        log.info("getQualitySafetyQuestionNumSGImportant：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumSGImportant(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumJL")
    //当月监理单位自查质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumJL(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("质量");
        log.info("getQualitySafetyQuestionNumJL：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumJL(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumJLImportant")
    //当月监理单位自查质量问题数（条） 其中：影响功能和安全使用的重大质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumJLImportant(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("质量");
        log.info("getQualitySafetyQuestionNumJLImportant：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumJLImportant(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumSGJL")
    //当月施工监理发现质量问题总数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumSGJL(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("质量");
        log.info("getQualitySafetyQuestionNumSGJL：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumSGJL(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumSGJLSame")
    //当月施工监理同一质量问题重合数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumSGJLSame(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("质量");
        log.info("getQualitySafetyQuestionNumSGJLSame：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumSGJLSame(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumSGJLSameImportant")
    //当月监理单位自查质量问题数（条） 其中：影响功能和安全使用的重大质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumSGJLSameImportant(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("质量");
        log.info("getQualitySafetyQuestionNumSGJLSameImportant：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumSGJLSameImportant(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumYZ")
    //当月业主（代建）发现质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumYZ(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("质量");
        log.info("getQualitySafetyQuestionNumYZ：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumYZ(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumYZImportant")
    //当月业主（代建）发现质量问题数（条）影响功能和安全使用的重大质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumYZImportant(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("质量");
        log.info("getQualitySafetyQuestionNumYZImportant：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumYZImportant(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumYZSGSame")
    //当月业主（代建）与施工同一质量问题重合数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumYZSGSame(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("质量");
        log.info("getQualitySafetyQuestionNumYZSGSame：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumYZSGSame(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumYZSGSameImportant")
    //当月业主（代建）发现质量问题数（条）影响功能和安全使用的重大质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumYZSGSameImportant(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("质量");
        log.info("getQualitySafetyQuestionNumYZSGSameImportant：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumYZSGSameImportant(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumYZJLSame")
    //当月业主（代建）与监理同一质量问题重合数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumYZJLSame(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("质量");
        log.info("getQualitySafetyQuestionNumYZJLSame：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumYZJLSame(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumYZJLSameImportant")
    //当月业主（代建）与监理同一质量问题重合数（条）影响功能和安全使用的重大质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumYZJLSameImportant(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("质量");
        log.info("getQualitySafetyQuestionNumYZJLSameImportant：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumYZJLSameImportant(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumZG")
    //当月质量问题整改数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumZG(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("质量");
        log.info("getQualitySafetyQuestionNumZG：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumZG(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumZGImportant")
    //当月质量问题整改数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumZGImportant(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("质量");
        log.info("getQualitySafetyQuestionNumZGImportant：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumZGImportant(request);
    }

    @PostMapping("/getQualitySafetyQuestionZCRate")
    //当月不影响功能和安全使用的重大质量问题数(去除重合数)占当月发现质量问题总数（条）(去除重合数)
    public IndicatorDataResponse getQualitySafetyQuestionZCRate(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("质量");
        log.info("getQualitySafetyQuestionZCRate：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionZCRate(request);
    }

    @PostMapping("/getQualitySafetyQuestionImportantRate")
    //当月施工监理发现质量问题总数(去除重合数)占当月发现质量问题总数（条）(去除重合数)比例
    public IndicatorDataResponse getQualitySafetyQuestionImportantRate(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("质量");
        log.info("getQualitySafetyQuestionImportantRate：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionImportantRate(request);
    }


    @PostMapping("/getQualitySafetyQuestionNumSGAQ")
    //当月施工单位自查质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumSGAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("安全");
        log.info("getQualitySafetyQuestionNumSGAQ：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumSG(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumSGImportantAQ")
    //当月施工单位自查质量问题数（条） 其中：影响功能和安全使用的重大质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumSGImportantAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("安全");
        log.info("getQualitySafetyQuestionNumSGImportantAQ：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumSGImportant(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumJLAQ")
    //当月监理单位自查质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumJLAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("安全");
        log.info("getQualitySafetyQuestionNumJLAQ：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumJL(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumJLImportantAQ")
    //当月监理单位自查质量问题数（条） 其中：影响功能和安全使用的重大质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumJLImportantAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("安全");
        log.info("getQualitySafetyQuestionNumJLImportantAQ：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumJLImportant(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumSGJLAQ")
    //当月施工监理发现质量问题总数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumSGJLAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("安全");
        log.info("getQualitySafetyQuestionNumSGJLAQ：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumSGJL(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumSGJLSameAQ")
    //当月施工监理同一质量问题重合数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumSGJLSameAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("安全");
        log.info("getQualitySafetyQuestionNumSGJLSameAQ：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumSGJLSame(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumSGJLSameImportantAQ")
    //当月监理单位自查质量问题数（条） 其中：影响功能和安全使用的重大质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumSGJLSameImportantAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("安全");
        log.info("getQualitySafetyQuestionNumSGJLSameImportantAQ：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumSGJLSameImportant(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumYZAQ")
    //当月业主（代建）发现质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumYZAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("安全");
        log.info("getQualitySafetyQuestionNumYZAQ：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumYZ(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumYZImportantAQ")
    //当月业主（代建）发现质量问题数（条）影响功能和安全使用的重大质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumYZImportantAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("安全");
        log.info("getQualitySafetyQuestionNumYZImportantAQ：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumYZImportant(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumYZSGSameAQ")
    //当月业主（代建）与施工同一质量问题重合数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumYZSGSameAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("安全");
        log.info("getQualitySafetyQuestionNumYZSGSameAQ：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumYZSGSame(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumYZSGSameImportantAQ")
    //当月业主（代建）发现质量问题数（条）影响功能和安全使用的重大质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumYZSGSameImportantAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("安全");
        log.info("getQualitySafetyQuestionNumYZSGSameImportantAQ：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumYZSGSameImportant(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumYZJLSameAQ")
    //当月业主（代建）与监理同一质量问题重合数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumYZJLSameAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("安全");
        log.info("getQualitySafetyQuestionNumYZJLSameAQ：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumYZJLSame(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumYZJLSameImportantAQ")
    //当月业主（代建）与监理同一质量问题重合数（条）影响功能和安全使用的重大质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumYZJLSameImportantAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("安全");
        log.info("getQualitySafetyQuestionNumYZJLSameImportantAQ：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumYZJLSameImportant(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumZGAQ")
    //当月质量问题整改数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumZGAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("安全");
        log.info("getQualitySafetyQuestionNumZGAQ：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumZG(request);
    }

    @PostMapping("/getQualitySafetyQuestionNumZGImportantAQ")
    //当月质量问题整改数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumZGImportantAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("安全");
        log.info("getQualitySafetyQuestionNumZGImportantAQ：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionNumZGImportant(request);
    }

    @PostMapping("/getQualitySafetyQuestionZCRateAQ")
    //当月不影响功能和安全使用的重大质量问题数(去除重合数)占当月发现质量问题总数（条）(去除重合数)
    public IndicatorDataResponse getQualitySafetyQuestionZCRateAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("安全");
        log.info("getQualitySafetyQuestionZCRateAQ：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionZCRate(request);
    }

    @PostMapping("/getQualitySafetyQuestionImportantRateAQ")
    //当月施工监理发现质量问题总数(去除重合数)占当月发现质量问题总数（条）(去除重合数)比例
    public IndicatorDataResponse getQualitySafetyQuestionImportantRateAQ(@RequestBody QualitySafetyEvaluateRequest request) {
        request.setQsType("安全");
        log.info("getQualitySafetyQuestionImportantRateAQ：{}",JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        return qualitySafetyEvaluateService.getQualitySafetyQuestionImportantRate(request);
    }
}