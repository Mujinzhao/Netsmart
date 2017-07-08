package datacvg.gather.entity;

import java.util.ArrayList;
import java.util.List;
/**
*
* 
*  采集链接存储对象
*  @author mql
*/
public class Link {

	private String linkurl="";//链接地址
	private List<Link> childLink=null;//链接对象集合
	private boolean isExistChildLink =false;//是否存在子连接
	private boolean isGather =true;//是否采集
	private Link parentLink=null;//父级连接
	private int childlivel;
	public int getChildlivel() {
		return childlivel;
	}
	public void setChildlivel(int childlivel) {
		this.childlivel = childlivel;
	}
	public boolean isGather() {
		return isGather;
	}
	public void setGather(boolean isGather) {
		this.isGather = isGather;
	}
	public String getLinkurl() {
		return linkurl;
	}
	public Link(String linkurl){
		this.linkurl=linkurl;
	}
	public Link(){}
	public void setLinkurl(String linkurl) {
		this.linkurl = linkurl;
	}
	public List<Link> getChildLink() {
		return childLink;
	}
	public void add(String childurl){
		if(childLink==null){
			childLink= new ArrayList<Link>();
		}
		childLink.add(new Link(childurl));
		isExistChildLink=true;
	}
	public void add(Link link){
		if(childLink==null){
			childLink= new ArrayList<Link>();
			isExistChildLink=true;
		}
		childLink.add(link);
		isExistChildLink=true;
	}
	public void setChildLink(List<Link> childLink) {
		this.childLink = childLink;
		isExistChildLink=true;
	}
	public boolean isExistChildLink() {

		return isExistChildLink;
	}
	public Link getParentLink() {
		return parentLink;
	}
	public void setParentLink(Link parentLink) {
		this.parentLink = parentLink;
	}
	public void setExistChildLink(boolean isExistChildLink) {
		this.isExistChildLink = isExistChildLink;
	}

}
