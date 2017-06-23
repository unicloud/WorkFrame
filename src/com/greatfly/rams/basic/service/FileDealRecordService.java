package com.greatfly.rams.basic.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.tools.ExtractText;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.greatfly.common.config.PublicProperty;
import com.greatfly.common.dao.impl.JdbcBaseDao;
import com.greatfly.common.service.impl.BaseService;
import com.greatfly.common.util.GlobalUtil;
import com.greatfly.common.util.file.FileUtil;
import com.greatfly.common.util.msgconverter.CommonMsgOutput;

/**
 * 过程调用统一接口服务类
 * 
 * @ClassName: PFileDealRecordService
 * @author QiBin
 * @date 2016-3-11 上午10:17:52
 */
@Service
public class FileDealRecordService extends BaseService<Object, Long> {
    
    private static Logger logger = Logger.getLogger(FileDealRecordService.class);

    @Resource
    private JdbcBaseDao jdBaseDao;

	@Resource
	private PrcExcuteService prcExcuteService;

    private String dataDir;
    
    static PublicProperty pubParameter = new PublicProperty();
    // 指定文件上传路径
    public static final String UPLOADFILEPATH = pubParameter.getValue("UPLOAD_PATH");

    public JdbcBaseDao getJdBaseDao() {
        return jdBaseDao;
    }

    public void setJdBaseDao(JdbcBaseDao jdBaseDao) {
        this.jdBaseDao = jdBaseDao;
    }

	public PrcExcuteService getPrcExcuteService() {
		return prcExcuteService;
	}

	public void setPrcExcuteService(PrcExcuteService prcExcuteService) {
		this.prcExcuteService = prcExcuteService;
	}
	
    public String getDataDir() {
        return dataDir;
    }

    public void setDataDir(String dataDir) {
        this.dataDir = dataDir;
    }    

    /**
     * Excel文件导入(java代码解析)
     * 
     * @param file
     *            文件流
     * @param fileType
     *            文件解析类型
     * @param fileName
     *            文件名称
     * @return String
     * @throws Exception
     *             异常抛出
     */
    public String execlImport(File file, String fileType, String fileName) throws Exception {
        String jsonString = "";
        Workbook workbook = null;
        if (fileName.toLowerCase().endsWith(".xlsx")) {
            workbook = new XSSFWorkbook(new FileInputStream(file));
        } else if (fileName.toLowerCase().endsWith(".xls")) {
            workbook = new HSSFWorkbook(new FileInputStream(file));
        }
        // 此处开始的代码需要重新规划
        if ("IATA_DATA".equals(fileType)) {
           //jsonString = parseIataData(workbook);
        } else {
            jsonString = CommonMsgOutput.getResponseJson(true, 0, file, "暂不支持此数据的导入！", "error");
        }
        return jsonString;
    }

    /**
     * Excel文件导入(过程解析)
     * 
     * @param file
     *            文件流
     * @param fileType
     *            文件解析类型
     * @param fileName
     *            文件名称
     * @param sessionId
     *            会话ID
     * @return String
     * @throws Exception
     *             异常抛出
     */
    public String execlImportInPrc(File file, String fileType, String fileName, String sessionId) throws Exception {
        Workbook workbook = null;
        FileInputStream fileInput = null;
        FileOutputStream fileOutput = null;
        OutputStreamWriter fwriter = null;
        File txtFile = null;
        try {
            fileInput = new FileInputStream(file);
            if (fileName.toLowerCase().endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(fileInput);
            } else if (fileName.toLowerCase().endsWith(".xls")) {
                workbook = new HSSFWorkbook(fileInput);
            } else {
                return CommonMsgOutput.getResponseJson(false, 0, 0, "不是Excel文件", "error");
            }
            Sheet sheet = workbook.getSheetAt(0); // 第一个sheet
            int rowNum = sheet.getLastRowNum();
            UUID uuid = UUID.randomUUID();
            txtFile = new File("..\\" + uuid + ".txt");
            boolean createBool = txtFile.createNewFile();
            if (!createBool) {
                logger.error("execlImportInPrc() 方法创建 txtFile 失败!");
            }
            fileOutput = new FileOutputStream(txtFile);
            fwriter = new OutputStreamWriter(fileOutput, "UTF-8");
            char[] charArr = new char[3];
            charArr[0] = (char) -17;
            charArr[1] = (char) -69;
            charArr[2] = (char) -65;
            fwriter.write(charArr);
            int nullRowCount = 0;
            for (int i = 0; i <= rowNum; i++) {
                StringBuffer sb = new StringBuffer();
                Row curRow = sheet.getRow(i);
                if (curRow == null) {
                    continue;
                }
                int colNumber = curRow.getLastCellNum();
                for (int j = 0; j < colNumber; j++) {
                    sb.append(getExcelCellValue(curRow.getCell(j)));
                    sb.append("\t");
                }
                sb.append("\n");
                if (sb.toString().trim().length() > 0) {
                    fwriter.write(sb.toString());
                } else {
                    nullRowCount++;
                    // 空行大于二十行时跳出
                    if (nullRowCount > 20) {
                        break;
                    }
                }
            }
            fwriter.flush();
            return txtImportInPrc(txtFile, fileType, fileName, sessionId);
        } catch (Exception e) {
            if (e.getMessage().contains("Invalid header signature")) {
                return txtImportInPrc(file, fileType, fileName, sessionId);
            }
            return CommonMsgOutput.getResponseJson(false, 0, 0, e.getMessage(), "error");
        } finally {
            if (fileInput != null) {
                fileInput.close();
            }
            if (fileOutput != null) {
                fileOutput.close();
            }
            if (fwriter != null) {
                fwriter.close();
            }
            if (txtFile != null && txtFile.exists()) {
                boolean deBool = txtFile.delete();
                if (!deBool) {
                    logger.error("execlImportInPrc() 方法删除 txtFile 失败!");
                }
            }
        }
    }

