package com.jpmsoft.jpbpm.util;

import com.jpmsoft.common.utils.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
public class fileLog {
    /**
     * @将错误信息输入到txt中 * @param path * @throws IOException
     */
    public void write_to_txt(String logSavePath, String content) throws IOException {
        try {


            if (ValidateUtil.isNotEmpty(logSavePath)) {
                File F = new File(logSavePath + "/" + this.get_nowDate() + ".text");
                //判断文件目录是否存在，如果不存在则创建
                File dir = new File(logSavePath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                //如果文件不存在,就动态创建文件
                if (!F.exists()) {
                    F.createNewFile();
                }
                FileWriter fw = null;
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String writeDate = "时间:" + sd.format(new Date()) + "---" + "日志内容:" + content;
                try {
                    //设置为:True,表示写入的时候追加数据
                    fw = new FileWriter(F, true);
                    // 回车并换行
                    fw.write(writeDate + "\r\n");
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fw != null) {
                        fw.close();
                    }
                }
                try {
                    //判断三个月前的文件是否存在，如果存在则删除
                    Date DateTime = DateUtil.DateChangeDay(new Date(), -90);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    File deleteFlie = new File(logSavePath + "/" + sdf.format(DateTime) + ".text");
                    if (deleteFlie.exists()) {
                        deleteFlie.delete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            log.error(content);
        }
    }


    public  void  write_to_txt_batch(String logSavePath,String uId, List<String> contents){
         if(!CollectionUtils.isEmpty(contents)){
             for (String content : contents) {
                 try {
                     write_to_txt(logSavePath, "操作人id："+uId+"；"+content);
                 }catch (Exception e){
                     log.error("write_to_txt_batch error:", e);
                 }
             }
         }
    }


    /**
     * @获取系统当前时间 * @return
     */
    public String get_nowDate() {
        Calendar D = Calendar.getInstance();
        int year = 0;
        int moth = 0;
        int day = 0;
        year = D.get(Calendar.YEAR);
        moth = D.get(Calendar.MONTH) + 1;
        day = D.get(Calendar.DAY_OF_MONTH);
        String now_date = String.valueOf(year) + "-" + String.valueOf(moth) + "-" + String.valueOf(day);
        return now_date;
    }
    //测试方法
}
