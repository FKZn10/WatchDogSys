package com.jpmsoft.projectProducer.utils;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.io.IoUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Freemarker模板工具类
 *
 * @author tianhelin
 * @date 2025/7/2
 */
public class FreemarkerUtils {
    public static final Pattern VAR_PATTERN = Pattern.compile("\\$\\{\\s*(\\w+)(?:\\[.*?])?(?:_index)?(?:\\.[^}!]+)?(?:![^}]*)?}");
    public static final Pattern TR_PATTERN = Pattern.compile("<tr[^>]*?>[\\s\\S]*?</tr>", Pattern.CASE_INSENSITIVE);
    public static final Pattern ROOT_PAT = Pattern.compile("\\$\\{\\s*([A-Za-z_][A-Za-z0-9_]*)");

    /**
     * 将数据源渲染上HTML
     *
     * @param htmlTemplate HTML模板字符串
     * @param dataModel    数据源
     * @return 渲染后的HTML字符串
     */
    @SneakyThrows
    public static String renderContent(String htmlTemplate, Object dataModel) {
        if (htmlTemplate == null || htmlTemplate.isEmpty()) {
            return "";
        }
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler((te, env, out) -> {
            String blamed = te.getBlamedExpressionString();
            if (blamed != null && !blamed.isEmpty()) {
                try {
                    out.write("${" + blamed + "}");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw te;
            }
        });
        cfg.setLogTemplateExceptions(false);

        StringTemplateLoader loader = new StringTemplateLoader();
        loader.putTemplate("htmlTemplate", htmlTemplate);
        cfg.setTemplateLoader(loader);

        Template template = cfg.getTemplate("htmlTemplate");

        StringWriter writer = new StringWriter();
        template.process(dataModel, writer);
        return writer.toString();
    }

    /**
     * 自动处理多行列表模板
     *
     * @param html HTML字符串
     * @return 处理后的HTML字符串
     */
    public static String autoWrapMultipleListTemplates(String html) {
        StringBuilder result = new StringBuilder();
        int lastPos = 0;

        Pattern tablePattern = Pattern.compile("<table[^>]*>.*?</table>", Pattern.DOTALL);
        Matcher tableMatcher = tablePattern.matcher(html);

        while (tableMatcher.find()) {
            result.append(html, lastPos, tableMatcher.start());
            String table = tableMatcher.group();

            int tbodyStart = table.indexOf("<tbody>");
            int tbodyEnd = table.indexOf("</tbody>");
            if (tbodyStart == -1 || tbodyEnd == -1) {
                result.append(table);
                lastPos = tableMatcher.end();
                continue;
            }

            String beforeTbody = table.substring(0, tbodyStart + "<tbody>".length());
            String tbodyContent = table.substring(tbodyStart + "<tbody>".length(), tbodyEnd);
            String afterTbody = table.substring(tbodyEnd);

            // 查找所有<tr>
            Pattern trPattern = Pattern.compile("<tr>.*?</tr>", Pattern.DOTALL);
            Matcher trMatcher = trPattern.matcher(tbodyContent);

            List<String> trList = new ArrayList<>();
            while (trMatcher.find()) {
                trList.add(trMatcher.group());
            }

            if (trList.size() < 2) {
                result.append(table);
                lastPos = tableMatcher.end();
                continue;
            }

            String headerRow = trList.get(0);
            String dataRow = trList.get(1);

            // 提取候选 listVar
            Matcher rootM = ROOT_PAT.matcher(dataRow);
            Map<String, Integer> freq = new HashMap<>();
            while (rootM.find()) {
                String var = rootM.group(1);
                freq.put(var, freq.getOrDefault(var, 0) + 1);
            }

            String listVar = null;
            if (!freq.isEmpty()) {
                listVar = freq.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .orElse(null);
            }

            if (listVar == null) {
                result.append(table);
            } else {
                String itemVar = "__it_" + listVar;

                // 替换数据行里的占位
                String newDataRow = dataRow;
                newDataRow = newDataRow.replaceAll("\\$\\{\\s*" + listVar + "\\.", "\\${" + itemVar + ".");
                newDataRow = newDataRow.replaceAll("\\$\\{\\s*" + listVar + "\\[", "\\${" + itemVar + "[");
                newDataRow = newDataRow.replaceAll("\\$\\{\\s*" + listVar + "_index\\b", "\\${" + itemVar + "_index");

                StringBuilder newTbody = new StringBuilder();
                newTbody.append(headerRow).append("\n");

                // 有数据时渲染循环；否则保留原始数据行
                newTbody.append("<#if ").append(listVar).append("?has_content>\n");
                newTbody.append("  <#list ").append(listVar).append(" as ").append(itemVar).append(">\n");
                newTbody.append("    ").append(newDataRow).append("\n");
                newTbody.append("  </#list>\n");
                newTbody.append("<#else>\n");
                newTbody.append("    ").append(dataRow).append("\n");
                newTbody.append("</#if>\n");

                for (int i = 2; i < trList.size(); i++) {
                    newTbody.append(trList.get(i)).append("\n");
                }

                result.append(beforeTbody).append(newTbody).append(afterTbody);
            }

            lastPos = tableMatcher.end();
        }

        result.append(html.substring(lastPos));
        return result.toString();
    }

    /**
     * 将HTML中的图片链接转换为Base64
     *
     * @param html HTML字符串
     * @return 转换后的HTML字符串
     */
    public static String convertImgToBase64(String html) {
        if (html == null || !html.contains("<img")) {
            return html;
        }

        Pattern pattern = Pattern.compile("<img[^>]*?\\s+src=[\"']([^\"']+)[\"'][^>]*?>", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(html);

        // 提取
        List<String> imageTags = new ArrayList<>();
        List<String> imageUrls = new ArrayList<>();

        while (matcher.find()) {
            imageTags.add(matcher.group(0));
            imageUrls.add(matcher.group(1));
        }

        // 并发转换
        List<CompletableFuture<String>> futures = imageUrls.stream()
                .map(url -> CompletableFuture.supplyAsync(() -> {
                    try {
                        return imageUrlToBase64(url);
                    } catch (Exception e) {
                        return url;
                    }
                }))
                .collect(Collectors.toList());

        // 等待所有任务完成
        List<String> base64Results = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        // 替换原始HTML中的图片链接
        for (int i = 0; i < imageTags.size(); i++) {
            String originalTag = imageTags.get(i);
            String originalUrl = imageUrls.get(i);
            String result = base64Results.get(i);
            html = html.replace(originalTag, originalTag.replace(originalUrl, result));
        }

        return html;
    }

    /**
     * 将图片URL转换为Base64编码
     *
     * @param imageUrl 图片URL
     * @return Base64编码的字符串
     */
    public static String imageUrlToBase64(String imageUrl) {
        try (HttpResponse response = HttpRequest.get(imageUrl).execute()) {
            byte[] bytes = IoUtil.readBytes(response.bodyStream());
            String contentType = response.header(Header.CONTENT_TYPE) != null
                    ? response.header(Header.CONTENT_TYPE)
                    : "image/png";
            if (!contentType.startsWith("image/")) {
                throw new IllegalArgumentException("URL 不是图片类型: " + contentType);
            }
            return "data:" + contentType + ";base64," + Base64Encoder.encode(bytes);
        } catch (Exception e) {
            throw new RuntimeException("图片转Base64失败: " + imageUrl, e);
        }
    }

    /**
     * 清理模板中无数据的区块或占位符
     *
     * @param html  HTML模板字符串
     * @param model 数据源
     * @return 清理后的HTML字符串
     */
    public static String cleanByMissingDataSource(String html, Map<String, Object> model) {
        if (html == null) {
            return "";
        }

        // 清除整行<tr>中包含缺失数据源的
        Matcher trMatcher = TR_PATTERN.matcher(html);
        StringBuffer buffer = new StringBuffer();

        while (trMatcher.find()) {
            String row = trMatcher.group();
            Set<String> dataSources = extractPrefixes(row);

            boolean keepRow = dataSources.stream().allMatch(key -> existsAndNotEmpty(key, model));

            if (keepRow) {
                trMatcher.appendReplacement(buffer, Matcher.quoteReplacement(row));
            } else {
                trMatcher.appendReplacement(buffer, "");
            }
        }
        trMatcher.appendTail(buffer);
        html = buffer.toString();

        // 清除单变量占位符
        Matcher varMatcher = VAR_PATTERN.matcher(html);
        StringBuffer result = new StringBuffer();

        while (varMatcher.find()) {
            String prefix = varMatcher.group(1);
            if (!existsAndNotEmpty(prefix, model)) {
                varMatcher.appendReplacement(result, "");
            } else {
                varMatcher.appendReplacement(result, Matcher.quoteReplacement(varMatcher.group()));
            }
        }
        varMatcher.appendTail(result);
        return result.toString();
    }

    /**
     * 检查模型中是否存在指定键，并且对应的值不为空
     *
     * @param key   键名
     * @param model 模型数据
     * @return 如果键存在且对应的值不为空，则返回true；否则返回false
     */
    private static boolean existsAndNotEmpty(String key, Map<String, Object> model) {
        if (!model.containsKey(key)) {
            return false;
        }
        Object val = model.get(key);
        if (val instanceof Collection) {
            return !((Collection<?>) val).isEmpty();
        }
        if (val instanceof Map) {
            return !((Map<?, ?>) val).isEmpty();
        }
        return val != null;
    }

    /**
     * 从HTML片段中提取所有的前缀
     *
     * @param htmlPart HTML片段
     * @return 包含所有前缀的集合
     */
    public static Set<String> extractPrefixes(String htmlPart) {
        Set<String> prefixes = new HashSet<>();
        Matcher matcher = VAR_PATTERN.matcher(htmlPart);
        while (matcher.find()) {
            prefixes.add(matcher.group(1));
        }
        return prefixes;
    }

    /**
     * 渲染前保护缺失占位：没有值的保持原样
     *
     * @param html
     * @param model
     * @return
     */
    public static String protectMissingPlaceholders(String html, Map<String, Object> model) {
        if (html == null || html.isEmpty()) {
            return html;
        }

        // 临时替换掉自动生成的循环体，避免里面的 __it_xxx 被误判
        List<String> loops = new ArrayList<>();
        String placeholderFmt = "\u0000LOOP_%d\u0000";
        Pattern loopPat = Pattern.compile("<#list[\\s\\S]*?</#list>");
        Matcher lm = loopPat.matcher(html);
        StringBuffer pre = new StringBuffer();
        int idx = 0;
        while (lm.find()) {
            loops.add(lm.group());
            lm.appendReplacement(pre, String.format(placeholderFmt, idx++).replace("$", "\\$"));
        }
        lm.appendTail(pre);
        String working = pre.toString();

        // 普通占位处理
        Pattern exprPat = Pattern.compile("\\$\\{([^}]+)}");
        Matcher m = exprPat.matcher(working);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String full = m.group(0);
            String expr = m.group(1).trim();

            String path = extractPath(expr);
            boolean exists = existsAndHasContent(model, path);

            if (!exists) {
                String protectedExpr = "<#noparse>" + full + "</#noparse>";
                m.appendReplacement(sb, Matcher.quoteReplacement(protectedExpr));
            } else {
                m.appendReplacement(sb, Matcher.quoteReplacement(full));
            }
        }
        m.appendTail(sb);
        working = sb.toString();

        // 还原循环体
        for (int i = 0; i < loops.size(); i++) {
            working = working.replace(String.format(placeholderFmt, i), loops.get(i));
        }
        return working;
    }

    /**
     * 提取表达式路径
     *
     * @param expr
     * @return
     */
    private static String extractPath(String expr) {
        int cut = Integer.MAX_VALUE;
        int p = expr.indexOf('!');
        if (p >= 0) {
            cut = Math.min(cut, p);
        }
        p = expr.indexOf('?');
        if (p >= 0) {
            cut = Math.min(cut, p);
        }
        String s = (cut == Integer.MAX_VALUE) ? expr : expr.substring(0, cut);

        s = s.trim();
        while (s.startsWith("(") && s.endsWith(")") && balanced(s)) {
            s = s.substring(1, s.length() - 1).trim();
        }
        return s;
    }

    /**
     * 判断括号是否平衡
     *
     * @param s
     * @return
     */
    private static boolean balanced(String s) {
        int c = 0;
        for (char ch : s.toCharArray()) {
            if (ch == '(') {
                c++;
            } else if (ch == ')') {
                c--;
            }
            if (c < 0) {
                return false;
            }
        }
        return c == 0;
    }

    /**
     * 判断路径在 model 中是否存在且有值
     *
     * @param root
     * @param path
     * @return
     */
    private static boolean existsAndHasContent(Map<String, Object> root, String path) {
        if (path == null || path.isEmpty()) {
            return false;
        }

        String[] tokens = path.split("\\.");
        Object cur = root.get(tokens[0]);
        for (int i = 1; i < tokens.length; i++) {
            if (cur == null) {
                return false;
            }
            if (cur instanceof Map) {
                cur = ((Map<?, ?>) cur).get(tokens[i]);
            } else {
                return false;
            }
        }
        if (cur == null) {
            return false;
        }
        if (cur instanceof String) {
            return !((String) cur).isEmpty();
        }
        if (cur instanceof Collection) {
            return !((Collection<?>) cur).isEmpty();
        }
        if (cur instanceof Map) {
            return !((Map<?, ?>) cur).isEmpty();
        }
        return true;
    }

    /**
     * 按别名清理模板中无数据的区块或占位符
     * <p>
     * 需要保证模板里存在的别名和配置的数据源别名对的上，不然可能出现无法清除的情况
     *
     * @param content
     * @param aliases
     */
    public static String cleanByAliases(String content, List<String> aliases) {
        if (content == null || content.isEmpty() || aliases == null || aliases.isEmpty()) {
            return content;
        }
        Set<String> aliasSet = new HashSet<>(aliases);

        // 清除整行<tr>中包含缺失数据源的
        Matcher trMatcher = TR_PATTERN.matcher(content);
        StringBuffer buffer = new StringBuffer();

        while (trMatcher.find()) {
            String row = trMatcher.group();
            Set<String> dataSources = extractPrefixes(row);

            boolean shouldDelete = dataSources.stream().anyMatch(aliasSet::contains);

            if (shouldDelete) {
                trMatcher.appendReplacement(buffer, "");
            } else {
                trMatcher.appendReplacement(buffer, Matcher.quoteReplacement(row));
            }
        }

        trMatcher.appendTail(buffer);
        content = buffer.toString();

        // 清除单变量占位符
        Matcher varMatcher = VAR_PATTERN.matcher(content);
        StringBuffer result = new StringBuffer();

        while (varMatcher.find()) {
            String prefix = varMatcher.group(1);
            if (!aliasSet.contains(prefix)) {
                varMatcher.appendReplacement(result, "");
            } else {
                varMatcher.appendReplacement(result, Matcher.quoteReplacement(varMatcher.group()));
            }
        }
        varMatcher.appendTail(result);
        return result.toString();
    }
}
