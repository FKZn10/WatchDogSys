package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.dto.UserPositionPDTO;
import com.jpmsoft.userProducer.pojo.entity.SysPositionP;
import com.jpmsoft.userProducer.pojo.vo.SysPositionPVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SysPositionPMapper extends BaseMapper<SysPositionP> {

    /**
     * 按筛选条件分页查询项目岗位。
     *
     * @param start                   分页起始位置
     * @param size                    每页条数
     * @param ppStatus                岗位状态
     * @param pdId                    部门ID
     * @param ppName                  岗位名称，模糊匹配
     * @param sortType                排序方向
     * @param sortFiled               排序字段
     * @param filterSpecifiedPosition 排除的岗位编码，多个编码以逗号分隔
     * @param pId                     项目ID
     * @param pdCode                  部门编码，非空时精确匹配
     * @return 当前页岗位列表
     */
    List<SysPositionPVo> GetPositionPList(@Param("start") Integer start, @Param("size") Integer size,
                                          @Param("ppStatus") String ppStatus, @Param("pdId") String pdId, @Param("ppName") String ppName,
                                          @Param("sortType") String sortType, @Param("sortFiled") String sortFiled,
                                          @Param("filterSpecifiedPosition") String filterSpecifiedPosition,
                                          @Param("pId") String pId, @Param("pdCode") String pdCode);

    List<SysPositionPVo> getPositionPByUid(@Param("uId") String uId, @Param("pId") String pId);

    /**
     * 使用与分页列表相同的筛选条件统计岗位总数。
     *
     * @param ppStatus                岗位状态
     * @param pdId                    部门ID
     * @param ppName                  岗位名称，模糊匹配
     * @param filterSpecifiedPosition 排除的岗位编码，多个编码以逗号分隔
     * @param pId                     项目ID
     * @param pdCode                  部门编码，非空时精确匹配
     * @return 符合条件的岗位总数
     */
    Integer GetPositionPListCount(@Param("ppStatus") String ppStatus, @Param("pdId") String pdId, @Param("ppName") String ppName,
                                  @Param("filterSpecifiedPosition") String filterSpecifiedPosition, @Param("pId") String pId,
                                   @Param("pdCode") String pdCode);

    List<Map<String,String>> getPositionPByRole(@Param("roleId") String roleId);

    Integer getMaxPpSort(@Param("pdId")String pdId);

    List<UserPositionPDTO> getProjectPositionP(@Param("pId") String pId, @Param("pdCodes") List<String> pdCodes);

    List<UserPositionPDTO> getProjectPositionPOwner(@Param("pId") String pId, @Param("yzdwoCode") String yzdwoCode, @Param("yzdwtCode") String yzdwtCode);
}
