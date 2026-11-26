package com.jpmsoft.projectProducer.service.conBoq.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.RequestBean;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.config.SnowflakeNew;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.conBoq.ConBoqMapper;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConBoqDto;
import com.jpmsoft.projectProducer.pojo.entity.conBoq.ConBoq;
import com.jpmsoft.projectProducer.pojo.entity.conBoq.ConBoqDtl;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConBoqDtlVo;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConBoqVo;
import com.jpmsoft.projectProducer.service.conBoq.IConBoqDtlService;
import com.jpmsoft.projectProducer.service.conBoq.IConBoqService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ConBoqServiceImpl extends FileImpl<ConBoqMapper, ConBoq, ConBoqDto> implements IConBoqService {

    @Resource
    private IConBoqDtlService conBoqDtlService;

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysFileService sysFileService;

    @Resource
    @Qualifier("mySnowFlake")
    private SnowflakeNew snowflakeNew;
    @Resource
    private ISysUserService sysUserService;

    /**
     * 首页查询
     *
     * @param page
     */
    @Override
    public BaseResponse getBoqList(Page page,String teId) {
        if (ValidateUtil.isNull(page)) {
            page = new Page<>();
        }
        QueryWrapper<ConBoq> queryWrapper = new QueryWrapper<>();
        // TODO 添加查询条件
        Map<String, String> map = page.getRecords().size() > 0 ? (HashMap) page.getRecords().get(0) : null;
        if (ValidateUtil.isNotEmpty(teId))
            queryWrapper.eq("Te_Id", teId);
        if (ValidateUtil.isNotEmpty(map.get("boqContractName"))) {
            queryWrapper.like("boqContractName", map.get("boqContractName"));
        }
        if (ValidateUtil.isNotEmpty(map.get("boqProductName"))) {
            queryWrapper.like("boqProductName", map.get("boqProductName"));
        }
        if (ValidateUtil.isNotEmpty(map.get("beginTime"))) {
            String startTime = map.get("beginTime").replace("T", " ");
            startTime = startTime.substring(0, 19);
            queryWrapper.apply(true, "TO_DAYS(" + "'" + startTime + "'" + ")-TO_DAYS(Boq_Create_Time) = 0");
        }
        if (ValidateUtil.isNotEmpty(map.get("boqCreateUname"))) {
            queryWrapper.like("boqCreateUname", map.get("boqCreateUname"));
        }
        if (ValidateUtil.isNotEmpty(map.get("sortFiled"))) {
            if (map.get("sortType").equals("1")) {
                queryWrapper.orderByDesc(map.get("sortFiled"));
            } else {
                queryWrapper.orderByAsc(map.get("sortFiled"));
            }
        } else {
            queryWrapper.orderByDesc("boqCreateTime");
        }

        IPage page1 = baseMapper.getBoqList(page, queryWrapper);
        return BaseResponse.ok(page1);
    }

    /**
     * 新建
     *
     * @param
     */
    @Override
    public BaseResponse createBoq(RequestBean requestBean, String teId) {
        ConBoq conBoq = JacksonUtils.parseObject(JacksonUtils.toJSONString(requestBean.getInfo()), ConBoq.class);
        if (ValidateUtil.isEmpty(conBoq.getBoqProductId()) || ValidateUtil.isEmpty(conBoq.getBoqContractId())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        //主键
        conBoq.setBoqId(snowflakeNew.nextUUID());
        conBoq.setBoqState(ApprovalStatusEnum.Not_Started.getIndex());
        conBoq.setTeId(teId);
        this.save(conBoq);
        List<ConBoqDtl> dtlPojoList = conBoq.getDtlPojoList();
        if (ValidateUtil.isNotEmpty(dtlPojoList)) {
            for (ConBoqDtl conBoqDtl : dtlPojoList) {
                conBoqDtl.setBoqdId(snowflakeNew.nextUUID())
                        .setBoqId(conBoq.getBoqId());
                conBoqDtlService.save(conBoqDtl);
                List<ConBoqDtl> childNodes = conBoqDtl.getChildNodes();
                if (ValidateUtil.isNotEmpty(childNodes)) {
                    for (ConBoqDtl childNode : childNodes) {
                        childNode.setBoqdParentId(conBoqDtl.getBoqdId());
                        childNode.setBoqdId(snowflakeNew.nextUUID());
                        childNode.setBoqId(conBoq.getBoqId());
                        conBoqDtlService.save(childNode);
                    }
                }
            }
        }
        //调用流程
        sysFileService.saveFileOrUpdate(conBoq.getBoqId(), "清单管理", "0", conBoq.getBoqCreateUid(), conBoq.getBoqCreateUname(), "conBoqService", true);
        //图片处理
        if (ValidateUtil.isNotEmpty(conBoq.getFileRelationId())) {
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(conBoq.getFileRelationId()), conBoq.getBoqId());
        }
        return BaseResponse.ok(conBoq.getBoqId());
    }

    /**
     * 工程清单查看
     *
     * @param
     */
    @Override
    public BaseResponse getBoqById(RequestBean requestBean) {
        ObjectMapper mapper = new ObjectMapper();
        String boqId = mapper.convertValue(requestBean.getInfo(), String.class);
        ConBoq byId = this.getById(boqId);
        if (ValidateUtil.isNull(byId)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        //查询清单扩展表中所关联的全部信息
        LambdaQueryWrapper<ConBoqDtl> eq = new LambdaQueryWrapper<ConBoqDtl>()
                .eq(ConBoqDtl::getBoqId, boqId);
        List<ConBoqDtl> list = conBoqDtlService.list(eq);
        List<ConBoqDtl> collect = list.stream().filter(m -> ValidateUtil.isEmpty(m.getBoqdParentId())).map(
                (m) -> {
                    m.setChildNodes(getChildrens(m, list));
                    return m;
                }
        ).collect(Collectors.toList());
        byId.setDtlPojoList(collect);
        return BaseResponse.ok(byId);
    }

    /**
     * 工程清单修改
     *
     * @param
     */
    @Override
    public BaseResponse updateBop(RequestBean requestBean) {
        ObjectMapper mapper = new ObjectMapper();
        ConBoq conBoq = mapper.convertValue(requestBean.getInfo(), ConBoq.class);
        if (ValidateUtil.isEmpty(conBoq.getBoqId())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        this.updateById(conBoq);
        //  sysFileService.saveFileOrUpdate(conBoqPojo.getBoqId(), "清单管理", conBoqPojo.getBoqState(), conBoqPojo.getBoqCreateUid(), conBoqPojo.getBoqCreateUname(), "conBoqService", false);
        return BaseResponse.ok();
    }

    public BaseResponse updateBoqState(RequestBean requestBean) {
        String info = (String) requestBean.getInfo();
        if (ValidateUtil.isEmpty(info)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        SysFile byId = sysFileService.getById(info);
        ConBoq byId1 = this.getById(info);
        if (ValidateUtil.isNull(byId) || ValidateUtil.isNull(byId1)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        byId1.setBoqState(Integer.parseInt(byId.getFileflowstatus()));
        this.updateById(byId1);
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateConBoq(ConBoqDto request) {
        ConBoq entity = new ConBoq();
        createOrUpdateFileEntity(entity, request);
        if (request.getActionDtl())
            conBoqDtlService.createOrUpdateConBoqDtl(request.getConBoqDtls(), entity.getBoqId());
        return BaseResponse.ok(entity.getBoqId());
    }

    @Override
    public BaseResponse getConBoqList(ConBoqDto request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Boq", uId, "Boq_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Boq_State");
        if (ValidateUtil.isEmpty(authSql)) return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConBoqDto> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("boqTitle").put("boqRemark").put("boqVersion")
                .put("boqCreateUname").put("boqState").put("pName").put("pId").put("conName");
        IPage<ConBoqVo> page = new Page<>(request.getPage(), request.getSize());
        List<ConBoqVo> datas = baseMapper.getConBoqList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConBoqById(String boqId, Boolean queryDtl) {
        ConBoqVo data = baseMapper.getConBoqById(boqId);
        if (ValidateUtil.isNull(queryDtl)) queryDtl=true;
        if (queryDtl) {
            if (ValidateUtil.isNotNull(data)) {
                List<ConBoqDtlVo> dtls = conBoqDtlService.getConBoqDtlByBoqId(boqId);
                data.setConBoqDtls(dtls);
            }
        }
        return BaseResponse.ok(data);
    }



    /*----------------------------------私有方法区--------------------------------------*/

    private List<ConBoqDtl> getChildrens(ConBoqDtl root, List<ConBoqDtl> all) {
        List<ConBoqDtl> children = all.stream().filter(m -> {
            return Objects.equals(m.getBoqdParentId(), root.getBoqdId());
        }).map(
                (m) -> {
                    m.setChildNodes(getChildrens(m, all));
                    return m;
                }
        ).collect(Collectors.toList());
        return children;
    }


}
