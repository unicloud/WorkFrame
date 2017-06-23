package com.greatfly.common.util.file;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.log4j.Logger;

/**
 * 文件工具类
 * 
 * @author Li 2016-1-25 下午6:46:47
 */
public class FileUtil {
    
	private static Logger g_logger = Logger.getLogger(FileUtil.class);

	/**
     * 创建一个私有新的实例 FileUtil.
     *
	 */
	private FileUtil() {
	    
	}
	
	/**
	 * 创建文件
	 * 
	 * @param path
	 *            文件路径
	 * @param fileData
	 *            文件流
	 * @return boolean
	 */
	public static boolean createFile(String path, byte[] fileData) {
		File file = null;
		FileOutputStream fos = null;
		try {
			file = new File(path);
			if (!file.getParentFile().exists()) {
			    boolean dirBool = file.getParentFile().mkdirs();
				if (!dirBool) {
                    g_logger.error("createFile() 创建目录失败!");
                }
			}
			if (file.exists()) {
				boolean deBool = file.delete();
				if (!deBool) {
                    g_logger.error("createFile() 删除文件失败!");
                }
			}
			fos = new FileOutputStream(file);
			fos.write(fileData);
			return true;
		} catch (Exception e) {
			g_logger.error(e.getMessage(), e);
		} finally {
			fileOutputStreamClose(fos);
		}
		return false;
	}

	/**
	 * 创建文件
	 * 
	 * @param fileName
	 *            文件名
	 * @return boolean
	 */
	public static boolean createFile(File fileName) {
		boolean flag = false;
		try {
			if (!fileName.exists()) {
				boolean createBool = fileName.createNewFile();
				if (!createBool) {
                    g_logger.error("createFile() 创建新文件失败!");
                }
				flag = true;
			}
		} catch (Exception e) {
			g_logger.error(e.getMessage(), e);
		}
		return flag;
	}

