package datacvg.gather.entity;



import java.util.List;

import datacvg.gather.util.TagItem;

/**
 * 
 * 采集模版配置信息
 */
public class GatherBean {
	private Integer id;
	private String taskcode; //任务编号
	public String getTaskcode() {
		return taskcode;
	}

	public void setTaskcode(String taskcode) {
		this.taskcode = taskcode;
	}

	public String getSitecode() {
		return sitecode;
	}

	public void setSitecode(String sitecode) {
		this.sitecode = sitecode;
	}

	public String getTaskurl() {
		return taskurl;
	}

	public void setTaskurl(String taskurl) {
		this.taskurl = taskurl;
	}

	public String getEncodeurl() {
		return encodeurl;
	}

	public void setEncodeurl(String encodeurl) {
		this.encodeurl = encodeurl;
	}

	private String sitecode; //站点编号
	private String taskname; //任务名称
	private String taskurl;  //任务网址
	private String encodeurl;//网页编码
	private boolean isvalid=false;  //是否有效
	private boolean jsparser=false; //相应的详细页是否调用JS解析
	public boolean isIsvalid() {
		return isvalid;
	}

	public void setIsvalid(boolean isvalid) {
		this.isvalid = isvalid;
	}

	public boolean isJsparser() {
		return jsparser;
	}

	public void setJsparser(boolean jsparser) {
		this.jsparser = jsparser;
	}
	private String remark;	
	private String tasktype; //任务类型
	private boolean islogin; //是否需要登录
	private String selectype; //采集方式
	private String totalpage; //要采集的页数
	private boolean isspider=false;//是否采集
	
	private String cronexpression;// 任务执行的时间

	private String enabled;// 任务状态
	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getCronexpression() {
		return cronexpression;
	}

	public void setCronexpression(String cronexpression) {
		this.cronexpression = cronexpression;
	}

	public boolean isIsspider() {
		return isspider;
	}

	public void setIsspider(boolean isspider) {
		this.isspider = isspider;
	}
	public String getTotalpage() {
		return totalpage;
	}

	public void setTotalpage(String totalpage) {
		this.totalpage = totalpage;
	}

	public String getSelectype() {
		return selectype;
	}

	public void setSelectype(String selectype) {
		this.selectype = selectype;
	}

	private List<TagItem> pageTags;//分页标签
	private List<IterateDrillTag> drillTags;//迭代标签合集
	private List<TagGroup> simGroupTags;//模拟组标签集
	
	private List<KeywordBean> keyWords;//元搜索关键词集合
	private List<SIMParam> simparamlist;//模拟采集设置动作数据来源集合
	
	public List<SIMParam> getSimparamlist() {
		return simparamlist;
	}

	public void setSimparamlist(List<SIMParam> simparamlist) {
		this.simparamlist = simparamlist;
	}

	public List<KeywordBean> getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(List<KeywordBean> keyWords) {
		this.keyWords = keyWords;
	}

	public final static String TASK_LIST="0";//列表采集
	public final static String TASK_SIMULATION="1";//模拟采集
	public final static String TASK_SOURCE="2";//元搜索采集
	public final static String TASK_CUSOURCE="3";//当前源采集
	public List<TagItem> getPageTags() {
		return pageTags;
	}


	public void setPageTags(List<TagItem> pageTags) {
		this.pageTags = pageTags;
	}

	public List<IterateDrillTag> getDrillTags() {
		return drillTags;
	}

	public void setDrillTags(List<IterateDrillTag> drillTags) {
		this.drillTags = drillTags;
	}


	public List<TagGroup> getSimGroupTags() {
		return simGroupTags;
	}


	public void setSimGroupTags(List<TagGroup> simGroupTags) {
		this.simGroupTags = simGroupTags;
	}

	//获取解析后分页标签
	public TagItem GetPageTagItem(){
		 
		return ParsePageTag(null);
	}
	
	//解析分页标签集
	public TagItem ParsePageTag(TagItem roottag){
		if(roottag==null){
			roottag=getChildTagItem("0");
		}
		
		//容器标签,递归解析
		if(roottag != null && TagItem.CONTAIN_TAG.equals(roottag.getTagType())){
			
			TagItem childtag =getChildTagItem(roottag.getTagCode());
			if(childtag==null){
				return roottag;
			}
			roottag.add(ParsePageTag(childtag));
		}
		
		return roottag;
	}
	
	//获取分页容器根标签
	public TagItem getChildTagItem(String pcode){
		if(this.getPageTags()!=null){
			for (int i = 0; i < this.getPageTags().size(); i++) {
				//System.out.println(this.getPageTags().get(i).getParentCode());
				//判断是否为分页容器根标签
				if(pcode.equals(this.getPageTags().get(i).getParentCode())){
					return  this.getPageTags().get(i);
				}
			}
		}
		
		return null;
	}
	
	
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public String getTasktype() {
		return tasktype;
	}
	
	public void setTasktype(String tasktype) {
		
			this.tasktype =tasktype;
				
	}
	


	public boolean isIslogin() {
		return islogin;
	}

	public void setIslogin(boolean islogin) {
		this.islogin = islogin;
	}

	public GatherBean() {
		super();
	}
	
	 //克隆对象
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
