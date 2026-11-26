package com.jpmsoft.userProducer.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.Project.CompanyRegisterModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.enums.RegisterStateEnum;
import com.jpmsoft.userProducer.mapper.CompanyRegisterMapper;
import com.jpmsoft.userProducer.mapper.SysCompanyMapper;
import com.jpmsoft.userProducer.mapper.SysUserMapper;
import com.jpmsoft.userProducer.pojo.dto.CompanyDto;
import com.jpmsoft.userProducer.pojo.dto.SupportDocumentDto;
import com.jpmsoft.userProducer.pojo.dto.UserDto;
import com.jpmsoft.userProducer.pojo.entity.CompanyRegister;
import com.jpmsoft.userProducer.pojo.entity.SysCompany;
import com.jpmsoft.userProducer.pojo.entity.SysUser;
import com.jpmsoft.userProducer.pojo.vo.LogUserInfoVo;
import com.jpmsoft.userProducer.service.*;
import com.jpmsoft.userProducer.utils.IdWorker;
import com.jpmsoft.userProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class CompanyRegisterServiceImpl extends ServiceImpl<CompanyRegisterMapper, CompanyRegister> implements CompanyRegisterService {
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private SysCompanyMapper sysCompanyMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysCompanyService sysCompanyService;

    @Override
    public BaseResponse createOrUpdateCompanyRegister(CompanyRegisterModel dto, HttpServletRequest request) {
        log.info("createOrUpdateCompanyRegister");
        List<SupportDocumentDto> documentDtos = new ArrayList<>();
        SupportDocumentDto supportDocumentDto = new SupportDocumentDto();
        supportDocumentDto.setSupportDocument(dto.getLicenseUrl());
        supportDocumentDto.setSupportDocumentFileIDs(dto.getLicenseUrlFileIds());
        documentDtos.add(supportDocumentDto);
        String id;
        if (StrUtil.isEmpty(dto.getId())) {
            id = IdWorker.getGenerateID();
            QueryWrapper<CompanyRegister> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("credit_code", dto.getCreditCode());
            CompanyRegister companyRegister = baseMapper.selectOne(queryWrapper);
            if (companyRegister != null)
                return BaseResponse.fail(BaseMsg.creditCodeExist.replace("｛creditCode｝", dto.getCreditCode()));
            String sessionId = request.getHeader("Se-Auth-Code");
            log.info("createOrUpdateCompanyRegister:{}", sessionId);
            if (ValidateUtil.isEmpty(dto.getAuthCode())) return BaseResponse.fail(BaseMsg.authCodeInvalid);
            if (ValidateUtil.isEmpty(sessionId)) return BaseResponse.fail(BaseMsg.authCodeInvalid);
            Object serverCode = redisUtils.get("session_auto_code_register_" + sessionId);
            log.info("createOrUpdateCompanyRegister:{}", serverCode);
            if (serverCode == null) return BaseResponse.fail(BaseMsg.authCodeInvalid);
            if (!dto.getAuthCode().equalsIgnoreCase(serverCode.toString()))
                return BaseResponse.fail(BaseMsg.authCodeInvalid);
            redisUtils.delete("session_auto_code_register_" + sessionId);
            companyRegister = new CompanyRegister();
            BeanUtils.copyProperties(dto, companyRegister);
            companyRegister.setProvinceCityDistrict(JacksonUtils.toJSONString(dto.getProvinceCityDistrictList()));
            companyRegister.setId(id);
            companyRegister.setCreateUid("68D319413F904195A859973BD4C5702D");
            companyRegister.setCreateUname("admin");
            companyRegister.setCreateTime(LocalDateTime.now());
            saveFile(companyRegister, documentDtos);
            //生成单位信息
            QueryWrapper<SysCompany> queryWrapperCompany = new QueryWrapper<>();
            queryWrapperCompany.eq("C_CreditCode", companyRegister.getCreditCode());
            List<SysCompany> companys = sysCompanyMapper.selectList(queryWrapperCompany);
            log.info("companys:{}", JacksonUtils.toJSONString(companys));
            String cId;
            if (CollectionUtil.isEmpty(companys)) {
                CompanyDto data = getCompanyDto(companyRegister);
                log.info("CreateOrUpdateCompany:{}", JacksonUtils.toJSONString(data));
                BaseResponse response = sysCompanyService.CreateOrUpdateCompany(data);
                if (!response.getSuccess()) return BaseResponse.fail(response.getMessage());
                cId = response.getData().toString();
            } else {
                cId = companys.get(0).getcId();
            }
            companyRegister.setCId(cId);
            //生成账号信息
            QueryWrapper<SysUser> queryWrapperUser = new QueryWrapper<>();
            queryWrapperUser.eq("U_Account", companyRegister.getCreditCode());
            SysUser sysUser = sysUserMapper.selectOne(queryWrapperUser);
            String uId;
            if (sysUser == null) {
                UserDto user = getUserDto(dto, cId);
                log.info("CreateOrUpdateUser:{}", JacksonUtils.toJSONString(user));
                BaseResponse response = sysUserService.CreateOrUpdateUser(user, null);
                if (!response.getSuccess()) {
                    log.info("CreateOrUpdateUser:{}", response.getMessage());
                    return BaseResponse.fail(response.getMessage());
                }
                uId = response.getData().toString();
            } else {
                uId = sysUser.getuId();
            }
            companyRegister.setUserId(uId);
            baseMapper.insert(companyRegister);
        } else {
            id = dto.getId();
            CompanyRegister companyRegister = baseMapper.selectById(id);
            BeanUtils.copyProperties(dto, companyRegister);
            companyRegister.setProvinceCityDistrict(JacksonUtils.toJSONString(dto.getProvinceCityDistrictList()));
            baseMapper.updateById(companyRegister);
        }
        return BaseResponse.ok(id);
    }

    private void saveFile(CompanyRegister entity, List<SupportDocumentDto> documentDtos) {
        log.info("saveFile:{}", JacksonUtils.toJSONString(documentDtos));
        for (SupportDocumentDto supportDocumentDto : documentDtos) {
            sysFileAnnexService.addFormCopyAndRefreshRelationFile(Collections.singletonList(supportDocumentDto.getSupportDocument()), Collections.singletonList(supportDocumentDto.getSupportDocumentFileIDs()), entity.getId());
        }
    }

    @Override
    public BaseResponse approveCompanyRegister(CompanyRegisterModel request) {
        log.info("approveCompanyRegister");
        CompanyRegister companyRegister = baseMapper.selectById(request.getId());
        if (companyRegister == null) return BaseResponse.fail("未找到数据");
        companyRegister.setStatus(request.getStatus());
        if (RegisterStateEnum.Rejected.getkey().equals(request.getStatus()) && StrUtil.isEmpty(request.getAuditComment())) {
            return BaseResponse.fail("请填写驳回原因");
        }
        companyRegister.setAuditComment(request.getAuditComment());
        companyRegister.setStateTime(LocalDateTime.now());
        List<SupportDocumentDto> documentDtos = new ArrayList<>();
        SupportDocumentDto supportDocumentDto = new SupportDocumentDto();
        supportDocumentDto.setSupportDocument(request.getDeclarationAttachment());
        supportDocumentDto.setSupportDocumentFileIDs(request.getDeclarationAttachmentFileIds());
        documentDtos.add(supportDocumentDto);
        saveFile(companyRegister, documentDtos);
        baseMapper.updateById(companyRegister);
        return BaseResponse.ok(companyRegister.getId());
    }

    @NotNull
    private static UserDto getUserDto(CompanyRegisterModel companyRegister, String cId) {
        UserDto user = new UserDto();
        user.setuName(companyRegister.getName());
        user.setuAccount(companyRegister.getCreditCode());
        user.setuPassword(companyRegister.getUPassword());
        user.setcId(cId);
        user.setcName(companyRegister.getName());
        user.setuState("1");
        user.setuType("2");
        user.setuCreateuName("admin");
        user.setuCreateuId("68D319413F904195A859973BD4C5702D");
        return user;
    }

    @NotNull
    private static CompanyDto getCompanyDto(CompanyRegister companyRegister) {
        CompanyDto data = new CompanyDto();
        data.setcName(companyRegister.getName());
        data.setcType(companyRegister.getType());
        data.setcCreditcode(companyRegister.getCreditCode());
        data.setcContactperson(companyRegister.getContactPerson());
        data.setcContactpersontel(companyRegister.getContactPhone());
        data.setcState("1");
        data.setcCreateuId("68D319413F904195A859973BD4C5702D");
        data.setcCreateuName("admin");
        return data;
    }

    @Override
    public BaseResponse getCompanyRegisterList(CompanyRegisterModel request, String uid) {
        log.info("getCompanyRegisterList");
        LogUserInfoVo userInfoVo = redisUtils.getUserInfoByUid(uid);
        QueryMap<CompanyRegisterModel> queryMap = new QueryMap<>(request);
        queryMap.put("creditCode").put("name").put("status").put("createUid");
        SysUser sysUser = sysUserMapper.selectById(uid);
        if (!"1".equals(sysUser.getuType())) {
            queryMap.put("cId", userInfoVo.getCId());
        }
        IPage<CompanyRegisterModel> page = new Page<>(request.getPage(), request.getSize());
        List<CompanyRegisterModel> companyRegisters = baseMapper.getCompanyRegisterList(queryMap.getQuery(), page);
        return BaseResponse.ok(companyRegisters);
    }

    @Override
    public BaseResponse getCompanyRegister(String id) {
        CompanyRegisterModel companyRegisterModel = baseMapper.getCompanyRegister(id);
        if (companyRegisterModel == null) {
            return BaseResponse.fail("未找到数据");
        }
        companyRegisterModel.setProvinceCityDistrictList(JacksonUtils.parseArray(companyRegisterModel.getProvinceCityDistrict(), String.class));
        return BaseResponse.ok(companyRegisterModel);
    }
}
