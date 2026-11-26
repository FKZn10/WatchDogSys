package com.jpmsoft.fileUpload.utils;

import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.pojo.dto.ConBoqDtlXmlTreeDto;
import org.dom4j.Element;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 造价 合同工程量清单XML解析
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/19 17:00
 */
public class BoqDtlXml {

    public static List<ConBoqDtlXmlTreeDto> analysisBoqDtlXml(File file) throws Exception {
        //3.获取根节点
        Element rootElement = Dom4jXml.getRootElement(file);
        List<ConBoqDtlXmlTreeDto> xmlTrees = new ArrayList<>();
        analysisDxgcxx(rootElement, xmlTrees);
        return xmlTrees;
    }

    /**
     * 单项工程层
     */
    private static void analysisDxgcxx(Element rootElement, List<ConBoqDtlXmlTreeDto> xmlTrees) {
        List<Element> Dxgcxxs = rootElement.elements("Dxgcxx");
        if (CollectionUtils.isEmpty(Dxgcxxs)) throw new JPMException(FileMsg.fileFormatError);
        int i = 0;
        for (Element dxgcxx : Dxgcxxs) {
            ConBoqDtlXmlTreeDto xmlTree = new ConBoqDtlXmlTreeDto();
            //单项工程信息
            xmlTree.setBoqdType("Dxgcxx");
            xmlTree.setBoqdName(dxgcxx.attributeValue("Dxgcmc"));
            xmlTree.setBoqdXh(dxgcxx.attributeValue("Dxgcbh"));
            xmlTree.setBoqdGf(new BigDecimal(dxgcxx.attributeValue("Gf")));
            xmlTree.setBoqdAqsg(new BigDecimal(dxgcxx.attributeValue("Aqwmf")));
            xmlTree.setBoqdPrice(new BigDecimal(dxgcxx.attributeValue("Je")));
            xmlTree.setBoqdId(GenerateUUID.getUUID());
            xmlTree.setBoqdProvisionalEstimate(new BigDecimal(dxgcxx.attributeValue("Zgj")));
            List<ConBoqDtlXmlTreeDto> DwgcxxTree = new ArrayList<>();
            analysisDwgcxx(dxgcxx, DwgcxxTree, xmlTree.getBoqdId());
            xmlTree.setChildNodes(DwgcxxTree);
            xmlTree.setBoqdSort(i += 1);
            xmlTrees.add(xmlTree);
        }
    }

    /**
     * 单位工程层
     *
     * @param dxgcxx
     * @param DwgcxxTree
     * @param parentId
     */
    private static void analysisDwgcxx(Element dxgcxx, List<ConBoqDtlXmlTreeDto> DwgcxxTree, String parentId) {
        List<Element> Dwgcxxs = dxgcxx.elements("Dwgcxx");
        if (CollectionUtils.isEmpty(Dwgcxxs)) throw new JPMException(FileMsg.fileFormatError);
        int i = 0;
        for (Element dwgcxx : Dwgcxxs) {
            ConBoqDtlXmlTreeDto xmlTree = new ConBoqDtlXmlTreeDto();
            //单项工程信息
            xmlTree.setBoqdParentId(parentId);
            xmlTree.setBoqdType("Dwgcxx");
            xmlTree.setBoqdName(dwgcxx.attributeValue("Dwgcmc"));
            xmlTree.setBoqdXh(dwgcxx.attributeValue("Dwgcbh"));
            xmlTree.setBoqdId(GenerateUUID.getUUID());
            xmlTree.setChildNodes(analysisDwgcxxChild(dwgcxx, xmlTree.getBoqdId()));
            xmlTree.setBoqdSort(i += 1);
            DwgcxxTree.add(xmlTree);
        }
    }

    /**
     * 单项工程下级层
     *
     * @param dwgcxx
     * @param parentId
     * @return
     */
    private static List<ConBoqDtlXmlTreeDto> analysisDwgcxxChild(Element dwgcxx, String parentId) {
        List<ConBoqDtlXmlTreeDto> datas = new ArrayList<>();
        int i = 0;
        //单位工程费汇总表
        add(datas, analysisFywj(dwgcxx, parentId, i += 1));
        //规费 税金
        add(datas, analysisGfsj(dwgcxx, parentId, i += 1));
        //分部分项工程量清单
        add(datas, analysisQdxm(dwgcxx, parentId, i += 1));
        //措施项目
        add(datas, analysisCsxm(dwgcxx, parentId, i += 1));
        //其他项目
        add(datas, analysisQtxm(dwgcxx, parentId, i += 1));
        //暂列金额
        add(datas, analysisZlje(dwgcxx, parentId, i += 1));
        //材料暂估价格
        add(datas, analysisClzg(dwgcxx, parentId, i += 1));
        //专业工程暂估价
        add(datas, analysisZygczg(dwgcxx, parentId, i += 1));
        //计日工
        add(datas, analysisJrg(dwgcxx, parentId, i += 1));
        //总承包服务费
        add(datas, analysisZcbfwf(dwgcxx, parentId, i += 1));
        //总价项目进度款支付分解表
        add(datas, analysisZjxmjdkzffj(dwgcxx, parentId, i += 1));
        //发包人提供材料和工程设备一览
        add(datas, analysisFbrgycl(dwgcxx, parentId, i += 1));
        //承包人提供主要材料和工程设备一览
        add(datas, analysisCbrgycl(dwgcxx, parentId, i += 1));
        //人材机汇总
        add(datas, analysisRcjhz(dwgcxx, parentId, i += 1));
        return datas;
    }

