package com.jpmsoft.projectProducer.service.conSupNotice.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.ConBop.ConSupNoticeRequest;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.RequestBean;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.config.SnowflakeNew;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.conSupNotice.ConSupNoticeReplyMapper;
import com.jpmsoft.projectProducer.pojo.entity.conSupNotice.ConSupNotice;
import com.jpmsoft.projectProducer.pojo.entity.conSupNotice.ConSupNoticeReply;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.service.conSupNotice.IConSupNoticeReplyService;
import com.jpmsoft.projectProducer.service.conSupNotice.IConSupNoticeService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.DateUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ConSupNoticeReplyServiceImpl extends ServiceImpl<ConSupNoticeReplyMapper, ConSupNoticeReply> implements IConSupNoticeReplyService {
    @Resource
    private ISysFileService sysFileService;
    @Resource
    @Qualifier("mySnowFlake")
    private SnowflakeNew snowflakeNew;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IConSupNoticeService conSupNoticeService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private ISysConfigService sysConfigService;

    /**
     * 新增或者修改  回复得通知单
     *
     * @param requestBean@return
     */
    @Override
    public BaseResponse createOrUpdateConSupNoticeReply(RequestBean requestBean, String teId) {
        ConSupNotice conSupNotice = JacksonUtils.parseObject(JacksonUtils.toJSONString(requestBean.getInfo()), ConSupNotice.class);
        ConSupNoticeReply conSupNoticeReply = conSupNotice.getNoticeReplyPojo();
        if (ValidateUtil.isEmpty(conSupNoticeReply.getSnId())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        conSupNoticeReply.setTeId(teId);
        if (ValidateUtil.isNotEmpty(conSupNoticeReply.getSnrId())) {
            // 修改
            ConSupNoticeReply old = this.getById(conSupNoticeReply.getSnrId());
            if (old == null) return BaseResponse.fail(ResMsg.infoNotExist);
            conSupNoticeReply.setSnrCreateUname(old.getSnrCreateUname());
            conSupNoticeReply.setSnrCreateTime(old.getSnrCreateTime());
            conSupNoticeReply.setSnrCreateUid(old.getSnrCreateUid());
            this.updateById(conSupNoticeReply);
            // 新增 调用流程
            sysFileService.saveFileOrUpdate(conSupNoticeReply.getSnrId(), "通知单管理回复", conSupNoticeReply.getSnrState(), conSupNoticeReply.getSnrCreateUid(), conSupNoticeReply.getSnrCreateUname(), "conSupNoticeReplyService", false);
            // 附件处理
            sysFileAnnexService.refreshRelationFile(conSupNoticeReply.getFileRelationId(), conSupNoticeReply.getRteFileIds(), conSupNoticeReply.getSnrId());
        } else {
            conSupNoticeReply.setSnrId(snowflakeNew.nextUUID());
            conSupNoticeReply.setSnrCreateTime(DateUtil.dateFormatToDate(new Date()));
            this.save(conSupNoticeReply);
            ConSupNotice conSupNotice1 = new ConSupNotice();
            conSupNotice1.setSnId(conSupNotice.getSnId());
            conSupNotice1.setSnReplyState("1");
            conSupNoticeService.updateById(conSupNotice1);
            // 新增 调用流程
            sysFileService.saveFileOrUpdate(conSupNoticeReply.getSnrId(), "通知单管理回复", "0", conSupNoticeReply.getSnrCreateUid(), conSupNoticeReply.getSnrCreateUname(), "conSupNoticeReplyService", true);
            // 附件处理
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(conSupNoticeReply.getFileRelationId()), conSupNoticeReply.getSnrId());
        }
        return BaseResponse.ok(conSupNoticeReply.getSnrId());
    }


    public BaseResponse updateSnrState(RequestBean requestBean) {
        String info = (String) requestBean.getInfo();
        if (ValidateUtil.isEmpty(info)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        SysFile byId = sysFileService.getById(info);
        ConSupNoticeReply byId1 = this.getById(info);
        if (ValidateUtil.isNull(byId) || ValidateUtil.isNull(byId1)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        byId1.setSnrState(byId.getFileflowstatus());
        this.updateById(byId1);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getConSupNoticeReplyPageList(Page page, String teId, String uId) {
        String authSql = getAuthSql(OutUserAuthEnum.Organization.getIndex(), uId);
        if (ValidateUtil.isNull(page)) {
            page = new Page<>();
        }
        QueryWrapper<ConSupNoticeRequest> queryWrapper = new QueryWrapper<>();
        // TODO 添加查询条件
        Map<String, String> map = page.getRecords().size() > 0 ? (HashMap) page.getRecords().get(0) : null;
        if (ValidateUtil.isNotEmpty(teId)) {
            queryWrapper.eq("teId", teId);
        }
        if (ValidateUtil.isNotEmpty(map.get("pId"))) {
            queryWrapper.like("pId", map.get("pId"));
        }
        // 项目名
        if (ValidateUtil.isNotEmpty(map.get("pName"))) {
            queryWrapper.like("pName", map.get("pName"));
        }
        // 合同名
        if (ValidateUtil.isNotEmpty(map.get("conName"))) {
            queryWrapper.like("conName", map.get("conName"));
        }
        // 通知单编号
        if (ValidateUtil.isNotEmpty(map.get("snNo"))) {
            queryWrapper.eq("snNo", map.get("snNo"));
        }
        // 编号
        if (ValidateUtil.isNotEmpty(map.get("snCode"))) {
            queryWrapper.eq("snCode", map.get("snCode"));
        }
        // 文件标题
        if (ValidateUtil.isNotEmpty(map.get("snReplyDeadline"))) {
            queryWrapper.eq("snReplyDeadline", map.get("snReplyDeadline"));
        }
        if (ValidateUtil.isNotEmpty(map.get("snState"))) {
            queryWrapper.eq("snState", map.get("snState"));
        }
        if (ValidateUtil.isNotEmpty(map.get("snrState"))) {
            queryWrapper.eq("snrState", map.get("snrState"));
        }
        // 创建人名称
        if (ValidateUtil.isNotEmpty(map.get("snCreateUname"))) {
            queryWrapper.like("snCreateUname", map.get("snCreateUname"));
        }
        // 发文单位
        if (ValidateUtil.isNotEmpty(map.get("snReceivingUnitName"))) {
            queryWrapper.like("snReceivingUnitName", map.get("snReceivingUnitName"));
        }
        // 创建时间
        if (ValidateUtil.isNotEmpty(map.get("snCreateTime"))) {
            String startTime = map.get("snCreateTime").replace("T", " ");
            startTime = startTime.substring(0, 19);
            queryWrapper.apply(true, "TO_DAYS(" + "'" + startTime + "'" + ")-TO_DAYS(snCreateTime) = 0");
        }
        queryWrapper.eq("snNeedReply", "1");
        // 更新时间查询
        if (ValidateUtil.isNotEmpty(map.get("sortFiled"))) {
            if (map.get("sortType").equals("1")) {
                queryWrapper.orderByDesc(map.get("sortFiled"));
            } else {
                queryWrapper.orderByAsc(map.get("sortFiled"));
            }
        } else {
            queryWrapper.orderByDesc("snCreateTime");
        }
        IPage page1 = baseMapper.getConSupNoticeReplyList(page, queryWrapper, authSql);
        List<ConSupNoticeRequest> records = page1.getRecords();
        records.stream().forEach(item -> {
            if (ValidateUtil.isNull(item.getSnrState())) {
                item.setSnrState(0);
            }
        });

        return BaseResponse.ok(page1);
    }

    private String getAuthSql(Integer OutUserAuthEnum, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "P_Create_User_ID", OutUserAuthEnum, "State");
        if (ValidateUtil.isEmpty(authSql))
            throw new JPMException(ResMsg.noPermission);
        String code = "projectDateLimit";
        String str = sysConfigService.getConfigByCode(code);
        if (StringUtils.isNotEmpty(str)) {
            StringBuffer sb = new StringBuffer();
            sb.append(authSql);
            sb.append("and fpro.P_Create_Time >='" + str + "'");
            authSql = sb.toString();
        }
        return authSql;
    }


    public BaseResponse getConSupNoticeReplyById(RequestBean requestBean) {
        if (ValidateUtil.isNull(requestBean.getInfo())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        String info = (String) requestBean.getInfo();
        ConSupNoticeReply byId = this.getById(info);
        if (ValidateUtil.isNull(byId)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        QueryWrapper<ConSupNotice> conSupNoticePojoQueryWrapper = new QueryWrapper<>();
        conSupNoticePojoQueryWrapper.eq("Sn_Id", byId.getSnId());
        ConSupNotice one = conSupNoticeService.getOne(conSupNoticePojoQueryWrapper);
        one.setNoticeReplyPojo(byId);
        return BaseResponse.ok(one);
    }

    /**
     * 获取回复次序
     *
     * @param pId
     * @param snId
     * @return
     */
    @Override
    public BaseResponse getReplyOrder(String pId, String snId) {
        if (ValidateUtil.isAnObjEmpty(pId, snId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        long count = this.count(Wrappers.<ConSupNoticeReply>lambdaQuery()
                .eq(ConSupNoticeReply::getPid, pId)
                .eq(ConSupNoticeReply::getSnId, snId)
                .eq(ConSupNoticeReply::getSnrState, ApprovalStatusEnum.Already_Over.getIndex())
        );
        return BaseResponse.ok(count + 1);
    }
}
