package com.jpmsoft.projectConsumer.controller.v1.conSupNotice;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.RequestBean;
import com.jpmsoft.projectConsumer.feign.ConSupNoticeFegin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/conSupNotice")
public class ConSupNoticeController {

    @Autowired
    private ConSupNoticeFegin conSupNoticeFegin;

    /**
     * 首页
     */
    @PostMapping("/getConSupNoticeList")
    public BaseResponse getConSupNoticeList(@RequestBody Page page) {
        try {
            log.info("getConSupNoticeList");
            return conSupNoticeFegin.getConSupNoticeList(page);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new BaseResponse("服务内部错误！");
        }

    }

    /**
     * 新增或者修改
     */
    @PostMapping("/createOrUpdateConSupNotice")
    public BaseResponse createOrUpdateConSupNotice(@RequestBody RequestBean requestBean) {
        try {
            log.info("createOrUpdateConSupNotice");
            return conSupNoticeFegin.createOrUpdateConSupNotice(requestBean);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new BaseResponse("服务内部错误！");
        }

    }

    /**
     * 根据监理报表生成监理通知单并发送待办
     */
    @GetMapping("/createConSupNoticeByWorkReport")
    public BaseResponse createConSupNoticeByWorkReport(@RequestParam String wrId) {
        try {
            log.info("createConSupNoticeByWorkReport wrId:{}", wrId);
            return conSupNoticeFegin.createConSupNoticeByWorkReport(wrId);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new BaseResponse("服务内部错误！");
        }

    }

    /**
     * 查看
     */
    @PostMapping("/getConSupNoticeById")
    public BaseResponse getConSupNoticeById(@RequestBody RequestBean requestBean) {
        try {
            log.info("getConSupNoticeById");
            return conSupNoticeFegin.getConSupNoticeById(requestBean);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new BaseResponse("服务内部错误！");
        }

    }

    /**
     * 通知单审核
     */
    @PostMapping("/updateSnState")
    public BaseResponse updateSnState(@RequestBody RequestBean requestBean) {
        try {
            log.info("updateSnState");
            return conSupNoticeFegin.updateSnState(requestBean);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new BaseResponse("服务内部错误！");
        }

    }

    /**
     * 通知单回复
     */
    @PostMapping("/createOrUpdateConSupNoticeReply")
    public BaseResponse createOrUpdateConSupNoticeReply(@RequestBody RequestBean requestBean) {
        try {
            log.info("createOrUpdateConSupNoticeReply");
            return conSupNoticeFegin.createOrUpdateConSupNoticeReply(requestBean);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new BaseResponse("服务内部错误！");
        }

    }

    /**
     * 通知单回复审核
     */
    @PostMapping("/updateSnrState")
    public BaseResponse updateSnrState(@RequestBody RequestBean requestBean) {
        try {
            log.info("updateSnrState");
            return conSupNoticeFegin.updateSnrState(requestBean);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new BaseResponse("服务内部错误！");
        }

    }
    /**
     * 通知单回复审核
     */
    @PostMapping("/getConSupNoticeReplyPageList")
    public BaseResponse getConSupNoticeReplyPageList(@RequestBody Page page) {
        try {
            log.info("getConSupNoticeReplyPageList");
            return conSupNoticeFegin.getConSupNoticeReplyPageList(page);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new BaseResponse("服务内部错误！");
        }

    }
    /**
     * 通知单回复详情
     */
    @PostMapping("/getConSupNoticeReplyById")
    public BaseResponse getConSupNoticeReplyById(@RequestBody RequestBean requestBean) {
        try {
            log.info("getConSupNoticeReplyById");
            return conSupNoticeFegin.getConSupNoticeReplyById(requestBean);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new BaseResponse("服务内部错误！");
        }

    }
}