	/**
	 * 判断文件路径是否存在，不存在自动创建
	 * 
	 * @param path
	 *            文件路径
	 */
	public static void createFileDir(String path) {
		File file = null;
		try {
			file = new File(path);
			if  (!file.exists()  && !file.isDirectory()) {
				 boolean dirBool = file.mkdir();
				 if (!dirBool) {
                    g_logger.error("createFile() 创建目录失败!");
                 }
			}	
		} catch (Exception e) {
			g_logger.error(e.getMessage(), e);
		}
	}
	/**
	 * 文件输出流关闭
	 * 
	 * @param fos
	 *            FileOutputStream文件
	 */
	public static void fileOutputStreamClose(FileOutputStream fos) {
		if (fos != null) {
			try {
				fos.flush();
			} catch (Exception e) {
				g_logger.error(e.getMessage(), e);
			} finally {
				try {
					fos.close();
				} catch (IOException e) {
					g_logger.error(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 返回web工程当前根目录
	 * 
	 * @return String
	 */
	public static String getWebRootPath() {
		String result = FileUtil.class.getResource("FileUtil.class").toString();
		int index = result.indexOf("WEB-INF");
		if (index == -1) {
			index = result.indexOf("bin");
		}
		if (index == -1) {
			index = result.indexOf("cloud2-");
			result = result.substring(0, index);
			result = result + "\\cloud2-web\\src\\main\\webapp\\";
			index = result.length() - 1;
		}
		result = result.substring(0, index);
		if (result.startsWith("jar")) {
			// 当class文件在jar文件中时，返回"jar:file:/F:/ ..."样的路径
			result = result.substring(10);
		} else if (result.startsWith("file")) {
			// 当class文件在class文件中时，返回"file:/F:/ ..."样的路径
			result = result.substring(6);
		}
		if (result.endsWith("/")) {
		    result = result.substring(0, result.length() - 1);
		}
		String os = System.getProperty("os.name");
		// 如果不为win操作系统
		if (!(os.startsWith("win") || os.startsWith("Win"))) {
			result = "/" + result;
		}
		return result;
	}

	/**
	 * 删除指定文件夹下所有文件
	 * 
	 * @param path
	 *            文件夹完整绝对路径
	 * @return boolean
	 */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (file.isFile()) {
			boolean deBool = file.delete();
			if (!deBool) {
                g_logger.error("delAllFile() 删除文件 file 失败");
            }
			flag = true;
			return flag;
		}
		String[] tempList = file.list();
		if (tempList == null) {
            return flag;
        }
		File temp = null;
		int tempListLength = tempList.length;
		for (int i = 0; i < tempListLength; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				// 先删除文件夹里面的文件
				delAllFile(path + "/" + tempList[i]);
				// delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				new File(path + "/" + tempList[i]).delete();
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 判断是否为文件
	 * 
	 * @param path
	 *            文件路径
	 * @return Boolean
	 */
	public static Boolean isFile(String path) {
		File file = new File(path);
		return file.exists() && file.isFile();
	}

	/**
	 * 获取图片格式
	 * 
	 * @param f
	 *            需要判断格式的文件
	 * @return String
	 */
	public static String getImageFileType(File f) {
		if (f.exists() && f.isFile()) {
			try {
				String[] array = f.getName().split("\\.");
				if (array.length > 1) {
					return array[array.length - 1];
				}
			} catch (Exception e) {
				g_logger.error(e.getMessage(), e);
			}
		}
		return null;
	}

	/**
	 * 
	 * @param f
	 *            文件
	 * @return String
	 */
	public static String getImageFileType2(File f) {
		ImageInputStream iis = null;
		if (isImage(f)) {
			try {
				iis = ImageIO.createImageInputStream(f);
				Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
				if (!iter.hasNext()) {
					return null;
				}
				ImageReader reader = iter.next();
				return reader.getFormatName().toLowerCase();
			} catch (Exception e) {
				g_logger.error(e.getMessage(), e);
			} finally {
				if (iis != null) {
					try {
						iis.close();
					} catch (IOException e) {
						g_logger.error(e.getMessage(), e);
					}
				}
			}
		}
		return null;
	}

	/**
	 * 判断文件是否为图片
	 * 
	 * @param file
	 *            文件
	 * @return boolean
	 */
	public static final boolean isImage(File file) {
		try {
			BufferedImage bufreader = ImageIO.read(file);
			int width = bufreader.getWidth();
			int height = bufreader.getHeight();
			if (!(width == 0 || height == 0)) {
				return true;
			}
		} catch (Exception e) {
			g_logger.error(e.getMessage(), e);
		}
		return false;
	}

	/**
	 * 
	 * @param file
	 *            文件
	 * @param charsetName
	 *            字符集名称
	 * @return string
	 */
	public static String readFile(File file, String charsetName) {
		InputStreamReader read = null;
		BufferedReader reader = null;
		StringBuffer result = new StringBuffer();
		String line;
		try {
			read = new InputStreamReader(new FileInputStream(file), charsetName);
			reader = new BufferedReader(read);
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			g_logger.error(e.getMessage(), e);
		} finally {
		    if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    g_logger.error(e.getMessage(), e);
                }
            }
		}
		return result.toString();
	}

	/**
	 * 判断两文件名称一直*
	 * 
	 * @param name1
	 *            文件名称1
	 * @param name2
	 *            文件名称2
	 * @return boolean
	 */
	public static final boolean fileNameEquals(String name1, String name2) {
		try {
			return new File(name1).getName().equals(new File(name2).getName());
		} catch (Exception e) {
			g_logger.error(e.getMessage(), e);
		}
		return false;
	}

	/**
	 * 获取文件的数据
	 * 
	 * @param path
	 *            路径
	 * @return byte[]
	 */
	public static byte[] getFileData(String path) {
		byte[] byte1 = null;
		InputStream inputStream = null;
		try {
			File file = new File(path);
			inputStream = new FileInputStream(file);
			return inputStreamToByte(inputStream);
		} catch (Exception e) {
			g_logger.error(e.getMessage(), e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					g_logger.error(e.getMessage(), e);
				}
			}
		}
		return byte1;
	}

	/**
	 * 输入流字节
	 * 
	 * @param is
	 *            输入流
	 * @return byte[] 字符流
	 * @throws IOException
	 *             IO流异常
	 */

	private static byte[] inputStreamToByte(InputStream is) throws IOException {
		byte[] byte1 = null;
		ByteArrayOutputStream baos = null;
		int ch;
		try {
			baos = new ByteArrayOutputStream();
			while ((ch = is.read()) != -1) {
				baos.write(ch);
			}
			return baos.toByteArray();
		} catch (Exception e) {
			g_logger.error(e.getMessage(), e);
		} finally {
			if (baos != null) {
				baos.close();
			}
		}
		return byte1;
	}

	/**
	 * 复制文件
	 * 
	 * @param path1
	 *            文件1
	 * @param path2
	 *            文件2
	 * @return Boolean
	 */
	public static Boolean copyFile(String path1, String path2) {
		FileInputStream in = null;
		File file;
		FileOutputStream out = null;
		Boolean result = false;
		try {
			in = new FileInputStream(path1);
			file = new File(path2);
			if (!file.getParentFile().exists()) {
			    boolean createBool = file.getParentFile().mkdirs();
				if (!createBool) {
                    g_logger.error("copyFile() 创建新目录失败!");
                }
			}
			if (file.exists()) {
				boolean deBool = file.delete();
				if (!deBool) {
                    g_logger.error("copyFile() 删除 file 文件失败!");
                }
			}
			if (!file.exists()) {
				boolean createBool = file.createNewFile();
				if (!createBool) {
                    g_logger.error("copyFile() 创建新文件失败!");
                }
			}
			out = new FileOutputStream(file);
			int c;
			byte[] buffer = new byte[1024];
			while ((c = in.read(buffer)) != -1) {
				for (int i = 0; i < c; i++) {
				    out.write(buffer[i]);
				}
			}
			result = true;
		} catch (Exception e) {
			g_logger.error(e.getMessage(), e);
		} finally {
			close(in, out);
		}
		return result;
	}

	/**
	 * 关闭
	 * 
	 * @param in
	 *            输入流接口
	 * 
	 * @param out
	 *            输出流接口
	 */
	public static void close(InputStream in, OutputStream out) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				g_logger.error(e.getMessage(), e);
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						g_logger.error(e.getMessage(), e);
					}
				}
			}
		}
	}

	/**
	 * 关闭
	 * 
	 * @param fos
	 *            文件输出流
	 * @param fis
	 *            文件输入流
	 */
	public static void close(FileOutputStream fos, FileInputStream fis) {
		if (fis != null) {
			try {
				fis.close();
			} catch (IOException e) {
				g_logger.error(e.getMessage(), e);
			} finally {
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						g_logger.error(e.getMessage(), e);
					}
				}
			}
		}
	}

	/**
	 * 根据byte数组，生成文件
	 * 
	 * @param bfile
	 *            byte 数组
	 * @param filePath
	 *            文件路径
	 * @param fileName
	 *            文件名字
	 */
	public static void getFile(byte[] bfile, String filePath, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			// 判断文件目录是否存在
			if (!dir.exists() && dir.isDirectory()) {
				boolean dirBool = dir.mkdirs();
				if (!dirBool) {
                    g_logger.error("getFile()创建目录失败!");
                }
			}
			file = new File(filePath + "\\" + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bfile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
				    g_logger.error("getFile()释放 bos 失败!");
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
				    g_logger.error("getFile()释放 fos 失败!");
				}
			}
		}
	}

	/**
	 * 去掉文件后缀名
	 * 
	 * @param filename
	 *            文件名
	 * @return String
	 */
	public static String getFileNameNoEx(String filename) {
		if (filename != null && filename.length() > 0) {
			int dot = filename.lastIndexOf('.');
			if (dot > -1 && dot < filename.length()) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

	/**
	 * 程序实现了ZIP解压（decompression）
	 * 
	 * @param filePath
	 *            文件目录
	 * 
	 * @param parent
	 *            输出路径（文件夹目录）
	 * @return Boolean
	 */

	public static Boolean zipDecompressing(String filePath, String parent) {
		try {
			// 输入源zip路径
			ZipInputStream zin = new ZipInputStream(new FileInputStream(filePath));
			BufferedInputStream bin = new BufferedInputStream(zin);
			File fout = null;
			ZipEntry entry;
			try {
				while ((entry = zin.getNextEntry()) != null && !entry.isDirectory()) {
					fout = new File(parent, entry.getName());
					if (!fout.exists()) {
						boolean dirBool = new File(fout.getParent()).mkdirs();
						if (!dirBool) {
						    g_logger.error("zipDecompressing() 创建目录失败!");
                        }
					}
					FileOutputStream out = new FileOutputStream(fout);
					BufferedOutputStream bout = new BufferedOutputStream(out);
					int b;
					while ((b = bin.read()) != -1) {
						bout.write(b);
					}
					bout.close();
					out.close();
				}
			} catch (IOException e) {
			    g_logger.error("zipDecompressing() 压缩文件失败!", e);
			} finally {
			    try {
			        bin.close();
                } catch (Exception e2) {
                    g_logger.error("zipDecompressing() 释放  bin 失败!");
                }
			    try {
			        zin.close();
                } catch (Exception e2) {
                    g_logger.error("zipDecompressing() 释放  zin 失败!");
                }
			}
		} catch (FileNotFoundException e) {
			g_logger.error("zipDecompressing() 压缩文件失败!", e);
		}
		return true;
	}

	/**
	 * 删除单个文件
	 * 
	 * @param fileName
	 *            要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}