package com.jpmsoft.projectProducer.controller.v1.secInfo;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.SecInfo.InsExpertRequest;
import com.jpmsoft.common.model.request.SecInfo.InsExpertSearchDTO;
import com.jpmsoft.common.model.request.SecInfo.JudgeExtractionDTO;
import com.jpmsoft.common.model.request.SecInfo.JudgeExtractionSearchDTO;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.common.utils.VoInfoUtil;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.StorageChangeRequest;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.InsExpert;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.StorageChangeDetail;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.secInfo.InsExpertService;
import com.jpmsoft.projectProducer.service.secInfo.JudgeExtractionService;
import com.jpmsoft.projectProducer.service.secInfo.StorageChangeDetailService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 一个内部专家的录入（这是一个接口createOrUpdateInsideExpert，默认就是未入库）然后前端会有一个添加的按钮，
 * 我先查询未入库的内部专家给你（这是一个查询的接口queryNotInLib），然后你可以选择未入库的专家入库，
 * 然后给我（也是之前的接口createOrUpdateInsideExpert)，前端还有一个删除的按钮，
 * 会有一个查询已入库的内部专家的接口（又是一个接口queryInLib），
 * 然后你选择这个内部专家提出库（还是之前的接口createOrUpdateInsideExpert），
 * 然后就是专家抽取的接口(chooseExpert如果有需要你要给我屏蔽的单位，我会把这个单位下的入库的内部专家排除掉)
 */
@Slf4j
@RestController
@RequestMapping("v1/insideExpert")
public class InsideExpertController {

    @Autowired
    private InsExpertService insExpertService;

    @Autowired
    private StorageChangeDetailService storageChangeDetailService;
    @Resource
    private JudgeExtractionService judgeExtractionService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ISysUserService sysUserService;

    @PostMapping("/createOrUpdateInsideExpert")
    public BaseResponse createOrUpdateInsideExpert(@RequestBody InsExpertRequest insExpertRequest) {
        return insExpertService.createOrUpdateInsideExpert(insExpertRequest);
    }

    @PostMapping("/getInsExpertClassInfo")
    public BaseResponse getInsExpertClassInfo(@RequestBody InsExpertSearchDTO request) {
        return insExpertService.getInsExpertClassInfo(request);
    }

    @GetMapping("/queryNotInLib")
    public BaseResponse queryNotInLib() {
        return insExpertService.queryNotInLib();
    }

    @GetMapping("/queryInLib")
    public BaseResponse queryInLib() {
        return insExpertService.queryInLib();
    }

    @GetMapping("/queryExpert")
    public BaseResponse queryExpert() {
        return BaseResponse.ok(insExpertService.list());
    }

    @PostMapping("/queryExpertByPage")
    public BaseResponse queryExpertByPage(@RequestBody InsExpertSearchDTO request, @RequestHeader String token) {
        Page<InsExpert> pageInfo = new Page<>(request.getPage(), request.getSize());
        QueryWrapper<InsExpert> wrapper = new QueryWrapper<>();
        wrapper.eq("Ex_Category", request.getExCategory());
        //如果是专家库管理员则只查询审批通过的以及申请人为自己的
        String uId = redisUtils.getUid(token);
        if (!sysUserService.checkShowAllDataAuth(uId)) {
            if (ValidateUtil.isNotNull(request.getExpertAdmin()) && request.getExpertAdmin()) {
                wrapper.and(e -> e.eq("Ex_Applicant_Id", uId).or().eq("Ex_State", ApprovalStatusEnum.Already_Over.getIndex()));
            } else if (ValidateUtil.isNotEmpty(request.getExApplicantId())) {
                wrapper.eq("Ex_Applicant_Id", request.getExApplicantId());
            }
        }
        if (ValidateUtil.isNotNull(request.getExName()))
            wrapper.in("ex_name", request.getExName());
        if (ValidateUtil.isNotNull(request.getCName()))
            wrapper.in("c_name", request.getCName());
        if (ValidateUtil.isNotNull(request.getExDName()))
            wrapper.in("ex_d_name", request.getExDName());
        if (ValidateUtil.isNotNull(request.getInLibrary()))
            wrapper.in("In_Library", request.getInLibrary());
        if (ValidateUtil.isNotNull(request.getExTitle()))
            wrapper.in("Ex_Title", request.getExTitle());
        if (ValidateUtil.isNotNull(request.getExCellphoneNumber()))
            wrapper.in("Ex_Cellphone_Number", request.getExCellphoneNumber());
        if (ValidateUtil.isNotNull(request.getExPracticeCode())) {
            if (request.getExPracticeCode().size() > 1) {
                wrapper.and(e -> {
                    e.like("ex_Practice_Code", request.getExPracticeCode().get(0));
                    for (int i = 1; i < request.getExPracticeCode().size(); i++) {
                        e.or().like("ex_Practice_Code", request.getExPracticeCode().get(i));
                    }
                });
            } else {
                wrapper.like("ex_Practice_Code", request.getExPracticeCode().get(0));
            }
        }

        if (ValidateUtil.isNotNull(request.getExFinishDate()))
            wrapper.in("to_char(Ex_Finish_Date,'YYYY-MM-DD')", request.getExFinishDate());
        if (request.getExState() != null) {
            wrapper.eq("Ex_State", request.getExState());
        }
        IPage<InsExpert> records = insExpertService.page(pageInfo, wrapper);
        List<InsExpert> data = null;
        if (!CollectionUtils.isEmpty(records.getRecords())) {
            data = records.getRecords().stream().peek(
                    item -> item.setExIdNumber(VoInfoUtil.handleUserIdCard(item.getExIdNumber()))
            ).collect(Collectors.toList());
        }
        return BaseResponse.ok(data, (int) records.getTotal());
    }


