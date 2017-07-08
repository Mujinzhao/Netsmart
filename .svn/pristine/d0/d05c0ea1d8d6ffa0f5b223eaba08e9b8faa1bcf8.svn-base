package datacvg.gather.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.watij.webspec.dsl.WebSpec;
/**
 * 采集标签提取检测
 * @author maqianli
 *
 */
public class ExtractingGatherTags {

	/**
	 * 标签提取
	 * @param url 网页链接
	 * @param txtkey 提取列表数据关键词
	 * @param level  检测深度
	 * @return
	 * @throws Exception
	 */
	public static List<Map> extractingTags(String url,String txtkey,int level)throws Exception{
		
		Document doc = Jsoup.connect(url).get();
		Elements eles=doc.getElementsContainingOwnText(txtkey);
	    //System.out.println("SIZE:"+eles.size());
		Map map = new HashMap();
		int i=0;
		for (Element element : eles) {
		
			//System.out.println("tagname:"+element.tagName());
			Element elep=getEleClass(element);
			
			List<Element> listele =getEleLevel(element,level);
			
			if(elep.tagName()!=element.tagName()){
				
				List<String> list = new ArrayList<String>();
			
				String temstr="";
				int strartj=listele.size()-1;
				for(int j=strartj;j>=0;j--){
					
					if(listele.get(j).className()!=null&&!"".equals(listele.get(j).className())){
						
						temstr+=listele.get(j).tagName()+"."+listele.get(j).className().replaceAll(" ", ".")+" ";
						list.add(listele.get(j).tagName()+"."+listele.get(j).className());
						
					}else{
						temstr+=listele.get(j).tagName()+" ";
						list.add(listele.get(j).tagName());
					}
					
					
				}
				list.add(temstr);
				map.put(i,list);
				
				
			}else{
				
				List<String> list = new ArrayList<String>();
				list.add(elep.tagName()+"."+elep.className());
				list.add(elep.tagName()+"."+elep.className().replace(" ", "."));
				map.put(i,list);
			}
			//System.out.println("tagname:"+elep.tagName());
			//System.out.println("classname:"+elep.className());
			i++;
		}
		List<Map> listmap = new ArrayList<Map>();
		for(int j=0;j<map.size();j++){
			List<String> liststr=(List<String>)map.get(j);
			Elements mapeles = doc.select(liststr.get(liststr.size()-1));
	        //System.out.println("selector:"+liststr.get(liststr.size()-1));
			List<String> listlinks = new ArrayList<String>();
			for (Element element2 : mapeles) {
				listlinks.add(element2.attr("href")+"  "+element2.text());
			}
			//listlinks.add(mapeles.size()+"");
			Map resmap = new HashMap();
			liststr.remove(liststr.size()-1);
			resmap.put("taglist", liststr);
			resmap.put("reslist", listlinks);
			
			listmap.add(resmap);
		} 
		
		return listmap;
	}
	
   
	/**
	 * 
	 * @param ele 标签
	 * @param level 标签层级数
	 * @return
	 */
	public static List<Element> getEleLevel(Element ele,int level){
		
		if(level<2){
			level=2;
		}
		List<Element> listeles = new ArrayList<Element>();
		Element eletemp =ele;
		for(int i=0;i<level;i++){
			 
			if(i==level-1){
				listeles.add(getEleClass(eletemp));
			}else{
				listeles.add(eletemp);
				
			}
			
			if(ele.className()!=null&&ele.className()!=""){
				break;
			}
			
			eletemp=eletemp.parent();
		}
		
		
		return listeles;
	}
	
	public static Element getEleClass(Element ele){
		
		if(ele.className()!=null&&ele.className()!=""){
			return ele;
		}
		if(ele.parent()!=null)
			return getEleClass(ele.parent());
		else
			return ele;
	}
	
	
	public static void main(String [] args) throws Exception{
		
		// Document doc = Jsoup.connect("http://dev.lyg.focus.cn/common/modules/zixun/zixunlist.php?category_id=34&class_id=383&type_id=-3").get();
		// Elements eles=doc.getElementsContainingOwnText("帝豪水榭花都庆六一");
//		List<Map> lmap=extractingTags("http://www.lygfdc.com/WebSite/Search/Default.aspx?type=spf&city=&price=&wuye=&stat=&key=%u8BF7%u8F93%u5165%u697C%u76D8%u540D%u79F0%u6216%u5F00%u53D1%u5546 ", "秀逸苏",3);
		List<Map> lmap=extractingTags("http://sh.fang.anjuke.com/fangyuan/","中环边学区房 紧邻大宁商圈 品质生活从此开启",0);
		
		if(lmap.size()==0){
			System.out.println("没有检测到任何数据！");
			return;
		}
		List<String> listr = (List<String>)lmap.get(0).get("reslist");
		List<String> listt = (List<String>)lmap.get(0).get("taglist");
		
		System.out.println("列表数据：=======>>");
		for (String stringhref : listr) {
			System.out.println(stringhref);
		}
		System.out.println("提取标签：========>>");
		for (String tatstring : listt) {
			System.out.println(tatstring);
		}
//		WebSpec web =new WebSpec();
//		web.open("/shouye/common/viewArticle.jsp?group=1001&articleID=4060738&cat=1050006&topic=0");
	}
}
