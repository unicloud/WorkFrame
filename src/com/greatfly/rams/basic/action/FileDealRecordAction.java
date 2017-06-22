package com.greatfly.rams.basic.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.greatfly.common.action.BaseAction;
import com.greatfly.common.annotation.Description;
import com.greatfly.common.dao.impl.JdbcBaseDao;
import com.greatfly.common.util.msgconverter.CommonMsgOutput;
import com.greatfly.rams.basic.service.FileDealRecordService;

/**
 * 文件上传/解析调用Action
 * 
 * @author QiBin
 * @date 2016-03-24 15:43:31
 */
@Scope("prototype")
@Controller
@Description("文件上传/解析调用Action")
public class FileDealRecordAction extends BaseAction {
    private static Logger logger = Logger.getLogger(FileDealRecordAction.class);
    
    @Resource
    private FileDealRecordService fileDealRecordService;
    /**
     * 文件流
     */
    private java.io.File fileToUpload;

    /**
     * 文件名称
     */
    private String fileToUploadFileName;


    public java.io.File getFileToUpload() {
        return fileToUpload;
    }

    public void setFileToUpload(java.io.File fileToUpload) {
        this.fileToUpload = fileToUpload;
    }

    public String getFileToUploadFileName() {
        return fileToUploadFileName;
    }

    public void setFileToUploadFileName(String fileToUploadFileName) {
        this.fileToUploadFileName = fileToUploadFileName;
    }

    @Resource
    private JdbcBaseDao jdBaseDao;
    
    public JdbcBaseDao getJdBaseDao() {
        return jdBaseDao;
    }

    public void setJdBaseDao(JdbcBaseDao jdBaseDao) {
        this.jdBaseDao = jdBaseDao;
    }

    /**
     * 文件导入(java代码解析)
     */
    @Description("文件导入")
    public void fileImport() {
        String jsonString = "";
        String fileType = request.getParameter("fileType");
        if (fileToUpload == null) {
            jsonString = CommonMsgOutput.getResponseJson(false, 0, "0394" + "", "9", "error");
        } else {
            try {
                if (fileToUploadFileName.toLowerCase().endsWith(".xlsx") || fileToUploadFileName.toLowerCase().endsWith(".xls")) {
                    jsonString = fileDealRecordService.execlImport(fileToUpload, fileType, fileToUploadFileName);
                } else {
                    jsonString = CommonMsgOutput.getResponseJson(false, 0, "0", "请使用Excel文件导入数据！", "error");
                }
            } catch (Exception e) {
                jsonString = CommonMsgOutput.getResponseJson(false, 0, "0", e.getMessage().replace("\"", "'"), "error");
                logger.error("fileImport()出现异常", e);
            }
        }
        try {
            response.setContentType("text/html");
            response.getWriter().append(jsonString);
        } catch (IOException e) {
            logger.error("fileImport()出现异常", e);
        }
    }

    /**
     * 文件导入(存储过程解析)
     */
    @Description("文件导入")
    public void fileImportInPrc() {
        String jsonString = "";
        String fileType = request.getParameter("fileType");
        String sessionId = getSession().getId();
        if (fileToUpload == null) {
            jsonString = CommonMsgOutput.getResponseJson(false, 0, "0394" + "", "9", "error");
        } else {
            try {
                if (fileToUploadFileName.toLowerCase().endsWith(".xlsx") || fileToUploadFileName.toLowerCase().endsWith(".xls")) {
                    jsonString = fileDealRecordService.execlImportInPrc(fileToUpload, fileType, fileToUploadFileName, sessionId);
                } else if (fileToUploadFileName.toLowerCase().endsWith(".txt")) {
                    jsonString = fileDealRecordService.txtImportInPrc(fileToUpload, fileType, fileToUploadFileName, sessionId);
                } else if (fileToUploadFileName.toLowerCase().endsWith(".csv")) {
                    jsonString = fileDealRecordService.txtImportInPrc(fileToUpload, fileType, fileToUploadFileName, sessionId);
                } else if (fileToUploadFileName.toLowerCase().endsWith(".pdf")) {
                    jsonString = fileDealRecordService.pdfImportInPrc(fileToUpload, fileType, fileToUploadFileName, sessionId);
                } else {
                    jsonString = fileDealRecordService.txtImportInPrc(fileToUpload, fileType, fileToUploadFileName, sessionId);
                }
            } catch (Exception e) {
                jsonString = CommonMsgOutput.getResponseJson(false, 0, "0", e.getMessage().replace("\"", "'"), "error");
                logger.error("fileImportInPrc()出现异常", e);
            }
        }
        try {
            response.setContentType("text/html");
            response.getWriter().append(jsonString);
        } catch (IOException e) {
            logger.error("fileImportInPrc()出现异常", e);
        }
    }

