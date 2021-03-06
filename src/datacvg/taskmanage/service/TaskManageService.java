package datacvg.taskmanage.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.dbcontrol.BaseDao;
import core.dbcontrol.MySessionFactory;
import core.util.RegexValidate;

import datacvg.gather.entity.EventEntity;
import datacvg.gather.entity.GatherBean;
import datacvg.gather.entity.GatherTask;
import datacvg.gather.entity.IterateDrillTag;
import datacvg.gather.entity.KeywordBean;
import datacvg.gather.entity.SIMParam;
import datacvg.gather.entity.TagGroup;
import datacvg.gather.util.TagItem;

public class TaskManageService {
	
	public BaseDao dao = null;
	private MySessionFactory mySessionFactory;
	public MySessionFactory getMySessionFactory() {
		return mySessionFactory;
	}
	public void setMySessionFactory(MySessionFactory mySessionFactory) {
		this.mySessionFactory = mySessionFactory;
	}
	public BaseDao getDao() {
		return dao;
	}
	public void setDao(BaseDao dao) {
		this.dao = dao;
	}
	public TaskManageService(){};

	  /**
	   * 获取模拟执行标签
	   * @param sql
	   * @param paramMap
	   * @return
	   * @throws Exception
	   */
	  public List<EventEntity> getEventInfo(String sql,Map<String, String>  paramMap)throws Exception{
		  List<?>  eventList = dao.selectSqlAuto(sql, paramMap);
		  List<EventEntity> listeventEntity = new ArrayList<EventEntity>();
		  if (null != eventList && eventList.size() > 0) {
				
				for (int i = 0; i < eventList.size(); i++) {
					EventEntity evententity = new EventEntity();
					Object[] obj = (Object[]) eventList.get(i);
					evententity.setTagname(String.valueOf(obj[0] == null ? "": obj[0]));
					evententity.setTagattr(String.valueOf(obj[1] == null ? "": obj[1]));
					evententity.setTagattrval(String.valueOf(obj[2] == null ? "": obj[2]));
					evententity.setEventval(String.valueOf(obj[3] == null ? "": obj[3]));
					evententity.setEventype(String.valueOf(obj[4] == null ? "": obj[4]));
					listeventEntity.add(evententity);
				}
			}
		  return listeventEntity;
		  
	  }
	
	  /**
	   * 获取组模拟标签
	   * @param taskcode
	   * @param groupcode
	   * @return
	   * @throws Exception
	   */
	  public List<TagItem> getListSimagItem(String taskcode,String groupcode)throws Exception{
		    Map<String,String> paramMap = new HashMap<String,String>();
		    paramMap.put("taskcode", taskcode);
		    paramMap.put("gcode", groupcode);
		    String sql="select event.taskcode,event.gcode,event.tagcode,event.partagcode,event.tagtype," +
		    		"event.tagname,event.tagattr,event.tagattrval,event.tagattr1val,event.tagattr1 " +
		    		" from spider_eventinfo event where event.taskcode=:taskcode ";
			
		    if(RegexValidate.StrNotNull(groupcode)){
				sql+=" and event.gcode=:gcode ";
			}
//		   sql+="order by  tagcode asc";
		    sql+=" order by to_number(event.tagcode) asc";//将oracle中的nvarchar类型转换为number类型在进行排序（数据库中的tagcode是nvarchar类型）
		    List<?>  tagitemList = dao.selectSqlAuto(sql, paramMap);
		 
			List<TagItem> listtags = new ArrayList<TagItem>();	
		    if (null != tagitemList && tagitemList.size() > 0) {
				for (int i = 0; i < tagitemList.size(); i++) {
					Object[] obj = (Object[]) tagitemList.get(i);
					TagItem tag = new TagItem();
					//组编号
					tag.setGroupCode(obj[1]==null?"":String.valueOf(obj[1]));
					// 标签编号
					tag.setTagCode(obj[2]==null?"":String.valueOf(obj[2]));
					//父级编号
					tag.setParentCode(obj[3]==null?"":String.valueOf(obj[3]));
					//标签类型
					tag.setTagType(obj[4]==null?"":String.valueOf(obj[4]));
					//标签名称
					tag.setTagName(obj[5]==null?"":String.valueOf(obj[5]));
					//标签属性
					tag.setTagAttrName(obj[6]==null?"":String.valueOf(obj[6]));
					//属性值
					tag.setTagAttrValue(obj[7]==null?"":String.valueOf(obj[7]));
					// 操作事件
					tag.setActionName(obj[8]==null?"":String.valueOf(obj[8]));
					// 操作事件值
					tag.setActionValue(obj[9]==null?"":String.valueOf(obj[9]));
//					// 是否采集
//					tag.setGather((obj[11]==null?"":String.valueOf(obj[11]))=="T"?true:false);
					listtags.add(tag);
				}
			}
			 return listtags;
	  }
	  
