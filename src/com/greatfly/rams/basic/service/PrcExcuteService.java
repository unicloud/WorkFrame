package com.greatfly.rams.basic.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.greatfly.common.dao.impl.JdbcBaseDao;
import com.greatfly.common.service.impl.BaseService;

/**
 * 过程调用统一接口服务类
 * @ClassName: DDatawindowFactorService
 * @author QiBin
 * @date 2016-3-11 上午10:17:52
 */
@Service
public class PrcExcuteService extends BaseService<Object, Long> {
    
    private static Logger logger = Logger.getLogger(PrcExcuteService.class);

    @Resource
    private JdbcBaseDao jdBaseDao;

//    @Resource
//    private DDatawindowFactorService datawindowFactorService;

    public JdbcBaseDao getJdBaseDao() {
        return jdBaseDao;
    }

    public void setJdBaseDao(JdbcBaseDao jdBaseDao) {
        this.jdBaseDao = jdBaseDao;
    }

    /**
     * @Title: ExcutePrc 执行存储过程
     * @param prcName 存储过程名称
     * @param args 过程参数对应的值
     * @param conn 数据库连接
     * @return 执行过程的结果字符串
     * @throws SQLException 
     */
    public String ExcutePrc(String prcName, String args, Connection conn) throws SQLException {
        Connection tConnection = conn;
        Boolean innerCreate = false;
        if (tConnection == null) {
            DataSource ds = jdBaseDao.getJdbcTemplate().getDataSource();
            try {
                //设置为true,表示是这个方法自己创建的conn,需要在这个方法中自己关闭连接。否则不关闭
                innerCreate = true;
                tConnection = ds.getConnection();                
            } catch (SQLException e) {
                return e.getMessage();
            }
        }
        //存储过程执行语句
        StringBuffer executeStatement = new StringBuffer();
        //参数值JSON字符串
        JSONArray argArray = JSONArray.parseArray(args);
        //输出参数所在位置(支持小于等于2个输出参数)
        int outParamPosition = -1;
        int outParamPosition2 = -1;
        
        CallableStatement cs = null;
        try {
            executeStatement.append("{call ");
            executeStatement.append(prcName);
            executeStatement.append("(");
            for (int i = 1; i < argArray.size(); i++) {
                executeStatement.append("?,");
            }
            executeStatement.append("?");
            executeStatement.append(")}");
            String storedProc = executeStatement.toString();
            cs = tConnection.prepareCall(storedProc);
            for (int i = 0; i < argArray.size(); i++) {
                JSONObject o = argArray.getJSONObject(i);
                if (o.get("type").equals("String")) {
                    String valueString = (String) o.get("val");
                    valueString = valueString.replace("&^", "'");
                    cs.setString(i + 1, valueString);
                } else if (o.get("type").equals("int")) {
                    cs.setInt(i + 1, (int) o.getIntValue("val"));
                } else if (o.get("type").equals("date")) {
                    Date date = Date.valueOf(o.getString("val"));
                    cs.setDate(i + 1, date);
                } else if (o.get("type").equals("decimal")) {
                    cs.setBigDecimal(i + 1, o.getBigDecimal("val"));
                } else if (o.get("type").equals("outString")) {
                    cs.registerOutParameter(i + 1, Types.VARCHAR);
                    if (outParamPosition < 0) {
                        outParamPosition = i + 1;
                    } else {
                        outParamPosition2 = i + 1;
                    }
                }
            }
            cs.execute();
            if (outParamPosition < 0) {
                //表示存储过程中没有输出参数,直接返回OK
                return "OK";
            } else {
                String resultString = cs.getString(outParamPosition);
                if (outParamPosition2 > 0) {
                    resultString += "||" + cs.getString(outParamPosition2);
                }
                return resultString;
            }
        } catch (Exception e) {
            return e.getMessage();
        } finally {
            try {
                if (cs != null && !cs.isClosed()) {
                    cs.close();
                }
            } catch (Exception e2) {
                logger.error("ExcutePrc()方法释放 cs 失败!");
            }
            try {
                if (tConnection != null && !tConnection.isClosed() && innerCreate) {
                    tConnection.close();
                }
            } catch (Exception e2) {
                logger.error("ExcutePrc()方法释放 tConnection 失败!");
            }
            
        }
    }

    /**
     * @Title: ExcutePrcDs 执行存储过程，并在同一事务中执行查询语句
     * @param prcName 存储过程名称
     * @param prcArgs 过程参数对应的值
     * @param datawindowFactorVo 查询实体
     * @return 查询结果集合jsonObject
     * @throws Exception 
     */
    public String ExcutePrcDs(String prcName, String prcArgs) throws Exception {
        DataSource ds = null;
        Connection conn = null;
        String jsonObject = null;
        try {
            ds = jdBaseDao.getJdbcTemplate().getDataSource();
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            jsonObject = ExcutePrc(prcName, prcArgs, conn);
            if ("OK".equals(jsonObject)) {
//                List<Map<String, Object>> listMap = datawindowFactorService.getNoPagingTable(datawindowFactorVo);
//                jsonObject =  CommonMethodUtil.getResponseJson(true, 0, listMap, "0", "select");  
            }
            conn.commit();
        } catch (Exception e) {
            if (conn != null && !conn.isClosed()) {
                conn.rollback();
            }
            logger.error("ExcutePrcDs方法出现异常:", e);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (Exception e2) {
                logger.error("ExcutePrcDs()释放 conn 失败!");
            }
        }
        return jsonObject;
    }

}