    /**
     * 人材机汇总
     *
     * @param dwgcxx
     * @param parentId
     * @param sort
     * @return
     */
    private static ConBoqDtlXmlTreeDto analysisRcjhz(Element dwgcxx, String parentId, int sort) {
        Element Rcjhz = dwgcxx.element("Rcjhz");
        if (Rcjhz == null) return null;
        ConBoqDtlXmlTreeDto xmlTree = newObj("人材机汇总", parentId, sort, "Rcjhz");
        List<ConBoqDtlXmlTreeDto> datas = new ArrayList<>();
        List<Element> RcjhzMxs = Rcjhz.elements("RcjhzMx");
        if (!CollectionUtils.isEmpty(RcjhzMxs)) {
            int i = 0;
            for (Element element : RcjhzMxs) {
                ConBoqDtlXmlTreeDto mx = newObj(element.attributeValue("Mc"), parentId, i += 1, "RcjhzMx");
                mx.setBoqdRcjId(element.attributeValue("RcjId"));
                mx.setBoqdCode(element.attributeValue("RcjBm"));
                mx.setBoqdFeature(element.attributeValue("Ggxh"));
                mx.setBoqdUnit(element.attributeValue("Dw"));
                mx.setBoqdAmount(Float.valueOf(element.attributeValue("Sl")));
                mx.setBoqdPrice(new BigDecimal(element.attributeValue("Dj")));
                mx.setBoqdTotalPrice(new BigDecimal(element.attributeValue("Hj")));
                datas.add(mx);
            }
            xmlTree.setChildNodes(datas);
        }
        return xmlTree;
    }

    /**
     * 承包人提供主要材料和工程设备一览
     *
     * @param dwgcxx
     * @param parentId
     * @param sort
     * @return
     */
    private static ConBoqDtlXmlTreeDto analysisCbrgycl(Element dwgcxx, String parentId, int sort) {
        Element Cbrgycl = dwgcxx.element("Cbrgycl");
        if (Cbrgycl == null) return null;
        ConBoqDtlXmlTreeDto xmlTree = newObj("承包人提供主要材料和工程设备一览", parentId, sort, "Cbrgycl");
        List<ConBoqDtlXmlTreeDto> datas = new ArrayList<>();
        List<Element> CbrgyclMxs = Cbrgycl.elements("CbrgyclMx");
        if (!CollectionUtils.isEmpty(CbrgyclMxs)) {
            int i = 0;
            for (Element element : CbrgyclMxs) {
                ConBoqDtlXmlTreeDto mx = newObj(element.attributeValue("Mc"), parentId, i += 1, "CbrgyclMx");
                mx.setBoqdXh(element.attributeValue("Xh"));
                mx.setBoqdRcjId(element.attributeValue("RcjId"));
                mx.setBoqdCode(element.attributeValue("Clbh"));
                mx.setBoqdFeature(element.attributeValue("Ggxh"));
                mx.setBoqdUnit(element.attributeValue("Dw"));
                mx.setBoqdAmount(Float.valueOf(element.attributeValue("Sl")));
                mx.setBoqdFxxs(money(element,"Fxxs"));
                mx.setBoqdJzDj(money(element,"JzDj"));
                mx.setBoqdTbDj(money(element,"TbDj"));
                mx.setBoqdRemark(element.attributeValue("Bz"));
                datas.add(mx);
            }
            xmlTree.setChildNodes(datas);
        }
        return xmlTree;
    }


    /**
     * 发包人提供材料和工程设备一览
     *
     * @param dwgcxx
     * @param parentId
     * @param sort
     * @return
     */
    private static ConBoqDtlXmlTreeDto analysisFbrgycl(Element dwgcxx, String parentId, int sort) {
        Element Fbrgycl = dwgcxx.element("Fbrgycl");
        if (Fbrgycl == null) return null;
        ConBoqDtlXmlTreeDto xmlTree = newObj("发包人提供材料和工程设备一览", parentId, sort, "Fbrgycl");
        List<ConBoqDtlXmlTreeDto> datas = new ArrayList<>();
        List<Element> FbrgyclMxs = Fbrgycl.elements("FbrgyclMx");
        if (!CollectionUtils.isEmpty(FbrgyclMxs)) {
            int i = 0;
            for (Element element : FbrgyclMxs) {
                ConBoqDtlXmlTreeDto mx = newObj(element.attributeValue("Mc"), parentId, i += 1, "FbrgyclMx");
                mx.setBoqdXh(element.attributeValue("Xh"));
                mx.setBoqdRcjId(element.attributeValue("RcjId"));
                mx.setBoqdCode(element.attributeValue("Clbh"));
                mx.setBoqdFeature(element.attributeValue("Ggxh"));
                mx.setBoqdUnit(element.attributeValue("Dw"));
                mx.setBoqdAmount(Float.valueOf(element.attributeValue("Sl")));
                mx.setBoqdPrice(new BigDecimal(element.attributeValue("Dj")));
                mx.setBoqdTotalPrice(new BigDecimal(element.attributeValue("Hj")));
                mx.setBoqdRemark(element.attributeValue("Bz"));
                datas.add(mx);
            }
            xmlTree.setChildNodes(datas);
        }
        return xmlTree;
    }

