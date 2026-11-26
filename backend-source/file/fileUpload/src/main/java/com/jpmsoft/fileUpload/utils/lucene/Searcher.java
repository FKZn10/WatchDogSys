package com.jpmsoft.fileUpload.utils.lucene;

import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.pojo.dto.FullTextDTO;
import com.jpmsoft.fileUpload.service.ISysConfigService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.lucene.IKAnalyzer;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * lucene查询类
 *
 * @Author: 李文涛 aaron
 * @date: 2023/7/20 9:12
 */
@Component
public class Searcher {

    @Resource
    private ISysConfigService sysConfigService;

    public List<String> search(FullTextDTO request) throws IOException, ParseException {
        String indexDir = sysConfigService.getSysConfByCode("luceneIndexDir");
        if (ValidateUtil.isEmpty(indexDir))
            throw new JPMException(String.format(FileMsg.notFoundSysConfig, "luceneIndexDir"));
        Directory dir = FSDirectory.open(Paths.get(indexDir));
        //获取要查询的路径， 也就是索引所在的位置
        IndexReader reader = DirectoryReader.open(dir);
        //构建IndexSearcher
        IndexSearcher searcher = new IndexSearcher(reader);
        //标准分词器， 会自动去掉空格， is a the等单词
        Analyzer analyzer = new IKAnalyzer();
        //查询的字段为contents以及fileName(建立索引时生成的表字段)
        String[] fields = {"fileName", "contents"};
        TopDocs docs;
        //查询解析器
        QueryParser parser = new MultiFieldQueryParser(fields, analyzer);
        long startTime = System.currentTimeMillis();
        Query query;
        if (request.getText().contains(",")) {
            // 多条件必备神器
            BooleanQuery.Builder builder = new BooleanQuery.Builder();
            for (String s : request.getText().split(",")) {
                Query qr = parser.parse(s);
                builder.add(qr, BooleanClause.Occur.SHOULD);
            }
            query = builder.build();
        } else {
            //通过解析要查询的String, 获取查询对象, q为传赤来的待查的字符串
            query = parser.parse(request.getText());
        }
        if (request.getPage() <= 1)
            docs = searcher.search(query, request.getSize());
        else {
            int start = (request.getPage() - 1) * request.getSize();
            TopDocs topDocs = searcher.search(query, start);
            ScoreDoc preScore = topDocs.scoreDocs[topDocs.scoreDocs.length - 1];
            docs = searcher.searchAfter(preScore, query, request.getSize());
        }
        //记录索引结束时间
        long endTime = System.currentTimeMillis();
        System.out.println("匹配" + request.getText() + "共耗时" + (endTime - startTime) + "毫秒");
        System.out.println("查询到" + docs.totalHits + "条记录");
        List<String> afIds = new ArrayList<>();
        //取出每条查询结果
        for (ScoreDoc scoreDoc : docs.scoreDocs) {
            //scoreDoc.doc相当于docId, 根据这个docID来获取文档
            Document doc = searcher.doc(scoreDoc.doc);
            //fullPath是刚刚建立索引时候我们定义的一个字段，表示路径。也可以取其它的内容，只要我们在建立索引时有定义即可.
            System.out.println(doc.get("afId") + "  " + doc.get("fileName"));
            afIds.add(doc.get("afId"));
        }
        reader.close();
        return afIds;
    }

    public static List<String> wordExtraction(String text) {
        if (ValidateUtil.isEmpty(text)) return null;
        List<String> result = new ArrayList<>();
        StringReader sr = new StringReader(text);
        //标准分词器， 会自动去掉空格， is a the等单词
        IKSegmenter ik = new IKSegmenter(sr, false);
        try {
            Lexeme lex;
            while ((lex = ik.next()) != null) {
                String lexemeText = lex.getLexemeText();
                if (lexemeText.length() > 1)
                    result.add(lexemeText);
            }
        } catch (IOException ex) {
            return null;
        }
        return result;
    }
}
