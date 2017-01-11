package com.greatfly.common.util;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.lang.StringUtils;

import com.greatfly.common.util.exception.AppException;

/**
 * 字符串操作工具类
 * @author wuwq
 * Jul 23, 2010   11:13:03 AM
 */
public final class StringUtil {
    private static String hexString = "0123456789ABCDEF"; //定义16进制字符
    
    // 定义 String Map List 之间互转时的 分隔符
    private static final String SEP1 = ","; 
    private static final String SEP2 = "|";
    private static final String SEP3 = "=";
    
    /**
     * 防止实例化使用
     */
    private StringUtil() {}
    
    /**
     * 判断某字符串是否为空，为空的标准是 str==null 或 str.length()==0<br>
     * StringUtil.isEmpty(null) = true<br>
     * StringUtil.isEmpty("") = true<br>
     * StringUtil.isEmpty(" ") = false //注意在 StringUtil 中空格作非空处理<br>
     * StringUtil.isEmpty("   ") = false<br>
     * StringUtil.isEmpty("bob") = false<br>
     * StringUtil.isEmpty(" bob ") = false<br>
     * @param str 需要判断的字符串
     * @return 判断结果
     */
    public static boolean isEmpty(String str) {
        return StringUtils.isEmpty(str);
    }
    /**
     * 判断某字符串是否不为空，为空的标准是 str==null 或 str.length()==0<br>
     * StringUtil.isNotEmpty(null) = false<br>
     * StringUtil.isNotEmpty("") = false<br>
     * StringUtil.isNotEmpty(" ") = true<br>
     * StringUtil.isNotEmpty("   ") = true<br>
     * StringUtil.isNotEmpty("bob") = true<br>
     * StringUtil.isNotEmpty(" bob ") = true <br>
     * @param str 需要判断的字符串
     * @return 判断结果
     */
    public static boolean isNotEmpty(String str) {
        return StringUtils.isNotEmpty(str);
    }
    /**
     * 判断某字符串是否为空或长度为0或由空白符(whitespace) 构成<br>
     * StringUtil.isBlank(null) = true<br>
     * StringUtil.isBlank("") = true<br>
     * StringUtil.isBlank(" ") = true<br>
     * StringUtil.isBlank("  ") = true<br>
     * StringUtil.isBlank("\t \n \f \r") = true   //对于制表符、换行符、换页符和回车符 <br>
     * StringUtil.isBlank()   //均识为空白符<br>
     * StringUtil.isBlank("\b") = false   //"\b"为单词边界符<br>
     * StringUtil.isBlank("bob") = false<br>
     * StringUtil.isBlank(" bob ") = false <br>
     * @param str 需要判断的字符串
     * @return 判断结果
     */
    public static boolean isBlank(String str) {
        return StringUtils.isBlank(str);
    }
    /**
     * 判断某字符串是否不为空或长度不为0或不是都由空白符(whitespace) 构成<br>
     * StringUtil.isNotBlank(null) = false<br>
     * StringUtil.isNotBlank("") = false<br>
     * StringUtil.isNotBlank(" ") = false<br>
     * StringUtil.isNotBlank("   ") = false<br>
     * StringUtil.isNotBlank("\t \n \f \r") = false<br>
     * StringUtil.isNotBlank("\b") = true<br>
     * StringUtil.isNotBlank("bob") = true<br>
     * StringUtil.isNotBlank(" bob ") = true <br>
     * @param str 需要判断的字符串
     * @return 判断结果
     */
    public static boolean isNotBlank(String str) {
        return StringUtils.isNotBlank(str);
    }
    /**
     * 去掉字符串两端的控制符(control characters, char <= 32) , 如果输入为 null 则返回null<br>
     * StringUtil.trim(null) = null<br>
     * StringUtil.trim("") = ""<br>
     * StringUtil.trim(" ") = ""<br>
     * StringUtil.trim("  \b \t \n \f \r    ") = ""<br>
     * StringUtil.trim("     \n\tss   \b") = "ss"<br>
     * StringUtil.trim(" d   d dd     ") = "d   d dd"<br>
     * StringUtil.trim("dd     ") = "dd"<br>
     * StringUtil.trim("     dd ") = "dd"<br>
     * @param str 需要过滤的字符串
     * @return 过滤后的字符串
     */
    public static String trim(String str) {
        return StringUtils.trim(str);
    }
    /**
     * 将输入的字符串全部转化为大写字母
     * @param str 要转换的字符串
     * @return 转换后的字符串
     */
    public static String toUpperStr(String str) {
        return StringUtils.upperCase(str);
    }    
    /**
     * 将输入的字符串全部转换为小写字母
     * @param str 要转换的字符串
     * @return 转换后的字符串
     */
    public static String toLowerStr(String str) {
        return StringUtils.lowerCase(str);
    }
    
