package com.jpmsoft.userProducer.utils;

import com.jpmsoft.common.utils.ValidateUtil;
import org.owasp.html.ElementPolicy;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import java.util.List;

/**
 * html xss注入处理  代码文本替换为html实体字符
 * @Author: 李文涛 aaron
 * @date: 2023/4/7 9:42
 */
public class HtmlUtils {

    //允许的标签
    private static final String[] allowedTags = {"h1", "h2", "h3", "h4", "h5", "h6",
            "span", "strong",
            "img", "video", "source",
            "blockquote", "p", "div",
            "ul", "ol", "li",
            "table", "thead", "caption", "tbody", "tr", "th", "td", "br",
            "a"
    };

    //需要转化的标签
    private static final String[] needTransformTags = {"article", "aside", "command", "datalist", "details", "figcaption", "figure",
            "footer", "header", "hgroup", "section", "summary"};

    //带有超链接的标签
    private static final String[] linkTags = {"img", "video", "source", "a"};

    public static String sanitizeHtml(String htmlContent) {
        if (ValidateUtil.isEmpty(htmlContent)) return null;
        PolicyFactory policy = new HtmlPolicyBuilder()
                //所有允许的标签
                .allowElements(allowedTags)
                //内容标签转化为div
                .allowElements(new ElementPolicy() {
                    @Override
                    public String apply(String elementName, List<String> attributes) {
                        return "div";
                    }
                }, needTransformTags)
                .allowAttributes("src", "href", "target").onElements(linkTags)
                //校验链接中的是否为http
                .allowUrlProtocols("https")
                .toFactory();
        return policy.sanitize(htmlContent);
    }
}
