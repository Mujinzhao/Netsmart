package datacvg.gather.entity;


import java.util.List;

import datacvg.gather.util.TagItem;

/**
 * 
 * 
 * 采集任务
 * @author mql
 *
 */
public class GatherTask implements Cloneable {
	
	private String siteCode="";//站点编号
	private String taskCode="";//任务编号
	private String loginAddr="";//登录地址
	private String taskEncode="GBK";//站点编码
	private String taskAddr="";//任务地址
	private String taskDesc="";//任务描述
	private String taskType="";//任务类型

	public final static String TASK_LIST="0";//列表采集
	public final static String TASK_SIMULATION="1";//模拟采集
	public final static String TASK_SOURCESEARCH="2";//元搜索采集
	public final static String TASK_CUSOURCE="3";//当前源采集
	
	private boolean isRequireLogin=false;//是否需要登录采集
	private boolean isExistPageTag=false;//是否存在分页
	private boolean isActive=false;//任务是否可用
	

	private List<TagItem> pageTags;//分页标签
	private List<IterateDrillTag> drillTags;//迭代标签合集
	private List<TagGroup> simGroupTags;//模拟组标签集
	
	public String getSiteCode() {
		return siteCode;
	}
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getTaskAddr() {
		return taskAddr;
	}
	public void setTaskAddr(String taskAddr) {
		this.taskAddr = taskAddr;
	}
	public String getTaskDesc() {
		return taskDesc;
	}
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public boolean isRequireLogin() {
		return isRequireLogin;
	}
	public void setRequireLogin(boolean isRequireLogin) {
		this.isRequireLogin = isRequireLogin;
	}
	public boolean isExistPageTag() {
		if(this.getPageTags()!=null&&this.getPageTags().size()>0){
			return true;
		}
		return isExistPageTag;
	}
	public void setExistPageTag(boolean isExistPageTag) {
		this.isExistPageTag = isExistPageTag;
	}
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
	public String getLoginAddr() {
		return loginAddr;
	}
	public void setLoginAddr(String loginAddr) {
		this.loginAddr = loginAddr;
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
		if(TagItem.CONTAIN_TAG.equals(roottag.getTagType())){
			
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
				//判断是否为分页容器根标签
				if(pcode.equals(this.getPageTags().get(i).getParentCode())){
					return  this.getPageTags().get(i);
				}
			}
		}
		
		return null;
	}
	public String getTaskEncode() {
		return taskEncode;
	}
	public void setTaskEncode(String taskEncode) {
		this.taskEncode = taskEncode;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	 //克隆对象
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    


}
