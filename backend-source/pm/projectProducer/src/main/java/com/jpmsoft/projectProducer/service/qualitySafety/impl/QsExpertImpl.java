package com.jpmsoft.projectProducer.service.qualitySafety.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.requestAndResponse.Qs.QsExpertModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.common.utils.VoInfoUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.qualitySafety.QsExpertMapper;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.QsExpertLogicalConfDTO;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.*;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsExpert;
import com.jpmsoft.projectProducer.service.other.*;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsExpertCertificateService;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsExpertService;
import com.jpmsoft.projectProducer.utils.DateUtil;
import com.jpmsoft.projectProducer.utils.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QsExpertImpl extends ServiceImpl<QsExpertMapper, QsExpert> implements IQsExpertService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private UserProducerFeign userProducerFeign;

    @Resource
    private IQsExpertCertificateService qsExpertCertificateService;
    @Override
    public BaseResponse resetPlaintextNum() {
        QueryWrapper<QsExpert> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("Ex_Id_Number");
        wrapper.isNull("Ex_Plaintext_Num");
        List<QsExpert> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            data = data.stream().peek(d -> {
                d.setExPlaintextNum(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber()));
            }).collect(Collectors.toList());
            updateBatchById(data);
        }
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateQsExpert(QsExpertModel request) {
        return createOrUpdate(request, false, false);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateQsExpertCurrent(QsExpertModel request) {
        return createOrUpdate(request, true, false);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateQsExpertVerify(QsExpertModel request) {
        return createOrUpdate(request, true, true);
    }


    public BaseResponse createOrUpdate(QsExpertModel request, boolean idCard, boolean only) {
        QsExpert entity = new QsExpert();
        BeanUtils.copyProperties(request, entity);
        QsExpert model = null;
        if (idCard) {
            if (ValidateUtil.isAllEmpty(request.getExIdNumber(), request.getExId())) {
                //创建数据时 传入身份证以传入的为准 没传入身份证则根据用户信息查找身份证信息
                if (ValidateUtil.isNotEmpty(request.getuId())) {
                    SysUser user = sysUserService.getById(request.getuId());
                    entity.setExIdNumber(user.getUIdcard());
                } else entity.setExIdNumber(null);
            } else if (ValidateUtil.isNotEmpty(request.getExId())) {
                model = baseMapper.selectById(request.getExId());
                if (model == null) return BaseResponse.fail(ResMsg.infoNotExist);
                //编辑数据时如果不修改则取旧值
                if (ValidateUtil.isEmpty(entity.getExIdNumber()))
                    entity.setExIdNumber(model.getExIdNumber());
            }
        }
        if (ValidateUtil.isNotEmpty(entity.getExIdNumber()))
            entity.setExPlaintextNum(apiUtilsService.SelfSm2Decrypt(entity.getExIdNumber()));
        if (only && expertExists(entity.getExPlaintextNum(), entity.getExWorkTime(),entity.getExApplicantId(), request.getExId(), request.getTeId()))
            return BaseResponse.fail(ResMsg.recordExist);
        if (ValidateUtil.isEmpty(request.getExId())) {
            entity.setExId(GenerateUUID.getUUID());
            entity.setExCreateTime(new Date());
            entity.setExState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getExId(), request.gettId(), entity.getExState() + "", entity.getExCreateUid(), entity.getExCreateUname(), entity.getExCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getExCardFile(), request.getExDegree(), request.getExPerformFile(), request.getExTitleFile(), request.getExPromiseFile(), request.getExAvoidFile(), request.getExOtherFile(), request.getFileRelationId()), entity.getExId());
        } else {
            entity.setExCreateTime(model.getExCreateTime());
            entity.setExCreateUid(model.getExCreateUid());
            entity.setExCreateUname(model.getExCreateUname());
            entity.setExState(model.getExState());
            entity.setExStateTime(model.getExStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getExId(), request.gettId(), entity.getExState() + "", entity.getExCreateUid(), entity.getExCreateUname(), entity.getExCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getExCardFile(), request.getExDegree(), request.getExPerformFile(), request.getExTitleFile(), request.getExPromiseFile(), request.getExAvoidFile(), request.getExOtherFile(), request.getFileRelationId()), Arrays.asList(request.getExCardFileIds(), request.getExDegreeIds(), request.getExPerformFileIds(), request.getExTitleFileIds(), request.getExPromiseFileIds(), request.getExAvoidFileIds(), request.getExOtherFileIds(), request.getFileIds()), entity.getExId());
        }
        qsExpertCertificateService.createOrUpdateQsExpertCertificate(request.getQsExpertCertificates(), entity.getExId());
        return BaseResponse.ok(entity.getExId());
    }

    @Override
    public BaseResponse getQsExpertList(QsExpertModel request) {
        QueryMap<QsExpertModel> params = new QueryMap<>(request);
        params.put("cId").put("cName").put("cNameHistory").put("exName")
                .put("exSex").put("exIdType").put("exIdNumber").put("exLocation")
                .put("exPolitics").put("exEducation").put("exMajor").put("exSchool")
                .put("exWorkStat").put("exWorkUnit").put("exPost").put("exTitle")
                .put("exType").put("exTitleNumber").put("exProfessionalCategory").put("exApplicantCId")
                .put("exIndustry").put("exCellphoneNumber").put("exState").put("exStatus")
                .put("exCreateUname").put("exCategory").put("exPracticeCode").put("exApplicantId");
        List<QsExpertModel> datas = baseMapper.getQsExpertList(params.getQuery());
        Integer total = baseMapper.getQsExpertListCount(params.getQuery());
        if (!CollectionUtils.isEmpty(datas)) {
            List<QsExpertModel> data = datas.stream().peek(d -> {
                d.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber())));
            }).collect(Collectors.toList());
            return BaseResponse.ok(data, total);
        }
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getQsExpertById(String exId) {
        QsExpertModel data = baseMapper.getQsExpertById(exId);
        if (data != null)
            if (ValidateUtil.isNotEmpty(data.getExIdNumber()))
                data.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(data.getExIdNumber())));
        return BaseResponse.ok(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteQsExpertById(String exId) {
        QsExpert model = baseMapper.selectById(exId);
        if (model == null) return BaseResponse.fail(ResMsg.infoNotExist);
        //if (secSelectionRecordDtlService.getExpertCount(exId) > 0) return BaseResponse.fail(ResMsg.delAssociated);
        baseMapper.deleteById(exId);
        sysFileService.deleteFileById(exId);
        return BaseResponse.ok();
    }



    private void extractExpert(List<QsExpertModel> datas, List<QsExpertModel> allExpert, int num) {
        if (num >= allExpert.size()) {
            datas.addAll(allExpert);
        } else {
            if (num > 0) {
                int index = MathUtil.getRandomNumberInRange(0, allExpert.size() - 1);
                datas.add(allExpert.get(index));
                allExpert.remove(index);
                extractExpert(datas, allExpert, num - 1);
            }
        }
    }

    @Override
    public boolean expertExists(String idNum, String id, String teId) {
        idNum = apiUtilsService.SelfSm2Decrypt(idNum);
        QsExpertLogicalConfDTO conf = sysConfigService.getQsExpertLogicalConf();
        Integer age = DateUtil.getAge(idNum);
        if (age != null && age >= conf.getMaxAge()) throw new JPMException(ResMsg.maxAge);
        QueryWrapper<QsExpert> wrapper = new QueryWrapper<>();
        wrapper.eq("Ex_Plaintext_Num", idNum);
        if (ValidateUtil.isNotEmpty(teId)) wrapper.eq("Te_Id", teId);
        if (ValidateUtil.isNotEmpty(id)) wrapper.ne("Ex_Id", id);
        wrapper.ne("Ex_State", ApprovalStatusEnum.Invalid.getIndex());
        return baseMapper.exists(wrapper);
    }

    /**
     * 申请人ID唯一
     * 校验身份证年龄
     *
     * @return
     */
    public boolean expertExists(String idNum, String uid, String id, String teId) {
        QsExpertLogicalConfDTO conf = sysConfigService.getQsExpertLogicalConf();
        Integer age = DateUtil.getAge(idNum);
        if (age != null && age >= conf.getMaxAge()) throw new JPMException(ResMsg.maxAge);
        QueryWrapper<QsExpert> wrapper = new QueryWrapper<>();
        wrapper.eq("Ex_Applicant_Id", uid);
        if (ValidateUtil.isNotEmpty(teId)) wrapper.eq("Te_Id", teId);
        if (ValidateUtil.isNotEmpty(id)) wrapper.ne("Ex_Id", id);
        wrapper.ne("Ex_State", ApprovalStatusEnum.Invalid.getIndex());
        return baseMapper.exists(wrapper);
    }

    public boolean expertExists(String idNum,Date exWorkTime ,String uid, String id, String teId) {
        QsExpertLogicalConfDTO conf = sysConfigService.getQsExpertLogicalConf();
        /*Integer exSysWorkYearLimit = conf.getExSysWorkYear();
        if(exSysWorkYearLimit!=-1){//-1不限制
            Integer workYear = getWorkYear(exWorkTime);
            if (workYear < exSysWorkYearLimit) throw new JPMException("参加集团系统工作时间最小为"+exSysWorkYearLimit+"年");
        }*/
        Integer age = DateUtil.getAge(idNum);
        if (age != null && age >= conf.getMaxAge()) throw new JPMException(ResMsg.maxAge);
        QueryWrapper<QsExpert> wrapper = new QueryWrapper<>();
        wrapper.eq("Ex_Applicant_Id", uid);
        if (ValidateUtil.isNotEmpty(teId)) wrapper.eq("Te_Id", teId);
        if (ValidateUtil.isNotEmpty(id)) wrapper.ne("Ex_Id", id);
        wrapper.ne("Ex_State", ApprovalStatusEnum.Invalid.getIndex());
        return baseMapper.exists(wrapper);
    }

    /**
     * 获取工作年限
     * @param workTime 工作时间
     * @return
     */
    private Integer getWorkYear(Date workTime){
        // 入职日期
        LocalDate hireDate = LocalDate.parse(cn.hutool.core.date.DateUtil.format(workTime,"yyyy-MM-dd"));
        // 当前日期
        LocalDate currentDate = LocalDate.now();
        // 计算工作年限
        Period experience = Period.between(hireDate, currentDate);
        int years = experience.getYears();
        return years;
    }

    @Override
    public void updateQsExpertStatus(String exId, Integer status) {
        baseMapper.updateQsExpertStatus(exId, status);
    }
    
}