    /**
     * 总价项目进度款支付分解表
     *
     * @param dwgcxx
     * @param parentId
     * @param sort
     * @return
     */
    private static ConBoqDtlXmlTreeDto analysisZjxmjdkzffj(Element dwgcxx, String parentId, int sort) {
        Element Zjxmjdkzffj = dwgcxx.element("Zjxmjdkzffj");
        if (Zjxmjdkzffj == null) return null;
        ConBoqDtlXmlTreeDto xmlTree = newObj("总价项目进度款支付分解", parentId, sort, "Zjxmjdkzffj");
        List<ConBoqDtlXmlTreeDto> datas = new ArrayList<>();
        List<Element> ZjxmjdkzffjMxs = Zjxmjdkzffj.elements("ZjxmjdkzffjMx");
        if (!CollectionUtils.isEmpty(ZjxmjdkzffjMxs)) {
            int i = 0;
            for (Element element : ZjxmjdkzffjMxs) {
                ConBoqDtlXmlTreeDto mx = newObj(element.attributeValue("Mc"), parentId, i += 1, "ZjxmjdkzffjMx");
                mx.setBoqdXh(element.attributeValue("Xh"));
                mx.setBoqdTotalPrice(new BigDecimal(element.attributeValue("Zjje")));
                datas.add(mx);
            }
            xmlTree.setChildNodes(datas);
        }
        return xmlTree;
    }

    /**
     * 总承包服务费
     *
     * @param dwgcxx
     * @param parentId
     * @param sort
     * @return
     */
    private static ConBoqDtlXmlTreeDto analysisZcbfwf(Element dwgcxx, String parentId, int sort) {
        Element Zcbfwf = dwgcxx.element("Zcbfwf");
        if (Zcbfwf == null) return null;
        ConBoqDtlXmlTreeDto xmlTree = newObj("总承包服务费", parentId, sort, "Zcbfwf");
        List<ConBoqDtlXmlTreeDto> datas = new ArrayList<>();
        List<Element> ZcbfwfMxs = Zcbfwf.elements("ZcbfwfMx");
        if (!CollectionUtils.isEmpty(ZcbfwfMxs)) {
            int i = 0;
            for (Element element : ZcbfwfMxs) {
                ConBoqDtlXmlTreeDto mx = newObj(element.attributeValue("Mc"), parentId, i += 1, "ZcbfwfMx");
                mx.setBoqdXh(element.attributeValue("Xh"));
                mx.setBoqdContent(element.attributeValue("Fwnr"));
                mx.setBoqdJsjc(element.attributeValue("Jsjc"));
                mx.setBoqdPrice(new BigDecimal(element.attributeValue("Je")));
                mx.setBoqdTotalPrice(new BigDecimal(element.attributeValue("Xmjz")));
                mx.setBoqdRate(Float.valueOf(element.attributeValue("Fl")));
                datas.add(mx);
            }
            xmlTree.setChildNodes(datas);
        }
        return xmlTree;
    }

    /**
     * 计日工
     *
     * @param dwgcxx
     * @param parentId
     * @param sort
     * @return
     */
    private static ConBoqDtlXmlTreeDto analysisJrg(Element dwgcxx, String parentId, int sort) {
        Element Jrg = dwgcxx.element("Jrg");
        if (Jrg == null) return null;
        ConBoqDtlXmlTreeDto xmlTree = newObj("计日工", parentId, sort, "Jrg");
        List<ConBoqDtlXmlTreeDto> datas = new ArrayList<>();
        //region 计日工标题
        List<Element> JrgBts = Jrg.elements("JrgBt");
        if (!CollectionUtils.isEmpty(JrgBts)) {
            int i = 0;
            for (Element element : JrgBts) {
                ConBoqDtlXmlTreeDto mx = newObj(element.attributeValue("Mc"), parentId, i += 1, "JrgBt");
                mx.setBoqdXh(element.attributeValue("Xh"));
                mx.setBoqdPrice(new BigDecimal(element.attributeValue("Je")));
                datas.add(mx);
            }
        }
        //endregion

        //region 计日工明细
        List<Element> JrgMxs = Jrg.elements("JrgMx");
        if (!CollectionUtils.isEmpty(JrgMxs)) {
            int i = 0;
            for (Element element : JrgMxs) {
                ConBoqDtlXmlTreeDto mx = newObj(element.attributeValue("Mc"), parentId, i += 1, "JrgMx");
                mx.setBoqdXh(element.attributeValue("Xh"));
                mx.setBoqdUnit(element.attributeValue("Dw"));
                mx.setBoqdAmount(Float.valueOf(element.attributeValue("Zdsl")));
                mx.setBoqdPrice(new BigDecimal(element.attributeValue("Zhdj")));
                mx.setBoqdTotalPrice(new BigDecimal(element.attributeValue("Zhhj")));
                datas.add(mx);
            }
        }
        //endregion
        xmlTree.setChildNodes(datas);
        return xmlTree;
    }

