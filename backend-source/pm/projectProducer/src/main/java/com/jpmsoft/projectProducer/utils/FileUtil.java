package com.jpmsoft.projectProducer.utils;

import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.FileMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * 文件工具
 * 上传文件，下载文件，文件断点传输，文件打包下载
 *
 * @author Aaron
 * @date 2021/09/22
 */
@Slf4j
public class FileUtil {

    private static String exportAddress = "";


    /**
     * 校验上传的文件是否是excel
     *
     * @param file
     */
    public static void checkExcelFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            if (ValidateUtil.isEmpty(fileName))
                throw new JPMException(FileMsg.unSupportedFormat);
            if (!fileName.matches(".*\\.(xls|xlsx)$"))
                throw new JPMException(FileMsg.onlyExcel);
        }catch (Exception e) {
            throw new JPMException(FileMsg.fileUpError);
        }

    }

    public static long getRemoteFileSize(String url) {
        long size = 0;
        try {
            File file = new File(url);
            if (!file.exists() || !file.isFile()) {
                System.out.println("文件不存在");
                return -1;
            }
            size = file.length();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    public static void zipFiles(File[] srcFiles, File zipFile) {
         // 判断压缩后的文件存在不，不存在则创建
        if (!zipFile.exists()) {
            try {
                zipFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 创建 FileOutputStream 对象
        FileOutputStream fileOutputStream = null;
        // 创建 ZipOutputStream
        ZipOutputStream zipOutputStream = null;
        // 创建 FileInputStream 对象
        FileInputStream fileInputStream = null;

        try {
            // 实例化 FileOutputStream 对象
            fileOutputStream = new FileOutputStream(zipFile);
            // 实例化 ZipOutputStream 对象
            zipOutputStream = new ZipOutputStream(fileOutputStream);
            // 创建 ZipEntry 对象
            ZipEntry zipEntry = null;
            // 遍历源文件数组
            zipOutputStream.setEncoding("UTF-8");
            for (int i = 0; i < srcFiles.length; i++) {
                // 将源文件数组中的当前文件读入 FileInputStream 流中
                fileInputStream = new FileInputStream(srcFiles[i]);
                try {
                    // 实例化 ZipEntry 对象，源文件数组中的当前文件
                    zipEntry = new ZipEntry(srcFiles[i].getName());
                    zipOutputStream.putNextEntry(zipEntry);
                    // 该变量记录每次真正读的字节个数
                    int len;
                    // 定义每次读取的字节数组
                    byte[] buffer = new byte[1024];
                    while ((len = fileInputStream.read(buffer)) > 0) {
                        zipOutputStream.write(buffer, 0, len);
                    }
                } finally {
                    fiClose(fileInputStream);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            zosClose(zipOutputStream);
            fosClose(fileOutputStream);
        }
    }


    /**
     * 根据文件后缀获取ContentType
     *
     * @param suffix
     * @return
     */
    private static String getContentType(String suffix) {
        String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        if (ValidateUtil.isEmpty(suffix))
            return contentType;
        if (suffix.equals("jpg") || suffix.equals("jpeg"))
            contentType = MediaType.IMAGE_JPEG_VALUE;
        if (suffix.equals("png"))
            contentType = MediaType.IMAGE_PNG_VALUE;
        if (suffix.equals("svg"))
            contentType = "image/svg+xml";
        if (suffix.equals("bmp"))
            contentType = "image/bmp";
        if (suffix.equals("gif"))
            contentType = MediaType.IMAGE_GIF_VALUE;
        if (suffix.equals("tiff"))
            contentType = "image/tiff";
        if (suffix.equals("pdf"))
            contentType = MediaType.APPLICATION_PDF_VALUE;
        return contentType;
    }

    public static void fiClose(FileInputStream fis) {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void isClose(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void zosClose(ZipOutputStream zos) {
        if (zos != null) {
            try {
                zos.closeEntry();
                zos.close();
            } catch (IOException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }


    public static void fosClose(FileOutputStream fos) {
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void fisClose(FileInputStream fis) {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void bosClose(BufferedOutputStream bos) {
        if (bos != null) {
            try {
                bos.flush();
                bos.close();
            } catch (IOException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}

