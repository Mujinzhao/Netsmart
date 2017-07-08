package datacvg.gather.entity;

import java.util.ArrayList;
import java.util.List;

import datacvg.gather.util.TagItem;

/**
 * 
 * 
 * 模拟标签组
 * 
 */
public class TagGroup
{

	private String taskCode = "";// 任务编号
	private String groupCode = "";// 标签组编号
	private String groupDesc = "";// 组描述
	private boolean islogin = false;// 登录标识

	public boolean isIslogin()
	{
		return islogin;
	}

	public void setIslogin(boolean islogin)
	{
		this.islogin = islogin;
	}

	private boolean isspider = false;// 采集标识

	public boolean isIsspider()
	{
		return isspider;
	}

	public void setIsspider(boolean isspider)
	{
		this.isspider = isspider;
	}

	private String loginurl = "";// 登录地址

	public String getLoginurl()
	{
		return loginurl;
	}

	public void setLoginurl(String loginurl)
	{
		this.loginurl = loginurl;
	}

	private List<TagItem> groupTags;// 标签组合集
	private boolean isExistPageTag = false;// 是否存在分页
	private List<TagItem> pageTags;// 分页标签
	private List<IterateDrillTag> drillTags;// 迭代标签合集
	private boolean isSIMGather = false;// 是否模拟采集
	private List<TagItem> iframeTags;// iframe容器标签合集
	

	public String getTaskCode()
	{
		return taskCode;
	}

	public void setTaskCode(String taskCode)
	{
		this.taskCode = taskCode;
	}

	public String getGroupCode()
	{
		return groupCode;
	}

	public void setGroupCode(String groupCode)
	{
		this.groupCode = groupCode;
	}

	public List<TagItem> getGroupTags()
	{
		return groupTags;
	}

	public void setGroupTags(List<TagItem> groupTags)
	{
		this.groupTags = groupTags;
	}

	public boolean isExistPageTag()
	{
		return isExistPageTag;
	}

	public void setExistPageTag(boolean isExistPageTag)
	{
		this.isExistPageTag = isExistPageTag;
	}

	public List<TagItem> getPageTags()
	{
		return pageTags;
	}

	public void setPageTags(List<TagItem> pageTags)
	{
		this.pageTags = pageTags;
	}

	public List<IterateDrillTag> getDrillTags()
	{
		return drillTags;
	}

	public void setDrillTags(List<IterateDrillTag> drillTags)
	{
		this.drillTags = drillTags;
	}

	/**
	 * 标签组数据整理
	 * 
	 * @return
	 */
	public void InitTagSortOut()
	{

		if (this.getGroupTags() != null && this.getGroupTags().size() > 0)
		{

			for (int i = 0; i < this.getGroupTags().size(); i++)
			{
				// 添加容器标签
				if ("iframe".equals(this.getGroupTags().get(i).getTagName()))
				{

					if (this.iframeTags == null)
					{
						this.iframeTags = new ArrayList<TagItem>();
					}
					this.iframeTags.add(this.getGroupTags().get(i));
				}
				// 检查是否存在,采集
				if (this.getGroupTags().get(i).isGather())
				{
					this.setSIMGather(true);
					break;
				}
			}
			// 解析组合标签
			ParseTagItem();

		}

	}

	/**
	 * 标签组数据整理
	 * 
	 * @return
	 */
	public void InitTagSortOut(boolean isspider)
	{
		if (this.getGroupTags() != null && this.getGroupTags().size() > 0)
		{
			for (int i = 0; i < this.getGroupTags().size(); i++)
			{
				// 添加容器标签
				if ("iframe".equals(this.getGroupTags().get(i).getTagName()))
				{
					if (this.iframeTags == null)
					{
						this.iframeTags = new ArrayList<TagItem>();
					}
					this.iframeTags.add(this.getGroupTags().get(i));
				}
				// 检查是否存在,采集
				if (isspider)
				{
					this.setSIMGather(true);
					break;
				}
			}
			// 解析组合标签
			ParseTagItem();
		}
	}

	// 解析标签组
	public void ParseTagItem()
	{
		// 递归组合标签关系
		this.groupTags = getChildTagItem("0");

	}

	// 获取模拟容标签
	public List<TagItem> getChildTagItem(String pcode)
	{
		if (this.getGroupTags() != null)
		{
			List<TagItem> list = new ArrayList<TagItem>();
			for (int i = 0; i < this.getGroupTags().size(); i++)
			{
				// 判断是否为分页容器根标签
				if (pcode.equals(this.getGroupTags().get(i).getParentCode()))
				{
					TagItem tag = this.getGroupTags().get(i);
					List<TagItem> childitems = getChildTagItem(tag.getTagCode());
					if (childitems != null)
					{
						tag.setChildItems(childitems);
					}
					list.add(tag);
				}
			}
			if (list.size() == 0)
			{
				return null;
			}
			return list;
		}

		return null;
	}

	public boolean isSIMGather()
	{
		return isSIMGather;
	}

	public void setSIMGather(boolean isSIMGather)
	{
		this.isSIMGather = isSIMGather;
	}

	public List<TagItem> getIframeTags()
	{
		return iframeTags;
	}

	public void setIframeTags(List<TagItem> iframeTags)
	{
		this.iframeTags = iframeTags;
	}

	public String getGroupDesc()
	{
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc)
	{
		this.groupDesc = groupDesc;
	}

}
