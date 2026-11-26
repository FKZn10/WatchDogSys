package com.jpmsoft.fileUpload.pojo.dto.aspose;

import com.jpmsoft.fileUpload.pojo.entity.SysFileAnnex;
import com.jpmsoft.fileUpload.pojo.vo.FileAnnexInfoVo;
import com.jpmsoft.fileUpload.pojo.vo.FileResVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/10/9 11:29
 */
@Data
public class QsPlanTaskNodeDTO implements Serializable {


    private static final long serialVersionUID = 1037586013604228211L;

    private String qsrProblemType;
    private String qsrReasonType;
    private String qsrFormation;
    private String qsrDes;
    private String qsrLocation;
    private String qsrRectifyReply;

    private List<SysFileAnnex> files;
    private List<SysFileAnnex> rectifyFiles;
}
