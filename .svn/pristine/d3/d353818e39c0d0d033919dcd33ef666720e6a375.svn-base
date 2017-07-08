package datacvg.excel.util;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
/**
 * 类名：XmlCreater  <p>
 * 类描述： 创建DOM并生成XML文件<p>
 * 编写者 ：sxy<p>
 * 编写日期 ：2015-04-29<p>
 * 主要public成员变量：<p>
 * 主要public方法：   <p>
 **/
public class XmlCreater{
	/*全局变量*/
    private static Logger xmllog = Logger.getLogger(XmlCreater.class);
    private Document doc=null;//新创建的DOM
    private String path=null;//生成的XML文件绝对路径
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	/**
     * @return 返回 doc。
     */
    public Document getDoc()
    {
        return doc;
    }
    /**
     * @param doc 要设置的 doc。
     */
    public void setDoc(Document doc)
    {
        this.doc = doc;
    }
	/**
     *构造函数说明：       <p>
     *参数说明：@param path  xml文件路径 <p>
	 **/
	public XmlCreater(String path){
		this.path=path;
		init();
	}
	/**
    * 方法名称：init<p>
    * 方法功能： 初始化函数<p>
    * 参数说明： <p>
    * 返回：void <p>
    * 作者：sxy
    * 日期：2015-04-29
	**/
	private  void init(){
		try
		{
			doc=DocumentHelper.createDocument();//新建Document
		}catch(Exception e){
	        xmllog.error("Parse DOM builder error:"+e);
		}
	}
	/**
    * 方法名称：createRootElement<p>
    * 方法功能：创建根结点，并返回            <p>
    * 参数说明：@param rootTagName <p>
    * 返回：Element <p>
    * 作者：sxy
    * 日期：2015-04-29
	**/
	public Element createRootElement(String rootTagName){
		if(doc.getRootElement()==null)
		{
			xmllog.debug("create root element '"+rootTagName+"' success.");
			Element root=DocumentHelper.createElement(rootTagName);
			doc.setRootElement(root);
			return root;
		}
		xmllog.warn("this dom's root element is exist,create fail.");
		return doc.getRootElement();
	}
	/**
	* 方法名称：createElement<p>
	* 方法功能：在parent结点下增加子结点tagName<p>
	* 参数说明：@param parent
	* 参数说明：@param tagName <p>
	* 返回：Element <p>
	* 作者：sxy
    * 日期：2015-04-29
    **/
	public Element createElement(Element parent,String tagName)
    {
		return parent.addElement(tagName);
	}
	/**
	* 方法名称：createElement<p>
	* 方法功能：在parent结点下增加属性，属性值<p>
	* 参数说明：@param parent
	* 参数说明：@param tagName <p>
	* 参数说明：@param value <p>
	* 返回：Element <p>
	* 作者：sxy
    * 日期：2015-04-29
    **/
	public Element createElement(Element parent,String tagName,String value){
		return parent.addElement(tagName,value);
    }
	/**
    * 方法名称：createAttribute<p>
    * 方法功能：在parent结点下增加属性 <p>
    * 参数说明：@param parent
    * 参数说明：@param attrName 属性名
    * 参数说明：@param attrValue 属性值<p>
    * 返回：void <p>
	* 作者：sxy
    * 日期：2015-04-29
    **/
	public void createAttribute(Element parent,String attrName,String attrValue){
		parent.addAttribute(attrName,attrValue);
		doc.setParent(parent);
    }
	public  Integer buildXmlFile(){
		Integer docindex = 0;
		XMLWriter writer = null;
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");//设置XML文件的编码格式,如果有中文可设置为GBK或UTF-8
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			writer = new XMLWriter(fos, format);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			writer.write(doc);
			writer.close();
			docindex =1;
		} catch (IOException e) {
			e.printStackTrace();
			docindex=-1;
		}
		return docindex;
	}
   /**
    * 方法名称：addComment<p>
    * 方法功能：在objelement结点下增加注销信息 <p>
    * 参数说明：@param objelement
    * 参数说明：@param commonstr 注释内容
    * 返回：void <p>
	* 作者：sxy
    * 日期：2015-04-29
    **/
	public void addComment(Element objelement,String commonstr){
		//commonstr = "解析模板结构信息 structcode:结构ID,sheetname:SHEET名称,owner:存储用户,savetable:存储表,tabledesc:存储表描述";
//		Document doc = objelement.getDocument();
//		Node common=doc.addComment(commonstr);
		objelement.addComment(commonstr);
	}
	public static void main(String[] args) {
		XmlCreater xmlcreate = new XmlCreater("D:\\UDFILE\\UPLOAD\\TEMPLATE\\ExcelTemplate1.xml");
		Element root = xmlcreate.createRootElement("ExcelTemplate");
		xmlcreate.createAttribute(root, "name", "PaserTemplate");
		xmlcreate.createAttribute(root, "version", "1.1");
		Element temppath =  xmlcreate.createElement(root, "TemplatePath");
		Element temppath1 =  xmlcreate.createElement(temppath, "pathName");
		temppath1.addText("指标数据");
	
		Element temppath2 =  xmlcreate.createElement(temppath, "mainPath");
		xmlcreate.createAttribute(temppath2, "name", "\\xxxx\\xxx\\xxx\\xxx.xls");
		xmlcreate.createAttribute(temppath2, "desc", "xxx");
		
		Element tempstructure =  xmlcreate.createElement(root, "TemplateStructure");
//		Node common = tempstructure.setTextContent("");
//		Node common= xmlcreate.doc..createComment("<!-- 解析模板对应文件路径 -->");
//		 xmlcreate.doc.appendChild(common);
		for (int i = 0; i <5; i++) {
			Element structure =  xmlcreate.createElement(tempstructure, "structure");
			xmlcreate.createAttribute(structure, "id", "1");
			xmlcreate.createAttribute(structure, "name", "bi_sys_index_threshold");
			xmlcreate.createAttribute(structure, "saveowner", "DAP");
			xmlcreate.createAttribute(structure, "savetable", "bi_sys_index_threshold");
			xmlcreate.createAttribute(structure, "tabledesc", "指标阈值表");
			xmlcreate.createAttribute(structure, "pinterface", "1");
			xmlcreate.createAttribute(structure, "isactive", "T");
		}
		
		Element templaterule =  xmlcreate.createElement(root, "TemplateRule");
		
		for (int i = 0; i <5; i++) {
			Element tagrule =  xmlcreate.createElement(templaterule, "tagrule");
			xmlcreate.createAttribute(tagrule, "structcode",i+"");
    		xmlcreate.createAttribute(tagrule, "srownums",1+i+"");
			xmlcreate.createAttribute(tagrule, "erownums",2+i+"");
			xmlcreate.createAttribute(tagrule, "scellnums",3+i+"");
			xmlcreate.createAttribute(tagrule, "ecellnums",4+i+"");
			xmlcreate.createAttribute(tagrule, "celltorows","false");
		}
		xmlcreate.buildXmlFile();
	}
}