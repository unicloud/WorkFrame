package com.greatfly.common.util.msgconverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.greatfly.common.CommonConstant;
import com.greatfly.common.util.StringUtil;
import com.greatfly.common.util.exception.AppException;


/**
 * 公共方法类
 * @author 饶凤虎
 *
 */
public final class CommonMsgOutput {
    
    private static final Logger LOGGER = Logger.getLogger(CommonMsgOutput.class);
    
    /**
     * 创建一个私有新的实例 CommonMethodUtil.
     *
     */
    private CommonMsgOutput() {
        
    }

    /**
     * 将String字符串类型的数组转为 List<String> 
     * @param columnValue 列内容
     * @return List<String>
     */
    public static List<String> stringToList(String columnValue) {
        // 将字符串数组转化为List<String>
        String[] cValue = columnValue.split(CommonConstant.SPLITSEPARATOR);
        return stringToList(cValue);
    }
    
    /**
     * 将String数组转为 List<String> 
     * @param cValue string数组
     * @return List<String>
     */
    public static List<String> stringToList(String... cValue) {
        List<String> lsList = new ArrayList<String>() ;
        for (int i = 0; i < cValue.length; i++) {
            lsList.add(cValue[i]);
        }
        return lsList;
    }
    
    /**
     * 日志记录类
     * @param msg 错误信息
     * @param e   异常类
     * @return  
     * @throws
     * @author raofh
     * @date 2016-1-16
     */
    public static void logRecord(String msg, Exception e) {
        LOGGER.error(msg, e);
    }
 
    /**
     * 返回错误信息的JSON字符串拼接方法
     * @param success    方法是否返回成功
     * @param total     返回的总行数,只有在分页查询时才会使用
     * @param rows        返回的行信息,分页/不分页查询时使用
     * @param msg        返回的错误信息,用于在客户端进行提示
     *  -1 : 后台出现异常,请联系系统管理员
     *  0 : 操作成功!
     *  1 : 没有查询到数据窗口的配置信息,请联系系统管理员!    --   datawindowFactor == null
     *  2 : 传入参数为空或者格式错误,请联系系统管理员!
     *  3 : 执行结果有错误 
     *  4 : 数据删除成功
     *  5 : 数据删除失败,没有找到对应的数据行!
     *  6 : 数据更新成功
     *  7 : 数据添加成功
     *  8 : 数据窗口配置出错,请联系系统管理员!
     *  9 : 上传失败,文件过大或者网络过慢,请重新进行上传!
     *  10 : 查询结果为空,请进行维护!
     *  11 : 数据窗口配置信息中更新的表名称为空,请联系系统管理员!
     *  12 : 插入文件失败!
     *  13 : 生成语句失败
     *  14　：数据窗口配置信息未配置查询语句,请联系系统管理员!
     *  15 : 传入参数不是有效的JSON字符串
     *  16 : 数据更新失败,没有找到对应的数据行!
     *  17 : 用户名不能为空,请进行输入!
     *  18 : 密码不能为空,请进行输入!
     *  19 : 当前用户状态为[删除],无法进行登陆!
     *  20 : 导入失败，导入文件格式出错!
     *  21 : 参数中主键不存在,请联系系统管理员(PKID列不存在)
     *  22 : 数据保存失败!
     *  23 : 请先维护帐套信息!
     *  24 : 公共数据窗不允许删除!
     *  25 : 数据窗并非本人私有,请联系系统管理员!
     *  26 : 没有查询到结果信息!
     *  27 : 用户信息查询为空,请先进行维护!
     * @param  operationType 操作类型
     *  select
     *  add
     *  update
     *  delete
     *  error
     *  exception
     *  upload
     * @return String
     */
    @SuppressWarnings("static-access")
    public static String getResponseJson(boolean success, int total, Object rows, 
            String msg, String operationType) {
        JSONObject.DEFFAULT_DATE_FORMAT="yyyy-MM-dd HH:mm:ss";//设置日期格式
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", success);
        jsonObject.put("total", total);
        jsonObject.put("rows", rows);
        jsonObject.put("msg", getErrorMsg(msg));
        jsonObject.put("operationType", operationType);
        return jsonObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue,SerializerFeature.WriteDateUseDateFormat);
    }
    
    /**
     * 返回 错误信息的文字描述
     * @param msg 错误值
     * @return String 返回的信息
     */
    public static String getErrorMsg(String msg) {
    	//特别针对oracle抛出的错误进行修改
       return msg;
    }

    /**
     * 生成 字段-值 映射Map
     * @param whereJson 前端传入的字符串
     * {"datafield" : "字段别名", "value" : "值"},{"datafield" : "字段别名", "value" : "值"}
     * @return  Map<String, String>  字段 及 值的映射关系
     */
    public static Map<String, String> createFieldsValueMap(String whereJson) {
        JSONArray jsonArray = JSONArray.parseArray(whereJson);
        return createFieldsValueMap(jsonArray);
    }
    
    /**
     * 生成 字段-值 映射Map
     * @param jsonArray JSON数组
     * {"datafield" : "字段别名", "value" : "值"},{"datafield" : "字段别名", "value" : "值"}
     * @return  Map<String, String>  字段 及 值的映射关系
     */
    public static Map<String, String> createFieldsValueMap(JSONArray jsonArray) {
        Map<String, String> filedMapValue = new HashMap<String, String>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String datafield = jsonObject.getString("datafield");
            if (StringUtil.isBlank(datafield)) {
                throw new AppException(CommonMsgOutput.getErrorMsg("2"));
            }
            
            String value = jsonObject.getString("value");
            if (StringUtil.isBlank(value)) {
                value = "";
            }
            filedMapValue.put(datafield, value);
        }
        return filedMapValue;
    }
    
    
    /**
     * 当文本值为空时,抛出异常信息
     * @param fieldValue 字段值
     * @param msg 信息
     */
    public static void throwIsBlankAppEx(String fieldValue, String msg) {
        if (StringUtil.isBlank(fieldValue)) {
            throw new AppException(getErrorMsg(msg));
        }
    }

}