    /**
     * PDF文件导入(过程解析)
     * 
     * @param file
     *            文件流
     * @param fileType
     *            文件解析类型
     * @param fileName
     *            文件名称
     * @param sessionId
     *            会话ID
     * @return String
     */
    public String pdfImportInPrc(File file, String fileType, String fileName, String sessionId) {
        UUID uuid = UUID.randomUUID();
        String[] extractPrams = new String[4];
        File saveFile = null;
        File txtFile = null;
        try {
            saveFile = new File("..\\", fileName);
            boolean renameBool = file.renameTo(saveFile);
            if (!renameBool) {
                logger.error("pdfImportInPrc() 保存文件失败!");
            }
            extractPrams[0] = "-encoding";
            extractPrams[1] = "GBK";
            extractPrams[2] = saveFile.getPath();
            extractPrams[3] = "..\\" + uuid + ".txt";
            ExtractText.main(extractPrams);
            txtFile = new File("..\\" + uuid + ".txt");
            return txtImportInPrc(txtFile, fileType, fileName, sessionId);
        } catch (Exception e) {
            return CommonMsgOutput.getResponseJson(false, 0, 0, e.getMessage(), "error");
        } finally {
            // 删除saveFile txtFile
            if (saveFile != null && saveFile.exists()) {
                boolean saveBool = saveFile.delete();
                if (!saveBool) {
                    logger.error("pdfImportInPrc()方法删除 saveFile 文件失败!");
                }
            }
            if (txtFile != null && txtFile.exists()) {
                boolean deBool = txtFile.delete();
                if (!deBool) {
                    logger.error("pdfImportInPrc()方法删除 txtFile 文件失败!");
                }
            }
        }
    }

