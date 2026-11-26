package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.Oa.OaNoticeRequest;
import com.jpmsoft.common.model.response.FileResponse;
import com.jpmsoft.common.model.response.Oa.OaNoticeResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.RequestBean;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.oa.OaNoticeMapper;
import com.jpmsoft.projectProducer.mapper.oa.OaNoticeViewRecordsMapper;
import com.jpmsoft.projectProducer.mapper.other.SysUserMapper;
import com.jpmsoft.projectProducer.pojo.dto.other.UserDto;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaNotice;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaNoticeRead;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaNoticeViewRecords;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.vo.sys.SysUserVo;
import com.jpmsoft.projectProducer.service.oa.IOaNoticeReadService;
import com.jpmsoft.projectProducer.service.oa.IOaNoticeService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class OaNoticeImpl extends ServiceImpl<OaNoticeMapper, OaNotice> implements IOaNoticeService {
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private UserProducerFeign userProducerFeign;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IOaNoticeReadService oaNoticeReadService;
    /**
     * 收发文管理_查看记录 mapper
     */
    @Resource
    private OaNoticeViewRecordsMapper oaNoticeViewRecordsMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "nId", codeParam = "nCode", method = "setnCode", codeRule = "OaNotice", beanClass = OaNoticeRequest.class)
    public BaseResponse createOrUpdateOaNotice(OaNoticeRequest request) {
        OaNotice entity = new OaNotice();
        if (ValidateUtil.isEmpty(request.getnCode()))
            return BaseResponse.fail(ResMsg.codeIsEmpty);
        BeanUtils.copyProperties(request, entity);
//        if (ValidateUtil.isEmpty(request.getnCode()))
//            return  BaseResponse.fail(ResMsg.notReqParam);
//        QueryWrapper<OaNoticePojo> wrapper = new QueryWrapper<>();
//        wrapper.eq("N_Code", request.getnCode());
//        if (!ValidateUtil.isEmpty(entity.getNId()))
//            wrapper.ne("N_Id", request.getnId());
//        if (baseMapper.selectCount(wrapper) > 0)
//            return  BaseResponse.fail(ResMsg.recordExist);
        if (ValidateUtil.isEmpty(request.getnId())) {
            entity.setNId(GenerateUUID.getUUID());
            entity.setNCreateTime(new Date());
            entity.setNStateTime(null);
            entity.setNState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getNId(), request.gettId(), entity.getNState() + "", entity.getNCreateUid(), entity.getNCreateUname()
                    , entity.getNCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getnThumbnail()), entity.getNId());
            //富文本文件新增时无法确认用户是否删除文件，所以最终也要根据IDS去比对
            sysFileAnnexService.refreshRelationFile(request.getRteFileRelationId(), request.getRteFileIds(), entity.getNId());

        } else {
            OaNotice model = baseMapper.selectById(request.getnId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getNState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setNCreateUid(model.getNCreateUid());
            entity.setNCreateTime(model.getNCreateTime());
            entity.setNCreateUname(model.getNCreateUname());
            entity.setNState(model.getNState());
            entity.setNStateTime(model.getNStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getNId(), request.gettId(), entity.getNState() + "", entity.getNCreateUid(), entity.getNCreateUname()
                    , entity.getNCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "update", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getnThumbnail(), request.getRteFileRelationId()),
                    Arrays.asList(request.getFileIds(), request.getnThumbnailIds(), request.getRteFileIds()), entity.getNId());
        }
        String specificScopeid = request.getSpecificScopeid();

        List<OaNoticeRead> entitys = new ArrayList<>();

        if (StringUtils.isNotBlank(specificScopeid)){
            List<String> ids = Arrays.asList(specificScopeid.split(","));
            //项目id
            if ("2".equals(request.getRangeType())){
                List<SysUserVo> data = sysUserMapper.getUserByPid(ids);
                saveOaNticeRead(data,entitys,entity);
            }
            //组织机构id
            if ("3".equals(request.getRangeType())){
                UserDto user = new UserDto();
                QueryMap<UserDto> params = new QueryMap<>(user);
                params.put("depIds", ids);
                List<SysUserVo> data = sysUserMapper.getUserListByMoreCondition(params.getQuery());
                saveOaNticeRead(data,entitys,entity);
            }
            //人员id
            if ("4".equals(request.getRangeType())){
                List<SysUserVo> data = sysUserMapper.getUesrInfoByIds(ids);
                saveOaNticeRead(data,entitys,entity);
            }


        }
        //全部人员id
        if ("1".equals(request.getRangeType())){
            List<SysUser> sysUsers = sysUserMapper.selectList(new LambdaQueryWrapper<>());
            List<SysUserVo> sysUserVos = com.jpmsoft.common.utils.BeanUtils.copyListData(sysUsers, SysUserVo.class);
            saveOaNticeRead(sysUserVos,entitys,entity);
        }
        oaNoticeReadService.saveBatch(entitys);
        return BaseResponse.ok(entity.getNId());
    }

    private void saveOaNticeRead(List<SysUserVo> datas,List<OaNoticeRead> entitys, OaNotice entity){
        if (CollectionUtils.isNotEmpty(datas)){
            datas.forEach(sysUserVo -> {
                OaNoticeRead oaNoticeRead = new OaNoticeRead();
                oaNoticeRead.setNId(entity.getNId());
                //0-未读，1-已读
                oaNoticeRead.setIsRead("0");
                oaNoticeRead.setNrId(GenerateUUID.getUUID());
                oaNoticeRead.setNrUid(sysUserVo.getuId());
                oaNoticeRead.setNrUname(sysUserVo.getuName());
                entitys.add(oaNoticeRead);
            });
        }
    }
    @Override
    public BaseResponse getOaNoticeByNid(String nId) {
        OaNoticeResponse entity = baseMapper.getOaNoticeByNid(nId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (!ValidateUtil.isEmpty(entity.getFileRelationId())) {
            List<FileResponse> files = sysFileAnnexService.getFileByFileRelationId(entity.getFileRelationId());
            entity.setFiles(files == null ? new ArrayList<>() : files);
        }
        return BaseResponse.ok(entity);
    }

    @Override
    public BaseResponse getOaNoticeList(OaNoticeRequest request) {
        QueryMap<OaNoticeRequest> params = new QueryMap<>(request);
        params.put("queryMain").put("nType").put("nTypes").put("nKeyword").put("nCode")
                .put("nTitle").put("nState").put("rangeType").put("nCreateUid");
        IPage<OaNoticeResponse> page = new Page<>(request.getPage(), request.getSize());
        List<OaNoticeResponse> datas = baseMapper.getOaNoticeList(page, params.getQuery(), request.getSpecificScopeids());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getUserOaNoticeList(OaNoticeRequest request) {
        if (ValidateUtil.isEmpty(request.getUserId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        QueryMap<OaNoticeRequest> params = new QueryMap<>(request);
        params.put("userId").put("queryMain").put("nType");
        IPage<OaNoticeResponse> page = new Page<>(request.getPage(), request.getSize());
        List<OaNoticeResponse> datas = baseMapper.getUserOaNoticeList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse deleteOaNoticeByNid(String nId) {
        OaNotice model = baseMapper.selectById(nId);
        if (model == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (oaNoticeReadService.havingOaNoticeReadListByNid(nId))
            return BaseResponse.fail(ResMsg.readRecord);
        baseMapper.deleteById(nId);
        return BaseResponse.ok();
    }

    /**
     * 添加查看记录
     *
     * @param requestBean
     * @author:zhangl
     */
    @Override
    public BaseResponse createOrUpdateViewRecords(RequestBean requestBean) {
        OaNoticeViewRecords oaNoticeRead = JacksonUtils.parseObject(JacksonUtils.toJSONString(requestBean.getInfo()), OaNoticeViewRecords.class);
        LambdaQueryWrapper<OaNoticeViewRecords> eq = new LambdaQueryWrapper<OaNoticeViewRecords>()
                .eq(OaNoticeViewRecords::getOaNoticeObjectId, oaNoticeRead.getOaNoticeObjectId())
                .eq(OaNoticeViewRecords::getUserId, oaNoticeRead.getUserId());
        List<OaNoticeViewRecords> oaNoticeViewRecords = oaNoticeViewRecordsMapper.selectList(eq);
        if (oaNoticeViewRecords.size() > 0) {
            oaNoticeViewRecordsMapper.updateById(oaNoticeRead);
        } else {
            oaNoticeRead.setObjectId(GenerateUUID.getUUID());
            oaNoticeViewRecordsMapper.insert(oaNoticeRead);
        }
        return null;
    }

    /**
     * 获取分页列表 查看记录
     */
    @Override
    public BaseResponse getViewRecordsById(RequestBean requestBean) {
        Page page = JacksonUtils.parseObject(JacksonUtils.toJSONString(requestBean.getInfo()), Page.class);
        if (ValidateUtil.isEmpty(page)) {
            page = new Page();
        }
        // TODO 添加查询条件
        Map<String, String> queryMap = page.getRecords().size() > 0 ? (HashMap) page.getRecords().get(0) : null;
        if (ValidateUtil.isEmpty(queryMap.get("oaNoticeObjectId"))) {
            return BaseResponse.ok();
        }
        LambdaQueryWrapper<OaNoticeViewRecords> oaNoticeObjectId = new LambdaQueryWrapper<OaNoticeViewRecords>()
                .eq(OaNoticeViewRecords::getOaNoticeObjectId, queryMap.get("oaNoticeObjectId"));
        Page page1 = oaNoticeViewRecordsMapper.selectPage(page, oaNoticeObjectId);
        return BaseResponse.ok(page1);
    }
}
