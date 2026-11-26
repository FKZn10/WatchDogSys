package com.jpmsoft.fileUpload.utils.lucene;

import com.jpmsoft.fileUpload.pojo.dto.LuceneFileDTO;
import com.jpmsoft.fileUpload.utils.ReadFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.util.CollectionUtils;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * lucene 索引创建
 *
 * @Author: 李文涛 aaron
 * @date: 2023/7/20 8:53
 */
@Slf4j
public class Indexer {

    /*writer : 索引对象, 能够建立索引(即能够把文件中的词提取出来，并标注出现的次数及出现的位置及哪个文件)*/
    private IndexWriter writer;

    /**
     * 关闭
     *
     * @throws IOException
     */
    public void close() throws IOException {
        if (writer != null && writer.isOpen())
            writer.close();
    }

    /*
     * 构造方法，实例化IndexWriter
     * @param indexDir   //索引目录(要搜索信息的目录)
     * @throws Exception
     */
    public Indexer(String indexDir) throws IOException {
        // 构造方法传递一个存储建立索引的目录(文件夹的路径), 即要放建立的索引存储在哪里
        Directory dir = FSDirectory.open(Paths.get(indexDir));
        //打开索引文件夹
        Analyzer analyzer = new IKAnalyzer();
        //标准分词器,会自动去掉空格, is a the等单词
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        //将标准分词器配置到写索引的配置中， 索引时将会 去掉空格， is, a, the等
        writer = new IndexWriter(dir, config);
        //创建实例化索引对象
    }

    /**
     * 获取文档，文档里再设置每个字段，就类似于数据库中的一行记录
     *
     * @param file
     * @return
     * @throws Exception
     */
    private Document getDocument(File file) throws IOException {
        Document doc = new Document();
        //开始添加字段
        // 把doc当成数据库中的表的一行记录信息, 三个字段及对应的值
        //字段一： contents:值(表中的内容)
        //字段二： fileName:值(文件名)
        //字段三： fullPath:值(文件的路径)
        //添加内容
        doc.add(new TextField("contents", ReadFile.read(file.getPath()), Field.Store.YES));
        //添加文件名，并把这个字段存到索引文件里
        doc.add(new StringField("fileName", file.getName(), Field.Store.YES));
        //添加文件路径
        doc.add(new StringField("fullPath", file.getCanonicalPath(), Field.Store.YES));
        return doc;
    }

    /*索引指定的文件
    @param file
    @throws Exception
    */
    private void indexFile(File file) throws IOException {
        System.out.println("索引文件的路径：" + file.getCanonicalPath());
        Document doc = getDocument(file);
        //调用上面的getDocument方法， 获取该文件的document对象
        writer.addDocument(doc);
        //将doc添加到索引实例对象中
    }

    public void index(String filePath) throws IOException {
        File f = new File(filePath);
        if (f.exists() && f.isFile())
            indexFile(f);
    }

    /* 索引指定目录下的所有文件
     @param dataDir
     @return
     @throws Exception*/
    public int indexAll(String dataDir) throws Exception {
        File[] files = new File(dataDir).listFiles();
        //获取dataDir目录下的所有文件
        int numDocs = 0;
        if (null != files) {
            for (File file : files) {
                //调用上面的indexFile方法，对每个文件进行索引
                indexFile(file);
                //理解为： 有多少个文件，在writer中就有多少行信息
                //每行信息含有文件名，文件路径，及文件内容
            }
            numDocs = writer.numRamDocs();
            writer.close();
        }
        return numDocs;
        //返回索引的文件数
    }


    /**
     * 根据文件目录简历索引
     *
     * @param dataDir
     */
    public static void makeIndex(String indexDir, String dataDir) {
        Indexer indexer = null;
        int indexedNum = 0;
        //记录索引开始时间
        long startTime = System.currentTimeMillis();
        try {
            //开始构建索引
            indexer = new Indexer(indexDir);
            indexedNum = indexer.indexAll(dataDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("索引耗时" + (endTime - startTime) + "毫秒");
        System.out.println("共索引了" + indexedNum + "个文件");
    }


    /**************************************************************************************************************/

    public static int makeIndex(List<LuceneFileDTO> data, String indexDir) {
        if (CollectionUtils.isEmpty(data)) return 0;
        try {
            Indexer indexer = new Indexer(indexDir);
            return indexer.createIndex(data);
        } catch (IOException ex) {
            ex.printStackTrace();
            log.error("索引创建失败" + ex.getMessage(),ex);
        }
        return 0;
    }

    private int createIndex(List<LuceneFileDTO> data) throws IOException {
        int numDocs = 0;
        if (!CollectionUtils.isEmpty(data)) {
            for (LuceneFileDTO lf : data) {
                File file = new File(lf.getFilePath());
                if (file.exists() && file.isFile()) {
                    System.out.println("索引文件名：" + lf.getFileName() + "  文件路径：" + lf.getFilePath());
                    Document doc = getDocument(lf);
                    //调用上面的getDocument方法， 获取该文件的document对象
                    writer.addDocument(doc);
                }
            }
            numDocs = writer.numRamDocs();
            writer.close();
        }
        return numDocs;
    }

    private Document getDocument(LuceneFileDTO lf) {
        Document doc = new Document();
        //添加内容
        doc.add(new TextField("contents", ReadFile.read(lf.getFilePath()), Field.Store.YES));
        //添加文件名，并把这个字段存到索引文件里
        doc.add(new StringField("fileName", lf.getFileName().contains(".") ? lf.getFileName().substring(0, lf.getFileName().indexOf(".")) : lf.getFileName(), Field.Store.YES));
        //添加归档信息ID
        doc.add(new StringField("afId", lf.getAfId(), Field.Store.YES));
        return doc;
    }
}
