package datacvg.gather.entity;

/**
 * 元搜索采集关键词
 * @author admin
 *
 */
public class KeywordBean {

	private String taskcode;//任务编号
	public String getTaskcode() {
		return taskcode;
	}
	public void setTaskcode(String taskcode) {
		this.taskcode = taskcode;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	private String keywords;//搜索关键词
	
	private boolean isspider=false;//采集标识
	public boolean isIsspider() {
		return isspider;
	}
	public void setIsspider(boolean isspider) {
		this.isspider = isspider;
	}
}
