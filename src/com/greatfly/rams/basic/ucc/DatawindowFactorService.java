package com.greatfly.rams.basic.ucc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.greatfly.common.dao.impl.JdbcBaseDao;
import com.greatfly.common.service.impl.BaseService;
import com.greatfly.common.util.GlobalUtil;
import com.greatfly.common.util.PageSupport;
import com.greatfly.common.util.StringUtil;
import com.greatfly.rams.basic.dao.MdDatawindowFactorDao;
import com.greatfly.rams.basic.domain.MdDatawindowFactor;
import com.greatfly.rams.basic.vo.DatawindowFactorVo;

/**
 * 数据窗配置ucc
 * @author huangqb
 * 2017-01-12 15:47:18
 */
@SuppressWarnings("unchecked")
@Service
public class DatawindowFactorService  extends BaseService<MdDatawindowFactor, Long> {
    
    @Resource
    private JdbcBaseDao jdBaseDao;
    
    @Resource
    public void setDDatawindowFactorDao(MdDatawindowFactorDao mdDatawindowFactorDao) {
        this.baseDao = mdDatawindowFactorDao;
    }

    /**
     * @Title: getDatawindowFactors 获取用户所能查看到数据窗口配置信息
     * @param dwName 数据窗口名称
     * @param pcode 用户工号
     * @param pcType 客货类型
     * @return List<MdDatawindowFactor>
     */
    public List<MdDatawindowFactor> getDatawindowFactors(String dwName) {
        List<MdDatawindowFactor> dataWindowList = null;
        try {
            List<String> userCodeList = new ArrayList<String>();
            userCodeList.add(GlobalUtil.getUser().getPcode());
            userCodeList.add("***");
            Map<String, Object> map = new HashMap<String, Object>();
            String hql = "from MdDatawindowFactor as dwinfo " 
                    + "where dwinfo.dwName=:dwName and dwinfo.pcType=:pcType " 
                    + " and dwinfo.applyUser in (:applyUser)";
            map.put("dwName", dwName);
            map.put("pcType", GlobalUtil.getUser().getPcType());
            map.put("applyUser", userCodeList);

            dataWindowList = find(hql, map);
 
        } catch (Exception e) {
            throw e;
        }
        return dataWindowList;
    }
    