	 /**
	  * 获取组信息
	  * @param taskcode
	  * @return
	  * @throws Exception
	  */
	  public List<TagGroup> getTagGroupInfo(String taskcode)throws Exception{
		  String sql="select t.taskcode,t.groupcode,t.groupdesc,t.islogin,t.loginurl,t.isspider from spider_taggroup t where t.taskcode='"+taskcode+"' order by t.runsort asc";
		  List<?>  taggroupList = dao.selectSqlAuto(sql);
		  List<TagGroup> listtagGroup = new ArrayList<TagGroup>();
		  if (null != taggroupList && taggroupList.size() > 0) {
				
				for (int i = 0; i < taggroupList.size(); i++) {
					Object[] obj = (Object[]) taggroupList.get(i);
					TagGroup taggroup = new TagGroup();
					taggroup.setTaskCode(obj[0]==null?"":String.valueOf(obj[0]));
					taggroup.setGroupCode(obj[1]==null?"":String.valueOf(obj[1]));
					taggroup.setGroupDesc(obj[2]==null?"":String.valueOf(obj[2]));
					taggroup.setIslogin(String.valueOf(obj[3]).trim().equals("T")?true:false);
					taggroup.setLoginurl(obj[4]==null?"":String.valueOf(obj[4]));
					taggroup.setIsspider(String.valueOf(obj[5]).trim().equals("T")?true:false);
//					taggroup.setIsspider(true);
					
					//设置取该组列表采集标签
					List<IterateDrillTag> dirlltags = getListDrillTagByTCode(taskcode,taggroup.getGroupCode());
					taggroup.setDrillTags(dirlltags);
					
					//设置取该组标签信息
					taggroup.setGroupTags(getListSimagItem(taskcode,taggroup.getGroupCode()));
					
					//设置取该组分页标签
					List<TagItem> pagetags = getListPageTagItem(taskcode,taggroup.getGroupCode());
					taggroup.setPageTags(pagetags);
					
					listtagGroup.add(taggroup);
				}
			}
		  return listtagGroup;
		  
	  }
	
