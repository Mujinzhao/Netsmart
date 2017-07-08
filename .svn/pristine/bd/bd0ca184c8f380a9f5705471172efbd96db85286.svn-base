package datacvg.excel.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;



import datacvg.excel.action.ExcelParserXmlController;
import datacvg.excel.entity.ParserDB;

/**
 * 解析配置文件操作类
 * @author admin
 *
 */
public class XmlParser {
	    private static final Logger excel = Logger.getLogger(XmlParser.class);
	    private Document doc=null;//获取DOM对象
	    private Element root=null;//获取Element 根目录对象
	    public Element getRoot() {
			return root;
		}
		public void setRoot(Element root) {
			this.root = root;
		}
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
	    public XmlParser(){
		}
	   /**
	     *构造函数说明：       <p>
	     *参数说明：@param path  xml文件路径 <p>
		 **/
		public XmlParser(String path){
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
				SAXReader saxReader = new SAXReader();
				
				doc = saxReader.read(new FileInputStream(new File(path)));
			}catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				excel.error("DocumentException XML模板异常"+e.getMessage());
			}catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	            excel.error("IOException 读取"+path+"文件异常"+e.getMessage());
	        }
		}
		public Element getRootElement(){
			root= doc.getRootElement();
			return root;
		}
		// 获取所有子元素
		public List<Element> getChildElement(Element root){
			List<Element> childList = root.elements();
			return childList;
		}
		//获取特定名称的子元素
		public List<Element> getChildElement(Element root,String TagName){
			List<Element> childList = root.elements(TagName);
			return childList;
		}
		/**
		 * 需要两个参数：配置文件名，sleep时间（分钟）
		 * @param args
		 */
		public static void main(String[] args) {
				String excelPath = ".\\ExcelTemplate.xml";
				if(args.length > 0){
					excelPath = args[0];
				}
				//获取解析文件地址
//				excelPath = SystemConstant.get("templatePath");
				excelPath="E:\\360YunPan\\项目文档\\管理创新\\指标数据_V1.4.xls";
//				ExcelControllerXml parserxml1 = new ExcelControllerXml(excelPath);
//				parserxml1.parserXmlStructToDb();
				ParserDB.parserxmlFile="E:\\360YunPan\\imsworkpase\\DMC\\src\\parser\\指标数据.xml";

				ExcelParserXmlController parserxml = new ExcelParserXmlController(excelPath);
				try{
					parserxml.parserXmlToDb("1",null);
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
//				
			
			}
			
//			XmlParser xmlparser = new XmlParser("D:\\UDFILE\\UPLOAD\\TEMPLATE\\ExcelTemplate.xml");
//			Element root = xmlparser.getRootElement();
////			List<Element> childEments = xmlparser.getChildElement(root);
//			List<Element> structurements = xmlparser.getChildElement(root,"TemplateStructure");
//			System.out.println(structurements.size());
//			 for(Element childement :structurements){  
//				 List<Element> field =  xmlparser.getChildElement(childement,"structure");
//				 for(Element fieldement :field){  
//					 System.out.println( fieldement.attributeValue("structcode"));
//					 System.out.println( fieldement.attributeValue("owner"));
//					 System.out.println( fieldement.attributeValue("savetable"));
//					 System.out.println( fieldement.attributeValue("sheetname"));
//					 System.out.println( fieldement.attributeValue("tabledesc"));
//				 }
//			 }
//			 List<Element> childEments = xmlparser.getChildElement(root,"TemplateFileds");
//			 System.out.println(childEments.size());
//			 for(Element childement :childEments){  
//				 List<Element> field =  xmlparser.getChildElement(childement,"field");
//				 System.out.println(field.size());
//			 }
			  
//			Element structelement = xmlparser.getRootElement();
//			NodeList structList =structelement.getChildNodes();
//					//structelement.getElementsByTagName("TemplateStructure");
//			 for(int i=0;i<structList.getLength();i++){  
//	                Node employee=structList.item(i);  
//	                NodeList employeeInfo=employee.getChildNodes();  
//	                for(int j=0;j<employeeInfo.getLength();j++){  
//	                    Node node=employeeInfo.item(j);  
//	                    NodeList employeeMeta=node.getChildNodes();  
//	                    for(int k=0;k<employeeMeta.getLength();k++){  
//	                        System.out.println(employeeMeta.item(k).getNodeName()+":"+employeeMeta.item(k).getTextContent());  
//	                    }  
//	                }  
//	            }  
//			System.out.println(structList.getLength());
}
