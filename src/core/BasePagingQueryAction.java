package core;


import java.util.Map;


/**
 * 分页查询Action基类
 * 
 * @author ZhangBo
 * @version 2012-8-7
 */
public class BasePagingQueryAction extends BaseAction
{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 3253435709505000270L;
	
	/**
	 * 页码
	 */
	protected int page = 1;
	
	/**
	 * 每页数量
	 */
	protected int pagesize = 10;
	
	/**
	 * 排序字段名
	 */
	protected String sortname;
	
	/**
	 * 页排序方向
	 */
	protected String sortorder;

	public int getPage()
	{
		return page;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public int getPagesize()
	{
		return pagesize;
	}

	public void setPagesize(int pagesize)
	{
		this.pagesize = pagesize;
	}

	public String getSortname()
	{
		return sortname;
	}

	public void setSortname(String sortname)
	{
		this.sortname = sortname;
	}

	public String getSortorder()
	{
		return sortorder;
	}

	public void setSortorder(String sortorder)
	{
		this.sortorder = sortorder;
	}
}
