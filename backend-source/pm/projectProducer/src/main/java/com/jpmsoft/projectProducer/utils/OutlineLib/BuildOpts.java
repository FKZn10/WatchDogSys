package com.jpmsoft.projectProducer.utils.OutlineLib;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * 链式构建管理规划子系统目录全部可选参数。
 * <p>
 * 使用示例：
 * <pre>{@code List<Menu> result = OutlineLibUtils.buildTreeOrList(
 *         rawList,
 *         BuildOpts.<Menu>builder()
 *                  .idGetter(Menu::getId)
 *                  .parentIdGetter(Menu::getParentId)
 *                  .sortGetter(Menu::getOrderNo)
 *                  .childrenSetter(Menu::setChildren)
 *                  .catalogSetter(Menu::setTitle)
 *                  .nameGetter(Menu::getName)
 *                  .withIndexPrefix(true)     // 前缀编号
 *                  .withScore(true)           // 后缀分数
 *                  .scoreGetter(Menu::getScore)
 *                  .sortRules("02")           // “第X章” 风格
 *                  .build()
 * );}
 * </pre>
 * <p>
 * 注意：
 * <ul>
 *   <li>必填项：{@code idGetter}、{@code parentIdGetter}；缺失会在 {@code build()} 时抛出 {@link IllegalStateException}。</li>
 *   <li>依赖关系：
 *       <ul>
 *         <li>{@code withIndexPrefix = true}，必须提供 {@code catalogSetter} 以及 {@code nameGetter}</li>
 *         <li>{@code treeMode = true}，必须提供 {@code childrenSetter}</li>
 *         <li>{@code withScore = true}，必须提供 {@code scoreGetter}</li>
 *         <li>{@code withRisk = true}，必须提供 {@code riskCountGetter}</li>
 *         <li>{@code withOutContent = true}或者{@code prettyPrint = true}，必须提供 {@code contentSetter}</li>
 *         <li>{@code prettyPrint = true}，必须提供 {@code contentGetter}</li>
 *         <li>{@code withLevel = true}，必须提供 {@code levelSetter}</li>
 *         <li>{@code withLevelBottom = true}，必须提供 {@code levelBottomSetter}</li>
 *       </ul>
 *   </li>
 *   <li>互斥项：{@code treeMode} 与 {@code forceListMode} 互斥，不能同时为true</li>
 * </ul>
 *
 * @param <T> 节点实体类型
 * @author tianhelin
 * @date 2025/6/25
 */
public class BuildOpts<T> {
    // 必填参数
    /**
     * 节点id get
     */
    Function<T, String> idGetter;
    /**
     * 节点父id get
     */
    Function<T, String> parentIdGetter;