    /**
     * 将输入的姓名内码转化为明码，规则是把以00开始和以Z0结尾之间的字符串转码，其他字符保留
     * @param interCode 内码
     * @return 明码字符串
     */
    public static String interCodeToPlainCode(String interCode) {
        if (isBlank(interCode)) {
            return null;
        }
        
        int begin = interCode.indexOf("00"); //开始字符
        int end = interCode.indexOf("Z0"); //结束字符
        if (begin < 0 || end < 0) { //没有需要转码的起止标志位，不转码
            return interCode;
        } else if (begin > end) {
            throw new AppException("查到00在Z0之后，不符合转码规则，参数：" + interCode);
        }
        
        String leftCode = interCode.substring(0, begin); //开始字符前的字符串
        String rightCode = interCode.substring(end + 2, interCode.length()); //结束字符后的字符串
        StringBuffer plainCode = new StringBuffer();
        plainCode.append(leftCode);
        interCode = interCode.substring(begin + 2, end); //需要转码的字符串
        String str = null;
        for (int i = 0; i < interCode.length() - 1; i += 4) {
            str = interCode.substring(i, i + 4);
            plainCode.append(hexStrTochnStr(str));
        }
        plainCode.append(rightCode);
        return plainCode.toString();
    }
    
    /**
     * 将汉字转化为16进制
     * @param chnStr 转换汉字
     * @return 16进制串
     */
    public static String chnStrToHexStr(String chnStr) {
        //根据默认编码获取字节数组
        byte[] bytes = chnStr.getBytes(Charset.forName("GBK"));
        StringBuffer sb = new StringBuffer(bytes.length * 2);
        //将字节数组中每个字节拆解成2位16进制整数
        for (int i = 0; i < bytes.length; i++) {
            sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
        }
        return sb.toString();
    }
    
    /**
     * 增加给字符串中的中文增加对应个数的空格
     * @param inputStr 待处理的字符串
     * @return 转换后的字符串
     */
    public static String addSpaceAfterChn(String inputStr) {
        StringBuffer sbt = new StringBuffer();
        if (isBlank(inputStr)) {
            return "";
        }
        
        int spaceC = 0; //需增加空格个数
        for (int i = 0; i < inputStr.length(); i++) {
            sbt.append(inputStr.charAt(i));
            
            if (!isLetter(inputStr.charAt(i)) && inputStr.length() > i + 1 && !isLetter(inputStr.charAt(i + 1))) {
                spaceC++;
            } 
            if (!isLetter(inputStr.charAt(i)) && inputStr.length() > i + 1 && isLetter(inputStr.charAt(i + 1))) {
                for (int j = 0; j <= spaceC; j++) {
                    sbt.append(' ');
                }
                spaceC = 0;
            }
        }
        return sbt.toString();
    }
    
    /**
     * 判断一个字符是Ascill字符还是其它字符（如汉，日，韩文字符），若为汉字，返回false
     * @param c 字符
     * @return 是否是Ascill字符
     */
    public static boolean isLetter(char c) {    
        int k = 0x80;    
        return c / k == 0;    
    }
    
    /**
     * 处理由,隔开的数字型id串，以保证最后不是以,结尾，防止转成数组的时候出现空元素，用于sql拼接
     * @param idStr 待处理的id串
     * @return 处理后的id串
     */
    public static String dealIdStr4Num(String idStr) {
        if (StringUtil.isBlank(idStr)) {
            return null;
        } else if (idStr.lastIndexOf(',') == idStr.length() - 1) { //以,结尾
            return idStr.substring(0, idStr.length() - 1);
        }
        return idStr;
    }
    
    /**
     * 处理由,隔开的字符串型id串，以保证最后不是以,结尾，防止转成数组的时候出现空元素，用于sql拼接
     * @param oldStr 待处理的id串
     * @return 处理后的id串
     */
    public static String dealIdStr4Str(String oldStr) {
        if (StringUtil.isBlank(oldStr)) {
            return null;
        }
        if (oldStr.lastIndexOf(',') == oldStr.length() - 1) { //以,结尾
            oldStr = oldStr.substring(0, oldStr.length() - 1);
        }
        String[] arr = oldStr.split(",");
        if (arr.length == 0) {
            return null;
        }
        StringBuffer newStr = new StringBuffer();
        for (String tmp : arr) {
            newStr.append('\'').append(tmp).append("',");
        }
        
        oldStr = newStr.toString();
        oldStr = oldStr.substring(0, oldStr.length() - 1); //再去除一次结尾的,
        return oldStr;
    }
    