    /**
     * 专业工程暂估价
     *
     * @param dwgcxx
     * @param parentId
     * @param sort
     * @return
     */
    private static ConBoqDtlXmlTreeDto analysisZygczg(Element dwgcxx, String parentId, int sort) {
        Element Zygczg = dwgcxx.element("Zygczg");
        if (Zygczg == null) return null;
        ConBoqDtlXmlTreeDto xmlTree = newObj("专业工程暂估价", parentId, sort, "Zygczg");
        List<ConBoqDtlXmlTreeDto> datas = new ArrayList<>();
        List<Element> ZygczgMxs = Zygczg.elements("ZygczgMx");
        if (!CollectionUtils.isEmpty(ZygczgMxs)) {
            int i = 0;
            for (Element element : ZygczgMxs) {
                ConBoqDtlXmlTreeDto mx = newObj(element.attributeValue("Mc"), parentId, i += 1, "ZygczgMx");
                mx.setBoqdXh(element.attributeValue("Xh"));
                mx.setBoqdContent(element.attributeValue("Gcnr"));
                mx.setBoqdPrice(new BigDecimal(element.attributeValue("Je")));
                mx.setBoqdRemark(element.attributeValue("Bz"));
                datas.add(mx);
            }
            xmlTree.setChildNodes(datas);
        }
        return xmlTree;
    }

    /**
     * 材料暂估价格
     *
     * @param dwgcxx
     * @param parentId
     * @param sort
     * @return
     */
    private static ConBoqDtlXmlTreeDto analysisClzg(Element dwgcxx, String parentId, int sort) {
        Element Clzg = dwgcxx.element("Clzg");
        if (Clzg == null) return null;
        ConBoqDtlXmlTreeDto xmlTree = newObj("材料暂估价格", parentId, sort, "Clzg");
        List<ConBoqDtlXmlTreeDto> datas = new ArrayList<>();
        List<Element> ClzgMxs = Clzg.elements("ClzgMx");
        if (!CollectionUtils.isEmpty(ClzgMxs)) {
            int i = 0;
            for (Element element : ClzgMxs) {
                ConBoqDtlXmlTreeDto mx = newObj(element.attributeValue("Mc"), parentId, i += 1, "ClzgMx");
                mx.setBoqdXh(element.attributeValue("Xh"));
                mx.setBoqdRcjId(element.attributeValue("RcjId"));
                mx.setBoqdCode(element.attributeValue("Clbh"));
                mx.setBoqdFeature(element.attributeValue("Ggxh"));
                mx.setBoqdUnit(element.attributeValue("Dw"));
                mx.setBoqdAmount(Float.valueOf(element.attributeValue("Sl")));
                mx.setBoqdPrice(new BigDecimal(element.attributeValue("Dj")));
                mx.setBoqdTotalPrice(new BigDecimal(element.attributeValue("Hj")));
                mx.setBoqdRemark(element.attributeValue("Bz"));
                datas.add(mx);
            }
            xmlTree.setChildNodes(datas);
        }
        return xmlTree;
    }

    /**
     * 暂列金额
     *
     * @param dwgcxx
     * @param parentId
     * @param sort
     * @return
     */
    private static ConBoqDtlXmlTreeDto analysisZlje(Element dwgcxx, String parentId, int sort) {
        Element Zlje = dwgcxx.element("Zlje");
        if (Zlje == null) return null;
        ConBoqDtlXmlTreeDto xmlTree = newObj("暂列金额", parentId, sort, "Zlje");
        List<ConBoqDtlXmlTreeDto> datas = new ArrayList<>();
        List<Element> ZljeMxs = Zlje.elements("ZljeMx");
        if (!CollectionUtils.isEmpty(ZljeMxs)) {
            int i = 0;
            for (Element element : ZljeMxs) {
                ConBoqDtlXmlTreeDto mx = newObj(element.attributeValue("Mc"), parentId, i += 1, "ZljeMx");
                mx.setBoqdXh(element.attributeValue("Xh"));
                mx.setBoqdUnit(element.attributeValue("Dw"));
                mx.setBoqdProvisionalEstimate(new BigDecimal(element.attributeValue("Zdje")));
                mx.setBoqdRemark(element.attributeValue("Bz"));
                datas.add(mx);
            }
            xmlTree.setChildNodes(datas);
        }
        return xmlTree;
    }

    /**
     * 其他项目
     *
     * @param dwgcxx
     * @param parentId
     * @param sort
     * @return
     */
    private static ConBoqDtlXmlTreeDto analysisQtxm(Element dwgcxx, String parentId, int sort) {
        Element Qtxm = dwgcxx.element("Qtxm");
        if (Qtxm == null) return null;
        ConBoqDtlXmlTreeDto xmlTree = newObj("其他项目", parentId, sort, "Qtxm");
        List<ConBoqDtlXmlTreeDto> datas = new ArrayList<>();
        List<Element> QtxmMxs = Qtxm.elements("QtxmMx");
        if (!CollectionUtils.isEmpty(QtxmMxs)) {
            int i = 0;
            for (Element element : QtxmMxs) {
                ConBoqDtlXmlTreeDto mx = newObj(element.attributeValue("Mc"), parentId, i += 1, "QtxmMx");
                mx.setBoqdXh(element.attributeValue("Xh"));
                mx.setBoqdUnit(element.attributeValue("Dw"));
                mx.setBoqdPrice(new BigDecimal(element.attributeValue("Je")));
                mx.setBoqdRemark(element.attributeValue("Bz"));
                datas.add(mx);
            }
            xmlTree.setChildNodes(datas);
        }
        return xmlTree;
    }

