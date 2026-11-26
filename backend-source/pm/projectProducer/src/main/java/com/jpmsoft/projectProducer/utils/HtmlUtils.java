package com.jpmsoft.projectProducer.utils;

import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineProjectStyleDtl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.util.*;

public class HtmlUtils {
    /**
     * 将HTML字符串解析为纯文本
     *
     * @param html
     * @return
     */
    public static String parseToText(String html) {
        if (html == null || html.isEmpty()) {
            return "";
        }
        Document doc = Jsoup.parse(html);
        String text = doc.text();
        return text.replace('\u00A0', ' ').replaceAll("\\s{2,}", " ");
    }

    /**
     * 美化HTML输出
     *
     * @param html
     * @return
     */
    public static String prettyHtmlPrint(String html, Map<String, OutlineProjectStyleDtl> styleDtlMap) {
        if (ValidateUtil.isEmpty(html) || styleDtlMap == null || styleDtlMap.isEmpty()) {
            return html;
        }
        // 获取样式
        OutlineProjectStyleDtl textStyle = styleDtlMap.get("01");
        OutlineProjectStyleDtl tableStyle = styleDtlMap.get("02");
        OutlineProjectStyleDtl imageStyle = styleDtlMap.get("03");

        // 使用jsoup
        Document doc = Jsoup.parse(html);
        // 标记所有自己居中的段落
        doc.select("p[align~=(?i)center], p[style*=text-align:center], p[style*=text-align: center]")
                .attr("data-self-center", "1");
        // 清除全部的样式
        doc.select("*")
                .removeAttr("style").removeAttr("class").removeAttr("id")
                .removeAttr("align").removeAttr("width").removeAttr("height");

        // 文字处理
        // 去除不需要的标签
        doc.select("font, span").unwrap();
        // 清除首尾空格
        trimParagraphSpaces(doc.select("p"));

        // 把字体改成指定类型
        StringBuilder pStyle = getTextStyle(textStyle);
        // 已经居中的不处理对齐
        doc.select("p[data-self-center=1]").attr("style", pStyle + "text-align:" + getTextAlign("02") + ";");

        doc.select("p:not([data-self-center=1])").attr("style", pStyle + "text-align:" + getTextAlign(textStyle.getAlign()) + ";");

        // h标签改为p标签
        doc.select("h1, h2, h3, h4, h5, h6").forEach(h -> {
            Element p = new Element(Tag.valueOf("p"), "");
            // 清除style里的text-indent
            String style = pStyle.toString().replaceAll("text-indent:[^;]+;", "");
            p.attr("style", style + "text-align:" + getTextAlign(textStyle.getAlign()) + ";");
            p.text(h.text());
            h.replaceWith(p);
        });

        // 图片处理
        StringBuilder iStyle = new StringBuilder();
        iStyle.append("max-width:100%;height:auto;")
                .append(getImageAlignStyle(imageStyle.getAlign()));
        doc.select("img").forEach(img -> {
            img.attr("style", iStyle.toString());
            boolean alreadyCentered = isAlreadyCentered(img);
            if (!alreadyCentered && "02".equals(imageStyle.getAlign())) {
                Element wrapper = new Element(Tag.valueOf("div"), "");
                wrapper.attr("style", "text-align:center;");
                img.replaceWith(wrapper);
                wrapper.appendChild(img);
            }
        });

        // 表格处理
        double borderWidthPx = tableStyle.getTableBorderWidth() != null ? tableStyle.getTableBorderWidth() * 1.333 : 1.0;
        String tableAlign = getTextAlign(tableStyle.getAlign());

        doc.select("table").forEach(table -> {
            table.attr("style", "border:" + borderWidthPx + "px solid #000;"
                    + "border-collapse:collapse;table-layout:fixed;width:100%;"
                    + "text-align:" + tableAlign + ";"
                    + "font-family:" + "'" + tableStyle.getFont() + "', '" + tableStyle.getFontEng() + "';"
                    + "font-size:" + Math.round(tableStyle.getFontSize() * 1.333f) + "px;"
            );

            // 表头和单元格样式
            String tdStyle = "border:" + borderWidthPx + "px solid #000;padding:8px 10px;text-align:center;"
                    + "vertical-align:" + getVerticalAlign(tableStyle.getAlign()) + ";word-break:break-word;white-space:normal;";

            Element thead = table.selectFirst("thead");
            if (thead != null) {
                thead.select("td").forEach(td -> td.tagName("th"));
                thead.select("th").attr("style", tdStyle);
            } else {
                Element firstTr = table.selectFirst("tr");
                if (firstTr != null) {
                    firstTr.select("td").forEach(td -> td.tagName("th"));
                    firstTr.select("th").attr("style", tdStyle);
                }
            }

            table.select("tbody td").forEach(td -> td.attr("style", tdStyle));

            table.select("caption").attr("style",
                    "caption-side:top;text-align:center;margin-bottom:6px;color:#666;font-size:13px;");

            table.select("p").attr("style", getTextStyle(tableStyle) + "text-align:" + tableAlign);
        });

        // 清除所有的data-self-center属性
        doc.select("p[data-self-center]").removeAttr("data-self-center");

        return doc.body().html();
    }

