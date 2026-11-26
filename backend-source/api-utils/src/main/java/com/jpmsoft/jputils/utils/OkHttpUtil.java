package com.jpmsoft.jputils.utils;

import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.yaml.snakeyaml.util.UriEncoder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * ok http
 *
 * @Author: 李文涛 aaron
 * @date: 2023/2/8 16:03
 */
@Slf4j
public class OkHttpUtil {


    public static String doGet(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        return res(client, request, url);
    }

    /**
     * application/x-www-form-urlencoded类型请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String formPost(String url, Map<String, String> params) {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (ValidateUtil.isNotEmpty(entry.getValue())) {
                log.info("formPost参数 key:" + entry.getKey() + "  value:" + entry.getValue());
                formBodyBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        // 构建RequestBody
        RequestBody formBody = formBodyBuilder.build();
        // 创建请求
        Request request = new Request.Builder()
                .url(url) // 替换为你的目标URL
                .post(formBody) // 设置请求方法为POST，并传入构建的RequestBody
                .build();
        // 发送请求并处理响应
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new JPMException("Unexpected code " + response);
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            throw new JPMException("OkHttp request error!");
        }
    }

    public static String doPost(String url, Object params, String bearer) {
        log.info("method:" + url + " params:" + JacksonUtils.toJSONString(params) + " token:" + bearer);
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(JacksonUtils.toJSONString(params), mediaType);
        Request.Builder builder = new Request.Builder();
        builder.url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("cache-control", "no-cache");
        if (ValidateUtil.isNotEmpty(bearer))
            builder.addHeader("Authorization", "Bearer " + bearer);
        Request request = builder.build();
        return res(client, request, url);
    }


    public static String doPost(String url, Object params) {
        log.info("请求地址:" + url + "请求参数:" + JacksonUtils.toJSONString(params));
        String str = doPost(url, params, null);
        log.info("请求结果:" + str);
        return str;
    }

    public static String res(OkHttpClient client, Request request, String url) {
        try {
            Response response = client.newCall(request).execute();
            String res = getRequestBody(response);
            response.close();
            return res;
        } catch (IOException ex) {
            ex.printStackTrace();
            log.error("请求失败：" + url);
        }
        return null;
    }

    public static String getRequestBody(Response response) throws IOException {
        ResponseBody responseBody = response.body();
        okio.BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        okio.Buffer buffer = source.getBuffer();
        Charset charset = StandardCharsets.UTF_8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(StandardCharsets.UTF_8);
        }
        return buffer.clone().readString(charset);
    }

    public static void wmtsGet(HttpServletResponse res, String url, String format) {

        try {
            URL uri = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(connection.getInputStream());
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                in.close();
                byte[] tileData = out.toByteArray();
                res.setContentType(format); // 根据实际的瓦片格式设置
                res.getOutputStream().write(tileData);
                res.getOutputStream().flush();
            } else {
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

//
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        try {
//            // 发送请求并获取响应
//            Response response = client.newCall(request).execute();
//            if (response.isSuccessful()) {
//                // 从响应中获取输入流
//                byte[] data = response.body().bytes();
//                res.setContentType(format);
//                res.getOutputStream().write(data);
////                res.reset();
////                String fileName = GenerateUUID.getUUID();
////                if (format.equals("image/png")) fileName += ".png";
////                if (format.equals("image/jpeg")) fileName += ".png";
////                res.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
////                res.addHeader("Content-Length", "" + data.length);
////                res.setContentType("application/octet-stream; charset=UTF-8");
////                IOUtils.write(data, res.getOutputStream());
////                System.out.println("Map tile received and saved successfully.");
//            } else {
//                System.out.println("Failed to receive map tile. Status code: " + response.code());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static String xwfuPost(String url, Map<String, String> params, String bearer) {
        try {
            String pm = xwfuParams(params);
            log.info("xwfuPost 请求地址:+" + url + "请求参数:" + pm);
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // 设置请求方法为 POST
            con.setRequestMethod("POST");
            // 设置请求头为 x-www-form-urlencoded 格式
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if (ValidateUtil.isNotEmpty(bearer))
                con.setRequestProperty("Authorization", "Bearer " + bearer);
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(pm);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                // 打印响应结果
                return response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String xwfuParams(Map<String, String> params) {
        StringBuilder pm = new StringBuilder();
        boolean flag = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (ValidateUtil.isNotEmpty(entry.getValue())) {
                log.info("formPost参数 key:" + entry.getKey() + "  value:" + entry.getValue());
                if (flag) {
                    pm.append(entry.getKey()).append("=").append(UriEncoder.encode(entry.getValue()));
                    flag = false;
                } else
                    pm.append("&").append(entry.getKey()).append("=").append(UriEncoder.encode(entry.getValue()));
            }
        }
        return pm.toString();
    }
}
