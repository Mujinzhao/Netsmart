package core.spider.entity;
import java.util.HashMap;

public class TagruleTemplate {

	private TagBean tagBean;		//标签配置信息
	private HashMap<Integer, String> relationMap; //解析字段与表中列的对应下标
	private String savetable;       //最终数据存入表
	private String taskCode;		//任务编号
	private String structuredCode;	//多结构编号
	public TagBean getTagBean() {
		return tagBean;
	}
	public void setTagBean(TagBean tagBean) {
		this.tagBean = tagBean;
	}
	public HashMap<Integer, String> getRelationMap() {
		return relationMap;
	}
	public void setRelationMap(HashMap<Integer, String> relationMap) {
		this.relationMap = relationMap;
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
}