    private static StringBuilder getTextStyle(OutlineProjectStyleDtl style) {
        String lineHeight = "1.6";
        if ("01".equals(style.getLineSpacingType())) {
            lineHeight = style.getLineSpacingValue() + "em";
        } else if ("02".equals(style.getLineSpacingType())) {
            lineHeight = style.getLineSpacingValue() + "px";
        }

        long beforePx = style.getParagraphBefore() != null ? Math.round(style.getParagraphBefore() * 1.333f) : 0;
        long afterPx = style.getParagraphAfter() != null ? Math.round(style.getParagraphAfter() * 1.333f) : 0;

        StringBuilder pStyle = new StringBuilder();
        pStyle.append("margin-top:").append(beforePx).append("px;").append("color:#000;")
                .append("font-family:").append("'").append(style.getFont()).append("', '").append(style.getFontEng()).append("'").append(";")
                .append("font-size:").append(Math.round(style.getFontSize() * 1.333f)).append("px;")
                .append("margin-bottom:").append(afterPx).append("px;")
                .append("padding:0;").append("line-height:").append(lineHeight).append(";");

        if (style.getIndentLeft() != null && style.getIndentLeft() > 0) {
            pStyle.append("padding-left:").append(style.getIndentLeft()).append("em;");
        }
        if (style.getIndentRight() != null && style.getIndentRight() > 0) {
            pStyle.append("padding-right:").append(style.getIndentRight()).append("em;");
        }
        if ("01".equals(style.getIndentSpecial())) {
            pStyle.append("text-indent:").append(style.getIndentSpecialValue()).append("em;");
        } else if ("02".equals(style.getIndentSpecial())) {
            pStyle.append("padding-left:").append(style.getIndentSpecialValue()).append("em;");
            pStyle.append("text-indent:-").append(style.getIndentSpecialValue()).append("em;");
        }
        if (style.getBold()) {
            pStyle.append("font-weight:bold;");
        }
        if (style.getItalic()) {
            pStyle.append("font-style:italic;");
        }
        return pStyle;
    }

    private static String getTextAlign(String alignCode) {
        switch (alignCode) {
            case "02":
                return "center";
            case "03":
                return "right";
            case "04":
                return "justify";
            case "01":
            default:
                return "left";
        }
    }

    private static String getVerticalAlign(String alignCode) {
        switch (alignCode) {
            case "01":
                return "top";
            case "03":
                return "bottom";
            case "02":
            default:
                return "middle";
        }
    }

    private static String getImageAlignStyle(String alignCode) {
        switch (alignCode) {
            case "02":
                return "display:block;margin-left:auto;margin-right:auto;";
            case "03":
                return "display:block;margin-left:auto;margin-right:0;";
            case "01":
            default:
                return "display:block;margin-left:0;margin-right:auto;";
        }
    }

    /**
     * 判断图片是否已经被包裹在一个居中容器中
     *
     * @param img
     * @return
     */
    private static boolean isAlreadyCentered(Element img) {
        Element parent = img.parent();
        // 判断父元素是否已经是一个居中容器
        boolean alreadyCentered = false;
        if (parent != null) {
            String tag = parent.tagName();
            String style = parent.attr("style").toLowerCase();
            String align = parent.attr("align").toLowerCase();

            alreadyCentered = ("div".equals(tag) && style.contains("text-align:center"))
                    || ("div".equals(tag) && "center".equals(align));
        }
        return alreadyCentered;
    }

