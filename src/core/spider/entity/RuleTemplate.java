package core.spider.entity;
/**
 * 解析配置bean
 */
public class RuleTemplate {
	private String id;
	private String fieldName;       //同最终存储表中的字段名称一致
	private String prefix;          //规则前缀
	private String suffix;          //规则后缀
	private String outRegex;        //内容排除正则表达式
	private String savetable;       //最终数据存入表
	private String taskCode;		//任务编号
	private String structuredCode;	//多结构编号
	private String remarks;         //备注
	private String fieldIndex;		//字段对应的下标值
	private String tagRuleId;		//解析模板标识
	
	private String extractregex; //使用正则表达式匹配
	
	public String getExtractregex() {
		return extractregex;
	}

	public void setExtractregex(String extractregex) {
		this.extractregex = extractregex;
	}

	public String getFieldIndex() {
		return fieldIndex;
	}

	public void setFieldIndex(String fieldIndex) {
		this.fieldIndex = fieldIndex;
	}

	public String getTagRuleId() {
		return tagRuleId;
	}

	public void setTagRuleId(String tagRuleId) {
		this.tagRuleId = tagRuleId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getOutRegex() {
		return outRegex;
	}

	public void setOutRegex(String outRegex) {
		this.outRegex = outRegex;
	}

	public String getSavetable() {
		return savetable;
	}

	public void setSavetable(String savetable) {
		this.savetable = savetable;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getStructuredCode() {
		return structuredCode;
	}

	public void setStructuredCode(String structuredCode) {
		this.structuredCode = structuredCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