	  /**
	   * 获取元搜索关键词信息
	   * @param taskcode
	   * @return
	   * @throws Exception
	   */
	  public List<KeywordBean> getKeyWordInfo(String taskcode)throws Exception{
		  String sql="select s.taskcode,s.keywords,s.isspider from search_engine_keywords s where s.taskcode='"+taskcode+"'";
		  List<?>  keywordlist = dao.selectSqlAuto(sql);
		  List<KeywordBean> listkeyword = new ArrayList<KeywordBean>();
		  if (null != keywordlist && keywordlist.size() > 0) {
				
				for (int i = 0; i < keywordlist.size(); i++) {
					Object[] obj = (Object[]) keywordlist.get(i);
					KeywordBean keywordbean = new KeywordBean();
					keywordbean.setTaskcode(obj[0]==null?"":String.valueOf(obj[0]));
					keywordbean.setKeywords(obj[1]==null?"":String.valueOf(obj[1]));
					keywordbean.setIsspider(String.valueOf(obj[2]).trim().equals("T")?true:false);
					listkeyword.add(keywordbean);
				}
			}
		  return listkeyword;
		  
	  }
	
	  
	  /**
	   * 获取页面标签合集
	   * @param taskcode
	   * @return
	   * @throws Exception
	   */
		public List<IterateDrillTag> getListDrillTagByTCode(String taskcode,String gcode)throws Exception {
			Map<String,String> paramMap = new HashMap<String,String>();
			paramMap.put("taskcode", taskcode);
			paramMap.put("gcode", gcode);
			//String sql="select t.id,t.taskcode,t.groupcode,t.groupdesc,t.islogin,t.loginurl from spider_taggroup t where t.taskcode=:taskcode";
			   //TAGCODE,PARTAGCODE,TAGTYPE,TAGNAME,TAGATTR,TAGATTRVAL,TARGETATTR,ISGATHER,ISDRILL,UPDATETIME
		     //:tagcode,:partagcode,:tagtype,:tagname,:tagattr,:tagval,:tagattrval,:isspider,:isdrill,'','',sysdate
		    
			String sql="select s.taskcode,s.tagcode,s.partagcode," +
					"s.tagtype,s.tagname,s.tagattr,s.tagattrval," +
					"s.targetattr,s.isgather,s.isdrill,s.targetregexstr from spider_itertaginfo s where s.taskcode=:taskcode";
		
			if(RegexValidate.StrNotNull(gcode)){
				sql+=" and s.gcode=:gcode";
				sql+=" order by to_number(s.tagcode) asc";
			}
			List<?>  drillslist = dao.selectSqlAuto(sql, paramMap);
			if(drillslist!=null&&drillslist.size()>0){
				List<IterateDrillTag> listdrills= new ArrayList<IterateDrillTag>();
				for (int i = 0; i < drillslist.size(); i++) {
					Object[] obj = (Object[]) drillslist.get(i);
					// 迭代钻取
					IterateDrillTag dtag =new IterateDrillTag();
					// 标签编号
					dtag.setCode(obj[1]==null?"":String.valueOf(obj[1]));
					dtag.setParentCode(obj[2]==null?"":String.valueOf(obj[2]));
					// 0目标标签  1容器标签
					dtag.setType(obj[3]==null?"":String.valueOf(obj[3]));
					
					// 页面标签
					TagItem  tag= new TagItem();
					tag.setTagName(obj[4]==null?"":String.valueOf(obj[4]));
					tag.setTagAttrName(obj[5]==null?"":String.valueOf(obj[5]));
					tag.setTagAttrValue(obj[6]==null?"":String.valueOf(obj[6]));
					tag.setTargetAttr(obj[7]==null?"":String.valueOf(obj[7]));
					tag.setTargetRegexStr((obj[10]==null?"":String.valueOf(obj[10])).trim());
					dtag.setTag(tag);
					// 采集标识
					dtag.setGather(String.valueOf(obj[8]).trim().equals("T")?true:false);
					// 钻取标识
					dtag.setDrill(String.valueOf(obj[9]).trim().equals("T")?true:false);
					listdrills.add(dtag);
				}
				return listdrills;
			}
			return null;
		}
		
