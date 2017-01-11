package com.greatfly.common.util.ftp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.greatfly.common.util.StringUtil;

/**
 * FTP工具类
 * @author hyh
 * 2015-8-12 上午11:11:13
 */
public class FtpUtil {
	public static final String ANONYMOUS_LOGIN = "anonymous";
	private FTPClient ftp;
	private boolean isConnected;

	/**
	 * 无参数构造函数
	 */
	public FtpUtil() {
		ftp = new FTPClient();
		isConnected = false;
	}
	
	/**
	 * 有参数构造函数
	 * @param defaultTimeoutSecond 默认时间
	 * @param connectTimeoutSecond 连接时间
	 * @param dataTimeoutSecond    超时时间
	 */
	public FtpUtil(int defaultTimeoutSecond, int connectTimeoutSecond, int dataTimeoutSecond) {
		ftp = new FTPClient();
		isConnected = false;
		ftp.setDefaultTimeout(defaultTimeoutSecond * 1000);
		ftp.setConnectTimeout(connectTimeoutSecond * 1000);
		ftp.setDataTimeout(dataTimeoutSecond * 1000);
	}

	/**
	 * Connects to FTP server.
	 * @param host FTP server address or name
	 * @param port FTP server port
	 * @param user user name
	 * @param password user password
	 * @throws IOException on I/O errors
	 */
	public void connect(String host, int port, String user, String password) throws IOException {
		// Connect to server.
		try {
			ftp.connect(host, port);
		} catch (UnknownHostException ex) {
			throw new IOException("Can't find FTP server '" + host + "'");
		}

		// Check response after connection attempt.
		int reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			disconnect();
			throw new IOException("Can't connect to server '" + host + "'");
		}

		if (StringUtil.isEmpty(user)) {
			user = ANONYMOUS_LOGIN;
		}

		// Login.
		if (!ftp.login(user, password)) {
			isConnected = false;
			disconnect();
			throw new IOException("Can't login to server '" + host + "'");
		} else {
			isConnected = true;
		}
	}

	/**
	 * 配置FTP的信息 
	 * @param bufferSize   缓冲区
	 * @param encoding     编码
	 * @param isTextType   文本类型
	 * @param passiveMode  被动模式
	 * @throws IOException 拋出異常
	 */
	public void config(int bufferSize, String encoding, boolean isTextType, boolean passiveMode) throws IOException {
		//Set bufferSize
		if (bufferSize > 0) {
			ftp.setBufferSize(bufferSize); 
		}   	
		//Set Encoding
		if (encoding != null) {
			ftp.setControlEncoding(encoding);
		}
		// Set data transfer mode.
		if (isTextType) {
			ftp.setFileType(FTP.ASCII_FILE_TYPE);
		} else {
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
		}
		//Set passiveMode
		if (passiveMode) {
			// Use passive mode to pass firewalls.
			ftp.enterLocalPassiveMode();
		}
	}

	/**
	 * Uploads the file to the FTP server.
	 * @param ftpFileName server file name (with absolute path)
	 * @param localFile local file to upload
	 * @throws IOException on I/O errors
	 */
	public void upload(String ftpFileName, File localFile) throws IOException {
		// File check.
		if (!localFile.exists()) {
			throw new IOException("Can't upload '" + localFile.getAbsolutePath() + "'. This file doesn't exist.");
		}
		// Upload.
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(localFile));
			if (!ftp.storeFile(ftpFileName, in)) {
				throw new IOException("Can't upload file '" + ftpFileName + "' to FTP server. Check FTP permissions and path.");
			}

		} finally {
			in.close();
		}
	}

	/**
	 * Downloads the file from the FTP server.
	 * @param ftpFileName server file name (with absolute path)
	 * @param localFile local file to download into
	 * @throws IOException on I/O errors
	 */
	public void download(String ftpFileName, File localFile) throws IOException {
		OutputStream out = null;
		try {
			// Get file info.
			FTPFile[] fileInfoArray = ftp.listFiles(ftpFileName);
			if (fileInfoArray == null) {
				throw new FileNotFoundException("File " + ftpFileName + " was not found on FTP server.");
			}

			// Check file size.
			FTPFile fileInfo = fileInfoArray[0];
			long size = fileInfo.getSize();
			if (size > Integer.MAX_VALUE) {
				throw new IOException("File " + ftpFileName + " is too large.");
			}

			// Download file.
			out = new BufferedOutputStream(new FileOutputStream(localFile));
			if (!ftp.retrieveFile(ftpFileName, out)) {
				throw new IOException("Error loading file " + ftpFileName + " from FTP server. Check FTP permissions and path.");
			}

			out.flush();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * Removes the file from the FTP server.
	 * @param ftpFileName server file name (with absolute path)
	 * @throws IOException on I/O errors
	 */
	public void remove(String ftpFileName) throws IOException {
		if (!ftp.deleteFile(ftpFileName)) {
			throw new IOException("Can't remove file '" + ftpFileName + "' from FTP server.");
		}
	}

	/**
	 * Sends an FTP Server site specific command
	 * @param args site command arguments
	 * @throws IOException on I/O errors
	 */
	public void sendSiteCommand(String args) throws IOException {
		if (ftp.isConnected()) {
			ftp.sendSiteCommand(args);
		}
	}

	/**
	 * Disconnects from the FTP server
	 * @throws IOException on I/O errors
	 */
	public void disconnect() throws IOException {

		if (ftp.isConnected()) {
			ftp.logout();
			ftp.disconnect();
			isConnected = false;
		}
	}

	/**
	 * Test coonection to ftp server
	 * @return true, if connected
	 */
	public boolean isConnected() {
		return isConnected;
	}
	/**
	 * Make Directory on ftp server
	 * @param dir new directory
	 * @return true, if directory made
	 * @throws IOException 
	 */
	public boolean makeDirectory(String dir) throws IOException { 
		if (!isConnected) {
			return false;
		}
		
		return ftp.makeDirectory(dir); 	   	
	}

	/**
	 * Set working directory on ftp server
	 * @param dir new working directory
	 * @return true, if working directory changed
	 * @throws IOException 
	 */
	public boolean setWorkingDirectory(String dir) throws IOException {
		if (!isConnected) {
			return false;
		}

		return ftp.changeWorkingDirectory(dir);
	}

	/**
	 * Get directory contents on ftp server
	 * @param filePath directory
	 * @return list of FTPFile structures
	 * @throws IOException 
	 */
	public FTPFile[] listFiles(String filePath) throws IOException {
		FTPFile[] ftpFiles = ftp.listFiles(filePath);
		return ftpFiles;
	}
}