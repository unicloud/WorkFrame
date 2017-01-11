package com.greatfly.common.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;


/**
 * 使用JAVA自带的DOM来对XML字符串进行解析
 * @author CGD
 *
 */
public final class XMLToDOMUtil {
    
    private static Logger log = Logger.getLogger(XMLToDOMUtil.class);

    /**
     * 使用JAVA自带的DOM来解析XML字符串
     * @param protocolXML XML字符串
     * @return List<String>
     * @throws Exception 
     */
    public static List<String> normalFieldsToList(String protocolXML) 
            throws Exception {
        List<String> list = new ArrayList<String>();
        
        if (StringUtil.isBlank(protocolXML)) {
            return list;
        }
        
        protocolXML = "<nodes>" + protocolXML.replace("<", "<A").replace("<A/", "</").replace("</", "</A") 
            + "</nodes>";

        try {   
             DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();   
             DocumentBuilder builder = factory.newDocumentBuilder();   
             Document doc = builder.parse(new InputSource(new StringReader(protocolXML)));   
  
             Element root = doc.getDocumentElement(); 
             
             NodeList nodes = root.getChildNodes();   
             if (nodes != null) {   
                 for (int i = 0; i < nodes.getLength(); i++) {   
                      Node node = nodes.item(i);   
                      list.add(node.getFirstChild().getNodeValue());
                  }   
              }  
        } catch (Exception e) {
             list.clear();
             log.error("XML转换为Map出错：", e);
             throw e;
        }
        return list;
     }

    
    /**
     * 手工遍历数据窗口信息中的NOTNULL字段信息
     * @param protocolXML XML字符串
     * @return 如果传入参数为空,则 return null;否则返回List
     */
    public static List<String> notNullFieldsToList(String protocolXML) {
        List<String> list = new ArrayList<String>();
    	if (StringUtil.isBlank(protocolXML)) {
            return list;
        }
       
    	try {
			// 遍历字符串
    		int start = protocolXML.indexOf("<NOTNULL>");
    		int end = 0;
    		if (start == -1) {
    		    list.clear();
    			return list;
    		} else {
    			end = protocolXML.indexOf("</NOTNULL>");
    			if (start + 9 >= end) {
    				list.clear();
        			return list;
    			}
    			String tempString = protocolXML.substring(start + 9, end);
    			String[] loopStrings = tempString.split(">");
    			for (int i = 0; i < loopStrings.length; i++) {
    				list.add(loopStrings[i].substring(1));
				}
    		}
		} catch (Exception e) {
		    list.clear();
		    log.error("XMLStringToMap()XML转换为Map出错：", e);
		}
    	return list;
    }
    
	/*
	 * DOM4j
	    1.读取XML文件,获得document对象            
	    SAXReader reader = new SAXReader();
		Document   document = reader.read(new File("csdn.xml"));
		2.解析XML形式的文本,得到document对象.
	    String text = "<csdn></csdn>";            
	    Document document = DocumentHelper.parseText(text);
	    3.主动创建document对象.
	    Document document = DocumentHelper.createDocument();             //创建根节点
	    Element root = document.addElement("csdn");
	    
	 * --------------------------------------
	    1.获取文档的根节点.
	      Element root = document.getRootElement();
	    2.取得某个节点的子节点.
	      Element element=node.element(“四大名著");
	    3.取得节点的文字
	        String text=node.getText();
	    4.取得某节点下所有名为“csdn”的子节点，并进行遍历.
	       List nodes = rootElm.elements("csdn"); 
	       for (Iterator it = nodes.iterator(); it.hasNext();) {  
			   Element elm = (Element) it.next();
		  	// do something
	 		}
	    5.对某节点下的所有子节点进行遍历.    
	      for(Iterator it=root.elementIterator();it.hasNext();){   
	        Element element = (Element) it.next();      
	       	// do something 
	 	  }
	    6.在某节点下添加子节点
	      Element elm = newElm.addElement("朝代");
	    7.设置节点文字.  elm.setText("明朝");
	    8.删除某节点.//childElement是待删除的节点,parentElement是其父节点  parentElement.remove(childElment);
	    9.添加一个CDATA节点.Element contentElm = infoElm.addElement("content");contentElm.addCDATA(“cdata区域”);
		    
   */
 
	/**
	 * 使用DOM4j来解析XML,获取某个节点下的所有属性及值,并返回Map<String, String>
	 * @param document		XML文档解析器
	 * @param columName		列名
	 * @return Map<String, String> 将列的配置信息都封闭到 map 中
	 */
	public static Map<String, String> Dom4jXMLToMap(org.dom4j.Document document, 
		String columName) {         
		org.dom4j.Element root = document.getRootElement();
		Map<String, String> map = new HashMap<String, String>();
		try {
			//遍历所有的元素节点
			org.dom4j.Element submit = root.element(columName);
			
			@SuppressWarnings("unchecked")
			List<org.dom4j.Element> list = submit.elements();
			if (list != null  && list.size() > 0) {
				for (org.dom4j.Element e : list) {
					map.put(e.getName(), e.getText());
				}
			}
		} catch (Exception e) {
		    log.error("Dom4jXML转换为Map出错：", e);
		}
		return map;
	}     
}
