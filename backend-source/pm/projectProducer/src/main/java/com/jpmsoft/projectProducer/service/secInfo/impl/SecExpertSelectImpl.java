package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecExpertSelectMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.ExtractExpertDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertSelectDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertSelect;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecExpertSelectVo;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertSelectService;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertService;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/8 16:19
 */
@Service
public class SecExpertSelectImpl extends BaseFileImpl<SecExpertSelectMapper, SecExpertSelect, SecExpertSelectDto>
        implements ISecExpertSelectService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISecExpertService secExpertService;

    @Override
    public BaseResponse createOrUpdateSecExpertSelect(SecExpertSelectDto request) {
        SecExpertSelect entity = new SecExpertSelect();
        entity = createOrUpdateEntity(entity, request, false);
        if (ValidateUtil.isEmpty(request.getId()))
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getId());
        else
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getSecExpertSelectList(SecExpertSelectDto request, String uId) {
        QueryMap<SecExpertSelectDto> queryMap = new QueryMap<>(request);
        queryMap.put("pName").put("pId").put("secName").put("createUname").put("state")
                .put("state").put("esCompanyName").put("esType").put("esCategory")
                .put("esMode").put("esBusiness").put("esTel").put("esContact");
        IPage<SecExpertSelectVo> page = new Page<>(request.getPage(), request.getSize());
        List<SecExpertSelectVo> datas = baseMapper.getSecExpertSelectList(page, queryMap.getQuery());
        return BaseResponse.ok(datas, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getSecExpertSelectById(String id) {
        SecExpertSelectVo data = baseMapper.getSecExpertSelectById(id);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse checkExPeople(ExtractExpertDto request, String teId) {
        BaseResponse br = secExpertService.extractExpert(request, teId);
        if (br.getSuccess() && br.getStatusCode() == HttpStatus.SC_OK)
            br.setData(Boolean.TRUE);
        else br.setData(Boolean.FALSE);
        return br;
//        List<SecExpertModel> records = secExpertService.getExtractExpertList(request.getTeId());
//        List<SecExpertModel> technologies = records.stream().filter(n -> {
//            if (ValidateUtil.isNotEmpty(n.getExCategory())) {
//                if (n.getExCategory().equals(ExpertTypeEnum.Technology.getkey()))
//                    return true;
//            }
//            return false;
//        }).collect(Collectors.toList());
//        if (technologies.size() < request.getEsTechnologyNum())
//            throw new JPMException(ResMsg.exTechNumberError);
//        //经济类专家
//        List<SecExpertModel> economics = records.stream().filter(n -> {
//            if (ValidateUtil.isNotEmpty(n.getExCategory())) {
//                if (n.getExCategory().equals(ExpertTypeEnum.Economics.getkey()))
//                    return true;
//            }
//            return false;
//        }).collect(Collectors.toList());
//        if (economics.size() < request.getEsTechnologyNum())
//            throw new JPMException(ResMsg.exEcoNumberError);
//        return BaseResponse.ok(Boolean.TRUE);
    }

    @Override
    public void updateState(String id, Integer state) {
        if (ValidateUtil.isNotEmpty(id) && ValidateUtil.isNotNull(state))
            baseMapper.updateState(id, state);
    }


}
