package datacvg.gather.util;
 
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
 

/**
 * 
 *
 * 采集模拟标签
 *
 */
public class TagItem {
	//组编号 
	protected String groupCode="";
	//编号
	protected String tagCode="";
	//父编号
	protected String parentCode="";
	
	//标签名
	protected String tagName="";
	//属性名
	protected String tagAttrName="";
	//属性值
	protected String tagAttrValue="";
	//动作名
	protected String actionName="";
	//动作值
//	protected String actionValue="";//click,set

	protected String actionValue=null;
	//验证码类型详见：http://wiki.dama2.com/index.php?n=ApiDoc.Pricedesc
	protected String validateCodeType = "";

	//动作之后是否有dialog
	protected boolean afterIsDialog=false;
	//dialog类型
	protected String dialogType="";
	//dialog中动作集合
	protected LinkedHashMap<String, String> dialogCommand=null;
	//set动作的属性名称
	protected String actionSetAttrName="";
	protected String actionSetAttrValue="";
	//执行完后下载
	protected boolean isGather=false;

	
	protected List<TagItem> childItems;
	//动作类型
	public final static String ACTION_CLICK="click";
	public final static String ACTION_DBCLICK="dbclick";
	public final static String ACTION_SET="set";
	public final static String ACTION_CHANGE="change";
	// 时间默认参数
	public final static String ACTION_TIMEDUFALT="timedefult";
	//识别验证码
	public final static String ACTION_VALIDATE="validate";
	//填写验证码
	public final static String ACTION_FILLVALIDATE="fillvalidate";
	//循环获取数据源
	public final static String ACTION_GETDATA="getdata";
	//选择日期控件日期
	public final static String ACTION_SETDATE="setdate";
	
	//窗口类型
	public final static String DIALOG_MODAL="modal";
	public final static String DIALOG_ALERT="alert";
	public final static String DIALOG_CONFIRM="confirm";
	
