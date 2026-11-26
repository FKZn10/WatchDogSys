package com.jpmsoft.projectProducer.utils.OutlineLib;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.NumberChineseFormatter;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.pojo.dto.projectOutline.OutlineLibConfDTO;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineProjectStyleDtl;
import com.jpmsoft.projectProducer.utils.HtmlUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 管理规划子系统目录工具类
 *
 * @author tianhelin
 * @date 2025/6/20
 */
@Slf4j
public class OutlineLibUtils {
    private static final String[] CN_NUMS = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};

    /**
     * 节点对
     * 用于压栈、出栈
     *
     * @param <O> 节点类型
     */
    public static class NodePair<O> {
        public O node;
        public List<Integer> index;
        public String parentId;

        public NodePair(O node, List<Integer> index, String parentId) {
            this.node = node;
            this.index = index;
            this.parentId = parentId;
        }

        public NodePair(O node, List<Integer> index) {
            this.node = node;
            this.index = index;
        }

        public NodePair(O node, String parentId) {
            this.node = node;
            this.parentId = parentId;
        }
    }

    /**
     * 文本处理返回
     * 包含文本片段和相对索引
     */
    @Getter
    public static class TextSnippet {
        private final String snippet;
        private final int relativeIndex;

        public TextSnippet(String snippet, int relativeIndex) {
            this.snippet = snippet;
            this.relativeIndex = relativeIndex;
        }
    }

    /**
     * 根据给定的层级索引列表和指定的序号规则，生成节点的目录编号前缀字符串
     *
     * @param index     当前节点在树结构中的层级索引路径
     * @param sortRules 序号格式规则，字典值
     * @return 格式化后的目录编号字符串
     */
    public static String formatIndexPath(List<Integer> index, String sortRules) {
        if (index == null || index.isEmpty()) {
            return "";
        }

        switch (sortRules) {
            case "02":
                if (index.size() == 1) {
                    return "第" + NumberChineseFormatter.format(index.get(0), false) + "章";
                } else {
                    return index.stream().map(String::valueOf).collect(Collectors.joining("."));
                }
            case "01":
            default:
                return index.stream().map(String::valueOf).collect(Collectors.joining("."));
        }
    }

    /**
     * 数字转中文，支持0到99
     *
     * @param num 数字
     * @return 中文数字
     */
    public static String numberToChinese(int num) {
        if (num <= 10) {
            return CN_NUMS[num];
        }
        if (num < 20) {
            return "十" + CN_NUMS[num % 10];
        }
        if (num < 100) {
            int tens = num / 10;
            int ones = num % 10;
            return CN_NUMS[tens] + "十" + (ones > 0 ? CN_NUMS[ones] : "");
        }
        return String.valueOf(num);
    }

    /**
     * 构建树形结构或扁平列表结构
     *
     * @param flatList 原始数据，数据库查询的列表数据
     * @param opt      构建参数对象
     * @param <T>      节点类型
     * @return 构建好的树或扁平列表
     */
    public static <T> List<T> buildTreeOrList(List<T> flatList, BuildOpts<T> opt, Map<String, OutlineProjectStyleDtl> styleDtlMap) {
        if (flatList == null || flatList.isEmpty()) {
            return Collections.emptyList();
        }
        String rule = opt.sortRules == null ? "01" : opt.sortRules;

        // 构造parentId和children的映射关系
        Map<String, List<T>> childrenMap = new HashMap<>();
        for (T node : flatList) {
            childrenMap.computeIfAbsent(opt.parentIdGetter.apply(node), k -> new ArrayList<>()).add(node);
            if (opt.treeMode) {
                opt.childrenSetter.accept(node, new ArrayList<>());
            }
            if (opt.withOutContent) {
                opt.contentSetter.accept(node, null);
            }
            if (opt.prettyPrint && ValidateUtil.isNotNull(styleDtlMap)) {
                opt.contentSetter.accept(node, HtmlUtils.prettyHtmlPrint(opt.contentGetter.apply(node), styleDtlMap));
            } else if (opt.prettyPrint) {
                log.warn("预览样式未配置");
            }
        }

        // 每个子列表内部排序
        Comparator<T> order = opt.sortGetter == null ? (a, b) -> 0 : Comparator.comparing(opt.sortGetter, Comparator.nullsLast(Integer::compareTo));
        for (List<T> list : childrenMap.values()) {
            list.sort(order);
        }

        // 计算根节点
        LinkedHashSet<T> rootSet = new LinkedHashSet<>();
        rootSet.addAll(childrenMap.getOrDefault(opt.rootFlag, Collections.emptyList()));
        rootSet.addAll(childrenMap.getOrDefault(null, Collections.emptyList()));
        List<T> rootList = new ArrayList<>(rootSet);
        rootList.sort(order);

        // 逆序压栈保证正序遍历
        Deque<NodePair<T>> stack = new ArrayDeque<>();
        List<T> entry = opt.forceListMode ? flatList : rootList;
        entry.sort(order);

        for (int i = entry.size() - 1; i >= 0; i--) {
            stack.push(new NodePair<>(entry.get(i), Collections.singletonList(i + 1)));
        }

        List<T> flatResult = new ArrayList<>(flatList.size());

        while (!stack.isEmpty()) {
            NodePair<T> nodePair = stack.pop();
            T node = nodePair.node;
            List<Integer> index = nodePair.index;

            // 设置目录层级
            if (opt.withLevel) {
                opt.levelSetter.accept(node, index.size());
            }

            String title = opt.nameGetter.apply(node);
            // 按需处理标题的前缀
            if (opt.withIndexPrefix) {
                title = formatIndexPath(new ArrayList<>(index), rule) + " " + title;
            }
            // 按需处理标题的后缀
            if (opt.withScore) {
                BigDecimal score = opt.scoreGetter.apply(node);
                if (score != null) {
                    title += "（" + score + "）";
                }
            }
            if (opt.withRisk) {
                Integer riskCount = opt.riskCountGetter.apply(node);
                if (riskCount != null) {
                    title += "（" + riskCount + "）";
                }
            }
            opt.catalogSetter.accept(node, title);

            flatResult.add(node);

            // 处理子节点
            List<T> children = childrenMap.get(opt.idGetter.apply(node));
            boolean isLeaf = (children == null || children.isEmpty());
            // 设置是否是叶子节点
            if (opt.withLevelBottom) {
                opt.levelBottomSetter.accept(node, isLeaf);
            }
            if (!isLeaf) {
                if (opt.treeMode) {
                    opt.childrenSetter.accept(node, children);
                }
                // 逆序压栈，保持遍历顺序
                for (int i = children.size() - 1; i >= 0; i--) {
                    List<Integer> childIndex = new ArrayList<>(index);
                    childIndex.add(i + 1);
                    stack.push(new NodePair<>(children.get(i), childIndex));
                }
            }
        }

        return opt.treeMode ? rootList : flatResult;
    }

    /**
     * 构建树形结构或扁平列表结构
     *
     * @param flatList 原始数据，数据库查询的列表数据
     * @param opt      构建参数对象
     * @param <T>      节点类型
     * @return 构建好的树或扁平列表
     */
    public static <T> List<T> buildTreeOrList(List<T> flatList, BuildOpts<T> opt) {
        return buildTreeOrList(flatList, opt, null);
    }

    /**
     * 将扁平列表进行与构建树结构时一样的排序
     *
     * @param flatList 扁平列表
     * @param opt      构建参数对象
     * @param <T>      泛型
     * @return 排序后的扁平列表
     */
    public static <T> List<T> flatSortByTree(List<T> flatList, BuildOpts<T> opt) {
        if (flatList == null || flatList.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, List<T>> childrenMap = new LinkedHashMap<>();
        for (T n : flatList) {
            childrenMap.computeIfAbsent(opt.parentIdGetter.apply(n), k -> new ArrayList<>()).add(n);
        }

        // 每个子列表内部排序
        Comparator<T> comparator = opt.sortGetter == null ? (a, b) -> 0 : Comparator.comparing(opt.sortGetter, Comparator.nullsLast(Integer::compareTo));
        for (List<T> group : childrenMap.values()) {
            group.sort(comparator);
        }

        // 计算根节点
        LinkedHashSet<T> rootSet = new LinkedHashSet<>();
        rootSet.addAll(childrenMap.getOrDefault(opt.rootFlag, Collections.emptyList()));
        rootSet.addAll(childrenMap.getOrDefault(null, Collections.emptyList()));
        List<T> rootList = new ArrayList<>(rootSet);
        rootList.sort(comparator);

        Deque<T> stack = new ArrayDeque<>();
        rootList.sort(comparator);
        // 从后往前压栈，保证正序遍历
        for (int i = rootList.size() - 1; i >= 0; i--) {
            stack.push(rootList.get(i));
        }

        List<T> flatResult = new ArrayList<>(flatList.size());
        while (!stack.isEmpty()) {
            T node = stack.pop();
            flatResult.add(node);
            List<T> children = childrenMap.get(opt.idGetter.apply(node));
            if (children != null && !children.isEmpty()) {
                // 逆序压栈，保证弹出时顺序正确
                for (int i = children.size() - 1; i >= 0; i--) {
                    stack.push(children.get(i));
                }
            }
        }

        return flatResult;
    }

    /**
     * 节点交换顺序
     *
     * @param nodeList    同层节点列表
     * @param nodeA       节点A
     * @param nodeB       节点B
     * @param getIdFunc   获取id的方法
     * @param setSortFunc 设置排序的方法
     * @param <T>         实体类泛型
     */
    public static <T> void moveNodeAndResort(
            List<T> nodeList,
            T nodeA,
            T nodeB,
            Function<T, String> getIdFunc,
            BiConsumer<T, Integer> setSortFunc
    ) {
        if (nodeList == null || nodeA == null || nodeB == null) {
            return;
        }

        String idA = getIdFunc.apply(nodeA);
        String idB = getIdFunc.apply(nodeB);

        int indexA = -1, indexB = -1;
        for (int i = 0; i < nodeList.size(); i++) {
            String id = getIdFunc.apply(nodeList.get(i));
            if (id.equals(idA)) {
                indexA = i;
            }
            if (id.equals(idB)) {
                indexB = i;
            }
        }

        if (indexA == -1 || indexB == -1 || indexA == indexB) {
            return;
        }

        // 交换位置
        Collections.swap(nodeList, indexA, indexB);

        // 重新排序
        for (int i = 0; i < nodeList.size(); i++) {
            setSortFunc.accept(nodeList.get(i), i + 1);
        }
    }

    /**
     * 文本缩减处理
     *
     * @param content        原始内容
     * @param keyword        关键词
     * @param completeLength 内容长度限制
     * @return 处理后的文本片段和相对索引
     */
    public static TextSnippet textReductionHandler(String content, String keyword, int completeLength, int index) {
        if (ValidateUtil.isEmpty(content) || ValidateUtil.isEmpty(keyword) || index < 0) {
            return new TextSnippet(content, -1);
        }

        int start = index - completeLength;
        int end = index + keyword.length() + completeLength;

        boolean prefixEllipsis = false;
        boolean suffixEllipsis = false;

        if (start < 0) {
            completeLength += Math.abs(start);
            start = 0;
            end = Math.min(content.length(), index + keyword.length() + completeLength);
        }

        if (end > content.length()) {
            int overflow = end - content.length();
            start = Math.max(0, start - overflow);
            end = content.length();
        }

        if (start > 0) {
            prefixEllipsis = true;
        }
        if (end < content.length()) {
            suffixEllipsis = true;
        }

        String snippet = content.substring(start, end);
        int relativeIndex = index - start;
        if (prefixEllipsis) {
            snippet = "..." + snippet;
            relativeIndex += 3;
        }
        if (suffixEllipsis) {
            snippet = snippet + "...";
        }
        return new TextSnippet(snippet, relativeIndex);
    }

    /**
     * 高亮
     *
     * @param content        原始内容
     * @param keyword        关键词
     * @param highlightColor 高亮颜色
     * @return 高亮处理后的内容
     */
    public static String getHighlightedContent(String content, String keyword, String highlightColor, int index) {
        if (ValidateUtil.isEmpty(content) || ValidateUtil.isEmpty(keyword) || index < 0 || index >= content.length()) {
            return content;
        }
        String before = content.substring(0, index);
        String match = content.substring(index, index + keyword.length());
        String after = content.substring(index + keyword.length());
        return before + "<span style=\"color:" + highlightColor + ";\">" + match + "</span>" + after;
    }

    /**
     * 匹配项目名
     *
     * @param projectNames 项目名称列表
     * @param contents     内容列表
     * @return 匹配到的项目名称列表
     */
    public static List<String> matchProjectName(List<String> projectNames, List<String> contents) {
        if (ValidateUtil.isEmpty(projectNames) || ValidateUtil.isEmpty(contents)) {
            return Collections.emptyList();
        }
        // 去除null和空字符串
        projectNames = projectNames.stream().filter(x -> x != null && !x.trim().isEmpty()).distinct().collect(Collectors.toList());
        List<String> finalContents = contents.stream().filter(x -> x != null && !x.trim().isEmpty()).distinct().collect(Collectors.toList());
        // 返回在content中所有匹配上的项目名称
        return projectNames.stream()
                .filter(projectName -> finalContents.stream().anyMatch(content -> content.contains(projectName)))
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 关键词处理
     *
     * @param keywords 关键词字符串“,"分隔
     * @return 关键词列表
     */
    public static List<String> keywordsHandler(String keywords) {
        if (ValidateUtil.isEmpty(keywords)) {
            return Collections.emptyList();
        }
        return Optional.of(keywords)
                .map(val -> Arrays.asList(val.split(",")))
                .orElse(Collections.emptyList());
    }

    /**
     * 筛选出包含关键词的项
     *
     * @param flatList      原始列表
     * @param keywords      关键词列表
     * @param nameGetter    获取名称的方法
     * @param contentGetter 获取内容的方法
     * @param <T>           泛型
     * @return 包含关键词的项列表
     */
    public static <T> List<T> filterByKeywords(
            List<T> flatList,
            List<String> keywords,
            Function<T, String> nameGetter,
            Function<T, String> contentGetter
    ) {
        return flatList.stream()
                .filter(item -> {
                    String name = Optional.ofNullable(nameGetter.apply(item)).orElse("");
                    String content = Optional.ofNullable(contentGetter.apply(item)).orElse("");
                    return keywords.stream().anyMatch(keyword -> name.contains(keyword) || content.contains(keyword));
                })
                .collect(Collectors.toList());
    }

    /**
     * 内容高亮处理
     *
     * @param items               列表项
     * @param keywords            关键词列表
     * @param conf                配置项
     * @param catalogNameGetter   目录名称获取方法
     * @param contentGetter       内容获取方法
     * @param catalogNameSetter   目录名称设置方法
     * @param contentSetter       内容设置方法
     * @param contentExtendSetter 内容扩展设置方法
     * @param <T>                 泛型
     */
    public static <T> List<T> highlightMatchedContent(
            List<T> items,
            List<String> keywords,
            OutlineLibConfDTO conf,
            Function<T, String> catalogNameGetter,
            Function<T, String> contentGetter,
            BiConsumer<T, String> catalogNameSetter,
            BiConsumer<T, String> contentSetter,
            BiConsumer<T, String> contentExtendSetter,
            BiConsumer<T, Integer> indexSetter,
            Supplier<T> instanceCreator
    ) {
        List<T> resultList = new ArrayList<>();
        Map<T, String> parsedContentCache = new HashMap<>();

        for (T item : items) {
            String catalog = Optional.ofNullable(catalogNameGetter.apply(item)).orElse("");
            String content = parsedContentCache.computeIfAbsent(item, i -> {
                String raw = Optional.ofNullable(contentGetter.apply(i)).orElse("");
                return HtmlUtils.parseToText(raw);
            });

            for (String keyword : keywords) {
                int idx = content.indexOf(keyword);
                while (idx != -1) {
                    T copy = instanceCreator.get();
                    BeanUtil.copyProperties(item, copy);
                    TextSnippet reduced = textReductionHandler(content, keyword, conf.getCompleteLength(), idx);
                    contentSetter.accept(copy, getHighlightedContent(reduced.getSnippet(), keyword, conf.getHighLightColor(), reduced.getRelativeIndex()));
                    TextSnippet reducedExt = textReductionHandler(content, keyword, conf.getCompleteLengthExtend(), idx);
                    contentExtendSetter.accept(copy, reducedExt.getSnippet());
                    catalogNameSetter.accept(copy, catalog);
                    indexSetter.accept(copy, idx);
                    resultList.add(copy);
                    idx = content.indexOf(keyword, idx + 1);
                }

                int catIdx = catalog.indexOf(keyword);
                while (catIdx != -1) {
                    T copy = instanceCreator.get();
                    BeanUtil.copyProperties(item, copy);
                    contentSetter.accept(copy, getHighlightedContent(catalog, keyword, conf.getHighLightColor(), catIdx));
                    contentExtendSetter.accept(copy, catalog);
                    catalogNameSetter.accept(copy, catalog);
                    indexSetter.accept(copy, catIdx);
                    resultList.add(copy);
                    catIdx = catalog.indexOf(keyword, catIdx + 1);
                }
            }
        }

        return resultList;
    }

    /**
     * 替换关键字
     *
     * @param items         列表项
     * @param searchValues  搜索值列表
     * @param replaceValues 替换值列表
     * @param nameGetter    获取名称的方法
     * @param contentGetter 获取内容的方法
     * @param nameSetter    设置名称的方法
     * @param contentSetter 设置内容的方法
     * @param <T>           泛型
     */
    public static <T> void replaceKeywords(
            List<T> items,
            List<String> searchValues,
            List<String> replaceValues,
            Function<T, String> nameGetter,
            Function<T, String> contentGetter,
            BiConsumer<T, String> nameSetter,
            BiConsumer<T, String> contentSetter
    ) {
        boolean oneToOne = searchValues.size() == replaceValues.size();
        String replacement = replaceValues.isEmpty() ? "" : replaceValues.get(0);

        for (T item : items) {
            String name = Optional.ofNullable(nameGetter.apply(item)).orElse("");
            String content = Optional.ofNullable(contentGetter.apply(item)).orElse("");

            if (oneToOne) {
                for (int i = 0; i < searchValues.size(); i++) {
                    String replaceValue = replaceValues.get(i);
                    if (replaceValue != null) {
                        name = name.replace(searchValues.get(i), replaceValue);
                        content = content.replace(searchValues.get(i), replaceValue);
                    }
                }
            } else {
                for (String keyword : searchValues) {
                    name = name.replace(keyword, replacement);
                    content = content.replace(keyword, replacement);
                }
            }
            nameSetter.accept(item, name);
            contentSetter.accept(item, content);
        }
    }
}
