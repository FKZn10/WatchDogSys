package com.jpmsoft.projectProducer.mapper.fund;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.fund.BankAccountInfo;
import com.jpmsoft.projectProducer.pojo.vo.fund.BankAccountInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BankAccountInfoMapper extends BaseMapper<BankAccountInfo> {

    List<BankAccountInfoVo> getBankAccountInfoList(@Param("page") IPage<BankAccountInfoVo> page,
                                                   @Param("params") Map<String, Object> query);

    List<BankAccountInfoVo> selectByPId(@Param("pId") String pId);

    List<BankAccountInfoVo> selectByCompetentDepartment(@Param("pCompetentDepartment")String pCompetentDepartment,
                                                        @Param("pCompetentDepartmentName")String pCompetentDepartmentName);

    BankAccountInfoVo getBankAccountInfo(@Param("params")Map<String, Object> query);
}