	//判断标签唯一性属性name
	protected String attrNameOnly ="";
	protected String attrValueOnly="";
	protected  int atIndex=0;//标签在容器内位置
	protected  String targetAttr="";//迭代标签获取的目标属性
	private String tagType="1";//标签类型
    public final static String CONTAIN_TAG="0";//容器标签(存在子标签)
    public final static String GETVAL_TAG="1";//目标标签
    protected String targetRegexStr="";//目标属性正则取值匹配
	
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getTagAttrName() {
		return tagAttrName;
	}
	public void setTagAttrName(String tagAttrName) {
		this.tagAttrName = tagAttrName;
	}
	public String getTagAttrValue() {
		return tagAttrValue;
	}
	public void setTagAttrValue(String tagAttrValue) {
		this.tagAttrValue = tagAttrValue;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getActionValue() {
		return actionValue;
	}
	public void setActionValue(String actionValue) {
		this.actionValue = actionValue;
	}
	public String getValidateCodeType()
	{
		return validateCodeType;
	}
	public void setValidateCodeType(String validateCodeType)
	{
		this.validateCodeType = validateCodeType;
	}
	public boolean isAfterIsDialog() {
		return afterIsDialog;
	}
	public void setAfterIsDialog(boolean afterIsDialog) {
		this.afterIsDialog = afterIsDialog;
	}
	public String getDialogType() {
		return dialogType;
	}
	public void setDialogType(String dialogType) {
		this.dialogType = dialogType;
	}
	public LinkedHashMap<String, String> getDialogCommand() {
		return dialogCommand;
	}
	public void setDialogCommand(LinkedHashMap<String, String> dialogCommand) {
		this.dialogCommand = dialogCommand;
	}
	//tagname:attrname,attrvalue
	public void setTagItem(String tagstr){
		
		String[] vals = tagstr.split("[:]");
		String[] attrs=vals[1].split("[,]");
		this.setTagName(vals[0]);
		if(attrs.length>1){
			this.setTagAttrName(attrs[0]);
			this.setTagAttrValue(attrs[1]);
		}
		if(attrs.length>2){
			this.setAttrNameOnly(attrs[2]);
			this.setAttrValueOnly(attrs[3]);
		}
	}
	//action:value
	public void setTagAction(String action){
		
		String[] actionstr= action.split("[:]");
		if(actionstr.length==0){
			this.setActionName("");
		}else if(actionstr.length==1){
			this.setActionName(actionstr[0]);
		}else{
			this.setAction(actionstr[0], actionstr[1]);
		}
	}
	//设置迭代标签
	public void setIterationTag(String tagstr){
		
		String[] vals = tagstr.split("[:]");
		String[] attrs=vals[1].split("[,]");
		this.setTagName(vals[0]);
		this.setAtIndex(Integer.parseInt(attrs[0]));
		if(attrs.length>1){
			this.setTargetAttr(attrs[1]);
		}
	}
	
	//设置标签唯一性属性
	public void setTagItemOnly(String onlyattr){
		String[] vals = onlyattr.split("[,]");
		if(vals.length>1){
			this.setAttrNameOnly(vals[0]);
			this.setAttrValueOnly(vals[1]);
		}
		
	}
	//是否有设置标签唯一属性
	public boolean isExistsOnlyAttr(){
		if(!this.attrNameOnly.equals("")&&!this.attrValueOnly.equals("")){
			return true;
		}
		return false;
	}
	
	public void setAfterIsDialog(int isdialog){
		if(isdialog==1){
			setAfterIsDialog(true);
		}else{
			setAfterIsDialog(false);
		}
	}
	
	public void setAction(String name,String value){
		this.setActionName(name);
		this.setActionValue(value);
		//动作值不为空
		if(TagItem.ACTION_SET.equals(name)&&!value.equals("")){
			//如果包含=
			if(value.indexOf("=")>0){
				String[] params = value.split("[=]");
				this.actionSetAttrName=params[0];
				this.actionSetAttrValue=params[1];
			}else{
				this.actionSetAttrName="value";
				this.actionSetAttrValue=value;
			}
			
		}
	}
//	public void setAction(String name,String[] value){
//		this.setActionName(name);
//		this.setActionValue(value);
//		if(TagItem.ACTION_SET.equals(name)&&value.length!=0){
//			for(String value1:value ){
//				if(value1.indexOf("=")>0){
//					String[] params = value1.split("[=]");
//					this.actionSetAttrName=params[0];
//					this.actionSetAttrValue=params[1];
//				}else{
//					this.actionSetAttrName="value";
//					this.actionSetAttrValue=value1;
//				}
//			}
//		}
//	}
	
	
	public void setAction(String name,boolean afterIsDialog){
		this.setActionName(name);
		this.setAfterIsDialog(afterIsDialog);
	}
	//添加子标签项 
	public void add(TagItem item){
		if(this.childItems==null){
			childItems= new ArrayList<TagItem>();
		}
		childItems.add(item);
	}
	public List<TagItem> getChildItems(){
		
		return childItems;
	}
	public void setChildItems(List<TagItem> childItems){
		this.childItems=childItems;
	}
	//是否存在子标签
	public boolean existchilds(){
		if(this.childItems==null||this.childItems.size()==0){
			return false;
		}
		return true;
	}
	
	public String getActionSetAttrName() {
		return actionSetAttrName;
	}
	public String getActionSetAttrValue() {
		return actionSetAttrValue;
	}
	public String getAttrNameOnly() {
		return attrNameOnly;
	}
	public void setAttrNameOnly(String attrNameOnly) {
		this.attrNameOnly = attrNameOnly;
	}
	public String getAttrValueOnly() {
		return attrValueOnly;
	}
	public void setAttrValueOnly(String attrValueOnly) {
		this.attrValueOnly = attrValueOnly;
	}
	public int getAtIndex() {
		return atIndex;
	}
	public void setAtIndex(int atIndex) {
		this.atIndex = atIndex;
	}
	public String getTargetAttr() {
		return targetAttr;
	}
	public void setTargetAttr(String targetAttr) {
		this.targetAttr = targetAttr;
	}
	public String getTagType() {
		return tagType;
	}
	public void setTagType(String tagType) {
		this.tagType = tagType;
	}
	public boolean isGather() {
		return isGather;
	}
	public void setGather(boolean isGather) {
		this.isGather = isGather;
	}
	public String getTagCode() {
		return tagCode;
	}
	public void setTagCode(String tagCode) {
		this.tagCode = tagCode;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getTargetRegexStr() {
		return targetRegexStr;
	}
	public void setTargetRegexStr(String targetRegexStr) {
		this.targetRegexStr = targetRegexStr;
	}

	public static void main(String[] args)
	{
		String a = "1";
		String b = "1";
		String[] a1 = {"1","2"};
		String[] b1 = {"1","2"};
		System.out.println(a1 == b1);
	}
	
	
}
