package datacvg.gather.entity;

import datacvg.gather.util.TagItem;


/**
 * 
 *
 * 迭代钻取标签
 */
public class IterateDrillTag {

	protected String groupCode="";//组编号
	protected String code="";//编号
	protected String parentCode="";//上级编号
    protected String type="0";//0为容器标签，1为目标标签
    protected TagItem tag;//标签对象
    protected boolean isGather=false;//是否采集
    protected boolean isDrill=false;//是否钻取数据
    public final static String GETVAL_TAG ="0";//目标标签
    public final static String CONTAIN_TAG="1";//容器标签(存在子标签)
    
    //标签名
	protected String tagName="";
	
	//属性名
	protected String tagAttrName="";
	//属性值
	protected String tagAttrValue="";
	
	protected  String targetAttr="";//迭代标签获取的目标属性
	
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

	public String getTargetAttr() {
		return targetAttr;
	}

	public void setTargetAttr(String targetAttr) {
		this.targetAttr = targetAttr;
	}

	public String getTargetRegexStr() {
		return targetRegexStr;
	}

	public void setTargetRegexStr(String targetRegexStr) {
		this.targetRegexStr = targetRegexStr;
	}
   
    
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public TagItem getTag() {
		return tag;
	}
	public void setTag(TagItem tag) {
		this.tag = tag;
	}
	public boolean isGather() {
		return isGather;
	}
	public void setGather(boolean isGather) {
		this.isGather = isGather;
	}
	public boolean isDrill() {
		return isDrill;
	}
	public void setDrill(boolean isDrill) {
		this.isDrill = isDrill;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
    
}
