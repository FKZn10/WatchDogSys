package com.jpmsoft.fileUpload.mapper;

import com.jpmsoft.fileUpload.pojo.entity.Complaint;
import com.jpmsoft.fileUpload.pojo.entity.Lawsuit;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ComplaintLawsuitImportMapper {

    List<Complaint> selectComplaintCandidates(@Param("preIds") Set<String> preIds, @Param("unitIds") Set<String> unitIds);

    void deleteComplaintBatchIds(@Param("ids") List<String> ids);

    void insertComplaintBatch(@Param("list") List<Complaint> list);

    List<Lawsuit> selectLawsuitCandidates(@Param("preIds") Set<String> preIds, @Param("unitIds") Set<String> unitIds);

    void deleteLawsuitBatchIds(@Param("ids") List<String> ids);

    void insertLawsuitBatch(@Param("list") List<Lawsuit> list);
}