    /**
     * 措施项目
     *
     * @param dwgcxx
     * @param parentId
     * @param sort
     * @return
     */
    private static ConBoqDtlXmlTreeDto analysisCsxm(Element dwgcxx, String parentId, int sort) {
        Element Csxm = dwgcxx.element("Csxm");
        if (Csxm == null) return null;
        ConBoqDtlXmlTreeDto xmlTree = newObj("措施项目", parentId, sort, "Csxm");
        List<ConBoqDtlXmlTreeDto> datas = new ArrayList<>();
        //region 总价措施
        Element ZjCs = Csxm.element("ZjCs");
        if (ZjCs != null) {//总价措施
            ConBoqDtlXmlTreeDto zjcs = newObj("总价措施", parentId, 1, "ZjCs");
            zjcs.setChildNodes(analysisZjCsMx(ZjCs, zjcs.getBoqdId()));
            datas.add(zjcs);
        }
        //endregion

        //region 单价措施
        Element DjCs = Csxm.element("DjCs");
        if (DjCs != null) {// 单价措施
            ConBoqDtlXmlTreeDto djcs = newObj("单价措施", parentId, 2, "DjCs");
            djcs.setChildNodes(analysisDjCsMx(DjCs, djcs.getBoqdId()));
            datas.add(djcs);
        }
        //endregion
        xmlTree.setChildNodes(datas);
        return xmlTree;
    }

    /**
     * 单价措施明细
     *
     * @param Qdxdezj
     * @param parentId
     * @return
     */
    private static List<ConBoqDtlXmlTreeDto> analysisDjCsMx(Element DjCs, String parentId) {
        List<ConBoqDtlXmlTreeDto> datas = new ArrayList<>();
        List<Element> ZjCsMxs = DjCs.elements("DjCsMx");
        if (!CollectionUtils.isEmpty(ZjCsMxs)) {
            int i = 0;
            for (Element element : ZjCsMxs) {
                ConBoqDtlXmlTreeDto mx = newObj(element.attributeValue("Mc"), parentId, i += 1, "DjCsMx");
                mx.setBoqdXh(element.attributeValue("Xh"));
                mx.setBoqdCode(element.attributeValue("Bm"));
                mx.setBoqdFeature(element.attributeValue("Xmtz"));
                mx.setBoqdContent(element.attributeValue("Gcnr"));
                mx.setBoqdJsgz(element.attributeValue("Jsgz"));
                mx.setBoqdUnit(element.attributeValue("Dw"));
                mx.setBoqdAmount(Float.valueOf(element.attributeValue("Sl")));
                mx.setBoqdPrice(new BigDecimal(element.attributeValue("Zhdj")));
                mx.setBoqdRgf(money(element, "Rgf"));
                mx.setBoqdZcf(money(element, "Zcf"));
                mx.setBoqdSbf(money(element, "Sbf"));
                mx.setBoqdFcf(money(element, "Fcf"));
                mx.setBoqdClf(money(element, "Clf"));
                mx.setBoqdJxf(money(element, "Jxf"));
                mx.setBoqdGlf(money(element, "Glf"));
                mx.setBoqdLr(money(element, "Lr"));
                mx.setBoqdTotalPrice(new BigDecimal(element.attributeValue("Zhhj")));
                mx.setBoqdProvisionalEstimate(new BigDecimal(element.attributeValue("Zgj")));
                mx.setBoqdZgr(money(element, "Zgr"));
                analysisDjCsMxChild(mx, element);
                datas.add(mx);
            }
        }
        return datas;
    }

    /**
     * 单价措施-子项
     *
     * @param mx
     * @param DjCsMx
     */
    private static void analysisDjCsMxChild(ConBoqDtlXmlTreeDto mx, Element DjCsMx) {
        List<ConBoqDtlXmlTreeDto> datas = new ArrayList<>();
        Element Csxdezj = DjCsMx.element("Csxdezj");
        if (Csxdezj != null) {//清单项定额组价
            ConBoqDtlXmlTreeDto csxdezj = newObj("措施项定额组价", mx.getBoqdId(), 1, "Csxdezj");
            csxdezj.setChildNodes(analysisQdxdezjMx(Csxdezj, csxdezj.getBoqdId(), "CsxdezjMx"));
            datas.add(csxdezj);
        }

        Element Csxrcjhl = DjCsMx.element("Csxrcjhl");
        if (Csxrcjhl != null) {// 清单项人材机含量
            ConBoqDtlXmlTreeDto csxrcjhl = newObj("措施项人材机含量", mx.getBoqdId(), 2, "Csxrcjhl");
            csxrcjhl.setChildNodes(analysisQdxrcjhlMx(Csxrcjhl, csxrcjhl.getBoqdId(), "CsxrcjhlMx", "措施项人材机含量明细"));
            datas.add(csxrcjhl);
        }

        Element Csxdercjhl = DjCsMx.element("单价措施项定额人材机含量");
        if (Csxdercjhl != null) {// 清单项人材机含量
            ConBoqDtlXmlTreeDto csxdercjhl = newObj("单价措施项定额人材机含量", mx.getBoqdId(), 3, "Qdxdercjhl");
            csxdercjhl.setChildNodes(analysisQdxrcjhlMx(Csxdercjhl, csxdercjhl.getBoqdId(), "CsxdercjhlMx", "单价措施项定额人材机含量明细"));
            datas.add(csxdercjhl);
        }
        mx.setChildNodes(datas);
    }

