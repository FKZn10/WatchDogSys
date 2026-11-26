package com.jpmsoft.userProducer.utils;

import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.pojo.dto.PwdRuleChckConfDTO;
import com.jpmsoft.userProducer.pojo.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/9 17:44
 */
@Slf4j
public class CheckPwdUtil {
    /**
     * 数字
     */
    private static final String REG_NUMBER = ".*\\d+.*";
    /**
     * 小写字母
     */
    private static final String REG_UPPERCASE = ".*[A-Z]+.*";
    /**
     * 大写字母
     */
    private static final String REG_LOWERCASE = ".*[a-z]+.*";
    /**
     * 特殊符号(~!@#$%^&*()_+|<>,.?/:;'[]{}\)
     */
    private static final String REG_SYMBOL = ".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*";
    /**
     * 键盘字符表(小写)
     * 非shift键盘字符表
     */
    private static final char[][] CHAR_TABLE1 = new char[][]{{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '-', '=', '\0'}, {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', '[', ']', '\\'}, {'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', ';', '\'', '\0', '\0'}, {'z', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.', '/', '\0', '\0', '\0'}};
    /**
     * shift键盘的字符表
     */
    private static final char[][] CHAR_TABLE2 = new char[][]{{'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+', '\0'}, {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', '{', '}', '|'}, {'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', ':', '"', '\0', '\0'}, {'z', 'x', 'c', 'v', 'b', 'n', 'm', '<', '>', '?', '\0', '\0', '\0'}};

    /**
     * 判断是否是汉字
     */
    public static boolean isHz(String c) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(c);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 汉字换拼音
     *
     * @param hz
     * @return
     */
    public static String hz2Py(String hz) {
        if (ValidateUtil.isEmpty(hz)) return null;
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        //拼音小写
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        //不带声调
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        //要转换的中文，格式，转换之后的拼音的分隔符，遇到不能转换的是否保留
        try {
            return PinyinHelper.toHanYuPinyinString(hz, format, "", true);
        } catch (BadHanyuPinyinOutputFormatCombination ex) {
            log.error("汉字转拼音失败！" + ex.getMessage(),ex);
        }
        return null;
    }

    /**
     * 校验密码是否包含用户姓名
     *
     * @param name 用户姓名
     * @return
     */
    public static boolean checkContainsName(String pwd, String name) {
        pwd=pwd.toLowerCase(Locale.ENGLISH);
        //没有用户名则不校验
        if (ValidateUtil.isEmpty(name)) return false;
        String qp = hz2Py(name);
        //密码包含全拼
        if (ValidateUtil.isNotEmpty(qp) && pwd.contains(qp)) return true;
        char[] chars = name.toCharArray();
        //包含单个汉字拼音
        for (char a : chars) {
            String dz = a + "";
            if (isHz(dz)) {
                String dzPy = hz2Py(dz);
                if (ValidateUtil.isNotEmpty(dzPy) && pwd.contains(dzPy)) return true;
            }
        }
        return false;
    }

    /**
     * 校验密码
     *
     * @param password 密码
     * @param user     用户信息
     */
    public static boolean check(String password, SysUser user, PwdRuleChckConfDTO conf) {
        //密码长度校验
        if (ValidateUtil.isEmpty(password) || password.length() < conf.getMinLen() || password.length() > conf.getMaxLen())
            throw new JPMException(String.format(BaseMsg.pwdLenthAsk, conf.getMinLen(), conf.getMaxLen()));
        //密码不能包含账户名
        if (conf.isContainsAccount() && password.toLowerCase(Locale.ENGLISH).contains(user.getuAccount().toLowerCase(Locale.ENGLISH)))
            throw new JPMException(BaseMsg.pwdContainsAccount);
        //密码不能包含三个及以上连续的数值、字符、键盘连续按键
        if (conf.isContinuous() && (isContinuousChar(password) || isKeyBoardContinuousChar(password)))
            throw new JPMException(BaseMsg.pwdContinuousChar);
        //密码是否包含姓名(中文转英文后的单个拼音匹配,以及转英文后全匹配(中英结合名称))
        if (conf.isContainsName() && checkContainsName(password, user.getuName()))
            throw new JPMException(String.format(BaseMsg.pwdContainsName, conf.getCharType()));
        int i = 0;
        if (password.matches(REG_NUMBER)) i++;
        if (password.matches(REG_LOWERCASE)) i++;
        if (password.matches(REG_UPPERCASE)) i++;
        if (password.matches(REG_SYMBOL)) i++;
        if (i < conf.getCharType()) {
            log.info("数字、大小写字母、特殊字符,4中类型必须全部都有");
            throw new JPMException(String.format(BaseMsg.pwdCategoryAsk, conf.getCharType()));
        }
        return true;
    }


    /**
     * 是否包含3个及以上相同或字典连续字符
     */
    private static boolean isContinuousChar(String password) {
        //连续字符不区分大小写
        char[] chars = password.toLowerCase(Locale.ENGLISH).toCharArray();
        for (int i = 0; i < chars.length - 2; i++) {
            int n1 = chars[i];
            int n2 = chars[i + 1];
            int n3 = chars[i + 2];
            // 判断重复字符
            if (n1 == n2 && n1 == n3) {
                return true;
            }
            // 判断连续字符： 正序 + 倒序
            if ((n1 + 1 == n2 && n1 + 2 == n3) || (n1 - 1 == n2 && n1 - 2 == n3)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否包含3个及以上键盘连续字符
     *
     * @param password 待匹配的字符串
     */
    private static boolean isKeyBoardContinuousChar(String password) {
        if (ValidateUtil.isEmpty(password)) {
            return false;
        }
        //考虑大小写，都转换成小写字母
        char[] lpStrChars = password.toLowerCase(Locale.ENGLISH).toCharArray();

        // 获取字符串长度
        int nStrLen = lpStrChars.length;
        // 定义位置数组：row - 行，col - column 列
        int[] pRowCharPos = new int[nStrLen];
        int[] pColCharPos = new int[nStrLen];
        for (int i = 0; i < nStrLen; i++) {
            char chLower = lpStrChars[i];
            pColCharPos[i] = -1;
            // 检索在表1中的位置，构建位置数组
            for (int nRowTable1Idx = 0; nRowTable1Idx < 4; nRowTable1Idx++) {
                for (int nColTable1Idx = 0; nColTable1Idx < 13; nColTable1Idx++) {
                    if (chLower == CHAR_TABLE1[nRowTable1Idx][nColTable1Idx]) {
                        pRowCharPos[i] = nRowTable1Idx;
                        pColCharPos[i] = nColTable1Idx;
                    }
                }
            }
            // 在表1中没找到，到表二中去找，找到则continue
            if (pColCharPos[i] >= 0) {
                continue;
            }
            // 检索在表2中的位置，构建位置数组
            for (int nRowTable2Idx = 0; nRowTable2Idx < 4; nRowTable2Idx++) {
                for (int nColTable2Idx = 0; nColTable2Idx < 13; nColTable2Idx++) {
                    if (chLower == CHAR_TABLE2[nRowTable2Idx][nColTable2Idx]) {
                        pRowCharPos[i] = nRowTable2Idx;
                        pColCharPos[i] = nColTable2Idx;
                    }
                }
            }
        }

        // 匹配坐标连线
        for (int j = 1; j <= nStrLen - 2; j++) {
            //同一行
            if (pRowCharPos[j - 1] == pRowCharPos[j] && pRowCharPos[j] == pRowCharPos[j + 1]) {
                // 键盘行正向连续（asd）或者键盘行反向连续（dsa）
                if ((pColCharPos[j - 1] + 1 == pColCharPos[j] && pColCharPos[j] + 1 == pColCharPos[j + 1]) || (pColCharPos[j + 1] + 1 == pColCharPos[j] && pColCharPos[j] + 1 == pColCharPos[j - 1])) {
                    return true;
                }
            }
            //同一列
            if (pColCharPos[j - 1] == pColCharPos[j] && pColCharPos[j] == pColCharPos[j + 1]) {
                //键盘列连续（qaz）或者键盘列反向连续（zaq）
                if ((pRowCharPos[j - 1] + 1 == pRowCharPos[j] && pRowCharPos[j] + 1 == pRowCharPos[j + 1]) || (pRowCharPos[j - 1] - 1 == pRowCharPos[j] && pRowCharPos[j] - 1 == pRowCharPos[j + 1])) {
                    return true;
                }
            }
        }
        return false;
    }
}