    /**
     * 去除段落首尾的空白字符，包括空格、全角空格、换行符、制表符等
     *
     * @param paras
     */
    private static void trimParagraphSpaces(Elements paras) {
        if (paras == null) {
            return;
        }

        Set<String> inlineTags = new HashSet<>(Arrays.asList(
                "span", "b", "strong", "i", "em", "u", "s", "sub", "sup", "font", "a"));

        for (Element p : paras) {
            while (p.childNodeSize() > 0) {
                Node first = p.childNode(0);
                if (isWhitespaceNode(first, inlineTags)) {
                    first.remove();
                } else {
                    TextNode t = findFirstTextDesc(first);
                    if (t != null) {
                        t.text(t.text().replaceFirst("^[\\s\\u00A0\\u3000]+", ""));
                    }
                    break;
                }
            }

            while (p.childNodeSize() > 0) {
                Node last = p.childNode(p.childNodeSize() - 1);
                if (isWhitespaceNode(last, inlineTags)) {
                    last.remove();
                } else {
                    TextNode t = findLastTextDesc(last);
                    if (t != null) {
                        t.text(t.text().replaceFirst("[\\s\\u00A0\\u3000]+$", ""));
                    }
                    break;
                }
            }
        }
    }

    /**
     * 判断一个节点是否是空白节点
     *
     * @param n
     * @param inlineTags
     * @return
     */
    private static boolean isWhitespaceNode(Node n, Set<String> inlineTags) {
        if (n instanceof TextNode) {
            String s = ((TextNode) n).text().replace('\u00A0', ' ').replace('\u3000', ' ');
            return s.trim().isEmpty();
        }
        if (n instanceof Element) {
            Element el = (Element) n;
            String tag = el.tagName().toLowerCase();
            if ("br".equals(tag)) {
                return true;
            }
            if (inlineTags.contains(tag) && el.select("img,video,canvas,svg").isEmpty()) {
                String s = el.text().replace('\u00A0', ' ').replace('\u3000', ' ');
                return s.trim().isEmpty();
            }
        }
        return false;
    }

    /**
     * 查找节点的第一个文本节点
     *
     * @param n
     * @return
     */
    private static TextNode findFirstTextDesc(Node n) {
        if (n instanceof TextNode) {
            return (TextNode) n;
        }
        if (n instanceof Element) {
            for (Node c : n.childNodes()) {
                TextNode t = findFirstTextDesc(c);
                if (t != null) {
                    return t;
                }
            }
        }
        return null;
    }

    /**
     * 查找节点的最后一个文本节点
     *
     * @param n
     * @return
     */
    private static TextNode findLastTextDesc(Node n) {
        if (n instanceof TextNode) {
            return (TextNode) n;
        }
        if (n instanceof Element) {
            List<Node> cs = n.childNodes();
            for (int i = cs.size() - 1; i >= 0; i--) {
                TextNode t = findLastTextDesc(cs.get(i));
                if (t != null) {
                    return t;
                }
            }
        }
        return null;
    }

    /**
     * 判断HTML内容是否为空
     * 规则：
     *  1. 没有任何文本内容
     *  2. 所有段落、表格、列表等结构均为空（只包含空白字符或单个<br>标签）
     *  3. 图片、占位符算有效内容
     */
    public static boolean isInvalidHtml(String html) {
        if (html == null || html.trim().isEmpty()) {
            return true;
        }
        Document doc = Jsoup.parse(html);

        if (html.contains("${")) {
            return false;
        }

        Elements imgs = doc.select("img");
        for (Element img : imgs) {
            String src = img.attr("src");
            if (src != null && !src.trim().isEmpty()) {
                return false;
            }
        }

        Elements cells = doc.select("td, th");
        for (Element cell : cells) {
            String text = cell.text().trim();
            boolean onlyBr = cell.children().size() == 1 && "br".equalsIgnoreCase(cell.child(0).tagName());
            if (!text.isEmpty() || (!cell.children().isEmpty() && !onlyBr)) {
                return false;
            }
        }

        Elements ps = doc.select("p");
        for (Element p : ps) {
            String text = p.text().trim();
            boolean onlyBr = p.children().size() == 1 && "br".equalsIgnoreCase(p.child(0).tagName());
            if (!text.isEmpty() || (!p.children().isEmpty() && !onlyBr)) {
                return false;
            }
        }

        Elements lis = doc.select("li");
        for (Element li : lis) {
            if (!li.text().trim().isEmpty()) {
                return false;
            }
        }

        if (!doc.text().trim().isEmpty()) {
            return false;
        }

        return true;
    }
}