    /**
     * 总价措施明细
     *
     * @param Qdxdezj
     * @param parentId
     * @return
     */
    private static List<ConBoqDtlXmlTreeDto> analysisZjCsMx(Element ZjCs, String parentId) {
        List<ConBoqDtlXmlTreeDto> datas = new ArrayList<>();
        List<Element> ZjCsMxs = ZjCs.elements("ZjCsMx");
        if (!CollectionUtils.isEmpty(ZjCsMxs)) {
            int i = 0;
            for (Element element : ZjCsMxs) {
                ConBoqDtlXmlTreeDto mx = newObj(element.attributeValue("Mc"), parentId, i += 1, "ZjCsMx");
                mx.setBoqdXh(element.attributeValue("Xh"));
                mx.setBoqdCode(element.attributeValue("Bm"));
                mx.setBoqdJsjc(element.attributeValue("Jsjc"));
                mx.setBoqdPrice(new BigDecimal(element.attributeValue("Je")));
                mx.setBoqdRate(Float.valueOf(element.attributeValue("Fl")));
                mx.setBoqdQfjs(new BigDecimal(element.attributeValue("Js")));
                datas.add(mx);
            }
        }
        return datas;
    }

    /**
     * 分部分项工程量清单
     *
     * @param dwgcxx
     * @param parentId
     * @param sort
     * @return
     */
    private static ConBoqDtlXmlTreeDto analysisQdxm(Element dwgcxx, String parentId, int sort) {
        Element Fywj = dwgcxx.element("Qdxm");
        if (Fywj == null) return null;
        ConBoqDtlXmlTreeDto xmlTree = newObj("分部分项", parentId, sort, "Qdxm");
        int i = 0;
        List<ConBoqDtlXmlTreeDto> mxs = new ArrayList<>();
        //分部分项标题
        List<Element> QdBts = Fywj.elements("QdBt");
        if (!CollectionUtils.isEmpty(QdBts)) {
            for (Element element : QdBts) {
                ConBoqDtlXmlTreeDto mx = new ConBoqDtlXmlTreeDto();
                mx.setBoqdParentId(xmlTree.getBoqdId());
                mx.setBoqdType("QdBt");
                mx.setBoqdXh(element.attributeValue("Xh"));
                mx.setBoqdName(element.attributeValue("Mc"));
                mx.setBoqdRemark(element.attributeValue("Bz"));
                mx.setBoqdPrice(new BigDecimal(element.attributeValue("Je")));
                mx.setBoqdProvisionalEstimate(new BigDecimal(element.attributeValue("Zgj")));
                mx.setBoqdSort(i += 1);
                mxs.add(mx);
            }
        }
        //分部分项清单明细
        List<Element> Qdmxs = Fywj.elements("Qdmx");
        if (!CollectionUtils.isEmpty(Qdmxs)) {
            for (Element element : Qdmxs) {
                ConBoqDtlXmlTreeDto mx = newObj(element.attributeValue("Mc"), xmlTree.getBoqdId(), i += 1, "Qdmx");
                mx.setBoqdXh(element.attributeValue("Xh"));
                mx.setBoqdCode(element.attributeValue("Qdbm"));
                mx.setBoqdFeature(element.attributeValue("Xmtz"));
                mx.setBoqdContent(element.attributeValue("Gcnr"));
                mx.setBoqdJsgz(element.attributeValue("Jsgz"));
                mx.setBoqdUnit(element.attributeValue("Dw"));
                mx.setBoqdAmount(Float.valueOf(element.attributeValue("Sl")));
                mx.setBoqdPrice(money(element, "Zhdj"));
                mx.setBoqdRgf(money(element, "Rgf"));
                mx.setBoqdZcf(money(element, "Zcf"));
                mx.setBoqdSbf(money(element, "Sbf"));
                mx.setBoqdFcf(money(element, "Fcf"));
                mx.setBoqdClf(money(element, "Clf"));
                mx.setBoqdJxf(money(element, "Jxf"));
                mx.setBoqdGlf(money(element, "Glf"));
                mx.setBoqdLr(money(element, "Lr"));
                mx.setBoqdTotalPrice(money(element, "Zhhj"));
                mx.setBoqdProvisionalEstimate(money(element, "Zgj"));
                mx.setBoqdZgr(money(element, "Zgr"));
                mx.setBoqdRemark(element.attributeValue("Bz"));
                mx.setChildNodes(analysisQdxmChild(element, mx.getBoqdId()));
                mxs.add(mx);
            }
        }
        xmlTree.setChildNodes(mxs);
        return xmlTree;
    }

