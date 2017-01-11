package com.greatfly.rams.basic.action;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.greatfly.common.action.BaseAction;
import com.greatfly.common.annotation.Description;
import com.greatfly.common.util.cryptography.AESUtil;

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

    /**
     * 此方法弃用,只为兼容之前的代码
     * 现在获取企业二字码,三字码直接从用户的SESSION中取
     * 获取公司企业二字码,三字码 reCo2c, reCo3c,返回企业EntVo类
     */
    @Description("获取公司企业二字码,三字码 reCo2c, reCo3c")
    public void getEntInfo() {
    	AESUtil aesUtil = AESUtil.getInstance();
    	String aaString = "";
    	try {
    	aaString = aesUtil.getEncString("rams_basic_adm");
    	} catch (Exception e) {
    		aaString = e.getMessage();
    	}
        output(aaString, logger);
    }
}