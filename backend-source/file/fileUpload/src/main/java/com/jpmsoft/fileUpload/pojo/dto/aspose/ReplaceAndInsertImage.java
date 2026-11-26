package com.jpmsoft.fileUpload.pojo.dto.aspose;


import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.IReplacingCallback;
import com.aspose.words.Node;
import com.aspose.words.ReplaceAction;
import com.aspose.words.ReplacingArgs;

public class ReplaceAndInsertImage implements IReplacingCallback{
    public String url;
    public double width=150d;
    public double height=150d;
    public ReplaceAndInsertImage(String url){
        this.url = url;
    }
    public ReplaceAndInsertImage(String url,double width,double height){
        this.url = url;
        this.width = width;
        this.height = height;
    }

    @Override
    public int replacing(ReplacingArgs e) throws Exception {
        //获取当前节点
        Node node = e.getMatchNode();
        //获取当前文档
        Document document =  (Document) node.getDocument();
        DocumentBuilder builder = new DocumentBuilder(document);
//        //将光标移动到指定节点
        builder.moveTo(node);
//        //插入图片
        builder.insertImage(url,width,height);
        return ReplaceAction.REPLACE;
    }
}