    /**
     * 分部分项清单明细子集
     * Qdxdezj 和 Qdxrcjhl
     *
     * @param qdmx
     * @return
     */
    private static List<ConBoqDtlXmlTreeDto> analysisQdxmChild(Element qdmx, String parentId) {
        List<ConBoqDtlXmlTreeDto> datas = new ArrayList<>();
        Element Qdxdezj = qdmx.element("Qdxdezj");
        if (Qdxdezj != null) {//清单项定额组价
            ConBoqDtlXmlTreeDto qdxdezj = newObj("清单项定额组价", parentId, 1, "Qdxdezj");
            qdxdezj.setChildNodes(analysisQdxdezjMx(Qdxdezj, qdxdezj.getBoqdId(), "QdxdezjMx"));
            datas.add(qdxdezj);
        }

        Element Qdxrcjhl = qdmx.element("Qdxrcjhl");
        if (Qdxrcjhl != null) {// 清单项人材机含量
            ConBoqDtlXmlTreeDto qdxrcjhl = newObj("清单项人材机含量", parentId, 2, "Qdxrcjhl");
            qdxrcjhl.setChildNodes(analysisQdxrcjhlMx(Qdxrcjhl, qdxrcjhl.getBoqdId(), "QdxrcjhlMx", "清单项人材机含量明细"));
            datas.add(qdxrcjhl);
        }

        Element Qdxdercjhl = qdmx.element("Qdxdercjhl");
        if (Qdxdercjhl != null) {// 清单项定额人材机含量
            ConBoqDtlXmlTreeDto qdxdercjhl = newObj("清单项定额人材机含量", parentId, 3, "Qdxdercjhl");
            qdxdercjhl.setChildNodes(analysisQdxrcjhlMx(Qdxdercjhl, qdxdercjhl.getBoqdId(), "QdxdercjhlMx", "清单项定额人材机含量明细"));
            datas.add(qdxdercjhl);
        }
        return datas;
    }

    /**
     * 清单项定额人材机含量明细
     *
     * @param Qdxdercjhl
     * @param parentId
     * @return
     */
    private static List<ConBoqDtlXmlTreeDto> analysisQdxdercjhlMx(Element Qdxdercjhl, String parentId) {
        List<ConBoqDtlXmlTreeDto> datas = new ArrayList<>();
        List<Element> QdxdercjhlMxs = Qdxdercjhl.elements("QdxdercjhlMx");
        if (!CollectionUtils.isEmpty(QdxdercjhlMxs)) {
            int i = 0;
            for (Element element : QdxdercjhlMxs) {
                ConBoqDtlXmlTreeDto mx = newObj("清单项定额人材机含量明细", parentId, i += 1, "QdxdercjhlMx");
                mx.setBoqdRcjId(element.attributeValue("RcjId"));
                mx.setBoqdTotalPrice(new BigDecimal(element.attributeValue("Rcjhj")));
                datas.add(mx);
            }
        }
        return datas;
    }

    /**
     * 清单项人材机含量明细
     * 或者 单价措施项定额人材机含量明细
     *
     * @param Qdxrcjhl
     * @param parentId
     * @return
     */
    private static List<ConBoqDtlXmlTreeDto> analysisQdxrcjhlMx(Element Qdxrcjhl, String parentId, String nodeName, String boqdName) {
        List<ConBoqDtlXmlTreeDto> datas = new ArrayList<>();
        List<Element> QdxrcjhlMxs = Qdxrcjhl.elements(nodeName);
        if (!CollectionUtils.isEmpty(QdxrcjhlMxs)) {
            int i = 0;
            for (Element element : QdxrcjhlMxs) {
                ConBoqDtlXmlTreeDto mx = newObj(boqdName, parentId, i += 1, nodeName);
                mx.setBoqdRcjId(element.attributeValue("RcjId"));
                mx.setBoqdRcjhl(money(element,"Rcjhl"));
                mx.setBoqdTotalPrice(new BigDecimal(element.attributeValue("Rcjhj")));
                datas.add(mx);
            }
        }
        return datas;
    }

    /**
     * 清单项定额组价明细
     *
     * @param Qdxdezj
     * @param parentId
     * @return
     */
    private static List<ConBoqDtlXmlTreeDto> analysisQdxdezjMx(Element Qdxdezj, String parentId, String nodeName) {
        List<ConBoqDtlXmlTreeDto> datas = new ArrayList<>();
        List<Element> QdxdezjMxs = Qdxdezj.elements(nodeName);
        if (!CollectionUtils.isEmpty(QdxdezjMxs)) {
            int i = 0;
            for (Element element : QdxdezjMxs) {
                ConBoqDtlXmlTreeDto mx = newObj(element.attributeValue("Mc"), parentId, i += 1, nodeName);
                mx.setBoqdCode(element.attributeValue("Debm"));
                mx.setBoqdUnit(element.attributeValue("Dw"));
                mx.setBoqdAmount(Float.valueOf(element.attributeValue("Sl")));
                mx.setBoqdPrice(money(element, "Dj"));
                mx.setBoqdRgf(money(element, "Rgf"));
                mx.setBoqdZcf(money(element, "Zcf"));
                mx.setBoqdSbf(money(element, "Sbf"));
                mx.setBoqdFcf(money(element, "Fcf"));
                mx.setBoqdClf(money(element, "Clf"));
                mx.setBoqdJxf(money(element, "Jxf"));
                mx.setBoqdGlf(money(element, "Glf"));
                mx.setBoqdLr(money(element, "Lr"));
                mx.setBoqdTotalPrice(money(element, "Hj"));
                datas.add(mx);
            }
        }
        return datas;
    }

