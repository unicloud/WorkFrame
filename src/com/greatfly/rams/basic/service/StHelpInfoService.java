package com.greatfly.rams.basic.service;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.greatfly.common.dao.impl.JdbcBaseDao;
import com.greatfly.common.util.GlobalUtil;
import com.greatfly.common.util.msgconverter.CommonMsgOutput;

/**
 * 帮助信息service
 * @author huangqb
 * 2017-07-21 09:05:57
 */
@Service
public class StHelpInfoService {
    
    private static Logger logger = Logger.getLogger(StHelpInfoService.class);
    
    @Resource
    private JdbcBaseDao jdBaseDao;
    
    /**
     * 获取页面对应的帮助信息
     * @param pageUrl 页面url
     * @return String 返回结果JSON
     */
    public String getPageHelpInfo(String pageUrl) {
        DataSource ds = null;
        Connection conn = null;
        PreparedStatement queryPstmt = null;
        ResultSet rs = null;
        String querySql = "SELECT HELP_INFO FROM ST_HELP_INFO WHERE PAGE_URL = ? AND ID_CODE = ?";
        String resultStr = "";
        try {
            ds = jdBaseDao.getJdbcTemplate().getDataSource();
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            queryPstmt = conn.prepareStatement(querySql);
            queryPstmt.setString(1, pageUrl);
            queryPstmt.setString(2, GlobalUtil.getUser().getIdCode());
            rs = queryPstmt.executeQuery();
            Blob infoBlob = null;
            while (rs.next()) {
                infoBlob = rs.getBlob("HELP_INFO");
            }
            int infoLength = (int) infoBlob.length();
            byte[] buffer = infoBlob.getBytes(1, infoLength);
            resultStr = new String(buffer);
        } catch (Exception e) {
            resultStr = "此页面尚未维护帮助信息！";
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                } 
            } catch (Exception e2) {
                logger.error("getPageHelpInfo()释放 rs 失败!");
            }
            try {
                if (queryPstmt != null) {
                    queryPstmt.close();
                }
            } catch (Exception e2) {
                logger.error("getPageHelpInfo()释放 queryPstmt 失败!");
            }
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (Exception e2) {
                logger.error("getPageHelpInfo()释放 conn 失败!");
            }
        }
        return CommonMsgOutput.getResponseJson(true, 0, "", resultStr, "query");
    }
    
    /**
     * 保存页面帮助信息
     * @param pageUrl 页面url
     * @param pageName 页面名称
     * @param helpInfo 帮助信息
     * @return String 返回结果JSON
     */
    public String savePageHelpInfo(String pageUrl,String pageName,String helpInfo) {
        DataSource ds = null;
        Connection conn = null;
        PreparedStatement updatePstmt = null;
        PreparedStatement insertPstmt = null;
        String querySql = "SELECT COUNT(*) FROM ST_HELP_INFO WHERE ID_CODE = ? AND PAGE_URL = ?";
        String insertSql = "INSERT INTO ST_HELP_INFO (PKID,PAGE_URL,PAGE_NAME,HELP_INFO,ID_CODE," 
                        + "CREATE_USER,CREATE_TIME,MODIFY_USER,MODIFY_TIME) VALUES " 
                        + "(fun_get_comm_pkid('ST_HELP_INFO'),?,?,?,?,?,sysdate,?,sysdate)";
        String updateSql = "UPDATE ST_HELP_INFO SET PAGE_NAME=?, HELP_INFO=?,MODIFY_USER=?,MODIFY_TIME=sysdate WHERE ID_CODE=? AND PAGE_URL=?";

        try {
            ds = jdBaseDao.getJdbcTemplate().getDataSource();
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            Object[] objArr = new Object[2];
            objArr[0] = GlobalUtil.getUser().getIdCode();
            objArr[1] = pageUrl;
            int count = jdBaseDao.queryForInt(querySql,objArr);
            if (count >= 1) {
                updatePstmt = conn.prepareStatement(updateSql);
                updatePstmt.setString(1, pageName);
                updatePstmt.setBlob(2, new ByteArrayInputStream(helpInfo.getBytes()));
                updatePstmt.setString(3, GlobalUtil.getUser().getPcode());
                updatePstmt.setString(4, GlobalUtil.getUser().getIdCode());
                updatePstmt.setString(5, pageUrl);
                updatePstmt.execute();
                conn.commit();
            } else {
                insertPstmt = conn.prepareStatement(insertSql);
                insertPstmt.setString(1, pageUrl);
                insertPstmt.setString(2, pageName);
                insertPstmt.setBlob(3, new ByteArrayInputStream(helpInfo.getBytes()));
                insertPstmt.setString(4, GlobalUtil.getUser().getIdCode());
                insertPstmt.setString(5, GlobalUtil.getUser().getPcode());
                insertPstmt.setString(6, GlobalUtil.getUser().getPcode());
                insertPstmt.execute();
                conn.commit();
            }
            return CommonMsgOutput.getResponseJson(true, 0, 0, "OK", "update");
        } catch (Exception e) {
            return CommonMsgOutput.getResponseJson(false, 0, 0, e.getMessage(), "upload");
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                } 
            } catch (Exception e2) {
                logger.error("savePageHelpInfo() 释放 conn 失败!");
            }
            try {
                if (insertPstmt != null) {
                    insertPstmt.close();
                }
            } catch (Exception e2) {
                logger.error("savePageHelpInfo() 释放 insertPstmt 失败!");
            }
            try {
                if (updatePstmt != null) {
                    updatePstmt.close();
                }
            } catch (Exception e2) {
                logger.error("savePageHelpInfo() 释放 updatePstmt 失败!");
            }
        }
    }
}