    // private methods
    
    /**
     * 将16进制转化为汉字
     * @param hexStr 16进制代码
     * @return 对应汉字
     */
    private static String hexStrTochnStr(String hexStr) {
        if (isBlank(hexStr) || (hexStr.length()) % 2 != 0) {
            return null;
        }
        
        int byteLength = hexStr.length() / 2;        
        byte[] bytes = new byte[ byteLength ];
        
        int temp = 0;
        for (int i = 0; i < byteLength; i++) {
            temp = hex2Dec(hexStr.charAt(2 * i)) * 16 + hex2Dec(hexStr.charAt(2 * i + 1));
            bytes[i] = (byte) (temp < 128 ? temp : temp - 256);
        }
        try {
            return new String(bytes, "GBK");
        } catch (Exception e) {
            throw new AppException("hexStrTochnStr转码异常", e);
        }
    }
    
    /**
     * 将16进制字符进行相应转换成数字
     * @param ch 16进制字符
     * @return 数字
     */
    private static int hex2Dec(char ch) {
        int num = -1;
        switch (ch) {
            case '0':
                num = 0;
                break;
            case '1':
                num = 1;
                break;
            case '2':
                num = 2;
                break;
            case '3':
                num = 3;
                break;
            case '4':
                num = 4;
                break;
            case '5':
                num = 5;
                break;
            case '6':
                num = 6;
                break;
            case '7':
                num = 7;
                break;
            case '8':
                num = 8;
                break;
            case '9':
                num = 9;
                break;
            case 'a':
            case 'A':
                num = 10;
                break;
            case 'b':
            case 'B':
                num = 11;
                break;
            case 'c':
            case 'C':
                num = 12;
                break;
            case 'd':
            case 'D':
                num = 13;
                break;
            case 'e':
            case 'E':
                num = 14;
                break;
            case 'f':
            case 'F':
                num = 15;
                break;
            default:
                num = -1;
        }
        return num;
    }
    
