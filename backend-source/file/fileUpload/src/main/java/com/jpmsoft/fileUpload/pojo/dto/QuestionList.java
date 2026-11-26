package com.jpmsoft.fileUpload.pojo.dto;
import com.jpmsoft.fileUpload.pojo.vo.FileResVo;
import lombok.Data;
import java.util.List;

/**
 * 巡检问题列表 DTO
 *
 */
@Data
public class QuestionList {
        private Integer serialNumber;           // 序号
        private String inspectionIssue;         // 分类编号
        private String inspectionIssueName;     // 分类名称
        private String problemDescription;      // 问题描述
        private String witnessMaterials;        // 佐证材料id
        private String rectificationStatus;     // 整改状态
        private String inspectionProblemLevel;  // 问题等级（白牌/黄牌/红牌）
        private List<FileResVo> qsrRectifyPicIds;  // 整改图片ID
        private List<FileResVo> qasReferencePicIds;// 参考图片ID
        private List<FileResVo> qsrPicIds;         // 巡检图片ID
        private String diId;                    // 巡检单ID
        private String problemPhoto;            // 问题照片ID
        private String rectificationSuggestions;// 整改建议图片ID
        private String rectificationInstructions;// 整改说明
        private String rectificationMeasures;   // 整改措施
        private String id;                      // 问题ID
        private String rectificationResponse;   // 整改回复
        private String referencePracrice;       // 参考案例

}