    /**
     * 数据窗导入(存储过程解析)
     */
    @Description("数据窗导入")
    public void dwFactorImportInPrc() {
        String jsonString = "";
        if (fileToUpload == null) {
            jsonString = CommonMsgOutput.getResponseJson(false, 0, "0394" + "", "9", "error");
        } else {
            try {
                jsonString = fileDealRecordService.dwFactorImportInPrc(fileToUpload, fileToUploadFileName);
            } catch (Exception e) {
                jsonString = CommonMsgOutput.getResponseJson(false, 0, "0", e.getMessage().replace("\"", "'"), "error");
                logger.error("dwFactorImportInPrc()出现异常", e);
            }
        }
        try {
            response.setContentType("text/html");
            response.getWriter().append(jsonString);
        } catch (IOException e) {
            logger.error("dwFactorImportInPrc()出现异常", e);
        }
    }

    /**
     * 文件上传(文件上传至服务器文件夹中)
     */
    @Description("文件上传")
    public void fileUpload() {
        String tempType = request.getParameter("tempType");
        String jsonString = "";
        if (fileToUpload == null) {
            jsonString = CommonMsgOutput.getResponseJson(false, 0, 0, "文件流丢失！", "error");
        } else {
            try {
                jsonString = fileDealRecordService.fileUpload(tempType, fileToUpload, fileToUploadFileName);
            } catch (Exception e) {
                jsonString = CommonMsgOutput.getResponseJson(false, 0, "0", e.getMessage().replace("\"", "'"), "error");
                logger.error("fileUpload()出现异常", e);
            }
        }
        try {
            response.setContentType("text/html");
            response.getWriter().append(jsonString);
        } catch (IOException e) {
            logger.error("fileUpload()出现异常", e);
        }
    }

    /**
     * 判断需要下载的模板文件是否存在
     */
    @Description("判断需要下载的模板文件是否存在")
    public void isTemplateFileExists() {
        String tempType = request.getParameter("tempType");
        String jsonString = "";
        try {
            jsonString = fileDealRecordService.isTemplateFileExists(tempType);
        } catch (Exception e) {
            jsonString = CommonMsgOutput.getResponseJson(false, 0, "0", e.getMessage().replace("\"", "'"), "error");
            logger.error("fileUpload()出现异常", e);
        }
        output(jsonString, logger);
    }
    /**
     * 模板文件下载
     */
    @Description("模板文件下载")
    public void tempFileDownload() {
        String tempType = request.getParameter("tempType");
        String sql = "SELECT TEMP_FILE_DIR, FILE_NAME FROM MD_FILE_IMP_TEMP WHERE TEMP_TYPE = ?";
        Object[] objArr = new Object[1];
        objArr[0] = tempType;
        @SuppressWarnings("rawtypes")
		Map data = jdBaseDao.queryForMap(sql, objArr);
        String sourcefilePath = data.get("TEMP_FILE_DIR").toString(); //原配置的地址
        String sourceFileName = data.get("FILE_NAME").toString(); //原配置的文件名
        String filePath = sourcefilePath + sourceFileName;
        String filename = sourceFileName;
        File file = null;
        InputStream fis = null;
        try {
            // path是指欲下载的文件的路径。
            filePath = filePath.replace("＼", "\\");
            file = new File(filePath);
            // 以流的形式下载文件。
            fis = new BufferedInputStream(new FileInputStream(filePath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            String isoFilename = new String(filename.getBytes("GBK"), "ISO-8859-1");
            response.addHeader("Content-Disposition", "attachment;filename=" + isoFilename);
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            logger.error("tempFileDownload()方法出现异常:", ex);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    logger.error("tempFileDownload()关闭文件出现异常:", e);
                }
            }
        }
    }

     /**
     * 文件下载(服务器文件夹中下载文件)
     */
    @Description("文件下载")
    public void fileDownload() {
        String filePath = request.getParameter("filepath");
        String filename = request.getParameter("filename");
        File file = null;
        InputStream fis = null;
        try {
            // path是指欲下载的文件的路径。
            filePath = filePath.replace("＼", "\\");
            file = new File(filePath);
            // 以流的形式下载文件。
            fis = new BufferedInputStream(new FileInputStream(filePath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            String isoFilename = new String(filename.getBytes("GBK"), "ISO-8859-1");
            response.addHeader("Content-Disposition", "attachment;filename=" + isoFilename);

            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            logger.error("fileDownload()方法出现异常:", ex);
        } finally {
            if (file != null && file.exists()) {
                boolean de = file.delete();
                if (!de) {
                    logger.error("fileDownload()删除文件出现错误.");
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    logger.error("fileDownload()关闭文件出现异常:", e);
                }
            }
        }
    }

    /**
     * 获取文件上传路径
     */
    @Description("获取文件上传路径")
    public void getFilePath() {
        @SuppressWarnings("static-access")
		String jsonString = CommonMsgOutput.getResponseJson(true, 0, "0", fileDealRecordService.UPLOADFILEPATH, "select");
        output(jsonString, logger);
    }
}