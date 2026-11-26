package com.jpmsoft.projectProducer.service.materialsEquipment.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.materialsEquipment.MeBrandApprovalMapper;
import com.jpmsoft.projectProducer.mapper.other.SysDictionaryMapper;
import com.jpmsoft.projectProducer.pojo.dto.materialsEquipment.MeBrandApprovalDto;
import com.jpmsoft.projectProducer.pojo.dto.system.SelectDto;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeBrandApproval;
import com.jpmsoft.projectProducer.pojo.entity.other.SysDictionary;
import com.jpmsoft.projectProducer.pojo.vo.materialsEquipment.MeBrandApprovalReportVo;
import com.jpmsoft.projectProducer.pojo.vo.materialsEquipment.MeBrandApprovalVo;
import com.jpmsoft.projectProducer.pojo.vo.sys.VueTreeVo;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeBrandApprovalDtlService;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeBrandApprovalService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 10:28
 */
@Slf4j
@Service
public class MeBrandApprovalImpl extends FileImpl<MeBrandApprovalMapper, MeBrandApproval, MeBrandApprovalDto>
        implements IMeBrandApprovalService {

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IMeBrandApprovalDtlService meBrandApprovalDtlService;
    @Resource
    private RedisUtils redisUtils;
    @Autowired
    private SysDictionaryMapper sysDictionaryMapper;

    /**
     * 统计项目、合同下已有报审单的个数，无记录时返回 0。
     *
     * @param pId   项目 ID
     * @param conId 合同 ID
     * @return 报审单个数
     */
    @Override
    public BaseResponse getMeBrandApprovalReviewsCount(String pId, String conId) {
        return BaseResponse.ok(baseMapper.getMeBrandApprovalReviewsCount(pId, conId));
    }

    /**
     * 新增或修改品牌报审，保存请求中的报审次数及明细。
     *
     * @param request 品牌报审信息，包含 reviewsCount
     * @return 报审单 ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "baId", codeParam = "baCode", method = "setBaCode", codeRule = "MeBrandApproval", beanClass = MeBrandApprovalDto.class)
    public BaseResponse createOrUpdateMeBrandApproval(MeBrandApprovalDto request) {
        MeBrandApproval entity = new MeBrandApproval();
        if (ValidateUtil.isEmpty(request.getpId())) {
            request.setpId(request.getPId());
        }
        createOrUpdateFileEntity(entity, request);
        meBrandApprovalDtlService.createOrUpdateMeBrandApprovalDtl(request.getMeBrandApprovalDtls(), entity.getBaId());
        return BaseResponse.ok(entity.getBaId());
    }

    /**
     * 分页查询品牌报审，支持按合同、报审次数等条件筛选。
     *
     * @param request 查询条件
     * @param uId     当前用户 ID
     * @return 含报审次数的分页结果
     */
    @Override
    public BaseResponse getMeBrandApprovalList(MeBrandApprovalDto request, String uId) {
        String authSql = sysUserService.getAuthSql("Me_Brand_Approval", uId, "Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<MeBrandApprovalDto> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("baCode").put("baPm").put("baCategory")
                .put("baType").put("baProfessional").put("state").put("createUname")
                .put("pName").put("secName").put("conName").put("pId").put("conId").put("reviewsCount");
        IPage<MeBrandApprovalVo> page = new Page<>(request.getPage(), request.getSize());
        List<MeBrandApprovalVo> datas = baseMapper.getMeBrandApprovalList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getMeBrandApprovalById(String id) {
        MeBrandApprovalVo meBrandApprovalVo= baseMapper.getMeBrandApprovalById(id);
        if (ValidateUtil.isEmpty(meBrandApprovalVo))
            return BaseResponse.fail(ResMsg.noData);
        return BaseResponse.ok(meBrandApprovalVo);
    }
    @Override
    public BaseResponse getMeBrandApprovalDetailsById(String id) {
        MeBrandApprovalReportVo meBrandApprovalVo= baseMapper.getMeBrandApprovalReportById(id);
        if (ValidateUtil.isEmpty(meBrandApprovalVo))
            return BaseResponse.fail(ResMsg.noData);
        List<String> codes =new ArrayList<>();
        codes.add("JPMsoft_CLSBPPLX");
        codes.add("JPMsoft_CLSBZYLB");
        SelectDto selectDto = new SelectDto();
        selectDto.setCodes(codes);
        Map<String, List<VueTreeVo>> map = getDictionaryList(selectDto);
        //log.info("VueTreeVoMap:"+JacksonUtils.toJSONString(map));
        String baType=meBrandApprovalVo.getBaType();
        String baProfessional=meBrandApprovalVo.getBaProfessional();
        if (!ValidateUtil.isEmpty(baType)&&!ValidateUtil.isEmpty(map)){
            if (baType.contains(",")){
                StringBuffer sb1 = new StringBuffer();
                String[] split = baType.split(",");
                for (int i = 0; i < split.length; i++) {
                    List<VueTreeVo> vueTreeVo = map.get("JPMsoft_CLSBPPLX");
                    getCodeName(sb1,split,i,vueTreeVo);
                }
                String baTypeName=sb1.toString().substring(0,sb1.length()-1);
                meBrandApprovalVo.setBaTypeName(baTypeName);
            }else {
                String code=baType;
                List<VueTreeVo> vueTreeVo = map.get("JPMsoft_CLSBPPLX");
                String name =vueTreeVo.stream().filter(n->n.getValue().equals(code)).findFirst().get().getTitle();
                meBrandApprovalVo.setBaTypeName(name);
            }
        }
        if (!ValidateUtil.isEmpty(baProfessional) &&!ValidateUtil.isEmpty(map)){
            if (baProfessional.contains(",")){
                StringBuffer sb1 = new StringBuffer();
                String[] split = baProfessional.split(",");
                for (int i = 0; i < split.length; i++) {
                    List<VueTreeVo> vueTreeVo = map.get("JPMsoft_CLSBZYLB");
                    getCodeName(sb1,split,i,vueTreeVo);
                }
                String baProfessionalName=sb1.toString().substring(0,sb1.length()-1);
                meBrandApprovalVo.setBaProfessionalName(baProfessionalName);
            }else {
                String code=baProfessional;
                List<VueTreeVo> vueTreeVo = map.get("JPMsoft_CLSBZYLB");
                String name =vueTreeVo.stream().filter(n->n.getValue().equals(code)).findFirst().get().getTitle();
                meBrandApprovalVo.setBaProfessionalName(name);
            }
        }
        List<MeBrandApprovalReportVo> value = new ArrayList<>();
        value.add(meBrandApprovalVo);
        return BaseResponse.ok(value);
    }

    private void getCodeName(StringBuffer sb1,String[] split,int i,List<VueTreeVo> vueTreeVo) {
        //log.info("VueTreeVo:"+ JacksonUtils.toJSONString(vueTreeVo));
        int finalI = i;
        String code=split[finalI];
        String name = vueTreeVo.stream().filter(n->n.getValue().equals(code)).findFirst().get().getTitle();
        sb1.append(name);
        sb1.append(",");
    }

    private Map<String, List<VueTreeVo>> getDictionaryList(SelectDto request) {
        List<String> codes = request.getCodes();
        if (codes == null || codes.size() < 1) return null;
        List<SysDictionary> list = getAll();
        Map<String, List<VueTreeVo>> data = new HashMap<>();
        for (String code : codes) {
            List<VueTreeVo> treeList = new ArrayList<>();
            list.forEach(model -> {
                if (code.equals(model.getdCode())) {
                    list.forEach(child -> {
                        if (ValidateUtil.isNotEmpty(child.getdStatus()) && child.getdStatus().equals("1") && ValidateUtil.isNotEmpty(child.getdParentid()) && child.getdParentid().equals(model.getdId())) {
                            VueTreeVo vueTree = new VueTreeVo();
                            vueTree.setKey(child.getdId());
                            vueTree.setTitle(child.getdName());
                            vueTree.setValue(child.getdCode());
                            vueTree.setdStatus(child.getdStatus());
                            List<VueTreeVo> vueTrees = new ArrayList<>();
                            getDictionaryTreeChildren(vueTrees, child.getdId(), list);
                            vueTree.setChildren(vueTrees);
                            treeList.add(vueTree);
                        }
                    });
                }
            });
            data.put(code, treeList);
        }
        return data;
    }
    private List<SysDictionary> getAll() {
        QueryWrapper<SysDictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("D_Sort");
        return sysDictionaryMapper.selectList(queryWrapper);
    }
    private void getDictionaryTreeChildren(List<VueTreeVo> treeList, String parentId, List<SysDictionary> list) {
        list.forEach(model -> {
            if (ValidateUtil.isNotEmpty(model.getdStatus()) && model.getdStatus().equals("1") && ValidateUtil.isNotEmpty(model.getdParentid()) && model.getdParentid().equals(parentId)) {
                VueTreeVo vueTree = new VueTreeVo();
                vueTree.setKey(model.getdId());
                vueTree.setTitle(model.getdName());
                vueTree.setValue(model.getdCode());
                vueTree.setdStatus(model.getdStatus());
                List<VueTreeVo> vueTrees = new ArrayList<>();
                getDictionaryTreeChildren(vueTrees, model.getdId(), list);
                vueTree.setChildren(vueTrees);
                treeList.add(vueTree);
            }
        });
    }

}
