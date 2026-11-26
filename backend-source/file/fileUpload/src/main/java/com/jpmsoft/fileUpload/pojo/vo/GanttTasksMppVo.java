package com.jpmsoft.fileUpload.pojo.vo;

import com.jpmsoft.fileUpload.pojo.dto.GanttLinksDTO;
import com.jpmsoft.fileUpload.pojo.dto.GanttTasksDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 进度计划 mpp文件导入
 *
 * @Author: 李文涛 aaron
 * @date: 2023/4/10 9:49
 */
@Data
public class GanttTasksMppVo implements Serializable {

    private List<GanttTasksDto> tasks;
    private List<GanttLinksDTO> links;
}
