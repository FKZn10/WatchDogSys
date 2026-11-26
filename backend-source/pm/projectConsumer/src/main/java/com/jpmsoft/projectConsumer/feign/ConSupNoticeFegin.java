package com.jpmsoft.projectConsumer.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.RequestBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "project-producer", contextId = "conSupNotice")
public interface ConSupNoticeFegin {

    /**
     * 监理通知单列表
     *
     * @param page
     * @return
     */
    @PostMapping("/v1/conSupNotice/getConSupNoticeList")
    BaseResponse getConSupNoticeList(@RequestBody Page page);


    @PostMapping("/v1/conSupNotice/createOrUpdateConSupNotice")
    BaseResponse createOrUpdateConSupNotice(@RequestBody RequestBean requestBean);

    @PostMapping("/v1/conSupNotice/getConSupNoticeById")
    BaseResponse getConSupNoticeById(@RequestBody RequestBean requestBean);

    @PostMapping("/v1/conSupNotice/updateSnState")
    BaseResponse updateSnState(@RequestBody RequestBean requestBean);

    @PostMapping("/v1/conSupNotice/createOrUpdateConSupNoticeReply")
    BaseResponse createOrUpdateConSupNoticeReply(@RequestBody RequestBean requestBean);

    @PostMapping("/v1/conSupNotice/updateSnrState")
    BaseResponse updateSnrState(@RequestBody RequestBean requestBean);

    @PostMapping("/v1/conSupNotice/getConSupNoticeReplyPageList")
    BaseResponse getConSupNoticeReplyPageList(@RequestBody Page page);

    @PostMapping("/v1/conSupNotice/getConSupNoticeReplyById")
    BaseResponse getConSupNoticeReplyById(@RequestBody RequestBean requestBean);

    @GetMapping("/v1/conSupNoticeReply/getReplyOrder")
    BaseResponse getReplyOrder(@RequestParam String pId, @RequestParam String snId);

    /**
     * 根据监理报表生成监理通知单并发送待办
     */
    @GetMapping("/v1/conSupNotice/createConSupNoticeByWorkReport")
    BaseResponse createConSupNoticeByWorkReport(@RequestParam String wrId);
}
