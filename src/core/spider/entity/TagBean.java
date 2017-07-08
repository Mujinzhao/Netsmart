package core.spider.entity;
public class TagBean {

	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private String tagCode;		//标签编号
	private String parTagCode;	//父标签编号
	private String tagName;		//标签名称
	private String tagAttr;		//标签属性
	private String tagAttrVal;	//标签属性值
	private int begIndex;		//标签开始下标
	private int endIndex;		//标签结束下标
	
	private String begindexs;   //标签开始下标 多个
	public String getBegindexs() {
		return begindexs;
	}
	public void setBegindexs(String begindexs) {
		this.begindexs = begindexs;
	}
	public String getEndindexs() {
		return endindexs;
	}
	public void setEndindexs(String endindexs) {
		this.endindexs = endindexs;
	}
	private String endindexs;   //标签结束下标 多个
	private String glcellrows;      //过滤行数
	public String getGlcellrows() {
		return glcellrows;
	}
	public void setGlcellrows(String glcellrows) {
		this.glcellrows = glcellrows;
	}
	private TagBean childTag;	//子标签
	public String getTagCode() {
		return tagCode;
	}
	public void setTagCode(String tagCode) {
		this.tagCode = tagCode;
	}
	public String getParTagCode() {
		return parTagCode;
	}
	public void setParTagCode(String parTagCode) {
		this.parTagCode = parTagCode;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getTagAttr() {
		return tagAttr;
	}
	public void setTagAttr(String tagAttr) {
		this.tagAttr = tagAttr;
	}
	public String getTagAttrVal() {
		return tagAttrVal;
	}
	public void setTagAttrVal(String tagAttrVal) {
		this.tagAttrVal = tagAttrVal;
	}
	public int getBegIndex() {
		return begIndex;
	}
	public void setBegIndex(int begIndex) {
		this.begIndex = begIndex;
	}
	public int getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	public TagBean getChildTag() {
		return childTag;
	}
	public void setChildTag(TagBean childTag) {
		this.childTag = childTag;
	}
}
