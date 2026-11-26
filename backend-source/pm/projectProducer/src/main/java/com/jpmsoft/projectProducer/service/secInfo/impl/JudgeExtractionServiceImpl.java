package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.SecInfo.JudgeExtractionDTO;
import com.jpmsoft.common.model.request.SecInfo.JudgeExtractionSearchDTO;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.JudgeExtractionMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.InsExpert;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.JudgeExtraction;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertService;
import com.jpmsoft.projectProducer.service.secInfo.InsExpertService;
import com.jpmsoft.projectProducer.service.secInfo.JudgeExtractionDetailService;
import com.jpmsoft.projectProducer.service.secInfo.JudgeExtractionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【judge_extraction(评委抽取表)】的数据库操作Service实现
 * @createDate 2025-04-24 18:08:19
 */
@Service
public class JudgeExtractionServiceImpl extends ServiceImpl<JudgeExtractionMapper, JudgeExtraction>
        implements JudgeExtractionService {

    @Resource
    private ISecExpertService secExpertService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private InsExpertService insExpertService;
    @Resource
    private JudgeExtractionDetailService judgeExtractionDetailService;

    @Override
    public JudgeExtractionDTO selectById(String id) {
        JudgeExtraction data = baseMapper.selectById(id);
        if (data == null) throw new JPMException(ResMsg.infoNotExist);
        JudgeExtractionDTO res = new JudgeExtractionDTO();
        BeanUtils.copyProperties(data, res);
        res.setJudgeExtractions(judgeExtractionDetailService.selectByExId(id));
        return res;
    }

    @Override
    public BaseResponse getJudgeExtractionClassInfo(JudgeExtractionSearchDTO request, LogUserInfoVo user) {
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<>();
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(JudgeExtractionSearchDTO.class.getName());
            String authSql = getJudgeExtractionAuthSql(request, user);
            if (ValidateUtil.isEmpty(authSql)) {
                return BaseResponse.fail(ResMsg.noPermission);
            }
            for (ClassInfoAnnotation classInfoAnnotation : classInfoAnnotationList) {
                if (!classInfoAnnotation.getType().equals("date")) {
                    List<Object> dictionaryValues = baseMapper.getJudgeExtractionClassInfo(authSql, classInfoAnnotation.getDbValue());
                    dictionaryValues = dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                    classInfoAnnotation.setDictionaryValues(dictionaryValues);
                    classInfoAnnotation.setNo(classInfoAnnotation.getNo());
                }
            }
        } catch (Exception e) {
            log.error("queryFinalAccountsClassInfo error:{}", e);
            return BaseResponse.ok(classInfoAnnotationList);
        }
        classInfoAnnotationList = classInfoAnnotationList.stream().sorted(Comparator.comparing(ClassInfoAnnotation::getNo)).collect(Collectors.toList());

        return BaseResponse.ok(classInfoAnnotationList);
    }

    @Override
    public BaseResponse expertExtraction(JudgeExtractionSearchDTO request) {
        if (request.getInsideNum() == 0 && request.getOutNum() == 0)
            return BaseResponse.fail("未指定抽取人数！");
        List<InsExpert> ins = new ArrayList<>();
        List<SecExpertModel> out = new ArrayList<>();
        //region 抽取集团本部专家/小城投专家
        if (request.getInsideNum() > 0) {
            LambdaQueryWrapper<InsExpert> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(InsExpert::getInLibrary, Boolean.TRUE);
            wrapper.eq(InsExpert::getExCategory, request.getJeCategory());
            if (!CollectionUtils.isEmpty(request.getAvoidUser()))
                wrapper.notIn(InsExpert::getExCreateUid, request.getAvoidUser());
            if (ValidateUtil.isNotEmpty(request.getAvoidUnit()))
                wrapper.notIn(InsExpert::getCId, Arrays.asList(request.getAvoidUnit().split(",")));
            List<InsExpert> insExperts = insExpertService.list(wrapper);
            if (CollectionUtils.isEmpty(insExperts) || insExperts.size() < request.getInsideNum())
                return BaseResponse.fail("集团本部评委可抽取人数不足！");
            Collections.shuffle(insExperts);//随机打乱
            ins = insExperts.subList(0, request.getInsideNum());
        }
        //endregion
        //region 抽取外部专家
        if (request.getOutNum() > 0) {
            List<SecExpertModel> expertModels = secExpertService.getExtractExpertList(request.getAvoidUser(),
                    ValidateUtil.isNotEmpty(request.getAvoidUnit()) ? Arrays.asList(request.getAvoidUnit().split(",")) : null);
            if (CollectionUtils.isEmpty(expertModels) || expertModels.size() < request.getOutNum())
                return BaseResponse.fail("外部评委可抽取人数不足！");
            Collections.shuffle(expertModels);//随机打乱
            out = expertModels.subList(0, request.getOutNum());
        }
        //endregion
        //region 结果集合并
        List<SecExpertModel> returnData = new ArrayList<>();

        if (!CollectionUtils.isEmpty(ins)) {
            out.addAll(ins.stream().map(d -> {
                SecExpertModel m = new SecExpertModel();
                BeanUtils.copyProperties(d, m);
                return m;
            }).collect(Collectors.toList()));
        }
        //endregion
        return BaseResponse.ok(out);
    }

    @Override
    public BaseResponse queryJudgeExtractionByPage(JudgeExtractionSearchDTO request, LogUserInfoVo user) {
        String authSql = getJudgeExtractionAuthSql(request, user);
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        Page<JudgeExtraction> pageInfo = new Page<>(request.getPage(), request.getSize());
        List<JudgeExtraction> records = baseMapper.queryJudgeExtractionByPage(pageInfo, request, authSql);
        return BaseResponse.ok(records, (int) pageInfo.getTotal());
    }

    private String getJudgeExtractionAuthSql(JudgeExtractionSearchDTO request, LogUserInfoVo user) {
        String njcj = sysConfigService.getConfigByCode("njcjCompanyId");
        if (ValidateUtil.isEmpty(njcj)) {
            njcj = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        }
        String authSql = sysUserService.getAuthSql("judge_extraction", user.getId(), "create_uid", OutUserAuthEnum.Approver_Creator.getIndex(), "state", "id", "02");
        if (ValidateUtil.isEmpty(authSql)) {
            return null;
        }
        boolean groupUser = ValidateUtil.isNotEmpty(njcj) && Objects.equals(njcj, user.getCId());
        boolean myParticipation = ValidateUtil.isNotEmpty(request.getParticipationOrFinished()) && request.getParticipationOrFinished().contains("01");
        boolean finished = ValidateUtil.isNotEmpty(request.getParticipationOrFinished()) && request.getParticipationOrFinished().contains("02");
        String finishedSql = "exists (select 1 from Sec_Win_Notice swn where swn.Sec_Id = mn.Sec_Id and swn.Wn_State = 7)";
        String sameCompanyFinishedSql = getSameCompanyFinishedSql(user.getCId(), finishedSql);
        if (finished) {
            if (groupUser) {
                authSql = " where " + finishedSql + " ";
            } else {
                authSql = appendAndAuthSql(appendOrAuthSql(authSql, sameCompanyFinishedSql), finishedSql);
            }
        } else if (groupUser && !myParticipation) {
            authSql = appendOrAuthSql(authSql, finishedSql);
        } else if (!myParticipation) {
            authSql = appendOrAuthSql(authSql, sameCompanyFinishedSql);
        }
        return authSql;
    }

    private String appendOrAuthSql(String authSql, String orSql) {
        if (ValidateUtil.isEmpty(orSql)) {
            return authSql;
        }
        int whereIndex = authSql.toLowerCase().indexOf("where");
        if (whereIndex < 0) {
            return authSql;
        }
        return authSql.substring(0, whereIndex) + "where ((" + authSql.substring(whereIndex + "where".length()) + ") or (" + orSql + ")) ";
    }

    private String appendAndAuthSql(String authSql, String andSql) {
        if (ValidateUtil.isEmpty(andSql)) {
            return authSql;
        }
        return authSql + " and (" + andSql + ") ";
    }

    private String getSameCompanyFinishedSql(String cId, String finishedSql) {
        if (ValidateUtil.isEmpty(cId)) {
            return null;
        }
        return finishedSql + " and exists (select 1 from Sys_User su where su.U_Id = mn.create_uid and su.C_Id = '" + cId.replace("'", "''") + "')";
    }
}