    // 可选参数
    /**
     * 排序字段get
     */
    Function<T, Integer> sortGetter = null;
    /**
     * 子节点set（仅在treeMode设置为true时使用）
     */
    BiConsumer<T, List<T>> childrenSetter = (n, l) -> {
    };
    /**
     * 目录名称set（仅在withIndexPrefix设置为true时使用）
     */
    BiConsumer<T, String> catalogSetter = (n, s) -> {
    };
    /**
     * 节点名称获取器get（仅在withIndexPrefix设置为true时使用）
     */
    Function<T, String> nameGetter = Object::toString;
    /**
     * 节点分数获取器get（仅在withScore设置为true时使用）
     */
    Function<T, BigDecimal> scoreGetter = null;
    /**
     * 节点风险数量获取器get（仅在withRisk设置为true时使用）
     */
    Function<T, Integer> riskCountGetter = null;
    /**
     * 节点内容set（仅在withContent设置为true时使用）
     */
    BiConsumer<T, String> contentSetter = (n, s) -> {
    };
    /**
     * 用于prettyPrint时获取内容（仅在prettyPrint设置为true时使用）
     */
    Function<T, String> contentGetter = Object::toString;
    /**
     * 节点层级set（仅在withLevel设置为true时使用）
     */
    BiConsumer<T, Integer> levelSetter = (n, i) -> {
    };
    /**
     * 节点层级底部set（仅在withLevel设置为true时使用）
     * <p>
     * 用于标识是否是最底层节点，默认是false
     */
    BiConsumer<T, Boolean> levelBottomSetter = (n, b) -> {
    };
    /**
     * 序号格式规则，默认值为01
     */
    String sortRules = "01";
    /**
     * 是否构建树结构，true=返回树；false=返回扁平列表，默认是true
     * <p>
     * 设置为false时需要确保原数据都是有根节点的
     */
    boolean treeMode = true;
    /**
     * 是否在标题前拼接指定规则的序号，默认是true
     */
    boolean withIndexPrefix = true;
    /**
     * 是否在标题后拼接分数，默认是false
     */
    boolean withScore = false;
    /**
     * 是否在标题后拼接预警数量，默认是false
     */
    boolean withRisk = false;
    /**
     * 强制列表模式，true=忽略父子结构构建，强制返回扁平列表，默认是false，与treeMode冲突
     * <p>
     * 如果确定原数据中都是有根节点的，那么可以不设置为true而直接使用treeMode=false来构建列表结构
     * <p>
     * 如果需要在<b>没有根节点</b>的情况下构建列表结构，那么必须设置为true来强制返回列表结构
     */
    boolean forceListMode = false;
    /**
     * 是否返回无内容数据，默认是false
     * <p>
     * 用于生成一个单纯的目录树结构，将目录下的具体的内容去除
     */
    boolean withOutContent = false;
    /**
     * 是否返回节点层级，默认是false
     * <p>
     * 比如用于导出word时候的标识
     */
    boolean withLevel = false;
    /**
     * 节点层级底部标识，默认是false
     * <p>
     * 用于标识是否是最底层节点
     */
    boolean withLevelBottom = false;
    /**
     * 是否美化展示输出，默认是false
     */
    boolean prettyPrint = false;
    /**
     * 根节点的标识，默认是0
     */
    String rootFlag = "0";

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T> {
        private final BuildOpts<T> o = new BuildOpts<>();

        /**
         * 节点id get
         *
         * @param f
         * @return
         */
        public Builder<T> idGetter(Function<T, String> f) {
            o.idGetter = f;
            return this;
        }

        /**
         * 节点父id get
         *
         * @param f
         * @return
         */
        public Builder<T> parentIdGetter(Function<T, String> f) {
            o.parentIdGetter = f;
            return this;
        }

        /**
         * 排序字段get
         *
         * @param f
         * @return
         */
        public Builder<T> sortGetter(Function<T, Integer> f) {
            o.sortGetter = f;
            return this;
        }

        /**
         * 子节点set（仅在treeMode设置为true时使用）
         *
         * @param c
         * @return
         */
        public Builder<T> childrenSetter(BiConsumer<T, List<T>> c) {
            o.childrenSetter = c;
            return this;
        }

        /**
         * 目录名称set（仅在withIndexPrefix设置为true时使用）
         *
         * @param c
         * @return
         */
        public Builder<T> catalogSetter(BiConsumer<T, String> c) {
            o.catalogSetter = c;
            return this;
        }

        /**
         * 节点名称获取器get（仅在withIndexPrefix设置为true时使用）
         *
         * @param f
         * @return
         */
        public Builder<T> nameGetter(Function<T, String> f) {
            o.nameGetter = f;
            return this;
        }

        /**
         * 节点分数获取器get（仅在withScore设置为true时使用）
         *
         * @param f
         * @return
         */
        public Builder<T> scoreGetter(Function<T, BigDecimal> f) {
            o.scoreGetter = f;
            return this;
        }

        /**
         * 节点风险数量获取器get（仅在withRisk设置为true时使用）
         *
         * @param f
         * @return
         */
        public Builder<T> riskCountGetter(Function<T, Integer> f) {
            o.riskCountGetter = f;
            return this;
        }

        /**
         * 节点内容set（仅在withOutContent设置为true时使用）
         *
         * @param c
         * @return
         */
        public Builder<T> contentSetter(BiConsumer<T, String> c) {
            o.contentSetter = c;
            return this;
        }

        /**
         * 用于prettyPrint时获取内容（仅在prettyPrint设置为true时使用）
         *
         * @param f
         * @return
         */
        public Builder<T> contentGetter(Function<T, String> f) {
            o.contentGetter = f;
            return this;
        }

        /**
         * 节点层级set（仅在withLevel设置为true时使用）
         *
         * @param c
         * @return
         */
        public Builder<T> levelSetter(BiConsumer<T, Integer> c) {
            o.levelSetter = c;
            return this;
        }

        /**
         * 节点层级底部set（仅在withLevel设置为true时使用）
         * <p>
         * 用于标识是否是最底层节点，默认是false
         *
         * @param c
         * @return
         */
        public Builder<T> levelBottomSetter(BiConsumer<T, Boolean> c) {
            o.levelBottomSetter = c;
            return this;
        }

        /**
         * 序号格式规则，默认值为01
         *
         * @param r
         * @return
         */
        public Builder<T> sortRules(String r) {
            o.sortRules = r;
            return this;
        }

        /**
         * 是否构建树结构，true=返回树；false=返回扁平列表，默认是true
         * <p>
         * 设置为false时需要确保原数据都是有根节点的
         *
         * @param b
         * @return
         */
        public Builder<T> treeMode(boolean b) {
            o.treeMode = b;
            return this;
        }

        /**
         * 是否在标题前拼接指定规则的序号，默认是true
         *
         * @param b
         * @return
         */
        public Builder<T> withIndexPrefix(boolean b) {
            o.withIndexPrefix = b;
            return this;
        }

        /**
         * 是否在标题后拼接分数，默认是false
         *
         * @param b
         * @return
         */
        public Builder<T> withScore(boolean b) {
            o.withScore = b;
            return this;
        }

        /**
         * 是否在标题后拼接预警数量，默认是false
         *
         * @param b
         * @return
         */
        public Builder<T> withRisk(boolean b) {
            o.withRisk = b;
            return this;
        }

        /**
         * 强制列表模式，true=忽略父子结构构建，强制返回扁平列表，默认是false，与treeMode冲突
         * <p>
         * 如果确定原数据中都是有根节点的，那么可以不设置为true而直接使用treeMode=false来构建列表结构
         * <p>
         * 如果需要在<b>没有根节点</b>的情况下构建列表结构，那么必须设置为true来强制返回列表结构
         *
         * @param b
         * @return
         */
        public Builder<T> forceListMode(boolean b) {
            o.forceListMode = b;
            return this;
        }

        /**
         * 是否返回无内容数据，默认是false
         * <p>
         * 用于生成一个单纯的目录树结构，将目录下的具体的内容去除
         *
         * @param b
         * @return
         */
        public Builder<T> withOutContent(boolean b) {
            o.withOutContent = b;
            return this;
        }

        /**
         * 是否返回节点层级，默认是false
         * <p>
         * 比如用于导出word时候的标识
         *
         * @param b
         * @return
         */
        public Builder<T> withLevel(boolean b) {
            o.withLevel = b;
            return this;
        }

        /**
         * 是否返回节点层级底部标识，默认是false
         * <p>
         * 用于标识是否是最底层节点
         *
         * @param b
         * @return
         */
        public Builder<T> withLevelBottom(boolean b) {
            o.withLevelBottom = b;
            return this;
        }

        /**
         * 是否美化展示输出，默认是false
         *
         * @param b
         * @return
         */
        public Builder<T> prettyPrint(boolean b) {
            o.prettyPrint = b;
            return this;
        }

        /**
         * 根节点的标识，默认是0
         *
         * @param r
         * @return
         */
        public Builder<T> rootFlag(String r) {
            o.rootFlag = r;
            return this;
        }

        /**
         * 构建并校验
         *
         * @return
         */
        public BuildOpts<T> build() {
            // 必填项
            if (o.idGetter == null || o.parentIdGetter == null) {
                throw new IllegalStateException("idGetter和parentIdGetter必填");
            }
            // 依赖校验
            if (o.withIndexPrefix) {
                if (o.catalogSetter == null || o.nameGetter == null) {
                    throw new IllegalStateException("withIndexPrefix=true时必须设置catalogSetter和nameGetter");
                }
            }
            if (o.treeMode) {
                if (o.childrenSetter == null) {
                    throw new IllegalStateException("treeMode=true时必须设置childrenSetter");
                }
            }
            if (o.withScore) {
                if (o.scoreGetter == null) {
                    throw new IllegalStateException("withScore=true时必须设置scoreGetter");
                }
            }
            if (o.withRisk) {
                if (o.riskCountGetter == null) {
                    throw new IllegalStateException("withRisk=true时必须设置riskCountGetter");
                }
            }
            if (o.withOutContent || o.prettyPrint) {
                if (o.contentSetter == null) {
                    throw new IllegalStateException("withOutContent=true时必须设置contentSetter");
                }
            }
            if (o.prettyPrint) {
                if (o.contentGetter == null) {
                    throw new IllegalStateException("prettyPrint=true时必须设置contentGetter");
                }
            }
            if (o.withLevel) {
                if (o.levelSetter == null) {
                    throw new IllegalStateException("withLevel=true时必须设置levelSetter");
                }
            }
            if (o.withLevelBottom) {
                if (o.levelBottomSetter == null) {
                    throw new IllegalStateException("withLevelBottom=true时必须设置levelBottomSetter");
                }
            }
            // 互斥校验
            if (o.treeMode && o.forceListMode) {
                throw new IllegalStateException("treeMode=true与forceListMode=true互斥，请二选一");
            }
            return o;
        }
    }
}
