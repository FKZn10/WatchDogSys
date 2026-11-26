package com.jpmsoft.projectProducer.service.conBoq.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.RequestBean;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.config.SnowflakeNew;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.conBoq.ConBoqDtlMapper;
import com.jpmsoft.projectProducer.mapper.conBoq.ConBoqTempDtlMapper;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConBoqBudgetDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConBoqDtlDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConBoqDtlMeasureDTO;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConMearUpperAmtDto;
import com.jpmsoft.projectProducer.pojo.entity.conBoq.ConBoqDtl;
import com.jpmsoft.projectProducer.pojo.entity.conBoq.ConBoqDtlTemp;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConBoqDtlMeasureVo;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConBoqDtlVo;
import com.jpmsoft.projectProducer.service.conBoq.IConBoqDtlService;
import com.jpmsoft.projectProducer.service.contract.IConMeasureRecordService;
import com.jpmsoft.projectProducer.service.investment.IInvBudgetDeclareDtlService;
import com.jpmsoft.projectProducer.utils.MathUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ConBoqDtlServiceImpl extends ServiceImpl<ConBoqDtlMapper, ConBoqDtl> implements IConBoqDtlService {
    @Resource
    private ConBoqTempDtlMapper conBoqTempDtlMapper;

    @Resource
    private IConMeasureRecordService conMeasureRecordService;

    @Resource
    private IInvBudgetDeclareDtlService invBudgetDeclareDtlService;


    @Resource
    @Qualifier("mySnowFlake")
    private SnowflakeNew snowflakeNew;

    @Override
    public BaseResponse createOne(ConBoqDtlDto request) {
        ConBoqDtl entity = new ConBoqDtl();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getBoqdId())) {
            entity.setBoqdId(GenerateUUID.getUUID());
            entity.setBoqdCreateTime(new Date());
        }
        if (ValidateUtil.isEmpty(request.getBoqdParentId())) entity.setBoqdParentId("0");
        save(entity);
        return BaseResponse.ok(entity.getBoqdId());
    }

    @Override
    public BaseResponse updateOne(ConBoqDtlDto request) {
        if (ValidateUtil.isEmpty(request.getBoqdId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        ConBoqDtl data=baseMapper.selectById(request.getBoqdId());
        if (data==null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        BeanUtils.copyProperties(request,data);
        updateById(data);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse deleteOne(String boqdId) {
        ConBoqDtl data=baseMapper.selectById(boqdId);
        if (data==null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        baseMapper.deleteById(boqdId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse deleteBath(List<String> ids) {
        if (!CollectionUtils.isEmpty(ids))
        {
            baseMapper.deleteBatchIds(ids);
        }
        return BaseResponse.ok();
    }

    /**
     * 保存
     *
     * @param
     */
    @Override
    public BaseResponse saveBoqDtl(RequestBean requestBean) {
        LambdaQueryWrapper<ConBoqDtlTemp> queryWrapper = new LambdaQueryWrapper<>();
        List<ConBoqDtlTemp> list = conBoqTempDtlMapper.selectList(queryWrapper);
        if (ValidateUtil.isEmpty(list)) {
            return BaseResponse.ok();
        }
        if (!requestBean.getInfo().equals("1")) {
            LambdaQueryWrapper<ConBoqDtlTemp> queryWrapper1 = new LambdaQueryWrapper<>();
            conBoqTempDtlMapper.delete(queryWrapper1);
            return BaseResponse.ok();
        }
        for (ConBoqDtlTemp conBoqDtlTemp : list) {

//                ObjectMapper mapper = new ObjectMapper();
            ConBoqDtl conBoqDtl = new ConBoqDtl().setBoqdId(conBoqDtlTemp.getBoqdId()).setBoqdCode(conBoqDtlTemp.getBoqdCode()).setBoqdName(conBoqDtlTemp.getBoqdName()).setBoqdFeature(conBoqDtlTemp.getBoqdFeature()).setBoqdContent(conBoqDtlTemp.getBoqdContent()).setBoqdUnit(conBoqDtlTemp.getBoqdUnit()).setBoqdAmount(conBoqDtlTemp.getBoqdAmount()).setBoqdPrice(conBoqDtlTemp.getBoqdPrice()).setBoqdTotalPrice(conBoqDtlTemp.getBoqdTotalPrice()).setBoqdRemark(conBoqDtlTemp.getBoqdRemark()).setBoqdState(conBoqDtlTemp.getBoqdState()).setBoqdParentId(conBoqDtlTemp.getBoqdParentId()).setBoqId(conBoqDtlTemp.getBoqId()).setBoqdSort(conBoqDtlTemp.getBoqdSort()).setBoqdBddId(conBoqDtlTemp.getBoqdBddId()).setBoqdCreateUid(conBoqDtlTemp.getBoqdCreateUid()).setBoqdCreateUname(conBoqDtlTemp.getBoqdCreateUname()).setBoqdCreateTime(conBoqDtlTemp.getBoqdCreateTime()).setBoqdUpdateTime(conBoqDtlTemp.getBoqdUpdateTime());
            // ConBoqDtlPojo conBoqDtlPojo = mapper.convertValue(conBoqDtlTempPojo, ConBoqDtlPojo.class);

            //新增
            if (conBoqDtlTemp.getBoqType().equals("0")) {
                this.save(conBoqDtl);
//                if (ValidateUtil.isNotEmpty(conBoqDtlPojo.getBoqdParentId())) {
//                    ConBoqDtlPojo byId = this.getById(conBoqDtlPojo.getBoqdParentId());
//                    BigDecimal boqdTotalPrice = byId.getBoqdTotalPrice();
//                    if (ValidateUtil.isNull(boqdTotalPrice)) {
//                        boqdTotalPrice = new BigDecimal(0);
//                    }
//                    if (ValidateUtil.isNotNull(conBoqDtlPojo.getBoqdTotalPrice())) {
//                        boqdTotalPrice.add(conBoqDtlPojo.getBoqdTotalPrice());
//                    }
//                    this.updateById(byId);
//                }
            } else if (conBoqDtlTemp.getBoqType().equals("1")) {
                this.updateById(conBoqDtl);
//                if (ValidateUtil.isNotEmpty(conBoqDtlPojo.getBoqdParentId())) {
//                    ConBoqDtlPojo byId = this.getById(conBoqDtlPojo.getBoqdParentId());
//                    BigDecimal boqdTotalPrice = byId.getBoqdTotalPrice();
//                    if (ValidateUtil.isNull(byId.getBoqdTotalPrice())) {
//                        boqdTotalPrice = new BigDecimal(0);
//                    }
//                    if (ValidateUtil.isNotNull(conBoqDtlPojo.getBoqdTotalPrice())) {
//                        boqdTotalPrice.add(conBoqDtlPojo.getBoqdTotalPrice());
//                    }
//                    byId.setBoqdTotalPrice(boqdTotalPrice);
//                    this.updateById(byId);
//                }
            } else {
                this.removeById(conBoqDtl.getBoqdId());
            }

            conBoqTempDtlMapper.deleteById(conBoqDtlTemp.getBoqdId());
        }
        return BaseResponse.ok();
    }

    /**
     * @param
     */
    @Override
    public BaseResponse createOrUpdateOrdeleteConBoqDtl(RequestBean requestBean) {
        ConBoqDtlTemp conBoqDtlPojo = JacksonUtils.parseObject(JacksonUtils.toJSONString(requestBean.getInfo()), ConBoqDtlTemp.class);
        if (ValidateUtil.isEmpty(conBoqDtlPojo.getBoqdId())) {
            conBoqDtlPojo.setBoqdId(snowflakeNew.nextUUID());
        }
        ConBoqDtlTemp conBoqDtlTemp = conBoqTempDtlMapper.selectById(conBoqDtlPojo.getBoqdId());
        if (ValidateUtil.isNull(conBoqDtlTemp)) {
            conBoqTempDtlMapper.insert(conBoqDtlPojo);
        } else {
            conBoqTempDtlMapper.updateById(conBoqDtlPojo);
        }

        return BaseResponse.ok();
    }

    /**
     * 清除临时表
     *
     * @param
     */
    @Override
    public BaseResponse deleteConBoqDtlTemp(RequestBean requestBean) {
        QueryWrapper<ConBoqDtlTemp> queryWrapper = new QueryWrapper<>();
        conBoqTempDtlMapper.delete(queryWrapper);
        return BaseResponse.ok();
    }

//    @Override
//    public BaseResponse importExcel(MultipartFile file, HttpServletRequest request, String bopId, String parentId) {
//        if (ValidateUtil.isEmpty(bopId) || ValidateUtil.isEmpty(parentId)) {
//            return BaseResponse.fail(ResMsg.notReqParam);
//        }
//        if (file.isEmpty()) {
//            return BaseResponse.fail(ResMsg.notReqParam);
//        }
//        String contentType = file.getContentType();
//        String fileName = file.getOriginalFilename();
//        try {
//            //根据路径获取这个操作excel的实例
//            HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
//            //根据页面index 获取sheet页
//            HSSFSheet sheet = wb.getSheetAt(0);
//            HSSFRow row = null;
//            //循环sesheet页中数据从第二行开始，第一行是标题
//
//            List<ConBoqDtlPojo> list = new ArrayList<>();
//            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
//                //获取每一行数据
//                row = sheet.getRow(i);
//                String boqdCode = row.getCell(0).toString();
//                String boqdName = row.getCell(1).toString();
//                //两个必填项
//                if (ValidateUtil.isEmpty(boqdCode) || ValidateUtil.isEmpty(boqdName)) {
//                    continue;
//                }
//                ConBoqDtlPojo conBoqDtlPojo = new ConBoqDtlPojo()
//                        .setBoqdCode(boqdCode)
//                        .setBoqdName(boqdName)
//                        .setBoqdFeature(row.getCell(2).toString())
//                        .setBoqdContent(row.getCell(3).toString())
//                        .setBoqdUnit(row.getCell(4).toString())
//                        .setBoqdAmount(new BigDecimal(row.getCell(5).toString()))
//                        .setBoqdPrice(new BigDecimal(row.getCell(6).toString()))
//                        .setBoqdTotalPrice(new BigDecimal(row.getCell(7).toString()))
//                        .setBoqdRemark(row.getCell(8).toString());
//                list.add(conBoqDtlPojo);
//            }
//            list.forEach(item -> {
//                item.setBoqdId(snowflakeNew.nextUUID());
//                item.setBoqdParentId(parentId);
//                item.setBoqId(bopId);
//                this.save(item);
//            });
////            BigDecimal bigDecimal = list.stream().map(ConBoqDtlPojo::getBoqdTotalPrice).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
////            ConBoqDtlPojo byId = this.getById(parentId);
//////            QueryWrapper<ConBoqDtlPojo> queryWrapper = new QueryWrapper<ConBoqDtlPojo>()
//////                    .select("NVL(SUM(Boqd_Total_Price),0) as totalPrice")
//////                    .eq("Boqd_Parent_Id", parentId);
//////            Map<String, Object> map = this.getMap(queryWrapper);
//////            BigDecimal totalPrice = (BigDecimal) map.get("totalPrice");
////            byId.setBoqdTotalPrice(byId.getBoqdTotalPrice().add(bigDecimal));
////            this.updateById(byId);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    @Override
    public BaseResponse importExcel(RequestBean requestBean) {
        List<Map<String, Object>> infos = requestBean.getInfos();
        for (Map<String, Object> info : infos) {
            ConBoqDtlTemp conBoqDtlTemp = JacksonUtils.parseObject(JacksonUtils.toJSONString(requestBean.getInfo()), ConBoqDtlTemp.class);
            conBoqDtlTemp.setBoqdId(snowflakeNew.nextUUID());
            conBoqTempDtlMapper.insert(conBoqDtlTemp);
        }
        return BaseResponse.ok();
    }

    /**
     * temp临时表数据
     *
     * @param requestBean
     */
    @Override
    public BaseResponse getConBoqDtlTempList(RequestBean requestBean) {
        String info = (String) requestBean.getInfo();
        if (ValidateUtil.isEmpty(info)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        QueryWrapper<ConBoqDtlTemp> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Boq_Id", info);
        List<ConBoqDtlTemp> list = conBoqTempDtlMapper.selectList(queryWrapper);
        return BaseResponse.ok(list);
    }


    @Override
    public void createOrUpdateConBoqDtl(List<ConBoqDtlDto> datas, String boqId) {
        delConBoqDtlByBoqId(datas, boqId);
        if (ValidateUtil.isNotEmpty(datas)) {
            List<ConBoqDtl> entitys = new ArrayList<>();
            Date now = new Date();
            entitys = datas.stream().map(n -> {
                ConBoqDtl entity = new ConBoqDtl();
                BeanUtils.copyProperties(n, entity);
                entity.setBoqId(boqId);
                if (ValidateUtil.isEmpty(n.getBoqdId())) {
                    entity.setBoqdId(GenerateUUID.getUUID());
                    entity.setBoqdCreateTime(now);
                }
                if (ValidateUtil.isEmpty(n.getBoqdParentId())) entity.setBoqdParentId("0");
                return entity;
            }).collect(Collectors.toList());
            if (entitys.size()<=500)
            {
                baseMapper.insertBatchBoqDtl(entitys);
            }else {
                List<ConBoqDtl> adds=new ArrayList<>();
                for (ConBoqDtl entity : entitys) {
                    adds.add(entity);
                    if (adds.size()==500)
                    {
                        baseMapper.insertBatchBoqDtl(adds);
                        adds=new ArrayList<>();
                    }
                }
                if (!CollectionUtils.isEmpty(adds))
                    baseMapper.insertBatchBoqDtl(adds);
            }
        }
    }

    @Override
    public void delConBoqDtlByBoqId(List<ConBoqDtlDto> datas, String boqId) {
        List<String> ids = datas.stream().map(ConBoqDtlDto::getBoqdId).filter(ValidateUtil::isNotEmpty).collect(Collectors.toList());
        QueryWrapper<ConBoqDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Boq_Id", boqId);
        List<ConBoqDtl> boqds = baseMapper.selectList(wrapper);
        List<String> boqdIds = boqds.stream().map(d -> {
            if (!CollectionUtils.isEmpty(ids) && !ids.contains(d.getBoqdId())) return d.getBoqdId();
            if (CollectionUtils.isEmpty(ids)) return d.getBoqdId();
            return null;
        }).filter(ValidateUtil::isNotEmpty).collect(Collectors.toList());
        if (conMeasureRecordService.can2deleteBoqd(boqdIds)) baseMapper.delete(wrapper);
        else throw new JPMException(ResMsg.delMeasureRecordFirst);
    }

    @Override
    public List<ConBoqDtlVo> getConBoqDtlByBoqId(String boqId) {
        List<ConBoqDtlVo> dtls = baseMapper.getConBoqDtlByBoqId(boqId);
        return getConBoqDtlTree(dtls);
    }

    @Override
    public List<ConBoqDtlVo> getConBoqDtlByBoqIdAndParent(String boqId, String parentId) {
        return baseMapper.getConBoqDtlByBoqIdAndParent(boqId,parentId);
    }

    @Override
    public BaseResponse getConBoqDtlAndMeasureTree(ConBoqDtlMeasureDTO request) {
        List<ConBoqDtlMeasureVo> dtls = baseMapper.getConBoqDtlAndMeasureTree(request);
        if (!CollectionUtils.isEmpty(dtls)) {
            dtls.forEach(d -> {
                d.setCanBoqdAmout(MathUtil.floatSubtractFloat(d.getMearBoqdAmount(), d.getMearUpperAmount()));
            });
        }
        if (request.isJoinMeasure()) {
            if (request.isFilterOut())
            {
                List<ConBoqDtlMeasureVo> data=dtls.stream().filter(f->{
                    if (MathUtil.floatAddFloat(f.getMearUpperAmount(), f.getMearDeclareAmount()).compareTo(f.getBoqdAmount()) > 0)
                        return true;
                    return false;
                }).collect(Collectors.toList());
                return BaseResponse.ok(data);
            }
            return BaseResponse.ok(dtls);
        }
        return BaseResponse.ok(getDtlMeasureTree(dtls));
    }

    @Override
    public Integer getConBoqDtlAndMeasureOutNum(ConBoqDtlMeasureDTO request) {
        List<ConBoqDtlMeasureVo> dtls = baseMapper.getConBoqDtlAndMeasureTree(request);
        if (!CollectionUtils.isEmpty(dtls)) {
            dtls.forEach(d -> {
                d.setCanBoqdAmout(MathUtil.floatSubtractFloat(d.getMearBoqdAmount(), d.getMearUpperAmount()));
            });
        }
        if (request.isJoinMeasure()) {
            if (request.isFilterOut())
            {
                List<ConBoqDtlMeasureVo> data=dtls.stream().filter(f->{
                    if (MathUtil.floatAddFloat(f.getMearUpperAmount(), f.getMearDeclareAmount()).compareTo(f.getBoqdAmount()) > 0)
                        return true;
                    return false;
                }).collect(Collectors.toList());
                return data.size();
            }
            return dtls.size();
        }
        return dtls.size();
    }

    @Override
    public List<ConBoqDtlDto> getConBoqDtlByConId(String conId) {
        return baseMapper.getConBoqDtlByConId(conId);
    }

    @Override
    public BaseResponse getConBoqDtlTreeByConId(String conId,String boqId,String parentId) {
        if(ValidateUtil.isEmpty(parentId)){
            parentId = "0";
        }
//        List<ConBoqDtlVo> data=baseMapper.getConBoqDtlVoByConId(conId);
        List<ConBoqDtlVo> data= baseMapper.getConBoqDtlByBoqIdAndParentCon(conId,boqId,parentId);
        return BaseResponse.ok(data);

//        return BaseResponse.ok(getConBoqDtlTree(data));
    }

    @Override
    public BaseResponse getConBoqDtlByConIdAndCode(String conId, String boqdCode) {
        List<ConBoqDtlVo> dtls = baseMapper.getConBoqDtlByConIdAndCode(conId, boqdCode);
        if (CollectionUtils.isEmpty(dtls)) return BaseResponse.ok();
        ConBoqDtlVo data = dtls.get(0);
        ConMearUpperAmtDto mear = conMeasureRecordService.getConMearUpperAmt(conId, boqdCode);
        BeanUtils.copyProperties(mear, data);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getConBoqDtlParentByIds(List<String> boqdIds) {
        if (CollectionUtils.isEmpty(boqdIds)) return BaseResponse.ok();
        return BaseResponse.ok(baseMapper.getConBoqDtlParentByIds(boqdIds));
    }

    @Override
    public BaseResponse getConBoqDtlBudgetList(ConBoqBudgetDto request) {
        List<String> bddIds = null;
        if (ValidateUtil.isNotEmpty(request.getBoqdBddId()))
            bddIds = invBudgetDeclareDtlService.getAllChildIdByBddId(request.getBoqdBddId());
        if (ValidateUtil.isNotEmpty(request.getPId()))
            bddIds = invBudgetDeclareDtlService.getAllChildIdByPId(request.getPId());
        if (CollectionUtils.isEmpty(bddIds)) return BaseResponse.ok();
        QueryMap<ConBoqBudgetDto> params = new QueryMap<>(request);
        params.put("boqState").put("bddIds", bddIds);
        IPage<ConBoqDtlVo> page = new Page<>(request.getPage(), request.getSize());
        List<ConBoqDtlVo> datas = baseMapper.getConBoqDtlBudgetList(page, params.getQuery());
        return BaseResponse.ok(datas, Long.valueOf(page.getTotal()).intValue());
    }

    private List<ConBoqDtlMeasureVo> getDtlMeasureTree(List<ConBoqDtlMeasureVo> dtls) {
        if (ValidateUtil.isNotEmpty(dtls)) {
            List<ConBoqDtlMeasureVo> datas = dtls.stream().filter(n -> n.getBoqdParentId().equals("0")).collect(Collectors.toList());
            datas = datas.stream().map(n -> {
                List<ConBoqDtlMeasureVo> childs = dtls.stream().filter(c -> c.getBoqdParentId().equals(n.getBoqdId())).collect(Collectors.toList());
                setDtlMeasureChilds(dtls, childs);
                n.setChildNodes(childs);
                return n;
            }).collect(Collectors.toList());
            return datas;
        }
        return null;
    }

    private void setDtlMeasureChilds(List<ConBoqDtlMeasureVo> dtls, List<ConBoqDtlMeasureVo> datas) {
        datas = datas.stream().map(n -> {
            List<ConBoqDtlMeasureVo> childs = dtls.stream().filter(c -> c.getBoqdParentId().equals(n.getBoqdId())).collect(Collectors.toList());
            setDtlMeasureChilds(dtls, childs);
            n.setChildNodes(childs);
            return n;
        }).collect(Collectors.toList());
    }

    private List<ConBoqDtlVo> getConBoqDtlTree(List<ConBoqDtlVo> dtls) {
        if (ValidateUtil.isNotEmpty(dtls)) {
            List<ConBoqDtlVo> datas = dtls.stream().filter(n -> n.getBoqdParentId().equals("0")).collect(Collectors.toList());
            datas = datas.stream().map(n -> {
                List<ConBoqDtlVo> childs = dtls.stream().filter(c -> c.getBoqdParentId().equals(n.getBoqdId())).collect(Collectors.toList());
                setChilds(dtls, childs);
                n.setChildNodes(childs);
                return n;
            }).collect(Collectors.toList());
            return datas;
        }
        return null;
    }

    private void setChilds(List<ConBoqDtlVo> dtls, List<ConBoqDtlVo> datas) {
        datas = datas.stream().map(n -> {
            List<ConBoqDtlVo> childs = dtls.stream().filter(c -> c.getBoqdParentId().equals(n.getBoqdId())).collect(Collectors.toList());
            setChilds(dtls, childs);
            n.setChildNodes(childs);
            return n;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ConBoqDtl> getBoQdName(List<String> boqIds, List<String> boqdParIds) {
        LambdaQueryWrapper<ConBoqDtl> queryWrapper = new LambdaQueryWrapper<>();
        if(ValidateUtil.isNotEmpty(boqIds) && ValidateUtil.isNotEmpty(boqdParIds) ){
            queryWrapper.in(ConBoqDtl::getBoqId,boqIds).or().in(ConBoqDtl::getBoqdId,boqdParIds);
             List<ConBoqDtl> conBoqDtlList = baseMapper.selectList(queryWrapper);
             return  conBoqDtlList;
        }else{
            return null;
        }
    }
}



