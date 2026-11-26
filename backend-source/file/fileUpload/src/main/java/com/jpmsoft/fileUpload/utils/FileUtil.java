package com.jpmsoft.fileUpload.utils;

import cn.hutool.core.io.FileTypeUtil;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.oss.crc.CrcOssUtil;
import com.jpmsoft.fileUpload.pojo.dto.OssClientParamsDto;
import com.jpmsoft.fileUpload.pojo.entity.SysFileAnnex;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.*;

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

    // 文件签名的常量
    private static final byte[] XLS_SIGNATURE = {(byte) 0xD0, (byte) 0xCF, (byte) 0x11, (byte) 0xE0, (byte) 0xA1, (byte) 0xB1, (byte) 0x1A, (byte) 0xE1};
    private static final byte[] XLSX_SIGNATURE = {(byte) 0x50, (byte) 0x4B, (byte) 0x03, (byte) 0x04};
    private static final byte[] XML_SIGNATURE = {(byte) 0x3C, (byte) 0x3F, (byte) 0x78, (byte) 0x6D, (byte) 0x6C}; // "<?xml" 的 UTF-8 编码

    // 缓冲区大小，根据最长的签名来设置
    private static final int BUFFER_SIZE = 8;

    public static String checkFileType(InputStream inputStream) {
        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = inputStream.read(buffer);
            if (bytesRead >= XLS_SIGNATURE.length && matchesSignature(buffer, XLS_SIGNATURE)) {
                return "xls";
            } else if (bytesRead >= XLSX_SIGNATURE.length && matchesSignature(buffer, XLSX_SIGNATURE)) {
                return "xlsx";
            } else if (bytesRead >= XML_SIGNATURE.length && matchesSignature(buffer, XML_SIGNATURE)) {
                return "xml";
            } else {
                return "unknown";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    // 辅助方法，用于比较文件签名
    private static boolean matchesSignature(byte[] buffer, byte[] signature) {
        for (int i = 0; i < signature.length; i++) {
            if (buffer[i] != signature[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 临时文件绝对路径
     *
     * @param root
     * @return
     */
    public static String temporaryPath(String root, String suffix) {
        return root + "/JPBPMTemporaryFolder/" + GenerateUUID.getUUID() + "." + suffix;
    }


    /**
     * 下载临时文件后删除文件
     *
     * @param response
     * @param filePath
     * @param fileName
     */
    public static void downFileAndDelFile(HttpServletResponse response, String filePath, String fileName) {
        FileUtil.download(filePath, response, fileName);
        File file = new File(filePath);
        file.delete();
    }

    /**
     * 校验上传的文件是否是excel
     *
     * @param file
     */
    public static void checkExcelFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (ValidateUtil.isEmpty(fileName))
            throw new JPMException(FileMsg.unSupportedFormat);
        if (!fileName.matches(".*\\.(xls|xlsx)$"))
            throw new JPMException(FileMsg.onlyExcel);
    }

    /**
     * 保存在线编辑文件
     *
     * @param filePath
     * @param part
     * @param response
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static void saveWebOfficeFile(String filePath, Part part, HttpServletResponse response)
            throws IOException, FileNotFoundException {
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            is = part.getInputStream();
            fos = new FileOutputStream(filePath);
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = is.read(buffer)) != -1) {
                fos.write(buffer, 0, length);
            }
        } finally {
            isClose(is);
            fosClose(fos);
        }
    }


//    public static void webOfficeDown(String path, String fileName, HttpServletRequest request, HttpServletResponse response) {
//        try {
//            // path是指欲下载的文件的路径。
//            File file = new File(path);
//            // 取得文件名。
//            String filename = ValidateUtil.isEmpty(fileName) ? file.getName() : fileName;
//            // 取得文件的后缀名。
//            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase(Locale.ENGLISH);
//            // 以流的形式下载文件。
//            OutputStream out = response.getOutputStream();
//            FileInputStream fis = new FileInputStream(path);
//            response.setCharacterEncoding("utf-8");
//            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
//            byte[] bt = new byte[1024];
//            int length = 0;
//            while ((length = fis.read(bt)) != -1) {
//                out.write(bt, 0, length);
//            }
//            out.close();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            log.error(ex.getMessage(),ex);
//        }
//    }

    public static HttpServletResponse download(String path, HttpServletResponse response, String fileName) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            if (!file.exists() || !file.isFile()) {
                log.error(FileMsg.noneDownFile);
                return response;
            }
            // 取得文件名。
            String filename = ValidateUtil.isEmpty(fileName) ? file.getName() : fileName;
            // 以流的形式下载文件。
            FileInputStream fileInputStream = null;
            InputStream fis = null;
            byte[] buffer;
            try {
                fileInputStream = new FileInputStream(path);
                fis = new BufferedInputStream(fileInputStream);
                buffer = new byte[fis.available()];
                fis.read(buffer);
            } finally {
                fiClose(fileInputStream);
                isClose(fis);
            }
            fis.close();
            fileInputStream.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            //获取文件后缀
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            //设置ContentType
            response.setContentType(getContentType(suffix));
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
            response.getOutputStream().close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }


//    //批量文件下载(将多个文件打包成zip包下载)
//    public static void batchDownLoadFile(HttpServletRequest request, HttpServletResponse response, String filename, String[] filepath, String[] documentname, String loginname) {
//        byte[] buffer = new byte[1024];
//        Date date = new Date();
//        //生成zip文件存放位置
//        String strZipPath = exportAddress + loginname + date.getTime() + ".zip";
//        File file = new File(exportAddress);
//        if (!file.isDirectory() && !file.exists()) {
//            //创建单层目录
//            // f.mkdir();
//            // 创建多层目录
//            file.mkdirs();
//        }
//        try {
//            OutputStream os = Files.newOutputStream(Paths.get(strZipPath));
//            ZipOutputStream out = new ZipOutputStream(os);
//            try {
//                // 需要同时下载的多个文件
//                for (int i = 0; i < filepath.length; i++) {
//                    File f = new File(filepath[i]);
//                    FileInputStream fis = new FileInputStream(f);
//                    System.out.println(documentname[i]);
//                    out.putNextEntry(new ZipEntry(documentname[i]));
//                    //设置压缩文件内的字符编码，不然会变成乱码
//                    out.setEncoding("GBK");
//                    int len;
//                    // 读入需要下载的文件的内容，打包到zip文件
//                    while ((len = fis.read(buffer)) > 0) {
//                        out.write(buffer, 0, len);
//                    }
//                    out.closeEntry();
//                    fis.close();
//                }
//            } finally {
//                out.close();
//                if (os != null)
//                    os.close();
//            }
//            download(filename + ".zip", response, null);
////            PublicMethod.downLoadFile(request, response, strZipPath, ");
//            File temp = new File(strZipPath);
//            if (temp.exists()) {
//                temp.delete();
//            }
//        } catch (Exception e) {
//            System.out.println("文件下载错误");
//        }
//    }


    /**
     * 文件批量下载
     *
     * @param request
     * @param response
     * @param files
     */
    public static void batchDownload(HttpServletRequest request, HttpServletResponse response, String root, String archive, List<SysFileAnnex> files) throws IOException {
        int count = 0;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        java.util.zip.ZipOutputStream zip = new java.util.zip.ZipOutputStream(outputStream);
        Map<String, Integer> nameCountMap = new HashMap<>();
        for (SysFileAnnex file : files) {
            String path = file.getArchive() == 1 ? archive : root;
            File f = new File(path + "/" + file.getFilePath());
            if (f.exists() && f.isFile()) {
                byte[] bytes = fileToByte(f);
                String originalName = file.getFileName();
                String entryName = originalName;
                // 重复命名处理
                if (nameCountMap.containsKey(originalName)) {
                    int index = nameCountMap.get(originalName) + 1;
                    nameCountMap.put(originalName, index);
                    int dotIndex = originalName.lastIndexOf(".");
                    if (dotIndex != -1) {
                        // 带扩展名的文件
                        String namePart = originalName.substring(0, dotIndex);
                        String extPart = originalName.substring(dotIndex);
                        entryName = namePart + "(" + index + ")" + extPart;
                    } else {
                        // 无扩展名
                        entryName = originalName + "(" + index + ")";
                    }
                } else {
                    nameCountMap.put(originalName, 0);
                }
                zip.putNextEntry(new java.util.zip.ZipEntry(entryName));
                IOUtils.write(bytes, zip);
                zip.flush();
                zip.closeEntry();
                count++;
            }
        }
        IOUtils.closeQuietly(zip);
        byte[] data = outputStream.toByteArray();
        outputStream.close();
        if (count > 0) {
            download(response, GenerateUUID.getUUID() + ".zip", data);
        } else {
            throw new JPMException(HttpStatus.SC_NO_CONTENT, FileMsg.noneDownFile);
        }
    }

    /**
     * 根据文件路径映射打包下载ZIP
     *
     * @param response    响应对象
     * @param zipFileName 下载的ZIP文件名
     * @param pathFileMap ZIP内部路径与文件的映射，例如 key 为 "项目A/单体B/结构图/file1.pdf"，value 为 SysFileAnnex
     * @param root        根目录路径
     * @param archive     归档目录路径
     * @throws IOException
     */
    public static void downloadZipWithPathMapping(HttpServletResponse response,
                                                  String zipFileName,
                                                  Map<String, SysFileAnnex> pathFileMap,
                                                  String root, String archive) throws IOException {
        if (pathFileMap == null || pathFileMap.isEmpty()) {
            response.setStatus(HttpStatus.SC_NO_CONTENT);
            return;
        }

        List<Map.Entry<String, SysFileAnnex>> validEntries = new ArrayList<>();
        for (Map.Entry<String, SysFileAnnex> entry : pathFileMap.entrySet()) {
            SysFileAnnex file = entry.getValue();
            if (file == null) {
                continue;
            }
            String basePath = (file.getArchive() != null && file.getArchive() == 1) ? archive : root;
            File realFile = new File(basePath, file.getFilePath());
            if (realFile.exists() && realFile.isFile()) {
                validEntries.add(entry);
            }
        }

        if (validEntries.isEmpty()) {
            response.setStatus(HttpStatus.SC_NO_CONTENT);
            return;
        }

        response.resetBuffer();
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition",
                "attachment; filename=" + URLEncoder.encode(zipFileName, "UTF-8"));
        response.setHeader("Cache-Control", "no-store");

        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()))) {
            for (Map.Entry<String, SysFileAnnex> entry : validEntries) {
                String zipPath = entry.getKey();
                SysFileAnnex file = entry.getValue();
                String basePath = (file.getArchive() != null && file.getArchive() == 1) ? archive : root;
                File realFile = new File(basePath, file.getFilePath());
                String safeZipPath = zipPath.startsWith("/") ? zipPath.substring(1) : zipPath;
                zip.putNextEntry(new ZipEntry(safeZipPath));
                try (InputStream in = new BufferedInputStream(Files.newInputStream(realFile.toPath()))) {
                    IOUtils.copy(in, zip);
                }
                zip.closeEntry();
            }
            zip.finish();
        }
    }

    /**
     * 判断当前下载是走断点下载还是普通下载
     *
     * @param request
     * @param response
     * @param proposeFile
     * @param fileName
     * @throws IOException
     */
    public static void downloadExistsFile(HttpServletRequest request,
                                          HttpServletResponse response, File proposeFile, String fileName) throws IOException {
        if (ValidateUtil.isEmpty(request.getHeader("Range")))
//            webOfficeDown(proposeFile.getPath(), fileName, request, response);
            download(proposeFile.getPath(), response, fileName);
        else
            downloadBreakPointFile(request, response, proposeFile, fileName);
    }


    public static void downloadBreakPointFile(HttpServletRequest request,
                                              HttpServletResponse response, File proposeFile, String fileName) throws IOException {
        if (!proposeFile.exists() || !proposeFile.isFile())
            throw new JPMException(FileMsg.noneDownFile);
        String url = proposeFile.getPath();
        log.info("下载文件路径：" + url);
        long fSize = getRemoteFileSize(url);
        //处理正反斜杠 windows文件路径为反斜杠 linux为正斜杠
        String sp = "#ad&";
        String nameStr = url.replaceAll("\\\\", sp).replaceAll("/", sp);
        String isoFileName = ValidateUtil.isEmpty(fileName) ? nameStr.substring(nameStr.lastIndexOf(sp)).replaceAll(sp, "") : fileName;
        // 下载
        response.setContentType("application/x-download");
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("Content-Length", String.valueOf(fSize));
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(isoFileName, "UTF-8"));
        long pos = 0;
        if (null != request.getHeader("Range")) {
            // 断点续传
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            try {
                pos = Long.parseLong(request.getHeader("Range").replaceAll(
                        "bytes=", "").replaceAll("-", ""));
            } catch (NumberFormatException e) {
                log.error(request.getHeader("Range") + " is not Number!", e);
                pos = 0;
            }
        }
        OutputStream out = null;
        BufferedOutputStream bufferOut = null;
        InputStream inputStream = null;
        try {
            out = response.getOutputStream();
            bufferOut = new BufferedOutputStream(out);
            inputStream = Files.newInputStream(proposeFile.toPath());
            String contentRange = new StringBuffer("bytes ").append(
                    Long.toString(pos)).append("-").append(
                    Long.toString(fSize - 1)).append("/").append(
                    Long.toString(fSize)).toString();
            response.setHeader("Content-Range", contentRange);
            log.info("Content-Range: {}", contentRange);
            inputStream.skip(pos);
            byte[] buffer = new byte[5 * 1024];
            int length = 0;
            while ((length = inputStream.read(buffer, 0, buffer.length)) != -1) {
                bufferOut.write(buffer, 0, length);
            }
        } finally {
            bosClose(bufferOut);
            if (out != null) out.close();
            isClose(inputStream);
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
     * 归档文件时需要拷贝的文件
     *
     * @param fromTo  文件原地址-》归档地址
     * @param root    根目录
     * @param archive 归档根目录
     * @return
     */
    public static List<String> listFileInDir(List<Map<String, String>> fromTo, OssClientParamsDto param, String root, String archive) {
        root = root.endsWith("/") ? root : root + "/";
        archive = archive.endsWith("/") ? archive : archive + "/";
        List<String> fileNotExists = new ArrayList<>();
        //region 先看看本地文件是否存在 不存在先尝试从oss拉取
        for (Map<String, String> map : fromTo) {
            File f = new File(root + map.get("from"));
            if (!f.exists())
                CrcOssUtil.downLocalNotExistFile(param, map.get("from"), root, 0);
        }
        //endregion

        for (Map<String, String> map : fromTo) {
            File f = new File(root + map.get("from"));
            if (!f.exists()) {
                fileNotExists.add(map.get("id"));
            } else {
                int endindex = map.get("to").lastIndexOf("\\");// 找到"\"所在的位置
                int endpoint = map.get("to").lastIndexOf("/");// 找到"/"所在的位置
                if (endpoint > endindex)
                    endindex = endpoint;
                String mkdirPath = map.get("to").substring(0, endindex);
                File tempFile = new File(archive + mkdirPath);
                if (!tempFile.exists())
                    tempFile.mkdirs();// 创建立文件夹
                File toFile = new File(archive + map.get("to"));
                if (!toFile.exists() || (toFile.exists() && !toFile.isFile())) {
                    copy(root + map.get("from"), archive + map.get("to"));
                    //上传文件到OSS
                    if (param != null)
                        CrcOssUtil.upload(param, archive + map.get("to"), param.getOssArchiveDir() + mkdirPath);
                }
                log.info("源文件" + map.get("from"));
                log.info("目标文件" + map.get("to"));
            }
        }
        return fileNotExists;
    }

    /**
     * 封装好的文件拷贝方法
     */
    public static void copy(String from, String to) {
        try {
            //正反斜杠处理
            int nameIndex1 = to.lastIndexOf("\\");
            int nameIndex2 = to.lastIndexOf("/");
            if (nameIndex2 > nameIndex1)
                nameIndex1 = nameIndex2;
            String fileName = to.substring(nameIndex1 + 1, to.length());
            String dirPath = to.substring(0, nameIndex1);
            File dir = new File(dirPath);
            if (!dir.exists() || !dir.isDirectory())
                dir.mkdirs();
            //直接File(文件路径) createNewFile创建的可能是文件夹(windows)
            File file = new File(dirPath, fileName);
            //不是文件但是同名
            if (file.exists() && !file.isFile())
                file.delete();
            if (!file.exists() || (file.exists() && !file.isFile()))
                file.createNewFile();
            InputStream in = Files.newInputStream(Paths.get(from));
            OutputStream out = Files.newOutputStream(file.toPath());
            try {
                byte[] buff = new byte[1024];
                int len = 0;
                while ((len = in.read(buff)) != -1) {
                    out.write(buff, 0, len);
                }
            } finally {
                in.close();
                if (out != null)
                    out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
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
        if (suffix.equalsIgnoreCase("jpg") || suffix.equalsIgnoreCase("jpeg"))
            contentType = MediaType.IMAGE_JPEG_VALUE;
        if (suffix.equalsIgnoreCase("png"))
            contentType = MediaType.IMAGE_PNG_VALUE;
        if (suffix.equalsIgnoreCase("svg"))
            contentType = ContentType.IMAGE_SVG.getMimeType();
        if (suffix.equalsIgnoreCase("bmp"))
            contentType = ContentType.IMAGE_BMP.getMimeType();
        if (suffix.equalsIgnoreCase("gif"))
            contentType = MediaType.IMAGE_GIF_VALUE;
        if (suffix.equalsIgnoreCase("tiff"))
            contentType = ContentType.IMAGE_TIFF.getMimeType();
        if (suffix.equalsIgnoreCase("xml"))
            contentType = MediaType.APPLICATION_XML_VALUE;
        if (suffix.equalsIgnoreCase("pdf"))
            contentType = MediaType.APPLICATION_PDF_VALUE;
        if (suffix.equalsIgnoreCase("zip"))
            contentType = "application/zip";
        return contentType;
    }


    /**
     * 下载
     * common io
     *
     * @param response
     * @param fileName
     * @param data
     * @throws IOException
     */
    private static void download(HttpServletResponse response, String fileName, byte[] data) throws IOException {
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }

    /**
     * 文件File类型转byte[]
     *
     * @param file 文件对象
     * @return
     */
    public static byte[] fileToByte(File file) {
        byte[] fileBytes = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            fileBytes = new byte[(int) file.length()];
            fis.read(fileBytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fisClose(fis);
        }
        return fileBytes;
    }

    /**
     * 校验文件是否存在
     *
     * @param path
     * @return
     */
    public static boolean isFile(String path) {
        File file = new File(path);
        if (file.exists() && file.isFile()) return true;
        return false;
    }


    public static String httpPost2File(String url, String param, String downloadPath, String token) {
        String dir = downloadPath.substring(0, downloadPath.lastIndexOf("/") + 1);
        File file = new File(dir);
        if (!file.exists() || !file.isDirectory()) file.mkdirs();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // https   需要SSL
        if (url.startsWith("https://")) {
            httpClient = sslClient();
        }
        String resultString = "";
        CloseableHttpResponse response = null;
        HttpPost httppost = new HttpPost(url);
        //返回的字节流
        byte[] bytes = null;
        try {
            // HttpMultipartMode.RFC6532    避免文件名为中文时乱码
//            MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532);
//            builder.setCharset(Consts.UTF_8);
//            builder.setContentType(ContentType.APPLICATION_JSON);
//            //或者使用字节流也行,根据具体需要使用
//            //builder.addBinaryBody("file", Files.readAllBytes(file.toPath()), ContentType.APPLICATION_OCTET_STREAM, file.getName());
//            // 添加参数 addTextBody的key可以自定义和被调接口的入参保持一直    可多个addTextBody     key不一样即可  需在接收方接收
//            builder.addTextBody("param", param);
            //builder.addTextBody("key1", param);
            //可以设置 请求头
            httppost.addHeader("token", token);
            httppost.addHeader("Content-Type", "application/json");
//            HttpEntity reqEntity = builder.build();
            httppost.setEntity(new StringEntity(param));
            // 设置超时时间
            httppost.setConfig(getConfig());
            response = httpClient.execute(httppost);
            //我这里  调用接口返的字节流  所以获取字节数组
            //resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            HttpEntity entity = response.getEntity();
            //返回体应该是pdf
            if (entity.getContentType().toString().contains("application/json")) {
                System.out.println(EntityUtils.toString(entity, "UTF-8"));
                return null;
            }
//            response.getHeaders("Content-Type");
//            entity.getContentType().toString();
//            String data=EntityUtils.toString(entity,"UTF-8");
            //输出流 字节数组
            bytes = EntityUtils.toByteArray(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        InputStream inputStream = new ByteArrayInputStream(bytes);
        OutputStream outputStream = null;
        try {
            // todo下载的目录不存在需要创建
            byte[] bs = new byte[1024];
            int len;
            outputStream = Files.newOutputStream(Paths.get(downloadPath));
            while ((len = inputStream.read(bs)) != -1) {
                outputStream.write(bs, 0, len);
            }
        } catch (Exception e) {
            throw new RuntimeException("文件保存失败：" + e);
        } finally {
            try {
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return downloadPath;
    }

    //超时时间;
    private static RequestConfig getConfig() {
        return RequestConfig.custom().setConnectionRequestTimeout(50000).setSocketTimeout(150000)
                .setConnectTimeout(50000).build();
    }

    /**
     * HTTPS   设置SSL请求处理
     */
    private static CloseableHttpClient sslClient() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLSv1.2");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
                        throws CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
                        throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLConnectionSocketFactory sslConnectionSocketFactory = SSLConnectionSocketFactory.getSocketFactory();
            return HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }


    public static String postInnerAppoint(boolean archiveDir, String fileName, String path, String newPath, String url) {
        File f = new File(path);
        if (!f.exists() || !f.isFile()) throw new JPMException(String.format(FileMsg.notFoundFile, path));
        // 创建Http实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建HttpPost实例
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(getConfig());
        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(StandardCharsets.UTF_8);
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            // 文件流
            builder.addBinaryBody("file", Files.newInputStream(f.toPath()), ContentType.MULTIPART_FORM_DATA, fileName);
            //表单中其他参数
            builder.addPart("path", new StringBody(newPath, ContentType.create("text/plain", Consts.UTF_8)));
            builder.addPart("archiveDir", new StringBody(String.valueOf(archiveDir), ContentType.create("text/plain", Consts.UTF_8)));
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);// 执行提交
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 返回
                return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("调用HttpPost失败！", e);
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    log.error("关闭HttpPost连接失败！", e);
                }
            }
        }
        return null;
    }


    /**
     * 解压压缩包到指定位置
     *
     * @param path   压缩文件
     * @param target 解压地址
     */
    public static void decompression(String path, String target) {
        File dir = new File(target);
        if (!dir.exists() || !dir.isDirectory()) dir.mkdirs();
        if (path.endsWith("zip")) {
            try {
                unzip(path, target);
            } catch (IOException exception) {
                throw new JPMException(FileMsg.unzipError);
            }
        } else throw new JPMException(FileMsg.unSupportedCompress);
    }

    public static void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipFile zipFile = new ZipFile(zipFilePath);
        Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();
        while (entries.hasMoreElements()) {
            ZipArchiveEntry entry = entries.nextElement();
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                File parentDir = new File(filePath).getParentFile();
                if (!parentDir.exists()) {
                    parentDir.mkdirs();
                }
                extractFile(zipFile, entry, filePath);
            } else {
                File dir = new File(filePath);
                if (!dir.exists())
                    dir.mkdir();
            }
        }
        zipFile.close();
    }

    private static void extractFile(ZipFile zipFile, ZipArchiveEntry entry, String filePath) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
        File newFile = new File(filePath);
        FileOutputStream fos = new FileOutputStream(newFile);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        byte[] bytesIn = new byte[4096];
        int read = 0;
        while ((read = bis.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }

        bos.close();
        bis.close();
    }

    public static void deleteFile(File file) {
        if (file != null) {
            if (file.exists() && file.isFile()) file.delete();
        }
    }

    public static void deleteFiles(File directory, boolean removeDir) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    file.delete(); // 删除文件
                } else if (file.isDirectory()) {
                    deleteFiles(file, true); // 递归删除子目录下的文件
                }
            }
        }
        if (removeDir) directory.delete();
    }

    public static void moveTo(String sourcePath, String targetDirectory, String newFileName) {
        File sourceFile = new File(sourcePath);
        if (!sourceFile.exists()) {
            throw new JPMException(String.format(FileMsg.notFoundFile, sourcePath));
        }
        File targetDir = new File(targetDirectory);
        if (!targetDir.isDirectory() || !sourceFile.exists())
            targetDir.mkdirs();
        File targetFile = new File(targetDirectory + "/" + newFileName);
        try (InputStream inStream = new FileInputStream(sourceFile)) {
            Files.copy(inStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new JPMException(FileMsg.moveError);
        }
    }

    public static void zip(String sourceDir, String zipFile) {
        try {
            // 创建缓冲区
            byte[] buffer = new byte[1024];
            // 创建输出流
            FileOutputStream fos = new FileOutputStream(zipFile);
            // 创建压缩输出流
            ZipOutputStream zos = new ZipOutputStream(fos);
            // 获取源目录文件集合
            File dir = new File(sourceDir);
            File[] files = dir.listFiles();
            // 遍历文件集合，压缩每个文件和文件夹
            for (File file : files) {
                if (file.isFile() && !file.isDirectory()) {
                    // 如果是文件，则添加到压缩文件
                    addFileToZip(zos, buffer, file, "");
                } else {
                    // 如果是文件夹，则递归调用此方法
                    zipDirectory(zos, buffer, file, "");
                }
            }
            // 关闭流
            zos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void zipDirectory(ZipOutputStream zos, byte[] buffer, File file, String parentPath) throws IOException {
        // 创建目录条目
        ZipEntry dirZipEntry = new ZipEntry(parentPath + file.getName() + "/");
        zos.putNextEntry(dirZipEntry);
        // 关闭当前条目，开始写入下一个条目
        zos.closeEntry();
        // 如果是文件夹，则递归调用此方法
        if (file.isFile() && !file.isDirectory()) {
            // 如果是文件，则添加到压缩文件
            addFileToZip(zos, buffer, file, parentPath);
        } else {
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isFile() && !f.isDirectory())
                    addFileToZip(zos, buffer, f, parentPath + file.getName() + "/");
                else
                    zipDirectory(zos, buffer, f, parentPath + file.getName() + "/");
            }
        }
    }

    private static void addFileToZip(ZipOutputStream zos, byte[] buffer, File file, String parentPath) throws IOException {
        // 创建文件条目并添加到压缩文件
        ZipEntry zipEntry = new ZipEntry(parentPath + file.getName());
        zos.putNextEntry(zipEntry);
        // 从文件中读取数据并写入压缩文件
        FileInputStream fis = new FileInputStream(file);
        int len;
        while ((len = fis.read(buffer)) > 0) {
            zos.write(buffer, 0, len);
        }
        // 关闭当前条目，开始写入下一个条目
        zos.closeEntry();
        // 关闭输入流和输出流
        fis.close();
    }

    /**
     * 获取文件路径中段(没有前缀根目录以及后缀文件名)
     */
    public static String path(String dirPath, String code, String uid, String root) {
        String dateDir = new SimpleDateFormat("yyyy/MM").format(new Date());
        String filePath = dirPath + code + "/" + uid + "/" + dateDir;
        String fileDirPath = root + "/" + filePath;
        File fileDir = new File(fileDirPath);
        if (!fileDir.exists() || !fileDir.isDirectory())
            fileDir.mkdirs();
        return filePath;
    }

    /**
     * 调整图片尺寸
     *
     * @param inputImagePath
     * @param outputImagePath
     * @param targetWidth
     * @param targetHeight
     * @param suffix
     */
    public static void resizeImage(String inputImagePath, String outputImagePath, int targetWidth, int targetHeight, String suffix, String fName) {
        try {
            String png = null;
            if (!suffix.equalsIgnoreCase("png")) {
                //非png图片转为png图片后再去调整图片尺寸
                File inputFile = new File(inputImagePath);
                BufferedImage inputImage = ImageIO.read(inputFile);
                File outputFile = new File(outputImagePath + GenerateUUID.getUUID() + ".png");
                ImageIO.write(inputImage, "PNG", outputFile);
                png = outputFile.getPath();
            }
            dd(ValidateUtil.isEmpty(png) ? inputImagePath : png, outputImagePath, targetWidth, targetHeight, fName);
            if (!ValidateUtil.isEmpty(png)) new File(png).delete(); //尺寸调整完成后删除转换后的png原图
        } catch (IOException e) {
            log.error("调整图片尺寸报错", e);
            e.printStackTrace();
        }
    }

    public static void dd(String inputImagePath, String outputImagePath, int targetWidth, int targetHeight, String suffix) {
        try {
            File inputFile = new File(inputImagePath);
            BufferedImage inputImage = ImageIO.read(inputFile);
            BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, inputImage.getType());
            outputImage.getGraphics().drawImage(inputImage, 0, 0, targetWidth, targetHeight, null);
            File outputFile = new File(outputImagePath + suffix);
            ImageIO.write(inputImage, "PNG", outputFile);
        } catch (IOException e) {
            log.error("调整图片尺寸报错", e);
            e.printStackTrace();
        }
    }


    public static File[] findExcel(String directoryPath) {
        File directory = new File(directoryPath);
        return directory.listFiles(new ExcelFileFilter());
    }


    public static void fiClose(FileInputStream fis) {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                e.printStackTrace();
            }
        }
    }

    public static void isClose(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
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
                log.error(e.getMessage(), e);
                e.printStackTrace();
            }
        }
    }


    public static void fosClose(FileOutputStream fos) {
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                e.printStackTrace();
            }
        }
    }

    public static void fisClose(FileInputStream fis) {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
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
                log.error(e.getMessage(), e);
                e.printStackTrace();
            }
        }
    }

    static class ExcelFileFilter implements FileFilter {
        @Override
        public boolean accept(File file) {
            String fileName = file.getName();
            return fileName.endsWith(".xls") || fileName.endsWith(".xlsx"); // 只处理Excel文件
        }
    }

    public static boolean isImage(File file) {
        if (!isFile(file.getPath())) {
            return false;
        }
        String type = FileTypeUtil.getType(file);
        return type.matches("jpg|jpeg|png|gif|bmp|webp");
    }
}