    /**
     * txt文件导入(过程解析)
     * @param file     文件流
     * @param fileType 文件解析类型
     * @param fileName 文件名称
     * @param sessionId    会话ID
     * @return String  返回字符串
     * @throws IOException 抛出异常
     * @throws SQLException 抛出异常
     */
    public String txtImportInPrc(File file, String fileType, final String fileName,
        final String sessionId) throws IOException, SQLException {
        String tabName = "";
        String prcName = "";
        String parseType = "";
        String parseChildType = "";
        DataSource ds = null;
        Connection conn = null;
        PreparedStatement queryPstmt = null;
        PreparedStatement delPstmt = null;
        PreparedStatement insertPstmt = null;
        ResultSet rs = null;
        InputStream f = null;
        File newFile = null;
        try {
            ds = jdBaseDao.getJdbcTemplate().getDataSource();
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            String querySql = "SELECT * FROM MD_FILE_DOWN_CONFIG WHERE FILE_TYPE=? AND ID_CODE=? AND ISS_CO=? AND ROWNUM=1";
            queryPstmt = conn.prepareStatement(querySql);
            // 1.先删除dm_blob_tmp表中的相关文件
            queryPstmt.setString(1, fileType);
            queryPstmt.setString(2, GlobalUtil.getUser().getIdCode());
            queryPstmt.setString(3, GlobalUtil.getUser().getCo3c());
            rs = queryPstmt.executeQuery();
            while (rs.next()) {
                prcName = rs.getString("PARSE_PRC_NAME");
                parseType = rs.getString("PARSE_TYPE");
                parseChildType = rs.getString("PARSE_CHILD_TYPE");
            }
            if (prcName == null || prcName.isEmpty()) {
                // 前台发送的文件类型--导入基础金额数据
                if ("BASE_AMOUNT".equals(fileType)) {
                    // 需要调用的过程名
                    prcName = "PRC_FILE_PARSE_FIRST";
                    // 解析类型
                    parseType = "PMP-BASE";
                    // 解析子类型
                    parseChildType = "PMP-BASE";
                } else if ("PMP_DATA".equals(fileType)) {
                    // 导入PMP数据
                    prcName = "PRC_FILE_PARSE_FIRST";
                    parseType = "PMP-FACTOR";
                    parseChildType = "PMP-FACTOR";
                } else if ("FDR".equals(fileType)) {
                    // 导入FDR数据(五天比价数据)
                    prcName = "PRC_FILE_PARSE_FIRST";
                    parseType = "FDR";
                    parseChildType = "FDR";
                } else if ("MMR".equals(fileType)) {
                    // 导入MMR数据(月平均数据)
                    prcName = "PRC_FILE_PARSE_FIRST";
                    parseType = "MMR";
                    parseChildType = "MMR";
                } else if ("ICER".equals(fileType)) {
                    // 导入ICER数据(ICER汇率)
                    prcName = "PRC_FILE_PARSE_FIRST";
                    parseType = "ICER";
                    parseChildType = "ICER";
                } else if ("ROE".equals(fileType)) {
                    // 导入ROE数据(ROE汇率)
                    prcName = "PRC_FILE_PARSE_FIRST";
                    parseType = "ROE";
                    parseChildType = "ROE";
                } else if ("ADDUP_OAL".equals(fileType)) {
                    // 导入外航承运数据（累计奖励模块）
                    prcName = "PRC_FILE_PARSE_THIRD";
                    parseType = "IPDATA";
                    parseChildType = "ADDUP_OAL";
                } else if ("AGENT_MIR".equals(fileType)) {
                    //导入BSP代理人监控数据
                    prcName = "PRC_FILE_PARSE_SECOND";
                    parseType = "EXCEL";
                    parseChildType = "";
                } else if ("ACC_SUBJECT".equals(fileType)) {
                    //导入账务科目对照数据
                    prcName = "PRC_FILE_PARSE_THIRD";
                    parseType = "IPDATA";
                    parseChildType = "ACC_SUB";
                } else if ("ATPCO_MAIN".equals(fileType)) {
                    //导入ATPCO主运价数据
                    prcName = "PRC_FILE_PARSE_THIRD";
                    parseType = "IPDATA";
                    parseChildType = "ATPCO_MAIN";
                } else if ("ATPCO_ADDON".equals(fileType)) {
                    //导入ATPCO——ADDON运价数据
                    prcName = "PRC_FILE_PARSE_THIRD";
                    parseType = "IPDATA";
                    parseChildType = "ATPCO_ADDON";
                } else if ("ATPCO_FC".equals(fileType)) {
                    //导入ATPCO_FC运价数据
                    prcName = "PRC_FILE_PARSE_THIRD";
                    parseType = "IPDATA";
                    parseChildType = "ATPCO_FC";
                } else if ("UATP".equals(fileType)) {
                    // 导入UATP数据
                    prcName = "PRC_FILE_PARSE_THIRD";
                    parseType = "IPDATA";
                    parseChildType = "UATP";
                } else if ("CARD".equals(fileType)) {
                    // 导入支付数据（信用卡管理-数据明细-支付数据）
                    prcName = "PRC_FILE_PARSE_THIRD";
                    parseType = "IPDATA";
                    parseChildType = "CARD";
                } else if ("SLIP".equals(fileType)) {
                    // 导入银行回单数据（网电管理-银行回单、信用卡管理-银行回单）
                    prcName = "PRC_FILE_PARSE_THIRD";
                    parseType = "IPDATA";
                    parseChildType = "SLIP";
                } else if ("ICCS".equals(fileType)) {
                    // 导入ICCS数据
                    prcName = "PRC_FILE_PARSE_THIRD";
                    parseType = "IPDATA";
                    parseChildType = "ICCS";
                } else if ("CARD_REFUSE".equals(fileType)) {
                    // 导入拒付数据
                    prcName = "PRC_FILE_PARSE_THIRD";
                    parseType = "IPDATA";
                    parseChildType = "CARD_REFUSE";
                } else if ("CARD_BILL".equals(fileType)) {
                    // 导入信用卡账单数据
                    prcName = "PRC_FILE_PARSE_THIRD";
                    parseType = "IPDATA";
                    parseChildType = "CARD_BILL";
                } else if ("BSP_BILL".equals(fileType)) {
                    // 导入BSP账单数据
                    prcName = "PRC_FILE_PARSE_THIRD";
                    parseType = "IPDATA";
                    parseChildType = "BSP_BILL";
                } else {
                    return CommonMsgOutput.getResponseJson(true, 0, 0, "未配置解析程序，请联系系统管理员！", "query");
                }
            }
            if ("PRC_FILE_PARSE_FIRST".equals(prcName)) {
                tabName = "dm_blob_tmp1";
            } else if ("PRC_FILE_PARSE_SECOND".equals(prcName)) {
                tabName = "dm_blob_tmp2";
            } else if ("PRC_FILE_PARSE_THIRD".equals(prcName)) {
                tabName = "dm_blob_tmp3";
            } else {
                return CommonMsgOutput.getResponseJson(true, 0, 0, "未知的解析程序" + prcName + "，请联系系统管理员！", "query");
            }
            String delSql = "DELETE FROM " + tabName + " WHERE USER_SESSION=? AND USER_CODE=? AND FILE_NAME=? AND ID_CODE=?";
            delPstmt = conn.prepareStatement(delSql);
            String insertSql = "INSERT INTO " + tabName + " (USER_SESSION, USER_CODE,FILE_NAME,SERIAL_NO,ID_CODE,BLOB_STR,REMARK,CREATE_DATE)VALUES(?,?,?,?,?,?,?,sysdate)";
            insertPstmt = conn.prepareStatement(insertSql);
            newFile = convertEncoding(file);
            f = new FileInputStream(newFile);
            // 1.先删除dm_blob_tmp表中的相关文件
            delPstmt.setString(1, sessionId);
            delPstmt.setString(2, GlobalUtil.getUser().getPcode());
            delPstmt.setString(3, fileName);
            delPstmt.setString(4, GlobalUtil.getUser().getIdCode());
            delPstmt.execute();
            conn.commit();
            // 2.插入dm_blob_tmp表
            insertPstmt.setString(1, sessionId);
            insertPstmt.setString(2, GlobalUtil.getUser().getPcode());
            insertPstmt.setString(3, fileName);
            insertPstmt.setString(4, "1");
            insertPstmt.setString(5, GlobalUtil.getUser().getIdCode());
            insertPstmt.setBlob(6, f);
            insertPstmt.setString(7, "");
            insertPstmt.execute();
            conn.commit();
            DateFormat dateFormat = DateFormat.getDateInstance();
            String fileDate = dateFormat.format(new Date());
            // 调用解析程序
            String args = "[{'type':'String','val': '" + sessionId + "'},{'type':'String','val': '" 
                    + GlobalUtil.getUser().getPcode() + "'},{'type':'String','val': '" + fileName + "'}" 
                    + ",{'type':'String','val': 'T'},{'type':'date','val': '" + fileDate 
                    + "'},{'type':'String','val': '" + parseType + "'},{'type':'String','val': '" 
                    + parseChildType + "'}" + ",{'type':'String','val': '" + fileType 
                    + "'},{'type':'String','val': '" + GlobalUtil.getUser().getStatCode() 
                    + "'},{'type':'String','val': '" + GlobalUtil.getUser().getIdCode() + "'}" 
                    + ",{'type':'String','val': '" + GlobalUtil.getUser().getCo2c()
                    + "'},{'type':'String','val': '" + GlobalUtil.getUser().getCo3c()
                    + "'},{'type':'String','val': ''},{'type':'outString','val': ''}]";
            String resultStr = prcExcuteService.ExcutePrc(prcName, args, conn);
            return CommonMsgOutput.getResponseJson(true, 0, 0, resultStr, "executePrc");
        } catch (RuntimeException e) {
            return CommonMsgOutput.getResponseJson(false, 0, 0, e.getMessage(), "update");
        } catch (Exception e) {
            return CommonMsgOutput.getResponseJson(false, 0, 0, e.getMessage(), "update");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e2) {
                logger.error("txtImportInPrc() 释放 rs 失败!");
            }
            try {
                if (queryPstmt != null) {
                    queryPstmt.close();
                }
            } catch (Exception e2) {
                logger.error("txtImportInPrc() 释放 queryPstmt 失败!");
            }
            try {
                if (delPstmt != null) {
                    delPstmt.close();
                }
            } catch (Exception e2) {
                logger.error("txtImportInPrc() 释放 delPstmt 失败!");
            }
            try {
                if (insertPstmt != null) {
                    insertPstmt.close();
                }
            } catch (Exception e2) {
                logger.error("txtImportInPrc() 释放 insertPstmt 失败!");
            }
            try {
                if (f != null) {
                    f.close();
                }
            } catch (Exception e2) {
                logger.error("txtImportInPrc() 释放 f 失败!");
            }
            // 删除newFile
            if (newFile != null && newFile.exists()) {
                boolean de = newFile.delete();
                if (!de) {
                    logger.error("txtImportInPrc() 删除 newFile 文件失败!");
                }
            }
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                } 
            } catch (Exception e2) {
                logger.error("txtImportInPrc() 释放 conn 失败!");
            }  
        }
    }

    /**
     * 数据窗文件导入(过程解析)
     * @param file     文件流
     * @param fileName 文件名称
     * @return String 返回值
     * @throws IOException 抛出异常
     * @throws SQLException 抛出异常
     */
    public String dwFactorImportInPrc(File file, final String fileName) 
        throws IOException, SQLException {
        DataSource ds = null;
        Connection conn = null;
        PreparedStatement insertPstmt = null;
        InputStream f = null;
        File newFile = null;
        try {
            ds = jdBaseDao.getJdbcTemplate().getDataSource();
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            String insertSql = "INSERT INTO ST_DATAWINDOW_MIANTAIN (PKID, ACT_CODE, ACT_BLOB, ACT_FINISH_SI, SYS_REMARK, CREATE_USER, CREATE_TIME, MODIFY_USER, MODIFY_TIME) " 
                             + "VALUES (fun_get_comm_pkid('ST_DATAWINDOW_MIANTAIN'),?,?,?,?,?,sysdate,?,sysdate)";
            insertPstmt = conn.prepareStatement(insertSql);
            newFile = convertEncoding(file);
            f = new FileInputStream(newFile);
            // 1.插入ST_DATAWINDOW_MIANTAIN表
            insertPstmt.setString(1, "INSERT");
            insertPstmt.setBlob(2, f);
            insertPstmt.setString(3, "F");
            insertPstmt.setString(4, fileName);
            insertPstmt.setString(5, GlobalUtil.getUser().getPcode());
            insertPstmt.setString(6, GlobalUtil.getUser().getPcode());
            insertPstmt.execute();
            conn.commit();
            // 调用解析程序
            String args = "[{'type':'String','val': '" + GlobalUtil.getUser().getPcode() + "'},{'type':'outString','val': ''}]";
            String resultStr = prcExcuteService.ExcutePrc("PRC_ST_DATAWINDOW_MIANTAIN", args, conn);
            return CommonMsgOutput.getResponseJson(true, 0, 0, resultStr, "executePrc");
        } catch (RuntimeException e) {
            return CommonMsgOutput.getResponseJson(false, 0, 0, e.getMessage(), "executePrc");
        } catch (Exception e) {
            return CommonMsgOutput.getResponseJson(false, 0, 0, e.getMessage(), "executePrc");
        } finally {
            try {
                if (insertPstmt != null) {
                    insertPstmt.close();
                }
            } catch (Exception e2) {
                logger.error("dwFactorImportInPrc() 释放 insertPstmt 失败!");
            }
            try {
                if (f != null) {
                    f.close();
                }
            } catch (Exception e2) {
                logger.error("dwFactorImportInPrc() 释放 f 失败!");
            }
            // 删除newFile
            if (newFile != null && newFile.exists()) {
                boolean de = newFile.delete();
                if (!de) {
                    logger.error("dwFactorImportInPrc() 删除 newFile 文件失败!");
                }
            }
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                } 
            } catch (Exception e2) {
                logger.error("dwFactorImportInPrc() 释放 conn 失败!");
            }
        }
    }

    /**
     * txt文件导入(过程解析)
     * 
     * @param file
     *            文件流
     * @return File
     * @throws IOException
     *             异常抛出
     */
    public File convertEncoding(File file) throws IOException {
        UUID uuid = UUID.randomUUID();
        FileWriter fWriter = null;
        InputStream fileInput = null;
        InputStream fileStream = null;
        InputStreamReader reader = null;
        BufferedReader breader = null;
        File txtFile = new File("..\\" + uuid + ".txt");
        try {
            boolean createBool = txtFile.createNewFile();
            if (!createBool) {
                logger.error("ConvertEncoding() 方法创建 txtFile 失败!");
            }
            fWriter = new FileWriter(txtFile);
            fileInput = new FileInputStream(file);
            byte[] head = new byte[3];
            int finput = fileInput.read(head);
            if (finput == 0) {
                logger.error("ConvertEncoding() 方法读取文件的长度为0!");
            }
            String charsetName = "GBK";
            fileStream = new FileInputStream(file);
            if (head[0] == -1 && head[1] == -2) {
                charsetName = "UTF-16";
            } else if (head[0] == -2 && head[1] == -1) {
                charsetName = "Unicode";
            } else if (head[0] == -17 && head[1] == -69 && head[2] == -65) {
                charsetName = "UTF-8";
            } else if (head[0] == -17 && head[1] == -65 && head[2] == -81) {
                charsetName = "UTF-8";
                byte[] headFlag = new byte[9];
                int fileLength = fileStream.read(headFlag);
                if (fileLength == 0) {
                    logger.error("convertEncoding() 读取文件长度为 0;");
                }
            }
            reader = new InputStreamReader(fileStream, charsetName);
            breader = new BufferedReader(reader);
            String s = breader.readLine();
            while (s != null) {
                fWriter.write(s);
                fWriter.write("\r\n");
                s = breader.readLine();
            }
            fWriter.flush();
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (fWriter != null) {
                    fWriter.close();
                }
            } catch (Exception e2) {
                logger.error("ConvertEncoding() 方法关闭 fWriter 失败!", e2);
            }
            try {
                if (fileInput != null) {
                    fileInput.close();
                }
            } catch (Exception e2) {
                logger.error("ConvertEncoding() 方法关闭 fileInput 失败!", e2);
            }
            try {
                if (fileStream != null) {
                    fileStream.close();
                }
            } catch (Exception e2) {
                logger.error("ConvertEncoding() 方法关闭 fileStream 失败!", e2);
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e2) {
                logger.error("ConvertEncoding() 方法关闭 reader 失败!", e2);
            }
            try {
                if (breader != null) {
                    breader.close();
                }
            } catch (Exception e2) {
                logger.error("ConvertEncoding() 方法关闭 breader 失败!", e2);
            }
        }
        return txtFile;
    }

    /**
     * 文件上传至服务器指定路径中
     * @param tempType 模板类型
     * @param file 文件流
     * @param fileName 文件名
     * @return String 返回值
     */
    public String fileUpload(String tempType, File file, String fileName) {
        DataSource ds = null;
        Connection conn = null;
        PreparedStatement insertPstmt = null;
        PreparedStatement updatePstmt = null;
        try {
            ds = jdBaseDao.getJdbcTemplate().getDataSource();
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            //1、判断是否有tempType配置,1.1 有配置则往下走  1.2没有配置，则，有则提示，没有则可以创建新的配置了
            String sql = "SELECT count(*) from MD_FILE_IMP_TEMP WHERE TEMP_TYPE = ? AND ID_CODE = ? ";
            Object[] objArr = new Object[2];
            objArr[0] = tempType;
            objArr[1] = GlobalUtil.getUser().getIdCode();
            int count = jdBaseDao.queryForInt(sql, objArr);
            if (count <= 0) {
                //用默认路径和文件名来判断是否有重复的模板配置
                sql = "SELECT count(*) from MD_FILE_IMP_TEMP WHERE UPPER(FILE_NAME) = ? AND UPPER(TEMP_FILE_DIR) = ? AND TEMP_TYPE != ?";
                Object[] objArr2 = new Object[3];
                objArr2[0] = fileName.toUpperCase();
                objArr2[1] = UPLOADFILEPATH.toUpperCase();
                objArr2[2] = tempType;
                int count2 = jdBaseDao.queryForInt(sql, objArr2);
                if (count2 > 0) {
                    sql = "SELECT TEMP_TYPE from MD_FILE_IMP_TEMP WHERE UPPER(FILE_NAME) = ? AND UPPER(TEMP_FILE_DIR) = ? AND TEMP_TYPE != ?";
                    Map data = jdBaseDao.queryForMap(sql, objArr2);
                    String tempType2 = data.get("TEMP_TYPE").toString();
                    return CommonMsgOutput.getResponseJson(false, 0, 0,  "文件名与" + tempType2 + "模板重复!", "upload");
                }
                //如果没有重复，则可以添加配置了
                String insertSql = "INSERT INTO MD_FILE_IMP_TEMP (PKID, ID_CODE, TEMP_TYPE, TEMP_FILE_DIR, FILE_NAME, CREATE_USER, CREATE_TIME, MODIFY_USER, MODIFY_TIME) "
                         + " VALUES (fun_get_comm_pkid('MD_FILE_IMP_TEMP'),?,?,?,?,?,sysdate,?,sysdate)";
                insertPstmt = conn.prepareStatement(insertSql);
                // 1.插入ST_DATAWINDOW_MIANTAIN表
                insertPstmt.setString(1, GlobalUtil.getUser().getIdCode());
                insertPstmt.setString(2, tempType);
                insertPstmt.setString(3, UPLOADFILEPATH);
                insertPstmt.setString(4, fileName);
                insertPstmt.setString(5, GlobalUtil.getUser().getPcode());
                insertPstmt.setString(6, GlobalUtil.getUser().getPcode());
                insertPstmt.execute();
                conn.commit();
            }
            //2、取出配置。
            sql = "SELECT TEMP_FILE_DIR, FILE_NAME FROM MD_FILE_IMP_TEMP WHERE TEMP_TYPE = ?";
            Object[] objArr3 = new Object[1];
            objArr3[0] = tempType;
            Map data = jdBaseDao.queryForMap(sql, objArr3);
            String sourcefilePath = data.get("TEMP_FILE_DIR").toString(); //原配置的地址
            String sourceFileName = data.get("FILE_NAME").toString(); //原配置的文件名
            // 2.1判断是否有不同类型，同原配置路径的同名文件存在
            sql = "SELECT COUNT(*) FROM MD_FILE_IMP_TEMP WHERE UPPER(FILE_NAME) = ? AND UPPER(TEMP_FILE_DIR) = ? AND TEMP_TYPE != ?";
            Object[] objArr4 = new Object[3];
            objArr4[0] = fileName.toUpperCase();
            objArr4[1] = sourcefilePath.toUpperCase();
            objArr4[2] = tempType;
            int count4 = jdBaseDao.queryForInt(sql, objArr4);
            if (count4 > 0) {
                sql = "SELECT TEMP_TYPE from MD_FILE_IMP_TEMP WHERE UPPER(FILE_NAME) = ? AND UPPER(TEMP_FILE_DIR) = ? AND TEMP_TYPE != ?";
                Map data2 = jdBaseDao.queryForMap(sql, objArr4);
                String tempType2 = data2.get("TEMP_TYPE").toString();
                return CommonMsgOutput.getResponseJson(false, 0, 0,  "文件名与" + tempType2 + "模板重复!", "upload");
            }
            //3、采用覆盖上传的方式上传文件。按配置的路径来上传
            FileUtil.createFileDir(sourcefilePath);
            File sourceFile = new File(sourcefilePath, sourceFileName);
            File saveFile = new File(sourcefilePath, fileName);
            if (saveFile.exists()) {
                saveFile.delete();
            }
            if (sourceFile.exists()) {
                sourceFile.delete();
                boolean saveBool = file.renameTo(saveFile);
                if (saveBool) {
                    if (fileName != sourceFileName) {
                        //更新配置信息
                        String updateSql = "UPDATE MD_FILE_IMP_TEMP SET FILE_NAME = ? WHERE TEMP_TYPE=? AND ID_CODE=?";
                        updatePstmt = conn.prepareStatement(updateSql);
                        updatePstmt.setString(1, fileName);
                        updatePstmt.setString(2, tempType);
                        updatePstmt.setString(3, GlobalUtil.getUser().getIdCode());
                        updatePstmt.execute();
                        conn.commit();
                    }
                    return CommonMsgOutput.getResponseJson(true, 0, 0, "OK!" + tempType + "模板覆盖上传成功!", "upload");
                } else {
                    return CommonMsgOutput.getResponseJson(false, 0, 0,  tempType + "模板覆盖上传失败!", "upload");
                }
            }
            boolean saveBool = file.renameTo(saveFile);
            if (!saveBool) {
                return CommonMsgOutput.getResponseJson(false, 0, 0,  tempType + "模板上传失败!", "upload");
            } else {
                if (!fileName.equals(sourceFileName)) {
                    //更新配置信息
                    String updateSql = "UPDATE MD_FILE_IMP_TEMP SET FILE_NAME = ? WHERE TEMP_TYPE=? AND ID_CODE=?";
                    updatePstmt = conn.prepareStatement(updateSql);
                    updatePstmt.setString(1, fileName);
                    updatePstmt.setString(2, tempType);
                    updatePstmt.setString(3, GlobalUtil.getUser().getIdCode());
                    updatePstmt.execute();
                    conn.commit();
                }
                return CommonMsgOutput.getResponseJson(true, 0, 0, "OK!" + tempType + "模板上传成功!", "upload");
            }
        } catch (Exception e) {
            return CommonMsgOutput.getResponseJson(false, 0, 0, e.getMessage(), "upload");
        } finally {
            try {
                if (insertPstmt != null) {
                    insertPstmt.close();
                }
            } catch (Exception e2) {
                logger.error("fileUpload() 释放 insertPstmt 失败!");
            }
            try {
                if (updatePstmt != null) {
                    updatePstmt.close();
                }
            } catch (Exception e2) {
                logger.error("fileUpload() 释放 updatePstmt 失败!");
            }
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                } 
            } catch (Exception e2) {
                logger.error("fileUpload() 释放 conn 失败!");
            }
        }
    }

    /**
     * 判断对应的模板类型是否有文件
     * @param tempType 模板类型
     * @return String 返回值
     */
    public String isTemplateFileExists(String tempType) {
        DataSource ds = null;
        Connection conn = null;
        PreparedStatement insertPstmt = null;
        PreparedStatement updatePstmt = null;
        try {
            ds = jdBaseDao.getJdbcTemplate().getDataSource();
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            //1、判断是否有tempType配置,1.1 有配置则往下走  1.2没有配置，则，有则提示，没有则可以创建新的配置了
            String sql = "SELECT count(*) from MD_FILE_IMP_TEMP WHERE TEMP_TYPE = ?";
            Object[] objArr = new Object[1];
            objArr[0] = tempType;
            int count = jdBaseDao.queryForInt(sql, objArr);
            if (count > 0) {
                //判断文件是否存在
                sql = "SELECT TEMP_FILE_DIR, FILE_NAME FROM MD_FILE_IMP_TEMP WHERE TEMP_TYPE = ?";
                Map data = jdBaseDao.queryForMap(sql, objArr);
                String sourcefilePath = data.get("TEMP_FILE_DIR").toString(); //原配置的地址
                String sourceFileName = data.get("FILE_NAME").toString(); //原配置的文件名
                File sourceFile = new File(sourcefilePath, sourceFileName);
                if (!sourceFile.exists()) {
                    return CommonMsgOutput.getResponseJson(false, 0, 0,  tempType + "模板文件不存在于服务器!", "upload");
                }
            } else {
                return CommonMsgOutput.getResponseJson(false, 0, 0,  "未配置模板" + tempType, "upload");
            }
            return CommonMsgOutput.getResponseJson(true, 0, 0,  "", "upload");
        } catch (Exception e) {
            return CommonMsgOutput.getResponseJson(false, 0, 0, e.getMessage(), "upload");
        } finally {
            try {
                if (insertPstmt != null) {
                    insertPstmt.close();
                }
            } catch (Exception e2) {
                logger.error("fileUpload() 释放 insertPstmt 失败!");
            }
            try {
                if (updatePstmt != null) {
                    updatePstmt.close();
                }
            } catch (Exception e2) {
                logger.error("fileUpload() 释放 updatePstmt 失败!");
            }
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                } 
            } catch (Exception e2) {
                logger.error("fileUpload() 释放 conn 失败!");
            }
        }
    }

    /**
     * 获取Excel单元格的值
     * 
     * @param cell
     *            Excel单元格
     * @return String
     */
    private String getExcelCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        Object valObject;
        switch (cell.getCellType()) {
        case 0:
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                Date date = cell.getDateCellValue();
                valObject = DateFormatUtils.format(date, "yyyy-MM-dd");
            } else {
                valObject = cell.getNumericCellValue();
                DecimalFormat df = new DecimalFormat("##.########");
                valObject = df.format(valObject);
            }
            break;
        case 1:
            valObject = cell.getStringCellValue().trim();
            break;
        case 2:
            valObject = cell.getCellFormula();
            break;
        case 3:
            valObject = "";
            break;
        case 4:
            valObject = cell.getBooleanCellValue();
            break;
        case 5:
            valObject = cell.getErrorCellValue();
            break;
        default:
            valObject = "";
            break;
        }
        return valObject.toString();
    }
}