    @GetMapping("/queryNotInLibByPage")
    public BaseResponse queryNotInLib(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return insExpertService.queryNotInLibByPage(page, size);
    }

    @GetMapping("/queryInLibByPage")
    public BaseResponse queryInLib(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return insExpertService.queryInLibByPage(page, size);
    }

    @PostMapping("/chooseExpert")
    public BaseResponse chooseExpert(@RequestBody JudgeExtractionSearchDTO judgeExtractionSearchDTO) {
        return insExpertService.chooseExpert(judgeExtractionSearchDTO);
    }

    @GetMapping("/getExpertByExId")
    public BaseResponse getExpertByExId(@RequestParam("exId") String exId) {
        return insExpertService.getExpertByExId(exId);
    }


    @PostMapping("/inLibraryOrChange")
    public BaseResponse inLibraryOrChange(@RequestBody StorageChangeRequest request) {
        return insExpertService.inLibraryOrChange(request);
    }

    @GetMapping("/rewriteInsExpert")
    public BaseResponse rewriteInsExpert(@RequestParam("storageChangeId") String storageChangeId) {
        return insExpertService.rewriteInsExpert(storageChangeId);
    }

    @GetMapping("/reject")
    public BaseResponse reject(@RequestParam("exId") String exId) {
        return insExpertService.reject(exId);
    }

    @GetMapping("/deleteInsExpert")
    public BaseResponse deleteInsExpert(@RequestParam("exId") String exId) {
        return insExpertService.deleteExpert(exId);
    }


    @GetMapping("/queryStorageChange")
    public BaseResponse queryStorageChange(@RequestParam("page") Integer page, @RequestParam("size") Integer size, @RequestHeader String token) {
        return insExpertService.queryStorageChange(page, size, redisUtils.getUid(token));
    }


    @GetMapping("/queryStorageChangeDetail")
    public BaseResponse queryStorageChange(@RequestParam("id") String id) {
        return insExpertService.queryStorageDetail(id);
    }


    @PostMapping("/createOrUpdateJudgeExtraction")
    public BaseResponse createOrUpdateJudgeExtraction(@RequestBody JudgeExtractionDTO judgeExtraction) {
        return insExpertService.createOrUpdateJudgeExtraction(judgeExtraction);
    }


    @GetMapping("/deleteJudgeExtraction")
    public BaseResponse deleteJudgeExtraction(@RequestParam String judgeExtractionId) {
        return insExpertService.deleteJudgeExtraction(judgeExtractionId);
    }


    @GetMapping("/queryJudgeExtraction")
    public BaseResponse queryJudgeExtraction(@RequestParam String judgeExtractionId) {
        return insExpertService.queryJudgeExtraction(judgeExtractionId);
    }


    @PostMapping("/queryJudgeExtractionByPage")
    public BaseResponse queryJudgeExtraction(@RequestBody JudgeExtractionSearchDTO request, @RequestHeader String token) {
        return judgeExtractionService.queryJudgeExtractionByPage(request, redisUtils.getUserInfoByToken(token));
    }


    @PostMapping("/getJudgeExtractionClassInfo")
    public BaseResponse getJudgeExtractionClassInfo(@RequestBody JudgeExtractionSearchDTO request, @RequestHeader String token) {
        return judgeExtractionService.getJudgeExtractionClassInfo(request, redisUtils.getUserInfoByToken(token));
    }


    @PostMapping("/expertExtraction")
    public BaseResponse expertExtraction(@RequestBody JudgeExtractionSearchDTO request) {
        return judgeExtractionService.expertExtraction(request);
    }

    @PostMapping("/CreateOrUpdateStorageChangeDetail")
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse CreateOrUpdateStorageChangeDetail(@RequestBody StorageChangeDetail request) {
        storageChangeDetailService.saveOrUpdate(request);
        return BaseResponse.ok();
    }

    @GetMapping("/getJudgeExtractionBySecId")
    public BaseResponse getJudgeExtractionBySecId(@RequestParam String secId) {
        log.info("getJudgeExtractionBySecId");
        return insExpertService.getJudgeExtractionBySecId(secId);
    }

    @GetMapping("/encryptIdNumber")
    public BaseResponse encryptIdNumber() {
        log.info("encryptIdNumber");
        insExpertService.encryptIdNumber();
        return BaseResponse.ok();
    }
}