    /**
     * @Title: getCurDatawindowFactor 获取用户当前的数据窗配置（优先私有配置，然后是公共配置）
     * @param dwName 数据窗口名称
     * @param pcode 用户工号
     * @param pcType 客货类型
     * @return MdDatawindowFactor 
     */
    public MdDatawindowFactor getCurDatawindowFactor(String dwName) {
        MdDatawindowFactor curDwfactor = null;
        try {
        	String pcode = GlobalUtil.getUser().getPcode();
            List<MdDatawindowFactor> dwfactorList = getDatawindowFactors(dwName);
            int count = dwfactorList.size();
            if (count == 1) {
                curDwfactor = dwfactorList.get(0);
            } else if (count > 1) {
                for (int i = 0; i < dwfactorList.size(); i++) {
                    MdDatawindowFactor dwfactor = dwfactorList.get(i);
                    if (dwfactor.getApplyUser() == pcode || i == dwfactorList.size()) {
                        curDwfactor = dwfactor;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return curDwfactor;
    }

    /**
     * @Title: resolveDwfactor 解析数据窗要素
     * @param dwfactor 数据窗对象
     * @return 返回grid需要的信息
     */
    public JSONObject resolveDwfactor(MdDatawindowFactor dwfactor) {
        JSONObject gridInfos = new JSONObject();
        JSONArray gridcols = new JSONArray();
        JSONArray hidecols = new JSONArray();
        //将数据窗中字段值所有都转成JSON格式数据
        String colInfos = getdwcolumn_info1_10(dwfactor);
        String disCols = dwfactor.getDisColumn();
        String hideCols = dwfactor.getHideColumn();
        String notNullCols = StringUtil.getSubString(dwfactor.getDwInfo(), "NOTNULL");
        String indexCols = StringUtil.getSubString(dwfactor.getDwInfo(), "INDEX");
        String prmCols = dwfactor.getPrmColumn();
        JSONObject colObject = null;
        //获取显示列信息
        for (int i = 1; i < 200; i++) {
            String colName = StringUtil.getSubString(disCols, String.valueOf(i)); //获取显示列的别名
            if (colName.isEmpty()) {
                break;
            }
            String colInfo = StringUtil.getSubString(colInfos, colName);
            colObject = new JSONObject();
            if (notNullCols.indexOf("<" + colName + ">") >= 0) {
                //表示此列为非空字段
                colObject.put("notnull", true);
            }
            if (indexCols.indexOf("<" + colName + ">") >= 0) {
                //表示此列为索引列
                colObject.put("isIndex", true);
            }
            String cnName = StringUtil.getSubString(colInfo, "CN"); //获取列的中文对照
            //String dbName = StringUtil.getSubString(colInfo, "DB"); //数据库列名
            String showType = StringUtil.getSubString(colInfo, "ST").toUpperCase(); //获取列的显示类型，为TXT或CMB
            String comboxValue = StringUtil.getSubString(colInfo, "VL"); //值,用于初始化下拉列表
            String dataType = StringUtil.getSubString(colInfo, "DT").toUpperCase(); //数据格式：CHAR\DATE\NUM
            String format = StringUtil.getSubString(colInfo, "FM").toUpperCase(); //显示格式,主要用于日期格式设置、数字保留位数设置
            String dataLen = StringUtil.getSubString(colInfo, "DL"); //数据长度
            String width = StringUtil.getSubString(colInfo, "WD"); //界面显示的列宽
            String editable = StringUtil.getSubString(colInfo, "ED"); //是否可编辑
            //String limit = StringUtil.getSubString(colInfo, "LT"); //数据长度限制
            colObject.put("text", cnName);
            colObject.put("datafield", colName);
            if (width.isEmpty()) {
                width = "60";
            }
            colObject.put("width", Integer.parseInt(width) + "px");
            if ("NO".equals(editable)) {
                colObject.put("editable", false);
            } else {
                colObject.put("editable", true);
            }
            if (Integer.parseInt(dataLen) > 0) { //dataLen = 0表示没限制长度，如数值、日期，主要用于做校验
                colObject.put("dataLength", Integer.parseInt(dataLen));
            }
            if ("CMB".equals(showType)) {
                //需要获取下拉列表的值
                List<Map<String, Object>> comboxList = getDropDownListForGrid(comboxValue);
                if (comboxList.size() == 0) {
                    colObject.put("columntype", "textbox");
                } else {
                    colObject.put("columntype", "dropdownlist");
                    colObject.put("comboxList", comboxList);
                }
            } else {
                if ("NUM".equals(dataType)) {
                    colObject.put("columntype", "numberinput");
                    colObject.put("groupable", false);
                    int decDigit = 0;
                    if (format.endsWith("%")) {
                        if (format.indexOf('.') >= 0) {
                            decDigit = format.length() - format.indexOf('.') - 2;
                        }
                        colObject.put("cellsformat", "p" + decDigit);
                    } else {
                        if (format.indexOf('.') >= 0) {
                            decDigit = format.length() - format.indexOf('.') - 1;
                        }
                        colObject.put("cellsformat", "d" + decDigit);
                    }
                } else if ("DATE".equals(dataType)) {
                    colObject.put("columntype", "datetimeinput");
                    switch (format.replace("/", "-")) {
                        case "YYYY-MM-DD":
                            colObject.put("cellsformat", "yyyy-MM-dd");
                            break;
                        case "YYYY-MM-DD HH24":
                            colObject.put("cellsformat", "yyyy-MM-dd HH");
                            break;
                        case "YYYY-MM-DD HH24:MI":
                            colObject.put("cellsformat", "yyyy-MM-dd HH:mm");
                            break;
                        case "YYYY-MM-DD HH24:MI:SS":
                            colObject.put("cellsformat", "yyyy-MM-dd HH:mm:ss");
                            break;
                        default:
                            colObject.put("cellsformat", "yyyy-MM-dd HH:mm:ss");
                            break;
                    }
                } else {
                    colObject.put("columntype", "textbox");
                }
            }
            gridcols.add(colObject);
        }
        //获取隐藏列信息（只需要列中文名、别名）
        JSONObject hidColObject = null;
        for (int j = 1; j < 200; j++) {
            String colName = StringUtil.getSubString(hideCols, String.valueOf(j)); //获取显示列的别名
            if (colName.isEmpty()) {
                break;
            }
            String colInfo = StringUtil.getSubString(colInfos, colName);
            hidColObject = new JSONObject();
            String cnName = StringUtil.getSubString(colInfo, "CN"); //获取列的中文对照
            String width = StringUtil.getSubString(colInfo, "WD"); //界面显示的列宽
            if (cnName.isEmpty()) {
                cnName = colName;
            }
            if (width.isEmpty()) {
                width = "60";
            }
            hidColObject.put("text", cnName);
            hidColObject.put("datafield", colName);
            hidColObject.put("width", Integer.parseInt(width) + "px");
            hidecols.add(hidColObject);
        }
        gridInfos.put("disColumns", gridcols);
        gridInfos.put("hideColumns", hidecols);
        gridInfos.put("prmColumns", prmCols);
        return gridInfos;
    }
    
    /** 
     * @Title: getDropDownListForGrid 获取下拉列表
     * @prarm comboxVl 数据窗中下拉列表的值
     * @return 下拉列表
     */
    public List<Map<String, Object>> getDropDownListForGrid(String comboxVl) {
        String vlXMLSub = comboxVl.substring(0, 4);
        List<Map<String, Object>> listMaps = new ArrayList<Map<String, Object>>();
        if ("####".equals(vlXMLSub) || "****".equals(vlXMLSub)) {
            String tvalue = comboxVl.substring(4).trim();
            if (tvalue.endsWith("/")) {
                tvalue = tvalue.substring(0, tvalue.length() - 1);
            }
            if ("####".equals(vlXMLSub)) {
                listMaps = getDropDownList("_" + tvalue);
            } else {
                listMaps = jdBaseDao.queryForList(tvalue); 
            }
        } else {
            //    管理员  DBA/普通用户  COMMON/查询用户  QUERY/
            String[] vlArray = comboxVl.split("/");
            int vlCount = vlArray.length;
            Map<String, Object> tMap = null;
            for (int i = 0; i < vlCount; i++) {
                if (StringUtil.isNotBlank(vlArray[i])) {
                    String ts = vlArray[i].replace("\t", " ");
                    if (ts.length() > 0) {
                        int k = ts.indexOf(' ');
                        tMap =  new HashMap<String, Object>();
                        if (k >= 0) {
                            tMap.put("TEXT", ts.substring(0, k).trim());
                            tMap.put("VALUE", ts.substring(k + 1).trim());
                        } else {
                            tMap.put("TEXT", ts.trim());
                            tMap.put("VALUE", ts.trim());
                        }
                        listMaps.add(tMap); 
                    }
                }
           }
        }
        return listMaps;
    }
    
    /** 
     * @Title: getDropDownList 获取下拉列表
     * @param cmbType 下拉类型
     * @return 下拉列表
     */
    public List<Map<String, Object>> getDropDownList(String cmbType) {
        String tcmbType = cmbType;
        String sql = "";
        Map<String, String> values = new HashMap<String, String>();
        if (tcmbType.startsWith("_")) {
            tcmbType = tcmbType.substring(1);
            values.put("markCode", tcmbType);
            sql = "SELECT B_CODE VALUE,CODE_NAME TEXT FROM MD_CODE_LIST where mark_code = :markCode order by CURRLOCATION";
        } else if ("issue".equals(tcmbType)) {
            sql = "select B.CODE_NAME TEXT,B.B_CODE VALUE from md_code_list  b where b.mark_code='ISS_FLAG' AND B.B_CODE<>'N' order by CURRLOCATION ";
        } else if ("ticketCode".equals(tcmbType)) {
            sql = "select t.tic_code ticCode,t.tic_length ticLength ,t.b_tno tno, t.tic_name ticName from MD_TICKET_CODE  t";
        }
        return jdBaseDao.queryForList(sql, values);
    }

    /**
     * @Title: getdwcolumn_info1_10 获取数据窗口 column_info1 - column_info10 列的合并信息
     * @param dwfactor 数据窗信息
     * @return Document 数据窗配置信息中所有列的配置信息
     */
    public String getdwcolumn_info1_10(MdDatawindowFactor dwfactor) {
        return dwfactor.getColumnInfo1() 
            + ((dwfactor.getColumnInfo2() == null) ? "" : dwfactor.getColumnInfo2()) 
            + ((dwfactor.getColumnInfo3() == null) ? "" : dwfactor.getColumnInfo3()) 
            + ((dwfactor.getColumnInfo4() == null) ? "" : dwfactor.getColumnInfo4())
            + ((dwfactor.getColumnInfo5() == null) ? "" : dwfactor.getColumnInfo5()) 
            + ((dwfactor.getColumnInfo6() == null) ? "" : dwfactor.getColumnInfo6()) 
            + ((dwfactor.getColumnInfo7() == null) ? "" : dwfactor.getColumnInfo7())
            + ((dwfactor.getColumnInfo8() == null) ? "" : dwfactor.getColumnInfo8()) 
            + ((dwfactor.getColumnInfo9() == null) ? "" : dwfactor.getColumnInfo9()) 
            + ((dwfactor.getColumnInfo10() == null) ? "" : dwfactor.getColumnInfo10());
    }


    /**
     * @Title: getdwsqlStr 获取数据窗口 sql_str - sql_str1 列的合并信息
     * @param dwfactor 数据窗信息
     * @return Document 数据窗配置信息中原始查询语句
     */
    public String getdwsqlStr(MdDatawindowFactor dwfactor) {
        return dwfactor.getSqlStr() 
            + ((dwfactor.getSqlStr1() == null) ? "" : dwfactor.getSqlStr1());
    }

    /**
     * @Title: getSelectSqlStr 获取查询语句（不包含条件）
     * @param dwfactor 数据窗信息
     * @return StringBuffer 查询语句
     */
    public StringBuffer getSelectSqlStr(MdDatawindowFactor dwfactor) {
    	StringBuffer querySqlStr = new StringBuffer(1000);
        querySqlStr.append("SELECT ");
        String colInfos = getdwcolumn_info1_10(dwfactor);
        String disCols = dwfactor.getDisColumn();
        String notNullCols = StringUtil.getSubString(dwfactor.getDwInfo(), "NOTNULL");
        String colName = "";
        String dbName = "";
        String colInfo = "";
        //获取显示列信息
        for (int i = 1; i < 200; i++) {
            colName = StringUtil.getSubString(disCols, String.valueOf(i)); //获取显示列的别名
            if (colName.isEmpty()) {
                break;
            }
            colInfo = StringUtil.getSubString(colInfos, colName);
            dbName = StringUtil.getSubString(colInfo, "DB"); //数据库列名
            querySqlStr.append(dbName + " \"" + colName + "\",");
        }
        for (int j = 1; j < 200; j++) {
        	colName = StringUtil.getSubString(notNullCols, String.valueOf(j)); //获取非空列的别名
            if (colName.isEmpty()) {
                break;
            }
            if (disCols.indexOf(">" + colName + "<") >= 0) {
                continue;
            }
            colInfo = StringUtil.getSubString(colInfos, colName);
            dbName = StringUtil.getSubString(colInfo, "DB"); //数据库列名
            querySqlStr.append(dbName + " \"" + colName + "\",");
        }
    	querySqlStr.deleteCharAt(querySqlStr.lastIndexOf(","));
    	return querySqlStr;
    }
    
    /**
     * @Title: getWhereCondStr 获取查询where条件
     * @param dwfactor 数据窗信息
     * @param datawindowFactorVo 查询信息
     * @param values 查询参数值
     * @return String 查询where条件
     */
    public String getWhereCondStr(MdDatawindowFactor dwfactor,DatawindowFactorVo datawindowFactorVo, Map<String, Object> values) {	
    	String whereCondStr = "";
        String sqlStr = getdwsqlStr(dwfactor);
        String colInfos = getdwcolumn_info1_10(dwfactor);
        String whereJson = datawindowFactorVo.getWhereJson();
        String filterJson = datawindowFactorVo.getFilterJson();
        String fromStr = sqlStr.substring(sqlStr.indexOf(" FROM ")).toUpperCase();
        //1、首先获取到FROM子句，将数据窗自带的条件做个转换
        fromStr = replaceDefaultCondition(fromStr);
        //2、截取数据窗自带的 GROUP BY 子句
        String groupOrOrderStr = "";
        if (fromStr.indexOf(" GROUP BY ") >= 0) {
            groupOrOrderStr = fromStr.substring(fromStr.indexOf(" GROUP BY "));
            fromStr = fromStr.substring(0, fromStr.indexOf(" GROUP BY "));
        } else if (fromStr.indexOf(" ORDER BY ") >= 0) {
            groupOrOrderStr = fromStr.substring(fromStr.indexOf(" ORDER BY "));
            fromStr = fromStr.substring(0, fromStr.indexOf(" ORDER BY "));
        }
        if (fromStr.indexOf(" WHERE ") < 0) {
            fromStr = fromStr + " WHERE 1=1 ";
        }
        //3、拼接界面默认的查询条件defaultJson
    	JSONObject jsonObject = JSONObject.parseObject(whereJson);

        String defaultJson = jsonObject.getString("defaultCond");
        if (StringUtil.isNotBlank(defaultJson)) {
            JSONArray defaultArray = JSONArray.parseArray(defaultJson);
            if (!defaultArray.isEmpty()) {
            	String defaultCond = "";
            	for (int i = 0; i < defaultArray.size(); i++) {
            		defaultCond += resolveQueryCond(defaultArray.getJSONObject(i), values, colInfos);
            	}
            	defaultCond = defaultCond.substring(defaultCond.indexOf(" "));
            	fromStr = fromStr + " AND (" + defaultCond + ") ";
            }
        }
        //4、拼接用户输入的查询条件customJson
        String customJson = jsonObject.getString("customCond");
        String customCond = "";
        if (StringUtil.isNotBlank(customJson)) {
            JSONArray customArray = JSONArray.parseArray(customJson);
            if (!customArray.isEmpty()) {
            	String rowCond = "";
            	for (int i = 0; i < customArray.size(); i++) {
            		JSONArray rowCondArry = (JSONArray) customArray.get(i);
            		for (int j =0; j < rowCondArry.size(); j++) {
            			rowCond += resolveQueryCond(rowCondArry.getJSONObject(j), values, colInfos);
            		}
            		rowCond = rowCond.substring(rowCond.indexOf(" "));
            		if (i == 0) {
            			customCond = " (" + rowCond + ") ";
            		} else {
                		customCond += " OR (" + rowCond + ") ";
            		}
            		rowCond = "";
            	}
            	fromStr = fromStr + " AND (" + customCond + ") ";
            }
        }
    	//5、拼接用户界面交互的过滤和排序语句
        //过滤
    	String filterCond = "";
        if (StringUtil.isNotBlank(filterJson)) {
        	JSONArray filterArry = JSONArray.parseArray(filterJson);
            if (!filterArry.isEmpty()) {
            	String colCond = "";
            	for (int i = 0; i < filterArry.size(); i++) {
            		JSONArray colCondArry = (JSONArray) filterArry.get(i);
            		for (int j =0; j < colCondArry.size(); j++) {
            			colCond += resolveQueryCond(colCondArry.getJSONObject(j), values, colInfos);
            		}
            		colCond = colCond.substring(colCond.indexOf(" "));
            		if (i == 0) {
            			filterCond = " (" + colCond + ") ";
            		} else {
            			filterCond += " AND (" + colCond + ") ";
            		}
            		colCond = "";
            	}
            	fromStr = fromStr + " AND (" + filterCond + ") ";
            }
        }
    	
        //排序
        String sortField = datawindowFactorVo.getSortdatafield();
    	String sortOrder = datawindowFactorVo.getSortorder();
        if (StringUtil.isNotBlank(sortField) && StringUtil.isNotBlank(sortOrder)) {
            String colInfo = StringUtil.getSubString(colInfos, sortField);
            if (StringUtil.isBlank(colInfo) && sortField.endsWith("TXT")) {
            	sortField = sortField.substring(0, sortField.length() - 3);
            	colInfo = StringUtil.getSubString(colInfos, sortField);
            }
            String sortFieldDbName = StringUtil.getSubString(colInfo, "DB"); //数据库列名;
            if (StringUtil.isNotBlank(sortFieldDbName)) {
            	if (groupOrOrderStr.indexOf(" ORDER BY ") >= 0) {
            		int index = groupOrOrderStr.indexOf(" ORDER BY ") + 10;
            		groupOrOrderStr = groupOrOrderStr.substring(0, index) + sortFieldDbName + " " + sortOrder + ", " + groupOrOrderStr.substring(index);
            	} else {
            		groupOrOrderStr = groupOrOrderStr + " ORDER BY " + sortFieldDbName + " " + sortOrder;
            	}
            }
        }
        //6、拼接最终的where子句
        whereCondStr = fromStr + groupOrOrderStr;
    	return whereCondStr;
    }

    /**
     * 根据查询语句和参数值查询分页数据
     * @param querySql 数据窗口实体vo
     * @param values 查询参数值
     * @param ps 分页信息
     * @throws Exception 抛出异常方法给上层调用方法
     */
    @SuppressWarnings({ "rawtypes" })
    public void getPagingDataList(String querySql, Map<String, Object> values,PageSupport ps) throws Exception {
        try {
            jdBaseDao.queryForList(querySql, values, ps);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 根据查询语句和参数值查询分页数据
     * @param querySql 数据窗口实体vo
     * @param values 查询参数值
     * @return List 数据集
     * @throws Exception 抛出异常方法给上层调用方法
     */
    public List<Map<String, Object>> getNoPagingDataList(String querySql, Map<String, Object> values) throws Exception {
        try {
            List<Map<String, Object>> listMap = jdBaseDao.queryForList(querySql, values);
            return listMap;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 将json查询对象转换为字符串
     * @param condObj 查询条件
     * @param values 记录参数值
     * @param colInfos 列信息
     * @return String 返回替换后的内容
     */
    public String resolveQueryCond(JSONObject condObj,  Map<String, Object> values, String colInfos) {
    	String rusltStr = "";
    	String colName = condObj.getString("colName");//别名
    	String colOperator = condObj.getString("colOperator");//操作符
    	String colVal = condObj.getString("colVal");//列值
    	String colType = condObj.getString("colType");//列类型 CHAR、DATE
    	String colRelate = condObj.getString("colRelate");//连接符
        String colInfo = StringUtil.getSubString(colInfos, colName);
    	String dbName = StringUtil.getSubString(colInfo, "DB"); //数据库列名
    	int[] random = StringUtil.randomCommon(1, 50, 1); //获取一个随机数
        if ("NVL".equals(colOperator)) {
        	rusltStr = colRelate + " NVL(" + dbName + ",:" + colName + random[0] + ") = :" + colName + random[0];
        	values.put(colName + random[0], colVal);
        } else if ("INSTR".equals(colOperator)) {
        	rusltStr = colRelate + " INSTR(:" + colName + random[0] + ",','||" + dbName + "||',') > 0 ";
        	values.put(colName + random[0], colVal);
        } else {
            if ("IS NULL".equals(colOperator) || "IS NOT NULL".equals(colOperator)) {
            	rusltStr = colRelate + " " + dbName + " " + colOperator + " ";
            } else if ("IN".equals(colOperator) || "NOT IN".equals(colOperator)) {
            	rusltStr = colRelate + " " + dbName + " " + colOperator +  " (:" + colName + random[0] + ") ";
            	String[] aList = colVal.split(",");
            	List<String> lsList = new ArrayList<String>() ;
                for (int i = 0; i < aList.length; i++) {
                    lsList.add(aList[i]);
                }
	        	values.put(colName + random[0], lsList);
            } else {
	        	if ("DATE".equals(colType)) {
	        		rusltStr = colRelate + " " + dbName + " " + colOperator +  " TO_DATE(:" + colName + random[0];
	        		if (colVal.indexOf('-') > 0) {
	        			rusltStr = rusltStr + ",'yyyy-MM-dd') ";
	        		} else if (colVal.indexOf('/') > 0) {
	        			rusltStr = rusltStr + ",'yyyy-MM-dd') ";
	        		} else {
	        			rusltStr = rusltStr + ",'yyyyMMdd') ";
	        		}
	        	} else {
	            	rusltStr = colRelate + " " + dbName + " " + colOperator +  " :" + colName + random[0] + " ";
	        	}
	        	values.put(colName + random[0], colVal);
            }
        }
    	return rusltStr;
    	
    }
    
    /**
     * 处理从数据配置表中获取到的表名的后缀,比如后缀了一些条件,那么就要进行替换
     * @param fromStr 从数据窗信息中获取到的from子句(可能含有WEHRE条件)
     *      1.账套-ID_CODE = '#######'
     *      2.国际国内-INSTR('*######',STAT_CODE)>0 
     *      3.客货标志-PC_TYPE='**#####'
     *      4.角色代码-ROLE_CODE='***####'
     *      5.用户代码-LOGIN_CODE='****###'
     * @return String 返回替换后的内容
     */
    public String replaceDefaultCondition(String fromStr) {
        String resultStr = fromStr;
        if (fromStr.indexOf("#######") >= 0) {
            resultStr = resultStr.replace("#######", GlobalUtil.getUser().getIdCode());
        } 
        if (fromStr.indexOf("*######") >= 0) {
            resultStr = resultStr.replace("*######", GlobalUtil.getUser().getStatCode());
        } 
        if (fromStr.indexOf("**#####") >= 0) {
            resultStr = resultStr.replace("**#####", "P");
        }
        if (fromStr.indexOf("***####") >= 0) {
            resultStr = resultStr.replace("***####", GlobalUtil.getUser().getPcode());
        }
        if (fromStr.indexOf("****###") >= 0) {
            resultStr = resultStr.replace("****#####", GlobalUtil.getUser().getPcode());
        }
        return resultStr;
    }
}