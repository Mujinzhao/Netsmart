package datacvg.gather.entity;
/**
 * 事件操作实体bean
 * @author admin
 *
 */
public class EventEntity {
	//设置事件
	public String eventset;
	// 点击事件
	public String eventclick;
	//双击事件
	public String eventdbclick;
	// 事件类型
	public String eventype;
	//事件入口url
	public String taskurl;
	public String getEventype() {
		return eventype;
	}
	public void setEventype(String eventype) {
		this.eventype = eventype;
	}
	public String getTagattr() {
		return tagattr;
	}
	public void setTagattr(String tagattr) {
		this.tagattr = tagattr;
	}
	public String getTagattrval() {
		return tagattrval;
	}
	public void setTagattrval(String tagattrval) {
		this.tagattrval = tagattrval;
	}
	public String getEventval() {
		return eventval;
	}
	public void setEventval(String eventval) {
		this.eventval = eventval;
	}
	public String tagname;
	public String getTagname() {
		return tagname;
	}
	public void setTagname(String tagname) {
		this.tagname = tagname;
	}
	//标签属性
	public String tagattr;
	//标签属性值
	public String tagattrval;
	
	//事件参数值
	public String eventval;
	
	public String getEventset() {
		return eventset;
	}
	public void setEventset(String eventset) {
		this.eventset = eventset;
	}
	public String getEventclick() {
		return eventclick;
	}
	public void setEventclick(String eventclick) {
		this.eventclick = eventclick;
	}
	public String getEventdbclick() {
		return eventdbclick;
	}
	public void setEventdbclick(String eventdbclick) {
		this.eventdbclick = eventdbclick;
	}
	public String getTaskurl() {
		return taskurl;
	}
	public void setTaskurl(String taskurl) {
		this.taskurl = taskurl;
	}
}
