package datacvg.parse.action;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.watij.webspec.dsl.WebSpec;

import com.datacvg.crawler.general.ie.GetNodebyAttr;

import core.BasePagingQueryAction;
import core.spider.entity.RuleTemplate;
import core.spider.entity.StaticVar;
import core.spider.entity.TagBean;
import core.spider.pagedetail.RuleExtract;
import core.spider.pagedetail.TagExtract;
import core.util.MyURLDecoder;
import core.util.RegexValidate;
import core.util.WDWUtil;
import datacvg.gather.util.ParseDownload;
import datacvg.parse.service.ParserTaskService;


/**
 *  标签规则解析类
 * @author Administrator
 *
 */
public class TagParserFiledAction extends BasePagingQueryAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ParserTaskService parsertaskService;
	public ParserTaskService getParsertaskService() {
		return parsertaskService;
	}
	public void setParsertaskService(ParserTaskService parsertaskService) {
		this.parsertaskService = parsertaskService;
	}
	/**
	 *  标签规则验证  综合解析
	 * @return
	 * @throws Exception
	 */
	public String checktagparser()throws Exception
	{
		// 标签迭代解析类
		TagExtract tagex = new TagExtract();
		Map<String, String> paraMap = getParamMap(request);
		// 是否列转行解析   false 表示不需要，true 表示需要
		String rowtocell = MyURLDecoder.decode(request.getParameter("rowtocell"), "UTF-8");
	  
	    //获取解析方式
	    String parsetype=MyURLDecoder.decode(request.getParameter("checktype"), "UTF-8");
	    Parser parser =null;
	
	    String testUrl = MyURLDecoder.decode(request.getParameter("testUrl"), "UTF-8");
        String encode = MyURLDecoder.decode(request.getParameter("encode"), "UTF-8");
        String mes;
        String htmlSource=null;
 		try{
 			    //本地文件解析验证
 				if(parsetype.equals("0")){
 					 // 获取本地html 数据源解析
// 					Object obj=this.request.getSession().getAttribute("htmlSource");
 					Object obj=StaticVar.htmlsource.get("htmlSource");
 					if(obj!=null &&!"".equals(obj)){
 						htmlSource = (String)obj;
 						
 					}else{
 						mes = "解析失败 没有成功上传需解析的 html文件信息";
 						write(mes);
 						return null;
 					}
 				}else{
// 					    htmlSource = ParseDownload.RequestHTML(testUrl, encode);
 					WebSpec gatherWeb = new WebSpec().ie();
 					if (gatherWeb.isVisible())
 					{
 						gatherWeb.hide();
 					}
 					// 打开地址
 					gatherWeb.open(testUrl);
 					while (!gatherWeb.ready())
 					{
 						Thread.sleep(3000);
 					}
 					htmlSource = gatherWeb.source();
// 						// URL解析
// 					    parser = new Parser(testUrl);
 				}
 			parser = Parser.createParser(htmlSource, encode);
		    String tabcj = MyURLDecoder.decode(request.getParameter("tabparam"), "UTF-8");
		    String[] tabtag = tabcj.split("[$]");
		    
		    String trcj  = MyURLDecoder.decode(request.getParameter("trparam"), "UTF-8");
		    String[]trtag= trcj.split("[$]");
		    
		    String tdcj  = MyURLDecoder.decode(request.getParameter("tdparam"), "UTF-8");
		    String[] tdtag = tdcj.split("[$]");
		    // 将table、tr、td 转换成数组
		    TagBean[]  tagbean = getTagBeaninfo(tabtag, trtag, tdtag); 
			   
		    //解析字段与表中列的对应下标
		    String sql ="select t.fieldindex,t.acqfield from spider_parse_rule t where t.taskcode=:taskcode and t.structuredcode=:structuredid and t.fieldindex is not null ";
		  
//		    String sql = "select s.fieldindex,t.comments from spider_parse_rule s,all_col_comments t" +
//		    		"  where t.table_name = s.datatable and t.column_name = s.acqfield "+
//                    " and s.structuredcode=:structuredid  and s.tagruleid=1 and s.fieldindex is not null";
		    //SystemConstant.get("");
		    HashMap<Integer, String> relationMap   =parsertaskService.getTagindex(sql, paraMap);
//		    		SelectTreeUtil.getFiledIndexInfo("GETFILEDINDEX",taskcode,taskindex);
		    if(relationMap == null || relationMap.size() <= 0)
		    {
		    	// 表示规则表中不存在字段的对应关系
		    	write("warring");
			    return null;	
		    }
		    else
		    {
		    	String parsucc = "";
		    	String tmpsucc = "";
		    	int i =0;
		    	int j =1;
			    List<HashMap<String, String>> taglist = tagex.testmoreTable(tagbean, relationMap, parser,rowtocell); // 获取标签规则解析值
			    if(null != taglist)
			    {
					    	// 迭代 标签规则值
					    	for(HashMap<String, String> tagdata :taglist)
							{
					    		// 迭代规则字段信息
								for(Entry<Integer, String> entry : relationMap.entrySet())
								{
									parsucc +=tagdata.get(entry.getValue())+" "; // 迭代出每个规则字段解析的值
									i++;
									if(i == (relationMap.size()*j))// 判断 当前解析规则字段列数，进行换行
									{
										tmpsucc += parsucc+"\n";
										parsucc ="";
										j++;
									}
								}
							}
					}	
			     if(!tmpsucc.equals(""))
				 {
			    	 write(tmpsucc);
//			    	 if(i <= 3) //当解析字段值 为三列  已三列作为依据换行
//			    	 {
//			    		 write(tmpsucc);
//			    	 }
//			    	 else
//			    	 {
//			    		 write(tmpsucc); // 判断 当前解析规则字段列数，进行换行
//			    	 }
				 }
			     else
			     {
			    	write("标签规则没有解析出值");// 标签规则没有解析出值
			     }
		    }
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			write("解析异常");
		}
	    return null;	
	}
	
	/**
	 * 元搜索解析验证
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String checkMateParser() throws UnsupportedEncodingException{
	    //获取解析方式
	    String parsetype=MyURLDecoder.decode(request.getParameter("checktype"), "UTF-8");
	    Parser parser =null;
	    String testUrl = MyURLDecoder.decode(request.getParameter("testUrl"), "UTF-8");
        String encode = MyURLDecoder.decode(request.getParameter("encode"), "UTF-8");
        String mes;
        String htmlSource=null;
        RuleExtract er = new RuleExtract();
        List<HashMap<String, String>> taglist  = new ArrayList<HashMap<String,String>>();
        HashMap<String, String> ruleresult =null;
        Map<String, String> paraMap = getParamMap(request);
 		try{
 			
 			    //本地文件解析验证
 				if(parsetype.equals("0")){
 					 // 获取本地html 数据源解析
 					Object obj=StaticVar.htmlsource.get("htmlSource");
 					if(obj!=null &&!"".equals(obj)){
 						htmlSource = (String)obj;
 						
 					}else{
 						mes = "解析失败 没有成功上传需解析的 html文件信息";
 						write(mes);
 						return null;
 					}
 				}else{
 					    htmlSource = ParseDownload.RequestHTML(testUrl, encode);
 				}
 			  parser = Parser.createParser(htmlSource, encode);
 			  List<TagBean> tagbeans = getTagBeaninfo(request);
 			  //获取前后缀解析集合
 			  String sql ="select acqfield,ruleprefix,rulesuffix,extractregex,excludregex from spider_parse_rule where taskcode=:taskcode and structuredcode=:structuredid";
 			  List<RuleTemplate>  ruletemplateList =  parsertaskService.getRuleTemplate(sql, paraMap);
 			  if(tagbeans != null && tagbeans.size()>0){
 				  int k=0;
 				  int n=1;
				  String parsucc = "";
		    	  String tmpsucc = "";
 					for(int i=0;i<tagbeans.size();i++){
 						NodeList divlist = GetNodebyAttr.getNodeByAttribute(parser, tagbeans.get(i).getTagName(),
 								tagbeans.get(i).getTagAttr(),tagbeans.get(i).getTagAttrVal());
 						if (divlist == null) {
 		 					return null;
 		 				}
 						//迭代标签
 		 				for(int j=0;j<divlist.size();j++){
 		 					Node divNode = divlist.elementAt(j);
 		 					if(null != divNode){
 		 					    htmlSource = divNode.toHtml();
 		 						ruleresult = er.getMateFieldCon(ruletemplateList, htmlSource);
 		 						// 解析值跟存储值相等
 		 						if(ruleresult.size() == ruletemplateList.size()){
 		 							taglist.add(ruleresult);
 		 						}
 		 					}
 		 				}
 					}
 					
 					if(null != taglist)
 				    {
				    	// 迭代 前后缀规则值
				    	for(HashMap<String, String> tagdata :taglist)
						{
				    		// 迭代解析规则字段
				    		for(RuleTemplate ruleTemplate: ruletemplateList){
				    			parsucc +=tagdata.get(ruleTemplate.getFieldName())+"\n "; 
				    			k++;
					    		// 判断 当前解析规则字段列数，进行换行
								if(k == (ruletemplateList.size()*n))
								{
									tmpsucc += parsucc+"\n";
									parsucc ="";
									n++;
								}
				    		}
						}
 					}	
 					if(!tmpsucc.equals(""))
 					{
 				    	 if(k <= 3)
 				    	 {
 				    		 write(tmpsucc);
 				    	 }
 				    	 else
 				    	 {
 				    		 write(tmpsucc); // 判断 当前解析规则字段列数，进行换行
 				    	 }
 					 }
 				     else
 				     {
 				    	write("标签规则没有解析出值");// 标签规则没有解析出值
 				     }
 					
 			  }
 		}catch (Exception e) {
			// TODO: handle exception
 			e.printStackTrace();
		}
 		return null;
	}
	
	
	/**
	 * 保存标签规则
	 * @return
	 */
	public String savetagparser(){
//		String tabcj = MyURLDecoder.decode(request.getParameter("tabparam");
//		String[] tabtag = tabcj.split("[$]");
//		
//	    String trcj  = MyURLDecoder.decode(request.getParameter("trparam");
//	    String[]trtag= trcj.split("[$]");
//	    
//	    String tdcj  = MyURLDecoder.decode(request.getParameter("tdparam");
//	    String[] tdtag = tdcj.split("[$]");
	    
	    List<TagBean> tagbeans = getTagBeaninfo(request);
		String sql ="insert into spider_parse_tagrule values(:id,:taskcode,:structuredid,:tagcode,:partagcode,:tagname,:tagattr,:tagattrval,:bindex,:eindex,:parsertype,:rowcellnum,sysdate,null)";
		int sequencenum = 0;
		Transaction tran = null;
		Session session = parsertaskService.getMySessionFactory().openSession();
		 try{
		    tran = session.beginTransaction();
		    tran.begin();
			Map<String, String> paraMap = getParamMap(request);
			if(tagbeans != null && tagbeans.size()>0){
				for(int i=0;i<tagbeans.size();i++){
					String seqsql ="SELECT spider_tagcode.NEXTVAL from dual";
					sequencenum = parsertaskService.getTagSequence(seqsql);
					paraMap.put("tagcode",    sequencenum+"");
					if(i==0){
						paraMap.put("partagcode", i+"");
					}else{
						paraMap.put("partagcode", (sequencenum-1)+"");
					}
					paraMap.put("id",         tagbeans.get(i).getId());
					paraMap.put("tagname",    tagbeans.get(i).getTagName());
					paraMap.put("tagattr",    tagbeans.get(i).getTagAttr());
					paraMap.put("tagattrval", tagbeans.get(i).getTagAttrVal());
					paraMap.put("bindex",     tagbeans.get(i).getBegindexs());
					paraMap.put("eindex",     tagbeans.get(i).getEndindexs());
					paraMap.put("rowcellnum", tagbeans.get(i).getGlcellrows());
					parsertaskService.ParserExeSql(sql, paraMap);
				}
				tran.commit();
				write("操作成功");
			}
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tran.rollback();
			write("操作异常");
		}finally{
			 try{
				 if(session != null){
					parsertaskService.getMySessionFactory().closeSession(session);
				 } 
			 }catch (Exception e) {
				// TODO: handle exception
				 write("操作异常");
			}
		 }
		return null;
	}
	
	/**
	 * 删除修改规则
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String deltagparsser() throws UnsupportedEncodingException{
		 Map<String, String> paramMap = getParamMap(request);
 		 String sql ="delete from  spider_parse_tagrule s where  s.id in ("+paramMap.get("id").toString()+")";//(:id,:taskcode,:structuredid,:datatable,:acqfield,:prefixrule,:suffixrule,:extractregex,:outregex,'',:tagindex,'',sysdate,'')";
 		 try{
 			 Integer executenum =parsertaskService.OparserSql(sql);
 			 if(executenum > 0){
 				 write("操作成功");
 			 }else{
 				 write("操作失败");
 			 }
 			  
 		 }catch (Exception e) {
 			// TODO: handle exception
 			 write("操作异常");
 		 }
 		
 		 return null;
	}
	/**
	 * 修改标签规则信息
	 */
	public void updatetagparser(){
//		String tabcj = MyURLDecoder.decode(request.getParameter("tabparam");
//		String[] tabtag = tabcj.split("[$]");
//		
//	    String trcj  = MyURLDecoder.decode(request.getParameter("trparam");
//	    String[]trtag= trcj.split("[$]");
//	    
//	    String tdcj  = MyURLDecoder.decode(request.getParameter("tdparam");
//	    String[] tdtag = tdcj.split("[$]");
	    
		List<TagBean> tagbeans  = getTagBeaninfo(request);
		String sql ="update spider_parse_tagrule  t set t.tagname=:tagname,t.tagattr=:tagattr," +
				"t.tagattrval=:tagattrval,t.bindex=:bindex,t.eindex=:eindex,t.glrowcellnum=:rowcellnum,t.creatime=sysdate " +
				"where t.id=:id";
		Transaction tran = null;
		Session session = parsertaskService.getMySessionFactory().openSession();
		 try{
		    tran = session.beginTransaction();
		    tran.begin();
			Map<String, String> paraMap = getParamMap(request);
			if(tagbeans != null && tagbeans.size()>0){
				for(int i=0;i<tagbeans.size();i++){
					paraMap.put("id",         tagbeans.get(i).getId());
					paraMap.put("tagname",    tagbeans.get(i).getTagName());
					paraMap.put("tagattr",    tagbeans.get(i).getTagAttr());
					paraMap.put("tagattrval", tagbeans.get(i).getTagAttrVal());
					paraMap.put("bindex",     tagbeans.get(i).getBegindexs());
					paraMap.put("eindex",     tagbeans.get(i).getEndindexs());
					paraMap.put("rowcellnum", tagbeans.get(i).getGlcellrows());
					if(RegexValidate.StrNotNull(tagbeans.get(i).getId())){
						parsertaskService.ParserExeSql(sql, paraMap);
					}
				}
				tran.commit();
				write("操作成功");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tran.rollback();
			write("操作异常");
		}finally{
			 try{
				 if(session != null){
						parsertaskService.getMySessionFactory().closeSession(session);
				 } 
			 }catch (Exception e) {
				// TODO: handle exception
				 e.printStackTrace();
				 write("操作异常");
			}
		 }
	}
	
	/**
	 *  存储迭代标签中的信息 转换成数组
	 * @param tabtag
	 * @param trtag
	 * @param tdtag
	 * @return
	 */
	public TagBean[] getTagBeaninfo(String[] tabtag,String[] trtag,String[] tdtag)
	{
		TagBean tabbean = new TagBean();
		TagBean trbean = new TagBean();
		TagBean tdbean = new TagBean();
		TagBean[] tagbean = null;
	
		 try {
			 // 获取第一行标签数据
			 if(tabtag != null &&  tabtag.length>0){
			    /*    获取                                            */
			    tabbean.setTagName(tabtag[0].replaceAll("'", ""));
			    tabbean.setTagAttr(tabtag[1].replaceAll("'", ""));
			    tabbean.setTagAttrVal(tabtag[2].replaceAll("'", ""));
			    if(RegexValidate.StrNotNull(tabtag[3]) && !"''".equals(tabtag[3]))
			    {
//			    	 tabbean.setBegIndex(Integer.parseInt(String.valueOf(tabtag[3].replaceAll("'", "")) == null ? "0":String.valueOf(tabtag[3].replaceAll("'", "")) ));
			    	 tabbean.setBegindexs(String.valueOf(tabtag[3].replaceAll("'", "")) == null ? "0":String.valueOf(tabtag[3].replaceAll("'", "")));
			    }else{
			    	tabbean.setBegindexs("0");
			    }
			    if(RegexValidate.StrNotNull(tabtag[4]) && !"''".equals(tabtag[4]))
			    {
//			    	 tabbean.setEndIndex(Integer.parseInt(String.valueOf(tabtag[4].replaceAll("'", ""))));
			    	 tabbean.setEndindexs(String.valueOf(tabtag[4].replaceAll("'", "")));
			    }else{
			      	 tabbean.setEndindexs("0");
			    }
			    if(RegexValidate.StrNotNull(tabtag[6]) && !"''".equals(tabtag[6]))
			    {
			    	 tabbean.setId(String.valueOf(tabtag[6].replaceAll("'", "")));
			    }else{
			    	 tabbean.setId(WDWUtil.getSeqNextval());
			    }
			 }
			 // 获取第二行标签数据
			 if(trtag != null &&  trtag.length>0){
			    trbean.setTagName(trtag[0].replaceAll("'", ""));
			    if(RegexValidate.StrNotNull(trtag[1]) && !"''".equals(trtag[1]))
			    {
				    trbean.setTagAttr(trtag[1].replaceAll("'", "") == null ? "" : trtag[1].replaceAll("'", ""));
			    }
			    if(RegexValidate.StrNotNull(trtag[2])  && !"''".equals(trtag[2]))
			    {
			    	trbean.setTagAttrVal(trtag[2].replaceAll("'", "") == null ? "" : trtag[2].replaceAll("'", ""));
			    }
			    if(RegexValidate.StrNotNull(trtag[3])  && !"''".equals(trtag[3]))
			    {
//			    	trbean.setBegIndex(Integer.parseInt(trtag[3].replaceAll("'", "")));//String.valueOf(trtag[3].replaceAll("'", ""))));
			    	trbean.setBegindexs(String.valueOf(trtag[3].replaceAll("'", "")));
			    }else{
			    	trbean.setBegindexs("0");
			    }
			    if(RegexValidate.StrNotNull(trtag[4])  && !"''".equals(trtag[4]))
			    {
//			    	trbean.setEndIndex(Integer.parseInt(trtag[4].replaceAll("'", "")));
			    	trbean.setEndindexs(String.valueOf(trtag[4].replaceAll("'", "")));
			    }else{
			    	trbean.setEndindexs("0");
			    }
			  
			    if(RegexValidate.StrNotNull(trtag[5])  && !"''".equals(trtag[5]))
			    {
			    	trbean.setGlcellrows(String.valueOf(trtag[5].replaceAll("'", "")));
			    }
			    if(RegexValidate.StrNotNull(trtag[6]) && !"''".equals(trtag[6]))
			    {
			    	trbean.setId(String.valueOf(trtag[6].replaceAll("'", "")));
			    }else{
			    	trbean.setId(WDWUtil.getSeqNextval());
			    }
			    
			 }
			// 获取第三行标签数据
			 if(tdtag != null &&  tdtag.length>0){
			    
			    tdbean.setTagName(tdtag[0].replaceAll("'", ""));
			    if(RegexValidate.StrNotNull(tdtag[1])  && !"''".equals(tdtag[1]))
			    {
			    	tdbean.setTagAttr(tdtag[1].replaceAll("'", "") == null ? "" : tdtag[1].replaceAll("'", ""));
			    }
			    if(RegexValidate.StrNotNull(tdtag[2])   && !"''".equals(tdtag[2]))
			    {
			    	tdbean.setTagAttrVal(tdtag[2].replaceAll("'", "") == null ? "" : tdtag[2].replaceAll("'", ""));
			    }
			    if(RegexValidate.StrNotNull(tdtag[3])   && !"''".equals(tdtag[3]))
			    {
//			    	tdbean.setBegIndex(Integer.parseInt(tdtag[3].replaceAll("'", "")));
			    	tdbean.setBegindexs(String.valueOf(tdtag[3].replaceAll("'", "")));
			    }
			    else{
			    	tdbean.setBegindexs("0");
			    }
			    if(RegexValidate.StrNotNull(tdtag[4])  && !"''".equals(tdtag[4]))
			    {
//			    	tdbean.setEndIndex(Integer.parseInt(tdtag[4].replaceAll("'", "")));
			    	tdbean.setEndindexs(String.valueOf(tdtag[4].replaceAll("'", "")));
			    }else{
			    	tdbean.setEndindexs("0");
			    }
			    if(RegexValidate.StrNotNull(tdtag[6]) && !"''".equals(tdtag[6]))
			    {
			    	tdbean.setId(String.valueOf(tdtag[6].replaceAll("'", "")));
			    }else{
			    	tdbean.setId(WDWUtil.getSeqNextval());
			    }
			 }
			 tagbean = new TagBean[]{tabbean,trbean,tdbean};
		} catch (Exception e) { 
			// TODO: handle exception
			e.printStackTrace(); 
		}
		 return   tagbean; 
	} 
	
	/**
	 *  存储迭代标签中的信息 转换成数组
	 * @param tabtag
	 * @param trtag
	 * @param tdtag
	 * @return
	 */
	public List<TagBean> getTagBeaninfo(HttpServletRequest request)
	{
		 List<TagBean> tagBeanList = new ArrayList<TagBean>();
		 try {
			 String tabcj = MyURLDecoder.decode(request.getParameter("tabparam"), "UTF-8");
			 // 获取第一行标签数据
			 if(RegexValidate.StrNotNull(tabcj)){
				TagBean tabbean = new TagBean();
				String[] tabtag = tabcj.split("[$]");
			    /*    获取                                            */
			    tabbean.setTagName(tabtag[0].replaceAll("'", ""));
			    tabbean.setTagAttr(tabtag[1].replaceAll("'", ""));
			    tabbean.setTagAttrVal(tabtag[2].replaceAll("'", ""));
			    if(RegexValidate.StrNotNull(tabtag[3]) && !"''".equals(tabtag[3]))
			    {
//			    	 tabbean.setBegIndex(Integer.parseInt(String.valueOf(tabtag[3].replaceAll("'", "")) == null ? "0":String.valueOf(tabtag[3].replaceAll("'", "")) ));
			    	 tabbean.setBegindexs(String.valueOf(tabtag[3].replaceAll("'", "")) == null ? "0":String.valueOf(tabtag[3].replaceAll("'", "")));
			    }else{
			    	tabbean.setBegindexs("0");
			    }
			    if(RegexValidate.StrNotNull(tabtag[4]) && !"''".equals(tabtag[4]))
			    {
//			    	 tabbean.setEndIndex(Integer.parseInt(String.valueOf(tabtag[4].replaceAll("'", ""))));
			    	 tabbean.setEndindexs(String.valueOf(tabtag[4].replaceAll("'", "")));
			    }else{
			      	 tabbean.setEndindexs("0");
			    }
			    if(RegexValidate.StrNotNull(tabtag[6]) && !"''".equals(tabtag[6]))
			    {
			    	 tabbean.setId(String.valueOf(tabtag[6].replaceAll("'", "")));
			    }else{
			    	 tabbean.setId(WDWUtil.getSeqNextval());
			    }
			    tagBeanList.add(tabbean);
			 }
			 // 获取第二行标签数据
			  String trcj  = MyURLDecoder.decode(request.getParameter("trparam"), "UTF-8");
			 if(RegexValidate.StrNotNull(trcj)){
				TagBean trbean = new TagBean();
				String[]trtag= trcj.split("[$]");
			    trbean.setTagName(trtag[0].replaceAll("'", ""));
			    if(RegexValidate.StrNotNull(trtag[1]) && !"''".equals(trtag[1]))
			    {
				    trbean.setTagAttr(trtag[1].replaceAll("'", "") == null ? "" : trtag[1].replaceAll("'", ""));
			    }
			    if(RegexValidate.StrNotNull(trtag[2])  && !"''".equals(trtag[2]))
			    {
			    	trbean.setTagAttrVal(trtag[2].replaceAll("'", "") == null ? "" : trtag[2].replaceAll("'", ""));
			    }
			    if(RegexValidate.StrNotNull(trtag[3])  && !"''".equals(trtag[3]))
			    {
//			    	trbean.setBegIndex(Integer.parseInt(trtag[3].replaceAll("'", "")));//String.valueOf(trtag[3].replaceAll("'", ""))));
			    	trbean.setBegindexs(String.valueOf(trtag[3].replaceAll("'", "")));
			    }else{
			    	trbean.setBegindexs("0");
			    }
			    if(RegexValidate.StrNotNull(trtag[4])  && !"''".equals(trtag[4]))
			    {
//			    	trbean.setEndIndex(Integer.parseInt(trtag[4].replaceAll("'", "")));
			    	trbean.setEndindexs(String.valueOf(trtag[4].replaceAll("'", "")));
			    }else{
			    	trbean.setEndindexs("0");
			    }
			  
			    if(RegexValidate.StrNotNull(trtag[5])  && !"''".equals(trtag[5]))
			    {
			    	trbean.setGlcellrows(String.valueOf(trtag[5].replaceAll("'", "")));
			    }
			    if(RegexValidate.StrNotNull(trtag[6]) && !"''".equals(trtag[6]))
			    {
			    	trbean.setId(String.valueOf(trtag[6].replaceAll("'", "")));
			    }else{
			    	trbean.setId(WDWUtil.getSeqNextval());
			    }
			    tagBeanList.add(trbean);
			 }
			// 获取第三行标签数据
			  String tdcj  = MyURLDecoder.decode(request.getParameter("tdparam"), "UTF-8");
			 if(RegexValidate.StrNotNull(tdcj)){
				TagBean tdbean = new TagBean();
				String[] tdtag = tdcj.split("[$]");
			    tdbean.setTagName(tdtag[0].replaceAll("'", ""));
			    if(RegexValidate.StrNotNull(tdtag[1])  && !"''".equals(tdtag[1]))
			    {
			    	tdbean.setTagAttr(tdtag[1].replaceAll("'", "") == null ? "" : tdtag[1].replaceAll("'", ""));
			    }
			    if(RegexValidate.StrNotNull(tdtag[2])   && !"''".equals(tdtag[2]))
			    {
			    	tdbean.setTagAttrVal(tdtag[2].replaceAll("'", "") == null ? "" : tdtag[2].replaceAll("'", ""));
			    }
			    if(RegexValidate.StrNotNull(tdtag[3])   && !"''".equals(tdtag[3]))
			    {
//			    	tdbean.setBegIndex(Integer.parseInt(tdtag[3].replaceAll("'", "")));
			    	tdbean.setBegindexs(String.valueOf(tdtag[3].replaceAll("'", "")));
			    }
			    else{
			    	tdbean.setBegindexs("0");
			    }
			    if(RegexValidate.StrNotNull(tdtag[4])  && !"''".equals(tdtag[4]))
			    {
//			    	tdbean.setEndIndex(Integer.parseInt(tdtag[4].replaceAll("'", "")));
			    	tdbean.setEndindexs(String.valueOf(tdtag[4].replaceAll("'", "")));
			    }else{
			    	tdbean.setEndindexs("0");
			    }
			    if(RegexValidate.StrNotNull(tdtag[6]) && !"''".equals(tdtag[6]))
			    {
			    	tdbean.setId(String.valueOf(tdtag[6].replaceAll("'", "")));
			    }else{
			    	tdbean.setId(WDWUtil.getSeqNextval());
			    }
			    tagBeanList.add(tdbean);
			 }
			
		} catch (Exception e) { 
			// TODO: handle exception
			e.printStackTrace(); 
		}
		 return   tagBeanList; 
	} 
    /**
     * 页面传入后台参数  增 删 改 查 操作
     * @throws UnsupportedEncodingException 
     */
    public Map<String, String> getParamMap(HttpServletRequest request) throws UnsupportedEncodingException{
    	 Map<String, String> paramMap = new HashMap<String, String>();
    	 String id = MyURLDecoder.decode(request.getParameter("id"), "UTF-8");
 		 paramMap.put("id", RegexValidate.StrNotNull(id)==true?id:WDWUtil.getSeqNextval());
 		
 		String taskcode = MyURLDecoder.decode(request.getParameter("taskcode"), "UTF-8");
		paramMap.put("taskcode", taskcode);
		
	    String taskindex= MyURLDecoder.decode(request.getParameter("taskindex"), "UTF-8");
	    paramMap.put("taskindex", taskindex);
	    
		String structuredid = getStringParameter("structuredid");
		paramMap.put("structuredid", structuredid);
		// 表名称
		String asstable =  getStringParameter("asstable");
		paramMap.put("asstable", asstable);
		
		//解析类型
		String parsertype = getStringParameter("parsertype");
		paramMap.put("parsertype", parsertype);
		//表字段
	    String parsefiled =  getStringParameter("parsefiled");
		paramMap.put("parsefiled", parsefiled);
		
		//字段下标
		String filedindex = MyURLDecoder.decode(request.getParameter("filedindex"), "UTF-8");
		paramMap.put("filedindex", filedindex);
		
		
    	return paramMap;
    }
   
}
