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
import com.greatfly.common.util.StringUtil;
import com.greatfly.rams.basic.dao.MdDatawindowFactorDao;
import com.greatfly.rams.basic.domain.MdDatawindowFactor;

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
        Map<String, Object> tMap = new HashMap<String, Object>();
        tMap.put("TEXT", "");
        tMap.put("VALUE", "");
        listMaps.add(tMap);
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
}    