	  /**
	   * 获取分页标签合集
	   * @param taskcode
	   * @return
	   * @throws Exception
	   */
	  public List<TagItem> getListPageTagItem(String taskcode,String gcode)throws Exception{
		  Map<String,String> paramMap = new HashMap<String,String>();
		    paramMap.put("taskcode", taskcode);
		    paramMap.put("gcode", gcode);
		   
		    String sql="select p.taskcode,p.gcode,p.tagcode,p.partagcode,p.tagtype,p.tagname,p.tagattr,p.tagattrval from  spider_pageinginfo p where p.taskcode=:taskcode";
		    if(RegexValidate.StrNotNull(gcode)){
				sql+=" and p.gcode=:gcode";
			}
		    List<?>  tagitemList = dao.selectSqlAuto(sql, paramMap);
			List<TagItem> listpagetags = new ArrayList<TagItem>();	
		    if (null != tagitemList && tagitemList.size() > 0) {
				for (int i = 0; i < tagitemList.size(); i++) {
					Object[] obj = (Object[]) tagitemList.get(i);
					TagItem tag = new TagItem();
					//组编号
					tag.setGroupCode(obj[1]==null?"":String.valueOf(obj[1]));
					// 标签编号
					tag.setTagCode(obj[2]==null?"":String.valueOf(obj[2]));
					//父级编号
					tag.setParentCode(obj[3]==null?"":String.valueOf(obj[3]));
					//标签类型
					tag.setTagType(obj[4]==null?"":String.valueOf(obj[4]));
					//标签名称
					tag.setTagName(obj[5]==null?"":String.valueOf(obj[5]));
					//标签属性
					tag.setTagAttrName(obj[6]==null?"":String.valueOf(obj[6]));
					//属性值
					tag.setTagAttrValue(obj[7]==null?"":String.valueOf(obj[7]));
					listpagetags.add(tag);
				}
			}
			 return listpagetags;
	  }
	 
	  
	  /**
	   * 获取所有有效的采集任务信息
	   * @param sql
	   * @return
	   * @throws Exception
	   */
	  public List<GatherBean> getGatherInfo(String sql,Map<String, String> paramMap)throws Exception{
		  List<?>  gatherList = dao.selectSqlAuto(sql,paramMap);
		  List<GatherBean> listGather = new ArrayList<GatherBean>(); 
		  if (null != gatherList && gatherList.size() > 0) {
				
				for (int i = 0; i < gatherList.size(); i++) {
					GatherBean gatherbean = new GatherBean();
					Object[] obj = (Object[]) gatherList.get(i);
					gatherbean.setTaskcode(String.valueOf(obj[0] == null ? "": obj[0]));
					gatherbean.setTaskname(String.valueOf(obj[1] == null ? "": obj[1]));
					gatherbean.setTaskurl(String.valueOf(obj[2] == null ? "": obj[2]));
					gatherbean.setEncodeurl(String.valueOf(obj[3] == null ? "": obj[3]));
					gatherbean.setTasktype(String.valueOf(obj[4] == null ? "": obj[4]));
					gatherbean.setIsvalid(String.valueOf(obj[5]).trim().equals("T")?true:false);
					gatherbean.setIsspider(String.valueOf(obj[6]).trim().equals("T")?true:false);
					gatherbean.setJsparser(String.valueOf(obj[7]).trim().equals("T")?true:false);
					gatherbean.setTotalpage(String.valueOf(obj[8] == null ? "": obj[8]));
					gatherbean.setCronexpression(String.valueOf(obj[9] == null ? "": obj[9]));
					gatherbean.setEnabled(String.valueOf(obj[10] == null ? "": obj[10]));
					
					//列表采集
					if(GatherTask.TASK_LIST.equals(gatherbean.getTasktype())){
						List<IterateDrillTag> drillTags  = getListDrillTagByTCode(gatherbean.getTaskcode(),"");
						gatherbean.setDrillTags(drillTags);
					}
					// 模拟采集
					else if(GatherTask.TASK_SIMULATION.equals(gatherbean.getTasktype())){
						// 获取当前任务模拟采集组事件信息  可能包含页面配置、分页配置
						List<TagGroup>  simGroupTags = getTagGroupInfo(gatherbean.getTaskcode());
						List<SIMParam> simparamlist=getParam(gatherbean.getTaskcode());
						gatherbean.setSimGroupTags(simGroupTags);
						gatherbean.setSimparamlist(simparamlist);
					}
					// 元搜索采集
					else if(GatherTask.TASK_SOURCESEARCH.equals(gatherbean.getTasktype())){
						// 获取当前任务关键词
						List<KeywordBean>  keywords = getKeyWordInfo(gatherbean.getTaskcode());
						gatherbean.setKeyWords(keywords);
					}
//					// 当前源采集
//					else if(GatherTask.TASK_CUSOURCE.equals(gatherbean.getTasktype())){
//						
//					}
					//设置分页标签
					List<TagItem> pagetags = getListPageTagItem(gatherbean.getTaskcode(),"");
					gatherbean.setPageTags(pagetags);
					
					listGather.add(gatherbean);
				}
			}
		  return listGather;
		  
	  }
	  /**
	   * 获取任务定时信息
	   * @param sql
	   * @return
	   * @throws Exception
	   */
	  public List<Map<String, Object>>  getCronExpressions(String sql)throws Exception{
//		  List<?>  crontimelist = dao.selectSqlAuto("");
		  Map<String, String> paramMap = new HashMap<String, String>();
		  List<Map<String, Object>> crontimemap = dao.queryMapList(sql,paramMap);
		  return crontimemap;
	  }
	  
	  
	  
	  //获取模拟采集事件参数信息
	  public List<SIMParam> getParam(String taskcode) throws Exception{
		  String selectsql="select tablename from taskcode_table where taskcode="+taskcode;
		  List<?> tablelist=dao.selectSqlAuto(selectsql);
		  if(tablelist==null|tablelist.size()==0){
			  return null;
		  }
		  String tablename=String.valueOf(tablelist.get(0));
		  String sql ="select t.invoicecode,t.invoicenumber,t.seller_regno from "+tablename+" t order by to_number(t.invoiceid)";
		  List<?> list =dao.selectSqlAuto(sql);
		  
		  List <SIMParam> paramlist=new ArrayList<SIMParam>();
		  if (null != list && list.size() > 0) {
				
				for (int i = 0; i < list.size(); i++) {
					SIMParam simparam = new SIMParam();
					Object[] obj = (Object[]) list.get(i);
					simparam.setInvoiceCode(String.valueOf(obj[0] == null ? "": obj[0]));
					simparam.setInvoiceNumber(String.valueOf(obj[1]==null?"":obj[1]));
					simparam.setSellerRegNumber(String.valueOf(obj[2]==null?"":obj[2]));
					paramlist.add(simparam);
				}
			}
		  return paramlist;
	  }
}