    /**
     * 单位工程费汇总表
     *
     * @param dwgcxx
     * @param parentId
     * @return
     */
    private static ConBoqDtlXmlTreeDto analysisFywj(Element dwgcxx, String parentId, int sort) {
        Element Fywj = dwgcxx.element("Fywj");
        if (Fywj == null) return null;
        ConBoqDtlXmlTreeDto xmlTree = new ConBoqDtlXmlTreeDto();
        //单项工程信息
        xmlTree.setBoqdParentId(parentId);
        xmlTree.setBoqdType("Fywj");
        xmlTree.setBoqdName("单位工程费汇总");
        xmlTree.setBoqdId(GenerateUUID.getUUID());
        xmlTree.setBoqdSort(sort);
        List<Element> FywjMxs = Fywj.elements("FywjMx");
        List<ConBoqDtlXmlTreeDto> mxs = new ArrayList<>();
        if (!CollectionUtils.isEmpty(FywjMxs)) {
            int i = 0;
            for (Element fywjMx : FywjMxs) {
                ConBoqDtlXmlTreeDto mx = new ConBoqDtlXmlTreeDto();
                mx.setBoqdParentId(xmlTree.getBoqdId());
                mx.setBoqdId(GenerateUUID.getUUID());
                mx.setBoqdType("FywjMx");
                mx.setBoqdXh(fywjMx.attributeValue("Xh"));
                mx.setBoqdName(fywjMx.attributeValue("Mc"));
                mx.setBoqdPrice(new BigDecimal(fywjMx.attributeValue("Je")));
                mx.setBoqdProvisionalEstimate(new BigDecimal(fywjMx.attributeValue("Zgj")));
                mx.setBoqdSort(i += 1);
                mxs.add(mx);
            }
        }
        xmlTree.setChildNodes(mxs);
        return xmlTree;
    }

    /**
     * 规费 税金
     *
     * @param dwgcxx
     * @param parentId
     * @param sort
     * @return
     */
    private static ConBoqDtlXmlTreeDto analysisGfsj(Element dwgcxx, String parentId, int sort) {
        Element Fywj = dwgcxx.element("Gfsj");
        if (Fywj == null) return null;
        ConBoqDtlXmlTreeDto xmlTree = new ConBoqDtlXmlTreeDto();
        //单项工程信息
        xmlTree.setBoqdParentId(parentId);
        xmlTree.setBoqdType("Gfsj");
        xmlTree.setBoqdName("规费、税金项目");
        xmlTree.setBoqdId(GenerateUUID.getUUID());
        xmlTree.setBoqdSort(sort);
        List<Element> FywjMxs = Fywj.elements("GfsjMx");
        List<ConBoqDtlXmlTreeDto> mxs = new ArrayList<>();
        if (!CollectionUtils.isEmpty(FywjMxs)) {
            int i = 0;
            for (Element fywjMx : FywjMxs) {
                ConBoqDtlXmlTreeDto mx = new ConBoqDtlXmlTreeDto();
                mx.setBoqdParentId(xmlTree.getBoqdId());
                mx.setBoqdId(GenerateUUID.getUUID());
                mx.setBoqdType("GfsjMx");
                mx.setBoqdXh(fywjMx.attributeValue("Xh"));
                mx.setBoqdName(fywjMx.attributeValue("Mc"));
                mx.setBoqdJsjc(fywjMx.attributeValue("Jsjc"));
                mx.setBoqdPrice(new BigDecimal(fywjMx.attributeValue("Je")));
                mx.setBoqdQfjs(new BigDecimal(fywjMx.attributeValue("Qfjs")));
                mx.setBoqdRate(Float.valueOf(fywjMx.attributeValue("Fl")));
                mx.setBoqdSort(i += 1);
                mxs.add(mx);
            }
        }
        xmlTree.setChildNodes(mxs);
        return xmlTree;
    }

    private static void add(List<ConBoqDtlXmlTreeDto> datas, ConBoqDtlXmlTreeDto tree) {
        if (tree == null) return;
        datas.add(tree);
    }

    private static BigDecimal money(Element element, String key) {
        return new BigDecimal(element.attributeValue(key));
    }

    private static ConBoqDtlXmlTreeDto newObj(String name, String parentId, int sort, String type) {
        ConBoqDtlXmlTreeDto data = new ConBoqDtlXmlTreeDto();
        data.setBoqdSort(sort);
        data.setBoqdName(name);
        data.setBoqdParentId(parentId);
        data.setBoqdId(GenerateUUID.getUUID());
        data.setBoqdType(type);
        return data;
    }
}
