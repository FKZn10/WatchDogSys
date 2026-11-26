package com.jpmsoft.projectProducer.pojo.entity.other;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangl
 * @since 2022-05-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_FileAnnex")
public class SysFileannex implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "file_id", type = IdType.INPUT)
    private String fileId;

    private static final String FILEID ="fileId";

    private String fileRelationId;

    private static final String FILERELATIONID ="fileRelationId";

    private String filePath;

    private static final String FILEPATH ="filePath";

    private Integer fileSize;

    private static final String FILESIZE ="fileSize";

    private String fileSuffix;

    private static final String FILESUFFIX ="fileSuffix";

    private String fileName;

    private static final String FILENAME ="fileName";

    private Date fileUploadTime;

    private static final String FILEUPLOADTIME ="fileUploadTime";

    private String fileCreateUId;

    private static final String FILECREATEUID ="fileCreateUId";

    private String fileCreateUName;

    private static final String FILECREATEUNAME ="fileCreateUName";

    private String fileCode;

    private static final String FILECODE ="fileCode";

    private String tableName;

    private static final String TABLENAME ="tableName";

    private String tableCode;

    private static final String TABLECODE ="tableCode";

    private String columnName;

    private static final String COLUMNNAME ="columnName";

    private String columnCode;

    private static final String COLUMNCODE ="columnCode";

    private Integer uploadStatus;

    private static final String UPLOADSTATUS ="uploadStatus";

    @TableField("ARCHIVE")
    private Integer archive;

    private static final String ARCHIVE ="archive";

    @TableField("File_Location")
    private Integer fileLocation;

    private static final String FILELOCATION ="fileLocation";

    @TableField("File_Unarchive_Id")
    private String fileUnarchiveId;

    private static final String FILEUNARCHIVEID ="fileUnarchiveId";

    @TableField("File_Replace")
    private Integer fileReplace;

    private static final String FILEREPLACE ="fileReplace";

    @TableField("File_Entity_Id")
    private String fileEntityId;

    private static final String FILEENTITYID ="fileEntityId";

    @TableField("File_MD5_Code")
    private String fileMd5Code;
}