    /** 
     * 半角转全角 
     * @param input 输入字符
     * @return 半角字符
     */  
    public static final String full2Half(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);
            }
        }
        String returnString = new String(c);

        return returnString;
    }
    
    /**
     * 字符串匹配,返回是否匹配成功
     * @param regexPattern    正则表达式
     * @param regexContext    字符串内容
     * @return boolean      
     * @throws
     * @author raofh
     * @date 2016-1-21
     */
    public static final boolean regexVerify(String regexPattern, String regexContext) {
        Pattern p1 = Pattern.compile(regexPattern); 
        Matcher m1 = p1.matcher(regexContext);
        return m1.find();
    }
    
    
    /**
     * 判断字符串是否为数字
     * @param str 字符串内容
     * @return boolean 是为true
     */
    public static final boolean isNumeric(String str) {
      for (int i = 0; i < str.length(); i++) {
         if (!Character.isDigit(str.charAt(i))) {
             return false;
         }
      }
      return true;
    }

    /** 
     * 随机指定范围内N个不重复的数 
     * 最简单最基本的方法 
     * @param min 指定范围最小值 
     * @param max 指定范围最大值 
     * @param n 随机数个数 
     * @return int[] 返回生成的数组
     */  
    public static int[] randomCommon(int min, int max, int n) {  
        if (n > (max - min + 1) || max < min) {  
               return null;  
           }  
        int[] result = new int[n];  
        int count = 0;  
        while (count < n) {  
            int num = (int) (Math.random() * (max - min)) + min;  
            boolean flag = true;  
            for (int j = 0; j < n; j++) {  
                if (num == result[j]) {  
                    flag = false;  
                    break;  
                }  
            }  
            if (flag) {  
                result[count] = num;  
                count++;  
            }  
        }  
        return result;  
    }  
    
    /**
     * List转换String
     * @param list 需要转换的List
     * @return List转换后的字符串
     */ 
    public static String ListToString(List<?> list) { 
        StringBuffer sb = new StringBuffer(); 
        if (list != null && list.size() > 0) { 
            for (int i = 0; i < list.size(); i++) { 
                if (list.get(i) == null || list.get(i).equals("")) { 
                    continue; 
                } 
                if (i > 0) {
                    sb.append(SEP1); 
                }
                // 如果值是list类型则调用自己 
                if (list.get(i) instanceof List) { 
                    sb.append(ListToString((List<?>) list.get(i))); 
                } else if (list.get(i) instanceof Map) { 
                    sb.append(MapToString((Map<?, ?>) list.get(i))); 
                } else { 
                    sb.append(list.get(i)); 
                }
            } 
        } 
        return sb.toString(); 
    } 
     
    /**
     * Map转换String
     * @param map :需要转换的Map
     * @return Map转换后的字符串
     */ 
    public static String MapToString(Map<?, ?> map) { 
        StringBuffer sb = new StringBuffer(); 
        // 遍历map 
        for (Object obj : map.keySet()) { 
            if (obj == null) { 
                continue; 
            } 
            Object key = obj; 
            Object value = map.get(key); 
            
            if (value instanceof List<?>) { 
                sb.append(key.toString() + SEP1 + ListToString((List<?>) value)); 
                sb.append(SEP2); 
            } else if (value instanceof Map<?, ?>) { 
                sb.append(key.toString() + SEP1 
                        + MapToString((Map<?, ?>) value)); 
                sb.append(SEP2); 
            } else { 
                sb.append(key.toString() + SEP3 + value.toString()); 
                sb.append(SEP2); 
            } 
        } 
        return "M" + sb.toString(); 
    } 
   
    /**
     * String转换Map
     * @param mapText :需要转换的字符串
     * @return Map<?,?> 返回Map对象
     * 3
     */ 
    public static Map<String, Object> StringToMap(String mapText) { 
        if (mapText == null || mapText.equals("")) { 
            return null; 
        } 
        mapText = mapText.substring(1); 
   
        Map<String, Object> map = new HashMap<String, Object>(); 
        String[] text = mapText.split("\\" + SEP2); // 转换为数组 
        for (String str : text) { 
            String[] keyText = str.split(SEP3); // 转换key与value的数组 
            if (keyText.length < 1) { 
                continue; 
            } 
            String key = keyText[0]; // key 
            String value = keyText[1]; // value 
            if (value.charAt(0) == 'M') { 
                Map<?, ?> map1 = StringToMap(value); 
                map.put(key, map1); 
            } else if (value.charAt(0) == 'L') { 
                List<?> list = StringToList(value); 
                map.put(key, list); 
            } else { 
                map.put(key, value); 
            } 
        } 
        return map; 
    } 
   
    /**
     * String转换List
     * @param listText
     *            :需要转换的文本
     * @return List<?>
     */ 
    public static List<Object> StringToList(String listText) { 
        if (listText == null || listText.equals("")) { 
            return null; 
        } 
        listText = listText.substring(1); 

        List<Object> list = new ArrayList<Object>(); 
        String[] text = listText.split(SEP1); 
        for (String str : text) { 
            if (str.charAt(0) == 'M') { 
                Map<?, ?> map = StringToMap(str); 
                list.add(map); 
            } else if (str.charAt(0) == 'L') { 
                List<?> lists = StringToList(str); 
                list.add(lists); 
            } else { 
                list.add(str); 
            } 
        } 
        return list; 
    }
    
    /**
     * 将汉字转换为全拼  
     * @param src  文字
     * @return  中文首字母(大写)
     */
    public static String getPingYin(String src) {  
  
        char[] t1 = null;  
        t1 = src.toCharArray();  
        String[] t2 = new String[t1.length];  
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();  
          
        t3.setCaseType(HanyuPinyinCaseType.UPPERCASE);  
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);  
        String t4 = "";  
        int t0 = t1.length;  
        try {  
            for (int i = 0; i < t0; i++) {  
                // 判断是否为汉字字符  
                if (java.lang.Character.toString(t1[i]).matches(
                        "[\\u4E00-\\u9FA5]+")) {  
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);  
                    t4 += t2[0];  
                } else  
                    t4 += java.lang.Character.toString(t1[i]);  
            }  
            return t4;  
        } catch (BadHanyuPinyinOutputFormatCombination e1) {  
            e1.printStackTrace();  
        }  
        return t4;  
    }  
  
    /**
     * 返回中文的首字母 
     * @param str  文字
     * @return  中文首字母(小写)
     */
    public static String getPinYinHeadChar(String str) {  
        String convert = "";  
    
        for (int j = 0; j < str.length(); j++) {  
            char word = str.charAt(j);  
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);  
            if (pinyinArray != null) {  
                convert += pinyinArray[0].charAt(0);  
            } else {  
                convert += word;  
            }  
        }  
        return convert;  
    }  
   
    /**
     * 将字符串转移为ASCII码 
     * @param cnStr 需要转换的字符串
     * @return String  转换后的结果字符串
     */
    public static String getCnASCII(String cnStr) { 
        // new String(String.getBytes("ISO-8859-1"), "utf-8");
        StringBuffer strBuf = new StringBuffer();  
        byte[] bGBK =  cnStr.getBytes();  
        for (int i = 0; i < bGBK.length; i++) {  
            strBuf.append(Integer.toHexString(bGBK[i] & 0xff));  
        }  
        return strBuf.toString();
    }  
    

}
