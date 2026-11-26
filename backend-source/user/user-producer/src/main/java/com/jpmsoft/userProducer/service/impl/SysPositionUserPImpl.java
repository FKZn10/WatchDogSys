package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.ReportedPersonnelDtlModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysPositionUserPMapper;
import com.jpmsoft.userProducer.pojo.dto.DepartmentPCjConfDTO;
import com.jpmsoft.userProducer.pojo.dto.PositionUserPDto;
import com.jpmsoft.userProducer.pojo.dto.UserPositionPDTO;
import com.jpmsoft.userProducer.pojo.entity.SysPositionUserP;
import com.jpmsoft.userProducer.service.ISysConfigEntityService;
import com.jpmsoft.userProducer.service.ISysPositionUserPService;
import com.jpmsoft.userProducer.utils.CheckNullUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysPositionUserPImpl extends ServiceImpl<SysPositionUserPMapper, SysPositionUserP> implements ISysPositionUserPService {

    @Resource
    private ISysConfigEntityService configEntityService;

    @Override
    public BaseResponse getProjectPositionUser(String pId, String jtOrGl) {
        DepartmentPCjConfDTO conf = configEntityService.getDepartmentPCjConf();
        if ("jt".equalsIgnoreCase(jtOrGl)) {
            return BaseResponse.ok(baseMapper.selectPositionPUser(pId, Collections.singletonList(conf.getJtCode())));
        } else if ("yzdw".equalsIgnoreCase(jtOrGl)) {
            return BaseResponse.ok(baseMapper.selectPositionPOwner(pId, conf.getYzdwoCode(), conf.getYzdwtCode()));
        } else if ("cjdw".equalsIgnoreCase(jtOrGl)) {
            List<String> codes = Arrays.asList(
                    conf.getCjdwCode(),
                    conf.getJsdwCode(),
                    conf.getJldwCode(),
                    conf.getSgdwCode(),
                    conf.getKcdwCode(),
                    conf.getSjdwCode(),
                    conf.getGzsjdwCode()
            );
            return BaseResponse.ok(baseMapper.selectPositionPUser(pId, codes));
        } else {
            return BaseResponse.ok(baseMapper.selectPositionPUser(pId, Collections.singletonList(conf.getGldwCode())));
        }
    }

    @Override
    public BaseResponse CreateOrDeletePP_U(PositionUserPDto request) {
        QueryWrapper<SysPositionUserP> wrapper = new QueryWrapper<>();
        wrapper.eq("PP_ID", request.getPpId());
        wrapper.eq("U_ID", request.getuId());
        wrapper.last("limit 1");
        SysPositionUserP pojo = baseMapper.selectOne(wrapper);

        if (request.getType().equalsIgnoreCase("CREATE")) {
            if (!CheckNullUtil.checkObject(pojo))
                return BaseResponse.fail(BaseMsg.infoIsExist);
            SysPositionUserP entity = new SysPositionUserP();
            entity.setPpId(request.getPpId());
            entity.setuId(request.getuId());
            entity.setPupId(GenerateUUID.getUUID());
            Integer maxSort = baseMapper.getMaxPupSort(request.getPdId());
            entity.setPupSort(ValidateUtil.isNull(maxSort) ? 1 : maxSort + 1);
            baseMapper.insert(entity);
            return BaseResponse.ok();
        } else {
            if (!CheckNullUtil.checkObject(pojo)) {
                baseMapper.deleteById(pojo.getPupId());
                return BaseResponse.ok();
            } else {
                return BaseResponse.fail(BaseMsg.infoNotExist);
            }
        }
    }

    @Override
    public BaseResponse updatePositionUserP(PositionUserPDto request) {
        SysPositionUserP entity = baseMapper.selectById(request.getPupId());
        if (ValidateUtil.isNull(entity)) return BaseResponse.fail(BaseMsg.infoNotExist);
        BeanUtils.copyProperties(request, entity);
        baseMapper.updateById(entity);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse updateSortUpOrDown(String pupId, String pdId, String action) {
        SysPositionUserP entity = baseMapper.selectById(pupId);
        if (ValidateUtil.isNull(entity)) return BaseResponse.fail(BaseMsg.infoNotExist);
        QueryWrapper<SysPositionUserP> wrapper = new QueryWrapper<>();
        wrapper.apply(String.format(" Pp_ID in (select Pp_ID from SYS_Position_P  where PD_ID='%s' )", pdId));
        if (action.equals("up")) {
            wrapper.lt("Pup_Sort", entity.getPupSort());
            wrapper.orderByDesc("Pup_Sort");
        } else {
            wrapper.gt("Pup_Sort", entity.getPupSort());
            wrapper.orderByAsc("Pup_Sort");
        }
        wrapper.last(" limit 1");
        SysPositionUserP up = baseMapper.selectOne(wrapper);
        if (ValidateUtil.isNotNull(up)) {
            Integer sort = entity.getPupSort();
            entity.setPupSort(up.getPupSort());
            up.setPupSort(sort);
            updateBatchById(Arrays.asList(entity, up));
        } else return BaseResponse.fail(BaseMsg.notMove);
        return BaseResponse.ok();
    }

    @Override
    public List<SysPositionUserP> getPositionUserPByPPId(String ppId) {
        QueryWrapper<SysPositionUserP> wrapper = new QueryWrapper<>();
        wrapper.eq("PP_ID", ppId);
        List<SysPositionUserP> list = baseMapper.selectList(wrapper);
        return list;
    }

    @Override
    public BaseResponse createOrDeletePUPByBath(List<ReportedPersonnelDtlModel> datas) {
        if (datas.size() > 0) {
            List<SysPositionUserP> adds = new ArrayList<>();
            SysPositionUserP entity;
            List<ReportedPersonnelDtlModel> dels = datas.stream().filter(n -> !ValidateUtil.isEmpty(n.getRpdType()) && n.getRpdType().equals("移除")).collect(Collectors.toList());
            if (dels != null && dels.size() > 0) {
                QueryWrapper<SysPositionUserP> delWr = new QueryWrapper<>();
                for (ReportedPersonnelDtlModel del : dels) {
                    delWr.or(qr -> qr.eq("Pp_Id", del.getRpdPpid()).eq("U_Id", del.getRpdUid()));
                }
                baseMapper.delete(delWr);
            }
            List<ReportedPersonnelDtlModel> addModels = datas.stream().filter(n -> !ValidateUtil.isEmpty(n.getRpdType()) && n.getRpdType().equals("添加")).collect(Collectors.toList());
            List<String> pdIds = new ArrayList<>();
            List<Integer> sorts = new ArrayList<>();
            if (addModels != null && addModels.size() > 0) {
                for (ReportedPersonnelDtlModel data : addModels) {
                    if (!havingData(data.getRpdPpid(), data.getRpdUid())) {
                        entity = new SysPositionUserP();
                        entity.setPpId(data.getRpdPpid());
                        entity.setuId(data.getRpdUid());
                        entity.setPupId(GenerateUUID.getUUID());
                        Integer currentSort = 0;
                        if (!pdIds.contains(data.getRpdPdid())) {
                            pdIds.add(data.getRpdPdid());
                            Integer sort = baseMapper.getMaxPupSort(data.getRpdPdid());
                            currentSort = ValidateUtil.isNull(sort) ? 1 : sort + 1;
                            sorts.add(currentSort);
                        } else {
                            int index = pdIds.indexOf(data.getRpdPdid());
                            currentSort = sorts.get(index) + 1;
                            sorts.set(index, currentSort);
                        }
                        entity.setPupSort(currentSort);
                        adds.add(entity);
                    }
                }
                if (adds.size() > 0) saveBatch(adds);
            }
            List<ReportedPersonnelDtlModel> updateModels = datas.stream().filter(n -> !ValidateUtil.isEmpty(n.getRpdType()) && n.getRpdType().equals("变更")).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(updateModels)) {
                List<SysPositionUserP> pups = new ArrayList<>();
                updateModels.forEach(f -> {
                    SysPositionUserP pup = new SysPositionUserP();
                    pup.setPupId(f.getPupId());
                    pup.setuId(f.getRpdUid());
                    pup.setPpId(f.getRpdPpid());
                    pups.add(pup);
                });
                if (!CollectionUtils.isEmpty(updateModels)) updateBatchById(pups);
            }
        }
        return BaseResponse.ok();
    }


    private Boolean havingData(String ppid, String uId) {
        QueryWrapper<SysPositionUserP> wrapper = new QueryWrapper<>();
        wrapper.eq("Pp_Id", ppid).eq("U_Id", uId);
        if (baseMapper.selectCount(wrapper) > 0) return true;
        else return false;
    }

    @Override
    public UserPositionPDTO getProjectPositionUserGlManager(String pId) {
        DepartmentPCjConfDTO conf = configEntityService.getDepartmentPCjConf();
        List<UserPositionPDTO> userPositions = baseMapper.selectPositionPUser(pId, Collections.singletonList(conf.getGldwCode()));
        if (!userPositions.isEmpty()) {
            return userPositions.stream().filter(n -> n.getPpCode().equals(conf.getGldwXmjl())).findFirst().orElse(null);
        }
        return null;
    }
}
